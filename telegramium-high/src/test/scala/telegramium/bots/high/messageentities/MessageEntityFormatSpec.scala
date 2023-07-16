package telegramium.bots.high.messageentities

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.BoldMessageEntity
import telegramium.bots.ItalicMessageEntity
import telegramium.bots.high.messageentities.MessageEntityFormat.*

class MessageEntityFormatSpec extends AnyFunSpec with Matchers {

  describe("MessageEntityFormatSpec") {
    describe("convertation") {
      it("toMessageEntities should convert all contained entities") {
        val stringMessageEntities = Vector(Bold("bold"), Plain("plain"), Italic("italic"))
        val expected              = List(BoldMessageEntity(0, 4), ItalicMessageEntity(9, 6))

        MessageEntityFormat.toMessageEntities(stringMessageEntities) should contain theSameElementsAs expected
      }
    }

    describe("interpolation") {
      it("plain") {
        plain"1${1 + 1}3" should be(Plain("123"))
      }

      it("bold") {
        bold"1${1 + 1}3" should be(Bold("123"))
      }

      it("italic") {
        italic"1${1 + 1}3" should be(Italic("123"))
      }
    }
  }

}
