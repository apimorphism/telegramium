package telegramium.bots.client

import telegramium.bots.IFile

final case class SetWebhookReq(
                               /** HTTPS url to send updates to. Use an empty string to remove
                                 * webhook integration*/
                               url: String,
                               /** Upload your public key certificate so that the root
                                 * certificate in use can be checked. See our self-signed guide
                                 * for details.*/
                               certificate: Option[IFile] = Option.empty,
                               /** The fixed IP address which will be used to send webhook
                                 * requests instead of the IP address resolved through DNS*/
                               ipAddress: Option[String] = Option.empty,
                               /** Maximum allowed number of simultaneous HTTPS connections to
                                 * the webhook for update delivery, 1-100. Defaults to 40. Use
                                 * lower values to limit the load on your bot's server, and
                                 * higher values to increase your bot's throughput.*/
                               maxConnections: Option[Int] = Option.empty,
                               /** A JSON-serialized list of the update types you want your
                                 * bot to receive. For example, specify [“message”,
                                 * “edited_channel_post”, “callback_query”] to only receive
                                 * updates of these types. See Update for a complete list of
                                 * available update types. Specify an empty list to receive all
                                 * updates regardless of type (default). If not specified, the
                                 * previous setting will be used. Please note that this
                                 * parameter doesn't affect updates created before the call to
                                 * the setWebhook, so unwanted updates may be received for a
                                 * short period of time.*/
                               allowedUpdates: List[String] = List.empty,
                               /** Pass True to drop all pending updates*/
                               dropPendingUpdates: Option[Boolean] = Option.empty)
