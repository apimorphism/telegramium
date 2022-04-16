package telegramium.bots.client

import telegramium.bots.ChatAdministratorRights

/** @param rights
  *   A JSON-serialized object describing new default administrator rights. If not specified, the default administrator
  *   rights will be cleared.
  * @param forChannels
  *   Pass True to change the default administrator rights of the bot in channels. Otherwise, the default administrator
  *   rights of the bot for groups and supergroups will be changed.
  */
final case class SetMyDefaultAdministratorRightsReq(
  rights: Option[ChatAdministratorRights] = Option.empty,
  forChannels: Option[Boolean] = Option.empty
)
