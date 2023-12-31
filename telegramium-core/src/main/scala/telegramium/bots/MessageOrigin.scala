package telegramium.bots

sealed trait MessageOrigin {}

/** The message was originally sent by a known user.
  *
  * @param date
  *   Date the message was sent originally in Unix time
  * @param senderUser
  *   User that sent the message originally
  */
final case class MessageOriginUser(date: Int, senderUser: User) extends MessageOrigin

/** The message was originally sent to a channel chat.
  *
  * @param date
  *   Date the message was sent originally in Unix time
  * @param chat
  *   Channel chat to which the message was originally sent
  * @param messageId
  *   Unique message identifier inside the chat
  * @param authorSignature
  *   Optional. Signature of the original post author
  */
final case class MessageOriginChannel(
  date: Int,
  chat: Chat,
  messageId: Int,
  authorSignature: Option[String] = Option.empty
) extends MessageOrigin

/** The message was originally sent by an unknown user.
  *
  * @param date
  *   Date the message was sent originally in Unix time
  * @param senderUserName
  *   Name of the user that sent the message originally
  */
final case class MessageOriginHiddenUser(date: Int, senderUserName: String) extends MessageOrigin

/** The message was originally sent on behalf of a chat to a group chat.
  *
  * @param date
  *   Date the message was sent originally in Unix time
  * @param senderChat
  *   Chat that sent the message originally
  * @param authorSignature
  *   Optional. For messages originally sent by an anonymous chat administrator, original message author signature
  */
final case class MessageOriginChat(date: Int, senderChat: Chat, authorSignature: Option[String] = Option.empty)
    extends MessageOrigin
