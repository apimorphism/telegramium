package telegramium.bots.client

import io.circe.Json
import org.scalatest.funsuite.AnyFunSuite

import telegramium.bots._

/** Covers https://github.com/apimorphism/telegramium/issues/666
  *
  * Methods that take a file wrapped inside a compound type (InputMedia, InputSticker, InputPaidMedia,
  * InputProfilePhoto, InputStoryContent) must upload each embedded `InputPartFile` as a multipart attachment and
  * reference it from the JSON body as `attach://<name>`, while leaving file_id/URL references (`InputLinkFile`) as is.
  */
class MethodsSpec extends AnyFunSuite with Methods {

  private val file  = InputPartFile(new java.io.File("test.jpg"))
  private val file2 = InputPartFile(new java.io.File("thumb.jpg"))
  private val link  = InputLinkFile("AgACAgIAAxkBAAI")

  private def payloadOf(m: Method[_]): MethodPayload = m.asInstanceOf[MethodReq[_]].payload

  private def str(json: Json, path: Any*): Option[String] =
    path
      .foldLeft(json.hcursor: io.circe.ACursor) {
        case (c, i: Int)    => c.downN(i)
        case (c, k: String) => c.downField(k)
        case (c, _)         => c
      }
      .as[String]
      .toOption

  test("sendMediaGroup should attach each embedded file under an indexed name") {
    val media   = List(InputMediaPhoto(media = file), InputMediaPhoto(media = file2))
    val payload = payloadOf(sendMediaGroup(ChatIntId(1L), media = media))

    assert(payload.files == Map("media.0.media" -> file, "media.1.media" -> file2))
    assert(str(payload.json, "media", 0, "media").contains("attach://media.0.media"))
    assert(str(payload.json, "media", 1, "media").contains("attach://media.1.media"))
  }

  test("sendMediaGroup should leave file_id/URL references untouched and not attach them") {
    val media   = List(InputMediaPhoto(media = link), InputMediaPhoto(media = file))
    val payload = payloadOf(sendMediaGroup(ChatIntId(1L), media = media))

    assert(payload.files == Map("media.1.media" -> file))
    assert(str(payload.json, "media", 0, "media").contains(link.file))
    assert(str(payload.json, "media", 1, "media").contains("attach://media.1.media"))
  }

  test("sendMediaGroup should attach optional embedded files only when present") {
    val media = List(
      InputMediaVideo(media = file, thumbnail = Some(file2)),
      InputMediaVideo(media = file)
    )
    val payload = payloadOf(sendMediaGroup(ChatIntId(1L), media = media))

    assert(payload.files == Map("media.0.media" -> file, "media.0.thumbnail" -> file2, "media.1.media" -> file))
    assert(str(payload.json, "media", 0, "thumbnail").contains("attach://media.0.thumbnail"))
    assert(str(payload.json, "media", 1, "thumbnail").isEmpty)
  }

  test("editMessageMedia should attach the embedded media file") {
    val payload = payloadOf(editMessageMedia(InputMediaPhoto(media = file)))

    assert(payload.files == Map("media.media" -> file))
    assert(str(payload.json, "media", "media").contains("attach://media.media"))
  }

  test("addStickerToSet should attach the embedded sticker file") {
    val sticker = InputSticker(sticker = file, format = "static")
    val payload = payloadOf(addStickerToSet(1L, "set_name", sticker))

    assert(payload.files == Map("sticker.sticker" -> file))
    assert(str(payload.json, "sticker", "sticker").contains("attach://sticker.sticker"))
  }

  test("createNewStickerSet should attach each embedded sticker file with a unique name") {
    val stickers = List(
      InputSticker(sticker = file, format = "static"),
      InputSticker(sticker = file2, format = "static")
    )
    val payload = payloadOf(createNewStickerSet(1L, "set_name", "Set Title", stickers))

    assert(payload.files == Map("stickers.0.sticker" -> file, "stickers.1.sticker" -> file2))
  }

  test("sendPaidMedia should attach the embedded media file") {
    val media   = List(InputPaidMediaPhoto(media = file))
    val payload = payloadOf(sendPaidMedia(ChatIntId(1L), starCount = 1, media = media))

    assert(payload.files == Map("media.0.media" -> file))
    assert(str(payload.json, "media", 0, "media").contains("attach://media.0.media"))
  }

  test("setMyProfilePhoto should attach the embedded profile photo file") {
    val payload = payloadOf(setMyProfilePhoto(InputProfilePhotoStatic(photo = file)))

    assert(payload.files == Map("photo.photo" -> file))
    assert(str(payload.json, "photo", "photo").contains("attach://photo.photo"))
  }

  test("postStory should attach the embedded story content file") {
    val payload = payloadOf(postStory("business_connection_id", InputStoryContentPhoto(photo = file), 1))

    assert(payload.files == Map("content.photo" -> file))
    assert(str(payload.json, "content", "photo").contains("attach://content.photo"))
  }

  test("direct file params should still be attached under their own name") {
    val payload = payloadOf(sendPhoto(ChatIntId(1L), photo = file))

    assert(payload.files == Map("photo" -> file))
  }
}
