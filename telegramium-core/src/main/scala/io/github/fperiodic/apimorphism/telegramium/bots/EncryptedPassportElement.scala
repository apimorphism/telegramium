package io.github.fperiodic.apimorphism.telegramium.bots

/** Contains information about documents or other Telegram Passport elements shared
  * with the bot by the user.*/
final case class EncryptedPassportElement(
                                          /** Element type. One of “personal_details”, “passport”,
                                            * “driver_license”, “identity_card”, “internal_passport”,
                                            * “address”, “utility_bill”, “bank_statement”,
                                            * “rental_agreement”, “passport_registration”,
                                            * “temporary_registration”, “phone_number”, “email”.*/
                                          `type`: String,
                                          /** Optional. Base64-encoded encrypted Telegram Passport
                                            * element data provided by the user, available for
                                            * “personal_details”, “passport”, “driver_license”,
                                            * “identity_card”, “internal_passport” and “address” types.
                                            * Can be decrypted and verified using the accompanying
                                            * EncryptedCredentials.*/
                                          data: Option[String] = Option.empty,
                                          /** Optional. User's verified phone number, available only for
                                            * “phone_number” type*/
                                          phoneNumber: Option[String] = Option.empty,
                                          /** Optional. User's verified email address, available only for
                                            * “email” type*/
                                          email: Option[String] = Option.empty,
                                          /** Optional. Array of encrypted files with documents provided
                                            * by the user, available for “utility_bill”, “bank_statement”,
                                            * “rental_agreement”, “passport_registration” and
                                            * “temporary_registration” types. Files can be decrypted and
                                            * verified using the accompanying EncryptedCredentials.*/
                                          files: List[PassportFile] = List.empty,
                                          /** Optional. Encrypted file with the front side of the
                                            * document, provided by the user. Available for “passport”,
                                            * “driver_license”, “identity_card” and “internal_passport”.
                                            * The file can be decrypted and verified using the
                                            * accompanying EncryptedCredentials.*/
                                          frontSide: Option[PassportFile] = Option.empty,
                                          /** Optional. Encrypted file with the reverse side of the
                                            * document, provided by the user. Available for
                                            * “driver_license” and “identity_card”. The file can be
                                            * decrypted and verified using the accompanying
                                            * EncryptedCredentials.*/
                                          reverseSide: Option[PassportFile] = Option.empty,
                                          /** Optional. Encrypted file with the selfie of the user
                                            * holding a document, provided by the user; available for
                                            * “passport”, “driver_license”, “identity_card” and
                                            * “internal_passport”. The file can be decrypted and verified
                                            * using the accompanying EncryptedCredentials.*/
                                          selfie: Option[PassportFile] = Option.empty,
                                          /** Optional. Array of encrypted files with translated versions
                                            * of documents provided by the user. Available if requested
                                            * for “passport”, “driver_license”, “identity_card”,
                                            * “internal_passport”, “utility_bill”, “bank_statement”,
                                            * “rental_agreement”, “passport_registration” and
                                            * “temporary_registration” types. Files can be decrypted and
                                            * verified using the accompanying EncryptedCredentials.*/
                                          translation: List[PassportFile] = List.empty,
                                          /** Base64-encoded element hash for using in
                                            * PassportElementErrorUnspecified*/
                                          hash: String)
