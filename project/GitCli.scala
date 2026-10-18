import scala.sys.process.Process
import scala.util.Try

/** sbt-dynver covers versioning; this just gets HEAD's commit for buildinfo, via the git binary
  * instead of JGit so it also works from a `git worktree` checkout.
  */
object GitCli {

  def headCommit: Option[String] =
    Try(Process("git", Seq("rev-parse", "HEAD")).!!.trim).toOption.filter(_.nonEmpty)

}
