package telegramium.bots

sealed trait Emoji {}

case object EmojiDice extends Emoji {
  override def toString() = "🎲"
}

case object EmojiDarts extends Emoji {
  override def toString() = "🎯"
}

case object EmojiBasketball extends Emoji {
  override def toString() = "🏀"
}

case object EmojiFootball extends Emoji {
  override def toString() = "⚽"
}

case object EmojiSlotMachine extends Emoji {
  override def toString() = "🎰"
}

case object EmojiBowling extends Emoji {
  override def toString() = "🎳"
}
