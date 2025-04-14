package telegramium.bots.client

import telegramium.bots.AcceptedGiftTypes

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param showGiftButton
  *   Pass True, if a button for sending a gift to the user or by the business account must always be shown in the input
  *   field
  * @param acceptedGiftTypes
  *   Types of gifts accepted by the business account
  */
final case class SetBusinessAccountGiftSettingsReq(
  businessConnectionId: String,
  showGiftButton: Boolean,
  acceptedGiftTypes: AcceptedGiftTypes
)
