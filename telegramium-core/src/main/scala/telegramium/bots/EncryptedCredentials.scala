package telegramium.bots

/** Describes data required for decrypting and authenticating <a
  * href="https://core.telegram.org/bots/api#encryptedpassportelement">EncryptedPassportElement</a>. See the <a
  * href="https://core.telegram.org/passport#receiving-information">Telegram Passport Documentation</a> for a complete
  * description of the data decryption and authentication processes.
  *
  * @param data
  *   Base64-encoded encrypted JSON-serialized data with unique user's payload, data hashes and secrets required for <a
  *   href="https://core.telegram.org/bots/api#encryptedpassportelement">EncryptedPassportElement</a> decryption and
  *   authentication
  * @param hash
  *   Base64-encoded data hash for data authentication
  * @param secret
  *   Base64-encoded secret, encrypted with the bot's public RSA key, required for data decryption
  */
final case class EncryptedCredentials(data: String, hash: String, secret: String)
