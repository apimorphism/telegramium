package object iozhik {

  /** An OpenEnum read from JSON: an Unknown value keeps the JSON it was decoded from. */
  type JsonOpenEnum[+T] = OpenEnum[T, io.circe.Json]
}
