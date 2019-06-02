package io.github.fperiodic.apimorphism.telegramium.bots

/** This object describes the position on faces where a mask should be placed by
  * default.*/
final case class MaskPosition(
                              /** The part of the face relative to which the mask should be
                                * placed. One of “forehead”, “eyes”, “mouth”, or “chin”.*/
                              point: String,
                              /** Shift by X-axis measured in widths of the mask scaled to
                                * the face size, from left to right. For example, choosing
                                * -1.0 will place mask just to the left of the default mask
                                * position.*/
                              xShift: Float,
                              /** Shift by Y-axis measured in heights of the mask scaled to
                                * the face size, from top to bottom. For example, 1.0 will
                                * place the mask just below the default mask position.*/
                              yShift: Float,
                              /** Mask scaling coefficient. For example, 2.0 means double
                                * size.*/
                              scale: Float)
