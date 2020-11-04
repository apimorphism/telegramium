package telegramium.bots

/** This object represents a venue.*/
final case class Venue(
                       /** Venue location. Can't be a live location*/
                       location: Location,
                       /** Name of the venue*/
                       title: String,
                       /** Address of the venue*/
                       address: String,
                       /** Optional. Foursquare identifier of the venue*/
                       foursquareId: Option[String] = Option.empty,
                       /** Optional. Foursquare type of the venue. (For example,
                         * “arts_entertainment/default”, “arts_entertainment/aquarium”
                         * or “food/icecream”.)*/
                       foursquareType: Option[String] = Option.empty,
                       /** Optional. Google Places identifier of the venue*/
                       googlePlaceId: Option[String] = Option.empty,
                       /** Optional. Google Places type of the venue. (See supported
                         * types.)*/
                       googlePlaceType: Option[String] = Option.empty)
