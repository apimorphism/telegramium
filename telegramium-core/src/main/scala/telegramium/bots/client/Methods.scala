package telegramium.bots.client

import io.circe.syntax._
import CirceImplicits._
import telegramium.bots.WebhookInfo
import telegramium.bots._
import telegramium.bots.CirceImplicits._
import telegramium.bots.GameHighScore
import telegramium.bots.Sticker
import telegramium.bots.ChatAdministratorRights
import telegramium.bots.Message
import telegramium.bots.UserProfilePhotos
import telegramium.bots.SentWebAppMessage
import telegramium.bots.File
import telegramium.bots.Poll
import telegramium.bots.ForumTopic
import telegramium.bots.MessageId
import telegramium.bots.ChatInviteLink
import telegramium.bots.User
import telegramium.bots.ChatMember
import telegramium.bots.BotCommand
import telegramium.bots.Chat
import telegramium.bots.MenuButton
import telegramium.bots.Update
import telegramium.bots.StickerSet

trait Methods {

  import io.circe.Decoder

  private implicit def decodeEither[A, B](implicit
    decoderA: Decoder[A],
    decoderB: Decoder[B]
  ): Decoder[Either[A, B]] = decoderA.either(decoderB)

  /** Use this method to get current webhook status. Requires no parameters. On success, returns a WebhookInfo object.
    * If the bot is using getUpdates, will return an object with the url field empty.
    */
  def getWebhookInfo(): Method[WebhookInfo] = {
    val req = GetWebhookInfoReq
    MethodReq[WebhookInfo]("getWebhookInfo", req.asJson)
  }

  /** Use this method to unhide the 'General' topic in a forum supergroup chat. The bot must be an administrator in the
    * chat for this to work and must have the can_manage_topics administrator rights. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    */
  def unhideGeneralForumTopic(chatId: ChatId): Method[Boolean] = {
    val req = UnhideGeneralForumTopicReq(chatId)
    MethodReq[Boolean]("unhideGeneralForumTopic", req.asJson)
  }

  /** Use this method to reopen a closed 'General' topic in a forum supergroup chat. The bot must be an administrator in
    * the chat for this to work and must have the can_manage_topics administrator rights. The topic will be
    * automatically unhidden if it was hidden. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    */
  def reopenGeneralForumTopic(chatId: ChatId): Method[Boolean] = {
    val req = ReopenGeneralForumTopicReq(chatId)
    MethodReq[Boolean]("reopenGeneralForumTopic", req.asJson)
  }

  /** Use this method to change the list of the bot's commands. See this manual for more details about bot commands.
    * Returns True on success.
    *
    * @param commands
    *   A JSON-serialized list of bot commands to be set as the list of the bot's commands. At most 100 commands can be
    *   specified.
    * @param scope
    *   A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to
    *   BotCommandScopeDefault.
    * @param languageCode
    *   A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for
    *   whose language there are no dedicated commands
    */
  def setMyCommands(
    commands: List[BotCommand] = List.empty,
    scope: Option[BotCommandScope] = Option.empty,
    languageCode: Option[String] = Option.empty
  ): Method[Boolean] = {
    val req = SetMyCommandsReq(commands, scope, languageCode)
    MethodReq[Boolean]("setMyCommands", req.asJson)
  }

  /** Use this method to set a new profile photo for the chat. Photos can't be changed for private chats. The bot must
    * be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns True
    * on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param photo
    *   New chat photo, uploaded using multipart/form-data
    */
  def setChatPhoto(chatId: ChatId, photo: IFile): Method[Boolean] = {
    val req = SetChatPhotoReq(chatId, photo)
    MethodReq[Boolean](
      "setChatPhoto",
      req.asJson,
      Map("photo" -> Option(photo)).collect { case (k, Some(v)) => k -> v }
    )
  }

  /** Use this method to get data for high score tables. Will return the score of the specified user and several of
    * their neighbors in a game. Returns an Array of GameHighScore objects.
    *
    * @param userId
    *   Target user id
    * @param chatId
    *   Required if inline_message_id is not specified. Unique identifier for the target chat
    * @param messageId
    *   Required if inline_message_id is not specified. Identifier of the sent message
    * @param inlineMessageId
    *   Required if chat_id and message_id are not specified. Identifier of the inline message
    */
  def getGameHighScores(
    userId: Long,
    chatId: Option[Int] = Option.empty,
    messageId: Option[Int] = Option.empty,
    inlineMessageId: Option[String] = Option.empty
  ): Method[List[GameHighScore]] = {
    val req = GetGameHighScoresReq(userId, chatId, messageId, inlineMessageId)
    MethodReq[List[GameHighScore]]("getGameHighScores", req.asJson)
  }

  /** Use this method to reopen a closed topic in a forum supergroup chat. The bot must be an administrator in the chat
    * for this to work and must have the can_manage_topics administrator rights, unless it is the creator of the topic.
    * Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param messageThreadId
    *   Unique identifier for the target message thread of the forum topic
    */
  def reopenForumTopic(chatId: ChatId, messageThreadId: Int): Method[Boolean] = {
    val req = ReopenForumTopicReq(chatId, messageThreadId)
    MethodReq[Boolean]("reopenForumTopic", req.asJson)
  }

  /** Use this method to clear the list of pinned messages in a chat. If the chat is not a private chat, the bot must be
    * an administrator in the chat for this to work and must have the 'can_pin_messages' administrator right in a
    * supergroup or 'can_edit_messages' administrator right in a channel. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    */
  def unpinAllChatMessages(chatId: ChatId): Method[Boolean] = {
    val req = UnpinAllChatMessagesReq(chatId)
    MethodReq[Boolean]("unpinAllChatMessages", req.asJson)
  }

  /** Use this method to send answers to callback queries sent from inline keyboards. The answer will be displayed to
    * the user as a notification at the top of the chat screen or as an alert. On success, True is returned.
    *
    * @param callbackQueryId
    *   Unique identifier for the query to be answered
    * @param text
    *   Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters
    * @param showAlert
    *   If True, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults
    *   to false.
    * @param url
    *   URL that will be opened by the user's client. If you have created a Game and accepted the conditions via
    *   &#064;BotFather, specify the URL that opens your game - note that this will only work if the query comes from a
    *   callback_game button. Otherwise, you may use links like t.me/your_bot?start=XXXX that open your bot with a
    *   parameter.
    * @param cacheTime
    *   The maximum amount of time in seconds that the result of the callback query may be cached client-side. Telegram
    *   apps will support caching starting in version 3.14. Defaults to 0.
    */
  def answerCallbackQuery(
    callbackQueryId: String,
    text: Option[String] = Option.empty,
    showAlert: Option[Boolean] = Option.empty,
    url: Option[String] = Option.empty,
    cacheTime: Option[Int] = Option.empty
  ): Method[Boolean] = {
    val req = AnswerCallbackQueryReq(callbackQueryId, text, showAlert, url, cacheTime)
    MethodReq[Boolean]("answerCallbackQuery", req.asJson)
  }

  /** Use this method to get custom emoji stickers, which can be used as a forum topic icon by any user. Requires no
    * parameters. Returns an Array of Sticker objects.
    */
  def getForumTopicIconStickers(): Method[List[Sticker]] = {
    val req = GetForumTopicIconStickersReq
    MethodReq[List[Sticker]]("getForumTopicIconStickers", req.asJson)
  }

  /** Use this method to get the current default administrator rights of the bot. Returns ChatAdministratorRights on
    * success.
    *
    * @param forChannels
    *   Pass True to get default administrator rights of the bot in channels. Otherwise, default administrator rights of
    *   the bot for groups and supergroups will be returned.
    */
  def getMyDefaultAdministratorRights(forChannels: Option[Boolean] = Option.empty): Method[ChatAdministratorRights] = {
    val req = GetMyDefaultAdministratorRightsReq(forChannels)
    MethodReq[ChatAdministratorRights]("getMyDefaultAdministratorRights", req.asJson)
  }

  /** Use this method to send text messages. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param text
    *   Text of the message to be sent, 1-4096 characters after entities parsing
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param parseMode
    *   Mode for parsing entities in the message text. See formatting options for more details.
    * @param entities
    *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
    *   parse_mode
    * @param disableWebPagePreview
    *   Disables link previews for links in this message
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendMessage(
    chatId: ChatId,
    text: String,
    messageThreadId: Option[Int] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    entities: List[MessageEntity] = List.empty,
    disableWebPagePreview: Option[Boolean] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendMessageReq(
      chatId,
      text,
      messageThreadId,
      parseMode,
      entities,
      disableWebPagePreview,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendMessage", req.asJson)
  }

  /** Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
    *
    * @param userId
    *   Unique identifier of the target user
    * @param offset
    *   Sequential number of the first photo to be returned. By default, all photos are returned.
    * @param limit
    *   Limits the number of photos to be retrieved. Values between 1-100 are accepted. Defaults to 100.
    */
  def getUserProfilePhotos(
    userId: Long,
    offset: Option[Int] = Option.empty,
    limit: Option[Int] = Option.empty
  ): Method[UserProfilePhotos] = {
    val req = GetUserProfilePhotosReq(userId, offset, limit)
    MethodReq[UserProfilePhotos]("getUserProfilePhotos", req.asJson)
  }

  /** Use this method to send a native poll. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param question
    *   Poll question, 1-300 characters
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param options
    *   A JSON-serialized list of answer options, 2-10 strings 1-100 characters each
    * @param isAnonymous
    *   True, if the poll needs to be anonymous, defaults to True
    * @param type
    *   Poll type, “quiz” or “regular”, defaults to “regular”
    * @param allowsMultipleAnswers
    *   True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
    * @param correctOptionId
    *   0-based identifier of the correct answer option, required for polls in quiz mode
    * @param explanation
    *   Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200
    *   characters with at most 2 line feeds after entities parsing
    * @param explanationParseMode
    *   Mode for parsing entities in the explanation. See formatting options for more details.
    * @param explanationEntities
    *   A JSON-serialized list of special entities that appear in the poll explanation, which can be specified instead
    *   of parse_mode
    * @param openPeriod
    *   Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
    * @param closeDate
    *   Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than
    *   600 seconds in the future. Can't be used together with open_period.
    * @param isClosed
    *   Pass True if the poll needs to be immediately closed. This can be useful for poll preview.
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendPoll(
    chatId: ChatId,
    question: String,
    messageThreadId: Option[Int] = Option.empty,
    options: List[String] = List.empty,
    isAnonymous: Option[Boolean] = Option.empty,
    `type`: Option[String] = Option.empty,
    allowsMultipleAnswers: Option[Boolean] = Option.empty,
    correctOptionId: Option[Int] = Option.empty,
    explanation: Option[String] = Option.empty,
    explanationParseMode: Option[String] = Option.empty,
    explanationEntities: List[MessageEntity] = List.empty,
    openPeriod: Option[Int] = Option.empty,
    closeDate: Option[Int] = Option.empty,
    isClosed: Option[Boolean] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendPollReq(
      chatId,
      question,
      messageThreadId,
      options,
      isAnonymous,
      `type`,
      allowsMultipleAnswers,
      correctOptionId,
      explanation,
      explanationParseMode,
      explanationEntities,
      openPeriod,
      closeDate,
      isClosed,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendPoll", req.asJson)
  }

  /** Use this method to set the result of an interaction with a Web App and send a corresponding message on behalf of
    * the user to the chat from which the query originated. On success, a SentWebAppMessage object is returned.
    *
    * @param webAppQueryId
    *   Unique identifier for the query to be answered
    * @param result
    *   A JSON-serialized object describing the message to be sent
    */
  def answerWebAppQuery(webAppQueryId: String, result: InlineQueryResult): Method[SentWebAppMessage] = {
    val req = AnswerWebAppQueryReq(webAppQueryId, result)
    MethodReq[SentWebAppMessage]("answerWebAppQuery", req.asJson)
  }

