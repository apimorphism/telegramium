package telegramium.bots.high

import java.io.File
import java.nio.file.Files

import cats.effect.IO

import io.circe.Json
import io.circe.parser.parse
import org.http4s.Media
import org.http4s.circe.*
import org.http4s.headers.`Content-Type`
import org.http4s.multipart.Multipart
import org.scalacheck.Prop
import org.scalacheck.Test
import org.scalacheck.rng.Seed
import org.scalacheck.util.Pretty
import org.scalatest.freespec.AnyFreeSpec

import telegramium.bots.InputPartFile
import telegramium.bots.MethodCases
import telegramium.bots.MethodCases.MethodCase
import telegramium.bots.GenPrims.FieldsOf
import telegramium.bots.JsonFields
import telegramium.bots.client.Method
import telegramium.bots.client.MethodReq

object WireSpec {

  /** A wire request: a JSON body, or a multipart form with its fields as text and its files as part name -> filename */
  sealed trait Wire
  final case class JsonWire(json: Json)                                                   extends Wire
  final case class FormWire(fields: Map[String, String], files: Vector[(String, String)]) extends Wire

  /** A value in the arguments, at its path, with the JSON it was sent as (None: left out of the JSON), and for a field,
    * which: `<type>.<JSON key>`, and whether it's an optional array
    */
  final case class Sent(value: Any, json: Option[Json], path: String, field: Option[String], optionalArray: Boolean)

}

/** The wire checks, run on every Method built from generated arguments and sent by `send`: one subclass per path a
  * Method takes to Telegram
  */
abstract class WireSpec extends AnyFreeSpec {

  import WireSpec.*

  private val samples = 100

  // A run's samples are random, so a failure prints its seed; WIRE_SEED=<seed> replays that run
  private val seed = sys.env.get("WIRE_SEED").fold(Seed.random())(Seed.fromBase64(_).get)

  // Generated InputPartFiles are plain file names; the request is only built from files that exist
  private val tmpDir = Files.createTempDirectory("wire").toFile
  tmpDir.deleteOnExit()

  private def withTempFiles[A](method: Method[A]): Method[A] = {
    val files = method.payload.files.map {
      case (name, InputPartFile(file)) =>
        val tmp = new File(tmpDir, file.getName)
        if (!tmp.exists()) {
          Files.write(tmp.toPath, Array[Byte](1))
          tmp.deleteOnExit()
        }
        name -> InputPartFile(tmp)
      case other => other
    }
    new MethodReq(method.payload.copy(files = files), method.decoder)
  }

  /** The wire request `method` is sent as; its local files exist */
  protected def send(method: Method[?]): Wire

  /** A recorded HTTP message, a request or a response, as the wire request it carries */
  protected def readWire(message: Media[IO]): IO[Wire] =
    if (message.headers.get[`Content-Type`].exists(_.mediaType.isMultipart))
      message.as[Multipart[IO]].flatMap { m =>
        MultipartFields.formFields(m).map(FormWire(_, MultipartFields.fileParts(m)))
      }
    else message.as[Json].map(JsonWire(_))

  /** The wire request as JSON, read back the way Telegram reads it, for comparing with the encoded arguments. A form
    * field is text: a string as it is, anything else as the JSON it holds. A top-level file param is a part under its
    * own key, which stands for `attach://<key>`, unless a form field has that name too: then the field is what's
    * compared, so a file also sent as text fails.
    */
  private def sentJson(wire: Wire, encoded: Json): Json = wire match {
    case JsonWire(json)          => json
    case FormWire(fields, parts) =>
      val expected = encoded.asObject.fold(Map.empty[String, Json])(_.toMap)
      val read     = fields.map { case (name, text) =>
        name -> (expected.get(name) match {
          case Some(j) if j.isString => Json.fromString(text)
          case _                     => parse(text).getOrElse(Json.fromString(text))
        })
      }
      val ownParts = parts.collect {
        case (name, _) if expected.contains(name) => name -> Json.fromString(s"attach://$name")
      }
      Json.fromFields(ownParts ++ read)
  }

