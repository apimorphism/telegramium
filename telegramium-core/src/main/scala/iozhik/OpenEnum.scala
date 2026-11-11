package iozhik

import io.circe.Encoder
import io.circe.Json

/** Indicates that the enumeration is open and can accept values beyond those already specified.
  */
sealed trait OpenEnum[+T]

object OpenEnum {
  case class Known[T](value: T) extends OpenEnum[T]

  /** A value this version doesn't know: its type tag, and the JSON it was decoded from, which it encodes back to. */
  case class Unknown[T](value: String, raw: Json) extends OpenEnum[T]

  def apply[T](value: T): OpenEnum[T] = Known(value)

  implicit def encoder[T: Encoder]: Encoder[OpenEnum[T]] = {
    case Known(value)    => Encoder[T].apply(value)
    case Unknown(_, raw) => raw
  }

}
