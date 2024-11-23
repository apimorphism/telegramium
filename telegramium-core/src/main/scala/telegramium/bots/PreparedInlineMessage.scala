package telegramium.bots

/** Describes an inline message to be sent by a user of a Mini App.
  *
  * @param id
  *   Unique identifier of the prepared message
  * @param expirationDate
  *   Expiration date of the prepared message, in Unix time. Expired prepared messages can no longer be used
  */
final case class PreparedInlineMessage(id: String, expirationDate: Int)
