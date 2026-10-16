package telegramium.bots

sealed trait ChatMember {}

/** Represents a <a href="https://core.telegram.org/bots/api#chatmember">chat member</a> that owns the chat and has all
  * administrator privileges.
  *
  * @param user
  *   Information about the user
  * @param isAnonymous
  *   <em>True</em>, if the user's presence in the chat is hidden
  * @param customTitle
  *   <em>Optional</em>. Custom title for this user
  */
final case class ChatMemberOwner(user: User, isAnonymous: Boolean, customTitle: Option[String] = Option.empty)
    extends ChatMember

/** Represents a <a href="https://core.telegram.org/bots/api#chatmember">chat member</a> that isn't currently a member
  * of the chat, but may join it themselves.
  *
  * @param user
  *   Information about the user
  */
final case class ChatMemberLeft(user: User) extends ChatMember

/** Represents a <a href="https://core.telegram.org/bots/api#chatmember">chat member</a> that has some additional
  * privileges.
  *
  * @param user
  *   Information about the user
  * @param canBeEdited
  *   <em>True</em>, if the bot is allowed to edit administrator privileges of that user
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
  * @param customTitle
  *   <em>Optional</em>. Custom title for this user
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
  canSendWelcomeMessages: Boolean,
  canPostMessages: Option[Boolean] = Option.empty,
  canEditMessages: Option[Boolean] = Option.empty,
  canPinMessages: Option[Boolean] = Option.empty,
  canManageTopics: Option[Boolean] = Option.empty,
  canManageDirectMessages: Option[Boolean] = Option.empty,
  canManageTags: Option[Boolean] = Option.empty,
  customTitle: Option[String] = Option.empty
) extends ChatMember

/** Represents a <a href="https://core.telegram.org/bots/api#chatmember">chat member</a> that has no additional
  * privileges or restrictions.
  *
  * @param user
  *   Information about the user
  * @param tag
  *   <em>Optional</em>. Tag of the member
  * @param untilDate
  *   <em>Optional</em>. Date when the user's subscription will expire; Unix time
  */
final case class ChatMemberMember(
  user: User,
  tag: Option[String] = Option.empty,
  untilDate: Option[Long] = Option.empty
) extends ChatMember

/** Represents a <a href="https://core.telegram.org/bots/api#chatmember">chat member</a> that was banned in the chat and
  * can't return to the chat or view chat messages.
  *
  * @param user
  *   Information about the user
  * @param untilDate
  *   Date when restrictions will be lifted for this user; Unix time. If 0, then the user is banned forever.
  */
final case class ChatMemberBanned(user: User, untilDate: Long) extends ChatMember

/** Represents a <a href="https://core.telegram.org/bots/api#chatmember">chat member</a> that is under certain
  * restrictions in the chat. Supergroups only.
  *
  * @param user
  *   Information about the user
  * @param isMember
  *   <em>True</em>, if the user is a member of the chat at the moment of the request
  * @param canSendMessages
  *   <em>True</em>, if the user is allowed to send text messages, rich messages, contacts, giveaways, giveaway winners,
  *   invoices, locations and venues
  * @param canSendAudios
  *   <em>True</em>, if the user is allowed to send audios
  * @param canSendDocuments
  *   <em>True</em>, if the user is allowed to send documents
  * @param canSendPhotos
  *   <em>True</em>, if the user is allowed to send photos
  * @param canSendVideos
  *   <em>True</em>, if the user is allowed to send videos
  * @param canSendVideoNotes
  *   <em>True</em>, if the user is allowed to send video notes
  * @param canSendVoiceNotes
  *   <em>True</em>, if the user is allowed to send voice notes
  * @param canSendPolls
  *   <em>True</em>, if the user is allowed to send polls and checklists
  * @param canSendOtherMessages
  *   <em>True</em>, if the user is allowed to send animations, games, stickers and use inline bots
  * @param canAddWebPagePreviews
  *   <em>True</em>, if the user is allowed to add web page previews to their messages
  * @param canReactToMessages
  *   <em>True</em>, if the user is allowed to react to messages
  * @param canEditTag
  *   <em>True</em>, if the user is allowed to edit their own tag
  * @param canChangeInfo
  *   <em>True</em>, if the user is allowed to change the chat title, photo and other settings
  * @param canInviteUsers
  *   <em>True</em>, if the user is allowed to invite new users to the chat
  * @param canPinMessages
  *   <em>True</em>, if the user is allowed to pin messages
  * @param canManageTopics
  *   <em>True</em>, if the user is allowed to create forum topics
  * @param untilDate
  *   Date when restrictions will be lifted for this user; Unix time. If 0, then the user is restricted forever.
  * @param tag
  *   <em>Optional</em>. Tag of the member
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
  canReactToMessages: Boolean,
  canEditTag: Boolean,
  canChangeInfo: Boolean,
  canInviteUsers: Boolean,
  canPinMessages: Boolean,
  canManageTopics: Boolean,
  untilDate: Long,
  tag: Option[String] = Option.empty
) extends ChatMember
