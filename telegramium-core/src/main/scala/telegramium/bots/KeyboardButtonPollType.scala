package telegramium.bots

/** This object represents type of a poll, which is allowed to be created and sent when the corresponding button is
  * pressed.
  *
  * @param type
  *   Optional. If quiz is passed, the user will be allowed to create only polls in the quiz mode. If regular is passed,
  *   only regular polls will be allowed. Otherwise, the user will be allowed to create a poll of any type.
  */
final case class KeyboardButtonPollType(`type`: Option[String] = Option.empty)
