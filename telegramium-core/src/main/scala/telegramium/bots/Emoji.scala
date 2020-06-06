package telegramium.bots

sealed trait Emoji {}

final case object EmojiDice extends Emoji {
  override def toString() = "🎲"
}

final case object EmojiDarts extends Emoji {
  override def toString() = "🎯"
}

final case object EmojiBasketball extends Emoji {
  override def toString() = "🏀"
}
