package telegramium.bots

/** This object represents a dice with a random value from 1 to 6 for currently
  * supported base emoji. (Yes, we're aware of the “proper” singular of die. But
  * it's awkward, and we decided to help it change. One dice at a time!)*/
final case class Dice(
                      /** Emoji on which the dice throw animation is based*/
                      emoji: String,
                      /** Value of the dice, 1-6 for currently supported base emoji*/
                      value: Int)
