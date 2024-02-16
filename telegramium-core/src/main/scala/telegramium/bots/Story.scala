package telegramium.bots

/** This object represents a story.
  *
  * @param chat
  *   Chat that posted the story
  * @param id
  *   Unique identifier for the story in the chat
  */
final case class Story(chat: Chat, id: Int)
