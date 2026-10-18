package telegramium.bots

sealed trait InputRichBlock {}

/** A table, corresponding to the HTML tag <code>ᐸtableᐳ</code>.
  *
  * @param cells
  *   Cells of the table
  * @param isBordered
  *   <em>Optional</em>. Pass <em>True</em> if the table has borders
  * @param isStriped
  *   <em>Optional</em>. Pass <em>True</em> if the table is striped
  * @param isCompact
  *   <em>Optional</em>. Pass <em>True</em> if table cells must have smaller indents
  * @param caption
  *   <em>Optional</em>. Caption of the table
  */
final case class InputRichBlockTable(
  cells: List[List[RichBlockTableCell]] = List.empty,
  isBordered: Option[Boolean] = Option.empty,
  isStriped: Option[Boolean] = Option.empty,
  isCompact: Option[Boolean] = Option.empty,
  caption: Option[RichText] = Option.empty
) extends InputRichBlock

/** A block with an anchor, corresponding to the HTML tag <code>ᐸaᐳ</code> with the attribute <code>name</code>.
  *
  * @param name
  *   The name of the anchor
  */
final case class InputRichBlockAnchor(name: String) extends InputRichBlock

/** A block with a voice note, corresponding to the HTML tag <code>ᐸaudioᐳ</code>.
  *
  * @param voiceNote
  *   The voice note. Caption is ignored.
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockVoiceNote(
  voiceNote: InputMediaVoiceNote,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A list of blocks, corresponding to the HTML tag <code>ᐸulᐳ</code> or <code>ᐸolᐳ</code> with multiple nested tags
  * <code>ᐸliᐳ</code>.
  *
  * @param items
  *   Items of the list
  */
final case class InputRichBlockList(items: List[InputRichBlockListItem] = List.empty) extends InputRichBlock

/** A text paragraph, corresponding to the HTML tag <code>ᐸpᐳ</code>.
  *
  * @param text
  *   Text of the block
  */
final case class InputRichBlockParagraph(text: RichText) extends InputRichBlock

/** A preformatted text block, corresponding to the nested HTML tags <code>ᐸpreᐳ</code> and <code>ᐸcodeᐳ</code>.
  *
  * @param text
  *   Text of the block
  * @param language
  *   <em>Optional</em>. The programming language of the text
  */
final case class InputRichBlockPreformatted(text: RichText, language: Option[String] = Option.empty)
    extends InputRichBlock

/** A block with a music file, corresponding to the HTML tag <code>ᐸaudioᐳ</code>.
  *
  * @param audio
  *   The audio. Caption is ignored.
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockAudio(audio: InputMediaAudio, caption: Option[RichBlockCaption] = Option.empty)
    extends InputRichBlock

/** A block quotation, corresponding to the HTML tag <code>ᐸblockquoteᐳ</code> with custom attribute
  * <code>"expandable"</code>.
  *
  * @param text
  *   Content of the block
  * @param credit
  *   <em>Optional</em>. Credit of the block
  */
final case class InputRichBlockExpandableBlockQuotation(text: RichText, credit: Option[RichText] = Option.empty)
    extends InputRichBlock

/** A collage, corresponding to the custom HTML tag <code>ᐸtg-collageᐳ</code>.
  *
  * @param blocks
  *   Elements of the collage
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockCollage(
  blocks: List[InputRichBlock] = List.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A footer, corresponding to the HTML tag <code>ᐸfooterᐳ</code>.
  *
  * @param text
  *   Text of the block
  */
final case class InputRichBlockFooter(text: RichText) extends InputRichBlock

/** A block with a general file, corresponding to the custom HTML tag <code>ᐸtg-documentᐳ</code>.
  *
  * @param document
  *   The document. Caption is ignored.
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockDocument(document: InputMediaDocument, caption: Option[RichBlockCaption] = Option.empty)
    extends InputRichBlock

/** A block with a mathematical expression in LaTeX format, corresponding to the custom HTML tag
  * <code>ᐸtg-math-blockᐳ</code>.
  *
  * @param expression
  *   The mathematical expression in LaTeX format
  */
final case class InputRichBlockMathematicalExpression(expression: String) extends InputRichBlock

/** A divider, corresponding to the HTML tag <code>ᐸhr/ᐳ</code>. */
case object InputRichBlockDivider extends InputRichBlock

