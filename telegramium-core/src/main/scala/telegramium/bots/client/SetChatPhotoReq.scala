package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile

/** @param chatId Unique identifier for the target chat or username of the
  * target channel (in the format &#064;channelusername)
  * @param photo New chat photo, uploaded using multipart/form-data */
final case class SetChatPhotoReq(chatId: ChatId, photo: IFile)
