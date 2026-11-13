package telegramium.bots

/** Caption of a rich formatted block.
  *
  * @param text
  *   Block caption
  * @param credit
  *   <em>Optional</em>. Block credit which corresponds to the HTML tag ᐸciteᐳ
  */
final case class RichBlockCaption(
  text: iozhik.OpenUnion[RichText],
  credit: Option[iozhik.OpenUnion[RichText]] = Option.empty
)
