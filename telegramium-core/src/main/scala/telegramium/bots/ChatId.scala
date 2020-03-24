package telegramium.bots

sealed trait ChatId {}

final case class ChatIntId(id: Long) extends ChatId

final case class ChatStrId(id: String) extends ChatId
