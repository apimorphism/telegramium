package io.github.fperiodic.apimorphism.telegramium.bots

sealed trait KeyboardMarkup {}

final case class MarkupInlineKeyboard(markup: InlineKeyboardMarkup) extends KeyboardMarkup

final case class MarkupReplyKeyboard(markup: ReplyKeyboardMarkup) extends KeyboardMarkup

final case class MarkupRemoveKeyboard(markup: ReplyKeyboardRemove) extends KeyboardMarkup

final case class MarkupForceReply(markup: ForceReply) extends KeyboardMarkup
