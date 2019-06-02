package io.github.fperiodic.apimorphism.telegramium.bots

sealed trait PassportElementError {}

/** Represents an issue with a list of scans. The error is considered resolved when
  * the list of files containing the scans changes.*/
final case class PassportElementErrorFiles(
                                           /** The section of the user's Telegram Passport which has the
                                             * issue, one of “utility_bill”, “bank_statement”,
                                             * “rental_agreement”, “passport_registration”,
                                             * “temporary_registration”*/
                                           `type`: String,
                                           /** List of base64-encoded file hashes*/
                                           fileHashes: List[String] = List.empty,
                                           /** Error message*/
                                           message: String)
    extends PassportElementError

/** Represents an issue in one of the data fields that was provided by the user.
  * The error is considered resolved when the field's value changes.*/
final case class PassportElementErrorDataField(
                                               /** The section of the user's Telegram Passport which has the
                                                 * error, one of “personal_details”, “passport”,
                                                 * “driver_license”, “identity_card”, “internal_passport”,
                                                 * “address”*/
                                               `type`: String,
                                               /** Name of the data field which has the error*/
                                               fieldName: String,
                                               /** Base64-encoded data hash*/
                                               dataHash: String,
                                               /** Error message*/
                                               message: String)
    extends PassportElementError

/** Represents an issue with the reverse side of a document. The error is
  * considered resolved when the file with reverse side of the document changes.*/
final case class PassportElementErrorReverseSide(
                                                 /** The section of the user's Telegram Passport which has the
                                                   * issue, one of “driver_license”, “identity_card”*/
                                                 `type`: String,
                                                 /** Base64-encoded hash of the file with the reverse side of
                                                   * the document*/
                                                 fileHash: String,
                                                 /** Error message*/
                                                 message: String)
    extends PassportElementError

/** Represents an issue with the selfie with a document. The error is considered
  * resolved when the file with the selfie changes.*/
final case class PassportElementErrorSelfie(
                                            /** The section of the user's Telegram Passport which has the
                                              * issue, one of “passport”, “driver_license”, “identity_card”,
                                              * “internal_passport”*/
                                            `type`: String,
                                            /** Base64-encoded hash of the file with the selfie*/
                                            fileHash: String,
                                            /** Error message*/
                                            message: String)
    extends PassportElementError

/** Represents an issue with the front side of a document. The error is considered
  * resolved when the file with the front side of the document changes.*/
final case class PassportElementErrorFrontSide(
                                               /** The section of the user's Telegram Passport which has the
                                                 * issue, one of “passport”, “driver_license”, “identity_card”,
                                                 * “internal_passport”*/
                                               `type`: String,
                                               /** Base64-encoded hash of the file with the front side of the
                                                 * document*/
                                               fileHash: String,
                                               /** Error message*/
                                               message: String)
    extends PassportElementError

/** Represents an issue with a document scan. The error is considered resolved when
  * the file with the document scan changes.*/
final case class PassportElementErrorFile(
                                          /** The section of the user's Telegram Passport which has the
                                            * issue, one of “utility_bill”, “bank_statement”,
                                            * “rental_agreement”, “passport_registration”,
                                            * “temporary_registration”*/
                                          `type`: String,
                                          /** Base64-encoded file hash*/
                                          fileHash: String,
                                          /** Error message*/
                                          message: String)
    extends PassportElementError

/** Represents an issue in an unspecified place. The error is considered resolved
  * when new data is added.*/
final case class PassportElementErrorUnspecified(
                                                 /** Type of element of the user's Telegram Passport which has
                                                   * the issue*/
                                                 `type`: String,
                                                 /** Base64-encoded element hash*/
                                                 elementHash: String,
                                                 /** Error message*/
                                                 message: String)
    extends PassportElementError

/** Represents an issue with one of the files that constitute the translation of a
  * document. The error is considered resolved when the file changes.*/
final case class PassportElementErrorTranslationFile(
                                                     /** Type of element of the user's Telegram Passport which has
                                                       * the issue, one of “passport”, “driver_license”,
                                                       * “identity_card”, “internal_passport”, “utility_bill”,
                                                       * “bank_statement”, “rental_agreement”,
                                                       * “passport_registration”, “temporary_registration”*/
                                                     `type`: String,
                                                     /** Base64-encoded file hash*/
                                                     fileHash: String,
                                                     /** Error message*/
                                                     message: String)
    extends PassportElementError

/** Represents an issue with the translated version of a document. The error is
  * considered resolved when a file with the document translation change.*/
final case class PassportElementErrorTranslationFiles(
                                                      /** Type of element of the user's Telegram Passport which has
                                                        * the issue, one of “passport”, “driver_license”,
                                                        * “identity_card”, “internal_passport”, “utility_bill”,
                                                        * “bank_statement”, “rental_agreement”,
                                                        * “passport_registration”, “temporary_registration”*/
                                                      `type`: String,
                                                      /** List of base64-encoded file hashes*/
                                                      fileHashes: List[String] = List.empty,
                                                      /** Error message*/
                                                      message: String)
    extends PassportElementError
