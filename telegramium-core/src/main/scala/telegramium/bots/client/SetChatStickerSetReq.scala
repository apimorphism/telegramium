package telegramium.bots.client

import telegramium.bots.ChatId

final case class SetChatStickerSetReq(
                                      /** Unique identifier for the target chat or username of the
                                        * target supergroup (in the format &#064;supergroupusername)*/
                                      chatId: ChatId,
                                      /** Name of the sticker set to be set as the group sticker set*/
                                      stickerSetName: String)
