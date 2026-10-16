package telegramium.bots

sealed trait MaybeInaccessibleMessage {}

/** This object represents a message.
  *
  * @param messageId
  *   Unique message identifier inside this chat; 0 for ephemeral messages. In specific instances (e.g., a message
  *   containing a video sent to a big chat), the server might automatically schedule a message instead of sending it
  *   immediately. In such cases, this field will be 0 and the relevant message will be unusable until it is actually
  *   sent.
  * @param date
  *   Date the message was sent in Unix time. It is always a positive number, representing a valid date.
  * @param chat
  *   Chat the message belongs to
  * @param messageThreadId
  *   <em>Optional</em>. Unique identifier of a message thread or forum topic to which the message belongs; for
  *   supergroups and private chats only
  * @param directMessagesTopic
  *   <em>Optional</em>. Information about the direct messages chat topic that contains the message
  * @param from
  *   <em>Optional</em>. Sender of the message; may be empty for messages sent to channels. For backward compatibility,
  *   if the message was sent on behalf of a chat, the field contains a fake sender user in non-channel chats.
  * @param senderChat
  *   <em>Optional</em>. Sender of the message when sent on behalf of a chat. For example, the supergroup itself for
  *   messages sent by its anonymous administrators or a linked channel for messages automatically forwarded to the
  *   channel's discussion group. For backward compatibility, if the message was sent on behalf of a chat, the field
  *   <em>from</em> contains a fake sender user in non-channel chats.
  * @param senderBoostCount
  *   <em>Optional</em>. If the sender of the message boosted the chat, the number of boosts added by the user
  * @param senderBusinessBot
  *   <em>Optional</em>. The bot that actually sent the message on behalf of the business account. Available only for
  *   outgoing messages sent on behalf of the connected business account.
  * @param senderTag
  *   <em>Optional</em>. Tag or custom title of the sender of the message; for supergroups only
  * @param receiverUser
  *   <em>Optional</em>. For ephemeral messages, the user who received the message
  * @param ephemeralMessageId
  *   <em>Optional</em>. For ephemeral messages, identifier of the ephemeral message inside this chat. The identifier
  *   may be reused for another ephemeral message after the message is deleted or expires.
  * @param guestQueryId
  *   <em>Optional</em>. The unique identifier for the guest query. Use this identifier with the method <a
  *   href="https://core.telegram.org/bots/api#answerguestquery">answerGuestQuery</a> to send a response message. If
  *   non-empty, the message belongs to the chat where the guest bot was summoned, which may not coincide with other
  *   existing bot chats sharing the same identifier.
  * @param businessConnectionId
  *   <em>Optional</em>. Unique identifier of the business connection from which the message was received. If non-empty,
  *   the message belongs to a chat of the corresponding business account that is independent from any potential bot
  *   chat which might share the same identifier.
  * @param forwardOrigin
  *   <em>Optional</em>. Information about the original message for forwarded messages
  * @param isTopicMessage
  *   <em>Optional</em>. <em>True</em>, if the message is sent to a topic in a forum supergroup or a private chat with
  *   the bot
  * @param isAutomaticForward
  *   <em>Optional</em>. <em>True</em>, if the message is a channel post that was automatically forwarded to the
  *   connected discussion group
  * @param replyToMessage
  *   <em>Optional</em>. For replies in the same chat and message thread, the original message. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain further
  *   <em>reply_to_message</em> fields even if it itself is a reply. If the message is a reply to an ephemeral message,
  *   then this field may be omitted.
  * @param externalReply
  *   <em>Optional</em>. Information about the message that is being replied to, which may come from another chat or
  *   forum topic
  * @param quote
  *   <em>Optional</em>. For replies that quote part of the original message, the quoted part of the message
  * @param replyToStory
  *   <em>Optional</em>. For replies to a story, the original story
  * @param replyToChecklistTaskId
  *   <em>Optional</em>. Identifier of the specific checklist task that is being replied to
  * @param replyToPollOptionId
  *   <em>Optional</em>. Persistent identifier of the specific poll option that is being replied to
  * @param viaBot
  *   <em>Optional</em>. Bot through which the message was sent
  * @param guestBotCallerUser
  *   <em>Optional</em>. For a message sent by a guest bot, this is the user whose original message triggered the bot's
  *   response
  * @param guestBotCallerChat
  *   <em>Optional</em>. For a message sent by a guest bot, this is the chat whose original message triggered the bot's
  *   response
  * @param editDate
  *   <em>Optional</em>. Date the message was last edited in Unix time
  * @param hasProtectedContent
  *   <em>Optional</em>. <em>True</em>, if the message can't be forwarded
  * @param isFromOffline
  *   <em>Optional</em>. <em>True</em>, if the message was sent by an implicit action, for example, as an away or a
  *   greeting business message, or as a scheduled message
  * @param isPaidPost
  *   <em>Optional</em>. <em>True</em>, if the message is a paid post. Note that such posts must not be deleted for 24
  *   hours to receive the payment and can't be edited.
  * @param mediaGroupId
  *   <em>Optional</em>. The unique identifier inside this chat of a media message group this message belongs to
  * @param authorSignature
  *   <em>Optional</em>. Signature of the post author for messages in channels, or the custom title of an anonymous
  *   group administrator
  * @param paidStarCount
  *   <em>Optional</em>. The number of Telegram Stars that were paid by the sender of the message to send it
  * @param text
  *   <em>Optional</em>. For text messages, the actual UTF-8 text of the message
  * @param entities
  *   <em>Optional</em>. For text messages, special entities like usernames, URLs, bot commands, etc. that appear in the
  *   text
  * @param linkPreviewOptions
  *   <em>Optional</em>. Options used for link preview generation for the message, if it is a text message and link
  *   preview options were changed
  * @param suggestedPostInfo
  *   <em>Optional</em>. Information about suggested post parameters if the message is a suggested post in a channel
  *   direct messages chat. If the message is an approved or declined suggested post, then it can't be edited.
  * @param effectId
  *   <em>Optional</em>. Unique identifier of the message effect added to the message
  * @param richMessage
  *   <em>Optional</em>. Message is a rich formatted message
  * @param animation
  *   <em>Optional</em>. Message is an animation, information about the animation. For backward compatibility, when this
  *   field is set, the <em>document</em> field will also be set.
  * @param audio
  *   <em>Optional</em>. Message is an audio file, information about the file
  * @param document
  *   <em>Optional</em>. Message is a general file, information about the file
  * @param livePhoto
  *   <em>Optional</em>. Message is a live photo, information about the live photo. For backward compatibility, when
  *   this field is set, the <em>photo</em> field will also be set.
  * @param paidMedia
  *   <em>Optional</em>. Message contains paid media; information about the paid media
  * @param photo
  *   <em>Optional</em>. Message is a photo, available sizes of the photo
  * @param sticker
  *   <em>Optional</em>. Message is a sticker, information about the sticker
  * @param story
  *   <em>Optional</em>. Message is a forwarded story
  * @param video
  *   <em>Optional</em>. Message is a video, information about the video
  * @param videoNote
  *   <em>Optional</em>. Message is a <a href="https://telegram.org/blog/video-messages-and-telescope">video note</a>,
  *   information about the video message
  * @param voice
  *   <em>Optional</em>. Message is a voice message, information about the file
  * @param caption
  *   <em>Optional</em>. Caption for the animation, audio, document, paid media, photo, video or voice
  * @param captionEntities
  *   <em>Optional</em>. For messages with a caption, special entities like usernames, URLs, bot commands, etc. that
  *   appear in the caption
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. <em>True</em>, if the caption must be shown above the message media
  * @param hasMediaSpoiler
  *   <em>Optional</em>. <em>True</em>, if the message media is covered by a spoiler animation
  * @param checklist
  *   <em>Optional</em>. Message is a checklist
  * @param contact
  *   <em>Optional</em>. Message is a shared contact, information about the contact
  * @param dice
  *   <em>Optional</em>. Message is a dice with random value
  * @param game
  *   <em>Optional</em>. Message is a game, information about the game.
  * @param poll
  *   <em>Optional</em>. Message is a native poll, information about the poll
  * @param venue
  *   <em>Optional</em>. Message is a venue, information about the venue. For backward compatibility, when this field is
  *   set, the <em>location</em> field will also be set.
  * @param location
  *   <em>Optional</em>. Message is a shared location, information about the location
  * @param newChatMembers
  *   <em>Optional</em>. New members that were added to the group or supergroup and information about them (the bot
  *   itself may be one of these members)
  * @param leftChatMember
  *   <em>Optional</em>. A member was removed from the group, information about them (this member may be the bot itself)
  * @param chatOwnerLeft
  *   <em>Optional</em>. Service message: chat owner has left
  * @param chatOwnerChanged
  *   <em>Optional</em>. Service message: chat owner has changed
  * @param newChatTitle
  *   <em>Optional</em>. A chat title was changed to this value
  * @param newChatPhoto
  *   <em>Optional</em>. A chat photo was change to this value
  * @param deleteChatPhoto
  *   <em>Optional</em>. Service message: the chat photo was deleted
  * @param groupChatCreated
  *   <em>Optional</em>. Service message: the group has been created
  * @param supergroupChatCreated
  *   <em>Optional</em>. Service message: the supergroup has been created. This field can't be received in a message
  *   coming through updates, because bot can't be a member of a supergroup when it is created. It can only be found in
  *   reply_to_message if someone replies to a very first message in a directly created supergroup.
  * @param channelChatCreated
  *   <em>Optional</em>. Service message: the channel has been created. This field can't be received in a message coming
  *   through updates, because bot can't be a member of a channel when it is created. It can only be found in
  *   reply_to_message if someone replies to a very first message in a channel.
  * @param messageAutoDeleteTimerChanged
  *   <em>Optional</em>. Service message: auto-delete timer settings changed in the chat
  * @param migrateToChatId
  *   <em>Optional</em>. The group has been migrated to a supergroup with the specified identifier. This number may have
  *   more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting
  *   it. But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type are safe for
  *   storing this identifier.
  * @param migrateFromChatId
  *   <em>Optional</em>. The supergroup has been migrated from a group with the specified identifier. This number may
  *   have more than 32 significant bits and some programming languages may have difficulty/silent defects in
  *   interpreting it. But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type
  *   are safe for storing this identifier.
  * @param pinnedMessage
  *   <em>Optional</em>. Specified message was pinned. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain further
  *   <em>reply_to_message</em> fields even if it itself is a reply.
  * @param invoice
  *   <em>Optional</em>. Message is an invoice for a <a href="https://core.telegram.org/bots/api#payments">payment</a>,
  *   information about the invoice.
  * @param successfulPayment
  *   <em>Optional</em>. Message is a service message about a successful payment, information about the payment.
  * @param refundedPayment
  *   <em>Optional</em>. Message is a service message about a refunded payment, information about the payment.
  * @param usersShared
  *   <em>Optional</em>. Service message: users were shared with the bot
  * @param chatShared
  *   <em>Optional</em>. Service message: a chat was shared with the bot
  * @param gift
  *   <em>Optional</em>. Service message: a regular gift was sent or received
  * @param uniqueGift
  *   <em>Optional</em>. Service message: a unique gift was sent or received
  * @param giftUpgradeSent
  *   <em>Optional</em>. Service message: upgrade of a gift was purchased after the gift was sent
  * @param connectedWebsite
  *   <em>Optional</em>. The domain name of the website on which the user has logged in.
  * @param writeAccessAllowed
  *   <em>Optional</em>. Service message: the user allowed the bot to write messages after adding it to the attachment
  *   or side menu, launching a Web App from a link, or accepting an explicit request from a Web App sent by the method
  *   <a href="https://core.telegram.org/bots/webapps#initializing-mini-apps">requestWriteAccess</a>
  * @param passportData
  *   <em>Optional</em>. Telegram Passport data
  * @param proximityAlertTriggered
  *   <em>Optional</em>. Service message: a user in the chat triggered another user's proximity alert while sharing Live
  *   Location
  * @param boostAdded
  *   <em>Optional</em>. Service message: user boosted the chat
  * @param chatBackgroundSet
  *   <em>Optional</em>. Service message: chat background set
  * @param checklistTasksDone
  *   <em>Optional</em>. Service message: some tasks in a checklist were marked as done or not done
  * @param checklistTasksAdded
  *   <em>Optional</em>. Service message: tasks were added to a checklist
  * @param communityChatAdded
  *   <em>Optional</em>. Service message: chat or bot added to a <a
  *   href="https://core.telegram.org/bots/api#community">Community</a>
  * @param communityChatJoined
  *   <em>Optional</em>. Service message: chat was joined by a user from a <a
  *   href="https://core.telegram.org/bots/api#community">Community</a>
  * @param communityChatRemoved
  *   <em>Optional</em>. Service message: chat or bot removed from a <a
  *   href="https://core.telegram.org/bots/api#community">Community</a>
  * @param directMessagePriceChanged
  *   <em>Optional</em>. Service message: the price for paid messages in the corresponding direct messages chat of a
  *   channel has changed
  * @param forumTopicCreated
  *   <em>Optional</em>. Service message: forum topic created
  * @param forumTopicEdited
  *   <em>Optional</em>. Service message: forum topic edited
  * @param forumTopicClosed
  *   <em>Optional</em>. Service message: forum topic closed
  * @param forumTopicReopened
  *   <em>Optional</em>. Service message: forum topic reopened
  * @param generalForumTopicHidden
  *   <em>Optional</em>. Service message: the 'General' forum topic hidden
  * @param generalForumTopicUnhidden
  *   <em>Optional</em>. Service message: the 'General' forum topic unhidden
  * @param giveawayCreated
  *   <em>Optional</em>. Service message: a scheduled giveaway was created
  * @param giveaway
  *   <em>Optional</em>. The message is a scheduled giveaway message
  * @param giveawayWinners
  *   <em>Optional</em>. A giveaway with public winners was completed
  * @param giveawayCompleted
  *   <em>Optional</em>. Service message: a giveaway without public winners was completed
  * @param managedBotCreated
  *   <em>Optional</em>. Service message: user created a bot that will be managed by the current bot
  * @param paidMessagePriceChanged
  *   <em>Optional</em>. Service message: the price for paid messages has changed in the chat
  * @param pollOptionAdded
  *   <em>Optional</em>. Service message: answer option was added to a poll
  * @param pollOptionDeleted
  *   <em>Optional</em>. Service message: answer option was deleted from a poll
  * @param suggestedPostApproved
  *   <em>Optional</em>. Service message: a suggested post was approved
  * @param suggestedPostApprovalFailed
  *   <em>Optional</em>. Service message: approval of a suggested post has failed
  * @param suggestedPostDeclined
  *   <em>Optional</em>. Service message: a suggested post was declined
  * @param suggestedPostPaid
  *   <em>Optional</em>. Service message: payment for a suggested post was received
  * @param suggestedPostRefunded
  *   <em>Optional</em>. Service message: payment for a suggested post was refunded
  * @param videoChatScheduled
  *   <em>Optional</em>. Service message: video chat scheduled
  * @param videoChatStarted
  *   <em>Optional</em>. Service message: video chat started
  * @param videoChatEnded
  *   <em>Optional</em>. Service message: video chat ended
  * @param videoChatParticipantsInvited
  *   <em>Optional</em>. Service message: new participants invited to a video chat
  * @param webAppData
  *   <em>Optional</em>. Service message: data sent by a Web App
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message. <code>login_url</code> buttons are represented as ordinary <code>url</code> buttons.
  */
