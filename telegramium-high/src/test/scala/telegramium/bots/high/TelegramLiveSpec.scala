package telegramium.bots.high

import java.awt.Color
import java.awt.GradientPaint
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import org.http4s.blaze.client.BlazeClientBuilder
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.ChatIntId
import telegramium.bots.InputLinkFile
import telegramium.bots.InputMediaPhoto
import telegramium.bots.InputPartFile
import telegramium.bots.InputRichMessage
import telegramium.bots.client.Method
import telegramium.bots.high.keyboards.InlineKeyboardButtons
import telegramium.bots.high.keyboards.InlineKeyboardMarkups

/** Replays past bugs against real Telegram, for what only Telegram decides: whether it rejects a Method, an attachment,
  * or ignores a field. Each scenario is checked through the Message Telegram returns. Needs TELEGRAM_LIVE_BOT_TOKEN and
  * TELEGRAM_LIVE_CHAT_ID (see scripts/live-bot-wizard.sh); the bot posts to a private channel.
  */
class TelegramLiveSpec extends AnyFreeSpec with Matchers {

  private def env(name: String): String =
    sys.env.getOrElse(name, fail(s"$name is not set; see CONTRIBUTING.md on running the live test"))

  // read inside each test, so plain `sbt test`, which skips them, doesn't need the variables
  private def chatId = ChatIntId(env("TELEGRAM_LIVE_CHAT_ID").toLong)

  private def execute[A](method: Method[A]): A =
    BlazeClientBuilder[IO].resource
      .use(http => BotApi(http, s"https://api.telegram.org/bot${env("TELEGRAM_LIVE_BOT_TOKEN")}").execute(method))
      .unsafeRunSync()

  // Telegram rejects a tiny image (a 1×1 PNG fails with IMAGE_PROCESS_FAILED), so upload a realistic one: a JPEG of
  // 320×320, which is also the most a thumbnail may be
  private lazy val image: File = {
    val img = new BufferedImage(320, 320, BufferedImage.TYPE_INT_RGB)
    val g   = img.createGraphics()
    g.setPaint(new GradientPaint(0f, 0f, Color.ORANGE, 320f, 320f, Color.BLUE))
    g.fillRect(0, 0, 320, 320)
    g.dispose()
    val file = File.createTempFile("telegramium-live", ".jpg")
    file.deleteOnExit()
    ImageIO.write(img, "jpg", file)
    file
  }

  private val urlPhoto = InputLinkFile("https://telegram.org/img/t_logo.png")

  "a media group with an uploaded embedded file and a URL photo sends both (#666)" taggedAs LiveTest in {
    val messages = execute(
      Methods.sendMediaGroup(
        chatId,
        media = List(
          InputMediaPhoto(media = InputPartFile(image), caption = Some("#666: uploaded, embedded in InputMediaPhoto")),
          InputMediaPhoto(media = urlPhoto, caption = Some("#666: by URL"))
        )
      )
    )
    messages should have size 2
  }

  "a photo sent as an attachment keeps its inline keyboard as reply_markup (#669)" taggedAs LiveTest in {
    // channels don't support custom reply keyboards, only inline ones
    val markup = InlineKeyboardMarkups.singleButton(
      InlineKeyboardButtons.url("Issue #669", "https://github.com/apimorphism/telegramium/issues/669")
    )
    val message = execute(
      Methods.sendPhoto(
        chatId,
        InputPartFile(image),
        caption = Some("#669: uploaded, with reply_markup"),
        replyMarkup = Some(markup)
      )
    )
    message.replyMarkup shouldBe Some(markup)
  }

  "a markdown-only rich message is not rejected as RICH_MESSAGE_EMPTY (#686)" taggedAs LiveTest in {
    val message = execute(
      Methods.sendRichMessage(chatId, InputRichMessage(markdown = Some("**#686**: a *markdown-only* rich message")))
    )
    message.richMessage shouldBe defined
  }

  "a document with an uploaded thumbnail keeps the thumbnail" taggedAs LiveTest in {
    val message = execute(
      Methods.sendDocument(
        chatId,
        InputPartFile(new File(this.getClass.getResource("/document.txt").getFile)),
        thumbnail = Some(InputPartFile(image)),
        caption = Some("Uploaded, with an uploaded thumbnail")
      )
    )
    message.document.flatMap(_.thumbnail) shouldBe defined
  }

}
