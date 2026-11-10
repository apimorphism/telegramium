package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Required if <em>inline_message_id</em> is not specified. Unique identifier for the target chat or username of the
  *   target supergroup in the format <code>&#064;username</code>.
  * @param messageId
  *   Required if <em>inline_message_id</em> is not specified. Identifier of the message with the widget.
  * @param inlineMessageId
  *   Required if <em>chat_id</em> and <em>message_id</em> are not specified. Identifier of the inline message.
  * @param publicData
  *   New public data of the widget, 0-1024 bytes; pass an empty string to remove the public data. If omitted, then the
  *   public data isn't changed.
  * @param chatData
  *   New data of the widget, which is specific to the message, 0-1024 bytes; pass an empty string to remove the data.
  *   If omitted, then the data isn't changed.
  */
final case class EditMessageWidgetDataReq(
  chatId: Option[ChatId] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  publicData: Option[String] = Option.empty,
  chatData: Option[String] = Option.empty
)
