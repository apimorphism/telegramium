package telegramium.bots

/** Represents the rights of a business bot.
  *
  * @param canReply
  *   <em>Optional</em>. <em>True</em>, if the bot can send and edit messages in the private chats that had incoming
  *   messages in the last 24 hours
  * @param canReadMessages
  *   <em>Optional</em>. <em>True</em>, if the bot can mark incoming private messages as read
  * @param canDeleteSentMessages
  *   <em>Optional</em>. <em>True</em>, if the bot can delete messages sent by the bot
  * @param canDeleteAllMessages
  *   <em>Optional</em>. <em>True</em>, if the bot can delete all private messages in managed chats
  * @param canEditName
  *   <em>Optional</em>. <em>True</em>, if the bot can edit the first and last name of the business account
  * @param canEditBio
  *   <em>Optional</em>. <em>True</em>, if the bot can edit the bio of the business account
  * @param canEditProfilePhoto
  *   <em>Optional</em>. <em>True</em>, if the bot can edit the profile photo of the business account
  * @param canEditUsername
  *   <em>Optional</em>. <em>True</em>, if the bot can edit the username of the business account
  * @param canChangeGiftSettings
  *   <em>Optional</em>. <em>True</em>, if the bot can change the privacy settings pertaining to gifts for the business
  *   account
  * @param canViewGiftsAndStars
  *   <em>Optional</em>. <em>True</em>, if the bot can view gifts and the amount of Telegram Stars owned by the business
  *   account
  * @param canConvertGiftsToStars
  *   <em>Optional</em>. <em>True</em>, if the bot can convert regular gifts owned by the business account to Telegram
  *   Stars
  * @param canTransferAndUpgradeGifts
  *   <em>Optional</em>. <em>True</em>, if the bot can transfer and upgrade gifts owned by the business account
  * @param canTransferStars
  *   <em>Optional</em>. <em>True</em>, if the bot can transfer Telegram Stars received by the business account to its
  *   own account, or use them to upgrade and transfer gifts
  * @param canManageStories
  *   <em>Optional</em>. <em>True</em>, if the bot can post, edit and delete stories on behalf of the business account
  */
final case class BusinessBotRights(
  canReply: Option[Boolean] = Option.empty,
  canReadMessages: Option[Boolean] = Option.empty,
  canDeleteSentMessages: Option[Boolean] = Option.empty,
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
