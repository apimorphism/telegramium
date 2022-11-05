package telegramium.bots

sealed trait PassportElementError {}

/** Represents an issue with a list of scans. The error is considered resolved when the list of files containing the
  * scans changes.
  *
  * @param type
  *   The section of the user's Telegram Passport which has the issue, one of “utility_bill”, “bank_statement”,
  *   “rental_agreement”, “passport_registration”, “temporary_registration”
  * @param message
  *   Error message
  * @param fileHashes
  *   List of base64-encoded file hashes
  */
final case class PassportElementErrorFiles(`type`: String, message: String, fileHashes: List[String] = List.empty)
    extends PassportElementError

/** Represents an issue in one of the data fields that was provided by the user. The error is considered resolved when
  * the field's value changes.
  *
  * @param type
  *   The section of the user's Telegram Passport which has the error, one of “personal_details”, “passport”,
  *   “driver_license”, “identity_card”, “internal_passport”, “address”
  * @param fieldName
  *   Name of the data field which has the error
  * @param dataHash
  *   Base64-encoded data hash
  * @param message
  *   Error message
  */
final case class PassportElementErrorDataField(`type`: String, fieldName: String, dataHash: String, message: String)
    extends PassportElementError

/** Represents an issue with the reverse side of a document. The error is considered resolved when the file with reverse
  * side of the document changes.
  *
  * @param type
  *   The section of the user's Telegram Passport which has the issue, one of “driver_license”, “identity_card”
  * @param fileHash
  *   Base64-encoded hash of the file with the reverse side of the document
  * @param message
  *   Error message
  */
final case class PassportElementErrorReverseSide(`type`: String, fileHash: String, message: String)
    extends PassportElementError

/** Represents an issue with the selfie with a document. The error is considered resolved when the file with the selfie
  * changes.
  *
  * @param type
  *   The section of the user's Telegram Passport which has the issue, one of “passport”, “driver_license”,
  *   “identity_card”, “internal_passport”
  * @param fileHash
  *   Base64-encoded hash of the file with the selfie
  * @param message
  *   Error message
  */
final case class PassportElementErrorSelfie(`type`: String, fileHash: String, message: String)
    extends PassportElementError

/** Represents an issue with the front side of a document. The error is considered resolved when the file with the front
  * side of the document changes.
  *
  * @param type
  *   The section of the user's Telegram Passport which has the issue, one of “passport”, “driver_license”,
  *   “identity_card”, “internal_passport”
  * @param fileHash
  *   Base64-encoded hash of the file with the front side of the document
  * @param message
  *   Error message
  */
final case class PassportElementErrorFrontSide(`type`: String, fileHash: String, message: String)
    extends PassportElementError

/** Represents an issue with a document scan. The error is considered resolved when the file with the document scan
  * changes.
  *
  * @param type
  *   The section of the user's Telegram Passport which has the issue, one of “utility_bill”, “bank_statement”,
  *   “rental_agreement”, “passport_registration”, “temporary_registration”
  * @param fileHash
  *   Base64-encoded file hash
  * @param message
  *   Error message
  */
final case class PassportElementErrorFile(`type`: String, fileHash: String, message: String)
    extends PassportElementError

/** Represents an issue in an unspecified place. The error is considered resolved when new data is added.
  *
  * @param type
  *   Type of element of the user's Telegram Passport which has the issue
  * @param elementHash
  *   Base64-encoded element hash
  * @param message
  *   Error message
  */
final case class PassportElementErrorUnspecified(`type`: String, elementHash: String, message: String)
    extends PassportElementError

/** Represents an issue with one of the files that constitute the translation of a document. The error is considered
  * resolved when the file changes.
  *
  * @param type
  *   Type of element of the user's Telegram Passport which has the issue, one of “passport”, “driver_license”,
  *   “identity_card”, “internal_passport”, “utility_bill”, “bank_statement”, “rental_agreement”,
  *   “passport_registration”, “temporary_registration”
  * @param fileHash
  *   Base64-encoded file hash
  * @param message
  *   Error message
  */
final case class PassportElementErrorTranslationFile(`type`: String, fileHash: String, message: String)
    extends PassportElementError

/** Represents an issue with the translated version of a document. The error is considered resolved when a file with the
  * document translation change.
  *
  * @param type
  *   Type of element of the user's Telegram Passport which has the issue, one of “passport”, “driver_license”,
  *   “identity_card”, “internal_passport”, “utility_bill”, “bank_statement”, “rental_agreement”,
  *   “passport_registration”, “temporary_registration”
  * @param message
  *   Error message
  * @param fileHashes
  *   List of base64-encoded file hashes
  */
final case class PassportElementErrorTranslationFiles(
  `type`: String,
  message: String,
  fileHashes: List[String] = List.empty
) extends PassportElementError
