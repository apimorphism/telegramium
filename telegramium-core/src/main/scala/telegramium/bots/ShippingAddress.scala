package telegramium.bots

/** This object represents a shipping address.
  *
  * @param countryCode ISO 3166-1 alpha-2 country code
  * @param state State, if applicable
  * @param city City
  * @param streetLine1 First line for the address
  * @param streetLine2 Second line for the address
  * @param postCode Address post code */
final case class ShippingAddress(countryCode: String,
                                 state: String,
                                 city: String,
                                 streetLine1: String,
                                 streetLine2: String,
                                 postCode: String)
