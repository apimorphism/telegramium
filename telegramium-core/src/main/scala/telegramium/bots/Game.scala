package telegramium.bots

/** This object represents a game. Use BotFather to create and edit games, their short names will act as unique
  * identifiers.
  *
  * @param title
  *   Title of the game
  * @param description
  *   Description of the game
  * @param photo
  *   Photo that will be displayed in the game message in chats
  * @param text
  *   <em>Optional</em>. Brief description of the game or high scores included in the game message. Can be automatically
  *   edited to include current high scores for the game when the bot calls <a
  *   href="https://core.telegram.org/bots/api#setgamescore">setGameScore</a>, or manually edited using <a
  *   href="https://core.telegram.org/bots/api#editmessagetext">editMessageText</a>. 0-4096 characters.
  * @param textEntities
  *   <em>Optional</em>. Special entities that appear in <em>text</em>, such as usernames, URLs, bot commands, etc.
  * @param animation
  *   <em>Optional</em>. Animation that will be displayed in the game message in chats. Upload via <a
  *   href="https://t.me/botfather">BotFather</a>.
  */
final case class Game(
  title: String,
  description: String,
  photo: List[PhotoSize] = List.empty,
  text: Option[String] = Option.empty,
  textEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  animation: Option[Animation] = Option.empty
)
