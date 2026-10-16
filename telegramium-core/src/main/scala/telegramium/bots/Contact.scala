package telegramium.bots

/** This object represents a phone contact.
  *
  * @param phoneNumber
  *   Contact's phone number
  * @param firstName
  *   Contact's first name
  * @param lastName
  *   <em>Optional</em>. Contact's last name
  * @param userId
  *   <em>Optional</em>. Contact's user identifier in Telegram. This number may have more than 32 significant bits and
  *   some programming languages may have difficulty/silent defects in interpreting it. But it has at most 52
  *   significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
  * @param vcard
  *   <em>Optional</em>. Additional data about the contact in the form of a <a
  *   href="https://en.wikipedia.org/wiki/VCard">vCard</a>
  */
final case class Contact(
  phoneNumber: String,
  firstName: String,
  lastName: Option[String] = Option.empty,
  userId: Option[Long] = Option.empty,
  vcard: Option[String] = Option.empty
)
