package telegramium.bots.examples

import cats.effect.{Sync, Timer}
import cats.syntax.flatMap._
import cats.syntax.functor._
import telegramium.bots.client.{AnswerCallbackQueryReq, Api, EditMessageReplyMarkupReq, EditMessageTextReq, SendMessageReq}
import telegramium.bots.high.LongPollBot
import telegramium.bots.{CallbackQuery, ChatIntId, InlineKeyboardButton, InlineKeyboardMarkup, Message}

/**
 * Show how to use inline keyboards
 */
class IceCreamParlorBot[F[_]](bot: Api[F])(implicit syncF: Sync[F], timer: Timer[F]) extends LongPollBot[F](bot) {
  override def onMessage(msg: Message): F[Unit] =
    msg.text.filter(_.toLowerCase.startsWith("/order")).fold(syncF.unit) { _ =>
      bot.sendMessage(SendMessageReq(
        chatId = ChatIntId(msg.chat.id),
        text = "Choose your flavor:",
        replyMarkup = Some(InlineKeyboardMarkup(List(
          List(InlineKeyboardButton("Vanilla", callbackData = Some("vanilla"))),
          List(InlineKeyboardButton("Chocolate", callbackData = Some("chocolate"))),
          List(InlineKeyboardButton("Cookies & Cream", callbackData = Some("cookies_cream"))),
          List(InlineKeyboardButton("Mint Chocolate Chip", callbackData = Some("mint_chocolate_chip"))),
          List(InlineKeyboardButton("Chocolate Chip Cookie Dough", callbackData = Some("chocolate_chip_cookie_dough")))
        )))
      )).void
    }

  override def onCallbackQuery(query: CallbackQuery): F[Unit] =
    query.data.flatMap { flavor =>
      query.message.map { msg =>
        bot.answerCallbackQuery(AnswerCallbackQueryReq(callbackQueryId = query.id)) >>
          bot.editMessageReplyMarkup(EditMessageReplyMarkupReq(
            chatId = Some(ChatIntId(msg.chat.id)),
            messageId = Some(msg.messageId),
            replyMarkup = None
          )) >>
          bot.editMessageText(EditMessageTextReq(
            chatId = Some(ChatIntId(msg.chat.id)),
            messageId = Some(msg.messageId),
            text = s"You have chosen: $flavor"
          )).void
      }
    }.getOrElse(syncF.unit)
}
