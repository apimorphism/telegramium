package telegramium.bots

/** This object represents a point on the map.
  *
  * @param latitude
  *   Latitude as defined by the sender
  * @param longitude
  *   Longitude as defined by the sender
  * @param horizontalAccuracy
  *   <em>Optional</em>. The radius of uncertainty for the location, measured in meters; 0-1500
  * @param livePeriod
  *   <em>Optional</em>. Time relative to the message sending date, during which the location can be updated; in
  *   seconds. For active live locations only.
  * @param heading
  *   <em>Optional</em>. The direction in which user is moving, in degrees; 1-360. For active live locations only.
  * @param proximityAlertRadius
  *   <em>Optional</em>. The maximum distance for proximity alerts about approaching another chat member, in meters. For
  *   sent live locations only.
  */
final case class Location(
  latitude: Float,
  longitude: Float,
  horizontalAccuracy: Option[Float] = Option.empty,
  livePeriod: Option[Int] = Option.empty,
  heading: Option[Int] = Option.empty,
  proximityAlertRadius: Option[Int] = Option.empty
)
