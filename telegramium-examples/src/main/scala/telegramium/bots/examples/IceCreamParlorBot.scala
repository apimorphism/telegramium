package telegramium.bots.examples

import cats.effect.{Sync, Timer}
import cats.syntax.flatMap._
import cats.syntax.functor._
import telegramium.bots.high._
import telegramium.bots.high.implicits._
import telegramium.bots.{CallbackQuery, ChatIntId, Message}

/**
 * Show how to use inline keyboards
 */
class IceCreamParlorBot[F[_]]()(implicit bot: Api[F], syncF: Sync[F], timer: Timer[F]) extends LongPollBot[F](bot) {
  override def onMessage(msg: Message): F[Unit] =
    msg.text.filter(_.toLowerCase.startsWith("/order")).fold(syncF.unit) { _ =>
      sendMessage(
        chatId = ChatIntId(msg.chat.id),
        text = "Choose your flavor:",
        replyMarkup = Some(InlineKeyboardMarkup.singleColumn(
          List(
            InlineKeyboardButton.callbackData("Vanilla", callbackData = "vanilla"),
            InlineKeyboardButton.callbackData("Chocolate", callbackData = "chocolate"),
            InlineKeyboardButton.callbackData("Cookies & Cream", callbackData = "cookies_cream"),
            InlineKeyboardButton.callbackData("Mint Chocolate Chip", callbackData = "mint_chocolate_chip"),
            InlineKeyboardButton.callbackData("Chocolate Chip Cookie Dough", callbackData = "chocolate_chip_cookie_dough")
          )
        ))
      ).exec.void
    }

  override def onCallbackQuery(query: CallbackQuery): F[Unit] =
    query.data.flatMap { flavor =>
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
    }.getOrElse(syncF.unit)
}
