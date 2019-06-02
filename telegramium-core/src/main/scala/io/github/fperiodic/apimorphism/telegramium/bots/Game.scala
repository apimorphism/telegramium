package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents a game. Use BotFather to create and edit games, their
  * short names will act as unique identifiers.*/
final case class Game(
                      /** Title of the game*/
                      title: String,
                      /** Description of the game*/
                      description: String,
                      /** Photo that will be displayed in the game message in chats.*/
                      photo: List[PhotoSize] = List.empty,
                      /** Optional. Brief description of the game or high scores
                        * included in the game message. Can be automatically edited to
                        * include current high scores for the game when the bot calls
                        * setGameScore, or manually edited using editMessageText.
                        * 0-4096 characters.*/
                      text: Option[String] = Option.empty,
                      /** Optional. Special entities that appear in text, such as
                        * usernames, URLs, bot commands, etc.*/
                      textEntities: List[MessageEntity] = List.empty,
                      /** Optional. Animation that will be displayed in the game
                        * message in chats. Upload via BotFather*/
                      animation: Option[Animation] = Option.empty)
