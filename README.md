# F[Tg] - Telegramium

[![Telegram](https://img.shields.io/badge/Telegram%20Bot%20API-7.4%20(May%2028%2C%202024)-blue)](https://core.telegram.org/bots/api#recent-changes)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.apimorphism/telegramium-core_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.apimorphism/telegramium-core_2.13)


F[Tg] is a pure functional Telegram Bot API for Scala.

This project is a try to provide a comprehensive, well documented and Scala-idiomatic client/server implementations to work with Telegram Bot API. Please refer to telegramium-examples module for usage examples. There is a support both for polling and webhooks.
API core is generated from the official documentation, so it is believed to cover all the available methods, entities and to be up to date.

Currently the following backends are supported:

- http4s
- cats-effect
- circe

You may want to start with [Methods.scala](telegramium-core/src/main/scala/telegramium/bots/client/Methods.scala) and [EchoBot.scala](telegramium-examples/src/main/scala/telegramium/bots/examples/EchoBot.scala).

### How to use
Create the dependency by adding the following lines to your build.sbt:

```
libraryDependencies += "io.github.apimorphism" %% "telegramium-core" % "<version>"
libraryDependencies += "io.github.apimorphism" %% "telegramium-high" % "<version>"
```

Imports:
```scala
import telegramium.bots.high._
import telegramium.bots.high.implicits._
```

Use the `Methods` factory to create requests. You will need an instance of the `BotApi` class to execute them:
```scala
BlazeClientBuilder[F].resource.use { httpClient =>
  implicit val api: Api[F] = BotApi(httpClient, baseUrl = s"https://api.telegram.org/bot$token")
  val bot = new MyLongPollBot()
  bot.start()
}
```

#### Long polling
```scala
class MyLongPollBot[F[_]: Async: Parallel]()(implicit api: Api[F]) extends LongPollBot[F](api) {
  override def onMessage(msg: Message): F[Unit] =
    Methods.sendMessage(chatId = ChatIntId(msg.chat.id), text = "Hello, world!").exec.void
}
```

`LongPollBot` and `WebhookBot` extend the `Methods` trait so you can call `sendMessage` directly.

#### Webhooks
```scala
class MyWebhookBot[F[_]: Async](url: String, path: String)(
  implicit api: Api[F]
) extends WebhookBot[F](api, url, path) {
  override def onMessage(msg: Message): F[Unit] =
    sendMessage(chatId = ChatIntId(msg.chat.id), text = "Hello, world!").exec.void
}
```

You can also perform a request to the Bot API while [sending an answer to the webhook](https://core.telegram.org/bots/api#making-requests-when-getting-updates):
```scala
override def onMessageReply(msg: Message): F[Option[Method[_]]] =
  Sync[F].pure(Some(sendMessage(chatId = ChatIntId(msg.chat.id), text = "Hello, world!")))
```
In addition, the library provides a way to compose multiple Webhook
bots into a single `Http4s` Server that will handle the webhooks
registration as well as the incoming requests of all the composed
bots. You ultimately decide at which host:port the server will be
binded to:

``` scala
val api1: Api[IO] = BotApi(http, baseUrl = s"https://api.telegram.org/bot$bot_token1")
val api2: Api[IO] = BotApi(http, baseUrl = s"https://api.telegram.org/bot$bot_token1")
val bot1: MyWebhookbot = new MyWebhookbot[IO](api1, "ServerVisibleFromOutside", s"/$bot_token1")
val bot2: MyWebhookbot = new MyWebhookbot[IO](api2, "ServerVisibleFromOutside", s"/$bot_token2")

WebhookBot.compose[IO](
    List(bot1, bot2),
    8080,
    "127.0.0.1" //optional, localhost as default
  ).useForever.runSyncUnsafe()
```

For details, have a look
at the [Github Issue](https://github.com/apimorphism/telegramium/issues/143) and
the related [Pull Request](https://github.com/apimorphism/telegramium/pull/145).

#### OpenEnum

The Telegram Bot API is not versioned, which means bots and libraries must try to be forward-compatible. Specifically for enumerations, this implies that bots should always anticipate new values being added. Handling these values depends on the bot's business logic. Some apps may simply ignore unknown values, while others might respond with "This message is not supported," save the message for manual processing later, or throw a request handling error.

Telegramium introduces `OpenEnum`, a wrapper for Telegram Bot API enumerations with two cases: `Known` and `Unknown`. This type allows unknown values to be explicitly handled (similar to `Option` or `Either`), enabling bots using Telegramium to be more robust, improve error handling, and degrade gracefully in light of frequent Telegram Bot API updates.

#### Keyboards
To use smart and safe constructors for keyboard markups, import `telegramium.bots.high.keyboards._`:
```scala
val button: KeyboardButton = KeyboardButtons.text("Hello, world!")
val keyboard: ReplyKeyboardMarkup = ReplyKeyboardMarkups.singleButton(button)

val inlineButton: InlineKeyboardButton =
  InlineKeyboardButtons.callbackData(text = "Press me", callbackData = "button_pressed")

val inlineKeyboard: InlineKeyboardMarkup =
  InlineKeyboardMarkups.singleButton(inlineButton)
```

#### Message entities

`telegramium.bots.high.messageentities.MessageEntities` is a utility class to work with styled messages using [message entities](https://core.telegram.org/bots/api#messageentity). Compose your message's text using different formatting options like plain text, bold, italics, links, mentions, hashtags and more. 
This class automatically takes care of calculating text offsets and lengths required by the Telegram API.

```scala
import telegramium.bots.high.messageentities.*

val msgEntities = MessageEntities()
  .bold("Hello, ")
  .mention("@user")
  .plain("! Welcome to our ")
  .textLink("website", "https://example.com")
  .lineBreak()
  .plain("Enjoy your stay.")

Methods.sendMessage(
  chatId,
  // Hello, @user! Welcome to our website\nEnjoy your stay.
  text = msgEntities.toPlainText(),
  // List(BoldMessageEntity(0, 7), MentionMessageEntity(7, 5), TextLinkMessageEntity(29, 7, "https://example.com"))
  entities = msgEntities.toTelegramEntities()
)
```

### Versioning

X.Y.Z where

* X major changes to telegramium high or internals of the core.
* Y denotes Telegram Bot API version which is supported by this `X.Y.Z`.
* Z bug fix changes.

Please, note, that this versioning scheme is started from version `1.48.0`.

### Contribution

I'd love to have more testing and more example bots. Ideas and PRs on telegramium-high -
high level interface for the bot API are also highly encouraged.

If you want to change something in telegramium-core or found a bug in it, please create an issue.
Do not create pull requests with changes on telegramium-core as we use semi-automatic way to work with it.
Except that any PR-s are welcome.

Before you submit the PR, ensure that you've formatted your code by running the `sbt fmtAll` command.

### Alternatives

You may also want to have a look at these projects:
* [augustjune/canoe](https://github.com/augustjune/canoe)
* [bot4s/telegram](https://github.com/bot4s/telegram)

### Known usages

* [johnspade/taskobot-scala](https://github.com/johnspade/taskobot-scala): Taskobot is a task collaboration inline Telegram bot. 
Perhaps the most up-to-date bot from a Telegramium mantainer, built with ZIO.
* [johnspade/s10ns_bot](https://github.com/johnspade/s10ns_bot): Subscription Management Telegram Bot
* [TurtleBots/playcs](https://github.com/TurtleBots/playcs): create dedicated servers for CS 1.6 client
* [TurtleBots/gdetram](https://github.com/TurtleBots/gdetram): public transport arrival tabloid with chat bot interface
* [b1nd/stonks](https://github.com/b1nd/stonks): Investment Portfolio Calculation Bot
* [benkio/myTelegramBot](https://github.com/benkio/myTelegramBot)
* [TurtleBots/adjutant](https://github.com/TurtleBots/adjutant): adjutant provides shareplace for sc2 builds through the chatbot
* [TurtleBots/bender](https://github.com/TurtleBots/bender): undefeatable tic-tac-toe player
* [a-khakimov/undicator](https://github.com/a-khakimov/undicator): simple bot for viewing unsplash user statistics
