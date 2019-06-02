package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId
import io.github.fperiodic.apimorphism.telegramium.bots.IFile

final case class SetChatPhotoReq(
                                 /** Unique identifier for the target chat or username of the
                                   * target channel (in the format @channelusername)*/
                                 chatId: ChatId,
                                 /** New chat photo, uploaded using multipart/form-data*/
                                 photo: IFile)
