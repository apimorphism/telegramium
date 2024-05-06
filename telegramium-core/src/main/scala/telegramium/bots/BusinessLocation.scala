package telegramium.bots

/** Contains information about the location of a Telegram Business account.
  *
  * @param address
  *   Address of the business
  * @param location
  *   Optional. Location of the business
  */
final case class BusinessLocation(address: String, location: Option[Location] = Option.empty)
