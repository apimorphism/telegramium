package telegramium.bots.examples

import cats.Parallel
import cats.effect.{Sync, Timer}
import telegramium.bots.high.implicits._
import telegramium.bots.high.{Api, LongPollBot}

class EchoBot[F[_]]()(implicit
  bot: Api[F],
  syncF: Sync[F],
  timer: Timer[F],
  parallel: Parallel[F]
) extends LongPollBot[F](bot) {

  import cats.syntax.flatMap._
  import cats.syntax.functor._
  import telegramium.bots._

  override def onMessage(msg: Message): F[Unit] = {
    sendMessage(
      chatId = ChatIntId(msg.chat.id),
      text = msg.text.getOrElse("NO_TEXT"),
      replyMarkup = Some(
        InlineKeyboardMarkup(
          inlineKeyboard = List(
            List(
              InlineKeyboardButton("Markdown", callbackData = Some("Markdown")),
              InlineKeyboardButton("Markdown2", callbackData = Some("Markdown2")),
              InlineKeyboardButton("HTML", callbackData = Some("HTML"))
            ),
            List(
              InlineKeyboardButton("Yes", callbackData = Some("Yes")),
              InlineKeyboardButton("No", callbackData = Some("No"))
            ),
            List(
              InlineKeyboardButton(EmojiDice.toString, callbackData = Some("dice")),
              InlineKeyboardButton(EmojiDarts.toString, callbackData = Some("darts")),
              InlineKeyboardButton(EmojiBasketball.toString, callbackData = Some("basketball"))
            ),
            List(
              InlineKeyboardButton("Gimme a quiz", callbackData = Some("quiz"))
            )
          )
        )
      )
    ).exec.void
  }

  override def onCallbackQuery(query: CallbackQuery): F[Unit] = {
    def rollTheDice(chatId: Long, emoji: Emoji = EmojiDice): F[Unit] = {
      sendDice(ChatIntId(chatId), Some(emoji)).exec.void >>
        answerCallbackQuery(callbackQueryId = query.id).exec.void
    }

    def quiz(chatId: Long): F[Unit] = {
      sendPoll(
        chatId = ChatIntId(chatId),
        question = "How much is the fish?",
        `type` = Some("quiz"),
        options = List(
          "3.14",
          "2.72",
          "1.62",
          "Sunshine in the air!"
        ),
        correctOptionId = Some(2),
        isAnonymous = Some(false),
        explanation = Some("https://en.wikipedia.org/wiki/Golden_ratio")
      ).exec.void >>
        answerCallbackQuery(callbackQueryId = query.id).exec.void
    }

    def sendMsg(chatId: Long, text: String, parseMode: ParseMode): F[Unit] = {
      sendMessage(
        chatId = ChatIntId(chatId),
        text = text,
        parseMode = Some(parseMode)
      ).exec.void >> answerCallbackQuery(callbackQueryId = query.id).exec.void
    }

    query.data
      .map {
        case "HTML"       => query.message.fold(syncF.unit)(m => sendMsg(m.chat.id, htmlText, Html))
        case "Markdown"   => query.message.fold(syncF.unit)(m => sendMsg(m.chat.id, markdownText, Markdown))
        case "Markdown2"  => query.message.fold(syncF.unit)(m => sendMsg(m.chat.id, markdown2Text, Markdown2))
        case "dice"       => query.message.fold(syncF.unit)(m => rollTheDice(m.chat.id))
        case "darts"      => query.message.fold(syncF.unit)(m => rollTheDice(m.chat.id, EmojiDarts))
        case "basketball" => query.message.fold(syncF.unit)(m => rollTheDice(m.chat.id, EmojiBasketball))
        case "quiz"       => query.message.fold(syncF.unit)(m => quiz(m.chat.id))
        case x =>
          answerCallbackQuery(
            callbackQueryId = query.id,
            text = Some(s"Your choice is $x")
          ).exec.void
      }
      .getOrElse(syncF.unit)
  }

  override def onInlineQuery(query: InlineQuery): F[Unit] = {
    answerInlineQuery(
      inlineQueryId = query.id,
      results = query.query
        .split(" ")
        .zipWithIndex
        .map { case (word, idx) =>
          InlineQueryResultArticle(
            id = idx.toString,
            title = word,
            inputMessageContent = InputTextMessageContent(messageText = word)
          )
        }
        .toList
    ).exec.void
  }

  override def onChosenInlineResult(inlineResult: ChosenInlineResult): F[Unit] = {
    import io.circe.syntax._
    import telegramium.bots.CirceImplicits._
    syncF.delay {
      println(inlineResult.asJson.spaces4)
    }
  }

  val htmlText =
    """<b>bold</b>, <strong>bold</strong>
      |<i>italic</i>, <em>italic</em>
      |<u>underline</u>, <ins>underline</ins>
      |<s>strikethrough</s>, <strike>strikethrough</strike>, <del>strikethrough</del>
      |<b>bold <i>italic bold <s>italic bold strikethrough</s> <u>underline italic bold</u></i> bold</b>
      |<a href="http://www.example.com/">inline URL</a>
      |<a href="tg://user?id=123456789">inline mention of a user</a>
      |<code>inline fixed-width code</code>
      |<pre>pre-formatted fixed-width code block</pre>
      |<pre><code class="language-python">pre-formatted fixed-width code block written in the Python programming language</code></pre>
      |""".stripMargin

  val markdownText =
    """*bold text*
      |_italic text_
      |[inline URL](http://www.example.com/)
      |[inline mention of a user](tg://user?id=123456789)
      |`inline fixed-width code`
      |```
      |pre-formatted fixed-width code block
      |```
      |```python
      |pre-formatted fixed-width code block written in the Python programming language
      |```
      |""".stripMargin

  val markdown2Text =
    """*bold \*text*
      |_italic \*text_
      |__underline__
      |~strikethrough~
      |*bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
      |[inline URL](http://www.example.com/)
      |[inline mention of a user](tg://user?id=123456789)
      |`inline fixed-width code`
      |```
      |pre-formatted fixed-width code block
      |```
      |```python
      |pre-formatted fixed-width code block written in the Python programming language
      |```
      |""".stripMargin
}
