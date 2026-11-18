package fix

import iozhik.OpenEnum
import telegramium.bots.*
import telegramium.bots.high.Methods

// What the rule can't rewrite: it reports these and leaves them as they are
object Telegramium11_1004Reports:
  val tooManyPositional = Methods.sendMessage(ChatIntId(1), "hi", None, Some(3)) 
  val handBuilt         = OpenEnum.Unknown[MessageEntity]("future_entity") 
