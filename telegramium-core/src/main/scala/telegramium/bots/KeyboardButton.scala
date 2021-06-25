package telegramium.bots

/**
 * This object represents one button of the reply keyboard. For simple text
 * buttons String can be used instead of this object to specify text of the button.
 * Optional fields request_contact, request_location, and request_poll are mutually
 * exclusive.
 *
 * @param text Text of the button. If none of the optional fields are
 * used, it will be sent as a message when the button is
 * pressed
 * @param requestContact Optional. If True, the user's phone number will be sent as
 * a contact when the button is pressed. Available in private
 * chats only
 * @param requestLocation Optional. If True, the user's current location will be sent
 * when the button is pressed. Available in private chats only
 * @param requestPoll Optional. If specified, the user will be asked to create a
 * poll and send it to the bot when the button is pressed.
 * Available in private chats only
 */
final case class KeyboardButton(text: String,
                                requestContact: Option[Boolean] = Option.empty,
                                requestLocation: Option[Boolean] = Option.empty,
                                requestPoll: Option[KeyboardButtonPollType] = Option.empty
)
