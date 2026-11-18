# F[Tg] - Telegramium

[![Telegram](https://img.shields.io/badge/Telegram%20Bot%20API-10.4%20(November%209%2C%202026)-blue)](https://core.telegram.org/bots/api#recent-changes)
![Maven Central](https://img.shields.io/maven-central/v/io.github.apimorphism/telegramium-core_3)

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
    Methods.sendMessage(chatId = Some(ChatIntId(msg.chat.id)), text = "Hello, world!").exec.void
}
```

`LongPollBot` and `WebhookBot` extend the `Methods` trait so you can call `sendMessage` directly.

#### Webhooks
```scala
class MyWebhookBot[F[_]: Async](url: String, path: String)(
  implicit api: Api[F]
) extends WebhookBot[F](api, url, path) {
  override def onMessage(msg: Message): F[Unit] =
    sendMessage(chatId = Some(ChatIntId(msg.chat.id)), text = "Hello, world!").exec.void
}
```

You can also perform a request to the Bot API while [sending an answer to the webhook](https://core.telegram.org/bots/api#making-requests-when-getting-updates):
```scala
override def onMessageReply(msg: Message): F[Option[Method[_]]] =
  Sync[F].pure(Some(sendMessage(chatId = Some(ChatIntId(msg.chat.id)), text = "Hello, world!")))
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

Telegram adds new values to its enums without notice, such as a new kind of message entity. So that a bot doesn't break when that happens, Telegramium wraps these values in `OpenEnum`: `Known(value)` for a value this version knows, and `Unknown(tag, raw)` for a new one. It's up to the bot what to do with an unknown value: ignore it, log it, or reply "not supported".

```scala
import iozhik.OpenEnum

msg.entities.collect { case OpenEnum.Known(e: BoldMessageEntity) => e }

msg.entities.foreach {
  case OpenEnum.Known(entity)   => ...
  case OpenEnum.Unknown(tag, _) => ... // an entity type this version doesn't know
}
```

- **Sending:** methods take plain values, as in `entities = List(BoldMessageEntity(0, 4))`. Quotes are the exception. Telegram rejects a quote that doesn't match the original (`QUOTE_TEXT_INVALID`), so `ReplyParameters.quoteRichTexts` and `quoteEntities` take the received values as they are, unknown ones included. Offsets in `quoteEntities` count from the start of the quote.
- **Comparing:** a wrapped value never equals a plain one. `entity == BoldMessageEntity(0, 4)` is always false, and you get no warning. Use `entity.equalsKnown(BoldMessageEntity(0, 4))` or a pattern match instead. On Scala 3, an "Unreachable case" warning usually means a pattern is missing its `OpenEnum.Known(...)`.
- **Adding to a received list:** wrap the new value, `msg.entities :+ OpenEnum(ItalicMessageEntity(0, 4))`.
- **Unknown values** keep Telegram's JSON in `raw` and are sent back unchanged. One prints only its tag, e.g. `Unknown(date_time)`, because `raw` may contain personal data.
- **Broken values:** only a new type becomes `Unknown`. A known type with bad data, such as a `bold` entity without an `offset`, still fails to decode.
- **Rich text:** write it without the wrapper, `RichTextBold(RichTextPlain("hi"))`. To put a received unknown rich text into a block you send, wrap it as `RichTextConcat(List(unknown))`.
- **Limitation:** a new field Telegram adds to a known type is dropped when the value is sent back. So until you upgrade, quoting text that has such a field fails ([iozhik#44](https://github.com/apimorphism/iozhik/issues/44)).
- **Rich messages** have no `text`: their content is only in `richMessage`. Helpers for reading one as plain text and for quoting part of one are planned ([#725](https://github.com/apimorphism/telegramium/issues/725)).

#### Unknown updates

For the same reason, Telegram may introduce a new update type (a new top-level field on `Update`) before Telegramium adds explicit support for it. When that happens, `LongPollBot` and `WebhookBot` still dispatch the update's known fields as usual, and additionally call `onUnknownUpdate` with the decoded update, the names of the unknown fields and the raw JSON:

```scala
override def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): F[Unit] =
  Async[F].delay(logger.warn(s"Update ${update.updateId} has unknown fields: ${unknownFields.mkString(", ")}"))
```

By default, a warning is printed to stderr. Detection only looks at top-level fields of `Update` itself, not at new fields nested inside an already-known object (e.g. a new field on `Message`). The raw update may contain personal data, so avoid logging `json` as-is unless you've considered that.

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
  chatId = Some(chatId),
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

#### Upgrading

* Pass optional arguments by name: `editMessageReplyMarkup(chatId = Some(ChatIntId(id)), messageId = Some(msgId))`. Telegram often adds new optional parameters in the middle of a method's list, so positional calls can stop compiling after a Bot API update.
* A Scalafix rule makes the source changes `11.1004.0` needs: it passes `chatId` as `Some(...)` and the other arguments of those 14 methods by name, and turns `case OpenEnum.Unknown(tag)` into `case OpenEnum.Unknown(tag, _)`. Run it before bumping the version, while the code still compiles against 10.x: `sbt ";scalafixEnable;scalafixAll github:apimorphism/telegramium/Telegramium11_1004?sha=11.1004.0"`. Then bump. It warns about what it leaves to you: an `OpenEnum.Unknown` built by hand, and, on Scala 3, a call that passes optional parameters by position.
* Since `11.1004.0`, `chatId` is an `Option[ChatId]` in `sendMessage`, `sendRichMessage`, `sendAnimation`, `sendAudio`, `sendContact`, `sendDocument`, `sendLivePhoto`, `sendLocation`, `sendPhoto`, `sendSticker`, `sendVenue`, `sendVideo`, `sendVideoNote` and `sendVoice`, and comes after their required parameters: Bot API 10.4 lets an ephemeral reply to a callback query omit it. Wrap it in `Some` and pass it by name: `sendMessage(chatId = Some(ChatIntId(id)), text = "hi")`.
* Since `11.1004.0`, an `OpenEnum.Unknown` keeps the JSON it was decoded from, as `raw`, and encodes back to it, not to its bare type tag (see [OpenEnum](#openenum)). Match it as `case OpenEnum.Unknown(tag, _)`. It has no type parameter, and one built by hand, e.g. in a test, takes the JSON too: `OpenEnum.Unknown("future_entity", json)`.
* Since `11.1004.0`, `ReplyParameters.quoteRichTexts` is a `List[OpenEnum[RichText]]` and `quoteEntities` a `List[OpenEnum[MessageEntity]]`. Received values go in as they are, and a list written out takes plain values; a `List[RichText]` or `List[MessageEntity]` the bot already holds needs `.map(OpenEnum(_))`.
* On Scala 2.13, telegramium depends on the latest scala-library (2.13.18 since `10.905.0`), and sbt refuses to build a project whose `scalaVersion` is older than that. Upgrade `scalaVersion` along with telegramium.

### Contribution

See [CONTRIBUTING.md](CONTRIBUTING.md).

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
