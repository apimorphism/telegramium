package telegramium.bots

sealed trait ParseMode {}

case object Markdown extends ParseMode {
  override def toString = "Markdown"
}

case object Markdown2 extends ParseMode {
  override def toString = "MarkdownV2"
}

case object Html extends ParseMode {
  override def toString = "HTML"
}
