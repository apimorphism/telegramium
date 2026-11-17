package iozhik

import io.circe.Encoder
import io.circe.Json
import scala.language.implicitConversions

/** A value Telegram may add to, an enum's string or an object told apart by a type tag: one this version doesn't know
  * is an Unknown.
  */
sealed trait OpenEnum[+T]

object OpenEnum {
  final case class Known[T](value: T) extends OpenEnum[T]

  /** A value this version doesn't know: the enum's string or the object's type tag, and the JSON it was decoded from,
    * which it encodes back to. `raw` is outside the pattern, so a match reads `case Unknown(value)`.
    */
  final case class Unknown(value: String)(val raw: Json) extends OpenEnum[Nothing] {

    override def equals(that: Any): Boolean = that match {
      case u: Unknown => u.value == value && u.raw == raw
      case _          => false
    }

    override def hashCode: Int = (value, raw).##
  }

  def apply[T](value: T): OpenEnum[T] = Known(value)

  /** A value to send is written without the wrapper: `RichTextBold(RichTextPlain("hi"))`. One way only, so a received
    * value still has to be matched to get at what it holds.
    */
  implicit def lift[T](value: T): OpenEnum[T] = Known(value)

  implicit def encoder[T: Encoder]: Encoder[OpenEnum[T]] = {
    case Known(value)     => Encoder[T].apply(value)
    case unknown: Unknown => unknown.raw
  }

}
