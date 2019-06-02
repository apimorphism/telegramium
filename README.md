# F[Tg] - Telegramium

F[Tg] is a pure functional Telegram Bot API for scala.

In this project we try to provide comprehensive, well documented and Scala-idiomatic client/server implementations to work with Telegram Bot API.
Please refer to telegramium-examples module for usage examples. We support both polling and webhooks.

Currently we support the following backends:

- http4s
- Circe
- uPickle (MsgPack binary deserialization for storage purposes)

You may want to start with [Api.scala](telegramium-core/src/main/scala/io/github/fperiodic/apimorphism/telegramium/bots/client/Api.scala) and [EchoBot.scala](telegramium-examples/src/main/scala/io/github/fperiodic/apimorphism/telegramium/bots/examples/EchoBot.scala).

### Contribution

If you want to change something in telegramium-core or found a bug in it, please create an issue.
Do not create pull requests with changes on telegramium-core as we use semi-automatic way to work with it.
Except that any PR-s are welcome.

### Note for Yan

Yan, напиши бота.
