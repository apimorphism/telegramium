package telegramium.bots

/** This object represents a venue.
  *
  * @param location
  *   Venue location. Can't be a live location
  * @param title
  *   Name of the venue
  * @param address
  *   Address of the venue
  * @param foursquareId
  *   Optional. Foursquare identifier of the venue
  * @param foursquareType
  *   Optional. Foursquare type of the venue. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium”
  *   or “food/icecream”.)
  * @param googlePlaceId
  *   Optional. Google Places identifier of the venue
  * @param googlePlaceType
  *   Optional. Google Places type of the venue. (See supported types.)
  */
final case class Venue(
  location: Location,
  title: String,
  address: String,
  foursquareId: Option[String] = Option.empty,
  foursquareType: Option[String] = Option.empty,
  googlePlaceId: Option[String] = Option.empty,
  googlePlaceType: Option[String] = Option.empty
)
