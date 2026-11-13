package telegramium.bots

/** Cell in a table.
  *
  * @param align
  *   Horizontal cell content alignment. Currently, must be one of “left”, “center”, or “right”.
  * @param valign
  *   Vertical cell content alignment. Currently, must be one of “top”, “middle”, or “bottom”.
  * @param text
  *   <em>Optional</em>. Text in the cell. If omitted, then the cell is invisible.
  * @param isHeader
  *   <em>Optional</em>. <em>True</em>, if the cell is a header cell
  * @param colspan
  *   <em>Optional</em>. The number of columns the cell spans if it is bigger than 1
  * @param rowspan
  *   <em>Optional</em>. The number of rows the cell spans if it is bigger than 1
  */
final case class RichBlockTableCell(
  align: String,
  valign: String,
  text: Option[iozhik.OpenEnum[RichText]] = Option.empty,
  isHeader: Option[Boolean] = Option.empty,
  colspan: Option[Int] = Option.empty,
  rowspan: Option[Int] = Option.empty
)
