package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents a venue.*/
final case class Venue(
                       /** Venue location*/
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
                       foursquareType: Option[String] = Option.empty)
