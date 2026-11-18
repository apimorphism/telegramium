/*
rule = Telegramium11_1004
 */
package fix

import iozhik.OpenEnum
import telegramium.bots.*
import telegramium.bots.high.Methods
import telegramium.bots.high.Methods.sendMessage

object Telegramium11_1004:
  object Other:
    def sendMessage(chatId: Long, text: String): String = text

  val imported = sendMessage(ChatIntId(1), "hi")
  val multiLine = Methods.sendMessage(
    ChatIntId(1),
    "hi",
    parseMode = Some(Html)
  )
  val chatIdByName        = Methods.sendMessage(chatId = ChatIntId(1), text = "hi")
  val namedThenPositional = Methods.sendMessage(chatId = ChatIntId(1), "hi")
  val otherOrder          = Methods.sendMessage(text = "hi", chatId = ChatStrId("@channel"))
  val photo               = Methods.sendPhoto(ChatIntId(1), InputLinkFile("https://example.com/a.png"))
  val contact             = Methods.sendContact(ChatIntId(1), "+100", "Ann")
  val venue               = Methods.sendVenue(ChatIntId(1), 1f, 2f, "title", "address")
  val unrelated           = Other.sendMessage(1L, "hi")
  val notMoved            = Methods.answerCallbackQuery("id", Some("text"))
  val areaPosition        = StoryAreaPosition(1f, 2f, 3f, 4f, 0f, 0f)

  def tag(e: OpenEnum[MessageEntity]): String = e match
    case OpenEnum.Unknown(tag) => tag
    case OpenEnum.Known(_)     => ""
