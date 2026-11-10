package telegramium.bots

/** This object describes a backdrop, which can be used as the background of a theme of the screen for sharing a link to
  * a user or a chat.
  *
  * @param id
  *   Unique identifier of the backdrop
  * @param name
  *   Name of the backdrop
  * @param colors
  *   Colors of the backdrop
  */
final case class ChatShareThemeBackdrop(id: Int, name: String, colors: UniqueGiftBackdropColors)
