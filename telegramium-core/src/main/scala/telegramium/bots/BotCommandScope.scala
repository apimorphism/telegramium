package telegramium.bots

sealed trait BotCommandScope {}

/** Represents the scope of bot commands, covering all group and supergroup chat administrators.
  */
case object BotCommandScopeAllChatAdministrators extends BotCommandScope

/** Represents the scope of bot commands, covering all group and supergroup chats. */
case object BotCommandScopeAllGroupChats extends BotCommandScope

/** Represents the default scope of bot commands. Default commands are used if no commands with a narrower scope are
  * specified for the user.
  */
case object BotCommandScopeDefault extends BotCommandScope

/** Represents the scope of bot commands, covering all administrators of a specific group or supergroup chat.
  *
  * @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  */
final case class BotCommandScopeChatAdministrators(chatId: ChatId) extends BotCommandScope

/** Represents the scope of bot commands, covering a specific member of a group or supergroup chat.
  *
  * @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param userId
  *   Unique identifier of the target user
  */
final case class BotCommandScopeChatMember(chatId: ChatId, userId: Long) extends BotCommandScope

/** Represents the scope of bot commands, covering a specific chat.
  *
  * @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  */
final case class BotCommandScopeChat(chatId: ChatId) extends BotCommandScope

/** Represents the scope of bot commands, covering all private chats. */
case object BotCommandScopeAllPrivateChats extends BotCommandScope
