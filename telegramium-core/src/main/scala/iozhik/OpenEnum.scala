package iozhik

import io.circe.Encoder

sealed trait OpenEnum[+T]

object OpenEnum {
  case class Known[T](value: T)        extends OpenEnum[T]
  case class Unknown[T](value: String) extends OpenEnum[T]

  def apply[T](value: T): OpenEnum[T] = Known(value)

  implicit def encoder[T: Encoder]: Encoder[OpenEnum[T]] = {
    case Known(value)   => Encoder[T].apply(value)
    case Unknown(value) => Encoder[String].apply(value)
  }

}
