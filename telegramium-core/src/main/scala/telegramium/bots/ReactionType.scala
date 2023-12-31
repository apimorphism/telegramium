package telegramium.bots

sealed trait ReactionType {}

/** The reaction is based on an emoji.
  *
  * @param type
  *   Type of the reaction, always “emoji”
  * @param emoji
  *   Reaction emoji. Currently, it can be one of "👍", "👎", "❤", "🔥", "🥰", "👏", "😁", "🤔", "🤯", "😱", "🤬", "😢",
  *   "🎉", "🤩", "🤮", "💩", "🙏", "👌", "🕊", "🤡", "🥱", "🥴", "😍", "🐳", "❤‍🔥", "🌚", "🌭", "💯", "🤣", "⚡", "🍌",
  *   "🏆", "💔", "🤨", "😐", "🍓", "🍾", "💋", "🖕", "😈", "😴", "😭", "🤓", "👻", "👨‍💻", "👀", "🎃", "🙈", "😇",
  *   "😨", "🤝", "✍", "🤗", "🫡", "🎅", "🎄", "☃", "💅", "🤪", "🗿", "🆒", "💘", "🙉", "🦄", "😘", "💊", "🙊", "😎",
  *   "👾", "🤷‍♂", "🤷", "🤷‍♀", "😡"
  */
final case class ReactionTypeEmoji(`type`: String, emoji: String) extends ReactionType

/** The reaction is based on a custom emoji.
  *
  * @param type
  *   Type of the reaction, always “custom_emoji”
  * @param customEmojiId
  *   Custom emoji identifier
  */
final case class ReactionTypeCustomEmoji(`type`: String, customEmojiId: String) extends ReactionType
