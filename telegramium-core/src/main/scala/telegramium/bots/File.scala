package telegramium.bots

/** This object represents a file ready to be downloaded. The file can be downloaded via the link
  * <code>https://api.telegram.org/file/botᐸtokenᐳ/ᐸfile_pathᐳ</code>. It is guaranteed that the link will be valid for
  * at least 1 hour. When the link expires, a new one can be requested by calling <a
  * href="https://core.telegram.org/bots/api#getfile">getFile</a>.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param fileSize
  *   <em>Optional</em>. File size in bytes. It can be bigger than 2&#94;31 and some programming languages may have
  *   difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit integer
  *   or double-precision float type are safe for storing this value.
  * @param filePath
  *   <em>Optional</em>. File path. Use <code>https://api.telegram.org/file/botᐸtokenᐳ/ᐸfile_pathᐳ</code> to get the
  *   file.
  */
final case class File(
  fileId: String,
  fileUniqueId: String,
  fileSize: Option[Long] = Option.empty,
  filePath: Option[String] = Option.empty
)
