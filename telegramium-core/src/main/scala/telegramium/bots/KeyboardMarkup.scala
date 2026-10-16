package telegramium.bots

sealed trait KeyboardMarkup {}

/** This object represents an <a href="https://core.telegram.org/bots/features#inline-keyboards">inline keyboard</a>
  * that appears right next to the message it belongs to.
  *
  * @param inlineKeyboard
  *   Array of button rows, each represented by an Array of <a
  *   href="https://core.telegram.org/bots/api#inlinekeyboardbutton">InlineKeyboardButton</a> objects
  * @param forceReply
  *   <em>Optional</em>. Pass <em>True</em> if the reply interface must be shown to the user, as if they had manually
  *   selected the bot's message and tapped 'Reply'. The value of the field can't be changed when the inline keyboard is
  *   edited.
  */
final case class InlineKeyboardMarkup(
  inlineKeyboard: List[List[InlineKeyboardButton]] = List.empty,
  forceReply: Option[Boolean] = Option.empty
) extends KeyboardMarkup

/** Upon receiving a message with this object, Telegram clients will display a reply interface to the user (act as if
  * the user has selected the bot's message and tapped 'Reply'). This can be extremely useful if you want to create
  * user-friendly step-by-step interfaces without having to sacrifice <a
  * href="https://core.telegram.org/bots/features#privacy-mode">privacy mode</a>. Not supported in channels and for
  * messages sent on behalf of a user account.
  *
  * @param forceReply
  *   Shows reply interface to the user, as if they had manually selected the bot's message and tapped 'Reply'
  * @param inputFieldPlaceholder
  *   <em>Optional</em>. The placeholder to be shown in the input field when the reply is active; 1-64 characters
  * @param selective
  *   <em>Optional</em>. Use this parameter if you want to force reply from specific users only. Targets: 1) users that
  *   are &#064;mentioned in the <em>text</em> of the <a href="https://core.telegram.org/bots/api#message">Message</a>
  *   object; 2) if the bot's message is a reply to a message in the same chat and forum topic, sender of the original
  *   message.
  */
final case class ForceReply(
  forceReply: Boolean,
  inputFieldPlaceholder: Option[String] = Option.empty,
  selective: Option[Boolean] = Option.empty
) extends KeyboardMarkup

/** Upon receiving a message with this object, Telegram clients will remove the current custom keyboard and display the
  * default letter-keyboard. By default, custom keyboards are displayed until a new keyboard is sent by a bot. An
  * exception is made for one-time keyboards that are hidden immediately after the user presses a button (see <a
  * href="https://core.telegram.org/bots/api#replykeyboardmarkup">ReplyKeyboardMarkup</a>). Not supported in channels
  * and for messages sent on behalf of a business account.
  *
  * @param removeKeyboard
  *   Requests clients to remove the custom keyboard (user will not be able to summon this keyboard; if you want to hide
  *   the keyboard from sight but keep it accessible, use <em>one_time_keyboard</em> in <a
  *   href="https://core.telegram.org/bots/api#replykeyboardmarkup">ReplyKeyboardMarkup</a>)
  * @param selective
  *   <em>Optional</em>. Use this parameter if you want to remove the keyboard for specific users only. Targets: 1)
  *   users that are &#064;mentioned in the <em>text</em> of the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object; 2) if the bot's message is a reply to a
  *   message in the same chat and forum topic, sender of the original message.<br>
  *
  * <br>
  *
  * <em>Example:</em> A user votes in a poll, bot returns confirmation message in reply to the vote and removes the
  * keyboard for that user, while still showing the keyboard with poll options to users who haven't voted yet.
  */
final case class ReplyKeyboardRemove(removeKeyboard: Boolean, selective: Option[Boolean] = Option.empty)
    extends KeyboardMarkup

/** This object represents a <a href="https://core.telegram.org/bots/features#keyboards">custom keyboard</a> with reply
  * options (see <a href="https://core.telegram.org/bots/features#keyboards">Introduction to bots</a> for details and
  * examples). Not supported in channels and for messages sent on behalf of a business account.
  *
  * @param keyboard
  *   Array of button rows, each represented by an Array of <a
  *   href="https://core.telegram.org/bots/api#keyboardbutton">KeyboardButton</a> objects
  * @param isPersistent
  *   <em>Optional</em>. Requests clients to always show the keyboard when the regular keyboard is hidden. Defaults to
  *   <em>False</em>, in which case the custom keyboard can be hidden and opened with a keyboard icon.
  * @param resizeKeyboard
  *   <em>Optional</em>. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard
  *   smaller if there are just two rows of buttons). Defaults to <em>False</em>, in which case the custom keyboard is
  *   always of the same height as the app's standard keyboard.
  * @param oneTimeKeyboard
  *   <em>Optional</em>. Requests clients to hide the keyboard as soon as it's been used. The keyboard will still be
  *   available, but clients will automatically display the usual letter-keyboard in the chat - the user can press a
  *   special button in the input field to see the custom keyboard again. Defaults to <em>False</em>.
  * @param inputFieldPlaceholder
  *   <em>Optional</em>. The placeholder to be shown in the input field when the keyboard is active; 1-64 characters
  * @param selective
  *   <em>Optional</em>. Use this parameter if you want to show the keyboard to specific users only. Targets: 1) users
  *   that are &#064;mentioned in the <em>text</em> of the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object; 2) if the bot's message is a reply to a
  *   message in the same chat and forum topic, sender of the original message.<br>
  *
  * <br>
  *
  * <em>Example:</em> A user requests to change the bot's language, bot replies to the request with a keyboard to select
  * the new language. Other users in the group don't see the keyboard.
  * @param forceReply
  *   <em>Optional</em>. Pass <em>True</em> if the reply interface must be shown to the user, as if they had manually
  *   selected the bot's message and tapped 'Reply'
  */
final case class ReplyKeyboardMarkup(
  keyboard: List[List[KeyboardButton]] = List.empty,
  isPersistent: Option[Boolean] = Option.empty,
  resizeKeyboard: Option[Boolean] = Option.empty,
  oneTimeKeyboard: Option[Boolean] = Option.empty,
  inputFieldPlaceholder: Option[String] = Option.empty,
  selective: Option[Boolean] = Option.empty,
  forceReply: Option[Boolean] = Option.empty
) extends KeyboardMarkup
