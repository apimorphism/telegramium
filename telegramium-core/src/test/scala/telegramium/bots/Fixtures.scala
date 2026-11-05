package telegramium.bots

import java.io.{File => JFile}
import java.nio.charset.StandardCharsets
import java.nio.file.Files

import io.circe.Json
import io.circe.parser.parse

/** The JSON fixtures in telegramium-core's test resources (see CONTRIBUTING.md), also read by telegramium-high's tests
  */
object Fixtures {

  /** The fixtures in fixtures/`dir` (updates, responses or future), sorted by name */
  def files(dir: String): List[JFile] =
    Option(getClass.getResource(s"/fixtures/$dir"))
      .map(url => new JFile(url.toURI).listFiles().toList.filter(_.getName.endsWith(".json")).sortBy(_.getName))
      .getOrElse(List.empty)

  def read(file: JFile): Json =
    parse(new String(Files.readAllBytes(file.toPath), StandardCharsets.UTF_8))
      .fold(e => throw new IllegalArgumentException(s"${file.getName}: ${e.getMessage}", e), json => json)

}
