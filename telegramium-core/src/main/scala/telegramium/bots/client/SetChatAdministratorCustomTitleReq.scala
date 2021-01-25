package telegramium.bots.client

import telegramium.bots.ChatId

final case class SetChatAdministratorCustomTitleReq(
                                                    /** Unique identifier for the target chat or username of the
                                                      * target supergroup (in the format &#064;supergroupusername)*/
                                                    chatId: ChatId,
                                                    /** Unique identifier of the target user*/
                                                    userId: Int,
                                                    /** New custom title for the administrator; 0-16 characters,
                                                      * emoji are not allowed*/
                                                    customTitle: String)
