package telegramium.bots

/** Describes actions that a non-administrator user is allowed to take in a chat.
  *
  * @param canSendMessages
  *   Optional. True, if the user is allowed to send text messages, contacts, invoices, locations and venues
  * @param canSendAudios
  *   Optional. True, if the user is allowed to send audios
  * @param canSendDocuments
  *   Optional. True, if the user is allowed to send documents
  * @param canSendPhotos
  *   Optional. True, if the user is allowed to send photos
  * @param canSendVideos
  *   Optional. True, if the user is allowed to send videos
  * @param canSendVideoNotes
  *   Optional. True, if the user is allowed to send video notes
  * @param canSendVoiceNotes
  *   Optional. True, if the user is allowed to send voice notes
  * @param canSendPolls
  *   Optional. True, if the user is allowed to send polls
  * @param canSendOtherMessages
  *   Optional. True, if the user is allowed to send animations, games, stickers and use inline bots
  * @param canAddWebPagePreviews
  *   Optional. True, if the user is allowed to add web page previews to their messages
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
  canSendAudios: Option[Boolean] = Option.empty,
  canSendDocuments: Option[Boolean] = Option.empty,
  canSendPhotos: Option[Boolean] = Option.empty,
  canSendVideos: Option[Boolean] = Option.empty,
  canSendVideoNotes: Option[Boolean] = Option.empty,
  canSendVoiceNotes: Option[Boolean] = Option.empty,
  canSendPolls: Option[Boolean] = Option.empty,
  canSendOtherMessages: Option[Boolean] = Option.empty,
  canAddWebPagePreviews: Option[Boolean] = Option.empty,
  canChangeInfo: Option[Boolean] = Option.empty,
  canInviteUsers: Option[Boolean] = Option.empty,
  canPinMessages: Option[Boolean] = Option.empty,
  canManageTopics: Option[Boolean] = Option.empty
)
