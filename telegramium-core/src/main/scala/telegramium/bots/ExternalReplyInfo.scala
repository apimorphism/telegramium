package telegramium.bots

/** This object contains information about a message that is being replied to, which may come from another chat or forum
  * topic.
  *
  * @param origin
  *   Origin of the message replied to by the given message
  * @param chat
  *   Optional. Chat the original message belongs to. Available only if the chat is a supergroup or a channel.
  * @param messageId
  *   Optional. Unique message identifier inside the original chat. Available only if the original chat is a supergroup
  *   or a channel.
  * @param linkPreviewOptions
  *   Optional. Options used for link preview generation for the original message, if it is a text message
  * @param animation
  *   Optional. Message is an animation, information about the animation
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
  * @param hasMediaSpoiler
  *   Optional. True, if the message media is covered by a spoiler animation
  * @param contact
  *   Optional. Message is a shared contact, information about the contact
  * @param dice
  *   Optional. Message is a dice with random value
  * @param game
  *   Optional. Message is a game, information about the game.
  * @param giveaway
  *   Optional. Message is a scheduled giveaway, information about the giveaway
  * @param giveawayWinners
  *   Optional. A giveaway with public winners was completed
  * @param invoice
  *   Optional. Message is an invoice for a payment, information about the invoice.
  * @param location
  *   Optional. Message is a shared location, information about the location
  * @param poll
  *   Optional. Message is a native poll, information about the poll
  * @param venue
  *   Optional. Message is a venue, information about the venue
  */
final case class ExternalReplyInfo(
  origin: MessageOrigin,
  chat: Option[Chat] = Option.empty,
  messageId: Option[Int] = Option.empty,
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
  hasMediaSpoiler: Option[Boolean] = Option.empty,
  contact: Option[Contact] = Option.empty,
  dice: Option[Dice] = Option.empty,
  game: Option[Game] = Option.empty,
  giveaway: Option[Giveaway] = Option.empty,
  giveawayWinners: Option[GiveawayWinners] = Option.empty,
  invoice: Option[Invoice] = Option.empty,
  location: Option[Location] = Option.empty,
  poll: Option[Poll] = Option.empty,
  venue: Option[Venue] = Option.empty
)
