package telegramium.bots

/** @param openingMinute
  *   The minute's sequence number in a week, starting on Monday, marking the start of the time interval during which
  *   the business is open; 0 - 7 24 60
  * @param closingMinute
  *   The minute's sequence number in a week, starting on Monday, marking the end of the time interval during which the
  *   business is open; 0 - 8 24 60
  */
final case class BusinessOpeningHoursInterval(openingMinute: Int, closingMinute: Int)
