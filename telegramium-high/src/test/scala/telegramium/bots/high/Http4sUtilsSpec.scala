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
import telegramium.bots.InputLinkFile
import telegramium.bots.InputMediaDocument
import telegramium.bots.InputMediaPhoto
import telegramium.bots.InputPartFile
import telegramium.bots.InputPollOption
import telegramium.bots.InputRichBlockCollage
import telegramium.bots.InputRichBlockPhoto
import telegramium.bots.InputRichMessage
import telegramium.bots.InputRichMessageMedia
import telegramium.bots.InputSticker
import telegramium.bots.ReplyParameters
import telegramium.bots.client.Method
import telegramium.bots.high.keyboards.InlineKeyboardButtons
import telegramium.bots.high.keyboards.InlineKeyboardMarkups

class Http4sUtilsSpec extends AnyFreeSpec with Matchers {

  private val file         = new File(this.getClass.getResource("/document.txt").getFile)
  private val keyboard     = InlineKeyboardMarkups.singleButton(InlineKeyboardButtons.callbackData("Press", "pressed"))
  private val keyboardJson = parse(
    """{"inline_keyboard":[[{"text":"Press","callback_data":"pressed"}]]}"""
  ).toOption.get
  private val replyParamsJs = parse("""{"message_id":1}""").toOption.get

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

    fields.keySet shouldBe Set("chat_id", "caption", "reply_parameters", "reply_markup", "method")
    fields("chat_id") shouldBe "0"
    fields("caption") shouldBe "hi"
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

  // https://github.com/apimorphism/telegramium/issues/666
  "should send a file embedded in an object param together with the object referencing it" in {
    val multipart = toMultipart(
      Methods.addStickerToSet(1L, "set", InputSticker(InputPartFile(file), "static", emojiList = List("🙂")))
    )
    val fields = formFields(multipart)

    fields.keySet shouldBe Set("user_id", "name", "sticker", "method")
    parse(fields("sticker")).map(_.hcursor.get[String]("sticker")) shouldBe Right(Right("attach://sticker_sticker"))
    MultipartFields.fileParts(multipart) shouldBe Vector("sticker_sticker" -> "document.txt")
  }

  "should send a file embedded in an object param of editMessageMedia" in {
    val multipart = toMultipart(
      Methods.editMessageMedia(
        InputMediaDocument(InputPartFile(file)),
        chatId = Some(ChatIntId(0)),
        messageId = Some(1)
      )
    )

    parse(formFields(multipart)("media")).map(_.hcursor.get[String]("media")) shouldBe Right(
      Right("attach://media_media")
    )
    MultipartFields.fileParts(multipart) shouldBe Vector("media_media" -> "document.txt")
  }

  "should send files embedded in a list param under indexed names" in {
    val multipart = toMultipart(
      Methods.sendMediaGroup(
        ChatIntId(0),
        media = List(InputMediaDocument(InputPartFile(file)), InputMediaDocument(InputLinkFile("file_id")))
      )
    )
    val media = parse(formFields(multipart)("media")).map(_.hcursor)

    media.map(_.downN(0).get[String]("media")) shouldBe Right(Right("attach://media_0_media"))
    media.map(_.downN(1).get[String]("media")) shouldBe Right(Right("file_id"))
    MultipartFields.fileParts(multipart) shouldBe Vector("media_0_media" -> "document.txt")
  }

  // https://github.com/apimorphism/iozhik/issues/21
  "should send a file in a poll option's media, and in the poll's own media" in {
    val multipart = toMultipart(
      Methods.sendPoll(
        ChatIntId(0),
        "question",
        options = List(
          InputPollOption("a", media = Some(InputMediaPhoto(InputPartFile(file)))),
          InputPollOption("b", media = Some(InputMediaPhoto(InputLinkFile("file_id"))))
        ),
        media = Some(InputMediaPhoto(InputPartFile(file)))
      )
    )
    val fields  = formFields(multipart)
    val options = parse(fields("options")).map(_.hcursor)

    options.map(_.downN(0).downField("media").get[String]("media")) shouldBe Right(
      Right("attach://options_0_media_media")
    )
    options.map(_.downN(1).downField("media").get[String]("media")) shouldBe Right(Right("file_id"))
    parse(fields("media")).map(_.hcursor.get[String]("media")) shouldBe Right(Right("attach://media_media"))
    MultipartFields.fileParts(multipart).sorted shouldBe Vector(
      "media_media"           -> "document.txt",
      "options_0_media_media" -> "document.txt"
    )
  }

  "should send files in a rich message's media and in its blocks, nested ones too" in {
    val richMessage = InputRichMessage(
      blocks = List(
        InputRichBlockPhoto(InputMediaPhoto(InputLinkFile("file_id"))),
        InputRichBlockCollage(blocks = List(InputRichBlockPhoto(InputMediaPhoto(InputPartFile(file)))))
      ),
      media = List(InputRichMessageMedia("id", InputMediaDocument(InputPartFile(file))))
    )
    val multipart = toMultipart(Methods.sendRichMessage(ChatIntId(0), richMessage))
    val rich      = parse(formFields(multipart)("rich_message")).map(_.hcursor)

    rich.map(_.downField("media").downN(0).downField("media").get[String]("media")) shouldBe Right(
      Right("attach://rich_message_media_0_media_media")
    )
    rich.map(_.downField("blocks").downN(0).downField("photo").get[String]("media")) shouldBe Right(Right("file_id"))
    rich.map(
      _.downField("blocks").downN(1).downField("blocks").downN(0).downField("photo").get[String]("media")
    ) shouldBe
      Right(Right("attach://rich_message_blocks_1_blocks_0_photo_media"))
    MultipartFields.fileParts(multipart).sorted shouldBe Vector(
      "rich_message_blocks_1_blocks_0_photo_media" -> "document.txt",
      "rich_message_media_0_media_media"           -> "document.txt"
    )
  }

  "should send a file in the rich message of editMessageText" in {
    val multipart = toMultipart(
      Methods.editMessageText(
        chatId = Some(ChatIntId(0)),
        messageId = Some(1),
        richMessage =
          Some(InputRichMessage(media = List(InputRichMessageMedia("id", InputMediaPhoto(InputPartFile(file))))))
      )
    )

    parse(formFields(multipart)("rich_message"))
      .map(_.hcursor.downField("media").downN(0).downField("media").get[String]("media")) shouldBe Right(
      Right("attach://rich_message_media_0_media_media")
    )
    MultipartFields.fileParts(multipart) shouldBe Vector("rich_message_media_0_media_media" -> "document.txt")
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
