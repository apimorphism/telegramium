package telegramium.bots.examples

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import scala.collection.mutable
import scala.jdk.CollectionConverters.*
import scala.util.Random

import io.circe.Json
import io.circe.parser.parse

/** Scrubs recorded Telegram JSON of personal data, so it can be committed as fixtures.
  *
  * Usage: FixtureScrubber [rawDir] [fixturesDir], turning what CaptureBot recorded in rawDir (default .fixtures-raw)
  * into fixturesDir (default telegramium-core's fixtures): updates/<kind>-<n>.json and responses/<method>-<n>.json.
  * Both subdirectories are replaced, so scrub everything recorded so far, not just the latest session.
  */
object FixtureScrubber {

  def main(args: Array[String]): Unit = {
    val raw      = Paths.get(args.lift(0).getOrElse(".fixtures-raw"))
    val fixtures = Paths.get(args.lift(1).getOrElse("telegramium-core/src/test/resources/fixtures"))

    def files(dir: Path): List[Path] =
      if (Files.isDirectory(dir)) Files.walk(dir).iterator().asScala.filter(_.toString.endsWith(".json")).toList
      else List.empty
    // updates in the order Telegram sent them
    val updates   = files(raw.resolve("updates")).sortBy(_.getFileName.toString.stripSuffix(".json").toLong)
    val responses = files(raw.resolve("responses")).sortBy(_.toString)
    val read      = (path: Path) => parse(new String(Files.readAllBytes(path), StandardCharsets.UTF_8)).toTry.get
    val scrubbed  = scrub((updates ++ responses).map(read))

    // an update is named after its one field besides update_id, a response after its method
    val names = scrubbed.take(updates.size).map { update =>
      s"updates/${update.asObject.flatMap(_.keys.find(_ != "update_id")).getOrElse("unknown")}"
    } ++ responses.map(path => s"responses/${path.getParent.getFileName}")
    val numbered = names.zipWithIndex.map { case (name, i) => f"$name-${names.take(i).count(_ == name) + 1}%03d.json" }

    List("updates", "responses").map(fixtures.resolve).filter(Files.exists(_)).foreach { dir =>
      Files.walk(dir).iterator().asScala.toList.reverse.foreach(Files.delete)
    }
    numbered.zip(scrubbed).foreach { case (name, json) =>
      val path = fixtures.resolve(name)
      Files.createDirectories(path.getParent)
      Files.write(path, (json.spaces2 + "\n").getBytes(StandardCharsets.UTF_8))
    }
    println(s"Wrote ${updates.size} updates and ${responses.size} responses to $fixtures")
  }

  // Strings are scrubbed unless their key is listed here: anything else may be personal, including fields that are new
  // to the Bot API. Decoders dispatch on type, status and source, so scrubbing those would turn values into unknown
  // ones, which the fixture check catches.
  private val keptStrings = Set("type", "chat_type", "status", "source", "mime_type", "emoji", "currency")

  // shared locations and venues are moved here
  private val fixedPlace = Map("latitude" -> 52.52, "longitude" -> 13.405)

  // personal numbers that aren't IDs, under objects whose fields have generic names: each field gets a fixed value
  private val fixedObjects = Map("birthdate" -> Map("day" -> 1, "month" -> 1, "year" -> 2000))

  // keys ending in _id that hold an index or an enum value, not an identifier
  private val notIds =
    Set("accent_color_id", "profile_accent_color_id", "correct_option_id", "correct_option_ids", "option_ids")

  private def isId(key: String): Boolean =
    (key == "id" || key.endsWith("_id") || key.endsWith("_ids") || key == "chat_instance") && !notIds(key)

  /** Scrubs the documents together, so that an ID gets the same fake value wherever it appears. */
  def scrub(docs: List[Json]): List[Json] = {
    val ids       = mutable.Map.empty[Long, Long]
    val stringIds = mutable.Map.empty[String, String]
    // seeded, so that scrubbing the same recordings again gives the same fixtures
    val random = new Random(0)

    // a fresh fake ID of the same kind: a channel or supergroup ID (-100…), one above 2³¹, or a small one
    def fakeId(id: Long): Long = ids.getOrElseUpdate(
      id, {
        val n = ids.size + 1L
        if (id <= -1000000000000L) -1000000000000L - n
        else if (math.abs(id) > Int.MaxValue) math.signum(id) * (7000000000L + n)
        else math.signum(id) * (1000L + n)
      }
    )

    // a fresh fake string ID with a random character of the same class in place of each letter and digit
    def fakeStringId(id: String): String = stringIds.getOrElseUpdate(
      id,
      id.map { c =>
        if (c.isDigit) ('0' + random.nextInt(10)).toChar
        else if (c.isUpper) ('A' + random.nextInt(26)).toChar
        else if (c.isLower) ('a' + random.nextInt(26)).toChar
        else c
      }
    )

    def scrubValue(key: String, json: Json): Json =
      json.fold(
        json,
        _ => json,
        num =>
          num.toLong match {
            case Some(id) if isId(key) => Json.fromLong(fakeId(id))
            case _                     => fixedPlace.get(key).flatMap(Json.fromDouble).getOrElse(json)
          },
        s =>
          if (keptStrings(key)) json
          else if (isId(key)) Json.fromString(fakeStringId(s))
          else Json.fromString(placeholder(s)),
        arr => Json.fromValues(arr.map(scrubValue(key, _))),
        obj => {
          val fixed = fixedObjects.get(key)
          Json.fromFields(
            obj.toIterable.map((k, v) => k -> fixed.fold(scrubValue(k, v))(_.get(k).fold(v)(Json.fromInt)))
          )
        }
      )

    docs.map(scrubValue("", _))
  }

  // keeps the length in UTF-16 units, so message entities' offsets still fit the text
  private def placeholder(s: String): String =
    s.codePoints()
      .toArray
      .map { cp =>
        val units = Character.charCount(cp)
        if (Character.isLetter(cp)) "x" * units
        else if (Character.isDigit(cp)) "0" * units
        else new String(Array(cp), 0, 1)
      }
      .mkString

}
