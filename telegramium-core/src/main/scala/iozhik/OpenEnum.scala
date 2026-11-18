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
    * which it encodes back to.
    */
  final case class Unknown(value: String, raw: Json) extends OpenEnum[Nothing] {
    // only the tag: raw may hold personal data, which shouldn't reach a log
    override def toString: String = s"Unknown($value)"
  }

  def apply[T](value: T): OpenEnum[T] = Known(value)

  /** A value to send is written without the wrapper: `RichTextBold(RichTextPlain("hi"))`. One way only, so a received
    * value still has to be matched to get at what it holds.
    */
  implicit def lift[T](value: T): OpenEnum[T] = Known(value)

  implicit def encoder[T: Encoder]: Encoder[OpenEnum[T]] = {
    case Known(value)    => Encoder[T].apply(value)
    case Unknown(_, raw) => raw
  }

  /** `==` takes anything, so the lift doesn't apply to it: `entity == BoldMessageEntity(0, 4)` is always false, and
    * Scala 3 doesn't warn. `entity.equalsKnown(BoldMessageEntity(0, 4))` compares with the Known value, and only takes
    * a value of the wrapped type. The evidence pins that type: with `value: T`, Scala 3 widens `T` to fit the argument,
    * as OpenEnum is covariant, and takes any value.
    */
  implicit final class OpenEnumOps[T](private val e: OpenEnum[T]) extends AnyVal {
    def equalsKnown[U](value: U)(implicit ev: U <:< T): Boolean = e == Known(value)
  }

}
