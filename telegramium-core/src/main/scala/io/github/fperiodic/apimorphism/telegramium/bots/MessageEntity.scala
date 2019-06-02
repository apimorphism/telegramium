package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents one special entity in a text message. For example,
  * hashtags, usernames, URLs, etc.*/
final case class MessageEntity(
                               /** Type of the entity. Can be mention (@username), hashtag,
                                 * cashtag, bot_command, url, email, phone_number, bold (bold
                                 * text), italic (italic text), code (monowidth string), pre
                                 * (monowidth block), text_link (for clickable text URLs),
                                 * text_mention (for users without usernames)*/
                               `type`: String,
                               /** Offset in UTF-16 code units to the start of the entity*/
                               offset: Int,
                               /** Length of the entity in UTF-16 code units*/
                               length: Int,
                               /** Optional. For “text_link” only, url that will be opened
                                 * after user taps on the text*/
                               url: Option[String] = Option.empty,
                               /** Optional. For “text_mention” only, the mentioned user*/
                               user: Option[User] = Option.empty)
