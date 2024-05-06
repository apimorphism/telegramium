package telegramium.bots

/** This object defines the criteria used to request a suitable chat. Information about the selected chat will be shared
  * with the bot when the corresponding button is pressed. The bot will be granted requested rights in the chat if
  * appropriate.
  *
  * @param requestId
  *   Signed 32-bit identifier of the request, which will be received back in the ChatShared object. Must be unique
  *   within the message
  * @param chatIsChannel
  *   Pass True to request a channel chat, pass False to request a group or a supergroup chat.
  * @param chatIsForum
  *   Optional. Pass True to request a forum supergroup, pass False to request a non-forum chat. If not specified, no
  *   additional restrictions are applied.
  * @param chatHasUsername
  *   Optional. Pass True to request a supergroup or a channel with a username, pass False to request a chat without a
  *   username. If not specified, no additional restrictions are applied.
  * @param chatIsCreated
  *   Optional. Pass True to request a chat owned by the user. Otherwise, no additional restrictions are applied.
  * @param userAdministratorRights
  *   Optional. A JSON-serialized object listing the required administrator rights of the user in the chat. The rights
  *   must be a superset of bot_administrator_rights. If not specified, no additional restrictions are applied.
  * @param botAdministratorRights
  *   Optional. A JSON-serialized object listing the required administrator rights of the bot in the chat. The rights
  *   must be a subset of user_administrator_rights. If not specified, no additional restrictions are applied.
  * @param botIsMember
  *   Optional. Pass True to request a chat with the bot as a member. Otherwise, no additional restrictions are applied.
  * @param requestTitle
  *   Optional. Pass True to request the chat's title
  * @param requestUsername
  *   Optional. Pass True to request the chat's username
  * @param requestPhoto
  *   Optional. Pass True to request the chat's photo
  */
final case class KeyboardButtonRequestChat(
  requestId: Int,
  chatIsChannel: Boolean,
  chatIsForum: Option[Boolean] = Option.empty,
  chatHasUsername: Option[Boolean] = Option.empty,
  chatIsCreated: Option[Boolean] = Option.empty,
  userAdministratorRights: Option[ChatAdministratorRights] = Option.empty,
  botAdministratorRights: Option[ChatAdministratorRights] = Option.empty,
  botIsMember: Option[Boolean] = Option.empty,
  requestTitle: Option[Boolean] = Option.empty,
  requestUsername: Option[Boolean] = Option.empty,
  requestPhoto: Option[Boolean] = Option.empty
)
