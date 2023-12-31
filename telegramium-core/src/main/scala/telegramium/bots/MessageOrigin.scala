package telegramium.bots

sealed trait MessageOrigin {}

/** The message was originally sent by a known user.
  *
  * @param type
  *   Type of the message origin, always “user”
  * @param date
  *   Date the message was sent originally in Unix time
  * @param senderUser
  *   User that sent the message originally
  */
final case class MessageOriginUser(`type`: String, date: Int, senderUser: User) extends MessageOrigin

/** The message was originally sent to a channel chat.
  *
  * @param type
  *   Type of the message origin, always “channel”
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
  `type`: String,
  date: Int,
  chat: Chat,
  messageId: Int,
  authorSignature: Option[String] = Option.empty
) extends MessageOrigin

/** The message was originally sent by an unknown user.
  *
  * @param type
  *   Type of the message origin, always “hidden_user”
  * @param date
  *   Date the message was sent originally in Unix time
  * @param senderUserName
  *   Name of the user that sent the message originally
  */
final case class MessageOriginHiddenUser(`type`: String, date: Int, senderUserName: String) extends MessageOrigin

/** The message was originally sent on behalf of a chat to a group chat.
  *
  * @param type
  *   Type of the message origin, always “chat”
  * @param date
  *   Date the message was sent originally in Unix time
  * @param senderChat
  *   Chat that sent the message originally
  * @param authorSignature
  *   Optional. For messages originally sent by an anonymous chat administrator, original message author signature
  */
final case class MessageOriginChat(
  `type`: String,
  date: Int,
  senderChat: Chat,
  authorSignature: Option[String] = Option.empty
) extends MessageOrigin