  private def strings(json: Json): List[String] =
    json.fold(Nil, _ => Nil, _ => Nil, s => List(s), _.toList.flatMap(strings), _.values.toList.flatMap(strings))

  // every value reachable from a Method's arguments; unlike walk, it needs no field names
  private def reachable(a: Any): Iterator[Any] =
    Iterator(a) ++ (a match {
      case it: Iterable[?] => it.iterator.flatMap(reachable)
      case p: Product      => p.productIterator.flatMap(reachable)
      case _               => Iterator.empty
    })

  /** Where the sent JSON differs from the encoded arguments. The one difference allowed is a local file: encoded as its
    * file name, sent as `attach://<name>`, where the part `<name>` holds that file.
    */
  private def differences(localFiles: Set[String], parts: Map[String, String])(
    encoded: Json,
    sent: Json,
    path: String
  ): List[String] =
    (encoded.asObject, sent.asObject) match {
      case (Some(e), Some(s)) =>
        (e.keys ++ s.keys).toList.distinct.flatMap { key =>
          (e(key), s(key)) match {
            case (Some(ej), Some(sj)) => differences(localFiles, parts)(ej, sj, s"$path.$key")
            case (Some(ej), None)     => List(s"$path.$key: left out, was $ej")
            case (None, Some(sj))     => List(s"$path.$key: sent $sj, not in the arguments")
            case (None, None)         => Nil
          }
        }
      case _ =>
        (encoded.asArray, sent.asArray) match {
          case (Some(e), Some(s)) if e.size == s.size =>
            e.zip(s).zipWithIndex.toList.flatMap { case ((ej, sj), i) =>
              differences(localFiles, parts)(ej, sj, s"$path[$i]")
            }
          case _ =>
            encoded.asString.filter(localFiles) match {
              case Some(file)
                  if sent.asString.exists(s => s.startsWith("attach://") && parts.get(s.drop(9)).contains(file)) =>
                Nil
              case Some(file)              => List(s"$path: the local file $file sent as $sent")
              case None if encoded == sent => Nil
              case None                    => List(s"$path: sent $sent, was $encoded")
            }
        }
    }

  private def checkMethod[A](c: MethodCase[A], check: (Wire, Any, Json) => Prop): Unit =
    c.name in {
      val prop = Prop.forAllShrink(c.gen, c.shrink.shrink) { args =>
        val wire = send(withTempFiles(c.method(args)))
        check(wire, args, c.json(args)) :| s"wire request: $wire"
      }
      val result = Test.check(Test.Parameters.default.withMinSuccessfulTests(samples).withInitialSeed(seed), prop)
      // the report has the failing labels and the shrunk arguments
      if (!result.passed) fail(s"${c.name} (WIRE_SEED=${seed.toBase64}): ${Pretty.pretty(result, Pretty.Params(0))}")
    }

  // https://github.com/apimorphism/telegramium/issues/666, https://github.com/apimorphism/telegramium/issues/669
  "The wire request is the encoded arguments, with each local file sent as an attachment" - {
    MethodCases.all.foreach { c =>
      checkMethod(
        c,
        (wire, args, encoded) => {
          val parts = wire match {
            case FormWire(_, ps) => ps
            case JsonWire(_)     => Vector.empty
          }
          val sent       = sentJson(wire, encoded)
          val localFiles = reachable(args).collect { case InputPartFile(f) => f.getName }.toSet
          val diffs      = differences(localFiles, parts.toMap)(encoded, sent, "")
          // each part referenced once: none left over, none sent twice
          val referenced = strings(sent).collect { case s if s.startsWith("attach://") => s.drop(9) }
          (Prop(diffs.isEmpty) :| diffs.mkString("\n")) &&
          (Prop(referenced.sorted == parts.map(_._1).sorted) :| s"parts ${parts.map(_._1)}, referenced $referenced")
        }
      )
    }
  }

  // A case class's fields under their JSON keys, generated with the encoders: no field names, so this runs on 2.12 too
  private val fieldsOf: Map[Class[?], FieldsOf[?]] = JsonFields.all.map(f => (f.runtimeClass: Class[?]) -> f).toMap

