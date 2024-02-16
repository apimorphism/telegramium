package telegramium.bots

/** This object represents an incoming update. At most one of the optional parameters can be present in any given
  * update.
  *
  * @param updateId
  *   The update's unique identifier. Update identifiers start from a certain positive number and increase sequentially.
  *   This identifier becomes especially handy if you're using webhooks, since it allows you to ignore repeated updates
  *   or to restore the correct update sequence, should they get out of order. If there are no new updates for at least
  *   a week, then identifier of the next update will be chosen randomly instead of sequentially.
  * @param message
  *   Optional. New incoming message of any kind - text, photo, sticker, etc.
  * @param editedMessage
  *   Optional. New version of a message that is known to the bot and was edited. This update may at times be triggered
  *   by changes to message fields that are either unavailable or not actively used by your bot.
  * @param channelPost
  *   Optional. New incoming channel post of any kind - text, photo, sticker, etc.
  * @param editedChannelPost
  *   Optional. New version of a channel post that is known to the bot and was edited. This update may at times be
  *   triggered by changes to message fields that are either unavailable or not actively used by your bot.
  * @param messageReaction
  *   Optional. A reaction to a message was changed by a user. The bot must be an administrator in the chat and must
  *   explicitly specify "message_reaction" in the list of allowed_updates to receive these updates. The update isn't
  *   received for reactions set by bots.
  * @param messageReactionCount
  *   Optional. Reactions to a message with anonymous reactions were changed. The bot must be an administrator in the
  *   chat and must explicitly specify "message_reaction_count" in the list of allowed_updates to receive these updates.
  *   The updates are grouped and can be sent with delay up to a few minutes.
  * @param inlineQuery
  *   Optional. New incoming inline query
  * @param chosenInlineResult
  *   Optional. The result of an inline query that was chosen by a user and sent to their chat partner. Please see our
  *   documentation on the feedback collecting for details on how to enable these updates for your bot.
  * @param callbackQuery
  *   Optional. New incoming callback query
  * @param shippingQuery
  *   Optional. New incoming shipping query. Only for invoices with flexible price
  * @param preCheckoutQuery
  *   Optional. New incoming pre-checkout query. Contains full information about checkout
  * @param poll
  *   Optional. New poll state. Bots receive only updates about manually stopped polls and polls, which are sent by the
  *   bot
  * @param pollAnswer
  *   Optional. A user changed their answer in a non-anonymous poll. Bots receive new votes only in polls that were sent
  *   by the bot itself.
  * @param myChatMember
  *   Optional. The bot's chat member status was updated in a chat. For private chats, this update is received only when
  *   the bot is blocked or unblocked by the user.
  * @param chatMember
  *   Optional. A chat member's status was updated in a chat. The bot must be an administrator in the chat and must
  *   explicitly specify "chat_member" in the list of allowed_updates to receive these updates.
  * @param chatJoinRequest
  *   Optional. A request to join the chat has been sent. The bot must have the can_invite_users administrator right in
  *   the chat to receive these updates.
  * @param chatBoost
  *   Optional. A chat boost was added or changed. The bot must be an administrator in the chat to receive these
  *   updates.
  * @param removedChatBoost
  *   Optional. A boost was removed from a chat. The bot must be an administrator in the chat to receive these updates.
  */
final case class Update(
  updateId: Int,
  message: Option[Message] = Option.empty,
  editedMessage: Option[Message] = Option.empty,
  channelPost: Option[Message] = Option.empty,
  editedChannelPost: Option[Message] = Option.empty,
  messageReaction: Option[MessageReactionUpdated] = Option.empty,
  messageReactionCount: Option[MessageReactionCountUpdated] = Option.empty,
  inlineQuery: Option[InlineQuery] = Option.empty,
  chosenInlineResult: Option[ChosenInlineResult] = Option.empty,
  callbackQuery: Option[CallbackQuery] = Option.empty,
  shippingQuery: Option[ShippingQuery] = Option.empty,
  preCheckoutQuery: Option[PreCheckoutQuery] = Option.empty,
  poll: Option[Poll] = Option.empty,
  pollAnswer: Option[PollAnswer] = Option.empty,
  myChatMember: Option[ChatMemberUpdated] = Option.empty,
  chatMember: Option[ChatMemberUpdated] = Option.empty,
  chatJoinRequest: Option[ChatJoinRequest] = Option.empty,
  chatBoost: Option[ChatBoostUpdated] = Option.empty,
  removedChatBoost: Option[ChatBoostRemoved] = Option.empty
)
