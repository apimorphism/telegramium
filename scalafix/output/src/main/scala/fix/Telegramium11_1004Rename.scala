package fix

import telegramium.bots.AreaPosition
import telegramium.bots.{AreaPosition => Position}

object Telegramium11_1004Rename:
  val position: AreaPosition = AreaPosition(1f, 2f, 3f, 4f, 0f, 0f)
  def width(p: AreaPosition): Float = p.widthPercentage
  val renamed: Position = Position(1f, 2f, 3f, 4f, 0f, 0f)
  val qualified = telegramium.bots.AreaPosition(1f, 2f, 3f, 4f, 0f, 0f)
