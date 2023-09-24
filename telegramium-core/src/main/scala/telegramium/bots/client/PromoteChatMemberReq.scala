package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param userId
  *   Unique identifier of the target user
  * @param isAnonymous
  *   Pass True if the administrator's presence in the chat is hidden
  * @param canManageChat
  *   Pass True if the administrator can access the chat event log, chat statistics, boost list in channels, message
  *   statistics in channels, see channel members, see anonymous administrators in supergroups and ignore slow mode.
  *   Implied by any other administrator privilege
  * @param canPostMessages
  *   Pass True if the administrator can post messages in the channel; channels only
  * @param canEditMessages
  *   Pass True if the administrator can edit messages of other users and can pin messages; channels only
  * @param canDeleteMessages
  *   Pass True if the administrator can delete messages of other users
  * @param canPostStories
  *   Pass True if the administrator can post stories in the channel; channels only
  * @param canEditStories
  *   Pass True if the administrator can edit stories posted by other users; channels only
  * @param canDeleteStories
  *   Pass True if the administrator can delete stories posted by other users; channels only
  * @param canManageVideoChats
  *   Pass True if the administrator can manage video chats
  * @param canRestrictMembers
  *   Pass True if the administrator can restrict, ban or unban chat members
  * @param canPromoteMembers
  *   Pass True if the administrator can add new administrators with a subset of their own privileges or demote
  *   administrators that they have promoted, directly or indirectly (promoted by administrators that were appointed by
  *   him)
  * @param canChangeInfo
  *   Pass True if the administrator can change chat title, photo and other settings
  * @param canInviteUsers
  *   Pass True if the administrator can invite new users to the chat
  * @param canPinMessages
  *   Pass True if the administrator can pin messages, supergroups only
  * @param canManageTopics
  *   Pass True if the user is allowed to create, rename, close, and reopen forum topics, supergroups only
  */
final case class PromoteChatMemberReq(
  chatId: ChatId,
  userId: Long,
  isAnonymous: Option[Boolean] = Option.empty,
  canManageChat: Option[Boolean] = Option.empty,
  canPostMessages: Option[Boolean] = Option.empty,
  canEditMessages: Option[Boolean] = Option.empty,
  canDeleteMessages: Option[Boolean] = Option.empty,
  canPostStories: Option[Boolean] = Option.empty,
  canEditStories: Option[Boolean] = Option.empty,
  canDeleteStories: Option[Boolean] = Option.empty,
  canManageVideoChats: Option[Boolean] = Option.empty,
  canRestrictMembers: Option[Boolean] = Option.empty,
  canPromoteMembers: Option[Boolean] = Option.empty,
  canChangeInfo: Option[Boolean] = Option.empty,
  canInviteUsers: Option[Boolean] = Option.empty,
  canPinMessages: Option[Boolean] = Option.empty,
  canManageTopics: Option[Boolean] = Option.empty
)
