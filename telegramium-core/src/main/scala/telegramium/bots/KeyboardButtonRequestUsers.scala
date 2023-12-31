package telegramium.bots

/** This object defines the criteria used to request suitable users. The identifiers of the selected users will be
  * shared with the bot when the corresponding button is pressed.
  *
  * @param requestId
  *   Signed 32-bit identifier of the request that will be received back in the UsersShared object. Must be unique
  *   within the message
  * @param userIsBot
  *   Optional. Pass True to request bots, pass False to request regular users. If not specified, no additional
  *   restrictions are applied.
  * @param userIsPremium
  *   Optional. Pass True to request premium users, pass False to request non-premium users. If not specified, no
  *   additional restrictions are applied.
  * @param maxQuantity
  *   Optional. The maximum number of users to be selected; 1-10. Defaults to 1.
  */
final case class KeyboardButtonRequestUsers(
  requestId: Int,
  userIsBot: Option[Boolean] = Option.empty,
  userIsPremium: Option[Boolean] = Option.empty,
  maxQuantity: Option[Int] = Option.empty
)
