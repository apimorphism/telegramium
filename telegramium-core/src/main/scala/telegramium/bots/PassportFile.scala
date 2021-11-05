package telegramium.bots

/** This object represents a file uploaded to Telegram Passport. Currently all Telegram Passport files are in JPEG
  * format when decrypted and don't exceed 10MB.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param fileSize
  *   File size in bytes
  * @param fileDate
  *   Unix time when the file was uploaded
  */
final case class PassportFile(fileId: String, fileUniqueId: String, fileSize: Int, fileDate: Int)
