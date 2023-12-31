package telegramium.bots

/** Represents the rights of an administrator in a chat.
  *
  * @param isAnonymous
  *   True, if the user's presence in the chat is hidden
  * @param canManageChat
  *   True, if the administrator can access the chat event log, boost list in channels, see channel members, report spam
  *   messages, see anonymous administrators in supergroups and ignore slow mode. Implied by any other administrator
  *   privilege
  * @param canDeleteMessages
  *   True, if the administrator can delete messages of other users
  * @param canManageVideoChats
  *   True, if the administrator can manage video chats
  * @param canRestrictMembers
  *   True, if the administrator can restrict, ban or unban chat members, or access supergroup statistics
  * @param canPromoteMembers
  *   True, if the administrator can add new administrators with a subset of their own privileges or demote
  *   administrators that they have promoted, directly or indirectly (promoted by administrators that were appointed by
  *   the user)
  * @param canChangeInfo
  *   True, if the user is allowed to change the chat title, photo and other settings
  * @param canInviteUsers
  *   True, if the user is allowed to invite new users to the chat
  * @param canPostMessages
  *   Optional. True, if the administrator can post messages in the channel, or access channel statistics; channels only
  * @param canEditMessages
  *   Optional. True, if the administrator can edit messages of other users and can pin messages; channels only
  * @param canPinMessages
  *   Optional. True, if the user is allowed to pin messages; groups and supergroups only
  * @param canPostStories
  *   Optional. True, if the administrator can post stories in the channel; channels only
  * @param canEditStories
  *   Optional. True, if the administrator can edit stories posted by other users; channels only
  * @param canDeleteStories
  *   Optional. True, if the administrator can delete stories posted by other users; channels only
  * @param canManageTopics
  *   Optional. True, if the user is allowed to create, rename, close, and reopen forum topics; supergroups only
  */
final case class ChatAdministratorRights(
  isAnonymous: Boolean,
  canManageChat: Boolean,
  canDeleteMessages: Boolean,
  canManageVideoChats: Boolean,
  canRestrictMembers: Boolean,
  canPromoteMembers: Boolean,
  canChangeInfo: Boolean,
  canInviteUsers: Boolean,
  canPostMessages: Option[Boolean] = Option.empty,
  canEditMessages: Option[Boolean] = Option.empty,
  canPinMessages: Option[Boolean] = Option.empty,
  canPostStories: Option[Boolean] = Option.empty,
  canEditStories: Option[Boolean] = Option.empty,
  canDeleteStories: Option[Boolean] = Option.empty,
  canManageTopics: Option[Boolean] = Option.empty
)
