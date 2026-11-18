package fix

import iozhik.OpenEnum
import telegramium.bots.*
import telegramium.bots.high.Methods
import telegramium.bots.high.Methods.sendMessage

object Telegramium11_1004:
  object Other:
    def sendMessage(chatId: Long, text: String): String = text

  val imported = sendMessage(chatId = Some(ChatIntId(1)), text = "hi")
  val multiLine = Methods.sendMessage(
    chatId = Some(ChatIntId(1)),
    text = "hi",
    parseMode = Some(Html)
  )
  val chatIdByName        = Methods.sendMessage(chatId = Some(ChatIntId(1)), text = "hi")
  val namedThenPositional = Methods.sendMessage(chatId = Some(ChatIntId(1)), text = "hi")
  val otherOrder          = Methods.sendMessage(text = "hi", chatId = Some(ChatStrId("@channel")))
  val photo               = Methods.sendPhoto(chatId = Some(ChatIntId(1)), photo = InputLinkFile("https://example.com/a.png"))
  val contact             = Methods.sendContact(chatId = Some(ChatIntId(1)), phoneNumber = "+100", firstName = "Ann")
  val venue               = Methods.sendVenue(chatId = Some(ChatIntId(1)), latitude = 1f, longitude = 2f, title = "title", address = "address")
  val unrelated           = Other.sendMessage(1L, "hi")
  val notMoved            = Methods.answerCallbackQuery("id", Some("text"))
  val areaPosition        = AreaPosition(1f, 2f, 3f, 4f, 0f, 0f)

  def tag(e: OpenEnum[MessageEntity]): String = e match
    case OpenEnum.Unknown(tag, _) => tag
    case OpenEnum.Known(_)     => ""
