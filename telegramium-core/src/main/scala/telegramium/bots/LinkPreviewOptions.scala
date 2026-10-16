package telegramium.bots

/** Describes the options used for link preview generation.
  *
  * @param isDisabled
  *   <em>Optional</em>. <em>True</em>, if the link preview is disabled
  * @param url
  *   <em>Optional</em>. URL to use for the link preview. If empty, then the first URL found in the message text will be
  *   used.
  * @param preferSmallMedia
  *   <em>Optional</em>. <em>True</em>, if the media in the link preview is supposed to be shrunk; ignored if the URL
  *   isn't explicitly specified or media size change isn't supported for the preview
  * @param preferLargeMedia
  *   <em>Optional</em>. <em>True</em>, if the media in the link preview is supposed to be enlarged; ignored if the URL
  *   isn't explicitly specified or media size change isn't supported for the preview
  * @param showAboveText
  *   <em>Optional</em>. <em>True</em>, if the link preview must be shown above the message text; otherwise, the link
  *   preview will be shown below the message text
  */
final case class LinkPreviewOptions(
  isDisabled: Option[Boolean] = Option.empty,
  url: Option[String] = Option.empty,
  preferSmallMedia: Option[Boolean] = Option.empty,
  preferLargeMedia: Option[Boolean] = Option.empty,
  showAboveText: Option[Boolean] = Option.empty
)
