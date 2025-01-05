package telegramium.bots.client

/** @param userId
  *   Unique identifier of the target user
  * @param customDescription
  *   Custom description for the verification; 0-70 characters. Must be empty if the organization isn't allowed to
  *   provide a custom verification description.
  */
final case class VerifyUserReq(userId: Long, customDescription: Option[String] = Option.empty)
