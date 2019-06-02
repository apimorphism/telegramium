package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.PassportElementError

final case class SetPassportDataErrorsReq(
                                          /** User identifier*/
                                          userId: Int,
                                          /** A JSON-serialized array describing the errors*/
                                          errors: List[PassportElementError] = List.empty)
