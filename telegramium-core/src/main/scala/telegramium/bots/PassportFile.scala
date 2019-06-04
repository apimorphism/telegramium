package telegramium.bots

/** This object represents a file uploaded to Telegram Passport. Currently all
  * Telegram Passport files are in JPEG format when decrypted and don't exceed 10MB.*/
final case class PassportFile(
                              /** Unique identifier for this file*/
                              fileId: String,
                              /** File size*/
                              fileSize: Int,
                              /** Unix time when the file was uploaded*/
                              fileDate: Int)
