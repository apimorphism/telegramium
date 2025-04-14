package telegramium.bots

/** Describes an amount of Telegram Stars.
  *
  * @param amount
  *   Integer amount of Telegram Stars, rounded to 0; can be negative
  * @param nanostarAmount
  *   Optional. The number of 1/1000000000 shares of Telegram Stars; from -999999999 to 999999999; can be negative if
  *   and only if amount is non-positive
  */
final case class StarAmount(amount: Int, nanostarAmount: Option[Int] = Option.empty)
