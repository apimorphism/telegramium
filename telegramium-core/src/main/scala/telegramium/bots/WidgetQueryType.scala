package telegramium.bots

sealed trait WidgetQueryType {}

/** The query contains data signed by a TON wallet of the user who sent the query. The data is signed for the domain
  * <code>botᐸbot_idᐳ</code>, where <code>ᐸbot_idᐳ</code> is the identifier of the bot.
  *
  * @param wallet
  *   The wallet that signed the data
  * @param signData
  *   JSON-serialized signed data
  */
final case class WidgetQueryTypeSignData(wallet: WidgetQueryWallet, signData: String) extends WidgetQueryType

/** The query notifies the bot about a transaction sent from a TON wallet of the user who sent the query.
  *
  * @param wallet
  *   The wallet from which the transaction was sent
  * @param msgHash
  *   Value of <em>msg_hash</em> of the transaction
  */
final case class WidgetQueryTypeSentTransaction(wallet: WidgetQueryWallet, msgHash: String) extends WidgetQueryType

/** The query contains data for the bot.
  *
  * @param walletCard
  *   <em>Optional</em>. TON wallet card of the user who sent the query, if it was sent to the bot along with the query
  */
final case class WidgetQueryTypeData(walletCard: Option[TonWalletCard] = Option.empty) extends WidgetQueryType
