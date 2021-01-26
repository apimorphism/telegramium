package telegramium.bots.client

import io.circe.syntax._
import CirceImplicits._
import telegramium.bots.WebhookInfo
import telegramium.bots._
import telegramium.bots.CirceImplicits._
import telegramium.bots.GameHighScore
import telegramium.bots.Message
import telegramium.bots.UserProfilePhotos
import telegramium.bots.File
import telegramium.bots.Poll
import telegramium.bots.MessageId
import telegramium.bots.User
import telegramium.bots.ChatMember
import telegramium.bots.BotCommand
import telegramium.bots.Audio
import telegramium.bots.Document
import telegramium.bots.Chat
import telegramium.bots.Update
import telegramium.bots.StickerSet

trait Methods {

  import io.circe.Decoder
  private implicit def decodeEither[A, B](
      implicit decoderA: Decoder[A],
      decoderB: Decoder[B]
  ): Decoder[Either[A, B]] = decoderA.either(decoderB)

  /** Use this method to get current webhook status. Requires no parameters. On
    * success, returns a WebhookInfo object. If the bot is using getUpdates, will
    * return an object with the url field empty.*/
  def getWebhookInfo(): Method[WebhookInfo] = {
    val req = GetWebhookInfoReq
    MethodReq[WebhookInfo]("getWebhookInfo", req.asJson)
  }

  /** Use this method to change the list of the bot's commands. Returns True on
    * success.

    * @param commands A JSON-serialized list of bot commands to be set as the
    * list of the bot's commands. At most 100 commands can be
    * specified.*/
  def setMyCommands(commands: List[BotCommand] = List.empty): Method[Boolean] = {
    val req = SetMyCommandsReq(commands)
    MethodReq[Boolean]("setMyCommands", req.asJson)
  }

  /** Use this method to set a new profile photo for the chat. Photos can't be
    * changed for private chats. The bot must be an administrator in the chat for this
    * to work and must have the appropriate admin rights. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param photo New chat photo, uploaded using multipart/form-data*/
  def setChatPhoto(chatId: ChatId, photo: IFile): Method[Boolean] = {
    val req = SetChatPhotoReq(chatId, photo)
    MethodReq[Boolean]("setChatPhoto", req.asJson, Map("photo" -> Option(photo)).collect {
      case (k, Some(v)) => k -> v
    })
  }

  /** Use this method to get data for high score tables. Will return the score of the
    * specified user and several of their neighbors in a game. On success, returns an
    * Array of GameHighScore objects.

    * @param userId Target user id
    * @param chatId Required if inline_message_id is not specified. Unique
    * identifier for the target chat
    * @param messageId Required if inline_message_id is not specified. Identifier
    * of the sent message
    * @param inlineMessageId Required if chat_id and message_id are not specified.
    * Identifier of the inline message*/
  def getGameHighScores(
      userId: Int,
      chatId: Option[Int] = Option.empty,
      messageId: Option[Int] = Option.empty,
      inlineMessageId: Option[String] = Option.empty): Method[List[GameHighScore]] = {
    val req = GetGameHighScoresReq(userId, chatId, messageId, inlineMessageId)
    MethodReq[List[GameHighScore]]("getGameHighScores", req.asJson)
  }

  /** Use this method to clear the list of pinned messages in a chat. If the chat is
    * not a private chat, the bot must be an administrator in the chat for this to
    * work and must have the 'can_pin_messages' admin right in a supergroup or
    * 'can_edit_messages' admin right in a channel. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)*/
  def unpinAllChatMessages(chatId: ChatId): Method[Boolean] = {
    val req = UnpinAllChatMessagesReq(chatId)
    MethodReq[Boolean]("unpinAllChatMessages", req.asJson)
  }

  /** Use this method to send answers to callback queries sent from inline keyboards.
    * The answer will be displayed to the user as a notification at the top of the
    * chat screen or as an alert. On success, True is returned.

    * @param callbackQueryId Unique identifier for the query to be answered
    * @param text Text of the notification. If not specified, nothing will be
    * shown to the user, 0-200 characters
    * @param showAlert If true, an alert will be shown by the client instead of a
    * notification at the top of the chat screen. Defaults to
    * false.
    * @param url URL that will be opened by the user's client. If you have
    * created a Game and accepted the conditions via
    * &#064;Botfather, specify the URL that opens your game — note
    * that this will only work if the query comes from a
    * callback_game button. Otherwise, you may use links like
    * t.me/your_bot?start=XXXX that open your bot with a
    * parameter.
    * @param cacheTime The maximum amount of time in seconds that the result of
    * the callback query may be cached client-side. Telegram apps
    * will support caching starting in version 3.14. Defaults to
    * 0.*/
  def answerCallbackQuery(callbackQueryId: String,
                          text: Option[String] = Option.empty,
                          showAlert: Option[Boolean] = Option.empty,
                          url: Option[String] = Option.empty,
                          cacheTime: Option[Int] = Option.empty): Method[Boolean] = {
    val req = AnswerCallbackQueryReq(callbackQueryId, text, showAlert, url, cacheTime)
    MethodReq[Boolean]("answerCallbackQuery", req.asJson)
  }

  /** Use this method to send text messages. On success, the sent Message is
    * returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param text Text of the message to be sent, 1-4096 characters after
    * entities parsing
    * @param parseMode Mode for parsing entities in the message text. See
    * formatting options for more details.
    * @param entities List of special entities that appear in message text, which
    * can be specified instead of parse_mode
    * @param disableWebPagePreview Disables link previews for links in this message
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendMessage(chatId: ChatId,
                  text: String,
                  parseMode: Option[ParseMode] = Option.empty,
                  entities: List[MessageEntity] = List.empty,
                  disableWebPagePreview: Option[Boolean] = Option.empty,
                  disableNotification: Option[Boolean] = Option.empty,
                  replyToMessageId: Option[Int] = Option.empty,
                  allowSendingWithoutReply: Option[Boolean] = Option.empty,
                  replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendMessageReq(chatId,
                             text,
                             parseMode,
                             entities,
                             disableWebPagePreview,
                             disableNotification,
                             replyToMessageId,
                             allowSendingWithoutReply,
                             replyMarkup)
    MethodReq[Message]("sendMessage", req.asJson)
  }

  /** Use this method to get a list of profile pictures for a user. Returns a
    * UserProfilePhotos object.

    * @param userId Unique identifier of the target user
    * @param offset Sequential number of the first photo to be returned. By
    * default, all photos are returned.
    * @param limit Limits the number of photos to be retrieved. Values between
    * 1-100 are accepted. Defaults to 100.*/
  def getUserProfilePhotos(userId: Int,
                           offset: Option[Int] = Option.empty,
                           limit: Option[Int] = Option.empty): Method[UserProfilePhotos] = {
    val req = GetUserProfilePhotosReq(userId, offset, limit)
    MethodReq[UserProfilePhotos]("getUserProfilePhotos", req.asJson)
  }

  /** Use this method to send a native poll. On success, the sent Message is
    * returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param question Poll question, 1-300 characters
    * @param options A JSON-serialized list of answer options, 2-10 strings
    * 1-100 characters each
    * @param isAnonymous True, if the poll needs to be anonymous, defaults to True
    * @param type Poll type, “quiz” or “regular”, defaults to “regular”
    * @param allowsMultipleAnswers True, if the poll allows multiple answers, ignored for
    * polls in quiz mode, defaults to False
    * @param correctOptionId 0-based identifier of the correct answer option, required
    * for polls in quiz mode
    * @param explanation Text that is shown when a user chooses an incorrect answer
    * or taps on the lamp icon in a quiz-style poll, 0-200
    * characters with at most 2 line feeds after entities parsing
    * @param explanationParseMode Mode for parsing entities in the explanation. See
    * formatting options for more details.
    * @param explanationEntities List of special entities that appear in the poll
    * explanation, which can be specified instead of parse_mode
    * @param openPeriod Amount of time in seconds the poll will be active after
    * creation, 5-600. Can't be used together with close_date.
    * @param closeDate Point in time (Unix timestamp) when the poll will be
    * automatically closed. Must be at least 5 and no more than
    * 600 seconds in the future. Can't be used together with
    * open_period.
    * @param isClosed Pass True, if the poll needs to be immediately closed. This
    * can be useful for poll preview.
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendPoll(chatId: ChatId,
               question: String,
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
               replyToMessageId: Option[Int] = Option.empty,
               allowSendingWithoutReply: Option[Boolean] = Option.empty,
               replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendPollReq(
      chatId,
      question,
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
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendPoll", req.asJson)
  }

  /** Use this method to send phone contacts. On success, the sent Message is
    * returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param phoneNumber Contact's phone number
    * @param firstName Contact's first name
    * @param lastName Contact's last name
    * @param vcard Additional data about the contact in the form of a vCard,
    * 0-2048 bytes
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove keyboard or to force a reply from the user.*/
  def sendContact(chatId: ChatId,
                  phoneNumber: String,
                  firstName: String,
                  lastName: Option[String] = Option.empty,
                  vcard: Option[String] = Option.empty,
                  disableNotification: Option[Boolean] = Option.empty,
                  replyToMessageId: Option[Int] = Option.empty,
                  allowSendingWithoutReply: Option[Boolean] = Option.empty,
                  replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendContactReq(chatId,
                             phoneNumber,
                             firstName,
                             lastName,
                             vcard,
                             disableNotification,
                             replyToMessageId,
                             allowSendingWithoutReply,
                             replyMarkup)
    MethodReq[Message]("sendContact", req.asJson)
  }

