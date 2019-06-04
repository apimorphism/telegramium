package telegramium.bots.client

import telegramium.bots.PassportElementError

final case class SetPassportDataErrorsReq(
                                          /** User identifier*/
                                          userId: Int,
                                          /** A JSON-serialized array describing the errors*/
                                          errors: List[PassportElementError] = List.empty)
