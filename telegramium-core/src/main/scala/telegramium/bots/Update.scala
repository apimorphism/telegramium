package telegramium.bots

/** This <a href="https://core.telegram.org/bots/api#available-types">object</a> represents an incoming update.<br>
  *
  * At most <strong>one</strong> of the optional fields can be present in any given update.
  *
  * @param updateId
  *   The update's unique identifier. Update identifiers start from a certain positive number and increase sequentially.
  *   This identifier becomes especially handy if you're using <a
  *   href="https://core.telegram.org/bots/api#setwebhook">webhooks</a>, since it allows you to ignore repeated updates
  *   or to restore the correct update sequence, should they get out of order. If there are no new updates for at least
  *   a week, then identifier of the next update will be chosen randomly instead of sequentially.
  * @param message
  *   <em>Optional</em>. New incoming message of any kind - text, photo, sticker, etc.
  * @param editedMessage
  *   <em>Optional</em>. New version of a message that is known to the bot and was edited. This update may at times be
  *   triggered by changes to message fields that are either unavailable or not actively used by your bot.
  * @param channelPost
  *   <em>Optional</em>. New incoming channel post of any kind - text, photo, sticker, etc.
  * @param editedChannelPost
  *   <em>Optional</em>. New version of a channel post that is known to the bot and was edited. This update may at times
  *   be triggered by changes to message fields that are either unavailable or not actively used by your bot.
  * @param businessConnection
  *   <em>Optional</em>. The bot was connected to or disconnected from a business account, or a user edited an existing
  *   connection with the bot
  * @param businessMessage
  *   <em>Optional</em>. New message from a connected business account
  * @param editedBusinessMessage
  *   <em>Optional</em>. New version of a message from a connected business account
  * @param deletedBusinessMessages
  *   <em>Optional</em>. Messages were deleted from a connected business account
  * @param guestMessage
  *   <em>Optional</em>. New guest message. The bot can use the field <em>Message.guest_query_id</em> and the method <a
  *   href="https://core.telegram.org/bots/api#answerguestquery">answerGuestQuery</a> to send a message in response.
  * @param messageReaction
  *   <em>Optional</em>. A reaction to a message was changed by a user. The bot must be an administrator in the chat and
  *   must explicitly specify <code>"message_reaction"</code> in the list of <em>allowed_updates</em> to receive these
  *   updates. The update isn't received for reactions set by bots.
  * @param messageReactionCount
  *   <em>Optional</em>. Reactions to a message with anonymous reactions were changed. The bot must be an administrator
  *   in the chat and must explicitly specify <code>"message_reaction_count"</code> in the list of
  *   <em>allowed_updates</em> to receive these updates. The updates are grouped and can be sent with delay up to a few
  *   minutes.
  * @param inlineQuery
  *   <em>Optional</em>. New incoming <a href="https://core.telegram.org/bots/api#inline-mode">inline</a> query
  * @param chosenInlineResult
  *   <em>Optional</em>. The result of an <a href="https://core.telegram.org/bots/api#inline-mode">inline</a> query that
  *   was chosen by a user and sent to their chat partner. Please see our documentation on the <a
  *   href="https://core.telegram.org/bots/inline#collecting-feedback">feedback collecting</a> for details on how to
  *   enable these updates for your bot.
  * @param callbackQuery
  *   <em>Optional</em>. New incoming callback query
  * @param shippingQuery
  *   <em>Optional</em>. New incoming shipping query. Only for invoices with flexible price.
  * @param preCheckoutQuery
  *   <em>Optional</em>. New incoming pre-checkout query. Contains full information about checkout.
  * @param purchasedPaidMedia
  *   <em>Optional</em>. A user purchased paid media with a non-empty payload sent by the bot in a non-channel chat
  * @param poll
  *   <em>Optional</em>. New poll state. Bots receive only updates about manually stopped polls and polls, which are
  *   sent by the bot.
  * @param pollAnswer
  *   <em>Optional</em>. A user changed their answer in a non-anonymous poll. Bots receive new votes only in polls that
  *   were sent by the bot itself.
  * @param myChatMember
  *   <em>Optional</em>. The bot's chat member status was updated in a chat. For private chats, this update is received
  *   only when the bot is blocked or unblocked by the user.
  * @param chatMember
  *   <em>Optional</em>. A chat member's status was updated in a chat. The bot must be an administrator in the chat and
  *   must explicitly specify <code>"chat_member"</code> in the list of <em>allowed_updates</em> to receive these
  *   updates.
  * @param chatJoinRequest
  *   <em>Optional</em>. A request to join the chat has been sent. The bot must have the <em>can_invite_users</em>
  *   administrator right in the chat to receive these updates.
  * @param chatBoost
  *   <em>Optional</em>. A chat boost was added or changed. The bot must be an administrator in the chat to receive
  *   these updates.
  * @param removedChatBoost
  *   <em>Optional</em>. A boost was removed from a chat. The bot must be an administrator in the chat to receive these
  *   updates.
  * @param managedBot
  *   <em>Optional</em>. A new bot was created to be managed by the bot, or token or owner of a managed bot was changed
  * @param subscription
  *   <em>Optional</em>. User payment subscription has changed
  * @param stoppedMessageGeneration
  *   <em>Optional</em>. A user asked the bot to stop the generation of a message
  */
final case class Update(
  updateId: Int,
  message: Option[Message] = Option.empty,
  editedMessage: Option[Message] = Option.empty,
  channelPost: Option[Message] = Option.empty,
  editedChannelPost: Option[Message] = Option.empty,
  businessConnection: Option[BusinessConnection] = Option.empty,
  businessMessage: Option[Message] = Option.empty,
  editedBusinessMessage: Option[Message] = Option.empty,
  deletedBusinessMessages: Option[BusinessMessagesDeleted] = Option.empty,
  guestMessage: Option[Message] = Option.empty,
  messageReaction: Option[MessageReactionUpdated] = Option.empty,
  messageReactionCount: Option[MessageReactionCountUpdated] = Option.empty,
  inlineQuery: Option[InlineQuery] = Option.empty,
  chosenInlineResult: Option[ChosenInlineResult] = Option.empty,
  callbackQuery: Option[CallbackQuery] = Option.empty,
  shippingQuery: Option[ShippingQuery] = Option.empty,
  preCheckoutQuery: Option[PreCheckoutQuery] = Option.empty,
  purchasedPaidMedia: Option[PaidMediaPurchased] = Option.empty,
  poll: Option[Poll] = Option.empty,
  pollAnswer: Option[PollAnswer] = Option.empty,
  myChatMember: Option[ChatMemberUpdated] = Option.empty,
  chatMember: Option[ChatMemberUpdated] = Option.empty,
  chatJoinRequest: Option[ChatJoinRequest] = Option.empty,
  chatBoost: Option[ChatBoostUpdated] = Option.empty,
  removedChatBoost: Option[ChatBoostRemoved] = Option.empty,
  managedBot: Option[ManagedBotUpdated] = Option.empty,
  subscription: Option[BotSubscriptionUpdated] = Option.empty,
  stoppedMessageGeneration: Option[MessageGenerationStopped] = Option.empty
)