  /** Use this method to create a new sticker set owned by a user. The bot will be
    * able to edit the sticker set thus created. You must use exactly one of the
    * fields png_sticker or tgs_sticker. Returns True on success.

    * @param userId User identifier of created sticker set owner
    * @param name Short name of sticker set, to be used in t.me/addstickers/
    * URLs (e.g., animals). Can contain only english letters,
    * digits and underscores. Must begin with a letter, can't
    * contain consecutive underscores and must end in “_by_<bot
    * username>”. <bot_username> is case insensitive. 1-64
    * characters.
    * @param title Sticker set title, 1-64 characters
    * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in
    * size, dimensions must not exceed 512px, and either width or
    * height must be exactly 512px. Pass a file_id as a String to
    * send a file that already exists on the Telegram servers,
    * pass an HTTP URL as a String for Telegram to get a file from
    * the Internet, or upload a new one using multipart/form-data.
    * @param tgsSticker TGS animation with the sticker, uploaded using
    * multipart/form-data. See
    * https://core.telegram.org/animated_stickers#technical-requirements
    * for technical requirements
    * @param emojis One or more emoji corresponding to the sticker
    * @param containsMasks Pass True, if a set of mask stickers should be created
    * @param maskPosition A JSON-serialized object for position where the mask should
    * be placed on faces*/
  def createNewStickerSet(userId: Int,
                          name: String,
                          title: String,
                          pngSticker: Option[IFile] = Option.empty,
                          tgsSticker: Option[IFile] = Option.empty,
                          emojis: String,
                          containsMasks: Option[Boolean] = Option.empty,
                          maskPosition: Option[MaskPosition] = Option.empty): Method[Boolean] = {
    val req = CreateNewStickerSetReq(userId,
                                     name,
                                     title,
                                     pngSticker,
                                     tgsSticker,
                                     emojis,
                                     containsMasks,
                                     maskPosition)
    MethodReq[Boolean]("createNewStickerSet",
                       req.asJson,
                       Map("png_sticker" -> pngSticker, "tgs_sticker" -> tgsSticker).collect {
                         case (k, Some(v)) => k -> v
                       })
  }

  /** Use this method to upload a .PNG file with a sticker for later use in
    * createNewStickerSet and addStickerToSet methods (can be used multiple times).
    * Returns the uploaded File on success.

    * @param userId User identifier of sticker file owner
    * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in
    * size, dimensions must not exceed 512px, and either width or
    * height must be exactly 512px.*/
  def uploadStickerFile(userId: Int, pngSticker: IFile): Method[File] = {
    val req = UploadStickerFileReq(userId, pngSticker)
    MethodReq[File](
      "uploadStickerFile",
      req.asJson,
      Map("png_sticker" -> Option(pngSticker)).collect { case (k, Some(v)) => k -> v })
  }

  /** Use this method to set default chat permissions for all members. The bot must
    * be an administrator in the group or a supergroup for this to work and must have
    * the can_restrict_members admin rights. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup (in the format &#064;supergroupusername)
    * @param permissions New default chat permissions*/
  def setChatPermissions(chatId: ChatId, permissions: ChatPermissions): Method[Boolean] = {
    val req = SetChatPermissionsReq(chatId, permissions)
    MethodReq[Boolean]("setChatPermissions", req.asJson)
  }

  /** Use this method to send point on the map. On success, the sent Message is
    * returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param latitude Latitude of the location
    * @param longitude Longitude of the location
    * @param horizontalAccuracy The radius of uncertainty for the location, measured in
    * meters; 0-1500
    * @param livePeriod Period in seconds for which the location will be updated
    * (see Live Locations, should be between 60 and 86400.
    * @param heading For live locations, a direction in which the user is
    * moving, in degrees. Must be between 1 and 360 if specified.
    * @param proximityAlertRadius For live locations, a maximum distance for proximity alerts
    * about approaching another chat member, in meters. Must be
    * between 1 and 100000 if specified.
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendLocation(chatId: ChatId,
                   latitude: Float,
                   longitude: Float,
                   horizontalAccuracy: Option[Float] = Option.empty,
                   livePeriod: Option[Int] = Option.empty,
                   heading: Option[Int] = Option.empty,
                   proximityAlertRadius: Option[Int] = Option.empty,
                   disableNotification: Option[Boolean] = Option.empty,
                   replyToMessageId: Option[Int] = Option.empty,
                   allowSendingWithoutReply: Option[Boolean] = Option.empty,
                   replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendLocationReq(
      chatId,
      latitude,
      longitude,
      horizontalAccuracy,
      livePeriod,
      heading,
      proximityAlertRadius,
      disableNotification,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendLocation", req.asJson)
  }

  /** Use this method to delete a group sticker set from a supergroup. The bot must
    * be an administrator in the chat for this to work and must have the appropriate
    * admin rights. Use the field can_set_sticker_set optionally returned in getChat
    * requests to check if the bot can use this method. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup (in the format &#064;supergroupusername)*/
  def deleteChatStickerSet(chatId: ChatId): Method[Boolean] = {
    val req = DeleteChatStickerSetReq(chatId)
    MethodReq[Boolean]("deleteChatStickerSet", req.asJson)
  }