  /** Use this method to send phone contacts. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param phoneNumber
    *   Contact's phone number
    * @param firstName
    *   Contact's first name
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param lastName
    *   Contact's last name
    * @param vcard
    *   Additional data about the contact in the form of a vCard, 0-2048 bytes
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendContact(
    chatId: ChatId,
    phoneNumber: String,
    firstName: String,
    messageThreadId: Option[Int] = Option.empty,
    lastName: Option[String] = Option.empty,
    vcard: Option[String] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendContactReq(
      chatId,
      phoneNumber,
      firstName,
      messageThreadId,
      lastName,
      vcard,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendContact", req.asJson)
  }

  /** Use this method to create a new sticker set owned by a user. The bot will be able to edit the sticker set thus
    * created. You must use exactly one of the fields png_sticker, tgs_sticker, or webm_sticker. Returns True on
    * success.
    *
    * @param userId
    *   User identifier of created sticker set owner
    * @param name
    *   Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals). Can contain only English
    *   letters, digits and underscores. Must begin with a letter, can't contain consecutive underscores and must end in
    *   "_by_<bot_username>". <bot_username> is case insensitive. 1-64 characters.
    * @param title
    *   Sticker set title, 1-64 characters
    * @param emojis
    *   One or more emoji corresponding to the sticker
    * @param pngSticker
    *   PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either
    *   width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the
    *   Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one
    *   using multipart/form-data.
    * @param tgsSticker
    *   TGS animation with the sticker, uploaded using multipart/form-data. See
    *   https://core.telegram.org/stickers#animated-sticker-requirements for technical requirements
    * @param webmSticker
    *   WEBM video with the sticker, uploaded using multipart/form-data. See
    *   https://core.telegram.org/stickers#video-sticker-requirements for technical requirements
    * @param stickerType
    *   Type of stickers in the set, pass “regular” or “mask”. Custom emoji sticker sets can't be created via the Bot
    *   API at the moment. By default, a regular sticker set is created.
    * @param maskPosition
    *   A JSON-serialized object for position where the mask should be placed on faces
    */
  def createNewStickerSet(
    userId: Long,
    name: String,
    title: String,
    emojis: String,
    pngSticker: Option[IFile] = Option.empty,
    tgsSticker: Option[IFile] = Option.empty,
    webmSticker: Option[IFile] = Option.empty,
    stickerType: Option[String] = Option.empty,
    maskPosition: Option[MaskPosition] = Option.empty
  ): Method[Boolean] = {
    val req = CreateNewStickerSetReq(
      userId,
      name,
      title,
      emojis,
      pngSticker,
      tgsSticker,
      webmSticker,
      stickerType,
      maskPosition
    )
    MethodReq[Boolean](
      "createNewStickerSet",
      req.asJson,
      Map("png_sticker" -> pngSticker, "tgs_sticker" -> tgsSticker, "webm_sticker" -> webmSticker).collect {
        case (k, Some(v)) => k -> v
      }
    )
  }

  /** Use this method to upload a .PNG file with a sticker for later use in createNewStickerSet and addStickerToSet
    * methods (can be used multiple times). Returns the uploaded File on success.
    *
    * @param userId
    *   User identifier of sticker file owner
    * @param pngSticker
    *   PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either
    *   width or height must be exactly 512px.
    */
  def uploadStickerFile(userId: Long, pngSticker: IFile): Method[File] = {
    val req = UploadStickerFileReq(userId, pngSticker)
    MethodReq[File](
      "uploadStickerFile",
      req.asJson,
      Map("png_sticker" -> Option(pngSticker)).collect { case (k, Some(v)) => k -> v }
    )
  }

  /** Use this method to set default chat permissions for all members. The bot must be an administrator in the group or
    * a supergroup for this to work and must have the can_restrict_members administrator rights. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param permissions
    *   A JSON-serialized object for new default chat permissions
    */
  def setChatPermissions(chatId: ChatId, permissions: ChatPermissions): Method[Boolean] = {
    val req = SetChatPermissionsReq(chatId, permissions)
    MethodReq[Boolean]("setChatPermissions", req.asJson)
  }

  /** Use this method to ban a channel chat in a supergroup or a channel. Until the chat is unbanned, the owner of the
    * banned chat won't be able to send messages on behalf of any of their channels. The bot must be an administrator in
    * the supergroup or channel for this to work and must have the appropriate administrator rights. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param senderChatId
    *   Unique identifier of the target sender chat
    */
  def banChatSenderChat(chatId: ChatId, senderChatId: Int): Method[Boolean] = {
    val req = BanChatSenderChatReq(chatId, senderChatId)
    MethodReq[Boolean]("banChatSenderChat", req.asJson)
  }

  /** Use this method to send point on the map. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param latitude
    *   Latitude of the location
    * @param longitude
    *   Longitude of the location
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param horizontalAccuracy
    *   The radius of uncertainty for the location, measured in meters; 0-1500
    * @param livePeriod
    *   Period in seconds for which the location will be updated (see Live Locations, should be between 60 and 86400.
    * @param heading
    *   For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
    * @param proximityAlertRadius
    *   For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters.
    *   Must be between 1 and 100000 if specified.
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendLocation(
    chatId: ChatId,
    latitude: Float,
    longitude: Float,
    messageThreadId: Option[Int] = Option.empty,
    horizontalAccuracy: Option[Float] = Option.empty,
    livePeriod: Option[Int] = Option.empty,
    heading: Option[Int] = Option.empty,
    proximityAlertRadius: Option[Int] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendLocationReq(
      chatId,
      latitude,
      longitude,
      messageThreadId,
      horizontalAccuracy,
      livePeriod,
      heading,
      proximityAlertRadius,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendLocation", req.asJson)
  }

  /** Use this method to delete a group sticker set from a supergroup. The bot must be an administrator in the chat for
    * this to work and must have the appropriate administrator rights. Use the field can_set_sticker_set optionally
    * returned in getChat requests to check if the bot can use this method. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    */
  def deleteChatStickerSet(chatId: ChatId): Method[Boolean] = {
    val req = DeleteChatStickerSetReq(chatId)
    MethodReq[Boolean]("deleteChatStickerSet", req.asJson)
  }

  /** Use this method to stop updating a live location message before live_period expires. On success, if the message is
    * not an inline message, the edited Message is returned, otherwise True is returned.
    *
    * @param chatId
    *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
    *   channel (in the format &#064;channelusername)
    * @param messageId
    *   Required if inline_message_id is not specified. Identifier of the message with live location to stop
    * @param inlineMessageId
    *   Required if chat_id and message_id are not specified. Identifier of the inline message
    * @param replyMarkup
    *   A JSON-serialized object for a new inline keyboard.
    */
  def stopMessageLiveLocation(
    chatId: Option[ChatId] = Option.empty,
    messageId: Option[Int] = Option.empty,
    inlineMessageId: Option[String] = Option.empty,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Either[Boolean, Message]] = {
    val req = StopMessageLiveLocationReq(chatId, messageId, inlineMessageId, replyMarkup)
    MethodReq[Either[Boolean, Message]]("stopMessageLiveLocation", req.asJson)
  }

  /** Use this method to generate a new primary invite link for a chat; any previously generated primary link is
    * revoked. The bot must be an administrator in the chat for this to work and must have the appropriate administrator
    * rights. Returns the new invite link as String on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    */
  def exportChatInviteLink(chatId: ChatId): Method[String] = {
    val req = ExportChatInviteLinkReq(chatId)
    MethodReq[String]("exportChatInviteLink", req.asJson)
  }

  /** Use this method to decline a chat join request. The bot must be an administrator in the chat for this to work and
    * must have the can_invite_users administrator right. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param userId
    *   Unique identifier of the target user
    */
  def declineChatJoinRequest(chatId: ChatId, userId: Long): Method[Boolean] = {
    val req = DeclineChatJoinRequestReq(chatId, userId)
    MethodReq[Boolean]("declineChatJoinRequest", req.asJson)
  }

  /** Use this method to send an animated emoji that will display a random value. On success, the sent Message is
    * returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param emoji
    *   Emoji on which the dice throw animation is based. Currently, must be one of EmojiDice, EmojiDarts,
    *   EmojiBasketball, EmojiFootball, EmojiBowling or EmojiSlotMachine. Dice can have values 1-6 for EmojiDice,
    *   EmojiDarts and EmojiBowling, values 1-5 for EmojiBasketball and EmojiFootball, and values 1-64 for
    *   EmojiSlotMachine. Defaults to EmojiDice
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendDice(
    chatId: ChatId,
    messageThreadId: Option[Int] = Option.empty,
    emoji: Option[Emoji] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendDiceReq(
      chatId,
      messageThreadId,
      emoji,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendDice", req.asJson)
  }

  /** Use this method when you need to tell the user that something is happening on the bot's side. The status is set
    * for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status). Returns
    * True on success. We only recommend using this method when a response from the bot will take a noticeable amount of
    * time to arrive.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param action
    *   Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text
    *   messages, upload_photo for photos, record_video or upload_video for videos, record_voice or upload_voice for
    *   voice notes, upload_document for general files, choose_sticker for stickers, find_location for location data,
    *   record_video_note or upload_video_note for video notes.
    * @param messageThreadId
    *   Unique identifier for the target message thread; supergroups only
    */
  def sendChatAction(chatId: ChatId, action: String, messageThreadId: Option[Int] = Option.empty): Method[Boolean] = {
    val req = SendChatActionReq(chatId, action, messageThreadId)
    MethodReq[Boolean]("sendChatAction", req.asJson)
  }

  /** Use this method to add a new sticker to a set created by the bot. You must use exactly one of the fields
    * png_sticker, tgs_sticker, or webm_sticker. Animated stickers can be added to animated sticker sets and only to
    * them. Animated sticker sets can have up to 50 stickers. Static sticker sets can have up to 120 stickers. Returns
    * True on success.
    *
    * @param userId
    *   User identifier of sticker set owner
    * @param name
    *   Sticker set name
    * @param emojis
    *   One or more emoji corresponding to the sticker
    * @param pngSticker
    *   PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either
    *   width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the
    *   Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one
    *   using multipart/form-data.
    * @param tgsSticker
    *   TGS animation with the sticker, uploaded using multipart/form-data. See
    *   https://core.telegram.org/stickers#animated-sticker-requirements for technical requirements
    * @param webmSticker
    *   WEBM video with the sticker, uploaded using multipart/form-data. See
    *   https://core.telegram.org/stickers#video-sticker-requirements for technical requirements
    * @param maskPosition
    *   A JSON-serialized object for position where the mask should be placed on faces
    */
  def addStickerToSet(
    userId: Long,
    name: String,
    emojis: String,
    pngSticker: Option[IFile] = Option.empty,
    tgsSticker: Option[IFile] = Option.empty,
    webmSticker: Option[IFile] = Option.empty,
    maskPosition: Option[MaskPosition] = Option.empty
  ): Method[Boolean] = {
    val req = AddStickerToSetReq(userId, name, emojis, pngSticker, tgsSticker, webmSticker, maskPosition)
    MethodReq[Boolean](
      "addStickerToSet",
      req.asJson,
      Map("png_sticker" -> pngSticker, "tgs_sticker" -> tgsSticker, "webm_sticker" -> webmSticker).collect {
        case (k, Some(v)) => k -> v
      }
    )
  }

