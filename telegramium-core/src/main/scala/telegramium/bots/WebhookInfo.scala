package telegramium.bots

/** Contains information about the current status of a webhook.*/
final case class WebhookInfo(
                             /** Webhook URL, may be empty if webhook is not set up*/
                             url: String,
                             /** True, if a custom certificate was provided for webhook
                               * certificate checks*/
                             hasCustomCertificate: Boolean,
                             /** Number of updates awaiting delivery*/
                             pendingUpdateCount: Int,
                             /** Optional. Unix time for the most recent error that happened
                               * when trying to deliver an update via webhook*/
                             lastErrorDate: Option[Int] = Option.empty,
                             /** Optional. Error message in human-readable format for the
                               * most recent error that happened when trying to deliver an
                               * update via webhook*/
                             lastErrorMessage: Option[String] = Option.empty,
                             /** Optional. Maximum allowed number of simultaneous HTTPS
                               * connections to the webhook for update delivery*/
                             maxConnections: Option[Int] = Option.empty,
                             /** Optional. A list of update types the bot is subscribed to.
                               * Defaults to all update types*/
                             allowedUpdates: List[String] = List.empty)
