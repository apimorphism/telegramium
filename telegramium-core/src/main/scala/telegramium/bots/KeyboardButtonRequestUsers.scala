package telegramium.bots

/** This object defines the criteria used to request suitable users. Information about the selected users will be shared
  * with the bot when the corresponding button is pressed.
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
  * @param requestName
  *   Optional. Pass True to request the users' first and last name
  * @param requestUsername
  *   Optional. Pass True to request the users' username
  * @param requestPhoto
  *   Optional. Pass True to request the users' photo
  */
final case class KeyboardButtonRequestUsers(
  requestId: Int,
  userIsBot: Option[Boolean] = Option.empty,
  userIsPremium: Option[Boolean] = Option.empty,
  maxQuantity: Option[Int] = Option.empty,
  requestName: Option[Boolean] = Option.empty,
  requestUsername: Option[Boolean] = Option.empty,
  requestPhoto: Option[Boolean] = Option.empty
)
