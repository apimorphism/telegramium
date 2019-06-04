package telegramium.bots

/** This object represents a phone contact.*/
final case class Contact(
                         /** Contact's phone number*/
                         phoneNumber: String,
                         /** Contact's first name*/
                         firstName: String,
                         /** Optional. Contact's last name*/
                         lastName: Option[String] = Option.empty,
                         /** Optional. Contact's user identifier in Telegram*/
                         userId: Option[Int] = Option.empty,
                         /** Optional. Additional data about the contact in the form of
                           * a vCard*/
                         vcard: Option[String] = Option.empty)
