package telegramium.bots.client

/** @param chatJoinRequestQueryId
  *   Unique identifier of the join request query
  * @param webAppUrl
  *   An HTTPS URL of a Web App to be opened with additional data as specified in Initializing Web Apps
  */
final case class SendChatJoinRequestWebAppReq(chatJoinRequestQueryId: String, webAppUrl: String)
