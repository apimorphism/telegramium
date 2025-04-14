package telegramium.bots

/** Describes a clickable area on a story media.
  *
  * @param position
  *   Position of the area
  * @param type
  *   Type of the area
  */
final case class StoryArea(position: StoryAreaPosition, `type`: StoryAreaType)
