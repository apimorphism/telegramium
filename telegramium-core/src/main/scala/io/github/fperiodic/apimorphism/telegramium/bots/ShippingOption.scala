package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents one shipping option.*/
final case class ShippingOption(
                                /** Shipping option identifier*/
                                id: String,
                                /** Option title*/
                                title: String,
                                /** List of price portions*/
                                prices: List[LabeledPrice] = List.empty)
