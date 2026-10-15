package telegramium.bots

/** Describes the current status of a webhook.
  *
  * @param url
  *   Webhook URL, may be empty if webhook is not set up
  * @param hasCustomCertificate
  *   <em>True</em>, if a custom certificate was provided for webhook certificate checks
  * @param pendingUpdateCount
  *   Number of updates awaiting delivery
  * @param ipAddress
  *   <em>Optional</em>. Currently used webhook IP address
  * @param lastErrorDate
  *   <em>Optional</em>. Unix time for the most recent error that happened when trying to deliver an update via webhook
  * @param lastErrorMessage
  *   <em>Optional</em>. Error message in human-readable format for the most recent error that happened when trying to
  *   deliver an update via webhook
  * @param lastSynchronizationErrorDate
  *   <em>Optional</em>. Unix time of the most recent error that happened when trying to synchronize available updates
  *   with Telegram datacenters
  * @param maxConnections
  *   <em>Optional</em>. The maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery
  * @param allowedUpdates
  *   <em>Optional</em>. A list of update types the bot is subscribed to. Defaults to all update types except
  *   <em>chat_member</em>, <em>message_reaction</em>, and <em>message_reaction_count</em>.
  */
final case class WebhookInfo(
  url: String,
  hasCustomCertificate: Boolean,
  pendingUpdateCount: Int,
  ipAddress: Option[String] = Option.empty,
  lastErrorDate: Option[Long] = Option.empty,
  lastErrorMessage: Option[String] = Option.empty,
  lastSynchronizationErrorDate: Option[Long] = Option.empty,
  maxConnections: Option[Int] = Option.empty,
  allowedUpdates: List[String] = List.empty
)
