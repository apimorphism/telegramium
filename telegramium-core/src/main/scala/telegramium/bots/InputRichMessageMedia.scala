package telegramium.bots

/** Describes a media element embedded in an outgoing rich message.
  *
  * @param id
  *   Unique identifier of the media used in a tg://photo?id=, tg://video?id=, or tg://audio?id= link. 1-64 characters,
  *   only A-Z, a-z, 0-9, _ and - are allowed.
  * @param media
  *   The media to be sent. Everything except the media itself and its properties is ignored.
  */
final case class InputRichMessageMedia(id: String, media: InputMedia)
