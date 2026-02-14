package telegramium.bots

/** Describes a service message about the chat owner leaving the chat.
  *
  * @param newOwner
  *   Optional. The user which will be the new owner of the chat if the previous owner does not return to the chat
  */
final case class ChatOwnerLeft(newOwner: Option[User] = Option.empty)
