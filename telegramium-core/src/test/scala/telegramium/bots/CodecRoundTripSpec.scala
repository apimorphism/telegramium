package telegramium.bots

import org.scalacheck.{Prop, Test}
import org.scalacheck.util.Pretty
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.GenPrims.RoundTripCase

class CodecRoundTripSpec extends AnyFlatSpec with Matchers {

  private val samples = 100

  private def roundTrips[A](c: RoundTripCase[A]): Unit =
    c.name should "survive encode → decode" in {
      val prop = Prop.forAllShrink(c.gen, c.shrink.shrink) { a =>
        val json = c.encoder(a)
        c.decoder.decodeJson(json) match {
          case Right(decoded) => Prop(decoded == a) :| s"decoded to a different value: $decoded"
          case Left(error)    => Prop.falsified :| s"failed to decode ${json.noSpaces}: $error"
        }
      }
      val result = Test.check(Test.Parameters.default.withMinSuccessfulTests(samples), prop)
      // ScalaTest's own ScalaCheck integration prints the original example; print the shrunk one
      result.status match {
        case Test.Failed(args, labels) =>
          fail(
            s"${c.name} failed after ${result.succeeded} successful samples.\n" +
              s"Shrunk example (${args.head.shrinks} shrinks), as JSON: ${c.encoder(args.head.arg.asInstanceOf[A]).noSpaces}\n" +
              s"Shrunk example: ${args.head.arg}\n" +
              labels.mkString("\n")
          )
        case _ if !result.passed => fail(s"${c.name}: ${Pretty.pretty(result, Pretty.Params(0))}")
        case _                   => succeed
      }
    }

  RoundTrips.all.foreach(c => roundTrips(c))
}
