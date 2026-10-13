package telegramium.bots.high

import cats.effect.Async

import io.circe.Json

import telegramium.bots.Update

private[high] object UnknownUpdateFields {

  /** Top-level JSON keys of [[telegramium.bots.Update]], derived from its declared fields so this stays in sync
    * automatically whenever a new update kind is added to the generated class.
    */
  private val knownFields: Set[String] =
    classOf[Update].getDeclaredFields.map(f => camelToSnakeCase(f.getName)).toSet

  private def camelToSnakeCase(name: String): String =
    name
      .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
      .replaceAll("([a-z\\d])([A-Z])", "$1_$2")
      .toLowerCase

  /** Top-level fields of a raw update that were dropped while decoding it, e.g. a newer Bot API update type. Only
    * top-level fields are checked: a new field nested inside an already-known object (e.g. a new field on `Message`)
    * isn't detected.
    */
  def apply(update: Update, json: Json): List[String] =
    json.asObject.fold(List.empty[String])(_.toList.collect {
      case (field, value) if !value.isNull && !knownFields(field) => field
    })

  def warning(update: Update, unknownFields: List[String]): String =
    s"Update ${update.updateId} contains unknown fields: ${unknownFields.mkString(", ")}. " +
      "It may be a newer Bot API update type, consider upgrading telegramium."

}

private[high] trait UnknownUpdateHandling[F[_]] {

  protected def asyncF: Async[F]

  /** Called before onUpdate for an update containing fields unknown to this version of the library, e.g. a newer Bot
    * API update type. The raw update may contain personal data, so the default implementation only reports the names of
    * the unknown fields.
    */
  def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): F[Unit] =
    asyncF.delay(System.err.println(UnknownUpdateFields.warning(update, unknownFields)))

}