  /** Use this method to create a link for an invoice. Returns the created invoice link as String on success.
    *
    * @param title
    *   Product name, 1-32 characters
    * @param description
    *   Product description, 1-255 characters
    * @param payload
    *   Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal
    *   processes.
    * @param providerToken
    *   Payment provider token, obtained via BotFather
    * @param currency
    *   Three-letter ISO 4217 currency code, see more on currencies
    * @param prices
    *   Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost,
    *   delivery tax, bonus, etc.)
    * @param maxTipAmount
    *   The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double). For
    *   example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145. See the exp parameter in currencies.json, it
    *   shows the number of digits past the decimal point for each currency (2 for the majority of currencies). Defaults
    *   to 0
    * @param suggestedTipAmounts
    *   A JSON-serialized array of suggested amounts of tips in the smallest units of the currency (integer, not
    *   float/double). At most 4 suggested tip amounts can be specified. The suggested tip amounts must be positive,
    *   passed in a strictly increased order and must not exceed max_tip_amount.
    * @param providerData
    *   JSON-serialized data about the invoice, which will be shared with the payment provider. A detailed description
    *   of required fields should be provided by the payment provider.
    * @param photoUrl
    *   URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a service.
    * @param photoSize
    *   Photo size in bytes
    * @param photoWidth
    *   Photo width
    * @param photoHeight
    *   Photo height
    * @param needName
    *   Pass True if you require the user's full name to complete the order
    * @param needPhoneNumber
    *   Pass True if you require the user's phone number to complete the order
    * @param needEmail
    *   Pass True if you require the user's email address to complete the order
    * @param needShippingAddress
    *   Pass True if you require the user's shipping address to complete the order
    * @param sendPhoneNumberToProvider
    *   Pass True if the user's phone number should be sent to the provider
    * @param sendEmailToProvider
    *   Pass True if the user's email address should be sent to the provider
    * @param isFlexible
    *   Pass True if the final price depends on the shipping method
    */
  def createInvoiceLink(
    title: String,
    description: String,
    payload: String,
    providerToken: String,
    currency: String,
    prices: List[LabeledPrice] = List.empty,
    maxTipAmount: Option[Int] = Option.empty,
    suggestedTipAmounts: List[Int] = List.empty,
    providerData: Option[String] = Option.empty,
    photoUrl: Option[String] = Option.empty,
    photoSize: Option[Long] = Option.empty,
    photoWidth: Option[Int] = Option.empty,
    photoHeight: Option[Int] = Option.empty,
    needName: Option[Boolean] = Option.empty,
    needPhoneNumber: Option[Boolean] = Option.empty,
    needEmail: Option[Boolean] = Option.empty,
    needShippingAddress: Option[Boolean] = Option.empty,
    sendPhoneNumberToProvider: Option[Boolean] = Option.empty,
    sendEmailToProvider: Option[Boolean] = Option.empty,
    isFlexible: Option[Boolean] = Option.empty
  ): Method[String] = {
    val req = CreateInvoiceLinkReq(
      title,
      description,
      payload,
      providerToken,
      currency,
      prices,
      maxTipAmount,
      suggestedTipAmounts,
      providerData,
      photoUrl,
      photoSize,
      photoWidth,
      photoHeight,
      needName,
      needPhoneNumber,
      needEmail,
      needShippingAddress,
      sendPhoneNumberToProvider,
      sendEmailToProvider,
      isFlexible
    )
    MethodReq[String]("createInvoiceLink", req.asJson)
  }

  /** Use this method to delete a sticker from a set created by the bot. Returns True on success.
    *
    * @param sticker
    *   File identifier of the sticker
    */
  def deleteStickerFromSet(sticker: String): Method[Boolean] = {
    val req = DeleteStickerFromSetReq(sticker)
    MethodReq[Boolean]("deleteStickerFromSet", req.asJson)
  }

  /** Use this method to stop a poll which was sent by the bot. On success, the stopped Poll is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param messageId
    *   Identifier of the original message with the poll
    * @param replyMarkup
    *   A JSON-serialized object for a new message inline keyboard.
    */
  def stopPoll(
    chatId: ChatId,
    messageId: Int,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Poll] = {
    val req = StopPollReq(chatId, messageId, replyMarkup)
    MethodReq[Poll]("stopPoll", req.asJson)
  }

  /** Use this method to remove a message from the list of pinned messages in a chat. If the chat is not a private chat,
    * the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' administrator
    * right in a supergroup or 'can_edit_messages' administrator right in a channel. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param messageId
    *   Identifier of a message to unpin. If not specified, the most recent pinned message (by sending date) will be
    *   unpinned.
    */
  def unpinChatMessage(chatId: ChatId, messageId: Option[Int] = Option.empty): Method[Boolean] = {
    val req = UnpinChatMessageReq(chatId, messageId)
    MethodReq[Boolean]("unpinChatMessage", req.asJson)
  }

  /** Use this method to get the number of members in a chat. Returns Int on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup or channel (in the format
    *   &#064;channelusername)
    */
  def getChatMemberCount(chatId: ChatId): Method[Int] = {
    val req = GetChatMemberCountReq(chatId)
    MethodReq[Int]("getChatMemberCount", req.asJson)
  }

  /** Use this method to send a group of photos, videos, documents or audios as an album. Documents and audio files can
    * be only grouped in an album with messages of the same type. On success, an array of Messages that were sent is
    * returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param media
    *   A JSON-serialized array describing messages to be sent, must include 2-10 items
    * @param disableNotification
    *   Sends messages silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent messages from forwarding and saving
    * @param replyToMessageId
    *   If the messages are a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    */
  def sendMediaGroup(
    chatId: ChatId,
    messageThreadId: Option[Int] = Option.empty,
    media: List[InputMedia] = List.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty
  ): Method[List[Message]] = {
    val req = SendMediaGroupReq(
      chatId,
      messageThreadId,
      media,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply
    )
    MethodReq[List[Message]]("sendMediaGroup", req.asJson)
  }

