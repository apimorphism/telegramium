package telegramium.bots

/** This object represents an animated emoji that displays a random value.*/
final case class Dice(
                      /** Emoji on which the dice throw animation is based*/
                      emoji: Emoji,
                      /** Value of the dice, 1-6 for EmojiDice and EmojiDarts base
                        * emoji, 1-5 for EmojiBasketball base emoji*/
                      value: Int)
