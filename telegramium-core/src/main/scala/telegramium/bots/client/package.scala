package telegramium.bots.client

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Encoder, Decoder, Json}
  import iozhik._
  import io.circe.HCursor
  import telegramium.bots.ChatId
  import telegramium.bots.CirceImplicits._
  import telegramium.bots.BotCommand
  import telegramium.bots.BotCommandScope
  import telegramium.bots.IFile
  import telegramium.bots.ParseMode
  import telegramium.bots.MessageEntity
  import telegramium.bots.LinkPreviewOptions
  import telegramium.bots.ReplyParameters
  import telegramium.bots.KeyboardMarkup
  import telegramium.bots.InputPollOption
  import telegramium.bots.InlineQueryResult
  import telegramium.bots.InputSticker
  import telegramium.bots.ChatPermissions
  import telegramium.bots.MaskPosition
  import telegramium.bots.InlineKeyboardMarkup
  import telegramium.bots.LabeledPrice
  import telegramium.bots.InputMedia
  import telegramium.bots.MenuButton
  import telegramium.bots.PassportElementError
  import telegramium.bots.InlineQueryResultsButton
  import telegramium.bots.ChatAdministratorRights
  import telegramium.bots.ShippingOption
  import telegramium.bots.ReactionType

  implicit lazy val responseEncoder: Encoder[Response] =
    (x: Response) => {
      Json.fromFields(
        List(
          "ok"          -> x.ok.asJson,
          "description" -> x.description.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val responseDecoder: Decoder[Response] =
    Decoder.instance { h =>
      for {
        _ok          <- h.get[Boolean]("ok")
        _description <- h.get[Option[String]]("description")
      } yield {
        Response(ok = _ok, description = _description)
      }
    }

  implicit lazy val getwebhookinforeqEncoder: Encoder[GetWebhookInfoReq.type] = (_: GetWebhookInfoReq.type) => ().asJson
  implicit lazy val getwebhookinforeqDecoder: Decoder[GetWebhookInfoReq.type] = (_: HCursor) => Right(GetWebhookInfoReq)

  implicit lazy val unhidegeneralforumtopicreqEncoder: Encoder[UnhideGeneralForumTopicReq] =
    (x: UnhideGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "unhideGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val unhidegeneralforumtopicreqDecoder: Decoder[UnhideGeneralForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        UnhideGeneralForumTopicReq(chatId = _chatId)
      }
    }

  implicit lazy val reopengeneralforumtopicreqEncoder: Encoder[ReopenGeneralForumTopicReq] =
    (x: ReopenGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "reopenGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val reopengeneralforumtopicreqDecoder: Decoder[ReopenGeneralForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        ReopenGeneralForumTopicReq(chatId = _chatId)
      }
    }

  implicit lazy val setmycommandsreqEncoder: Encoder[SetMyCommandsReq] =
    (x: SetMyCommandsReq) => {
      Json.fromFields(
        List(
          "commands"      -> x.commands.asJson,
          "scope"         -> x.scope.asJson,
          "language_code" -> x.languageCode.asJson,
          "method"        -> "setMyCommands".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setmycommandsreqDecoder: Decoder[SetMyCommandsReq] =
    Decoder.instance { h =>
      for {
        _commands     <- h.getOrElse[List[BotCommand]]("commands")(List.empty)
        _scope        <- h.get[Option[BotCommandScope]]("scope")
        _languageCode <- h.get[Option[String]]("language_code")
      } yield {
        SetMyCommandsReq(commands = _commands, scope = _scope, languageCode = _languageCode)
      }
    }

  implicit lazy val getuserchatboostsreqEncoder: Encoder[GetUserChatBoostsReq] =
    (x: GetUserChatBoostsReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "user_id" -> x.userId.asJson,
          "method"  -> "getUserChatBoosts".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getuserchatboostsreqDecoder: Decoder[GetUserChatBoostsReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _userId <- h.get[Long]("user_id")
      } yield {
        GetUserChatBoostsReq(chatId = _chatId, userId = _userId)
      }
    }

  implicit lazy val setchatphotoreqEncoder: Encoder[SetChatPhotoReq] =
    (x: SetChatPhotoReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "photo"   -> x.photo.asJson,
          "method"  -> "setChatPhoto".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setchatphotoreqDecoder: Decoder[SetChatPhotoReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _photo  <- h.get[IFile]("photo")
      } yield {
        SetChatPhotoReq(chatId = _chatId, photo = _photo)
      }
    }

  implicit lazy val getgamehighscoresreqEncoder: Encoder[GetGameHighScoresReq] =
    (x: GetGameHighScoresReq) => {
      Json.fromFields(
        List(
          "user_id"           -> x.userId.asJson,
          "chat_id"           -> x.chatId.asJson,
          "message_id"        -> x.messageId.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "method"            -> "getGameHighScores".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getgamehighscoresreqDecoder: Decoder[GetGameHighScoresReq] =
    Decoder.instance { h =>
      for {
        _userId          <- h.get[Long]("user_id")
        _chatId          <- h.get[Option[Long]]("chat_id")
        _messageId       <- h.get[Option[Int]]("message_id")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
      } yield {
        GetGameHighScoresReq(
          userId = _userId,
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId
        )
      }
    }

  implicit lazy val reopenforumtopicreqEncoder: Encoder[ReopenForumTopicReq] =
    (x: ReopenForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id"           -> x.chatId.asJson,
          "message_thread_id" -> x.messageThreadId.asJson,
          "method"            -> "reopenForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val reopenforumtopicreqDecoder: Decoder[ReopenForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId          <- h.get[ChatId]("chat_id")
        _messageThreadId <- h.get[Int]("message_thread_id")
      } yield {
        ReopenForumTopicReq(chatId = _chatId, messageThreadId = _messageThreadId)
      }
    }

  implicit lazy val unpinallchatmessagesreqEncoder: Encoder[UnpinAllChatMessagesReq] =
    (x: UnpinAllChatMessagesReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "unpinAllChatMessages".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val unpinallchatmessagesreqDecoder: Decoder[UnpinAllChatMessagesReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        UnpinAllChatMessagesReq(chatId = _chatId)
      }
    }

  implicit lazy val answercallbackqueryreqEncoder: Encoder[AnswerCallbackQueryReq] =
    (x: AnswerCallbackQueryReq) => {
      Json.fromFields(
        List(
          "callback_query_id" -> x.callbackQueryId.asJson,
          "text"              -> x.text.asJson,
          "show_alert"        -> x.showAlert.asJson,
          "url"               -> x.url.asJson,
          "cache_time"        -> x.cacheTime.asJson,
          "method"            -> "answerCallbackQuery".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val answercallbackqueryreqDecoder: Decoder[AnswerCallbackQueryReq] =
    Decoder.instance { h =>
      for {
        _callbackQueryId <- h.get[String]("callback_query_id")
        _text            <- h.get[Option[String]]("text")
        _showAlert       <- h.get[Option[Boolean]]("show_alert")
        _url             <- h.get[Option[String]]("url")
        _cacheTime       <- h.get[Option[Int]]("cache_time")
      } yield {
        AnswerCallbackQueryReq(
          callbackQueryId = _callbackQueryId,
          text = _text,
          showAlert = _showAlert,
          url = _url,
          cacheTime = _cacheTime
        )
      }
    }

  implicit lazy val getforumtopiciconstickersreqEncoder: Encoder[GetForumTopicIconStickersReq.type] =
    (_: GetForumTopicIconStickersReq.type) => ().asJson

  implicit lazy val getforumtopiciconstickersreqDecoder: Decoder[GetForumTopicIconStickersReq.type] = (_: HCursor) =>
    Right(GetForumTopicIconStickersReq)

  implicit lazy val getmynamereqEncoder: Encoder[GetMyNameReq] =
    (x: GetMyNameReq) => {
      Json.fromFields(
        List(
          "language_code" -> x.languageCode.asJson,
          "method"        -> "getMyName".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getmynamereqDecoder: Decoder[GetMyNameReq] =
    Decoder.instance { h =>
      for {
        _languageCode <- h.get[Option[String]]("language_code")
      } yield {
        GetMyNameReq(languageCode = _languageCode)
      }
    }

  implicit lazy val getbusinessconnectionreqEncoder: Encoder[GetBusinessConnectionReq] =
    (x: GetBusinessConnectionReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "method"                 -> "getBusinessConnection".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getbusinessconnectionreqDecoder: Decoder[GetBusinessConnectionReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[String]("business_connection_id")
      } yield {
        GetBusinessConnectionReq(businessConnectionId = _businessConnectionId)
      }
    }

  implicit lazy val getmydefaultadministratorrightsreqEncoder: Encoder[GetMyDefaultAdministratorRightsReq] =
    (x: GetMyDefaultAdministratorRightsReq) => {
      Json.fromFields(
        List(
          "for_channels" -> x.forChannels.asJson,
          "method"       -> "getMyDefaultAdministratorRights".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getmydefaultadministratorrightsreqDecoder: Decoder[GetMyDefaultAdministratorRightsReq] =
    Decoder.instance { h =>
      for {
        _forChannels <- h.get[Option[Boolean]]("for_channels")
      } yield {
        GetMyDefaultAdministratorRightsReq(forChannels = _forChannels)
      }
    }

  implicit lazy val sendmessagereqEncoder: Encoder[SendMessageReq] =
    (x: SendMessageReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "text"                   -> x.text.asJson,
          "parse_mode"             -> x.parseMode.asJson,
          "entities"               -> x.entities.asJson,
          "link_preview_options"   -> x.linkPreviewOptions.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendmessagereqDecoder: Decoder[SendMessageReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _text                 <- h.get[String]("text")
        _parseMode            <- h.get[Option[ParseMode]]("parse_mode")
        _entities             <- h.getOrElse[List[MessageEntity]]("entities")(List.empty)
        _linkPreviewOptions   <- h.get[Option[LinkPreviewOptions]]("link_preview_options")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendMessageReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          text = _text,
          parseMode = _parseMode,
          entities = _entities,
          linkPreviewOptions = _linkPreviewOptions,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val getuserprofilephotosreqEncoder: Encoder[GetUserProfilePhotosReq] =
    (x: GetUserProfilePhotosReq) => {
      Json.fromFields(
        List(
          "user_id" -> x.userId.asJson,
          "offset"  -> x.offset.asJson,
          "limit"   -> x.limit.asJson,
          "method"  -> "getUserProfilePhotos".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getuserprofilephotosreqDecoder: Decoder[GetUserProfilePhotosReq] =
    Decoder.instance { h =>
      for {
        _userId <- h.get[Long]("user_id")
        _offset <- h.get[Option[Int]]("offset")
        _limit  <- h.get[Option[Int]]("limit")
      } yield {
        GetUserProfilePhotosReq(userId = _userId, offset = _offset, limit = _limit)
      }
    }

  implicit lazy val sendpollreqEncoder: Encoder[SendPollReq] =
    (x: SendPollReq) => {
      Json.fromFields(
        List(
          "business_connection_id"  -> x.businessConnectionId.asJson,
          "chat_id"                 -> x.chatId.asJson,
          "message_thread_id"       -> x.messageThreadId.asJson,
          "question"                -> x.question.asJson,
          "question_parse_mode"     -> x.questionParseMode.asJson,
          "question_entities"       -> x.questionEntities.asJson,
          "options"                 -> x.options.asJson,
          "is_anonymous"            -> x.isAnonymous.asJson,
          "type"                    -> x.`type`.asJson,
          "allows_multiple_answers" -> x.allowsMultipleAnswers.asJson,
          "correct_option_id"       -> x.correctOptionId.asJson,
          "explanation"             -> x.explanation.asJson,
          "explanation_parse_mode"  -> x.explanationParseMode.asJson,
          "explanation_entities"    -> x.explanationEntities.asJson,
          "open_period"             -> x.openPeriod.asJson,
          "close_date"              -> x.closeDate.asJson,
          "is_closed"               -> x.isClosed.asJson,
          "disable_notification"    -> x.disableNotification.asJson,
          "protect_content"         -> x.protectContent.asJson,
          "reply_parameters"        -> x.replyParameters.asJson,
          "reply_markup"            -> x.replyMarkup.asJson,
          "method"                  -> "sendPoll".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendpollreqDecoder: Decoder[SendPollReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId  <- h.get[Option[String]]("business_connection_id")
        _chatId                <- h.get[ChatId]("chat_id")
        _messageThreadId       <- h.get[Option[Int]]("message_thread_id")
        _question              <- h.get[String]("question")
        _questionParseMode     <- h.get[Option[String]]("question_parse_mode")
        _questionEntities      <- h.getOrElse[List[MessageEntity]]("question_entities")(List.empty)
        _options               <- h.getOrElse[List[InputPollOption]]("options")(List.empty)
        _isAnonymous           <- h.get[Option[Boolean]]("is_anonymous")
        _type                  <- h.get[Option[String]]("type")
        _allowsMultipleAnswers <- h.get[Option[Boolean]]("allows_multiple_answers")
        _correctOptionId       <- h.get[Option[Int]]("correct_option_id")
        _explanation           <- h.get[Option[String]]("explanation")
        _explanationParseMode  <- h.get[Option[ParseMode]]("explanation_parse_mode")
        _explanationEntities   <- h.getOrElse[List[MessageEntity]]("explanation_entities")(List.empty)
        _openPeriod            <- h.get[Option[Int]]("open_period")
        _closeDate             <- h.get[Option[Int]]("close_date")
        _isClosed              <- h.get[Option[Boolean]]("is_closed")
        _disableNotification   <- h.get[Option[Boolean]]("disable_notification")
        _protectContent        <- h.get[Option[Boolean]]("protect_content")
        _replyParameters       <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup           <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendPollReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          question = _question,
          questionParseMode = _questionParseMode,
          questionEntities = _questionEntities,
          options = _options,
          isAnonymous = _isAnonymous,
          `type` = _type,
          allowsMultipleAnswers = _allowsMultipleAnswers,
          correctOptionId = _correctOptionId,
          explanation = _explanation,
          explanationParseMode = _explanationParseMode,
          explanationEntities = _explanationEntities,
          openPeriod = _openPeriod,
          closeDate = _closeDate,
          isClosed = _isClosed,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val answerwebappqueryreqEncoder: Encoder[AnswerWebAppQueryReq] =
    (x: AnswerWebAppQueryReq) => {
      Json.fromFields(
        List(
          "web_app_query_id" -> x.webAppQueryId.asJson,
          "result"           -> x.result.asJson,
          "method"           -> "answerWebAppQuery".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val answerwebappqueryreqDecoder: Decoder[AnswerWebAppQueryReq] =
    Decoder.instance { h =>
      for {
        _webAppQueryId <- h.get[String]("web_app_query_id")
        _result        <- h.get[InlineQueryResult]("result")
      } yield {
        AnswerWebAppQueryReq(webAppQueryId = _webAppQueryId, result = _result)
      }
    }

  implicit lazy val sendcontactreqEncoder: Encoder[SendContactReq] =
    (x: SendContactReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "phone_number"           -> x.phoneNumber.asJson,
          "first_name"             -> x.firstName.asJson,
          "last_name"              -> x.lastName.asJson,
          "vcard"                  -> x.vcard.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendContact".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendcontactreqDecoder: Decoder[SendContactReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _phoneNumber          <- h.get[String]("phone_number")
        _firstName            <- h.get[String]("first_name")
        _lastName             <- h.get[Option[String]]("last_name")
        _vcard                <- h.get[Option[String]]("vcard")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendContactReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          phoneNumber = _phoneNumber,
          firstName = _firstName,
          lastName = _lastName,
          vcard = _vcard,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val setcustomemojistickersetthumbnailreqEncoder: Encoder[SetCustomEmojiStickerSetThumbnailReq] =
    (x: SetCustomEmojiStickerSetThumbnailReq) => {
      Json.fromFields(
        List(
          "name"            -> x.name.asJson,
          "custom_emoji_id" -> x.customEmojiId.asJson,
          "method"          -> "setCustomEmojiStickerSetThumbnail".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setcustomemojistickersetthumbnailreqDecoder: Decoder[SetCustomEmojiStickerSetThumbnailReq] =
    Decoder.instance { h =>
      for {
        _name          <- h.get[String]("name")
        _customEmojiId <- h.get[Option[String]]("custom_emoji_id")
      } yield {
        SetCustomEmojiStickerSetThumbnailReq(name = _name, customEmojiId = _customEmojiId)
      }
    }

  implicit lazy val createnewstickersetreqEncoder: Encoder[CreateNewStickerSetReq] =
    (x: CreateNewStickerSetReq) => {
      Json.fromFields(
        List(
          "user_id"          -> x.userId.asJson,
          "name"             -> x.name.asJson,
          "title"            -> x.title.asJson,
          "stickers"         -> x.stickers.asJson,
          "sticker_type"     -> x.stickerType.asJson,
          "needs_repainting" -> x.needsRepainting.asJson,
          "method"           -> "createNewStickerSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val createnewstickersetreqDecoder: Decoder[CreateNewStickerSetReq] =
    Decoder.instance { h =>
      for {
        _userId          <- h.get[Long]("user_id")
        _name            <- h.get[String]("name")
        _title           <- h.get[String]("title")
        _stickers        <- h.getOrElse[List[InputSticker]]("stickers")(List.empty)
        _stickerType     <- h.get[Option[String]]("sticker_type")
        _needsRepainting <- h.get[Option[Boolean]]("needs_repainting")
      } yield {
        CreateNewStickerSetReq(
          userId = _userId,
          name = _name,
          title = _title,
          stickers = _stickers,
          stickerType = _stickerType,
          needsRepainting = _needsRepainting
        )
      }
    }

  implicit lazy val uploadstickerfilereqEncoder: Encoder[UploadStickerFileReq] =
    (x: UploadStickerFileReq) => {
      Json.fromFields(
        List(
          "user_id"        -> x.userId.asJson,
          "sticker"        -> x.sticker.asJson,
          "sticker_format" -> x.stickerFormat.asJson,
          "method"         -> "uploadStickerFile".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val uploadstickerfilereqDecoder: Decoder[UploadStickerFileReq] =
    Decoder.instance { h =>
      for {
        _userId        <- h.get[Long]("user_id")
        _sticker       <- h.get[IFile]("sticker")
        _stickerFormat <- h.get[String]("sticker_format")
      } yield {
        UploadStickerFileReq(userId = _userId, sticker = _sticker, stickerFormat = _stickerFormat)
      }
    }

  implicit lazy val deletemessagesreqEncoder: Encoder[DeleteMessagesReq] =
    (x: DeleteMessagesReq) => {
      Json.fromFields(
        List(
          "chat_id"     -> x.chatId.asJson,
          "message_ids" -> x.messageIds.asJson,
          "method"      -> "deleteMessages".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deletemessagesreqDecoder: Decoder[DeleteMessagesReq] =
    Decoder.instance { h =>
      for {
        _chatId     <- h.get[ChatId]("chat_id")
        _messageIds <- h.getOrElse[List[Int]]("message_ids")(List.empty)
      } yield {
        DeleteMessagesReq(chatId = _chatId, messageIds = _messageIds)
      }
    }

  implicit lazy val setchatpermissionsreqEncoder: Encoder[SetChatPermissionsReq] =
    (x: SetChatPermissionsReq) => {
      Json.fromFields(
        List(
          "chat_id"                          -> x.chatId.asJson,
          "permissions"                      -> x.permissions.asJson,
          "use_independent_chat_permissions" -> x.useIndependentChatPermissions.asJson,
          "method"                           -> "setChatPermissions".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setchatpermissionsreqDecoder: Decoder[SetChatPermissionsReq] =
    Decoder.instance { h =>
      for {
        _chatId                        <- h.get[ChatId]("chat_id")
        _permissions                   <- h.get[ChatPermissions]("permissions")
        _useIndependentChatPermissions <- h.get[Option[Boolean]]("use_independent_chat_permissions")
      } yield {
        SetChatPermissionsReq(
          chatId = _chatId,
          permissions = _permissions,
          useIndependentChatPermissions = _useIndependentChatPermissions
        )
      }
    }

  implicit lazy val replacestickerinsetreqEncoder: Encoder[ReplaceStickerInSetReq] =
    (x: ReplaceStickerInSetReq) => {
      Json.fromFields(
        List(
          "user_id"     -> x.userId.asJson,
          "name"        -> x.name.asJson,
          "old_sticker" -> x.oldSticker.asJson,
          "sticker"     -> x.sticker.asJson,
          "method"      -> "replaceStickerInSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val replacestickerinsetreqDecoder: Decoder[ReplaceStickerInSetReq] =
    Decoder.instance { h =>
      for {
        _userId     <- h.get[Long]("user_id")
        _name       <- h.get[String]("name")
        _oldSticker <- h.get[String]("old_sticker")
        _sticker    <- h.get[InputSticker]("sticker")
      } yield {
        ReplaceStickerInSetReq(userId = _userId, name = _name, oldSticker = _oldSticker, sticker = _sticker)
      }
    }

  implicit lazy val banchatsenderchatreqEncoder: Encoder[BanChatSenderChatReq] =
    (x: BanChatSenderChatReq) => {
      Json.fromFields(
        List(
          "chat_id"        -> x.chatId.asJson,
          "sender_chat_id" -> x.senderChatId.asJson,
          "method"         -> "banChatSenderChat".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val banchatsenderchatreqDecoder: Decoder[BanChatSenderChatReq] =
    Decoder.instance { h =>
      for {
        _chatId       <- h.get[ChatId]("chat_id")
        _senderChatId <- h.get[Long]("sender_chat_id")
      } yield {
        BanChatSenderChatReq(chatId = _chatId, senderChatId = _senderChatId)
      }
    }

  implicit lazy val setstickermaskpositionreqEncoder: Encoder[SetStickerMaskPositionReq] =
    (x: SetStickerMaskPositionReq) => {
      Json.fromFields(
        List(
          "sticker"       -> x.sticker.asJson,
          "mask_position" -> x.maskPosition.asJson,
          "method"        -> "setStickerMaskPosition".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setstickermaskpositionreqDecoder: Decoder[SetStickerMaskPositionReq] =
    Decoder.instance { h =>
      for {
        _sticker      <- h.get[String]("sticker")
        _maskPosition <- h.get[Option[MaskPosition]]("mask_position")
      } yield {
        SetStickerMaskPositionReq(sticker = _sticker, maskPosition = _maskPosition)
      }
    }

  implicit lazy val sendlocationreqEncoder: Encoder[SendLocationReq] =
    (x: SendLocationReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "latitude"               -> x.latitude.asJson,
          "longitude"              -> x.longitude.asJson,
          "horizontal_accuracy"    -> x.horizontalAccuracy.asJson,
          "live_period"            -> x.livePeriod.asJson,
          "heading"                -> x.heading.asJson,
          "proximity_alert_radius" -> x.proximityAlertRadius.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendLocation".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendlocationreqDecoder: Decoder[SendLocationReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _latitude             <- h.get[Float]("latitude")
        _longitude            <- h.get[Float]("longitude")
        _horizontalAccuracy   <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod           <- h.get[Option[Int]]("live_period")
        _heading              <- h.get[Option[Int]]("heading")
        _proximityAlertRadius <- h.get[Option[Int]]("proximity_alert_radius")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendLocationReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          latitude = _latitude,
          longitude = _longitude,
          horizontalAccuracy = _horizontalAccuracy,
          livePeriod = _livePeriod,
          heading = _heading,
          proximityAlertRadius = _proximityAlertRadius,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val deletechatstickersetreqEncoder: Encoder[DeleteChatStickerSetReq] =
    (x: DeleteChatStickerSetReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "deleteChatStickerSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deletechatstickersetreqDecoder: Decoder[DeleteChatStickerSetReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        DeleteChatStickerSetReq(chatId = _chatId)
      }
    }

  implicit lazy val copymessagesreqEncoder: Encoder[CopyMessagesReq] =
    (x: CopyMessagesReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "message_thread_id"    -> x.messageThreadId.asJson,
          "from_chat_id"         -> x.fromChatId.asJson,
          "message_ids"          -> x.messageIds.asJson,
          "disable_notification" -> x.disableNotification.asJson,
          "protect_content"      -> x.protectContent.asJson,
          "remove_caption"       -> x.removeCaption.asJson,
          "method"               -> "copyMessages".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val copymessagesreqDecoder: Decoder[CopyMessagesReq] =
    Decoder.instance { h =>
      for {
        _chatId              <- h.get[ChatId]("chat_id")
        _messageThreadId     <- h.get[Option[Int]]("message_thread_id")
        _fromChatId          <- h.get[ChatId]("from_chat_id")
        _messageIds          <- h.getOrElse[List[Int]]("message_ids")(List.empty)
        _disableNotification <- h.get[Option[Boolean]]("disable_notification")
        _protectContent      <- h.get[Option[Boolean]]("protect_content")
        _removeCaption       <- h.get[Option[Boolean]]("remove_caption")
      } yield {
        CopyMessagesReq(
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          fromChatId = _fromChatId,
          messageIds = _messageIds,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          removeCaption = _removeCaption
        )
      }
    }

  implicit lazy val getmyshortdescriptionreqEncoder: Encoder[GetMyShortDescriptionReq] =
    (x: GetMyShortDescriptionReq) => {
      Json.fromFields(
        List(
          "language_code" -> x.languageCode.asJson,
          "method"        -> "getMyShortDescription".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getmyshortdescriptionreqDecoder: Decoder[GetMyShortDescriptionReq] =
    Decoder.instance { h =>
      for {
        _languageCode <- h.get[Option[String]]("language_code")
      } yield {
        GetMyShortDescriptionReq(languageCode = _languageCode)
      }
    }

  implicit lazy val stopmessagelivelocationreqEncoder: Encoder[StopMessageLiveLocationReq] =
    (x: StopMessageLiveLocationReq) => {
      Json.fromFields(
        List(
          "chat_id"           -> x.chatId.asJson,
          "message_id"        -> x.messageId.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "reply_markup"      -> x.replyMarkup.asJson,
          "method"            -> "stopMessageLiveLocation".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stopmessagelivelocationreqDecoder: Decoder[StopMessageLiveLocationReq] =
    Decoder.instance { h =>
      for {
        _chatId          <- h.get[Option[ChatId]]("chat_id")
        _messageId       <- h.get[Option[Int]]("message_id")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
        _replyMarkup     <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        StopMessageLiveLocationReq(
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val exportchatinvitelinkreqEncoder: Encoder[ExportChatInviteLinkReq] =
    (x: ExportChatInviteLinkReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "exportChatInviteLink".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val exportchatinvitelinkreqDecoder: Decoder[ExportChatInviteLinkReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        ExportChatInviteLinkReq(chatId = _chatId)
      }
    }

  implicit lazy val declinechatjoinrequestreqEncoder: Encoder[DeclineChatJoinRequestReq] =
    (x: DeclineChatJoinRequestReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "user_id" -> x.userId.asJson,
          "method"  -> "declineChatJoinRequest".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val declinechatjoinrequestreqDecoder: Decoder[DeclineChatJoinRequestReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _userId <- h.get[Long]("user_id")
      } yield {
        DeclineChatJoinRequestReq(chatId = _chatId, userId = _userId)
      }
    }

  implicit lazy val senddicereqEncoder: Encoder[SendDiceReq] =
    (x: SendDiceReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "emoji"                  -> x.emoji.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendDice".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val senddicereqDecoder: Decoder[SendDiceReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _emoji                <- h.get[Option[String]]("emoji")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendDiceReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          emoji = _emoji,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val sendchatactionreqEncoder: Encoder[SendChatActionReq] =
    (x: SendChatActionReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "action"                 -> x.action.asJson,
          "method"                 -> "sendChatAction".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendchatactionreqDecoder: Decoder[SendChatActionReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _action               <- h.get[String]("action")
      } yield {
        SendChatActionReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          action = _action
        )
      }
    }

  implicit lazy val addstickertosetreqEncoder: Encoder[AddStickerToSetReq] =
    (x: AddStickerToSetReq) => {
      Json.fromFields(
        List(
          "user_id" -> x.userId.asJson,
          "name"    -> x.name.asJson,
          "sticker" -> x.sticker.asJson,
          "method"  -> "addStickerToSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val addstickertosetreqDecoder: Decoder[AddStickerToSetReq] =
    Decoder.instance { h =>
      for {
        _userId  <- h.get[Long]("user_id")
        _name    <- h.get[String]("name")
        _sticker <- h.get[InputSticker]("sticker")
      } yield {
        AddStickerToSetReq(userId = _userId, name = _name, sticker = _sticker)
      }
    }

  implicit lazy val createinvoicelinkreqEncoder: Encoder[CreateInvoiceLinkReq] =
    (x: CreateInvoiceLinkReq) => {
      Json.fromFields(
        List(
          "title"                         -> x.title.asJson,
          "description"                   -> x.description.asJson,
          "payload"                       -> x.payload.asJson,
          "provider_token"                -> x.providerToken.asJson,
          "currency"                      -> x.currency.asJson,
          "prices"                        -> x.prices.asJson,
          "max_tip_amount"                -> x.maxTipAmount.asJson,
          "suggested_tip_amounts"         -> x.suggestedTipAmounts.asJson,
          "provider_data"                 -> x.providerData.asJson,
          "photo_url"                     -> x.photoUrl.asJson,
          "photo_size"                    -> x.photoSize.asJson,
          "photo_width"                   -> x.photoWidth.asJson,
          "photo_height"                  -> x.photoHeight.asJson,
          "need_name"                     -> x.needName.asJson,
          "need_phone_number"             -> x.needPhoneNumber.asJson,
          "need_email"                    -> x.needEmail.asJson,
          "need_shipping_address"         -> x.needShippingAddress.asJson,
          "send_phone_number_to_provider" -> x.sendPhoneNumberToProvider.asJson,
          "send_email_to_provider"        -> x.sendEmailToProvider.asJson,
          "is_flexible"                   -> x.isFlexible.asJson,
          "method"                        -> "createInvoiceLink".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val createinvoicelinkreqDecoder: Decoder[CreateInvoiceLinkReq] =
    Decoder.instance { h =>
      for {
        _title                     <- h.get[String]("title")
        _description               <- h.get[String]("description")
        _payload                   <- h.get[String]("payload")
        _providerToken             <- h.get[String]("provider_token")
        _currency                  <- h.get[String]("currency")
        _prices                    <- h.getOrElse[List[LabeledPrice]]("prices")(List.empty)
        _maxTipAmount              <- h.get[Option[Int]]("max_tip_amount")
        _suggestedTipAmounts       <- h.getOrElse[List[Int]]("suggested_tip_amounts")(List.empty)
        _providerData              <- h.get[Option[String]]("provider_data")
        _photoUrl                  <- h.get[Option[String]]("photo_url")
        _photoSize                 <- h.get[Option[Long]]("photo_size")
        _photoWidth                <- h.get[Option[Int]]("photo_width")
        _photoHeight               <- h.get[Option[Int]]("photo_height")
        _needName                  <- h.get[Option[Boolean]]("need_name")
        _needPhoneNumber           <- h.get[Option[Boolean]]("need_phone_number")
        _needEmail                 <- h.get[Option[Boolean]]("need_email")
        _needShippingAddress       <- h.get[Option[Boolean]]("need_shipping_address")
        _sendPhoneNumberToProvider <- h.get[Option[Boolean]]("send_phone_number_to_provider")
        _sendEmailToProvider       <- h.get[Option[Boolean]]("send_email_to_provider")
        _isFlexible                <- h.get[Option[Boolean]]("is_flexible")
      } yield {
        CreateInvoiceLinkReq(
          title = _title,
          description = _description,
          payload = _payload,
          providerToken = _providerToken,
          currency = _currency,
          prices = _prices,
          maxTipAmount = _maxTipAmount,
          suggestedTipAmounts = _suggestedTipAmounts,
          providerData = _providerData,
          photoUrl = _photoUrl,
          photoSize = _photoSize,
          photoWidth = _photoWidth,
          photoHeight = _photoHeight,
          needName = _needName,
          needPhoneNumber = _needPhoneNumber,
          needEmail = _needEmail,
          needShippingAddress = _needShippingAddress,
          sendPhoneNumberToProvider = _sendPhoneNumberToProvider,
          sendEmailToProvider = _sendEmailToProvider,
          isFlexible = _isFlexible
        )
      }
    }

  implicit lazy val deletestickerfromsetreqEncoder: Encoder[DeleteStickerFromSetReq] =
    (x: DeleteStickerFromSetReq) => {
      Json.fromFields(
        List(
          "sticker" -> x.sticker.asJson,
          "method"  -> "deleteStickerFromSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deletestickerfromsetreqDecoder: Decoder[DeleteStickerFromSetReq] =
    Decoder.instance { h =>
      for {
        _sticker <- h.get[String]("sticker")
      } yield {
        DeleteStickerFromSetReq(sticker = _sticker)
      }
    }

  implicit lazy val stoppollreqEncoder: Encoder[StopPollReq] =
    (x: StopPollReq) => {
      Json.fromFields(
        List(
          "chat_id"      -> x.chatId.asJson,
          "message_id"   -> x.messageId.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "method"       -> "stopPoll".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stoppollreqDecoder: Decoder[StopPollReq] =
    Decoder.instance { h =>
      for {
        _chatId      <- h.get[ChatId]("chat_id")
        _messageId   <- h.get[Int]("message_id")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        StopPollReq(chatId = _chatId, messageId = _messageId, replyMarkup = _replyMarkup)
      }
    }

  implicit lazy val unpinchatmessagereqEncoder: Encoder[UnpinChatMessageReq] =
    (x: UnpinChatMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"    -> x.chatId.asJson,
          "message_id" -> x.messageId.asJson,
          "method"     -> "unpinChatMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val unpinchatmessagereqDecoder: Decoder[UnpinChatMessageReq] =
    Decoder.instance { h =>
      for {
        _chatId    <- h.get[ChatId]("chat_id")
        _messageId <- h.get[Option[Int]]("message_id")
      } yield {
        UnpinChatMessageReq(chatId = _chatId, messageId = _messageId)
      }
    }

  implicit lazy val getchatmembercountreqEncoder: Encoder[GetChatMemberCountReq] =
    (x: GetChatMemberCountReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "getChatMemberCount".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getchatmembercountreqDecoder: Decoder[GetChatMemberCountReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        GetChatMemberCountReq(chatId = _chatId)
      }
    }

  implicit lazy val sendmediagroupreqEncoder: Encoder[SendMediaGroupReq] =
    (x: SendMediaGroupReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "media"                  -> x.media.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "method"                 -> "sendMediaGroup".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendmediagroupreqDecoder: Decoder[SendMediaGroupReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _media                <- h.getOrElse[List[InputMedia]]("media")(List.empty)
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
      } yield {
        SendMediaGroupReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          media = _media,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters
        )
      }
    }

  implicit lazy val sendgamereqEncoder: Encoder[SendGameReq] =
    (x: SendGameReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "game_short_name"        -> x.gameShortName.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendGame".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendgamereqDecoder: Decoder[SendGameReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[Long]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _gameShortName        <- h.get[String]("game_short_name")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        SendGameReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          gameShortName = _gameShortName,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val sendvenuereqEncoder: Encoder[SendVenueReq] =
    (x: SendVenueReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "latitude"               -> x.latitude.asJson,
          "longitude"              -> x.longitude.asJson,
          "title"                  -> x.title.asJson,
          "address"                -> x.address.asJson,
          "foursquare_id"          -> x.foursquareId.asJson,
          "foursquare_type"        -> x.foursquareType.asJson,
          "google_place_id"        -> x.googlePlaceId.asJson,
          "google_place_type"      -> x.googlePlaceType.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendVenue".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvenuereqDecoder: Decoder[SendVenueReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _latitude             <- h.get[Float]("latitude")
        _longitude            <- h.get[Float]("longitude")
        _title                <- h.get[String]("title")
        _address              <- h.get[String]("address")
        _foursquareId         <- h.get[Option[String]]("foursquare_id")
        _foursquareType       <- h.get[Option[String]]("foursquare_type")
        _googlePlaceId        <- h.get[Option[String]]("google_place_id")
        _googlePlaceType      <- h.get[Option[String]]("google_place_type")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendVenueReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          latitude = _latitude,
          longitude = _longitude,
          title = _title,
          address = _address,
          foursquareId = _foursquareId,
          foursquareType = _foursquareType,
          googlePlaceId = _googlePlaceId,
          googlePlaceType = _googlePlaceType,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val createforumtopicreqEncoder: Encoder[CreateForumTopicReq] =
    (x: CreateForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "name"                 -> x.name.asJson,
          "icon_color"           -> x.iconColor.asJson,
          "icon_custom_emoji_id" -> x.iconCustomEmojiId.asJson,
          "method"               -> "createForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val createforumtopicreqDecoder: Decoder[CreateForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId            <- h.get[ChatId]("chat_id")
        _name              <- h.get[String]("name")
        _iconColor         <- h.get[Option[Int]]("icon_color")
        _iconCustomEmojiId <- h.get[Option[String]]("icon_custom_emoji_id")
      } yield {
        CreateForumTopicReq(
          chatId = _chatId,
          name = _name,
          iconColor = _iconColor,
          iconCustomEmojiId = _iconCustomEmojiId
        )
      }
    }

  implicit lazy val editgeneralforumtopicreqEncoder: Encoder[EditGeneralForumTopicReq] =
    (x: EditGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "name"    -> x.name.asJson,
          "method"  -> "editGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editgeneralforumtopicreqDecoder: Decoder[EditGeneralForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _name   <- h.get[String]("name")
      } yield {
        EditGeneralForumTopicReq(chatId = _chatId, name = _name)
      }
    }

  implicit lazy val unbanchatmemberreqEncoder: Encoder[UnbanChatMemberReq] =
    (x: UnbanChatMemberReq) => {
      Json.fromFields(
        List(
          "chat_id"        -> x.chatId.asJson,
          "user_id"        -> x.userId.asJson,
          "only_if_banned" -> x.onlyIfBanned.asJson,
          "method"         -> "unbanChatMember".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val unbanchatmemberreqDecoder: Decoder[UnbanChatMemberReq] =
    Decoder.instance { h =>
      for {
        _chatId       <- h.get[ChatId]("chat_id")
        _userId       <- h.get[Long]("user_id")
        _onlyIfBanned <- h.get[Option[Boolean]]("only_if_banned")
      } yield {
        UnbanChatMemberReq(chatId = _chatId, userId = _userId, onlyIfBanned = _onlyIfBanned)
      }
    }

  implicit lazy val getcustomemojistickersreqEncoder: Encoder[GetCustomEmojiStickersReq] =
    (x: GetCustomEmojiStickersReq) => {
      Json.fromFields(
        List(
          "custom_emoji_ids" -> x.customEmojiIds.asJson,
          "method"           -> "getCustomEmojiStickers".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getcustomemojistickersreqDecoder: Decoder[GetCustomEmojiStickersReq] =
    Decoder.instance { h =>
      for {
        _customEmojiIds <- h.getOrElse[List[String]]("custom_emoji_ids")(List.empty)
      } yield {
        GetCustomEmojiStickersReq(customEmojiIds = _customEmojiIds)
      }
    }

  implicit lazy val deleteforumtopicreqEncoder: Encoder[DeleteForumTopicReq] =
    (x: DeleteForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id"           -> x.chatId.asJson,
          "message_thread_id" -> x.messageThreadId.asJson,
          "method"            -> "deleteForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deleteforumtopicreqDecoder: Decoder[DeleteForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId          <- h.get[ChatId]("chat_id")
        _messageThreadId <- h.get[Int]("message_thread_id")
      } yield {
        DeleteForumTopicReq(chatId = _chatId, messageThreadId = _messageThreadId)
      }
    }

  implicit lazy val setchatdescriptionreqEncoder: Encoder[SetChatDescriptionReq] =
    (x: SetChatDescriptionReq) => {
      Json.fromFields(
        List(
          "chat_id"     -> x.chatId.asJson,
          "description" -> x.description.asJson,
          "method"      -> "setChatDescription".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setchatdescriptionreqDecoder: Decoder[SetChatDescriptionReq] =
    Decoder.instance { h =>
      for {
        _chatId      <- h.get[ChatId]("chat_id")
        _description <- h.get[Option[String]]("description")
      } yield {
        SetChatDescriptionReq(chatId = _chatId, description = _description)
      }
    }

  implicit lazy val editmessagetextreqEncoder: Encoder[EditMessageTextReq] =
    (x: EditMessageTextReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "message_id"           -> x.messageId.asJson,
          "inline_message_id"    -> x.inlineMessageId.asJson,
          "text"                 -> x.text.asJson,
          "parse_mode"           -> x.parseMode.asJson,
          "entities"             -> x.entities.asJson,
          "link_preview_options" -> x.linkPreviewOptions.asJson,
          "reply_markup"         -> x.replyMarkup.asJson,
          "method"               -> "editMessageText".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagetextreqDecoder: Decoder[EditMessageTextReq] =
    Decoder.instance { h =>
      for {
        _chatId             <- h.get[Option[ChatId]]("chat_id")
        _messageId          <- h.get[Option[Int]]("message_id")
        _inlineMessageId    <- h.get[Option[String]]("inline_message_id")
        _text               <- h.get[String]("text")
        _parseMode          <- h.get[Option[ParseMode]]("parse_mode")
        _entities           <- h.getOrElse[List[MessageEntity]]("entities")(List.empty)
        _linkPreviewOptions <- h.get[Option[LinkPreviewOptions]]("link_preview_options")
        _replyMarkup        <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        EditMessageTextReq(
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId,
          text = _text,
          parseMode = _parseMode,
          entities = _entities,
          linkPreviewOptions = _linkPreviewOptions,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val getmydescriptionreqEncoder: Encoder[GetMyDescriptionReq] =
    (x: GetMyDescriptionReq) => {
      Json.fromFields(
        List(
          "language_code" -> x.languageCode.asJson,
          "method"        -> "getMyDescription".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getmydescriptionreqDecoder: Decoder[GetMyDescriptionReq] =
    Decoder.instance { h =>
      for {
        _languageCode <- h.get[Option[String]]("language_code")
      } yield {
        GetMyDescriptionReq(languageCode = _languageCode)
      }
    }

  implicit lazy val editmessagelivelocationreqEncoder: Encoder[EditMessageLiveLocationReq] =
    (x: EditMessageLiveLocationReq) => {
      Json.fromFields(
        List(
          "chat_id"                -> x.chatId.asJson,
          "message_id"             -> x.messageId.asJson,
          "inline_message_id"      -> x.inlineMessageId.asJson,
          "latitude"               -> x.latitude.asJson,
          "longitude"              -> x.longitude.asJson,
          "live_period"            -> x.livePeriod.asJson,
          "horizontal_accuracy"    -> x.horizontalAccuracy.asJson,
          "heading"                -> x.heading.asJson,
          "proximity_alert_radius" -> x.proximityAlertRadius.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "editMessageLiveLocation".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagelivelocationreqDecoder: Decoder[EditMessageLiveLocationReq] =
    Decoder.instance { h =>
      for {
        _chatId               <- h.get[Option[ChatId]]("chat_id")
        _messageId            <- h.get[Option[Int]]("message_id")
        _inlineMessageId      <- h.get[Option[String]]("inline_message_id")
        _latitude             <- h.get[Float]("latitude")
        _longitude            <- h.get[Float]("longitude")
        _livePeriod           <- h.get[Option[Int]]("live_period")
        _horizontalAccuracy   <- h.get[Option[Float]]("horizontal_accuracy")
        _heading              <- h.get[Option[Int]]("heading")
        _proximityAlertRadius <- h.get[Option[Int]]("proximity_alert_radius")
        _replyMarkup          <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        EditMessageLiveLocationReq(
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId,
          latitude = _latitude,
          longitude = _longitude,
          livePeriod = _livePeriod,
          horizontalAccuracy = _horizontalAccuracy,
          heading = _heading,
          proximityAlertRadius = _proximityAlertRadius,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val unpinallforumtopicmessagesreqEncoder: Encoder[UnpinAllForumTopicMessagesReq] =
    (x: UnpinAllForumTopicMessagesReq) => {
      Json.fromFields(
        List(
          "chat_id"           -> x.chatId.asJson,
          "message_thread_id" -> x.messageThreadId.asJson,
          "method"            -> "unpinAllForumTopicMessages".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val unpinallforumtopicmessagesreqDecoder: Decoder[UnpinAllForumTopicMessagesReq] =
    Decoder.instance { h =>
      for {
        _chatId          <- h.get[ChatId]("chat_id")
        _messageThreadId <- h.get[Int]("message_thread_id")
      } yield {
        UnpinAllForumTopicMessagesReq(chatId = _chatId, messageThreadId = _messageThreadId)
      }
    }

  implicit lazy val getfilereqEncoder: Encoder[GetFileReq] =
    (x: GetFileReq) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "method"  -> "getFile".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getfilereqDecoder: Decoder[GetFileReq] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
      } yield {
        GetFileReq(fileId = _fileId)
      }
    }

  implicit lazy val setgamescorereqEncoder: Encoder[SetGameScoreReq] =
    (x: SetGameScoreReq) => {
      Json.fromFields(
        List(
          "user_id"              -> x.userId.asJson,
          "score"                -> x.score.asJson,
          "force"                -> x.force.asJson,
          "disable_edit_message" -> x.disableEditMessage.asJson,
          "chat_id"              -> x.chatId.asJson,
          "message_id"           -> x.messageId.asJson,
          "inline_message_id"    -> x.inlineMessageId.asJson,
          "method"               -> "setGameScore".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setgamescorereqDecoder: Decoder[SetGameScoreReq] =
    Decoder.instance { h =>
      for {
        _userId             <- h.get[Long]("user_id")
        _score              <- h.get[Int]("score")
        _force              <- h.get[Option[Boolean]]("force")
        _disableEditMessage <- h.get[Option[Boolean]]("disable_edit_message")
        _chatId             <- h.get[Option[Long]]("chat_id")
        _messageId          <- h.get[Option[Int]]("message_id")
        _inlineMessageId    <- h.get[Option[String]]("inline_message_id")
      } yield {
        SetGameScoreReq(
          userId = _userId,
          score = _score,
          force = _force,
          disableEditMessage = _disableEditMessage,
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId
        )
      }
    }

  implicit lazy val leavechatreqEncoder: Encoder[LeaveChatReq] =
    (x: LeaveChatReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "leaveChat".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val leavechatreqDecoder: Decoder[LeaveChatReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        LeaveChatReq(chatId = _chatId)
      }
    }

  implicit lazy val setchattitlereqEncoder: Encoder[SetChatTitleReq] =
    (x: SetChatTitleReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "title"   -> x.title.asJson,
          "method"  -> "setChatTitle".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setchattitlereqDecoder: Decoder[SetChatTitleReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _title  <- h.get[String]("title")
      } yield {
        SetChatTitleReq(chatId = _chatId, title = _title)
      }
    }

  implicit lazy val copymessagereqEncoder: Encoder[CopyMessageReq] =
    (x: CopyMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "message_thread_id"    -> x.messageThreadId.asJson,
          "from_chat_id"         -> x.fromChatId.asJson,
          "message_id"           -> x.messageId.asJson,
          "caption"              -> x.caption.asJson,
          "parse_mode"           -> x.parseMode.asJson,
          "caption_entities"     -> x.captionEntities.asJson,
          "disable_notification" -> x.disableNotification.asJson,
          "protect_content"      -> x.protectContent.asJson,
          "reply_parameters"     -> x.replyParameters.asJson,
          "reply_markup"         -> x.replyMarkup.asJson,
          "method"               -> "copyMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val copymessagereqDecoder: Decoder[CopyMessageReq] =
    Decoder.instance { h =>
      for {
        _chatId              <- h.get[ChatId]("chat_id")
        _messageThreadId     <- h.get[Option[Int]]("message_thread_id")
        _fromChatId          <- h.get[ChatId]("from_chat_id")
        _messageId           <- h.get[Int]("message_id")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _disableNotification <- h.get[Option[Boolean]]("disable_notification")
        _protectContent      <- h.get[Option[Boolean]]("protect_content")
        _replyParameters     <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup         <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        CopyMessageReq(
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          fromChatId = _fromChatId,
          messageId = _messageId,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val sendvideonotereqEncoder: Encoder[SendVideoNoteReq] =
    (x: SendVideoNoteReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "video_note"             -> x.videoNote.asJson,
          "duration"               -> x.duration.asJson,
          "length"                 -> x.length.asJson,
          "thumbnail"              -> x.thumbnail.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendVideoNote".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvideonotereqDecoder: Decoder[SendVideoNoteReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _videoNote            <- h.get[IFile]("video_note")
        _duration             <- h.get[Option[Int]]("duration")
        _length               <- h.get[Option[Int]]("length")
        _thumbnail            <- h.get[Option[IFile]]("thumbnail")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendVideoNoteReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          videoNote = _videoNote,
          duration = _duration,
          length = _length,
          thumbnail = _thumbnail,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val setchatmenubuttonreqEncoder: Encoder[SetChatMenuButtonReq] =
    (x: SetChatMenuButtonReq) => {
      Json.fromFields(
        List(
          "chat_id"     -> x.chatId.asJson,
          "menu_button" -> x.menuButton.asJson,
          "method"      -> "setChatMenuButton".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setchatmenubuttonreqDecoder: Decoder[SetChatMenuButtonReq] =
    Decoder.instance { h =>
      for {
        _chatId     <- h.get[Option[Long]]("chat_id")
        _menuButton <- h.get[Option[MenuButton]]("menu_button")
      } yield {
        SetChatMenuButtonReq(chatId = _chatId, menuButton = _menuButton)
      }
    }

  implicit lazy val setpassportdataerrorsreqEncoder: Encoder[SetPassportDataErrorsReq] =
    (x: SetPassportDataErrorsReq) => {
      Json.fromFields(
        List(
          "user_id" -> x.userId.asJson,
          "errors"  -> x.errors.asJson,
          "method"  -> "setPassportDataErrors".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setpassportdataerrorsreqDecoder: Decoder[SetPassportDataErrorsReq] =
    Decoder.instance { h =>
      for {
        _userId <- h.get[Long]("user_id")
        _errors <- h.getOrElse[List[PassportElementError]]("errors")(List.empty)
      } yield {
        SetPassportDataErrorsReq(userId = _userId, errors = _errors)
      }
    }

  implicit lazy val deletechatphotoreqEncoder: Encoder[DeleteChatPhotoReq] =
    (x: DeleteChatPhotoReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "deleteChatPhoto".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deletechatphotoreqDecoder: Decoder[DeleteChatPhotoReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        DeleteChatPhotoReq(chatId = _chatId)
      }
    }

  implicit lazy val editchatinvitelinkreqEncoder: Encoder[EditChatInviteLinkReq] =
    (x: EditChatInviteLinkReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "invite_link"          -> x.inviteLink.asJson,
          "name"                 -> x.name.asJson,
          "expire_date"          -> x.expireDate.asJson,
          "member_limit"         -> x.memberLimit.asJson,
          "creates_join_request" -> x.createsJoinRequest.asJson,
          "method"               -> "editChatInviteLink".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editchatinvitelinkreqDecoder: Decoder[EditChatInviteLinkReq] =
    Decoder.instance { h =>
      for {
        _chatId             <- h.get[ChatId]("chat_id")
        _inviteLink         <- h.get[String]("invite_link")
        _name               <- h.get[Option[String]]("name")
        _expireDate         <- h.get[Option[Int]]("expire_date")
        _memberLimit        <- h.get[Option[Int]]("member_limit")
        _createsJoinRequest <- h.get[Option[Boolean]]("creates_join_request")
      } yield {
        EditChatInviteLinkReq(
          chatId = _chatId,
          inviteLink = _inviteLink,
          name = _name,
          expireDate = _expireDate,
          memberLimit = _memberLimit,
          createsJoinRequest = _createsJoinRequest
        )
      }
    }

  implicit lazy val sendinvoicereqEncoder: Encoder[SendInvoiceReq] =
    (x: SendInvoiceReq) => {
      Json.fromFields(
        List(
          "chat_id"                       -> x.chatId.asJson,
          "message_thread_id"             -> x.messageThreadId.asJson,
          "title"                         -> x.title.asJson,
          "description"                   -> x.description.asJson,
          "payload"                       -> x.payload.asJson,
          "provider_token"                -> x.providerToken.asJson,
          "currency"                      -> x.currency.asJson,
          "prices"                        -> x.prices.asJson,
          "max_tip_amount"                -> x.maxTipAmount.asJson,
          "suggested_tip_amounts"         -> x.suggestedTipAmounts.asJson,
          "start_parameter"               -> x.startParameter.asJson,
          "provider_data"                 -> x.providerData.asJson,
          "photo_url"                     -> x.photoUrl.asJson,
          "photo_size"                    -> x.photoSize.asJson,
          "photo_width"                   -> x.photoWidth.asJson,
          "photo_height"                  -> x.photoHeight.asJson,
          "need_name"                     -> x.needName.asJson,
          "need_phone_number"             -> x.needPhoneNumber.asJson,
          "need_email"                    -> x.needEmail.asJson,
          "need_shipping_address"         -> x.needShippingAddress.asJson,
          "send_phone_number_to_provider" -> x.sendPhoneNumberToProvider.asJson,
          "send_email_to_provider"        -> x.sendEmailToProvider.asJson,
          "is_flexible"                   -> x.isFlexible.asJson,
          "disable_notification"          -> x.disableNotification.asJson,
          "protect_content"               -> x.protectContent.asJson,
          "reply_parameters"              -> x.replyParameters.asJson,
          "reply_markup"                  -> x.replyMarkup.asJson,
          "method"                        -> "sendInvoice".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendinvoicereqDecoder: Decoder[SendInvoiceReq] =
    Decoder.instance { h =>
      for {
        _chatId                    <- h.get[ChatId]("chat_id")
        _messageThreadId           <- h.get[Option[Int]]("message_thread_id")
        _title                     <- h.get[String]("title")
        _description               <- h.get[String]("description")
        _payload                   <- h.get[String]("payload")
        _providerToken             <- h.get[String]("provider_token")
        _currency                  <- h.get[String]("currency")
        _prices                    <- h.getOrElse[List[LabeledPrice]]("prices")(List.empty)
        _maxTipAmount              <- h.get[Option[Int]]("max_tip_amount")
        _suggestedTipAmounts       <- h.getOrElse[List[Int]]("suggested_tip_amounts")(List.empty)
        _startParameter            <- h.get[Option[String]]("start_parameter")
        _providerData              <- h.get[Option[String]]("provider_data")
        _photoUrl                  <- h.get[Option[String]]("photo_url")
        _photoSize                 <- h.get[Option[Long]]("photo_size")
        _photoWidth                <- h.get[Option[Int]]("photo_width")
        _photoHeight               <- h.get[Option[Int]]("photo_height")
        _needName                  <- h.get[Option[Boolean]]("need_name")
        _needPhoneNumber           <- h.get[Option[Boolean]]("need_phone_number")
        _needEmail                 <- h.get[Option[Boolean]]("need_email")
        _needShippingAddress       <- h.get[Option[Boolean]]("need_shipping_address")
        _sendPhoneNumberToProvider <- h.get[Option[Boolean]]("send_phone_number_to_provider")
        _sendEmailToProvider       <- h.get[Option[Boolean]]("send_email_to_provider")
        _isFlexible                <- h.get[Option[Boolean]]("is_flexible")
        _disableNotification       <- h.get[Option[Boolean]]("disable_notification")
        _protectContent            <- h.get[Option[Boolean]]("protect_content")
        _replyParameters           <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup               <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        SendInvoiceReq(
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          title = _title,
          description = _description,
          payload = _payload,
          providerToken = _providerToken,
          currency = _currency,
          prices = _prices,
          maxTipAmount = _maxTipAmount,
          suggestedTipAmounts = _suggestedTipAmounts,
          startParameter = _startParameter,
          providerData = _providerData,
          photoUrl = _photoUrl,
          photoSize = _photoSize,
          photoWidth = _photoWidth,
          photoHeight = _photoHeight,
          needName = _needName,
          needPhoneNumber = _needPhoneNumber,
          needEmail = _needEmail,
          needShippingAddress = _needShippingAddress,
          sendPhoneNumberToProvider = _sendPhoneNumberToProvider,
          sendEmailToProvider = _sendEmailToProvider,
          isFlexible = _isFlexible,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val setstickersettitlereqEncoder: Encoder[SetStickerSetTitleReq] =
    (x: SetStickerSetTitleReq) => {
      Json.fromFields(
        List(
          "name"   -> x.name.asJson,
          "title"  -> x.title.asJson,
          "method" -> "setStickerSetTitle".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setstickersettitlereqDecoder: Decoder[SetStickerSetTitleReq] =
    Decoder.instance { h =>
      for {
        _name  <- h.get[String]("name")
        _title <- h.get[String]("title")
      } yield {
        SetStickerSetTitleReq(name = _name, title = _title)
      }
    }

  implicit lazy val senddocumentreqEncoder: Encoder[SendDocumentReq] =
    (x: SendDocumentReq) => {
      Json.fromFields(
        List(
          "business_connection_id"         -> x.businessConnectionId.asJson,
          "chat_id"                        -> x.chatId.asJson,
          "message_thread_id"              -> x.messageThreadId.asJson,
          "document"                       -> x.document.asJson,
          "thumbnail"                      -> x.thumbnail.asJson,
          "caption"                        -> x.caption.asJson,
          "parse_mode"                     -> x.parseMode.asJson,
          "caption_entities"               -> x.captionEntities.asJson,
          "disable_content_type_detection" -> x.disableContentTypeDetection.asJson,
          "disable_notification"           -> x.disableNotification.asJson,
          "protect_content"                -> x.protectContent.asJson,
          "reply_parameters"               -> x.replyParameters.asJson,
          "reply_markup"                   -> x.replyMarkup.asJson,
          "method"                         -> "sendDocument".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val senddocumentreqDecoder: Decoder[SendDocumentReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId        <- h.get[Option[String]]("business_connection_id")
        _chatId                      <- h.get[ChatId]("chat_id")
        _messageThreadId             <- h.get[Option[Int]]("message_thread_id")
        _document                    <- h.get[IFile]("document")
        _thumbnail                   <- h.get[Option[IFile]]("thumbnail")
        _caption                     <- h.get[Option[String]]("caption")
        _parseMode                   <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities             <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _disableContentTypeDetection <- h.get[Option[Boolean]]("disable_content_type_detection")
        _disableNotification         <- h.get[Option[Boolean]]("disable_notification")
        _protectContent              <- h.get[Option[Boolean]]("protect_content")
        _replyParameters             <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup                 <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendDocumentReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          document = _document,
          thumbnail = _thumbnail,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          disableContentTypeDetection = _disableContentTypeDetection,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val deletemessagereqEncoder: Encoder[DeleteMessageReq] =
    (x: DeleteMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"    -> x.chatId.asJson,
          "message_id" -> x.messageId.asJson,
          "method"     -> "deleteMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deletemessagereqDecoder: Decoder[DeleteMessageReq] =
    Decoder.instance { h =>
      for {
        _chatId    <- h.get[ChatId]("chat_id")
        _messageId <- h.get[Int]("message_id")
      } yield {
        DeleteMessageReq(chatId = _chatId, messageId = _messageId)
      }
    }

  implicit lazy val answerinlinequeryreqEncoder: Encoder[AnswerInlineQueryReq] =
    (x: AnswerInlineQueryReq) => {
      Json.fromFields(
        List(
          "inline_query_id" -> x.inlineQueryId.asJson,
          "results"         -> x.results.asJson,
          "cache_time"      -> x.cacheTime.asJson,
          "is_personal"     -> x.isPersonal.asJson,
          "next_offset"     -> x.nextOffset.asJson,
          "button"          -> x.button.asJson,
          "method"          -> "answerInlineQuery".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val answerinlinequeryreqDecoder: Decoder[AnswerInlineQueryReq] =
    Decoder.instance { h =>
      for {
        _inlineQueryId <- h.get[String]("inline_query_id")
        _results       <- h.getOrElse[List[InlineQueryResult]]("results")(List.empty)
        _cacheTime     <- h.get[Option[Int]]("cache_time")
        _isPersonal    <- h.get[Option[Boolean]]("is_personal")
        _nextOffset    <- h.get[Option[String]]("next_offset")
        _button        <- h.get[Option[InlineQueryResultsButton]]("button")
      } yield {
        AnswerInlineQueryReq(
          inlineQueryId = _inlineQueryId,
          results = _results,
          cacheTime = _cacheTime,
          isPersonal = _isPersonal,
          nextOffset = _nextOffset,
          button = _button
        )
      }
    }

  implicit lazy val unbanchatsenderchatreqEncoder: Encoder[UnbanChatSenderChatReq] =
    (x: UnbanChatSenderChatReq) => {
      Json.fromFields(
        List(
          "chat_id"        -> x.chatId.asJson,
          "sender_chat_id" -> x.senderChatId.asJson,
          "method"         -> "unbanChatSenderChat".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val unbanchatsenderchatreqDecoder: Decoder[UnbanChatSenderChatReq] =
    Decoder.instance { h =>
      for {
        _chatId       <- h.get[ChatId]("chat_id")
        _senderChatId <- h.get[Long]("sender_chat_id")
      } yield {
        UnbanChatSenderChatReq(chatId = _chatId, senderChatId = _senderChatId)
      }
    }

  implicit lazy val closegeneralforumtopicreqEncoder: Encoder[CloseGeneralForumTopicReq] =
    (x: CloseGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "closeGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val closegeneralforumtopicreqDecoder: Decoder[CloseGeneralForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        CloseGeneralForumTopicReq(chatId = _chatId)
      }
    }

  implicit lazy val sendaudioreqEncoder: Encoder[SendAudioReq] =
    (x: SendAudioReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "audio"                  -> x.audio.asJson,
          "caption"                -> x.caption.asJson,
          "parse_mode"             -> x.parseMode.asJson,
          "caption_entities"       -> x.captionEntities.asJson,
          "duration"               -> x.duration.asJson,
          "performer"              -> x.performer.asJson,
          "title"                  -> x.title.asJson,
          "thumbnail"              -> x.thumbnail.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendAudio".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendaudioreqDecoder: Decoder[SendAudioReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _audio                <- h.get[IFile]("audio")
        _caption              <- h.get[Option[String]]("caption")
        _parseMode            <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities      <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _duration             <- h.get[Option[Int]]("duration")
        _performer            <- h.get[Option[String]]("performer")
        _title                <- h.get[Option[String]]("title")
        _thumbnail            <- h.get[Option[IFile]]("thumbnail")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendAudioReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          audio = _audio,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          duration = _duration,
          performer = _performer,
          title = _title,
          thumbnail = _thumbnail,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val restrictchatmemberreqEncoder: Encoder[RestrictChatMemberReq] =
    (x: RestrictChatMemberReq) => {
      Json.fromFields(
        List(
          "chat_id"                          -> x.chatId.asJson,
          "user_id"                          -> x.userId.asJson,
          "permissions"                      -> x.permissions.asJson,
          "use_independent_chat_permissions" -> x.useIndependentChatPermissions.asJson,
          "until_date"                       -> x.untilDate.asJson,
          "method"                           -> "restrictChatMember".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val restrictchatmemberreqDecoder: Decoder[RestrictChatMemberReq] =
    Decoder.instance { h =>
      for {
        _chatId                        <- h.get[ChatId]("chat_id")
        _userId                        <- h.get[Long]("user_id")
        _permissions                   <- h.get[ChatPermissions]("permissions")
        _useIndependentChatPermissions <- h.get[Option[Boolean]]("use_independent_chat_permissions")
        _untilDate                     <- h.get[Option[Int]]("until_date")
      } yield {
        RestrictChatMemberReq(
          chatId = _chatId,
          userId = _userId,
          permissions = _permissions,
          useIndependentChatPermissions = _useIndependentChatPermissions,
          untilDate = _untilDate
        )
      }
    }

  implicit lazy val setmyshortdescriptionreqEncoder: Encoder[SetMyShortDescriptionReq] =
    (x: SetMyShortDescriptionReq) => {
      Json.fromFields(
        List(
          "short_description" -> x.shortDescription.asJson,
          "language_code"     -> x.languageCode.asJson,
          "method"            -> "setMyShortDescription".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setmyshortdescriptionreqDecoder: Decoder[SetMyShortDescriptionReq] =
    Decoder.instance { h =>
      for {
        _shortDescription <- h.get[Option[String]]("short_description")
        _languageCode     <- h.get[Option[String]]("language_code")
      } yield {
        SetMyShortDescriptionReq(shortDescription = _shortDescription, languageCode = _languageCode)
      }
    }

  implicit lazy val getmereqEncoder: Encoder[GetMeReq.type] = (_: GetMeReq.type) => ().asJson
  implicit lazy val getmereqDecoder: Decoder[GetMeReq.type] = (_: HCursor) => Right(GetMeReq)

  implicit lazy val forwardmessagereqEncoder: Encoder[ForwardMessageReq] =
    (x: ForwardMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "message_thread_id"    -> x.messageThreadId.asJson,
          "from_chat_id"         -> x.fromChatId.asJson,
          "disable_notification" -> x.disableNotification.asJson,
          "protect_content"      -> x.protectContent.asJson,
          "message_id"           -> x.messageId.asJson,
          "method"               -> "forwardMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val forwardmessagereqDecoder: Decoder[ForwardMessageReq] =
    Decoder.instance { h =>
      for {
        _chatId              <- h.get[ChatId]("chat_id")
        _messageThreadId     <- h.get[Option[Int]]("message_thread_id")
        _fromChatId          <- h.get[ChatId]("from_chat_id")
        _disableNotification <- h.get[Option[Boolean]]("disable_notification")
        _protectContent      <- h.get[Option[Boolean]]("protect_content")
        _messageId           <- h.get[Int]("message_id")
      } yield {
        ForwardMessageReq(
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          fromChatId = _fromChatId,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          messageId = _messageId
        )
      }
    }

  implicit lazy val getchatmemberreqEncoder: Encoder[GetChatMemberReq] =
    (x: GetChatMemberReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "user_id" -> x.userId.asJson,
          "method"  -> "getChatMember".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getchatmemberreqDecoder: Decoder[GetChatMemberReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _userId <- h.get[Long]("user_id")
      } yield {
        GetChatMemberReq(chatId = _chatId, userId = _userId)
      }
    }

  implicit lazy val getmycommandsreqEncoder: Encoder[GetMyCommandsReq] =
    (x: GetMyCommandsReq) => {
      Json.fromFields(
        List(
          "scope"         -> x.scope.asJson,
          "language_code" -> x.languageCode.asJson,
          "method"        -> "getMyCommands".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getmycommandsreqDecoder: Decoder[GetMyCommandsReq] =
    Decoder.instance { h =>
      for {
        _scope        <- h.get[Option[BotCommandScope]]("scope")
        _languageCode <- h.get[Option[String]]("language_code")
      } yield {
        GetMyCommandsReq(scope = _scope, languageCode = _languageCode)
      }
    }

  implicit lazy val banchatmemberreqEncoder: Encoder[BanChatMemberReq] =
    (x: BanChatMemberReq) => {
      Json.fromFields(
        List(
          "chat_id"         -> x.chatId.asJson,
          "user_id"         -> x.userId.asJson,
          "until_date"      -> x.untilDate.asJson,
          "revoke_messages" -> x.revokeMessages.asJson,
          "method"          -> "banChatMember".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val banchatmemberreqDecoder: Decoder[BanChatMemberReq] =
    Decoder.instance { h =>
      for {
        _chatId         <- h.get[ChatId]("chat_id")
        _userId         <- h.get[Long]("user_id")
        _untilDate      <- h.get[Option[Int]]("until_date")
        _revokeMessages <- h.get[Option[Boolean]]("revoke_messages")
      } yield {
        BanChatMemberReq(chatId = _chatId, userId = _userId, untilDate = _untilDate, revokeMessages = _revokeMessages)
      }
    }

  implicit lazy val getchatadministratorsreqEncoder: Encoder[GetChatAdministratorsReq] =
    (x: GetChatAdministratorsReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "getChatAdministrators".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getchatadministratorsreqDecoder: Decoder[GetChatAdministratorsReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        GetChatAdministratorsReq(chatId = _chatId)
      }
    }

  implicit lazy val sendvoicereqEncoder: Encoder[SendVoiceReq] =
    (x: SendVoiceReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "voice"                  -> x.voice.asJson,
          "caption"                -> x.caption.asJson,
          "parse_mode"             -> x.parseMode.asJson,
          "caption_entities"       -> x.captionEntities.asJson,
          "duration"               -> x.duration.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendVoice".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvoicereqDecoder: Decoder[SendVoiceReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _voice                <- h.get[IFile]("voice")
        _caption              <- h.get[Option[String]]("caption")
        _parseMode            <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities      <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _duration             <- h.get[Option[Int]]("duration")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendVoiceReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          voice = _voice,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          duration = _duration,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val logoutreqEncoder: Encoder[LogOutReq.type] = (_: LogOutReq.type) => ().asJson
  implicit lazy val logoutreqDecoder: Decoder[LogOutReq.type] = (_: HCursor) => Right(LogOutReq)

  implicit lazy val promotechatmemberreqEncoder: Encoder[PromoteChatMemberReq] =
    (x: PromoteChatMemberReq) => {
      Json.fromFields(
        List(
          "chat_id"                -> x.chatId.asJson,
          "user_id"                -> x.userId.asJson,
          "is_anonymous"           -> x.isAnonymous.asJson,
          "can_manage_chat"        -> x.canManageChat.asJson,
          "can_delete_messages"    -> x.canDeleteMessages.asJson,
          "can_manage_video_chats" -> x.canManageVideoChats.asJson,
          "can_restrict_members"   -> x.canRestrictMembers.asJson,
          "can_promote_members"    -> x.canPromoteMembers.asJson,
          "can_change_info"        -> x.canChangeInfo.asJson,
          "can_invite_users"       -> x.canInviteUsers.asJson,
          "can_post_stories"       -> x.canPostStories.asJson,
          "can_edit_stories"       -> x.canEditStories.asJson,
          "can_delete_stories"     -> x.canDeleteStories.asJson,
          "can_post_messages"      -> x.canPostMessages.asJson,
          "can_edit_messages"      -> x.canEditMessages.asJson,
          "can_pin_messages"       -> x.canPinMessages.asJson,
          "can_manage_topics"      -> x.canManageTopics.asJson,
          "method"                 -> "promoteChatMember".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val promotechatmemberreqDecoder: Decoder[PromoteChatMemberReq] =
    Decoder.instance { h =>
      for {
        _chatId              <- h.get[ChatId]("chat_id")
        _userId              <- h.get[Long]("user_id")
        _isAnonymous         <- h.get[Option[Boolean]]("is_anonymous")
        _canManageChat       <- h.get[Option[Boolean]]("can_manage_chat")
        _canDeleteMessages   <- h.get[Option[Boolean]]("can_delete_messages")
        _canManageVideoChats <- h.get[Option[Boolean]]("can_manage_video_chats")
        _canRestrictMembers  <- h.get[Option[Boolean]]("can_restrict_members")
        _canPromoteMembers   <- h.get[Option[Boolean]]("can_promote_members")
        _canChangeInfo       <- h.get[Option[Boolean]]("can_change_info")
        _canInviteUsers      <- h.get[Option[Boolean]]("can_invite_users")
        _canPostStories      <- h.get[Option[Boolean]]("can_post_stories")
        _canEditStories      <- h.get[Option[Boolean]]("can_edit_stories")
        _canDeleteStories    <- h.get[Option[Boolean]]("can_delete_stories")
        _canPostMessages     <- h.get[Option[Boolean]]("can_post_messages")
        _canEditMessages     <- h.get[Option[Boolean]]("can_edit_messages")
        _canPinMessages      <- h.get[Option[Boolean]]("can_pin_messages")
        _canManageTopics     <- h.get[Option[Boolean]]("can_manage_topics")
      } yield {
        PromoteChatMemberReq(
          chatId = _chatId,
          userId = _userId,
          isAnonymous = _isAnonymous,
          canManageChat = _canManageChat,
          canDeleteMessages = _canDeleteMessages,
          canManageVideoChats = _canManageVideoChats,
          canRestrictMembers = _canRestrictMembers,
          canPromoteMembers = _canPromoteMembers,
          canChangeInfo = _canChangeInfo,
          canInviteUsers = _canInviteUsers,
          canPostStories = _canPostStories,
          canEditStories = _canEditStories,
          canDeleteStories = _canDeleteStories,
          canPostMessages = _canPostMessages,
          canEditMessages = _canEditMessages,
          canPinMessages = _canPinMessages,
          canManageTopics = _canManageTopics
        )
      }
    }

  implicit lazy val unpinallgeneralforumtopicmessagesreqEncoder: Encoder[UnpinAllGeneralForumTopicMessagesReq] =
    (x: UnpinAllGeneralForumTopicMessagesReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "unpinAllGeneralForumTopicMessages".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val unpinallgeneralforumtopicmessagesreqDecoder: Decoder[UnpinAllGeneralForumTopicMessagesReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        UnpinAllGeneralForumTopicMessagesReq(chatId = _chatId)
      }
    }

  implicit lazy val editmessagecaptionreqEncoder: Encoder[EditMessageCaptionReq] =
    (x: EditMessageCaptionReq) => {
      Json.fromFields(
        List(
          "chat_id"           -> x.chatId.asJson,
          "message_id"        -> x.messageId.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "caption"           -> x.caption.asJson,
          "parse_mode"        -> x.parseMode.asJson,
          "caption_entities"  -> x.captionEntities.asJson,
          "reply_markup"      -> x.replyMarkup.asJson,
          "method"            -> "editMessageCaption".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagecaptionreqDecoder: Decoder[EditMessageCaptionReq] =
    Decoder.instance { h =>
      for {
        _chatId          <- h.get[Option[ChatId]]("chat_id")
        _messageId       <- h.get[Option[Int]]("message_id")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
        _caption         <- h.get[Option[String]]("caption")
        _parseMode       <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup     <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        EditMessageCaptionReq(
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val editmessagemediareqEncoder: Encoder[EditMessageMediaReq] =
    (x: EditMessageMediaReq) => {
      Json.fromFields(
        List(
          "chat_id"           -> x.chatId.asJson,
          "message_id"        -> x.messageId.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "media"             -> x.media.asJson,
          "reply_markup"      -> x.replyMarkup.asJson,
          "method"            -> "editMessageMedia".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagemediareqDecoder: Decoder[EditMessageMediaReq] =
    Decoder.instance { h =>
      for {
        _chatId          <- h.get[Option[ChatId]]("chat_id")
        _messageId       <- h.get[Option[Int]]("message_id")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
        _media           <- h.get[InputMedia]("media")
        _replyMarkup     <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        EditMessageMediaReq(
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId,
          media = _media,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val setmydefaultadministratorrightsreqEncoder: Encoder[SetMyDefaultAdministratorRightsReq] =
    (x: SetMyDefaultAdministratorRightsReq) => {
      Json.fromFields(
        List(
          "rights"       -> x.rights.asJson,
          "for_channels" -> x.forChannels.asJson,
          "method"       -> "setMyDefaultAdministratorRights".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setmydefaultadministratorrightsreqDecoder: Decoder[SetMyDefaultAdministratorRightsReq] =
    Decoder.instance { h =>
      for {
        _rights      <- h.get[Option[ChatAdministratorRights]]("rights")
        _forChannels <- h.get[Option[Boolean]]("for_channels")
      } yield {
        SetMyDefaultAdministratorRightsReq(rights = _rights, forChannels = _forChannels)
      }
    }

  implicit lazy val deletemycommandsreqEncoder: Encoder[DeleteMyCommandsReq] =
    (x: DeleteMyCommandsReq) => {
      Json.fromFields(
        List(
          "scope"         -> x.scope.asJson,
          "language_code" -> x.languageCode.asJson,
          "method"        -> "deleteMyCommands".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deletemycommandsreqDecoder: Decoder[DeleteMyCommandsReq] =
    Decoder.instance { h =>
      for {
        _scope        <- h.get[Option[BotCommandScope]]("scope")
        _languageCode <- h.get[Option[String]]("language_code")
      } yield {
        DeleteMyCommandsReq(scope = _scope, languageCode = _languageCode)
      }
    }

  implicit lazy val pinchatmessagereqEncoder: Encoder[PinChatMessageReq] =
    (x: PinChatMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "message_id"           -> x.messageId.asJson,
          "disable_notification" -> x.disableNotification.asJson,
          "method"               -> "pinChatMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pinchatmessagereqDecoder: Decoder[PinChatMessageReq] =
    Decoder.instance { h =>
      for {
        _chatId              <- h.get[ChatId]("chat_id")
        _messageId           <- h.get[Int]("message_id")
        _disableNotification <- h.get[Option[Boolean]]("disable_notification")
      } yield {
        PinChatMessageReq(chatId = _chatId, messageId = _messageId, disableNotification = _disableNotification)
      }
    }

  implicit lazy val deletestickersetreqEncoder: Encoder[DeleteStickerSetReq] =
    (x: DeleteStickerSetReq) => {
      Json.fromFields(
        List(
          "name"   -> x.name.asJson,
          "method" -> "deleteStickerSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deletestickersetreqDecoder: Decoder[DeleteStickerSetReq] =
    Decoder.instance { h =>
      for {
        _name <- h.get[String]("name")
      } yield {
        DeleteStickerSetReq(name = _name)
      }
    }

  implicit lazy val editmessagereplymarkupreqEncoder: Encoder[EditMessageReplyMarkupReq] =
    (x: EditMessageReplyMarkupReq) => {
      Json.fromFields(
        List(
          "chat_id"           -> x.chatId.asJson,
          "message_id"        -> x.messageId.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "reply_markup"      -> x.replyMarkup.asJson,
          "method"            -> "editMessageReplyMarkup".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagereplymarkupreqDecoder: Decoder[EditMessageReplyMarkupReq] =
    Decoder.instance { h =>
      for {
        _chatId          <- h.get[Option[ChatId]]("chat_id")
        _messageId       <- h.get[Option[Int]]("message_id")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
        _replyMarkup     <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        EditMessageReplyMarkupReq(
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val createchatinvitelinkreqEncoder: Encoder[CreateChatInviteLinkReq] =
    (x: CreateChatInviteLinkReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "name"                 -> x.name.asJson,
          "expire_date"          -> x.expireDate.asJson,
          "member_limit"         -> x.memberLimit.asJson,
          "creates_join_request" -> x.createsJoinRequest.asJson,
          "method"               -> "createChatInviteLink".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val createchatinvitelinkreqDecoder: Decoder[CreateChatInviteLinkReq] =
    Decoder.instance { h =>
      for {
        _chatId             <- h.get[ChatId]("chat_id")
        _name               <- h.get[Option[String]]("name")
        _expireDate         <- h.get[Option[Int]]("expire_date")
        _memberLimit        <- h.get[Option[Int]]("member_limit")
        _createsJoinRequest <- h.get[Option[Boolean]]("creates_join_request")
      } yield {
        CreateChatInviteLinkReq(
          chatId = _chatId,
          name = _name,
          expireDate = _expireDate,
          memberLimit = _memberLimit,
          createsJoinRequest = _createsJoinRequest
        )
      }
    }

  implicit lazy val sendvideoreqEncoder: Encoder[SendVideoReq] =
    (x: SendVideoReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "video"                  -> x.video.asJson,
          "duration"               -> x.duration.asJson,
          "width"                  -> x.width.asJson,
          "height"                 -> x.height.asJson,
          "thumbnail"              -> x.thumbnail.asJson,
          "caption"                -> x.caption.asJson,
          "parse_mode"             -> x.parseMode.asJson,
          "caption_entities"       -> x.captionEntities.asJson,
          "has_spoiler"            -> x.hasSpoiler.asJson,
          "supports_streaming"     -> x.supportsStreaming.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendVideo".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvideoreqDecoder: Decoder[SendVideoReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _video                <- h.get[IFile]("video")
        _duration             <- h.get[Option[Int]]("duration")
        _width                <- h.get[Option[Int]]("width")
        _height               <- h.get[Option[Int]]("height")
        _thumbnail            <- h.get[Option[IFile]]("thumbnail")
        _caption              <- h.get[Option[String]]("caption")
        _parseMode            <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities      <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _hasSpoiler           <- h.get[Option[Boolean]]("has_spoiler")
        _supportsStreaming    <- h.get[Option[Boolean]]("supports_streaming")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendVideoReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          video = _video,
          duration = _duration,
          width = _width,
          height = _height,
          thumbnail = _thumbnail,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          hasSpoiler = _hasSpoiler,
          supportsStreaming = _supportsStreaming,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val setchatstickersetreqEncoder: Encoder[SetChatStickerSetReq] =
    (x: SetChatStickerSetReq) => {
      Json.fromFields(
        List(
          "chat_id"          -> x.chatId.asJson,
          "sticker_set_name" -> x.stickerSetName.asJson,
          "method"           -> "setChatStickerSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setchatstickersetreqDecoder: Decoder[SetChatStickerSetReq] =
    Decoder.instance { h =>
      for {
        _chatId         <- h.get[ChatId]("chat_id")
        _stickerSetName <- h.get[String]("sticker_set_name")
      } yield {
        SetChatStickerSetReq(chatId = _chatId, stickerSetName = _stickerSetName)
      }
    }

  implicit lazy val getchatreqEncoder: Encoder[GetChatReq] =
    (x: GetChatReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "getChat".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getchatreqDecoder: Decoder[GetChatReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        GetChatReq(chatId = _chatId)
      }
    }

  implicit lazy val deletewebhookreqEncoder: Encoder[DeleteWebhookReq] =
    (x: DeleteWebhookReq) => {
      Json.fromFields(
        List(
          "drop_pending_updates" -> x.dropPendingUpdates.asJson,
          "method"               -> "deleteWebhook".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val deletewebhookreqDecoder: Decoder[DeleteWebhookReq] =
    Decoder.instance { h =>
      for {
        _dropPendingUpdates <- h.get[Option[Boolean]]("drop_pending_updates")
      } yield {
        DeleteWebhookReq(dropPendingUpdates = _dropPendingUpdates)
      }
    }

  implicit lazy val revokechatinvitelinkreqEncoder: Encoder[RevokeChatInviteLinkReq] =
    (x: RevokeChatInviteLinkReq) => {
      Json.fromFields(
        List(
          "chat_id"     -> x.chatId.asJson,
          "invite_link" -> x.inviteLink.asJson,
          "method"      -> "revokeChatInviteLink".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val revokechatinvitelinkreqDecoder: Decoder[RevokeChatInviteLinkReq] =
    Decoder.instance { h =>
      for {
        _chatId     <- h.get[ChatId]("chat_id")
        _inviteLink <- h.get[String]("invite_link")
      } yield {
        RevokeChatInviteLinkReq(chatId = _chatId, inviteLink = _inviteLink)
      }
    }

  implicit lazy val closereqEncoder: Encoder[CloseReq.type] = (_: CloseReq.type) => ().asJson
  implicit lazy val closereqDecoder: Decoder[CloseReq.type] = (_: HCursor) => Right(CloseReq)

  implicit lazy val setstickerpositioninsetreqEncoder: Encoder[SetStickerPositionInSetReq] =
    (x: SetStickerPositionInSetReq) => {
      Json.fromFields(
        List(
          "sticker"  -> x.sticker.asJson,
          "position" -> x.position.asJson,
          "method"   -> "setStickerPositionInSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setstickerpositioninsetreqDecoder: Decoder[SetStickerPositionInSetReq] =
    Decoder.instance { h =>
      for {
        _sticker  <- h.get[String]("sticker")
        _position <- h.get[Int]("position")
      } yield {
        SetStickerPositionInSetReq(sticker = _sticker, position = _position)
      }
    }

  implicit lazy val setchatadministratorcustomtitlereqEncoder: Encoder[SetChatAdministratorCustomTitleReq] =
    (x: SetChatAdministratorCustomTitleReq) => {
      Json.fromFields(
        List(
          "chat_id"      -> x.chatId.asJson,
          "user_id"      -> x.userId.asJson,
          "custom_title" -> x.customTitle.asJson,
          "method"       -> "setChatAdministratorCustomTitle".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setchatadministratorcustomtitlereqDecoder: Decoder[SetChatAdministratorCustomTitleReq] =
    Decoder.instance { h =>
      for {
        _chatId      <- h.get[ChatId]("chat_id")
        _userId      <- h.get[Long]("user_id")
        _customTitle <- h.get[String]("custom_title")
      } yield {
        SetChatAdministratorCustomTitleReq(chatId = _chatId, userId = _userId, customTitle = _customTitle)
      }
    }

  implicit lazy val hidegeneralforumtopicreqEncoder: Encoder[HideGeneralForumTopicReq] =
    (x: HideGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "hideGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val hidegeneralforumtopicreqDecoder: Decoder[HideGeneralForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        HideGeneralForumTopicReq(chatId = _chatId)
      }
    }

  implicit lazy val closeforumtopicreqEncoder: Encoder[CloseForumTopicReq] =
    (x: CloseForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id"           -> x.chatId.asJson,
          "message_thread_id" -> x.messageThreadId.asJson,
          "method"            -> "closeForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val closeforumtopicreqDecoder: Decoder[CloseForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId          <- h.get[ChatId]("chat_id")
        _messageThreadId <- h.get[Int]("message_thread_id")
      } yield {
        CloseForumTopicReq(chatId = _chatId, messageThreadId = _messageThreadId)
      }
    }

  implicit lazy val getchatmenubuttonreqEncoder: Encoder[GetChatMenuButtonReq] =
    (x: GetChatMenuButtonReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "getChatMenuButton".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getchatmenubuttonreqDecoder: Decoder[GetChatMenuButtonReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[Option[Long]]("chat_id")
      } yield {
        GetChatMenuButtonReq(chatId = _chatId)
      }
    }

  implicit lazy val sendanimationreqEncoder: Encoder[SendAnimationReq] =
    (x: SendAnimationReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "animation"              -> x.animation.asJson,
          "duration"               -> x.duration.asJson,
          "width"                  -> x.width.asJson,
          "height"                 -> x.height.asJson,
          "thumbnail"              -> x.thumbnail.asJson,
          "caption"                -> x.caption.asJson,
          "parse_mode"             -> x.parseMode.asJson,
          "caption_entities"       -> x.captionEntities.asJson,
          "has_spoiler"            -> x.hasSpoiler.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendAnimation".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendanimationreqDecoder: Decoder[SendAnimationReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _animation            <- h.get[IFile]("animation")
        _duration             <- h.get[Option[Int]]("duration")
        _width                <- h.get[Option[Int]]("width")
        _height               <- h.get[Option[Int]]("height")
        _thumbnail            <- h.get[Option[IFile]]("thumbnail")
        _caption              <- h.get[Option[String]]("caption")
        _parseMode            <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities      <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _hasSpoiler           <- h.get[Option[Boolean]]("has_spoiler")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendAnimationReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          animation = _animation,
          duration = _duration,
          width = _width,
          height = _height,
          thumbnail = _thumbnail,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          hasSpoiler = _hasSpoiler,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val setmydescriptionreqEncoder: Encoder[SetMyDescriptionReq] =
    (x: SetMyDescriptionReq) => {
      Json.fromFields(
        List(
          "description"   -> x.description.asJson,
          "language_code" -> x.languageCode.asJson,
          "method"        -> "setMyDescription".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setmydescriptionreqDecoder: Decoder[SetMyDescriptionReq] =
    Decoder.instance { h =>
      for {
        _description  <- h.get[Option[String]]("description")
        _languageCode <- h.get[Option[String]]("language_code")
      } yield {
        SetMyDescriptionReq(description = _description, languageCode = _languageCode)
      }
    }

  implicit lazy val answershippingqueryreqEncoder: Encoder[AnswerShippingQueryReq] =
    (x: AnswerShippingQueryReq) => {
      Json.fromFields(
        List(
          "shipping_query_id" -> x.shippingQueryId.asJson,
          "ok"                -> x.ok.asJson,
          "shipping_options"  -> x.shippingOptions.asJson,
          "error_message"     -> x.errorMessage.asJson,
          "method"            -> "answerShippingQuery".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val answershippingqueryreqDecoder: Decoder[AnswerShippingQueryReq] =
    Decoder.instance { h =>
      for {
        _shippingQueryId <- h.get[String]("shipping_query_id")
        _ok              <- h.get[Boolean]("ok")
        _shippingOptions <- h.getOrElse[List[ShippingOption]]("shipping_options")(List.empty)
        _errorMessage    <- h.get[Option[String]]("error_message")
      } yield {
        AnswerShippingQueryReq(
          shippingQueryId = _shippingQueryId,
          ok = _ok,
          shippingOptions = _shippingOptions,
          errorMessage = _errorMessage
        )
      }
    }

  implicit lazy val setstickersetthumbnailreqEncoder: Encoder[SetStickerSetThumbnailReq] =
    (x: SetStickerSetThumbnailReq) => {
      Json.fromFields(
        List(
          "name"      -> x.name.asJson,
          "user_id"   -> x.userId.asJson,
          "thumbnail" -> x.thumbnail.asJson,
          "format"    -> x.format.asJson,
          "method"    -> "setStickerSetThumbnail".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setstickersetthumbnailreqDecoder: Decoder[SetStickerSetThumbnailReq] =
    Decoder.instance { h =>
      for {
        _name      <- h.get[String]("name")
        _userId    <- h.get[Long]("user_id")
        _thumbnail <- h.get[Option[IFile]]("thumbnail")
        _format    <- h.get[String]("format")
      } yield {
        SetStickerSetThumbnailReq(name = _name, userId = _userId, thumbnail = _thumbnail, format = _format)
      }
    }

  implicit lazy val approvechatjoinrequestreqEncoder: Encoder[ApproveChatJoinRequestReq] =
    (x: ApproveChatJoinRequestReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "user_id" -> x.userId.asJson,
          "method"  -> "approveChatJoinRequest".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val approvechatjoinrequestreqDecoder: Decoder[ApproveChatJoinRequestReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _userId <- h.get[Long]("user_id")
      } yield {
        ApproveChatJoinRequestReq(chatId = _chatId, userId = _userId)
      }
    }

  implicit lazy val answerprecheckoutqueryreqEncoder: Encoder[AnswerPreCheckoutQueryReq] =
    (x: AnswerPreCheckoutQueryReq) => {
      Json.fromFields(
        List(
          "pre_checkout_query_id" -> x.preCheckoutQueryId.asJson,
          "ok"                    -> x.ok.asJson,
          "error_message"         -> x.errorMessage.asJson,
          "method"                -> "answerPreCheckoutQuery".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val answerprecheckoutqueryreqDecoder: Decoder[AnswerPreCheckoutQueryReq] =
    Decoder.instance { h =>
      for {
        _preCheckoutQueryId <- h.get[String]("pre_checkout_query_id")
        _ok                 <- h.get[Boolean]("ok")
        _errorMessage       <- h.get[Option[String]]("error_message")
      } yield {
        AnswerPreCheckoutQueryReq(preCheckoutQueryId = _preCheckoutQueryId, ok = _ok, errorMessage = _errorMessage)
      }
    }

  implicit lazy val sendstickerreqEncoder: Encoder[SendStickerReq] =
    (x: SendStickerReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "sticker"                -> x.sticker.asJson,
          "emoji"                  -> x.emoji.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendSticker".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendstickerreqDecoder: Decoder[SendStickerReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _sticker              <- h.get[IFile]("sticker")
        _emoji                <- h.get[Option[String]]("emoji")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendStickerReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          sticker = _sticker,
          emoji = _emoji,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val setstickeremojilistreqEncoder: Encoder[SetStickerEmojiListReq] =
    (x: SetStickerEmojiListReq) => {
      Json.fromFields(
        List(
          "sticker"    -> x.sticker.asJson,
          "emoji_list" -> x.emojiList.asJson,
          "method"     -> "setStickerEmojiList".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setstickeremojilistreqDecoder: Decoder[SetStickerEmojiListReq] =
    Decoder.instance { h =>
      for {
        _sticker   <- h.get[String]("sticker")
        _emojiList <- h.getOrElse[List[String]]("emoji_list")(List.empty)
      } yield {
        SetStickerEmojiListReq(sticker = _sticker, emojiList = _emojiList)
      }
    }

  implicit lazy val editforumtopicreqEncoder: Encoder[EditForumTopicReq] =
    (x: EditForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "message_thread_id"    -> x.messageThreadId.asJson,
          "name"                 -> x.name.asJson,
          "icon_custom_emoji_id" -> x.iconCustomEmojiId.asJson,
          "method"               -> "editForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editforumtopicreqDecoder: Decoder[EditForumTopicReq] =
    Decoder.instance { h =>
      for {
        _chatId            <- h.get[ChatId]("chat_id")
        _messageThreadId   <- h.get[Int]("message_thread_id")
        _name              <- h.get[Option[String]]("name")
        _iconCustomEmojiId <- h.get[Option[String]]("icon_custom_emoji_id")
      } yield {
        EditForumTopicReq(
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          name = _name,
          iconCustomEmojiId = _iconCustomEmojiId
        )
      }
    }

  implicit lazy val setmynamereqEncoder: Encoder[SetMyNameReq] =
    (x: SetMyNameReq) => {
      Json.fromFields(
        List(
          "name"          -> x.name.asJson,
          "language_code" -> x.languageCode.asJson,
          "method"        -> "setMyName".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setmynamereqDecoder: Decoder[SetMyNameReq] =
    Decoder.instance { h =>
      for {
        _name         <- h.get[Option[String]]("name")
        _languageCode <- h.get[Option[String]]("language_code")
      } yield {
        SetMyNameReq(name = _name, languageCode = _languageCode)
      }
    }

  implicit lazy val forwardmessagesreqEncoder: Encoder[ForwardMessagesReq] =
    (x: ForwardMessagesReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "message_thread_id"    -> x.messageThreadId.asJson,
          "from_chat_id"         -> x.fromChatId.asJson,
          "message_ids"          -> x.messageIds.asJson,
          "disable_notification" -> x.disableNotification.asJson,
          "protect_content"      -> x.protectContent.asJson,
          "method"               -> "forwardMessages".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val forwardmessagesreqDecoder: Decoder[ForwardMessagesReq] =
    Decoder.instance { h =>
      for {
        _chatId              <- h.get[ChatId]("chat_id")
        _messageThreadId     <- h.get[Option[Int]]("message_thread_id")
        _fromChatId          <- h.get[ChatId]("from_chat_id")
        _messageIds          <- h.getOrElse[List[Int]]("message_ids")(List.empty)
        _disableNotification <- h.get[Option[Boolean]]("disable_notification")
        _protectContent      <- h.get[Option[Boolean]]("protect_content")
      } yield {
        ForwardMessagesReq(
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          fromChatId = _fromChatId,
          messageIds = _messageIds,
          disableNotification = _disableNotification,
          protectContent = _protectContent
        )
      }
    }

  implicit lazy val sendphotoreqEncoder: Encoder[SendPhotoReq] =
    (x: SendPhotoReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_thread_id"      -> x.messageThreadId.asJson,
          "photo"                  -> x.photo.asJson,
          "caption"                -> x.caption.asJson,
          "parse_mode"             -> x.parseMode.asJson,
          "caption_entities"       -> x.captionEntities.asJson,
          "has_spoiler"            -> x.hasSpoiler.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "protect_content"        -> x.protectContent.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendPhoto".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendphotoreqDecoder: Decoder[SendPhotoReq] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[Option[String]]("business_connection_id")
        _chatId               <- h.get[ChatId]("chat_id")
        _messageThreadId      <- h.get[Option[Int]]("message_thread_id")
        _photo                <- h.get[IFile]("photo")
        _caption              <- h.get[Option[String]]("caption")
        _parseMode            <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities      <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _hasSpoiler           <- h.get[Option[Boolean]]("has_spoiler")
        _disableNotification  <- h.get[Option[Boolean]]("disable_notification")
        _protectContent       <- h.get[Option[Boolean]]("protect_content")
        _replyParameters      <- h.get[Option[ReplyParameters]]("reply_parameters")
        _replyMarkup          <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendPhotoReq(
          businessConnectionId = _businessConnectionId,
          chatId = _chatId,
          messageThreadId = _messageThreadId,
          photo = _photo,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          hasSpoiler = _hasSpoiler,
          disableNotification = _disableNotification,
          protectContent = _protectContent,
          replyParameters = _replyParameters,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val getupdatesreqEncoder: Encoder[GetUpdatesReq] =
    (x: GetUpdatesReq) => {
      Json.fromFields(
        List(
          "offset"          -> x.offset.asJson,
          "limit"           -> x.limit.asJson,
          "timeout"         -> x.timeout.asJson,
          "allowed_updates" -> x.allowedUpdates.asJson,
          "method"          -> "getUpdates".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getupdatesreqDecoder: Decoder[GetUpdatesReq] =
    Decoder.instance { h =>
      for {
        _offset         <- h.get[Option[Int]]("offset")
        _limit          <- h.get[Option[Int]]("limit")
        _timeout        <- h.get[Option[Int]]("timeout")
        _allowedUpdates <- h.getOrElse[List[String]]("allowed_updates")(List.empty)
      } yield {
        GetUpdatesReq(offset = _offset, limit = _limit, timeout = _timeout, allowedUpdates = _allowedUpdates)
      }
    }

  implicit lazy val getstickersetreqEncoder: Encoder[GetStickerSetReq] =
    (x: GetStickerSetReq) => {
      Json.fromFields(
        List(
          "name"   -> x.name.asJson,
          "method" -> "getStickerSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getstickersetreqDecoder: Decoder[GetStickerSetReq] =
    Decoder.instance { h =>
      for {
        _name <- h.get[String]("name")
      } yield {
        GetStickerSetReq(name = _name)
      }
    }

  implicit lazy val setwebhookreqEncoder: Encoder[SetWebhookReq] =
    (x: SetWebhookReq) => {
      Json.fromFields(
        List(
          "url"                  -> x.url.asJson,
          "certificate"          -> x.certificate.asJson,
          "ip_address"           -> x.ipAddress.asJson,
          "max_connections"      -> x.maxConnections.asJson,
          "allowed_updates"      -> x.allowedUpdates.asJson,
          "drop_pending_updates" -> x.dropPendingUpdates.asJson,
          "secret_token"         -> x.secretToken.asJson,
          "method"               -> "setWebhook".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setwebhookreqDecoder: Decoder[SetWebhookReq] =
    Decoder.instance { h =>
      for {
        _url                <- h.get[String]("url")
        _certificate        <- h.get[Option[IFile]]("certificate")
        _ipAddress          <- h.get[Option[String]]("ip_address")
        _maxConnections     <- h.get[Option[Int]]("max_connections")
        _allowedUpdates     <- h.getOrElse[List[String]]("allowed_updates")(List.empty)
        _dropPendingUpdates <- h.get[Option[Boolean]]("drop_pending_updates")
        _secretToken        <- h.get[Option[String]]("secret_token")
      } yield {
        SetWebhookReq(
          url = _url,
          certificate = _certificate,
          ipAddress = _ipAddress,
          maxConnections = _maxConnections,
          allowedUpdates = _allowedUpdates,
          dropPendingUpdates = _dropPendingUpdates,
          secretToken = _secretToken
        )
      }
    }

  implicit lazy val setstickerkeywordsreqEncoder: Encoder[SetStickerKeywordsReq] =
    (x: SetStickerKeywordsReq) => {
      Json.fromFields(
        List(
          "sticker"  -> x.sticker.asJson,
          "keywords" -> x.keywords.asJson,
          "method"   -> "setStickerKeywords".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setstickerkeywordsreqDecoder: Decoder[SetStickerKeywordsReq] =
    Decoder.instance { h =>
      for {
        _sticker  <- h.get[String]("sticker")
        _keywords <- h.getOrElse[List[String]]("keywords")(List.empty)
      } yield {
        SetStickerKeywordsReq(sticker = _sticker, keywords = _keywords)
      }
    }

  implicit lazy val setmessagereactionreqEncoder: Encoder[SetMessageReactionReq] =
    (x: SetMessageReactionReq) => {
      Json.fromFields(
        List(
          "chat_id"    -> x.chatId.asJson,
          "message_id" -> x.messageId.asJson,
          "reaction"   -> x.reaction.asJson,
          "is_big"     -> x.isBig.asJson,
          "method"     -> "setMessageReaction".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setmessagereactionreqDecoder: Decoder[SetMessageReactionReq] =
    Decoder.instance { h =>
      for {
        _chatId    <- h.get[ChatId]("chat_id")
        _messageId <- h.get[Int]("message_id")
        _reaction  <- h.getOrElse[List[ReactionType]]("reaction")(List.empty)
        _isBig     <- h.get[Option[Boolean]]("is_big")
      } yield {
        SetMessageReactionReq(chatId = _chatId, messageId = _messageId, reaction = _reaction, isBig = _isBig)
      }
    }

}
