package telegramium.bots

/** An item of a list.
  *
  * @param label
  *   Label of the item
  * @param blocks
  *   The content of the item
  * @param hasCheckbox
  *   Optional. True, if the item has a checkbox
  * @param isChecked
  *   Optional. True, if the item has a checked checkbox
  * @param value
  *   Optional. For ordered lists, the numeric value of the item label
  * @param type
  *   Optional. For ordered lists, the type of the item label; must be one of “a” for lowercase letters, “A” for
  *   uppercase letters, “i” for lowercase Roman numerals, “I” for uppercase Roman numerals, or “1” for decimal numbers
  */
final case class RichBlockListItem(
  label: String,
  blocks: List[iozhik.OpenEnum[RichBlock]] = List.empty,
  hasCheckbox: Option[Boolean] = Option.empty,
  isChecked: Option[Boolean] = Option.empty,
  value: Option[Int] = Option.empty,
  `type`: Option[String] = Option.empty
)
