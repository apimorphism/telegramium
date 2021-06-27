package telegramium.bots

/** Contains information about the current status of a webhook.
  *
  * @param url
  *   Webhook URL, may be empty if webhook is not set up
  * @param hasCustomCertificate
  *   True, if a custom certificate was provided for webhook certificate checks
  * @param pendingUpdateCount
  *   Number of updates awaiting delivery
  * @param ipAddress
  *   Optional. Currently used webhook IP address
  * @param lastErrorDate
  *   Optional. Unix time for the most recent error that happened when trying to deliver an update via webhook
  * @param lastErrorMessage
  *   Optional. Error message in human-readable format for the most recent error that happened when trying to deliver an
  *   update via webhook
  * @param maxConnections
  *   Optional. Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery
  * @param allowedUpdates
  *   Optional. A list of update types the bot is subscribed to. Defaults to all update types except chat_member
  */
final case class WebhookInfo(
  url: String,
  hasCustomCertificate: Boolean,
  pendingUpdateCount: Int,
  ipAddress: Option[String] = Option.empty,
  lastErrorDate: Option[Int] = Option.empty,
  lastErrorMessage: Option[String] = Option.empty,
  maxConnections: Option[Int] = Option.empty,
  allowedUpdates: List[String] = List.empty
)
