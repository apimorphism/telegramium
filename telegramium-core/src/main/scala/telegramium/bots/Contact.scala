package telegramium.bots

/** This object represents a phone contact.
  *
  * @param phoneNumber Contact's phone number
  * @param firstName Contact's first name
  * @param lastName Optional. Contact's last name
  * @param userId Optional. Contact's user identifier in Telegram
  * @param vcard Optional. Additional data about the contact in the form of
  * a vCard */
final case class Contact(phoneNumber: String,
                         firstName: String,
                         lastName: Option[String] = Option.empty,
                         userId: Option[Int] = Option.empty,
                         vcard: Option[String] = Option.empty)
