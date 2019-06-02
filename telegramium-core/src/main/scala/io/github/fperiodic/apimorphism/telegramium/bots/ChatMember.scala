package io.github.fperiodic.apimorphism.telegramium.bots

/** This object contains information about one member of a chat.*/
final case class ChatMember(
                            /** Information about the user*/
                            user: User,
                            /** The member's status in the chat. Can be “creator”,
                              * “administrator”, “member”, “restricted”, “left” or “kicked”*/
                            status: String,
                            /** Optional. Restricted and kicked only. Date when
                              * restrictions will be lifted for this user, unix time*/
                            untilDate: Option[Int] = Option.empty,
                            /** Optional. Administrators only. True, if the bot is allowed
                              * to edit administrator privileges of that user*/
                            canBeEdited: Option[Boolean] = Option.empty,
                            /** Optional. Administrators only. True, if the administrator
                              * can change the chat title, photo and other settings*/
                            canChangeInfo: Option[Boolean] = Option.empty,
                            /** Optional. Administrators only. True, if the administrator
                              * can post in the channel, channels only*/
                            canPostMessages: Option[Boolean] = Option.empty,
                            /** Optional. Administrators only. True, if the administrator
                              * can edit messages of other users and can pin messages,
                              * channels only*/
                            canEditMessages: Option[Boolean] = Option.empty,
                            /** Optional. Administrators only. True, if the administrator
                              * can delete messages of other users*/
                            canDeleteMessages: Option[Boolean] = Option.empty,
                            /** Optional. Administrators only. True, if the administrator
                              * can invite new users to the chat*/
                            canInviteUsers: Option[Boolean] = Option.empty,
                            /** Optional. Administrators only. True, if the administrator
                              * can restrict, ban or unban chat members*/
                            canRestrictMembers: Option[Boolean] = Option.empty,
                            /** Optional. Administrators only. True, if the administrator
                              * can pin messages, groups and supergroups only*/
                            canPinMessages: Option[Boolean] = Option.empty,
                            /** Optional. Administrators only. True, if the administrator
                              * can add new administrators with a subset of his own
                              * privileges or demote administrators that he has promoted,
                              * directly or indirectly (promoted by administrators that were
                              * appointed by the user)*/
                            canPromoteMembers: Option[Boolean] = Option.empty,
                            /** Optional. Restricted only. True, if the user is a member of
                              * the chat at the moment of the request*/
                            isMember: Option[Boolean] = Option.empty,
                            /** Optional. Restricted only. True, if the user can send text
                              * messages, contacts, locations and venues*/
                            canSendMessages: Option[Boolean] = Option.empty,
                            /** Optional. Restricted only. True, if the user can send
                              * audios, documents, photos, videos, video notes and voice
                              * notes, implies can_send_messages*/
                            canSendMediaMessages: Option[Boolean] = Option.empty,
                            /** Optional. Restricted only. True, if the user can send
                              * animations, games, stickers and use inline bots, implies
                              * can_send_media_messages*/
                            canSendOtherMessages: Option[Boolean] = Option.empty,
                            /** Optional. Restricted only. True, if user may add web page
                              * previews to his messages, implies can_send_media_messages*/
                            canAddWebPagePreviews: Option[Boolean] = Option.empty)
