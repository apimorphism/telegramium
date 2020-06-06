package telegramium.bots

sealed trait ParseMode {}

final case object Markdown extends ParseMode {
  override def toString() = "Markdown"
}

final case object Markdown2 extends ParseMode {
  override def toString() = "MarkdownV2"
}

final case object Html extends ParseMode {
  override def toString() = "HTML"
}
