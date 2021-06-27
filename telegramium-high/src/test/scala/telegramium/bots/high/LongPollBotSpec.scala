package telegramium.bots.high

import io.circe.syntax._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.scalamock.scalatest.MockFactory
import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._
import telegramium.bots.client.CirceImplicits._
import telegramium.bots.client.{MethodReq, SendMessageReq}
import telegramium.bots.{CallbackQuery, Chat, ChatIntId, ChatMember, ChatMemberUpdated, ChosenInlineResult, InlineQuery, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingAddress, ShippingQuery, Update, User}

class LongPollBotSpec extends AnyFreeSpec with MockFactory with Matchers with OptionValues {
  private val testUpdate  = Update(updateId = 0)
  private val testMessage = Message(0, date = 0, chat = Chat(0, `type` = ""))
  private val testUser    = User(0, isBot = false, "")

  private val testChatMemberUpdated =
    ChatMemberUpdated(
      Chat(0, `type` = ""),
      testUser,
      0,
      ChatMember(testUser, ""),
      ChatMember(testUser, "")
    )

  "should support all Update types" - {
    "message" in new Test {
      verifyOnUpdate(
        testUpdate.copy(message = Some(testMessage)),
        expectedSentMessage = "onMessage"
      )
    }

    "edited message" in new Test {
      verifyOnUpdate(
        testUpdate.copy(editedMessage = Some(testMessage)),
        expectedSentMessage = "onEditedMessage"
      )
    }

    "channel post" in new Test {
      verifyOnUpdate(
        testUpdate.copy(channelPost = Some(testMessage)),
        expectedSentMessage = "onChannelPost"
      )
    }

    "edited channel post" in new Test {
      verifyOnUpdate(
        testUpdate.copy(editedChannelPost = Some(testMessage)),
        expectedSentMessage = "onEditedChannelPost"
      )
    }

    "inline query" in new Test {
      verifyOnUpdate(
        testUpdate.copy(inlineQuery = Some(InlineQuery("0", testUser, query = "", offset = "0"))),
        expectedSentMessage = "onInlineQuery"
      )
    }

    "chosen inline result" in new Test {
      verifyOnUpdate(
        testUpdate.copy(chosenInlineResult = Some(ChosenInlineResult("0", testUser, query = ""))),
        expectedSentMessage = "onChosenInlineResult"
      )
    }

    "callback query" in new Test {
      verifyOnUpdate(
        testUpdate.copy(callbackQuery = Some(CallbackQuery("0", testUser, chatInstance = ""))),
        expectedSentMessage = "onCallbackQuery"
      )
    }

    "shipping query" in new Test {
      verifyOnUpdate(
        testUpdate.copy(shippingQuery =
          Some(ShippingQuery("0", testUser, "", ShippingAddress("", "", "", "", "", "")))
        ),
        expectedSentMessage = "onShippingQuery"
      )
    }

    "pre-checkout query" in new Test {
      verifyOnUpdate(
        testUpdate.copy(preCheckoutQuery = Some(PreCheckoutQuery("0", testUser, "", 0, ""))),
        expectedSentMessage = "onPreCheckoutQuery"
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
        expectedSentMessage = "onPoll"
      )
    }

    "poll answer" in new Test {
      verifyOnUpdate(
        testUpdate.copy(pollAnswer = Some(PollAnswer("0", testUser))),
        expectedSentMessage = "onPollAnswer"
      )
    }

    "The bot's chat member status was updated in a chat" in new Test {
      verifyOnUpdate(
        testUpdate.copy(myChatMember = Some(testChatMemberUpdated)),
        expectedSentMessage = "onMyChatMember"
      )
    }

    "A chat member's status was updated in a chat" in new Test {
      verifyOnUpdate(
        testUpdate.copy(chatMember = Some(testChatMemberUpdated)),
        expectedSentMessage = "onChatMember"
      )
    }
  }

  private trait Test {
    protected val api: Api[Task]       = stub[Api[Task]]
    protected val bot: TestLongPollBot = new TestLongPollBot(api)

    (api.execute[Any] _).when(*).returns(Task.unit)

    protected def verifyOnUpdate(update: Update, expectedSentMessage: String): Unit = {
      bot.onUpdate(update).runSyncUnsafe()
      (api.execute[Message] _)
        .verify(MethodReq[Message]("sendMessage", SendMessageReq(ChatIntId(0), expectedSentMessage).asJson))
        .once()
      ()
    }

  }

}
