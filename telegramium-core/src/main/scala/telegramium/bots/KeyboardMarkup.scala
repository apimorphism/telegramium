package telegramium.bots

sealed trait KeyboardMarkup {}

/** This object represents an inline keyboard that appears right next to the
  * message it belongs to.*/
final case class InlineKeyboardMarkup(
    /** Array of button rows, each represented by an Array of
      * InlineKeyboardButton objects*/
    inlineKeyboard: List[List[InlineKeyboardButton]] = List.empty)
    extends KeyboardMarkup

/** Upon receiving a message with this object, Telegram clients will display a
  * reply interface to the user (act as if the user has selected the bot's message
  * and tapped 'Reply'). This can be extremely useful if you want to create
  * user-friendly step-by-step interfaces without having to sacrifice privacy mode.*/
final case class ForceReply(
                            /** Shows reply interface to the user, as if they manually
                              * selected the bot's message and tapped 'Reply'*/
                            forceReply: Boolean,
                            /** Optional. Use this parameter if you want to force reply
                              * from specific users only. Targets: 1) users that are
                              * @mentioned in the text of the Message object; 2) if the
                              * bot's message is a reply (has reply_to_message_id), sender
                              * of the original message.*/
                            selective: Option[Boolean] = Option.empty)
    extends KeyboardMarkup

/** Upon receiving a message with this object, Telegram clients will remove the
  * current custom keyboard and display the default letter-keyboard. By default,
  * custom keyboards are displayed until a new keyboard is sent by a bot. An
  * exception is made for one-time keyboards that are hidden immediately after the
  * user presses a button (see ReplyKeyboardMarkup).*/
final case class ReplyKeyboardRemove(
                                     /** Requests clients to remove the custom keyboard (user will
                                       * not be able to summon this keyboard; if you want to hide the
                                       * keyboard from sight but keep it accessible, use
                                       * one_time_keyboard in ReplyKeyboardMarkup)*/
                                     removeKeyboard: Boolean,
                                     /** Optional. Use this parameter if you want to remove the
                                       * keyboard for specific users only. Targets: 1) users that are
                                       * @mentioned in the text of the Message object; 2) if the
                                       * bot's message is a reply (has reply_to_message_id), sender
                                       * of the original message. Example: A user votes in a poll,
                                       * bot returns confirmation message in reply to the vote and
                                       * removes the keyboard for that user, while still showing the
                                       * keyboard with poll options to users who haven't voted yet.*/
                                     selective: Option[Boolean] = Option.empty)
    extends KeyboardMarkup

/** This object represents a custom keyboard with reply options (see Introduction
  * to bots for details and examples).*/
final case class ReplyKeyboardMarkup(
                                     /** Array of button rows, each represented by an Array of
                                       * KeyboardButton objects*/
                                     keyboard: List[List[KeyboardButton]] = List.empty,
                                     /** Optional. Requests clients to resize the keyboard
                                       * vertically for optimal fit (e.g., make the keyboard smaller
                                       * if there are just two rows of buttons). Defaults to false,
                                       * in which case the custom keyboard is always of the same
                                       * height as the app's standard keyboard.*/
                                     resizeKeyboard: Option[Boolean] = Option.empty,
                                     /** Optional. Requests clients to hide the keyboard as soon as
                                       * it's been used. The keyboard will still be available, but
                                       * clients will automatically display the usual letter-keyboard
                                       * in the chat – the user can press a special button in the
                                       * input field to see the custom keyboard again. Defaults to
                                       * false.*/
                                     oneTimeKeyboard: Option[Boolean] = Option.empty,
                                     /** Optional. Use this parameter if you want to show the
                                       * keyboard to specific users only. Targets: 1) users that are
                                       * @mentioned in the text of the Message object; 2) if the
                                       * bot's message is a reply (has reply_to_message_id), sender
                                       * of the original message. Example: A user requests to change
                                       * the bot's language, bot replies to the request with a
                                       * keyboard to select the new language. Other users in the
                                       * group don't see the keyboard.*/
                                     selective: Option[Boolean] = Option.empty)
    extends KeyboardMarkup
