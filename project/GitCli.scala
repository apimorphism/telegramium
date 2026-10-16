import scala.sys.process.Process
import scala.util.Try

/** A minimal, worktree-safe replacement for sbt-git's JGit-backed settings. JGit throws
  * NoWorkTreeException when run from a `git worktree` checkout, since it doesn't resolve the linked
  * .git file / commondir layout. Shelling out to the git binary avoids that entirely.
  */
object GitCli {

  private def run(args: String*): Option[String] =
    Try(Process("git" +: args).!!.trim).toOption.filter(_.nonEmpty)

  def currentBranch: String =
    run("rev-parse", "--abbrev-ref", "HEAD").getOrElse("master")

  def describedVersion: Option[String] =
    run("describe", "--tags")

  def hasUncommittedChanges: Boolean =
    run("status", "--porcelain").nonEmpty

  def headCommit: Option[String] =
    run("rev-parse", "HEAD")

}
