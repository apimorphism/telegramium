package telegramium.bots

/** Describes a service message about a transfer with a TON wallet.
  *
  * @param transactionId
  *   Unique identifier of the transaction; may be empty if the transfer hasn't been sent yet
  * @param peerAddress
  *   Address of the transfer peer in the TON blockchain
  * @param amount
  *   The amount of transferred cryptocurrency, in the <em>smallest units</em> of the cryptocurrency, as a decimal
  *   number; negative for outgoing transfers
  * @param comment
  *   <em>Optional</em>. Comment added to the transfer; omitted if the comment is encrypted
  */
final case class TonWalletTransfer(
  transactionId: String,
  peerAddress: String,
  amount: String,
  comment: Option[String] = Option.empty
)
