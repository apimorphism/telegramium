package telegramium.bots.client

import telegramium.bots.IFile

/** @param url
  *   HTTPS URL to send updates to. Use an empty string to remove webhook integration
  * @param certificate
  *   Upload your public key certificate so that the root certificate in use can be checked. See our self-signed guide
  *   for details.
  * @param ipAddress
  *   The fixed IP address which will be used to send webhook requests instead of the IP address resolved through DNS
  * @param maxConnections
  *   The maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100. Defaults
  *   to 40. Use lower values to limit the load on your bot's server, and higher values to increase your bot's
  *   throughput.
  * @param allowedUpdates
  *   A JSON-serialized list of the update types you want your bot to receive. For example, specify [“message”,
  *   “edited_channel_post”, “callback_query”] to only receive updates of these types. See Update for a complete list of
  *   available update types. Specify an empty list to receive all update types except chat_member (default). If not
  *   specified, the previous setting will be used. Please note that this parameter doesn't affect updates created
  *   before the call to the setWebhook, so unwanted updates may be received for a short period of time.
  * @param dropPendingUpdates
  *   Pass True to drop all pending updates
  * @param secretToken
  *   A secret token to be sent in a header “X-Telegram-Bot-Api-Secret-Token” in every webhook request, 1-256
  *   characters. Only characters A-Z, a-z, 0-9, _ and - are allowed. The header is useful to ensure that the request
  *   comes from a webhook set by you.
  */
final case class SetWebhookReq(
  url: String,
  certificate: Option[IFile] = Option.empty,
  ipAddress: Option[String] = Option.empty,
  maxConnections: Option[Int] = Option.empty,
  allowedUpdates: List[String] = List.empty,
  dropPendingUpdates: Option[Boolean] = Option.empty,
  secretToken: Option[String] = Option.empty
)
