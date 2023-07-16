package telegramium.bots.examples

import cats.Parallel
import cats.effect.Async
import cats.syntax.all.*

import telegramium.bots.CallbackQuery
import telegramium.bots.ChatIntId
import telegramium.bots.Message
import telegramium.bots.high.Api
import telegramium.bots.high.LongPollBot
import telegramium.bots.high.implicits.*
import telegramium.bots.high.keyboards.InlineKeyboardButtons
import telegramium.bots.high.keyboards.InlineKeyboardMarkups

/** Show how to use inline keyboards
  */
class IceCreamParlorBot[F[_]]()(implicit
  bot: Api[F],
  asyncF: Async[F],
  parallel: Parallel[F]
) extends LongPollBot[F](bot) {

  override def onMessage(msg: Message): F[Unit] =
    msg.text.filter(_.toLowerCase.startsWith("/order")).fold(asyncF.unit) { _ =>
      sendMessage(
        chatId = ChatIntId(msg.chat.id),
        text = "Choose your flavor:",
        replyMarkup = Some(
          InlineKeyboardMarkups.singleColumn(
            List(
              InlineKeyboardButtons.callbackData("Vanilla", callbackData = "vanilla"),
              InlineKeyboardButtons.callbackData("Chocolate", callbackData = "chocolate"),
              InlineKeyboardButtons.callbackData("Cookies & Cream", callbackData = "cookies_cream"),
              InlineKeyboardButtons.callbackData("Mint Chocolate Chip", callbackData = "mint_chocolate_chip"),
              InlineKeyboardButtons
                .callbackData("Chocolate Chip Cookie Dough", callbackData = "chocolate_chip_cookie_dough")
            )
          )
        )
      ).exec.void
    }

  override def onCallbackQuery(query: CallbackQuery): F[Unit] =
    query.data
      .flatMap { flavor =>
        query.message.map { msg =>
          answerCallbackQuery(callbackQueryId = query.id).exec >>
            editMessageReplyMarkup(
              chatId = Some(ChatIntId(msg.chat.id)),
              messageId = Some(msg.messageId),
              replyMarkup = None
            ).exec >>
            editMessageText(
              chatId = Some(ChatIntId(msg.chat.id)),
              messageId = Some(msg.messageId),
              text = s"You have chosen: $flavor"
            ).exec.void
        }
      }
      .getOrElse(asyncF.unit)

}
