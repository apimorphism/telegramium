package io.github.fperiodic.apimorphism.telegramium.bots

/** This object contains information about an incoming shipping query.*/
final case class ShippingQuery(
                               /** Unique query identifier*/
                               id: String,
                               /** User who sent the query*/
                               from: User,
                               /** Bot specified invoice payload*/
                               invoicePayload: String,
                               /** User specified shipping address*/
                               shippingAddress: ShippingAddress)
