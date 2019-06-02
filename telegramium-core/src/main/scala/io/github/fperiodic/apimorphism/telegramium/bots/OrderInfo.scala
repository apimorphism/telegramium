package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents information about an order.*/
final case class OrderInfo(
                           /** Optional. User name*/
                           name: Option[String] = Option.empty,
                           /** Optional. User's phone number*/
                           phoneNumber: Option[String] = Option.empty,
                           /** Optional. User email*/
                           email: Option[String] = Option.empty,
                           /** Optional. User shipping address*/
                           shippingAddress: Option[ShippingAddress] = Option.empty)
