package telegramium.bots

/** This object represents an animated emoji that displays a random value.
  *
  * @param emoji
  *   Emoji on which the dice throw animation is based
  * @param value
  *   Value of the dice, 1-6 for “🎲”, “🎯” and “🎳” base emoji, 1-5 for “🏀” and “⚽” base emoji, 1-64 for “🎰” base
  *   emoji
  */
final case class Dice(emoji: iozhik.OpenEnum[Emoji], value: Int)
