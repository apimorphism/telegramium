package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel in the format <code>&#064;username</code>
  * @param photo
  *   New chat photo, uploaded using multipart/form-data
  */
final case class SetChatPhotoReq(chatId: ChatId, photo: IFile)
