package telegramium.bots

/** This object represents a point on the map.*/
final case class Location(
                          /** Longitude as defined by sender*/
                          longitude: Float,
                          /** Latitude as defined by sender*/
                          latitude: Float)
