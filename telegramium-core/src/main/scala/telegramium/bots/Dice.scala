package telegramium.bots

/** This object represents an animated emoji that displays a random value.
  *
  * @param emoji Emoji on which the dice throw animation is based
  * @param value Value of the dice, 1-6 for EmojiDice and EmojiDarts base
  * emoji, 1-5 for EmojiBasketball and EmojiFootball base emoji,
  * 1-64 for EmojiSlotMachine base emoji */
final case class Dice(emoji: Emoji, value: Int)
