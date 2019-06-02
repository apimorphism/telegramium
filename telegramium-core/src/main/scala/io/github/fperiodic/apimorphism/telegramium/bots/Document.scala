package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents a general file (as opposed to photos, voice messages and
  * audio files).*/
final case class Document(
                          /** Unique file identifier*/
                          fileId: String,
                          /** Optional. Document thumbnail as defined by sender*/
                          thumb: Option[PhotoSize] = Option.empty,
                          /** Optional. Original filename as defined by sender*/
                          fileName: Option[String] = Option.empty,
                          /** Optional. MIME type of the file as defined by sender*/
                          mimeType: Option[String] = Option.empty,
                          /** Optional. File size*/
                          fileSize: Option[Int] = Option.empty)
