package telegramium.bots

/** Describes actions that a non-administrator user is allowed to take in a chat.
  *
  * @param canSendMessages
  *   Optional. True, if the user is allowed to send text messages, contacts, locations and venues
  * @param canSendMediaMessages
  *   Optional. True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes,
  *   implies can_send_messages
  * @param canSendPolls
  *   Optional. True, if the user is allowed to send polls, implies can_send_messages
  * @param canSendOtherMessages
  *   Optional. True, if the user is allowed to send animations, games, stickers and use inline bots, implies
  *   can_send_media_messages
  * @param canAddWebPagePreviews
  *   Optional. True, if the user is allowed to add web page previews to their messages, implies can_send_media_messages
  * @param canChangeInfo
  *   Optional. True, if the user is allowed to change the chat title, photo and other settings. Ignored in public
  *   supergroups
  * @param canInviteUsers
  *   Optional. True, if the user is allowed to invite new users to the chat
  * @param canPinMessages
  *   Optional. True, if the user is allowed to pin messages. Ignored in public supergroups
  * @param canManageTopics
  *   Optional. True, if the user is allowed to create forum topics. If omitted defaults to the value of
  *   can_pin_messages
  */
final case class ChatPermissions(
  canSendMessages: Option[Boolean] = Option.empty,
  canSendMediaMessages: Option[Boolean] = Option.empty,
  canSendPolls: Option[Boolean] = Option.empty,
  canSendOtherMessages: Option[Boolean] = Option.empty,
  canAddWebPagePreviews: Option[Boolean] = Option.empty,
  canChangeInfo: Option[Boolean] = Option.empty,
  canInviteUsers: Option[Boolean] = Option.empty,
  canPinMessages: Option[Boolean] = Option.empty,
  canManageTopics: Option[Boolean] = Option.empty
)
