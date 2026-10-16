package telegramium.bots

sealed trait RichBlock {}

/** A block with a video, corresponding to the HTML tag <code>ᐸvideoᐳ</code>.
  *
  * @param video
  *   The video
  * @param hasSpoiler
  *   <em>Optional</em>. <em>True</em>, if the media preview is covered by a spoiler animation
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockVideo(
  video: Video,
  hasSpoiler: Option[Boolean] = Option.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** An expandable block for details disclosure, corresponding to the HTML tag <code>ᐸdetailsᐳ</code>.
  *
  * @param summary
  *   Always shown summary of the block
  * @param blocks
  *   Content of the block
  * @param isOpen
  *   <em>Optional</em>. <em>True</em>, if the content of the block is visible by default
  */
final case class RichBlockDetails(
  summary: iozhik.OpenEnum[RichText],
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  isOpen: Option[Boolean] = Option.empty
) extends RichBlock

/** A list of blocks, corresponding to the HTML tag <code>ᐸulᐳ</code> or <code>ᐸolᐳ</code> with multiple nested tags
  * <code>ᐸliᐳ</code>.
  *
  * @param items
  *   Items of the list
  */
final case class RichBlockList(items: List[RichBlockListItem] = List.empty) extends RichBlock

/** A block with a photo, corresponding to the HTML tag <code>ᐸimgᐳ</code>.
  *
  * @param photo
  *   Available sizes of the photo
  * @param hasSpoiler
  *   <em>Optional</em>. <em>True</em>, if the media preview is covered by a spoiler animation
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockPhoto(
  photo: List[PhotoSize] = List.empty,
  hasSpoiler: Option[Boolean] = Option.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A block with a “Thinking…” placeholder, corresponding to the custom HTML tag <code>ᐸtg-thinkingᐳ</code>. The block
  * may be used only in <a href="https://core.telegram.org/bots/api#sendrichmessagedraft">sendRichMessageDraft</a>,
  * therefore it can't be received in messages. See <a href="https://t.me/addemoji/AIActions"></a><a
  * href="https://t.me/addemoji/AIActions">https://t.me/addemoji/AIActions</a> for examples of custom emoji that are
  * recommended for usage in the block.
  *
  * @param text
  *   Text of the block. See <a href="https://t.me/addemoji/AIActions"></a><a
  *   href="https://t.me/addemoji/AIActions">https://t.me/addemoji/AIActions</a> for examples of custom emoji that are
  *   recommended for usage in the block.
  */
final case class RichBlockThinking(text: iozhik.OpenEnum[RichText]) extends RichBlock

/** A footer, corresponding to the HTML tag <code>ᐸfooterᐳ</code>.
  *
  * @param text
  *   Text of the block
  */
final case class RichBlockFooter(text: iozhik.OpenEnum[RichText]) extends RichBlock

/** A block with an anchor, corresponding to the HTML tag <code>ᐸaᐳ</code> with the attribute <code>name</code>.
  *
  * @param name
  *   The name of the anchor
  */
final case class RichBlockAnchor(name: String) extends RichBlock

/** A block with a general file, corresponding to the custom HTML tag <code>ᐸtg-documentᐳ</code>.
  *
  * @param document
  *   The document
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockDocument(document: Document, caption: Option[RichBlockCaption] = Option.empty)
    extends RichBlock

/** A block with a mathematical expression in LaTeX format, corresponding to the custom HTML tag
  * <code>ᐸtg-math-blockᐳ</code>.
  *
  * @param expression
  *   The mathematical expression in LaTeX format
  */
final case class RichBlockMathematicalExpression(expression: String) extends RichBlock

/** A block quotation, corresponding to the HTML tag <code>ᐸblockquoteᐳ</code>.
  *
  * @param blocks
  *   Content of the block
  * @param credit
  *   <em>Optional</em>. Credit of the block
  */
final case class RichBlockBlockQuotation(
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  credit: Option[iozhik.OpenEnum[RichText]] = Option.empty
) extends RichBlock

/** A block with a map, corresponding to the custom HTML tag <code>ᐸtg-mapᐳ</code>.
  *
  * @param location
  *   Location of the center of the map
  * @param zoom
  *   Map zoom level
  * @param width
  *   Expected width of the map
  * @param height
  *   Expected height of the map
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockMap(
  location: Location,
  zoom: Int,
  width: Int,
  height: Int,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A slideshow, corresponding to the custom HTML tag <code>ᐸtg-slideshowᐳ</code>.
  *
  * @param blocks
  *   Elements of the slideshow
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockSlideshow(
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A text paragraph, corresponding to the HTML tag <code>ᐸpᐳ</code>.
  *
  * @param text
  *   Text of the block
  */
