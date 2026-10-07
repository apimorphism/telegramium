package telegramium.bots.high

import io.circe.Json
import io.circe.syntax.*

import telegramium.bots.CirceImplicits.*
import telegramium.bots.Update

private[high] object UnknownUpdateFields {

  /** Top-level fields of a raw update that were dropped while decoding it, e.g. a newer Bot API update type */
  def apply(update: Update, json: Json): List[String] = {
    val knownFields = update.asJson.asObject.fold(Set.empty[String])(_.keys.toSet)
    json.asObject.fold(List.empty[String])(_.toList.collect {
      case (field, value) if !value.isNull && !knownFields(field) => field
    })
  }

  def warning(update: Update, unknownFields: List[String]): String =
    s"Update ${update.updateId} contains unknown fields: ${unknownFields.mkString(", ")}. " +
      "It may be a newer Bot API update type, consider upgrading telegramium."

}
