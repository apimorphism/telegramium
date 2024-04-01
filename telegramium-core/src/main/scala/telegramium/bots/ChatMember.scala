package telegramium.bots

sealed trait ChatMember {}

/** Represents a chat member that owns the chat and has all administrator privileges.
  *
  * @param user
  *   Information about the user
  * @param isAnonymous
  *   True, if the user's presence in the chat is hidden
  * @param customTitle
  *   Optional. Custom title for this user
  */
final case class ChatMemberOwner(user: User, isAnonymous: Boolean, customTitle: Option[String] = Option.empty)
    extends ChatMember

/** Represents a chat member that has some additional privileges.
  *
  * @param user
  *   Information about the user
  * @param canBeEdited
  *   True, if the bot is allowed to edit administrator privileges of that user
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
  *   True, if the administrator can edit stories posted by other users
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
  * @param customTitle
  *   Optional. Custom title for this user
  */
final case class ChatMemberAdministrator(
  user: User,
  canBeEdited: Boolean,
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
  canManageTopics: Option[Boolean] = Option.empty,
  customTitle: Option[String] = Option.empty
) extends ChatMember

/** Represents a chat member that isn't currently a member of the chat, but may join it themselves.
  *
  * @param user
  *   Information about the user
  */
final case class ChatMemberLeft(user: User) extends ChatMember

/** Represents a chat member that has no additional privileges or restrictions.
  *
  * @param user
  *   Information about the user
  */
final case class ChatMemberMember(user: User) extends ChatMember

/** Represents a chat member that was banned in the chat and can't return to the chat or view chat messages.
  *
  * @param user
  *   Information about the user
  * @param untilDate
  *   Date when restrictions will be lifted for this user; Unix time. If 0, then the user is banned forever
  */
final case class ChatMemberBanned(user: User, untilDate: Int) extends ChatMember

/** Represents a chat member that is under certain restrictions in the chat. Supergroups only.
  *
  * @param user
  *   Information about the user
  * @param isMember
  *   True, if the user is a member of the chat at the moment of the request
  * @param canSendMessages
  *   True, if the user is allowed to send text messages, contacts, giveaways, giveaway winners, invoices, locations and
  *   venues
  * @param canSendAudios
  *   True, if the user is allowed to send audios
  * @param canSendDocuments
  *   True, if the user is allowed to send documents
  * @param canSendPhotos
  *   True, if the user is allowed to send photos
  * @param canSendVideos
  *   True, if the user is allowed to send videos
  * @param canSendVideoNotes
  *   True, if the user is allowed to send video notes
  * @param canSendVoiceNotes
  *   True, if the user is allowed to send voice notes
  * @param canSendPolls
  *   True, if the user is allowed to send polls
  * @param canSendOtherMessages
  *   True, if the user is allowed to send animations, games, stickers and use inline bots
  * @param canAddWebPagePreviews
  *   True, if the user is allowed to add web page previews to their messages
  * @param canChangeInfo
  *   True, if the user is allowed to change the chat title, photo and other settings
  * @param canInviteUsers
  *   True, if the user is allowed to invite new users to the chat
  * @param canPinMessages
  *   True, if the user is allowed to pin messages
  * @param canManageTopics
  *   True, if the user is allowed to create forum topics
  * @param untilDate
  *   Date when restrictions will be lifted for this user; Unix time. If 0, then the user is restricted forever
  */
final case class ChatMemberRestricted(
  user: User,
  isMember: Boolean,
  canSendMessages: Boolean,
  canSendAudios: Boolean,
  canSendDocuments: Boolean,
  canSendPhotos: Boolean,
  canSendVideos: Boolean,
  canSendVideoNotes: Boolean,
  canSendVoiceNotes: Boolean,
  canSendPolls: Boolean,
  canSendOtherMessages: Boolean,
  canAddWebPagePreviews: Boolean,
  canChangeInfo: Boolean,
  canInviteUsers: Boolean,
  canPinMessages: Boolean,
  canManageTopics: Boolean,
  untilDate: Int
) extends ChatMember
