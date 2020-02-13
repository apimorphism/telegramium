package telegramium.bots.examples

import cats.effect.{Sync, Timer}
import telegramium.bots.client.Api
import telegramium.bots.high.LongPollBot

class EchoBot[F[_]](bot: Api[F])(implicit syncF: Sync[F], timer: Timer[F]) extends LongPollBot[F](bot) {

  import cats.syntax.functor._
  import telegramium.bots._
  import telegramium.bots.client._

  override def onMessage(msg: Message): F[Unit] = {
    bot.sendMessage(SendMessageReq(
      chatId = ChatIntId(msg.chat.id),
      text = msg.text.getOrElse("NO_TEXT"),
      replyMarkup = Some(
        InlineKeyboardMarkup(
          inlineKeyboard = List(
            List(
              InlineKeyboardButton("OK", callbackData = Some("OK")),
              InlineKeyboardButton("Cancel", callbackData = Some("Cancel"))
            ),
            List(
              InlineKeyboardButton("Yes", callbackData = Some("Yes")),
              InlineKeyboardButton("No", callbackData = Some("No"))
            ),
          )
        )
      )
    )).void
  }

  override def onCallbackQuery(query: CallbackQuery): F[Unit] = {
    bot.answerCallbackQuery(AnswerCallbackQueryReq(
      callbackQueryId = query.id,
      text = query.data.map(x => s"Your choice is $x")
    )).void
  }

  override def onInlineQuery(query: InlineQuery): F[Unit] = {
    bot.answerInlineQuery(
      AnswerInlineQueryReq(
        inlineQueryId = query.id,
        results = query.query.split(" ").zipWithIndex.map{ case (word, idx) =>
          InlineQueryResultArticle(
            id = idx.toString,
            title = word,
            inputMessageContent = InputTextMessageContent(messageText = word),
          )
        }.toList
      )
    ).void
  }

  override def onChosenInlineResult(inlineResult: ChosenInlineResult): F[Unit] = {
    import telegramium.bots.CirceImplicits._
    import io.circe.syntax._
    syncF.delay {
      println(inlineResult.asJson.spaces4)
    }
  }

}