  /** Use this method to stop updating a live location message before live_period
    * expires. On success, if the message was sent by the bot, the sent Message is
    * returned, otherwise True is returned.

    * @param chatId Required if inline_message_id is not specified. Unique
    * identifier for the target chat or username of the target
    * channel (in the format &#064;channelusername)
    * @param messageId Required if inline_message_id is not specified. Identifier
    * of the message with live location to stop
    * @param inlineMessageId Required if chat_id and message_id are not specified.
    * Identifier of the inline message
    * @param replyMarkup A JSON-serialized object for a new inline keyboard.*/
  def stopMessageLiveLocation(chatId: Option[ChatId] = Option.empty,
                              messageId: Option[Int] = Option.empty,
                              inlineMessageId: Option[String] = Option.empty,
                              replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
    : Method[Either[Boolean, Message]] = {
    val req = StopMessageLiveLocationReq(chatId, messageId, inlineMessageId, replyMarkup)
    MethodReq[Either[Boolean, Message]]("stopMessageLiveLocation", req.asJson)
  }

  /** Use this method to generate a new invite link for a chat; any previously
    * generated link is revoked. The bot must be an administrator in the chat for this
    * to work and must have the appropriate admin rights. Returns the new invite link
    * as String on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)*/
  def exportChatInviteLink(chatId: ChatId): Method[String] = {
    val req = ExportChatInviteLinkReq(chatId)
    MethodReq[String]("exportChatInviteLink", req.asJson)
  }

  /** Use this method to send an animated emoji that will display a random value. On
    * success, the sent Message is returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param emoji Emoji on which the dice throw animation is based.
    * Currently, must be one of EmojiDice, EmojiDarts,
    * EmojiBasketball, EmojiFootball, or EmojiSlotMachine. Dice
    * can have values 1-6 for EmojiDice and EmojiDarts, values 1-5
    * for EmojiBasketball and EmojiFootball, and values 1-64 for
    * EmojiSlotMachine. Defaults to EmojiDice
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendDice(chatId: ChatId,
               emoji: Option[Emoji] = Option.empty,
               disableNotification: Option[Boolean] = Option.empty,
               replyToMessageId: Option[Int] = Option.empty,
               allowSendingWithoutReply: Option[Boolean] = Option.empty,
               replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendDiceReq(chatId,
                          emoji,
                          disableNotification,
                          replyToMessageId,
                          allowSendingWithoutReply,
                          replyMarkup)
    MethodReq[Message]("sendDice", req.asJson)
  }

  /** Use this method when you need to tell the user that something is happening on
    * the bot's side. The status is set for 5 seconds or less (when a message arrives
    * from your bot, Telegram clients clear its typing status). Returns True on
    * success.*/
  def sendChatAction(): Method[Boolean] = {
    val req = SendChatActionReq
    MethodReq[Boolean]("sendChatAction", req.asJson)
  }

  /** Use this method to add a new sticker to a set created by the bot. You must use
    * exactly one of the fields png_sticker or tgs_sticker. Animated stickers can be
    * added to animated sticker sets and only to them. Animated sticker sets can have
    * up to 50 stickers. Static sticker sets can have up to 120 stickers. Returns True
    * on success.

    * @param userId User identifier of sticker set owner
    * @param name Sticker set name
    * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in
    * size, dimensions must not exceed 512px, and either width or
    * height must be exactly 512px. Pass a file_id as a String to
    * send a file that already exists on the Telegram servers,
    * pass an HTTP URL as a String for Telegram to get a file from
    * the Internet, or upload a new one using multipart/form-data.
    * @param tgsSticker TGS animation with the sticker, uploaded using
    * multipart/form-data. See
    * https://core.telegram.org/animated_stickers#technical-requirements
    * for technical requirements
    * @param emojis One or more emoji corresponding to the sticker
    * @param maskPosition A JSON-serialized object for position where the mask should
    * be placed on faces*/
  def addStickerToSet(userId: Int,
                      name: String,
                      pngSticker: Option[IFile] = Option.empty,
                      tgsSticker: Option[IFile] = Option.empty,
                      emojis: String,
                      maskPosition: Option[MaskPosition] = Option.empty): Method[Boolean] = {
    val req = AddStickerToSetReq(userId, name, pngSticker, tgsSticker, emojis, maskPosition)
    MethodReq[Boolean]("addStickerToSet",
                       req.asJson,
                       Map("png_sticker" -> pngSticker, "tgs_sticker" -> tgsSticker).collect {
                         case (k, Some(v)) => k -> v
                       })
  }

  /** Use this method to delete a sticker from a set created by the bot. Returns True
    * on success.

    * @param sticker File identifier of the sticker*/
  def deleteStickerFromSet(sticker: String): Method[Boolean] = {
    val req = DeleteStickerFromSetReq(sticker)
    MethodReq[Boolean]("deleteStickerFromSet", req.asJson)
  }

  /** Use this method to stop a poll which was sent by the bot. On success, the
    * stopped Poll with the final results is returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param messageId Identifier of the original message with the poll
    * @param replyMarkup A JSON-serialized object for a new message inline keyboard.*/
  def stopPoll(chatId: ChatId,
               messageId: Int,
               replyMarkup: Option[InlineKeyboardMarkup] = Option.empty): Method[Poll] = {
    val req = StopPollReq(chatId, messageId, replyMarkup)
    MethodReq[Poll]("stopPoll", req.asJson)
  }

  /** Use this method to remove a message from the list of pinned messages in a chat.
    * If the chat is not a private chat, the bot must be an administrator in the chat
    * for this to work and must have the 'can_pin_messages' admin right in a
    * supergroup or 'can_edit_messages' admin right in a channel. Returns True on
    * success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param messageId Identifier of a message to unpin. If not specified, the
    * most recent pinned message (by sending date) will be
    * unpinned.*/
  def unpinChatMessage(chatId: ChatId, messageId: Option[Int] = Option.empty): Method[Boolean] = {
    val req = UnpinChatMessageReq(chatId, messageId)
    MethodReq[Boolean]("unpinChatMessage", req.asJson)
  }

  /** Use this method to send a group of photos, videos, documents or audios as an
    * album. Documents and audio files can be only grouped in an album with messages
    * of the same type. On success, an array of Messages that were sent is returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param media A JSON-serialized array describing messages to be sent,
    * must include 2-10 items
    * @param disableNotification Sends messages silently. Users will receive a notification
    * with no sound.
    * @param replyToMessageId If the messages are a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found*/
  def sendMediaGroup(
      chatId: ChatId,
      media: List[InputMedia] = List.empty,
      disableNotification: Option[Boolean] = Option.empty,
      replyToMessageId: Option[Int] = Option.empty,
      allowSendingWithoutReply: Option[Boolean] = Option.empty): Method[List[Message]] = {
    val req = SendMediaGroupReq(chatId,
                                media,
                                disableNotification,
                                replyToMessageId,
                                allowSendingWithoutReply)
    MethodReq[List[Message]]("sendMediaGroup", req.asJson)
  }

  /** Use this method to send a game. On success, the sent Message is returned.

    * @param chatId Unique identifier for the target chat
    * @param gameShortName Short name of the game, serves as the unique identifier for
    * the game. Set up your games via Botfather.
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup A JSON-serialized object for an inline keyboard. If empty,
    * one 'Play game_title' button will be shown. If not empty,
    * the first button must launch the game.*/
  def sendGame(chatId: Int,
               gameShortName: String,
               disableNotification: Option[Boolean] = Option.empty,
               replyToMessageId: Option[Int] = Option.empty,
               allowSendingWithoutReply: Option[Boolean] = Option.empty,
               replyMarkup: Option[InlineKeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendGameReq(chatId,
                          gameShortName,
                          disableNotification,
                          replyToMessageId,
                          allowSendingWithoutReply,
                          replyMarkup)
    MethodReq[Message]("sendGame", req.asJson)
  }

  /** Use this method to send information about a venue. On success, the sent Message
    * is returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param latitude Latitude of the venue
    * @param longitude Longitude of the venue
    * @param title Name of the venue
    * @param address Address of the venue
    * @param foursquareId Foursquare identifier of the venue
    * @param foursquareType Foursquare type of the venue, if known. (For example,
    * “arts_entertainment/default”, “arts_entertainment/aquarium”
    * or “food/icecream”.)
    * @param googlePlaceId Google Places identifier of the venue
    * @param googlePlaceType Google Places type of the venue. (See supported types.)
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendVenue(chatId: ChatId,
                latitude: Float,
                longitude: Float,
                title: String,
                address: String,
                foursquareId: Option[String] = Option.empty,
                foursquareType: Option[String] = Option.empty,
                googlePlaceId: Option[String] = Option.empty,
                googlePlaceType: Option[String] = Option.empty,
                disableNotification: Option[Boolean] = Option.empty,
                replyToMessageId: Option[Int] = Option.empty,
                allowSendingWithoutReply: Option[Boolean] = Option.empty,
                replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendVenueReq(
      chatId,
      latitude,
      longitude,
      title,
      address,
      foursquareId,
      foursquareType,
      googlePlaceId,
      googlePlaceType,
      disableNotification,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendVenue", req.asJson)
  }

  /** Use this method to unban a previously kicked user in a supergroup or channel.
    * The user will not return to the group or channel automatically, but will be able
    * to join via link, etc. The bot must be an administrator for this to work. By
    * default, this method guarantees that after the call the user is not a member of
    * the chat, but will be able to join it. So if the user is a member of the chat
    * they will also be removed from the chat. If you don't want this, use the
    * parameter only_if_banned. Returns True on success.

    * @param chatId Unique identifier for the target group or username of the
    * target supergroup or channel (in the format &#064;username)
    * @param userId Unique identifier of the target user
    * @param onlyIfBanned Do nothing if the user is not banned*/
  def unbanChatMember(chatId: ChatId,
                      userId: Int,
                      onlyIfBanned: Option[Boolean] = Option.empty): Method[Boolean] = {
    val req = UnbanChatMemberReq(chatId, userId, onlyIfBanned)
    MethodReq[Boolean]("unbanChatMember", req.asJson)
  }

  /** Use this method to change the description of a group, a supergroup or a
    * channel. The bot must be an administrator in the chat for this to work and must
    * have the appropriate admin rights. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param description New chat description, 0-255 characters*/
  def setChatDescription(chatId: ChatId,
                         description: Option[String] = Option.empty): Method[Boolean] = {
    val req = SetChatDescriptionReq(chatId, description)
    MethodReq[Boolean]("setChatDescription", req.asJson)
  }

  /** Use this method to edit text and game messages. On success, if the edited
    * message is not an inline message, the edited Message is returned, otherwise True
    * is returned.

    * @param chatId Required if inline_message_id is not specified. Unique
    * identifier for the target chat or username of the target
    * channel (in the format &#064;channelusername)
    * @param messageId Required if inline_message_id is not specified. Identifier
    * of the message to edit
    * @param inlineMessageId Required if chat_id and message_id are not specified.
    * Identifier of the inline message
    * @param text New text of the message, 1-4096 characters after entities
    * parsing
    * @param parseMode Mode for parsing entities in the message text. See
    * formatting options for more details.
    * @param entities List of special entities that appear in message text, which
    * can be specified instead of parse_mode
    * @param disableWebPagePreview Disables link previews for links in this message
    * @param replyMarkup A JSON-serialized object for an inline keyboard.*/
  def editMessageText(chatId: Option[ChatId] = Option.empty,
                      messageId: Option[Int] = Option.empty,
                      inlineMessageId: Option[String] = Option.empty,
                      text: String,
                      parseMode: Option[ParseMode] = Option.empty,
                      entities: List[MessageEntity] = List.empty,
                      disableWebPagePreview: Option[Boolean] = Option.empty,
                      replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
    : Method[Either[Boolean, Message]] = {
    val req = EditMessageTextReq(chatId,
                                 messageId,
                                 inlineMessageId,
                                 text,
                                 parseMode,
                                 entities,
                                 disableWebPagePreview,
                                 replyMarkup)
    MethodReq[Either[Boolean, Message]]("editMessageText", req.asJson)
  }

  /** Use this method to edit live location messages. A location can be edited until
    * its live_period expires or editing is explicitly disabled by a call to
    * stopMessageLiveLocation. On success, if the edited message is not an inline
    * message, the edited Message is returned, otherwise True is returned.

    * @param chatId Required if inline_message_id is not specified. Unique
    * identifier for the target chat or username of the target
    * channel (in the format &#064;channelusername)
    * @param messageId Required if inline_message_id is not specified. Identifier
    * of the message to edit
    * @param inlineMessageId Required if chat_id and message_id are not specified.
    * Identifier of the inline message
    * @param latitude Latitude of new location
    * @param longitude Longitude of new location
    * @param horizontalAccuracy The radius of uncertainty for the location, measured in
    * meters; 0-1500
    * @param heading Direction in which the user is moving, in degrees. Must be
    * between 1 and 360 if specified.
    * @param proximityAlertRadius Maximum distance for proximity alerts about approaching
    * another chat member, in meters. Must be between 1 and 100000
    * if specified.
    * @param replyMarkup A JSON-serialized object for a new inline keyboard.*/
  def editMessageLiveLocation(chatId: Option[ChatId] = Option.empty,
                              messageId: Option[Int] = Option.empty,
                              inlineMessageId: Option[String] = Option.empty,
                              latitude: Float,
                              longitude: Float,
                              horizontalAccuracy: Option[Float] = Option.empty,
                              heading: Option[Int] = Option.empty,
                              proximityAlertRadius: Option[Int] = Option.empty,
                              replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
    : Method[Either[Boolean, Message]] = {
    val req = EditMessageLiveLocationReq(chatId,
                                         messageId,
                                         inlineMessageId,
                                         latitude,
                                         longitude,
                                         horizontalAccuracy,
                                         heading,
                                         proximityAlertRadius,
                                         replyMarkup)
    MethodReq[Either[Boolean, Message]]("editMessageLiveLocation", req.asJson)
  }

  /** Use this method to get basic info about a file and prepare it for downloading.
    * For the moment, bots can download files of up to 20MB in size. On success, a
    * File object is returned. The file can then be downloaded via the link
    * https://api.telegram.org/file/bot<token>/<file_path>, where <file_path> is taken
    * from the response. It is guaranteed that the link will be valid for at least 1
    * hour. When the link expires, a new one can be requested by calling getFile
    * again.

    * @param fileId File identifier to get info about*/
  def getFile(fileId: String): Method[File] = {
    val req = GetFileReq(fileId)
    MethodReq[File]("getFile", req.asJson)
  }

  /** Use this method to set the score of the specified user in a game. On success,
    * if the message was sent by the bot, returns the edited Message, otherwise
    * returns True. Returns an error, if the new score is not greater than the user's
    * current score in the chat and force is False.

    * @param userId User identifier
    * @param score New score, must be non-negative
    * @param force Pass True, if the high score is allowed to decrease. This
    * can be useful when fixing mistakes or banning cheaters
    * @param disableEditMessage Pass True, if the game message should not be automatically
    * edited to include the current scoreboard
    * @param chatId Required if inline_message_id is not specified. Unique
    * identifier for the target chat
    * @param messageId Required if inline_message_id is not specified. Identifier
    * of the sent message
    * @param inlineMessageId Required if chat_id and message_id are not specified.
    * Identifier of the inline message*/
  def setGameScore(
      userId: Int,
      score: Int,
      force: Option[Boolean] = Option.empty,
      disableEditMessage: Option[Boolean] = Option.empty,
      chatId: Option[Int] = Option.empty,
      messageId: Option[Int] = Option.empty,
      inlineMessageId: Option[String] = Option.empty): Method[Either[Boolean, Message]] = {
    val req =
      SetGameScoreReq(userId, score, force, disableEditMessage, chatId, messageId, inlineMessageId)
    MethodReq[Either[Boolean, Message]]("setGameScore", req.asJson)
  }

  /** Use this method for your bot to leave a group, supergroup or channel. Returns
    * True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup or channel (in the format
    * &#064;channelusername)*/
  def leaveChat(chatId: ChatId): Method[Boolean] = {
    val req = LeaveChatReq(chatId)
    MethodReq[Boolean]("leaveChat", req.asJson)
  }

  /** Use this method to change the title of a chat. Titles can't be changed for
    * private chats. The bot must be an administrator in the chat for this to work and
    * must have the appropriate admin rights. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param title New chat title, 1-255 characters*/
  def setChatTitle(chatId: ChatId, title: String): Method[Boolean] = {
    val req = SetChatTitleReq(chatId, title)
    MethodReq[Boolean]("setChatTitle", req.asJson)
  }

  /** Use this method to copy messages of any kind. The method is analogous to the
    * method forwardMessages, but the copied message doesn't have a link to the
    * original message. Returns the MessageId of the sent message on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param fromChatId Unique identifier for the chat where the original message
    * was sent (or channel username in the format
    * &#064;channelusername)
    * @param messageId Message identifier in the chat specified in from_chat_id
    * @param caption New caption for media, 0-1024 characters after entities
    * parsing. If not specified, the original caption is kept
    * @param parseMode Mode for parsing entities in the new caption. See
    * formatting options for more details.
    * @param captionEntities List of special entities that appear in the new caption,
    * which can be specified instead of parse_mode
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def copyMessage(chatId: ChatId,
                  fromChatId: ChatId,
                  messageId: Int,
                  caption: Option[String] = Option.empty,
                  parseMode: Option[ParseMode] = Option.empty,
                  captionEntities: List[MessageEntity] = List.empty,
                  disableNotification: Option[Boolean] = Option.empty,
                  replyToMessageId: Option[Int] = Option.empty,
                  allowSendingWithoutReply: Option[Boolean] = Option.empty,
                  replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[MessageId] = {
    val req = CopyMessageReq(chatId,
                             fromChatId,
                             messageId,
                             caption,
                             parseMode,
                             captionEntities,
                             disableNotification,
                             replyToMessageId,
                             allowSendingWithoutReply,
                             replyMarkup)
    MethodReq[MessageId]("copyMessage", req.asJson)
  }

  /** As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1
    * minute long. Use this method to send video messages. On success, the sent
    * Message is returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param videoNote Video note to send. Pass a file_id as String to send a
    * video note that exists on the Telegram servers (recommended)
    * or upload a new video using multipart/form-data. Sending
    * video notes by a URL is currently unsupported
    * @param duration Duration of sent video in seconds
    * @param length Video width and height, i.e. diameter of the video message
    * @param thumb Thumbnail of the file sent; can be ignored if thumbnail
    * generation for the file is supported server-side. The
    * thumbnail should be in JPEG format and less than 200 kB in
    * size. A thumbnail's width and height should not exceed 320.
    * Ignored if the file is not uploaded using
    * multipart/form-data. Thumbnails can't be reused and can be
    * only uploaded as a new file, so you can pass
    * “attach://<file_attach_name>” if the thumbnail was uploaded
    * using multipart/form-data under <file_attach_name>.
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendVideoNote(chatId: ChatId,
                    videoNote: IFile,
                    duration: Option[Int] = Option.empty,
                    length: Option[Int] = Option.empty,
                    thumb: Option[IFile] = Option.empty,
                    disableNotification: Option[Boolean] = Option.empty,
                    replyToMessageId: Option[Int] = Option.empty,
                    allowSendingWithoutReply: Option[Boolean] = Option.empty,
                    replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendVideoNoteReq(chatId,
                               videoNote,
                               duration,
                               length,
                               thumb,
                               disableNotification,
                               replyToMessageId,
                               allowSendingWithoutReply,
                               replyMarkup)
    MethodReq[Message]("sendVideoNote",
                       req.asJson,
                       Map("video_note" -> Option(videoNote), "thumb" -> thumb).collect {
                         case (k, Some(v)) => k -> v
                       })
  }

  /** Informs a user that some of the Telegram Passport elements they provided
    * contains errors. The user will not be able to re-submit their Passport to you
    * until the errors are fixed (the contents of the field for which you returned the
    * error must change). Returns True on success. Use this if the data submitted by
    * the user doesn't satisfy the standards your service requires for any reason. For
    * example, if a birthday date seems invalid, a submitted document is blurry, a
    * scan shows evidence of tampering, etc. Supply some details in the error message
    * to make sure the user knows how to correct the issues.

    * @param userId User identifier
    * @param errors A JSON-serialized array describing the errors*/
  def setPassportDataErrors(userId: Int,
                            errors: List[PassportElementError] = List.empty): Method[Boolean] = {
    val req = SetPassportDataErrorsReq(userId, errors)
    MethodReq[Boolean]("setPassportDataErrors", req.asJson)
  }

  /** Use this method to delete a chat photo. Photos can't be changed for private
    * chats. The bot must be an administrator in the chat for this to work and must
    * have the appropriate admin rights. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)*/
  def deleteChatPhoto(chatId: ChatId): Method[Boolean] = {
    val req = DeleteChatPhotoReq(chatId)
    MethodReq[Boolean]("deleteChatPhoto", req.asJson)
  }

  /** Use this method to send invoices. On success, the sent Message is returned.

    * @param chatId Unique identifier for the target private chat
    * @param title Product name, 1-32 characters
    * @param description Product description, 1-255 characters
    * @param payload Bot-defined invoice payload, 1-128 bytes. This will not be
    * displayed to the user, use for your internal processes.
    * @param providerToken Payments provider token, obtained via Botfather
    * @param startParameter Unique deep-linking parameter that can be used to generate
    * this invoice when used as a start parameter
    * @param currency Three-letter ISO 4217 currency code, see more on currencies
    * @param prices Price breakdown, a JSON-serialized list of components (e.g.
    * product price, tax, discount, delivery cost, delivery tax,
    * bonus, etc.)
    * @param providerData A JSON-serialized data about the invoice, which will be
    * shared with the payment provider. A detailed description of
    * required fields should be provided by the payment provider.
    * @param photoUrl URL of the product photo for the invoice. Can be a photo of
    * the goods or a marketing image for a service. People like it
    * better when they see what they are paying for.
    * @param photoSize Photo size
    * @param photoWidth Photo width
    * @param photoHeight Photo height
    * @param needName Pass True, if you require the user's full name to complete
    * the order
    * @param needPhoneNumber Pass True, if you require the user's phone number to
    * complete the order
    * @param needEmail Pass True, if you require the user's email address to
    * complete the order
    * @param needShippingAddress Pass True, if you require the user's shipping address to
    * complete the order
    * @param sendPhoneNumberToProvider Pass True, if user's phone number should be sent to
    * provider
    * @param sendEmailToProvider Pass True, if user's email address should be sent to
    * provider
    * @param isFlexible Pass True, if the final price depends on the shipping
    * method
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup A JSON-serialized object for an inline keyboard. If empty,
    * one 'Pay total price' button will be shown. If not empty,
    * the first button must be a Pay button.*/
  def sendInvoice(chatId: Int,
                  title: String,
                  description: String,
                  payload: String,
                  providerToken: String,
                  startParameter: String,
                  currency: String,
                  prices: List[LabeledPrice] = List.empty,
                  providerData: Option[String] = Option.empty,
                  photoUrl: Option[String] = Option.empty,
                  photoSize: Option[Int] = Option.empty,
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
                  replyToMessageId: Option[Int] = Option.empty,
                  allowSendingWithoutReply: Option[Boolean] = Option.empty,
                  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendInvoiceReq(
      chatId,
      title,
      description,
      payload,
      providerToken,
      startParameter,
      currency,
      prices,
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
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendInvoice", req.asJson)
  }

  /** Use this method to send general files. On success, the sent Message is
    * returned. Bots can currently send files of any type of up to 50 MB in size, this
    * limit may be changed in the future.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param document File to send. Pass a file_id as String to send a file that
    * exists on the Telegram servers (recommended), pass an HTTP
    * URL as a String for Telegram to get a file from the
    * Internet, or upload a new one using multipart/form-data.
    * @param thumb Thumbnail of the file sent; can be ignored if thumbnail
    * generation for the file is supported server-side. The
    * thumbnail should be in JPEG format and less than 200 kB in
    * size. A thumbnail's width and height should not exceed 320.
    * Ignored if the file is not uploaded using
    * multipart/form-data. Thumbnails can't be reused and can be
    * only uploaded as a new file, so you can pass
    * “attach://<file_attach_name>” if the thumbnail was uploaded
    * using multipart/form-data under <file_attach_name>.
    * @param caption Document caption (may also be used when resending documents
    * by file_id), 0-1024 characters after entities parsing
    * @param parseMode Mode for parsing entities in the document caption. See
    * formatting options for more details.
    * @param captionEntities List of special entities that appear in the caption, which
    * can be specified instead of parse_mode
    * @param disableContentTypeDetection Disables automatic server-side content type detection for
    * files uploaded using multipart/form-data
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendDocument(chatId: ChatId,
                   document: IFile,
                   thumb: Option[IFile] = Option.empty,
                   caption: Option[String] = Option.empty,
                   parseMode: Option[ParseMode] = Option.empty,
                   captionEntities: List[MessageEntity] = List.empty,
                   disableContentTypeDetection: Option[Boolean] = Option.empty,
                   disableNotification: Option[Boolean] = Option.empty,
                   replyToMessageId: Option[Int] = Option.empty,
                   allowSendingWithoutReply: Option[Boolean] = Option.empty,
                   replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendDocumentReq(
      chatId,
      document,
      thumb,
      caption,
      parseMode,
      captionEntities,
      disableContentTypeDetection,
      disableNotification,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendDocument",
                       req.asJson,
                       Map("document" -> Option(document), "thumb" -> thumb).collect {
                         case (k, Some(v)) => k -> v
                       })
  }

  /** Use this method to delete a message, including service messages, with the
    * following limitations: - A message can only be deleted if it was sent less than
    * 48 hours ago. - A dice message in a private chat can only be deleted if it was
    * sent more than 24 hours ago. - Bots can delete outgoing messages in private
    * chats, groups, and supergroups. - Bots can delete incoming messages in private
    * chats. - Bots granted can_post_messages permissions can delete outgoing messages
    * in channels. - If the bot is an administrator of a group, it can delete any
    * message there. - If the bot has can_delete_messages permission in a supergroup
    * or a channel, it can delete any message there. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param messageId Identifier of the message to delete*/
  def deleteMessage(chatId: ChatId, messageId: Int): Method[Boolean] = {
    val req = DeleteMessageReq(chatId, messageId)
    MethodReq[Boolean]("deleteMessage", req.asJson)
  }

  /** Use this method to send answers to an inline query. On success, True is
    * returned. No more than 50 results per query are allowed.

    * @param inlineQueryId Unique identifier for the answered query
    * @param results A JSON-serialized array of results for the inline query
    * @param cacheTime The maximum amount of time in seconds that the result of
    * the inline query may be cached on the server. Defaults to
    * 300.
    * @param isPersonal Pass True, if results may be cached on the server side only
    * for the user that sent the query. By default, results may be
    * returned to any user who sends the same query
    * @param nextOffset Pass the offset that a client should send in the next query
    * with the same text to receive more results. Pass an empty
    * string if there are no more results or if you don't support
    * pagination. Offset length can't exceed 64 bytes.
    * @param switchPmText If passed, clients will display a button with specified
    * text that switches the user to a private chat with the bot
    * and sends the bot a start message with the parameter
    * switch_pm_parameter
    * @param switchPmParameter Deep-linking parameter for the /start message sent to the
    * bot when user presses the switch button. 1-64 characters,
    * only A-Z, a-z, 0-9, _ and - are allowed. Example: An inline
    * bot that sends YouTube videos can ask the user to connect
    * the bot to their YouTube account to adapt search results
    * accordingly. To do this, it displays a 'Connect your YouTube
    * account' button above the results, or even before showing
    * any. The user presses the button, switches to a private chat
    * with the bot and, in doing so, passes a start parameter that
    * instructs the bot to return an oauth link. Once done, the
    * bot can offer a switch_inline button so that the user can
    * easily return to the chat where they wanted to use the bot's
    * inline capabilities.*/
  def answerInlineQuery(inlineQueryId: String,
                        results: List[InlineQueryResult] = List.empty,
                        cacheTime: Option[Int] = Option.empty,
                        isPersonal: Option[Boolean] = Option.empty,
                        nextOffset: Option[String] = Option.empty,
                        switchPmText: Option[String] = Option.empty,
                        switchPmParameter: Option[String] = Option.empty): Method[Boolean] = {
    val req = AnswerInlineQueryReq(inlineQueryId,
                                   results,
                                   cacheTime,
                                   isPersonal,
                                   nextOffset,
                                   switchPmText,
                                   switchPmParameter)
    MethodReq[Boolean]("answerInlineQuery", req.asJson)
  }

  /** Use this method to kick a user from a group, a supergroup or a channel. In the
    * case of supergroups and channels, the user will not be able to return to the
    * chat on their own using invite links, etc., unless unbanned first. The bot must
    * be an administrator in the chat for this to work and must have the appropriate
    * admin rights. Returns True on success.

    * @param chatId Unique identifier for the target group or username of the
    * target supergroup or channel (in the format
    * &#064;channelusername)
    * @param userId Unique identifier of the target user
    * @param untilDate Date when the user will be unbanned, unix time. If user is
    * banned for more than 366 days or less than 30 seconds from
    * the current time they are considered to be banned forever.
    * Applied for supergroups and channels only.*/
  def kickChatMember(chatId: ChatId,
                     userId: Int,
                     untilDate: Option[Int] = Option.empty): Method[Boolean] = {
    val req = KickChatMemberReq(chatId, userId, untilDate)
    MethodReq[Boolean]("kickChatMember", req.asJson)
  }

  /** Use this method to send audio files, if you want Telegram clients to display
    * them in the music player. Your audio must be in the .MP3 or .M4A format. On
    * success, the sent Message is returned. Bots can currently send audio files of up
    * to 50 MB in size, this limit may be changed in the future. For sending voice
    * messages, use the sendVoice method instead.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param audio Audio file to send. Pass a file_id as String to send an
    * audio file that exists on the Telegram servers
    * (recommended), pass an HTTP URL as a String for Telegram to
    * get an audio file from the Internet, or upload a new one
    * using multipart/form-data.
    * @param caption Audio caption, 0-1024 characters after entities parsing
    * @param parseMode Mode for parsing entities in the audio caption. See
    * formatting options for more details.
    * @param captionEntities List of special entities that appear in the caption, which
    * can be specified instead of parse_mode
    * @param duration Duration of the audio in seconds
    * @param performer Performer
    * @param title Track name
    * @param thumb Thumbnail of the file sent; can be ignored if thumbnail
    * generation for the file is supported server-side. The
    * thumbnail should be in JPEG format and less than 200 kB in
    * size. A thumbnail's width and height should not exceed 320.
    * Ignored if the file is not uploaded using
    * multipart/form-data. Thumbnails can't be reused and can be
    * only uploaded as a new file, so you can pass
    * “attach://<file_attach_name>” if the thumbnail was uploaded
    * using multipart/form-data under <file_attach_name>.
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendAudio(chatId: ChatId,
                audio: IFile,
                caption: Option[String] = Option.empty,
                parseMode: Option[ParseMode] = Option.empty,
                captionEntities: List[MessageEntity] = List.empty,
                duration: Option[Int] = Option.empty,
                performer: Option[String] = Option.empty,
                title: Option[String] = Option.empty,
                thumb: Option[IFile] = Option.empty,
                disableNotification: Option[Boolean] = Option.empty,
                replyToMessageId: Option[Int] = Option.empty,
                allowSendingWithoutReply: Option[Boolean] = Option.empty,
                replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendAudioReq(
      chatId,
      audio,
      caption,
      parseMode,
      captionEntities,
      duration,
      performer,
      title,
      thumb,
      disableNotification,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendAudio",
                       req.asJson,
                       Map("audio" -> Option(audio), "thumb" -> thumb).collect {
                         case (k, Some(v)) => k -> v
                       })
  }

  /** Use this method to restrict a user in a supergroup. The bot must be an
    * administrator in the supergroup for this to work and must have the appropriate
    * admin rights. Pass True for all permissions to lift restrictions from a user.
    * Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup (in the format &#064;supergroupusername)
    * @param userId Unique identifier of the target user
    * @param permissions A JSON-serialized object for new user permissions
    * @param untilDate Date when restrictions will be lifted for the user, unix
    * time. If user is restricted for more than 366 days or less
    * than 30 seconds from the current time, they are considered
    * to be restricted forever*/
  def restrictChatMember(chatId: ChatId,
                         userId: Int,
                         permissions: ChatPermissions,
                         untilDate: Option[Int] = Option.empty): Method[Boolean] = {
    val req = RestrictChatMemberReq(chatId, userId, permissions, untilDate)
    MethodReq[Boolean]("restrictChatMember", req.asJson)
  }

  /** A simple method for testing your bot's auth token. Requires no parameters.
    * Returns basic information about the bot in form of a User object.*/
  def getMe(): Method[User] = {
    val req = GetMeReq
    MethodReq[User]("getMe", req.asJson)
  }

  /** Use this method to forward messages of any kind. On success, the sent Message
    * is returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param fromChatId Unique identifier for the chat where the original message
    * was sent (or channel username in the format
    * &#064;channelusername)
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param messageId Message identifier in the chat specified in from_chat_id*/
  def forwardMessage(chatId: ChatId,
                     fromChatId: ChatId,
                     disableNotification: Option[Boolean] = Option.empty,
                     messageId: Int): Method[Message] = {
    val req = ForwardMessageReq(chatId, fromChatId, disableNotification, messageId)
    MethodReq[Message]("forwardMessage", req.asJson)
  }

  /** Use this method to get information about a member of a chat. Returns a
    * ChatMember object on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup or channel (in the format
    * &#064;channelusername)
    * @param userId Unique identifier of the target user*/
  def getChatMember(chatId: ChatId, userId: Int): Method[ChatMember] = {
    val req = GetChatMemberReq(chatId, userId)
    MethodReq[ChatMember]("getChatMember", req.asJson)
  }

  /** Use this method to get the current list of the bot's commands. Requires no
    * parameters. Returns Array of BotCommand on success.*/
  def getMyCommands(): Method[List[BotCommand]] = {
    val req = GetMyCommandsReq
    MethodReq[List[BotCommand]]("getMyCommands", req.asJson)
  }

  /** Use this method to get a list of administrators in a chat. On success, returns
    * an Array of ChatMember objects that contains information about all chat
    * administrators except other bots. If the chat is a group or a supergroup and no
    * administrators were appointed, only the creator will be returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup or channel (in the format
    * &#064;channelusername)*/
  def getChatAdministrators(chatId: ChatId): Method[List[ChatMember]] = {
    val req = GetChatAdministratorsReq(chatId)
    MethodReq[List[ChatMember]]("getChatAdministrators", req.asJson)
  }

  /** Use this method to send audio files, if you want Telegram clients to display
    * the file as a playable voice message. For this to work, your audio must be in an
    * .OGG file encoded with OPUS (other formats may be sent as Audio or Document). On
    * success, the sent Message is returned. Bots can currently send voice messages of
    * up to 50 MB in size, this limit may be changed in the future.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param voice Audio file to send. Pass a file_id as String to send a file
    * that exists on the Telegram servers (recommended), pass an
    * HTTP URL as a String for Telegram to get a file from the
    * Internet, or upload a new one using multipart/form-data.
    * @param caption Voice message caption, 0-1024 characters after entities
    * parsing
    * @param parseMode Mode for parsing entities in the voice message caption. See
    * formatting options for more details.
    * @param captionEntities List of special entities that appear in the caption, which
    * can be specified instead of parse_mode
    * @param duration Duration of the voice message in seconds
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendVoice(chatId: ChatId,
                voice: IFile,
                caption: Option[String] = Option.empty,
                parseMode: Option[ParseMode] = Option.empty,
                captionEntities: List[MessageEntity] = List.empty,
                duration: Option[Int] = Option.empty,
                disableNotification: Option[Boolean] = Option.empty,
                replyToMessageId: Option[Int] = Option.empty,
                allowSendingWithoutReply: Option[Boolean] = Option.empty,
                replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Audio] = {
    val req = SendVoiceReq(chatId,
                           voice,
                           caption,
                           parseMode,
                           captionEntities,
                           duration,
                           disableNotification,
                           replyToMessageId,
                           allowSendingWithoutReply,
                           replyMarkup)
    MethodReq[Audio]("sendVoice", req.asJson, Map("voice" -> Option(voice)).collect {
      case (k, Some(v)) => k -> v
    })
  }

  /** Use this method to log out from the cloud Bot API server before launching the
    * bot locally. You must log out the bot before running it locally, otherwise there
    * is no guarantee that the bot will receive updates. After a successful call, you
    * can immediately log in on a local server, but will not be able to log in back to
    * the cloud Bot API server for 10 minutes. Returns True on success. Requires no
    * parameters.*/
  def logOut(): Method[Boolean] = {
    val req = LogOutReq
    MethodReq[Boolean]("logOut", req.asJson)
  }

  /** Use this method to promote or demote a user in a supergroup or a channel. The
    * bot must be an administrator in the chat for this to work and must have the
    * appropriate admin rights. Pass False for all boolean parameters to demote a
    * user. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param userId Unique identifier of the target user
    * @param isAnonymous Pass True, if the administrator's presence in the chat is
    * hidden
    * @param canChangeInfo Pass True, if the administrator can change chat title,
    * photo and other settings
    * @param canPostMessages Pass True, if the administrator can create channel posts,
    * channels only
    * @param canEditMessages Pass True, if the administrator can edit messages of other
    * users and can pin messages, channels only
    * @param canDeleteMessages Pass True, if the administrator can delete messages of
    * other users
    * @param canInviteUsers Pass True, if the administrator can invite new users to the
    * chat
    * @param canRestrictMembers Pass True, if the administrator can restrict, ban or unban
    * chat members
    * @param canPinMessages Pass True, if the administrator can pin messages,
    * supergroups only
    * @param canPromoteMembers Pass True, if the administrator can add new administrators
    * with a subset of their own privileges or demote
    * administrators that he has promoted, directly or indirectly
    * (promoted by administrators that were appointed by him)*/
  def promoteChatMember(chatId: ChatId,
                        userId: Int,
                        isAnonymous: Option[Boolean] = Option.empty,
                        canChangeInfo: Option[Boolean] = Option.empty,
                        canPostMessages: Option[Boolean] = Option.empty,
                        canEditMessages: Option[Boolean] = Option.empty,
                        canDeleteMessages: Option[Boolean] = Option.empty,
                        canInviteUsers: Option[Boolean] = Option.empty,
                        canRestrictMembers: Option[Boolean] = Option.empty,
                        canPinMessages: Option[Boolean] = Option.empty,
                        canPromoteMembers: Option[Boolean] = Option.empty): Method[Boolean] = {
    val req = PromoteChatMemberReq(
      chatId,
      userId,
      isAnonymous,
      canChangeInfo,
      canPostMessages,
      canEditMessages,
      canDeleteMessages,
      canInviteUsers,
      canRestrictMembers,
      canPinMessages,
      canPromoteMembers
    )
    MethodReq[Boolean]("promoteChatMember", req.asJson)
  }

  /** Use this method to edit captions of messages. On success, if the edited message
    * is not an inline message, the edited Message is returned, otherwise True is
    * returned.

    * @param chatId Required if inline_message_id is not specified. Unique
    * identifier for the target chat or username of the target
    * channel (in the format &#064;channelusername)
    * @param messageId Required if inline_message_id is not specified. Identifier
    * of the message to edit
    * @param inlineMessageId Required if chat_id and message_id are not specified.
    * Identifier of the inline message
    * @param caption New caption of the message, 0-1024 characters after
    * entities parsing
    * @param parseMode Mode for parsing entities in the message caption. See
    * formatting options for more details.
    * @param captionEntities List of special entities that appear in the caption, which
    * can be specified instead of parse_mode
    * @param replyMarkup A JSON-serialized object for an inline keyboard.*/
  def editMessageCaption(chatId: Option[ChatId] = Option.empty,
                         messageId: Option[Int] = Option.empty,
                         inlineMessageId: Option[String] = Option.empty,
                         caption: Option[String] = Option.empty,
                         parseMode: Option[ParseMode] = Option.empty,
                         captionEntities: List[MessageEntity] = List.empty,
                         replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
    : Method[Either[Boolean, Message]] = {
    val req = EditMessageCaptionReq(chatId,
                                    messageId,
                                    inlineMessageId,
                                    caption,
                                    parseMode,
                                    captionEntities,
                                    replyMarkup)
    MethodReq[Either[Boolean, Message]]("editMessageCaption", req.asJson)
  }

  /** Use this method to edit animation, audio, document, photo, or video messages.
    * If a message is part of a message album, then it can be edited only to an audio
    * for audio albums, only to a document for document albums and to a photo or a
    * video otherwise. When an inline message is edited, a new file can't be uploaded.
    * Use a previously uploaded file via its file_id or specify a URL. On success, if
    * the edited message was sent by the bot, the edited Message is returned,
    * otherwise True is returned.

    * @param chatId Required if inline_message_id is not specified. Unique
    * identifier for the target chat or username of the target
    * channel (in the format &#064;channelusername)
    * @param messageId Required if inline_message_id is not specified. Identifier
    * of the message to edit
    * @param inlineMessageId Required if chat_id and message_id are not specified.
    * Identifier of the inline message
    * @param media A JSON-serialized object for a new media content of the
    * message
    * @param replyMarkup A JSON-serialized object for a new inline keyboard.*/
  def editMessageMedia(chatId: Option[ChatId] = Option.empty,
                       messageId: Option[Int] = Option.empty,
                       inlineMessageId: Option[String] = Option.empty,
                       media: InputMedia,
                       replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
    : Method[Either[Boolean, Message]] = {
    val req = EditMessageMediaReq(chatId, messageId, inlineMessageId, media, replyMarkup)
    MethodReq[Either[Boolean, Message]]("editMessageMedia", req.asJson)
  }

  /** Use this method to add a message to the list of pinned messages in a chat. If
    * the chat is not a private chat, the bot must be an administrator in the chat for
    * this to work and must have the 'can_pin_messages' admin right in a supergroup or
    * 'can_edit_messages' admin right in a channel. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param messageId Identifier of a message to pin
    * @param disableNotification Pass True, if it is not necessary to send a notification to
    * all chat members about the new pinned message. Notifications
    * are always disabled in channels and private chats.*/
  def pinChatMessage(chatId: ChatId,
                     messageId: Int,
                     disableNotification: Option[Boolean] = Option.empty): Method[Boolean] = {
    val req = PinChatMessageReq(chatId, messageId, disableNotification)
    MethodReq[Boolean]("pinChatMessage", req.asJson)
  }

  /** Use this method to set the thumbnail of a sticker set. Animated thumbnails can
    * be set for animated sticker sets only. Returns True on success.

    * @param name Sticker set name
    * @param userId User identifier of the sticker set owner
    * @param thumb A PNG image with the thumbnail, must be up to 128 kilobytes
    * in size and have width and height exactly 100px, or a TGS
    * animation with the thumbnail up to 32 kilobytes in size; see
    * https://core.telegram.org/animated_stickers#technical-requirements
    * for animated sticker technical requirements. Pass a file_id
    * as a String to send a file that already exists on the
    * Telegram servers, pass an HTTP URL as a String for Telegram
    * to get a file from the Internet, or upload a new one using
    * multipart/form-data. Animated sticker set thumbnail can't be
    * uploaded via HTTP URL.*/
  def setStickerSetThumb(name: String,
                         userId: Int,
                         thumb: Option[IFile] = Option.empty): Method[Boolean] = {
    val req = SetStickerSetThumbReq(name, userId, thumb)
    MethodReq[Boolean]("setStickerSetThumb", req.asJson, Map("thumb" -> thumb).collect {
      case (k, Some(v)) => k -> v
    })
  }

  /** Use this method to edit only the reply markup of messages. On success, if the
    * edited message is not an inline message, the edited Message is returned,
    * otherwise True is returned.

    * @param chatId Required if inline_message_id is not specified. Unique
    * identifier for the target chat or username of the target
    * channel (in the format &#064;channelusername)
    * @param messageId Required if inline_message_id is not specified. Identifier
    * of the message to edit
    * @param inlineMessageId Required if chat_id and message_id are not specified.
    * Identifier of the inline message
    * @param replyMarkup A JSON-serialized object for an inline keyboard.*/
  def editMessageReplyMarkup(chatId: Option[ChatId] = Option.empty,
                             messageId: Option[Int] = Option.empty,
                             inlineMessageId: Option[String] = Option.empty,
                             replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
    : Method[Either[Boolean, Message]] = {
    val req = EditMessageReplyMarkupReq(chatId, messageId, inlineMessageId, replyMarkup)
    MethodReq[Either[Boolean, Message]]("editMessageReplyMarkup", req.asJson)
  }

  /** Use this method to send video files, Telegram clients support mp4 videos (other
    * formats may be sent as Document). On success, the sent Message is returned. Bots
    * can currently send video files of up to 50 MB in size, this limit may be changed
    * in the future.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param video Video to send. Pass a file_id as String to send a video
    * that exists on the Telegram servers (recommended), pass an
    * HTTP URL as a String for Telegram to get a video from the
    * Internet, or upload a new video using multipart/form-data.
    * @param duration Duration of sent video in seconds
    * @param width Video width
    * @param height Video height
    * @param thumb Thumbnail of the file sent; can be ignored if thumbnail
    * generation for the file is supported server-side. The
    * thumbnail should be in JPEG format and less than 200 kB in
    * size. A thumbnail's width and height should not exceed 320.
    * Ignored if the file is not uploaded using
    * multipart/form-data. Thumbnails can't be reused and can be
    * only uploaded as a new file, so you can pass
    * “attach://<file_attach_name>” if the thumbnail was uploaded
    * using multipart/form-data under <file_attach_name>.
    * @param caption Video caption (may also be used when resending videos by
    * file_id), 0-1024 characters after entities parsing
    * @param parseMode Mode for parsing entities in the video caption. See
    * formatting options for more details.
    * @param captionEntities List of special entities that appear in the caption, which
    * can be specified instead of parse_mode
    * @param supportsStreaming Pass True, if the uploaded video is suitable for streaming
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendVideo(chatId: ChatId,
                video: IFile,
                duration: Option[Int] = Option.empty,
                width: Option[Int] = Option.empty,
                height: Option[Int] = Option.empty,
                thumb: Option[IFile] = Option.empty,
                caption: Option[String] = Option.empty,
                parseMode: Option[ParseMode] = Option.empty,
                captionEntities: List[MessageEntity] = List.empty,
                supportsStreaming: Option[Boolean] = Option.empty,
                disableNotification: Option[Boolean] = Option.empty,
                replyToMessageId: Option[Int] = Option.empty,
                allowSendingWithoutReply: Option[Boolean] = Option.empty,
                replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Document] = {
    val req = SendVideoReq(
      chatId,
      video,
      duration,
      width,
      height,
      thumb,
      caption,
      parseMode,
      captionEntities,
      supportsStreaming,
      disableNotification,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Document]("sendVideo",
                        req.asJson,
                        Map("video" -> Option(video), "thumb" -> thumb).collect {
                          case (k, Some(v)) => k -> v
                        })
  }

  /** Use this method to set a new group sticker set for a supergroup. The bot must
    * be an administrator in the chat for this to work and must have the appropriate
    * admin rights. Use the field can_set_sticker_set optionally returned in getChat
    * requests to check if the bot can use this method. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup (in the format &#064;supergroupusername)
    * @param stickerSetName Name of the sticker set to be set as the group sticker set*/
  def setChatStickerSet(chatId: ChatId, stickerSetName: String): Method[Boolean] = {
    val req = SetChatStickerSetReq(chatId, stickerSetName)
    MethodReq[Boolean]("setChatStickerSet", req.asJson)
  }

  /** Use this method to get up to date information about the chat (current name of
    * the user for one-on-one conversations, current username of a user, group or
    * channel, etc.). Returns a Chat object on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup or channel (in the format
    * &#064;channelusername)*/
  def getChat(chatId: ChatId): Method[Chat] = {
    val req = GetChatReq(chatId)
    MethodReq[Chat]("getChat", req.asJson)
  }

  /** Use this method to remove webhook integration if you decide to switch back to
    * getUpdates. Returns True on success.

    * @param dropPendingUpdates Pass True to drop all pending updates*/
  def deleteWebhook(dropPendingUpdates: Option[Boolean] = Option.empty): Method[Boolean] = {
    val req = DeleteWebhookReq(dropPendingUpdates)
    MethodReq[Boolean]("deleteWebhook", req.asJson)
  }

  /** Use this method to close the bot instance before moving it from one local
    * server to another. You need to delete the webhook before calling this method to
    * ensure that the bot isn't launched again after server restart. The method will
    * return error 429 in the first 10 minutes after the bot is launched. Returns True
    * on success. Requires no parameters.*/
  def close(): Method[Boolean] = {
    val req = CloseReq
    MethodReq[Boolean]("close", req.asJson)
  }

  /** Use this method to move a sticker in a set created by the bot to a specific
    * position. Returns True on success.

    * @param sticker File identifier of the sticker
    * @param position New sticker position in the set, zero-based*/
  def setStickerPositionInSet(sticker: String, position: Int): Method[Boolean] = {
    val req = SetStickerPositionInSetReq(sticker, position)
    MethodReq[Boolean]("setStickerPositionInSet", req.asJson)
  }

  /** Use this method to set a custom title for an administrator in a supergroup
    * promoted by the bot. Returns True on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup (in the format &#064;supergroupusername)
    * @param userId Unique identifier of the target user
    * @param customTitle New custom title for the administrator; 0-16 characters,
    * emoji are not allowed*/
  def setChatAdministratorCustomTitle(chatId: ChatId,
                                      userId: Int,
                                      customTitle: String): Method[Boolean] = {
    val req = SetChatAdministratorCustomTitleReq(chatId, userId, customTitle)
    MethodReq[Boolean]("setChatAdministratorCustomTitle", req.asJson)
  }

  /** Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without
    * sound). On success, the sent Message is returned. Bots can currently send
    * animation files of up to 50 MB in size, this limit may be changed in the future.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param animation Animation to send. Pass a file_id as String to send an
    * animation that exists on the Telegram servers (recommended),
    * pass an HTTP URL as a String for Telegram to get an
    * animation from the Internet, or upload a new animation using
    * multipart/form-data.
    * @param duration Duration of sent animation in seconds
    * @param width Animation width
    * @param height Animation height
    * @param thumb Thumbnail of the file sent; can be ignored if thumbnail
    * generation for the file is supported server-side. The
    * thumbnail should be in JPEG format and less than 200 kB in
    * size. A thumbnail's width and height should not exceed 320.
    * Ignored if the file is not uploaded using
    * multipart/form-data. Thumbnails can't be reused and can be
    * only uploaded as a new file, so you can pass
    * “attach://<file_attach_name>” if the thumbnail was uploaded
    * using multipart/form-data under <file_attach_name>.
    * @param caption Animation caption (may also be used when resending
    * animation by file_id), 0-1024 characters after entities
    * parsing
    * @param parseMode Mode for parsing entities in the animation caption. See
    * formatting options for more details.
    * @param captionEntities List of special entities that appear in the caption, which
    * can be specified instead of parse_mode
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendAnimation(chatId: ChatId,
                    animation: IFile,
                    duration: Option[Int] = Option.empty,
                    width: Option[Int] = Option.empty,
                    height: Option[Int] = Option.empty,
                    thumb: Option[IFile] = Option.empty,
                    caption: Option[String] = Option.empty,
                    parseMode: Option[ParseMode] = Option.empty,
                    captionEntities: List[MessageEntity] = List.empty,
                    disableNotification: Option[Boolean] = Option.empty,
                    replyToMessageId: Option[Int] = Option.empty,
                    allowSendingWithoutReply: Option[Boolean] = Option.empty,
                    replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendAnimationReq(
      chatId,
      animation,
      duration,
      width,
      height,
      thumb,
      caption,
      parseMode,
      captionEntities,
      disableNotification,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    )
    MethodReq[Message]("sendAnimation",
                       req.asJson,
                       Map("animation" -> Option(animation), "thumb" -> thumb).collect {
                         case (k, Some(v)) => k -> v
                       })
  }

  /** If you sent an invoice requesting a shipping address and the parameter
    * is_flexible was specified, the Bot API will send an Update with a shipping_query
    * field to the bot. Use this method to reply to shipping queries. On success, True
    * is returned.

    * @param shippingQueryId Unique identifier for the query to be answered
    * @param ok Specify True if delivery to the specified address is
    * possible and False if there are any problems (for example,
    * if delivery to the specified address is not possible)
    * @param shippingOptions Required if ok is True. A JSON-serialized array of
    * available shipping options.
    * @param errorMessage Required if ok is False. Error message in human readable
    * form that explains why it is impossible to complete the
    * order (e.g. "Sorry, delivery to your desired address is
    * unavailable'). Telegram will display this message to the
    * user.*/
  def answerShippingQuery(shippingQueryId: String,
                          ok: Boolean,
                          shippingOptions: List[ShippingOption] = List.empty,
                          errorMessage: Option[String] = Option.empty): Method[Update] = {
    val req = AnswerShippingQueryReq(shippingQueryId, ok, shippingOptions, errorMessage)
    MethodReq[Update]("answerShippingQuery", req.asJson)
  }

  /** Once the user has confirmed their payment and shipping details, the Bot API
    * sends the final confirmation in the form of an Update with the field
    * pre_checkout_query. Use this method to respond to such pre-checkout queries. On
    * success, True is returned. Note: The Bot API must receive an answer within 10
    * seconds after the pre-checkout query was sent.

    * @param preCheckoutQueryId Unique identifier for the query to be answered
    * @param ok Specify True if everything is alright (goods are available,
    * etc.) and the bot is ready to proceed with the order. Use
    * False if there are any problems.
    * @param errorMessage Required if ok is False. Error message in human readable
    * form that explains the reason for failure to proceed with
    * the checkout (e.g. "Sorry, somebody just bought the last of
    * our amazing black T-shirts while you were busy filling out
    * your payment details. Please choose a different color or
    * garment!"). Telegram will display this message to the user.*/
  def answerPreCheckoutQuery(preCheckoutQueryId: String,
                             ok: Boolean,
                             errorMessage: Option[String] = Option.empty): Method[Update] = {
    val req = AnswerPreCheckoutQueryReq(preCheckoutQueryId, ok, errorMessage)
    MethodReq[Update]("answerPreCheckoutQuery", req.asJson)
  }

  /** Use this method to send static .WEBP or animated .TGS stickers. On success, the
    * sent Message is returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param sticker Sticker to send. Pass a file_id as String to send a file
    * that exists on the Telegram servers (recommended), pass an
    * HTTP URL as a String for Telegram to get a .WEBP file from
    * the Internet, or upload a new one using multipart/form-data.
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendSticker(chatId: ChatId,
                  sticker: IFile,
                  disableNotification: Option[Boolean] = Option.empty,
                  replyToMessageId: Option[Int] = Option.empty,
                  allowSendingWithoutReply: Option[Boolean] = Option.empty,
                  replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendStickerReq(chatId,
                             sticker,
                             disableNotification,
                             replyToMessageId,
                             allowSendingWithoutReply,
                             replyMarkup)
    MethodReq[Message]("sendSticker", req.asJson, Map("sticker" -> Option(sticker)).collect {
      case (k, Some(v)) => k -> v
    })
  }

  /** Use this method to get the number of members in a chat. Returns Int on success.

    * @param chatId Unique identifier for the target chat or username of the
    * target supergroup or channel (in the format
    * &#064;channelusername)*/
  def getChatMembersCount(chatId: ChatId): Method[Int] = {
    val req = GetChatMembersCountReq(chatId)
    MethodReq[Int]("getChatMembersCount", req.asJson)
  }

  /** Use this method to send photos. On success, the sent Message is returned.

    * @param chatId Unique identifier for the target chat or username of the
    * target channel (in the format &#064;channelusername)
    * @param photo Photo to send. Pass a file_id as String to send a photo
    * that exists on the Telegram servers (recommended), pass an
    * HTTP URL as a String for Telegram to get a photo from the
    * Internet, or upload a new photo using multipart/form-data.
    * The photo must be at most 10 MB in size. The photo's width
    * and height must not exceed 10000 in total. Width and height
    * ratio must be at most 20.
    * @param caption Photo caption (may also be used when resending photos by
    * file_id), 0-1024 characters after entities parsing
    * @param parseMode Mode for parsing entities in the photo caption. See
    * formatting options for more details.
    * @param captionEntities List of special entities that appear in the caption, which
    * can be specified instead of parse_mode
    * @param disableNotification Sends the message silently. Users will receive a
    * notification with no sound.
    * @param replyToMessageId If the message is a reply, ID of the original message
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
    * specified replied-to message is not found
    * @param replyMarkup Additional interface options. A JSON-serialized object for
    * an inline keyboard, custom reply keyboard, instructions to
    * remove reply keyboard or to force a reply from the user.*/
  def sendPhoto(chatId: ChatId,
                photo: IFile,
                caption: Option[String] = Option.empty,
                parseMode: Option[ParseMode] = Option.empty,
                captionEntities: List[MessageEntity] = List.empty,
                disableNotification: Option[Boolean] = Option.empty,
                replyToMessageId: Option[Int] = Option.empty,
                allowSendingWithoutReply: Option[Boolean] = Option.empty,
                replyMarkup: Option[KeyboardMarkup] = Option.empty): Method[Message] = {
    val req = SendPhotoReq(chatId,
                           photo,
                           caption,
                           parseMode,
                           captionEntities,
                           disableNotification,
                           replyToMessageId,
                           allowSendingWithoutReply,
                           replyMarkup)
    MethodReq[Message]("sendPhoto", req.asJson, Map("photo" -> Option(photo)).collect {
      case (k, Some(v)) => k -> v
    })
  }

  /** Use this method to receive incoming updates using long polling (wiki). An Array
    * of Update objects is returned.

    * @param offset Identifier of the first update to be returned. Must be
    * greater by one than the highest among the identifiers of
    * previously received updates. By default, updates starting
    * with the earliest unconfirmed update are returned. An update
    * is considered confirmed as soon as getUpdates is called with
    * an offset higher than its update_id. The negative offset can
    * be specified to retrieve updates starting from -offset
    * update from the end of the updates queue. All previous
    * updates will forgotten.
    * @param limit Limits the number of updates to be retrieved. Values
    * between 1-100 are accepted. Defaults to 100.
    * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e.
    * usual short polling. Should be positive, short polling
    * should be used for testing purposes only.
    * @param allowedUpdates A JSON-serialized list of the update types you want your
    * bot to receive. For example, specify [“message”,
    * “edited_channel_post”, “callback_query”] to only receive
    * updates of these types. See Update for a complete list of
    * available update types. Specify an empty list to receive all
    * updates regardless of type (default). If not specified, the
    * previous setting will be used. Please note that this
    * parameter doesn't affect updates created before the call to
    * the getUpdates, so unwanted updates may be received for a
    * short period of time.*/
  def getUpdates(offset: Option[Int] = Option.empty,
                 limit: Option[Int] = Option.empty,
                 timeout: Option[Int] = Option.empty,
                 allowedUpdates: List[String] = List.empty): Method[List[Update]] = {
    val req = GetUpdatesReq(offset, limit, timeout, allowedUpdates)
    MethodReq[List[Update]]("getUpdates", req.asJson)
  }

  /** Use this method to get a sticker set. On success, a StickerSet object is
    * returned.

    * @param name Name of the sticker set*/
  def getStickerSet(name: String): Method[StickerSet] = {
    val req = GetStickerSetReq(name)
    MethodReq[StickerSet]("getStickerSet", req.asJson)
  }

  /** Use this method to specify a url and receive incoming updates via an outgoing
    * webhook. Whenever there is an update for the bot, we will send an HTTPS POST
    * request to the specified url, containing a JSON-serialized Update. In case of an
    * unsuccessful request, we will give up after a reasonable amount of attempts.
    * Returns True on success. If you'd like to make sure that the Webhook request
    * comes from Telegram, we recommend using a secret path in the URL, e.g.
    * https://www.example.com/<token>. Since nobody else knows your bot's token, you
    * can be pretty sure it's us.

    * @param url HTTPS url to send updates to. Use an empty string to remove
    * webhook integration
    * @param certificate Upload your public key certificate so that the root
    * certificate in use can be checked. See our self-signed guide
    * for details.
    * @param ipAddress The fixed IP address which will be used to send webhook
    * requests instead of the IP address resolved through DNS
    * @param maxConnections Maximum allowed number of simultaneous HTTPS connections to
    * the webhook for update delivery, 1-100. Defaults to 40. Use
    * lower values to limit the load on your bot's server, and
    * higher values to increase your bot's throughput.
    * @param allowedUpdates A JSON-serialized list of the update types you want your
    * bot to receive. For example, specify [“message”,
    * “edited_channel_post”, “callback_query”] to only receive
    * updates of these types. See Update for a complete list of
    * available update types. Specify an empty list to receive all
    * updates regardless of type (default). If not specified, the
    * previous setting will be used. Please note that this
    * parameter doesn't affect updates created before the call to
    * the setWebhook, so unwanted updates may be received for a
    * short period of time.
    * @param dropPendingUpdates Pass True to drop all pending updates*/
  def setWebhook(url: String,
                 certificate: Option[IFile] = Option.empty,
                 ipAddress: Option[String] = Option.empty,
                 maxConnections: Option[Int] = Option.empty,
                 allowedUpdates: List[String] = List.empty,
                 dropPendingUpdates: Option[Boolean] = Option.empty): Method[Boolean] = {
    val req =
      SetWebhookReq(url, certificate, ipAddress, maxConnections, allowedUpdates, dropPendingUpdates)
    MethodReq[Boolean]("setWebhook", req.asJson, Map("certificate" -> certificate).collect {
      case (k, Some(v)) => k -> v
    })
  }

}
