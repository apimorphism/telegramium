package telegramium.bots

/** This object represents one shipping option.
  *
  * @param id Shipping option identifier
  * @param title Option title
  * @param prices List of price portions */
final case class ShippingOption(id: String, title: String, prices: List[LabeledPrice] = List.empty)
