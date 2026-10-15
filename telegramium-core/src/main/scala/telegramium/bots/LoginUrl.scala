package telegramium.bots

/** This object represents a parameter of the inline keyboard button used to automatically authorize a user. It serves
  * as a great replacement for the <a href="https://core.telegram.org/widgets/login">Telegram Login Widget</a> when the
  * user is coming from Telegram. All the user needs to do is tap/click a button and confirm that they want to log in:
  *
  * @param url
  *   An HTTPS URL to be opened with user authorization data added to the query string when the button is pressed. If
  *   the user refuses to provide authorization data, the original URL without information about the user will be
  *   opened. The data added is the same as described in <a
  *   href="https://core.telegram.org/widgets/login#receiving-authorization-data">Receiving authorization
  *   data</a>.<br><br><strong>NOTE:</strong> You <strong>must</strong> always check the hash of the received data to
  *   verify the authentication and the integrity of the data as described in <a
  *   href="https://core.telegram.org/widgets/login#checking-authorization">Checking authorization</a>.
  * @param forwardText
  *   <em>Optional</em>. New text of the button in forwarded messages
  * @param botUsername
  *   <em>Optional</em>. Username of a bot, which will be used for user authorization; not supported in <a
  *   href="https://core.telegram.org/bots/api#richmessagebutton">RichMessageButton</a>. See <a
  *   href="https://core.telegram.org/widgets/login#setting-up-a-bot">Setting up a bot</a> for more details. If not
  *   specified, the current bot's username will be assumed. The <em>url</em>'s domain must be the same as the domain
  *   linked with the bot. See <a href="https://core.telegram.org/widgets/login#linking-your-domain-to-the-bot">Linking
  *   your domain to the bot</a> for more details.
  * @param requestWriteAccess
  *   <em>Optional</em>. Pass <em>True</em> to request the permission for your bot to send messages to the user
  */
final case class LoginUrl(
  url: String,
  forwardText: Option[String] = Option.empty,
  botUsername: Option[String] = Option.empty,
  requestWriteAccess: Option[Boolean] = Option.empty
)
