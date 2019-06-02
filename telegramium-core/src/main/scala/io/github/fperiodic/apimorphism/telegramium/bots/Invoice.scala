package io.github.fperiodic.apimorphism.telegramium.bots

/** This object contains basic information about an invoice.*/
final case class Invoice(
                         /** Product name*/
                         title: String,
                         /** Product description*/
                         description: String,
                         /** Unique bot deep-linking parameter that can be used to
                           * generate this invoice*/
                         startParameter: String,
                         /** Three-letter ISO 4217 currency code*/
                         currency: String,
                         /** Total price in the smallest units of the currency (integer,
                           * not float/double). For example, for a price of US$ 1.45 pass
                           * amount = 145. See the exp parameter in currencies.json, it
                           * shows the number of digits past the decimal point for each
                           * currency (2 for the majority of currencies).*/
                         totalAmount: Int)
