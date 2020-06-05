package telegramium.bots

package object high {

  type KeyboardButton = telegramium.bots.KeyboardButton
  object KeyboardButton {
    def apply(
      text: String,
      requestContact: Option[Boolean] = Option.empty,
      requestLocation: Option[Boolean] = Option.empty,
      requestPoll: Option[KeyboardButtonPollType] = Option.empty
    ): KeyboardButton = new KeyboardButton(text, requestContact, requestLocation, requestPoll)

    /**
     * Text will be sent to the bot as a message when the button is pressed.
     */
    def text(text: String): KeyboardButton = new KeyboardButton(text)

    /**
     * The user's phone number will be sent as a contact when the button is pressed.
     */
    def requestContact(text: String): KeyboardButton = new KeyboardButton(text, requestContact = Some(true))

    /**
     * The user's current location will be sent when the button is pressed
     */
    def requestLocation(text: String): KeyboardButton = new KeyboardButton(text, requestLocation = Some(true))

    /**
     * The user will be asked to create a poll and send it to the bot when the button is pressed
     */
    def requestPoll(text: String, `type`: KeyboardButtonPollType) = new KeyboardButton(text, requestPoll = Some(`type`))
  }

  type ReplyKeyboardMarkup = telegramium.bots.ReplyKeyboardMarkup
  object ReplyKeyboardMarkup {
    def apply(
      keyboard: List[List[KeyboardButton]] = List.empty,
      resizeKeyboard: Option[Boolean] = Option.empty,
      oneTimeKeyboard: Option[Boolean] = Option.empty,
      selective: Option[Boolean] = Option.empty
    ): ReplyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard, resizeKeyboard, oneTimeKeyboard,selective)

    /**
     * Creates a reply keyboard markup with one button
     */
    def singleButton(
      button: KeyboardButton,
      resizeKeyboard: Option[Boolean] = Option.empty,
      oneTimeKeyboard: Option[Boolean] = Option.empty,
      selective: Option[Boolean] = Option.empty
    ) = new ReplyKeyboardMarkup(
      keyboard = List(List(button)),
      resizeKeyboard = resizeKeyboard,
      oneTimeKeyboard = oneTimeKeyboard,
      selective = selective
    )

    /**
     * Creates a reply keyboard markup with multiple buttons on a single row
     */
    def singleRow(
      row: List[KeyboardButton],
      resizeKeyboard: Option[Boolean] = Option.empty,
      oneTimeKeyboard: Option[Boolean] = Option.empty,
      selective: Option[Boolean] = Option.empty
    ) = new ReplyKeyboardMarkup(
      keyboard = List(row),
      resizeKeyboard = resizeKeyboard,
      oneTimeKeyboard = oneTimeKeyboard,
      selective = selective
    )

    /**
     * Creates a reply keyboard markup with multiple buttons on a single column
     */
    def singleColumn(
      column: List[KeyboardButton],
      resizeKeyboard: Option[Boolean] = Option.empty,
      oneTimeKeyboard: Option[Boolean] = Option.empty,
      selective: Option[Boolean] = Option.empty
    ) = new ReplyKeyboardMarkup(
      keyboard = column.map(List(_)),
      resizeKeyboard = resizeKeyboard,
      oneTimeKeyboard = oneTimeKeyboard,
      selective = selective
    )
  }

  type InlineKeyboardButton = telegramium.bots.InlineKeyboardButton
  object InlineKeyboardButton {
    def apply(
      text: String,
      url: Option[String] = Option.empty,
      loginUrl: Option[LoginUrl] = Option.empty,
      callbackData: Option[String] = Option.empty,
      switchInlineQuery: Option[String] = Option.empty,
      switchInlineQueryCurrentChat: Option[String] = Option.empty,
      callbackGame: Option[String] = Option.empty,
      pay: Option[Boolean] = Option.empty
    ): InlineKeyboardButton =
      new InlineKeyboardButton(text, url, loginUrl, callbackData, switchInlineQuery, switchInlineQueryCurrentChat, callbackGame, pay)

    /**
     * Creates an inline keyboard button that sends a callback query to bot when pressed
     */
    def callbackData(text: String, callbackData: String): InlineKeyboardButton =
      new InlineKeyboardButton(text, callbackData = Some(callbackData))

    /**
     * Creates an inline keyboard button that opens a HTTP url when pressed
     */
    def url(text: String, url: String): InlineKeyboardButton =
      new InlineKeyboardButton(text, url = Some(url))

    /**
     * Creates an inline keyboard button that opens a HTTP URL to automatically authorize the user
     */
    def loginUrl(text: String, loginUrl: LoginUrl): InlineKeyboardButton =
      new InlineKeyboardButton(text, loginUrl = Some(loginUrl))

    /**
     * Creates an inline keyboard button. Pressing the button will prompt the user to select one of their chats,
     * open that chat and insert the bot's username and the specified inline query in the input field.
     */
    def switchInlineQuery(text: String, query: String): InlineKeyboardButton =
      new InlineKeyboardButton(text, switchInlineQuery = Some(query))

    /**
     * Creates an inline keyboard button. Pressing the button will insert the bot's username and the specified inline
     * query in the current chat's input field.
     */
    def switchInlineQueryCurrentChat(text: String, query: String): InlineKeyboardButton =
      new InlineKeyboardButton(text, switchInlineQueryCurrentChat = Some(query))

    /**
     * Creates an inline keyboard button. Pressing the button will launch the game.
     */
    def callbackGame(text: String, callbackGame: String): InlineKeyboardButton =
      new InlineKeyboardButton(text, callbackGame = Some(callbackGame))

    /**
     * Creates an inline keyboard button for a Pay button
     */
    def pay(text: String): InlineKeyboardButton =
      new InlineKeyboardButton(text, pay = Some(true))
  }

  type InlineKeyboardMarkup = telegramium.bots.InlineKeyboardMarkup
  object InlineKeyboardMarkup {
    def apply(inlineKeyboard: List[List[InlineKeyboardButton]] = List.empty): InlineKeyboardMarkup =
      new InlineKeyboardMarkup(inlineKeyboard)

    /**
     * Creates an inline keyboard markup with one button
     */
    def singleButton(button: InlineKeyboardButton): InlineKeyboardMarkup =
      new InlineKeyboardMarkup(List(List(button)))

    /**
     * Creates an inline keyboard markup with multiple buttons on a single row
     */
    def singleRow(row: List[InlineKeyboardButton]): InlineKeyboardMarkup =
      new InlineKeyboardMarkup(List(row))

    /**
     * Creates an inline keyboard markup with multiple buttons on a single column
     */
    def singleColumn(column: List[InlineKeyboardButton]): InlineKeyboardMarkup =
      new InlineKeyboardMarkup(column.map(List(_)))
  }

}
