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
  *   preview. See <a href="https://core.telegram.org/bots/api#accent-colors">accent colors</a> for more details.
  * @param maxReactionCount
  *   The maximum number of reactions that can be set on a message in the chat
  * @param acceptedGiftTypes
  *   Information about types of gifts that are accepted by the chat or by the corresponding user for private chats
  * @param title
  *   <em>Optional</em>. Title, for supergroups, channels and group chats
  * @param username
  *   <em>Optional</em>. Username, for private chats, supergroups and channels if available
  * @param firstName
  *   <em>Optional</em>. First name of the other party in a private chat
  * @param lastName
  *   <em>Optional</em>. Last name of the other party in a private chat
  * @param isForum
  *   <em>Optional</em>. <em>True</em>, if the supergroup chat is a forum (has <a
  *   href="https://telegram.org/blog/topics-in-groups-collectible-usernames#topics-in-groups">topics</a> enabled)
  * @param isDirectMessages
  *   <em>Optional</em>. <em>True</em>, if the chat is the direct messages chat of a channel
  * @param photo
  *   <em>Optional</em>. Chat photo
  * @param activeUsernames
  *   <em>Optional</em>. If non-empty, the list of all <a
  *   href="https://telegram.org/blog/topics-in-groups-collectible-usernames#collectible-usernames">active chat
  *   usernames</a>; for private chats, supergroups and channels
  * @param birthdate
  *   <em>Optional</em>. For private chats, the date of birth of the user
  * @param businessIntro
  *   <em>Optional</em>. For private chats with business accounts, the intro of the business
  * @param businessLocation
  *   <em>Optional</em>. For private chats with business accounts, the location of the business
  * @param businessOpeningHours
  *   <em>Optional</em>. For private chats with business accounts, the opening hours of the business
  * @param personalChat
  *   <em>Optional</em>. For private chats, the personal channel of the user
  * @param parentChat
  *   <em>Optional</em>. Information about the corresponding channel chat; for direct messages chats only
  * @param availableReactions
  *   <em>Optional</em>. List of available reactions allowed in the chat. If omitted, then all <a
  *   href="https://core.telegram.org/bots/api#reactiontypeemoji">emoji reactions</a> are allowed.
  * @param backgroundCustomEmojiId
  *   <em>Optional</em>. Custom emoji identifier of the emoji chosen by the chat for the reply header and link preview
  *   background
  * @param profileAccentColorId
  *   <em>Optional</em>. Identifier of the accent color for the chat's profile background. See <a
  *   href="https://core.telegram.org/bots/api#profile-accent-colors">profile accent colors</a> for more details.
  * @param profileBackgroundCustomEmojiId
  *   <em>Optional</em>. Custom emoji identifier of the emoji chosen by the chat for its profile background
  * @param emojiStatusCustomEmojiId
  *   <em>Optional</em>. Custom emoji identifier of the emoji status of the chat or the other party in a private chat
  * @param emojiStatusExpirationDate
  *   <em>Optional</em>. Expiration date of the emoji status of the chat or the other party in a private chat, in Unix
  *   time, if any
  * @param bio
  *   <em>Optional</em>. Bio of the other party in a private chat
  * @param hasPrivateForwards
  *   <em>Optional</em>. <em>True</em>, if privacy settings of the other party in the private chat allows to use
  *   <code>tg://user?id=ᐸuser_idᐳ</code> links only in chats with the user
  * @param hasRestrictedVoiceAndVideoMessages
  *   <em>Optional</em>. <em>True</em>, if the privacy settings of the other party restrict sending voice and video note
  *   messages in the private chat
  * @param joinToSendMessages
  *   <em>Optional</em>. <em>True</em>, if users need to join the supergroup before they can send messages
  * @param joinByRequest
  *   <em>Optional</em>. <em>True</em>, if all users directly joining the supergroup without using an invite link need
  *   to be approved by supergroup administrators
  * @param description
  *   <em>Optional</em>. Description, for groups, supergroups and channel chats
  * @param inviteLink
  *   <em>Optional</em>. Primary invite link, for groups, supergroups and channel chats
  * @param pinnedMessage
  *   <em>Optional</em>. The most recent pinned message (by sending date)
  * @param permissions
  *   <em>Optional</em>. Default chat member permissions, for groups and supergroups
  * @param canSendPaidMedia
  *   <em>Optional</em>. <em>True</em>, if paid media messages can be sent or forwarded to the channel chat. The field
  *   is available only for channel chats.
  * @param slowModeDelay
  *   <em>Optional</em>. For supergroups, the minimum allowed delay between consecutive messages sent by each
  *   unprivileged user; in seconds
  * @param unrestrictBoostCount
  *   <em>Optional</em>. For supergroups, the minimum number of boosts that a non-administrator user needs to add in
  *   order to ignore slow mode and chat permissions
  * @param messageAutoDeleteTime
  *   <em>Optional</em>. The time after which all messages sent to the chat will be automatically deleted; in seconds
  * @param hasAggressiveAntiSpamEnabled
  *   <em>Optional</em>. <em>True</em>, if aggressive anti-spam checks are enabled in the supergroup. The field is only
  *   available to chat administrators.
  * @param hasHiddenMembers
  *   <em>Optional</em>. <em>True</em>, if non-administrators can only get the list of bots and administrators in the
  *   chat
  * @param hasProtectedContent
  *   <em>Optional</em>. <em>True</em>, if messages from the chat can't be forwarded to other chats
  * @param hasVisibleHistory
  *   <em>Optional</em>. <em>True</em>, if new chat members will have access to old messages; available only to chat
  *   administrators
  * @param stickerSetName
  *   <em>Optional</em>. For supergroups, name of the group sticker set
  * @param canSetStickerSet
  *   <em>Optional</em>. <em>True</em>, if the bot can change the group sticker set
  * @param customEmojiStickerSetName
  *   <em>Optional</em>. For supergroups, the name of the group's custom emoji sticker set. Custom emoji from this set
  *   can be used by all users and bots in the group.
  * @param linkedChatId
  *   <em>Optional</em>. Unique identifier for the linked chat,
  * i.e. the discussion group identifier for a channel and vice versa; for supergroups and channel chats. This
  * identifier may be greater than 32 bits and some programming languages may have difficulty/silent defects in
  * interpreting it. But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe
  * for storing this identifier.
  * @param location
  *   <em>Optional</em>. For supergroups, the location to which the supergroup is connected
  * @param rating
  *   <em>Optional</em>. For private chats, the rating of the user if any
  * @param firstProfileAudio
  *   <em>Optional</em>. For private chats, the first audio added to the profile of the user
  * @param uniqueGiftColors
  *   <em>Optional</em>. The color scheme based on a unique gift that must be used for the chat's name, message replies
  *   and link previews
  * @param paidMessageStarCount
  *   <em>Optional</em>. The number of Telegram Stars a general user has to pay to send a message to the chat
  * @param guardBot
  *   <em>Optional</em>. The bot that processes join request queries in the chat. The field is only available to chat
  *   administrators.
  * @param community
  *   <em>Optional</em>. The <a href="https://core.telegram.org/bots/api#community">Community</a> to which the chat
  *   belongs
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
  availableReactions: Option[List[iozhik.OpenEnum[ReactionType]]] = Option.empty,
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
  paidMessageStarCount: Option[Int] = Option.empty,
  guardBot: Option[User] = Option.empty,
  community: Option[Community] = Option.empty
)
