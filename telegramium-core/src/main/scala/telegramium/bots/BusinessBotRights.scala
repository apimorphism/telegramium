package telegramium.bots

/** Represents the rights of a business bot.
  *
  * @param canReply
  *   Optional. True, if the bot can send and edit messages in the private chats that had incoming messages in the last
  *   24 hours
  * @param canReadMessages
  *   Optional. True, if the bot can mark incoming private messages as read
  * @param canDeleteOutgoingMessages
  *   Optional. True, if the bot can delete messages sent by the bot
  * @param canDeleteAllMessages
  *   Optional. True, if the bot can delete all private messages in managed chats
  * @param canEditName
  *   Optional. True, if the bot can edit the first and last name of the business account
  * @param canEditBio
  *   Optional. True, if the bot can edit the bio of the business account
  * @param canEditProfilePhoto
  *   Optional. True, if the bot can edit the profile photo of the business account
  * @param canEditUsername
  *   Optional. True, if the bot can edit the username of the business account
  * @param canChangeGiftSettings
  *   Optional. True, if the bot can change the privacy settings pertaining to gifts for the business account
  * @param canViewGiftsAndStars
  *   Optional. True, if the bot can view gifts and the amount of Telegram Stars owned by the business account
  * @param canConvertGiftsToStars
  *   Optional. True, if the bot can convert regular gifts owned by the business account to Telegram Stars
  * @param canTransferAndUpgradeGifts
  *   Optional. True, if the bot can transfer and upgrade gifts owned by the business account
  * @param canTransferStars
  *   Optional. True, if the bot can transfer Telegram Stars received by the business account to its own account, or use
  *   them to upgrade and transfer gifts
  * @param canManageStories
  *   Optional. True, if the bot can post, edit and delete stories on behalf of the business account
  */
final case class BusinessBotRights(
  canReply: Option[Boolean] = Option.empty,
  canReadMessages: Option[Boolean] = Option.empty,
  canDeleteOutgoingMessages: Option[Boolean] = Option.empty,
  canDeleteAllMessages: Option[Boolean] = Option.empty,
  canEditName: Option[Boolean] = Option.empty,
  canEditBio: Option[Boolean] = Option.empty,
  canEditProfilePhoto: Option[Boolean] = Option.empty,
  canEditUsername: Option[Boolean] = Option.empty,
  canChangeGiftSettings: Option[Boolean] = Option.empty,
  canViewGiftsAndStars: Option[Boolean] = Option.empty,
  canConvertGiftsToStars: Option[Boolean] = Option.empty,
  canTransferAndUpgradeGifts: Option[Boolean] = Option.empty,
  canTransferStars: Option[Boolean] = Option.empty,
  canManageStories: Option[Boolean] = Option.empty
)
