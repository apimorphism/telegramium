package telegramium.bots

/** Contains a list of Telegram Star transactions.
  *
  * @param transactions
  *   The list of transactions
  */
final case class StarTransactions(transactions: List[StarTransaction] = List.empty)
