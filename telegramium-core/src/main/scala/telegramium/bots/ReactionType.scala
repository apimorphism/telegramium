package telegramium.bots

sealed trait ReactionType {}

/** The reaction is paid. */
case object ReactionTypePaid extends ReactionType

/** The reaction is based on an emoji.
  *
  * @param emoji
  *   Reaction emoji. Currently, it can be one of "❤", "👍", "👎", "🔥", "🥰", "👏", "😁", "🤔", "🤯", "😱", "🤬", "😢",
  *   "🎉", "🤩", "🤮", "💩", "🙏", "👌", "🕊", "🤡", "🥱", "🥴", "😍", "🐳", "❤‍🔥", "🌚", "🌭", "💯", "🤣", "⚡", "🍌",
  *   "🏆", "💔", "🤨", "😐", "🍓", "🍾", "💋", "🖕", "😈", "😴", "😭", "🤓", "👻", "👨‍💻", "👀", "🎃", "🙈", "😇",
  *   "😨", "🤝", "✍", "🤗", "🫡", "🎅", "🎄", "☃", "💅", "🤪", "🗿", "🆒", "💘", "🙉", "🦄", "😘", "💊", "🙊", "😎",
  *   "👾", "🤷‍♂", "🤷", "🤷‍♀", "😡".
  */
final case class ReactionTypeEmoji(emoji: String) extends ReactionType

/** The reaction is based on a custom emoji.
  *
  * @param customEmojiId
  *   Custom emoji identifier
  */
final case class ReactionTypeCustomEmoji(customEmojiId: String) extends ReactionType
