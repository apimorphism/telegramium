package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents a shipping address.*/
final case class ShippingAddress(
                                 /** ISO 3166-1 alpha-2 country code*/
                                 countryCode: String,
                                 /** State, if applicable*/
                                 state: String,
                                 /** City*/
                                 city: String,
                                 /** First line for the address*/
                                 streetLine1: String,
                                 /** Second line for the address*/
                                 streetLine2: String,
                                 /** Address post code*/
                                 postCode: String)
