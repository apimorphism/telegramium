package telegramium.bots

/** Contains information about documents or other Telegram Passport elements shared with the bot by the user.
  *
  * @param type
  *   Element type. One of “personal_details”, “passport”, “driver_license”, “identity_card”, “internal_passport”,
  *   “address”, “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration”,
  *   “temporary_registration”, “phone_number”, “email”.
  * @param data
  *   Optional. Base64-encoded encrypted Telegram Passport element data provided by the user, available for
  *   “personal_details”, “passport”, “driver_license”, “identity_card”, “internal_passport” and “address” types. Can be
  *   decrypted and verified using the accompanying EncryptedCredentials.
  * @param phoneNumber
  *   Optional. User's verified phone number, available only for “phone_number” type
  * @param email
  *   Optional. User's verified email address, available only for “email” type
  * @param files
  *   Optional. Array of encrypted files with documents provided by the user, available for “utility_bill”,
  *   “bank_statement”, “rental_agreement”, “passport_registration” and “temporary_registration” types. Files can be
  *   decrypted and verified using the accompanying EncryptedCredentials.
  * @param frontSide
  *   Optional. Encrypted file with the front side of the document, provided by the user. Available for “passport”,
  *   “driver_license”, “identity_card” and “internal_passport”. The file can be decrypted and verified using the
  *   accompanying EncryptedCredentials.
  * @param reverseSide
  *   Optional. Encrypted file with the reverse side of the document, provided by the user. Available for
  *   “driver_license” and “identity_card”. The file can be decrypted and verified using the accompanying
  *   EncryptedCredentials.
  * @param selfie
  *   Optional. Encrypted file with the selfie of the user holding a document, provided by the user; available for
  *   “passport”, “driver_license”, “identity_card” and “internal_passport”. The file can be decrypted and verified
  *   using the accompanying EncryptedCredentials.
  * @param translation
  *   Optional. Array of encrypted files with translated versions of documents provided by the user. Available if
  *   requested for “passport”, “driver_license”, “identity_card”, “internal_passport”, “utility_bill”,
  *   “bank_statement”, “rental_agreement”, “passport_registration” and “temporary_registration” types. Files can be
  *   decrypted and verified using the accompanying EncryptedCredentials.
  * @param hash
  *   Base64-encoded element hash for using in PassportElementErrorUnspecified
  */
final case class EncryptedPassportElement(
  `type`: String,
  data: Option[String] = Option.empty,
  phoneNumber: Option[String] = Option.empty,
  email: Option[String] = Option.empty,
  files: List[PassportFile] = List.empty,
  frontSide: Option[PassportFile] = Option.empty,
  reverseSide: Option[PassportFile] = Option.empty,
  selfie: Option[PassportFile] = Option.empty,
  translation: List[PassportFile] = List.empty,
  hash: String
)
