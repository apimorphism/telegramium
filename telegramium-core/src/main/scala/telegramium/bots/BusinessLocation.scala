package telegramium.bots

/** @param address
  *   Address of the business
  * @param location
  *   Optional. Location of the business
  */
final case class BusinessLocation(address: String, location: Option[Location] = Option.empty)
