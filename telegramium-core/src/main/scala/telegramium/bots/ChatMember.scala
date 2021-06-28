package telegramium.bots

sealed trait ChatMember {}

/** Represents a chat member that owns the chat and has all administrator privileges.
  *
  * @param status
  *   The member's status in the chat, always “creator”
  * @param user
  *   Information about the user
  * @param customTitle
  *   Custom title for this user
  * @param isAnonymous
  *   True, if the user's presence in the chat is hidden
  */
final case class ChatMemberOwner(status: String, user: User, customTitle: String, isAnonymous: Boolean)
    extends ChatMember

/** Represents a chat member that has some additional privileges.
  *
  * @param status
  *   The member's status in the chat, always “administrator”
  * @param user
  *   Information about the user
  * @param canBeEdited
  *   True, if the bot is allowed to edit administrator privileges of that user
  * @param customTitle
  *   Custom title for this user
  * @param isAnonymous
  *   True, if the user's presence in the chat is hidden
  * @param canManageChat
  *   True, if the administrator can access the chat event log, chat statistics, message statistics in channels, see
  *   channel members, see anonymous administrators in supergroups and ignore slow mode. Implied by any other
  *   administrator privilege
  * @param canPostMessages
  *   True, if the administrator can post in the channel; channels only
  * @param canEditMessages
  *   True, if the administrator can edit messages of other users and can pin messages; channels only
  * @param canDeleteMessages
  *   True, if the administrator can delete messages of other users
  * @param canManageVoiceChats
  *   True, if the administrator can manage voice chats
  * @param canRestrictMembers
  *   True, if the administrator can restrict, ban or unban chat members
  * @param canPromoteMembers
  *   True, if the administrator can add new administrators with a subset of their own privileges or demote
  *   administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by the
  *   user)
  * @param canChangeInfo
  *   True, if the user is allowed to change the chat title, photo and other settings
  * @param canInviteUsers
  *   True, if the user is allowed to invite new users to the chat
  * @param canPinMessages
  *   True, if the user is allowed to pin messages; groups and supergroups only
  */
final case class ChatMemberAdministrator(
  status: String,
  user: User,
  canBeEdited: Boolean,
  customTitle: String,
  isAnonymous: Boolean,
  canManageChat: Boolean,
  canPostMessages: Boolean,
  canEditMessages: Boolean,
  canDeleteMessages: Boolean,
  canManageVoiceChats: Boolean,
  canRestrictMembers: Boolean,
  canPromoteMembers: Boolean,
  canChangeInfo: Boolean,
  canInviteUsers: Boolean,
  canPinMessages: Boolean
) extends ChatMember

/** Represents a chat member that isn't currently a member of the chat, but may join it themselves.
  *
  * @param status
  *   The member's status in the chat, always “left”
  * @param user
  *   Information about the user
  */
final case class ChatMemberLeft(status: String, user: User) extends ChatMember

/** Represents a chat member that has no additional privileges or restrictions.
  *
  * @param status
  *   The member's status in the chat, always “member”
  * @param user
  *   Information about the user
  */
final case class ChatMemberMember(status: String, user: User) extends ChatMember

/** Represents a chat member that was banned in the chat and can't return to the chat or view chat messages.
  *
  * @param status
  *   The member's status in the chat, always “kicked”
  * @param user
  *   Information about the user
  * @param untilDate
  *   Date when restrictions will be lifted for this user; unix time
  */
final case class ChatMemberBanned(status: String, user: User, untilDate: Int) extends ChatMember

/** Represents a chat member that is under certain restrictions in the chat. Supergroups only.
  *
  * @param status
  *   The member's status in the chat, always “restricted”
  * @param user
  *   Information about the user
  * @param isMember
  *   True, if the user is a member of the chat at the moment of the request
  * @param canChangeInfo
  *   True, if the user is allowed to change the chat title, photo and other settings
  * @param canInviteUsers
  *   True, if the user is allowed to invite new users to the chat
  * @param canPinMessages
  *   True, if the user is allowed to pin messages; groups and supergroups only
  * @param canSendMessages
  *   True, if the user is allowed to send text messages, contacts, locations and venues
  * @param canSendMediaMessages
  *   True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes
  * @param canSendPolls
  *   True, if the user is allowed to send polls
  * @param canSendOtherMessages
  *   True, if the user is allowed to send animations, games, stickers and use inline bots
  * @param canAddWebPagePreviews
  *   True, if the user is allowed to add web page previews to their messages
  * @param untilDate
  *   Date when restrictions will be lifted for this user; unix time
  */
final case class ChatMemberRestricted(
  status: String,
  user: User,
  isMember: Boolean,
  canChangeInfo: Boolean,
  canInviteUsers: Boolean,
  canPinMessages: Boolean,
  canSendMessages: Boolean,
  canSendMediaMessages: Boolean,
  canSendPolls: Boolean,
  canSendOtherMessages: Boolean,
  canAddWebPagePreviews: Boolean,
  untilDate: Int
) extends ChatMember
