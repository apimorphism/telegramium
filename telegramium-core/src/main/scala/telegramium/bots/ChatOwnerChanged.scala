package telegramium.bots

/** Describes a service message about an ownership change in the chat.
  *
  * @param newOwner
  *   The new owner of the chat
  */
final case class ChatOwnerChanged(newOwner: User)
