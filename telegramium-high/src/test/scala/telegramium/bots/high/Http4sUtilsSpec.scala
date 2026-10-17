package telegramium.bots.high

import java.io.File

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import io.circe.Json
import io.circe.parser.parse
import org.http4s.multipart.Multipart
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.ChatIntId
import telegramium.bots.InputPartFile
import telegramium.bots.ReplyParameters
import telegramium.bots.client.Method
import telegramium.bots.high.keyboards.InlineKeyboardButtons
import telegramium.bots.high.keyboards.InlineKeyboardMarkups

class Http4sUtilsSpec extends AnyFreeSpec with Matchers {

  private val file          = new File(this.getClass.getResource("/document.txt").getFile)
  private val keyboard      = InlineKeyboardMarkups.singleButton(InlineKeyboardButtons.callbackData("Press", "pressed"))
  private val keyboardJson  = parse("""{"inline_keyboard":[[{"text":"Press","callback_data":"pressed"}]]}""").toOption.get
  private val replyParamsJs = parse("""{"message_id":1,"quote_entities":[]}""").toOption.get

  private def toMultipart(method: Method[?]): Multipart[IO] = {
    val files = method.payload.files.collect { case (name, InputPartFile(f)) => (name, f) }
    Http4sUtils
      .toMultipartWithFormData[IO](method.payload.json, files.keys.toList, Http4sUtils.toFileDataParts[IO](files))
      .unsafeRunSync()
  }

  private def formFields(multipart: Multipart[IO]): Map[String, String] =
    MultipartFields.formFields(multipart).unsafeRunSync()

  "should send object-valued fields as compact JSON form fields" in {
    val multipart = toMultipart(
      Methods.sendDocument(
        ChatIntId(0),
        InputPartFile(file),
        caption = Some("hi"),
        replyParameters = Some(ReplyParameters(messageId = Some(1))),
        replyMarkup = Some(keyboard)
      )
    )
    val fields = formFields(multipart)

    fields.keySet shouldBe Set("chat_id", "caption", "caption_entities", "reply_parameters", "reply_markup", "method")
    fields("chat_id") shouldBe "0"
    fields("caption") shouldBe "hi"
    fields("caption_entities") shouldBe "[]"
    fields("method") shouldBe "sendDocument"
    fields("reply_markup") shouldBe keyboardJson.noSpaces
    fields("reply_parameters") shouldBe replyParamsJs.noSpaces
    MultipartFields.fileParts(multipart) shouldBe Vector("document" -> "document.txt")
  }

  "should send object-valued fields for other upload methods" in {
    val methods = List(
      Methods.sendPhoto(ChatIntId(0), InputPartFile(file), replyMarkup = Some(keyboard)),
      Methods.sendVideo(ChatIntId(0), InputPartFile(file), replyMarkup = Some(keyboard)),
      Methods.sendAudio(ChatIntId(0), InputPartFile(file), replyMarkup = Some(keyboard)),
      Methods.sendSticker(ChatIntId(0), InputPartFile(file), replyMarkup = Some(keyboard))
    )
    methods.foreach { method =>
      withClue(method.payload.name) {
        parse(formFields(toMultipart(method))("reply_markup")) shouldBe Right(keyboardJson)
      }
    }
  }

  "should skip null fields and the fields of uploaded files" in {
    val json = Json.obj(
      "document" -> Json.fromString("attach://document"),
      "caption"  -> Json.Null,
      "chat_id"  -> Json.fromInt(0)
    )
    val multipart =
      Http4sUtils.toMultipartWithFormData[IO](json, List("document"), Vector.empty).unsafeRunSync()

    formFields(multipart) shouldBe Map("chat_id" -> "0")
  }

}
