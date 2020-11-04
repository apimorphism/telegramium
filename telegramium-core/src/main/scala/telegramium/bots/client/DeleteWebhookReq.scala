package telegramium.bots.client

final case class DeleteWebhookReq(
    /** Pass True to drop all pending updates*/
    dropPendingUpdates: Option[Boolean] = Option.empty)
