package telegramium.bots.client

/**
 * @param dropPendingUpdates Pass True to drop all pending updates
 */
final case class DeleteWebhookReq(dropPendingUpdates: Option[Boolean] = Option.empty)
