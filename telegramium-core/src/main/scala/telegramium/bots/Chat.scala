package telegramium.bots

/**
 * This object represents a chat.
 *
 * @param id Unique identifier for this chat. This number may have more
 * than 32 significant bits and some programming languages may
 * have difficulty/silent defects in interpreting it. But it
 * has at most 52 significant bits, so a signed 64-bit integer
 * or double-precision float type are safe for storing this
 * identifier.
 * @param type Type of chat, can be either “private”, “group”,
 * “supergroup” or “channel”
 * @param title Optional. Title, for supergroups, channels and group chats
 * @param username Optional. Username, for private chats, supergroups and
 * channels if available
 * @param firstName Optional. First name of the other party in a private chat
 * @param lastName Optional. Last name of the other party in a private chat
 * @param photo Optional. Chat photo. Returned only in getChat.
 * @param bio Optional. Bio of the other party in a private chat.
 * Returned only in getChat.
 * @param description Optional. Description, for groups, supergroups and channel
 * chats. Returned only in getChat.
 * @param inviteLink Optional. Primary invite link, for groups, supergroups and
 * channel chats. Returned only in getChat.
 * @param pinnedMessage Optional. The most recent pinned message (by sending date).
 * Returned only in getChat.
 * @param permissions Optional. Default chat member permissions, for groups and
 * supergroups. Returned only in getChat.
 * @param slowModeDelay Optional. For supergroups, the minimum allowed delay
 * between consecutive messages sent by each unpriviledged
 * user. Returned only in getChat.
 * @param messageAutoDeleteTime Optional. The time after which all messages sent to the
 * chat will be automatically deleted; in seconds. Returned
 * only in getChat.
 * @param stickerSetName Optional. For supergroups, name of group sticker set.
 * Returned only in getChat.
 * @param canSetStickerSet Optional. True, if the bot can change the group sticker
 * set. Returned only in getChat.
 * @param linkedChatId Optional. Unique identifier for the linked chat, i.e. the
 * discussion group identifier for a channel and vice versa;
 * for supergroups and channel chats. This identifier may be
 * greater than 32 bits and some programming languages may have
 * difficulty/silent defects in interpreting it. But it is
 * smaller than 52 bits, so a signed 64 bit integer or
 * double-precision float type are safe for storing this
 * identifier. Returned only in getChat.
 * @param location Optional. For supergroups, the location to which the
 * supergroup is connected. Returned only in getChat.
 */
final case class Chat(id: Long,
                      `type`: String,
                      title: Option[String] = Option.empty,
                      username: Option[String] = Option.empty,
                      firstName: Option[String] = Option.empty,
                      lastName: Option[String] = Option.empty,
                      photo: Option[ChatPhoto] = Option.empty,
                      bio: Option[String] = Option.empty,
                      description: Option[String] = Option.empty,
                      inviteLink: Option[String] = Option.empty,
                      pinnedMessage: Option[Message] = Option.empty,
                      permissions: Option[ChatPermissions] = Option.empty,
                      slowModeDelay: Option[Int] = Option.empty,
                      messageAutoDeleteTime: Option[Int] = Option.empty,
                      stickerSetName: Option[String] = Option.empty,
                      canSetStickerSet: Option[Boolean] = Option.empty,
                      linkedChatId: Option[Long] = Option.empty,
                      location: Option[ChatLocation] = Option.empty
)