/** A block quotation, corresponding to the HTML tag <code>ᐸblockquoteᐳ</code>.
  *
  * @param blocks
  *   Content of the block
  * @param credit
  *   <em>Optional</em>. Credit of the block
  */
final case class InputRichBlockBlockQuotation(
  blocks: List[InputRichBlock] = List.empty,
  credit: Option[RichText] = Option.empty
) extends InputRichBlock

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
final case class InputRichBlockThinking(text: RichText) extends InputRichBlock

/** A block with a map, corresponding to the custom HTML tag <code>ᐸtg-mapᐳ</code>. The map's width and height must not
  * exceed 10000 in total. The width and height ratio must be at most 20.
  *
  * @param location
  *   Location of the center of the map
  * @param zoom
  *   <em>Optional</em>. Map zoom level; 0-24
  * @param width
  *   <em>Optional</em>. Map width; 0-10000
  * @param height
  *   <em>Optional</em>. Map height; 0-10000
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockMap(
  location: Location,
  zoom: Option[Int] = Option.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A slideshow, corresponding to the custom HTML tag <code>ᐸtg-slideshowᐳ</code>.
  *
  * @param blocks
  *   Elements of the slideshow
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockSlideshow(
  blocks: List[InputRichBlock] = List.empty,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A block with a photo, corresponding to the HTML tag <code>ᐸimgᐳ</code>.
  *
  * @param photo
  *   The photo. Caption is ignored.
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockPhoto(photo: InputMediaPhoto, caption: Option[RichBlockCaption] = Option.empty)
    extends InputRichBlock

/** A block with an animation, corresponding to the HTML tag <code>ᐸvideoᐳ</code>.
  *
  * @param animation
  *   The animation. Caption is ignored.
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockAnimation(
  animation: InputMediaAnimation,
  caption: Option[RichBlockCaption] = Option.empty
) extends InputRichBlock

/** A section heading, corresponding to the HTML tags <code>ᐸh1ᐳ</code>, <code>ᐸh2ᐳ</code>, <code>ᐸh3ᐳ</code>,
  * <code>ᐸh4ᐳ</code>, <code>ᐸh5ᐳ</code>, or <code>ᐸh6ᐳ</code>.
  *
  * @param text
  *   Text of the block
  * @param size
  *   Relative size of the text font; 1-6, 1 is the largest, 6 is the smallest
  */
final case class InputRichBlockSectionHeading(text: RichText, size: Int) extends InputRichBlock

/** An expandable block for details disclosure, corresponding to the HTML tag <code>ᐸdetailsᐳ</code>.
  *
  * @param summary
  *   Always shown summary of the block
  * @param blocks
  *   Content of the block
  * @param isOpen
  *   <em>Optional</em>. Pass <em>True</em> if the content of the block is visible by default
  */
final case class InputRichBlockDetails(
  summary: RichText,
  blocks: List[InputRichBlock] = List.empty,
  isOpen: Option[Boolean] = Option.empty
) extends InputRichBlock

/** A block containing a list of buttons that are shown in one row, corresponding to the custom HTML tag
  * <code>ᐸtg-button-rowᐳ</code>.
  *
  * @param buttons
  *   List of 1-8 buttons to send
  * @param align
  *   <em>Optional</em>. Horizontal alignment of the buttons. Currently, must be one of “left”, “center”, or “right”.
  */
final case class InputRichBlockButtons(
  buttons: List[RichMessageButton] = List.empty,
  align: Option[String] = Option.empty
) extends InputRichBlock

/** A block with a video, corresponding to the HTML tag <code>ᐸvideoᐳ</code>.
  *
  * @param video
  *   The video. Caption is ignored.
  * @param caption
  *   <em>Optional</em>. Caption of the block
  */
final case class InputRichBlockVideo(video: InputMediaVideo, caption: Option[RichBlockCaption] = Option.empty)
    extends InputRichBlock

/** A quotation with centered text, loosely corresponding to the HTML tag <code>ᐸasideᐳ</code>.
  *
  * @param text
  *   Text of the block
  * @param credit
  *   <em>Optional</em>. Credit of the block
  */
final case class InputRichBlockPullQuotation(text: RichText, credit: Option[RichText] = Option.empty)
    extends InputRichBlock
