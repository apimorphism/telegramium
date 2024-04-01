package telegramium.bots

/** This object contains information about the users whose identifiers were shared with the bot using a
  * KeyboardButtonRequestUsers button.
  *
  * @param requestId
  *   Identifier of the request
  * @param users
  *   Information about users shared with the bot.
  */
final case class UsersShared(requestId: Int, users: List[SharedUser] = List.empty)
