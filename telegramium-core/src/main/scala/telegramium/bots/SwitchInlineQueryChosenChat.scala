package telegramium.bots

/** This object represents an inline button that switches the current user to inline mode in a chosen chat, with an
  * optional default inline query.
  *
  * @param query
  *   <em>Optional</em>. The default inline query to be inserted in the input field. If left empty, only the bot's
  *   username will be inserted.
  * @param allowUserChats
  *   <em>Optional</em>. <em>True</em>, if private chats with users can be chosen
  * @param allowBotChats
  *   <em>Optional</em>. <em>True</em>, if private chats with bots can be chosen
  * @param allowGroupChats
  *   <em>Optional</em>. <em>True</em>, if group and supergroup chats can be chosen
  * @param allowChannelChats
  *   <em>Optional</em>. <em>True</em>, if channel chats can be chosen
  */
final case class SwitchInlineQueryChosenChat(
  query: Option[String] = Option.empty,
  allowUserChats: Option[Boolean] = Option.empty,
  allowBotChats: Option[Boolean] = Option.empty,
  allowGroupChats: Option[Boolean] = Option.empty,
  allowChannelChats: Option[Boolean] = Option.empty
)
