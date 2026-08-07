package telegramium.bots

sealed trait InputRichBlock {}

/** A table, corresponding to the HTML tag <table>.
  *
  * @param cells
  *   Cells of the table
  * @param isBordered
  *   Optional. Pass True if the table has borders
  * @param isStriped
  *   Optional. Pass True if the table is striped
  * @param caption
  *   Optional. Caption of the table
  */
final case class InputRichBlockTable(
  cells: List[List[RichBlockTableCell]] = List.empty,
  isBordered: Option[Boolean] = Option.empty,
  isStriped: Option[Boolean] = Option.empty,
  caption: Option[RichText] = Option.empty
) extends InputRichBlock

/** A block with an anchor, corresponding to the HTML tag <a> with the attribute name.
  *
  * @param name
  *   The name of the anchor
  */
final case class InputRichBlockAnchor(name: String) extends InputRichBlock

/** A block with a voice note, corresponding to the HTML tag <audio>.
  *
  * @param voiceNote
  *   The voice note. Caption is ignored.
  * @param caption
  *   Optional. Caption of the block
  */
final case class InputRichBlockVoiceNote(
  voiceNote: InputMediaVoiceNote,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A list of blocks, corresponding to the HTML tag <ul> or <ol> with multiple nested tags <li>.
  *
  * @param items
  *   Items of the list
  */
final case class InputRichBlockList(items: List[InputRichBlockListItem] = List.empty) extends InputRichBlock

/** A text paragraph, corresponding to the HTML tag <p>.
  *
  * @param text
  *   Text of the block
  */
final case class InputRichBlockParagraph(text: RichText) extends InputRichBlock

/** A preformatted text block, corresponding to the nested HTML tags <pre> and <code>.
  *
  * @param text
  *   Text of the block
  * @param language
  *   Optional. The programming language of the text
  */
final case class InputRichBlockPreformatted(text: RichText, language: Option[String] = Option.empty)
    extends InputRichBlock

/** A block with a music file, corresponding to the HTML tag <audio>.
  *
  * @param audio
  *   The audio. Caption is ignored.
  * @param caption
  *   Optional. Caption of the block
  */
final case class InputRichBlockAudio(audio: InputMediaAudio, caption: Option[RichBlockCaption] = Option.empty)
    extends InputRichBlock

/** A collage, corresponding to the custom HTML tag <tg-collage>.
  *
  * @param blocks
  *   Elements of the collage
  * @param caption
  *   Optional. Caption of the block
  */
final case class InputRichBlockCollage(
  blocks: List[InputRichBlock] = List.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A footer, corresponding to the HTML tag <footer>.
  *
  * @param text
  *   Text of the block
  */
final case class InputRichBlockFooter(text: RichText) extends InputRichBlock

/** A block with a mathematical expression in LaTeX format, corresponding to the custom HTML tag <tg-math-block>.
  *
  * @param expression
  *   The mathematical expression in LaTeX format
  */
final case class InputRichBlockMathematicalExpression(expression: String) extends InputRichBlock

/** A divider, corresponding to the HTML tag <hr/>. */
case object InputRichBlockDivider extends InputRichBlock

/** A block quotation, corresponding to the HTML tag <blockquote>.
  *
  * @param blocks
  *   Content of the block
  * @param credit
  *   Optional. Credit of the block
  */
final case class InputRichBlockBlockQuotation(
  blocks: List[InputRichBlock] = List.empty,
  credit: Option[RichText] = Option.empty
) extends InputRichBlock

/** A block with a “Thinking…” placeholder, corresponding to the custom HTML tag <tg-thinking>. The block may be used
  * only in sendRichMessageDraft, therefore it can't be received in messages. See https://t.me/addemoji/AIActions for
  * examples of custom emoji that are recommended for usage in the block.
  *
  * @param text
  *   Text of the block. See https://t.me/addemoji/AIActions for examples of custom emoji that are recommended for usage
  *   in the block.
  */
final case class InputRichBlockThinking(text: RichText) extends InputRichBlock

/** A block with a map, corresponding to the custom HTML tag <tg-map>. The map's width and height must not exceed 10000
  * in total. The width and height ratio must be at most 20.
  *
  * @param location
  *   Location of the center of the map
  * @param zoom
  *   Map zoom level; 0-24
  * @param width
  *   Map width; 0-10000
  * @param height
  *   Map height; 0-10000
  * @param caption
  *   Optional. Caption of the block
  */
final case class InputRichBlockMap(
  location: Location,
  zoom: Int,
  width: Int,
  height: Int,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A slideshow, corresponding to the custom HTML tag <tg-slideshow>.
  *
  * @param blocks
  *   Elements of the slideshow
  * @param caption
  *   Optional. Caption of the block
  */
final case class InputRichBlockSlideshow(
  blocks: List[InputRichBlock] = List.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A block with a photo, corresponding to the HTML tag <img>.
  *
  * @param photo
  *   The photo. Caption is ignored.
  * @param caption
  *   Optional. Caption of the block
  */
final case class InputRichBlockPhoto(photo: InputMediaPhoto, caption: Option[RichBlockCaption] = Option.empty)
    extends InputRichBlock

/** A block with an animation, corresponding to the HTML tag <video>.
  *
  * @param animation
  *   The animation. Caption is ignored.
  * @param caption
  *   Optional. Caption of the block
  */
final case class InputRichBlockAnimation(
  animation: InputMediaAnimation,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A section heading, corresponding to the HTML tags <h1>, <h2>, <h3>, <h4>, <h5>, or <h6>.
  *
  * @param text
  *   Text of the block
  * @param size
  *   Relative size of the text font; 1-6, 1 is the largest, 6 is the smallest
  */
final case class InputRichBlockSectionHeading(text: RichText, size: Int) extends InputRichBlock

/** An expandable block for details disclosure, corresponding to the HTML tag <details>.
  *
  * @param summary
  *   Always shown summary of the block
  * @param blocks
  *   Content of the block
  * @param isOpen
  *   Optional. Pass True if the content of the block is visible by default
  */
final case class InputRichBlockDetails(
  summary: RichText,
  blocks: List[InputRichBlock] = List.empty,
  isOpen: Option[Boolean] = Option.empty
) extends InputRichBlock

/** A block with a video, corresponding to the HTML tag <video>.
  *
  * @param video
  *   The video. Caption is ignored.
  * @param caption
  *   Optional. Caption of the block
  */
final case class InputRichBlockVideo(video: InputMediaVideo, caption: Option[RichBlockCaption] = Option.empty)
    extends InputRichBlock

/** A quotation with centered text, loosely corresponding to the HTML tag <aside>.
  *
  * @param text
  *   Text of the block
  * @param credit
  *   Optional. Credit of the block
  */
final case class InputRichBlockPullQuotation(text: RichText, credit: Option[RichText] = Option.empty)
    extends InputRichBlock
