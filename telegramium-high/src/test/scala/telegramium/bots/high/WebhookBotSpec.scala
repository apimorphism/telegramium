package telegramium.bots.high

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import io.circe.syntax._
import org.scalamock.scalatest.MockFactory
import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._
import telegramium.bots.client.CirceImplicits._
import telegramium.bots.client.{MethodReq, SendMessageReq}
import telegramium.bots.{CallbackQuery, Chat, ChatIntId, ChatMemberMember, ChatMemberUpdated, ChosenInlineResult, InlineQuery, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingAddress, ShippingQuery, Update, User}

class WebhookBotSpec extends AnyFreeSpec with MockFactory with Matchers with OptionValues {
  private val testUpdate  = Update(updateId = 0)
  private val testMessage = Message(0, date = 0, chat = Chat(0, `type` = ""))
  private val testUser    = User(0, isBot = false, "")

  private val testChatMemberUpdated =
    ChatMemberUpdated(
      Chat(0, `type` = ""),
      testUser,
      0,
      ChatMemberMember("", testUser),
      ChatMemberMember("", testUser)
    )

  "should support all Update types" - {
    "message" in new Test {
      verifyOnUpdate(
        testUpdate.copy(message = Some(testMessage)),
        expectedSentMessage = "onMessage",
        expectedReply = "onMessageReply"
      )
    }

    "edited message" in new Test {
      verifyOnUpdate(
        testUpdate.copy(editedMessage = Some(testMessage)),
        expectedSentMessage = "onEditedMessage",
        expectedReply = "onEditedMessageReply"
      )
    }

    "channel post" in new Test {
      verifyOnUpdate(
        testUpdate.copy(channelPost = Some(testMessage)),
        expectedSentMessage = "onChannelPost",
        expectedReply = "onChannelPostReply"
      )
    }

    "edited channel post" in new Test {
      verifyOnUpdate(
        testUpdate.copy(editedChannelPost = Some(testMessage)),
        expectedSentMessage = "onEditedChannelPost",
        expectedReply = "onEditedChannelPostReply"
      )
    }

    "inline query" in new Test {
      verifyOnUpdate(
        testUpdate.copy(inlineQuery = Some(InlineQuery("0", testUser, query = "", offset = "0"))),
        expectedSentMessage = "onInlineQuery",
        expectedReply = "onInlineQueryReply"
      )
    }

    "chosen inline result" in new Test {
      verifyOnUpdate(
        testUpdate.copy(chosenInlineResult = Some(ChosenInlineResult("0", testUser, query = ""))),
        expectedSentMessage = "onChosenInlineResult",
        expectedReply = "onChosenInlineResultReply"
      )
    }

    "callback query" in new Test {
      verifyOnUpdate(
        testUpdate.copy(callbackQuery = Some(CallbackQuery("0", testUser, chatInstance = ""))),
        expectedSentMessage = "onCallbackQuery",
        expectedReply = "onCallbackQueryReply"
      )
    }

    "shipping query" in new Test {
      verifyOnUpdate(
        testUpdate.copy(shippingQuery =
          Some(ShippingQuery("0", testUser, "", ShippingAddress("", "", "", "", "", "")))
        ),
        expectedSentMessage = "onShippingQuery",
        expectedReply = "onShippingQueryReply"
      )
    }

    "pre-checkout query" in new Test {
      verifyOnUpdate(
        testUpdate.copy(preCheckoutQuery = Some(PreCheckoutQuery("0", testUser, "", 0, ""))),
        expectedSentMessage = "onPreCheckoutQuery",
        expectedReply = "onPreCheckoutQueryReply"
      )
    }

    "poll" in new Test {
      verifyOnUpdate(
        testUpdate.copy(poll =
          Some(
            Poll(
              "0",
              "",
              totalVoterCount = 0,
              isClosed = false,
              isAnonymous = false,
              `type` = "",
              allowsMultipleAnswers = false
            )
          )
        ),
        expectedSentMessage = "onPoll",
        expectedReply = "onPollReply"
      )
    }

    "poll answer" in new Test {
      verifyOnUpdate(
        testUpdate.copy(pollAnswer = Some(PollAnswer("0", testUser))),
        expectedSentMessage = "onPollAnswer",
        expectedReply = "onPollAnswerReply"
      )
    }

    "The bot's chat member status was updated in a chat" in new Test {
      verifyOnUpdate(
        testUpdate.copy(myChatMember = Some(testChatMemberUpdated)),
        expectedSentMessage = "onMyChatMember",
        expectedReply = "onMyChatMemberReply"
      )
    }

    "A chat member's status was updated in a chat" in new Test {
      verifyOnUpdate(
        testUpdate.copy(chatMember = Some(testChatMemberUpdated)),
        expectedSentMessage = "onChatMember",
        expectedReply = "onChatMemberReply"
      )
    }
  }

  private trait Test {
    protected val api: Api[IO]        = stub[Api[IO]]
    protected val bot: TestWebhookBot = new TestWebhookBot(api)

    (api.execute[Any] _).when(*).returns(IO.unit)

    protected def verifyOnUpdate(update: Update, expectedSentMessage: String, expectedReply: String): Unit = {
      bot.onUpdate(update).unsafeRunSync().value shouldBe
        MethodReq[Message]("sendMessage", SendMessageReq(ChatIntId(0), expectedReply).asJson)
      (api.execute[Message] _)
        .verify(MethodReq[Message]("sendMessage", SendMessageReq(ChatIntId(0), expectedSentMessage).asJson))
        .once()
      ()
    }

  }

}
