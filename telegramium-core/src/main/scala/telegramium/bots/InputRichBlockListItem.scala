package telegramium.bots

/** An item of a list to be sent.
  *
  * @param blocks
  *   The content of the item
  * @param hasCheckbox
  *   Optional. Pass True if the item has a checkbox
  * @param isChecked
  *   Optional. Pass True if the item has a checked checkbox
  * @param value
  *   Optional. For ordered lists, the numeric value of the item label
  * @param type
  *   Optional. For ordered lists, the type of the item label; must be one of “a” for lowercase letters, “A” for
  *   uppercase letters, “i” for lowercase Roman numerals, “I” for uppercase Roman numerals, or “1” for decimal numbers
  */
final case class InputRichBlockListItem(
  blocks: List[InputRichBlock] = List.empty,
  hasCheckbox: Option[Boolean] = Option.empty,
  isChecked: Option[Boolean] = Option.empty,
  value: Option[Int] = Option.empty,
  `type`: Option[String] = Option.empty
)
