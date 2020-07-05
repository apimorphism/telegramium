# F[Tg] - Telegramium

[![Telegram](https://img.shields.io/badge/Telegram%20Bot%20API-4.9%20(June%2004%2C%202020)-blue)](https://core.telegram.org/bots/api#recent-changes)


F[Tg] is a pure functional Telegram Bot API for scala.

This project is a try to provide a comprehensive, well documented and Scala-idiomatic client/server implementations to work with Telegram Bot API. Please refer to telegramium-examples module for usage examples. There is a support both for polling and webhooks.
API core is generated from the official documentation, so it is believed to cover all the available methods, entities and to be up to date.

Currently the following backends are supported:

- http4s
- cats-effect
- circe
- uPickle (MsgPack binary serialization, to be used for storing API messages in DB and sending over network)

You may want to start with [Methods.scala](telegramium-core/src/main/scala/telegramium/bots/client/Methods.scala) and [EchoBot.scala](telegramium-examples/src/main/scala/telegramium/bots/examples/EchoBot.scala).

### How to use
Create the dependency by adding the following lines to your build.sbt:

```
libraryDependencies += "io.github.apimorphism" %% "telegramium-core" % "1.49.0"
libraryDependencies += "io.github.apimorphism" %% "telegramium-high" % "1.49.0"
```

Imports:
```scala
import telegramium.bots.high._
import telegramium.bots.high.implicits._
```

Use the `Methods` fabric to create requests. You will need an instance of the `BotApi` class to execute them:
```scala
BlazeClientBuilder[F](ExecutionContext.global).resource.use { httpClient =>
  implicit val api: Api[F] = BotApi(http, baseUrl = s"https://api.telegram.org/bot$token")
  val bot = new MyLongPollBot()
  bot.start()
}
```

#### Long polling
```scala
class MyLongPollBot[F[_]: Sync: Timer]()(implicit api: Api[F]) extends LongPollBot[F](api) {
  override def onMessage(msg: Message): F[Unit] =
    Methods.sendMessage(chatId = ChatIntId(msg.chat.id), text = "Hello, world!").exec.void
}
```

`LongPollBot` and `WebhookBot` extend the `Methods` trait so you can call `sendMessage` directly.

#### Webhooks
```scala
class MyWebhookBot[F[_]: ConcurrentEffect: ContextShift: Timer](port: Int, url: String, path: String)(
  implicit api: Api[F]
) extends WebhookBot[F](api, port, url, path) {
  override def onMessage(msg: Message): F[Unit] =
    sendMessage(chatId = ChatIntId(msg.chat.id), text = "Hello, world!").exec.void
}
```

You can also perform a request to the Bot API while [sending an answer to the webhook](https://core.telegram.org/bots/api#making-requests-when-getting-updates):
```scala
override def onMessageReply(msg: Message): F[Option[Method[_]]] =
  Sync[F].pure(Some(sendMessage(chatId = ChatIntId(msg.chat.id), text = "Hello, world!")))
```

### Versioning

X.Y.Z where

* X major changes to telegramium high or internals of the core.
* Y denotes Telegram Bot API version which is supported by this `X.Y.Z`.
* Z bug fix changes.

Please, note, that this versioning scheme is started from version `1.48.0`.

### Known issues

InlineQueryResultMpeg4Gif and InlineQueryResultCachedMpeg4Gif
has the same discriminator value which comes from the official docs. Maybe the bug in Tg Bot API itself.

### Contribution

I'd love to have more testing and more example bots. Ideas and PRs on telegramium-high -
high level interface for the bot API are also highly encouraged.

If you want to change something in telegramium-core or found a bug in it, please create an issue.
Do not create pull requests with changes on telegramium-core as we use semi-automatic way to work with it.
Except that any PR-s are welcome.

### Alternatives

You may also want to have a look at these projects:
* https://github.com/augustjune/canoe
* https://github.com/bot4s/telegram
* https://github.com/paoloboni/telegram4s

### Known usages

* https://github.com/oybek/playcs_bot : telegram chat bot for creating counter strike 1.6 dedicated servers
* https://github.com/johnspade/s10ns_bot : Subscription Management Telegram Bot
* https://github.com/oybek/gdetram : Public transport info aggregator with chat bot interface

### Note for Yan

Yan, напиши бота.
