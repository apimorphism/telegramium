package telegramium.bots.client

import telegramium.bots.ChatId

/**
 * @param chatId Unique identifier for the target chat or username of the
 * target channel (in the format &#064;channelusername)
 * @param expireDate Point in time (Unix timestamp) when the link will expire
 * @param memberLimit Maximum number of users that can be members of the chat
 * simultaneously after joining the chat via this invite link;
 * 1-99999
 */
final case class CreateChatInviteLinkReq(chatId: ChatId,
                                         expireDate: Option[Int] = Option.empty,
                                         memberLimit: Option[Int] = Option.empty
)
