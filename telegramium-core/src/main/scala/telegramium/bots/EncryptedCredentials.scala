package telegramium.bots

/** Contains data required for decrypting and authenticating
  * EncryptedPassportElement. See the Telegram Passport Documentation for a complete
  * description of the data decryption and authentication processes.*/
final case class EncryptedCredentials(
                                      /** Base64-encoded encrypted JSON-serialized data with unique
                                        * user's payload, data hashes and secrets required for
                                        * EncryptedPassportElement decryption and authentication*/
                                      data: String,
                                      /** Base64-encoded data hash for data authentication*/
                                      hash: String,
                                      /** Base64-encoded secret, encrypted with the bot's public RSA
                                        * key, required for data decryption*/
                                      secret: String)
