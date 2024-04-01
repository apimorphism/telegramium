package telegramium.bots

/** @param day
  *   Day of the user's birth; 1-31
  * @param month
  *   Month of the user's birth; 1-12
  * @param year
  *   Optional. Year of the user's birth
  */
final case class Birthdate(day: Int, month: Int, year: Option[Int] = Option.empty)
