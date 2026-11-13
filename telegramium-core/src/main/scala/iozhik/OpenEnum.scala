package iozhik

/** An enum of strings that is open: a value can be one this version doesn't know. Its codecs are generated with the
  * enum's.
  */
sealed trait OpenEnum[+T]

object OpenEnum {
  final case class Known[T](value: T) extends OpenEnum[T]

  /** A string this version doesn't know. */
  final case class Unknown(value: String) extends OpenEnum[Nothing]

  def apply[T](value: T): OpenEnum[T] = Known(value)
}
