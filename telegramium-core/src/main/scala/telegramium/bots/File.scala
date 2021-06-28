package telegramium.bots

/** This object represents a file ready to be downloaded. The file can be downloaded via the link
  * https://api.telegram.org/file/bot<token>/<file_path>. It is guaranteed that the link will be valid for at least 1
  * hour. When the link expires, a new one can be requested by calling getFile.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param fileSize
  *   Optional. File size, if known
  * @param filePath
  *   Optional. File path. Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.
  */
final case class File(
  fileId: String,
  fileUniqueId: String,
  fileSize: Option[Int] = Option.empty,
  filePath: Option[String] = Option.empty
)
