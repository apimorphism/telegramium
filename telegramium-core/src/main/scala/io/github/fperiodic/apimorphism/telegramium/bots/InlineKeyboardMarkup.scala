package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents an inline keyboard that appears right next to the
  * message it belongs to.*/
final case class InlineKeyboardMarkup(
    /** Array of button rows, each represented by an Array of
      * InlineKeyboardButton objects*/
    inlineKeyboard: List[List[InlineKeyboardButton]] = List.empty)
