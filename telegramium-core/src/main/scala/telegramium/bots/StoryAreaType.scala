package telegramium.bots

sealed trait StoryAreaType {}

/** Describes a story area pointing to an HTTP or tg:// link. Currently, a story can have up to 3 link areas.
  *
  * @param url
  *   HTTP or tg:// URL to be opened when the area is clicked
  */
final case class StoryAreaTypeLink(url: String) extends StoryAreaType

/** Describes a story area pointing to a location. Currently, a story can have up to 10 location areas.
  *
  * @param latitude
  *   Location latitude in degrees
  * @param longitude
  *   Location longitude in degrees
  * @param address
  *   Optional. Address of the location
  */
final case class StoryAreaTypeLocation(
  latitude: Float,
  longitude: Float,
  address: Option[LocationAddress] = Option.empty
) extends StoryAreaType

/** Describes a story area containing weather information. Currently, a story can have up to 3 weather areas.
  *
  * @param temperature
  *   Temperature, in degree Celsius
  * @param emoji
  *   Emoji representing the weather
  * @param backgroundColor
  *   A color of the area background in the ARGB format
  */
final case class StoryAreaTypeWeather(temperature: Float, emoji: String, backgroundColor: Int) extends StoryAreaType

/** Describes a story area pointing to a unique gift. Currently, a story can have at most 1 unique gift area.
  *
  * @param name
  *   Unique name of the gift
  */
final case class StoryAreaTypeUniqueGift(name: String) extends StoryAreaType

/** Describes a story area pointing to a suggested reaction. Currently, a story can have up to 5 suggested reaction
  * areas.
  *
  * @param reactionType
  *   Type of the reaction
  * @param isDark
  *   Optional. Pass True if the reaction area has a dark background
  * @param isFlipped
  *   Optional. Pass True if reaction area corner is flipped
  */
final case class StoryAreaTypeSuggestedReaction(
  reactionType: ReactionType,
  isDark: Option[Boolean] = Option.empty,
  isFlipped: Option[Boolean] = Option.empty
) extends StoryAreaType
