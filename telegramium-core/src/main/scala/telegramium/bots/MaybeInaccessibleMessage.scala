package telegramium.bots

sealed trait MaybeInaccessibleMessage {}

/** This object represents a message.
  *
  * @param messageId
  *   Unique message identifier inside this chat
  * @param date
  *   Date the message was sent in Unix time. It is always a positive number, representing a valid date.
  * @param chat
  *   Chat the message belongs to
  * @param messageThreadId
  *   Optional. Unique identifier of a message thread to which the message belongs; for supergroups only
  * @param from
  *   Optional. Sender of the message; empty for messages sent to channels. For backward compatibility, the field
  *   contains a fake sender user in non-channel chats, if the message was sent on behalf of a chat.
  * @param senderChat
  *   Optional. Sender of the message, sent on behalf of a chat. For example, the channel itself for channel posts, the
  *   supergroup itself for messages from anonymous group administrators, the linked channel for messages automatically
  *   forwarded to the discussion group. For backward compatibility, the field from contains a fake sender user in
  *   non-channel chats, if the message was sent on behalf of a chat.
  * @param senderBoostCount
  *   Optional. If the sender of the message boosted the chat, the number of boosts added by the user
  * @param senderBusinessBot
  *   Optional. The bot that actually sent the message on behalf of the business account. Available only for outgoing
  *   messages sent on behalf of the connected business account.
  * @param businessConnectionId
  *   Optional. Unique identifier of the business connection from which the message was received. If non-empty, the
  *   message belongs to a chat of the corresponding business account that is independent from any potential bot chat
  *   which might share the same identifier.
  * @param forwardOrigin
  *   Optional. Information about the original message for forwarded messages
  * @param isTopicMessage
  *   Optional. True, if the message is sent to a forum topic
  * @param isAutomaticForward
  *   Optional. True, if the message is a channel post that was automatically forwarded to the connected discussion
  *   group
  * @param replyToMessage
  *   Optional. For replies in the same chat and message thread, the original message. Note that the Message object in
  *   this field will not contain further reply_to_message fields even if it itself is a reply.
  * @param externalReply
  *   Optional. Information about the message that is being replied to, which may come from another chat or forum topic
  * @param quote
  *   Optional. For replies that quote part of the original message, the quoted part of the message
  * @param replyToStory
  *   Optional. For replies to a story, the original story
  * @param viaBot
  *   Optional. Bot through which the message was sent
  * @param editDate
  *   Optional. Date the message was last edited in Unix time
  * @param hasProtectedContent
  *   Optional. True, if the message can't be forwarded
  * @param isFromOffline
  *   Optional. True, if the message was sent by an implicit action, for example, as an away or a greeting business
  *   message, or as a scheduled message
  * @param mediaGroupId
  *   Optional. The unique identifier of a media message group this message belongs to
  * @param authorSignature
  *   Optional. Signature of the post author for messages in channels, or the custom title of an anonymous group
  *   administrator
  * @param text
  *   Optional. For text messages, the actual UTF-8 text of the message
  * @param entities
  *   Optional. For text messages, special entities like usernames, URLs, bot commands, etc. that appear in the text
  * @param linkPreviewOptions
  *   Optional. Options used for link preview generation for the message, if it is a text message and link preview
  *   options were changed
  * @param animation
  *   Optional. Message is an animation, information about the animation. For backward compatibility, when this field is
  *   set, the document field will also be set
  * @param audio
  *   Optional. Message is an audio file, information about the file
  * @param document
  *   Optional. Message is a general file, information about the file
  * @param photo
  *   Optional. Message is a photo, available sizes of the photo
  * @param sticker
  *   Optional. Message is a sticker, information about the sticker
  * @param story
  *   Optional. Message is a forwarded story
  * @param video
  *   Optional. Message is a video, information about the video
  * @param videoNote
  *   Optional. Message is a video note, information about the video message
  * @param voice
  *   Optional. Message is a voice message, information about the file
  * @param caption
  *   Optional. Caption for the animation, audio, document, photo, video or voice
  * @param captionEntities
  *   Optional. For messages with a caption, special entities like usernames, URLs, bot commands, etc. that appear in
  *   the caption
  * @param hasMediaSpoiler
  *   Optional. True, if the message media is covered by a spoiler animation
  * @param contact
  *   Optional. Message is a shared contact, information about the contact
  * @param dice
  *   Optional. Message is a dice with random value
  * @param game
  *   Optional. Message is a game, information about the game.
  * @param poll
  *   Optional. Message is a native poll, information about the poll
  * @param venue
  *   Optional. Message is a venue, information about the venue. For backward compatibility, when this field is set, the
  *   location field will also be set
  * @param location
  *   Optional. Message is a shared location, information about the location
  * @param newChatMembers
  *   Optional. New members that were added to the group or supergroup and information about them (the bot itself may be
  *   one of these members)
  * @param leftChatMember
  *   Optional. A member was removed from the group, information about them (this member may be the bot itself)
  * @param newChatTitle
  *   Optional. A chat title was changed to this value
  * @param newChatPhoto
  *   Optional. A chat photo was change to this value
  * @param deleteChatPhoto
  *   Optional. Service message: the chat photo was deleted
  * @param groupChatCreated
  *   Optional. Service message: the group has been created
  * @param supergroupChatCreated
  *   Optional. Service message: the supergroup has been created. This field can't be received in a message coming
  *   through updates, because bot can't be a member of a supergroup when it is created. It can only be found in
  *   reply_to_message if someone replies to a very first message in a directly created supergroup.
  * @param channelChatCreated
  *   Optional. Service message: the channel has been created. This field can't be received in a message coming through
  *   updates, because bot can't be a member of a channel when it is created. It can only be found in reply_to_message
  *   if someone replies to a very first message in a channel.
  * @param messageAutoDeleteTimerChanged
  *   Optional. Service message: auto-delete timer settings changed in the chat
  * @param migrateToChatId
  *   Optional. The group has been migrated to a supergroup with the specified identifier. This number may have more
  *   than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it. But
  *   it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type are safe for storing
  *   this identifier.
  * @param migrateFromChatId
  *   Optional. The supergroup has been migrated from a group with the specified identifier. This number may have more
  *   than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it. But
  *   it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type are safe for storing
  *   this identifier.
  * @param pinnedMessage
  *   Optional. Specified message was pinned. Note that the Message object in this field will not contain further
  *   reply_to_message fields even if it itself is a reply.
  * @param invoice
  *   Optional. Message is an invoice for a payment, information about the invoice.
  * @param successfulPayment
  *   Optional. Message is a service message about a successful payment, information about the payment.
  * @param usersShared
  *   Optional. Service message: users were shared with the bot
  * @param chatShared
  *   Optional. Service message: a chat was shared with the bot
  * @param connectedWebsite
  *   Optional. The domain name of the website on which the user has logged in.
  * @param writeAccessAllowed
  *   Optional. Service message: the user allowed the bot to write messages after adding it to the attachment or side
  *   menu, launching a Web App from a link, or accepting an explicit request from a Web App sent by the method
  *   requestWriteAccess
  * @param passportData
  *   Optional. Telegram Passport data
  * @param proximityAlertTriggered
  *   Optional. Service message. A user in the chat triggered another user's proximity alert while sharing Live
  *   Location.
  * @param boostAdded
  *   Optional. Service message: user boosted the chat
  * @param chatBackgroundSet
  *   Optional. Service message: chat background set
  * @param forumTopicCreated
  *   Optional. Service message: forum topic created
  * @param forumTopicEdited
  *   Optional. Service message: forum topic edited
  * @param forumTopicClosed
  *   Optional. Service message: forum topic closed
  * @param forumTopicReopened
  *   Optional. Service message: forum topic reopened
  * @param generalForumTopicHidden
  *   Optional. Service message: the 'General' forum topic hidden
  * @param generalForumTopicUnhidden
  *   Optional. Service message: the 'General' forum topic unhidden
  * @param giveawayCreated
  *   Optional. Service message: a scheduled giveaway was created
  * @param giveaway
  *   Optional. The message is a scheduled giveaway message
  * @param giveawayWinners
  *   Optional. A giveaway with public winners was completed
  * @param giveawayCompleted
  *   Optional. Service message: a giveaway without public winners was completed
  * @param videoChatScheduled
  *   Optional. Service message: video chat scheduled
  * @param videoChatStarted
  *   Optional. Service message: video chat started
  * @param videoChatEnded
  *   Optional. Service message: video chat ended
  * @param videoChatParticipantsInvited
  *   Optional. Service message: new participants invited to a video chat
  * @param webAppData
  *   Optional. Service message: data sent by a Web App
  * @param replyMarkup
  *   Optional. Inline keyboard attached to the message. login_url buttons are represented as ordinary url buttons.
  */
