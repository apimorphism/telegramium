package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param bio
  *   The new value of the bio for the business account; 0-140 characters
  */
final case class SetBusinessAccountBioReq(businessConnectionId: String, bio: Option[String] = Option.empty)
