package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param firstName
  *   The new value of the first name for the business account; 1-64 characters
  * @param lastName
  *   The new value of the last name for the business account; 0-64 characters
  */
final case class SetBusinessAccountNameReq(
  businessConnectionId: String,
  firstName: String,
  lastName: Option[String] = Option.empty
)
