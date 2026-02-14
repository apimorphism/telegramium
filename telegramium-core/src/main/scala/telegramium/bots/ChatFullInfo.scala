package telegramium.bots

/** This object contains full information about a chat.
  *
  * @param id
  *   Unique identifier for this chat. This number may have more than 32 significant bits and some programming languages
  *   may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit
  *   integer or double-precision float type are safe for storing this identifier.
  * @param type
  *   Type of the chat, can be either “private”, “group”, “supergroup” or “channel”
  * @param accentColorId
  *   Identifier of the accent color for the chat name and backgrounds of the chat photo, reply header, and link
  *   preview. See accent colors for more details.
  * @param maxReactionCount
  *   The maximum number of reactions that can be set on a message in the chat
  * @param acceptedGiftTypes
  *   Information about types of gifts that are accepted by the chat or by the corresponding user for private chats
  * @param title
  *   Optional. Title, for supergroups, channels and group chats
  * @param username
  *   Optional. Username, for private chats, supergroups and channels if available
  * @param firstName
  *   Optional. First name of the other party in a private chat
  * @param lastName
  *   Optional. Last name of the other party in a private chat
  * @param isForum
  *   Optional. True, if the supergroup chat is a forum (has topics enabled)
  * @param isDirectMessages
  *   Optional. True, if the chat is the direct messages chat of a channel
  * @param photo
  *   Optional. Chat photo
  * @param activeUsernames
  *   Optional. If non-empty, the list of all active chat usernames; for private chats, supergroups and channels
  * @param birthdate
  *   Optional. For private chats, the date of birth of the user
  * @param businessIntro
  *   Optional. For private chats with business accounts, the intro of the business
  * @param businessLocation
  *   Optional. For private chats with business accounts, the location of the business
  * @param businessOpeningHours
  *   Optional. For private chats with business accounts, the opening hours of the business
  * @param personalChat
  *   Optional. For private chats, the personal channel of the user
  * @param parentChat
  *   Optional. Information about the corresponding channel chat; for direct messages chats only
  * @param availableReactions
  *   Optional. List of available reactions allowed in the chat. If omitted, then all emoji reactions are allowed.
  * @param backgroundCustomEmojiId
  *   Optional. Custom emoji identifier of the emoji chosen by the chat for the reply header and link preview background
  * @param profileAccentColorId
  *   Optional. Identifier of the accent color for the chat's profile background. See profile accent colors for more
  *   details.
  * @param profileBackgroundCustomEmojiId
  *   Optional. Custom emoji identifier of the emoji chosen by the chat for its profile background
  * @param emojiStatusCustomEmojiId
  *   Optional. Custom emoji identifier of the emoji status of the chat or the other party in a private chat
  * @param emojiStatusExpirationDate
  *   Optional. Expiration date of the emoji status of the chat or the other party in a private chat, in Unix time, if
  *   any
  * @param bio
  *   Optional. Bio of the other party in a private chat
  * @param hasPrivateForwards
  *   Optional. True, if privacy settings of the other party in the private chat allows to use tg://user?id=<user_id>
  *   links only in chats with the user
  * @param hasRestrictedVoiceAndVideoMessages
  *   Optional. True, if the privacy settings of the other party restrict sending voice and video note messages in the
  *   private chat
  * @param joinToSendMessages
  *   Optional. True, if users need to join the supergroup before they can send messages
  * @param joinByRequest
  *   Optional. True, if all users directly joining the supergroup without using an invite link need to be approved by
  *   supergroup administrators
  * @param description
  *   Optional. Description, for groups, supergroups and channel chats
  * @param inviteLink
  *   Optional. Primary invite link, for groups, supergroups and channel chats
  * @param pinnedMessage
  *   Optional. The most recent pinned message (by sending date)
  * @param permissions
  *   Optional. Default chat member permissions, for groups and supergroups
  * @param canSendPaidMedia
  *   Optional. True, if paid media messages can be sent or forwarded to the channel chat. The field is available only
  *   for channel chats.
  * @param slowModeDelay
  *   Optional. For supergroups, the minimum allowed delay between consecutive messages sent by each unprivileged user;
  *   in seconds
  * @param unrestrictBoostCount
  *   Optional. For supergroups, the minimum number of boosts that a non-administrator user needs to add in order to
  *   ignore slow mode and chat permissions
  * @param messageAutoDeleteTime
  *   Optional. The time after which all messages sent to the chat will be automatically deleted; in seconds
  * @param hasAggressiveAntiSpamEnabled
  *   Optional. True, if aggressive anti-spam checks are enabled in the supergroup. The field is only available to chat
  *   administrators.
  * @param hasHiddenMembers
  *   Optional. True, if non-administrators can only get the list of bots and administrators in the chat
  * @param hasProtectedContent
  *   Optional. True, if messages from the chat can't be forwarded to other chats
  * @param hasVisibleHistory
  *   Optional. True, if new chat members will have access to old messages; available only to chat administrators
  * @param stickerSetName
  *   Optional. For supergroups, name of the group sticker set
  * @param canSetStickerSet
  *   Optional. True, if the bot can change the group sticker set
  * @param customEmojiStickerSetName
  *   Optional. For supergroups, the name of the group's custom emoji sticker set. Custom emoji from this set can be
  *   used by all users and bots in the group.
  * @param linkedChatId
  *   Optional. Unique identifier for the linked chat, i.e. the discussion group identifier for a channel and vice
  *   versa; for supergroups and channel chats. This identifier may be greater than 32 bits and some programming
  *   languages may have difficulty/silent defects in interpreting it. But it is smaller than 52 bits, so a signed 64
  *   bit integer or double-precision float type are safe for storing this identifier.
  * @param location
  *   Optional. For supergroups, the location to which the supergroup is connected
  * @param rating
  *   Optional. For private chats, the rating of the user if any
  * @param firstProfileAudio
  *   Optional. For private chats, the first audio added to the profile of the user
  * @param uniqueGiftColors
  *   Optional. The color scheme based on a unique gift that must be used for the chat's name, message replies and link
  *   previews
  * @param paidMessageStarCount
  *   Optional. The number of Telegram Stars a general user have to pay to send a message to the chat
  */
