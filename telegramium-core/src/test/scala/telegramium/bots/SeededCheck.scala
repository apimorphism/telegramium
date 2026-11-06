package telegramium.bots

import org.scalacheck.{Prop, Test}
import org.scalacheck.rng.Seed
import org.scalacheck.util.Pretty

/** Runs properties on 100 random samples each. A run's samples are random, so a failure prints its seed;
  * `<seedVariable>=<seed>` replays that run.
  */
final class SeededCheck(seedVariable: String) {

  private val samples = 100

  private val seed = sys.env.get(seedVariable).fold(Seed.random())(Seed.fromBase64(_).get)

  /** None if `prop` holds; otherwise the failure report, with the shrunk example as `show` prints it. ScalaTest's own
    * ScalaCheck integration prints the original example.
    */
  def failure(name: String, prop: Prop, show: Any => String = _.toString): Option[String] = {
    val result = Test.check(Test.Parameters.default.withMinSuccessfulTests(samples).withInitialSeed(seed), prop)
    result.status match {
      case Test.Failed(args, labels) =>
        Some(
          s"$name failed after ${result.succeeded} successful samples ($seedVariable=${seed.toBase64}).\n" +
            s"Shrunk example (${args.head.shrinks} shrinks): ${show(args.head.arg)}\n" +
            labels.mkString("\n")
        )
      case _ if !result.passed =>
        Some(s"$name ($seedVariable=${seed.toBase64}): ${Pretty.pretty(result, Pretty.Params(0))}")
      case _ => None
    }
  }

}
