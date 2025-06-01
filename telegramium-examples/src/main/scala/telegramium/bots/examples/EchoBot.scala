package telegramium.bots.examples

import cats.Parallel
import cats.effect.Async

import telegramium.bots.high.Api
import telegramium.bots.high.LongPollBot
import telegramium.bots.high.implicits.*

class EchoBot[F[_]]()(implicit
  bot: Api[F],
  asyncF: Async[F],
  parallel: Parallel[F]
) extends LongPollBot[F](bot) {

  import cats.syntax.flatMap.*
  import cats.syntax.functor.*
  import telegramium.bots.*

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
              InlineKeyboardButton(EmojiDice.toString(), callbackData = Some("dice")),
              InlineKeyboardButton(EmojiDarts.toString(), callbackData = Some("darts")),
              InlineKeyboardButton(EmojiBasketball.toString(), callbackData = Some("basketball"))
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
    def onMsg(message: Option[MaybeInaccessibleMessage])(f: Message => F[Unit]): F[Unit] =
      message.collect { case m: Message => m }.fold(asyncF.unit)(f)

    def rollTheDice(chatId: Long, emoji: Emoji = EmojiDice): F[Unit] = {
      sendDice(ChatIntId(chatId), emoji = Some(emoji.toString)).exec.void >>
        answerCallbackQuery(callbackQueryId = query.id).exec.void
    }

    def quiz(chatId: Long): F[Unit] = {
      sendPoll(
        chatId = ChatIntId(chatId),
        question = "How much is the fish?",
        `type` = Some("quiz"),
        options = List(
          InputPollOption("3.14"),
          InputPollOption("2.72"),
          InputPollOption("1.62"),
          InputPollOption("Sunshine in the air!")
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
        case ""           => onMsg(query.message)(m => sendMsg(m.chat.id, htmlText, Html))
        case "HTML"       => onMsg(query.message)(m => sendMsg(m.chat.id, htmlText, Html))
        case "Markdown"   => onMsg(query.message)(m => sendMsg(m.chat.id, markdownText, Markdown))
        case "Markdown2"  => onMsg(query.message)(m => sendMsg(m.chat.id, markdown2Text, Markdown2))
        case "dice"       => onMsg(query.message)(m => rollTheDice(m.chat.id))
        case "darts"      => onMsg(query.message)(m => rollTheDice(m.chat.id, EmojiDarts))
        case "basketball" => onMsg(query.message)(m => rollTheDice(m.chat.id, EmojiBasketball))
        case "quiz"       => onMsg(query.message)(m => quiz(m.chat.id))
        case x            =>
          answerCallbackQuery(
            callbackQueryId = query.id,
            text = Some(s"Your choice is $x")
          ).exec.void
      }
      .getOrElse(asyncF.unit)
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
    import io.circe.syntax.*
    import telegramium.bots.CirceImplicits.*
    asyncF.delay {
      println(inlineResult.asJson.spaces4)
    }
  }

  val htmlText: String =
    """<b>bold</b>, <strong>bold</strong>
      |<i>italic</i>, <em>italic</em>
      |<u>underline</u>, <ins>underline</ins>
      |<s>strikethrough</s>, <strike>strikethrough</strike>, <del>strikethrough</del>
      |<b>bold <i>italic bold <s>italic bold strikethrough</s> <u>underline italic bold</u></i> bold</b>
      |<a href="https://www.example.com/">inline URL</a>
      |<a href="tg://user?id=123456789">inline mention of a user</a>
      |<code>inline fixed-width code</code>
      |<pre>pre-formatted fixed-width code block</pre>
      |<pre><code class="language-python">pre-formatted fixed-width code block written in the Python programming language</code></pre>
      |""".stripMargin

  val markdownText: String =
    """*bold text*
      |_italic text_
      |[inline URL](https://www.example.com/)
      |[inline mention of a user](tg://user?id=123456789)
      |`inline fixed-width code`
      |```
      |pre-formatted fixed-width code block
      |```
      |```python
      |pre-formatted fixed-width code block written in the Python programming language
      |```
      |""".stripMargin

  val markdown2Text: String =
    """*bold \*text*
      |_italic \*text_
      |__underline__
      |~strikethrough~
      |*bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
      |[inline URL](https://www.example.com/)
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
