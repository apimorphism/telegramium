package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param username
  *   The new value of the username for the business account; 0-32 characters
  */
final case class SetBusinessAccountUsernameReq(businessConnectionId: String, username: Option[String] = Option.empty)
