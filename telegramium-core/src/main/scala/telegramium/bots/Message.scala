package telegramium.bots

/** This object represents a message.*/
final case class Message(
                         /** Unique message identifier inside this chat*/
                         messageId: Int,
                         /** Optional. Sender, empty for messages sent to channels*/
                         from: Option[User] = Option.empty,
                         /** Date the message was sent in Unix time*/
                         date: Int,
                         /** Conversation the message belongs to*/
                         chat: Chat,
                         /** Optional. For forwarded messages, sender of the original
                           * message*/
                         forwardFrom: Option[User] = Option.empty,
                         /** Optional. For messages forwarded from channels, information
                           * about the original channel*/
                         forwardFromChat: Option[Chat] = Option.empty,
                         /** Optional. For messages forwarded from channels, identifier
                           * of the original message in the channel*/
                         forwardFromMessageId: Option[Int] = Option.empty,
                         /** Optional. For messages forwarded from channels, signature
                           * of the post author if present*/
                         forwardSignature: Option[String] = Option.empty,
                         /** Optional. Sender's name for messages forwarded from users
                           * who disallow adding a link to their account in forwarded
                           * messages*/
                         forwardSenderName: Option[String] = Option.empty,
                         /** Optional. For forwarded messages, date the original message
                           * was sent in Unix time*/
                         forwardDate: Option[Int] = Option.empty,
                         /** Optional. For replies, the original message. Note that the
                           * Message object in this field will not contain further
                           * reply_to_message fields even if it itself is a reply.*/
                         replyToMessage: Option[Message] = Option.empty,
                         /** Optional. Date the message was last edited in Unix time*/
                         editDate: Option[Int] = Option.empty,
                         /** Optional. The unique identifier of a media message group
                           * this message belongs to*/
                         mediaGroupId: Option[String] = Option.empty,
                         /** Optional. Signature of the post author for messages in
                           * channels*/
                         authorSignature: Option[String] = Option.empty,
                         /** Optional. For text messages, the actual UTF-8 text of the
                           * message, 0-4096 characters*/
                         text: Option[String] = Option.empty,
                         /** Optional. For text messages, special entities like
                           * usernames, URLs, bot commands, etc. that appear in the text*/
                         entities: List[MessageEntity] = List.empty,
                         /** Optional. For messages with a caption, special entities
                           * like usernames, URLs, bot commands, etc. that appear in the
                           * caption*/
                         captionEntities: List[MessageEntity] = List.empty,
                         /** Optional. Message is an audio file, information about the
                           * file*/
                         audio: Option[Audio] = Option.empty,
                         /** Optional. Message is a general file, information about the
                           * file*/
                         document: Option[Document] = Option.empty,
                         /** Optional. Message is an animation, information about the
                           * animation. For backward compatibility, when this field is
                           * set, the document field will also be set*/
                         animation: Option[Animation] = Option.empty,
                         /** Optional. Message is a game, information about the game.
                           * More about games »*/
                         game: Option[Game] = Option.empty,
                         /** Optional. Message is a photo, available sizes of the photo*/
                         photo: List[PhotoSize] = List.empty,
                         /** Optional. Message is a sticker, information about the
                           * sticker*/
                         sticker: Option[Sticker] = Option.empty,
                         /** Optional. Message is a video, information about the video*/
                         video: Option[Video] = Option.empty,
                         /** Optional. Message is a voice message, information about the
                           * file*/
                         voice: Option[Voice] = Option.empty,
                         /** Optional. Message is a video note, information about the
                           * video message*/
                         videoNote: Option[VideoNote] = Option.empty,
                         /** Optional. Caption for the animation, audio, document,
                           * photo, video or voice, 0-1024 characters*/
                         caption: Option[String] = Option.empty,
                         /** Optional. Message is a shared contact, information about
                           * the contact*/
                         contact: Option[Contact] = Option.empty,
                         /** Optional. Message is a shared location, information about
                           * the location*/
                         location: Option[Location] = Option.empty,
                         /** Optional. Message is a venue, information about the venue*/
                         venue: Option[Venue] = Option.empty,
                         /** Optional. Message is a native poll, information about the
                           * poll*/
                         poll: Option[Poll] = Option.empty,
                         /** Optional. New members that were added to the group or
                           * supergroup and information about them (the bot itself may be
                           * one of these members)*/
                         newChatMembers: List[User] = List.empty,
                         /** Optional. A member was removed from the group, information
                           * about them (this member may be the bot itself)*/
                         leftChatMember: Option[User] = Option.empty,
                         /** Optional. A chat title was changed to this value*/
                         newChatTitle: Option[String] = Option.empty,
                         /** Optional. A chat photo was change to this value*/
                         newChatPhoto: List[PhotoSize] = List.empty,
                         /** Optional. Service message: the chat photo was deleted*/
                         deleteChatPhoto: Option[Boolean] = Option.empty,
                         /** Optional. Service message: the group has been created*/
                         groupChatCreated: Option[Boolean] = Option.empty,
                         /** Optional. Service message: the supergroup has been created.
                           * This field can‘t be received in a message coming through
                           * updates, because bot can’t be a member of a supergroup when
                           * it is created. It can only be found in reply_to_message if
                           * someone replies to a very first message in a directly
                           * created supergroup.*/
                         supergroupChatCreated: Option[Boolean] = Option.empty,
                         /** Optional. Service message: the channel has been created.
                           * This field can‘t be received in a message coming through
                           * updates, because bot can’t be a member of a channel when it
                           * is created. It can only be found in reply_to_message if
                           * someone replies to a very first message in a channel.*/
                         channelChatCreated: Option[Boolean] = Option.empty,
                         /** Optional. The group has been migrated to a supergroup with
                           * the specified identifier. This number may be greater than 32
                           * bits and some programming languages may have
                           * difficulty/silent defects in interpreting it. But it is
                           * smaller than 52 bits, so a signed 64 bit integer or
                           * double-precision float type are safe for storing this
                           * identifier.*/
                         migrateToChatId: Option[Int] = Option.empty,
                         /** Optional. The supergroup has been migrated from a group
                           * with the specified identifier. This number may be greater
                           * than 32 bits and some programming languages may have
                           * difficulty/silent defects in interpreting it. But it is
                           * smaller than 52 bits, so a signed 64 bit integer or
                           * double-precision float type are safe for storing this
                           * identifier.*/
                         migrateFromChatId: Option[Int] = Option.empty,
                         /** Optional. Specified message was pinned. Note that the
                           * Message object in this field will not contain further
                           * reply_to_message fields even if it is itself a reply.*/
                         pinnedMessage: Option[Message] = Option.empty,
                         /** Optional. Message is an invoice for a payment, information
                           * about the invoice. More about payments »*/
                         invoice: Option[Invoice] = Option.empty,
                         /** Optional. Message is a service message about a successful
                           * payment, information about the payment. More about payments
                           * »*/
                         successfulPayment: Option[SuccessfulPayment] = Option.empty,
                         /** Optional. The domain name of the website on which the user
                           * has logged in. More about Telegram Login »*/
                         connectedWebsite: Option[String] = Option.empty,
                         /** Optional. Telegram Passport data*/
                         passportData: Option[PassportData] = Option.empty,
                         /** Optional. Inline keyboard attached to the message.
                           * login_url buttons are represented as ordinary url buttons.*/
                         replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
