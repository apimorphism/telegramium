package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents one size of a photo or a file / sticker thumbnail.*/
final case class PhotoSize(
                           /** Unique identifier for this file*/
                           fileId: String,
                           /** Photo width*/
                           width: Int,
                           /** Photo height*/
                           height: Int,
                           /** Optional. File size*/
                           fileSize: Option[Int] = Option.empty)
