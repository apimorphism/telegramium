package telegramium.bots.client

import telegramium.bots.PassportElementError

/** @param userId
  *   User identifier
  * @param errors
  *   A JSON-serialized array describing the errors
  */
final case class SetPassportDataErrorsReq(userId: Long, errors: List[PassportElementError] = List.empty)
