package telegramium.bots

sealed trait ChatShareTheme {}

/** The theme based on a unique gift owned by the user or the chat.
  *
  * @param gift
  *   The gift
  */
final case class ChatShareThemeUniqueGift(gift: UniqueGift) extends ChatShareTheme

/** The theme with emoji placed on a background.
  *
  * @param emojis
  *   The emoji; 0-6 regular or custom emoji
  * @param backgroundBackdropId
  *   <em>Optional</em>. Identifier of the backdrop used as the background; omitted if the default background is used.
  *   Use <a href="https://core.telegram.org/bots/api#getchatsharethemebackdrops">getChatShareThemeBackdrops</a> to get
  *   details about the backdrop.
  */
final case class ChatShareThemeEmoji(
  emojis: List[iozhik.JsonOpenEnum[EmojiType]] = List.empty,
  backgroundBackdropId: Option[Int] = Option.empty
) extends ChatShareTheme