final case class Message(
  messageId: Int,
  date: Long,
  chat: Chat,
  messageThreadId: Option[Int] = Option.empty,
  directMessagesTopic: Option[DirectMessagesTopic] = Option.empty,
  from: Option[User] = Option.empty,
  senderChat: Option[Chat] = Option.empty,
  senderBoostCount: Option[Int] = Option.empty,
  senderBusinessBot: Option[User] = Option.empty,
  senderTag: Option[String] = Option.empty,
  receiverUser: Option[User] = Option.empty,
  ephemeralMessageId: Option[Int] = Option.empty,
  guestQueryId: Option[String] = Option.empty,
  businessConnectionId: Option[String] = Option.empty,
  forwardOrigin: Option[iozhik.OpenEnum[MessageOrigin]] = Option.empty,
  isTopicMessage: Option[Boolean] = Option.empty,
  isAutomaticForward: Option[Boolean] = Option.empty,
  replyToMessage: Option[Message] = Option.empty,
  externalReply: Option[ExternalReplyInfo] = Option.empty,
  quote: Option[TextQuote] = Option.empty,
  replyToStory: Option[Story] = Option.empty,
  replyToChecklistTaskId: Option[Int] = Option.empty,
  replyToPollOptionId: Option[String] = Option.empty,
  viaBot: Option[User] = Option.empty,
  guestBotCallerUser: Option[User] = Option.empty,
  guestBotCallerChat: Option[Chat] = Option.empty,
  editDate: Option[Long] = Option.empty,
  hasProtectedContent: Option[Boolean] = Option.empty,
  isFromOffline: Option[Boolean] = Option.empty,
  isPaidPost: Option[Boolean] = Option.empty,
  mediaGroupId: Option[String] = Option.empty,
  authorSignature: Option[String] = Option.empty,
  paidStarCount: Option[Int] = Option.empty,
  text: Option[String] = Option.empty,
  entities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  linkPreviewOptions: Option[LinkPreviewOptions] = Option.empty,
  suggestedPostInfo: Option[SuggestedPostInfo] = Option.empty,
  effectId: Option[String] = Option.empty,
  richMessage: Option[RichMessage] = Option.empty,
  animation: Option[Animation] = Option.empty,
  audio: Option[Audio] = Option.empty,
  document: Option[Document] = Option.empty,
  livePhoto: Option[LivePhoto] = Option.empty,
  paidMedia: Option[PaidMediaInfo] = Option.empty,
  photo: List[PhotoSize] = List.empty,
  sticker: Option[Sticker] = Option.empty,
  story: Option[Story] = Option.empty,
  video: Option[Video] = Option.empty,
  videoNote: Option[VideoNote] = Option.empty,
  voice: Option[Voice] = Option.empty,
  caption: Option[String] = Option.empty,
  captionEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  hasMediaSpoiler: Option[Boolean] = Option.empty,
  checklist: Option[Checklist] = Option.empty,
  contact: Option[Contact] = Option.empty,
  dice: Option[Dice] = Option.empty,
  game: Option[Game] = Option.empty,
  poll: Option[Poll] = Option.empty,
  venue: Option[Venue] = Option.empty,
  location: Option[Location] = Option.empty,
  newChatMembers: List[User] = List.empty,
  leftChatMember: Option[User] = Option.empty,
  chatOwnerLeft: Option[ChatOwnerLeft] = Option.empty,
  chatOwnerChanged: Option[ChatOwnerChanged] = Option.empty,
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
  refundedPayment: Option[RefundedPayment] = Option.empty,
  usersShared: Option[UsersShared] = Option.empty,
  chatShared: Option[ChatShared] = Option.empty,
  gift: Option[GiftInfo] = Option.empty,
  uniqueGift: Option[UniqueGiftInfo] = Option.empty,
  giftUpgradeSent: Option[GiftInfo] = Option.empty,
  connectedWebsite: Option[String] = Option.empty,
  writeAccessAllowed: Option[WriteAccessAllowed] = Option.empty,
  passportData: Option[PassportData] = Option.empty,
  proximityAlertTriggered: Option[ProximityAlertTriggered] = Option.empty,
  boostAdded: Option[ChatBoostAdded] = Option.empty,
  chatBackgroundSet: Option[ChatBackground] = Option.empty,
  checklistTasksDone: Option[ChecklistTasksDone] = Option.empty,
  checklistTasksAdded: Option[ChecklistTasksAdded] = Option.empty,
  communityChatAdded: Option[CommunityChatAdded] = Option.empty,
  communityChatJoined: Option[CommunityChatJoined] = Option.empty,
  communityChatRemoved: Option[CommunityChatRemoved.type] = Option.empty,
  directMessagePriceChanged: Option[DirectMessagePriceChanged] = Option.empty,
  forumTopicCreated: Option[ForumTopicCreated] = Option.empty,
  forumTopicEdited: Option[ForumTopicEdited] = Option.empty,
  forumTopicClosed: Option[ForumTopicClosed.type] = Option.empty,
  forumTopicReopened: Option[ForumTopicReopened.type] = Option.empty,
  generalForumTopicHidden: Option[GeneralForumTopicHidden.type] = Option.empty,
  generalForumTopicUnhidden: Option[GeneralForumTopicUnhidden.type] = Option.empty,
  giveawayCreated: Option[GiveawayCreated] = Option.empty,
  giveaway: Option[Giveaway] = Option.empty,
  giveawayWinners: Option[GiveawayWinners] = Option.empty,
  giveawayCompleted: Option[GiveawayCompleted] = Option.empty,
  managedBotCreated: Option[ManagedBotCreated] = Option.empty,
  paidMessagePriceChanged: Option[PaidMessagePriceChanged] = Option.empty,
  pollOptionAdded: Option[PollOptionAdded] = Option.empty,
  pollOptionDeleted: Option[PollOptionDeleted] = Option.empty,
  suggestedPostApproved: Option[SuggestedPostApproved] = Option.empty,
  suggestedPostApprovalFailed: Option[SuggestedPostApprovalFailed] = Option.empty,
  suggestedPostDeclined: Option[SuggestedPostDeclined] = Option.empty,
  suggestedPostPaid: Option[SuggestedPostPaid] = Option.empty,
  suggestedPostRefunded: Option[SuggestedPostRefunded] = Option.empty,
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
