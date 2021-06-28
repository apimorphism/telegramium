package telegramium.bots

/** Contains data required for decrypting and authenticating EncryptedPassportElement. See the Telegram Passport
  * Documentation for a complete description of the data decryption and authentication processes.
  *
  * @param data
  *   Base64-encoded encrypted JSON-serialized data with unique user's payload, data hashes and secrets required for
  *   EncryptedPassportElement decryption and authentication
  * @param hash
  *   Base64-encoded data hash for data authentication
  * @param secret
  *   Base64-encoded secret, encrypted with the bot's public RSA key, required for data decryption
  */
final case class EncryptedCredentials(data: String, hash: String, secret: String)
