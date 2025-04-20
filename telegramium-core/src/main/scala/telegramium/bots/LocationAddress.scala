package telegramium.bots

/** Describes the physical address of a location.
  *
  * @param countryCode
  *   The two-letter ISO 3166-1 alpha-2 country code of the country where the location is located
  * @param state
  *   Optional. State of the location
  * @param city
  *   Optional. City of the location
  * @param street
  *   Optional. Street address of the location
  */
final case class LocationAddress(
  countryCode: String,
  state: Option[String] = Option.empty,
  city: Option[String] = Option.empty,
  street: Option[String] = Option.empty
)
