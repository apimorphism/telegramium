package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents an incoming inline query. When the user sends an empty
  * query, your bot could return some default or trending results.*/
final case class InlineQuery(
                             /** Unique identifier for this query*/
                             id: String,
                             /** Sender*/
                             from: User,
                             /** Optional. Sender location, only for bots that request user
                               * location*/
                             location: Option[Location] = Option.empty,
                             /** Text of the query (up to 512 characters)*/
                             query: String,
                             /** Offset of the results to be returned, can be controlled by
                               * the bot*/
                             offset: String)
