package telegramium.bots

sealed trait EmojiType {}

/** A custom emoji.
  *
  * @param customEmojiId
  *   Identifier of the custom emoji. Use <a
  *   href="https://core.telegram.org/bots/api#getcustomemojistickers">getCustomEmojiStickers</a> to get full
  *   information about the sticker.
  */
final case class EmojiTypeCustomEmoji(customEmojiId: String) extends EmojiType

/** A regular emoji.
  *
  * @param emoji
  *   The emoji
  */
final case class EmojiTypeEmoji(emoji: String) extends EmojiType
