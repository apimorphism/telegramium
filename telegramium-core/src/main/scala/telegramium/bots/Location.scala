package telegramium.bots

/** This object represents a point on the map.*/
final case class Location(
                          /** Longitude as defined by sender*/
                          longitude: Float,
                          /** Latitude as defined by sender*/
                          latitude: Float,
                          /** Optional. The radius of uncertainty for the location,
                            * measured in meters; 0-1500*/
                          horizontalAccuracy: Option[Float] = Option.empty,
                          /** Optional. Time relative to the message sending date, during
                            * which the location can be updated, in seconds. For active
                            * live locations only.*/
                          livePeriod: Option[Int] = Option.empty,
                          /** Optional. The direction in which user is moving, in
                            * degrees; 1-360. For active live locations only.*/
                          heading: Option[Int] = Option.empty,
                          /** Optional. Maximum distance for proximity alerts about
                            * approaching another chat member, in meters. For sent live
                            * locations only.*/
                          proximityAlertRadius: Option[Int] = Option.empty)
