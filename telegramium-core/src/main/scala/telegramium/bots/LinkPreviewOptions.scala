package telegramium.bots

/** Describes the options used for link preview generation.
  *
  * @param isDisabled
  *   Optional. True, if the link preview is disabled
  * @param url
  *   Optional. URL to use for the link preview. If empty, then the first URL found in the message text will be used
  * @param preferSmallMedia
  *   Optional. True, if the media in the link preview is suppposed to be shrunk; ignored if the URL isn't explicitly
  *   specified or media size change isn't supported for the preview
  * @param preferLargeMedia
  *   Optional. True, if the media in the link preview is suppposed to be enlarged; ignored if the URL isn't explicitly
  *   specified or media size change isn't supported for the preview
  * @param showAboveText
  *   Optional. True, if the link preview must be shown above the message text; otherwise, the link preview will be
  *   shown below the message text
  */
final case class LinkPreviewOptions(
  isDisabled: Option[Boolean] = Option.empty,
  url: Option[String] = Option.empty,
  preferSmallMedia: Option[Boolean] = Option.empty,
  preferLargeMedia: Option[Boolean] = Option.empty,
  showAboveText: Option[Boolean] = Option.empty
)
