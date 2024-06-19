package telegramium.bots

/** This object represents a boost removed from a chat.
  *
  * @param chat
  *   Chat which was boosted
  * @param boostId
  *   Unique identifier of the boost
  * @param removeDate
  *   Point in time (Unix timestamp) when the boost was removed
  * @param source
  *   Source of the removed boost
  */
final case class ChatBoostRemoved(
  chat: Chat,
  boostId: String,
  removeDate: Int,
  source: iozhik.OpenEnum[ChatBoostSource]
)
