package telegramium.bots

/** This object represents a service message about a voice chat scheduled in the
  * chat.
  *
  * @param startDate Point in time (Unix timestamp) when the voice chat is
  * supposed to be started by a chat administrator */
final case class VoiceChatScheduled(startDate: Int)
