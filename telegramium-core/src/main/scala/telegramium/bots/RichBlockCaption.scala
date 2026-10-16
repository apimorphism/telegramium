package telegramium.bots

/** Caption of a rich formatted block.
  *
  * @param text
  *   Block caption
  * @param credit
  *   <em>Optional</em>. Block credit which corresponds to the HTML tag &lt;cite&gt;
  */
final case class RichBlockCaption(
  text: iozhik.OpenEnum[RichText],
  credit: Option[iozhik.OpenEnum[RichText]] = Option.empty
)
