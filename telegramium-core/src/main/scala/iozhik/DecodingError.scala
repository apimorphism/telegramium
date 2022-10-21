package iozhik

final case class DecodingError(message: String) extends Throwable(message)
