package telegramium.bots

/** Caption of a rich formatted block.
  *
  * @param text
  *   Block caption
  * @param credit
  *   Optional. Block credit which corresponds to the HTML tag <cite>
  */
final case class RichBlockCaption(
  text: iozhik.OpenEnum[RichText],
  credit: Option[iozhik.OpenEnum[RichText]] = Option.empty
)
