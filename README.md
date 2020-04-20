# F[Tg] - Telegramium

[![Telegram](https://img.shields.io/badge/Telegram%20Bot%20API-4.7%20(March%2030%2C%202020)-blue)](https://core.telegram.org/bots/api#recent-changes)


F[Tg] is a pure functional Telegram Bot API for scala.

This project is a try to provide a comprehensive, well documented and Scala-idiomatic client/server implementations to work with Telegram Bot API. Please refer to telegramium-examples module for usage examples. There is a support both for polling and webhooks.
API core is generated from the official documentation, so it is believed to cover all the available methods, entities and to be up to date.

Currently the following backends are supported:

- http4s
- cats-effect
- circe
- uPickle (MsgPack binary serialization, to be used for storing API messages in DB and sending over network)

You may want to start with [Api.scala](telegramium-core/src/main/scala/telegramium/bots/client/Api.scala) and [EchoBot.scala](telegramium-examples/src/main/scala/telegramium/bots/examples/EchoBot.scala).

### How to use
Create the dependency by adding the following lines to your build.sbt:

```
libraryDependencies += "io.github.apimorphism" %% "telegramium-core" % "1.0.0-RC3"
libraryDependencies += "io.github.apimorphism" %% "telegramium-high" % "1.0.0-RC3"
```

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

### Note for Yan

Yan, напиши бота.
