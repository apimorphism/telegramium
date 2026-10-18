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

}