final case class RichBlockParagraph(text: iozhik.OpenEnum[RichText]) extends RichBlock

/** A quotation with centered text, loosely corresponding to the HTML tag <code>ᐸasideᐳ</code>.
  *
  * @param text
  *   Text of the block
  * @param credit
  *   <em>Optional</em>. Credit of the block
  */
final case class RichBlockPullQuotation(
  text: iozhik.OpenEnum[RichText],
  credit: Option[iozhik.OpenEnum[RichText]] = Option.empty
) extends RichBlock

/** A preformatted text block, corresponding to the nested HTML tags <code>ᐸpreᐳ</code> and <code>ᐸcodeᐳ</code>.
  *
  * @param text
  *   Text of the block
  * @param language
  *   <em>Optional</em>. The programming language of the text
  */
final case class RichBlockPreformatted(text: iozhik.OpenEnum[RichText], language: Option[String] = Option.empty)
    extends RichBlock

/** A table, corresponding to the HTML tag <code>ᐸtableᐳ</code>.
  *
  * @param cells
  *   Cells of the table
  * @param isBordered
  *   <em>Optional</em>. <em>True</em>, if the table has borders
  * @param isStriped
  *   <em>Optional</em>. <em>True</em>, if the table is striped
  * @param isCompact
  *   <em>Optional</em>. <em>True</em>, if table cells have smaller indents
  * @param caption
  *   <em>Optional</em>. Caption of the table
  */
final case class RichBlockTable(
  cells: List[List[RichBlockTableCell]] = List.empty,
  isBordered: Option[Boolean] = Option.empty,
  isStriped: Option[Boolean] = Option.empty,
  isCompact: Option[Boolean] = Option.empty,
  caption: Option[iozhik.OpenEnum[RichText]] = Option.empty
) extends RichBlock

/** A collage, corresponding to the custom HTML tag <code>ᐸtg-collageᐳ</code>.
  *
  * @param blocks
  *   Elements of the collage
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockCollage(
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A block with a music file, corresponding to the HTML tag <code>ᐸaudioᐳ</code>.
  *
  * @param audio
  *   The audio
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockAudio(audio: Audio, caption: Option[RichBlockCaption] = Option.empty) extends RichBlock

/** A divider, corresponding to the HTML tag <code>&lt;hr/&gt;</code>. */
case object RichBlockDivider extends RichBlock

/** A block with an animation, corresponding to the HTML tag <code>ᐸvideoᐳ</code>.
  *
  * @param animation
  *   The animation
  * @param hasSpoiler
  *   <em>Optional</em>. <em>True</em>, if the media preview is covered by a spoiler animation
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockAnimation(
  animation: Animation,
  hasSpoiler: Option[Boolean] = Option.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends RichBlock

/** A block quotation, corresponding to the HTML tag <code>ᐸblockquoteᐳ</code> with custom attribute
  * <code>"expandable"</code>.
  *
  * @param text
  *   Content of the block
  * @param credit
  *   <em>Optional</em>. Credit of the block
  */
final case class RichBlockExpandableBlockQuotation(
  text: iozhik.OpenEnum[RichText],
  credit: Option[iozhik.OpenEnum[RichText]] = Option.empty
) extends RichBlock

/** A section heading, corresponding to the HTML tags <code>ᐸh1ᐳ</code>, <code>ᐸh2ᐳ</code>, <code>ᐸh3ᐳ</code>,
  * <code>ᐸh4ᐳ</code>, <code>ᐸh5ᐳ</code>, or <code>ᐸh6ᐳ</code>.
  *
  * @param text
  *   Text of the block
  * @param size
  *   Relative size of the text font; 1-6, 1 is the largest, 6 is the smallest
  */
final case class RichBlockSectionHeading(text: iozhik.OpenEnum[RichText], size: Int) extends RichBlock

/** A block containing a list of buttons that are shown in one row, corresponding to the custom HTML tag
  * <code>ᐸtg-button-rowᐳ</code>.
  *
  * @param buttons
  *   The buttons
  * @param align
  *   <em>Optional</em>. Horizontal alignment of the buttons. Currently, must be one of “left”, “center”, or “right”.
  */
final case class RichBlockButtons(buttons: List[RichMessageButton] = List.empty, align: Option[String] = Option.empty)
    extends RichBlock

/** A block with a voice note, corresponding to the HTML tag <code>ᐸaudioᐳ</code>.
  *
  * @param voiceNote
  *   The voice note
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class RichBlockVoiceNote(voiceNote: Voice, caption: Option[RichBlockCaption] = Option.empty)
    extends RichBlock
