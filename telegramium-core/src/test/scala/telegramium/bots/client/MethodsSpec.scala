package telegramium.bots.client

import org.scalatest.funsuite.AnyFunSuite

import telegramium.bots._

/** Reproduces https://github.com/apimorphism/telegramium/issues/666
  *
  * Methods that take a file wrapped inside a compound type (InputMedia, InputSticker, InputPaidMedia,
  * InputProfilePhoto, InputStoryContent) never populate `MethodPayload.files`, so any `InputPartFile`/`InputLinkFile`
  * nested inside is silently dropped instead of being attached as multipart.
  */
class MethodsSpec extends AnyFunSuite with Methods {

  private val file = InputPartFile(new java.io.File("test.jpg"))

  test("sendMediaGroup should attach embedded files from the media list") {
    val media = List(InputMediaPhoto(media = file), InputMediaPhoto(media = file))
    val payload = sendMediaGroup(ChatIntId(1L), media = media).asInstanceOf[MethodReq[_]].payload

    assert(payload.files.nonEmpty, "files map is empty: embedded media files are never attached")
    assert(payload.files.values.toList.contains(file))
  }

  test("addStickerToSet should attach the embedded sticker file") {
    val sticker = InputSticker(sticker = file, format = "static")
    val payload = addStickerToSet(1L, "set_name", sticker).asInstanceOf[MethodReq[_]].payload

    assert(payload.files.nonEmpty, "files map is empty: the embedded sticker file is never attached")
    assert(payload.files.values.toList.contains(file))
  }

  test("createNewStickerSet should attach each embedded sticker file with a unique name") {
    val stickers = List(
      InputSticker(sticker = file, format = "static"),
      InputSticker(sticker = file, format = "static")
    )
    val payload =
      createNewStickerSet(1L, "set_name", "Set Title", stickers).asInstanceOf[MethodReq[_]].payload

    assert(payload.files.size == 2, s"expected 2 attached files, got ${payload.files.size}")
  }

  test("sendPaidMedia should attach the embedded media file") {
    val media = List(InputPaidMediaPhoto(media = file))
    val payload = sendPaidMedia(ChatIntId(1L), starCount = 1, media = media).asInstanceOf[MethodReq[_]].payload

    assert(payload.files.nonEmpty, "files map is empty: the embedded paid media file is never attached")
    assert(payload.files.values.toList.contains(file))
  }

  test("setMyProfilePhoto should attach the embedded profile photo file") {
    val photo = InputProfilePhotoStatic(photo = file)
    val payload = setMyProfilePhoto(photo).asInstanceOf[MethodReq[_]].payload

    assert(payload.files.nonEmpty, "files map is empty: the embedded profile photo file is never attached")
    assert(payload.files.values.toList.contains(file))
  }

  test("postStory should attach the embedded story content file") {
    val content = InputStoryContentPhoto(photo = file)
    val payload = postStory("business_connection_id", content, 1).asInstanceOf[MethodReq[_]].payload

    assert(payload.files.nonEmpty, "files map is empty: the embedded story content file is never attached")
    assert(payload.files.values.toList.contains(file))
  }
}
