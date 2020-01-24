package telegramium.bots

/** This object represents a file uploaded to Telegram Passport. Currently all
  * Telegram Passport files are in JPEG format when decrypted and don't exceed 10MB.*/
final case class PassportFile(
                              /** Identifier for this file, which can be used to download or
                                * reuse the file*/
                              fileId: String,
                              /** Unique identifier for this file, which is supposed to be
                                * the same over time and for different bots. Can't be used to
                                * download or reuse the file.*/
                              fileUniqueId: String,
                              /** File size*/
                              fileSize: Int,
                              /** Unix time when the file was uploaded*/
                              fileDate: Int)
