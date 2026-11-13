package iozhik

import io.circe.Encoder
import io.circe.Json
import scala.language.implicitConversions

/** A family of objects told apart by a type tag that is open: a value can be one this version doesn't know.
  */
sealed trait OpenUnion[+T]

object OpenUnion {
  final case class Known[T](value: T) extends OpenUnion[T]

  /** A value this version doesn't know: its type tag, and the JSON object it was decoded from, which it encodes back
    * to.
    */
  final case class Unknown(tag: String, raw: Json) extends OpenUnion[Nothing]

  def apply[T](value: T): OpenUnion[T] = Known(value)

  /** A value to send is written without the wrapper: `RichTextBold(RichTextPlain("hi"))`. One way only, so a received
    * value still has to be matched to get at what it holds.
    */
  implicit def lift[T](value: T): OpenUnion[T] = Known(value)

  implicit def encoder[T: Encoder]: Encoder[OpenUnion[T]] = {
    case Known(value)    => Encoder[T].apply(value)
    case Unknown(_, raw) => raw
  }

}
