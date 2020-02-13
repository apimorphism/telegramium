package telegramium.bots

sealed trait ParseMode {}

final case object Markdown extends ParseMode

final case object Markdown2 extends ParseMode

final case object Html extends ParseMode
