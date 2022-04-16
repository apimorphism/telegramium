package telegramium.bots.client

/** @param forChannels
  *   Pass True to get default administrator rights of the bot in channels. Otherwise, default administrator rights of
  *   the bot for groups and supergroups will be returned.
  */
final case class GetMyDefaultAdministratorRightsReq(forChannels: Option[Boolean] = Option.empty)
