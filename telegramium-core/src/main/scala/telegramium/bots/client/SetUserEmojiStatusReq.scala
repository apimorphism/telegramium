package telegramium.bots.client

/** @param userId
  *   Unique identifier of the target user
  * @param emojiStatusCustomEmojiId
  *   Custom emoji identifier of the emoji status to set. Pass an empty string to remove the status.
  * @param emojiStatusExpirationDate
  *   Expiration date of the emoji status, if any
  */
final case class SetUserEmojiStatusReq(
  userId: Long,
  emojiStatusCustomEmojiId: Option[String] = Option.empty,
  emojiStatusExpirationDate: Option[Int] = Option.empty
)
