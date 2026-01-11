package telegramium.bots

/** This object describes the rating of a user based on their Telegram Star spendings.
  *
  * @param level
  *   Current level of the user, indicating their reliability when purchasing digital goods and services. A higher level
  *   suggests a more trustworthy customer; a negative level is likely reason for concern.
  * @param rating
  *   Numerical value of the user's rating; the higher the rating, the better
  * @param currentLevelRating
  *   The rating value required to get the current level
  * @param nextLevelRating
  *   Optional. The rating value required to get to the next level; omitted if the maximum level was reached
  */
final case class UserRating(
  level: Int,
  rating: Int,
  currentLevelRating: Int,
  nextLevelRating: Option[Int] = Option.empty
)
