package telegramium.bots.client

import telegramium.bots.ChatId

final case class PromoteChatMemberReq(
                                      /** Unique identifier for the target chat or username of the
                                        * target channel (in the format @channelusername)*/
                                      chatId: ChatId,
                                      /** Unique identifier of the target user*/
                                      userId: Int,
                                      /** Pass True, if the administrator's presence in the chat is
                                        * hidden*/
                                      isAnonymous: Option[Boolean] = Option.empty,
                                      /** Pass True, if the administrator can change chat title,
                                        * photo and other settings*/
                                      canChangeInfo: Option[Boolean] = Option.empty,
                                      /** Pass True, if the administrator can create channel posts,
                                        * channels only*/
                                      canPostMessages: Option[Boolean] = Option.empty,
                                      /** Pass True, if the administrator can edit messages of other
                                        * users and can pin messages, channels only*/
                                      canEditMessages: Option[Boolean] = Option.empty,
                                      /** Pass True, if the administrator can delete messages of
                                        * other users*/
                                      canDeleteMessages: Option[Boolean] = Option.empty,
                                      /** Pass True, if the administrator can invite new users to the
                                        * chat*/
                                      canInviteUsers: Option[Boolean] = Option.empty,
                                      /** Pass True, if the administrator can restrict, ban or unban
                                        * chat members*/
                                      canRestrictMembers: Option[Boolean] = Option.empty,
                                      /** Pass True, if the administrator can pin messages,
                                        * supergroups only*/
                                      canPinMessages: Option[Boolean] = Option.empty,
                                      /** Pass True, if the administrator can add new administrators
                                        * with a subset of their own privileges or demote
                                        * administrators that he has promoted, directly or indirectly
                                        * (promoted by administrators that were appointed by him)*/
                                      canPromoteMembers: Option[Boolean] = Option.empty)
