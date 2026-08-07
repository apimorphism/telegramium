package telegramium.bots

sealed trait RichBlock {}

/** A block with a video, corresponding to the HTML tag <video>.
  *
  * @param video
  *   The video
  * @param hasSpoiler
  *   Optional. True, if the media preview is covered by a spoiler animation
  * @param caption
  *   Optional. Caption of the block
  */
final case class RichBlockVideo(
  video: Video,
  hasSpoiler: Option[Boolean] = Option.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** An expandable block for details disclosure, corresponding to the HTML tag <details>.
  *
  * @param summary
  *   Always shown summary of the block
  * @param blocks
  *   Content of the block
  * @param isOpen
  *   Optional. True, if the content of the block is visible by default
  */
final case class RichBlockDetails(
  summary: iozhik.OpenEnum[RichText],
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  isOpen: Option[Boolean] = Option.empty
) extends RichBlock

/** A list of blocks, corresponding to the HTML tag <ul> or <ol> with multiple nested tags <li>.
  *
  * @param items
  *   Items of the list
  */
final case class RichBlockList(items: List[RichBlockListItem] = List.empty) extends RichBlock

/** A block with a photo, corresponding to the HTML tag <img>.
  *
  * @param photo
  *   Available sizes of the photo
  * @param hasSpoiler
  *   Optional. True, if the media preview is covered by a spoiler animation
  * @param caption
  *   Optional. Caption of the block
  */
final case class RichBlockPhoto(
  photo: List[PhotoSize] = List.empty,
  hasSpoiler: Option[Boolean] = Option.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A block with a “Thinking…” placeholder, corresponding to the custom HTML tag <tg-thinking>. The block may be used
  * only in sendRichMessageDraft, therefore it can't be received in messages. See https://t.me/addemoji/AIActions for
  * examples of custom emoji that are recommended for usage in the block.
  *
  * @param text
  *   Text of the block. See https://t.me/addemoji/AIActions for examples of custom emoji that are recommended for usage
  *   in the block.
  */
final case class RichBlockThinking(text: iozhik.OpenEnum[RichText]) extends RichBlock

/** A footer, corresponding to the HTML tag <footer>.
  *
  * @param text
  *   Text of the block
  */
final case class RichBlockFooter(text: iozhik.OpenEnum[RichText]) extends RichBlock

/** A block with an anchor, corresponding to the HTML tag <a> with the attribute name.
  *
  * @param name
  *   The name of the anchor
  */
final case class RichBlockAnchor(name: String) extends RichBlock

/** A block with a mathematical expression in LaTeX format, corresponding to the custom HTML tag <tg-math-block>.
  *
  * @param expression
  *   The mathematical expression in LaTeX format
  */
final case class RichBlockMathematicalExpression(expression: String) extends RichBlock

/** A block quotation, corresponding to the HTML tag <blockquote>.
  *
  * @param blocks
  *   Content of the block
  * @param credit
  *   Optional. Credit of the block
  */
final case class RichBlockBlockQuotation(
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  credit: Option[iozhik.OpenEnum[RichText]] = Option.empty
) extends RichBlock

/** A block with a map, corresponding to the custom HTML tag <tg-map>.
  *
  * @param location
  *   Location of the center of the map
  * @param zoom
  *   Map zoom level; 13-20
  * @param width
  *   Expected width of the map
  * @param height
  *   Expected height of the map
  * @param caption
  *   Optional. Caption of the block
  */
final case class RichBlockMap(
  location: Location,
  zoom: Int,
  width: Int,
  height: Int,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A slideshow, corresponding to the custom HTML tag <tg-slideshow>.
  *
  * @param blocks
  *   Elements of the slideshow
  * @param caption
  *   Optional. Caption of the block
  */
final case class RichBlockSlideshow(
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A text paragraph, corresponding to the HTML tag <p>.
  *
  * @param text
  *   Text of the block
  */
final case class RichBlockParagraph(text: iozhik.OpenEnum[RichText]) extends RichBlock

/** A quotation with centered text, loosely corresponding to the HTML tag <aside>.
  *
  * @param text
  *   Text of the block
  * @param credit
  *   Optional. Credit of the block
  */
final case class RichBlockPullQuotation(
  text: iozhik.OpenEnum[RichText],
  credit: Option[iozhik.OpenEnum[RichText]] = Option.empty
) extends RichBlock

/** A preformatted text block, corresponding to the nested HTML tags <pre> and <code>.
  *
  * @param text
  *   Text of the block
  * @param language
  *   Optional. The programming language of the text
  */
final case class RichBlockPreformatted(text: iozhik.OpenEnum[RichText], language: Option[String] = Option.empty)
    extends RichBlock

/** A table, corresponding to the HTML tag <table>.
  *
  * @param cells
  *   Cells of the table
  * @param isBordered
  *   Optional. True, if the table has borders
  * @param isStriped
  *   Optional. True, if the table is striped
  * @param caption
  *   Optional. Caption of the table
  */
final case class RichBlockTable(
  cells: List[List[RichBlockTableCell]] = List.empty,
  isBordered: Option[Boolean] = Option.empty,
  isStriped: Option[Boolean] = Option.empty,
  caption: Option[iozhik.OpenEnum[RichText]] = Option.empty
) extends RichBlock

/** A collage, corresponding to the custom HTML tag <tg-collage>.
  *
  * @param blocks
  *   Elements of the collage
  * @param caption
  *   Optional. Caption of the block
  */
final case class RichBlockCollage(
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A block with a music file, corresponding to the HTML tag <audio>.
  *
  * @param audio
  *   The audio
  * @param caption
  *   Optional. Caption of the block
  */
final case class RichBlockAudio(audio: Audio, caption: Option[RichBlockCaption] = Option.empty) extends RichBlock

/** A divider, corresponding to the HTML tag <hr/>. */
case object RichBlockDivider extends RichBlock

/** A block with an animation, corresponding to the HTML tag <video>.
  *
  * @param animation
  *   The animation
  * @param hasSpoiler
  *   Optional. True, if the media preview is covered by a spoiler animation
  * @param caption
  *   Optional. Caption of the block
  */
final case class RichBlockAnimation(
  animation: Animation,
  hasSpoiler: Option[Boolean] = Option.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A section heading, corresponding to the HTML tags &lt;h1&gt;, &lt;h2&gt;, &lt;h3&gt;, &lt;h4&gt;, &lt;h5&gt;, or &lt;h6&gt;.
  *
  * @param text
  *   Text of the block
  * @param size
  *   Relative size of the text font; 1-6, 1 is the largest, 6 is the smallest
  */
final case class RichBlockSectionHeading(text: iozhik.OpenEnum[RichText], size: Int) extends RichBlock

/** A block with a voice note, corresponding to the HTML tag <audio>.
  *
  * @param voiceNote
  *   The voice note
  * @param caption
  *   Optional. Caption of the block
  */
final case class RichBlockVoiceNote(voiceNote: Voice, caption: Option[RichBlockCaption] = Option.empty)
    extends RichBlock
