package telegramium.bots

/** This object contains a list of backdrops, which can be used as the background of a theme of the screen for sharing a
  * link to a user or a chat.
  *
  * @param backdrops
  *   The list of backdrops
  */
final case class ChatShareThemeBackdrops(backdrops: List[ChatShareThemeBackdrop] = List.empty)
