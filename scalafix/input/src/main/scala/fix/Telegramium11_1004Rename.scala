/*
rule = Telegramium11_1004
 */
package fix

import telegramium.bots.StoryAreaPosition
import telegramium.bots.{StoryAreaPosition => Position}

object Telegramium11_1004Rename:
  val position: StoryAreaPosition = StoryAreaPosition(1f, 2f, 3f, 4f, 0f, 0f)
  def width(p: StoryAreaPosition): Float = p.widthPercentage
  val renamed: Position = Position(1f, 2f, 3f, 4f, 0f, 0f)
  val qualified = telegramium.bots.StoryAreaPosition(1f, 2f, 3f, 4f, 0f, 0f)
