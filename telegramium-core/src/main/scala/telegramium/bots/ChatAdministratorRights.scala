package telegramium.bots

/** Represents the rights of an administrator in a chat.
  *
  * @param isAnonymous
  *   True, if the user's presence in the chat is hidden
  * @param canManageChat
  *   True, if the administrator can access the chat event log, get boost list, see hidden supergroup and channel
  *   members, report spam messages and ignore slow mode. Implied by any other administrator privilege.
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
  * @param canPostStories
  *   True, if the administrator can post stories to the chat
  * @param canEditStories
  *   True, if the administrator can edit stories posted by other users, post stories to the chat page, pin chat
  *   stories, and access the chat's story archive
  * @param canDeleteStories
  *   True, if the administrator can delete stories posted by other users
  * @param canPostMessages
  *   Optional. True, if the administrator can post messages in the channel, or access channel statistics; for channels
  *   only
  * @param canEditMessages
  *   Optional. True, if the administrator can edit messages of other users and can pin messages; for channels only
  * @param canPinMessages
  *   Optional. True, if the user is allowed to pin messages; for groups and supergroups only
  * @param canManageTopics
  *   Optional. True, if the user is allowed to create, rename, close, and reopen forum topics; for supergroups only
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
  canPostStories: Boolean,
  canEditStories: Boolean,
  canDeleteStories: Boolean,
  canPostMessages: Option[Boolean] = Option.empty,
  canEditMessages: Option[Boolean] = Option.empty,
  canPinMessages: Option[Boolean] = Option.empty,
  canManageTopics: Option[Boolean] = Option.empty
)
