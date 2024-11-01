package telegramium.bots

/** This object represents an inline keyboard button that copies specified text to the clipboard.
  *
  * @param text
  *   The text to be copied to the clipboard; 1-256 characters
  */
final case class CopyTextButton(text: String)