final case class Message(
  messageId: Int,
  date: Int,
  chat: Chat,
  messageThreadId: Option[Int] = Option.empty,
  from: Option[User] = Option.empty,
  senderChat: Option[Chat] = Option.empty,
  senderBoostCount: Option[Int] = Option.empty,
  senderBusinessBot: Option[User] = Option.empty,
  businessConnectionId: Option[String] = Option.empty,
  forwardOrigin: Option[MessageOrigin] = Option.empty,
  isTopicMessage: Option[Boolean] = Option.empty,
  isAutomaticForward: Option[Boolean] = Option.empty,
  replyToMessage: Option[Message] = Option.empty,
  externalReply: Option[ExternalReplyInfo] = Option.empty,
  quote: Option[TextQuote] = Option.empty,
  replyToStory: Option[Story] = Option.empty,
  viaBot: Option[User] = Option.empty,
  editDate: Option[Int] = Option.empty,
  hasProtectedContent: Option[Boolean] = Option.empty,
  isFromOffline: Option[Boolean] = Option.empty,
  mediaGroupId: Option[String] = Option.empty,
  authorSignature: Option[String] = Option.empty,
  text: Option[String] = Option.empty,
  entities: List[MessageEntity] = List.empty,
  linkPreviewOptions: Option[LinkPreviewOptions] = Option.empty,
  animation: Option[Animation] = Option.empty,
  audio: Option[Audio] = Option.empty,
  document: Option[Document] = Option.empty,
  photo: List[PhotoSize] = List.empty,
  sticker: Option[Sticker] = Option.empty,
  story: Option[Story] = Option.empty,
  video: Option[Video] = Option.empty,
  videoNote: Option[VideoNote] = Option.empty,
  voice: Option[Voice] = Option.empty,
  caption: Option[String] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  hasMediaSpoiler: Option[Boolean] = Option.empty,
  contact: Option[Contact] = Option.empty,
  dice: Option[Dice] = Option.empty,
  game: Option[Game] = Option.empty,
  poll: Option[Poll] = Option.empty,
  venue: Option[Venue] = Option.empty,
  location: Option[Location] = Option.empty,
  newChatMembers: List[User] = List.empty,
  leftChatMember: Option[User] = Option.empty,
  newChatTitle: Option[String] = Option.empty,
  newChatPhoto: List[PhotoSize] = List.empty,
  deleteChatPhoto: Option[Boolean] = Option.empty,
  groupChatCreated: Option[Boolean] = Option.empty,
  supergroupChatCreated: Option[Boolean] = Option.empty,
  channelChatCreated: Option[Boolean] = Option.empty,
  messageAutoDeleteTimerChanged: Option[MessageAutoDeleteTimerChanged] = Option.empty,
  migrateToChatId: Option[Long] = Option.empty,
  migrateFromChatId: Option[Long] = Option.empty,
  pinnedMessage: Option[MaybeInaccessibleMessage] = Option.empty,
  invoice: Option[Invoice] = Option.empty,
  successfulPayment: Option[SuccessfulPayment] = Option.empty,
  usersShared: Option[UsersShared] = Option.empty,
  chatShared: Option[ChatShared] = Option.empty,
  connectedWebsite: Option[String] = Option.empty,
  writeAccessAllowed: Option[WriteAccessAllowed] = Option.empty,
  passportData: Option[PassportData] = Option.empty,
  proximityAlertTriggered: Option[ProximityAlertTriggered] = Option.empty,
  boostAdded: Option[ChatBoostAdded] = Option.empty,
  chatBackgroundSet: Option[ChatBackground] = Option.empty,
  forumTopicCreated: Option[ForumTopicCreated] = Option.empty,
  forumTopicEdited: Option[ForumTopicEdited] = Option.empty,
  forumTopicClosed: Option[ForumTopicClosed.type] = Option.empty,
  forumTopicReopened: Option[ForumTopicReopened.type] = Option.empty,
  generalForumTopicHidden: Option[GeneralForumTopicHidden.type] = Option.empty,
  generalForumTopicUnhidden: Option[GeneralForumTopicUnhidden.type] = Option.empty,
  giveawayCreated: Option[GiveawayCreated.type] = Option.empty,
  giveaway: Option[Giveaway] = Option.empty,
  giveawayWinners: Option[GiveawayWinners] = Option.empty,
  giveawayCompleted: Option[GiveawayCompleted] = Option.empty,
  videoChatScheduled: Option[VideoChatScheduled] = Option.empty,
  videoChatStarted: Option[VideoChatStarted.type] = Option.empty,
  videoChatEnded: Option[VideoChatEnded] = Option.empty,
  videoChatParticipantsInvited: Option[VideoChatParticipantsInvited] = Option.empty,
  webAppData: Option[WebAppData] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
) extends MaybeInaccessibleMessage

/** This object describes a message that was deleted or is otherwise inaccessible to the bot.
  *
  * @param chat
  *   Chat the message belonged to
  * @param messageId
  *   Unique message identifier inside the chat
  * @param date
  *   Always 0. The field can be used to differentiate regular and inaccessible messages.
  */
final case class InaccessibleMessage(chat: Chat, messageId: Int, date: Int) extends MaybeInaccessibleMessage
