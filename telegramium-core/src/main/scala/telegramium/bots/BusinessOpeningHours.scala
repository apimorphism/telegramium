package telegramium.bots

/** @param timeZoneName
  *   Unique name of the time zone for which the opening hours are defined
  * @param openingHours
  *   List of time intervals describing business opening hours
  */
final case class BusinessOpeningHours(
  timeZoneName: String,
  openingHours: List[BusinessOpeningHoursInterval] = List.empty
)
