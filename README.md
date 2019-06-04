# F[Tg] - Telegramium

F[Tg] is a pure functional Telegram Bot API for scala.

This project is a try to provide a comprehensive, well documented and Scala-idiomatic client/server implementations to work with Telegram Bot API. Please refer to telegramium-examples module for usage examples. There is a support both for polling and webhooks.

Currently the following backends are supported:

- http4s
- cats-effect
- circe
- uPickle (MsgPack binary serialization, to be used for storing API messages in DB and sending over network)

You may want to start with [Api.scala](telegramium-core/src/main/scala/telegramium/bots/client/Api.scala) and [EchoBot.scala](telegramium-examples/src/main/scala/telegramium/bots/examples/EchoBot.scala).

### Contribution

If you want to change something in telegramium-core or found a bug in it, please create an issue.
Do not create pull requests with changes on telegramium-core as we use semi-automatic way to work with it.
Except that any PR-s are welcome.

### Note for Yan

Yan, напиши бота.
