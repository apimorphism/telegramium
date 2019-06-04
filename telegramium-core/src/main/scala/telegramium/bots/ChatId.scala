package telegramium.bots

sealed trait ChatId {}

final case class ChatIntId(id: Int) extends ChatId

final case class ChatStrId(id: String) extends ChatId
