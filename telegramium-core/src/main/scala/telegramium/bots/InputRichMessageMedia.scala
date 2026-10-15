package telegramium.bots

/** Describes a media element embedded in an outgoing rich message.
  *
  * @param id
  *   Unique identifier of the media used in a <code>tg://photo?id=</code>, <code>tg://video?id=</code>,
  *   <code>tg://document?id=</code>, or <code>tg://audio?id=</code> link. 1-64 characters, only <code>A-Z</code>,
  *   <code>a-z</code>, <code>0-9</code>, <code>_</code> and <code>-</code> are allowed.
  * @param media
  *   The media to be sent. Everything except the media itself and its properties is ignored.
  */
final case class InputRichMessageMedia(id: String, media: InputMedia)
