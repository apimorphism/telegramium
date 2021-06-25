package telegramium.bots

object uPickleImplicits {

  import upickle.default._

  implicit lazy val javaiofileCodec: ReadWriter[java.io.File] = {
    readwriter[upack.Msg].bimap(
      x => upack.Str(x.getName),
      msg => new java.io.File(readBinary[String](msg))
    )
  }

  implicit lazy val emojiCodec: ReadWriter[Emoji] = {
    readwriter[upack.Msg].bimap(
      {
        case EmojiDice        => upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("🎲"))
        case EmojiDarts       => upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("🎯"))
        case EmojiBasketball  => upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("🏀"))
        case EmojiFootball    => upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("⚽"))
        case EmojiSlotMachine => upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("🎰"))
        case EmojiBowling     => upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("🎳"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("🎲") => EmojiDice
            case upack.Str("🎯") => EmojiDarts
            case upack.Str("🏀") => EmojiBasketball
            case upack.Str("⚽")  => EmojiFootball
            case upack.Str("🎰") => EmojiSlotMachine
            case upack.Str("🎳") => EmojiBowling
          }
          .get
      }
    )
  }

  implicit lazy val emojidiceCodec: ReadWriter[EmojiDice.type] = macroRW
  implicit lazy val emojidartsCodec: ReadWriter[EmojiDarts.type] = macroRW
  implicit lazy val emojibasketballCodec: ReadWriter[EmojiBasketball.type] = macroRW
  implicit lazy val emojifootballCodec: ReadWriter[EmojiFootball.type] = macroRW
  implicit lazy val emojislotmachineCodec: ReadWriter[EmojiSlotMachine.type] = macroRW
  implicit lazy val emojibowlingCodec: ReadWriter[EmojiBowling.type] = macroRW

  implicit lazy val parsemodeCodec: ReadWriter[ParseMode] = {
    readwriter[upack.Msg].bimap(
      {
        case Markdown => upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("Markdown"))
        case Markdown2 =>
          upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("MarkdownV2"))
        case Html => upack.Obj(upack.Obj().obj += upack.Str("_type_") -> writeMsg("HTML"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("Markdown")   => Markdown
            case upack.Str("MarkdownV2") => Markdown2
            case upack.Str("HTML")       => Html
          }
          .get
      }
    )
  }

  implicit lazy val markdownCodec: ReadWriter[Markdown.type] = macroRW
  implicit lazy val markdown2Codec: ReadWriter[Markdown2.type] = macroRW
  implicit lazy val htmlCodec: ReadWriter[Html.type] = macroRW

  implicit lazy val chatidCodec: ReadWriter[ChatId] = {
    readwriter[upack.Msg].bimap(
      {
        case x: ChatIntId => upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("Long"))
        case x: ChatStrId => upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("String"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("Long")   => readBinary[ChatIntId](msg)
            case upack.Str("String") => readBinary[ChatStrId](msg)
          }
          .get
      }
    )
  }

  implicit lazy val chatintidCodec: ReadWriter[ChatIntId] = {
    val idKey = upack.Str("id")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[Long](x))
        } yield {
          ChatIntId(
            id = id
          )
        }
        result.get
      }
    )
  }

  implicit lazy val chatstridCodec: ReadWriter[ChatStrId] = {
    val idKey = upack.Str("id")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
        } yield {
          ChatStrId(
            id = id
          )
        }
        result.get
      }
    )
  }

  implicit lazy val keyboardmarkupCodec: ReadWriter[KeyboardMarkup] = {
    readwriter[upack.Msg].bimap(
      {
        case x: InlineKeyboardMarkup =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("InlineKeyboardMarkup"))
        case x: ForceReply =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("ForceReply"))
        case x: ReplyKeyboardRemove =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("ReplyKeyboardRemove"))
        case x: ReplyKeyboardMarkup =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("ReplyKeyboardMarkup"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("InlineKeyboardMarkup") => readBinary[InlineKeyboardMarkup](msg)
            case upack.Str("ForceReply")           => readBinary[ForceReply](msg)
            case upack.Str("ReplyKeyboardRemove")  => readBinary[ReplyKeyboardRemove](msg)
            case upack.Str("ReplyKeyboardMarkup")  => readBinary[ReplyKeyboardMarkup](msg)
          }
          .get
      }
    )
  }

  implicit lazy val inlinekeyboardmarkupCodec: ReadWriter[InlineKeyboardMarkup] = {
    val inlineKeyboardKey = upack.Str("inlineKeyboard")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          inlineKeyboardKey -> writeMsg(x.inlineKeyboard)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          inlineKeyboard <- m
            .get(inlineKeyboardKey)
            .map(x => readBinary[List[List[InlineKeyboardButton]]](x))
        } yield {
          InlineKeyboardMarkup(
            inlineKeyboard = inlineKeyboard
          )
        }
        result.get
      }
    )
  }

  implicit lazy val forcereplyCodec: ReadWriter[ForceReply] = {
    val forceReplyKey = upack.Str("forceReply")
    val selectiveKey = upack.Str("selective")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          forceReplyKey -> writeMsg(x.forceReply),
          selectiveKey -> writeMsg(x.selective)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          forceReply <- m.get(forceReplyKey).map(x => readBinary[Boolean](x))
          selective <- m.get(selectiveKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          ForceReply(
            forceReply = forceReply,
            selective = selective
          )
        }
        result.get
      }
    )
  }

  implicit lazy val replykeyboardremoveCodec: ReadWriter[ReplyKeyboardRemove] = {
    val removeKeyboardKey = upack.Str("removeKeyboard")
    val selectiveKey = upack.Str("selective")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          removeKeyboardKey -> writeMsg(x.removeKeyboard),
          selectiveKey -> writeMsg(x.selective)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          removeKeyboard <- m.get(removeKeyboardKey).map(x => readBinary[Boolean](x))
          selective <- m.get(selectiveKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          ReplyKeyboardRemove(
            removeKeyboard = removeKeyboard,
            selective = selective
          )
        }
        result.get
      }
    )
  }

  implicit lazy val replykeyboardmarkupCodec: ReadWriter[ReplyKeyboardMarkup] = {
    val keyboardKey = upack.Str("keyboard")
    val resizeKeyboardKey = upack.Str("resizeKeyboard")
    val oneTimeKeyboardKey = upack.Str("oneTimeKeyboard")
    val selectiveKey = upack.Str("selective")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          keyboardKey -> writeMsg(x.keyboard),
          resizeKeyboardKey -> writeMsg(x.resizeKeyboard),
          oneTimeKeyboardKey -> writeMsg(x.oneTimeKeyboard),
          selectiveKey -> writeMsg(x.selective)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          keyboard <- m.get(keyboardKey).map(x => readBinary[List[List[KeyboardButton]]](x))
          resizeKeyboard <- m.get(resizeKeyboardKey).map(x => readBinary[Option[Boolean]](x))
          oneTimeKeyboard <- m.get(oneTimeKeyboardKey).map(x => readBinary[Option[Boolean]](x))
          selective <- m.get(selectiveKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          ReplyKeyboardMarkup(
            keyboard = keyboard,
            resizeKeyboard = resizeKeyboard,
            oneTimeKeyboard = oneTimeKeyboard,
            selective = selective
          )
        }
        result.get
      }
    )
  }

  implicit lazy val ifileCodec: ReadWriter[IFile] = {
    readwriter[upack.Msg].bimap(
      {
        case x: InputPartFile =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("java.io.File"))
        case x: InputLinkFile =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("String"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("java.io.File") => readBinary[InputPartFile](msg)
            case upack.Str("String")       => readBinary[InputLinkFile](msg)
          }
          .get
      }
    )
  }

  implicit lazy val inputpartfileCodec: ReadWriter[InputPartFile] = {
    val fileKey = upack.Str("file")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileKey -> writeMsg(x.file)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          file <- m.get(fileKey).map(x => readBinary[java.io.File](x))
        } yield {
          InputPartFile(
            file = file
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputlinkfileCodec: ReadWriter[InputLinkFile] = {
    val fileKey = upack.Str("file")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileKey -> writeMsg(x.file)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          file <- m.get(fileKey).map(x => readBinary[String](x))
        } yield {
          InputLinkFile(
            file = file
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputmediaCodec: ReadWriter[InputMedia] = {
    readwriter[upack.Msg].bimap(
      {
        case x: InputMediaAnimation =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("animation"))
        case x: InputMediaPhoto =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("photo"))
        case x: InputMediaVideo =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("video"))
        case x: InputMediaDocument =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("document"))
        case x: InputMediaAudio =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("audio"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("animation") => readBinary[InputMediaAnimation](msg)
            case upack.Str("photo")     => readBinary[InputMediaPhoto](msg)
            case upack.Str("video")     => readBinary[InputMediaVideo](msg)
            case upack.Str("document")  => readBinary[InputMediaDocument](msg)
            case upack.Str("audio")     => readBinary[InputMediaAudio](msg)
          }
          .get
      }
    )
  }

  implicit lazy val inputmediaanimationCodec: ReadWriter[InputMediaAnimation] = {
    val mediaKey = upack.Str("media")
    val thumbKey = upack.Str("thumb")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val widthKey = upack.Str("width")
    val heightKey = upack.Str("height")
    val durationKey = upack.Str("duration")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          mediaKey -> writeMsg(x.media),
          thumbKey -> writeMsg(x.thumb),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          widthKey -> writeMsg(x.width),
          heightKey -> writeMsg(x.height),
          durationKey -> writeMsg(x.duration)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          media <- m.get(mediaKey).map(x => readBinary[String](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          width <- m.get(widthKey).map(x => readBinary[Option[Int]](x))
          height <- m.get(heightKey).map(x => readBinary[Option[Int]](x))
          duration <- m.get(durationKey).map(x => readBinary[Option[Int]](x))
        } yield {
          InputMediaAnimation(
            media = media,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            width = width,
            height = height,
            duration = duration
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputmediaphotoCodec: ReadWriter[InputMediaPhoto] = {
    val mediaKey = upack.Str("media")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          mediaKey -> writeMsg(x.media),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          media <- m.get(mediaKey).map(x => readBinary[String](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
        } yield {
          InputMediaPhoto(
            media = media,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputmediavideoCodec: ReadWriter[InputMediaVideo] = {
    val mediaKey = upack.Str("media")
    val thumbKey = upack.Str("thumb")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val widthKey = upack.Str("width")
    val heightKey = upack.Str("height")
    val durationKey = upack.Str("duration")
    val supportsStreamingKey = upack.Str("supportsStreaming")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          mediaKey -> writeMsg(x.media),
          thumbKey -> writeMsg(x.thumb),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          widthKey -> writeMsg(x.width),
          heightKey -> writeMsg(x.height),
          durationKey -> writeMsg(x.duration),
          supportsStreamingKey -> writeMsg(x.supportsStreaming)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          media <- m.get(mediaKey).map(x => readBinary[String](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          width <- m.get(widthKey).map(x => readBinary[Option[Int]](x))
          height <- m.get(heightKey).map(x => readBinary[Option[Int]](x))
          duration <- m.get(durationKey).map(x => readBinary[Option[Int]](x))
          supportsStreaming <- m.get(supportsStreamingKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          InputMediaVideo(
            media = media,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            width = width,
            height = height,
            duration = duration,
            supportsStreaming = supportsStreaming
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputmediadocumentCodec: ReadWriter[InputMediaDocument] = {
    val mediaKey = upack.Str("media")
    val thumbKey = upack.Str("thumb")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val disableContentTypeDetectionKey = upack.Str("disableContentTypeDetection")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          mediaKey -> writeMsg(x.media),
          thumbKey -> writeMsg(x.thumb),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          disableContentTypeDetectionKey -> writeMsg(x.disableContentTypeDetection)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          media <- m.get(mediaKey).map(x => readBinary[String](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          disableContentTypeDetection <- m
            .get(disableContentTypeDetectionKey)
            .map(x => readBinary[Option[Boolean]](x))
        } yield {
          InputMediaDocument(
            media = media,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            disableContentTypeDetection = disableContentTypeDetection
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputmediaaudioCodec: ReadWriter[InputMediaAudio] = {
    val mediaKey = upack.Str("media")
    val thumbKey = upack.Str("thumb")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val durationKey = upack.Str("duration")
    val performerKey = upack.Str("performer")
    val titleKey = upack.Str("title")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          mediaKey -> writeMsg(x.media),
          thumbKey -> writeMsg(x.thumb),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          durationKey -> writeMsg(x.duration),
          performerKey -> writeMsg(x.performer),
          titleKey -> writeMsg(x.title)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          media <- m.get(mediaKey).map(x => readBinary[String](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[IFile]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          duration <- m.get(durationKey).map(x => readBinary[Option[Int]](x))
          performer <- m.get(performerKey).map(x => readBinary[Option[String]](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
        } yield {
          InputMediaAudio(
            media = media,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            duration = duration,
            performer = performer,
            title = title
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultCodec: ReadWriter[InlineQueryResult] = ReadWriter.merge(
    inlinequeryresultgifCodec,
    inlinequeryresultvenueCodec,
    inlinequeryresultcontactCodec,
    inlinequeryresultphotoCodec,
    inlinequeryresultdocumentCodec,
    inlinequeryresultcachedvoiceCodec,
    inlinequeryresultarticleCodec,
    inlinequeryresultaudioCodec,
    inlinequeryresultmpeg4gifCodec,
    inlinequeryresultcachedmpeg4gifCodec,
    inlinequeryresultcacheddocumentCodec,
    inlinequeryresultcachedvideoCodec,
    inlinequeryresultgameCodec,
    inlinequeryresultcachedphotoCodec,
    inlinequeryresultcachedstickerCodec,
    inlinequeryresultvideoCodec,
    inlinequeryresultcachedaudioCodec,
    inlinequeryresultlocationCodec,
    inlinequeryresultcachedgifCodec,
    inlinequeryresultvoiceCodec
  )

  implicit lazy val inlinequeryresultgifCodec: ReadWriter[InlineQueryResultGif] = {
    val idKey = upack.Str("id")
    val gifUrlKey = upack.Str("gifUrl")
    val gifWidthKey = upack.Str("gifWidth")
    val gifHeightKey = upack.Str("gifHeight")
    val gifDurationKey = upack.Str("gifDuration")
    val thumbUrlKey = upack.Str("thumbUrl")
    val thumbMimeTypeKey = upack.Str("thumbMimeType")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          gifUrlKey -> writeMsg(x.gifUrl),
          gifWidthKey -> writeMsg(x.gifWidth),
          gifHeightKey -> writeMsg(x.gifHeight),
          gifDurationKey -> writeMsg(x.gifDuration),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          thumbMimeTypeKey -> writeMsg(x.thumbMimeType),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          gifUrl <- m.get(gifUrlKey).map(x => readBinary[String](x))
          gifWidth <- m.get(gifWidthKey).map(x => readBinary[Option[Int]](x))
          gifHeight <- m.get(gifHeightKey).map(x => readBinary[Option[Int]](x))
          gifDuration <- m.get(gifDurationKey).map(x => readBinary[Option[Int]](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[String](x))
          thumbMimeType <- m.get(thumbMimeTypeKey).map(x => readBinary[Option[String]](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultGif(
            id = id,
            gifUrl = gifUrl,
            gifWidth = gifWidth,
            gifHeight = gifHeight,
            gifDuration = gifDuration,
            thumbUrl = thumbUrl,
            thumbMimeType = thumbMimeType,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultvenueCodec: ReadWriter[InlineQueryResultVenue] = {
    val idKey = upack.Str("id")
    val latitudeKey = upack.Str("latitude")
    val longitudeKey = upack.Str("longitude")
    val titleKey = upack.Str("title")
    val addressKey = upack.Str("address")
    val foursquareIdKey = upack.Str("foursquareId")
    val foursquareTypeKey = upack.Str("foursquareType")
    val googlePlaceIdKey = upack.Str("googlePlaceId")
    val googlePlaceTypeKey = upack.Str("googlePlaceType")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    val thumbUrlKey = upack.Str("thumbUrl")
    val thumbWidthKey = upack.Str("thumbWidth")
    val thumbHeightKey = upack.Str("thumbHeight")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          latitudeKey -> writeMsg(x.latitude),
          longitudeKey -> writeMsg(x.longitude),
          titleKey -> writeMsg(x.title),
          addressKey -> writeMsg(x.address),
          foursquareIdKey -> writeMsg(x.foursquareId),
          foursquareTypeKey -> writeMsg(x.foursquareType),
          googlePlaceIdKey -> writeMsg(x.googlePlaceId),
          googlePlaceTypeKey -> writeMsg(x.googlePlaceType),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          thumbWidthKey -> writeMsg(x.thumbWidth),
          thumbHeightKey -> writeMsg(x.thumbHeight)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          latitude <- m.get(latitudeKey).map(x => readBinary[Float](x))
          longitude <- m.get(longitudeKey).map(x => readBinary[Float](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          address <- m.get(addressKey).map(x => readBinary[String](x))
          foursquareId <- m.get(foursquareIdKey).map(x => readBinary[Option[String]](x))
          foursquareType <- m.get(foursquareTypeKey).map(x => readBinary[Option[String]](x))
          googlePlaceId <- m.get(googlePlaceIdKey).map(x => readBinary[Option[String]](x))
          googlePlaceType <- m.get(googlePlaceTypeKey).map(x => readBinary[Option[String]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[Option[String]](x))
          thumbWidth <- m.get(thumbWidthKey).map(x => readBinary[Option[Int]](x))
          thumbHeight <- m.get(thumbHeightKey).map(x => readBinary[Option[Int]](x))
        } yield {
          InlineQueryResultVenue(
            id = id,
            latitude = latitude,
            longitude = longitude,
            title = title,
            address = address,
            foursquareId = foursquareId,
            foursquareType = foursquareType,
            googlePlaceId = googlePlaceId,
            googlePlaceType = googlePlaceType,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent,
            thumbUrl = thumbUrl,
            thumbWidth = thumbWidth,
            thumbHeight = thumbHeight
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcontactCodec: ReadWriter[InlineQueryResultContact] = {
    val idKey = upack.Str("id")
    val phoneNumberKey = upack.Str("phoneNumber")
    val firstNameKey = upack.Str("firstName")
    val lastNameKey = upack.Str("lastName")
    val vcardKey = upack.Str("vcard")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    val thumbUrlKey = upack.Str("thumbUrl")
    val thumbWidthKey = upack.Str("thumbWidth")
    val thumbHeightKey = upack.Str("thumbHeight")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          phoneNumberKey -> writeMsg(x.phoneNumber),
          firstNameKey -> writeMsg(x.firstName),
          lastNameKey -> writeMsg(x.lastName),
          vcardKey -> writeMsg(x.vcard),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          thumbWidthKey -> writeMsg(x.thumbWidth),
          thumbHeightKey -> writeMsg(x.thumbHeight)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          phoneNumber <- m.get(phoneNumberKey).map(x => readBinary[String](x))
          firstName <- m.get(firstNameKey).map(x => readBinary[String](x))
          lastName <- m.get(lastNameKey).map(x => readBinary[Option[String]](x))
          vcard <- m.get(vcardKey).map(x => readBinary[Option[String]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[Option[String]](x))
          thumbWidth <- m.get(thumbWidthKey).map(x => readBinary[Option[Int]](x))
          thumbHeight <- m.get(thumbHeightKey).map(x => readBinary[Option[Int]](x))
        } yield {
          InlineQueryResultContact(
            id = id,
            phoneNumber = phoneNumber,
            firstName = firstName,
            lastName = lastName,
            vcard = vcard,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent,
            thumbUrl = thumbUrl,
            thumbWidth = thumbWidth,
            thumbHeight = thumbHeight
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultphotoCodec: ReadWriter[InlineQueryResultPhoto] = {
    val idKey = upack.Str("id")
    val photoUrlKey = upack.Str("photoUrl")
    val thumbUrlKey = upack.Str("thumbUrl")
    val photoWidthKey = upack.Str("photoWidth")
    val photoHeightKey = upack.Str("photoHeight")
    val titleKey = upack.Str("title")
    val descriptionKey = upack.Str("description")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          photoUrlKey -> writeMsg(x.photoUrl),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          photoWidthKey -> writeMsg(x.photoWidth),
          photoHeightKey -> writeMsg(x.photoHeight),
          titleKey -> writeMsg(x.title),
          descriptionKey -> writeMsg(x.description),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          photoUrl <- m.get(photoUrlKey).map(x => readBinary[String](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[String](x))
          photoWidth <- m.get(photoWidthKey).map(x => readBinary[Option[Int]](x))
          photoHeight <- m.get(photoHeightKey).map(x => readBinary[Option[Int]](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultPhoto(
            id = id,
            photoUrl = photoUrl,
            thumbUrl = thumbUrl,
            photoWidth = photoWidth,
            photoHeight = photoHeight,
            title = title,
            description = description,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultdocumentCodec: ReadWriter[InlineQueryResultDocument] = {
    val idKey = upack.Str("id")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val documentUrlKey = upack.Str("documentUrl")
    val mimeTypeKey = upack.Str("mimeType")
    val descriptionKey = upack.Str("description")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    val thumbUrlKey = upack.Str("thumbUrl")
    val thumbWidthKey = upack.Str("thumbWidth")
    val thumbHeightKey = upack.Str("thumbHeight")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          documentUrlKey -> writeMsg(x.documentUrl),
          mimeTypeKey -> writeMsg(x.mimeType),
          descriptionKey -> writeMsg(x.description),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          thumbWidthKey -> writeMsg(x.thumbWidth),
          thumbHeightKey -> writeMsg(x.thumbHeight)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          documentUrl <- m.get(documentUrlKey).map(x => readBinary[String](x))
          mimeType <- m.get(mimeTypeKey).map(x => readBinary[String](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[Option[String]](x))
          thumbWidth <- m.get(thumbWidthKey).map(x => readBinary[Option[Int]](x))
          thumbHeight <- m.get(thumbHeightKey).map(x => readBinary[Option[Int]](x))
        } yield {
          InlineQueryResultDocument(
            id = id,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            documentUrl = documentUrl,
            mimeType = mimeType,
            description = description,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent,
            thumbUrl = thumbUrl,
            thumbWidth = thumbWidth,
            thumbHeight = thumbHeight
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcachedvoiceCodec: ReadWriter[InlineQueryResultCachedVoice] = {
    val idKey = upack.Str("id")
    val voiceFileIdKey = upack.Str("voiceFileId")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          voiceFileIdKey -> writeMsg(x.voiceFileId),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          voiceFileId <- m.get(voiceFileIdKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultCachedVoice(
            id = id,
            voiceFileId = voiceFileId,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultarticleCodec: ReadWriter[InlineQueryResultArticle] = {
    val idKey = upack.Str("id")
    val titleKey = upack.Str("title")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    val replyMarkupKey = upack.Str("replyMarkup")
    val urlKey = upack.Str("url")
    val hideUrlKey = upack.Str("hideUrl")
    val descriptionKey = upack.Str("description")
    val thumbUrlKey = upack.Str("thumbUrl")
    val thumbWidthKey = upack.Str("thumbWidth")
    val thumbHeightKey = upack.Str("thumbHeight")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          titleKey -> writeMsg(x.title),
          inputMessageContentKey -> writeMsg(x.inputMessageContent),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          urlKey -> writeMsg(x.url),
          hideUrlKey -> writeMsg(x.hideUrl),
          descriptionKey -> writeMsg(x.description),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          thumbWidthKey -> writeMsg(x.thumbWidth),
          thumbHeightKey -> writeMsg(x.thumbHeight)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[InputMessageContent](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          url <- m.get(urlKey).map(x => readBinary[Option[String]](x))
          hideUrl <- m.get(hideUrlKey).map(x => readBinary[Option[Boolean]](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[Option[String]](x))
          thumbWidth <- m.get(thumbWidthKey).map(x => readBinary[Option[Int]](x))
          thumbHeight <- m.get(thumbHeightKey).map(x => readBinary[Option[Int]](x))
        } yield {
          InlineQueryResultArticle(
            id = id,
            title = title,
            inputMessageContent = inputMessageContent,
            replyMarkup = replyMarkup,
            url = url,
            hideUrl = hideUrl,
            description = description,
            thumbUrl = thumbUrl,
            thumbWidth = thumbWidth,
            thumbHeight = thumbHeight
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultaudioCodec: ReadWriter[InlineQueryResultAudio] = {
    val idKey = upack.Str("id")
    val audioUrlKey = upack.Str("audioUrl")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val performerKey = upack.Str("performer")
    val audioDurationKey = upack.Str("audioDuration")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          audioUrlKey -> writeMsg(x.audioUrl),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          performerKey -> writeMsg(x.performer),
          audioDurationKey -> writeMsg(x.audioDuration),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          audioUrl <- m.get(audioUrlKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          performer <- m.get(performerKey).map(x => readBinary[Option[String]](x))
          audioDuration <- m.get(audioDurationKey).map(x => readBinary[Option[Int]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultAudio(
            id = id,
            audioUrl = audioUrl,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            performer = performer,
            audioDuration = audioDuration,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultmpeg4gifCodec: ReadWriter[InlineQueryResultMpeg4Gif] = {
    val idKey = upack.Str("id")
    val mpeg4UrlKey = upack.Str("mpeg4Url")
    val mpeg4WidthKey = upack.Str("mpeg4Width")
    val mpeg4HeightKey = upack.Str("mpeg4Height")
    val mpeg4DurationKey = upack.Str("mpeg4Duration")
    val thumbUrlKey = upack.Str("thumbUrl")
    val thumbMimeTypeKey = upack.Str("thumbMimeType")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          mpeg4UrlKey -> writeMsg(x.mpeg4Url),
          mpeg4WidthKey -> writeMsg(x.mpeg4Width),
          mpeg4HeightKey -> writeMsg(x.mpeg4Height),
          mpeg4DurationKey -> writeMsg(x.mpeg4Duration),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          thumbMimeTypeKey -> writeMsg(x.thumbMimeType),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          mpeg4Url <- m.get(mpeg4UrlKey).map(x => readBinary[String](x))
          mpeg4Width <- m.get(mpeg4WidthKey).map(x => readBinary[Option[Int]](x))
          mpeg4Height <- m.get(mpeg4HeightKey).map(x => readBinary[Option[Int]](x))
          mpeg4Duration <- m.get(mpeg4DurationKey).map(x => readBinary[Option[Int]](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[String](x))
          thumbMimeType <- m.get(thumbMimeTypeKey).map(x => readBinary[Option[String]](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultMpeg4Gif(
            id = id,
            mpeg4Url = mpeg4Url,
            mpeg4Width = mpeg4Width,
            mpeg4Height = mpeg4Height,
            mpeg4Duration = mpeg4Duration,
            thumbUrl = thumbUrl,
            thumbMimeType = thumbMimeType,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcachedmpeg4gifCodec: ReadWriter[InlineQueryResultCachedMpeg4Gif] = {
    val idKey = upack.Str("id")
    val mpeg4FileIdKey = upack.Str("mpeg4FileId")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          mpeg4FileIdKey -> writeMsg(x.mpeg4FileId),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          mpeg4FileId <- m.get(mpeg4FileIdKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultCachedMpeg4Gif(
            id = id,
            mpeg4FileId = mpeg4FileId,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcacheddocumentCodec: ReadWriter[InlineQueryResultCachedDocument] = {
    val idKey = upack.Str("id")
    val titleKey = upack.Str("title")
    val documentFileIdKey = upack.Str("documentFileId")
    val descriptionKey = upack.Str("description")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          titleKey -> writeMsg(x.title),
          documentFileIdKey -> writeMsg(x.documentFileId),
          descriptionKey -> writeMsg(x.description),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          documentFileId <- m.get(documentFileIdKey).map(x => readBinary[String](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultCachedDocument(
            id = id,
            title = title,
            documentFileId = documentFileId,
            description = description,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcachedvideoCodec: ReadWriter[InlineQueryResultCachedVideo] = {
    val idKey = upack.Str("id")
    val videoFileIdKey = upack.Str("videoFileId")
    val titleKey = upack.Str("title")
    val descriptionKey = upack.Str("description")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          videoFileIdKey -> writeMsg(x.videoFileId),
          titleKey -> writeMsg(x.title),
          descriptionKey -> writeMsg(x.description),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          videoFileId <- m.get(videoFileIdKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultCachedVideo(
            id = id,
            videoFileId = videoFileId,
            title = title,
            description = description,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultgameCodec: ReadWriter[InlineQueryResultGame] = {
    val idKey = upack.Str("id")
    val gameShortNameKey = upack.Str("gameShortName")
    val replyMarkupKey = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          gameShortNameKey -> writeMsg(x.gameShortName),
          replyMarkupKey -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          gameShortName <- m.get(gameShortNameKey).map(x => readBinary[String](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          InlineQueryResultGame(
            id = id,
            gameShortName = gameShortName,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcachedphotoCodec: ReadWriter[InlineQueryResultCachedPhoto] = {
    val idKey = upack.Str("id")
    val photoFileIdKey = upack.Str("photoFileId")
    val titleKey = upack.Str("title")
    val descriptionKey = upack.Str("description")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          photoFileIdKey -> writeMsg(x.photoFileId),
          titleKey -> writeMsg(x.title),
          descriptionKey -> writeMsg(x.description),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          photoFileId <- m.get(photoFileIdKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultCachedPhoto(
            id = id,
            photoFileId = photoFileId,
            title = title,
            description = description,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcachedstickerCodec: ReadWriter[InlineQueryResultCachedSticker] = {
    val idKey = upack.Str("id")
    val stickerFileIdKey = upack.Str("stickerFileId")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          stickerFileIdKey -> writeMsg(x.stickerFileId),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          stickerFileId <- m.get(stickerFileIdKey).map(x => readBinary[String](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultCachedSticker(
            id = id,
            stickerFileId = stickerFileId,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultvideoCodec: ReadWriter[InlineQueryResultVideo] = {
    val idKey = upack.Str("id")
    val videoUrlKey = upack.Str("videoUrl")
    val mimeTypeKey = upack.Str("mimeType")
    val thumbUrlKey = upack.Str("thumbUrl")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val videoWidthKey = upack.Str("videoWidth")
    val videoHeightKey = upack.Str("videoHeight")
    val videoDurationKey = upack.Str("videoDuration")
    val descriptionKey = upack.Str("description")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          videoUrlKey -> writeMsg(x.videoUrl),
          mimeTypeKey -> writeMsg(x.mimeType),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          videoWidthKey -> writeMsg(x.videoWidth),
          videoHeightKey -> writeMsg(x.videoHeight),
          videoDurationKey -> writeMsg(x.videoDuration),
          descriptionKey -> writeMsg(x.description),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          videoUrl <- m.get(videoUrlKey).map(x => readBinary[String](x))
          mimeType <- m.get(mimeTypeKey).map(x => readBinary[String](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          videoWidth <- m.get(videoWidthKey).map(x => readBinary[Option[Int]](x))
          videoHeight <- m.get(videoHeightKey).map(x => readBinary[Option[Int]](x))
          videoDuration <- m.get(videoDurationKey).map(x => readBinary[Option[Int]](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultVideo(
            id = id,
            videoUrl = videoUrl,
            mimeType = mimeType,
            thumbUrl = thumbUrl,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            videoWidth = videoWidth,
            videoHeight = videoHeight,
            videoDuration = videoDuration,
            description = description,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcachedaudioCodec: ReadWriter[InlineQueryResultCachedAudio] = {
    val idKey = upack.Str("id")
    val audioFileIdKey = upack.Str("audioFileId")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          audioFileIdKey -> writeMsg(x.audioFileId),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          audioFileId <- m.get(audioFileIdKey).map(x => readBinary[String](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultCachedAudio(
            id = id,
            audioFileId = audioFileId,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultlocationCodec: ReadWriter[InlineQueryResultLocation] = {
    val idKey = upack.Str("id")
    val latitudeKey = upack.Str("latitude")
    val longitudeKey = upack.Str("longitude")
    val titleKey = upack.Str("title")
    val horizontalAccuracyKey = upack.Str("horizontalAccuracy")
    val livePeriodKey = upack.Str("livePeriod")
    val headingKey = upack.Str("heading")
    val proximityAlertRadiusKey = upack.Str("proximityAlertRadius")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    val thumbUrlKey = upack.Str("thumbUrl")
    val thumbWidthKey = upack.Str("thumbWidth")
    val thumbHeightKey = upack.Str("thumbHeight")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          latitudeKey -> writeMsg(x.latitude),
          longitudeKey -> writeMsg(x.longitude),
          titleKey -> writeMsg(x.title),
          horizontalAccuracyKey -> writeMsg(x.horizontalAccuracy),
          livePeriodKey -> writeMsg(x.livePeriod),
          headingKey -> writeMsg(x.heading),
          proximityAlertRadiusKey -> writeMsg(x.proximityAlertRadius),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent),
          thumbUrlKey -> writeMsg(x.thumbUrl),
          thumbWidthKey -> writeMsg(x.thumbWidth),
          thumbHeightKey -> writeMsg(x.thumbHeight)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          latitude <- m.get(latitudeKey).map(x => readBinary[Float](x))
          longitude <- m.get(longitudeKey).map(x => readBinary[Float](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          horizontalAccuracy <- m.get(horizontalAccuracyKey).map(x => readBinary[Option[Float]](x))
          livePeriod <- m.get(livePeriodKey).map(x => readBinary[Option[Int]](x))
          heading <- m.get(headingKey).map(x => readBinary[Option[Int]](x))
          proximityAlertRadius <- m
            .get(proximityAlertRadiusKey)
            .map(x => readBinary[Option[Int]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
          thumbUrl <- m.get(thumbUrlKey).map(x => readBinary[Option[String]](x))
          thumbWidth <- m.get(thumbWidthKey).map(x => readBinary[Option[Int]](x))
          thumbHeight <- m.get(thumbHeightKey).map(x => readBinary[Option[Int]](x))
        } yield {
          InlineQueryResultLocation(
            id = id,
            latitude = latitude,
            longitude = longitude,
            title = title,
            horizontalAccuracy = horizontalAccuracy,
            livePeriod = livePeriod,
            heading = heading,
            proximityAlertRadius = proximityAlertRadius,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent,
            thumbUrl = thumbUrl,
            thumbWidth = thumbWidth,
            thumbHeight = thumbHeight
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultcachedgifCodec: ReadWriter[InlineQueryResultCachedGif] = {
    val idKey = upack.Str("id")
    val gifFileIdKey = upack.Str("gifFileId")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          gifFileIdKey -> writeMsg(x.gifFileId),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          gifFileId <- m.get(gifFileIdKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultCachedGif(
            id = id,
            gifFileId = gifFileId,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryresultvoiceCodec: ReadWriter[InlineQueryResultVoice] = {
    val idKey = upack.Str("id")
    val voiceUrlKey = upack.Str("voiceUrl")
    val titleKey = upack.Str("title")
    val captionKey = upack.Str("caption")
    val parseModeKey = upack.Str("parseMode")
    val captionEntitiesKey = upack.Str("captionEntities")
    val voiceDurationKey = upack.Str("voiceDuration")
    val replyMarkupKey = upack.Str("replyMarkup")
    val inputMessageContentKey = upack.Str("inputMessageContent")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          voiceUrlKey -> writeMsg(x.voiceUrl),
          titleKey -> writeMsg(x.title),
          captionKey -> writeMsg(x.caption),
          parseModeKey -> writeMsg(x.parseMode),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          voiceDurationKey -> writeMsg(x.voiceDuration),
          replyMarkupKey -> writeMsg(x.replyMarkup),
          inputMessageContentKey -> writeMsg(x.inputMessageContent)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          voiceUrl <- m.get(voiceUrlKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          voiceDuration <- m.get(voiceDurationKey).map(x => readBinary[Option[Int]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
          inputMessageContent <- m
            .get(inputMessageContentKey)
            .map(x => readBinary[Option[InputMessageContent]](x))
        } yield {
          InlineQueryResultVoice(
            id = id,
            voiceUrl = voiceUrl,
            title = title,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            voiceDuration = voiceDuration,
            replyMarkup = replyMarkup,
            inputMessageContent = inputMessageContent
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputmessagecontentCodec: ReadWriter[InputMessageContent] = {
    readwriter[upack.Msg].bimap(
      {
        case x: InputVenueMessageContent =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("InputVenueMessageContent"))
        case x: InputInvoiceMessageContent =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("InputInvoiceMessageContent"))
        case x: InputContactMessageContent =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("InputContactMessageContent"))
        case x: InputLocationMessageContent =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("InputLocationMessageContent"))
        case x: InputTextMessageContent =>
          upack.Obj(writeMsg(x).obj += upack.Str("_type_") -> writeMsg("InputTextMessageContent"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("InputVenueMessageContent") => readBinary[InputVenueMessageContent](msg)
            case upack.Str("InputInvoiceMessageContent") =>
              readBinary[InputInvoiceMessageContent](msg)
            case upack.Str("InputContactMessageContent") =>
              readBinary[InputContactMessageContent](msg)
            case upack.Str("InputLocationMessageContent") =>
              readBinary[InputLocationMessageContent](msg)
            case upack.Str("InputTextMessageContent") => readBinary[InputTextMessageContent](msg)
          }
          .get
      }
    )
  }

  implicit lazy val inputvenuemessagecontentCodec: ReadWriter[InputVenueMessageContent] = {
    val latitudeKey = upack.Str("latitude")
    val longitudeKey = upack.Str("longitude")
    val titleKey = upack.Str("title")
    val addressKey = upack.Str("address")
    val foursquareIdKey = upack.Str("foursquareId")
    val foursquareTypeKey = upack.Str("foursquareType")
    val googlePlaceIdKey = upack.Str("googlePlaceId")
    val googlePlaceTypeKey = upack.Str("googlePlaceType")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          latitudeKey -> writeMsg(x.latitude),
          longitudeKey -> writeMsg(x.longitude),
          titleKey -> writeMsg(x.title),
          addressKey -> writeMsg(x.address),
          foursquareIdKey -> writeMsg(x.foursquareId),
          foursquareTypeKey -> writeMsg(x.foursquareType),
          googlePlaceIdKey -> writeMsg(x.googlePlaceId),
          googlePlaceTypeKey -> writeMsg(x.googlePlaceType)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          latitude <- m.get(latitudeKey).map(x => readBinary[Float](x))
          longitude <- m.get(longitudeKey).map(x => readBinary[Float](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          address <- m.get(addressKey).map(x => readBinary[String](x))
          foursquareId <- m.get(foursquareIdKey).map(x => readBinary[Option[String]](x))
          foursquareType <- m.get(foursquareTypeKey).map(x => readBinary[Option[String]](x))
          googlePlaceId <- m.get(googlePlaceIdKey).map(x => readBinary[Option[String]](x))
          googlePlaceType <- m.get(googlePlaceTypeKey).map(x => readBinary[Option[String]](x))
        } yield {
          InputVenueMessageContent(
            latitude = latitude,
            longitude = longitude,
            title = title,
            address = address,
            foursquareId = foursquareId,
            foursquareType = foursquareType,
            googlePlaceId = googlePlaceId,
            googlePlaceType = googlePlaceType
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputinvoicemessagecontentCodec: ReadWriter[InputInvoiceMessageContent] = {
    val titleKey = upack.Str("title")
    val descriptionKey = upack.Str("description")
    val payloadKey = upack.Str("payload")
    val providerTokenKey = upack.Str("providerToken")
    val currencyKey = upack.Str("currency")
    val pricesKey = upack.Str("prices")
    val maxTipAmountKey = upack.Str("maxTipAmount")
    val suggestedTipAmountsKey = upack.Str("suggestedTipAmounts")
    val providerDataKey = upack.Str("providerData")
    val photoUrlKey = upack.Str("photoUrl")
    val photoSizeKey = upack.Str("photoSize")
    val photoWidthKey = upack.Str("photoWidth")
    val photoHeightKey = upack.Str("photoHeight")
    val needNameKey = upack.Str("needName")
    val needPhoneNumberKey = upack.Str("needPhoneNumber")
    val needEmailKey = upack.Str("needEmail")
    val needShippingAddressKey = upack.Str("needShippingAddress")
    val sendPhoneNumberToProviderKey = upack.Str("sendPhoneNumberToProvider")
    val sendEmailToProviderKey = upack.Str("sendEmailToProvider")
    val isFlexibleKey = upack.Str("isFlexible")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          titleKey -> writeMsg(x.title),
          descriptionKey -> writeMsg(x.description),
          payloadKey -> writeMsg(x.payload),
          providerTokenKey -> writeMsg(x.providerToken),
          currencyKey -> writeMsg(x.currency),
          pricesKey -> writeMsg(x.prices),
          maxTipAmountKey -> writeMsg(x.maxTipAmount),
          suggestedTipAmountsKey -> writeMsg(x.suggestedTipAmounts),
          providerDataKey -> writeMsg(x.providerData),
          photoUrlKey -> writeMsg(x.photoUrl),
          photoSizeKey -> writeMsg(x.photoSize),
          photoWidthKey -> writeMsg(x.photoWidth),
          photoHeightKey -> writeMsg(x.photoHeight),
          needNameKey -> writeMsg(x.needName),
          needPhoneNumberKey -> writeMsg(x.needPhoneNumber),
          needEmailKey -> writeMsg(x.needEmail),
          needShippingAddressKey -> writeMsg(x.needShippingAddress),
          sendPhoneNumberToProviderKey -> writeMsg(x.sendPhoneNumberToProvider),
          sendEmailToProviderKey -> writeMsg(x.sendEmailToProvider),
          isFlexibleKey -> writeMsg(x.isFlexible)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          title <- m.get(titleKey).map(x => readBinary[String](x))
          description <- m.get(descriptionKey).map(x => readBinary[String](x))
          payload <- m.get(payloadKey).map(x => readBinary[String](x))
          providerToken <- m.get(providerTokenKey).map(x => readBinary[String](x))
          currency <- m.get(currencyKey).map(x => readBinary[String](x))
          prices <- m.get(pricesKey).map(x => readBinary[List[LabeledPrice]](x))
          maxTipAmount <- m.get(maxTipAmountKey).map(x => readBinary[Option[Int]](x))
          suggestedTipAmounts <- m.get(suggestedTipAmountsKey).map(x => readBinary[List[Int]](x))
          providerData <- m.get(providerDataKey).map(x => readBinary[Option[String]](x))
          photoUrl <- m.get(photoUrlKey).map(x => readBinary[Option[String]](x))
          photoSize <- m.get(photoSizeKey).map(x => readBinary[Option[Int]](x))
          photoWidth <- m.get(photoWidthKey).map(x => readBinary[Option[Int]](x))
          photoHeight <- m.get(photoHeightKey).map(x => readBinary[Option[Int]](x))
          needName <- m.get(needNameKey).map(x => readBinary[Option[Boolean]](x))
          needPhoneNumber <- m.get(needPhoneNumberKey).map(x => readBinary[Option[Boolean]](x))
          needEmail <- m.get(needEmailKey).map(x => readBinary[Option[Boolean]](x))
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
        } yield {
          InputInvoiceMessageContent(
            title = title,
            description = description,
            payload = payload,
            providerToken = providerToken,
            currency = currency,
            prices = prices,
            maxTipAmount = maxTipAmount,
            suggestedTipAmounts = suggestedTipAmounts,
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
            isFlexible = isFlexible
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputcontactmessagecontentCodec: ReadWriter[InputContactMessageContent] = {
    val phoneNumberKey = upack.Str("phoneNumber")
    val firstNameKey = upack.Str("firstName")
    val lastNameKey = upack.Str("lastName")
    val vcardKey = upack.Str("vcard")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          phoneNumberKey -> writeMsg(x.phoneNumber),
          firstNameKey -> writeMsg(x.firstName),
          lastNameKey -> writeMsg(x.lastName),
          vcardKey -> writeMsg(x.vcard)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          phoneNumber <- m.get(phoneNumberKey).map(x => readBinary[String](x))
          firstName <- m.get(firstNameKey).map(x => readBinary[String](x))
          lastName <- m.get(lastNameKey).map(x => readBinary[Option[String]](x))
          vcard <- m.get(vcardKey).map(x => readBinary[Option[String]](x))
        } yield {
          InputContactMessageContent(
            phoneNumber = phoneNumber,
            firstName = firstName,
            lastName = lastName,
            vcard = vcard
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputlocationmessagecontentCodec: ReadWriter[InputLocationMessageContent] = {
    val latitudeKey = upack.Str("latitude")
    val longitudeKey = upack.Str("longitude")
    val horizontalAccuracyKey = upack.Str("horizontalAccuracy")
    val livePeriodKey = upack.Str("livePeriod")
    val headingKey = upack.Str("heading")
    val proximityAlertRadiusKey = upack.Str("proximityAlertRadius")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          latitudeKey -> writeMsg(x.latitude),
          longitudeKey -> writeMsg(x.longitude),
          horizontalAccuracyKey -> writeMsg(x.horizontalAccuracy),
          livePeriodKey -> writeMsg(x.livePeriod),
          headingKey -> writeMsg(x.heading),
          proximityAlertRadiusKey -> writeMsg(x.proximityAlertRadius)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          latitude <- m.get(latitudeKey).map(x => readBinary[Float](x))
          longitude <- m.get(longitudeKey).map(x => readBinary[Float](x))
          horizontalAccuracy <- m.get(horizontalAccuracyKey).map(x => readBinary[Option[Float]](x))
          livePeriod <- m.get(livePeriodKey).map(x => readBinary[Option[Int]](x))
          heading <- m.get(headingKey).map(x => readBinary[Option[Int]](x))
          proximityAlertRadius <- m
            .get(proximityAlertRadiusKey)
            .map(x => readBinary[Option[Int]](x))
        } yield {
          InputLocationMessageContent(
            latitude = latitude,
            longitude = longitude,
            horizontalAccuracy = horizontalAccuracy,
            livePeriod = livePeriod,
            heading = heading,
            proximityAlertRadius = proximityAlertRadius
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputtextmessagecontentCodec: ReadWriter[InputTextMessageContent] = {
    val messageTextKey = upack.Str("messageText")
    val parseModeKey = upack.Str("parseMode")
    val entitiesKey = upack.Str("entities")
    val disableWebPagePreviewKey = upack.Str("disableWebPagePreview")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          messageTextKey -> writeMsg(x.messageText),
          parseModeKey -> writeMsg(x.parseMode),
          entitiesKey -> writeMsg(x.entities),
          disableWebPagePreviewKey -> writeMsg(x.disableWebPagePreview)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          messageText <- m.get(messageTextKey).map(x => readBinary[String](x))
          parseMode <- m.get(parseModeKey).map(x => readBinary[Option[ParseMode]](x))
          entities <- m.get(entitiesKey).map(x => readBinary[List[MessageEntity]](x))
          disableWebPagePreview <- m
            .get(disableWebPagePreviewKey)
            .map(x => readBinary[Option[Boolean]](x))
        } yield {
          InputTextMessageContent(
            messageText = messageText,
            parseMode = parseMode,
            entities = entities,
            disableWebPagePreview = disableWebPagePreview
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrorCodec: ReadWriter[PassportElementError] = {
    readwriter[upack.Msg].bimap(
      {
        case x: PassportElementErrorFiles =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("files"))
        case x: PassportElementErrorDataField =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("data"))
        case x: PassportElementErrorReverseSide =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("reverse_side"))
        case x: PassportElementErrorSelfie =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("selfie"))
        case x: PassportElementErrorFrontSide =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("front_side"))
        case x: PassportElementErrorFile =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("file"))
        case x: PassportElementErrorUnspecified =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("unspecified"))
        case x: PassportElementErrorTranslationFile =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("translation_file"))
        case x: PassportElementErrorTranslationFiles =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("translation_files"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("files")        => readBinary[PassportElementErrorFiles](msg)
            case upack.Str("data")         => readBinary[PassportElementErrorDataField](msg)
            case upack.Str("reverse_side") => readBinary[PassportElementErrorReverseSide](msg)
            case upack.Str("selfie")       => readBinary[PassportElementErrorSelfie](msg)
            case upack.Str("front_side")   => readBinary[PassportElementErrorFrontSide](msg)
            case upack.Str("file")         => readBinary[PassportElementErrorFile](msg)
            case upack.Str("unspecified")  => readBinary[PassportElementErrorUnspecified](msg)
            case upack.Str("translation_file") =>
              readBinary[PassportElementErrorTranslationFile](msg)
            case upack.Str("translation_files") =>
              readBinary[PassportElementErrorTranslationFiles](msg)
          }
          .get
      }
    )
  }

  implicit lazy val passportelementerrorfilesCodec: ReadWriter[PassportElementErrorFiles] = {
    val typeKey = upack.Str("type")
    val fileHashesKey = upack.Str("fileHashes")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          fileHashesKey -> writeMsg(x.fileHashes),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          fileHashes <- m.get(fileHashesKey).map(x => readBinary[List[String]](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorFiles(
            `type` = `type`,
            fileHashes = fileHashes,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrordatafieldCodec: ReadWriter[PassportElementErrorDataField] = {
    val typeKey = upack.Str("type")
    val fieldNameKey = upack.Str("fieldName")
    val dataHashKey = upack.Str("dataHash")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          fieldNameKey -> writeMsg(x.fieldName),
          dataHashKey -> writeMsg(x.dataHash),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          fieldName <- m.get(fieldNameKey).map(x => readBinary[String](x))
          dataHash <- m.get(dataHashKey).map(x => readBinary[String](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorDataField(
            `type` = `type`,
            fieldName = fieldName,
            dataHash = dataHash,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrorreversesideCodec: ReadWriter[PassportElementErrorReverseSide] = {
    val typeKey = upack.Str("type")
    val fileHashKey = upack.Str("fileHash")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          fileHashKey -> writeMsg(x.fileHash),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          fileHash <- m.get(fileHashKey).map(x => readBinary[String](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorReverseSide(
            `type` = `type`,
            fileHash = fileHash,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrorselfieCodec: ReadWriter[PassportElementErrorSelfie] = {
    val typeKey = upack.Str("type")
    val fileHashKey = upack.Str("fileHash")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          fileHashKey -> writeMsg(x.fileHash),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          fileHash <- m.get(fileHashKey).map(x => readBinary[String](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorSelfie(
            `type` = `type`,
            fileHash = fileHash,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrorfrontsideCodec: ReadWriter[PassportElementErrorFrontSide] = {
    val typeKey = upack.Str("type")
    val fileHashKey = upack.Str("fileHash")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          fileHashKey -> writeMsg(x.fileHash),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          fileHash <- m.get(fileHashKey).map(x => readBinary[String](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorFrontSide(
            `type` = `type`,
            fileHash = fileHash,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrorfileCodec: ReadWriter[PassportElementErrorFile] = {
    val typeKey = upack.Str("type")
    val fileHashKey = upack.Str("fileHash")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          fileHashKey -> writeMsg(x.fileHash),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          fileHash <- m.get(fileHashKey).map(x => readBinary[String](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorFile(
            `type` = `type`,
            fileHash = fileHash,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrorunspecifiedCodec: ReadWriter[PassportElementErrorUnspecified] = {
    val typeKey = upack.Str("type")
    val elementHashKey = upack.Str("elementHash")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          elementHashKey -> writeMsg(x.elementHash),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          elementHash <- m.get(elementHashKey).map(x => readBinary[String](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorUnspecified(
            `type` = `type`,
            elementHash = elementHash,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrortranslationfileCodec: ReadWriter[PassportElementErrorTranslationFile] = {
    val typeKey = upack.Str("type")
    val fileHashKey = upack.Str("fileHash")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          fileHashKey -> writeMsg(x.fileHash),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          fileHash <- m.get(fileHashKey).map(x => readBinary[String](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorTranslationFile(
            `type` = `type`,
            fileHash = fileHash,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportelementerrortranslationfilesCodec: ReadWriter[PassportElementErrorTranslationFiles] = {
    val typeKey = upack.Str("type")
    val fileHashesKey = upack.Str("fileHashes")
    val messageKey = upack.Str("message")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          fileHashesKey -> writeMsg(x.fileHashes),
          messageKey -> writeMsg(x.message)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          fileHashes <- m.get(fileHashesKey).map(x => readBinary[List[String]](x))
          message <- m.get(messageKey).map(x => readBinary[String](x))
        } yield {
          PassportElementErrorTranslationFiles(
            `type` = `type`,
            fileHashes = fileHashes,
            message = message
          )
        }
        result.get
      }
    )
  }

  implicit lazy val messageentityCodec: ReadWriter[MessageEntity] = {
    readwriter[upack.Msg].bimap(
      {
        case x: MentionMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("mention"))
        case x: CashtagMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("cashtag"))
        case x: CodeMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("code"))
        case x: BotCommandMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("bot_command"))
        case x: EmailMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("email"))
        case x: BoldMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("bold"))
        case x: PreMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("pre"))
        case x: ItalicMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("italic"))
        case x: StrikethroughMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("strikethrough"))
        case x: UnderlineMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("underline"))
        case x: HashtagMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("hashtag"))
        case x: TextMentionMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("text_mention"))
        case x: TextLinkMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("text_link"))
        case x: UrlMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("url"))
        case x: PhoneNumberMessageEntity =>
          upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("phone_number"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("mention")       => readBinary[MentionMessageEntity](msg)
            case upack.Str("cashtag")       => readBinary[CashtagMessageEntity](msg)
            case upack.Str("code")          => readBinary[CodeMessageEntity](msg)
            case upack.Str("bot_command")   => readBinary[BotCommandMessageEntity](msg)
            case upack.Str("email")         => readBinary[EmailMessageEntity](msg)
            case upack.Str("bold")          => readBinary[BoldMessageEntity](msg)
            case upack.Str("pre")           => readBinary[PreMessageEntity](msg)
            case upack.Str("italic")        => readBinary[ItalicMessageEntity](msg)
            case upack.Str("strikethrough") => readBinary[StrikethroughMessageEntity](msg)
            case upack.Str("underline")     => readBinary[UnderlineMessageEntity](msg)
            case upack.Str("hashtag")       => readBinary[HashtagMessageEntity](msg)
            case upack.Str("text_mention")  => readBinary[TextMentionMessageEntity](msg)
            case upack.Str("text_link")     => readBinary[TextLinkMessageEntity](msg)
            case upack.Str("url")           => readBinary[UrlMessageEntity](msg)
            case upack.Str("phone_number")  => readBinary[PhoneNumberMessageEntity](msg)
          }
          .get
      }
    )
  }

  implicit lazy val mentionmessageentityCodec: ReadWriter[MentionMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          MentionMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val cashtagmessageentityCodec: ReadWriter[CashtagMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          CashtagMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val codemessageentityCodec: ReadWriter[CodeMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          CodeMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val botcommandmessageentityCodec: ReadWriter[BotCommandMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          BotCommandMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val emailmessageentityCodec: ReadWriter[EmailMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          EmailMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val boldmessageentityCodec: ReadWriter[BoldMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          BoldMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val premessageentityCodec: ReadWriter[PreMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    val languageKey = upack.Str("language")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length),
          languageKey -> writeMsg(x.language)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
          language <- m.get(languageKey).map(x => readBinary[Option[String]](x))
        } yield {
          PreMessageEntity(
            offset = offset,
            length = length,
            language = language
          )
        }
        result.get
      }
    )
  }

  implicit lazy val italicmessageentityCodec: ReadWriter[ItalicMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          ItalicMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val strikethroughmessageentityCodec: ReadWriter[StrikethroughMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          StrikethroughMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val underlinemessageentityCodec: ReadWriter[UnderlineMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          UnderlineMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val hashtagmessageentityCodec: ReadWriter[HashtagMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          HashtagMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val textmentionmessageentityCodec: ReadWriter[TextMentionMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    val userKey = upack.Str("user")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length),
          userKey -> writeMsg(x.user)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
          user <- m.get(userKey).map(x => readBinary[User](x))
        } yield {
          TextMentionMessageEntity(
            offset = offset,
            length = length,
            user = user
          )
        }
        result.get
      }
    )
  }

  implicit lazy val textlinkmessageentityCodec: ReadWriter[TextLinkMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    val urlKey = upack.Str("url")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length),
          urlKey -> writeMsg(x.url)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
          url <- m.get(urlKey).map(x => readBinary[String](x))
        } yield {
          TextLinkMessageEntity(
            offset = offset,
            length = length,
            url = url
          )
        }
        result.get
      }
    )
  }

  implicit lazy val urlmessageentityCodec: ReadWriter[UrlMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          UrlMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val phonenumbermessageentityCodec: ReadWriter[PhoneNumberMessageEntity] = {
    val offsetKey = upack.Str("offset")
    val lengthKey = upack.Str("length")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          offsetKey -> writeMsg(x.offset),
          lengthKey -> writeMsg(x.length)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          offset <- m.get(offsetKey).map(x => readBinary[Int](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
        } yield {
          PhoneNumberMessageEntity(
            offset = offset,
            length = length
          )
        }
        result.get
      }
    )
  }

  implicit lazy val responseparametersCodec: ReadWriter[ResponseParameters] = {
    val migrateToChatIdKey = upack.Str("migrateToChatId")
    val retryAfterKey = upack.Str("retryAfter")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          migrateToChatIdKey -> writeMsg(x.migrateToChatId),
          retryAfterKey -> writeMsg(x.retryAfter)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          migrateToChatId <- m.get(migrateToChatIdKey).map(x => readBinary[Option[Long]](x))
          retryAfter <- m.get(retryAfterKey).map(x => readBinary[Option[Int]](x))
        } yield {
          ResponseParameters(
            migrateToChatId = migrateToChatId,
            retryAfter = retryAfter
          )
        }
        result.get
      }
    )
  }

  implicit lazy val animationCodec: ReadWriter[Animation] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val widthKey = upack.Str("width")
    val heightKey = upack.Str("height")
    val durationKey = upack.Str("duration")
    val thumbKey = upack.Str("thumb")
    val fileNameKey = upack.Str("fileName")
    val mimeTypeKey = upack.Str("mimeType")
    val fileSizeKey = upack.Str("fileSize")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          widthKey -> writeMsg(x.width),
          heightKey -> writeMsg(x.height),
          durationKey -> writeMsg(x.duration),
          thumbKey -> writeMsg(x.thumb),
          fileNameKey -> writeMsg(x.fileName),
          mimeTypeKey -> writeMsg(x.mimeType),
          fileSizeKey -> writeMsg(x.fileSize)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          width <- m.get(widthKey).map(x => readBinary[Int](x))
          height <- m.get(heightKey).map(x => readBinary[Int](x))
          duration <- m.get(durationKey).map(x => readBinary[Int](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[PhotoSize]](x))
          fileName <- m.get(fileNameKey).map(x => readBinary[Option[String]](x))
          mimeType <- m.get(mimeTypeKey).map(x => readBinary[Option[String]](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
        } yield {
          Animation(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            width = width,
            height = height,
            duration = duration,
            thumb = thumb,
            fileName = fileName,
            mimeType = mimeType,
            fileSize = fileSize
          )
        }
        result.get
      }
    )
  }

  implicit lazy val chatCodec: ReadWriter[Chat] = {
    val idKey = upack.Str("id")
    val typeKey = upack.Str("type")
    val titleKey = upack.Str("title")
    val usernameKey = upack.Str("username")
    val firstNameKey = upack.Str("firstName")
    val lastNameKey = upack.Str("lastName")
    val photoKey = upack.Str("photo")
    val bioKey = upack.Str("bio")
    val descriptionKey = upack.Str("description")
    val inviteLinkKey = upack.Str("inviteLink")
    val pinnedMessageKey = upack.Str("pinnedMessage")
    val permissionsKey = upack.Str("permissions")
    val slowModeDelayKey = upack.Str("slowModeDelay")
    val messageAutoDeleteTimeKey = upack.Str("messageAutoDeleteTime")
    val stickerSetNameKey = upack.Str("stickerSetName")
    val canSetStickerSetKey = upack.Str("canSetStickerSet")
    val linkedChatIdKey = upack.Str("linkedChatId")
    val locationKey = upack.Str("location")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          typeKey -> writeMsg(x.`type`),
          titleKey -> writeMsg(x.title),
          usernameKey -> writeMsg(x.username),
          firstNameKey -> writeMsg(x.firstName),
          lastNameKey -> writeMsg(x.lastName),
          photoKey -> writeMsg(x.photo),
          bioKey -> writeMsg(x.bio),
          descriptionKey -> writeMsg(x.description),
          inviteLinkKey -> writeMsg(x.inviteLink),
          pinnedMessageKey -> writeMsg(x.pinnedMessage),
          permissionsKey -> writeMsg(x.permissions),
          slowModeDelayKey -> writeMsg(x.slowModeDelay),
          messageAutoDeleteTimeKey -> writeMsg(x.messageAutoDeleteTime),
          stickerSetNameKey -> writeMsg(x.stickerSetName),
          canSetStickerSetKey -> writeMsg(x.canSetStickerSet),
          linkedChatIdKey -> writeMsg(x.linkedChatId),
          locationKey -> writeMsg(x.location)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[Long](x))
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          username <- m.get(usernameKey).map(x => readBinary[Option[String]](x))
          firstName <- m.get(firstNameKey).map(x => readBinary[Option[String]](x))
          lastName <- m.get(lastNameKey).map(x => readBinary[Option[String]](x))
          photo <- m.get(photoKey).map(x => readBinary[Option[ChatPhoto]](x))
          bio <- m.get(bioKey).map(x => readBinary[Option[String]](x))
          description <- m.get(descriptionKey).map(x => readBinary[Option[String]](x))
          inviteLink <- m.get(inviteLinkKey).map(x => readBinary[Option[String]](x))
          pinnedMessage <- m.get(pinnedMessageKey).map(x => readBinary[Option[Message]](x))
          permissions <- m.get(permissionsKey).map(x => readBinary[Option[ChatPermissions]](x))
          slowModeDelay <- m.get(slowModeDelayKey).map(x => readBinary[Option[Int]](x))
          messageAutoDeleteTime <- m
            .get(messageAutoDeleteTimeKey)
            .map(x => readBinary[Option[Int]](x))
          stickerSetName <- m.get(stickerSetNameKey).map(x => readBinary[Option[String]](x))
          canSetStickerSet <- m.get(canSetStickerSetKey).map(x => readBinary[Option[Boolean]](x))
          linkedChatId <- m.get(linkedChatIdKey).map(x => readBinary[Option[Long]](x))
          location <- m.get(locationKey).map(x => readBinary[Option[ChatLocation]](x))
        } yield {
          Chat(
            id = id,
            `type` = `type`,
            title = title,
            username = username,
            firstName = firstName,
            lastName = lastName,
            photo = photo,
            bio = bio,
            description = description,
            inviteLink = inviteLink,
            pinnedMessage = pinnedMessage,
            permissions = permissions,
            slowModeDelay = slowModeDelay,
            messageAutoDeleteTime = messageAutoDeleteTime,
            stickerSetName = stickerSetName,
            canSetStickerSet = canSetStickerSet,
            linkedChatId = linkedChatId,
            location = location
          )
        }
        result.get
      }
    )
  }

  implicit lazy val videonoteCodec: ReadWriter[VideoNote] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val lengthKey = upack.Str("length")
    val durationKey = upack.Str("duration")
    val thumbKey = upack.Str("thumb")
    val fileSizeKey = upack.Str("fileSize")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          lengthKey -> writeMsg(x.length),
          durationKey -> writeMsg(x.duration),
          thumbKey -> writeMsg(x.thumb),
          fileSizeKey -> writeMsg(x.fileSize)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          length <- m.get(lengthKey).map(x => readBinary[Int](x))
          duration <- m.get(durationKey).map(x => readBinary[Int](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[PhotoSize]](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
        } yield {
          VideoNote(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            length = length,
            duration = duration,
            thumb = thumb,
            fileSize = fileSize
          )
        }
        result.get
      }
    )
  }

  implicit lazy val locationCodec: ReadWriter[Location] = {
    val longitudeKey = upack.Str("longitude")
    val latitudeKey = upack.Str("latitude")
    val horizontalAccuracyKey = upack.Str("horizontalAccuracy")
    val livePeriodKey = upack.Str("livePeriod")
    val headingKey = upack.Str("heading")
    val proximityAlertRadiusKey = upack.Str("proximityAlertRadius")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          longitudeKey -> writeMsg(x.longitude),
          latitudeKey -> writeMsg(x.latitude),
          horizontalAccuracyKey -> writeMsg(x.horizontalAccuracy),
          livePeriodKey -> writeMsg(x.livePeriod),
          headingKey -> writeMsg(x.heading),
          proximityAlertRadiusKey -> writeMsg(x.proximityAlertRadius)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          longitude <- m.get(longitudeKey).map(x => readBinary[Float](x))
          latitude <- m.get(latitudeKey).map(x => readBinary[Float](x))
          horizontalAccuracy <- m.get(horizontalAccuracyKey).map(x => readBinary[Option[Float]](x))
          livePeriod <- m.get(livePeriodKey).map(x => readBinary[Option[Int]](x))
          heading <- m.get(headingKey).map(x => readBinary[Option[Int]](x))
          proximityAlertRadius <- m
            .get(proximityAlertRadiusKey)
            .map(x => readBinary[Option[Int]](x))
        } yield {
          Location(
            longitude = longitude,
            latitude = latitude,
            horizontalAccuracy = horizontalAccuracy,
            livePeriod = livePeriod,
            heading = heading,
            proximityAlertRadius = proximityAlertRadius
          )
        }
        result.get
      }
    )
  }

  implicit lazy val shippingqueryCodec: ReadWriter[ShippingQuery] = {
    val idKey = upack.Str("id")
    val fromKey = upack.Str("from")
    val invoicePayloadKey = upack.Str("invoicePayload")
    val shippingAddressKey = upack.Str("shippingAddress")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          fromKey -> writeMsg(x.from),
          invoicePayloadKey -> writeMsg(x.invoicePayload),
          shippingAddressKey -> writeMsg(x.shippingAddress)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          from <- m.get(fromKey).map(x => readBinary[User](x))
          invoicePayload <- m.get(invoicePayloadKey).map(x => readBinary[String](x))
          shippingAddress <- m.get(shippingAddressKey).map(x => readBinary[ShippingAddress](x))
        } yield {
          ShippingQuery(
            id = id,
            from = from,
            invoicePayload = invoicePayload,
            shippingAddress = shippingAddress
          )
        }
        result.get
      }
    )
  }

  implicit lazy val voicechatendedCodec: ReadWriter[VoiceChatEnded] = {
    val durationKey = upack.Str("duration")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          durationKey -> writeMsg(x.duration)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          duration <- m.get(durationKey).map(x => readBinary[Int](x))
        } yield {
          VoiceChatEnded(
            duration = duration
          )
        }
        result.get
      }
    )
  }

  implicit lazy val chatpermissionsCodec: ReadWriter[ChatPermissions] = {
    val canSendMessagesKey = upack.Str("canSendMessages")
    val canSendMediaMessagesKey = upack.Str("canSendMediaMessages")
    val canSendPollsKey = upack.Str("canSendPolls")
    val canSendOtherMessagesKey = upack.Str("canSendOtherMessages")
    val canAddWebPagePreviewsKey = upack.Str("canAddWebPagePreviews")
    val canChangeInfoKey = upack.Str("canChangeInfo")
    val canInviteUsersKey = upack.Str("canInviteUsers")
    val canPinMessagesKey = upack.Str("canPinMessages")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          canSendMessagesKey -> writeMsg(x.canSendMessages),
          canSendMediaMessagesKey -> writeMsg(x.canSendMediaMessages),
          canSendPollsKey -> writeMsg(x.canSendPolls),
          canSendOtherMessagesKey -> writeMsg(x.canSendOtherMessages),
          canAddWebPagePreviewsKey -> writeMsg(x.canAddWebPagePreviews),
          canChangeInfoKey -> writeMsg(x.canChangeInfo),
          canInviteUsersKey -> writeMsg(x.canInviteUsers),
          canPinMessagesKey -> writeMsg(x.canPinMessages)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          canSendMessages <- m.get(canSendMessagesKey).map(x => readBinary[Option[Boolean]](x))
          canSendMediaMessages <- m
            .get(canSendMediaMessagesKey)
            .map(x => readBinary[Option[Boolean]](x))
          canSendPolls <- m.get(canSendPollsKey).map(x => readBinary[Option[Boolean]](x))
          canSendOtherMessages <- m
            .get(canSendOtherMessagesKey)
            .map(x => readBinary[Option[Boolean]](x))
          canAddWebPagePreviews <- m
            .get(canAddWebPagePreviewsKey)
            .map(x => readBinary[Option[Boolean]](x))
          canChangeInfo <- m.get(canChangeInfoKey).map(x => readBinary[Option[Boolean]](x))
          canInviteUsers <- m.get(canInviteUsersKey).map(x => readBinary[Option[Boolean]](x))
          canPinMessages <- m.get(canPinMessagesKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          ChatPermissions(
            canSendMessages = canSendMessages,
            canSendMediaMessages = canSendMediaMessages,
            canSendPolls = canSendPolls,
            canSendOtherMessages = canSendOtherMessages,
            canAddWebPagePreviews = canAddWebPagePreviews,
            canChangeInfo = canChangeInfo,
            canInviteUsers = canInviteUsers,
            canPinMessages = canPinMessages
          )
        }
        result.get
      }
    )
  }

  implicit lazy val polloptionCodec: ReadWriter[PollOption] = {
    val textKey = upack.Str("text")
    val voterCountKey = upack.Str("voterCount")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          textKey -> writeMsg(x.text),
          voterCountKey -> writeMsg(x.voterCount)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          text <- m.get(textKey).map(x => readBinary[String](x))
          voterCount <- m.get(voterCountKey).map(x => readBinary[Int](x))
        } yield {
          PollOption(
            text = text,
            voterCount = voterCount
          )
        }
        result.get
      }
    )
  }

  implicit lazy val shippingaddressCodec: ReadWriter[ShippingAddress] = {
    val countryCodeKey = upack.Str("countryCode")
    val stateKey = upack.Str("state")
    val cityKey = upack.Str("city")
    val streetLine1Key = upack.Str("streetLine1")
    val streetLine2Key = upack.Str("streetLine2")
    val postCodeKey = upack.Str("postCode")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          countryCodeKey -> writeMsg(x.countryCode),
          stateKey -> writeMsg(x.state),
          cityKey -> writeMsg(x.city),
          streetLine1Key -> writeMsg(x.streetLine1),
          streetLine2Key -> writeMsg(x.streetLine2),
          postCodeKey -> writeMsg(x.postCode)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          countryCode <- m.get(countryCodeKey).map(x => readBinary[String](x))
          state <- m.get(stateKey).map(x => readBinary[String](x))
          city <- m.get(cityKey).map(x => readBinary[String](x))
          streetLine1 <- m.get(streetLine1Key).map(x => readBinary[String](x))
          streetLine2 <- m.get(streetLine2Key).map(x => readBinary[String](x))
          postCode <- m.get(postCodeKey).map(x => readBinary[String](x))
        } yield {
          ShippingAddress(
            countryCode = countryCode,
            state = state,
            city = city,
            streetLine1 = streetLine1,
            streetLine2 = streetLine2,
            postCode = postCode
          )
        }
        result.get
      }
    )
  }

  implicit lazy val chatlocationCodec: ReadWriter[ChatLocation] = {
    val locationKey = upack.Str("location")
    val addressKey = upack.Str("address")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          locationKey -> writeMsg(x.location),
          addressKey -> writeMsg(x.address)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          location <- m.get(locationKey).map(x => readBinary[Location](x))
          address <- m.get(addressKey).map(x => readBinary[String](x))
        } yield {
          ChatLocation(
            location = location,
            address = address
          )
        }
        result.get
      }
    )
  }

  implicit lazy val orderinfoCodec: ReadWriter[OrderInfo] = {
    val nameKey = upack.Str("name")
    val phoneNumberKey = upack.Str("phoneNumber")
    val emailKey = upack.Str("email")
    val shippingAddressKey = upack.Str("shippingAddress")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey -> writeMsg(x.name),
          phoneNumberKey -> writeMsg(x.phoneNumber),
          emailKey -> writeMsg(x.email),
          shippingAddressKey -> writeMsg(x.shippingAddress)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          name <- m.get(nameKey).map(x => readBinary[Option[String]](x))
          phoneNumber <- m.get(phoneNumberKey).map(x => readBinary[Option[String]](x))
          email <- m.get(emailKey).map(x => readBinary[Option[String]](x))
          shippingAddress <- m
            .get(shippingAddressKey)
            .map(x => readBinary[Option[ShippingAddress]](x))
        } yield {
          OrderInfo(
            name = name,
            phoneNumber = phoneNumber,
            email = email,
            shippingAddress = shippingAddress
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inputfileCodec: ReadWriter[InputFile.type] = macroRW

  implicit lazy val updateCodec: ReadWriter[Update] = {
    val updateIdKey = upack.Str("updateId")
    val messageKey = upack.Str("message")
    val editedMessageKey = upack.Str("editedMessage")
    val channelPostKey = upack.Str("channelPost")
    val editedChannelPostKey = upack.Str("editedChannelPost")
    val inlineQueryKey = upack.Str("inlineQuery")
    val chosenInlineResultKey = upack.Str("chosenInlineResult")
    val callbackQueryKey = upack.Str("callbackQuery")
    val shippingQueryKey = upack.Str("shippingQuery")
    val preCheckoutQueryKey = upack.Str("preCheckoutQuery")
    val pollKey = upack.Str("poll")
    val pollAnswerKey = upack.Str("pollAnswer")
    val myChatMemberKey = upack.Str("myChatMember")
    val chatMemberKey = upack.Str("chatMember")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          updateIdKey -> writeMsg(x.updateId),
          messageKey -> writeMsg(x.message),
          editedMessageKey -> writeMsg(x.editedMessage),
          channelPostKey -> writeMsg(x.channelPost),
          editedChannelPostKey -> writeMsg(x.editedChannelPost),
          inlineQueryKey -> writeMsg(x.inlineQuery),
          chosenInlineResultKey -> writeMsg(x.chosenInlineResult),
          callbackQueryKey -> writeMsg(x.callbackQuery),
          shippingQueryKey -> writeMsg(x.shippingQuery),
          preCheckoutQueryKey -> writeMsg(x.preCheckoutQuery),
          pollKey -> writeMsg(x.poll),
          pollAnswerKey -> writeMsg(x.pollAnswer),
          myChatMemberKey -> writeMsg(x.myChatMember),
          chatMemberKey -> writeMsg(x.chatMember)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          updateId <- m.get(updateIdKey).map(x => readBinary[Int](x))
          message <- m.get(messageKey).map(x => readBinary[Option[Message]](x))
          editedMessage <- m.get(editedMessageKey).map(x => readBinary[Option[Message]](x))
          channelPost <- m.get(channelPostKey).map(x => readBinary[Option[Message]](x))
          editedChannelPost <- m.get(editedChannelPostKey).map(x => readBinary[Option[Message]](x))
          inlineQuery <- m.get(inlineQueryKey).map(x => readBinary[Option[InlineQuery]](x))
          chosenInlineResult <- m
            .get(chosenInlineResultKey)
            .map(x => readBinary[Option[ChosenInlineResult]](x))
          callbackQuery <- m.get(callbackQueryKey).map(x => readBinary[Option[CallbackQuery]](x))
          shippingQuery <- m.get(shippingQueryKey).map(x => readBinary[Option[ShippingQuery]](x))
          preCheckoutQuery <- m
            .get(preCheckoutQueryKey)
            .map(x => readBinary[Option[PreCheckoutQuery]](x))
          poll <- m.get(pollKey).map(x => readBinary[Option[Poll]](x))
          pollAnswer <- m.get(pollAnswerKey).map(x => readBinary[Option[PollAnswer]](x))
          myChatMember <- m.get(myChatMemberKey).map(x => readBinary[Option[ChatMemberUpdated]](x))
          chatMember <- m.get(chatMemberKey).map(x => readBinary[Option[ChatMemberUpdated]](x))
        } yield {
          Update(
            updateId = updateId,
            message = message,
            editedMessage = editedMessage,
            channelPost = channelPost,
            editedChannelPost = editedChannelPost,
            inlineQuery = inlineQuery,
            chosenInlineResult = chosenInlineResult,
            callbackQuery = callbackQuery,
            shippingQuery = shippingQuery,
            preCheckoutQuery = preCheckoutQuery,
            poll = poll,
            pollAnswer = pollAnswer,
            myChatMember = myChatMember,
            chatMember = chatMember
          )
        }
        result.get
      }
    )
  }

  implicit lazy val maskpositionCodec: ReadWriter[MaskPosition] = {
    val pointKey = upack.Str("point")
    val xShiftKey = upack.Str("xShift")
    val yShiftKey = upack.Str("yShift")
    val scaleKey = upack.Str("scale")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          pointKey -> writeMsg(x.point),
          xShiftKey -> writeMsg(x.xShift),
          yShiftKey -> writeMsg(x.yShift),
          scaleKey -> writeMsg(x.scale)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          point <- m.get(pointKey).map(x => readBinary[String](x))
          xShift <- m.get(xShiftKey).map(x => readBinary[Float](x))
          yShift <- m.get(yShiftKey).map(x => readBinary[Float](x))
          scale <- m.get(scaleKey).map(x => readBinary[Float](x))
        } yield {
          MaskPosition(
            point = point,
            xShift = xShift,
            yShift = yShift,
            scale = scale
          )
        }
        result.get
      }
    )
  }

  implicit lazy val callbackgameCodec: ReadWriter[CallbackGame.type] = macroRW

  implicit lazy val keyboardbuttonCodec: ReadWriter[KeyboardButton] = {
    val textKey = upack.Str("text")
    val requestContactKey = upack.Str("requestContact")
    val requestLocationKey = upack.Str("requestLocation")
    val requestPollKey = upack.Str("requestPoll")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          textKey -> writeMsg(x.text),
          requestContactKey -> writeMsg(x.requestContact),
          requestLocationKey -> writeMsg(x.requestLocation),
          requestPollKey -> writeMsg(x.requestPoll)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          text <- m.get(textKey).map(x => readBinary[String](x))
          requestContact <- m.get(requestContactKey).map(x => readBinary[Option[Boolean]](x))
          requestLocation <- m.get(requestLocationKey).map(x => readBinary[Option[Boolean]](x))
          requestPoll <- m
            .get(requestPollKey)
            .map(x => readBinary[Option[KeyboardButtonPollType]](x))
        } yield {
          KeyboardButton(
            text = text,
            requestContact = requestContact,
            requestLocation = requestLocation,
            requestPoll = requestPoll
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportfileCodec: ReadWriter[PassportFile] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val fileSizeKey = upack.Str("fileSize")
    val fileDateKey = upack.Str("fileDate")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          fileSizeKey -> writeMsg(x.fileSize),
          fileDateKey -> writeMsg(x.fileDate)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Int](x))
          fileDate <- m.get(fileDateKey).map(x => readBinary[Int](x))
        } yield {
          PassportFile(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            fileSize = fileSize,
            fileDate = fileDate
          )
        }
        result.get
      }
    )
  }

  implicit lazy val photosizeCodec: ReadWriter[PhotoSize] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val widthKey = upack.Str("width")
    val heightKey = upack.Str("height")
    val fileSizeKey = upack.Str("fileSize")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          widthKey -> writeMsg(x.width),
          heightKey -> writeMsg(x.height),
          fileSizeKey -> writeMsg(x.fileSize)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          width <- m.get(widthKey).map(x => readBinary[Int](x))
          height <- m.get(heightKey).map(x => readBinary[Int](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
        } yield {
          PhotoSize(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            width = width,
            height = height,
            fileSize = fileSize
          )
        }
        result.get
      }
    )
  }

  implicit lazy val keyboardbuttonpolltypeCodec: ReadWriter[KeyboardButtonPollType] = {
    val typeKey = upack.Str("type")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[Option[String]](x))
        } yield {
          KeyboardButtonPollType(
            `type` = `type`
          )
        }
        result.get
      }
    )
  }

  implicit lazy val pollCodec: ReadWriter[Poll] = {
    val idKey = upack.Str("id")
    val questionKey = upack.Str("question")
    val optionsKey = upack.Str("options")
    val totalVoterCountKey = upack.Str("totalVoterCount")
    val isClosedKey = upack.Str("isClosed")
    val isAnonymousKey = upack.Str("isAnonymous")
    val typeKey = upack.Str("type")
    val allowsMultipleAnswersKey = upack.Str("allowsMultipleAnswers")
    val correctOptionIdKey = upack.Str("correctOptionId")
    val explanationKey = upack.Str("explanation")
    val explanationEntitiesKey = upack.Str("explanationEntities")
    val openPeriodKey = upack.Str("openPeriod")
    val closeDateKey = upack.Str("closeDate")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          questionKey -> writeMsg(x.question),
          optionsKey -> writeMsg(x.options),
          totalVoterCountKey -> writeMsg(x.totalVoterCount),
          isClosedKey -> writeMsg(x.isClosed),
          isAnonymousKey -> writeMsg(x.isAnonymous),
          typeKey -> writeMsg(x.`type`),
          allowsMultipleAnswersKey -> writeMsg(x.allowsMultipleAnswers),
          correctOptionIdKey -> writeMsg(x.correctOptionId),
          explanationKey -> writeMsg(x.explanation),
          explanationEntitiesKey -> writeMsg(x.explanationEntities),
          openPeriodKey -> writeMsg(x.openPeriod),
          closeDateKey -> writeMsg(x.closeDate)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          question <- m.get(questionKey).map(x => readBinary[String](x))
          options <- m.get(optionsKey).map(x => readBinary[List[PollOption]](x))
          totalVoterCount <- m.get(totalVoterCountKey).map(x => readBinary[Int](x))
          isClosed <- m.get(isClosedKey).map(x => readBinary[Boolean](x))
          isAnonymous <- m.get(isAnonymousKey).map(x => readBinary[Boolean](x))
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          allowsMultipleAnswers <- m.get(allowsMultipleAnswersKey).map(x => readBinary[Boolean](x))
          correctOptionId <- m.get(correctOptionIdKey).map(x => readBinary[Option[Int]](x))
          explanation <- m.get(explanationKey).map(x => readBinary[Option[String]](x))
          explanationEntities <- m
            .get(explanationEntitiesKey)
            .map(x => readBinary[List[MessageEntity]](x))
          openPeriod <- m.get(openPeriodKey).map(x => readBinary[Option[Int]](x))
          closeDate <- m.get(closeDateKey).map(x => readBinary[Option[Int]](x))
        } yield {
          Poll(
            id = id,
            question = question,
            options = options,
            totalVoterCount = totalVoterCount,
            isClosed = isClosed,
            isAnonymous = isAnonymous,
            `type` = `type`,
            allowsMultipleAnswers = allowsMultipleAnswers,
            correctOptionId = correctOptionId,
            explanation = explanation,
            explanationEntities = explanationEntities,
            openPeriod = openPeriod,
            closeDate = closeDate
          )
        }
        result.get
      }
    )
  }

  implicit lazy val stickersetCodec: ReadWriter[StickerSet] = {
    val nameKey = upack.Str("name")
    val titleKey = upack.Str("title")
    val isAnimatedKey = upack.Str("isAnimated")
    val containsMasksKey = upack.Str("containsMasks")
    val stickersKey = upack.Str("stickers")
    val thumbKey = upack.Str("thumb")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey -> writeMsg(x.name),
          titleKey -> writeMsg(x.title),
          isAnimatedKey -> writeMsg(x.isAnimated),
          containsMasksKey -> writeMsg(x.containsMasks),
          stickersKey -> writeMsg(x.stickers),
          thumbKey -> writeMsg(x.thumb)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          name <- m.get(nameKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          isAnimated <- m.get(isAnimatedKey).map(x => readBinary[Boolean](x))
          containsMasks <- m.get(containsMasksKey).map(x => readBinary[Boolean](x))
          stickers <- m.get(stickersKey).map(x => readBinary[List[Sticker]](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[PhotoSize]](x))
        } yield {
          StickerSet(
            name = name,
            title = title,
            isAnimated = isAnimated,
            containsMasks = containsMasks,
            stickers = stickers,
            thumb = thumb
          )
        }
        result.get
      }
    )
  }

  implicit lazy val pollanswerCodec: ReadWriter[PollAnswer] = {
    val pollIdKey = upack.Str("pollId")
    val userKey = upack.Str("user")
    val optionIdsKey = upack.Str("optionIds")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          pollIdKey -> writeMsg(x.pollId),
          userKey -> writeMsg(x.user),
          optionIdsKey -> writeMsg(x.optionIds)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          pollId <- m.get(pollIdKey).map(x => readBinary[String](x))
          user <- m.get(userKey).map(x => readBinary[User](x))
          optionIds <- m.get(optionIdsKey).map(x => readBinary[List[Int]](x))
        } yield {
          PollAnswer(
            pollId = pollId,
            user = user,
            optionIds = optionIds
          )
        }
        result.get
      }
    )
  }

  implicit lazy val contactCodec: ReadWriter[Contact] = {
    val phoneNumberKey = upack.Str("phoneNumber")
    val firstNameKey = upack.Str("firstName")
    val lastNameKey = upack.Str("lastName")
    val userIdKey = upack.Str("userId")
    val vcardKey = upack.Str("vcard")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          phoneNumberKey -> writeMsg(x.phoneNumber),
          firstNameKey -> writeMsg(x.firstName),
          lastNameKey -> writeMsg(x.lastName),
          userIdKey -> writeMsg(x.userId),
          vcardKey -> writeMsg(x.vcard)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          phoneNumber <- m.get(phoneNumberKey).map(x => readBinary[String](x))
          firstName <- m.get(firstNameKey).map(x => readBinary[String](x))
          lastName <- m.get(lastNameKey).map(x => readBinary[Option[String]](x))
          userId <- m.get(userIdKey).map(x => readBinary[Option[Long]](x))
          vcard <- m.get(vcardKey).map(x => readBinary[Option[String]](x))
        } yield {
          Contact(
            phoneNumber = phoneNumber,
            firstName = firstName,
            lastName = lastName,
            userId = userId,
            vcard = vcard
          )
        }
        result.get
      }
    )
  }

  implicit lazy val gamehighscoreCodec: ReadWriter[GameHighScore] = {
    val positionKey = upack.Str("position")
    val userKey = upack.Str("user")
    val scoreKey = upack.Str("score")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          positionKey -> writeMsg(x.position),
          userKey -> writeMsg(x.user),
          scoreKey -> writeMsg(x.score)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          position <- m.get(positionKey).map(x => readBinary[Int](x))
          user <- m.get(userKey).map(x => readBinary[User](x))
          score <- m.get(scoreKey).map(x => readBinary[Int](x))
        } yield {
          GameHighScore(
            position = position,
            user = user,
            score = score
          )
        }
        result.get
      }
    )
  }

  implicit lazy val messageautodeletetimerchangedCodec: ReadWriter[MessageAutoDeleteTimerChanged] = {
    val messageAutoDeleteTimeKey = upack.Str("messageAutoDeleteTime")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          messageAutoDeleteTimeKey -> writeMsg(x.messageAutoDeleteTime)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          messageAutoDeleteTime <- m.get(messageAutoDeleteTimeKey).map(x => readBinary[Int](x))
        } yield {
          MessageAutoDeleteTimerChanged(
            messageAutoDeleteTime = messageAutoDeleteTime
          )
        }
        result.get
      }
    )
  }

  implicit lazy val labeledpriceCodec: ReadWriter[LabeledPrice] = {
    val labelKey = upack.Str("label")
    val amountKey = upack.Str("amount")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          labelKey -> writeMsg(x.label),
          amountKey -> writeMsg(x.amount)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          label <- m.get(labelKey).map(x => readBinary[String](x))
          amount <- m.get(amountKey).map(x => readBinary[Int](x))
        } yield {
          LabeledPrice(
            label = label,
            amount = amount
          )
        }
        result.get
      }
    )
  }

  implicit lazy val venueCodec: ReadWriter[Venue] = {
    val locationKey = upack.Str("location")
    val titleKey = upack.Str("title")
    val addressKey = upack.Str("address")
    val foursquareIdKey = upack.Str("foursquareId")
    val foursquareTypeKey = upack.Str("foursquareType")
    val googlePlaceIdKey = upack.Str("googlePlaceId")
    val googlePlaceTypeKey = upack.Str("googlePlaceType")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          locationKey -> writeMsg(x.location),
          titleKey -> writeMsg(x.title),
          addressKey -> writeMsg(x.address),
          foursquareIdKey -> writeMsg(x.foursquareId),
          foursquareTypeKey -> writeMsg(x.foursquareType),
          googlePlaceIdKey -> writeMsg(x.googlePlaceId),
          googlePlaceTypeKey -> writeMsg(x.googlePlaceType)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          location <- m.get(locationKey).map(x => readBinary[Location](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          address <- m.get(addressKey).map(x => readBinary[String](x))
          foursquareId <- m.get(foursquareIdKey).map(x => readBinary[Option[String]](x))
          foursquareType <- m.get(foursquareTypeKey).map(x => readBinary[Option[String]](x))
          googlePlaceId <- m.get(googlePlaceIdKey).map(x => readBinary[Option[String]](x))
          googlePlaceType <- m.get(googlePlaceTypeKey).map(x => readBinary[Option[String]](x))
        } yield {
          Venue(
            location = location,
            title = title,
            address = address,
            foursquareId = foursquareId,
            foursquareType = foursquareType,
            googlePlaceId = googlePlaceId,
            googlePlaceType = googlePlaceType
          )
        }
        result.get
      }
    )
  }

  implicit lazy val successfulpaymentCodec: ReadWriter[SuccessfulPayment] = {
    val currencyKey = upack.Str("currency")
    val totalAmountKey = upack.Str("totalAmount")
    val invoicePayloadKey = upack.Str("invoicePayload")
    val shippingOptionIdKey = upack.Str("shippingOptionId")
    val orderInfoKey = upack.Str("orderInfo")
    val telegramPaymentChargeIdKey = upack.Str("telegramPaymentChargeId")
    val providerPaymentChargeIdKey = upack.Str("providerPaymentChargeId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          currencyKey -> writeMsg(x.currency),
          totalAmountKey -> writeMsg(x.totalAmount),
          invoicePayloadKey -> writeMsg(x.invoicePayload),
          shippingOptionIdKey -> writeMsg(x.shippingOptionId),
          orderInfoKey -> writeMsg(x.orderInfo),
          telegramPaymentChargeIdKey -> writeMsg(x.telegramPaymentChargeId),
          providerPaymentChargeIdKey -> writeMsg(x.providerPaymentChargeId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          currency <- m.get(currencyKey).map(x => readBinary[String](x))
          totalAmount <- m.get(totalAmountKey).map(x => readBinary[Int](x))
          invoicePayload <- m.get(invoicePayloadKey).map(x => readBinary[String](x))
          shippingOptionId <- m.get(shippingOptionIdKey).map(x => readBinary[Option[String]](x))
          orderInfo <- m.get(orderInfoKey).map(x => readBinary[Option[OrderInfo]](x))
          telegramPaymentChargeId <- m
            .get(telegramPaymentChargeIdKey)
            .map(x => readBinary[String](x))
          providerPaymentChargeId <- m
            .get(providerPaymentChargeIdKey)
            .map(x => readBinary[String](x))
        } yield {
          SuccessfulPayment(
            currency = currency,
            totalAmount = totalAmount,
            invoicePayload = invoicePayload,
            shippingOptionId = shippingOptionId,
            orderInfo = orderInfo,
            telegramPaymentChargeId = telegramPaymentChargeId,
            providerPaymentChargeId = providerPaymentChargeId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val chatinvitelinkCodec: ReadWriter[ChatInviteLink] = {
    val inviteLinkKey = upack.Str("inviteLink")
    val creatorKey = upack.Str("creator")
    val isPrimaryKey = upack.Str("isPrimary")
    val isRevokedKey = upack.Str("isRevoked")
    val expireDateKey = upack.Str("expireDate")
    val memberLimitKey = upack.Str("memberLimit")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          inviteLinkKey -> writeMsg(x.inviteLink),
          creatorKey -> writeMsg(x.creator),
          isPrimaryKey -> writeMsg(x.isPrimary),
          isRevokedKey -> writeMsg(x.isRevoked),
          expireDateKey -> writeMsg(x.expireDate),
          memberLimitKey -> writeMsg(x.memberLimit)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          inviteLink <- m.get(inviteLinkKey).map(x => readBinary[String](x))
          creator <- m.get(creatorKey).map(x => readBinary[User](x))
          isPrimary <- m.get(isPrimaryKey).map(x => readBinary[Boolean](x))
          isRevoked <- m.get(isRevokedKey).map(x => readBinary[Boolean](x))
          expireDate <- m.get(expireDateKey).map(x => readBinary[Option[Int]](x))
          memberLimit <- m.get(memberLimitKey).map(x => readBinary[Option[Int]](x))
        } yield {
          ChatInviteLink(
            inviteLink = inviteLink,
            creator = creator,
            isPrimary = isPrimary,
            isRevoked = isRevoked,
            expireDate = expireDate,
            memberLimit = memberLimit
          )
        }
        result.get
      }
    )
  }

  implicit lazy val diceCodec: ReadWriter[Dice] = {
    val emojiKey = upack.Str("emoji")
    val valueKey = upack.Str("value")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          emojiKey -> writeMsg(x.emoji),
          valueKey -> writeMsg(x.value)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          emoji <- m.get(emojiKey).map(x => readBinary[Emoji](x))
          value <- m.get(valueKey).map(x => readBinary[Int](x))
        } yield {
          Dice(
            emoji = emoji,
            value = value
          )
        }
        result.get
      }
    )
  }

  implicit lazy val chatmemberupdatedCodec: ReadWriter[ChatMemberUpdated] = {
    val chatKey = upack.Str("chat")
    val fromKey = upack.Str("from")
    val dateKey = upack.Str("date")
    val oldChatMemberKey = upack.Str("oldChatMember")
    val newChatMemberKey = upack.Str("newChatMember")
    val inviteLinkKey = upack.Str("inviteLink")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          chatKey -> writeMsg(x.chat),
          fromKey -> writeMsg(x.from),
          dateKey -> writeMsg(x.date),
          oldChatMemberKey -> writeMsg(x.oldChatMember),
          newChatMemberKey -> writeMsg(x.newChatMember),
          inviteLinkKey -> writeMsg(x.inviteLink)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          chat <- m.get(chatKey).map(x => readBinary[Chat](x))
          from <- m.get(fromKey).map(x => readBinary[User](x))
          date <- m.get(dateKey).map(x => readBinary[Int](x))
          oldChatMember <- m.get(oldChatMemberKey).map(x => readBinary[ChatMember](x))
          newChatMember <- m.get(newChatMemberKey).map(x => readBinary[ChatMember](x))
          inviteLink <- m.get(inviteLinkKey).map(x => readBinary[Option[ChatInviteLink]](x))
        } yield {
          ChatMemberUpdated(
            chat = chat,
            from = from,
            date = date,
            oldChatMember = oldChatMember,
            newChatMember = newChatMember,
            inviteLink = inviteLink
          )
        }
        result.get
      }
    )
  }

  implicit lazy val passportdataCodec: ReadWriter[PassportData] = {
    val dataKey = upack.Str("data")
    val credentialsKey = upack.Str("credentials")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          dataKey -> writeMsg(x.data),
          credentialsKey -> writeMsg(x.credentials)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          data <- m.get(dataKey).map(x => readBinary[List[EncryptedPassportElement]](x))
          credentials <- m.get(credentialsKey).map(x => readBinary[EncryptedCredentials](x))
        } yield {
          PassportData(
            data = data,
            credentials = credentials
          )
        }
        result.get
      }
    )
  }

  implicit lazy val fileCodec: ReadWriter[File] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val fileSizeKey = upack.Str("fileSize")
    val filePathKey = upack.Str("filePath")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          fileSizeKey -> writeMsg(x.fileSize),
          filePathKey -> writeMsg(x.filePath)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
          filePath <- m.get(filePathKey).map(x => readBinary[Option[String]](x))
        } yield {
          File(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            fileSize = fileSize,
            filePath = filePath
          )
        }
        result.get
      }
    )
  }

  implicit lazy val gameCodec: ReadWriter[Game] = {
    val titleKey = upack.Str("title")
    val descriptionKey = upack.Str("description")
    val photoKey = upack.Str("photo")
    val textKey = upack.Str("text")
    val textEntitiesKey = upack.Str("textEntities")
    val animationKey = upack.Str("animation")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          titleKey -> writeMsg(x.title),
          descriptionKey -> writeMsg(x.description),
          photoKey -> writeMsg(x.photo),
          textKey -> writeMsg(x.text),
          textEntitiesKey -> writeMsg(x.textEntities),
          animationKey -> writeMsg(x.animation)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          title <- m.get(titleKey).map(x => readBinary[String](x))
          description <- m.get(descriptionKey).map(x => readBinary[String](x))
          photo <- m.get(photoKey).map(x => readBinary[List[PhotoSize]](x))
          text <- m.get(textKey).map(x => readBinary[Option[String]](x))
          textEntities <- m.get(textEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          animation <- m.get(animationKey).map(x => readBinary[Option[Animation]](x))
        } yield {
          Game(
            title = title,
            description = description,
            photo = photo,
            text = text,
            textEntities = textEntities,
            animation = animation
          )
        }
        result.get
      }
    )
  }

  implicit lazy val choseninlineresultCodec: ReadWriter[ChosenInlineResult] = {
    val resultIdKey = upack.Str("resultId")
    val fromKey = upack.Str("from")
    val locationKey = upack.Str("location")
    val inlineMessageIdKey = upack.Str("inlineMessageId")
    val queryKey = upack.Str("query")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          resultIdKey -> writeMsg(x.resultId),
          fromKey -> writeMsg(x.from),
          locationKey -> writeMsg(x.location),
          inlineMessageIdKey -> writeMsg(x.inlineMessageId),
          queryKey -> writeMsg(x.query)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          resultId <- m.get(resultIdKey).map(x => readBinary[String](x))
          from <- m.get(fromKey).map(x => readBinary[User](x))
          location <- m.get(locationKey).map(x => readBinary[Option[Location]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
          query <- m.get(queryKey).map(x => readBinary[String](x))
        } yield {
          ChosenInlineResult(
            resultId = resultId,
            from = from,
            location = location,
            inlineMessageId = inlineMessageId,
            query = query
          )
        }
        result.get
      }
    )
  }

  implicit lazy val botcommandCodec: ReadWriter[BotCommand] = {
    val commandKey = upack.Str("command")
    val descriptionKey = upack.Str("description")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          commandKey -> writeMsg(x.command),
          descriptionKey -> writeMsg(x.description)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          command <- m.get(commandKey).map(x => readBinary[String](x))
          description <- m.get(descriptionKey).map(x => readBinary[String](x))
        } yield {
          BotCommand(
            command = command,
            description = description
          )
        }
        result.get
      }
    )
  }

  implicit lazy val audioCodec: ReadWriter[Audio] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val durationKey = upack.Str("duration")
    val performerKey = upack.Str("performer")
    val titleKey = upack.Str("title")
    val fileNameKey = upack.Str("fileName")
    val mimeTypeKey = upack.Str("mimeType")
    val fileSizeKey = upack.Str("fileSize")
    val thumbKey = upack.Str("thumb")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          durationKey -> writeMsg(x.duration),
          performerKey -> writeMsg(x.performer),
          titleKey -> writeMsg(x.title),
          fileNameKey -> writeMsg(x.fileName),
          mimeTypeKey -> writeMsg(x.mimeType),
          fileSizeKey -> writeMsg(x.fileSize),
          thumbKey -> writeMsg(x.thumb)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          duration <- m.get(durationKey).map(x => readBinary[Int](x))
          performer <- m.get(performerKey).map(x => readBinary[Option[String]](x))
          title <- m.get(titleKey).map(x => readBinary[Option[String]](x))
          fileName <- m.get(fileNameKey).map(x => readBinary[Option[String]](x))
          mimeType <- m.get(mimeTypeKey).map(x => readBinary[Option[String]](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[PhotoSize]](x))
        } yield {
          Audio(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            duration = duration,
            performer = performer,
            title = title,
            fileName = fileName,
            mimeType = mimeType,
            fileSize = fileSize,
            thumb = thumb
          )
        }
        result.get
      }
    )
  }

  implicit lazy val webhookinfoCodec: ReadWriter[WebhookInfo] = {
    val urlKey = upack.Str("url")
    val hasCustomCertificateKey = upack.Str("hasCustomCertificate")
    val pendingUpdateCountKey = upack.Str("pendingUpdateCount")
    val ipAddressKey = upack.Str("ipAddress")
    val lastErrorDateKey = upack.Str("lastErrorDate")
    val lastErrorMessageKey = upack.Str("lastErrorMessage")
    val maxConnectionsKey = upack.Str("maxConnections")
    val allowedUpdatesKey = upack.Str("allowedUpdates")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          urlKey -> writeMsg(x.url),
          hasCustomCertificateKey -> writeMsg(x.hasCustomCertificate),
          pendingUpdateCountKey -> writeMsg(x.pendingUpdateCount),
          ipAddressKey -> writeMsg(x.ipAddress),
          lastErrorDateKey -> writeMsg(x.lastErrorDate),
          lastErrorMessageKey -> writeMsg(x.lastErrorMessage),
          maxConnectionsKey -> writeMsg(x.maxConnections),
          allowedUpdatesKey -> writeMsg(x.allowedUpdates)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          url <- m.get(urlKey).map(x => readBinary[String](x))
          hasCustomCertificate <- m.get(hasCustomCertificateKey).map(x => readBinary[Boolean](x))
          pendingUpdateCount <- m.get(pendingUpdateCountKey).map(x => readBinary[Int](x))
          ipAddress <- m.get(ipAddressKey).map(x => readBinary[Option[String]](x))
          lastErrorDate <- m.get(lastErrorDateKey).map(x => readBinary[Option[Int]](x))
          lastErrorMessage <- m.get(lastErrorMessageKey).map(x => readBinary[Option[String]](x))
          maxConnections <- m.get(maxConnectionsKey).map(x => readBinary[Option[Int]](x))
          allowedUpdates <- m.get(allowedUpdatesKey).map(x => readBinary[List[String]](x))
        } yield {
          WebhookInfo(
            url = url,
            hasCustomCertificate = hasCustomCertificate,
            pendingUpdateCount = pendingUpdateCount,
            ipAddress = ipAddress,
            lastErrorDate = lastErrorDate,
            lastErrorMessage = lastErrorMessage,
            maxConnections = maxConnections,
            allowedUpdates = allowedUpdates
          )
        }
        result.get
      }
    )
  }

  implicit lazy val proximityalerttriggeredCodec: ReadWriter[ProximityAlertTriggered] = {
    val travelerKey = upack.Str("traveler")
    val watcherKey = upack.Str("watcher")
    val distanceKey = upack.Str("distance")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          travelerKey -> writeMsg(x.traveler),
          watcherKey -> writeMsg(x.watcher),
          distanceKey -> writeMsg(x.distance)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          traveler <- m.get(travelerKey).map(x => readBinary[User](x))
          watcher <- m.get(watcherKey).map(x => readBinary[User](x))
          distance <- m.get(distanceKey).map(x => readBinary[Int](x))
        } yield {
          ProximityAlertTriggered(
            traveler = traveler,
            watcher = watcher,
            distance = distance
          )
        }
        result.get
      }
    )
  }

  implicit lazy val voicechatscheduledCodec: ReadWriter[VoiceChatScheduled] = {
    val startDateKey = upack.Str("startDate")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          startDateKey -> writeMsg(x.startDate)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          startDate <- m.get(startDateKey).map(x => readBinary[Int](x))
        } yield {
          VoiceChatScheduled(
            startDate = startDate
          )
        }
        result.get
      }
    )
  }

  implicit lazy val invoiceCodec: ReadWriter[Invoice] = {
    val titleKey = upack.Str("title")
    val descriptionKey = upack.Str("description")
    val startParameterKey = upack.Str("startParameter")
    val currencyKey = upack.Str("currency")
    val totalAmountKey = upack.Str("totalAmount")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          titleKey -> writeMsg(x.title),
          descriptionKey -> writeMsg(x.description),
          startParameterKey -> writeMsg(x.startParameter),
          currencyKey -> writeMsg(x.currency),
          totalAmountKey -> writeMsg(x.totalAmount)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          title <- m.get(titleKey).map(x => readBinary[String](x))
          description <- m.get(descriptionKey).map(x => readBinary[String](x))
          startParameter <- m.get(startParameterKey).map(x => readBinary[String](x))
          currency <- m.get(currencyKey).map(x => readBinary[String](x))
          totalAmount <- m.get(totalAmountKey).map(x => readBinary[Int](x))
        } yield {
          Invoice(
            title = title,
            description = description,
            startParameter = startParameter,
            currency = currency,
            totalAmount = totalAmount
          )
        }
        result.get
      }
    )
  }

  implicit lazy val chatphotoCodec: ReadWriter[ChatPhoto] = {
    val smallFileIdKey = upack.Str("smallFileId")
    val smallFileUniqueIdKey = upack.Str("smallFileUniqueId")
    val bigFileIdKey = upack.Str("bigFileId")
    val bigFileUniqueIdKey = upack.Str("bigFileUniqueId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          smallFileIdKey -> writeMsg(x.smallFileId),
          smallFileUniqueIdKey -> writeMsg(x.smallFileUniqueId),
          bigFileIdKey -> writeMsg(x.bigFileId),
          bigFileUniqueIdKey -> writeMsg(x.bigFileUniqueId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          smallFileId <- m.get(smallFileIdKey).map(x => readBinary[String](x))
          smallFileUniqueId <- m.get(smallFileUniqueIdKey).map(x => readBinary[String](x))
          bigFileId <- m.get(bigFileIdKey).map(x => readBinary[String](x))
          bigFileUniqueId <- m.get(bigFileUniqueIdKey).map(x => readBinary[String](x))
        } yield {
          ChatPhoto(
            smallFileId = smallFileId,
            smallFileUniqueId = smallFileUniqueId,
            bigFileId = bigFileId,
            bigFileUniqueId = bigFileUniqueId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinequeryCodec: ReadWriter[InlineQuery] = {
    val idKey = upack.Str("id")
    val fromKey = upack.Str("from")
    val queryKey = upack.Str("query")
    val offsetKey = upack.Str("offset")
    val chatTypeKey = upack.Str("chatType")
    val locationKey = upack.Str("location")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          fromKey -> writeMsg(x.from),
          queryKey -> writeMsg(x.query),
          offsetKey -> writeMsg(x.offset),
          chatTypeKey -> writeMsg(x.chatType),
          locationKey -> writeMsg(x.location)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          from <- m.get(fromKey).map(x => readBinary[User](x))
          query <- m.get(queryKey).map(x => readBinary[String](x))
          offset <- m.get(offsetKey).map(x => readBinary[String](x))
          chatType <- m.get(chatTypeKey).map(x => readBinary[Option[String]](x))
          location <- m.get(locationKey).map(x => readBinary[Option[Location]](x))
        } yield {
          InlineQuery(
            id = id,
            from = from,
            query = query,
            offset = offset,
            chatType = chatType,
            location = location
          )
        }
        result.get
      }
    )
  }

  implicit lazy val userCodec: ReadWriter[User] = {
    val idKey = upack.Str("id")
    val isBotKey = upack.Str("isBot")
    val firstNameKey = upack.Str("firstName")
    val lastNameKey = upack.Str("lastName")
    val usernameKey = upack.Str("username")
    val languageCodeKey = upack.Str("languageCode")
    val canJoinGroupsKey = upack.Str("canJoinGroups")
    val canReadAllGroupMessagesKey = upack.Str("canReadAllGroupMessages")
    val supportsInlineQueriesKey = upack.Str("supportsInlineQueries")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          isBotKey -> writeMsg(x.isBot),
          firstNameKey -> writeMsg(x.firstName),
          lastNameKey -> writeMsg(x.lastName),
          usernameKey -> writeMsg(x.username),
          languageCodeKey -> writeMsg(x.languageCode),
          canJoinGroupsKey -> writeMsg(x.canJoinGroups),
          canReadAllGroupMessagesKey -> writeMsg(x.canReadAllGroupMessages),
          supportsInlineQueriesKey -> writeMsg(x.supportsInlineQueries)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[Long](x))
          isBot <- m.get(isBotKey).map(x => readBinary[Boolean](x))
          firstName <- m.get(firstNameKey).map(x => readBinary[String](x))
          lastName <- m.get(lastNameKey).map(x => readBinary[Option[String]](x))
          username <- m.get(usernameKey).map(x => readBinary[Option[String]](x))
          languageCode <- m.get(languageCodeKey).map(x => readBinary[Option[String]](x))
          canJoinGroups <- m.get(canJoinGroupsKey).map(x => readBinary[Option[Boolean]](x))
          canReadAllGroupMessages <- m
            .get(canReadAllGroupMessagesKey)
            .map(x => readBinary[Option[Boolean]](x))
          supportsInlineQueries <- m
            .get(supportsInlineQueriesKey)
            .map(x => readBinary[Option[Boolean]](x))
        } yield {
          User(
            id = id,
            isBot = isBot,
            firstName = firstName,
            lastName = lastName,
            username = username,
            languageCode = languageCode,
            canJoinGroups = canJoinGroups,
            canReadAllGroupMessages = canReadAllGroupMessages,
            supportsInlineQueries = supportsInlineQueries
          )
        }
        result.get
      }
    )
  }

  implicit lazy val encryptedpassportelementCodec: ReadWriter[EncryptedPassportElement] = {
    val typeKey = upack.Str("type")
    val dataKey = upack.Str("data")
    val phoneNumberKey = upack.Str("phoneNumber")
    val emailKey = upack.Str("email")
    val filesKey = upack.Str("files")
    val frontSideKey = upack.Str("frontSide")
    val reverseSideKey = upack.Str("reverseSide")
    val selfieKey = upack.Str("selfie")
    val translationKey = upack.Str("translation")
    val hashKey = upack.Str("hash")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          typeKey -> writeMsg(x.`type`),
          dataKey -> writeMsg(x.data),
          phoneNumberKey -> writeMsg(x.phoneNumber),
          emailKey -> writeMsg(x.email),
          filesKey -> writeMsg(x.files),
          frontSideKey -> writeMsg(x.frontSide),
          reverseSideKey -> writeMsg(x.reverseSide),
          selfieKey -> writeMsg(x.selfie),
          translationKey -> writeMsg(x.translation),
          hashKey -> writeMsg(x.hash)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          `type` <- m.get(typeKey).map(x => readBinary[String](x))
          data <- m.get(dataKey).map(x => readBinary[Option[String]](x))
          phoneNumber <- m.get(phoneNumberKey).map(x => readBinary[Option[String]](x))
          email <- m.get(emailKey).map(x => readBinary[Option[String]](x))
          files <- m.get(filesKey).map(x => readBinary[List[PassportFile]](x))
          frontSide <- m.get(frontSideKey).map(x => readBinary[Option[PassportFile]](x))
          reverseSide <- m.get(reverseSideKey).map(x => readBinary[Option[PassportFile]](x))
          selfie <- m.get(selfieKey).map(x => readBinary[Option[PassportFile]](x))
          translation <- m.get(translationKey).map(x => readBinary[List[PassportFile]](x))
          hash <- m.get(hashKey).map(x => readBinary[String](x))
        } yield {
          EncryptedPassportElement(
            `type` = `type`,
            data = data,
            phoneNumber = phoneNumber,
            email = email,
            files = files,
            frontSide = frontSide,
            reverseSide = reverseSide,
            selfie = selfie,
            translation = translation,
            hash = hash
          )
        }
        result.get
      }
    )
  }

  implicit lazy val stickerCodec: ReadWriter[Sticker] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val widthKey = upack.Str("width")
    val heightKey = upack.Str("height")
    val isAnimatedKey = upack.Str("isAnimated")
    val thumbKey = upack.Str("thumb")
    val emojiKey = upack.Str("emoji")
    val setNameKey = upack.Str("setName")
    val maskPositionKey = upack.Str("maskPosition")
    val fileSizeKey = upack.Str("fileSize")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          widthKey -> writeMsg(x.width),
          heightKey -> writeMsg(x.height),
          isAnimatedKey -> writeMsg(x.isAnimated),
          thumbKey -> writeMsg(x.thumb),
          emojiKey -> writeMsg(x.emoji),
          setNameKey -> writeMsg(x.setName),
          maskPositionKey -> writeMsg(x.maskPosition),
          fileSizeKey -> writeMsg(x.fileSize)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          width <- m.get(widthKey).map(x => readBinary[Int](x))
          height <- m.get(heightKey).map(x => readBinary[Int](x))
          isAnimated <- m.get(isAnimatedKey).map(x => readBinary[Boolean](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[PhotoSize]](x))
          emoji <- m.get(emojiKey).map(x => readBinary[Option[Emoji]](x))
          setName <- m.get(setNameKey).map(x => readBinary[Option[String]](x))
          maskPosition <- m.get(maskPositionKey).map(x => readBinary[Option[MaskPosition]](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
        } yield {
          Sticker(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            width = width,
            height = height,
            isAnimated = isAnimated,
            thumb = thumb,
            emoji = emoji,
            setName = setName,
            maskPosition = maskPosition,
            fileSize = fileSize
          )
        }
        result.get
      }
    )
  }

  implicit lazy val messageCodec: ReadWriter[Message] = {
    val messageIdKey = upack.Str("messageId")
    val fromKey = upack.Str("from")
    val senderChatKey = upack.Str("senderChat")
    val dateKey = upack.Str("date")
    val chatKey = upack.Str("chat")
    val forwardFromKey = upack.Str("forwardFrom")
    val forwardFromChatKey = upack.Str("forwardFromChat")
    val forwardFromMessageIdKey = upack.Str("forwardFromMessageId")
    val forwardSignatureKey = upack.Str("forwardSignature")
    val forwardSenderNameKey = upack.Str("forwardSenderName")
    val forwardDateKey = upack.Str("forwardDate")
    val replyToMessageKey = upack.Str("replyToMessage")
    val viaBotKey = upack.Str("viaBot")
    val editDateKey = upack.Str("editDate")
    val mediaGroupIdKey = upack.Str("mediaGroupId")
    val authorSignatureKey = upack.Str("authorSignature")
    val textKey = upack.Str("text")
    val entitiesKey = upack.Str("entities")
    val animationKey = upack.Str("animation")
    val audioKey = upack.Str("audio")
    val documentKey = upack.Str("document")
    val photoKey = upack.Str("photo")
    val stickerKey = upack.Str("sticker")
    val videoKey = upack.Str("video")
    val videoNoteKey = upack.Str("videoNote")
    val voiceKey = upack.Str("voice")
    val captionKey = upack.Str("caption")
    val captionEntitiesKey = upack.Str("captionEntities")
    val contactKey = upack.Str("contact")
    val diceKey = upack.Str("dice")
    val gameKey = upack.Str("game")
    val pollKey = upack.Str("poll")
    val venueKey = upack.Str("venue")
    val locationKey = upack.Str("location")
    val newChatMembersKey = upack.Str("newChatMembers")
    val leftChatMemberKey = upack.Str("leftChatMember")
    val newChatTitleKey = upack.Str("newChatTitle")
    val newChatPhotoKey = upack.Str("newChatPhoto")
    val deleteChatPhotoKey = upack.Str("deleteChatPhoto")
    val groupChatCreatedKey = upack.Str("groupChatCreated")
    val supergroupChatCreatedKey = upack.Str("supergroupChatCreated")
    val channelChatCreatedKey = upack.Str("channelChatCreated")
    val messageAutoDeleteTimerChangedKey = upack.Str("messageAutoDeleteTimerChanged")
    val migrateToChatIdKey = upack.Str("migrateToChatId")
    val migrateFromChatIdKey = upack.Str("migrateFromChatId")
    val pinnedMessageKey = upack.Str("pinnedMessage")
    val invoiceKey = upack.Str("invoice")
    val successfulPaymentKey = upack.Str("successfulPayment")
    val connectedWebsiteKey = upack.Str("connectedWebsite")
    val passportDataKey = upack.Str("passportData")
    val proximityAlertTriggeredKey = upack.Str("proximityAlertTriggered")
    val voiceChatScheduledKey = upack.Str("voiceChatScheduled")
    val voiceChatStartedKey = upack.Str("voiceChatStarted")
    val voiceChatEndedKey = upack.Str("voiceChatEnded")
    val voiceChatParticipantsInvitedKey = upack.Str("voiceChatParticipantsInvited")
    val replyMarkupKey = upack.Str("replyMarkup")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          messageIdKey -> writeMsg(x.messageId),
          fromKey -> writeMsg(x.from),
          senderChatKey -> writeMsg(x.senderChat),
          dateKey -> writeMsg(x.date),
          chatKey -> writeMsg(x.chat),
          forwardFromKey -> writeMsg(x.forwardFrom),
          forwardFromChatKey -> writeMsg(x.forwardFromChat),
          forwardFromMessageIdKey -> writeMsg(x.forwardFromMessageId),
          forwardSignatureKey -> writeMsg(x.forwardSignature),
          forwardSenderNameKey -> writeMsg(x.forwardSenderName),
          forwardDateKey -> writeMsg(x.forwardDate),
          replyToMessageKey -> writeMsg(x.replyToMessage),
          viaBotKey -> writeMsg(x.viaBot),
          editDateKey -> writeMsg(x.editDate),
          mediaGroupIdKey -> writeMsg(x.mediaGroupId),
          authorSignatureKey -> writeMsg(x.authorSignature),
          textKey -> writeMsg(x.text),
          entitiesKey -> writeMsg(x.entities),
          animationKey -> writeMsg(x.animation),
          audioKey -> writeMsg(x.audio),
          documentKey -> writeMsg(x.document),
          photoKey -> writeMsg(x.photo),
          stickerKey -> writeMsg(x.sticker),
          videoKey -> writeMsg(x.video),
          videoNoteKey -> writeMsg(x.videoNote),
          voiceKey -> writeMsg(x.voice),
          captionKey -> writeMsg(x.caption),
          captionEntitiesKey -> writeMsg(x.captionEntities),
          contactKey -> writeMsg(x.contact),
          diceKey -> writeMsg(x.dice),
          gameKey -> writeMsg(x.game),
          pollKey -> writeMsg(x.poll),
          venueKey -> writeMsg(x.venue),
          locationKey -> writeMsg(x.location),
          newChatMembersKey -> writeMsg(x.newChatMembers),
          leftChatMemberKey -> writeMsg(x.leftChatMember),
          newChatTitleKey -> writeMsg(x.newChatTitle),
          newChatPhotoKey -> writeMsg(x.newChatPhoto),
          deleteChatPhotoKey -> writeMsg(x.deleteChatPhoto),
          groupChatCreatedKey -> writeMsg(x.groupChatCreated),
          supergroupChatCreatedKey -> writeMsg(x.supergroupChatCreated),
          channelChatCreatedKey -> writeMsg(x.channelChatCreated),
          messageAutoDeleteTimerChangedKey -> writeMsg(x.messageAutoDeleteTimerChanged),
          migrateToChatIdKey -> writeMsg(x.migrateToChatId),
          migrateFromChatIdKey -> writeMsg(x.migrateFromChatId),
          pinnedMessageKey -> writeMsg(x.pinnedMessage),
          invoiceKey -> writeMsg(x.invoice),
          successfulPaymentKey -> writeMsg(x.successfulPayment),
          connectedWebsiteKey -> writeMsg(x.connectedWebsite),
          passportDataKey -> writeMsg(x.passportData),
          proximityAlertTriggeredKey -> writeMsg(x.proximityAlertTriggered),
          voiceChatScheduledKey -> writeMsg(x.voiceChatScheduled),
          voiceChatStartedKey -> writeMsg(x.voiceChatStarted),
          voiceChatEndedKey -> writeMsg(x.voiceChatEnded),
          voiceChatParticipantsInvitedKey -> writeMsg(x.voiceChatParticipantsInvited),
          replyMarkupKey -> writeMsg(x.replyMarkup)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          messageId <- m.get(messageIdKey).map(x => readBinary[Int](x))
          from <- m.get(fromKey).map(x => readBinary[Option[User]](x))
          senderChat <- m.get(senderChatKey).map(x => readBinary[Option[Chat]](x))
          date <- m.get(dateKey).map(x => readBinary[Int](x))
          chat <- m.get(chatKey).map(x => readBinary[Chat](x))
          forwardFrom <- m.get(forwardFromKey).map(x => readBinary[Option[User]](x))
          forwardFromChat <- m.get(forwardFromChatKey).map(x => readBinary[Option[Chat]](x))
          forwardFromMessageId <- m
            .get(forwardFromMessageIdKey)
            .map(x => readBinary[Option[Int]](x))
          forwardSignature <- m.get(forwardSignatureKey).map(x => readBinary[Option[String]](x))
          forwardSenderName <- m.get(forwardSenderNameKey).map(x => readBinary[Option[String]](x))
          forwardDate <- m.get(forwardDateKey).map(x => readBinary[Option[Int]](x))
          replyToMessage <- m.get(replyToMessageKey).map(x => readBinary[Option[Message]](x))
          viaBot <- m.get(viaBotKey).map(x => readBinary[Option[User]](x))
          editDate <- m.get(editDateKey).map(x => readBinary[Option[Int]](x))
          mediaGroupId <- m.get(mediaGroupIdKey).map(x => readBinary[Option[String]](x))
          authorSignature <- m.get(authorSignatureKey).map(x => readBinary[Option[String]](x))
          text <- m.get(textKey).map(x => readBinary[Option[String]](x))
          entities <- m.get(entitiesKey).map(x => readBinary[List[MessageEntity]](x))
          animation <- m.get(animationKey).map(x => readBinary[Option[Animation]](x))
          audio <- m.get(audioKey).map(x => readBinary[Option[Audio]](x))
          document <- m.get(documentKey).map(x => readBinary[Option[Document]](x))
          photo <- m.get(photoKey).map(x => readBinary[List[PhotoSize]](x))
          sticker <- m.get(stickerKey).map(x => readBinary[Option[Sticker]](x))
          video <- m.get(videoKey).map(x => readBinary[Option[Video]](x))
          videoNote <- m.get(videoNoteKey).map(x => readBinary[Option[VideoNote]](x))
          voice <- m.get(voiceKey).map(x => readBinary[Option[Voice]](x))
          caption <- m.get(captionKey).map(x => readBinary[Option[String]](x))
          captionEntities <- m.get(captionEntitiesKey).map(x => readBinary[List[MessageEntity]](x))
          contact <- m.get(contactKey).map(x => readBinary[Option[Contact]](x))
          dice <- m.get(diceKey).map(x => readBinary[Option[Dice]](x))
          game <- m.get(gameKey).map(x => readBinary[Option[Game]](x))
          poll <- m.get(pollKey).map(x => readBinary[Option[Poll]](x))
          venue <- m.get(venueKey).map(x => readBinary[Option[Venue]](x))
          location <- m.get(locationKey).map(x => readBinary[Option[Location]](x))
          newChatMembers <- m.get(newChatMembersKey).map(x => readBinary[List[User]](x))
          leftChatMember <- m.get(leftChatMemberKey).map(x => readBinary[Option[User]](x))
          newChatTitle <- m.get(newChatTitleKey).map(x => readBinary[Option[String]](x))
          newChatPhoto <- m.get(newChatPhotoKey).map(x => readBinary[List[PhotoSize]](x))
          deleteChatPhoto <- m.get(deleteChatPhotoKey).map(x => readBinary[Option[Boolean]](x))
          groupChatCreated <- m.get(groupChatCreatedKey).map(x => readBinary[Option[Boolean]](x))
          supergroupChatCreated <- m
            .get(supergroupChatCreatedKey)
            .map(x => readBinary[Option[Boolean]](x))
          channelChatCreated <- m
            .get(channelChatCreatedKey)
            .map(x => readBinary[Option[Boolean]](x))
          messageAutoDeleteTimerChanged <- m
            .get(messageAutoDeleteTimerChangedKey)
            .map(x => readBinary[Option[MessageAutoDeleteTimerChanged]](x))
          migrateToChatId <- m.get(migrateToChatIdKey).map(x => readBinary[Option[Long]](x))
          migrateFromChatId <- m.get(migrateFromChatIdKey).map(x => readBinary[Option[Long]](x))
          pinnedMessage <- m.get(pinnedMessageKey).map(x => readBinary[Option[Message]](x))
          invoice <- m.get(invoiceKey).map(x => readBinary[Option[Invoice]](x))
          successfulPayment <- m
            .get(successfulPaymentKey)
            .map(x => readBinary[Option[SuccessfulPayment]](x))
          connectedWebsite <- m.get(connectedWebsiteKey).map(x => readBinary[Option[String]](x))
          passportData <- m.get(passportDataKey).map(x => readBinary[Option[PassportData]](x))
          proximityAlertTriggered <- m
            .get(proximityAlertTriggeredKey)
            .map(x => readBinary[Option[ProximityAlertTriggered]](x))
          voiceChatScheduled <- m
            .get(voiceChatScheduledKey)
            .map(x => readBinary[Option[VoiceChatScheduled]](x))
          voiceChatStarted <- m
            .get(voiceChatStartedKey)
            .map(x => readBinary[Option[VoiceChatStarted.type]](x))
          voiceChatEnded <- m.get(voiceChatEndedKey).map(x => readBinary[Option[VoiceChatEnded]](x))
          voiceChatParticipantsInvited <- m
            .get(voiceChatParticipantsInvitedKey)
            .map(x => readBinary[Option[VoiceChatParticipantsInvited]](x))
          replyMarkup <- m.get(replyMarkupKey).map(x => readBinary[Option[InlineKeyboardMarkup]](x))
        } yield {
          Message(
            messageId = messageId,
            from = from,
            senderChat = senderChat,
            date = date,
            chat = chat,
            forwardFrom = forwardFrom,
            forwardFromChat = forwardFromChat,
            forwardFromMessageId = forwardFromMessageId,
            forwardSignature = forwardSignature,
            forwardSenderName = forwardSenderName,
            forwardDate = forwardDate,
            replyToMessage = replyToMessage,
            viaBot = viaBot,
            editDate = editDate,
            mediaGroupId = mediaGroupId,
            authorSignature = authorSignature,
            text = text,
            entities = entities,
            animation = animation,
            audio = audio,
            document = document,
            photo = photo,
            sticker = sticker,
            video = video,
            videoNote = videoNote,
            voice = voice,
            caption = caption,
            captionEntities = captionEntities,
            contact = contact,
            dice = dice,
            game = game,
            poll = poll,
            venue = venue,
            location = location,
            newChatMembers = newChatMembers,
            leftChatMember = leftChatMember,
            newChatTitle = newChatTitle,
            newChatPhoto = newChatPhoto,
            deleteChatPhoto = deleteChatPhoto,
            groupChatCreated = groupChatCreated,
            supergroupChatCreated = supergroupChatCreated,
            channelChatCreated = channelChatCreated,
            messageAutoDeleteTimerChanged = messageAutoDeleteTimerChanged,
            migrateToChatId = migrateToChatId,
            migrateFromChatId = migrateFromChatId,
            pinnedMessage = pinnedMessage,
            invoice = invoice,
            successfulPayment = successfulPayment,
            connectedWebsite = connectedWebsite,
            passportData = passportData,
            proximityAlertTriggered = proximityAlertTriggered,
            voiceChatScheduled = voiceChatScheduled,
            voiceChatStarted = voiceChatStarted,
            voiceChatEnded = voiceChatEnded,
            voiceChatParticipantsInvited = voiceChatParticipantsInvited,
            replyMarkup = replyMarkup
          )
        }
        result.get
      }
    )
  }

  implicit lazy val shippingoptionCodec: ReadWriter[ShippingOption] = {
    val idKey = upack.Str("id")
    val titleKey = upack.Str("title")
    val pricesKey = upack.Str("prices")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          titleKey -> writeMsg(x.title),
          pricesKey -> writeMsg(x.prices)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          title <- m.get(titleKey).map(x => readBinary[String](x))
          prices <- m.get(pricesKey).map(x => readBinary[List[LabeledPrice]](x))
        } yield {
          ShippingOption(
            id = id,
            title = title,
            prices = prices
          )
        }
        result.get
      }
    )
  }

  implicit lazy val precheckoutqueryCodec: ReadWriter[PreCheckoutQuery] = {
    val idKey = upack.Str("id")
    val fromKey = upack.Str("from")
    val currencyKey = upack.Str("currency")
    val totalAmountKey = upack.Str("totalAmount")
    val invoicePayloadKey = upack.Str("invoicePayload")
    val shippingOptionIdKey = upack.Str("shippingOptionId")
    val orderInfoKey = upack.Str("orderInfo")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          fromKey -> writeMsg(x.from),
          currencyKey -> writeMsg(x.currency),
          totalAmountKey -> writeMsg(x.totalAmount),
          invoicePayloadKey -> writeMsg(x.invoicePayload),
          shippingOptionIdKey -> writeMsg(x.shippingOptionId),
          orderInfoKey -> writeMsg(x.orderInfo)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          from <- m.get(fromKey).map(x => readBinary[User](x))
          currency <- m.get(currencyKey).map(x => readBinary[String](x))
          totalAmount <- m.get(totalAmountKey).map(x => readBinary[Int](x))
          invoicePayload <- m.get(invoicePayloadKey).map(x => readBinary[String](x))
          shippingOptionId <- m.get(shippingOptionIdKey).map(x => readBinary[Option[String]](x))
          orderInfo <- m.get(orderInfoKey).map(x => readBinary[Option[OrderInfo]](x))
        } yield {
          PreCheckoutQuery(
            id = id,
            from = from,
            currency = currency,
            totalAmount = totalAmount,
            invoicePayload = invoicePayload,
            shippingOptionId = shippingOptionId,
            orderInfo = orderInfo
          )
        }
        result.get
      }
    )
  }

  implicit lazy val voicechatstartedCodec: ReadWriter[VoiceChatStarted.type] = macroRW

  implicit lazy val encryptedcredentialsCodec: ReadWriter[EncryptedCredentials] = {
    val dataKey = upack.Str("data")
    val hashKey = upack.Str("hash")
    val secretKey = upack.Str("secret")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          dataKey -> writeMsg(x.data),
          hashKey -> writeMsg(x.hash),
          secretKey -> writeMsg(x.secret)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          data <- m.get(dataKey).map(x => readBinary[String](x))
          hash <- m.get(hashKey).map(x => readBinary[String](x))
          secret <- m.get(secretKey).map(x => readBinary[String](x))
        } yield {
          EncryptedCredentials(
            data = data,
            hash = hash,
            secret = secret
          )
        }
        result.get
      }
    )
  }

  implicit lazy val inlinekeyboardbuttonCodec: ReadWriter[InlineKeyboardButton] = {
    val textKey = upack.Str("text")
    val urlKey = upack.Str("url")
    val loginUrlKey = upack.Str("loginUrl")
    val callbackDataKey = upack.Str("callbackData")
    val switchInlineQueryKey = upack.Str("switchInlineQuery")
    val switchInlineQueryCurrentChatKey = upack.Str("switchInlineQueryCurrentChat")
    val callbackGameKey = upack.Str("callbackGame")
    val payKey = upack.Str("pay")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          textKey -> writeMsg(x.text),
          urlKey -> writeMsg(x.url),
          loginUrlKey -> writeMsg(x.loginUrl),
          callbackDataKey -> writeMsg(x.callbackData),
          switchInlineQueryKey -> writeMsg(x.switchInlineQuery),
          switchInlineQueryCurrentChatKey -> writeMsg(x.switchInlineQueryCurrentChat),
          callbackGameKey -> writeMsg(x.callbackGame),
          payKey -> writeMsg(x.pay)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          text <- m.get(textKey).map(x => readBinary[String](x))
          url <- m.get(urlKey).map(x => readBinary[Option[String]](x))
          loginUrl <- m.get(loginUrlKey).map(x => readBinary[Option[LoginUrl]](x))
          callbackData <- m.get(callbackDataKey).map(x => readBinary[Option[String]](x))
          switchInlineQuery <- m.get(switchInlineQueryKey).map(x => readBinary[Option[String]](x))
          switchInlineQueryCurrentChat <- m
            .get(switchInlineQueryCurrentChatKey)
            .map(x => readBinary[Option[String]](x))
          callbackGame <- m.get(callbackGameKey).map(x => readBinary[Option[String]](x))
          pay <- m.get(payKey).map(x => readBinary[Option[Boolean]](x))
        } yield {
          InlineKeyboardButton(
            text = text,
            url = url,
            loginUrl = loginUrl,
            callbackData = callbackData,
            switchInlineQuery = switchInlineQuery,
            switchInlineQueryCurrentChat = switchInlineQueryCurrentChat,
            callbackGame = callbackGame,
            pay = pay
          )
        }
        result.get
      }
    )
  }

  implicit lazy val loginurlCodec: ReadWriter[LoginUrl] = {
    val urlKey = upack.Str("url")
    val forwardTextKey = upack.Str("forwardText")
    val botUsernameKey = upack.Str("botUsername")
    val requestWriteAccessKey = upack.Str("requestWriteAccess")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          urlKey -> writeMsg(x.url),
          forwardTextKey -> writeMsg(x.forwardText),
          botUsernameKey -> writeMsg(x.botUsername),
          requestWriteAccessKey -> writeMsg(x.requestWriteAccess)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          url <- m.get(urlKey).map(x => readBinary[String](x))
          forwardText <- m.get(forwardTextKey).map(x => readBinary[Option[String]](x))
          botUsername <- m.get(botUsernameKey).map(x => readBinary[Option[String]](x))
          requestWriteAccess <- m
            .get(requestWriteAccessKey)
            .map(x => readBinary[Option[Boolean]](x))
        } yield {
          LoginUrl(
            url = url,
            forwardText = forwardText,
            botUsername = botUsername,
            requestWriteAccess = requestWriteAccess
          )
        }
        result.get
      }
    )
  }

  implicit lazy val voiceCodec: ReadWriter[Voice] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val durationKey = upack.Str("duration")
    val mimeTypeKey = upack.Str("mimeType")
    val fileSizeKey = upack.Str("fileSize")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          durationKey -> writeMsg(x.duration),
          mimeTypeKey -> writeMsg(x.mimeType),
          fileSizeKey -> writeMsg(x.fileSize)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          duration <- m.get(durationKey).map(x => readBinary[Int](x))
          mimeType <- m.get(mimeTypeKey).map(x => readBinary[Option[String]](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
        } yield {
          Voice(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            duration = duration,
            mimeType = mimeType,
            fileSize = fileSize
          )
        }
        result.get
      }
    )
  }

  implicit lazy val voicechatparticipantsinvitedCodec: ReadWriter[VoiceChatParticipantsInvited] = {
    val usersKey = upack.Str("users")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          usersKey -> writeMsg(x.users)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          users <- m.get(usersKey).map(x => readBinary[List[User]](x))
        } yield {
          VoiceChatParticipantsInvited(
            users = users
          )
        }
        result.get
      }
    )
  }

  implicit lazy val userprofilephotosCodec: ReadWriter[UserProfilePhotos] = {
    val totalCountKey = upack.Str("totalCount")
    val photosKey = upack.Str("photos")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          totalCountKey -> writeMsg(x.totalCount),
          photosKey -> writeMsg(x.photos)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          totalCount <- m.get(totalCountKey).map(x => readBinary[Int](x))
          photos <- m.get(photosKey).map(x => readBinary[List[List[PhotoSize]]](x))
        } yield {
          UserProfilePhotos(
            totalCount = totalCount,
            photos = photos
          )
        }
        result.get
      }
    )
  }

  implicit lazy val callbackqueryCodec: ReadWriter[CallbackQuery] = {
    val idKey = upack.Str("id")
    val fromKey = upack.Str("from")
    val messageKey = upack.Str("message")
    val inlineMessageIdKey = upack.Str("inlineMessageId")
    val chatInstanceKey = upack.Str("chatInstance")
    val dataKey = upack.Str("data")
    val gameShortNameKey = upack.Str("gameShortName")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          idKey -> writeMsg(x.id),
          fromKey -> writeMsg(x.from),
          messageKey -> writeMsg(x.message),
          inlineMessageIdKey -> writeMsg(x.inlineMessageId),
          chatInstanceKey -> writeMsg(x.chatInstance),
          dataKey -> writeMsg(x.data),
          gameShortNameKey -> writeMsg(x.gameShortName)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          id <- m.get(idKey).map(x => readBinary[String](x))
          from <- m.get(fromKey).map(x => readBinary[User](x))
          message <- m.get(messageKey).map(x => readBinary[Option[Message]](x))
          inlineMessageId <- m.get(inlineMessageIdKey).map(x => readBinary[Option[String]](x))
          chatInstance <- m.get(chatInstanceKey).map(x => readBinary[String](x))
          data <- m.get(dataKey).map(x => readBinary[Option[String]](x))
          gameShortName <- m.get(gameShortNameKey).map(x => readBinary[Option[String]](x))
        } yield {
          CallbackQuery(
            id = id,
            from = from,
            message = message,
            inlineMessageId = inlineMessageId,
            chatInstance = chatInstance,
            data = data,
            gameShortName = gameShortName
          )
        }
        result.get
      }
    )
  }

  implicit lazy val messageidCodec: ReadWriter[MessageId] = {
    val messageIdKey = upack.Str("messageId")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          messageIdKey -> writeMsg(x.messageId)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          messageId <- m.get(messageIdKey).map(x => readBinary[Int](x))
        } yield {
          MessageId(
            messageId = messageId
          )
        }
        result.get
      }
    )
  }

  implicit lazy val chatmemberCodec: ReadWriter[ChatMember] = {
    val userKey = upack.Str("user")
    val statusKey = upack.Str("status")
    val customTitleKey = upack.Str("customTitle")
    val isAnonymousKey = upack.Str("isAnonymous")
    val canBeEditedKey = upack.Str("canBeEdited")
    val canManageChatKey = upack.Str("canManageChat")
    val canPostMessagesKey = upack.Str("canPostMessages")
    val canEditMessagesKey = upack.Str("canEditMessages")
    val canDeleteMessagesKey = upack.Str("canDeleteMessages")
    val canManageVoiceChatsKey = upack.Str("canManageVoiceChats")
    val canRestrictMembersKey = upack.Str("canRestrictMembers")
    val canPromoteMembersKey = upack.Str("canPromoteMembers")
    val canChangeInfoKey = upack.Str("canChangeInfo")
    val canInviteUsersKey = upack.Str("canInviteUsers")
    val canPinMessagesKey = upack.Str("canPinMessages")
    val isMemberKey = upack.Str("isMember")
    val canSendMessagesKey = upack.Str("canSendMessages")
    val canSendMediaMessagesKey = upack.Str("canSendMediaMessages")
    val canSendPollsKey = upack.Str("canSendPolls")
    val canSendOtherMessagesKey = upack.Str("canSendOtherMessages")
    val canAddWebPagePreviewsKey = upack.Str("canAddWebPagePreviews")
    val untilDateKey = upack.Str("untilDate")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          userKey -> writeMsg(x.user),
          statusKey -> writeMsg(x.status),
          customTitleKey -> writeMsg(x.customTitle),
          isAnonymousKey -> writeMsg(x.isAnonymous),
          canBeEditedKey -> writeMsg(x.canBeEdited),
          canManageChatKey -> writeMsg(x.canManageChat),
          canPostMessagesKey -> writeMsg(x.canPostMessages),
          canEditMessagesKey -> writeMsg(x.canEditMessages),
          canDeleteMessagesKey -> writeMsg(x.canDeleteMessages),
          canManageVoiceChatsKey -> writeMsg(x.canManageVoiceChats),
          canRestrictMembersKey -> writeMsg(x.canRestrictMembers),
          canPromoteMembersKey -> writeMsg(x.canPromoteMembers),
          canChangeInfoKey -> writeMsg(x.canChangeInfo),
          canInviteUsersKey -> writeMsg(x.canInviteUsers),
          canPinMessagesKey -> writeMsg(x.canPinMessages),
          isMemberKey -> writeMsg(x.isMember),
          canSendMessagesKey -> writeMsg(x.canSendMessages),
          canSendMediaMessagesKey -> writeMsg(x.canSendMediaMessages),
          canSendPollsKey -> writeMsg(x.canSendPolls),
          canSendOtherMessagesKey -> writeMsg(x.canSendOtherMessages),
          canAddWebPagePreviewsKey -> writeMsg(x.canAddWebPagePreviews),
          untilDateKey -> writeMsg(x.untilDate)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          user <- m.get(userKey).map(x => readBinary[User](x))
          status <- m.get(statusKey).map(x => readBinary[String](x))
          customTitle <- m.get(customTitleKey).map(x => readBinary[Option[String]](x))
          isAnonymous <- m.get(isAnonymousKey).map(x => readBinary[Option[Boolean]](x))
          canBeEdited <- m.get(canBeEditedKey).map(x => readBinary[Option[Boolean]](x))
          canManageChat <- m.get(canManageChatKey).map(x => readBinary[Option[Boolean]](x))
          canPostMessages <- m.get(canPostMessagesKey).map(x => readBinary[Option[Boolean]](x))
          canEditMessages <- m.get(canEditMessagesKey).map(x => readBinary[Option[Boolean]](x))
          canDeleteMessages <- m.get(canDeleteMessagesKey).map(x => readBinary[Option[Boolean]](x))
          canManageVoiceChats <- m
            .get(canManageVoiceChatsKey)
            .map(x => readBinary[Option[Boolean]](x))
          canRestrictMembers <- m
            .get(canRestrictMembersKey)
            .map(x => readBinary[Option[Boolean]](x))
          canPromoteMembers <- m.get(canPromoteMembersKey).map(x => readBinary[Option[Boolean]](x))
          canChangeInfo <- m.get(canChangeInfoKey).map(x => readBinary[Option[Boolean]](x))
          canInviteUsers <- m.get(canInviteUsersKey).map(x => readBinary[Option[Boolean]](x))
          canPinMessages <- m.get(canPinMessagesKey).map(x => readBinary[Option[Boolean]](x))
          isMember <- m.get(isMemberKey).map(x => readBinary[Option[Boolean]](x))
          canSendMessages <- m.get(canSendMessagesKey).map(x => readBinary[Option[Boolean]](x))
          canSendMediaMessages <- m
            .get(canSendMediaMessagesKey)
            .map(x => readBinary[Option[Boolean]](x))
          canSendPolls <- m.get(canSendPollsKey).map(x => readBinary[Option[Boolean]](x))
          canSendOtherMessages <- m
            .get(canSendOtherMessagesKey)
            .map(x => readBinary[Option[Boolean]](x))
          canAddWebPagePreviews <- m
            .get(canAddWebPagePreviewsKey)
            .map(x => readBinary[Option[Boolean]](x))
          untilDate <- m.get(untilDateKey).map(x => readBinary[Option[Int]](x))
        } yield {
          ChatMember(
            user = user,
            status = status,
            customTitle = customTitle,
            isAnonymous = isAnonymous,
            canBeEdited = canBeEdited,
            canManageChat = canManageChat,
            canPostMessages = canPostMessages,
            canEditMessages = canEditMessages,
            canDeleteMessages = canDeleteMessages,
            canManageVoiceChats = canManageVoiceChats,
            canRestrictMembers = canRestrictMembers,
            canPromoteMembers = canPromoteMembers,
            canChangeInfo = canChangeInfo,
            canInviteUsers = canInviteUsers,
            canPinMessages = canPinMessages,
            isMember = isMember,
            canSendMessages = canSendMessages,
            canSendMediaMessages = canSendMediaMessages,
            canSendPolls = canSendPolls,
            canSendOtherMessages = canSendOtherMessages,
            canAddWebPagePreviews = canAddWebPagePreviews,
            untilDate = untilDate
          )
        }
        result.get
      }
    )
  }

  implicit lazy val videoCodec: ReadWriter[Video] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val widthKey = upack.Str("width")
    val heightKey = upack.Str("height")
    val durationKey = upack.Str("duration")
    val thumbKey = upack.Str("thumb")
    val fileNameKey = upack.Str("fileName")
    val mimeTypeKey = upack.Str("mimeType")
    val fileSizeKey = upack.Str("fileSize")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          widthKey -> writeMsg(x.width),
          heightKey -> writeMsg(x.height),
          durationKey -> writeMsg(x.duration),
          thumbKey -> writeMsg(x.thumb),
          fileNameKey -> writeMsg(x.fileName),
          mimeTypeKey -> writeMsg(x.mimeType),
          fileSizeKey -> writeMsg(x.fileSize)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          width <- m.get(widthKey).map(x => readBinary[Int](x))
          height <- m.get(heightKey).map(x => readBinary[Int](x))
          duration <- m.get(durationKey).map(x => readBinary[Int](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[PhotoSize]](x))
          fileName <- m.get(fileNameKey).map(x => readBinary[Option[String]](x))
          mimeType <- m.get(mimeTypeKey).map(x => readBinary[Option[String]](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
        } yield {
          Video(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            width = width,
            height = height,
            duration = duration,
            thumb = thumb,
            fileName = fileName,
            mimeType = mimeType,
            fileSize = fileSize
          )
        }
        result.get
      }
    )
  }

  implicit lazy val documentCodec: ReadWriter[Document] = {
    val fileIdKey = upack.Str("fileId")
    val fileUniqueIdKey = upack.Str("fileUniqueId")
    val thumbKey = upack.Str("thumb")
    val fileNameKey = upack.Str("fileName")
    val mimeTypeKey = upack.Str("mimeType")
    val fileSizeKey = upack.Str("fileSize")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          fileIdKey -> writeMsg(x.fileId),
          fileUniqueIdKey -> writeMsg(x.fileUniqueId),
          thumbKey -> writeMsg(x.thumb),
          fileNameKey -> writeMsg(x.fileName),
          mimeTypeKey -> writeMsg(x.mimeType),
          fileSizeKey -> writeMsg(x.fileSize)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          fileId <- m.get(fileIdKey).map(x => readBinary[String](x))
          fileUniqueId <- m.get(fileUniqueIdKey).map(x => readBinary[String](x))
          thumb <- m.get(thumbKey).map(x => readBinary[Option[PhotoSize]](x))
          fileName <- m.get(fileNameKey).map(x => readBinary[Option[String]](x))
          mimeType <- m.get(mimeTypeKey).map(x => readBinary[Option[String]](x))
          fileSize <- m.get(fileSizeKey).map(x => readBinary[Option[Int]](x))
        } yield {
          Document(
            fileId = fileId,
            fileUniqueId = fileUniqueId,
            thumb = thumb,
            fileName = fileName,
            mimeType = mimeType,
            fileSize = fileSize
          )
        }
        result.get
      }
    )
  }

}

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Decoder, Encoder, Json}
  import cats.syntax.functor._
  import io.circe.HCursor

  implicit lazy val emojiEncoder: Encoder[Emoji] = {
    case EmojiDice        => EmojiDice.asJson
    case EmojiDarts       => EmojiDarts.asJson
    case EmojiBasketball  => EmojiBasketball.asJson
    case EmojiFootball    => EmojiFootball.asJson
    case EmojiSlotMachine => EmojiSlotMachine.asJson
    case EmojiBowling     => EmojiBowling.asJson
  }
  implicit lazy val emojiDecoder: Decoder[Emoji] = {
    List[Decoder[Emoji]](
      emojidiceDecoder.widen,
      emojidartsDecoder.widen,
      emojibasketballDecoder.widen,
      emojifootballDecoder.widen,
      emojislotmachineDecoder.widen,
      emojibowlingDecoder.widen
    ).reduceLeft(_ or _)
  }

  implicit lazy val emojidiceEncoder: Encoder[EmojiDice.type] = (_: EmojiDice.type) => "🎲".asJson
  implicit lazy val emojidiceDecoder: Decoder[EmojiDice.type] = Decoder[String].map(_ => EmojiDice)
  implicit lazy val emojidartsEncoder: Encoder[EmojiDarts.type] = (_: EmojiDarts.type) => "🎯".asJson
  implicit lazy val emojidartsDecoder: Decoder[EmojiDarts.type] =
    Decoder[String].map(_ => EmojiDarts)
  implicit lazy val emojibasketballEncoder: Encoder[EmojiBasketball.type] =
    (_: EmojiBasketball.type) => "🏀".asJson
  implicit lazy val emojibasketballDecoder: Decoder[EmojiBasketball.type] =
    Decoder[String].map(_ => EmojiBasketball)
  implicit lazy val emojifootballEncoder: Encoder[EmojiFootball.type] = (_: EmojiFootball.type) => "⚽".asJson
  implicit lazy val emojifootballDecoder: Decoder[EmojiFootball.type] =
    Decoder[String].map(_ => EmojiFootball)
  implicit lazy val emojislotmachineEncoder: Encoder[EmojiSlotMachine.type] =
    (_: EmojiSlotMachine.type) => "🎰".asJson
  implicit lazy val emojislotmachineDecoder: Decoder[EmojiSlotMachine.type] =
    Decoder[String].map(_ => EmojiSlotMachine)
  implicit lazy val emojibowlingEncoder: Encoder[EmojiBowling.type] = (_: EmojiBowling.type) => "🎳".asJson
  implicit lazy val emojibowlingDecoder: Decoder[EmojiBowling.type] =
    Decoder[String].map(_ => EmojiBowling)

  implicit lazy val parsemodeEncoder: Encoder[ParseMode] = {
    case Markdown  => Markdown.asJson
    case Markdown2 => Markdown2.asJson
    case Html      => Html.asJson
  }
  implicit lazy val parsemodeDecoder: Decoder[ParseMode] = {
    List[Decoder[ParseMode]](
      markdownDecoder.widen,
      markdown2Decoder.widen,
      htmlDecoder.widen
    ).reduceLeft(_ or _)
  }

  implicit lazy val markdownEncoder: Encoder[Markdown.type] = (_: Markdown.type) => "Markdown".asJson
  implicit lazy val markdownDecoder: Decoder[Markdown.type] = Decoder[String].map(_ => Markdown)
  implicit lazy val markdown2Encoder: Encoder[Markdown2.type] = (_: Markdown2.type) => "MarkdownV2".asJson
  implicit lazy val markdown2Decoder: Decoder[Markdown2.type] = Decoder[String].map(_ => Markdown2)
  implicit lazy val htmlEncoder: Encoder[Html.type] = (_: Html.type) => "HTML".asJson
  implicit lazy val htmlDecoder: Decoder[Html.type] = Decoder[String].map(_ => Html)

  implicit lazy val chatidEncoder: Encoder[ChatId] = {
    case x: ChatIntId => x.asJson
    case x: ChatStrId => x.asJson
  }
  implicit lazy val chatidDecoder: Decoder[ChatId] = {
    List[Decoder[ChatId]](
      chatintidDecoder.widen,
      chatstridDecoder.widen
    ).reduceLeft(_ or _)
  }

  implicit lazy val chatintidEncoder: Encoder[ChatIntId] = (x: ChatIntId) => x.id.asJson
  implicit lazy val chatintidDecoder: Decoder[ChatIntId] = Decoder[Long].map(ChatIntId)
  implicit lazy val chatstridEncoder: Encoder[ChatStrId] = (x: ChatStrId) => x.id.asJson
  implicit lazy val chatstridDecoder: Decoder[ChatStrId] = Decoder[String].map(ChatStrId)

  implicit lazy val keyboardmarkupEncoder: Encoder[KeyboardMarkup] = {
    case x: InlineKeyboardMarkup => x.asJson
    case x: ForceReply           => x.asJson
    case x: ReplyKeyboardRemove  => x.asJson
    case x: ReplyKeyboardMarkup  => x.asJson
  }
  implicit lazy val keyboardmarkupDecoder: Decoder[KeyboardMarkup] = {
    List[Decoder[KeyboardMarkup]](
      inlinekeyboardmarkupDecoder.widen,
      forcereplyDecoder.widen,
      replykeyboardremoveDecoder.widen,
      replykeyboardmarkupDecoder.widen
    ).reduceLeft(_ or _)
  }

  implicit lazy val inlinekeyboardmarkupEncoder: Encoder[InlineKeyboardMarkup] =
    (x: InlineKeyboardMarkup) => {
      Json.fromFields(
        List(
          "inline_keyboard" -> x.inlineKeyboard.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinekeyboardmarkupDecoder: Decoder[InlineKeyboardMarkup] =
    Decoder.instance { h =>
      for {
        _inlineKeyboard <- h
          .getOrElse[List[List[InlineKeyboardButton]]]("inline_keyboard")(List.empty)
      } yield {
        InlineKeyboardMarkup(inlineKeyboard = _inlineKeyboard)
      }
    }

  implicit lazy val forcereplyEncoder: Encoder[ForceReply] =
    (x: ForceReply) => {
      Json.fromFields(
        List(
          "force_reply" -> x.forceReply.asJson,
          "selective" -> x.selective.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val forcereplyDecoder: Decoder[ForceReply] =
    Decoder.instance { h =>
      for {
        _forceReply <- h.get[Boolean]("force_reply")
        _selective <- h.get[Option[Boolean]]("selective")
      } yield {
        ForceReply(forceReply = _forceReply, selective = _selective)
      }
    }

  implicit lazy val replykeyboardremoveEncoder: Encoder[ReplyKeyboardRemove] =
    (x: ReplyKeyboardRemove) => {
      Json.fromFields(
        List(
          "remove_keyboard" -> x.removeKeyboard.asJson,
          "selective" -> x.selective.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val replykeyboardremoveDecoder: Decoder[ReplyKeyboardRemove] =
    Decoder.instance { h =>
      for {
        _removeKeyboard <- h.get[Boolean]("remove_keyboard")
        _selective <- h.get[Option[Boolean]]("selective")
      } yield {
        ReplyKeyboardRemove(removeKeyboard = _removeKeyboard, selective = _selective)
      }
    }

  implicit lazy val replykeyboardmarkupEncoder: Encoder[ReplyKeyboardMarkup] =
    (x: ReplyKeyboardMarkup) => {
      Json.fromFields(
        List(
          "keyboard" -> x.keyboard.asJson,
          "resize_keyboard" -> x.resizeKeyboard.asJson,
          "one_time_keyboard" -> x.oneTimeKeyboard.asJson,
          "selective" -> x.selective.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val replykeyboardmarkupDecoder: Decoder[ReplyKeyboardMarkup] =
    Decoder.instance { h =>
      for {
        _keyboard <- h.getOrElse[List[List[KeyboardButton]]]("keyboard")(List.empty)
        _resizeKeyboard <- h.get[Option[Boolean]]("resize_keyboard")
        _oneTimeKeyboard <- h.get[Option[Boolean]]("one_time_keyboard")
        _selective <- h.get[Option[Boolean]]("selective")
      } yield {
        ReplyKeyboardMarkup(keyboard = _keyboard,
                            resizeKeyboard = _resizeKeyboard,
                            oneTimeKeyboard = _oneTimeKeyboard,
                            selective = _selective
        )
      }
    }

  implicit lazy val ifileEncoder: Encoder[IFile] = {
    case x: InputPartFile => x.asJson
    case x: InputLinkFile => x.asJson
  }
  implicit lazy val ifileDecoder: Decoder[IFile] = {
    List[Decoder[IFile]](
      inputpartfileDecoder.widen,
      inputlinkfileDecoder.widen
    ).reduceLeft(_ or _)
  }

  implicit lazy val inputpartfileEncoder: Encoder[InputPartFile] = (x: InputPartFile) => x.file.getName.asJson
  implicit lazy val inputpartfileDecoder: Decoder[InputPartFile] =
    Decoder[String].map(s => InputPartFile(new java.io.File(s)))
  implicit lazy val inputlinkfileEncoder: Encoder[InputLinkFile] = (x: InputLinkFile) => x.file.asJson
  implicit lazy val inputlinkfileDecoder: Decoder[InputLinkFile] =
    Decoder[String].map(InputLinkFile)

  implicit lazy val inputmediaEncoder: Encoder[InputMedia] = {
    case photo: InputMediaPhoto => photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case document: InputMediaDocument =>
      document.asJson.mapObject(_.add("type", Json.fromString("document")))
    case audio: InputMediaAudio => audio.asJson.mapObject(_.add("type", Json.fromString("audio")))
    case animation: InputMediaAnimation =>
      animation.asJson.mapObject(_.add("type", Json.fromString("animation")))
    case video: InputMediaVideo => video.asJson.mapObject(_.add("type", Json.fromString("video")))
  }
  implicit lazy val inputmediaDecoder: Decoder[InputMedia] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "photo"     => Decoder[InputMediaPhoto]
      case "document"  => Decoder[InputMediaDocument]
      case "audio"     => Decoder[InputMediaAudio]
      case "animation" => Decoder[InputMediaAnimation]
      case "video"     => Decoder[InputMediaVideo]
    }
  } yield value

  implicit lazy val inputmediaanimationEncoder: Encoder[InputMediaAnimation] =
    (x: InputMediaAnimation) => {
      Json.fromFields(
        List(
          "media" -> x.media.asJson,
          "thumb" -> x.thumb.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "width" -> x.width.asJson,
          "height" -> x.height.asJson,
          "duration" -> x.duration.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediaanimationDecoder: Decoder[InputMediaAnimation] =
    Decoder.instance { h =>
      for {
        _media <- h.get[String]("media")
        _thumb <- h.get[Option[IFile]]("thumb")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _width <- h.get[Option[Int]]("width")
        _height <- h.get[Option[Int]]("height")
        _duration <- h.get[Option[Int]]("duration")
      } yield {
        InputMediaAnimation(media = _media,
                            thumb = _thumb,
                            caption = _caption,
                            parseMode = _parseMode,
                            captionEntities = _captionEntities,
                            width = _width,
                            height = _height,
                            duration = _duration
        )
      }
    }

  implicit lazy val inputmediaphotoEncoder: Encoder[InputMediaPhoto] =
    (x: InputMediaPhoto) => {
      Json.fromFields(
        List(
          "media" -> x.media.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediaphotoDecoder: Decoder[InputMediaPhoto] =
    Decoder.instance { h =>
      for {
        _media <- h.get[String]("media")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
      } yield {
        InputMediaPhoto(media = _media, caption = _caption, parseMode = _parseMode, captionEntities = _captionEntities)
      }
    }

  implicit lazy val inputmediavideoEncoder: Encoder[InputMediaVideo] =
    (x: InputMediaVideo) => {
      Json.fromFields(
        List(
          "media" -> x.media.asJson,
          "thumb" -> x.thumb.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "width" -> x.width.asJson,
          "height" -> x.height.asJson,
          "duration" -> x.duration.asJson,
          "supports_streaming" -> x.supportsStreaming.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediavideoDecoder: Decoder[InputMediaVideo] =
    Decoder.instance { h =>
      for {
        _media <- h.get[String]("media")
        _thumb <- h.get[Option[IFile]]("thumb")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _width <- h.get[Option[Int]]("width")
        _height <- h.get[Option[Int]]("height")
        _duration <- h.get[Option[Int]]("duration")
        _supportsStreaming <- h.get[Option[Boolean]]("supports_streaming")
      } yield {
        InputMediaVideo(
          media = _media,
          thumb = _thumb,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          width = _width,
          height = _height,
          duration = _duration,
          supportsStreaming = _supportsStreaming
        )
      }
    }

  implicit lazy val inputmediadocumentEncoder: Encoder[InputMediaDocument] =
    (x: InputMediaDocument) => {
      Json.fromFields(
        List(
          "media" -> x.media.asJson,
          "thumb" -> x.thumb.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "disable_content_type_detection" -> x.disableContentTypeDetection.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediadocumentDecoder: Decoder[InputMediaDocument] =
    Decoder.instance { h =>
      for {
        _media <- h.get[String]("media")
        _thumb <- h.get[Option[IFile]]("thumb")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _disableContentTypeDetection <- h.get[Option[Boolean]]("disable_content_type_detection")
      } yield {
        InputMediaDocument(
          media = _media,
          thumb = _thumb,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          disableContentTypeDetection = _disableContentTypeDetection
        )
      }
    }

  implicit lazy val inputmediaaudioEncoder: Encoder[InputMediaAudio] =
    (x: InputMediaAudio) => {
      Json.fromFields(
        List(
          "media" -> x.media.asJson,
          "thumb" -> x.thumb.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "duration" -> x.duration.asJson,
          "performer" -> x.performer.asJson,
          "title" -> x.title.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediaaudioDecoder: Decoder[InputMediaAudio] =
    Decoder.instance { h =>
      for {
        _media <- h.get[String]("media")
        _thumb <- h.get[Option[IFile]]("thumb")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _duration <- h.get[Option[Int]]("duration")
        _performer <- h.get[Option[String]]("performer")
        _title <- h.get[Option[String]]("title")
      } yield {
        InputMediaAudio(media = _media,
                        thumb = _thumb,
                        caption = _caption,
                        parseMode = _parseMode,
                        captionEntities = _captionEntities,
                        duration = _duration,
                        performer = _performer,
                        title = _title
        )
      }
    }

  implicit lazy val inlinequeryresultEncoder: Encoder[InlineQueryResult] = {
    case mpeg4_gif: InlineQueryResultMpeg4Gif =>
      mpeg4_gif.asJson.mapObject(_.add("type", Json.fromString("mpeg4_gif")))
    case mpeg4_gif: InlineQueryResultCachedMpeg4Gif =>
      mpeg4_gif.asJson.mapObject(_.add("type", Json.fromString("mpeg4_gif")))
    case location: InlineQueryResultLocation =>
      location.asJson.mapObject(_.add("type", Json.fromString("location")))
    case photo: InlineQueryResultPhoto =>
      photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case photo: InlineQueryResultCachedPhoto =>
      photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case document: InlineQueryResultDocument =>
      document.asJson.mapObject(_.add("type", Json.fromString("document")))
    case document: InlineQueryResultCachedDocument =>
      document.asJson.mapObject(_.add("type", Json.fromString("document")))
    case audio: InlineQueryResultAudio =>
      audio.asJson.mapObject(_.add("type", Json.fromString("audio")))
    case audio: InlineQueryResultCachedAudio =>
      audio.asJson.mapObject(_.add("type", Json.fromString("audio")))
    case voice: InlineQueryResultCachedVoice =>
      voice.asJson.mapObject(_.add("type", Json.fromString("voice")))
    case voice: InlineQueryResultVoice =>
      voice.asJson.mapObject(_.add("type", Json.fromString("voice")))
    case article: InlineQueryResultArticle =>
      article.asJson.mapObject(_.add("type", Json.fromString("article")))
    case contact: InlineQueryResultContact =>
      contact.asJson.mapObject(_.add("type", Json.fromString("contact")))
    case video: InlineQueryResultCachedVideo =>
      video.asJson.mapObject(_.add("type", Json.fromString("video")))
    case video: InlineQueryResultVideo =>
      video.asJson.mapObject(_.add("type", Json.fromString("video")))
    case gif: InlineQueryResultGif => gif.asJson.mapObject(_.add("type", Json.fromString("gif")))
    case gif: InlineQueryResultCachedGif =>
      gif.asJson.mapObject(_.add("type", Json.fromString("gif")))
    case sticker: InlineQueryResultCachedSticker =>
      sticker.asJson.mapObject(_.add("type", Json.fromString("sticker")))
    case game: InlineQueryResultGame =>
      game.asJson.mapObject(_.add("type", Json.fromString("game")))
    case venue: InlineQueryResultVenue =>
      venue.asJson.mapObject(_.add("type", Json.fromString("venue")))
  }
  implicit lazy val inlinequeryresultDecoder: Decoder[InlineQueryResult] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "mpeg4_gif" =>
        List[Decoder[InlineQueryResult]](Decoder[InlineQueryResultMpeg4Gif].widen,
                                         Decoder[InlineQueryResultCachedMpeg4Gif].widen
        ).reduceLeft(_ or _)
      case "location" => Decoder[InlineQueryResultLocation]
      case "photo" =>
        List[Decoder[InlineQueryResult]](Decoder[InlineQueryResultPhoto].widen,
                                         Decoder[InlineQueryResultCachedPhoto].widen
        ).reduceLeft(_ or _)
      case "document" =>
        List[Decoder[InlineQueryResult]](Decoder[InlineQueryResultDocument].widen,
                                         Decoder[InlineQueryResultCachedDocument].widen
        ).reduceLeft(_ or _)
      case "audio" =>
        List[Decoder[InlineQueryResult]](Decoder[InlineQueryResultAudio].widen,
                                         Decoder[InlineQueryResultCachedAudio].widen
        ).reduceLeft(_ or _)
      case "voice" =>
        List[Decoder[InlineQueryResult]](Decoder[InlineQueryResultCachedVoice].widen,
                                         Decoder[InlineQueryResultVoice].widen
        ).reduceLeft(_ or _)
      case "article" => Decoder[InlineQueryResultArticle]
      case "contact" => Decoder[InlineQueryResultContact]
      case "video" =>
        List[Decoder[InlineQueryResult]](Decoder[InlineQueryResultCachedVideo].widen,
                                         Decoder[InlineQueryResultVideo].widen
        ).reduceLeft(_ or _)
      case "gif" =>
        List[Decoder[InlineQueryResult]](Decoder[InlineQueryResultGif].widen, Decoder[InlineQueryResultCachedGif].widen)
          .reduceLeft(_ or _)
      case "sticker" => Decoder[InlineQueryResultCachedSticker]
      case "game"    => Decoder[InlineQueryResultGame]
      case "venue"   => Decoder[InlineQueryResultVenue]
    }
  } yield value

  implicit lazy val inlinequeryresultgifEncoder: Encoder[InlineQueryResultGif] =
    (x: InlineQueryResultGif) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "gif_url" -> x.gifUrl.asJson,
          "gif_width" -> x.gifWidth.asJson,
          "gif_height" -> x.gifHeight.asJson,
          "gif_duration" -> x.gifDuration.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "thumb_mime_type" -> x.thumbMimeType.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultgifDecoder: Decoder[InlineQueryResultGif] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _gifUrl <- h.get[String]("gif_url")
        _gifWidth <- h.get[Option[Int]]("gif_width")
        _gifHeight <- h.get[Option[Int]]("gif_height")
        _gifDuration <- h.get[Option[Int]]("gif_duration")
        _thumbUrl <- h.get[String]("thumb_url")
        _thumbMimeType <- h.get[Option[String]]("thumb_mime_type")
        _title <- h.get[Option[String]]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultGif(
          id = _id,
          gifUrl = _gifUrl,
          gifWidth = _gifWidth,
          gifHeight = _gifHeight,
          gifDuration = _gifDuration,
          thumbUrl = _thumbUrl,
          thumbMimeType = _thumbMimeType,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultvenueEncoder: Encoder[InlineQueryResultVenue] =
    (x: InlineQueryResultVenue) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "latitude" -> x.latitude.asJson,
          "longitude" -> x.longitude.asJson,
          "title" -> x.title.asJson,
          "address" -> x.address.asJson,
          "foursquare_id" -> x.foursquareId.asJson,
          "foursquare_type" -> x.foursquareType.asJson,
          "google_place_id" -> x.googlePlaceId.asJson,
          "google_place_type" -> x.googlePlaceType.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "thumb_width" -> x.thumbWidth.asJson,
          "thumb_height" -> x.thumbHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultvenueDecoder: Decoder[InlineQueryResultVenue] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _latitude <- h.get[Float]("latitude")
        _longitude <- h.get[Float]("longitude")
        _title <- h.get[String]("title")
        _address <- h.get[String]("address")
        _foursquareId <- h.get[Option[String]]("foursquare_id")
        _foursquareType <- h.get[Option[String]]("foursquare_type")
        _googlePlaceId <- h.get[Option[String]]("google_place_id")
        _googlePlaceType <- h.get[Option[String]]("google_place_type")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbUrl <- h.get[Option[String]]("thumb_url")
        _thumbWidth <- h.get[Option[Int]]("thumb_width")
        _thumbHeight <- h.get[Option[Int]]("thumb_height")
      } yield {
        InlineQueryResultVenue(
          id = _id,
          latitude = _latitude,
          longitude = _longitude,
          title = _title,
          address = _address,
          foursquareId = _foursquareId,
          foursquareType = _foursquareType,
          googlePlaceId = _googlePlaceId,
          googlePlaceType = _googlePlaceType,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent,
          thumbUrl = _thumbUrl,
          thumbWidth = _thumbWidth,
          thumbHeight = _thumbHeight
        )
      }
    }

  implicit lazy val inlinequeryresultcontactEncoder: Encoder[InlineQueryResultContact] =
    (x: InlineQueryResultContact) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "phone_number" -> x.phoneNumber.asJson,
          "first_name" -> x.firstName.asJson,
          "last_name" -> x.lastName.asJson,
          "vcard" -> x.vcard.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "thumb_width" -> x.thumbWidth.asJson,
          "thumb_height" -> x.thumbHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcontactDecoder: Decoder[InlineQueryResultContact] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _phoneNumber <- h.get[String]("phone_number")
        _firstName <- h.get[String]("first_name")
        _lastName <- h.get[Option[String]]("last_name")
        _vcard <- h.get[Option[String]]("vcard")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbUrl <- h.get[Option[String]]("thumb_url")
        _thumbWidth <- h.get[Option[Int]]("thumb_width")
        _thumbHeight <- h.get[Option[Int]]("thumb_height")
      } yield {
        InlineQueryResultContact(
          id = _id,
          phoneNumber = _phoneNumber,
          firstName = _firstName,
          lastName = _lastName,
          vcard = _vcard,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent,
          thumbUrl = _thumbUrl,
          thumbWidth = _thumbWidth,
          thumbHeight = _thumbHeight
        )
      }
    }

  implicit lazy val inlinequeryresultphotoEncoder: Encoder[InlineQueryResultPhoto] =
    (x: InlineQueryResultPhoto) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "photo_url" -> x.photoUrl.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "photo_width" -> x.photoWidth.asJson,
          "photo_height" -> x.photoHeight.asJson,
          "title" -> x.title.asJson,
          "description" -> x.description.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultphotoDecoder: Decoder[InlineQueryResultPhoto] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _photoUrl <- h.get[String]("photo_url")
        _thumbUrl <- h.get[String]("thumb_url")
        _photoWidth <- h.get[Option[Int]]("photo_width")
        _photoHeight <- h.get[Option[Int]]("photo_height")
        _title <- h.get[Option[String]]("title")
        _description <- h.get[Option[String]]("description")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultPhoto(
          id = _id,
          photoUrl = _photoUrl,
          thumbUrl = _thumbUrl,
          photoWidth = _photoWidth,
          photoHeight = _photoHeight,
          title = _title,
          description = _description,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultdocumentEncoder: Encoder[InlineQueryResultDocument] =
    (x: InlineQueryResultDocument) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "document_url" -> x.documentUrl.asJson,
          "mime_type" -> x.mimeType.asJson,
          "description" -> x.description.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "thumb_width" -> x.thumbWidth.asJson,
          "thumb_height" -> x.thumbHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultdocumentDecoder: Decoder[InlineQueryResultDocument] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _title <- h.get[String]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _documentUrl <- h.get[String]("document_url")
        _mimeType <- h.get[String]("mime_type")
        _description <- h.get[Option[String]]("description")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbUrl <- h.get[Option[String]]("thumb_url")
        _thumbWidth <- h.get[Option[Int]]("thumb_width")
        _thumbHeight <- h.get[Option[Int]]("thumb_height")
      } yield {
        InlineQueryResultDocument(
          id = _id,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          documentUrl = _documentUrl,
          mimeType = _mimeType,
          description = _description,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent,
          thumbUrl = _thumbUrl,
          thumbWidth = _thumbWidth,
          thumbHeight = _thumbHeight
        )
      }
    }

  implicit lazy val inlinequeryresultcachedvoiceEncoder: Encoder[InlineQueryResultCachedVoice] =
    (x: InlineQueryResultCachedVoice) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "voice_file_id" -> x.voiceFileId.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedvoiceDecoder: Decoder[InlineQueryResultCachedVoice] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _voiceFileId <- h.get[String]("voice_file_id")
        _title <- h.get[String]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedVoice(
          id = _id,
          voiceFileId = _voiceFileId,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultarticleEncoder: Encoder[InlineQueryResultArticle] =
    (x: InlineQueryResultArticle) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "title" -> x.title.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "url" -> x.url.asJson,
          "hide_url" -> x.hideUrl.asJson,
          "description" -> x.description.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "thumb_width" -> x.thumbWidth.asJson,
          "thumb_height" -> x.thumbHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultarticleDecoder: Decoder[InlineQueryResultArticle] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _title <- h.get[String]("title")
        _inputMessageContent <- h.get[InputMessageContent]("input_message_content")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _url <- h.get[Option[String]]("url")
        _hideUrl <- h.get[Option[Boolean]]("hide_url")
        _description <- h.get[Option[String]]("description")
        _thumbUrl <- h.get[Option[String]]("thumb_url")
        _thumbWidth <- h.get[Option[Int]]("thumb_width")
        _thumbHeight <- h.get[Option[Int]]("thumb_height")
      } yield {
        InlineQueryResultArticle(
          id = _id,
          title = _title,
          inputMessageContent = _inputMessageContent,
          replyMarkup = _replyMarkup,
          url = _url,
          hideUrl = _hideUrl,
          description = _description,
          thumbUrl = _thumbUrl,
          thumbWidth = _thumbWidth,
          thumbHeight = _thumbHeight
        )
      }
    }

  implicit lazy val inlinequeryresultaudioEncoder: Encoder[InlineQueryResultAudio] =
    (x: InlineQueryResultAudio) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "audio_url" -> x.audioUrl.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "performer" -> x.performer.asJson,
          "audio_duration" -> x.audioDuration.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultaudioDecoder: Decoder[InlineQueryResultAudio] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _audioUrl <- h.get[String]("audio_url")
        _title <- h.get[String]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _performer <- h.get[Option[String]]("performer")
        _audioDuration <- h.get[Option[Int]]("audio_duration")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultAudio(
          id = _id,
          audioUrl = _audioUrl,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          performer = _performer,
          audioDuration = _audioDuration,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultmpeg4gifEncoder: Encoder[InlineQueryResultMpeg4Gif] =
    (x: InlineQueryResultMpeg4Gif) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "mpeg4_url" -> x.mpeg4Url.asJson,
          "mpeg4_width" -> x.mpeg4Width.asJson,
          "mpeg4_height" -> x.mpeg4Height.asJson,
          "mpeg4_duration" -> x.mpeg4Duration.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "thumb_mime_type" -> x.thumbMimeType.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultmpeg4gifDecoder: Decoder[InlineQueryResultMpeg4Gif] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _mpeg4Url <- h.get[String]("mpeg4_url")
        _mpeg4Width <- h.get[Option[Int]]("mpeg4_width")
        _mpeg4Height <- h.get[Option[Int]]("mpeg4_height")
        _mpeg4Duration <- h.get[Option[Int]]("mpeg4_duration")
        _thumbUrl <- h.get[String]("thumb_url")
        _thumbMimeType <- h.get[Option[String]]("thumb_mime_type")
        _title <- h.get[Option[String]]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultMpeg4Gif(
          id = _id,
          mpeg4Url = _mpeg4Url,
          mpeg4Width = _mpeg4Width,
          mpeg4Height = _mpeg4Height,
          mpeg4Duration = _mpeg4Duration,
          thumbUrl = _thumbUrl,
          thumbMimeType = _thumbMimeType,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultcachedmpeg4gifEncoder: Encoder[InlineQueryResultCachedMpeg4Gif] =
    (x: InlineQueryResultCachedMpeg4Gif) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "mpeg4_file_id" -> x.mpeg4FileId.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedmpeg4gifDecoder: Decoder[InlineQueryResultCachedMpeg4Gif] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _mpeg4FileId <- h.get[String]("mpeg4_file_id")
        _title <- h.get[Option[String]]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedMpeg4Gif(
          id = _id,
          mpeg4FileId = _mpeg4FileId,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultcacheddocumentEncoder: Encoder[InlineQueryResultCachedDocument] =
    (x: InlineQueryResultCachedDocument) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "title" -> x.title.asJson,
          "document_file_id" -> x.documentFileId.asJson,
          "description" -> x.description.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcacheddocumentDecoder: Decoder[InlineQueryResultCachedDocument] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _title <- h.get[String]("title")
        _documentFileId <- h.get[String]("document_file_id")
        _description <- h.get[Option[String]]("description")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedDocument(
          id = _id,
          title = _title,
          documentFileId = _documentFileId,
          description = _description,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultcachedvideoEncoder: Encoder[InlineQueryResultCachedVideo] =
    (x: InlineQueryResultCachedVideo) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "video_file_id" -> x.videoFileId.asJson,
          "title" -> x.title.asJson,
          "description" -> x.description.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedvideoDecoder: Decoder[InlineQueryResultCachedVideo] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _videoFileId <- h.get[String]("video_file_id")
        _title <- h.get[String]("title")
        _description <- h.get[Option[String]]("description")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedVideo(
          id = _id,
          videoFileId = _videoFileId,
          title = _title,
          description = _description,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultgameEncoder: Encoder[InlineQueryResultGame] =
    (x: InlineQueryResultGame) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "game_short_name" -> x.gameShortName.asJson,
          "reply_markup" -> x.replyMarkup.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultgameDecoder: Decoder[InlineQueryResultGame] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _gameShortName <- h.get[String]("game_short_name")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        InlineQueryResultGame(id = _id, gameShortName = _gameShortName, replyMarkup = _replyMarkup)
      }
    }

  implicit lazy val inlinequeryresultcachedphotoEncoder: Encoder[InlineQueryResultCachedPhoto] =
    (x: InlineQueryResultCachedPhoto) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "photo_file_id" -> x.photoFileId.asJson,
          "title" -> x.title.asJson,
          "description" -> x.description.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedphotoDecoder: Decoder[InlineQueryResultCachedPhoto] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _photoFileId <- h.get[String]("photo_file_id")
        _title <- h.get[Option[String]]("title")
        _description <- h.get[Option[String]]("description")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedPhoto(
          id = _id,
          photoFileId = _photoFileId,
          title = _title,
          description = _description,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultcachedstickerEncoder: Encoder[InlineQueryResultCachedSticker] =
    (x: InlineQueryResultCachedSticker) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "sticker_file_id" -> x.stickerFileId.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedstickerDecoder: Decoder[InlineQueryResultCachedSticker] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _stickerFileId <- h.get[String]("sticker_file_id")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedSticker(id = _id,
                                       stickerFileId = _stickerFileId,
                                       replyMarkup = _replyMarkup,
                                       inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultvideoEncoder: Encoder[InlineQueryResultVideo] =
    (x: InlineQueryResultVideo) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "video_url" -> x.videoUrl.asJson,
          "mime_type" -> x.mimeType.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "video_width" -> x.videoWidth.asJson,
          "video_height" -> x.videoHeight.asJson,
          "video_duration" -> x.videoDuration.asJson,
          "description" -> x.description.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultvideoDecoder: Decoder[InlineQueryResultVideo] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _videoUrl <- h.get[String]("video_url")
        _mimeType <- h.get[String]("mime_type")
        _thumbUrl <- h.get[String]("thumb_url")
        _title <- h.get[String]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _videoWidth <- h.get[Option[Int]]("video_width")
        _videoHeight <- h.get[Option[Int]]("video_height")
        _videoDuration <- h.get[Option[Int]]("video_duration")
        _description <- h.get[Option[String]]("description")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultVideo(
          id = _id,
          videoUrl = _videoUrl,
          mimeType = _mimeType,
          thumbUrl = _thumbUrl,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          videoWidth = _videoWidth,
          videoHeight = _videoHeight,
          videoDuration = _videoDuration,
          description = _description,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultcachedaudioEncoder: Encoder[InlineQueryResultCachedAudio] =
    (x: InlineQueryResultCachedAudio) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "audio_file_id" -> x.audioFileId.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedaudioDecoder: Decoder[InlineQueryResultCachedAudio] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _audioFileId <- h.get[String]("audio_file_id")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedAudio(
          id = _id,
          audioFileId = _audioFileId,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultlocationEncoder: Encoder[InlineQueryResultLocation] =
    (x: InlineQueryResultLocation) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "latitude" -> x.latitude.asJson,
          "longitude" -> x.longitude.asJson,
          "title" -> x.title.asJson,
          "horizontal_accuracy" -> x.horizontalAccuracy.asJson,
          "live_period" -> x.livePeriod.asJson,
          "heading" -> x.heading.asJson,
          "proximity_alert_radius" -> x.proximityAlertRadius.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "thumb_url" -> x.thumbUrl.asJson,
          "thumb_width" -> x.thumbWidth.asJson,
          "thumb_height" -> x.thumbHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultlocationDecoder: Decoder[InlineQueryResultLocation] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _latitude <- h.get[Float]("latitude")
        _longitude <- h.get[Float]("longitude")
        _title <- h.get[String]("title")
        _horizontalAccuracy <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod <- h.get[Option[Int]]("live_period")
        _heading <- h.get[Option[Int]]("heading")
        _proximityAlertRadius <- h.get[Option[Int]]("proximity_alert_radius")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbUrl <- h.get[Option[String]]("thumb_url")
        _thumbWidth <- h.get[Option[Int]]("thumb_width")
        _thumbHeight <- h.get[Option[Int]]("thumb_height")
      } yield {
        InlineQueryResultLocation(
          id = _id,
          latitude = _latitude,
          longitude = _longitude,
          title = _title,
          horizontalAccuracy = _horizontalAccuracy,
          livePeriod = _livePeriod,
          heading = _heading,
          proximityAlertRadius = _proximityAlertRadius,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent,
          thumbUrl = _thumbUrl,
          thumbWidth = _thumbWidth,
          thumbHeight = _thumbHeight
        )
      }
    }

  implicit lazy val inlinequeryresultcachedgifEncoder: Encoder[InlineQueryResultCachedGif] =
    (x: InlineQueryResultCachedGif) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "gif_file_id" -> x.gifFileId.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedgifDecoder: Decoder[InlineQueryResultCachedGif] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _gifFileId <- h.get[String]("gif_file_id")
        _title <- h.get[Option[String]]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedGif(
          id = _id,
          gifFileId = _gifFileId,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultvoiceEncoder: Encoder[InlineQueryResultVoice] =
    (x: InlineQueryResultVoice) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "voice_url" -> x.voiceUrl.asJson,
          "title" -> x.title.asJson,
          "caption" -> x.caption.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "voice_duration" -> x.voiceDuration.asJson,
          "reply_markup" -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultvoiceDecoder: Decoder[InlineQueryResultVoice] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _voiceUrl <- h.get[String]("voice_url")
        _title <- h.get[String]("title")
        _caption <- h.get[Option[String]]("caption")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _voiceDuration <- h.get[Option[Int]]("voice_duration")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultVoice(
          id = _id,
          voiceUrl = _voiceUrl,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          voiceDuration = _voiceDuration,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inputmessagecontentEncoder: Encoder[InputMessageContent] = {
    case x: InputVenueMessageContent    => x.asJson
    case x: InputInvoiceMessageContent  => x.asJson
    case x: InputContactMessageContent  => x.asJson
    case x: InputLocationMessageContent => x.asJson
    case x: InputTextMessageContent     => x.asJson
  }
  implicit lazy val inputmessagecontentDecoder: Decoder[InputMessageContent] = {
    List[Decoder[InputMessageContent]](
      inputvenuemessagecontentDecoder.widen,
      inputinvoicemessagecontentDecoder.widen,
      inputcontactmessagecontentDecoder.widen,
      inputlocationmessagecontentDecoder.widen,
      inputtextmessagecontentDecoder.widen
    ).reduceLeft(_ or _)
  }

  implicit lazy val inputvenuemessagecontentEncoder: Encoder[InputVenueMessageContent] =
    (x: InputVenueMessageContent) => {
      Json.fromFields(
        List(
          "latitude" -> x.latitude.asJson,
          "longitude" -> x.longitude.asJson,
          "title" -> x.title.asJson,
          "address" -> x.address.asJson,
          "foursquare_id" -> x.foursquareId.asJson,
          "foursquare_type" -> x.foursquareType.asJson,
          "google_place_id" -> x.googlePlaceId.asJson,
          "google_place_type" -> x.googlePlaceType.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputvenuemessagecontentDecoder: Decoder[InputVenueMessageContent] =
    Decoder.instance { h =>
      for {
        _latitude <- h.get[Float]("latitude")
        _longitude <- h.get[Float]("longitude")
        _title <- h.get[String]("title")
        _address <- h.get[String]("address")
        _foursquareId <- h.get[Option[String]]("foursquare_id")
        _foursquareType <- h.get[Option[String]]("foursquare_type")
        _googlePlaceId <- h.get[Option[String]]("google_place_id")
        _googlePlaceType <- h.get[Option[String]]("google_place_type")
      } yield {
        InputVenueMessageContent(
          latitude = _latitude,
          longitude = _longitude,
          title = _title,
          address = _address,
          foursquareId = _foursquareId,
          foursquareType = _foursquareType,
          googlePlaceId = _googlePlaceId,
          googlePlaceType = _googlePlaceType
        )
      }
    }

  implicit lazy val inputinvoicemessagecontentEncoder: Encoder[InputInvoiceMessageContent] =
    (x: InputInvoiceMessageContent) => {
      Json.fromFields(
        List(
          "title" -> x.title.asJson,
          "description" -> x.description.asJson,
          "payload" -> x.payload.asJson,
          "provider_token" -> x.providerToken.asJson,
          "currency" -> x.currency.asJson,
          "prices" -> x.prices.asJson,
          "max_tip_amount" -> x.maxTipAmount.asJson,
          "suggested_tip_amounts" -> x.suggestedTipAmounts.asJson,
          "provider_data" -> x.providerData.asJson,
          "photo_url" -> x.photoUrl.asJson,
          "photo_size" -> x.photoSize.asJson,
          "photo_width" -> x.photoWidth.asJson,
          "photo_height" -> x.photoHeight.asJson,
          "need_name" -> x.needName.asJson,
          "need_phone_number" -> x.needPhoneNumber.asJson,
          "need_email" -> x.needEmail.asJson,
          "need_shipping_address" -> x.needShippingAddress.asJson,
          "send_phone_number_to_provider" -> x.sendPhoneNumberToProvider.asJson,
          "send_email_to_provider" -> x.sendEmailToProvider.asJson,
          "is_flexible" -> x.isFlexible.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputinvoicemessagecontentDecoder: Decoder[InputInvoiceMessageContent] =
    Decoder.instance { h =>
      for {
        _title <- h.get[String]("title")
        _description <- h.get[String]("description")
        _payload <- h.get[String]("payload")
        _providerToken <- h.get[String]("provider_token")
        _currency <- h.get[String]("currency")
        _prices <- h.getOrElse[List[LabeledPrice]]("prices")(List.empty)
        _maxTipAmount <- h.get[Option[Int]]("max_tip_amount")
        _suggestedTipAmounts <- h.getOrElse[List[Int]]("suggested_tip_amounts")(List.empty)
        _providerData <- h.get[Option[String]]("provider_data")
        _photoUrl <- h.get[Option[String]]("photo_url")
        _photoSize <- h.get[Option[Int]]("photo_size")
        _photoWidth <- h.get[Option[Int]]("photo_width")
        _photoHeight <- h.get[Option[Int]]("photo_height")
        _needName <- h.get[Option[Boolean]]("need_name")
        _needPhoneNumber <- h.get[Option[Boolean]]("need_phone_number")
        _needEmail <- h.get[Option[Boolean]]("need_email")
        _needShippingAddress <- h.get[Option[Boolean]]("need_shipping_address")
        _sendPhoneNumberToProvider <- h.get[Option[Boolean]]("send_phone_number_to_provider")
        _sendEmailToProvider <- h.get[Option[Boolean]]("send_email_to_provider")
        _isFlexible <- h.get[Option[Boolean]]("is_flexible")
      } yield {
        InputInvoiceMessageContent(
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

  implicit lazy val inputcontactmessagecontentEncoder: Encoder[InputContactMessageContent] =
    (x: InputContactMessageContent) => {
      Json.fromFields(
        List(
          "phone_number" -> x.phoneNumber.asJson,
          "first_name" -> x.firstName.asJson,
          "last_name" -> x.lastName.asJson,
          "vcard" -> x.vcard.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputcontactmessagecontentDecoder: Decoder[InputContactMessageContent] =
    Decoder.instance { h =>
      for {
        _phoneNumber <- h.get[String]("phone_number")
        _firstName <- h.get[String]("first_name")
        _lastName <- h.get[Option[String]]("last_name")
        _vcard <- h.get[Option[String]]("vcard")
      } yield {
        InputContactMessageContent(phoneNumber = _phoneNumber,
                                   firstName = _firstName,
                                   lastName = _lastName,
                                   vcard = _vcard
        )
      }
    }

  implicit lazy val inputlocationmessagecontentEncoder: Encoder[InputLocationMessageContent] =
    (x: InputLocationMessageContent) => {
      Json.fromFields(
        List(
          "latitude" -> x.latitude.asJson,
          "longitude" -> x.longitude.asJson,
          "horizontal_accuracy" -> x.horizontalAccuracy.asJson,
          "live_period" -> x.livePeriod.asJson,
          "heading" -> x.heading.asJson,
          "proximity_alert_radius" -> x.proximityAlertRadius.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputlocationmessagecontentDecoder: Decoder[InputLocationMessageContent] =
    Decoder.instance { h =>
      for {
        _latitude <- h.get[Float]("latitude")
        _longitude <- h.get[Float]("longitude")
        _horizontalAccuracy <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod <- h.get[Option[Int]]("live_period")
        _heading <- h.get[Option[Int]]("heading")
        _proximityAlertRadius <- h.get[Option[Int]]("proximity_alert_radius")
      } yield {
        InputLocationMessageContent(
          latitude = _latitude,
          longitude = _longitude,
          horizontalAccuracy = _horizontalAccuracy,
          livePeriod = _livePeriod,
          heading = _heading,
          proximityAlertRadius = _proximityAlertRadius
        )
      }
    }

  implicit lazy val inputtextmessagecontentEncoder: Encoder[InputTextMessageContent] =
    (x: InputTextMessageContent) => {
      Json.fromFields(
        List(
          "message_text" -> x.messageText.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "entities" -> x.entities.asJson,
          "disable_web_page_preview" -> x.disableWebPagePreview.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputtextmessagecontentDecoder: Decoder[InputTextMessageContent] =
    Decoder.instance { h =>
      for {
        _messageText <- h.get[String]("message_text")
        _parseMode <- h.get[Option[ParseMode]]("parse_mode")
        _entities <- h.getOrElse[List[MessageEntity]]("entities")(List.empty)
        _disableWebPagePreview <- h.get[Option[Boolean]]("disable_web_page_preview")
      } yield {
        InputTextMessageContent(messageText = _messageText,
                                parseMode = _parseMode,
                                entities = _entities,
                                disableWebPagePreview = _disableWebPagePreview
        )
      }
    }

  implicit lazy val passportelementerrorEncoder: Encoder[PassportElementError] = {
    case translation_file: PassportElementErrorTranslationFile =>
      translation_file.asJson.mapObject(_.add("type", Json.fromString("translation_file")))
    case translation_files: PassportElementErrorTranslationFiles =>
      translation_files.asJson.mapObject(_.add("type", Json.fromString("translation_files")))
    case reverse_side: PassportElementErrorReverseSide =>
      reverse_side.asJson.mapObject(_.add("type", Json.fromString("reverse_side")))
    case data: PassportElementErrorDataField =>
      data.asJson.mapObject(_.add("type", Json.fromString("data")))
    case front_side: PassportElementErrorFrontSide =>
      front_side.asJson.mapObject(_.add("type", Json.fromString("front_side")))
    case files: PassportElementErrorFiles =>
      files.asJson.mapObject(_.add("type", Json.fromString("files")))
    case unspecified: PassportElementErrorUnspecified =>
      unspecified.asJson.mapObject(_.add("type", Json.fromString("unspecified")))
    case file: PassportElementErrorFile =>
      file.asJson.mapObject(_.add("type", Json.fromString("file")))
    case selfie: PassportElementErrorSelfie =>
      selfie.asJson.mapObject(_.add("type", Json.fromString("selfie")))
  }
  implicit lazy val passportelementerrorDecoder: Decoder[PassportElementError] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "translation_file"  => Decoder[PassportElementErrorTranslationFile]
      case "translation_files" => Decoder[PassportElementErrorTranslationFiles]
      case "reverse_side"      => Decoder[PassportElementErrorReverseSide]
      case "data"              => Decoder[PassportElementErrorDataField]
      case "front_side"        => Decoder[PassportElementErrorFrontSide]
      case "files"             => Decoder[PassportElementErrorFiles]
      case "unspecified"       => Decoder[PassportElementErrorUnspecified]
      case "file"              => Decoder[PassportElementErrorFile]
      case "selfie"            => Decoder[PassportElementErrorSelfie]
    }
  } yield value

  implicit lazy val passportelementerrorfilesEncoder: Encoder[PassportElementErrorFiles] =
    (x: PassportElementErrorFiles) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "file_hashes" -> x.fileHashes.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorfilesDecoder: Decoder[PassportElementErrorFiles] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _fileHashes <- h.getOrElse[List[String]]("file_hashes")(List.empty)
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorFiles(`type` = _type, fileHashes = _fileHashes, message = _message)
      }
    }

  implicit lazy val passportelementerrordatafieldEncoder: Encoder[PassportElementErrorDataField] =
    (x: PassportElementErrorDataField) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "field_name" -> x.fieldName.asJson,
          "data_hash" -> x.dataHash.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrordatafieldDecoder: Decoder[PassportElementErrorDataField] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _fieldName <- h.get[String]("field_name")
        _dataHash <- h.get[String]("data_hash")
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorDataField(`type` = _type, fieldName = _fieldName, dataHash = _dataHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorreversesideEncoder: Encoder[PassportElementErrorReverseSide] =
    (x: PassportElementErrorReverseSide) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorreversesideDecoder: Decoder[PassportElementErrorReverseSide] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorReverseSide(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorselfieEncoder: Encoder[PassportElementErrorSelfie] =
    (x: PassportElementErrorSelfie) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorselfieDecoder: Decoder[PassportElementErrorSelfie] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorSelfie(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorfrontsideEncoder: Encoder[PassportElementErrorFrontSide] =
    (x: PassportElementErrorFrontSide) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorfrontsideDecoder: Decoder[PassportElementErrorFrontSide] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorFrontSide(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorfileEncoder: Encoder[PassportElementErrorFile] =
    (x: PassportElementErrorFile) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorfileDecoder: Decoder[PassportElementErrorFile] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorFile(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorunspecifiedEncoder: Encoder[PassportElementErrorUnspecified] =
    (x: PassportElementErrorUnspecified) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "element_hash" -> x.elementHash.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorunspecifiedDecoder: Decoder[PassportElementErrorUnspecified] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _elementHash <- h.get[String]("element_hash")
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorUnspecified(`type` = _type, elementHash = _elementHash, message = _message)
      }
    }

  implicit lazy val passportelementerrortranslationfileEncoder: Encoder[PassportElementErrorTranslationFile] =
    (x: PassportElementErrorTranslationFile) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrortranslationfileDecoder: Decoder[PassportElementErrorTranslationFile] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorTranslationFile(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrortranslationfilesEncoder: Encoder[PassportElementErrorTranslationFiles] =
    (x: PassportElementErrorTranslationFiles) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "file_hashes" -> x.fileHashes.asJson,
          "message" -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrortranslationfilesDecoder: Decoder[PassportElementErrorTranslationFiles] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _fileHashes <- h.getOrElse[List[String]]("file_hashes")(List.empty)
        _message <- h.get[String]("message")
      } yield {
        PassportElementErrorTranslationFiles(`type` = _type, fileHashes = _fileHashes, message = _message)
      }
    }

  implicit lazy val messageentityEncoder: Encoder[MessageEntity] = {
    case pre: PreMessageEntity => pre.asJson.mapObject(_.add("type", Json.fromString("pre")))
    case text_mention: TextMentionMessageEntity =>
      text_mention.asJson.mapObject(_.add("type", Json.fromString("text_mention")))
    case bot_command: BotCommandMessageEntity =>
      bot_command.asJson.mapObject(_.add("type", Json.fromString("bot_command")))
    case phone_number: PhoneNumberMessageEntity =>
      phone_number.asJson.mapObject(_.add("type", Json.fromString("phone_number")))
    case email: EmailMessageEntity =>
      email.asJson.mapObject(_.add("type", Json.fromString("email")))
    case url: UrlMessageEntity => url.asJson.mapObject(_.add("type", Json.fromString("url")))
    case underline: UnderlineMessageEntity =>
      underline.asJson.mapObject(_.add("type", Json.fromString("underline")))
    case italic: ItalicMessageEntity =>
      italic.asJson.mapObject(_.add("type", Json.fromString("italic")))
    case bold: BoldMessageEntity => bold.asJson.mapObject(_.add("type", Json.fromString("bold")))
    case cashtag: CashtagMessageEntity =>
      cashtag.asJson.mapObject(_.add("type", Json.fromString("cashtag")))
    case code: CodeMessageEntity => code.asJson.mapObject(_.add("type", Json.fromString("code")))
    case mention: MentionMessageEntity =>
      mention.asJson.mapObject(_.add("type", Json.fromString("mention")))
    case hashtag: HashtagMessageEntity =>
      hashtag.asJson.mapObject(_.add("type", Json.fromString("hashtag")))
    case text_link: TextLinkMessageEntity =>
      text_link.asJson.mapObject(_.add("type", Json.fromString("text_link")))
    case strikethrough: StrikethroughMessageEntity =>
      strikethrough.asJson.mapObject(_.add("type", Json.fromString("strikethrough")))
  }
  implicit lazy val messageentityDecoder: Decoder[MessageEntity] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "pre"           => Decoder[PreMessageEntity]
      case "text_mention"  => Decoder[TextMentionMessageEntity]
      case "bot_command"   => Decoder[BotCommandMessageEntity]
      case "phone_number"  => Decoder[PhoneNumberMessageEntity]
      case "email"         => Decoder[EmailMessageEntity]
      case "url"           => Decoder[UrlMessageEntity]
      case "underline"     => Decoder[UnderlineMessageEntity]
      case "italic"        => Decoder[ItalicMessageEntity]
      case "bold"          => Decoder[BoldMessageEntity]
      case "cashtag"       => Decoder[CashtagMessageEntity]
      case "code"          => Decoder[CodeMessageEntity]
      case "mention"       => Decoder[MentionMessageEntity]
      case "hashtag"       => Decoder[HashtagMessageEntity]
      case "text_link"     => Decoder[TextLinkMessageEntity]
      case "strikethrough" => Decoder[StrikethroughMessageEntity]
    }
  } yield value

  implicit lazy val mentionmessageentityEncoder: Encoder[MentionMessageEntity] =
    (x: MentionMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val mentionmessageentityDecoder: Decoder[MentionMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        MentionMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val cashtagmessageentityEncoder: Encoder[CashtagMessageEntity] =
    (x: CashtagMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val cashtagmessageentityDecoder: Decoder[CashtagMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        CashtagMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val codemessageentityEncoder: Encoder[CodeMessageEntity] =
    (x: CodeMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val codemessageentityDecoder: Decoder[CodeMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        CodeMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val botcommandmessageentityEncoder: Encoder[BotCommandMessageEntity] =
    (x: BotCommandMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botcommandmessageentityDecoder: Decoder[BotCommandMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        BotCommandMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val emailmessageentityEncoder: Encoder[EmailMessageEntity] =
    (x: EmailMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val emailmessageentityDecoder: Decoder[EmailMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        EmailMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val boldmessageentityEncoder: Encoder[BoldMessageEntity] =
    (x: BoldMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val boldmessageentityDecoder: Decoder[BoldMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        BoldMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val premessageentityEncoder: Encoder[PreMessageEntity] =
    (x: PreMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson,
          "language" -> x.language.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val premessageentityDecoder: Decoder[PreMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
        _language <- h.get[Option[String]]("language")
      } yield {
        PreMessageEntity(offset = _offset, length = _length, language = _language)
      }
    }

  implicit lazy val italicmessageentityEncoder: Encoder[ItalicMessageEntity] =
    (x: ItalicMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val italicmessageentityDecoder: Decoder[ItalicMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        ItalicMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val strikethroughmessageentityEncoder: Encoder[StrikethroughMessageEntity] =
    (x: StrikethroughMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val strikethroughmessageentityDecoder: Decoder[StrikethroughMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        StrikethroughMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val underlinemessageentityEncoder: Encoder[UnderlineMessageEntity] =
    (x: UnderlineMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val underlinemessageentityDecoder: Decoder[UnderlineMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        UnderlineMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val hashtagmessageentityEncoder: Encoder[HashtagMessageEntity] =
    (x: HashtagMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val hashtagmessageentityDecoder: Decoder[HashtagMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        HashtagMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val textmentionmessageentityEncoder: Encoder[TextMentionMessageEntity] =
    (x: TextMentionMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson,
          "user" -> x.user.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val textmentionmessageentityDecoder: Decoder[TextMentionMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
        _user <- h.get[User]("user")
      } yield {
        TextMentionMessageEntity(offset = _offset, length = _length, user = _user)
      }
    }

  implicit lazy val textlinkmessageentityEncoder: Encoder[TextLinkMessageEntity] =
    (x: TextLinkMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson,
          "url" -> x.url.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val textlinkmessageentityDecoder: Decoder[TextLinkMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
        _url <- h.get[String]("url")
      } yield {
        TextLinkMessageEntity(offset = _offset, length = _length, url = _url)
      }
    }

  implicit lazy val urlmessageentityEncoder: Encoder[UrlMessageEntity] =
    (x: UrlMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val urlmessageentityDecoder: Decoder[UrlMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        UrlMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val phonenumbermessageentityEncoder: Encoder[PhoneNumberMessageEntity] =
    (x: PhoneNumberMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val phonenumbermessageentityDecoder: Decoder[PhoneNumberMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        PhoneNumberMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val responseparametersEncoder: Encoder[ResponseParameters] =
    (x: ResponseParameters) => {
      Json.fromFields(
        List(
          "migrate_to_chat_id" -> x.migrateToChatId.asJson,
          "retry_after" -> x.retryAfter.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val responseparametersDecoder: Decoder[ResponseParameters] =
    Decoder.instance { h =>
      for {
        _migrateToChatId <- h.get[Option[Long]]("migrate_to_chat_id")
        _retryAfter <- h.get[Option[Int]]("retry_after")
      } yield {
        ResponseParameters(migrateToChatId = _migrateToChatId, retryAfter = _retryAfter)
      }
    }

  implicit lazy val animationEncoder: Encoder[Animation] =
    (x: Animation) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width" -> x.width.asJson,
          "height" -> x.height.asJson,
          "duration" -> x.duration.asJson,
          "thumb" -> x.thumb.asJson,
          "file_name" -> x.fileName.asJson,
          "mime_type" -> x.mimeType.asJson,
          "file_size" -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val animationDecoder: Decoder[Animation] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _width <- h.get[Int]("width")
        _height <- h.get[Int]("height")
        _duration <- h.get[Int]("duration")
        _thumb <- h.get[Option[PhotoSize]]("thumb")
        _fileName <- h.get[Option[String]]("file_name")
        _mimeType <- h.get[Option[String]]("mime_type")
        _fileSize <- h.get[Option[Int]]("file_size")
      } yield {
        Animation(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          width = _width,
          height = _height,
          duration = _duration,
          thumb = _thumb,
          fileName = _fileName,
          mimeType = _mimeType,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val chatEncoder: Encoder[Chat] =
    (x: Chat) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "type" -> x.`type`.asJson,
          "title" -> x.title.asJson,
          "username" -> x.username.asJson,
          "first_name" -> x.firstName.asJson,
          "last_name" -> x.lastName.asJson,
          "photo" -> x.photo.asJson,
          "bio" -> x.bio.asJson,
          "description" -> x.description.asJson,
          "invite_link" -> x.inviteLink.asJson,
          "pinned_message" -> x.pinnedMessage.asJson,
          "permissions" -> x.permissions.asJson,
          "slow_mode_delay" -> x.slowModeDelay.asJson,
          "message_auto_delete_time" -> x.messageAutoDeleteTime.asJson,
          "sticker_set_name" -> x.stickerSetName.asJson,
          "can_set_sticker_set" -> x.canSetStickerSet.asJson,
          "linked_chat_id" -> x.linkedChatId.asJson,
          "location" -> x.location.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatDecoder: Decoder[Chat] =
    Decoder.instance { h =>
      for {
        _id <- h.get[Long]("id")
        _type <- h.get[String]("type")
        _title <- h.get[Option[String]]("title")
        _username <- h.get[Option[String]]("username")
        _firstName <- h.get[Option[String]]("first_name")
        _lastName <- h.get[Option[String]]("last_name")
        _photo <- h.get[Option[ChatPhoto]]("photo")
        _bio <- h.get[Option[String]]("bio")
        _description <- h.get[Option[String]]("description")
        _inviteLink <- h.get[Option[String]]("invite_link")
        _pinnedMessage <- h.get[Option[Message]]("pinned_message")
        _permissions <- h.get[Option[ChatPermissions]]("permissions")
        _slowModeDelay <- h.get[Option[Int]]("slow_mode_delay")
        _messageAutoDeleteTime <- h.get[Option[Int]]("message_auto_delete_time")
        _stickerSetName <- h.get[Option[String]]("sticker_set_name")
        _canSetStickerSet <- h.get[Option[Boolean]]("can_set_sticker_set")
        _linkedChatId <- h.get[Option[Long]]("linked_chat_id")
        _location <- h.get[Option[ChatLocation]]("location")
      } yield {
        Chat(
          id = _id,
          `type` = _type,
          title = _title,
          username = _username,
          firstName = _firstName,
          lastName = _lastName,
          photo = _photo,
          bio = _bio,
          description = _description,
          inviteLink = _inviteLink,
          pinnedMessage = _pinnedMessage,
          permissions = _permissions,
          slowModeDelay = _slowModeDelay,
          messageAutoDeleteTime = _messageAutoDeleteTime,
          stickerSetName = _stickerSetName,
          canSetStickerSet = _canSetStickerSet,
          linkedChatId = _linkedChatId,
          location = _location
        )
      }
    }

  implicit lazy val videonoteEncoder: Encoder[VideoNote] =
    (x: VideoNote) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "length" -> x.length.asJson,
          "duration" -> x.duration.asJson,
          "thumb" -> x.thumb.asJson,
          "file_size" -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val videonoteDecoder: Decoder[VideoNote] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _length <- h.get[Int]("length")
        _duration <- h.get[Int]("duration")
        _thumb <- h.get[Option[PhotoSize]]("thumb")
        _fileSize <- h.get[Option[Int]]("file_size")
      } yield {
        VideoNote(fileId = _fileId,
                  fileUniqueId = _fileUniqueId,
                  length = _length,
                  duration = _duration,
                  thumb = _thumb,
                  fileSize = _fileSize
        )
      }
    }

  implicit lazy val locationEncoder: Encoder[Location] =
    (x: Location) => {
      Json.fromFields(
        List(
          "longitude" -> x.longitude.asJson,
          "latitude" -> x.latitude.asJson,
          "horizontal_accuracy" -> x.horizontalAccuracy.asJson,
          "live_period" -> x.livePeriod.asJson,
          "heading" -> x.heading.asJson,
          "proximity_alert_radius" -> x.proximityAlertRadius.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val locationDecoder: Decoder[Location] =
    Decoder.instance { h =>
      for {
        _longitude <- h.get[Float]("longitude")
        _latitude <- h.get[Float]("latitude")
        _horizontalAccuracy <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod <- h.get[Option[Int]]("live_period")
        _heading <- h.get[Option[Int]]("heading")
        _proximityAlertRadius <- h.get[Option[Int]]("proximity_alert_radius")
      } yield {
        Location(
          longitude = _longitude,
          latitude = _latitude,
          horizontalAccuracy = _horizontalAccuracy,
          livePeriod = _livePeriod,
          heading = _heading,
          proximityAlertRadius = _proximityAlertRadius
        )
      }
    }

  implicit lazy val shippingqueryEncoder: Encoder[ShippingQuery] =
    (x: ShippingQuery) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "from" -> x.from.asJson,
          "invoice_payload" -> x.invoicePayload.asJson,
          "shipping_address" -> x.shippingAddress.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val shippingqueryDecoder: Decoder[ShippingQuery] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _from <- h.get[User]("from")
        _invoicePayload <- h.get[String]("invoice_payload")
        _shippingAddress <- h.get[ShippingAddress]("shipping_address")
      } yield {
        ShippingQuery(id = _id, from = _from, invoicePayload = _invoicePayload, shippingAddress = _shippingAddress)
      }
    }

  implicit lazy val voicechatendedEncoder: Encoder[VoiceChatEnded] =
    (x: VoiceChatEnded) => {
      Json.fromFields(
        List(
          "duration" -> x.duration.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val voicechatendedDecoder: Decoder[VoiceChatEnded] =
    Decoder.instance { h =>
      for {
        _duration <- h.get[Int]("duration")
      } yield {
        VoiceChatEnded(duration = _duration)
      }
    }

  implicit lazy val chatpermissionsEncoder: Encoder[ChatPermissions] =
    (x: ChatPermissions) => {
      Json.fromFields(
        List(
          "can_send_messages" -> x.canSendMessages.asJson,
          "can_send_media_messages" -> x.canSendMediaMessages.asJson,
          "can_send_polls" -> x.canSendPolls.asJson,
          "can_send_other_messages" -> x.canSendOtherMessages.asJson,
          "can_add_web_page_previews" -> x.canAddWebPagePreviews.asJson,
          "can_change_info" -> x.canChangeInfo.asJson,
          "can_invite_users" -> x.canInviteUsers.asJson,
          "can_pin_messages" -> x.canPinMessages.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatpermissionsDecoder: Decoder[ChatPermissions] =
    Decoder.instance { h =>
      for {
        _canSendMessages <- h.get[Option[Boolean]]("can_send_messages")
        _canSendMediaMessages <- h.get[Option[Boolean]]("can_send_media_messages")
        _canSendPolls <- h.get[Option[Boolean]]("can_send_polls")
        _canSendOtherMessages <- h.get[Option[Boolean]]("can_send_other_messages")
        _canAddWebPagePreviews <- h.get[Option[Boolean]]("can_add_web_page_previews")
        _canChangeInfo <- h.get[Option[Boolean]]("can_change_info")
        _canInviteUsers <- h.get[Option[Boolean]]("can_invite_users")
        _canPinMessages <- h.get[Option[Boolean]]("can_pin_messages")
      } yield {
        ChatPermissions(
          canSendMessages = _canSendMessages,
          canSendMediaMessages = _canSendMediaMessages,
          canSendPolls = _canSendPolls,
          canSendOtherMessages = _canSendOtherMessages,
          canAddWebPagePreviews = _canAddWebPagePreviews,
          canChangeInfo = _canChangeInfo,
          canInviteUsers = _canInviteUsers,
          canPinMessages = _canPinMessages
        )
      }
    }

  implicit lazy val polloptionEncoder: Encoder[PollOption] =
    (x: PollOption) => {
      Json.fromFields(
        List(
          "text" -> x.text.asJson,
          "voter_count" -> x.voterCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val polloptionDecoder: Decoder[PollOption] =
    Decoder.instance { h =>
      for {
        _text <- h.get[String]("text")
        _voterCount <- h.get[Int]("voter_count")
      } yield {
        PollOption(text = _text, voterCount = _voterCount)
      }
    }

  implicit lazy val shippingaddressEncoder: Encoder[ShippingAddress] =
    (x: ShippingAddress) => {
      Json.fromFields(
        List(
          "country_code" -> x.countryCode.asJson,
          "state" -> x.state.asJson,
          "city" -> x.city.asJson,
          "street_line1" -> x.streetLine1.asJson,
          "street_line2" -> x.streetLine2.asJson,
          "post_code" -> x.postCode.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val shippingaddressDecoder: Decoder[ShippingAddress] =
    Decoder.instance { h =>
      for {
        _countryCode <- h.get[String]("country_code")
        _state <- h.get[String]("state")
        _city <- h.get[String]("city")
        _streetLine1 <- h.get[String]("street_line1")
        _streetLine2 <- h.get[String]("street_line2")
        _postCode <- h.get[String]("post_code")
      } yield {
        ShippingAddress(countryCode = _countryCode,
                        state = _state,
                        city = _city,
                        streetLine1 = _streetLine1,
                        streetLine2 = _streetLine2,
                        postCode = _postCode
        )
      }
    }

  implicit lazy val chatlocationEncoder: Encoder[ChatLocation] =
    (x: ChatLocation) => {
      Json.fromFields(
        List(
          "location" -> x.location.asJson,
          "address" -> x.address.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatlocationDecoder: Decoder[ChatLocation] =
    Decoder.instance { h =>
      for {
        _location <- h.get[Location]("location")
        _address <- h.get[String]("address")
      } yield {
        ChatLocation(location = _location, address = _address)
      }
    }

  implicit lazy val orderinfoEncoder: Encoder[OrderInfo] =
    (x: OrderInfo) => {
      Json.fromFields(
        List(
          "name" -> x.name.asJson,
          "phone_number" -> x.phoneNumber.asJson,
          "email" -> x.email.asJson,
          "shipping_address" -> x.shippingAddress.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val orderinfoDecoder: Decoder[OrderInfo] =
    Decoder.instance { h =>
      for {
        _name <- h.get[Option[String]]("name")
        _phoneNumber <- h.get[Option[String]]("phone_number")
        _email <- h.get[Option[String]]("email")
        _shippingAddress <- h.get[Option[ShippingAddress]]("shipping_address")
      } yield {
        OrderInfo(name = _name, phoneNumber = _phoneNumber, email = _email, shippingAddress = _shippingAddress)
      }
    }

  implicit lazy val inputfileEncoder: Encoder[InputFile.type] = (_: InputFile.type) => ().asJson
  implicit lazy val inputfileDecoder: Decoder[InputFile.type] = (_: HCursor) => Right(InputFile)
  implicit lazy val updateEncoder: Encoder[Update] =
    (x: Update) => {
      Json.fromFields(
        List(
          "update_id" -> x.updateId.asJson,
          "message" -> x.message.asJson,
          "edited_message" -> x.editedMessage.asJson,
          "channel_post" -> x.channelPost.asJson,
          "edited_channel_post" -> x.editedChannelPost.asJson,
          "inline_query" -> x.inlineQuery.asJson,
          "chosen_inline_result" -> x.chosenInlineResult.asJson,
          "callback_query" -> x.callbackQuery.asJson,
          "shipping_query" -> x.shippingQuery.asJson,
          "pre_checkout_query" -> x.preCheckoutQuery.asJson,
          "poll" -> x.poll.asJson,
          "poll_answer" -> x.pollAnswer.asJson,
          "my_chat_member" -> x.myChatMember.asJson,
          "chat_member" -> x.chatMember.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val updateDecoder: Decoder[Update] =
    Decoder.instance { h =>
      for {
        _updateId <- h.get[Int]("update_id")
        _message <- h.get[Option[Message]]("message")
        _editedMessage <- h.get[Option[Message]]("edited_message")
        _channelPost <- h.get[Option[Message]]("channel_post")
        _editedChannelPost <- h.get[Option[Message]]("edited_channel_post")
        _inlineQuery <- h.get[Option[InlineQuery]]("inline_query")
        _chosenInlineResult <- h.get[Option[ChosenInlineResult]]("chosen_inline_result")
        _callbackQuery <- h.get[Option[CallbackQuery]]("callback_query")
        _shippingQuery <- h.get[Option[ShippingQuery]]("shipping_query")
        _preCheckoutQuery <- h.get[Option[PreCheckoutQuery]]("pre_checkout_query")
        _poll <- h.get[Option[Poll]]("poll")
        _pollAnswer <- h.get[Option[PollAnswer]]("poll_answer")
        _myChatMember <- h.get[Option[ChatMemberUpdated]]("my_chat_member")
        _chatMember <- h.get[Option[ChatMemberUpdated]]("chat_member")
      } yield {
        Update(
          updateId = _updateId,
          message = _message,
          editedMessage = _editedMessage,
          channelPost = _channelPost,
          editedChannelPost = _editedChannelPost,
          inlineQuery = _inlineQuery,
          chosenInlineResult = _chosenInlineResult,
          callbackQuery = _callbackQuery,
          shippingQuery = _shippingQuery,
          preCheckoutQuery = _preCheckoutQuery,
          poll = _poll,
          pollAnswer = _pollAnswer,
          myChatMember = _myChatMember,
          chatMember = _chatMember
        )
      }
    }

  implicit lazy val maskpositionEncoder: Encoder[MaskPosition] =
    (x: MaskPosition) => {
      Json.fromFields(
        List(
          "point" -> x.point.asJson,
          "x_shift" -> x.xShift.asJson,
          "y_shift" -> x.yShift.asJson,
          "scale" -> x.scale.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val maskpositionDecoder: Decoder[MaskPosition] =
    Decoder.instance { h =>
      for {
        _point <- h.get[String]("point")
        _xShift <- h.get[Float]("x_shift")
        _yShift <- h.get[Float]("y_shift")
        _scale <- h.get[Float]("scale")
      } yield {
        MaskPosition(point = _point, xShift = _xShift, yShift = _yShift, scale = _scale)
      }
    }

  implicit lazy val callbackgameEncoder: Encoder[CallbackGame.type] = (_: CallbackGame.type) => ().asJson
  implicit lazy val callbackgameDecoder: Decoder[CallbackGame.type] = (_: HCursor) => Right(CallbackGame)
  implicit lazy val keyboardbuttonEncoder: Encoder[KeyboardButton] =
    (x: KeyboardButton) => {
      Json.fromFields(
        List(
          "text" -> x.text.asJson,
          "request_contact" -> x.requestContact.asJson,
          "request_location" -> x.requestLocation.asJson,
          "request_poll" -> x.requestPoll.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val keyboardbuttonDecoder: Decoder[KeyboardButton] =
    Decoder.instance { h =>
      for {
        _text <- h.get[String]("text")
        _requestContact <- h.get[Option[Boolean]]("request_contact")
        _requestLocation <- h.get[Option[Boolean]]("request_location")
        _requestPoll <- h.get[Option[KeyboardButtonPollType]]("request_poll")
      } yield {
        KeyboardButton(text = _text,
                       requestContact = _requestContact,
                       requestLocation = _requestLocation,
                       requestPoll = _requestPoll
        )
      }
    }

  implicit lazy val passportfileEncoder: Encoder[PassportFile] =
    (x: PassportFile) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "file_size" -> x.fileSize.asJson,
          "file_date" -> x.fileDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportfileDecoder: Decoder[PassportFile] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _fileSize <- h.get[Int]("file_size")
        _fileDate <- h.get[Int]("file_date")
      } yield {
        PassportFile(fileId = _fileId, fileUniqueId = _fileUniqueId, fileSize = _fileSize, fileDate = _fileDate)
      }
    }

  implicit lazy val photosizeEncoder: Encoder[PhotoSize] =
    (x: PhotoSize) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width" -> x.width.asJson,
          "height" -> x.height.asJson,
          "file_size" -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val photosizeDecoder: Decoder[PhotoSize] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _width <- h.get[Int]("width")
        _height <- h.get[Int]("height")
        _fileSize <- h.get[Option[Int]]("file_size")
      } yield {
        PhotoSize(fileId = _fileId,
                  fileUniqueId = _fileUniqueId,
                  width = _width,
                  height = _height,
                  fileSize = _fileSize
        )
      }
    }

  implicit lazy val keyboardbuttonpolltypeEncoder: Encoder[KeyboardButtonPollType] =
    (x: KeyboardButtonPollType) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val keyboardbuttonpolltypeDecoder: Decoder[KeyboardButtonPollType] =
    Decoder.instance { h =>
      for {
        _type <- h.get[Option[String]]("type")
      } yield {
        KeyboardButtonPollType(`type` = _type)
      }
    }

  implicit lazy val pollEncoder: Encoder[Poll] =
    (x: Poll) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "question" -> x.question.asJson,
          "options" -> x.options.asJson,
          "total_voter_count" -> x.totalVoterCount.asJson,
          "is_closed" -> x.isClosed.asJson,
          "is_anonymous" -> x.isAnonymous.asJson,
          "type" -> x.`type`.asJson,
          "allows_multiple_answers" -> x.allowsMultipleAnswers.asJson,
          "correct_option_id" -> x.correctOptionId.asJson,
          "explanation" -> x.explanation.asJson,
          "explanation_entities" -> x.explanationEntities.asJson,
          "open_period" -> x.openPeriod.asJson,
          "close_date" -> x.closeDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pollDecoder: Decoder[Poll] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _question <- h.get[String]("question")
        _options <- h.getOrElse[List[PollOption]]("options")(List.empty)
        _totalVoterCount <- h.get[Int]("total_voter_count")
        _isClosed <- h.get[Boolean]("is_closed")
        _isAnonymous <- h.get[Boolean]("is_anonymous")
        _type <- h.get[String]("type")
        _allowsMultipleAnswers <- h.get[Boolean]("allows_multiple_answers")
        _correctOptionId <- h.get[Option[Int]]("correct_option_id")
        _explanation <- h.get[Option[String]]("explanation")
        _explanationEntities <- h.getOrElse[List[MessageEntity]]("explanation_entities")(List.empty)
        _openPeriod <- h.get[Option[Int]]("open_period")
        _closeDate <- h.get[Option[Int]]("close_date")
      } yield {
        Poll(
          id = _id,
          question = _question,
          options = _options,
          totalVoterCount = _totalVoterCount,
          isClosed = _isClosed,
          isAnonymous = _isAnonymous,
          `type` = _type,
          allowsMultipleAnswers = _allowsMultipleAnswers,
          correctOptionId = _correctOptionId,
          explanation = _explanation,
          explanationEntities = _explanationEntities,
          openPeriod = _openPeriod,
          closeDate = _closeDate
        )
      }
    }

  implicit lazy val stickersetEncoder: Encoder[StickerSet] =
    (x: StickerSet) => {
      Json.fromFields(
        List(
          "name" -> x.name.asJson,
          "title" -> x.title.asJson,
          "is_animated" -> x.isAnimated.asJson,
          "contains_masks" -> x.containsMasks.asJson,
          "stickers" -> x.stickers.asJson,
          "thumb" -> x.thumb.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stickersetDecoder: Decoder[StickerSet] =
    Decoder.instance { h =>
      for {
        _name <- h.get[String]("name")
        _title <- h.get[String]("title")
        _isAnimated <- h.get[Boolean]("is_animated")
        _containsMasks <- h.get[Boolean]("contains_masks")
        _stickers <- h.getOrElse[List[Sticker]]("stickers")(List.empty)
        _thumb <- h.get[Option[PhotoSize]]("thumb")
      } yield {
        StickerSet(name = _name,
                   title = _title,
                   isAnimated = _isAnimated,
                   containsMasks = _containsMasks,
                   stickers = _stickers,
                   thumb = _thumb
        )
      }
    }

  implicit lazy val pollanswerEncoder: Encoder[PollAnswer] =
    (x: PollAnswer) => {
      Json.fromFields(
        List(
          "poll_id" -> x.pollId.asJson,
          "user" -> x.user.asJson,
          "option_ids" -> x.optionIds.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pollanswerDecoder: Decoder[PollAnswer] =
    Decoder.instance { h =>
      for {
        _pollId <- h.get[String]("poll_id")
        _user <- h.get[User]("user")
        _optionIds <- h.getOrElse[List[Int]]("option_ids")(List.empty)
      } yield {
        PollAnswer(pollId = _pollId, user = _user, optionIds = _optionIds)
      }
    }

  implicit lazy val contactEncoder: Encoder[Contact] =
    (x: Contact) => {
      Json.fromFields(
        List(
          "phone_number" -> x.phoneNumber.asJson,
          "first_name" -> x.firstName.asJson,
          "last_name" -> x.lastName.asJson,
          "user_id" -> x.userId.asJson,
          "vcard" -> x.vcard.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val contactDecoder: Decoder[Contact] =
    Decoder.instance { h =>
      for {
        _phoneNumber <- h.get[String]("phone_number")
        _firstName <- h.get[String]("first_name")
        _lastName <- h.get[Option[String]]("last_name")
        _userId <- h.get[Option[Long]]("user_id")
        _vcard <- h.get[Option[String]]("vcard")
      } yield {
        Contact(phoneNumber = _phoneNumber,
                firstName = _firstName,
                lastName = _lastName,
                userId = _userId,
                vcard = _vcard
        )
      }
    }

  implicit lazy val gamehighscoreEncoder: Encoder[GameHighScore] =
    (x: GameHighScore) => {
      Json.fromFields(
        List(
          "position" -> x.position.asJson,
          "user" -> x.user.asJson,
          "score" -> x.score.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val gamehighscoreDecoder: Decoder[GameHighScore] =
    Decoder.instance { h =>
      for {
        _position <- h.get[Int]("position")
        _user <- h.get[User]("user")
        _score <- h.get[Int]("score")
      } yield {
        GameHighScore(position = _position, user = _user, score = _score)
      }
    }

  implicit lazy val messageautodeletetimerchangedEncoder: Encoder[MessageAutoDeleteTimerChanged] =
    (x: MessageAutoDeleteTimerChanged) => {
      Json.fromFields(
        List(
          "message_auto_delete_time" -> x.messageAutoDeleteTime.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageautodeletetimerchangedDecoder: Decoder[MessageAutoDeleteTimerChanged] =
    Decoder.instance { h =>
      for {
        _messageAutoDeleteTime <- h.get[Int]("message_auto_delete_time")
      } yield {
        MessageAutoDeleteTimerChanged(messageAutoDeleteTime = _messageAutoDeleteTime)
      }
    }

  implicit lazy val labeledpriceEncoder: Encoder[LabeledPrice] =
    (x: LabeledPrice) => {
      Json.fromFields(
        List(
          "label" -> x.label.asJson,
          "amount" -> x.amount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val labeledpriceDecoder: Decoder[LabeledPrice] =
    Decoder.instance { h =>
      for {
        _label <- h.get[String]("label")
        _amount <- h.get[Int]("amount")
      } yield {
        LabeledPrice(label = _label, amount = _amount)
      }
    }

  implicit lazy val venueEncoder: Encoder[Venue] =
    (x: Venue) => {
      Json.fromFields(
        List(
          "location" -> x.location.asJson,
          "title" -> x.title.asJson,
          "address" -> x.address.asJson,
          "foursquare_id" -> x.foursquareId.asJson,
          "foursquare_type" -> x.foursquareType.asJson,
          "google_place_id" -> x.googlePlaceId.asJson,
          "google_place_type" -> x.googlePlaceType.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val venueDecoder: Decoder[Venue] =
    Decoder.instance { h =>
      for {
        _location <- h.get[Location]("location")
        _title <- h.get[String]("title")
        _address <- h.get[String]("address")
        _foursquareId <- h.get[Option[String]]("foursquare_id")
        _foursquareType <- h.get[Option[String]]("foursquare_type")
        _googlePlaceId <- h.get[Option[String]]("google_place_id")
        _googlePlaceType <- h.get[Option[String]]("google_place_type")
      } yield {
        Venue(
          location = _location,
          title = _title,
          address = _address,
          foursquareId = _foursquareId,
          foursquareType = _foursquareType,
          googlePlaceId = _googlePlaceId,
          googlePlaceType = _googlePlaceType
        )
      }
    }

  implicit lazy val successfulpaymentEncoder: Encoder[SuccessfulPayment] =
    (x: SuccessfulPayment) => {
      Json.fromFields(
        List(
          "currency" -> x.currency.asJson,
          "total_amount" -> x.totalAmount.asJson,
          "invoice_payload" -> x.invoicePayload.asJson,
          "shipping_option_id" -> x.shippingOptionId.asJson,
          "order_info" -> x.orderInfo.asJson,
          "telegram_payment_charge_id" -> x.telegramPaymentChargeId.asJson,
          "provider_payment_charge_id" -> x.providerPaymentChargeId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val successfulpaymentDecoder: Decoder[SuccessfulPayment] =
    Decoder.instance { h =>
      for {
        _currency <- h.get[String]("currency")
        _totalAmount <- h.get[Int]("total_amount")
        _invoicePayload <- h.get[String]("invoice_payload")
        _shippingOptionId <- h.get[Option[String]]("shipping_option_id")
        _orderInfo <- h.get[Option[OrderInfo]]("order_info")
        _telegramPaymentChargeId <- h.get[String]("telegram_payment_charge_id")
        _providerPaymentChargeId <- h.get[String]("provider_payment_charge_id")
      } yield {
        SuccessfulPayment(
          currency = _currency,
          totalAmount = _totalAmount,
          invoicePayload = _invoicePayload,
          shippingOptionId = _shippingOptionId,
          orderInfo = _orderInfo,
          telegramPaymentChargeId = _telegramPaymentChargeId,
          providerPaymentChargeId = _providerPaymentChargeId
        )
      }
    }

  implicit lazy val chatinvitelinkEncoder: Encoder[ChatInviteLink] =
    (x: ChatInviteLink) => {
      Json.fromFields(
        List(
          "invite_link" -> x.inviteLink.asJson,
          "creator" -> x.creator.asJson,
          "is_primary" -> x.isPrimary.asJson,
          "is_revoked" -> x.isRevoked.asJson,
          "expire_date" -> x.expireDate.asJson,
          "member_limit" -> x.memberLimit.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatinvitelinkDecoder: Decoder[ChatInviteLink] =
    Decoder.instance { h =>
      for {
        _inviteLink <- h.get[String]("invite_link")
        _creator <- h.get[User]("creator")
        _isPrimary <- h.get[Boolean]("is_primary")
        _isRevoked <- h.get[Boolean]("is_revoked")
        _expireDate <- h.get[Option[Int]]("expire_date")
        _memberLimit <- h.get[Option[Int]]("member_limit")
      } yield {
        ChatInviteLink(inviteLink = _inviteLink,
                       creator = _creator,
                       isPrimary = _isPrimary,
                       isRevoked = _isRevoked,
                       expireDate = _expireDate,
                       memberLimit = _memberLimit
        )
      }
    }

  implicit lazy val diceEncoder: Encoder[Dice] =
    (x: Dice) => {
      Json.fromFields(
        List(
          "emoji" -> x.emoji.asJson,
          "value" -> x.value.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val diceDecoder: Decoder[Dice] =
    Decoder.instance { h =>
      for {
        _emoji <- h.get[Emoji]("emoji")
        _value <- h.get[Int]("value")
      } yield {
        Dice(emoji = _emoji, value = _value)
      }
    }

  implicit lazy val chatmemberupdatedEncoder: Encoder[ChatMemberUpdated] =
    (x: ChatMemberUpdated) => {
      Json.fromFields(
        List(
          "chat" -> x.chat.asJson,
          "from" -> x.from.asJson,
          "date" -> x.date.asJson,
          "old_chat_member" -> x.oldChatMember.asJson,
          "new_chat_member" -> x.newChatMember.asJson,
          "invite_link" -> x.inviteLink.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberupdatedDecoder: Decoder[ChatMemberUpdated] =
    Decoder.instance { h =>
      for {
        _chat <- h.get[Chat]("chat")
        _from <- h.get[User]("from")
        _date <- h.get[Int]("date")
        _oldChatMember <- h.get[ChatMember]("old_chat_member")
        _newChatMember <- h.get[ChatMember]("new_chat_member")
        _inviteLink <- h.get[Option[ChatInviteLink]]("invite_link")
      } yield {
        ChatMemberUpdated(chat = _chat,
                          from = _from,
                          date = _date,
                          oldChatMember = _oldChatMember,
                          newChatMember = _newChatMember,
                          inviteLink = _inviteLink
        )
      }
    }

  implicit lazy val passportdataEncoder: Encoder[PassportData] =
    (x: PassportData) => {
      Json.fromFields(
        List(
          "data" -> x.data.asJson,
          "credentials" -> x.credentials.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportdataDecoder: Decoder[PassportData] =
    Decoder.instance { h =>
      for {
        _data <- h.getOrElse[List[EncryptedPassportElement]]("data")(List.empty)
        _credentials <- h.get[EncryptedCredentials]("credentials")
      } yield {
        PassportData(data = _data, credentials = _credentials)
      }
    }

  implicit lazy val fileEncoder: Encoder[File] =
    (x: File) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "file_size" -> x.fileSize.asJson,
          "file_path" -> x.filePath.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val fileDecoder: Decoder[File] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _fileSize <- h.get[Option[Int]]("file_size")
        _filePath <- h.get[Option[String]]("file_path")
      } yield {
        File(fileId = _fileId, fileUniqueId = _fileUniqueId, fileSize = _fileSize, filePath = _filePath)
      }
    }

  implicit lazy val gameEncoder: Encoder[Game] =
    (x: Game) => {
      Json.fromFields(
        List(
          "title" -> x.title.asJson,
          "description" -> x.description.asJson,
          "photo" -> x.photo.asJson,
          "text" -> x.text.asJson,
          "text_entities" -> x.textEntities.asJson,
          "animation" -> x.animation.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val gameDecoder: Decoder[Game] =
    Decoder.instance { h =>
      for {
        _title <- h.get[String]("title")
        _description <- h.get[String]("description")
        _photo <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
        _text <- h.get[Option[String]]("text")
        _textEntities <- h.getOrElse[List[MessageEntity]]("text_entities")(List.empty)
        _animation <- h.get[Option[Animation]]("animation")
      } yield {
        Game(title = _title,
             description = _description,
             photo = _photo,
             text = _text,
             textEntities = _textEntities,
             animation = _animation
        )
      }
    }

  implicit lazy val choseninlineresultEncoder: Encoder[ChosenInlineResult] =
    (x: ChosenInlineResult) => {
      Json.fromFields(
        List(
          "result_id" -> x.resultId.asJson,
          "from" -> x.from.asJson,
          "location" -> x.location.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "query" -> x.query.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val choseninlineresultDecoder: Decoder[ChosenInlineResult] =
    Decoder.instance { h =>
      for {
        _resultId <- h.get[String]("result_id")
        _from <- h.get[User]("from")
        _location <- h.get[Option[Location]]("location")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
        _query <- h.get[String]("query")
      } yield {
        ChosenInlineResult(resultId = _resultId,
                           from = _from,
                           location = _location,
                           inlineMessageId = _inlineMessageId,
                           query = _query
        )
      }
    }

  implicit lazy val botcommandEncoder: Encoder[BotCommand] =
    (x: BotCommand) => {
      Json.fromFields(
        List(
          "command" -> x.command.asJson,
          "description" -> x.description.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botcommandDecoder: Decoder[BotCommand] =
    Decoder.instance { h =>
      for {
        _command <- h.get[String]("command")
        _description <- h.get[String]("description")
      } yield {
        BotCommand(command = _command, description = _description)
      }
    }

  implicit lazy val audioEncoder: Encoder[Audio] =
    (x: Audio) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "duration" -> x.duration.asJson,
          "performer" -> x.performer.asJson,
          "title" -> x.title.asJson,
          "file_name" -> x.fileName.asJson,
          "mime_type" -> x.mimeType.asJson,
          "file_size" -> x.fileSize.asJson,
          "thumb" -> x.thumb.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val audioDecoder: Decoder[Audio] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _duration <- h.get[Int]("duration")
        _performer <- h.get[Option[String]]("performer")
        _title <- h.get[Option[String]]("title")
        _fileName <- h.get[Option[String]]("file_name")
        _mimeType <- h.get[Option[String]]("mime_type")
        _fileSize <- h.get[Option[Int]]("file_size")
        _thumb <- h.get[Option[PhotoSize]]("thumb")
      } yield {
        Audio(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          duration = _duration,
          performer = _performer,
          title = _title,
          fileName = _fileName,
          mimeType = _mimeType,
          fileSize = _fileSize,
          thumb = _thumb
        )
      }
    }

  implicit lazy val webhookinfoEncoder: Encoder[WebhookInfo] =
    (x: WebhookInfo) => {
      Json.fromFields(
        List(
          "url" -> x.url.asJson,
          "has_custom_certificate" -> x.hasCustomCertificate.asJson,
          "pending_update_count" -> x.pendingUpdateCount.asJson,
          "ip_address" -> x.ipAddress.asJson,
          "last_error_date" -> x.lastErrorDate.asJson,
          "last_error_message" -> x.lastErrorMessage.asJson,
          "max_connections" -> x.maxConnections.asJson,
          "allowed_updates" -> x.allowedUpdates.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val webhookinfoDecoder: Decoder[WebhookInfo] =
    Decoder.instance { h =>
      for {
        _url <- h.get[String]("url")
        _hasCustomCertificate <- h.get[Boolean]("has_custom_certificate")
        _pendingUpdateCount <- h.get[Int]("pending_update_count")
        _ipAddress <- h.get[Option[String]]("ip_address")
        _lastErrorDate <- h.get[Option[Int]]("last_error_date")
        _lastErrorMessage <- h.get[Option[String]]("last_error_message")
        _maxConnections <- h.get[Option[Int]]("max_connections")
        _allowedUpdates <- h.getOrElse[List[String]]("allowed_updates")(List.empty)
      } yield {
        WebhookInfo(
          url = _url,
          hasCustomCertificate = _hasCustomCertificate,
          pendingUpdateCount = _pendingUpdateCount,
          ipAddress = _ipAddress,
          lastErrorDate = _lastErrorDate,
          lastErrorMessage = _lastErrorMessage,
          maxConnections = _maxConnections,
          allowedUpdates = _allowedUpdates
        )
      }
    }

  implicit lazy val proximityalerttriggeredEncoder: Encoder[ProximityAlertTriggered] =
    (x: ProximityAlertTriggered) => {
      Json.fromFields(
        List(
          "traveler" -> x.traveler.asJson,
          "watcher" -> x.watcher.asJson,
          "distance" -> x.distance.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val proximityalerttriggeredDecoder: Decoder[ProximityAlertTriggered] =
    Decoder.instance { h =>
      for {
        _traveler <- h.get[User]("traveler")
        _watcher <- h.get[User]("watcher")
        _distance <- h.get[Int]("distance")
      } yield {
        ProximityAlertTriggered(traveler = _traveler, watcher = _watcher, distance = _distance)
      }
    }

  implicit lazy val voicechatscheduledEncoder: Encoder[VoiceChatScheduled] =
    (x: VoiceChatScheduled) => {
      Json.fromFields(
        List(
          "start_date" -> x.startDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val voicechatscheduledDecoder: Decoder[VoiceChatScheduled] =
    Decoder.instance { h =>
      for {
        _startDate <- h.get[Int]("start_date")
      } yield {
        VoiceChatScheduled(startDate = _startDate)
      }
    }

  implicit lazy val invoiceEncoder: Encoder[Invoice] =
    (x: Invoice) => {
      Json.fromFields(
        List(
          "title" -> x.title.asJson,
          "description" -> x.description.asJson,
          "start_parameter" -> x.startParameter.asJson,
          "currency" -> x.currency.asJson,
          "total_amount" -> x.totalAmount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val invoiceDecoder: Decoder[Invoice] =
    Decoder.instance { h =>
      for {
        _title <- h.get[String]("title")
        _description <- h.get[String]("description")
        _startParameter <- h.get[String]("start_parameter")
        _currency <- h.get[String]("currency")
        _totalAmount <- h.get[Int]("total_amount")
      } yield {
        Invoice(title = _title,
                description = _description,
                startParameter = _startParameter,
                currency = _currency,
                totalAmount = _totalAmount
        )
      }
    }

  implicit lazy val chatphotoEncoder: Encoder[ChatPhoto] =
    (x: ChatPhoto) => {
      Json.fromFields(
        List(
          "small_file_id" -> x.smallFileId.asJson,
          "small_file_unique_id" -> x.smallFileUniqueId.asJson,
          "big_file_id" -> x.bigFileId.asJson,
          "big_file_unique_id" -> x.bigFileUniqueId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatphotoDecoder: Decoder[ChatPhoto] =
    Decoder.instance { h =>
      for {
        _smallFileId <- h.get[String]("small_file_id")
        _smallFileUniqueId <- h.get[String]("small_file_unique_id")
        _bigFileId <- h.get[String]("big_file_id")
        _bigFileUniqueId <- h.get[String]("big_file_unique_id")
      } yield {
        ChatPhoto(smallFileId = _smallFileId,
                  smallFileUniqueId = _smallFileUniqueId,
                  bigFileId = _bigFileId,
                  bigFileUniqueId = _bigFileUniqueId
        )
      }
    }

  implicit lazy val inlinequeryEncoder: Encoder[InlineQuery] =
    (x: InlineQuery) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "from" -> x.from.asJson,
          "query" -> x.query.asJson,
          "offset" -> x.offset.asJson,
          "chat_type" -> x.chatType.asJson,
          "location" -> x.location.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryDecoder: Decoder[InlineQuery] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _from <- h.get[User]("from")
        _query <- h.get[String]("query")
        _offset <- h.get[String]("offset")
        _chatType <- h.get[Option[String]]("chat_type")
        _location <- h.get[Option[Location]]("location")
      } yield {
        InlineQuery(id = _id,
                    from = _from,
                    query = _query,
                    offset = _offset,
                    chatType = _chatType,
                    location = _location
        )
      }
    }

  implicit lazy val userEncoder: Encoder[User] =
    (x: User) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "is_bot" -> x.isBot.asJson,
          "first_name" -> x.firstName.asJson,
          "last_name" -> x.lastName.asJson,
          "username" -> x.username.asJson,
          "language_code" -> x.languageCode.asJson,
          "can_join_groups" -> x.canJoinGroups.asJson,
          "can_read_all_group_messages" -> x.canReadAllGroupMessages.asJson,
          "supports_inline_queries" -> x.supportsInlineQueries.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val userDecoder: Decoder[User] =
    Decoder.instance { h =>
      for {
        _id <- h.get[Long]("id")
        _isBot <- h.get[Boolean]("is_bot")
        _firstName <- h.get[String]("first_name")
        _lastName <- h.get[Option[String]]("last_name")
        _username <- h.get[Option[String]]("username")
        _languageCode <- h.get[Option[String]]("language_code")
        _canJoinGroups <- h.get[Option[Boolean]]("can_join_groups")
        _canReadAllGroupMessages <- h.get[Option[Boolean]]("can_read_all_group_messages")
        _supportsInlineQueries <- h.get[Option[Boolean]]("supports_inline_queries")
      } yield {
        User(
          id = _id,
          isBot = _isBot,
          firstName = _firstName,
          lastName = _lastName,
          username = _username,
          languageCode = _languageCode,
          canJoinGroups = _canJoinGroups,
          canReadAllGroupMessages = _canReadAllGroupMessages,
          supportsInlineQueries = _supportsInlineQueries
        )
      }
    }

  implicit lazy val encryptedpassportelementEncoder: Encoder[EncryptedPassportElement] =
    (x: EncryptedPassportElement) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson,
          "data" -> x.data.asJson,
          "phone_number" -> x.phoneNumber.asJson,
          "email" -> x.email.asJson,
          "files" -> x.files.asJson,
          "front_side" -> x.frontSide.asJson,
          "reverse_side" -> x.reverseSide.asJson,
          "selfie" -> x.selfie.asJson,
          "translation" -> x.translation.asJson,
          "hash" -> x.hash.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val encryptedpassportelementDecoder: Decoder[EncryptedPassportElement] =
    Decoder.instance { h =>
      for {
        _type <- h.get[String]("type")
        _data <- h.get[Option[String]]("data")
        _phoneNumber <- h.get[Option[String]]("phone_number")
        _email <- h.get[Option[String]]("email")
        _files <- h.getOrElse[List[PassportFile]]("files")(List.empty)
        _frontSide <- h.get[Option[PassportFile]]("front_side")
        _reverseSide <- h.get[Option[PassportFile]]("reverse_side")
        _selfie <- h.get[Option[PassportFile]]("selfie")
        _translation <- h.getOrElse[List[PassportFile]]("translation")(List.empty)
        _hash <- h.get[String]("hash")
      } yield {
        EncryptedPassportElement(
          `type` = _type,
          data = _data,
          phoneNumber = _phoneNumber,
          email = _email,
          files = _files,
          frontSide = _frontSide,
          reverseSide = _reverseSide,
          selfie = _selfie,
          translation = _translation,
          hash = _hash
        )
      }
    }

  implicit lazy val stickerEncoder: Encoder[Sticker] =
    (x: Sticker) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width" -> x.width.asJson,
          "height" -> x.height.asJson,
          "is_animated" -> x.isAnimated.asJson,
          "thumb" -> x.thumb.asJson,
          "emoji" -> x.emoji.asJson,
          "set_name" -> x.setName.asJson,
          "mask_position" -> x.maskPosition.asJson,
          "file_size" -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stickerDecoder: Decoder[Sticker] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _width <- h.get[Int]("width")
        _height <- h.get[Int]("height")
        _isAnimated <- h.get[Boolean]("is_animated")
        _thumb <- h.get[Option[PhotoSize]]("thumb")
        _emoji <- h.get[Option[Emoji]]("emoji")
        _setName <- h.get[Option[String]]("set_name")
        _maskPosition <- h.get[Option[MaskPosition]]("mask_position")
        _fileSize <- h.get[Option[Int]]("file_size")
      } yield {
        Sticker(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          width = _width,
          height = _height,
          isAnimated = _isAnimated,
          thumb = _thumb,
          emoji = _emoji,
          setName = _setName,
          maskPosition = _maskPosition,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val messageEncoder: Encoder[Message] =
    (x: Message) => {
      Json.fromFields(
        List(
          "message_id" -> x.messageId.asJson,
          "from" -> x.from.asJson,
          "sender_chat" -> x.senderChat.asJson,
          "date" -> x.date.asJson,
          "chat" -> x.chat.asJson,
          "forward_from" -> x.forwardFrom.asJson,
          "forward_from_chat" -> x.forwardFromChat.asJson,
          "forward_from_message_id" -> x.forwardFromMessageId.asJson,
          "forward_signature" -> x.forwardSignature.asJson,
          "forward_sender_name" -> x.forwardSenderName.asJson,
          "forward_date" -> x.forwardDate.asJson,
          "reply_to_message" -> x.replyToMessage.asJson,
          "via_bot" -> x.viaBot.asJson,
          "edit_date" -> x.editDate.asJson,
          "media_group_id" -> x.mediaGroupId.asJson,
          "author_signature" -> x.authorSignature.asJson,
          "text" -> x.text.asJson,
          "entities" -> x.entities.asJson,
          "animation" -> x.animation.asJson,
          "audio" -> x.audio.asJson,
          "document" -> x.document.asJson,
          "photo" -> x.photo.asJson,
          "sticker" -> x.sticker.asJson,
          "video" -> x.video.asJson,
          "video_note" -> x.videoNote.asJson,
          "voice" -> x.voice.asJson,
          "caption" -> x.caption.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "contact" -> x.contact.asJson,
          "dice" -> x.dice.asJson,
          "game" -> x.game.asJson,
          "poll" -> x.poll.asJson,
          "venue" -> x.venue.asJson,
          "location" -> x.location.asJson,
          "new_chat_members" -> x.newChatMembers.asJson,
          "left_chat_member" -> x.leftChatMember.asJson,
          "new_chat_title" -> x.newChatTitle.asJson,
          "new_chat_photo" -> x.newChatPhoto.asJson,
          "delete_chat_photo" -> x.deleteChatPhoto.asJson,
          "group_chat_created" -> x.groupChatCreated.asJson,
          "supergroup_chat_created" -> x.supergroupChatCreated.asJson,
          "channel_chat_created" -> x.channelChatCreated.asJson,
          "message_auto_delete_timer_changed" -> x.messageAutoDeleteTimerChanged.asJson,
          "migrate_to_chat_id" -> x.migrateToChatId.asJson,
          "migrate_from_chat_id" -> x.migrateFromChatId.asJson,
          "pinned_message" -> x.pinnedMessage.asJson,
          "invoice" -> x.invoice.asJson,
          "successful_payment" -> x.successfulPayment.asJson,
          "connected_website" -> x.connectedWebsite.asJson,
          "passport_data" -> x.passportData.asJson,
          "proximity_alert_triggered" -> x.proximityAlertTriggered.asJson,
          "voice_chat_scheduled" -> x.voiceChatScheduled.asJson,
          "voice_chat_started" -> x.voiceChatStarted.asJson,
          "voice_chat_ended" -> x.voiceChatEnded.asJson,
          "voice_chat_participants_invited" -> x.voiceChatParticipantsInvited.asJson,
          "reply_markup" -> x.replyMarkup.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageDecoder: Decoder[Message] =
    Decoder.instance { h =>
      for {
        _messageId <- h.get[Int]("message_id")
        _from <- h.get[Option[User]]("from")
        _senderChat <- h.get[Option[Chat]]("sender_chat")
        _date <- h.get[Int]("date")
        _chat <- h.get[Chat]("chat")
        _forwardFrom <- h.get[Option[User]]("forward_from")
        _forwardFromChat <- h.get[Option[Chat]]("forward_from_chat")
        _forwardFromMessageId <- h.get[Option[Int]]("forward_from_message_id")
        _forwardSignature <- h.get[Option[String]]("forward_signature")
        _forwardSenderName <- h.get[Option[String]]("forward_sender_name")
        _forwardDate <- h.get[Option[Int]]("forward_date")
        _replyToMessage <- h.get[Option[Message]]("reply_to_message")
        _viaBot <- h.get[Option[User]]("via_bot")
        _editDate <- h.get[Option[Int]]("edit_date")
        _mediaGroupId <- h.get[Option[String]]("media_group_id")
        _authorSignature <- h.get[Option[String]]("author_signature")
        _text <- h.get[Option[String]]("text")
        _entities <- h.getOrElse[List[MessageEntity]]("entities")(List.empty)
        _animation <- h.get[Option[Animation]]("animation")
        _audio <- h.get[Option[Audio]]("audio")
        _document <- h.get[Option[Document]]("document")
        _photo <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
        _sticker <- h.get[Option[Sticker]]("sticker")
        _video <- h.get[Option[Video]]("video")
        _videoNote <- h.get[Option[VideoNote]]("video_note")
        _voice <- h.get[Option[Voice]]("voice")
        _caption <- h.get[Option[String]]("caption")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _contact <- h.get[Option[Contact]]("contact")
        _dice <- h.get[Option[Dice]]("dice")
        _game <- h.get[Option[Game]]("game")
        _poll <- h.get[Option[Poll]]("poll")
        _venue <- h.get[Option[Venue]]("venue")
        _location <- h.get[Option[Location]]("location")
        _newChatMembers <- h.getOrElse[List[User]]("new_chat_members")(List.empty)
        _leftChatMember <- h.get[Option[User]]("left_chat_member")
        _newChatTitle <- h.get[Option[String]]("new_chat_title")
        _newChatPhoto <- h.getOrElse[List[PhotoSize]]("new_chat_photo")(List.empty)
        _deleteChatPhoto <- h.get[Option[Boolean]]("delete_chat_photo")
        _groupChatCreated <- h.get[Option[Boolean]]("group_chat_created")
        _supergroupChatCreated <- h.get[Option[Boolean]]("supergroup_chat_created")
        _channelChatCreated <- h.get[Option[Boolean]]("channel_chat_created")
        _messageAutoDeleteTimerChanged <- h.get[Option[MessageAutoDeleteTimerChanged]](
          "message_auto_delete_timer_changed"
        )
        _migrateToChatId <- h.get[Option[Long]]("migrate_to_chat_id")
        _migrateFromChatId <- h.get[Option[Long]]("migrate_from_chat_id")
        _pinnedMessage <- h.get[Option[Message]]("pinned_message")
        _invoice <- h.get[Option[Invoice]]("invoice")
        _successfulPayment <- h.get[Option[SuccessfulPayment]]("successful_payment")
        _connectedWebsite <- h.get[Option[String]]("connected_website")
        _passportData <- h.get[Option[PassportData]]("passport_data")
        _proximityAlertTriggered <- h.get[Option[ProximityAlertTriggered]]("proximity_alert_triggered")
        _voiceChatScheduled <- h.get[Option[VoiceChatScheduled]]("voice_chat_scheduled")
        _voiceChatStarted <- h.get[Option[VoiceChatStarted.type]]("voice_chat_started")
        _voiceChatEnded <- h.get[Option[VoiceChatEnded]]("voice_chat_ended")
        _voiceChatParticipantsInvited <- h.get[Option[VoiceChatParticipantsInvited]]("voice_chat_participants_invited")
        _replyMarkup <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        Message(
          messageId = _messageId,
          from = _from,
          senderChat = _senderChat,
          date = _date,
          chat = _chat,
          forwardFrom = _forwardFrom,
          forwardFromChat = _forwardFromChat,
          forwardFromMessageId = _forwardFromMessageId,
          forwardSignature = _forwardSignature,
          forwardSenderName = _forwardSenderName,
          forwardDate = _forwardDate,
          replyToMessage = _replyToMessage,
          viaBot = _viaBot,
          editDate = _editDate,
          mediaGroupId = _mediaGroupId,
          authorSignature = _authorSignature,
          text = _text,
          entities = _entities,
          animation = _animation,
          audio = _audio,
          document = _document,
          photo = _photo,
          sticker = _sticker,
          video = _video,
          videoNote = _videoNote,
          voice = _voice,
          caption = _caption,
          captionEntities = _captionEntities,
          contact = _contact,
          dice = _dice,
          game = _game,
          poll = _poll,
          venue = _venue,
          location = _location,
          newChatMembers = _newChatMembers,
          leftChatMember = _leftChatMember,
          newChatTitle = _newChatTitle,
          newChatPhoto = _newChatPhoto,
          deleteChatPhoto = _deleteChatPhoto,
          groupChatCreated = _groupChatCreated,
          supergroupChatCreated = _supergroupChatCreated,
          channelChatCreated = _channelChatCreated,
          messageAutoDeleteTimerChanged = _messageAutoDeleteTimerChanged,
          migrateToChatId = _migrateToChatId,
          migrateFromChatId = _migrateFromChatId,
          pinnedMessage = _pinnedMessage,
          invoice = _invoice,
          successfulPayment = _successfulPayment,
          connectedWebsite = _connectedWebsite,
          passportData = _passportData,
          proximityAlertTriggered = _proximityAlertTriggered,
          voiceChatScheduled = _voiceChatScheduled,
          voiceChatStarted = _voiceChatStarted,
          voiceChatEnded = _voiceChatEnded,
          voiceChatParticipantsInvited = _voiceChatParticipantsInvited,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val shippingoptionEncoder: Encoder[ShippingOption] =
    (x: ShippingOption) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "title" -> x.title.asJson,
          "prices" -> x.prices.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val shippingoptionDecoder: Decoder[ShippingOption] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _title <- h.get[String]("title")
        _prices <- h.getOrElse[List[LabeledPrice]]("prices")(List.empty)
      } yield {
        ShippingOption(id = _id, title = _title, prices = _prices)
      }
    }

  implicit lazy val precheckoutqueryEncoder: Encoder[PreCheckoutQuery] =
    (x: PreCheckoutQuery) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "from" -> x.from.asJson,
          "currency" -> x.currency.asJson,
          "total_amount" -> x.totalAmount.asJson,
          "invoice_payload" -> x.invoicePayload.asJson,
          "shipping_option_id" -> x.shippingOptionId.asJson,
          "order_info" -> x.orderInfo.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val precheckoutqueryDecoder: Decoder[PreCheckoutQuery] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _from <- h.get[User]("from")
        _currency <- h.get[String]("currency")
        _totalAmount <- h.get[Int]("total_amount")
        _invoicePayload <- h.get[String]("invoice_payload")
        _shippingOptionId <- h.get[Option[String]]("shipping_option_id")
        _orderInfo <- h.get[Option[OrderInfo]]("order_info")
      } yield {
        PreCheckoutQuery(id = _id,
                         from = _from,
                         currency = _currency,
                         totalAmount = _totalAmount,
                         invoicePayload = _invoicePayload,
                         shippingOptionId = _shippingOptionId,
                         orderInfo = _orderInfo
        )
      }
    }

  implicit lazy val voicechatstartedEncoder: Encoder[VoiceChatStarted.type] =
    (_: VoiceChatStarted.type) => ().asJson
  implicit lazy val voicechatstartedDecoder: Decoder[VoiceChatStarted.type] = (_: HCursor) => Right(VoiceChatStarted)
  implicit lazy val encryptedcredentialsEncoder: Encoder[EncryptedCredentials] =
    (x: EncryptedCredentials) => {
      Json.fromFields(
        List(
          "data" -> x.data.asJson,
          "hash" -> x.hash.asJson,
          "secret" -> x.secret.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val encryptedcredentialsDecoder: Decoder[EncryptedCredentials] =
    Decoder.instance { h =>
      for {
        _data <- h.get[String]("data")
        _hash <- h.get[String]("hash")
        _secret <- h.get[String]("secret")
      } yield {
        EncryptedCredentials(data = _data, hash = _hash, secret = _secret)
      }
    }

  implicit lazy val inlinekeyboardbuttonEncoder: Encoder[InlineKeyboardButton] =
    (x: InlineKeyboardButton) => {
      Json.fromFields(
        List(
          "text" -> x.text.asJson,
          "url" -> x.url.asJson,
          "login_url" -> x.loginUrl.asJson,
          "callback_data" -> x.callbackData.asJson,
          "switch_inline_query" -> x.switchInlineQuery.asJson,
          "switch_inline_query_current_chat" -> x.switchInlineQueryCurrentChat.asJson,
          "callback_game" -> x.callbackGame.asJson,
          "pay" -> x.pay.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinekeyboardbuttonDecoder: Decoder[InlineKeyboardButton] =
    Decoder.instance { h =>
      for {
        _text <- h.get[String]("text")
        _url <- h.get[Option[String]]("url")
        _loginUrl <- h.get[Option[LoginUrl]]("login_url")
        _callbackData <- h.get[Option[String]]("callback_data")
        _switchInlineQuery <- h.get[Option[String]]("switch_inline_query")
        _switchInlineQueryCurrentChat <- h.get[Option[String]]("switch_inline_query_current_chat")
        _callbackGame <- h.get[Option[String]]("callback_game")
        _pay <- h.get[Option[Boolean]]("pay")
      } yield {
        InlineKeyboardButton(
          text = _text,
          url = _url,
          loginUrl = _loginUrl,
          callbackData = _callbackData,
          switchInlineQuery = _switchInlineQuery,
          switchInlineQueryCurrentChat = _switchInlineQueryCurrentChat,
          callbackGame = _callbackGame,
          pay = _pay
        )
      }
    }

  implicit lazy val loginurlEncoder: Encoder[LoginUrl] =
    (x: LoginUrl) => {
      Json.fromFields(
        List(
          "url" -> x.url.asJson,
          "forward_text" -> x.forwardText.asJson,
          "bot_username" -> x.botUsername.asJson,
          "request_write_access" -> x.requestWriteAccess.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val loginurlDecoder: Decoder[LoginUrl] =
    Decoder.instance { h =>
      for {
        _url <- h.get[String]("url")
        _forwardText <- h.get[Option[String]]("forward_text")
        _botUsername <- h.get[Option[String]]("bot_username")
        _requestWriteAccess <- h.get[Option[Boolean]]("request_write_access")
      } yield {
        LoginUrl(url = _url,
                 forwardText = _forwardText,
                 botUsername = _botUsername,
                 requestWriteAccess = _requestWriteAccess
        )
      }
    }

  implicit lazy val voiceEncoder: Encoder[Voice] =
    (x: Voice) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "duration" -> x.duration.asJson,
          "mime_type" -> x.mimeType.asJson,
          "file_size" -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val voiceDecoder: Decoder[Voice] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _duration <- h.get[Int]("duration")
        _mimeType <- h.get[Option[String]]("mime_type")
        _fileSize <- h.get[Option[Int]]("file_size")
      } yield {
        Voice(fileId = _fileId,
              fileUniqueId = _fileUniqueId,
              duration = _duration,
              mimeType = _mimeType,
              fileSize = _fileSize
        )
      }
    }

  implicit lazy val voicechatparticipantsinvitedEncoder: Encoder[VoiceChatParticipantsInvited] =
    (x: VoiceChatParticipantsInvited) => {
      Json.fromFields(
        List(
          "users" -> x.users.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val voicechatparticipantsinvitedDecoder: Decoder[VoiceChatParticipantsInvited] =
    Decoder.instance { h =>
      for {
        _users <- h.getOrElse[List[User]]("users")(List.empty)
      } yield {
        VoiceChatParticipantsInvited(users = _users)
      }
    }

  implicit lazy val userprofilephotosEncoder: Encoder[UserProfilePhotos] =
    (x: UserProfilePhotos) => {
      Json.fromFields(
        List(
          "total_count" -> x.totalCount.asJson,
          "photos" -> x.photos.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val userprofilephotosDecoder: Decoder[UserProfilePhotos] =
    Decoder.instance { h =>
      for {
        _totalCount <- h.get[Int]("total_count")
        _photos <- h.getOrElse[List[List[PhotoSize]]]("photos")(List.empty)
      } yield {
        UserProfilePhotos(totalCount = _totalCount, photos = _photos)
      }
    }

  implicit lazy val callbackqueryEncoder: Encoder[CallbackQuery] =
    (x: CallbackQuery) => {
      Json.fromFields(
        List(
          "id" -> x.id.asJson,
          "from" -> x.from.asJson,
          "message" -> x.message.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "chat_instance" -> x.chatInstance.asJson,
          "data" -> x.data.asJson,
          "game_short_name" -> x.gameShortName.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val callbackqueryDecoder: Decoder[CallbackQuery] =
    Decoder.instance { h =>
      for {
        _id <- h.get[String]("id")
        _from <- h.get[User]("from")
        _message <- h.get[Option[Message]]("message")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
        _chatInstance <- h.get[String]("chat_instance")
        _data <- h.get[Option[String]]("data")
        _gameShortName <- h.get[Option[String]]("game_short_name")
      } yield {
        CallbackQuery(id = _id,
                      from = _from,
                      message = _message,
                      inlineMessageId = _inlineMessageId,
                      chatInstance = _chatInstance,
                      data = _data,
                      gameShortName = _gameShortName
        )
      }
    }

  implicit lazy val messageidEncoder: Encoder[MessageId] =
    (x: MessageId) => {
      Json.fromFields(
        List(
          "message_id" -> x.messageId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageidDecoder: Decoder[MessageId] =
    Decoder.instance { h =>
      for {
        _messageId <- h.get[Int]("message_id")
      } yield {
        MessageId(messageId = _messageId)
      }
    }

  implicit lazy val chatmemberEncoder: Encoder[ChatMember] =
    (x: ChatMember) => {
      Json.fromFields(
        List(
          "user" -> x.user.asJson,
          "status" -> x.status.asJson,
          "custom_title" -> x.customTitle.asJson,
          "is_anonymous" -> x.isAnonymous.asJson,
          "can_be_edited" -> x.canBeEdited.asJson,
          "can_manage_chat" -> x.canManageChat.asJson,
          "can_post_messages" -> x.canPostMessages.asJson,
          "can_edit_messages" -> x.canEditMessages.asJson,
          "can_delete_messages" -> x.canDeleteMessages.asJson,
          "can_manage_voice_chats" -> x.canManageVoiceChats.asJson,
          "can_restrict_members" -> x.canRestrictMembers.asJson,
          "can_promote_members" -> x.canPromoteMembers.asJson,
          "can_change_info" -> x.canChangeInfo.asJson,
          "can_invite_users" -> x.canInviteUsers.asJson,
          "can_pin_messages" -> x.canPinMessages.asJson,
          "is_member" -> x.isMember.asJson,
          "can_send_messages" -> x.canSendMessages.asJson,
          "can_send_media_messages" -> x.canSendMediaMessages.asJson,
          "can_send_polls" -> x.canSendPolls.asJson,
          "can_send_other_messages" -> x.canSendOtherMessages.asJson,
          "can_add_web_page_previews" -> x.canAddWebPagePreviews.asJson,
          "until_date" -> x.untilDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberDecoder: Decoder[ChatMember] =
    Decoder.instance { h =>
      for {
        _user <- h.get[User]("user")
        _status <- h.get[String]("status")
        _customTitle <- h.get[Option[String]]("custom_title")
        _isAnonymous <- h.get[Option[Boolean]]("is_anonymous")
        _canBeEdited <- h.get[Option[Boolean]]("can_be_edited")
        _canManageChat <- h.get[Option[Boolean]]("can_manage_chat")
        _canPostMessages <- h.get[Option[Boolean]]("can_post_messages")
        _canEditMessages <- h.get[Option[Boolean]]("can_edit_messages")
        _canDeleteMessages <- h.get[Option[Boolean]]("can_delete_messages")
        _canManageVoiceChats <- h.get[Option[Boolean]]("can_manage_voice_chats")
        _canRestrictMembers <- h.get[Option[Boolean]]("can_restrict_members")
        _canPromoteMembers <- h.get[Option[Boolean]]("can_promote_members")
        _canChangeInfo <- h.get[Option[Boolean]]("can_change_info")
        _canInviteUsers <- h.get[Option[Boolean]]("can_invite_users")
        _canPinMessages <- h.get[Option[Boolean]]("can_pin_messages")
        _isMember <- h.get[Option[Boolean]]("is_member")
        _canSendMessages <- h.get[Option[Boolean]]("can_send_messages")
        _canSendMediaMessages <- h.get[Option[Boolean]]("can_send_media_messages")
        _canSendPolls <- h.get[Option[Boolean]]("can_send_polls")
        _canSendOtherMessages <- h.get[Option[Boolean]]("can_send_other_messages")
        _canAddWebPagePreviews <- h.get[Option[Boolean]]("can_add_web_page_previews")
        _untilDate <- h.get[Option[Int]]("until_date")
      } yield {
        ChatMember(
          user = _user,
          status = _status,
          customTitle = _customTitle,
          isAnonymous = _isAnonymous,
          canBeEdited = _canBeEdited,
          canManageChat = _canManageChat,
          canPostMessages = _canPostMessages,
          canEditMessages = _canEditMessages,
          canDeleteMessages = _canDeleteMessages,
          canManageVoiceChats = _canManageVoiceChats,
          canRestrictMembers = _canRestrictMembers,
          canPromoteMembers = _canPromoteMembers,
          canChangeInfo = _canChangeInfo,
          canInviteUsers = _canInviteUsers,
          canPinMessages = _canPinMessages,
          isMember = _isMember,
          canSendMessages = _canSendMessages,
          canSendMediaMessages = _canSendMediaMessages,
          canSendPolls = _canSendPolls,
          canSendOtherMessages = _canSendOtherMessages,
          canAddWebPagePreviews = _canAddWebPagePreviews,
          untilDate = _untilDate
        )
      }
    }

  implicit lazy val videoEncoder: Encoder[Video] =
    (x: Video) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width" -> x.width.asJson,
          "height" -> x.height.asJson,
          "duration" -> x.duration.asJson,
          "thumb" -> x.thumb.asJson,
          "file_name" -> x.fileName.asJson,
          "mime_type" -> x.mimeType.asJson,
          "file_size" -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val videoDecoder: Decoder[Video] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _width <- h.get[Int]("width")
        _height <- h.get[Int]("height")
        _duration <- h.get[Int]("duration")
        _thumb <- h.get[Option[PhotoSize]]("thumb")
        _fileName <- h.get[Option[String]]("file_name")
        _mimeType <- h.get[Option[String]]("mime_type")
        _fileSize <- h.get[Option[Int]]("file_size")
      } yield {
        Video(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          width = _width,
          height = _height,
          duration = _duration,
          thumb = _thumb,
          fileName = _fileName,
          mimeType = _mimeType,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val documentEncoder: Encoder[Document] =
    (x: Document) => {
      Json.fromFields(
        List(
          "file_id" -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "thumb" -> x.thumb.asJson,
          "file_name" -> x.fileName.asJson,
          "mime_type" -> x.mimeType.asJson,
          "file_size" -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val documentDecoder: Decoder[Document] =
    Decoder.instance { h =>
      for {
        _fileId <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _thumb <- h.get[Option[PhotoSize]]("thumb")
        _fileName <- h.get[Option[String]]("file_name")
        _mimeType <- h.get[Option[String]]("mime_type")
        _fileSize <- h.get[Option[Int]]("file_size")
      } yield {
        Document(fileId = _fileId,
                 fileUniqueId = _fileUniqueId,
                 thumb = _thumb,
                 fileName = _fileName,
                 mimeType = _mimeType,
                 fileSize = _fileSize
        )
      }
    }

}
