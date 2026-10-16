package telegramium.bots

/** Describes actions that a non-administrator user is allowed to take in a chat.
  *
  * @param canSendMessages
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send text messages, rich messages, contacts,
  *   giveaways, giveaway winners, invoices, locations and venues
  * @param canSendAudios
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send audios
  * @param canSendDocuments
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send documents
  * @param canSendPhotos
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send photos
  * @param canSendVideos
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send videos
  * @param canSendVideoNotes
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send video notes
  * @param canSendVoiceNotes
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send voice notes
  * @param canSendPolls
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send polls and checklists
  * @param canSendOtherMessages
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to send animations, games, stickers and use inline bots
  * @param canAddWebPagePreviews
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to add web page previews to their messages
  * @param canReactToMessages
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to react to messages. If omitted, defaults to the value
  *   of <em>can_send_messages</em>.
  * @param canEditTag
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to edit their own tag. If omitted, defaults to the value
  *   of <em>can_pin_messages</em>.
  * @param canChangeInfo
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to change the chat title, photo and other settings.
  *   Ignored in public supergroups.
  * @param canInviteUsers
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to invite new users to the chat
  * @param canPinMessages
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to pin messages. Ignored in public supergroups.
  * @param canManageTopics
  *   <em>Optional</em>. <em>True</em>, if the user is allowed to create forum topics. If omitted, defaults to the value
  *   of can_pin_messages.
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
  canReactToMessages: Option[Boolean] = Option.empty,
  canEditTag: Option[Boolean] = Option.empty,
  canChangeInfo: Option[Boolean] = Option.empty,
  canInviteUsers: Option[Boolean] = Option.empty,
  canPinMessages: Option[Boolean] = Option.empty,
  canManageTopics: Option[Boolean] = Option.empty
)
