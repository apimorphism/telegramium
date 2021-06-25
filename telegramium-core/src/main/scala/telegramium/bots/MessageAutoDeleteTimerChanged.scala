package telegramium.bots

/**
 * This object represents a service message about a change in auto-delete timer
 * settings.
 *
 * @param messageAutoDeleteTime New auto-delete time for messages in the chat
 */
final case class MessageAutoDeleteTimerChanged(messageAutoDeleteTime: Int)
