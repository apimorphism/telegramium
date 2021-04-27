package telegramium.bots.client

object uPickleImplicits {

  import upickle.default._

  implicit lazy val javaiofileCodec: ReadWriter[java.io.File] = {
    readwriter[upack.Msg].bimap(
      x => upack.Str(x.getName),
      msg => new java.io.File(readBinary[String](msg))
    )
  }
  import telegramium.bots.BotCommand
  import telegramium.bots.uPickleImplicits._
  import telegramium.bots.ChatId
  import telegramium.bots.IFile
  import telegramium.bots.ParseMode
  import telegramium.bots.MessageEntity
  import telegramium.bots.KeyboardMarkup
  import telegramium.bots.MaskPosition
  import telegramium.bots.ChatPermissions
  import telegramium.bots.InlineKeyboardMarkup
  import telegramium.bots.Emoji
  import telegramium.bots.InputMedia
  import telegramium.bots.PassportElementError
  import telegramium.bots.LabeledPrice
  import telegramium.bots.InlineQueryResult
  import telegramium.bots.ShippingOption

  implicit lazy val responseCodec: ReadWriter[Response] = {
    val okKey          = upack.Str("ok")
    val descriptionKey = upack.Str("description")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          okKey          -> writeMsg(x.ok),
          descriptionKey -> writeMsg(x.description)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          ok          <- m.get(okKey).map(x => readBinary[Boolean](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
        } yield {
          Response(
            ok = ok,
            description = description
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getwebhookinforeqCodec: ReadWriter[GetWebhookInfoReq.type] = macroRW

  implicit lazy val setmycommandsreqCodec: ReadWriter[SetMyCommandsReq] = {
    val commandsKey = upack.Str("commands")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          commandsKey -> writeMsg(x.commands)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          commands <- m.get(commandsKey).map(x => readBinary[List[BotCommand]](x))
        } yield {
          SetMyCommandsReq(
            commands = commands
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setchatphotoreqCodec: ReadWriter[SetChatPhotoReq] = {
    val chatIdKey = upack.Str("chatId")
    val photoKey  = upack.Str("photo")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId),
          photoKey  -> writeMsg(x.photo)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          photo  <- m.get(photoKey).map(x => readBinary[IFile](x))
        } yield {
          SetChatPhotoReq(
            chatId = chatId,
            photo = photo
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getgamehighscoresreqCodec: ReadWriter[GetGameHighScoresReq] = {
    val userIdKey          = upack.Str("userId")
    val chatIdKey          = upack.Str("chatId")
    val messageIdKey       = upack.Str("messageId")
    val inlineMessageIdKey = upack.Str("inlineMessageId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          userIdKey          -> writeMsg(x.userId),
          chatIdKey          -> writeMsg(x.chatId),
          messageIdKey       -> writeMsg(x.messageId),
          inlineMessageIdKey -> writeMsg(x.inlineMessageId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          userId          <- m.get(userIdKey).map(x => readBinary[Int](x))
          chatId          <- m.get(chatIdKey).map(x => readBinary[Option[Int]](x))
          messageId       <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
        } yield {
          GetGameHighScoresReq(
            userId = userId,
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val unpinallchatmessagesreqCodec: ReadWriter[UnpinAllChatMessagesReq] = {
    val chatIdKey = upack.Str("chatId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
        } yield {
          UnpinAllChatMessagesReq(
            chatId = chatId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val answercallbackqueryreqCodec: ReadWriter[AnswerCallbackQueryReq] = {
    val callbackQueryIdKey = upack.Str("callbackQueryId")
    val textKey            = upack.Str("text")
    val showAlertKey       = upack.Str("showAlert")
    val urlKey             = upack.Str("url")
    val cacheTimeKey       = upack.Str("cacheTime")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          callbackQueryIdKey -> writeMsg(x.callbackQueryId),
          textKey            -> writeMsg(x.text),
          showAlertKey       -> writeMsg(x.showAlert),
          urlKey             -> writeMsg(x.url),
          cacheTimeKey       -> writeMsg(x.cacheTime)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          callbackQueryId <- m.get(callbackQueryIdKey).map(x => readBinary[String](x))
          text            <- m.get(textKey).map(x => readBinary[Option[String]](x))
          showAlert       <- m.get(showAlertKey).map(x => readBinary[Option[Boolean]](x))
          url             <- m.get(urlKey).map(x => readBinary[Option[String]](x))
          cacheTime       <- m.get(cacheTimeKey).map(x => readBinary[Option[Int]](x))
        } yield {
          AnswerCallbackQueryReq(
            callbackQueryId = callbackQueryId,
            text = text,
            showAlert = showAlert,
            url = url,
            cacheTime = cacheTime
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendmessagereqCodec: ReadWriter[SendMessageReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val textKey                     = upack.Str("text")
    val parseModeKey                = upack.Str("parseMode")
    val entitiesKey                 = upack.Str("entities")
    val disableWebPagePreviewKey    = upack.Str("disableWebPagePreview")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          textKey                     -> writeMsg(x.text),
          parseModeKey                -> writeMsg(x.parseMode),
          entitiesKey                 -> writeMsg(x.entities),
          disableWebPagePreviewKey    -> writeMsg(x.disableWebPagePreview),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId    <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          text      <- m.get(textKey).map(x => readBinary[String](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          entities  <- m.get(entitiesKey).map(x => readBinary[List[MessageEntity]](x))
          disableWebPagePreview <- m
            .get(disableWebPagePreviewKey)
            .map(x => readBinary[Option[Boolean]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendMessageReq(
            chatId = chatId,
            text = text,
            parseMode = parseMode,
            entities = entities,
            disableWebPagePreview = disableWebPagePreview,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getuserprofilephotosreqCodec: ReadWriter[GetUserProfilePhotosReq] = {
    val userIdKey = upack.Str("userId")
    val offsetKey = upack.Str("offset")
    val limitKey  = upack.Str("limit")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          userIdKey -> writeMsg(x.userId),
          offsetKey -> writeMsg(x.offset),
          limitKey  -> writeMsg(x.limit)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          userId <- m.get(userIdKey).map(x => readBinary[Int](x))
          offset <- m.get(offsetKey).map(x => readBinary[Option[Int]](x))
          limit  <- m.get(limitKey).map(x => readBinary[Option[Int]](x))
        } yield {
          GetUserProfilePhotosReq(
            userId = userId,
            offset = offset,
            limit = limit
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendpollreqCodec: ReadWriter[SendPollReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val questionKey                 = upack.Str("question")
    val optionsKey                  = upack.Str("options")
    val isAnonymousKey              = upack.Str("isAnonymous")
    val typeKey                     = upack.Str("type")
    val allowsMultipleAnswersKey    = upack.Str("allowsMultipleAnswers")
    val correctOptionIdKey          = upack.Str("correctOptionId")
    val explanationKey              = upack.Str("explanation")
    val explanationParseModeKey     = upack.Str("explanationParseMode")
    val explanationEntitiesKey      = upack.Str("explanationEntities")
    val openPeriodKey               = upack.Str("openPeriod")
    val closeDateKey                = upack.Str("closeDate")
    val isClosedKey                 = upack.Str("isClosed")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          questionKey                 -> writeMsg(x.question),
          optionsKey                  -> writeMsg(x.options),
          isAnonymousKey              -> writeMsg(x.isAnonymous),
          typeKey                     -> writeMsg(x.`type`),
          allowsMultipleAnswersKey    -> writeMsg(x.allowsMultipleAnswers),
          correctOptionIdKey          -> writeMsg(x.correctOptionId),
          explanationKey              -> writeMsg(x.explanation),
          explanationParseModeKey     -> writeMsg(x.explanationParseMode),
          explanationEntitiesKey      -> writeMsg(x.explanationEntities),
          openPeriodKey               -> writeMsg(x.openPeriod),
          closeDateKey                -> writeMsg(x.closeDate),
          isClosedKey                 -> writeMsg(x.isClosed),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          question    <- m.get(questionKey).map(x => readBinary[String](x))
          options     <- m.get(optionsKey).map(x => readBinary[List[String]](x))
          isAnonymous <- m.get(isAnonymousKey).map(x => readBinary[Option[Boolean]](x))
          `type`      <- m.get(typeKey).map(x => readBinary[Option[String]](x))
          allowsMultipleAnswers <- m
            .get(allowsMultipleAnswersKey)
            .map(x => readBinary[Option[Boolean]](x))
          correctOptionId <- m.get(correctOptionIdKey).map(x => readBinary[Option[Int]](x))
          explanation     <- m.get(explanationKey).map(x => readBinary[Option[String]](x))
          explanationParseMode <- m
            .get(explanationParseModeKey)
            .map(x => readBinary[Option[String]](x))
          explanationEntities <- m
            .get(explanationEntitiesKey)
            .map(x => readBinary[List[MessageEntity]](x))
          openPeriod <- m.get(openPeriodKey).map(x => readBinary[Option[Int]](x))
          closeDate  <- m.get(closeDateKey).map(x => readBinary[Option[Int]](x))
          isClosed   <- m.get(isClosedKey).map(x => readBinary[Option[Boolean]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendPollReq(
            chatId = chatId,
            question = question,
            options = options,
            isAnonymous = isAnonymous,
            `type` = `type`,
            allowsMultipleAnswers = allowsMultipleAnswers,
            correctOptionId = correctOptionId,
            explanation = explanation,
            explanationParseMode = explanationParseMode,
            explanationEntities = explanationEntities,
            openPeriod = openPeriod,
            closeDate = closeDate,
            isClosed = isClosed,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendcontactreqCodec: ReadWriter[SendContactReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val phoneNumberKey              = upack.Str("phoneNumber")
    val firstNameKey                = upack.Str("firstName")
    val lastNameKey                 = upack.Str("lastName")
    val vcardKey                    = upack.Str("vcard")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          phoneNumberKey              -> writeMsg(x.phoneNumber),
          firstNameKey                -> writeMsg(x.firstName),
          lastNameKey                 -> writeMsg(x.lastName),
          vcardKey                    -> writeMsg(x.vcard),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          phoneNumber <- m.get(phoneNumberKey).map(x => readBinary[String](x))
          firstName   <- m.get(firstNameKey).map(x => readBinary[String](x))
          lastName    <- m.get(lastNameKey).map(x => readBinary[Option[String]](x))
          vcard       <- m.get(vcardKey).map(x => readBinary[Option[String]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendContactReq(
            chatId = chatId,
            phoneNumber = phoneNumber,
            firstName = firstName,
            lastName = lastName,
            vcard = vcard,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val createnewstickersetreqCodec: ReadWriter[CreateNewStickerSetReq] = {
    val userIdKey        = upack.Str("userId")
    val nameKey          = upack.Str("name")
    val titleKey         = upack.Str("title")
    val pngStickerKey    = upack.Str("pngSticker")
    val tgsStickerKey    = upack.Str("tgsSticker")
    val emojisKey        = upack.Str("emojis")
    val containsMasksKey = upack.Str("containsMasks")
    val maskPositionKey  = upack.Str("maskPosition")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          userIdKey        -> writeMsg(x.userId),
          nameKey          -> writeMsg(x.name),
          titleKey         -> writeMsg(x.title),
          pngStickerKey    -> writeMsg(x.pngSticker),
          tgsStickerKey    -> writeMsg(x.tgsSticker),
          emojisKey        -> writeMsg(x.emojis),
          containsMasksKey -> writeMsg(x.containsMasks),
          maskPositionKey  -> writeMsg(x.maskPosition)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          userId        <- m.get(userIdKey).map(x => readBinary[Int](x))
          name          <- m.get(nameKey).map(x => readBinary[String](x))
          title         <- m.get(titleKey).map(x => readBinary[String](x))
          pngSticker    <- m.get(pngStickerKey).map(x => readBinary[Option[IFile]](x))
          tgsSticker    <- m.get(tgsStickerKey).map(x => readBinary[Option[IFile]](x))
          emojis        <- m.get(emojisKey).map(x => readBinary[String](x))
          containsMasks <- m.get(containsMasksKey).map(x => readBinary[Option[Boolean]](x))
          maskPosition  <- m.get(maskPositionKey).map(x => readBinary[Option[MaskPosition]](x))
        } yield {
          CreateNewStickerSetReq(
            userId = userId,
            name = name,
            title = title,
            pngSticker = pngSticker,
            tgsSticker = tgsSticker,
            emojis = emojis,
            containsMasks = containsMasks,
            maskPosition = maskPosition
          )
        }
        result.get
      }
    )
  }

  implicit lazy val uploadstickerfilereqCodec: ReadWriter[UploadStickerFileReq] = {
    val userIdKey     = upack.Str("userId")
    val pngStickerKey = upack.Str("pngSticker")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          userIdKey     -> writeMsg(x.userId),
          pngStickerKey -> writeMsg(x.pngSticker)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          userId     <- m.get(userIdKey).map(x => readBinary[Int](x))
          pngSticker <- m.get(pngStickerKey).map(x => readBinary[IFile](x))
        } yield {
          UploadStickerFileReq(
            userId = userId,
            pngSticker = pngSticker
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setchatpermissionsreqCodec: ReadWriter[SetChatPermissionsReq] = {
    val chatIdKey      = upack.Str("chatId")
    val permissionsKey = upack.Str("permissions")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey      -> writeMsg(x.chatId),
          permissionsKey -> writeMsg(x.permissions)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          permissions <- m.get(permissionsKey).map(x => readBinary[ChatPermissions](x))
        } yield {
          SetChatPermissionsReq(
            chatId = chatId,
            permissions = permissions
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendlocationreqCodec: ReadWriter[SendLocationReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val latitudeKey                 = upack.Str("latitude")
    val longitudeKey                = upack.Str("longitude")
    val horizontalAccuracyKey       = upack.Str("horizontalAccuracy")
    val livePeriodKey               = upack.Str("livePeriod")
    val headingKey                  = upack.Str("heading")
    val proximityAlertRadiusKey     = upack.Str("proximityAlertRadius")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          latitudeKey                 -> writeMsg(x.latitude),
          longitudeKey                -> writeMsg(x.longitude),
          horizontalAccuracyKey       -> writeMsg(x.horizontalAccuracy),
          livePeriodKey               -> writeMsg(x.livePeriod),
          headingKey                  -> writeMsg(x.heading),
          proximityAlertRadiusKey     -> writeMsg(x.proximityAlertRadius),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId             <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          latitude           <- m.get(latitudeKey).map(x => readBinary[Float](x))
          longitude          <- m.get(longitudeKey).map(x => readBinary[Float](x))
          horizontalAccuracy <- m.get(horizontalAccuracyKey).map(x => readBinary[Option[Float]](x))
          livePeriod         <- m.get(livePeriodKey).map(x => readBinary[Option[Int]](x))
          heading            <- m.get(headingKey).map(x => readBinary[Option[Int]](x))
          proximityAlertRadius <- m
            .get(proximityAlertRadiusKey)
            .map(x => readBinary[Option[Int]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendLocationReq(
            chatId = chatId,
            latitude = latitude,
            longitude = longitude,
            horizontalAccuracy = horizontalAccuracy,
            livePeriod = livePeriod,
            heading = heading,
            proximityAlertRadius = proximityAlertRadius,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val deletechatstickersetreqCodec: ReadWriter[DeleteChatStickerSetReq] = {
    val chatIdKey = upack.Str("chatId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
        } yield {
          DeleteChatStickerSetReq(
            chatId = chatId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val stopmessagelivelocationreqCodec: ReadWriter[StopMessageLiveLocationReq] = {
    val chatIdKey          = upack.Str("chatId")
    val messageIdKey       = upack.Str("messageId")
    val inlineMessageIdKey = upack.Str("inlineMessageId")
    val replyMarkupKey     = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey          -> writeMsg(x.chatId),
          messageIdKey       -> writeMsg(x.messageId),
          inlineMessageIdKey -> writeMsg(x.inlineMessageId),
          replyMarkupKey     -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[Option[ChatId]](x))
          messageId       <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
          replyMarkup     <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          StopMessageLiveLocationReq(
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val exportchatinvitelinkreqCodec: ReadWriter[ExportChatInviteLinkReq] = {
    val chatIdKey = upack.Str("chatId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
        } yield {
          ExportChatInviteLinkReq(
            chatId = chatId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val senddicereqCodec: ReadWriter[SendDiceReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val emojiKey                    = upack.Str("emoji")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          emojiKey                    -> writeMsg(x.emoji),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          emoji  <- m.get(emojiKey).map(x => readBinary[Option[Emoji]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendDiceReq(
            chatId = chatId,
            emoji = emoji,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendchatactionreqCodec: ReadWriter[SendChatActionReq] = {
    val chatIdKey = upack.Str("chatId")
    val actionKey = upack.Str("action")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId),
          actionKey -> writeMsg(x.action)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          action <- m.get(actionKey).map(x => readBinary[String](x))
        } yield {
          SendChatActionReq(
            chatId = chatId,
            action = action
          )
        }
        result.get
      }
    )
  }

  implicit lazy val addstickertosetreqCodec: ReadWriter[AddStickerToSetReq] = {
    val userIdKey       = upack.Str("userId")
    val nameKey         = upack.Str("name")
    val pngStickerKey   = upack.Str("pngSticker")
    val tgsStickerKey   = upack.Str("tgsSticker")
    val emojisKey       = upack.Str("emojis")
    val maskPositionKey = upack.Str("maskPosition")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          userIdKey       -> writeMsg(x.userId),
          nameKey         -> writeMsg(x.name),
          pngStickerKey   -> writeMsg(x.pngSticker),
          tgsStickerKey   -> writeMsg(x.tgsSticker),
          emojisKey       -> writeMsg(x.emojis),
          maskPositionKey -> writeMsg(x.maskPosition)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          userId       <- m.get(userIdKey).map(x => readBinary[Int](x))
          name         <- m.get(nameKey).map(x => readBinary[String](x))
          pngSticker   <- m.get(pngStickerKey).map(x => readBinary[Option[IFile]](x))
          tgsSticker   <- m.get(tgsStickerKey).map(x => readBinary[Option[IFile]](x))
          emojis       <- m.get(emojisKey).map(x => readBinary[String](x))
          maskPosition <- m.get(maskPositionKey).map(x => readBinary[Option[MaskPosition]](x))
        } yield {
          AddStickerToSetReq(
            userId = userId,
            name = name,
            pngSticker = pngSticker,
            tgsSticker = tgsSticker,
            emojis = emojis,
            maskPosition = maskPosition
          )
        }
        result.get
      }
    )
  }

  implicit lazy val deletestickerfromsetreqCodec: ReadWriter[DeleteStickerFromSetReq] = {
    val stickerKey = upack.Str("sticker")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          stickerKey -> writeMsg(x.sticker)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          sticker <- m.get(stickerKey).map(x => readBinary[String](x))
        } yield {
          DeleteStickerFromSetReq(
            sticker = sticker
          )
        }
        result.get
      }
    )
  }

  implicit lazy val stoppollreqCodec: ReadWriter[StopPollReq] = {
    val chatIdKey      = upack.Str("chatId")
    val messageIdKey   = upack.Str("messageId")
    val replyMarkupKey = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey      -> writeMsg(x.chatId),
          messageIdKey   -> writeMsg(x.messageId),
          replyMarkupKey -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          messageId   <- m.get(messageIdKey).map(x => readBinary[Int](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          StopPollReq(
            chatId = chatId,
            messageId = messageId,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val unpinchatmessagereqCodec: ReadWriter[UnpinChatMessageReq] = {
    val chatIdKey    = upack.Str("chatId")
    val messageIdKey = upack.Str("messageId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey    -> writeMsg(x.chatId),
          messageIdKey -> writeMsg(x.messageId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId    <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          messageId <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
        } yield {
          UnpinChatMessageReq(
            chatId = chatId,
            messageId = messageId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendmediagroupreqCodec: ReadWriter[SendMediaGroupReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val mediaKey                    = upack.Str("media")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          mediaKey                    -> writeMsg(x.media),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          media  <- m.get(mediaKey).map(x => readBinary[List[InputMedia]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
        } yield {
          SendMediaGroupReq(
            chatId = chatId,
            media = media,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendgamereqCodec: ReadWriter[SendGameReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val gameShortNameKey            = upack.Str("gameShortName")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          gameShortNameKey            -> writeMsg(x.gameShortName),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId        <- m.get(chatIdKey).map(x => readBinary[Int](x))
          gameShortName <- m.get(gameShortNameKey).map(x => readBinary[String](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          SendGameReq(
            chatId = chatId,
            gameShortName = gameShortName,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendvenuereqCodec: ReadWriter[SendVenueReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val latitudeKey                 = upack.Str("latitude")
    val longitudeKey                = upack.Str("longitude")
    val titleKey                    = upack.Str("title")
    val addressKey                  = upack.Str("address")
    val foursquareIdKey             = upack.Str("foursquareId")
    val foursquareTypeKey           = upack.Str("foursquareType")
    val googlePlaceIdKey            = upack.Str("googlePlaceId")
    val googlePlaceTypeKey          = upack.Str("googlePlaceType")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          latitudeKey                 -> writeMsg(x.latitude),
          longitudeKey                -> writeMsg(x.longitude),
          titleKey                    -> writeMsg(x.title),
          addressKey                  -> writeMsg(x.address),
          foursquareIdKey             -> writeMsg(x.foursquareId),
          foursquareTypeKey           -> writeMsg(x.foursquareType),
          googlePlaceIdKey            -> writeMsg(x.googlePlaceId),
          googlePlaceTypeKey          -> writeMsg(x.googlePlaceType),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          latitude        <- m.get(latitudeKey).map(x => readBinary[Float](x))
          longitude       <- m.get(longitudeKey).map(x => readBinary[Float](x))
          title           <- m.get(titleKey).map(x => readBinary[String](x))
          address         <- m.get(addressKey).map(x => readBinary[String](x))
          foursquareId    <- m.get(foursquareIdKey).map(x => readBinary[Option[String]](x))
          foursquareType  <- m.get(foursquareTypeKey).map(x => readBinary[Option[String]](x))
          googlePlaceId   <- m.get(googlePlaceIdKey).map(x => readBinary[Option[String]](x))
          googlePlaceType <- m.get(googlePlaceTypeKey).map(x => readBinary[Option[String]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendVenueReq(
            chatId = chatId,
            latitude = latitude,
            longitude = longitude,
            title = title,
            address = address,
            foursquareId = foursquareId,
            foursquareType = foursquareType,
            googlePlaceId = googlePlaceId,
            googlePlaceType = googlePlaceType,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val unbanchatmemberreqCodec: ReadWriter[UnbanChatMemberReq] = {
    val chatIdKey       = upack.Str("chatId")
    val userIdKey       = upack.Str("userId")
    val onlyIfBannedKey = upack.Str("onlyIfBanned")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey       -> writeMsg(x.chatId),
          userIdKey       -> writeMsg(x.userId),
          onlyIfBannedKey -> writeMsg(x.onlyIfBanned)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId       <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          userId       <- m.get(userIdKey).map(x => readBinary[Int](x))
          onlyIfBanned <- m.get(onlyIfBannedKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          UnbanChatMemberReq(
            chatId = chatId,
            userId = userId,
            onlyIfBanned = onlyIfBanned
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setchatdescriptionreqCodec: ReadWriter[SetChatDescriptionReq] = {
    val chatIdKey      = upack.Str("chatId")
    val descriptionKey = upack.Str("description")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey      -> writeMsg(x.chatId),
          descriptionKey -> writeMsg(x.description)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
        } yield {
          SetChatDescriptionReq(
            chatId = chatId,
            description = description
          )
        }
        result.get
      }
    )
  }

  implicit lazy val editmessagetextreqCodec: ReadWriter[EditMessageTextReq] = {
    val chatIdKey                = upack.Str("chatId")
    val messageIdKey             = upack.Str("messageId")
    val inlineMessageIdKey       = upack.Str("inlineMessageId")
    val textKey                  = upack.Str("text")
    val parseModeKey             = upack.Str("parseMode")
    val entitiesKey              = upack.Str("entities")
    val disableWebPagePreviewKey = upack.Str("disableWebPagePreview")
    val replyMarkupKey           = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                -> writeMsg(x.chatId),
          messageIdKey             -> writeMsg(x.messageId),
          inlineMessageIdKey       -> writeMsg(x.inlineMessageId),
          textKey                  -> writeMsg(x.text),
          parseModeKey             -> writeMsg(x.parseMode),
          entitiesKey              -> writeMsg(x.entities),
          disableWebPagePreviewKey -> writeMsg(x.disableWebPagePreview),
          replyMarkupKey           -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[Option[ChatId]](x))
          messageId       <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
          text            <- m.get(textKey).map(x => readBinary[String](x))
          parseMode       <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          entities        <- m.get(entitiesKey).map(x => readBinary[List[MessageEntity]](x))
          disableWebPagePreview <- m
            .get(disableWebPagePreviewKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          EditMessageTextReq(
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            text = text,
            parseMode = parseMode,
            entities = entities,
            disableWebPagePreview = disableWebPagePreview,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val editmessagelivelocationreqCodec: ReadWriter[EditMessageLiveLocationReq] = {
    val chatIdKey               = upack.Str("chatId")
    val messageIdKey            = upack.Str("messageId")
    val inlineMessageIdKey      = upack.Str("inlineMessageId")
    val latitudeKey             = upack.Str("latitude")
    val longitudeKey            = upack.Str("longitude")
    val horizontalAccuracyKey   = upack.Str("horizontalAccuracy")
    val headingKey              = upack.Str("heading")
    val proximityAlertRadiusKey = upack.Str("proximityAlertRadius")
    val replyMarkupKey          = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey               -> writeMsg(x.chatId),
          messageIdKey            -> writeMsg(x.messageId),
          inlineMessageIdKey      -> writeMsg(x.inlineMessageId),
          latitudeKey             -> writeMsg(x.latitude),
          longitudeKey            -> writeMsg(x.longitude),
          horizontalAccuracyKey   -> writeMsg(x.horizontalAccuracy),
          headingKey              -> writeMsg(x.heading),
          proximityAlertRadiusKey -> writeMsg(x.proximityAlertRadius),
          replyMarkupKey          -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId             <- m.get(chatIdKey).map(x => readBinary[Option[ChatId]](x))
          messageId          <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
          inlineMessageId    <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
          latitude           <- m.get(latitudeKey).map(x => readBinary[Float](x))
          longitude          <- m.get(longitudeKey).map(x => readBinary[Float](x))
          horizontalAccuracy <- m.get(horizontalAccuracyKey).map(x => readBinary[Option[Float]](x))
          heading            <- m.get(headingKey).map(x => readBinary[Option[Int]](x))
          proximityAlertRadius <- m
            .get(proximityAlertRadiusKey)
            .map(x => readBinary[Option[Int]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          EditMessageLiveLocationReq(
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            latitude = latitude,
            longitude = longitude,
            horizontalAccuracy = horizontalAccuracy,
            heading = heading,
            proximityAlertRadius = proximityAlertRadius,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getfilereqCodec: ReadWriter[GetFileReq] = {
    val fileIdKey = upack.Str("fileId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
        } yield {
          GetFileReq(
            fileId = fileId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setgamescorereqCodec: ReadWriter[SetGameScoreReq] = {
    val userIdKey             = upack.Str("userId")
    val scoreKey              = upack.Str("score")
    val forceKey              = upack.Str("force")
    val disableEditMessageKey = upack.Str("disableEditMessage")
    val chatIdKey             = upack.Str("chatId")
    val messageIdKey          = upack.Str("messageId")
    val inlineMessageIdKey    = upack.Str("inlineMessageId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          userIdKey             -> writeMsg(x.userId),
          scoreKey              -> writeMsg(x.score),
          forceKey              -> writeMsg(x.force),
          disableEditMessageKey -> writeMsg(x.disableEditMessage),
          chatIdKey             -> writeMsg(x.chatId),
          messageIdKey          -> writeMsg(x.messageId),
          inlineMessageIdKey    -> writeMsg(x.inlineMessageId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          userId <- m.get(userIdKey).map(x => readBinary[Int](x))
          score  <- m.get(scoreKey).map(x => readBinary[Int](x))
          force  <- m.get(forceKey).map(x => readBinary[Option[Boolean]](x))
          disableEditMessage <- m
            .get(disableEditMessageKey)
            .map(x => readBinary[Option[Boolean]](x))
          chatId          <- m.get(chatIdKey).map(x => readBinary[Option[Int]](x))
          messageId       <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
        } yield {
          SetGameScoreReq(
            userId = userId,
            score = score,
            force = force,
            disableEditMessage = disableEditMessage,
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val leavechatreqCodec: ReadWriter[LeaveChatReq] = {
    val chatIdKey = upack.Str("chatId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
        } yield {
          LeaveChatReq(
            chatId = chatId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setchattitlereqCodec: ReadWriter[SetChatTitleReq] = {
    val chatIdKey = upack.Str("chatId")
    val titleKey  = upack.Str("title")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId),
          titleKey  -> writeMsg(x.title)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          title  <- m.get(titleKey).map(x => readBinary[String](x))
        } yield {
          SetChatTitleReq(
            chatId = chatId,
            title = title
          )
        }
        result.get
      }
    )
  }

  implicit lazy val copymessagereqCodec: ReadWriter[CopyMessageReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val fromChatIdKey               = upack.Str("fromChatId")
    val messageIdKey                = upack.Str("messageId")
    val captionKey                  = upack.Str("caption")
    val parseModeKey                = upack.Str("parseMode")
    val captionEntitiesKey          = upack.Str("captionEntities")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          fromChatIdKey               -> writeMsg(x.fromChatId),
          messageIdKey                -> writeMsg(x.messageId),
          captionKey                  -> writeMsg(x.caption),
          parseModeKey                -> writeMsg(x.parseMode),
          captionEntitiesKey          -> writeMsg(x.captionEntities),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          fromChatId      <- m.get(fromChatIdKey).map(x => readBinary[ChatId](x))
          messageId       <- m.get(messageIdKey).map(x => readBinary[Int](x))
          caption         <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode       <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          CopyMessageReq(
            chatId = chatId,
            fromChatId = fromChatId,
            messageId = messageId,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendvideonotereqCodec: ReadWriter[SendVideoNoteReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val videoNoteKey                = upack.Str("videoNote")
    val durationKey                 = upack.Str("duration")
    val lengthKey                   = upack.Str("length")
    val thumbKey                    = upack.Str("thumb")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          videoNoteKey                -> writeMsg(x.videoNote),
          durationKey                 -> writeMsg(x.duration),
          lengthKey                   -> writeMsg(x.length),
          thumbKey                    -> writeMsg(x.thumb),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId    <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          videoNote <- m.get(videoNoteKey).map(x => readBinary[IFile](x))
          duration  <- m.get(durationKey).map(x => readBinary[Option[Int]](x))
          length    <- m.get(lengthKey).map(x => readBinary[Option[Int]](x))
          thumb     <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendVideoNoteReq(
            chatId = chatId,
            videoNote = videoNote,
            duration = duration,
            length = length,
            thumb = thumb,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setpassportdataerrorsreqCodec: ReadWriter[SetPassportDataErrorsReq] = {
    val userIdKey = upack.Str("userId")
    val errorsKey = upack.Str("errors")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          userIdKey -> writeMsg(x.userId),
          errorsKey -> writeMsg(x.errors)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          userId <- m.get(userIdKey).map(x => readBinary[Int](x))
          errors <- m.get(errorsKey).map(x => readBinary[List[PassportElementError]](x))
        } yield {
          SetPassportDataErrorsReq(
            userId = userId,
            errors = errors
          )
        }
        result.get
      }
    )
  }

  implicit lazy val deletechatphotoreqCodec: ReadWriter[DeleteChatPhotoReq] = {
    val chatIdKey = upack.Str("chatId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
        } yield {
          DeleteChatPhotoReq(
            chatId = chatId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val editchatinvitelinkreqCodec: ReadWriter[EditChatInviteLinkReq] = {
    val chatIdKey      = upack.Str("chatId")
    val inviteLinkKey  = upack.Str("inviteLink")
    val expireDateKey  = upack.Str("expireDate")
    val memberLimitKey = upack.Str("memberLimit")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey      -> writeMsg(x.chatId),
          inviteLinkKey  -> writeMsg(x.inviteLink),
          expireDateKey  -> writeMsg(x.expireDate),
          memberLimitKey -> writeMsg(x.memberLimit)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          inviteLink  <- m.get(inviteLinkKey).map(x => readBinary[String](x))
          expireDate  <- m.get(expireDateKey).map(x => readBinary[Option[Int]](x))
          memberLimit <- m.get(memberLimitKey).map(x => readBinary[Option[Int]](x))
        } yield {
          EditChatInviteLinkReq(
            chatId = chatId,
            inviteLink = inviteLink,
            expireDate = expireDate,
            memberLimit = memberLimit
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendinvoicereqCodec: ReadWriter[SendInvoiceReq] = {
    val chatIdKey                    = upack.Str("chatId")
    val titleKey                     = upack.Str("title")
    val descriptionKey               = upack.Str("description")
    val payloadKey                   = upack.Str("payload")
    val providerTokenKey             = upack.Str("providerToken")
    val currencyKey                  = upack.Str("currency")
    val pricesKey                    = upack.Str("prices")
    val maxTipAmountKey              = upack.Str("maxTipAmount")
    val suggestedTipAmountsKey       = upack.Str("suggestedTipAmounts")
    val startParameterKey            = upack.Str("startParameter")
    val providerDataKey              = upack.Str("providerData")
    val photoUrlKey                  = upack.Str("photoUrl")
    val photoSizeKey                 = upack.Str("photoSize")
    val photoWidthKey                = upack.Str("photoWidth")
    val photoHeightKey               = upack.Str("photoHeight")
    val needNameKey                  = upack.Str("needName")
    val needPhoneNumberKey           = upack.Str("needPhoneNumber")
    val needEmailKey                 = upack.Str("needEmail")
    val needShippingAddressKey       = upack.Str("needShippingAddress")
    val sendPhoneNumberToProviderKey = upack.Str("sendPhoneNumberToProvider")
    val sendEmailToProviderKey       = upack.Str("sendEmailToProvider")
    val isFlexibleKey                = upack.Str("isFlexible")
    val disableNotificationKey       = upack.Str("disableNotification")
    val replyToMessageIdKey          = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey  = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey               = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                    -> writeMsg(x.chatId),
          titleKey                     -> writeMsg(x.title),
          descriptionKey               -> writeMsg(x.description),
          payloadKey                   -> writeMsg(x.payload),
          providerTokenKey             -> writeMsg(x.providerToken),
          currencyKey                  -> writeMsg(x.currency),
          pricesKey                    -> writeMsg(x.prices),
          maxTipAmountKey              -> writeMsg(x.maxTipAmount),
          suggestedTipAmountsKey       -> writeMsg(x.suggestedTipAmounts),
          startParameterKey            -> writeMsg(x.startParameter),
          providerDataKey              -> writeMsg(x.providerData),
          photoUrlKey                  -> writeMsg(x.photoUrl),
          photoSizeKey                 -> writeMsg(x.photoSize),
          photoWidthKey                -> writeMsg(x.photoWidth),
          photoHeightKey               -> writeMsg(x.photoHeight),
          needNameKey                  -> writeMsg(x.needName),
          needPhoneNumberKey           -> writeMsg(x.needPhoneNumber),
          needEmailKey                 -> writeMsg(x.needEmail),
          needShippingAddressKey       -> writeMsg(x.needShippingAddress),
          sendPhoneNumberToProviderKey -> writeMsg(x.sendPhoneNumberToProvider),
          sendEmailToProviderKey       -> writeMsg(x.sendEmailToProvider),
          isFlexibleKey                -> writeMsg(x.isFlexible),
          disableNotificationKey       -> writeMsg(x.disableNotification),
          replyToMessageIdKey          -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey  -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey               -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId              <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          title               <- m.get(titleKey).map(x => readBinary[String](x))
          description         <- m.get(descriptionKey).map(x => readBinary[String](x))
          payload             <- m.get(payloadKey).map(x => readBinary[String](x))
          providerToken       <- m.get(providerTokenKey).map(x => readBinary[String](x))
          currency            <- m.get(currencyKey).map(x => readBinary[String](x))
          prices              <- m.get(pricesKey).map(x => readBinary[List[LabeledPrice]](x))
          maxTipAmount        <- m.get(maxTipAmountKey).map(x => readBinary[Option[Int]](x))
          suggestedTipAmounts <- m.get(suggestedTipAmountsKey).map(x => readBinary[List[Int]](x))
          startParameter      <- m.get(startParameterKey).map(x => readBinary[Option[String]](x))
          providerData        <- m.get(providerDataKey).map(x => readBinary[Option[String]](x))
          photoUrl            <- m.get(photoUrlKey).map(x => readBinary[Option[String]](x))
          photoSize           <- m.get(photoSizeKey).map(x => readBinary[Option[Int]](x))
          photoWidth          <- m.get(photoWidthKey).map(x => readBinary[Option[Int]](x))
          photoHeight         <- m.get(photoHeightKey).map(x => readBinary[Option[Int]](x))
          needName            <- m.get(needNameKey).map(x => readBinary[Option[Boolean]](x))
          needPhoneNumber     <- m.get(needPhoneNumberKey).map(x => readBinary[Option[Boolean]](x))
          needEmail           <- m.get(needEmailKey).map(x => readBinary[Option[Boolean]](x))
          needShippingAddress <- m
            .get(needShippingAddressKey)
            .map(x => readBinary[Option[Boolean]](x))
          sendPhoneNumberToProvider <- m
            .get(sendPhoneNumberToProviderKey)
            .map(x => readBinary[Option[Boolean]](x))
          sendEmailToProvider <- m
            .get(sendEmailToProviderKey)
            .map(x => readBinary[Option[Boolean]](x))
          isFlexible <- m.get(isFlexibleKey).map(x => readBinary[Option[Boolean]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          SendInvoiceReq(
            chatId = chatId,
            title = title,
            description = description,
            payload = payload,
            providerToken = providerToken,
            currency = currency,
            prices = prices,
            maxTipAmount = maxTipAmount,
            suggestedTipAmounts = suggestedTipAmounts,
            startParameter = startParameter,
            providerData = providerData,
            photoUrl = photoUrl,
            photoSize = photoSize,
            photoWidth = photoWidth,
            photoHeight = photoHeight,
            needName = needName,
            needPhoneNumber = needPhoneNumber,
            needEmail = needEmail,
            needShippingAddress = needShippingAddress,
            sendPhoneNumberToProvider = sendPhoneNumberToProvider,
            sendEmailToProvider = sendEmailToProvider,
            isFlexible = isFlexible,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val senddocumentreqCodec: ReadWriter[SendDocumentReq] = {
    val chatIdKey                      = upack.Str("chatId")
    val documentKey                    = upack.Str("document")
    val thumbKey                       = upack.Str("thumb")
    val captionKey                     = upack.Str("caption")
    val parseModeKey                   = upack.Str("parseMode")
    val captionEntitiesKey             = upack.Str("captionEntities")
    val disableContentTypeDetectionKey = upack.Str("disableContentTypeDetection")
    val disableNotificationKey         = upack.Str("disableNotification")
    val replyToMessageIdKey            = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey    = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey                 = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                      -> writeMsg(x.chatId),
          documentKey                    -> writeMsg(x.document),
          thumbKey                       -> writeMsg(x.thumb),
          captionKey                     -> writeMsg(x.caption),
          parseModeKey                   -> writeMsg(x.parseMode),
          captionEntitiesKey             -> writeMsg(x.captionEntities),
          disableContentTypeDetectionKey -> writeMsg(x.disableContentTypeDetection),
          disableNotificationKey         -> writeMsg(x.disableNotification),
          replyToMessageIdKey            -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey    -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey                 -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          document        <- m.get(documentKey).map(x => readBinary[IFile](x))
          thumb           <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          caption         <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode       <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          disableContentTypeDetection <- m
            .get(disableContentTypeDetectionKey)
            .map(x => readBinary[Option[Boolean]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendDocumentReq(
            chatId = chatId,
            document = document,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            disableContentTypeDetection = disableContentTypeDetection,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val deletemessagereqCodec: ReadWriter[DeleteMessageReq] = {
    val chatIdKey    = upack.Str("chatId")
    val messageIdKey = upack.Str("messageId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey    -> writeMsg(x.chatId),
          messageIdKey -> writeMsg(x.messageId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId    <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          messageId <- m.get(messageIdKey).map(x => readBinary[Int](x))
        } yield {
          DeleteMessageReq(
            chatId = chatId,
            messageId = messageId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val answerinlinequeryreqCodec: ReadWriter[AnswerInlineQueryReq] = {
    val inlineQueryIdKey     = upack.Str("inlineQueryId")
    val resultsKey           = upack.Str("results")
    val cacheTimeKey         = upack.Str("cacheTime")
    val isPersonalKey        = upack.Str("isPersonal")
    val nextOffsetKey        = upack.Str("nextOffset")
    val switchPmTextKey      = upack.Str("switchPmText")
    val switchPmParameterKey = upack.Str("switchPmParameter")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          inlineQueryIdKey     -> writeMsg(x.inlineQueryId),
          resultsKey           -> writeMsg(x.results),
          cacheTimeKey         -> writeMsg(x.cacheTime),
          isPersonalKey        -> writeMsg(x.isPersonal),
          nextOffsetKey        -> writeMsg(x.nextOffset),
          switchPmTextKey      -> writeMsg(x.switchPmText),
          switchPmParameterKey -> writeMsg(x.switchPmParameter)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          inlineQueryId     <- m.get(inlineQueryIdKey).map(x => readBinary[String](x))
          results           <- m.get(resultsKey).map(x => readBinary[List[InlineQueryResult]](x))
          cacheTime         <- m.get(cacheTimeKey).map(x => readBinary[Option[Int]](x))
          isPersonal        <- m.get(isPersonalKey).map(x => readBinary[Option[Boolean]](x))
          nextOffset        <- m.get(nextOffsetKey).map(x => readBinary[Option[String]](x))
          switchPmText      <- m.get(switchPmTextKey).map(x => readBinary[Option[String]](x))
          switchPmParameter <- m.get(switchPmParameterKey).map(x => readBinary[Option[String]](x))
        } yield {
          AnswerInlineQueryReq(
            inlineQueryId = inlineQueryId,
            results = results,
            cacheTime = cacheTime,
            isPersonal = isPersonal,
            nextOffset = nextOffset,
            switchPmText = switchPmText,
            switchPmParameter = switchPmParameter
          )
        }
        result.get
      }
    )
  }

  implicit lazy val kickchatmemberreqCodec: ReadWriter[KickChatMemberReq] = {
    val chatIdKey         = upack.Str("chatId")
    val userIdKey         = upack.Str("userId")
    val untilDateKey      = upack.Str("untilDate")
    val revokeMessagesKey = upack.Str("revokeMessages")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey         -> writeMsg(x.chatId),
          userIdKey         -> writeMsg(x.userId),
          untilDateKey      -> writeMsg(x.untilDate),
          revokeMessagesKey -> writeMsg(x.revokeMessages)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId         <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          userId         <- m.get(userIdKey).map(x => readBinary[Int](x))
          untilDate      <- m.get(untilDateKey).map(x => readBinary[Option[Int]](x))
          revokeMessages <- m.get(revokeMessagesKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          KickChatMemberReq(
            chatId = chatId,
            userId = userId,
            untilDate = untilDate,
            revokeMessages = revokeMessages
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendaudioreqCodec: ReadWriter[SendAudioReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val audioKey                    = upack.Str("audio")
    val captionKey                  = upack.Str("caption")
    val parseModeKey                = upack.Str("parseMode")
    val captionEntitiesKey          = upack.Str("captionEntities")
    val durationKey                 = upack.Str("duration")
    val performerKey                = upack.Str("performer")
    val titleKey                    = upack.Str("title")
    val thumbKey                    = upack.Str("thumb")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          audioKey                    -> writeMsg(x.audio),
          captionKey                  -> writeMsg(x.caption),
          parseModeKey                -> writeMsg(x.parseMode),
          captionEntitiesKey          -> writeMsg(x.captionEntities),
          durationKey                 -> writeMsg(x.duration),
          performerKey                -> writeMsg(x.performer),
          titleKey                    -> writeMsg(x.title),
          thumbKey                    -> writeMsg(x.thumb),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          audio           <- m.get(audioKey).map(x => readBinary[IFile](x))
          caption         <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode       <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          duration        <- m.get(durationKey).map(x => readBinary[Option[Int]](x))
          performer       <- m.get(performerKey).map(x => readBinary[Option[String]](x))
          title           <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          thumb           <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendAudioReq(
            chatId = chatId,
            audio = audio,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            duration = duration,
            performer = performer,
            title = title,
            thumb = thumb,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val restrictchatmemberreqCodec: ReadWriter[RestrictChatMemberReq] = {
    val chatIdKey      = upack.Str("chatId")
    val userIdKey      = upack.Str("userId")
    val permissionsKey = upack.Str("permissions")
    val untilDateKey   = upack.Str("untilDate")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey      -> writeMsg(x.chatId),
          userIdKey      -> writeMsg(x.userId),
          permissionsKey -> writeMsg(x.permissions),
          untilDateKey   -> writeMsg(x.untilDate)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          userId      <- m.get(userIdKey).map(x => readBinary[Int](x))
          permissions <- m.get(permissionsKey).map(x => readBinary[ChatPermissions](x))
          untilDate   <- m.get(untilDateKey).map(x => readBinary[Option[Int]](x))
        } yield {
          RestrictChatMemberReq(
            chatId = chatId,
            userId = userId,
            permissions = permissions,
            untilDate = untilDate
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getmereqCodec: ReadWriter[GetMeReq.type] = macroRW

  implicit lazy val forwardmessagereqCodec: ReadWriter[ForwardMessageReq] = {
    val chatIdKey              = upack.Str("chatId")
    val fromChatIdKey          = upack.Str("fromChatId")
    val disableNotificationKey = upack.Str("disableNotification")
    val messageIdKey           = upack.Str("messageId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey              -> writeMsg(x.chatId),
          fromChatIdKey          -> writeMsg(x.fromChatId),
          disableNotificationKey -> writeMsg(x.disableNotification),
          messageIdKey           -> writeMsg(x.messageId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId     <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          fromChatId <- m.get(fromChatIdKey).map(x => readBinary[ChatId](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          messageId <- m.get(messageIdKey).map(x => readBinary[Int](x))
        } yield {
          ForwardMessageReq(
            chatId = chatId,
            fromChatId = fromChatId,
            disableNotification = disableNotification,
            messageId = messageId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getchatmemberreqCodec: ReadWriter[GetChatMemberReq] = {
    val chatIdKey = upack.Str("chatId")
    val userIdKey = upack.Str("userId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId),
          userIdKey -> writeMsg(x.userId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          userId <- m.get(userIdKey).map(x => readBinary[Int](x))
        } yield {
          GetChatMemberReq(
            chatId = chatId,
            userId = userId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getmycommandsreqCodec: ReadWriter[GetMyCommandsReq.type] = macroRW

  implicit lazy val getchatadministratorsreqCodec: ReadWriter[GetChatAdministratorsReq] = {
    val chatIdKey = upack.Str("chatId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
        } yield {
          GetChatAdministratorsReq(
            chatId = chatId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendvoicereqCodec: ReadWriter[SendVoiceReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val voiceKey                    = upack.Str("voice")
    val captionKey                  = upack.Str("caption")
    val parseModeKey                = upack.Str("parseMode")
    val captionEntitiesKey          = upack.Str("captionEntities")
    val durationKey                 = upack.Str("duration")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          voiceKey                    -> writeMsg(x.voice),
          captionKey                  -> writeMsg(x.caption),
          parseModeKey                -> writeMsg(x.parseMode),
          captionEntitiesKey          -> writeMsg(x.captionEntities),
          durationKey                 -> writeMsg(x.duration),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          voice           <- m.get(voiceKey).map(x => readBinary[IFile](x))
          caption         <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode       <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          duration        <- m.get(durationKey).map(x => readBinary[Option[Int]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendVoiceReq(
            chatId = chatId,
            voice = voice,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            duration = duration,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val logoutreqCodec: ReadWriter[LogOutReq.type] = macroRW

  implicit lazy val promotechatmemberreqCodec: ReadWriter[PromoteChatMemberReq] = {
    val chatIdKey              = upack.Str("chatId")
    val userIdKey              = upack.Str("userId")
    val isAnonymousKey         = upack.Str("isAnonymous")
    val canManageChatKey       = upack.Str("canManageChat")
    val canPostMessagesKey     = upack.Str("canPostMessages")
    val canEditMessagesKey     = upack.Str("canEditMessages")
    val canDeleteMessagesKey   = upack.Str("canDeleteMessages")
    val canManageVoiceChatsKey = upack.Str("canManageVoiceChats")
    val canRestrictMembersKey  = upack.Str("canRestrictMembers")
    val canPromoteMembersKey   = upack.Str("canPromoteMembers")
    val canChangeInfoKey       = upack.Str("canChangeInfo")
    val canInviteUsersKey      = upack.Str("canInviteUsers")
    val canPinMessagesKey      = upack.Str("canPinMessages")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey              -> writeMsg(x.chatId),
          userIdKey              -> writeMsg(x.userId),
          isAnonymousKey         -> writeMsg(x.isAnonymous),
          canManageChatKey       -> writeMsg(x.canManageChat),
          canPostMessagesKey     -> writeMsg(x.canPostMessages),
          canEditMessagesKey     -> writeMsg(x.canEditMessages),
          canDeleteMessagesKey   -> writeMsg(x.canDeleteMessages),
          canManageVoiceChatsKey -> writeMsg(x.canManageVoiceChats),
          canRestrictMembersKey  -> writeMsg(x.canRestrictMembers),
          canPromoteMembersKey   -> writeMsg(x.canPromoteMembers),
          canChangeInfoKey       -> writeMsg(x.canChangeInfo),
          canInviteUsersKey      -> writeMsg(x.canInviteUsers),
          canPinMessagesKey      -> writeMsg(x.canPinMessages)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId            <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          userId            <- m.get(userIdKey).map(x => readBinary[Int](x))
          isAnonymous       <- m.get(isAnonymousKey).map(x => readBinary[Option[Boolean]](x))
          canManageChat     <- m.get(canManageChatKey).map(x => readBinary[Option[Boolean]](x))
          canPostMessages   <- m.get(canPostMessagesKey).map(x => readBinary[Option[Boolean]](x))
          canEditMessages   <- m.get(canEditMessagesKey).map(x => readBinary[Option[Boolean]](x))
          canDeleteMessages <- m.get(canDeleteMessagesKey).map(x => readBinary[Option[Boolean]](x))
          canManageVoiceChats <- m
            .get(canManageVoiceChatsKey)
            .map(x => readBinary[Option[Boolean]](x))
          canRestrictMembers <- m
            .get(canRestrictMembersKey)
            .map(x => readBinary[Option[Boolean]](x))
          canPromoteMembers <- m.get(canPromoteMembersKey).map(x => readBinary[Option[Boolean]](x))
          canChangeInfo     <- m.get(canChangeInfoKey).map(x => readBinary[Option[Boolean]](x))
          canInviteUsers    <- m.get(canInviteUsersKey).map(x => readBinary[Option[Boolean]](x))
          canPinMessages    <- m.get(canPinMessagesKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          PromoteChatMemberReq(
            chatId = chatId,
            userId = userId,
            isAnonymous = isAnonymous,
            canManageChat = canManageChat,
            canPostMessages = canPostMessages,
            canEditMessages = canEditMessages,
            canDeleteMessages = canDeleteMessages,
            canManageVoiceChats = canManageVoiceChats,
            canRestrictMembers = canRestrictMembers,
            canPromoteMembers = canPromoteMembers,
            canChangeInfo = canChangeInfo,
            canInviteUsers = canInviteUsers,
            canPinMessages = canPinMessages
          )
        }
        result.get
      }
    )
  }

  implicit lazy val editmessagecaptionreqCodec: ReadWriter[EditMessageCaptionReq] = {
    val chatIdKey          = upack.Str("chatId")
    val messageIdKey       = upack.Str("messageId")
    val inlineMessageIdKey = upack.Str("inlineMessageId")
    val captionKey         = upack.Str("caption")
    val parseModeKey       = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey     = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey          -> writeMsg(x.chatId),
          messageIdKey       -> writeMsg(x.messageId),
          inlineMessageIdKey -> writeMsg(x.inlineMessageId),
          captionKey         -> writeMsg(x.caption),
          parseModeKey       -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey     -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[Option[ChatId]](x))
          messageId       <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
          caption         <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode       <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup     <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          EditMessageCaptionReq(
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val editmessagemediareqCodec: ReadWriter[EditMessageMediaReq] = {
    val chatIdKey          = upack.Str("chatId")
    val messageIdKey       = upack.Str("messageId")
    val inlineMessageIdKey = upack.Str("inlineMessageId")
    val mediaKey           = upack.Str("media")
    val replyMarkupKey     = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey          -> writeMsg(x.chatId),
          messageIdKey       -> writeMsg(x.messageId),
          inlineMessageIdKey -> writeMsg(x.inlineMessageId),
          mediaKey           -> writeMsg(x.media),
          replyMarkupKey     -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[Option[ChatId]](x))
          messageId       <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
          media           <- m.get(mediaKey).map(x => readBinary[InputMedia](x))
          replyMarkup     <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          EditMessageMediaReq(
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            media = media,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val pinchatmessagereqCodec: ReadWriter[PinChatMessageReq] = {
    val chatIdKey              = upack.Str("chatId")
    val messageIdKey           = upack.Str("messageId")
    val disableNotificationKey = upack.Str("disableNotification")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey              -> writeMsg(x.chatId),
          messageIdKey           -> writeMsg(x.messageId),
          disableNotificationKey -> writeMsg(x.disableNotification)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId    <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          messageId <- m.get(messageIdKey).map(x => readBinary[Int](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
        } yield {
          PinChatMessageReq(
            chatId = chatId,
            messageId = messageId,
            disableNotification = disableNotification
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setstickersetthumbreqCodec: ReadWriter[SetStickerSetThumbReq] = {
    val nameKey   = upack.Str("name")
    val userIdKey = upack.Str("userId")
    val thumbKey  = upack.Str("thumb")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey   -> writeMsg(x.name),
          userIdKey -> writeMsg(x.userId),
          thumbKey  -> writeMsg(x.thumb)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          name   <- m.get(nameKey).map(x => readBinary[String](x))
          userId <- m.get(userIdKey).map(x => readBinary[Int](x))
          thumb  <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
        } yield {
          SetStickerSetThumbReq(
            name = name,
            userId = userId,
            thumb = thumb
          )
        }
        result.get
      }
    )
  }

  implicit lazy val editmessagereplymarkupreqCodec: ReadWriter[EditMessageReplyMarkupReq] = {
    val chatIdKey          = upack.Str("chatId")
    val messageIdKey       = upack.Str("messageId")
    val inlineMessageIdKey = upack.Str("inlineMessageId")
    val replyMarkupKey     = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey          -> writeMsg(x.chatId),
          messageIdKey       -> writeMsg(x.messageId),
          inlineMessageIdKey -> writeMsg(x.inlineMessageId),
          replyMarkupKey     -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[Option[ChatId]](x))
          messageId       <- m.get(messageIdKey).map(x => readBinary[Option[Int]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
          replyMarkup     <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          EditMessageReplyMarkupReq(
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val createchatinvitelinkreqCodec: ReadWriter[CreateChatInviteLinkReq] = {
    val chatIdKey      = upack.Str("chatId")
    val expireDateKey  = upack.Str("expireDate")
    val memberLimitKey = upack.Str("memberLimit")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey      -> writeMsg(x.chatId),
          expireDateKey  -> writeMsg(x.expireDate),
          memberLimitKey -> writeMsg(x.memberLimit)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          expireDate  <- m.get(expireDateKey).map(x => readBinary[Option[Int]](x))
          memberLimit <- m.get(memberLimitKey).map(x => readBinary[Option[Int]](x))
        } yield {
          CreateChatInviteLinkReq(
            chatId = chatId,
            expireDate = expireDate,
            memberLimit = memberLimit
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendvideoreqCodec: ReadWriter[SendVideoReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val videoKey                    = upack.Str("video")
    val durationKey                 = upack.Str("duration")
    val widthKey                    = upack.Str("width")
    val heightKey                   = upack.Str("height")
    val thumbKey                    = upack.Str("thumb")
    val captionKey                  = upack.Str("caption")
    val parseModeKey                = upack.Str("parseMode")
    val captionEntitiesKey          = upack.Str("captionEntities")
    val supportsStreamingKey        = upack.Str("supportsStreaming")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          videoKey                    -> writeMsg(x.video),
          durationKey                 -> writeMsg(x.duration),
          widthKey                    -> writeMsg(x.width),
          heightKey                   -> writeMsg(x.height),
          thumbKey                    -> writeMsg(x.thumb),
          captionKey                  -> writeMsg(x.caption),
          parseModeKey                -> writeMsg(x.parseMode),
          captionEntitiesKey          -> writeMsg(x.captionEntities),
          supportsStreamingKey        -> writeMsg(x.supportsStreaming),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId            <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          video             <- m.get(videoKey).map(x => readBinary[IFile](x))
          duration          <- m.get(durationKey).map(x => readBinary[Option[Int]](x))
          width             <- m.get(widthKey).map(x => readBinary[Option[Int]](x))
          height            <- m.get(heightKey).map(x => readBinary[Option[Int]](x))
          thumb             <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          caption           <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode         <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities   <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          supportsStreaming <- m.get(supportsStreamingKey).map(x => readBinary[Option[Boolean]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendVideoReq(
            chatId = chatId,
            video = video,
            duration = duration,
            width = width,
            height = height,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            supportsStreaming = supportsStreaming,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setchatstickersetreqCodec: ReadWriter[SetChatStickerSetReq] = {
    val chatIdKey         = upack.Str("chatId")
    val stickerSetNameKey = upack.Str("stickerSetName")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey         -> writeMsg(x.chatId),
          stickerSetNameKey -> writeMsg(x.stickerSetName)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId         <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          stickerSetName <- m.get(stickerSetNameKey).map(x => readBinary[String](x))
        } yield {
          SetChatStickerSetReq(
            chatId = chatId,
            stickerSetName = stickerSetName
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getchatreqCodec: ReadWriter[GetChatReq] = {
    val chatIdKey = upack.Str("chatId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
        } yield {
          GetChatReq(
            chatId = chatId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val deletewebhookreqCodec: ReadWriter[DeleteWebhookReq] = {
    val dropPendingUpdatesKey = upack.Str("dropPendingUpdates")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          dropPendingUpdatesKey -> writeMsg(x.dropPendingUpdates)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          dropPendingUpdates <- m
            .get(dropPendingUpdatesKey)
            .map(x => readBinary[Option[Boolean]](x))
        } yield {
          DeleteWebhookReq(
            dropPendingUpdates = dropPendingUpdates
          )
        }
        result.get
      }
    )
  }

  implicit lazy val revokechatinvitelinkreqCodec: ReadWriter[RevokeChatInviteLinkReq] = {
    val chatIdKey     = upack.Str("chatId")
    val inviteLinkKey = upack.Str("inviteLink")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey     -> writeMsg(x.chatId),
          inviteLinkKey -> writeMsg(x.inviteLink)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId     <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          inviteLink <- m.get(inviteLinkKey).map(x => readBinary[String](x))
        } yield {
          RevokeChatInviteLinkReq(
            chatId = chatId,
            inviteLink = inviteLink
          )
        }
        result.get
      }
    )
  }

  implicit lazy val closereqCodec: ReadWriter[CloseReq.type] = macroRW

  implicit lazy val setstickerpositioninsetreqCodec: ReadWriter[SetStickerPositionInSetReq] = {
    val stickerKey  = upack.Str("sticker")
    val positionKey = upack.Str("position")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          stickerKey  -> writeMsg(x.sticker),
          positionKey -> writeMsg(x.position)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          sticker  <- m.get(stickerKey).map(x => readBinary[String](x))
          position <- m.get(positionKey).map(x => readBinary[Int](x))
        } yield {
          SetStickerPositionInSetReq(
            sticker = sticker,
            position = position
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setchatadministratorcustomtitlereqCodec
    : ReadWriter[SetChatAdministratorCustomTitleReq] = {
    val chatIdKey      = upack.Str("chatId")
    val userIdKey      = upack.Str("userId")
    val customTitleKey = upack.Str("customTitle")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey      -> writeMsg(x.chatId),
          userIdKey      -> writeMsg(x.userId),
          customTitleKey -> writeMsg(x.customTitle)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId      <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          userId      <- m.get(userIdKey).map(x => readBinary[Int](x))
          customTitle <- m.get(customTitleKey).map(x => readBinary[String](x))
        } yield {
          SetChatAdministratorCustomTitleReq(
            chatId = chatId,
            userId = userId,
            customTitle = customTitle
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendanimationreqCodec: ReadWriter[SendAnimationReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val animationKey                = upack.Str("animation")
    val durationKey                 = upack.Str("duration")
    val widthKey                    = upack.Str("width")
    val heightKey                   = upack.Str("height")
    val thumbKey                    = upack.Str("thumb")
    val captionKey                  = upack.Str("caption")
    val parseModeKey                = upack.Str("parseMode")
    val captionEntitiesKey          = upack.Str("captionEntities")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          animationKey                -> writeMsg(x.animation),
          durationKey                 -> writeMsg(x.duration),
          widthKey                    -> writeMsg(x.width),
          heightKey                   -> writeMsg(x.height),
          thumbKey                    -> writeMsg(x.thumb),
          captionKey                  -> writeMsg(x.caption),
          parseModeKey                -> writeMsg(x.parseMode),
          captionEntitiesKey          -> writeMsg(x.captionEntities),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          animation       <- m.get(animationKey).map(x => readBinary[IFile](x))
          duration        <- m.get(durationKey).map(x => readBinary[Option[Int]](x))
          width           <- m.get(widthKey).map(x => readBinary[Option[Int]](x))
          height          <- m.get(heightKey).map(x => readBinary[Option[Int]](x))
          thumb           <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          caption         <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode       <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendAnimationReq(
            chatId = chatId,
            animation = animation,
            duration = duration,
            width = width,
            height = height,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val answershippingqueryreqCodec: ReadWriter[AnswerShippingQueryReq] = {
    val shippingQueryIdKey = upack.Str("shippingQueryId")
    val okKey              = upack.Str("ok")
    val shippingOptionsKey = upack.Str("shippingOptions")
    val errorMessageKey    = upack.Str("errorMessage")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          shippingQueryIdKey -> writeMsg(x.shippingQueryId),
          okKey              -> writeMsg(x.ok),
          shippingOptionsKey -> writeMsg(x.shippingOptions),
          errorMessageKey    -> writeMsg(x.errorMessage)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          shippingQueryId <- m.get(shippingQueryIdKey).map(x => readBinary[String](x))
          ok              <- m.get(okKey).map(x => readBinary[Boolean](x))
          shippingOptions <- m.get(shippingOptionsKey).map(x => readBinary[List[ShippingOption]](x))
          errorMessage    <- m.get(errorMessageKey).map(x => readBinary[Option[String]](x))
        } yield {
          AnswerShippingQueryReq(
            shippingQueryId = shippingQueryId,
            ok = ok,
            shippingOptions = shippingOptions,
            errorMessage = errorMessage
          )
        }
        result.get
      }
    )
  }

  implicit lazy val answerprecheckoutqueryreqCodec: ReadWriter[AnswerPreCheckoutQueryReq] = {
    val preCheckoutQueryIdKey = upack.Str("preCheckoutQueryId")
    val okKey                 = upack.Str("ok")
    val errorMessageKey       = upack.Str("errorMessage")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          preCheckoutQueryIdKey -> writeMsg(x.preCheckoutQueryId),
          okKey                 -> writeMsg(x.ok),
          errorMessageKey       -> writeMsg(x.errorMessage)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          preCheckoutQueryId <- m.get(preCheckoutQueryIdKey).map(x => readBinary[String](x))
          ok                 <- m.get(okKey).map(x => readBinary[Boolean](x))
          errorMessage       <- m.get(errorMessageKey).map(x => readBinary[Option[String]](x))
        } yield {
          AnswerPreCheckoutQueryReq(
            preCheckoutQueryId = preCheckoutQueryId,
            ok = ok,
            errorMessage = errorMessage
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendstickerreqCodec: ReadWriter[SendStickerReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val stickerKey                  = upack.Str("sticker")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          stickerKey                  -> writeMsg(x.sticker),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId  <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          sticker <- m.get(stickerKey).map(x => readBinary[IFile](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendStickerReq(
            chatId = chatId,
            sticker = sticker,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getchatmemberscountreqCodec: ReadWriter[GetChatMembersCountReq] = {
    val chatIdKey = upack.Str("chatId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey -> writeMsg(x.chatId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
        } yield {
          GetChatMembersCountReq(
            chatId = chatId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val sendphotoreqCodec: ReadWriter[SendPhotoReq] = {
    val chatIdKey                   = upack.Str("chatId")
    val photoKey                    = upack.Str("photo")
    val captionKey                  = upack.Str("caption")
    val parseModeKey                = upack.Str("parseMode")
    val captionEntitiesKey          = upack.Str("captionEntities")
    val disableNotificationKey      = upack.Str("disableNotification")
    val replyToMessageIdKey         = upack.Str("replyToMessageId")
    val allowSendingWithoutReplyKey = upack.Str("allowSendingWithoutReply")
    val replyMarkupKey              = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatIdKey                   -> writeMsg(x.chatId),
          photoKey                    -> writeMsg(x.photo),
          captionKey                  -> writeMsg(x.caption),
          parseModeKey                -> writeMsg(x.parseMode),
          captionEntitiesKey          -> writeMsg(x.captionEntities),
          disableNotificationKey      -> writeMsg(x.disableNotification),
          replyToMessageIdKey         -> writeMsg(x.replyToMessageId),
          allowSendingWithoutReplyKey -> writeMsg(x.allowSendingWithoutReply),
          replyMarkupKey              -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chatId          <- m.get(chatIdKey).map(x => readBinary[ChatId](x))
          photo           <- m.get(photoKey).map(x => readBinary[IFile](x))
          caption         <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode       <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          disableNotification <- m
            .get(disableNotificationKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyToMessageId <- m.get(replyToMessageIdKey).map(x => readBinary[Option[Int]](x))
          allowSendingWithoutReply <- m
            .get(allowSendingWithoutReplyKey)
            .map(x => readBinary[Option[Boolean]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[KeyboardMarkup]](x))
        } yield {
          SendPhotoReq(
            chatId = chatId,
            photo = photo,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getupdatesreqCodec: ReadWriter[GetUpdatesReq] = {
    val offsetKey         = upack.Str("offset")
    val limitKey          = upack.Str("limit")
    val timeoutKey        = upack.Str("timeout")
    val allowedUpdatesKey = upack.Str("allowedUpdates")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey         -> writeMsg(x.offset),
          limitKey          -> writeMsg(x.limit),
          timeoutKey        -> writeMsg(x.timeout),
          allowedUpdatesKey -> writeMsg(x.allowedUpdates)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset         <- m.get(offsetKey).map(x => readBinary[Option[Int]](x))
          limit          <- m.get(limitKey).map(x => readBinary[Option[Int]](x))
          timeout        <- m.get(timeoutKey).map(x => readBinary[Option[Int]](x))
          allowedUpdates <- m.get(allowedUpdatesKey).map(x => readBinary[List[String]](x))
        } yield {
          GetUpdatesReq(
            offset = offset,
            limit = limit,
            timeout = timeout,
            allowedUpdates = allowedUpdates
          )
        }
        result.get
      }
    )
  }

  implicit lazy val getstickersetreqCodec: ReadWriter[GetStickerSetReq] = {
    val nameKey = upack.Str("name")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey -> writeMsg(x.name)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          name <- m.get(nameKey).map(x => readBinary[String](x))
        } yield {
          GetStickerSetReq(
            name = name
          )
        }
        result.get
      }
    )
  }

  implicit lazy val setwebhookreqCodec: ReadWriter[SetWebhookReq] = {
    val urlKey                = upack.Str("url")
    val certificateKey        = upack.Str("certificate")
    val ipAddressKey          = upack.Str("ipAddress")
    val maxConnectionsKey     = upack.Str("maxConnections")
    val allowedUpdatesKey     = upack.Str("allowedUpdates")
    val dropPendingUpdatesKey = upack.Str("dropPendingUpdates")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          urlKey                -> writeMsg(x.url),
          certificateKey        -> writeMsg(x.certificate),
          ipAddressKey          -> writeMsg(x.ipAddress),
          maxConnectionsKey     -> writeMsg(x.maxConnections),
          allowedUpdatesKey     -> writeMsg(x.allowedUpdates),
          dropPendingUpdatesKey -> writeMsg(x.dropPendingUpdates)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          url            <- m.get(urlKey).map(x => readBinary[String](x))
          certificate    <- m.get(certificateKey).map(x => readBinary[Option[IFile]](x))
          ipAddress      <- m.get(ipAddressKey).map(x => readBinary[Option[String]](x))
          maxConnections <- m.get(maxConnectionsKey).map(x => readBinary[Option[Int]](x))
          allowedUpdates <- m.get(allowedUpdatesKey).map(x => readBinary[List[String]](x))
          dropPendingUpdates <- m
            .get(dropPendingUpdatesKey)
            .map(x => readBinary[Option[Boolean]](x))
        } yield {
          SetWebhookReq(
            url = url,
            certificate = certificate,
            ipAddress = ipAddress,
            maxConnections = maxConnections,
            allowedUpdates = allowedUpdates,
            dropPendingUpdates = dropPendingUpdates
          )
        }
        result.get
      }
    )
  }

}

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Encoder, Decoder, Json}
  import io.circe.HCursor
  import telegramium.bots.BotCommand
  import telegramium.bots.CirceImplicits._
  import telegramium.bots.ChatId
  import telegramium.bots.IFile
  import telegramium.bots.ParseMode
  import telegramium.bots.MessageEntity
  import telegramium.bots.KeyboardMarkup
  import telegramium.bots.MaskPosition
  import telegramium.bots.ChatPermissions
  import telegramium.bots.InlineKeyboardMarkup
  import telegramium.bots.Emoji
  import telegramium.bots.InputMedia
  import telegramium.bots.PassportElementError
  import telegramium.bots.LabeledPrice
  import telegramium.bots.InlineQueryResult
  import telegramium.bots.ShippingOption

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

  implicit lazy val getwebhookinforeqEncoder: Encoder[GetWebhookInfoReq.type] =
    (_: GetWebhookInfoReq.type) => ().asJson
  implicit lazy val getwebhookinforeqDecoder: Decoder[GetWebhookInfoReq.type] = (_: HCursor) =>
    Right(GetWebhookInfoReq)
  implicit lazy val setmycommandsreqEncoder: Encoder[SetMyCommandsReq] =
    (x: SetMyCommandsReq) => {
      Json.fromFields(
        List(
          "commands" -> x.commands.asJson,
          "method"   -> "setMyCommands".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setmycommandsreqDecoder: Decoder[SetMyCommandsReq] =
    Decoder.instance { h =>
      for {
        _commands <- h.getOrElse[List[BotCommand]]("commands")(List.empty)
      } yield {
        SetMyCommandsReq(commands = _commands)
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
        _userId          <- h.get[Int]("user_id")
        _chatId          <- h.get[Option[Int]]("chat_id")
        _messageId       <- h.get[Option[Int]]("message_id")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
      } yield {
        GetGameHighScoresReq(userId = _userId,
                             chatId = _chatId,
                             messageId = _messageId,
                             inlineMessageId = _inlineMessageId)
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
        AnswerCallbackQueryReq(callbackQueryId = _callbackQueryId,
                               text = _text,
                               showAlert = _showAlert,
                               url = _url,
                               cacheTime = _cacheTime)
      }
    }

  implicit lazy val sendmessagereqEncoder: Encoder[SendMessageReq] =
    (x: SendMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "text"                        -> x.text.asJson,
          "parse_mode"                  -> x.parseMode.asJson,
          "entities"                    -> x.entities.asJson,
          "disable_web_page_preview"    -> x.disableWebPagePreview.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendmessagereqDecoder: Decoder[SendMessageReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _text                     <- h.get[String]("text")
        _parseMode                <- h.get[Option[ParseMode]]("parse_mode")
        _entities                 <- h.getOrElse[List[MessageEntity]]("entities")(List.empty)
        _disableWebPagePreview    <- h.get[Option[Boolean]]("disable_web_page_preview")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendMessageReq(
          chatId = _chatId,
          text = _text,
          parseMode = _parseMode,
          entities = _entities,
          disableWebPagePreview = _disableWebPagePreview,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
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
        _userId <- h.get[Int]("user_id")
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
          "chat_id"                     -> x.chatId.asJson,
          "question"                    -> x.question.asJson,
          "options"                     -> x.options.asJson,
          "is_anonymous"                -> x.isAnonymous.asJson,
          "type"                        -> x.`type`.asJson,
          "allows_multiple_answers"     -> x.allowsMultipleAnswers.asJson,
          "correct_option_id"           -> x.correctOptionId.asJson,
          "explanation"                 -> x.explanation.asJson,
          "explanation_parse_mode"      -> x.explanationParseMode.asJson,
          "explanation_entities"        -> x.explanationEntities.asJson,
          "open_period"                 -> x.openPeriod.asJson,
          "close_date"                  -> x.closeDate.asJson,
          "is_closed"                   -> x.isClosed.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendPoll".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendpollreqDecoder: Decoder[SendPollReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _question                 <- h.get[String]("question")
        _options                  <- h.getOrElse[List[String]]("options")(List.empty)
        _isAnonymous              <- h.get[Option[Boolean]]("is_anonymous")
        _type                     <- h.get[Option[String]]("type")
        _allowsMultipleAnswers    <- h.get[Option[Boolean]]("allows_multiple_answers")
        _correctOptionId          <- h.get[Option[Int]]("correct_option_id")
        _explanation              <- h.get[Option[String]]("explanation")
        _explanationParseMode     <- h.get[Option[String]]("explanation_parse_mode")
        _explanationEntities      <- h.getOrElse[List[MessageEntity]]("explanation_entities")(List.empty)
        _openPeriod               <- h.get[Option[Int]]("open_period")
        _closeDate                <- h.get[Option[Int]]("close_date")
        _isClosed                 <- h.get[Option[Boolean]]("is_closed")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendPollReq(
          chatId = _chatId,
          question = _question,
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
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val sendcontactreqEncoder: Encoder[SendContactReq] =
    (x: SendContactReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "phone_number"                -> x.phoneNumber.asJson,
          "first_name"                  -> x.firstName.asJson,
          "last_name"                   -> x.lastName.asJson,
          "vcard"                       -> x.vcard.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendContact".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendcontactreqDecoder: Decoder[SendContactReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _phoneNumber              <- h.get[String]("phone_number")
        _firstName                <- h.get[String]("first_name")
        _lastName                 <- h.get[Option[String]]("last_name")
        _vcard                    <- h.get[Option[String]]("vcard")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendContactReq(
          chatId = _chatId,
          phoneNumber = _phoneNumber,
          firstName = _firstName,
          lastName = _lastName,
          vcard = _vcard,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val createnewstickersetreqEncoder: Encoder[CreateNewStickerSetReq] =
    (x: CreateNewStickerSetReq) => {
      Json.fromFields(
        List(
          "user_id"        -> x.userId.asJson,
          "name"           -> x.name.asJson,
          "title"          -> x.title.asJson,
          "png_sticker"    -> x.pngSticker.asJson,
          "tgs_sticker"    -> x.tgsSticker.asJson,
          "emojis"         -> x.emojis.asJson,
          "contains_masks" -> x.containsMasks.asJson,
          "mask_position"  -> x.maskPosition.asJson,
          "method"         -> "createNewStickerSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val createnewstickersetreqDecoder: Decoder[CreateNewStickerSetReq] =
    Decoder.instance { h =>
      for {
        _userId        <- h.get[Int]("user_id")
        _name          <- h.get[String]("name")
        _title         <- h.get[String]("title")
        _pngSticker    <- h.get[Option[IFile]]("png_sticker")
        _tgsSticker    <- h.get[Option[IFile]]("tgs_sticker")
        _emojis        <- h.get[String]("emojis")
        _containsMasks <- h.get[Option[Boolean]]("contains_masks")
        _maskPosition  <- h.get[Option[MaskPosition]]("mask_position")
      } yield {
        CreateNewStickerSetReq(
          userId = _userId,
          name = _name,
          title = _title,
          pngSticker = _pngSticker,
          tgsSticker = _tgsSticker,
          emojis = _emojis,
          containsMasks = _containsMasks,
          maskPosition = _maskPosition
        )
      }
    }

  implicit lazy val uploadstickerfilereqEncoder: Encoder[UploadStickerFileReq] =
    (x: UploadStickerFileReq) => {
      Json.fromFields(
        List(
          "user_id"     -> x.userId.asJson,
          "png_sticker" -> x.pngSticker.asJson,
          "method"      -> "uploadStickerFile".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val uploadstickerfilereqDecoder: Decoder[UploadStickerFileReq] =
    Decoder.instance { h =>
      for {
        _userId     <- h.get[Int]("user_id")
        _pngSticker <- h.get[IFile]("png_sticker")
      } yield {
        UploadStickerFileReq(userId = _userId, pngSticker = _pngSticker)
      }
    }

  implicit lazy val setchatpermissionsreqEncoder: Encoder[SetChatPermissionsReq] =
    (x: SetChatPermissionsReq) => {
      Json.fromFields(
        List(
          "chat_id"     -> x.chatId.asJson,
          "permissions" -> x.permissions.asJson,
          "method"      -> "setChatPermissions".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setchatpermissionsreqDecoder: Decoder[SetChatPermissionsReq] =
    Decoder.instance { h =>
      for {
        _chatId      <- h.get[ChatId]("chat_id")
        _permissions <- h.get[ChatPermissions]("permissions")
      } yield {
        SetChatPermissionsReq(chatId = _chatId, permissions = _permissions)
      }
    }

  implicit lazy val sendlocationreqEncoder: Encoder[SendLocationReq] =
    (x: SendLocationReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "latitude"                    -> x.latitude.asJson,
          "longitude"                   -> x.longitude.asJson,
          "horizontal_accuracy"         -> x.horizontalAccuracy.asJson,
          "live_period"                 -> x.livePeriod.asJson,
          "heading"                     -> x.heading.asJson,
          "proximity_alert_radius"      -> x.proximityAlertRadius.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendLocation".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendlocationreqDecoder: Decoder[SendLocationReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _latitude                 <- h.get[Float]("latitude")
        _longitude                <- h.get[Float]("longitude")
        _horizontalAccuracy       <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod               <- h.get[Option[Int]]("live_period")
        _heading                  <- h.get[Option[Int]]("heading")
        _proximityAlertRadius     <- h.get[Option[Int]]("proximity_alert_radius")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendLocationReq(
          chatId = _chatId,
          latitude = _latitude,
          longitude = _longitude,
          horizontalAccuracy = _horizontalAccuracy,
          livePeriod = _livePeriod,
          heading = _heading,
          proximityAlertRadius = _proximityAlertRadius,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
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
        StopMessageLiveLocationReq(chatId = _chatId,
                                   messageId = _messageId,
                                   inlineMessageId = _inlineMessageId,
                                   replyMarkup = _replyMarkup)
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

  implicit lazy val senddicereqEncoder: Encoder[SendDiceReq] =
    (x: SendDiceReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "emoji"                       -> x.emoji.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendDice".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val senddicereqDecoder: Decoder[SendDiceReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _emoji                    <- h.get[Option[Emoji]]("emoji")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendDiceReq(
          chatId = _chatId,
          emoji = _emoji,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val sendchatactionreqEncoder: Encoder[SendChatActionReq] =
    (x: SendChatActionReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "action"  -> x.action.asJson,
          "method"  -> "sendChatAction".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendchatactionreqDecoder: Decoder[SendChatActionReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _action <- h.get[String]("action")
      } yield {
        SendChatActionReq(chatId = _chatId, action = _action)
      }
    }

  implicit lazy val addstickertosetreqEncoder: Encoder[AddStickerToSetReq] =
    (x: AddStickerToSetReq) => {
      Json.fromFields(
        List(
          "user_id"       -> x.userId.asJson,
          "name"          -> x.name.asJson,
          "png_sticker"   -> x.pngSticker.asJson,
          "tgs_sticker"   -> x.tgsSticker.asJson,
          "emojis"        -> x.emojis.asJson,
          "mask_position" -> x.maskPosition.asJson,
          "method"        -> "addStickerToSet".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val addstickertosetreqDecoder: Decoder[AddStickerToSetReq] =
    Decoder.instance { h =>
      for {
        _userId       <- h.get[Int]("user_id")
        _name         <- h.get[String]("name")
        _pngSticker   <- h.get[Option[IFile]]("png_sticker")
        _tgsSticker   <- h.get[Option[IFile]]("tgs_sticker")
        _emojis       <- h.get[String]("emojis")
        _maskPosition <- h.get[Option[MaskPosition]]("mask_position")
      } yield {
        AddStickerToSetReq(userId = _userId,
                           name = _name,
                           pngSticker = _pngSticker,
                           tgsSticker = _tgsSticker,
                           emojis = _emojis,
                           maskPosition = _maskPosition)
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

  implicit lazy val sendmediagroupreqEncoder: Encoder[SendMediaGroupReq] =
    (x: SendMediaGroupReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "media"                       -> x.media.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "method"                      -> "sendMediaGroup".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendmediagroupreqDecoder: Decoder[SendMediaGroupReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _media                    <- h.getOrElse[List[InputMedia]]("media")(List.empty)
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
      } yield {
        SendMediaGroupReq(
          chatId = _chatId,
          media = _media,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply
        )
      }
    }

  implicit lazy val sendgamereqEncoder: Encoder[SendGameReq] =
    (x: SendGameReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "game_short_name"             -> x.gameShortName.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendGame".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendgamereqDecoder: Decoder[SendGameReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[Int]("chat_id")
        _gameShortName            <- h.get[String]("game_short_name")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        SendGameReq(
          chatId = _chatId,
          gameShortName = _gameShortName,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val sendvenuereqEncoder: Encoder[SendVenueReq] =
    (x: SendVenueReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "latitude"                    -> x.latitude.asJson,
          "longitude"                   -> x.longitude.asJson,
          "title"                       -> x.title.asJson,
          "address"                     -> x.address.asJson,
          "foursquare_id"               -> x.foursquareId.asJson,
          "foursquare_type"             -> x.foursquareType.asJson,
          "google_place_id"             -> x.googlePlaceId.asJson,
          "google_place_type"           -> x.googlePlaceType.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendVenue".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvenuereqDecoder: Decoder[SendVenueReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _latitude                 <- h.get[Float]("latitude")
        _longitude                <- h.get[Float]("longitude")
        _title                    <- h.get[String]("title")
        _address                  <- h.get[String]("address")
        _foursquareId             <- h.get[Option[String]]("foursquare_id")
        _foursquareType           <- h.get[Option[String]]("foursquare_type")
        _googlePlaceId            <- h.get[Option[String]]("google_place_id")
        _googlePlaceType          <- h.get[Option[String]]("google_place_type")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendVenueReq(
          chatId = _chatId,
          latitude = _latitude,
          longitude = _longitude,
          title = _title,
          address = _address,
          foursquareId = _foursquareId,
          foursquareType = _foursquareType,
          googlePlaceId = _googlePlaceId,
          googlePlaceType = _googlePlaceType,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
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
        _userId       <- h.get[Int]("user_id")
        _onlyIfBanned <- h.get[Option[Boolean]]("only_if_banned")
      } yield {
        UnbanChatMemberReq(chatId = _chatId, userId = _userId, onlyIfBanned = _onlyIfBanned)
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
          "chat_id"                  -> x.chatId.asJson,
          "message_id"               -> x.messageId.asJson,
          "inline_message_id"        -> x.inlineMessageId.asJson,
          "text"                     -> x.text.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "entities"                 -> x.entities.asJson,
          "disable_web_page_preview" -> x.disableWebPagePreview.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "method"                   -> "editMessageText".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagetextreqDecoder: Decoder[EditMessageTextReq] =
    Decoder.instance { h =>
      for {
        _chatId                <- h.get[Option[ChatId]]("chat_id")
        _messageId             <- h.get[Option[Int]]("message_id")
        _inlineMessageId       <- h.get[Option[String]]("inline_message_id")
        _text                  <- h.get[String]("text")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _entities              <- h.getOrElse[List[MessageEntity]]("entities")(List.empty)
        _disableWebPagePreview <- h.get[Option[Boolean]]("disable_web_page_preview")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        EditMessageTextReq(
          chatId = _chatId,
          messageId = _messageId,
          inlineMessageId = _inlineMessageId,
          text = _text,
          parseMode = _parseMode,
          entities = _entities,
          disableWebPagePreview = _disableWebPagePreview,
          replyMarkup = _replyMarkup
        )
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
          horizontalAccuracy = _horizontalAccuracy,
          heading = _heading,
          proximityAlertRadius = _proximityAlertRadius,
          replyMarkup = _replyMarkup
        )
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
        _userId             <- h.get[Int]("user_id")
        _score              <- h.get[Int]("score")
        _force              <- h.get[Option[Boolean]]("force")
        _disableEditMessage <- h.get[Option[Boolean]]("disable_edit_message")
        _chatId             <- h.get[Option[Int]]("chat_id")
        _messageId          <- h.get[Option[Int]]("message_id")
        _inlineMessageId    <- h.get[Option[String]]("inline_message_id")
      } yield {
        SetGameScoreReq(userId = _userId,
                        score = _score,
                        force = _force,
                        disableEditMessage = _disableEditMessage,
                        chatId = _chatId,
                        messageId = _messageId,
                        inlineMessageId = _inlineMessageId)
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
          "chat_id"                     -> x.chatId.asJson,
          "from_chat_id"                -> x.fromChatId.asJson,
          "message_id"                  -> x.messageId.asJson,
          "caption"                     -> x.caption.asJson,
          "parse_mode"                  -> x.parseMode.asJson,
          "caption_entities"            -> x.captionEntities.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "copyMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val copymessagereqDecoder: Decoder[CopyMessageReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _fromChatId               <- h.get[ChatId]("from_chat_id")
        _messageId                <- h.get[Int]("message_id")
        _caption                  <- h.get[Option[String]]("caption")
        _parseMode                <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities          <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        CopyMessageReq(
          chatId = _chatId,
          fromChatId = _fromChatId,
          messageId = _messageId,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val sendvideonotereqEncoder: Encoder[SendVideoNoteReq] =
    (x: SendVideoNoteReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "video_note"                  -> x.videoNote.asJson,
          "duration"                    -> x.duration.asJson,
          "length"                      -> x.length.asJson,
          "thumb"                       -> x.thumb.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendVideoNote".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvideonotereqDecoder: Decoder[SendVideoNoteReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _videoNote                <- h.get[IFile]("video_note")
        _duration                 <- h.get[Option[Int]]("duration")
        _length                   <- h.get[Option[Int]]("length")
        _thumb                    <- h.get[Option[IFile]]("thumb")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendVideoNoteReq(
          chatId = _chatId,
          videoNote = _videoNote,
          duration = _duration,
          length = _length,
          thumb = _thumb,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
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
        _userId <- h.get[Int]("user_id")
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
          "chat_id"      -> x.chatId.asJson,
          "invite_link"  -> x.inviteLink.asJson,
          "expire_date"  -> x.expireDate.asJson,
          "member_limit" -> x.memberLimit.asJson,
          "method"       -> "editChatInviteLink".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editchatinvitelinkreqDecoder: Decoder[EditChatInviteLinkReq] =
    Decoder.instance { h =>
      for {
        _chatId      <- h.get[ChatId]("chat_id")
        _inviteLink  <- h.get[String]("invite_link")
        _expireDate  <- h.get[Option[Int]]("expire_date")
        _memberLimit <- h.get[Option[Int]]("member_limit")
      } yield {
        EditChatInviteLinkReq(chatId = _chatId,
                              inviteLink = _inviteLink,
                              expireDate = _expireDate,
                              memberLimit = _memberLimit)
      }
    }

  implicit lazy val sendinvoicereqEncoder: Encoder[SendInvoiceReq] =
    (x: SendInvoiceReq) => {
      Json.fromFields(
        List(
          "chat_id"                       -> x.chatId.asJson,
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
          "reply_to_message_id"           -> x.replyToMessageId.asJson,
          "allow_sending_without_reply"   -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                  -> x.replyMarkup.asJson,
          "method"                        -> "sendInvoice".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendinvoicereqDecoder: Decoder[SendInvoiceReq] =
    Decoder.instance { h =>
      for {
        _chatId                    <- h.get[ChatId]("chat_id")
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
        _photoSize                 <- h.get[Option[Int]]("photo_size")
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
        _replyToMessageId          <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply  <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup               <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        SendInvoiceReq(
          chatId = _chatId,
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
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val senddocumentreqEncoder: Encoder[SendDocumentReq] =
    (x: SendDocumentReq) => {
      Json.fromFields(
        List(
          "chat_id"                        -> x.chatId.asJson,
          "document"                       -> x.document.asJson,
          "thumb"                          -> x.thumb.asJson,
          "caption"                        -> x.caption.asJson,
          "parse_mode"                     -> x.parseMode.asJson,
          "caption_entities"               -> x.captionEntities.asJson,
          "disable_content_type_detection" -> x.disableContentTypeDetection.asJson,
          "disable_notification"           -> x.disableNotification.asJson,
          "reply_to_message_id"            -> x.replyToMessageId.asJson,
          "allow_sending_without_reply"    -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                   -> x.replyMarkup.asJson,
          "method"                         -> "sendDocument".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val senddocumentreqDecoder: Decoder[SendDocumentReq] =
    Decoder.instance { h =>
      for {
        _chatId                      <- h.get[ChatId]("chat_id")
        _document                    <- h.get[IFile]("document")
        _thumb                       <- h.get[Option[IFile]]("thumb")
        _caption                     <- h.get[Option[String]]("caption")
        _parseMode                   <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities             <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _disableContentTypeDetection <- h.get[Option[Boolean]]("disable_content_type_detection")
        _disableNotification         <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId            <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply    <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup                 <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendDocumentReq(
          chatId = _chatId,
          document = _document,
          thumb = _thumb,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          disableContentTypeDetection = _disableContentTypeDetection,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
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
          "inline_query_id"     -> x.inlineQueryId.asJson,
          "results"             -> x.results.asJson,
          "cache_time"          -> x.cacheTime.asJson,
          "is_personal"         -> x.isPersonal.asJson,
          "next_offset"         -> x.nextOffset.asJson,
          "switch_pm_text"      -> x.switchPmText.asJson,
          "switch_pm_parameter" -> x.switchPmParameter.asJson,
          "method"              -> "answerInlineQuery".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val answerinlinequeryreqDecoder: Decoder[AnswerInlineQueryReq] =
    Decoder.instance { h =>
      for {
        _inlineQueryId     <- h.get[String]("inline_query_id")
        _results           <- h.getOrElse[List[InlineQueryResult]]("results")(List.empty)
        _cacheTime         <- h.get[Option[Int]]("cache_time")
        _isPersonal        <- h.get[Option[Boolean]]("is_personal")
        _nextOffset        <- h.get[Option[String]]("next_offset")
        _switchPmText      <- h.get[Option[String]]("switch_pm_text")
        _switchPmParameter <- h.get[Option[String]]("switch_pm_parameter")
      } yield {
        AnswerInlineQueryReq(
          inlineQueryId = _inlineQueryId,
          results = _results,
          cacheTime = _cacheTime,
          isPersonal = _isPersonal,
          nextOffset = _nextOffset,
          switchPmText = _switchPmText,
          switchPmParameter = _switchPmParameter
        )
      }
    }

  implicit lazy val kickchatmemberreqEncoder: Encoder[KickChatMemberReq] =
    (x: KickChatMemberReq) => {
      Json.fromFields(
        List(
          "chat_id"         -> x.chatId.asJson,
          "user_id"         -> x.userId.asJson,
          "until_date"      -> x.untilDate.asJson,
          "revoke_messages" -> x.revokeMessages.asJson,
          "method"          -> "kickChatMember".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val kickchatmemberreqDecoder: Decoder[KickChatMemberReq] =
    Decoder.instance { h =>
      for {
        _chatId         <- h.get[ChatId]("chat_id")
        _userId         <- h.get[Int]("user_id")
        _untilDate      <- h.get[Option[Int]]("until_date")
        _revokeMessages <- h.get[Option[Boolean]]("revoke_messages")
      } yield {
        KickChatMemberReq(chatId = _chatId,
                          userId = _userId,
                          untilDate = _untilDate,
                          revokeMessages = _revokeMessages)
      }
    }

  implicit lazy val sendaudioreqEncoder: Encoder[SendAudioReq] =
    (x: SendAudioReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "audio"                       -> x.audio.asJson,
          "caption"                     -> x.caption.asJson,
          "parse_mode"                  -> x.parseMode.asJson,
          "caption_entities"            -> x.captionEntities.asJson,
          "duration"                    -> x.duration.asJson,
          "performer"                   -> x.performer.asJson,
          "title"                       -> x.title.asJson,
          "thumb"                       -> x.thumb.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendAudio".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendaudioreqDecoder: Decoder[SendAudioReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _audio                    <- h.get[IFile]("audio")
        _caption                  <- h.get[Option[String]]("caption")
        _parseMode                <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities          <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _duration                 <- h.get[Option[Int]]("duration")
        _performer                <- h.get[Option[String]]("performer")
        _title                    <- h.get[Option[String]]("title")
        _thumb                    <- h.get[Option[IFile]]("thumb")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendAudioReq(
          chatId = _chatId,
          audio = _audio,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          duration = _duration,
          performer = _performer,
          title = _title,
          thumb = _thumb,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val restrictchatmemberreqEncoder: Encoder[RestrictChatMemberReq] =
    (x: RestrictChatMemberReq) => {
      Json.fromFields(
        List(
          "chat_id"     -> x.chatId.asJson,
          "user_id"     -> x.userId.asJson,
          "permissions" -> x.permissions.asJson,
          "until_date"  -> x.untilDate.asJson,
          "method"      -> "restrictChatMember".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val restrictchatmemberreqDecoder: Decoder[RestrictChatMemberReq] =
    Decoder.instance { h =>
      for {
        _chatId      <- h.get[ChatId]("chat_id")
        _userId      <- h.get[Int]("user_id")
        _permissions <- h.get[ChatPermissions]("permissions")
        _untilDate   <- h.get[Option[Int]]("until_date")
      } yield {
        RestrictChatMemberReq(chatId = _chatId,
                              userId = _userId,
                              permissions = _permissions,
                              untilDate = _untilDate)
      }
    }

  implicit lazy val getmereqEncoder: Encoder[GetMeReq.type] = (_: GetMeReq.type) => ().asJson
  implicit lazy val getmereqDecoder: Decoder[GetMeReq.type] = (_: HCursor) => Right(GetMeReq)
  implicit lazy val forwardmessagereqEncoder: Encoder[ForwardMessageReq] =
    (x: ForwardMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "from_chat_id"         -> x.fromChatId.asJson,
          "disable_notification" -> x.disableNotification.asJson,
          "message_id"           -> x.messageId.asJson,
          "method"               -> "forwardMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val forwardmessagereqDecoder: Decoder[ForwardMessageReq] =
    Decoder.instance { h =>
      for {
        _chatId              <- h.get[ChatId]("chat_id")
        _fromChatId          <- h.get[ChatId]("from_chat_id")
        _disableNotification <- h.get[Option[Boolean]]("disable_notification")
        _messageId           <- h.get[Int]("message_id")
      } yield {
        ForwardMessageReq(chatId = _chatId,
                          fromChatId = _fromChatId,
                          disableNotification = _disableNotification,
                          messageId = _messageId)
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
        _userId <- h.get[Int]("user_id")
      } yield {
        GetChatMemberReq(chatId = _chatId, userId = _userId)
      }
    }

  implicit lazy val getmycommandsreqEncoder: Encoder[GetMyCommandsReq.type] =
    (_: GetMyCommandsReq.type) => ().asJson
  implicit lazy val getmycommandsreqDecoder: Decoder[GetMyCommandsReq.type] = (_: HCursor) =>
    Right(GetMyCommandsReq)
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
          "chat_id"                     -> x.chatId.asJson,
          "voice"                       -> x.voice.asJson,
          "caption"                     -> x.caption.asJson,
          "parse_mode"                  -> x.parseMode.asJson,
          "caption_entities"            -> x.captionEntities.asJson,
          "duration"                    -> x.duration.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendVoice".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvoicereqDecoder: Decoder[SendVoiceReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _voice                    <- h.get[IFile]("voice")
        _caption                  <- h.get[Option[String]]("caption")
        _parseMode                <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities          <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _duration                 <- h.get[Option[Int]]("duration")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendVoiceReq(
          chatId = _chatId,
          voice = _voice,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          duration = _duration,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
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
          "can_post_messages"      -> x.canPostMessages.asJson,
          "can_edit_messages"      -> x.canEditMessages.asJson,
          "can_delete_messages"    -> x.canDeleteMessages.asJson,
          "can_manage_voice_chats" -> x.canManageVoiceChats.asJson,
          "can_restrict_members"   -> x.canRestrictMembers.asJson,
          "can_promote_members"    -> x.canPromoteMembers.asJson,
          "can_change_info"        -> x.canChangeInfo.asJson,
          "can_invite_users"       -> x.canInviteUsers.asJson,
          "can_pin_messages"       -> x.canPinMessages.asJson,
          "method"                 -> "promoteChatMember".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val promotechatmemberreqDecoder: Decoder[PromoteChatMemberReq] =
    Decoder.instance { h =>
      for {
        _chatId              <- h.get[ChatId]("chat_id")
        _userId              <- h.get[Int]("user_id")
        _isAnonymous         <- h.get[Option[Boolean]]("is_anonymous")
        _canManageChat       <- h.get[Option[Boolean]]("can_manage_chat")
        _canPostMessages     <- h.get[Option[Boolean]]("can_post_messages")
        _canEditMessages     <- h.get[Option[Boolean]]("can_edit_messages")
        _canDeleteMessages   <- h.get[Option[Boolean]]("can_delete_messages")
        _canManageVoiceChats <- h.get[Option[Boolean]]("can_manage_voice_chats")
        _canRestrictMembers  <- h.get[Option[Boolean]]("can_restrict_members")
        _canPromoteMembers   <- h.get[Option[Boolean]]("can_promote_members")
        _canChangeInfo       <- h.get[Option[Boolean]]("can_change_info")
        _canInviteUsers      <- h.get[Option[Boolean]]("can_invite_users")
        _canPinMessages      <- h.get[Option[Boolean]]("can_pin_messages")
      } yield {
        PromoteChatMemberReq(
          chatId = _chatId,
          userId = _userId,
          isAnonymous = _isAnonymous,
          canManageChat = _canManageChat,
          canPostMessages = _canPostMessages,
          canEditMessages = _canEditMessages,
          canDeleteMessages = _canDeleteMessages,
          canManageVoiceChats = _canManageVoiceChats,
          canRestrictMembers = _canRestrictMembers,
          canPromoteMembers = _canPromoteMembers,
          canChangeInfo = _canChangeInfo,
          canInviteUsers = _canInviteUsers,
          canPinMessages = _canPinMessages
        )
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
        EditMessageMediaReq(chatId = _chatId,
                            messageId = _messageId,
                            inlineMessageId = _inlineMessageId,
                            media = _media,
                            replyMarkup = _replyMarkup)
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
        PinChatMessageReq(chatId = _chatId,
                          messageId = _messageId,
                          disableNotification = _disableNotification)
      }
    }

  implicit lazy val setstickersetthumbreqEncoder: Encoder[SetStickerSetThumbReq] =
    (x: SetStickerSetThumbReq) => {
      Json.fromFields(
        List(
          "name"    -> x.name.asJson,
          "user_id" -> x.userId.asJson,
          "thumb"   -> x.thumb.asJson,
          "method"  -> "setStickerSetThumb".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val setstickersetthumbreqDecoder: Decoder[SetStickerSetThumbReq] =
    Decoder.instance { h =>
      for {
        _name   <- h.get[String]("name")
        _userId <- h.get[Int]("user_id")
        _thumb  <- h.get[Option[IFile]]("thumb")
      } yield {
        SetStickerSetThumbReq(name = _name, userId = _userId, thumb = _thumb)
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
        EditMessageReplyMarkupReq(chatId = _chatId,
                                  messageId = _messageId,
                                  inlineMessageId = _inlineMessageId,
                                  replyMarkup = _replyMarkup)
      }
    }

  implicit lazy val createchatinvitelinkreqEncoder: Encoder[CreateChatInviteLinkReq] =
    (x: CreateChatInviteLinkReq) => {
      Json.fromFields(
        List(
          "chat_id"      -> x.chatId.asJson,
          "expire_date"  -> x.expireDate.asJson,
          "member_limit" -> x.memberLimit.asJson,
          "method"       -> "createChatInviteLink".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val createchatinvitelinkreqDecoder: Decoder[CreateChatInviteLinkReq] =
    Decoder.instance { h =>
      for {
        _chatId      <- h.get[ChatId]("chat_id")
        _expireDate  <- h.get[Option[Int]]("expire_date")
        _memberLimit <- h.get[Option[Int]]("member_limit")
      } yield {
        CreateChatInviteLinkReq(chatId = _chatId,
                                expireDate = _expireDate,
                                memberLimit = _memberLimit)
      }
    }

  implicit lazy val sendvideoreqEncoder: Encoder[SendVideoReq] =
    (x: SendVideoReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "video"                       -> x.video.asJson,
          "duration"                    -> x.duration.asJson,
          "width"                       -> x.width.asJson,
          "height"                      -> x.height.asJson,
          "thumb"                       -> x.thumb.asJson,
          "caption"                     -> x.caption.asJson,
          "parse_mode"                  -> x.parseMode.asJson,
          "caption_entities"            -> x.captionEntities.asJson,
          "supports_streaming"          -> x.supportsStreaming.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendVideo".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvideoreqDecoder: Decoder[SendVideoReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _video                    <- h.get[IFile]("video")
        _duration                 <- h.get[Option[Int]]("duration")
        _width                    <- h.get[Option[Int]]("width")
        _height                   <- h.get[Option[Int]]("height")
        _thumb                    <- h.get[Option[IFile]]("thumb")
        _caption                  <- h.get[Option[String]]("caption")
        _parseMode                <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities          <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _supportsStreaming        <- h.get[Option[Boolean]]("supports_streaming")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendVideoReq(
          chatId = _chatId,
          video = _video,
          duration = _duration,
          width = _width,
          height = _height,
          thumb = _thumb,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          supportsStreaming = _supportsStreaming,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
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

  implicit lazy val setchatadministratorcustomtitlereqEncoder
    : Encoder[SetChatAdministratorCustomTitleReq] =
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

  implicit lazy val setchatadministratorcustomtitlereqDecoder
    : Decoder[SetChatAdministratorCustomTitleReq] =
    Decoder.instance { h =>
      for {
        _chatId      <- h.get[ChatId]("chat_id")
        _userId      <- h.get[Int]("user_id")
        _customTitle <- h.get[String]("custom_title")
      } yield {
        SetChatAdministratorCustomTitleReq(chatId = _chatId,
                                           userId = _userId,
                                           customTitle = _customTitle)
      }
    }

  implicit lazy val sendanimationreqEncoder: Encoder[SendAnimationReq] =
    (x: SendAnimationReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "animation"                   -> x.animation.asJson,
          "duration"                    -> x.duration.asJson,
          "width"                       -> x.width.asJson,
          "height"                      -> x.height.asJson,
          "thumb"                       -> x.thumb.asJson,
          "caption"                     -> x.caption.asJson,
          "parse_mode"                  -> x.parseMode.asJson,
          "caption_entities"            -> x.captionEntities.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendAnimation".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendanimationreqDecoder: Decoder[SendAnimationReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _animation                <- h.get[IFile]("animation")
        _duration                 <- h.get[Option[Int]]("duration")
        _width                    <- h.get[Option[Int]]("width")
        _height                   <- h.get[Option[Int]]("height")
        _thumb                    <- h.get[Option[IFile]]("thumb")
        _caption                  <- h.get[Option[String]]("caption")
        _parseMode                <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities          <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendAnimationReq(
          chatId = _chatId,
          animation = _animation,
          duration = _duration,
          width = _width,
          height = _height,
          thumb = _thumb,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
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
        AnswerShippingQueryReq(shippingQueryId = _shippingQueryId,
                               ok = _ok,
                               shippingOptions = _shippingOptions,
                               errorMessage = _errorMessage)
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
        AnswerPreCheckoutQueryReq(preCheckoutQueryId = _preCheckoutQueryId,
                                  ok = _ok,
                                  errorMessage = _errorMessage)
      }
    }

  implicit lazy val sendstickerreqEncoder: Encoder[SendStickerReq] =
    (x: SendStickerReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "sticker"                     -> x.sticker.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendSticker".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendstickerreqDecoder: Decoder[SendStickerReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _sticker                  <- h.get[IFile]("sticker")
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendStickerReq(
          chatId = _chatId,
          sticker = _sticker,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val getchatmemberscountreqEncoder: Encoder[GetChatMembersCountReq] =
    (x: GetChatMembersCountReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "getChatMembersCount".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getchatmemberscountreqDecoder: Decoder[GetChatMembersCountReq] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        GetChatMembersCountReq(chatId = _chatId)
      }
    }

  implicit lazy val sendphotoreqEncoder: Encoder[SendPhotoReq] =
    (x: SendPhotoReq) => {
      Json.fromFields(
        List(
          "chat_id"                     -> x.chatId.asJson,
          "photo"                       -> x.photo.asJson,
          "caption"                     -> x.caption.asJson,
          "parse_mode"                  -> x.parseMode.asJson,
          "caption_entities"            -> x.captionEntities.asJson,
          "disable_notification"        -> x.disableNotification.asJson,
          "reply_to_message_id"         -> x.replyToMessageId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "reply_markup"                -> x.replyMarkup.asJson,
          "method"                      -> "sendPhoto".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendphotoreqDecoder: Decoder[SendPhotoReq] =
    Decoder.instance { h =>
      for {
        _chatId                   <- h.get[ChatId]("chat_id")
        _photo                    <- h.get[IFile]("photo")
        _caption                  <- h.get[Option[String]]("caption")
        _parseMode                <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities          <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _disableNotification      <- h.get[Option[Boolean]]("disable_notification")
        _replyToMessageId         <- h.get[Option[Int]]("reply_to_message_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _replyMarkup              <- h.get[Option[KeyboardMarkup]]("reply_markup")
      } yield {
        SendPhotoReq(
          chatId = _chatId,
          photo = _photo,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          disableNotification = _disableNotification,
          replyToMessageId = _replyToMessageId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
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
        GetUpdatesReq(offset = _offset,
                      limit = _limit,
                      timeout = _timeout,
                      allowedUpdates = _allowedUpdates)
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
      } yield {
        SetWebhookReq(
          url = _url,
          certificate = _certificate,
          ipAddress = _ipAddress,
          maxConnections = _maxConnections,
          allowedUpdates = _allowedUpdates,
          dropPendingUpdates = _dropPendingUpdates
        )
      }
    }

}
