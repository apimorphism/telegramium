package telegramium.bots

/** This object describes a sticker to be added to a sticker set.
  *
  * @param sticker
  *   The added sticker. Pass a <em>file_id</em> as a String to send a file that already exists on the Telegram servers,
  *   pass an HTTP URL as a String for Telegram to get a file from the Internet, or pass
  *   “attach://&lt;file_attach_name&gt;” to upload a new file using multipart/form-data under &lt;file_attach_name&gt;
  *   name. Animated and video stickers can't be uploaded via HTTP URL.
  * @param format
  *   Format of the added sticker, must be one of “static” for a <strong>.WEBP</strong> or <strong>.PNG</strong> image,
  *   “animated” for a <strong>.TGS</strong> animation, “video” for a <strong>.WEBM</strong> video
  * @param emojiList
  *   List of 1-20 emoji associated with the sticker
  * @param maskPosition
  *   <em>Optional</em>. Position where the mask should be placed on faces. For “mask” stickers only.
  * @param keywords
  *   <em>Optional</em>. List of 0-20 search keywords for the sticker with total length of up to 64 characters. For
  *   “regular” and “custom_emoji” stickers only.
  */
final case class InputSticker(
  sticker: IFile,
  format: String,
  emojiList: List[String] = List.empty,
  maskPosition: Option[MaskPosition] = Option.empty,
  keywords: List[String] = List.empty
)
