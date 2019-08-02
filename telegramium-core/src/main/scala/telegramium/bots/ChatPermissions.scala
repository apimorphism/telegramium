package telegramium.bots

/** Describes actions that a non-administrator user is allowed to take in a chat.*/
final case class ChatPermissions(
                                 /** Optional. True, if the user is allowed to send text
                                   * messages, contacts, locations and venues*/
                                 canSendMessages: Option[Boolean] = Option.empty,
                                 /** Optional. True, if the user is allowed to send audios,
                                   * documents, photos, videos, video notes and voice notes,
                                   * implies can_send_messages*/
                                 canSendMediaMessages: Option[Boolean] = Option.empty,
                                 /** Optional. True, if the user is allowed to send polls,
                                   * implies can_send_messages*/
                                 canSendPolls: Option[Boolean] = Option.empty,
                                 /** Optional. True, if the user is allowed to send animations,
                                   * games, stickers and use inline bots, implies
                                   * can_send_media_messages*/
                                 canSendOtherMessages: Option[Boolean] = Option.empty,
                                 /** Optional. True, if the user is allowed to add web page
                                   * previews to their messages, implies can_send_media_messages*/
                                 canAddWebPagePreviews: Option[Boolean] = Option.empty,
                                 /** Optional. True, if the user is allowed to change the chat
                                   * title, photo and other settings. Ignored in public
                                   * supergroups*/
                                 canChangeInfo: Option[Boolean] = Option.empty,
                                 /** Optional. True, if the user is allowed to invite new users
                                   * to the chat*/
                                 canInviteUsers: Option[Boolean] = Option.empty,
                                 /** Optional. True, if the user is allowed to pin messages.
                                   * Ignored in public supergroups*/
                                 canPinMessages: Option[Boolean] = Option.empty)
