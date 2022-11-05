package telegramium.bots

/** Describes Telegram Passport data shared with the bot by the user.
  *
  * @param credentials
  *   Encrypted credentials required to decrypt the data
  * @param data
  *   Array with information about documents and other Telegram Passport elements that was shared with the bot
  */
final case class PassportData(credentials: EncryptedCredentials, data: List[EncryptedPassportElement] = List.empty)
