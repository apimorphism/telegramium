package io.github.fperiodic.apimorphism.telegramium.bots

/** Contains information about Telegram Passport data shared with the bot by the
  * user.*/
final case class PassportData(
                              /** Array with information about documents and other Telegram
                                * Passport elements that was shared with the bot*/
                              data: List[EncryptedPassportElement] = List.empty,
                              /** Encrypted credentials required to decrypt the data*/
                              credentials: EncryptedCredentials)
