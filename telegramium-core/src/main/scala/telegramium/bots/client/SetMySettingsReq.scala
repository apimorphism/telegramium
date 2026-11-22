package telegramium.bots.client

/** @param supportsInlineQueries
  *   Pass <em>True</em> to enable <a href="https://core.telegram.org/bots/api#inline-mode">inline mode</a> for the bot
  *   or <em>False</em> to disable it
  * @param inlineQueryPlaceholder
  *   New placeholder for inline queries, which is shown in the input field after the bot's username. Pass an empty
  *   string to use the default placeholder.
  * @param needsInlineQueryLocation
  *   Pass <em>True</em> to request the location of the user with every inline query to the bot or <em>False</em> to
  *   stop requesting it
  * @param inlineFeedbackShareDenominator
  *   Denominator of the share of <a href="https://core.telegram.org/bots/api#choseninlineresult">chosen inline
  *   results</a> received by the bot; the bot receives 1 out of <em>inline_feedback_share_denominator</em> chosen
  *   results. Must be one of 1, 10, 100, or 1000. Pass 0 to stop receiving chosen inline results.
  * @param canJoinGroups
  *   Pass <em>True</em> to allow adding the bot to groups or <em>False</em> to disallow it
  * @param canReadAllGroupMessages
  *   Pass <em>True</em> to disable <a href="https://core.telegram.org/bots/features#privacy-mode">privacy mode</a> for
  *   the bot or <em>False</em> to enable it
  * @param canConnectToBusiness
  *   Pass <em>True</em> to allow connecting the bot to user accounts to manage them or <em>False</em> to disallow it
  * @param canManageBots
  *   Pass <em>True</em> to allow creating other bots that will be controlled by the bot or <em>False</em> to disallow
  *   it
  * @param supportsGuestQueries
  *   Pass <em>True</em> to allow guest queries to the bot from chats it isn't a member of or <em>False</em> to disallow
  *   them
  * @param supportsJoinRequestQueries
  *   Pass <em>True</em> to allow assigning the bot to process join request queries or <em>False</em> to disallow it
  * @param hasTopicsEnabled
  *   Pass <em>True</em> to enable forum topic mode in private chats with the bot or <em>False</em> to disable it
  * @param allowsUsersToCreateTopics
  *   Pass <em>True</em> to allow users to create and delete topics in private chats with the bot or <em>False</em> to
  *   disallow it
  * @param hasTopicSidebar
  *   Pass <em>True</em> to show topics in private chats with the bot in a sidebar or <em>False</em> to stop showing
  *   them in a sidebar
  * @param canCommunicateWithBots
  *   Pass <em>True</em> to allow the bot to communicate with other bots in groups, private chats, and through business
  *   accounts or <em>False</em> to disallow it
  * @param callbackQueryMode
  *   Mode in which the bot receives <a href="https://core.telegram.org/bots/api#callbackquery">callback queries</a>
  *   from buttons in messages sent by the bot. Must be one of “default” (the field <em>message</em> is always used and
  *   contains an <a href="https://core.telegram.org/bots/api#inaccessiblemessage">InaccessibleMessage</a> if the
  *   message is inaccessible to the bot), “auto” (the field <em>inline_message_id</em> is used instead of the field
  *   <em>message</em> if the message is inaccessible to the bot; the field <em>message</em> can still contain an
  *   inaccessible message if the message has just been deleted), or “inline” (the field <em>inline_message_id</em> is
  *   always used instead of the field <em>message</em>).
  */
final case class SetMySettingsReq(
  supportsInlineQueries: Option[Boolean] = Option.empty,
  inlineQueryPlaceholder: Option[String] = Option.empty,
  needsInlineQueryLocation: Option[Boolean] = Option.empty,
  inlineFeedbackShareDenominator: Option[Int] = Option.empty,
  canJoinGroups: Option[Boolean] = Option.empty,
  canReadAllGroupMessages: Option[Boolean] = Option.empty,
  canConnectToBusiness: Option[Boolean] = Option.empty,
  canManageBots: Option[Boolean] = Option.empty,
  supportsGuestQueries: Option[Boolean] = Option.empty,
  supportsJoinRequestQueries: Option[Boolean] = Option.empty,
  hasTopicsEnabled: Option[Boolean] = Option.empty,
  allowsUsersToCreateTopics: Option[Boolean] = Option.empty,
  hasTopicSidebar: Option[Boolean] = Option.empty,
  canCommunicateWithBots: Option[Boolean] = Option.empty,
  callbackQueryMode: Option[String] = Option.empty
)
