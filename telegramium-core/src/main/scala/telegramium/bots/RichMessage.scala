package telegramium.bots

/** Rich formatted message.
  *
  * @param blocks
  *   Content of the message
  * @param isRtl
  *   Optional. True, if the rich message must be shown right-to-left
  */
final case class RichMessage(
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  isRtl: Option[Boolean] = Option.empty
)
