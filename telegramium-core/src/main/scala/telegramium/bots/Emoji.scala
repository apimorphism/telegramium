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

final case object EmojiFootball extends Emoji {
  override def toString() = "⚽"
}

final case object EmojiSlotMachine extends Emoji {
  override def toString() = "🎰"
}

final case object EmojiBowling extends Emoji {
  override def toString() = "🎳"
}
