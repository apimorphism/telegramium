import java.time.format.DateTimeFormatter
import java.time.{ZoneId, ZonedDateTime}

object Version {

  case class BuildException(override val getMessage: String) extends Exception

  val timestamp: String = ZonedDateTime
    .now(ZoneId.of("UTC"))
    .format(DateTimeFormatter.ofPattern("yyMMddHHmm"))

  def commit(commit: Option[String]): String = {
    commit.map(_.take(8)).getOrElse(throw BuildException("Can't build empty repository"))
  }

  def mkVersion(
    semVer: String,
    branch: String,
    gitVer: Option[String],
    isDirty: Boolean
  ): String = {
    val dirty = if (isDirty) "-DIRTY" else ""
    branch match {
      case "rc" =>
        val tagVer = gitVer.getOrElse(throw BuildException("No tag found to make an RC version"))
        s"$tagVer$dirty"
      case "rl" =>
        if (isDirty) {
          throw BuildException("Can't release with uncommitted changes in repo")
        } else {
          s"$semVer$dirty"
        }
      case _ =>
        s"$semVer$dirty-SNAPSHOT"
    }
  }

}
