package telegramium.bots

sealed trait ChatType {}

case object ChatTypePrivate extends ChatType {
  override def toString = "private"
}

case object ChatTypeGroup extends ChatType {
  override def toString = "group"
}

case object ChatTypeSupergroup extends ChatType {
  override def toString = "supergroup"
}

case object ChatTypeChannel extends ChatType {
  override def toString = "channel"
}
