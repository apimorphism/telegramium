package telegramium.bots

/** Represents the rights of an administrator in a chat.
  *
  * @param isAnonymous
  *   <em>True</em>, if the user's presence in the chat is hidden
  * @param canManageChat
  *   <em>True</em>, if the administrator can access the chat event log, get boost list, see hidden supergroup and
  *   channel members, report spam messages, ignore slow mode, and send messages to the chat without paying Telegram
  *   Stars. Implied by any other administrator privilege.
  * @param canDeleteMessages
  *   <em>True</em>, if the administrator can delete messages of other users
  * @param canManageVideoChats
  *   <em>True</em>, if the administrator can manage video chats
  * @param canRestrictMembers
  *   <em>True</em>, if the administrator can restrict, ban or unban chat members, or access supergroup statistics
  * @param canPromoteMembers
  *   <em>True</em>, if the administrator can add new administrators with a subset of their own privileges or demote
  *   administrators that they have promoted, directly or indirectly (promoted by administrators that were appointed by
  *   the user)
  * @param canChangeInfo
  *   <em>True</em>, if the user is allowed to change the chat title, photo and other settings
  * @param canInviteUsers
  *   <em>True</em>, if the user is allowed to invite new users to the chat
  * @param canPostStories
  *   <em>True</em>, if the administrator can post stories to the chat
  * @param canEditStories
  *   <em>True</em>, if the administrator can edit stories posted by other users, post stories to the chat page, pin
  *   chat stories, and access the chat's story archive
  * @param canDeleteStories
  *   <em>True</em>, if the administrator can delete stories posted by other users
  * @param canSendWelcomeMessages
  *   <em>True</em>, if the administrator can manage chat welcome messages or directly send them in the case of bots
  * @param canPostMessages
  *   <em>Optional</em>. <em>True</em>, if the administrator can post messages in the channel, approve suggested posts,
  *   or access channel statistics; for channels only
  * @param canEditMessages
  *   <em>Optional</em>. <em>True</em>, if the administrator can edit messages of other users and can pin messages; for
  *   channels only
  * @param canPinMessages
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to pin messages; for groups and supergroups only
  * @param canManageTopics
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to create, rename, close, and reopen forum topics; for
  *   supergroups only
  * @param canManageDirectMessages
  *   <em>Optional</em>. <em>True</em>, if the administrator can manage direct messages of the channel and decline
  *   suggested posts; for channels only
  * @param canManageTags
  *   <em>Optional</em>. <em>True</em>, if the administrator can edit the tags of regular members; for groups and
  *   supergroups only
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
  canSendWelcomeMessages: Boolean,
  canPostMessages: Option[Boolean] = Option.empty,
  canEditMessages: Option[Boolean] = Option.empty,
  canPinMessages: Option[Boolean] = Option.empty,
  canManageTopics: Option[Boolean] = Option.empty,
  canManageDirectMessages: Option[Boolean] = Option.empty,
  canManageTags: Option[Boolean] = Option.empty
)