final case class ChatFullInfo(
  id: Long,
  `type`: String,
  accentColorId: Int,
  maxReactionCount: Int,
  acceptedGiftTypes: AcceptedGiftTypes,
  title: Option[String] = Option.empty,
  username: Option[String] = Option.empty,
  firstName: Option[String] = Option.empty,
  lastName: Option[String] = Option.empty,
  isForum: Option[Boolean] = Option.empty,
  isDirectMessages: Option[Boolean] = Option.empty,
  photo: Option[ChatPhoto] = Option.empty,
  activeUsernames: List[String] = List.empty,
  birthdate: Option[Birthdate] = Option.empty,
  businessIntro: Option[BusinessIntro] = Option.empty,
  businessLocation: Option[BusinessLocation] = Option.empty,
  businessOpeningHours: Option[BusinessOpeningHours] = Option.empty,
  personalChat: Option[Chat] = Option.empty,
  parentChat: Option[Chat] = Option.empty,
  availableReactions: List[iozhik.OpenEnum[ReactionType]] = List.empty,
  backgroundCustomEmojiId: Option[String] = Option.empty,
  profileAccentColorId: Option[Int] = Option.empty,
  profileBackgroundCustomEmojiId: Option[String] = Option.empty,
  emojiStatusCustomEmojiId: Option[String] = Option.empty,
  emojiStatusExpirationDate: Option[Long] = Option.empty,
  bio: Option[String] = Option.empty,
  hasPrivateForwards: Option[Boolean] = Option.empty,
  hasRestrictedVoiceAndVideoMessages: Option[Boolean] = Option.empty,
  joinToSendMessages: Option[Boolean] = Option.empty,
  joinByRequest: Option[Boolean] = Option.empty,
  description: Option[String] = Option.empty,
  inviteLink: Option[String] = Option.empty,
  pinnedMessage: Option[Message] = Option.empty,
  permissions: Option[ChatPermissions] = Option.empty,
  canSendPaidMedia: Option[Boolean] = Option.empty,
  slowModeDelay: Option[Int] = Option.empty,
  unrestrictBoostCount: Option[Int] = Option.empty,
  messageAutoDeleteTime: Option[Int] = Option.empty,
  hasAggressiveAntiSpamEnabled: Option[Boolean] = Option.empty,
  hasHiddenMembers: Option[Boolean] = Option.empty,
  hasProtectedContent: Option[Boolean] = Option.empty,
  hasVisibleHistory: Option[Boolean] = Option.empty,
  stickerSetName: Option[String] = Option.empty,
  canSetStickerSet: Option[Boolean] = Option.empty,
  customEmojiStickerSetName: Option[String] = Option.empty,
  linkedChatId: Option[Long] = Option.empty,
  location: Option[ChatLocation] = Option.empty,
  rating: Option[UserRating] = Option.empty,
  firstProfileAudio: Option[Audio] = Option.empty,
  uniqueGiftColors: Option[UniqueGiftColors] = Option.empty,
  paidMessageStarCount: Option[Int] = Option.empty
)
