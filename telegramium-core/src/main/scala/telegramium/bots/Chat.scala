package telegramium.bots

/** This object represents a chat.*/
final case class Chat(
                      /** Unique identifier for this chat. This number may be greater
                        * than 32 bits and some programming languages may have
                        * difficulty/silent defects in interpreting it. But it is
                        * smaller than 52 bits, so a signed 64 bit integer or
                        * double-precision float type are safe for storing this
                        * identifier.*/
                      id: Long,
                      /** Type of chat, can be either “private”, “group”,
                        * “supergroup” or “channel”*/
                      `type`: String,
                      /** Optional. Title, for supergroups, channels and group chats*/
                      title: Option[String] = Option.empty,
                      /** Optional. Username, for private chats, supergroups and
                        * channels if available*/
                      username: Option[String] = Option.empty,
                      /** Optional. First name of the other party in a private chat*/
                      firstName: Option[String] = Option.empty,
                      /** Optional. Last name of the other party in a private chat*/
                      lastName: Option[String] = Option.empty,
                      /** Optional. Chat photo. Returned only in getChat.*/
                      photo: Option[ChatPhoto] = Option.empty,
                      /** Optional. Bio of the other party in a private chat.
                        * Returned only in getChat.*/
                      bio: Option[String] = Option.empty,
                      /** Optional. Description, for groups, supergroups and channel
                        * chats. Returned only in getChat.*/
                      description: Option[String] = Option.empty,
                      /** Optional. Chat invite link, for groups, supergroups and
                        * channel chats. Each administrator in a chat generates their
                        * own invite links, so the bot must first generate the link
                        * using exportChatInviteLink. Returned only in getChat.*/
                      inviteLink: Option[String] = Option.empty,
                      /** Optional. The most recent pinned message (by sending date).
                        * Returned only in getChat.*/
                      pinnedMessage: Option[Message] = Option.empty,
                      /** Optional. Default chat member permissions, for groups and
                        * supergroups. Returned only in getChat.*/
                      permissions: Option[ChatPermissions] = Option.empty,
                      /** Optional. For supergroups, the minimum allowed delay
                        * between consecutive messages sent by each unpriviledged
                        * user. Returned only in getChat.*/
                      slowModeDelay: Option[Int] = Option.empty,
                      /** Optional. For supergroups, name of group sticker set.
                        * Returned only in getChat.*/
                      stickerSetName: Option[String] = Option.empty,
                      /** Optional. True, if the bot can change the group sticker
                        * set. Returned only in getChat.*/
                      canSetStickerSet: Option[Boolean] = Option.empty,
                      /** Optional. Unique identifier for the linked chat, i.e. the
                        * discussion group identifier for a channel and vice versa;
                        * for supergroups and channel chats. This identifier may be
                        * greater than 32 bits and some programming languages may have
                        * difficulty/silent defects in interpreting it. But it is
                        * smaller than 52 bits, so a signed 64 bit integer or
                        * double-precision float type are safe for storing this
                        * identifier. Returned only in getChat.*/
                      linkedChatId: Option[Long] = Option.empty,
                      /** Optional. For supergroups, the location to which the
                        * supergroup is connected. Returned only in getChat.*/
                      location: Option[ChatLocation] = Option.empty)
