package telegramium.bots

/** This object represents a phone contact.
  *
  * @param phoneNumber
  *   Contact's phone number
  * @param firstName
  *   Contact's first name
  * @param lastName
  *   Optional. Contact's last name
  * @param userId
  *   Optional. Contact's user identifier in Telegram. This number may have more than 32 significant bits and some
  *   programming languages may have difficulty/silent defects in interpreting it. But it has at most 52 significant
  *   bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
  * @param vcard
  *   Optional. Additional data about the contact in the form of a vCard
  */
final case class Contact(
  phoneNumber: String,
  firstName: String,
  lastName: Option[String] = Option.empty,
  userId: Option[Long] = Option.empty,
  vcard: Option[String] = Option.empty
)