  /** Use this method to send a game. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat
    * @param gameShortName
    *   Short name of the game, serves as the unique identifier for the game. Set up your games via &#064;BotFather.
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   A JSON-serialized object for an inline keyboard. If empty, one 'Play game_title' button will be shown. If not
    *   empty, the first button must launch the game.
    */
  def sendGame(
    chatId: Int,
    gameShortName: String,
    messageThreadId: Option[Int] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendGameReq(
      chatId,
      gameShortName,
      messageThreadId,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendGame", req.asJson)
  }

  /** Use this method to send information about a venue. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param latitude
    *   Latitude of the venue
    * @param longitude
    *   Longitude of the venue
    * @param title
    *   Name of the venue
    * @param address
    *   Address of the venue
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param foursquareId
    *   Foursquare identifier of the venue
    * @param foursquareType
    *   Foursquare type of the venue, if known. (For example, “arts_entertainment/default”,
    *   “arts_entertainment/aquarium” or “food/icecream”.)
    * @param googlePlaceId
    *   Google Places identifier of the venue
    * @param googlePlaceType
    *   Google Places type of the venue. (See supported types.)
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendVenue(
    chatId: ChatId,
    latitude: Float,
    longitude: Float,
    title: String,
    address: String,
    messageThreadId: Option[Int] = Option.empty,
    foursquareId: Option[String] = Option.empty,
    foursquareType: Option[String] = Option.empty,
    googlePlaceId: Option[String] = Option.empty,
    googlePlaceType: Option[String] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendVenueReq(
      chatId,
      latitude,
      longitude,
      title,
      address,
      messageThreadId,
      foursquareId,
      foursquareType,
      googlePlaceId,
      googlePlaceType,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendVenue", req.asJson)
  }

  /** Use this method to create a topic in a forum supergroup chat. The bot must be an administrator in the chat for
    * this to work and must have the can_manage_topics administrator rights. Returns information about the created topic
    * as a ForumTopic object.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param name
    *   Topic name, 1-128 characters
    * @param iconColor
    *   Color of the topic icon in RGB format. Currently, must be one of 7322096 (0x6FB9F0), 16766590 (0xFFD67E),
    *   13338331 (0xCB86DB), 9367192 (0x8EEE98), 16749490 (0xFF93B2), or 16478047 (0xFB6F5F)
    * @param iconCustomEmojiId
    *   Unique identifier of the custom emoji shown as the topic icon. Use getForumTopicIconStickers to get all allowed
    *   custom emoji identifiers.
    */
  def createForumTopic(
    chatId: ChatId,
    name: String,
    iconColor: Option[Int] = Option.empty,
    iconCustomEmojiId: Option[String] = Option.empty
  ): Method[ForumTopic] = {
    val req = CreateForumTopicReq(chatId, name, iconColor, iconCustomEmojiId)
    MethodReq[ForumTopic]("createForumTopic", req.asJson)
  }

  /** Use this method to edit the name of the 'General' topic in a forum supergroup chat. The bot must be an
    * administrator in the chat for this to work and must have can_manage_topics administrator rights. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param name
    *   New topic name, 1-128 characters
    */
  def editGeneralForumTopic(chatId: ChatId, name: String): Method[Boolean] = {
    val req = EditGeneralForumTopicReq(chatId, name)
    MethodReq[Boolean]("editGeneralForumTopic", req.asJson)
  }

  /** Use this method to unban a previously banned user in a supergroup or channel. The user will not return to the
    * group or channel automatically, but will be able to join via link, etc. The bot must be an administrator for this
    * to work. By default, this method guarantees that after the call the user is not a member of the chat, but will be
    * able to join it. So if the user is a member of the chat they will also be removed from the chat. If you don't want
    * this, use the parameter only_if_banned. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target group or username of the target supergroup or channel (in the format
    *   &#064;channelusername)
    * @param userId
    *   Unique identifier of the target user
    * @param onlyIfBanned
    *   Do nothing if the user is not banned
    */
  def unbanChatMember(chatId: ChatId, userId: Long, onlyIfBanned: Option[Boolean] = Option.empty): Method[Boolean] = {
    val req = UnbanChatMemberReq(chatId, userId, onlyIfBanned)
    MethodReq[Boolean]("unbanChatMember", req.asJson)
  }

  /** Use this method to get information about custom emoji stickers by their identifiers. Returns an Array of Sticker
    * objects.
    *
    * @param customEmojiIds
    *   List of custom emoji identifiers. At most 200 custom emoji identifiers can be specified.
    */
  def getCustomEmojiStickers(customEmojiIds: List[String] = List.empty): Method[List[Sticker]] = {
    val req = GetCustomEmojiStickersReq(customEmojiIds)
    MethodReq[List[Sticker]]("getCustomEmojiStickers", req.asJson)
  }

  /** Use this method to delete a forum topic along with all its messages in a forum supergroup chat. The bot must be an
    * administrator in the chat for this to work and must have the can_delete_messages administrator rights. Returns
    * True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param messageThreadId
    *   Unique identifier for the target message thread of the forum topic
    */
  def deleteForumTopic(chatId: ChatId, messageThreadId: Int): Method[Boolean] = {
    val req = DeleteForumTopicReq(chatId, messageThreadId)
    MethodReq[Boolean]("deleteForumTopic", req.asJson)
  }

  /** Use this method to change the description of a group, a supergroup or a channel. The bot must be an administrator
    * in the chat for this to work and must have the appropriate administrator rights. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param description
    *   New chat description, 0-255 characters
    */
  def setChatDescription(chatId: ChatId, description: Option[String] = Option.empty): Method[Boolean] = {
    val req = SetChatDescriptionReq(chatId, description)
    MethodReq[Boolean]("setChatDescription", req.asJson)
  }

  /** Use this method to edit text and game messages. On success, if the edited message is not an inline message, the
    * edited Message is returned, otherwise True is returned.
    *
    * @param text
    *   New text of the message, 1-4096 characters after entities parsing
    * @param chatId
    *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
    *   channel (in the format &#064;channelusername)
    * @param messageId
    *   Required if inline_message_id is not specified. Identifier of the message to edit
    * @param inlineMessageId
    *   Required if chat_id and message_id are not specified. Identifier of the inline message
    * @param parseMode
    *   Mode for parsing entities in the message text. See formatting options for more details.
    * @param entities
    *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
    *   parse_mode
    * @param disableWebPagePreview
    *   Disables link previews for links in this message
    * @param replyMarkup
    *   A JSON-serialized object for an inline keyboard.
    */
  def editMessageText(
    text: String,
    chatId: Option[ChatId] = Option.empty,
    messageId: Option[Int] = Option.empty,
    inlineMessageId: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    entities: List[MessageEntity] = List.empty,
    disableWebPagePreview: Option[Boolean] = Option.empty,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Either[Boolean, Message]] = {
    val req = EditMessageTextReq(
      text,
      chatId,
      messageId,
      inlineMessageId,
      parseMode,
      entities,
      disableWebPagePreview,
      replyMarkup
    )
    MethodReq[Either[Boolean, Message]]("editMessageText", req.asJson)
  }

  /** Use this method to edit live location messages. A location can be edited until its live_period expires or editing
    * is explicitly disabled by a call to stopMessageLiveLocation. On success, if the edited message is not an inline
    * message, the edited Message is returned, otherwise True is returned.
    *
    * @param latitude
    *   Latitude of new location
    * @param longitude
    *   Longitude of new location
    * @param chatId
    *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
    *   channel (in the format &#064;channelusername)
    * @param messageId
    *   Required if inline_message_id is not specified. Identifier of the message to edit
    * @param inlineMessageId
    *   Required if chat_id and message_id are not specified. Identifier of the inline message
    * @param horizontalAccuracy
    *   The radius of uncertainty for the location, measured in meters; 0-1500
    * @param heading
    *   Direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
    * @param proximityAlertRadius
    *   The maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1
    *   and 100000 if specified.
    * @param replyMarkup
    *   A JSON-serialized object for a new inline keyboard.
    */
  def editMessageLiveLocation(
    latitude: Float,
    longitude: Float,
    chatId: Option[ChatId] = Option.empty,
    messageId: Option[Int] = Option.empty,
    inlineMessageId: Option[String] = Option.empty,
    horizontalAccuracy: Option[Float] = Option.empty,
    heading: Option[Int] = Option.empty,
    proximityAlertRadius: Option[Int] = Option.empty,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Either[Boolean, Message]] = {
    val req = EditMessageLiveLocationReq(
      latitude,
      longitude,
      chatId,
      messageId,
      inlineMessageId,
      horizontalAccuracy,
      heading,
      proximityAlertRadius,
      replyMarkup
    )
    MethodReq[Either[Boolean, Message]]("editMessageLiveLocation", req.asJson)
  }

  /** Use this method to clear the list of pinned messages in a forum topic. The bot must be an administrator in the
    * chat for this to work and must have the can_pin_messages administrator right in the supergroup. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param messageThreadId
    *   Unique identifier for the target message thread of the forum topic
    */
  def unpinAllForumTopicMessages(chatId: ChatId, messageThreadId: Int): Method[Boolean] = {
    val req = UnpinAllForumTopicMessagesReq(chatId, messageThreadId)
    MethodReq[Boolean]("unpinAllForumTopicMessages", req.asJson)
  }

  /** Use this method to get basic information about a file and prepare it for downloading. For the moment, bots can
    * download files of up to 20MB in size. On success, a File object is returned. The file can then be downloaded via
    * the link https://api.telegram.org/file/bot<token>/<file_path>, where <file_path> is taken from the response. It is
    * guaranteed that the link will be valid for at least 1 hour. When the link expires, a new one can be requested by
    * calling getFile again.
    *
    * @param fileId
    *   File identifier to get information about
    */
  def getFile(fileId: String): Method[File] = {
    val req = GetFileReq(fileId)
    MethodReq[File]("getFile", req.asJson)
  }

  /** Use this method to set the score of the specified user in a game message. On success, if the message is not an
    * inline message, the Message is returned, otherwise True is returned. Returns an error, if the new score is not
    * greater than the user's current score in the chat and force is False.
    *
    * @param userId
    *   User identifier
    * @param score
    *   New score, must be non-negative
    * @param force
    *   Pass True if the high score is allowed to decrease. This can be useful when fixing mistakes or banning cheaters
    * @param disableEditMessage
    *   Pass True if the game message should not be automatically edited to include the current scoreboard
    * @param chatId
    *   Required if inline_message_id is not specified. Unique identifier for the target chat
    * @param messageId
    *   Required if inline_message_id is not specified. Identifier of the sent message
    * @param inlineMessageId
    *   Required if chat_id and message_id are not specified. Identifier of the inline message
    */
  def setGameScore(
    userId: Long,
    score: Int,
    force: Option[Boolean] = Option.empty,
    disableEditMessage: Option[Boolean] = Option.empty,
    chatId: Option[Int] = Option.empty,
    messageId: Option[Int] = Option.empty,
    inlineMessageId: Option[String] = Option.empty
  ): Method[Either[Boolean, Message]] = {
    val req = SetGameScoreReq(userId, score, force, disableEditMessage, chatId, messageId, inlineMessageId)
    MethodReq[Either[Boolean, Message]]("setGameScore", req.asJson)
  }

  /** Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup or channel (in the format
    *   &#064;channelusername)
    */
  def leaveChat(chatId: ChatId): Method[Boolean] = {
    val req = LeaveChatReq(chatId)
    MethodReq[Boolean]("leaveChat", req.asJson)
  }

  /** Use this method to change the title of a chat. Titles can't be changed for private chats. The bot must be an
    * administrator in the chat for this to work and must have the appropriate administrator rights. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param title
    *   New chat title, 1-128 characters
    */
  def setChatTitle(chatId: ChatId, title: String): Method[Boolean] = {
    val req = SetChatTitleReq(chatId, title)
    MethodReq[Boolean]("setChatTitle", req.asJson)
  }

  /** Use this method to copy messages of any kind. Service messages and invoice messages can't be copied. A quiz poll
    * can be copied only if the value of the field correct_option_id is known to the bot. The method is analogous to the
    * method forwardMessage, but the copied message doesn't have a link to the original message. Returns the MessageId
    * of the sent message on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param fromChatId
    *   Unique identifier for the chat where the original message was sent (or channel username in the format
    *   &#064;channelusername)
    * @param messageId
    *   Message identifier in the chat specified in from_chat_id
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param caption
    *   New caption for media, 0-1024 characters after entities parsing. If not specified, the original caption is kept
    * @param parseMode
    *   Mode for parsing entities in the new caption. See formatting options for more details.
    * @param captionEntities
    *   A JSON-serialized list of special entities that appear in the new caption, which can be specified instead of
    *   parse_mode
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def copyMessage(
    chatId: ChatId,
    fromChatId: ChatId,
    messageId: Int,
    messageThreadId: Option[Int] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    captionEntities: List[MessageEntity] = List.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[MessageId] = {
    val req = CopyMessageReq(
      chatId,
      fromChatId,
      messageId,
      messageThreadId,
      caption,
      parseMode,
      captionEntities,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[MessageId]("copyMessage", req.asJson)
  }

  /** As of v.4.0, Telegram clients support rounded square MPEG4 videos of up to 1 minute long. Use this method to send
    * video messages. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param videoNote
    *   Video note to send. Pass a file_id as String to send a video note that exists on the Telegram servers
    *   (recommended) or upload a new video using multipart/form-data. Sending video notes by a URL is currently
    *   unsupported
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param duration
    *   Duration of sent video in seconds
    * @param length
    *   Video width and height, i.e. diameter of the video message
    * @param thumb
    *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
    *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not
    *   exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be
    *   only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
    *   multipart/form-data under <file_attach_name>.
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendVideoNote(
    chatId: ChatId,
    videoNote: IFile,
    messageThreadId: Option[Int] = Option.empty,
    duration: Option[Int] = Option.empty,
    length: Option[Int] = Option.empty,
    thumb: Option[IFile] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendVideoNoteReq(
      chatId,
      videoNote,
      messageThreadId,
      duration,
      length,
      thumb,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message](
      "sendVideoNote",
      req.asJson,
      Map("video_note" -> Option(videoNote), "thumb" -> thumb).collect { case (k, Some(v)) => k -> v }
    )
  }

  /** Use this method to change the bot's menu button in a private chat, or the default menu button. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target private chat. If not specified, default bot's menu button will be changed
    * @param menuButton
    *   A JSON-serialized object for the bot's new menu button. Defaults to MenuButtonDefault
    */
  def setChatMenuButton(
    chatId: Option[Int] = Option.empty,
    menuButton: Option[MenuButton] = Option.empty
  ): Method[Boolean] = {
    val req = SetChatMenuButtonReq(chatId, menuButton)
    MethodReq[Boolean]("setChatMenuButton", req.asJson)
  }

  /** Informs a user that some of the Telegram Passport elements they provided contains errors. The user will not be
    * able to re-submit their Passport to you until the errors are fixed (the contents of the field for which you
    * returned the error must change). Returns True on success. Use this if the data submitted by the user doesn't
    * satisfy the standards your service requires for any reason. For example, if a birthday date seems invalid, a
    * submitted document is blurry, a scan shows evidence of tampering, etc. Supply some details in the error message to
    * make sure the user knows how to correct the issues.
    *
    * @param userId
    *   User identifier
    * @param errors
    *   A JSON-serialized array describing the errors
    */
  def setPassportDataErrors(userId: Long, errors: List[PassportElementError] = List.empty): Method[Boolean] = {
    val req = SetPassportDataErrorsReq(userId, errors)
    MethodReq[Boolean]("setPassportDataErrors", req.asJson)
  }

  /** Use this method to delete a chat photo. Photos can't be changed for private chats. The bot must be an
    * administrator in the chat for this to work and must have the appropriate administrator rights. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    */
  def deleteChatPhoto(chatId: ChatId): Method[Boolean] = {
    val req = DeleteChatPhotoReq(chatId)
    MethodReq[Boolean]("deleteChatPhoto", req.asJson)
  }

  /** Use this method to edit a non-primary invite link created by the bot. The bot must be an administrator in the chat
    * for this to work and must have the appropriate administrator rights. Returns the edited invite link as a
    * ChatInviteLink object.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param inviteLink
    *   The invite link to edit
    * @param name
    *   Invite link name; 0-32 characters
    * @param expireDate
    *   Point in time (Unix timestamp) when the link will expire
    * @param memberLimit
    *   The maximum number of users that can be members of the chat simultaneously after joining the chat via this
    *   invite link; 1-99999
    * @param createsJoinRequest
    *   True, if users joining the chat via the link need to be approved by chat administrators. If True, member_limit
    *   can't be specified
    */
  def editChatInviteLink(
    chatId: ChatId,
    inviteLink: String,
    name: Option[String] = Option.empty,
    expireDate: Option[Int] = Option.empty,
    memberLimit: Option[Int] = Option.empty,
    createsJoinRequest: Option[Boolean] = Option.empty
  ): Method[ChatInviteLink] = {
    val req = EditChatInviteLinkReq(chatId, inviteLink, name, expireDate, memberLimit, createsJoinRequest)
    MethodReq[ChatInviteLink]("editChatInviteLink", req.asJson)
  }

  /** Use this method to send invoices. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param title
    *   Product name, 1-32 characters
    * @param description
    *   Product description, 1-255 characters
    * @param payload
    *   Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal
    *   processes.
    * @param providerToken
    *   Payment provider token, obtained via &#064;BotFather
    * @param currency
    *   Three-letter ISO 4217 currency code, see more on currencies
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param prices
    *   Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost,
    *   delivery tax, bonus, etc.)
    * @param maxTipAmount
    *   The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double). For
    *   example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145. See the exp parameter in currencies.json, it
    *   shows the number of digits past the decimal point for each currency (2 for the majority of currencies). Defaults
    *   to 0
    * @param suggestedTipAmounts
    *   A JSON-serialized array of suggested amounts of tips in the smallest units of the currency (integer, not
    *   float/double). At most 4 suggested tip amounts can be specified. The suggested tip amounts must be positive,
    *   passed in a strictly increased order and must not exceed max_tip_amount.
    * @param startParameter
    *   Unique deep-linking parameter. If left empty, forwarded copies of the sent message will have a Pay button,
    *   allowing multiple users to pay directly from the forwarded message, using the same invoice. If non-empty,
    *   forwarded copies of the sent message will have a URL button with a deep link to the bot (instead of a Pay
    *   button), with the value used as the start parameter
    * @param providerData
    *   JSON-serialized data about the invoice, which will be shared with the payment provider. A detailed description
    *   of required fields should be provided by the payment provider.
    * @param photoUrl
    *   URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a service. People
    *   like it better when they see what they are paying for.
    * @param photoSize
    *   Photo size in bytes
    * @param photoWidth
    *   Photo width
    * @param photoHeight
    *   Photo height
    * @param needName
    *   Pass True if you require the user's full name to complete the order
    * @param needPhoneNumber
    *   Pass True if you require the user's phone number to complete the order
    * @param needEmail
    *   Pass True if you require the user's email address to complete the order
    * @param needShippingAddress
    *   Pass True if you require the user's shipping address to complete the order
    * @param sendPhoneNumberToProvider
    *   Pass True if the user's phone number should be sent to provider
    * @param sendEmailToProvider
    *   Pass True if the user's email address should be sent to provider
    * @param isFlexible
    *   Pass True if the final price depends on the shipping method
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   A JSON-serialized object for an inline keyboard. If empty, one 'Pay total price' button will be shown. If not
    *   empty, the first button must be a Pay button.
    */
  def sendInvoice(
    chatId: ChatId,
    title: String,
    description: String,
    payload: String,
    providerToken: String,
    currency: String,
    messageThreadId: Option[Int] = Option.empty,
    prices: List[LabeledPrice] = List.empty,
    maxTipAmount: Option[Int] = Option.empty,
    suggestedTipAmounts: List[Int] = List.empty,
    startParameter: Option[String] = Option.empty,
    providerData: Option[String] = Option.empty,
    photoUrl: Option[String] = Option.empty,
    photoSize: Option[Long] = Option.empty,
    photoWidth: Option[Int] = Option.empty,
    photoHeight: Option[Int] = Option.empty,
    needName: Option[Boolean] = Option.empty,
    needPhoneNumber: Option[Boolean] = Option.empty,
    needEmail: Option[Boolean] = Option.empty,
    needShippingAddress: Option[Boolean] = Option.empty,
    sendPhoneNumberToProvider: Option[Boolean] = Option.empty,
    sendEmailToProvider: Option[Boolean] = Option.empty,
    isFlexible: Option[Boolean] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendInvoiceReq(
      chatId,
      title,
      description,
      payload,
      providerToken,
      currency,
      messageThreadId,
      prices,
      maxTipAmount,
      suggestedTipAmounts,
      startParameter,
      providerData,
      photoUrl,
      photoSize,
      photoWidth,
      photoHeight,
      needName,
      needPhoneNumber,
      needEmail,
      needShippingAddress,
      sendPhoneNumberToProvider,
      sendEmailToProvider,
      isFlexible,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendInvoice", req.asJson)
  }

  /** Use this method to send general files. On success, the sent Message is returned. Bots can currently send files of
    * any type of up to 50 MB in size, this limit may be changed in the future.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param document
    *   File to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an
    *   HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using
    *   multipart/form-data.
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param thumb
    *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
    *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not
    *   exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be
    *   only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
    *   multipart/form-data under <file_attach_name>.
    * @param caption
    *   Document caption (may also be used when resending documents by file_id), 0-1024 characters after entities
    *   parsing
    * @param parseMode
    *   Mode for parsing entities in the document caption. See formatting options for more details.
    * @param captionEntities
    *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
    *   parse_mode
    * @param disableContentTypeDetection
    *   Disables automatic server-side content type detection for files uploaded using multipart/form-data
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendDocument(
    chatId: ChatId,
    document: IFile,
    messageThreadId: Option[Int] = Option.empty,
    thumb: Option[IFile] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    captionEntities: List[MessageEntity] = List.empty,
    disableContentTypeDetection: Option[Boolean] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendDocumentReq(
      chatId,
      document,
      messageThreadId,
      thumb,
      caption,
      parseMode,
      captionEntities,
      disableContentTypeDetection,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message](
      "sendDocument",
      req.asJson,
      Map("document" -> Option(document), "thumb" -> thumb).collect { case (k, Some(v)) => k -> v }
    )
  }

  /** Use this method to delete a message, including service messages, with the following limitations: - A message can
    * only be deleted if it was sent less than 48 hours ago. - Service messages about a supergroup, channel, or forum
    * topic creation can't be deleted. - A dice message in a private chat can only be deleted if it was sent more than
    * 24 hours ago. - Bots can delete outgoing messages in private chats, groups, and supergroups. - Bots can delete
    * incoming messages in private chats. - Bots granted can_post_messages permissions can delete outgoing messages in
    * channels. - If the bot is an administrator of a group, it can delete any message there. - If the bot has
    * can_delete_messages permission in a supergroup or a channel, it can delete any message there. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param messageId
    *   Identifier of the message to delete
    */
  def deleteMessage(chatId: ChatId, messageId: Int): Method[Boolean] = {
    val req = DeleteMessageReq(chatId, messageId)
    MethodReq[Boolean]("deleteMessage", req.asJson)
  }

  /** Use this method to send answers to an inline query. On success, True is returned. No more than 50 results per
    * query are allowed.
    *
    * @param inlineQueryId
    *   Unique identifier for the answered query
    * @param results
    *   A JSON-serialized array of results for the inline query
    * @param cacheTime
    *   The maximum amount of time in seconds that the result of the inline query may be cached on the server. Defaults
    *   to 300.
    * @param isPersonal
    *   Pass True if results may be cached on the server side only for the user that sent the query. By default, results
    *   may be returned to any user who sends the same query
    * @param nextOffset
    *   Pass the offset that a client should send in the next query with the same text to receive more results. Pass an
    *   empty string if there are no more results or if you don't support pagination. Offset length can't exceed 64
    *   bytes.
    * @param switchPmText
    *   If passed, clients will display a button with specified text that switches the user to a private chat with the
    *   bot and sends the bot a start message with the parameter switch_pm_parameter
    * @param switchPmParameter
    *   Deep-linking parameter for the /start message sent to the bot when user presses the switch button. 1-64
    *   characters, only A-Z, a-z, 0-9, _ and - are allowed. Example: An inline bot that sends YouTube videos can ask
    *   the user to connect the bot to their YouTube account to adapt search results accordingly. To do this, it
    *   displays a 'Connect your YouTube account' button above the results, or even before showing any. The user presses
    *   the button, switches to a private chat with the bot and, in doing so, passes a start parameter that instructs
    *   the bot to return an OAuth link. Once done, the bot can offer a switch_inline button so that the user can easily
    *   return to the chat where they wanted to use the bot's inline capabilities.
    */
  def answerInlineQuery(
    inlineQueryId: String,
    results: List[InlineQueryResult] = List.empty,
    cacheTime: Option[Int] = Option.empty,
    isPersonal: Option[Boolean] = Option.empty,
    nextOffset: Option[String] = Option.empty,
    switchPmText: Option[String] = Option.empty,
    switchPmParameter: Option[String] = Option.empty
  ): Method[Boolean] = {
    val req =
      AnswerInlineQueryReq(inlineQueryId, results, cacheTime, isPersonal, nextOffset, switchPmText, switchPmParameter)
    MethodReq[Boolean]("answerInlineQuery", req.asJson)
  }

  /** Use this method to unban a previously banned channel chat in a supergroup or channel. The bot must be an
    * administrator for this to work and must have the appropriate administrator rights. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param senderChatId
    *   Unique identifier of the target sender chat
    */
  def unbanChatSenderChat(chatId: ChatId, senderChatId: Int): Method[Boolean] = {
    val req = UnbanChatSenderChatReq(chatId, senderChatId)
    MethodReq[Boolean]("unbanChatSenderChat", req.asJson)
  }

  /** Use this method to close an open 'General' topic in a forum supergroup chat. The bot must be an administrator in
    * the chat for this to work and must have the can_manage_topics administrator rights. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    */
  def closeGeneralForumTopic(chatId: ChatId): Method[Boolean] = {
    val req = CloseGeneralForumTopicReq(chatId)
    MethodReq[Boolean]("closeGeneralForumTopic", req.asJson)
  }

  /** Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio
    * must be in the .MP3 or .M4A format. On success, the sent Message is returned. Bots can currently send audio files
    * of up to 50 MB in size, this limit may be changed in the future. For sending voice messages, use the sendVoice
    * method instead.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param audio
    *   Audio file to send. Pass a file_id as String to send an audio file that exists on the Telegram servers
    *   (recommended), pass an HTTP URL as a String for Telegram to get an audio file from the Internet, or upload a new
    *   one using multipart/form-data.
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param caption
    *   Audio caption, 0-1024 characters after entities parsing
    * @param parseMode
    *   Mode for parsing entities in the audio caption. See formatting options for more details.
    * @param captionEntities
    *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
    *   parse_mode
    * @param duration
    *   Duration of the audio in seconds
    * @param performer
    *   Performer
    * @param title
    *   Track name
    * @param thumb
    *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
    *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not
    *   exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be
    *   only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
    *   multipart/form-data under <file_attach_name>.
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendAudio(
    chatId: ChatId,
    audio: IFile,
    messageThreadId: Option[Int] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    captionEntities: List[MessageEntity] = List.empty,
    duration: Option[Int] = Option.empty,
    performer: Option[String] = Option.empty,
    title: Option[String] = Option.empty,
    thumb: Option[IFile] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendAudioReq(
      chatId,
      audio,
      messageThreadId,
      caption,
      parseMode,
      captionEntities,
      duration,
      performer,
      title,
      thumb,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message](
      "sendAudio",
      req.asJson,
      Map("audio" -> Option(audio), "thumb" -> thumb).collect { case (k, Some(v)) => k -> v }
    )
  }

  /** Use this method to restrict a user in a supergroup. The bot must be an administrator in the supergroup for this to
    * work and must have the appropriate administrator rights. Pass True for all permissions to lift restrictions from a
    * user. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param userId
    *   Unique identifier of the target user
    * @param permissions
    *   A JSON-serialized object for new user permissions
    * @param untilDate
    *   Date when restrictions will be lifted for the user, unix time. If user is restricted for more than 366 days or
    *   less than 30 seconds from the current time, they are considered to be restricted forever
    */
  def restrictChatMember(
    chatId: ChatId,
    userId: Long,
    permissions: ChatPermissions,
    untilDate: Option[Int] = Option.empty
  ): Method[Boolean] = {
    val req = RestrictChatMemberReq(chatId, userId, permissions, untilDate)
    MethodReq[Boolean]("restrictChatMember", req.asJson)
  }

  /** A simple method for testing your bot's authentication token. Requires no parameters. Returns basic information
    * about the bot in form of a User object.
    */
  def getMe(): Method[User] = {
    val req = GetMeReq
    MethodReq[User]("getMe", req.asJson)
  }

  /** Use this method to forward messages of any kind. Service messages can't be forwarded. On success, the sent Message
    * is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param fromChatId
    *   Unique identifier for the chat where the original message was sent (or channel username in the format
    *   &#064;channelusername)
    * @param messageId
    *   Message identifier in the chat specified in from_chat_id
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the forwarded message from forwarding and saving
    */
  def forwardMessage(
    chatId: ChatId,
    fromChatId: ChatId,
    messageId: Int,
    messageThreadId: Option[Int] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty
  ): Method[Message] = {
    val req = ForwardMessageReq(chatId, fromChatId, messageId, messageThreadId, disableNotification, protectContent)
    MethodReq[Message]("forwardMessage", req.asJson)
  }

  /** Use this method to get information about a member of a chat. The method is guaranteed to work only if the bot is
    * an administrator in the chat. Returns a ChatMember object on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup or channel (in the format
    *   &#064;channelusername)
    * @param userId
    *   Unique identifier of the target user
    */
  def getChatMember(chatId: ChatId, userId: Long): Method[ChatMember] = {
    val req = GetChatMemberReq(chatId, userId)
    MethodReq[ChatMember]("getChatMember", req.asJson)
  }

  /** Use this method to get the current list of the bot's commands for the given scope and user language. Returns an
    * Array of BotCommand objects. If commands aren't set, an empty list is returned.
    *
    * @param scope
    *   A JSON-serialized object, describing scope of users. Defaults to BotCommandScopeDefault.
    * @param languageCode
    *   A two-letter ISO 639-1 language code or an empty string
    */
  def getMyCommands(
    scope: Option[BotCommandScope] = Option.empty,
    languageCode: Option[String] = Option.empty
  ): Method[List[BotCommand]] = {
    val req = GetMyCommandsReq(scope, languageCode)
    MethodReq[List[BotCommand]]("getMyCommands", req.asJson)
  }

  /** Use this method to ban a user in a group, a supergroup or a channel. In the case of supergroups and channels, the
    * user will not be able to return to the chat on their own using invite links, etc., unless unbanned first. The bot
    * must be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns
    * True on success.
    *
    * @param chatId
    *   Unique identifier for the target group or username of the target supergroup or channel (in the format
    *   &#064;channelusername)
    * @param userId
    *   Unique identifier of the target user
    * @param untilDate
    *   Date when the user will be unbanned, unix time. If user is banned for more than 366 days or less than 30 seconds
    *   from the current time they are considered to be banned forever. Applied for supergroups and channels only.
    * @param revokeMessages
    *   Pass True to delete all messages from the chat for the user that is being removed. If False, the user will be
    *   able to see messages in the group that were sent before the user was removed. Always True for supergroups and
    *   channels.
    */
  def banChatMember(
    chatId: ChatId,
    userId: Long,
    untilDate: Option[Int] = Option.empty,
    revokeMessages: Option[Boolean] = Option.empty
  ): Method[Boolean] = {
    val req = BanChatMemberReq(chatId, userId, untilDate, revokeMessages)
    MethodReq[Boolean]("banChatMember", req.asJson)
  }

  /** Use this method to get a list of administrators in a chat, which aren't bots. Returns an Array of ChatMember
    * objects.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup or channel (in the format
    *   &#064;channelusername)
    */
  def getChatAdministrators(chatId: ChatId): Method[List[ChatMember]] = {
    val req = GetChatAdministratorsReq(chatId)
    MethodReq[List[ChatMember]]("getChatAdministrators", req.asJson)
  }

  /** Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message.
    * For this to work, your audio must be in an .OGG file encoded with OPUS (other formats may be sent as Audio or
    * Document). On success, the sent Message is returned. Bots can currently send voice messages of up to 50 MB in
    * size, this limit may be changed in the future.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param voice
    *   Audio file to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended),
    *   pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using
    *   multipart/form-data.
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param caption
    *   Voice message caption, 0-1024 characters after entities parsing
    * @param parseMode
    *   Mode for parsing entities in the voice message caption. See formatting options for more details.
    * @param captionEntities
    *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
    *   parse_mode
    * @param duration
    *   Duration of the voice message in seconds
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendVoice(
    chatId: ChatId,
    voice: IFile,
    messageThreadId: Option[Int] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    captionEntities: List[MessageEntity] = List.empty,
    duration: Option[Int] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendVoiceReq(
      chatId,
      voice,
      messageThreadId,
      caption,
      parseMode,
      captionEntities,
      duration,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendVoice", req.asJson, Map("voice" -> Option(voice)).collect { case (k, Some(v)) => k -> v })
  }

  /** Use this method to log out from the cloud Bot API server before launching the bot locally. You must log out the
    * bot before running it locally, otherwise there is no guarantee that the bot will receive updates. After a
    * successful call, you can immediately log in on a local server, but will not be able to log in back to the cloud
    * Bot API server for 10 minutes. Returns True on success. Requires no parameters.
    */
  def logOut(): Method[Boolean] = {
    val req = LogOutReq
    MethodReq[Boolean]("logOut", req.asJson)
  }

  /** Use this method to promote or demote a user in a supergroup or a channel. The bot must be an administrator in the
    * chat for this to work and must have the appropriate administrator rights. Pass False for all boolean parameters to
    * demote a user. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param userId
    *   Unique identifier of the target user
    * @param isAnonymous
    *   Pass True if the administrator's presence in the chat is hidden
    * @param canManageChat
    *   Pass True if the administrator can access the chat event log, chat statistics, message statistics in channels,
    *   see channel members, see anonymous administrators in supergroups and ignore slow mode. Implied by any other
    *   administrator privilege
    * @param canPostMessages
    *   Pass True if the administrator can create channel posts, channels only
    * @param canEditMessages
    *   Pass True if the administrator can edit messages of other users and can pin messages, channels only
    * @param canDeleteMessages
    *   Pass True if the administrator can delete messages of other users
    * @param canManageVideoChats
    *   Pass True if the administrator can manage video chats
    * @param canRestrictMembers
    *   Pass True if the administrator can restrict, ban or unban chat members
    * @param canPromoteMembers
    *   Pass True if the administrator can add new administrators with a subset of their own privileges or demote
    *   administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by
    *   him)
    * @param canChangeInfo
    *   Pass True if the administrator can change chat title, photo and other settings
    * @param canInviteUsers
    *   Pass True if the administrator can invite new users to the chat
    * @param canPinMessages
    *   Pass True if the administrator can pin messages, supergroups only
    * @param canManageTopics
    *   Pass True if the user is allowed to create, rename, close, and reopen forum topics, supergroups only
    */
  def promoteChatMember(
    chatId: ChatId,
    userId: Long,
    isAnonymous: Option[Boolean] = Option.empty,
    canManageChat: Option[Boolean] = Option.empty,
    canPostMessages: Option[Boolean] = Option.empty,
    canEditMessages: Option[Boolean] = Option.empty,
    canDeleteMessages: Option[Boolean] = Option.empty,
    canManageVideoChats: Option[Boolean] = Option.empty,
    canRestrictMembers: Option[Boolean] = Option.empty,
    canPromoteMembers: Option[Boolean] = Option.empty,
    canChangeInfo: Option[Boolean] = Option.empty,
    canInviteUsers: Option[Boolean] = Option.empty,
    canPinMessages: Option[Boolean] = Option.empty,
    canManageTopics: Option[Boolean] = Option.empty
  ): Method[Boolean] = {
    val req = PromoteChatMemberReq(
      chatId,
      userId,
      isAnonymous,
      canManageChat,
      canPostMessages,
      canEditMessages,
      canDeleteMessages,
      canManageVideoChats,
      canRestrictMembers,
      canPromoteMembers,
      canChangeInfo,
      canInviteUsers,
      canPinMessages,
      canManageTopics
    )
    MethodReq[Boolean]("promoteChatMember", req.asJson)
  }

  /** Use this method to edit captions of messages. On success, if the edited message is not an inline message, the
    * edited Message is returned, otherwise True is returned.
    *
    * @param chatId
    *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
    *   channel (in the format &#064;channelusername)
    * @param messageId
    *   Required if inline_message_id is not specified. Identifier of the message to edit
    * @param inlineMessageId
    *   Required if chat_id and message_id are not specified. Identifier of the inline message
    * @param caption
    *   New caption of the message, 0-1024 characters after entities parsing
    * @param parseMode
    *   Mode for parsing entities in the message caption. See formatting options for more details.
    * @param captionEntities
    *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
    *   parse_mode
    * @param replyMarkup
    *   A JSON-serialized object for an inline keyboard.
    */
  def editMessageCaption(
    chatId: Option[ChatId] = Option.empty,
    messageId: Option[Int] = Option.empty,
    inlineMessageId: Option[String] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    captionEntities: List[MessageEntity] = List.empty,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Either[Boolean, Message]] = {
    val req =
      EditMessageCaptionReq(chatId, messageId, inlineMessageId, caption, parseMode, captionEntities, replyMarkup)
    MethodReq[Either[Boolean, Message]]("editMessageCaption", req.asJson)
  }

  /** Use this method to edit animation, audio, document, photo, or video messages. If a message is part of a message
    * album, then it can be edited only to an audio for audio albums, only to a document for document albums and to a
    * photo or a video otherwise. When an inline message is edited, a new file can't be uploaded; use a previously
    * uploaded file via its file_id or specify a URL. On success, if the edited message is not an inline message, the
    * edited Message is returned, otherwise True is returned.
    *
    * @param media
    *   A JSON-serialized object for a new media content of the message
    * @param chatId
    *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
    *   channel (in the format &#064;channelusername)
    * @param messageId
    *   Required if inline_message_id is not specified. Identifier of the message to edit
    * @param inlineMessageId
    *   Required if chat_id and message_id are not specified. Identifier of the inline message
    * @param replyMarkup
    *   A JSON-serialized object for a new inline keyboard.
    */
  def editMessageMedia(
    media: InputMedia,
    chatId: Option[ChatId] = Option.empty,
    messageId: Option[Int] = Option.empty,
    inlineMessageId: Option[String] = Option.empty,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Either[Boolean, Message]] = {
    val req = EditMessageMediaReq(media, chatId, messageId, inlineMessageId, replyMarkup)
    MethodReq[Either[Boolean, Message]]("editMessageMedia", req.asJson)
  }

  /** Use this method to change the default administrator rights requested by the bot when it's added as an
    * administrator to groups or channels. These rights will be suggested to users, but they are are free to modify the
    * list before adding the bot. Returns True on success.
    *
    * @param rights
    *   A JSON-serialized object describing new default administrator rights. If not specified, the default
    *   administrator rights will be cleared.
    * @param forChannels
    *   Pass True to change the default administrator rights of the bot in channels. Otherwise, the default
    *   administrator rights of the bot for groups and supergroups will be changed.
    */
  def setMyDefaultAdministratorRights(
    rights: Option[ChatAdministratorRights] = Option.empty,
    forChannels: Option[Boolean] = Option.empty
  ): Method[Boolean] = {
    val req = SetMyDefaultAdministratorRightsReq(rights, forChannels)
    MethodReq[Boolean]("setMyDefaultAdministratorRights", req.asJson)
  }

  /** Use this method to delete the list of the bot's commands for the given scope and user language. After deletion,
    * higher level commands will be shown to affected users. Returns True on success.
    *
    * @param scope
    *   A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to
    *   BotCommandScopeDefault.
    * @param languageCode
    *   A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for
    *   whose language there are no dedicated commands
    */
  def deleteMyCommands(
    scope: Option[BotCommandScope] = Option.empty,
    languageCode: Option[String] = Option.empty
  ): Method[Boolean] = {
    val req = DeleteMyCommandsReq(scope, languageCode)
    MethodReq[Boolean]("deleteMyCommands", req.asJson)
  }

  /** Use this method to add a message to the list of pinned messages in a chat. If the chat is not a private chat, the
    * bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' administrator right
    * in a supergroup or 'can_edit_messages' administrator right in a channel. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param messageId
    *   Identifier of a message to pin
    * @param disableNotification
    *   Pass True if it is not necessary to send a notification to all chat members about the new pinned message.
    *   Notifications are always disabled in channels and private chats.
    */
  def pinChatMessage(
    chatId: ChatId,
    messageId: Int,
    disableNotification: Option[Boolean] = Option.empty
  ): Method[Boolean] = {
    val req = PinChatMessageReq(chatId, messageId, disableNotification)
    MethodReq[Boolean]("pinChatMessage", req.asJson)
  }

  /** Use this method to set the thumbnail of a sticker set. Animated thumbnails can be set for animated sticker sets
    * only. Video thumbnails can be set only for video sticker sets only. Returns True on success.
    *
    * @param name
    *   Sticker set name
    * @param userId
    *   User identifier of the sticker set owner
    * @param thumb
    *   A PNG image with the thumbnail, must be up to 128 kilobytes in size and have width and height exactly 100px, or
    *   a TGS animation with the thumbnail up to 32 kilobytes in size; see
    *   https://core.telegram.org/stickers#animated-sticker-requirements for animated sticker technical requirements, or
    *   a WEBM video with the thumbnail up to 32 kilobytes in size; see
    *   https://core.telegram.org/stickers#video-sticker-requirements for video sticker technical requirements. Pass a
    *   file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for
    *   Telegram to get a file from the Internet, or upload a new one using multipart/form-data. Animated sticker set
    *   thumbnails can't be uploaded via HTTP URL.
    */
  def setStickerSetThumb(name: String, userId: Long, thumb: Option[IFile] = Option.empty): Method[Boolean] = {
    val req = SetStickerSetThumbReq(name, userId, thumb)
    MethodReq[Boolean]("setStickerSetThumb", req.asJson, Map("thumb" -> thumb).collect { case (k, Some(v)) => k -> v })
  }

  /** Use this method to edit only the reply markup of messages. On success, if the edited message is not an inline
    * message, the edited Message is returned, otherwise True is returned.
    *
    * @param chatId
    *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
    *   channel (in the format &#064;channelusername)
    * @param messageId
    *   Required if inline_message_id is not specified. Identifier of the message to edit
    * @param inlineMessageId
    *   Required if chat_id and message_id are not specified. Identifier of the inline message
    * @param replyMarkup
    *   A JSON-serialized object for an inline keyboard.
    */
  def editMessageReplyMarkup(
    chatId: Option[ChatId] = Option.empty,
    messageId: Option[Int] = Option.empty,
    inlineMessageId: Option[String] = Option.empty,
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
  ): Method[Either[Boolean, Message]] = {
    val req = EditMessageReplyMarkupReq(chatId, messageId, inlineMessageId, replyMarkup)
    MethodReq[Either[Boolean, Message]]("editMessageReplyMarkup", req.asJson)
  }

  /** Use this method to create an additional invite link for a chat. The bot must be an administrator in the chat for
    * this to work and must have the appropriate administrator rights. The link can be revoked using the method
    * revokeChatInviteLink. Returns the new invite link as ChatInviteLink object.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param name
    *   Invite link name; 0-32 characters
    * @param expireDate
    *   Point in time (Unix timestamp) when the link will expire
    * @param memberLimit
    *   The maximum number of users that can be members of the chat simultaneously after joining the chat via this
    *   invite link; 1-99999
    * @param createsJoinRequest
    *   True, if users joining the chat via the link need to be approved by chat administrators. If True, member_limit
    *   can't be specified
    */
  def createChatInviteLink(
    chatId: ChatId,
    name: Option[String] = Option.empty,
    expireDate: Option[Int] = Option.empty,
    memberLimit: Option[Int] = Option.empty,
    createsJoinRequest: Option[Boolean] = Option.empty
  ): Method[ChatInviteLink] = {
    val req = CreateChatInviteLinkReq(chatId, name, expireDate, memberLimit, createsJoinRequest)
    MethodReq[ChatInviteLink]("createChatInviteLink", req.asJson)
  }

  /** Use this method to send video files, Telegram clients support MPEG4 videos (other formats may be sent as
    * Document). On success, the sent Message is returned. Bots can currently send video files of up to 50 MB in size,
    * this limit may be changed in the future.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param video
    *   Video to send. Pass a file_id as String to send a video that exists on the Telegram servers (recommended), pass
    *   an HTTP URL as a String for Telegram to get a video from the Internet, or upload a new video using
    *   multipart/form-data.
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param duration
    *   Duration of sent video in seconds
    * @param width
    *   Video width
    * @param height
    *   Video height
    * @param thumb
    *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
    *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not
    *   exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be
    *   only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
    *   multipart/form-data under <file_attach_name>.
    * @param caption
    *   Video caption (may also be used when resending videos by file_id), 0-1024 characters after entities parsing
    * @param parseMode
    *   Mode for parsing entities in the video caption. See formatting options for more details.
    * @param captionEntities
    *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
    *   parse_mode
    * @param hasSpoiler
    *   Pass True if the video needs to be covered with a spoiler animation
    * @param supportsStreaming
    *   Pass True if the uploaded video is suitable for streaming
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendVideo(
    chatId: ChatId,
    video: IFile,
    messageThreadId: Option[Int] = Option.empty,
    duration: Option[Int] = Option.empty,
    width: Option[Int] = Option.empty,
    height: Option[Int] = Option.empty,
    thumb: Option[IFile] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    captionEntities: List[MessageEntity] = List.empty,
    hasSpoiler: Option[Boolean] = Option.empty,
    supportsStreaming: Option[Boolean] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendVideoReq(
      chatId,
      video,
      messageThreadId,
      duration,
      width,
      height,
      thumb,
      caption,
      parseMode,
      captionEntities,
      hasSpoiler,
      supportsStreaming,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message](
      "sendVideo",
      req.asJson,
      Map("video" -> Option(video), "thumb" -> thumb).collect { case (k, Some(v)) => k -> v }
    )
  }

  /** Use this method to set a new group sticker set for a supergroup. The bot must be an administrator in the chat for
    * this to work and must have the appropriate administrator rights. Use the field can_set_sticker_set optionally
    * returned in getChat requests to check if the bot can use this method. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param stickerSetName
    *   Name of the sticker set to be set as the group sticker set
    */
  def setChatStickerSet(chatId: ChatId, stickerSetName: String): Method[Boolean] = {
    val req = SetChatStickerSetReq(chatId, stickerSetName)
    MethodReq[Boolean]("setChatStickerSet", req.asJson)
  }

  /** Use this method to get up to date information about the chat (current name of the user for one-on-one
    * conversations, current username of a user, group or channel, etc.). Returns a Chat object on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup or channel (in the format
    *   &#064;channelusername)
    */
  def getChat(chatId: ChatId): Method[Chat] = {
    val req = GetChatReq(chatId)
    MethodReq[Chat]("getChat", req.asJson)
  }

  /** Use this method to remove webhook integration if you decide to switch back to getUpdates. Returns True on success.
    *
    * @param dropPendingUpdates
    *   Pass True to drop all pending updates
    */
  def deleteWebhook(dropPendingUpdates: Option[Boolean] = Option.empty): Method[Boolean] = {
    val req = DeleteWebhookReq(dropPendingUpdates)
    MethodReq[Boolean]("deleteWebhook", req.asJson)
  }

  /** Use this method to revoke an invite link created by the bot. If the primary link is revoked, a new link is
    * automatically generated. The bot must be an administrator in the chat for this to work and must have the
    * appropriate administrator rights. Returns the revoked invite link as ChatInviteLink object.
    *
    * @param chatId
    *   Unique identifier of the target chat or username of the target channel (in the format &#064;channelusername)
    * @param inviteLink
    *   The invite link to revoke
    */
  def revokeChatInviteLink(chatId: ChatId, inviteLink: String): Method[ChatInviteLink] = {
    val req = RevokeChatInviteLinkReq(chatId, inviteLink)
    MethodReq[ChatInviteLink]("revokeChatInviteLink", req.asJson)
  }

  /** Use this method to close the bot instance before moving it from one local server to another. You need to delete
    * the webhook before calling this method to ensure that the bot isn't launched again after server restart. The
    * method will return error 429 in the first 10 minutes after the bot is launched. Returns True on success. Requires
    * no parameters.
    */
  def close(): Method[Boolean] = {
    val req = CloseReq
    MethodReq[Boolean]("close", req.asJson)
  }

  /** Use this method to move a sticker in a set created by the bot to a specific position. Returns True on success.
    *
    * @param sticker
    *   File identifier of the sticker
    * @param position
    *   New sticker position in the set, zero-based
    */
  def setStickerPositionInSet(sticker: String, position: Int): Method[Boolean] = {
    val req = SetStickerPositionInSetReq(sticker, position)
    MethodReq[Boolean]("setStickerPositionInSet", req.asJson)
  }

  /** Use this method to set a custom title for an administrator in a supergroup promoted by the bot. Returns True on
    * success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param userId
    *   Unique identifier of the target user
    * @param customTitle
    *   New custom title for the administrator; 0-16 characters, emoji are not allowed
    */
  def setChatAdministratorCustomTitle(chatId: ChatId, userId: Long, customTitle: String): Method[Boolean] = {
    val req = SetChatAdministratorCustomTitleReq(chatId, userId, customTitle)
    MethodReq[Boolean]("setChatAdministratorCustomTitle", req.asJson)
  }

  /** Use this method to hide the 'General' topic in a forum supergroup chat. The bot must be an administrator in the
    * chat for this to work and must have the can_manage_topics administrator rights. The topic will be automatically
    * closed if it was open. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    */
  def hideGeneralForumTopic(chatId: ChatId): Method[Boolean] = {
    val req = HideGeneralForumTopicReq(chatId)
    MethodReq[Boolean]("hideGeneralForumTopic", req.asJson)
  }

  /** Use this method to close an open topic in a forum supergroup chat. The bot must be an administrator in the chat
    * for this to work and must have the can_manage_topics administrator rights, unless it is the creator of the topic.
    * Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param messageThreadId
    *   Unique identifier for the target message thread of the forum topic
    */
  def closeForumTopic(chatId: ChatId, messageThreadId: Int): Method[Boolean] = {
    val req = CloseForumTopicReq(chatId, messageThreadId)
    MethodReq[Boolean]("closeForumTopic", req.asJson)
  }

  /** Use this method to get the current value of the bot's menu button in a private chat, or the default menu button.
    * Returns MenuButton on success.
    *
    * @param chatId
    *   Unique identifier for the target private chat. If not specified, default bot's menu button will be returned
    */
  def getChatMenuButton(chatId: Option[Int] = Option.empty): Method[MenuButton] = {
    val req = GetChatMenuButtonReq(chatId)
    MethodReq[MenuButton]("getChatMenuButton", req.asJson)
  }

  /** Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound). On success, the sent
    * Message is returned. Bots can currently send animation files of up to 50 MB in size, this limit may be changed in
    * the future.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param animation
    *   Animation to send. Pass a file_id as String to send an animation that exists on the Telegram servers
    *   (recommended), pass an HTTP URL as a String for Telegram to get an animation from the Internet, or upload a new
    *   animation using multipart/form-data.
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param duration
    *   Duration of sent animation in seconds
    * @param width
    *   Animation width
    * @param height
    *   Animation height
    * @param thumb
    *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
    *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not
    *   exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be
    *   only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
    *   multipart/form-data under <file_attach_name>.
    * @param caption
    *   Animation caption (may also be used when resending animation by file_id), 0-1024 characters after entities
    *   parsing
    * @param parseMode
    *   Mode for parsing entities in the animation caption. See formatting options for more details.
    * @param captionEntities
    *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
    *   parse_mode
    * @param hasSpoiler
    *   Pass True if the animation needs to be covered with a spoiler animation
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendAnimation(
    chatId: ChatId,
    animation: IFile,
    messageThreadId: Option[Int] = Option.empty,
    duration: Option[Int] = Option.empty,
    width: Option[Int] = Option.empty,
    height: Option[Int] = Option.empty,
    thumb: Option[IFile] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    captionEntities: List[MessageEntity] = List.empty,
    hasSpoiler: Option[Boolean] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendAnimationReq(
      chatId,
      animation,
      messageThreadId,
      duration,
      width,
      height,
      thumb,
      caption,
      parseMode,
      captionEntities,
      hasSpoiler,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message](
      "sendAnimation",
      req.asJson,
      Map("animation" -> Option(animation), "thumb" -> thumb).collect { case (k, Some(v)) => k -> v }
    )
  }

  /** If you sent an invoice requesting a shipping address and the parameter is_flexible was specified, the Bot API will
    * send an Update with a shipping_query field to the bot. Use this method to reply to shipping queries. On success,
    * True is returned.
    *
    * @param shippingQueryId
    *   Unique identifier for the query to be answered
    * @param ok
    *   Pass True if delivery to the specified address is possible and False if there are any problems (for example, if
    *   delivery to the specified address is not possible)
    * @param shippingOptions
    *   Required if ok is True. A JSON-serialized array of available shipping options.
    * @param errorMessage
    *   Required if ok is False. Error message in human readable form that explains why it is impossible to complete the
    *   order (e.g. "Sorry, delivery to your desired address is unavailable'). Telegram will display this message to the
    *   user.
    */
  def answerShippingQuery(
    shippingQueryId: String,
    ok: Boolean,
    shippingOptions: List[ShippingOption] = List.empty,
    errorMessage: Option[String] = Option.empty
  ): Method[Boolean] = {
    val req = AnswerShippingQueryReq(shippingQueryId, ok, shippingOptions, errorMessage)
    MethodReq[Boolean]("answerShippingQuery", req.asJson)
  }

  /** Use this method to approve a chat join request. The bot must be an administrator in the chat for this to work and
    * must have the can_invite_users administrator right. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param userId
    *   Unique identifier of the target user
    */
  def approveChatJoinRequest(chatId: ChatId, userId: Long): Method[Boolean] = {
    val req = ApproveChatJoinRequestReq(chatId, userId)
    MethodReq[Boolean]("approveChatJoinRequest", req.asJson)
  }

  /** Once the user has confirmed their payment and shipping details, the Bot API sends the final confirmation in the
    * form of an Update with the field pre_checkout_query. Use this method to respond to such pre-checkout queries. On
    * success, True is returned. Note: The Bot API must receive an answer within 10 seconds after the pre-checkout query
    * was sent.
    *
    * @param preCheckoutQueryId
    *   Unique identifier for the query to be answered
    * @param ok
    *   Specify True if everything is alright (goods are available, etc.) and the bot is ready to proceed with the
    *   order. Use False if there are any problems.
    * @param errorMessage
    *   Required if ok is False. Error message in human readable form that explains the reason for failure to proceed
    *   with the checkout (e.g. "Sorry, somebody just bought the last of our amazing black T-shirts while you were busy
    *   filling out your payment details. Please choose a different color or garment!"). Telegram will display this
    *   message to the user.
    */
  def answerPreCheckoutQuery(
    preCheckoutQueryId: String,
    ok: Boolean,
    errorMessage: Option[String] = Option.empty
  ): Method[Boolean] = {
    val req = AnswerPreCheckoutQueryReq(preCheckoutQueryId, ok, errorMessage)
    MethodReq[Boolean]("answerPreCheckoutQuery", req.asJson)
  }

  /** Use this method to send static .WEBP, animated .TGS, or video .WEBM stickers. On success, the sent Message is
    * returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param sticker
    *   Sticker to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass
    *   an HTTP URL as a String for Telegram to get a .WEBP file from the Internet, or upload a new one using
    *   multipart/form-data.
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendSticker(
    chatId: ChatId,
    sticker: IFile,
    messageThreadId: Option[Int] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendStickerReq(
      chatId,
      sticker,
      messageThreadId,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message](
      "sendSticker",
      req.asJson,
      Map("sticker" -> Option(sticker)).collect { case (k, Some(v)) => k -> v }
    )
  }

  /** Use this method to edit name and icon of a topic in a forum supergroup chat. The bot must be an administrator in
    * the chat for this to work and must have can_manage_topics administrator rights, unless it is the creator of the
    * topic. Returns True on success.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target supergroup (in the format
    *   &#064;supergroupusername)
    * @param messageThreadId
    *   Unique identifier for the target message thread of the forum topic
    * @param name
    *   New topic name, 0-128 characters. If not specified or empty, the current name of the topic will be kept
    * @param iconCustomEmojiId
    *   New unique identifier of the custom emoji shown as the topic icon. Use getForumTopicIconStickers to get all
    *   allowed custom emoji identifiers. Pass an empty string to remove the icon. If not specified, the current icon
    *   will be kept
    */
  def editForumTopic(
    chatId: ChatId,
    messageThreadId: Int,
    name: Option[String] = Option.empty,
    iconCustomEmojiId: Option[String] = Option.empty
  ): Method[Boolean] = {
    val req = EditForumTopicReq(chatId, messageThreadId, name, iconCustomEmojiId)
    MethodReq[Boolean]("editForumTopic", req.asJson)
  }

  /** Use this method to send photos. On success, the sent Message is returned.
    *
    * @param chatId
    *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
    * @param photo
    *   Photo to send. Pass a file_id as String to send a photo that exists on the Telegram servers (recommended), pass
    *   an HTTP URL as a String for Telegram to get a photo from the Internet, or upload a new photo using
    *   multipart/form-data. The photo must be at most 10 MB in size. The photo's width and height must not exceed 10000
    *   in total. Width and height ratio must be at most 20.
    * @param messageThreadId
    *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
    * @param caption
    *   Photo caption (may also be used when resending photos by file_id), 0-1024 characters after entities parsing
    * @param parseMode
    *   Mode for parsing entities in the photo caption. See formatting options for more details.
    * @param captionEntities
    *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
    *   parse_mode
    * @param hasSpoiler
    *   Pass True if the photo needs to be covered with a spoiler animation
    * @param disableNotification
    *   Sends the message silently. Users will receive a notification with no sound.
    * @param protectContent
    *   Protects the contents of the sent message from forwarding and saving
    * @param replyToMessageId
    *   If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply
    *   Pass True if the message should be sent even if the specified replied-to message is not found
    * @param replyMarkup
    *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
    *   instructions to remove reply keyboard or to force a reply from the user.
    */
  def sendPhoto(
    chatId: ChatId,
    photo: IFile,
    messageThreadId: Option[Int] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    captionEntities: List[MessageEntity] = List.empty,
    hasSpoiler: Option[Boolean] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    protectContent: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    allowSendingWithoutReply: Option[Boolean] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendPhotoReq(
      chatId,
      photo,
      messageThreadId,
      caption,
      parseMode,
      captionEntities,
      hasSpoiler,
      disableNotification,
      protectContent,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendPhoto", req.asJson, Map("photo" -> Option(photo)).collect { case (k, Some(v)) => k -> v })
  }

  /** Use this method to receive incoming updates using long polling (wiki). Returns an Array of Update objects.
    *
    * @param offset
    *   Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of
    *   previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An
    *   update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id. The
    *   negative offset can be specified to retrieve updates starting from -offset update from the end of the updates
    *   queue. All previous updates will forgotten.
    * @param limit
    *   Limits the number of updates to be retrieved. Values between 1-100 are accepted. Defaults to 100.
    * @param timeout
    *   Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling. Should be positive, short polling
    *   should be used for testing purposes only.
    * @param allowedUpdates
    *   A JSON-serialized list of the update types you want your bot to receive. For example, specify [“message”,
    *   “edited_channel_post”, “callback_query”] to only receive updates of these types. See Update for a complete list
    *   of available update types. Specify an empty list to receive all update types except chat_member (default). If
    *   not specified, the previous setting will be used. Please note that this parameter doesn't affect updates created
    *   before the call to the getUpdates, so unwanted updates may be received for a short period of time.
    */
  def getUpdates(
    offset: Option[Int] = Option.empty,
    limit: Option[Int] = Option.empty,
    timeout: Option[Int] = Option.empty,
    allowedUpdates: List[String] = List.empty
  ): Method[List[Update]] = {
    val req = GetUpdatesReq(offset, limit, timeout, allowedUpdates)
    MethodReq[List[Update]]("getUpdates", req.asJson)
  }

  /** Use this method to get a sticker set. On success, a StickerSet object is returned.
    *
    * @param name
    *   Name of the sticker set
    */
  def getStickerSet(name: String): Method[StickerSet] = {
    val req = GetStickerSetReq(name)
    MethodReq[StickerSet]("getStickerSet", req.asJson)
  }

  /** Use this method to specify a URL and receive incoming updates via an outgoing webhook. Whenever there is an update
    * for the bot, we will send an HTTPS POST request to the specified URL, containing a JSON-serialized Update. In case
    * of an unsuccessful request, we will give up after a reasonable amount of attempts. Returns True on success. If
    * you'd like to make sure that the webhook was set by you, you can specify secret data in the parameter
    * secret_token. If specified, the request will contain a header “X-Telegram-Bot-Api-Secret-Token” with the secret
    * token as content.
    *
    * @param url
    *   HTTPS URL to send updates to. Use an empty string to remove webhook integration
    * @param certificate
    *   Upload your public key certificate so that the root certificate in use can be checked. See our self-signed guide
    *   for details.
    * @param ipAddress
    *   The fixed IP address which will be used to send webhook requests instead of the IP address resolved through DNS
    * @param maxConnections
    *   The maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100. Defaults
    *   to 40. Use lower values to limit the load on your bot's server, and higher values to increase your bot's
    *   throughput.
    * @param allowedUpdates
    *   A JSON-serialized list of the update types you want your bot to receive. For example, specify [“message”,
    *   “edited_channel_post”, “callback_query”] to only receive updates of these types. See Update for a complete list
    *   of available update types. Specify an empty list to receive all update types except chat_member (default). If
    *   not specified, the previous setting will be used. Please note that this parameter doesn't affect updates created
    *   before the call to the setWebhook, so unwanted updates may be received for a short period of time.
    * @param dropPendingUpdates
    *   Pass True to drop all pending updates
    * @param secretToken
    *   A secret token to be sent in a header “X-Telegram-Bot-Api-Secret-Token” in every webhook request, 1-256
    *   characters. Only characters A-Z, a-z, 0-9, _ and - are allowed. The header is useful to ensure that the request
    *   comes from a webhook set by you.
    */
  def setWebhook(
    url: String,
    certificate: Option[IFile] = Option.empty,
    ipAddress: Option[String] = Option.empty,
    maxConnections: Option[Int] = Option.empty,
    allowedUpdates: List[String] = List.empty,
    dropPendingUpdates: Option[Boolean] = Option.empty,
    secretToken: Option[String] = Option.empty
  ): Method[Boolean] = {
    val req =
      SetWebhookReq(url, certificate, ipAddress, maxConnections, allowedUpdates, dropPendingUpdates, secretToken)
    MethodReq[Boolean](
      "setWebhook",
      req.asJson,
      Map("certificate" -> certificate).collect { case (k, Some(v)) => k -> v }
    )
  }

}