  // One layer: the values one level down, each with the JSON it was sent as; Left for a case class the list doesn't have
  private def step(node: Sent): Either[String, List[Sent]] =
    node.value match {
      // sent as the value they hold
      case Some(v)                  => Right(List(node.copy(value = v, field = None, optionalArray = false)))
      case iozhik.OpenEnum.Known(v) => Right(List(node.copy(value = v, field = None, optionalArray = false)))
      case it: Iterable[?]          =>
        val js = node.json.flatMap(_.asArray).getOrElse(Vector.empty)
        Right(it.toList.zipWithIndex.map { case (v, i) => Sent(v, js.lift(i), s"${node.path}[$i]", None, false) })
      case p: Product =>
        node.json.flatMap(_.asObject) match {
          // a case class sent as an object: each field under its key
          case Some(obj) =>
            fieldsOf.get(p.getClass) match {
              case Some(fields) =>
                Right(fields(p).map { case (k, v) =>
                  Sent(v, obj(k.key), s"${node.path}.${k.key}", Some(s"${fields.typeName}.${k.key}"), k.optionalArray)
                })
              // a case object, sent as its tag
              case None if p.productArity == 0 => Right(Nil)
              case None                        =>
                Left(s"${node.path}: ${p.productPrefix} is sent as an object, but JsonFields doesn't list it")
            }
          // sent as the value it holds, like ChatIntId, InputLinkFile or the RichText shorthands
          case None =>
            Right(p.productIterator.toList.map(v => node.copy(value = v, field = None, optionalArray = false)))
        }
      case _ => Right(Nil)
    }

  // The recursion, written once: every value in the arguments, with the JSON it was sent as
  private def walk(node: Sent): List[Either[String, Sent]] =
    Right(node) :: (step(node) match {
      case Left(problem) => List(Left(problem))
      case Right(next)   => next.flatMap(walk)
    })

  // a key the encoders write differently would make the check pass without looking
  private def omittedKey(s: Sent): Option[String] = s match {
    case Sent(None, None, _, _, _)                          => None
    case Sent(it: Iterable[?], None, _, _, _) if it.isEmpty => None
    case Sent(v, None, path, Some(field), _) => Some(s"$path: $field holds $v, but the JSON has no such key")
    case _                                   => None
  }

  // https://github.com/apimorphism/telegramium/issues/686
  private def emptyOptionalArray(s: Sent): Option[String] = s match {
    case Sent(_, Some(j), path, Some(_), true) if j.asArray.exists(_.isEmpty) => Some(s"$path: sent as []")
    case _                                                                    => None
  }

  // the optional arrays seen holding values under their keys, by any Method
  private val matched = scala.collection.mutable.Set.empty[String]

  // https://github.com/apimorphism/telegramium/issues/686
  "No optional array is sent as [], unless it is an Option[List]" - {
    MethodCases.all.foreach { c =>
      checkMethod(
        c,
        (wire, args, encoded) => {
          val sent = walk(Sent(args, Some(sentJson(wire, encoded)), "", None, false))
          sent.foreach {
            case Right(Sent(it: Iterable[?], Some(_), _, Some(field), true)) if it.nonEmpty => matched += field
            case _                                                                          => ()
          }
          val problems = sent.flatMap {
            case Left(problem) => List(problem)
            case Right(s)      => omittedKey(s) ++ emptyOptionalArray(s)
          }
          Prop(problems.isEmpty) :| problems.mkString("\n")
        }
      )
    }

    // runs after the Methods above: a list the check never matches would make it pass without looking
    "The optional arrays are listed, and each is found holding values in some Method's arguments" in {
      val listed = JsonFields.all.flatMap(f => f.keys.filter(_.optionalArray).map(k => s"${f.typeName}.${k.key}"))
      assert(listed.nonEmpty, "no optional arrays listed")
      val unmatched = listed.filterNot(matched)
      assert(unmatched.isEmpty, s"never found holding values under their key: ${unmatched.mkString(", ")}")
    }
  }

}
