package telegramium.bots.client

/** @param dropPendingUpdates Pass <em>True</em> to drop all pending updates */
final case class DeleteWebhookReq(dropPendingUpdates: Option[Boolean] = Option.empty)
