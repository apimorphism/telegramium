package telegramium.bots

/** This object represents a chat.
  *
  * @param id
  *   Unique identifier for this chat. This number may have more than 32 significant bits and some programming languages
  *   may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit
  *   integer or double-precision float type are safe for storing this identifier.
  * @param type
  *   Type of chat, can be either “private”, “group”, “supergroup” or “channel”
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
  * @param photo
  *   Optional. Chat photo. Returned only in getChat.
  * @param activeUsernames
  *   Optional. If non-empty, the list of all active chat usernames; for private chats, supergroups and channels.
  *   Returned only in getChat.
  * @param birthdate
  *   Optional. For private chats, the date of birth of the user. Returned only in getChat.
  * @param businessIntro
  *   Optional. For private chats with business accounts, the intro of the business. Returned only in getChat.
  * @param businessLocation
  *   Optional. For private chats with business accounts, the location of the business. Returned only in getChat.
  * @param businessOpeningHours
  *   Optional. For private chats with business accounts, the opening hours of the business. Returned only in getChat.
  * @param personalChat
  *   Optional. For private chats, the personal channel of the user. Returned only in getChat.
  * @param availableReactions
  *   Optional. List of available reactions allowed in the chat. If omitted, then all emoji reactions are allowed.
  *   Returned only in getChat.
  * @param accentColorId
  *   Optional. Identifier of the accent color for the chat name and backgrounds of the chat photo, reply header, and
  *   link preview. See accent colors for more details. Returned only in getChat. Always returned in getChat.
  * @param backgroundCustomEmojiId
  *   Optional. Custom emoji identifier of emoji chosen by the chat for the reply header and link preview background.
  *   Returned only in getChat.
  * @param profileAccentColorId
  *   Optional. Identifier of the accent color for the chat's profile background. See profile accent colors for more
  *   details. Returned only in getChat.
  * @param profileBackgroundCustomEmojiId
  *   Optional. Custom emoji identifier of the emoji chosen by the chat for its profile background. Returned only in
  *   getChat.
  * @param emojiStatusCustomEmojiId
  *   Optional. Custom emoji identifier of the emoji status of the chat or the other party in a private chat. Returned
  *   only in getChat.
  * @param emojiStatusExpirationDate
  *   Optional. Expiration date of the emoji status of the chat or the other party in a private chat, in Unix time, if
  *   any. Returned only in getChat.
  * @param bio
  *   Optional. Bio of the other party in a private chat. Returned only in getChat.
  * @param hasPrivateForwards
  *   Optional. True, if privacy settings of the other party in the private chat allows to use tg://user?id=<user_id>
  *   links only in chats with the user. Returned only in getChat.
  * @param hasRestrictedVoiceAndVideoMessages
  *   Optional. True, if the privacy settings of the other party restrict sending voice and video note messages in the
  *   private chat. Returned only in getChat.
  * @param joinToSendMessages
  *   Optional. True, if users need to join the supergroup before they can send messages. Returned only in getChat.
  * @param joinByRequest
  *   Optional. True, if all users directly joining the supergroup need to be approved by supergroup administrators.
  *   Returned only in getChat.
  * @param description
  *   Optional. Description, for groups, supergroups and channel chats. Returned only in getChat.
  * @param inviteLink
  *   Optional. Primary invite link, for groups, supergroups and channel chats. Returned only in getChat.
  * @param pinnedMessage
  *   Optional. The most recent pinned message (by sending date). Returned only in getChat.
  * @param permissions
  *   Optional. Default chat member permissions, for groups and supergroups. Returned only in getChat.
  * @param slowModeDelay
  *   Optional. For supergroups, the minimum allowed delay between consecutive messages sent by each unprivileged user;
  *   in seconds. Returned only in getChat.
  * @param unrestrictBoostCount
  *   Optional. For supergroups, the minimum number of boosts that a non-administrator user needs to add in order to
  *   ignore slow mode and chat permissions. Returned only in getChat.
  * @param messageAutoDeleteTime
  *   Optional. The time after which all messages sent to the chat will be automatically deleted; in seconds. Returned
  *   only in getChat.
  * @param hasAggressiveAntiSpamEnabled
  *   Optional. True, if aggressive anti-spam checks are enabled in the supergroup. The field is only available to chat
  *   administrators. Returned only in getChat.
  * @param hasHiddenMembers
  *   Optional. True, if non-administrators can only get the list of bots and administrators in the chat. Returned only
  *   in getChat.
  * @param hasProtectedContent
  *   Optional. True, if messages from the chat can't be forwarded to other chats. Returned only in getChat.
  * @param hasVisibleHistory
  *   Optional. True, if new chat members will have access to old messages; available only to chat administrators.
  *   Returned only in getChat.
  * @param stickerSetName
  *   Optional. For supergroups, name of group sticker set. Returned only in getChat.
  * @param canSetStickerSet
  *   Optional. True, if the bot can change the group sticker set. Returned only in getChat.
  * @param customEmojiStickerSetName
  *   Optional. For supergroups, the name of the group's custom emoji sticker set. Custom emoji from this set can be
  *   used by all users and bots in the group. Returned only in getChat.
  * @param linkedChatId
  *   Optional. Unique identifier for the linked chat, i.e. the discussion group identifier for a channel and vice
  *   versa; for supergroups and channel chats. This identifier may be greater than 32 bits and some programming
  *   languages may have difficulty/silent defects in interpreting it. But it is smaller than 52 bits, so a signed 64
  *   bit integer or double-precision float type are safe for storing this identifier. Returned only in getChat.
  * @param location
  *   Optional. For supergroups, the location to which the supergroup is connected. Returned only in getChat.
  */
final case class Chat(
  id: Long,
  `type`: String,
  title: Option[String] = Option.empty,
  username: Option[String] = Option.empty,
  firstName: Option[String] = Option.empty,
  lastName: Option[String] = Option.empty,
  isForum: Option[Boolean] = Option.empty,
  photo: Option[ChatPhoto] = Option.empty,
  activeUsernames: List[String] = List.empty,
  birthdate: Option[Birthdate] = Option.empty,
  businessIntro: Option[BusinessIntro] = Option.empty,
  businessLocation: Option[BusinessLocation] = Option.empty,
  businessOpeningHours: Option[BusinessOpeningHours] = Option.empty,
  personalChat: Option[Chat] = Option.empty,
  availableReactions: List[ReactionType] = List.empty,
  accentColorId: Option[Int] = Option.empty,
  backgroundCustomEmojiId: Option[String] = Option.empty,
  profileAccentColorId: Option[Int] = Option.empty,
  profileBackgroundCustomEmojiId: Option[String] = Option.empty,
  emojiStatusCustomEmojiId: Option[String] = Option.empty,
  emojiStatusExpirationDate: Option[Int] = Option.empty,
  bio: Option[String] = Option.empty,
  hasPrivateForwards: Option[Boolean] = Option.empty,
  hasRestrictedVoiceAndVideoMessages: Option[Boolean] = Option.empty,
  joinToSendMessages: Option[Boolean] = Option.empty,
  joinByRequest: Option[Boolean] = Option.empty,
  description: Option[String] = Option.empty,
  inviteLink: Option[String] = Option.empty,
  pinnedMessage: Option[Message] = Option.empty,
  permissions: Option[ChatPermissions] = Option.empty,
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
  location: Option[ChatLocation] = Option.empty
)
