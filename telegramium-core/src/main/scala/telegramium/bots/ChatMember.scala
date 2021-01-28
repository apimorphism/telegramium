package telegramium.bots

/** This object contains information about one member of a chat.
  *
  * @param user Information about the user
  * @param status The member's status in the chat. Can be “creator”,
  * “administrator”, “member”, “restricted”, “left” or “kicked”
  * @param customTitle Optional. Owner and administrators only. Custom title for
  * this user
  * @param isAnonymous Optional. Owner and administrators only. True, if the
  * user's presence in the chat is hidden
  * @param canBeEdited Optional. Administrators only. True, if the bot is allowed
  * to edit administrator privileges of that user
  * @param canPostMessages Optional. Administrators only. True, if the administrator
  * can post in the channel; channels only
  * @param canEditMessages Optional. Administrators only. True, if the administrator
  * can edit messages of other users and can pin messages;
  * channels only
  * @param canDeleteMessages Optional. Administrators only. True, if the administrator
  * can delete messages of other users
  * @param canRestrictMembers Optional. Administrators only. True, if the administrator
  * can restrict, ban or unban chat members
  * @param canPromoteMembers Optional. Administrators only. True, if the administrator
  * can add new administrators with a subset of their own
  * privileges or demote administrators that he has promoted,
  * directly or indirectly (promoted by administrators that were
  * appointed by the user)
  * @param canChangeInfo Optional. Administrators and restricted only. True, if the
  * user is allowed to change the chat title, photo and other
  * settings
  * @param canInviteUsers Optional. Administrators and restricted only. True, if the
  * user is allowed to invite new users to the chat
  * @param canPinMessages Optional. Administrators and restricted only. True, if the
  * user is allowed to pin messages; groups and supergroups only
  * @param isMember Optional. Restricted only. True, if the user is a member of
  * the chat at the moment of the request
  * @param canSendMessages Optional. Restricted only. True, if the user is allowed to
  * send text messages, contacts, locations and venues
  * @param canSendMediaMessages Optional. Restricted only. True, if the user is allowed to
  * send audios, documents, photos, videos, video notes and
  * voice notes
  * @param canSendPolls Optional. Restricted only. True, if the user is allowed to
  * send polls
  * @param canSendOtherMessages Optional. Restricted only. True, if the user is allowed to
  * send animations, games, stickers and use inline bots
  * @param canAddWebPagePreviews Optional. Restricted only. True, if the user is allowed to
  * add web page previews to their messages
  * @param untilDate Optional. Restricted and kicked only. Date when
  * restrictions will be lifted for this user; unix time */
final case class ChatMember(user: User,
                            status: String,
                            customTitle: Option[String] = Option.empty,
                            isAnonymous: Option[Boolean] = Option.empty,
                            canBeEdited: Option[Boolean] = Option.empty,
                            canPostMessages: Option[Boolean] = Option.empty,
                            canEditMessages: Option[Boolean] = Option.empty,
                            canDeleteMessages: Option[Boolean] = Option.empty,
                            canRestrictMembers: Option[Boolean] = Option.empty,
                            canPromoteMembers: Option[Boolean] = Option.empty,
                            canChangeInfo: Option[Boolean] = Option.empty,
                            canInviteUsers: Option[Boolean] = Option.empty,
                            canPinMessages: Option[Boolean] = Option.empty,
                            isMember: Option[Boolean] = Option.empty,
                            canSendMessages: Option[Boolean] = Option.empty,
                            canSendMediaMessages: Option[Boolean] = Option.empty,
                            canSendPolls: Option[Boolean] = Option.empty,
                            canSendOtherMessages: Option[Boolean] = Option.empty,
                            canAddWebPagePreviews: Option[Boolean] = Option.empty,
                            untilDate: Option[Int] = Option.empty)
