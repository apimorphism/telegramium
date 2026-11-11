package iozhik

import io.circe.Encoder

/** Indicates that the enumeration is open and can accept values beyond those already specified. `Raw` is how the wire
  * format holds a value: an Unknown one keeps it, to encode back to.
  */
sealed trait OpenEnum[+T, +Raw]

object OpenEnum {
  case class Known[T](value: T) extends OpenEnum[T, Nothing]

  /** A value this version doesn't know: its type tag, and what it was decoded from, which it encodes back to. */
  case class Unknown[Raw](value: String, raw: Raw) extends OpenEnum[Nothing, Raw]

  def apply[T](value: T): OpenEnum[T, Nothing] = Known(value)

  // here, not next to JsonOpenEnum: Scala 3 doesn't look for implicits in package objects
  implicit def encoder[T: Encoder, Raw: Encoder]: Encoder[OpenEnum[T, Raw]] = {
    case Known(value)    => Encoder[T].apply(value)
    case Unknown(_, raw) => Encoder[Raw].apply(raw)
  }

}
