package telegramium.bots

/** This object represents a venue.
  *
  * @param location
  *   Venue location. Can't be a live location.
  * @param title
  *   Name of the venue
  * @param address
  *   Address of the venue
  * @param foursquareId
  *   <em>Optional</em>. Foursquare identifier of the venue
  * @param foursquareType
  *   <em>Optional</em>. Foursquare type of the venue. (For example, “arts_entertainment/default”,
  *   “arts_entertainment/aquarium” or “food/icecream”.)
  * @param googlePlaceId
  *   <em>Optional</em>. Google Places identifier of the venue
  * @param googlePlaceType
  *   <em>Optional</em>. Google Places type of the venue. (See <a
  *   href="https://developers.google.com/places/web-service/supported_types">supported types</a>.)
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
