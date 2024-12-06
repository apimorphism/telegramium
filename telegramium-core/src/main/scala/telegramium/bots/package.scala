package telegramium.bots

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Encoder, Decoder, Json}
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

  implicit lazy val emojidiceDecoder: Decoder[EmojiDice.type] =
    Decoder[String].emap(s => Either.cond(s == "🎲", EmojiDice, s"Expected 🎲"))

  implicit lazy val emojidartsEncoder: Encoder[EmojiDarts.type] = (_: EmojiDarts.type) => "🎯".asJson

  implicit lazy val emojidartsDecoder: Decoder[EmojiDarts.type] =
    Decoder[String].emap(s => Either.cond(s == "🎯", EmojiDarts, s"Expected 🎯"))

  implicit lazy val emojibasketballEncoder: Encoder[EmojiBasketball.type] = (_: EmojiBasketball.type) => "🏀".asJson

  implicit lazy val emojibasketballDecoder: Decoder[EmojiBasketball.type] =
    Decoder[String].emap(s => Either.cond(s == "🏀", EmojiBasketball, s"Expected 🏀"))

  implicit lazy val emojifootballEncoder: Encoder[EmojiFootball.type] = (_: EmojiFootball.type) => "⚽".asJson

  implicit lazy val emojifootballDecoder: Decoder[EmojiFootball.type] =
    Decoder[String].emap(s => Either.cond(s == "⚽", EmojiFootball, s"Expected ⚽"))

  implicit lazy val emojislotmachineEncoder: Encoder[EmojiSlotMachine.type] = (_: EmojiSlotMachine.type) => "🎰".asJson

  implicit lazy val emojislotmachineDecoder: Decoder[EmojiSlotMachine.type] =
    Decoder[String].emap(s => Either.cond(s == "🎰", EmojiSlotMachine, s"Expected 🎰"))

  implicit lazy val emojibowlingEncoder: Encoder[EmojiBowling.type] = (_: EmojiBowling.type) => "🎳".asJson

  implicit lazy val emojibowlingDecoder: Decoder[EmojiBowling.type] =
    Decoder[String].emap(s => Either.cond(s == "🎳", EmojiBowling, s"Expected 🎳"))

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

  implicit lazy val markdownDecoder: Decoder[Markdown.type] =
    Decoder[String].emap(s => Either.cond(s == "Markdown", Markdown, s"Expected Markdown"))

  implicit lazy val markdown2Encoder: Encoder[Markdown2.type] = (_: Markdown2.type) => "MarkdownV2".asJson

  implicit lazy val markdown2Decoder: Decoder[Markdown2.type] =
    Decoder[String].emap(s => Either.cond(s == "MarkdownV2", Markdown2, s"Expected MarkdownV2"))

  implicit lazy val htmlEncoder: Encoder[Html.type] = (_: Html.type) => "HTML".asJson

  implicit lazy val htmlDecoder: Decoder[Html.type] =
    Decoder[String].emap(s => Either.cond(s == "HTML", Html, s"Expected HTML"))

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
  implicit lazy val chatintidDecoder: Decoder[ChatIntId] = Decoder[Long].map(ChatIntId.apply)
  implicit lazy val chatstridEncoder: Encoder[ChatStrId] = (x: ChatStrId) => x.id.asJson
  implicit lazy val chatstridDecoder: Decoder[ChatStrId] = Decoder[String].map(ChatStrId.apply)

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
        _inlineKeyboard <- h.getOrElse[List[List[InlineKeyboardButton]]]("inline_keyboard")(List.empty)
      } yield {
        InlineKeyboardMarkup(inlineKeyboard = _inlineKeyboard)
      }
    }

  implicit lazy val forcereplyEncoder: Encoder[ForceReply] =
    (x: ForceReply) => {
      Json.fromFields(
        List(
          "force_reply"             -> x.forceReply.asJson,
          "input_field_placeholder" -> x.inputFieldPlaceholder.asJson,
          "selective"               -> x.selective.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val forcereplyDecoder: Decoder[ForceReply] =
    Decoder.instance { h =>
      for {
        _forceReply            <- h.get[Boolean]("force_reply")
        _inputFieldPlaceholder <- h.get[Option[String]]("input_field_placeholder")
        _selective             <- h.get[Option[Boolean]]("selective")
      } yield {
        ForceReply(forceReply = _forceReply, inputFieldPlaceholder = _inputFieldPlaceholder, selective = _selective)
      }
    }

  implicit lazy val replykeyboardremoveEncoder: Encoder[ReplyKeyboardRemove] =
    (x: ReplyKeyboardRemove) => {
      Json.fromFields(
        List(
          "remove_keyboard" -> x.removeKeyboard.asJson,
          "selective"       -> x.selective.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val replykeyboardremoveDecoder: Decoder[ReplyKeyboardRemove] =
    Decoder.instance { h =>
      for {
        _removeKeyboard <- h.get[Boolean]("remove_keyboard")
        _selective      <- h.get[Option[Boolean]]("selective")
      } yield {
        ReplyKeyboardRemove(removeKeyboard = _removeKeyboard, selective = _selective)
      }
    }

  implicit lazy val replykeyboardmarkupEncoder: Encoder[ReplyKeyboardMarkup] =
    (x: ReplyKeyboardMarkup) => {
      Json.fromFields(
        List(
          "keyboard"                -> x.keyboard.asJson,
          "is_persistent"           -> x.isPersistent.asJson,
          "resize_keyboard"         -> x.resizeKeyboard.asJson,
          "one_time_keyboard"       -> x.oneTimeKeyboard.asJson,
          "input_field_placeholder" -> x.inputFieldPlaceholder.asJson,
          "selective"               -> x.selective.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val replykeyboardmarkupDecoder: Decoder[ReplyKeyboardMarkup] =
    Decoder.instance { h =>
      for {
        _keyboard              <- h.getOrElse[List[List[KeyboardButton]]]("keyboard")(List.empty)
        _isPersistent          <- h.get[Option[Boolean]]("is_persistent")
        _resizeKeyboard        <- h.get[Option[Boolean]]("resize_keyboard")
        _oneTimeKeyboard       <- h.get[Option[Boolean]]("one_time_keyboard")
        _inputFieldPlaceholder <- h.get[Option[String]]("input_field_placeholder")
        _selective             <- h.get[Option[Boolean]]("selective")
      } yield {
        ReplyKeyboardMarkup(
          keyboard = _keyboard,
          isPersistent = _isPersistent,
          resizeKeyboard = _resizeKeyboard,
          oneTimeKeyboard = _oneTimeKeyboard,
          inputFieldPlaceholder = _inputFieldPlaceholder,
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
  implicit lazy val inputlinkfileDecoder: Decoder[InputLinkFile] = Decoder[String].map(InputLinkFile.apply)

  implicit lazy val backgroundfillEncoder: Encoder[BackgroundFill] = {
    case freeform_gradient: BackgroundFillFreeformGradient =>
      freeform_gradient.asJson.mapObject(_.add("type", Json.fromString("freeform_gradient")))
    case gradient: BackgroundFillGradient => gradient.asJson.mapObject(_.add("type", Json.fromString("gradient")))
    case solid: BackgroundFillSolid       => solid.asJson.mapObject(_.add("type", Json.fromString("solid")))
  }

  implicit lazy val backgroundfillDecoder: Decoder[iozhik.OpenEnum[BackgroundFill]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "freeform_gradient" => Decoder[BackgroundFillFreeformGradient].map(iozhik.OpenEnum.Known(_))
      case "gradient"          => Decoder[BackgroundFillGradient].map(iozhik.OpenEnum.Known(_))
      case "solid"             => Decoder[BackgroundFillSolid].map(iozhik.OpenEnum.Known(_))
      case unknown             => Decoder.const(iozhik.OpenEnum.Unknown[BackgroundFill](unknown))
    }
  } yield value

  implicit lazy val backgroundfillgradientEncoder: Encoder[BackgroundFillGradient] =
    (x: BackgroundFillGradient) => {
      Json.fromFields(
        List(
          "top_color"      -> x.topColor.asJson,
          "bottom_color"   -> x.bottomColor.asJson,
          "rotation_angle" -> x.rotationAngle.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val backgroundfillgradientDecoder: Decoder[BackgroundFillGradient] =
    Decoder.instance { h =>
      for {
        _topColor      <- h.get[Int]("top_color")
        _bottomColor   <- h.get[Int]("bottom_color")
        _rotationAngle <- h.get[Int]("rotation_angle")
      } yield {
        BackgroundFillGradient(topColor = _topColor, bottomColor = _bottomColor, rotationAngle = _rotationAngle)
      }
    }

  implicit lazy val backgroundfillfreeformgradientEncoder: Encoder[BackgroundFillFreeformGradient] =
    (x: BackgroundFillFreeformGradient) => {
      Json.fromFields(
        List(
          "colors" -> x.colors.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val backgroundfillfreeformgradientDecoder: Decoder[BackgroundFillFreeformGradient] =
    Decoder.instance { h =>
      for {
        _colors <- h.getOrElse[List[Int]]("colors")(List.empty)
      } yield {
        BackgroundFillFreeformGradient(colors = _colors)
      }
    }

  implicit lazy val backgroundfillsolidEncoder: Encoder[BackgroundFillSolid] =
    (x: BackgroundFillSolid) => {
      Json.fromFields(
        List(
          "color" -> x.color.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val backgroundfillsolidDecoder: Decoder[BackgroundFillSolid] =
    Decoder.instance { h =>
      for {
        _color <- h.get[Int]("color")
      } yield {
        BackgroundFillSolid(color = _color)
      }
    }

  implicit lazy val backgroundtypeEncoder: Encoder[BackgroundType] = {
    case chat_theme: BackgroundTypeChatTheme =>
      chat_theme.asJson.mapObject(_.add("type", Json.fromString("chat_theme")))
    case fill: BackgroundTypeFill           => fill.asJson.mapObject(_.add("type", Json.fromString("fill")))
    case wallpaper: BackgroundTypeWallpaper => wallpaper.asJson.mapObject(_.add("type", Json.fromString("wallpaper")))
    case pattern: BackgroundTypePattern     => pattern.asJson.mapObject(_.add("type", Json.fromString("pattern")))
  }

  implicit lazy val backgroundtypeDecoder: Decoder[iozhik.OpenEnum[BackgroundType]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "chat_theme" => Decoder[BackgroundTypeChatTheme].map(iozhik.OpenEnum.Known(_))
      case "fill"       => Decoder[BackgroundTypeFill].map(iozhik.OpenEnum.Known(_))
      case "wallpaper"  => Decoder[BackgroundTypeWallpaper].map(iozhik.OpenEnum.Known(_))
      case "pattern"    => Decoder[BackgroundTypePattern].map(iozhik.OpenEnum.Known(_))
      case unknown      => Decoder.const(iozhik.OpenEnum.Unknown[BackgroundType](unknown))
    }
  } yield value

  implicit lazy val backgroundtypewallpaperEncoder: Encoder[BackgroundTypeWallpaper] =
    (x: BackgroundTypeWallpaper) => {
      Json.fromFields(
        List(
          "document"           -> x.document.asJson,
          "dark_theme_dimming" -> x.darkThemeDimming.asJson,
          "is_blurred"         -> x.isBlurred.asJson,
          "is_moving"          -> x.isMoving.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val backgroundtypewallpaperDecoder: Decoder[BackgroundTypeWallpaper] =
    Decoder.instance { h =>
      for {
        _document         <- h.get[Document]("document")
        _darkThemeDimming <- h.get[Int]("dark_theme_dimming")
        _isBlurred        <- h.get[Option[Boolean]]("is_blurred")
        _isMoving         <- h.get[Option[Boolean]]("is_moving")
      } yield {
        BackgroundTypeWallpaper(
          document = _document,
          darkThemeDimming = _darkThemeDimming,
          isBlurred = _isBlurred,
          isMoving = _isMoving
        )
      }
    }

  implicit lazy val backgroundtypepatternEncoder: Encoder[BackgroundTypePattern] =
    (x: BackgroundTypePattern) => {
      Json.fromFields(
        List(
          "document"    -> x.document.asJson,
          "fill"        -> x.fill.asJson,
          "intensity"   -> x.intensity.asJson,
          "is_inverted" -> x.isInverted.asJson,
          "is_moving"   -> x.isMoving.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val backgroundtypepatternDecoder: Decoder[BackgroundTypePattern] =
    Decoder.instance { h =>
      for {
        _document   <- h.get[Document]("document")
        _fill       <- h.get[iozhik.OpenEnum[BackgroundFill]]("fill")
        _intensity  <- h.get[Int]("intensity")
        _isInverted <- h.get[Option[Boolean]]("is_inverted")
        _isMoving   <- h.get[Option[Boolean]]("is_moving")
      } yield {
        BackgroundTypePattern(
          document = _document,
          fill = _fill,
          intensity = _intensity,
          isInverted = _isInverted,
          isMoving = _isMoving
        )
      }
    }

  implicit lazy val backgroundtypechatthemeEncoder: Encoder[BackgroundTypeChatTheme] =
    (x: BackgroundTypeChatTheme) => {
      Json.fromFields(
        List(
          "theme_name" -> x.themeName.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val backgroundtypechatthemeDecoder: Decoder[BackgroundTypeChatTheme] =
    Decoder.instance { h =>
      for {
        _themeName <- h.get[String]("theme_name")
      } yield {
        BackgroundTypeChatTheme(themeName = _themeName)
      }
    }

  implicit lazy val backgroundtypefillEncoder: Encoder[BackgroundTypeFill] =
    (x: BackgroundTypeFill) => {
      Json.fromFields(
        List(
          "fill"               -> x.fill.asJson,
          "dark_theme_dimming" -> x.darkThemeDimming.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val backgroundtypefillDecoder: Decoder[BackgroundTypeFill] =
    Decoder.instance { h =>
      for {
        _fill             <- h.get[iozhik.OpenEnum[BackgroundFill]]("fill")
        _darkThemeDimming <- h.get[Int]("dark_theme_dimming")
      } yield {
        BackgroundTypeFill(fill = _fill, darkThemeDimming = _darkThemeDimming)
      }
    }

  implicit lazy val botcommandscopeEncoder: Encoder[BotCommandScope] = {
    case all_chat_administrators: BotCommandScopeAllChatAdministrators.type =>
      all_chat_administrators.asJson.mapObject(_.add("type", Json.fromString("all_chat_administrators")))
    case all_group_chats: BotCommandScopeAllGroupChats.type =>
      all_group_chats.asJson.mapObject(_.add("type", Json.fromString("all_group_chats")))
    case chat: BotCommandScopeChat => chat.asJson.mapObject(_.add("type", Json.fromString("chat")))
    case chat_administrators: BotCommandScopeChatAdministrators =>
      chat_administrators.asJson.mapObject(_.add("type", Json.fromString("chat_administrators")))
    case default: BotCommandScopeDefault.type => default.asJson.mapObject(_.add("type", Json.fromString("default")))
    case all_private_chats: BotCommandScopeAllPrivateChats.type =>
      all_private_chats.asJson.mapObject(_.add("type", Json.fromString("all_private_chats")))
    case chat_member: BotCommandScopeChatMember =>
      chat_member.asJson.mapObject(_.add("type", Json.fromString("chat_member")))
  }

  implicit lazy val botcommandscopeDecoder: Decoder[BotCommandScope] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "all_chat_administrators" => Decoder[BotCommandScopeAllChatAdministrators.type]
      case "all_group_chats"         => Decoder[BotCommandScopeAllGroupChats.type]
      case "chat"                    => Decoder[BotCommandScopeChat]
      case "chat_administrators"     => Decoder[BotCommandScopeChatAdministrators]
      case "default"                 => Decoder[BotCommandScopeDefault.type]
      case "all_private_chats"       => Decoder[BotCommandScopeAllPrivateChats.type]
      case "chat_member"             => Decoder[BotCommandScopeChatMember]
      case unknown                   => throw iozhik.DecodingError(s"Unknown type for BotCommandScope: $unknown")
    }
  } yield value

  implicit lazy val botcommandscopeallchatadministratorsEncoder: Encoder[BotCommandScopeAllChatAdministrators.type] =
    (_: BotCommandScopeAllChatAdministrators.type) => ().asJson

  implicit lazy val botcommandscopeallchatadministratorsDecoder: Decoder[BotCommandScopeAllChatAdministrators.type] =
    (_: HCursor) => Right(BotCommandScopeAllChatAdministrators)

  implicit lazy val botcommandscopeallgroupchatsEncoder: Encoder[BotCommandScopeAllGroupChats.type] =
    (_: BotCommandScopeAllGroupChats.type) => ().asJson

  implicit lazy val botcommandscopeallgroupchatsDecoder: Decoder[BotCommandScopeAllGroupChats.type] = (_: HCursor) =>
    Right(BotCommandScopeAllGroupChats)

  implicit lazy val botcommandscopedefaultEncoder: Encoder[BotCommandScopeDefault.type] =
    (_: BotCommandScopeDefault.type) => ().asJson

  implicit lazy val botcommandscopedefaultDecoder: Decoder[BotCommandScopeDefault.type] = (_: HCursor) =>
    Right(BotCommandScopeDefault)

  implicit lazy val botcommandscopechatadministratorsEncoder: Encoder[BotCommandScopeChatAdministrators] =
    (x: BotCommandScopeChatAdministrators) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botcommandscopechatadministratorsDecoder: Decoder[BotCommandScopeChatAdministrators] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        BotCommandScopeChatAdministrators(chatId = _chatId)
      }
    }

  implicit lazy val botcommandscopechatmemberEncoder: Encoder[BotCommandScopeChatMember] =
    (x: BotCommandScopeChatMember) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "user_id" -> x.userId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botcommandscopechatmemberDecoder: Decoder[BotCommandScopeChatMember] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
        _userId <- h.get[Long]("user_id")
      } yield {
        BotCommandScopeChatMember(chatId = _chatId, userId = _userId)
      }
    }

  implicit lazy val botcommandscopechatEncoder: Encoder[BotCommandScopeChat] =
    (x: BotCommandScopeChat) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botcommandscopechatDecoder: Decoder[BotCommandScopeChat] =
    Decoder.instance { h =>
      for {
        _chatId <- h.get[ChatId]("chat_id")
      } yield {
        BotCommandScopeChat(chatId = _chatId)
      }
    }

  implicit lazy val botcommandscopeallprivatechatsEncoder: Encoder[BotCommandScopeAllPrivateChats.type] =
    (_: BotCommandScopeAllPrivateChats.type) => ().asJson

  implicit lazy val botcommandscopeallprivatechatsDecoder: Decoder[BotCommandScopeAllPrivateChats.type] =
    (_: HCursor) => Right(BotCommandScopeAllPrivateChats)

  implicit lazy val chatboostsourceEncoder: Encoder[ChatBoostSource] = {
    case giveaway: ChatBoostSourceGiveaway  => giveaway.asJson.mapObject(_.add("source", Json.fromString("giveaway")))
    case premium: ChatBoostSourcePremium    => premium.asJson.mapObject(_.add("source", Json.fromString("premium")))
    case gift_code: ChatBoostSourceGiftCode => gift_code.asJson.mapObject(_.add("source", Json.fromString("gift_code")))
  }

  implicit lazy val chatboostsourceDecoder: Decoder[iozhik.OpenEnum[ChatBoostSource]] = for {
    fType <- Decoder[String].prepare(_.downField("source"))
    value <- fType match {
      case "giveaway"  => Decoder[ChatBoostSourceGiveaway].map(iozhik.OpenEnum.Known(_))
      case "premium"   => Decoder[ChatBoostSourcePremium].map(iozhik.OpenEnum.Known(_))
      case "gift_code" => Decoder[ChatBoostSourceGiftCode].map(iozhik.OpenEnum.Known(_))
      case unknown     => Decoder.const(iozhik.OpenEnum.Unknown[ChatBoostSource](unknown))
    }
  } yield value

  implicit lazy val chatboostsourcegiftcodeEncoder: Encoder[ChatBoostSourceGiftCode] =
    (x: ChatBoostSourceGiftCode) => {
      Json.fromFields(
        List(
          "user" -> x.user.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatboostsourcegiftcodeDecoder: Decoder[ChatBoostSourceGiftCode] =
    Decoder.instance { h =>
      for {
        _user <- h.get[User]("user")
      } yield {
        ChatBoostSourceGiftCode(user = _user)
      }
    }

  implicit lazy val chatboostsourcegiveawayEncoder: Encoder[ChatBoostSourceGiveaway] =
    (x: ChatBoostSourceGiveaway) => {
      Json.fromFields(
        List(
          "giveaway_message_id" -> x.giveawayMessageId.asJson,
          "user"                -> x.user.asJson,
          "prize_star_count"    -> x.prizeStarCount.asJson,
          "is_unclaimed"        -> x.isUnclaimed.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatboostsourcegiveawayDecoder: Decoder[ChatBoostSourceGiveaway] =
    Decoder.instance { h =>
      for {
        _giveawayMessageId <- h.get[Int]("giveaway_message_id")
        _user              <- h.get[Option[User]]("user")
        _prizeStarCount    <- h.get[Option[Int]]("prize_star_count")
        _isUnclaimed       <- h.get[Option[Boolean]]("is_unclaimed")
      } yield {
        ChatBoostSourceGiveaway(
          giveawayMessageId = _giveawayMessageId,
          user = _user,
          prizeStarCount = _prizeStarCount,
          isUnclaimed = _isUnclaimed
        )
      }
    }

  implicit lazy val chatboostsourcepremiumEncoder: Encoder[ChatBoostSourcePremium] =
    (x: ChatBoostSourcePremium) => {
      Json.fromFields(
        List(
          "user" -> x.user.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatboostsourcepremiumDecoder: Decoder[ChatBoostSourcePremium] =
    Decoder.instance { h =>
      for {
        _user <- h.get[User]("user")
      } yield {
        ChatBoostSourcePremium(user = _user)
      }
    }

  implicit lazy val chatmemberEncoder: Encoder[ChatMember] = {
    case kicked: ChatMemberBanned => kicked.asJson.mapObject(_.add("status", Json.fromString("kicked")))
    case left: ChatMemberLeft     => left.asJson.mapObject(_.add("status", Json.fromString("left")))
    case administrator: ChatMemberAdministrator =>
      administrator.asJson.mapObject(_.add("status", Json.fromString("administrator")))
    case creator: ChatMemberOwner         => creator.asJson.mapObject(_.add("status", Json.fromString("creator")))
    case member: ChatMemberMember         => member.asJson.mapObject(_.add("status", Json.fromString("member")))
    case restricted: ChatMemberRestricted => restricted.asJson.mapObject(_.add("status", Json.fromString("restricted")))
  }

  implicit lazy val chatmemberDecoder: Decoder[iozhik.OpenEnum[ChatMember]] = for {
    fType <- Decoder[String].prepare(_.downField("status"))
    value <- fType match {
      case "kicked"        => Decoder[ChatMemberBanned].map(iozhik.OpenEnum.Known(_))
      case "left"          => Decoder[ChatMemberLeft].map(iozhik.OpenEnum.Known(_))
      case "administrator" => Decoder[ChatMemberAdministrator].map(iozhik.OpenEnum.Known(_))
      case "creator"       => Decoder[ChatMemberOwner].map(iozhik.OpenEnum.Known(_))
      case "member"        => Decoder[ChatMemberMember].map(iozhik.OpenEnum.Known(_))
      case "restricted"    => Decoder[ChatMemberRestricted].map(iozhik.OpenEnum.Known(_))
      case unknown         => Decoder.const(iozhik.OpenEnum.Unknown[ChatMember](unknown))
    }
  } yield value

  implicit lazy val chatmemberownerEncoder: Encoder[ChatMemberOwner] =
    (x: ChatMemberOwner) => {
      Json.fromFields(
        List(
          "user"         -> x.user.asJson,
          "is_anonymous" -> x.isAnonymous.asJson,
          "custom_title" -> x.customTitle.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberownerDecoder: Decoder[ChatMemberOwner] =
    Decoder.instance { h =>
      for {
        _user        <- h.get[User]("user")
        _isAnonymous <- h.get[Boolean]("is_anonymous")
        _customTitle <- h.get[Option[String]]("custom_title")
      } yield {
        ChatMemberOwner(user = _user, isAnonymous = _isAnonymous, customTitle = _customTitle)
      }
    }

  implicit lazy val chatmemberadministratorEncoder: Encoder[ChatMemberAdministrator] =
    (x: ChatMemberAdministrator) => {
      Json.fromFields(
        List(
          "user"                   -> x.user.asJson,
          "can_be_edited"          -> x.canBeEdited.asJson,
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
          "custom_title"           -> x.customTitle.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberadministratorDecoder: Decoder[ChatMemberAdministrator] =
    Decoder.instance { h =>
      for {
        _user                <- h.get[User]("user")
        _canBeEdited         <- h.get[Boolean]("can_be_edited")
        _isAnonymous         <- h.get[Boolean]("is_anonymous")
        _canManageChat       <- h.get[Boolean]("can_manage_chat")
        _canDeleteMessages   <- h.get[Boolean]("can_delete_messages")
        _canManageVideoChats <- h.get[Boolean]("can_manage_video_chats")
        _canRestrictMembers  <- h.get[Boolean]("can_restrict_members")
        _canPromoteMembers   <- h.get[Boolean]("can_promote_members")
        _canChangeInfo       <- h.get[Boolean]("can_change_info")
        _canInviteUsers      <- h.get[Boolean]("can_invite_users")
        _canPostStories      <- h.get[Boolean]("can_post_stories")
        _canEditStories      <- h.get[Boolean]("can_edit_stories")
        _canDeleteStories    <- h.get[Boolean]("can_delete_stories")
        _canPostMessages     <- h.get[Option[Boolean]]("can_post_messages")
        _canEditMessages     <- h.get[Option[Boolean]]("can_edit_messages")
        _canPinMessages      <- h.get[Option[Boolean]]("can_pin_messages")
        _canManageTopics     <- h.get[Option[Boolean]]("can_manage_topics")
        _customTitle         <- h.get[Option[String]]("custom_title")
      } yield {
        ChatMemberAdministrator(
          user = _user,
          canBeEdited = _canBeEdited,
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
          canManageTopics = _canManageTopics,
          customTitle = _customTitle
        )
      }
    }

  implicit lazy val chatmemberleftEncoder: Encoder[ChatMemberLeft] =
    (x: ChatMemberLeft) => {
      Json.fromFields(
        List(
          "user" -> x.user.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberleftDecoder: Decoder[ChatMemberLeft] =
    Decoder.instance { h =>
      for {
        _user <- h.get[User]("user")
      } yield {
        ChatMemberLeft(user = _user)
      }
    }

  implicit lazy val chatmembermemberEncoder: Encoder[ChatMemberMember] =
    (x: ChatMemberMember) => {
      Json.fromFields(
        List(
          "user"       -> x.user.asJson,
          "until_date" -> x.untilDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmembermemberDecoder: Decoder[ChatMemberMember] =
    Decoder.instance { h =>
      for {
        _user      <- h.get[User]("user")
        _untilDate <- h.get[Option[Int]]("until_date")
      } yield {
        ChatMemberMember(user = _user, untilDate = _untilDate)
      }
    }

  implicit lazy val chatmemberbannedEncoder: Encoder[ChatMemberBanned] =
    (x: ChatMemberBanned) => {
      Json.fromFields(
        List(
          "user"       -> x.user.asJson,
          "until_date" -> x.untilDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberbannedDecoder: Decoder[ChatMemberBanned] =
    Decoder.instance { h =>
      for {
        _user      <- h.get[User]("user")
        _untilDate <- h.get[Int]("until_date")
      } yield {
        ChatMemberBanned(user = _user, untilDate = _untilDate)
      }
    }

  implicit lazy val chatmemberrestrictedEncoder: Encoder[ChatMemberRestricted] =
    (x: ChatMemberRestricted) => {
      Json.fromFields(
        List(
          "user"                      -> x.user.asJson,
          "is_member"                 -> x.isMember.asJson,
          "can_send_messages"         -> x.canSendMessages.asJson,
          "can_send_audios"           -> x.canSendAudios.asJson,
          "can_send_documents"        -> x.canSendDocuments.asJson,
          "can_send_photos"           -> x.canSendPhotos.asJson,
          "can_send_videos"           -> x.canSendVideos.asJson,
          "can_send_video_notes"      -> x.canSendVideoNotes.asJson,
          "can_send_voice_notes"      -> x.canSendVoiceNotes.asJson,
          "can_send_polls"            -> x.canSendPolls.asJson,
          "can_send_other_messages"   -> x.canSendOtherMessages.asJson,
          "can_add_web_page_previews" -> x.canAddWebPagePreviews.asJson,
          "can_change_info"           -> x.canChangeInfo.asJson,
          "can_invite_users"          -> x.canInviteUsers.asJson,
          "can_pin_messages"          -> x.canPinMessages.asJson,
          "can_manage_topics"         -> x.canManageTopics.asJson,
          "until_date"                -> x.untilDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberrestrictedDecoder: Decoder[ChatMemberRestricted] =
    Decoder.instance { h =>
      for {
        _user                  <- h.get[User]("user")
        _isMember              <- h.get[Boolean]("is_member")
        _canSendMessages       <- h.get[Boolean]("can_send_messages")
        _canSendAudios         <- h.get[Boolean]("can_send_audios")
        _canSendDocuments      <- h.get[Boolean]("can_send_documents")
        _canSendPhotos         <- h.get[Boolean]("can_send_photos")
        _canSendVideos         <- h.get[Boolean]("can_send_videos")
        _canSendVideoNotes     <- h.get[Boolean]("can_send_video_notes")
        _canSendVoiceNotes     <- h.get[Boolean]("can_send_voice_notes")
        _canSendPolls          <- h.get[Boolean]("can_send_polls")
        _canSendOtherMessages  <- h.get[Boolean]("can_send_other_messages")
        _canAddWebPagePreviews <- h.get[Boolean]("can_add_web_page_previews")
        _canChangeInfo         <- h.get[Boolean]("can_change_info")
        _canInviteUsers        <- h.get[Boolean]("can_invite_users")
        _canPinMessages        <- h.get[Boolean]("can_pin_messages")
        _canManageTopics       <- h.get[Boolean]("can_manage_topics")
        _untilDate             <- h.get[Int]("until_date")
      } yield {
        ChatMemberRestricted(
          user = _user,
          isMember = _isMember,
          canSendMessages = _canSendMessages,
          canSendAudios = _canSendAudios,
          canSendDocuments = _canSendDocuments,
          canSendPhotos = _canSendPhotos,
          canSendVideos = _canSendVideos,
          canSendVideoNotes = _canSendVideoNotes,
          canSendVoiceNotes = _canSendVoiceNotes,
          canSendPolls = _canSendPolls,
          canSendOtherMessages = _canSendOtherMessages,
          canAddWebPagePreviews = _canAddWebPagePreviews,
          canChangeInfo = _canChangeInfo,
          canInviteUsers = _canInviteUsers,
          canPinMessages = _canPinMessages,
          canManageTopics = _canManageTopics,
          untilDate = _untilDate
        )
      }
    }

  implicit lazy val inlinequeryresultEncoder: Encoder[InlineQueryResult] = {
    case mpeg4_gif: InlineQueryResultMpeg4Gif => mpeg4_gif.asJson.mapObject(_.add("type", Json.fromString("mpeg4_gif")))
    case mpeg4_gif: InlineQueryResultCachedMpeg4Gif =>
      mpeg4_gif.asJson.mapObject(_.add("type", Json.fromString("mpeg4_gif")))
    case location: InlineQueryResultLocation => location.asJson.mapObject(_.add("type", Json.fromString("location")))
    case photo: InlineQueryResultPhoto       => photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case photo: InlineQueryResultCachedPhoto => photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case document: InlineQueryResultDocument => document.asJson.mapObject(_.add("type", Json.fromString("document")))
    case document: InlineQueryResultCachedDocument =>
      document.asJson.mapObject(_.add("type", Json.fromString("document")))
    case audio: InlineQueryResultAudio           => audio.asJson.mapObject(_.add("type", Json.fromString("audio")))
    case audio: InlineQueryResultCachedAudio     => audio.asJson.mapObject(_.add("type", Json.fromString("audio")))
    case voice: InlineQueryResultCachedVoice     => voice.asJson.mapObject(_.add("type", Json.fromString("voice")))
    case voice: InlineQueryResultVoice           => voice.asJson.mapObject(_.add("type", Json.fromString("voice")))
    case article: InlineQueryResultArticle       => article.asJson.mapObject(_.add("type", Json.fromString("article")))
    case contact: InlineQueryResultContact       => contact.asJson.mapObject(_.add("type", Json.fromString("contact")))
    case video: InlineQueryResultCachedVideo     => video.asJson.mapObject(_.add("type", Json.fromString("video")))
    case video: InlineQueryResultVideo           => video.asJson.mapObject(_.add("type", Json.fromString("video")))
    case gif: InlineQueryResultGif               => gif.asJson.mapObject(_.add("type", Json.fromString("gif")))
    case gif: InlineQueryResultCachedGif         => gif.asJson.mapObject(_.add("type", Json.fromString("gif")))
    case sticker: InlineQueryResultCachedSticker => sticker.asJson.mapObject(_.add("type", Json.fromString("sticker")))
    case game: InlineQueryResultGame             => game.asJson.mapObject(_.add("type", Json.fromString("game")))
    case venue: InlineQueryResultVenue           => venue.asJson.mapObject(_.add("type", Json.fromString("venue")))
  }

  implicit lazy val inlinequeryresultDecoder: Decoder[InlineQueryResult] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "mpeg4_gif" =>
        List[Decoder[InlineQueryResult]](
          Decoder[InlineQueryResultMpeg4Gif].widen,
          Decoder[InlineQueryResultCachedMpeg4Gif].widen
        ).reduceLeft(_ or _)
      case "location" => Decoder[InlineQueryResultLocation]
      case "photo" =>
        List[Decoder[InlineQueryResult]](
          Decoder[InlineQueryResultPhoto].widen,
          Decoder[InlineQueryResultCachedPhoto].widen
        ).reduceLeft(_ or _)
      case "document" =>
        List[Decoder[InlineQueryResult]](
          Decoder[InlineQueryResultDocument].widen,
          Decoder[InlineQueryResultCachedDocument].widen
        ).reduceLeft(_ or _)
      case "audio" =>
        List[Decoder[InlineQueryResult]](
          Decoder[InlineQueryResultAudio].widen,
          Decoder[InlineQueryResultCachedAudio].widen
        ).reduceLeft(_ or _)
      case "voice" =>
        List[Decoder[InlineQueryResult]](
          Decoder[InlineQueryResultCachedVoice].widen,
          Decoder[InlineQueryResultVoice].widen
        ).reduceLeft(_ or _)
      case "article" => Decoder[InlineQueryResultArticle]
      case "contact" => Decoder[InlineQueryResultContact]
      case "video" =>
        List[Decoder[InlineQueryResult]](
          Decoder[InlineQueryResultCachedVideo].widen,
          Decoder[InlineQueryResultVideo].widen
        ).reduceLeft(_ or _)
      case "gif" =>
        List[Decoder[InlineQueryResult]](Decoder[InlineQueryResultGif].widen, Decoder[InlineQueryResultCachedGif].widen)
          .reduceLeft(_ or _)
      case "sticker" => Decoder[InlineQueryResultCachedSticker]
      case "game"    => Decoder[InlineQueryResultGame]
      case "venue"   => Decoder[InlineQueryResultVenue]
      case unknown   => throw iozhik.DecodingError(s"Unknown type for InlineQueryResult: $unknown")
    }
  } yield value

  implicit lazy val inlinequeryresultgifEncoder: Encoder[InlineQueryResultGif] =
    (x: InlineQueryResultGif) => {
      Json.fromFields(
        List(
          "id"                       -> x.id.asJson,
          "gif_url"                  -> x.gifUrl.asJson,
          "gif_width"                -> x.gifWidth.asJson,
          "gif_height"               -> x.gifHeight.asJson,
          "gif_duration"             -> x.gifDuration.asJson,
          "thumbnail_url"            -> x.thumbnailUrl.asJson,
          "thumbnail_mime_type"      -> x.thumbnailMimeType.asJson,
          "title"                    -> x.title.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "input_message_content"    -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultgifDecoder: Decoder[InlineQueryResultGif] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _gifUrl                <- h.get[String]("gif_url")
        _gifWidth              <- h.get[Option[Int]]("gif_width")
        _gifHeight             <- h.get[Option[Int]]("gif_height")
        _gifDuration           <- h.get[Option[Int]]("gif_duration")
        _thumbnailUrl          <- h.get[String]("thumbnail_url")
        _thumbnailMimeType     <- h.get[Option[String]]("thumbnail_mime_type")
        _title                 <- h.get[Option[String]]("title")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent   <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultGif(
          id = _id,
          gifUrl = _gifUrl,
          gifWidth = _gifWidth,
          gifHeight = _gifHeight,
          gifDuration = _gifDuration,
          thumbnailUrl = _thumbnailUrl,
          thumbnailMimeType = _thumbnailMimeType,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultvenueEncoder: Encoder[InlineQueryResultVenue] =
    (x: InlineQueryResultVenue) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "latitude"              -> x.latitude.asJson,
          "longitude"             -> x.longitude.asJson,
          "title"                 -> x.title.asJson,
          "address"               -> x.address.asJson,
          "foursquare_id"         -> x.foursquareId.asJson,
          "foursquare_type"       -> x.foursquareType.asJson,
          "google_place_id"       -> x.googlePlaceId.asJson,
          "google_place_type"     -> x.googlePlaceType.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "thumbnail_url"         -> x.thumbnailUrl.asJson,
          "thumbnail_width"       -> x.thumbnailWidth.asJson,
          "thumbnail_height"      -> x.thumbnailHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultvenueDecoder: Decoder[InlineQueryResultVenue] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _latitude            <- h.get[Float]("latitude")
        _longitude           <- h.get[Float]("longitude")
        _title               <- h.get[String]("title")
        _address             <- h.get[String]("address")
        _foursquareId        <- h.get[Option[String]]("foursquare_id")
        _foursquareType      <- h.get[Option[String]]("foursquare_type")
        _googlePlaceId       <- h.get[Option[String]]("google_place_id")
        _googlePlaceType     <- h.get[Option[String]]("google_place_type")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbnailUrl        <- h.get[Option[String]]("thumbnail_url")
        _thumbnailWidth      <- h.get[Option[Int]]("thumbnail_width")
        _thumbnailHeight     <- h.get[Option[Int]]("thumbnail_height")
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
          thumbnailUrl = _thumbnailUrl,
          thumbnailWidth = _thumbnailWidth,
          thumbnailHeight = _thumbnailHeight
        )
      }
    }

  implicit lazy val inlinequeryresultcontactEncoder: Encoder[InlineQueryResultContact] =
    (x: InlineQueryResultContact) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "phone_number"          -> x.phoneNumber.asJson,
          "first_name"            -> x.firstName.asJson,
          "last_name"             -> x.lastName.asJson,
          "vcard"                 -> x.vcard.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "thumbnail_url"         -> x.thumbnailUrl.asJson,
          "thumbnail_width"       -> x.thumbnailWidth.asJson,
          "thumbnail_height"      -> x.thumbnailHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcontactDecoder: Decoder[InlineQueryResultContact] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _phoneNumber         <- h.get[String]("phone_number")
        _firstName           <- h.get[String]("first_name")
        _lastName            <- h.get[Option[String]]("last_name")
        _vcard               <- h.get[Option[String]]("vcard")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbnailUrl        <- h.get[Option[String]]("thumbnail_url")
        _thumbnailWidth      <- h.get[Option[Int]]("thumbnail_width")
        _thumbnailHeight     <- h.get[Option[Int]]("thumbnail_height")
      } yield {
        InlineQueryResultContact(
          id = _id,
          phoneNumber = _phoneNumber,
          firstName = _firstName,
          lastName = _lastName,
          vcard = _vcard,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent,
          thumbnailUrl = _thumbnailUrl,
          thumbnailWidth = _thumbnailWidth,
          thumbnailHeight = _thumbnailHeight
        )
      }
    }

  implicit lazy val inlinequeryresultphotoEncoder: Encoder[InlineQueryResultPhoto] =
    (x: InlineQueryResultPhoto) => {
      Json.fromFields(
        List(
          "id"                       -> x.id.asJson,
          "photo_url"                -> x.photoUrl.asJson,
          "thumbnail_url"            -> x.thumbnailUrl.asJson,
          "photo_width"              -> x.photoWidth.asJson,
          "photo_height"             -> x.photoHeight.asJson,
          "title"                    -> x.title.asJson,
          "description"              -> x.description.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "input_message_content"    -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultphotoDecoder: Decoder[InlineQueryResultPhoto] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _photoUrl              <- h.get[String]("photo_url")
        _thumbnailUrl          <- h.get[String]("thumbnail_url")
        _photoWidth            <- h.get[Option[Int]]("photo_width")
        _photoHeight           <- h.get[Option[Int]]("photo_height")
        _title                 <- h.get[Option[String]]("title")
        _description           <- h.get[Option[String]]("description")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent   <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultPhoto(
          id = _id,
          photoUrl = _photoUrl,
          thumbnailUrl = _thumbnailUrl,
          photoWidth = _photoWidth,
          photoHeight = _photoHeight,
          title = _title,
          description = _description,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultdocumentEncoder: Encoder[InlineQueryResultDocument] =
    (x: InlineQueryResultDocument) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "document_url"          -> x.documentUrl.asJson,
          "mime_type"             -> x.mimeType.asJson,
          "description"           -> x.description.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "thumbnail_url"         -> x.thumbnailUrl.asJson,
          "thumbnail_width"       -> x.thumbnailWidth.asJson,
          "thumbnail_height"      -> x.thumbnailHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultdocumentDecoder: Decoder[InlineQueryResultDocument] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _title               <- h.get[String]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _documentUrl         <- h.get[String]("document_url")
        _mimeType            <- h.get[String]("mime_type")
        _description         <- h.get[Option[String]]("description")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbnailUrl        <- h.get[Option[String]]("thumbnail_url")
        _thumbnailWidth      <- h.get[Option[Int]]("thumbnail_width")
        _thumbnailHeight     <- h.get[Option[Int]]("thumbnail_height")
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
          thumbnailUrl = _thumbnailUrl,
          thumbnailWidth = _thumbnailWidth,
          thumbnailHeight = _thumbnailHeight
        )
      }
    }

  implicit lazy val inlinequeryresultcachedvoiceEncoder: Encoder[InlineQueryResultCachedVoice] =
    (x: InlineQueryResultCachedVoice) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "voice_file_id"         -> x.voiceFileId.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedvoiceDecoder: Decoder[InlineQueryResultCachedVoice] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _voiceFileId         <- h.get[String]("voice_file_id")
        _title               <- h.get[String]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "id"                    -> x.id.asJson,
          "title"                 -> x.title.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "url"                   -> x.url.asJson,
          "hide_url"              -> x.hideUrl.asJson,
          "description"           -> x.description.asJson,
          "thumbnail_url"         -> x.thumbnailUrl.asJson,
          "thumbnail_width"       -> x.thumbnailWidth.asJson,
          "thumbnail_height"      -> x.thumbnailHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultarticleDecoder: Decoder[InlineQueryResultArticle] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _title               <- h.get[String]("title")
        _inputMessageContent <- h.get[InputMessageContent]("input_message_content")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _url                 <- h.get[Option[String]]("url")
        _hideUrl             <- h.get[Option[Boolean]]("hide_url")
        _description         <- h.get[Option[String]]("description")
        _thumbnailUrl        <- h.get[Option[String]]("thumbnail_url")
        _thumbnailWidth      <- h.get[Option[Int]]("thumbnail_width")
        _thumbnailHeight     <- h.get[Option[Int]]("thumbnail_height")
      } yield {
        InlineQueryResultArticle(
          id = _id,
          title = _title,
          inputMessageContent = _inputMessageContent,
          replyMarkup = _replyMarkup,
          url = _url,
          hideUrl = _hideUrl,
          description = _description,
          thumbnailUrl = _thumbnailUrl,
          thumbnailWidth = _thumbnailWidth,
          thumbnailHeight = _thumbnailHeight
        )
      }
    }

  implicit lazy val inlinequeryresultaudioEncoder: Encoder[InlineQueryResultAudio] =
    (x: InlineQueryResultAudio) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "audio_url"             -> x.audioUrl.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "performer"             -> x.performer.asJson,
          "audio_duration"        -> x.audioDuration.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultaudioDecoder: Decoder[InlineQueryResultAudio] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _audioUrl            <- h.get[String]("audio_url")
        _title               <- h.get[String]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _performer           <- h.get[Option[String]]("performer")
        _audioDuration       <- h.get[Option[Int]]("audio_duration")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "id"                       -> x.id.asJson,
          "mpeg4_url"                -> x.mpeg4Url.asJson,
          "mpeg4_width"              -> x.mpeg4Width.asJson,
          "mpeg4_height"             -> x.mpeg4Height.asJson,
          "mpeg4_duration"           -> x.mpeg4Duration.asJson,
          "thumbnail_url"            -> x.thumbnailUrl.asJson,
          "thumbnail_mime_type"      -> x.thumbnailMimeType.asJson,
          "title"                    -> x.title.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "input_message_content"    -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultmpeg4gifDecoder: Decoder[InlineQueryResultMpeg4Gif] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _mpeg4Url              <- h.get[String]("mpeg4_url")
        _mpeg4Width            <- h.get[Option[Int]]("mpeg4_width")
        _mpeg4Height           <- h.get[Option[Int]]("mpeg4_height")
        _mpeg4Duration         <- h.get[Option[Int]]("mpeg4_duration")
        _thumbnailUrl          <- h.get[String]("thumbnail_url")
        _thumbnailMimeType     <- h.get[Option[String]]("thumbnail_mime_type")
        _title                 <- h.get[Option[String]]("title")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent   <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultMpeg4Gif(
          id = _id,
          mpeg4Url = _mpeg4Url,
          mpeg4Width = _mpeg4Width,
          mpeg4Height = _mpeg4Height,
          mpeg4Duration = _mpeg4Duration,
          thumbnailUrl = _thumbnailUrl,
          thumbnailMimeType = _thumbnailMimeType,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultcachedmpeg4gifEncoder: Encoder[InlineQueryResultCachedMpeg4Gif] =
    (x: InlineQueryResultCachedMpeg4Gif) => {
      Json.fromFields(
        List(
          "id"                       -> x.id.asJson,
          "mpeg4_file_id"            -> x.mpeg4FileId.asJson,
          "title"                    -> x.title.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "input_message_content"    -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedmpeg4gifDecoder: Decoder[InlineQueryResultCachedMpeg4Gif] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _mpeg4FileId           <- h.get[String]("mpeg4_file_id")
        _title                 <- h.get[Option[String]]("title")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent   <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedMpeg4Gif(
          id = _id,
          mpeg4FileId = _mpeg4FileId,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultcacheddocumentEncoder: Encoder[InlineQueryResultCachedDocument] =
    (x: InlineQueryResultCachedDocument) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "title"                 -> x.title.asJson,
          "document_file_id"      -> x.documentFileId.asJson,
          "description"           -> x.description.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcacheddocumentDecoder: Decoder[InlineQueryResultCachedDocument] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _title               <- h.get[String]("title")
        _documentFileId      <- h.get[String]("document_file_id")
        _description         <- h.get[Option[String]]("description")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "id"                       -> x.id.asJson,
          "video_file_id"            -> x.videoFileId.asJson,
          "title"                    -> x.title.asJson,
          "description"              -> x.description.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "input_message_content"    -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedvideoDecoder: Decoder[InlineQueryResultCachedVideo] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _videoFileId           <- h.get[String]("video_file_id")
        _title                 <- h.get[String]("title")
        _description           <- h.get[Option[String]]("description")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent   <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedVideo(
          id = _id,
          videoFileId = _videoFileId,
          title = _title,
          description = _description,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultgameEncoder: Encoder[InlineQueryResultGame] =
    (x: InlineQueryResultGame) => {
      Json.fromFields(
        List(
          "id"              -> x.id.asJson,
          "game_short_name" -> x.gameShortName.asJson,
          "reply_markup"    -> x.replyMarkup.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultgameDecoder: Decoder[InlineQueryResultGame] =
    Decoder.instance { h =>
      for {
        _id            <- h.get[String]("id")
        _gameShortName <- h.get[String]("game_short_name")
        _replyMarkup   <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        InlineQueryResultGame(id = _id, gameShortName = _gameShortName, replyMarkup = _replyMarkup)
      }
    }

  implicit lazy val inlinequeryresultcachedphotoEncoder: Encoder[InlineQueryResultCachedPhoto] =
    (x: InlineQueryResultCachedPhoto) => {
      Json.fromFields(
        List(
          "id"                       -> x.id.asJson,
          "photo_file_id"            -> x.photoFileId.asJson,
          "title"                    -> x.title.asJson,
          "description"              -> x.description.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "input_message_content"    -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedphotoDecoder: Decoder[InlineQueryResultCachedPhoto] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _photoFileId           <- h.get[String]("photo_file_id")
        _title                 <- h.get[Option[String]]("title")
        _description           <- h.get[Option[String]]("description")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent   <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedPhoto(
          id = _id,
          photoFileId = _photoFileId,
          title = _title,
          description = _description,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultcachedstickerEncoder: Encoder[InlineQueryResultCachedSticker] =
    (x: InlineQueryResultCachedSticker) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "sticker_file_id"       -> x.stickerFileId.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedstickerDecoder: Decoder[InlineQueryResultCachedSticker] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _stickerFileId       <- h.get[String]("sticker_file_id")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedSticker(
          id = _id,
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
          "id"                       -> x.id.asJson,
          "video_url"                -> x.videoUrl.asJson,
          "mime_type"                -> x.mimeType.asJson,
          "thumbnail_url"            -> x.thumbnailUrl.asJson,
          "title"                    -> x.title.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "video_width"              -> x.videoWidth.asJson,
          "video_height"             -> x.videoHeight.asJson,
          "video_duration"           -> x.videoDuration.asJson,
          "description"              -> x.description.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "input_message_content"    -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultvideoDecoder: Decoder[InlineQueryResultVideo] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _videoUrl              <- h.get[String]("video_url")
        _mimeType              <- h.get[String]("mime_type")
        _thumbnailUrl          <- h.get[String]("thumbnail_url")
        _title                 <- h.get[String]("title")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _videoWidth            <- h.get[Option[Int]]("video_width")
        _videoHeight           <- h.get[Option[Int]]("video_height")
        _videoDuration         <- h.get[Option[Int]]("video_duration")
        _description           <- h.get[Option[String]]("description")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent   <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultVideo(
          id = _id,
          videoUrl = _videoUrl,
          mimeType = _mimeType,
          thumbnailUrl = _thumbnailUrl,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
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
          "id"                    -> x.id.asJson,
          "audio_file_id"         -> x.audioFileId.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedaudioDecoder: Decoder[InlineQueryResultCachedAudio] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _audioFileId         <- h.get[String]("audio_file_id")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "id"                     -> x.id.asJson,
          "latitude"               -> x.latitude.asJson,
          "longitude"              -> x.longitude.asJson,
          "title"                  -> x.title.asJson,
          "horizontal_accuracy"    -> x.horizontalAccuracy.asJson,
          "live_period"            -> x.livePeriod.asJson,
          "heading"                -> x.heading.asJson,
          "proximity_alert_radius" -> x.proximityAlertRadius.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "input_message_content"  -> x.inputMessageContent.asJson,
          "thumbnail_url"          -> x.thumbnailUrl.asJson,
          "thumbnail_width"        -> x.thumbnailWidth.asJson,
          "thumbnail_height"       -> x.thumbnailHeight.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultlocationDecoder: Decoder[InlineQueryResultLocation] =
    Decoder.instance { h =>
      for {
        _id                   <- h.get[String]("id")
        _latitude             <- h.get[Float]("latitude")
        _longitude            <- h.get[Float]("longitude")
        _title                <- h.get[String]("title")
        _horizontalAccuracy   <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod           <- h.get[Option[Int]]("live_period")
        _heading              <- h.get[Option[Int]]("heading")
        _proximityAlertRadius <- h.get[Option[Int]]("proximity_alert_radius")
        _replyMarkup          <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent  <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbnailUrl         <- h.get[Option[String]]("thumbnail_url")
        _thumbnailWidth       <- h.get[Option[Int]]("thumbnail_width")
        _thumbnailHeight      <- h.get[Option[Int]]("thumbnail_height")
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
          thumbnailUrl = _thumbnailUrl,
          thumbnailWidth = _thumbnailWidth,
          thumbnailHeight = _thumbnailHeight
        )
      }
    }

  implicit lazy val inlinequeryresultcachedgifEncoder: Encoder[InlineQueryResultCachedGif] =
    (x: InlineQueryResultCachedGif) => {
      Json.fromFields(
        List(
          "id"                       -> x.id.asJson,
          "gif_file_id"              -> x.gifFileId.asJson,
          "title"                    -> x.title.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "input_message_content"    -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedgifDecoder: Decoder[InlineQueryResultCachedGif] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _gifFileId             <- h.get[String]("gif_file_id")
        _title                 <- h.get[Option[String]]("title")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _replyMarkup           <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent   <- h.get[Option[InputMessageContent]]("input_message_content")
      } yield {
        InlineQueryResultCachedGif(
          id = _id,
          gifFileId = _gifFileId,
          title = _title,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          replyMarkup = _replyMarkup,
          inputMessageContent = _inputMessageContent
        )
      }
    }

  implicit lazy val inlinequeryresultvoiceEncoder: Encoder[InlineQueryResultVoice] =
    (x: InlineQueryResultVoice) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "voice_url"             -> x.voiceUrl.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "voice_duration"        -> x.voiceDuration.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultvoiceDecoder: Decoder[InlineQueryResultVoice] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _voiceUrl            <- h.get[String]("voice_url")
        _title               <- h.get[String]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _voiceDuration       <- h.get[Option[Int]]("voice_duration")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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

  implicit lazy val inputmediaEncoder: Encoder[InputMedia] = {
    case photo: InputMediaPhoto         => photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case document: InputMediaDocument   => document.asJson.mapObject(_.add("type", Json.fromString("document")))
    case audio: InputMediaAudio         => audio.asJson.mapObject(_.add("type", Json.fromString("audio")))
    case animation: InputMediaAnimation => animation.asJson.mapObject(_.add("type", Json.fromString("animation")))
    case video: InputMediaVideo         => video.asJson.mapObject(_.add("type", Json.fromString("video")))
  }

  implicit lazy val inputmediaDecoder: Decoder[InputMedia] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "photo"     => Decoder[InputMediaPhoto]
      case "document"  => Decoder[InputMediaDocument]
      case "audio"     => Decoder[InputMediaAudio]
      case "animation" => Decoder[InputMediaAnimation]
      case "video"     => Decoder[InputMediaVideo]
      case unknown     => throw iozhik.DecodingError(s"Unknown type for InputMedia: $unknown")
    }
  } yield value

  implicit lazy val inputmediaanimationEncoder: Encoder[InputMediaAnimation] =
    (x: InputMediaAnimation) => {
      Json.fromFields(
        List(
          "media"                    -> x.media.asJson,
          "thumbnail"                -> x.thumbnail.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "width"                    -> x.width.asJson,
          "height"                   -> x.height.asJson,
          "duration"                 -> x.duration.asJson,
          "has_spoiler"              -> x.hasSpoiler.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediaanimationDecoder: Decoder[InputMediaAnimation] =
    Decoder.instance { h =>
      for {
        _media                 <- h.get[String]("media")
        _thumbnail             <- h.get[Option[IFile]]("thumbnail")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _width                 <- h.get[Option[Int]]("width")
        _height                <- h.get[Option[Int]]("height")
        _duration              <- h.get[Option[Int]]("duration")
        _hasSpoiler            <- h.get[Option[Boolean]]("has_spoiler")
      } yield {
        InputMediaAnimation(
          media = _media,
          thumbnail = _thumbnail,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          width = _width,
          height = _height,
          duration = _duration,
          hasSpoiler = _hasSpoiler
        )
      }
    }

  implicit lazy val inputmediaphotoEncoder: Encoder[InputMediaPhoto] =
    (x: InputMediaPhoto) => {
      Json.fromFields(
        List(
          "media"                    -> x.media.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "has_spoiler"              -> x.hasSpoiler.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediaphotoDecoder: Decoder[InputMediaPhoto] =
    Decoder.instance { h =>
      for {
        _media                 <- h.get[String]("media")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _hasSpoiler            <- h.get[Option[Boolean]]("has_spoiler")
      } yield {
        InputMediaPhoto(
          media = _media,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          hasSpoiler = _hasSpoiler
        )
      }
    }

  implicit lazy val inputmediavideoEncoder: Encoder[InputMediaVideo] =
    (x: InputMediaVideo) => {
      Json.fromFields(
        List(
          "media"                    -> x.media.asJson,
          "thumbnail"                -> x.thumbnail.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "width"                    -> x.width.asJson,
          "height"                   -> x.height.asJson,
          "duration"                 -> x.duration.asJson,
          "supports_streaming"       -> x.supportsStreaming.asJson,
          "has_spoiler"              -> x.hasSpoiler.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediavideoDecoder: Decoder[InputMediaVideo] =
    Decoder.instance { h =>
      for {
        _media                 <- h.get[String]("media")
        _thumbnail             <- h.get[Option[IFile]]("thumbnail")
        _caption               <- h.get[Option[String]]("caption")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _width                 <- h.get[Option[Int]]("width")
        _height                <- h.get[Option[Int]]("height")
        _duration              <- h.get[Option[Int]]("duration")
        _supportsStreaming     <- h.get[Option[Boolean]]("supports_streaming")
        _hasSpoiler            <- h.get[Option[Boolean]]("has_spoiler")
      } yield {
        InputMediaVideo(
          media = _media,
          thumbnail = _thumbnail,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          width = _width,
          height = _height,
          duration = _duration,
          supportsStreaming = _supportsStreaming,
          hasSpoiler = _hasSpoiler
        )
      }
    }

  implicit lazy val inputmediadocumentEncoder: Encoder[InputMediaDocument] =
    (x: InputMediaDocument) => {
      Json.fromFields(
        List(
          "media"                          -> x.media.asJson,
          "thumbnail"                      -> x.thumbnail.asJson,
          "caption"                        -> x.caption.asJson,
          "parse_mode"                     -> x.parseMode.asJson,
          "caption_entities"               -> x.captionEntities.asJson,
          "disable_content_type_detection" -> x.disableContentTypeDetection.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediadocumentDecoder: Decoder[InputMediaDocument] =
    Decoder.instance { h =>
      for {
        _media           <- h.get[String]("media")
        _thumbnail       <- h.get[Option[IFile]]("thumbnail")
        _caption         <- h.get[Option[String]]("caption")
        _parseMode       <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _disableContentTypeDetection <- h.get[Option[Boolean]]("disable_content_type_detection")
      } yield {
        InputMediaDocument(
          media = _media,
          thumbnail = _thumbnail,
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
          "media"            -> x.media.asJson,
          "thumbnail"        -> x.thumbnail.asJson,
          "caption"          -> x.caption.asJson,
          "parse_mode"       -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "duration"         -> x.duration.asJson,
          "performer"        -> x.performer.asJson,
          "title"            -> x.title.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediaaudioDecoder: Decoder[InputMediaAudio] =
    Decoder.instance { h =>
      for {
        _media           <- h.get[String]("media")
        _thumbnail       <- h.get[Option[IFile]]("thumbnail")
        _caption         <- h.get[Option[String]]("caption")
        _parseMode       <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _duration        <- h.get[Option[Int]]("duration")
        _performer       <- h.get[Option[String]]("performer")
        _title           <- h.get[Option[String]]("title")
      } yield {
        InputMediaAudio(
          media = _media,
          thumbnail = _thumbnail,
          caption = _caption,
          parseMode = _parseMode,
          captionEntities = _captionEntities,
          duration = _duration,
          performer = _performer,
          title = _title
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
          "latitude"          -> x.latitude.asJson,
          "longitude"         -> x.longitude.asJson,
          "title"             -> x.title.asJson,
          "address"           -> x.address.asJson,
          "foursquare_id"     -> x.foursquareId.asJson,
          "foursquare_type"   -> x.foursquareType.asJson,
          "google_place_id"   -> x.googlePlaceId.asJson,
          "google_place_type" -> x.googlePlaceType.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputvenuemessagecontentDecoder: Decoder[InputVenueMessageContent] =
    Decoder.instance { h =>
      for {
        _latitude        <- h.get[Float]("latitude")
        _longitude       <- h.get[Float]("longitude")
        _title           <- h.get[String]("title")
        _address         <- h.get[String]("address")
        _foursquareId    <- h.get[Option[String]]("foursquare_id")
        _foursquareType  <- h.get[Option[String]]("foursquare_type")
        _googlePlaceId   <- h.get[Option[String]]("google_place_id")
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
          "is_flexible"                   -> x.isFlexible.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputinvoicemessagecontentDecoder: Decoder[InputInvoiceMessageContent] =
    Decoder.instance { h =>
      for {
        _title                     <- h.get[String]("title")
        _description               <- h.get[String]("description")
        _payload                   <- h.get[String]("payload")
        _providerToken             <- h.get[Option[String]]("provider_token")
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
          "first_name"   -> x.firstName.asJson,
          "last_name"    -> x.lastName.asJson,
          "vcard"        -> x.vcard.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputcontactmessagecontentDecoder: Decoder[InputContactMessageContent] =
    Decoder.instance { h =>
      for {
        _phoneNumber <- h.get[String]("phone_number")
        _firstName   <- h.get[String]("first_name")
        _lastName    <- h.get[Option[String]]("last_name")
        _vcard       <- h.get[Option[String]]("vcard")
      } yield {
        InputContactMessageContent(
          phoneNumber = _phoneNumber,
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
          "latitude"               -> x.latitude.asJson,
          "longitude"              -> x.longitude.asJson,
          "horizontal_accuracy"    -> x.horizontalAccuracy.asJson,
          "live_period"            -> x.livePeriod.asJson,
          "heading"                -> x.heading.asJson,
          "proximity_alert_radius" -> x.proximityAlertRadius.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputlocationmessagecontentDecoder: Decoder[InputLocationMessageContent] =
    Decoder.instance { h =>
      for {
        _latitude             <- h.get[Float]("latitude")
        _longitude            <- h.get[Float]("longitude")
        _horizontalAccuracy   <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod           <- h.get[Option[Int]]("live_period")
        _heading              <- h.get[Option[Int]]("heading")
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
          "message_text"         -> x.messageText.asJson,
          "parse_mode"           -> x.parseMode.asJson,
          "entities"             -> x.entities.asJson,
          "link_preview_options" -> x.linkPreviewOptions.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputtextmessagecontentDecoder: Decoder[InputTextMessageContent] =
    Decoder.instance { h =>
      for {
        _messageText        <- h.get[String]("message_text")
        _parseMode          <- h.get[Option[ParseMode]]("parse_mode")
        _entities           <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("entities")(List.empty)
        _linkPreviewOptions <- h.get[Option[LinkPreviewOptions]]("link_preview_options")
      } yield {
        InputTextMessageContent(
          messageText = _messageText,
          parseMode = _parseMode,
          entities = _entities,
          linkPreviewOptions = _linkPreviewOptions
        )
      }
    }

  implicit lazy val inputpaidmediaEncoder: Encoder[InputPaidMedia] = {
    case photo: InputPaidMediaPhoto => photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case video: InputPaidMediaVideo => video.asJson.mapObject(_.add("type", Json.fromString("video")))
  }

  implicit lazy val inputpaidmediaDecoder: Decoder[InputPaidMedia] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "photo" => Decoder[InputPaidMediaPhoto]
      case "video" => Decoder[InputPaidMediaVideo]
      case unknown => throw iozhik.DecodingError(s"Unknown type for InputPaidMedia: $unknown")
    }
  } yield value

  implicit lazy val inputpaidmediaphotoEncoder: Encoder[InputPaidMediaPhoto] =
    (x: InputPaidMediaPhoto) => {
      Json.fromFields(
        List(
          "media" -> x.media.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputpaidmediaphotoDecoder: Decoder[InputPaidMediaPhoto] =
    Decoder.instance { h =>
      for {
        _media <- h.get[String]("media")
      } yield {
        InputPaidMediaPhoto(media = _media)
      }
    }

  implicit lazy val inputpaidmediavideoEncoder: Encoder[InputPaidMediaVideo] =
    (x: InputPaidMediaVideo) => {
      Json.fromFields(
        List(
          "media"              -> x.media.asJson,
          "thumbnail"          -> x.thumbnail.asJson,
          "width"              -> x.width.asJson,
          "height"             -> x.height.asJson,
          "duration"           -> x.duration.asJson,
          "supports_streaming" -> x.supportsStreaming.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputpaidmediavideoDecoder: Decoder[InputPaidMediaVideo] =
    Decoder.instance { h =>
      for {
        _media             <- h.get[String]("media")
        _thumbnail         <- h.get[Option[IFile]]("thumbnail")
        _width             <- h.get[Option[Int]]("width")
        _height            <- h.get[Option[Int]]("height")
        _duration          <- h.get[Option[Int]]("duration")
        _supportsStreaming <- h.get[Option[Boolean]]("supports_streaming")
      } yield {
        InputPaidMediaVideo(
          media = _media,
          thumbnail = _thumbnail,
          width = _width,
          height = _height,
          duration = _duration,
          supportsStreaming = _supportsStreaming
        )
      }
    }

  implicit lazy val maybeinaccessiblemessageEncoder: Encoder[MaybeInaccessibleMessage] = {
    case x: Message             => x.asJson
    case x: InaccessibleMessage => x.asJson
  }

  implicit lazy val maybeinaccessiblemessageDecoder: Decoder[MaybeInaccessibleMessage] = {
    for {
      fDate <- Decoder[Int].prepare(_.downField("date"))
      value <- fDate match {
        case 0 => Decoder[InaccessibleMessage]
        case _ => Decoder[Message]
      }
    } yield value
  }

  implicit lazy val messageEncoder: Encoder[Message] =
    (x: Message) => {
      Json.fromFields(
        List(
          "message_id"                        -> x.messageId.asJson,
          "message_thread_id"                 -> x.messageThreadId.asJson,
          "from"                              -> x.from.asJson,
          "sender_chat"                       -> x.senderChat.asJson,
          "sender_boost_count"                -> x.senderBoostCount.asJson,
          "sender_business_bot"               -> x.senderBusinessBot.asJson,
          "date"                              -> x.date.asJson,
          "business_connection_id"            -> x.businessConnectionId.asJson,
          "chat"                              -> x.chat.asJson,
          "forward_origin"                    -> x.forwardOrigin.asJson,
          "is_topic_message"                  -> x.isTopicMessage.asJson,
          "is_automatic_forward"              -> x.isAutomaticForward.asJson,
          "reply_to_message"                  -> x.replyToMessage.asJson,
          "external_reply"                    -> x.externalReply.asJson,
          "quote"                             -> x.quote.asJson,
          "reply_to_story"                    -> x.replyToStory.asJson,
          "via_bot"                           -> x.viaBot.asJson,
          "edit_date"                         -> x.editDate.asJson,
          "has_protected_content"             -> x.hasProtectedContent.asJson,
          "is_from_offline"                   -> x.isFromOffline.asJson,
          "media_group_id"                    -> x.mediaGroupId.asJson,
          "author_signature"                  -> x.authorSignature.asJson,
          "text"                              -> x.text.asJson,
          "entities"                          -> x.entities.asJson,
          "link_preview_options"              -> x.linkPreviewOptions.asJson,
          "effect_id"                         -> x.effectId.asJson,
          "animation"                         -> x.animation.asJson,
          "audio"                             -> x.audio.asJson,
          "document"                          -> x.document.asJson,
          "paid_media"                        -> x.paidMedia.asJson,
          "photo"                             -> x.photo.asJson,
          "sticker"                           -> x.sticker.asJson,
          "story"                             -> x.story.asJson,
          "video"                             -> x.video.asJson,
          "video_note"                        -> x.videoNote.asJson,
          "voice"                             -> x.voice.asJson,
          "caption"                           -> x.caption.asJson,
          "caption_entities"                  -> x.captionEntities.asJson,
          "show_caption_above_media"          -> x.showCaptionAboveMedia.asJson,
          "has_media_spoiler"                 -> x.hasMediaSpoiler.asJson,
          "contact"                           -> x.contact.asJson,
          "dice"                              -> x.dice.asJson,
          "game"                              -> x.game.asJson,
          "poll"                              -> x.poll.asJson,
          "venue"                             -> x.venue.asJson,
          "location"                          -> x.location.asJson,
          "new_chat_members"                  -> x.newChatMembers.asJson,
          "left_chat_member"                  -> x.leftChatMember.asJson,
          "new_chat_title"                    -> x.newChatTitle.asJson,
          "new_chat_photo"                    -> x.newChatPhoto.asJson,
          "delete_chat_photo"                 -> x.deleteChatPhoto.asJson,
          "group_chat_created"                -> x.groupChatCreated.asJson,
          "supergroup_chat_created"           -> x.supergroupChatCreated.asJson,
          "channel_chat_created"              -> x.channelChatCreated.asJson,
          "message_auto_delete_timer_changed" -> x.messageAutoDeleteTimerChanged.asJson,
          "migrate_to_chat_id"                -> x.migrateToChatId.asJson,
          "migrate_from_chat_id"              -> x.migrateFromChatId.asJson,
          "pinned_message"                    -> x.pinnedMessage.asJson,
          "invoice"                           -> x.invoice.asJson,
          "successful_payment"                -> x.successfulPayment.asJson,
          "refunded_payment"                  -> x.refundedPayment.asJson,
          "users_shared"                      -> x.usersShared.asJson,
          "chat_shared"                       -> x.chatShared.asJson,
          "connected_website"                 -> x.connectedWebsite.asJson,
          "write_access_allowed"              -> x.writeAccessAllowed.asJson,
          "passport_data"                     -> x.passportData.asJson,
          "proximity_alert_triggered"         -> x.proximityAlertTriggered.asJson,
          "boost_added"                       -> x.boostAdded.asJson,
          "chat_background_set"               -> x.chatBackgroundSet.asJson,
          "forum_topic_created"               -> x.forumTopicCreated.asJson,
          "forum_topic_edited"                -> x.forumTopicEdited.asJson,
          "forum_topic_closed"                -> x.forumTopicClosed.asJson,
          "forum_topic_reopened"              -> x.forumTopicReopened.asJson,
          "general_forum_topic_hidden"        -> x.generalForumTopicHidden.asJson,
          "general_forum_topic_unhidden"      -> x.generalForumTopicUnhidden.asJson,
          "giveaway_created"                  -> x.giveawayCreated.asJson,
          "giveaway"                          -> x.giveaway.asJson,
          "giveaway_winners"                  -> x.giveawayWinners.asJson,
          "giveaway_completed"                -> x.giveawayCompleted.asJson,
          "video_chat_scheduled"              -> x.videoChatScheduled.asJson,
          "video_chat_started"                -> x.videoChatStarted.asJson,
          "video_chat_ended"                  -> x.videoChatEnded.asJson,
          "video_chat_participants_invited"   -> x.videoChatParticipantsInvited.asJson,
          "web_app_data"                      -> x.webAppData.asJson,
          "reply_markup"                      -> x.replyMarkup.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageDecoder: Decoder[Message] =
    Decoder.instance { h =>
      for {
        _messageId             <- h.get[Int]("message_id")
        _messageThreadId       <- h.get[Option[Int]]("message_thread_id")
        _from                  <- h.get[Option[User]]("from")
        _senderChat            <- h.get[Option[Chat]]("sender_chat")
        _senderBoostCount      <- h.get[Option[Int]]("sender_boost_count")
        _senderBusinessBot     <- h.get[Option[User]]("sender_business_bot")
        _date                  <- h.get[Int]("date")
        _businessConnectionId  <- h.get[Option[String]]("business_connection_id")
        _chat                  <- h.get[Chat]("chat")
        _forwardOrigin         <- h.get[Option[iozhik.OpenEnum[MessageOrigin]]]("forward_origin")
        _isTopicMessage        <- h.get[Option[Boolean]]("is_topic_message")
        _isAutomaticForward    <- h.get[Option[Boolean]]("is_automatic_forward")
        _replyToMessage        <- h.get[Option[Message]]("reply_to_message")
        _externalReply         <- h.get[Option[ExternalReplyInfo]]("external_reply")
        _quote                 <- h.get[Option[TextQuote]]("quote")
        _replyToStory          <- h.get[Option[Story]]("reply_to_story")
        _viaBot                <- h.get[Option[User]]("via_bot")
        _editDate              <- h.get[Option[Int]]("edit_date")
        _hasProtectedContent   <- h.get[Option[Boolean]]("has_protected_content")
        _isFromOffline         <- h.get[Option[Boolean]]("is_from_offline")
        _mediaGroupId          <- h.get[Option[String]]("media_group_id")
        _authorSignature       <- h.get[Option[String]]("author_signature")
        _text                  <- h.get[Option[String]]("text")
        _entities              <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("entities")(List.empty)
        _linkPreviewOptions    <- h.get[Option[LinkPreviewOptions]]("link_preview_options")
        _effectId              <- h.get[Option[String]]("effect_id")
        _animation             <- h.get[Option[Animation]]("animation")
        _audio                 <- h.get[Option[Audio]]("audio")
        _document              <- h.get[Option[Document]]("document")
        _paidMedia             <- h.get[Option[PaidMediaInfo]]("paid_media")
        _photo                 <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
        _sticker               <- h.get[Option[Sticker]]("sticker")
        _story                 <- h.get[Option[Story]]("story")
        _video                 <- h.get[Option[Video]]("video")
        _videoNote             <- h.get[Option[VideoNote]]("video_note")
        _voice                 <- h.get[Option[Voice]]("voice")
        _caption               <- h.get[Option[String]]("caption")
        _captionEntities       <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("caption_entities")(List.empty)
        _showCaptionAboveMedia <- h.get[Option[Boolean]]("show_caption_above_media")
        _hasMediaSpoiler       <- h.get[Option[Boolean]]("has_media_spoiler")
        _contact               <- h.get[Option[Contact]]("contact")
        _dice                  <- h.get[Option[Dice]]("dice")
        _game                  <- h.get[Option[Game]]("game")
        _poll                  <- h.get[Option[Poll]]("poll")
        _venue                 <- h.get[Option[Venue]]("venue")
        _location              <- h.get[Option[Location]]("location")
        _newChatMembers        <- h.getOrElse[List[User]]("new_chat_members")(List.empty)
        _leftChatMember        <- h.get[Option[User]]("left_chat_member")
        _newChatTitle          <- h.get[Option[String]]("new_chat_title")
        _newChatPhoto          <- h.getOrElse[List[PhotoSize]]("new_chat_photo")(List.empty)
        _deleteChatPhoto       <- h.get[Option[Boolean]]("delete_chat_photo")
        _groupChatCreated      <- h.get[Option[Boolean]]("group_chat_created")
        _supergroupChatCreated <- h.get[Option[Boolean]]("supergroup_chat_created")
        _channelChatCreated    <- h.get[Option[Boolean]]("channel_chat_created")
        _messageAutoDeleteTimerChanged <- h.get[Option[MessageAutoDeleteTimerChanged]](
          "message_auto_delete_timer_changed"
        )
        _migrateToChatId              <- h.get[Option[Long]]("migrate_to_chat_id")
        _migrateFromChatId            <- h.get[Option[Long]]("migrate_from_chat_id")
        _pinnedMessage                <- h.get[Option[MaybeInaccessibleMessage]]("pinned_message")
        _invoice                      <- h.get[Option[Invoice]]("invoice")
        _successfulPayment            <- h.get[Option[SuccessfulPayment]]("successful_payment")
        _refundedPayment              <- h.get[Option[RefundedPayment]]("refunded_payment")
        _usersShared                  <- h.get[Option[UsersShared]]("users_shared")
        _chatShared                   <- h.get[Option[ChatShared]]("chat_shared")
        _connectedWebsite             <- h.get[Option[String]]("connected_website")
        _writeAccessAllowed           <- h.get[Option[WriteAccessAllowed]]("write_access_allowed")
        _passportData                 <- h.get[Option[PassportData]]("passport_data")
        _proximityAlertTriggered      <- h.get[Option[ProximityAlertTriggered]]("proximity_alert_triggered")
        _boostAdded                   <- h.get[Option[ChatBoostAdded]]("boost_added")
        _chatBackgroundSet            <- h.get[Option[ChatBackground]]("chat_background_set")
        _forumTopicCreated            <- h.get[Option[ForumTopicCreated]]("forum_topic_created")
        _forumTopicEdited             <- h.get[Option[ForumTopicEdited]]("forum_topic_edited")
        _forumTopicClosed             <- h.get[Option[ForumTopicClosed.type]]("forum_topic_closed")
        _forumTopicReopened           <- h.get[Option[ForumTopicReopened.type]]("forum_topic_reopened")
        _generalForumTopicHidden      <- h.get[Option[GeneralForumTopicHidden.type]]("general_forum_topic_hidden")
        _generalForumTopicUnhidden    <- h.get[Option[GeneralForumTopicUnhidden.type]]("general_forum_topic_unhidden")
        _giveawayCreated              <- h.get[Option[GiveawayCreated]]("giveaway_created")
        _giveaway                     <- h.get[Option[Giveaway]]("giveaway")
        _giveawayWinners              <- h.get[Option[GiveawayWinners]]("giveaway_winners")
        _giveawayCompleted            <- h.get[Option[GiveawayCompleted]]("giveaway_completed")
        _videoChatScheduled           <- h.get[Option[VideoChatScheduled]]("video_chat_scheduled")
        _videoChatStarted             <- h.get[Option[VideoChatStarted.type]]("video_chat_started")
        _videoChatEnded               <- h.get[Option[VideoChatEnded]]("video_chat_ended")
        _videoChatParticipantsInvited <- h.get[Option[VideoChatParticipantsInvited]]("video_chat_participants_invited")
        _webAppData                   <- h.get[Option[WebAppData]]("web_app_data")
        _replyMarkup                  <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
      } yield {
        Message(
          messageId = _messageId,
          messageThreadId = _messageThreadId,
          from = _from,
          senderChat = _senderChat,
          senderBoostCount = _senderBoostCount,
          senderBusinessBot = _senderBusinessBot,
          date = _date,
          businessConnectionId = _businessConnectionId,
          chat = _chat,
          forwardOrigin = _forwardOrigin,
          isTopicMessage = _isTopicMessage,
          isAutomaticForward = _isAutomaticForward,
          replyToMessage = _replyToMessage,
          externalReply = _externalReply,
          quote = _quote,
          replyToStory = _replyToStory,
          viaBot = _viaBot,
          editDate = _editDate,
          hasProtectedContent = _hasProtectedContent,
          isFromOffline = _isFromOffline,
          mediaGroupId = _mediaGroupId,
          authorSignature = _authorSignature,
          text = _text,
          entities = _entities,
          linkPreviewOptions = _linkPreviewOptions,
          effectId = _effectId,
          animation = _animation,
          audio = _audio,
          document = _document,
          paidMedia = _paidMedia,
          photo = _photo,
          sticker = _sticker,
          story = _story,
          video = _video,
          videoNote = _videoNote,
          voice = _voice,
          caption = _caption,
          captionEntities = _captionEntities,
          showCaptionAboveMedia = _showCaptionAboveMedia,
          hasMediaSpoiler = _hasMediaSpoiler,
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
          refundedPayment = _refundedPayment,
          usersShared = _usersShared,
          chatShared = _chatShared,
          connectedWebsite = _connectedWebsite,
          writeAccessAllowed = _writeAccessAllowed,
          passportData = _passportData,
          proximityAlertTriggered = _proximityAlertTriggered,
          boostAdded = _boostAdded,
          chatBackgroundSet = _chatBackgroundSet,
          forumTopicCreated = _forumTopicCreated,
          forumTopicEdited = _forumTopicEdited,
          forumTopicClosed = _forumTopicClosed,
          forumTopicReopened = _forumTopicReopened,
          generalForumTopicHidden = _generalForumTopicHidden,
          generalForumTopicUnhidden = _generalForumTopicUnhidden,
          giveawayCreated = _giveawayCreated,
          giveaway = _giveaway,
          giveawayWinners = _giveawayWinners,
          giveawayCompleted = _giveawayCompleted,
          videoChatScheduled = _videoChatScheduled,
          videoChatStarted = _videoChatStarted,
          videoChatEnded = _videoChatEnded,
          videoChatParticipantsInvited = _videoChatParticipantsInvited,
          webAppData = _webAppData,
          replyMarkup = _replyMarkup
        )
      }
    }

  implicit lazy val inaccessiblemessageEncoder: Encoder[InaccessibleMessage] =
    (x: InaccessibleMessage) => {
      Json.fromFields(
        List(
          "chat"       -> x.chat.asJson,
          "message_id" -> x.messageId.asJson,
          "date"       -> x.date.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inaccessiblemessageDecoder: Decoder[InaccessibleMessage] =
    Decoder.instance { h =>
      for {
        _chat      <- h.get[Chat]("chat")
        _messageId <- h.get[Int]("message_id")
        _date      <- h.get[Int]("date")
      } yield {
        InaccessibleMessage(chat = _chat, messageId = _messageId, date = _date)
      }
    }

  implicit lazy val menubuttonEncoder: Encoder[MenuButton] = {
    case web_app: MenuButtonWebApp         => web_app.asJson.mapObject(_.add("type", Json.fromString("web_app")))
    case default: MenuButtonDefault.type   => default.asJson.mapObject(_.add("type", Json.fromString("default")))
    case commands: MenuButtonCommands.type => commands.asJson.mapObject(_.add("type", Json.fromString("commands")))
  }

  implicit lazy val menubuttonDecoder: Decoder[iozhik.OpenEnum[MenuButton]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "web_app"  => Decoder[MenuButtonWebApp].map(iozhik.OpenEnum.Known(_))
      case "default"  => Decoder[MenuButtonDefault.type].map(iozhik.OpenEnum.Known(_))
      case "commands" => Decoder[MenuButtonCommands.type].map(iozhik.OpenEnum.Known(_))
      case unknown    => Decoder.const(iozhik.OpenEnum.Unknown[MenuButton](unknown))
    }
  } yield value

  implicit lazy val menubuttondefaultEncoder: Encoder[MenuButtonDefault.type] = (_: MenuButtonDefault.type) => ().asJson
  implicit lazy val menubuttondefaultDecoder: Decoder[MenuButtonDefault.type] = (_: HCursor) => Right(MenuButtonDefault)

  implicit lazy val menubuttonwebappEncoder: Encoder[MenuButtonWebApp] =
    (x: MenuButtonWebApp) => {
      Json.fromFields(
        List(
          "text"    -> x.text.asJson,
          "web_app" -> x.webApp.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val menubuttonwebappDecoder: Decoder[MenuButtonWebApp] =
    Decoder.instance { h =>
      for {
        _text   <- h.get[String]("text")
        _webApp <- h.get[WebAppInfo]("web_app")
      } yield {
        MenuButtonWebApp(text = _text, webApp = _webApp)
      }
    }

  implicit lazy val menubuttoncommandsEncoder: Encoder[MenuButtonCommands.type] = (_: MenuButtonCommands.type) =>
    ().asJson

  implicit lazy val menubuttoncommandsDecoder: Decoder[MenuButtonCommands.type] = (_: HCursor) =>
    Right(MenuButtonCommands)

  implicit lazy val messageentityEncoder: Encoder[MessageEntity] = {
    case underline: UnderlineMessageEntity => underline.asJson.mapObject(_.add("type", Json.fromString("underline")))
    case expandable_blockquote: ExpandableBlockquoteMessageEntity =>
      expandable_blockquote.asJson.mapObject(_.add("type", Json.fromString("expandable_blockquote")))
    case hashtag: HashtagMessageEntity => hashtag.asJson.mapObject(_.add("type", Json.fromString("hashtag")))
    case blockquote: BlockquoteMessageEntity =>
      blockquote.asJson.mapObject(_.add("type", Json.fromString("blockquote")))
    case text_link: TextLinkMessageEntity => text_link.asJson.mapObject(_.add("type", Json.fromString("text_link")))
    case strikethrough: StrikethroughMessageEntity =>
      strikethrough.asJson.mapObject(_.add("type", Json.fromString("strikethrough")))
    case pre: PreMessageEntity => pre.asJson.mapObject(_.add("type", Json.fromString("pre")))
    case text_mention: TextMentionMessageEntity =>
      text_mention.asJson.mapObject(_.add("type", Json.fromString("text_mention")))
    case bot_command: BotCommandMessageEntity =>
      bot_command.asJson.mapObject(_.add("type", Json.fromString("bot_command")))
    case phone_number: PhoneNumberMessageEntity =>
      phone_number.asJson.mapObject(_.add("type", Json.fromString("phone_number")))
    case email: EmailMessageEntity => email.asJson.mapObject(_.add("type", Json.fromString("email")))
    case custom_emoji: CustomEmojiMessageEntity =>
      custom_emoji.asJson.mapObject(_.add("type", Json.fromString("custom_emoji")))
    case url: UrlMessageEntity         => url.asJson.mapObject(_.add("type", Json.fromString("url")))
    case italic: ItalicMessageEntity   => italic.asJson.mapObject(_.add("type", Json.fromString("italic")))
    case bold: BoldMessageEntity       => bold.asJson.mapObject(_.add("type", Json.fromString("bold")))
    case cashtag: CashtagMessageEntity => cashtag.asJson.mapObject(_.add("type", Json.fromString("cashtag")))
    case code: CodeMessageEntity       => code.asJson.mapObject(_.add("type", Json.fromString("code")))
    case spoiler: SpoilerMessageEntity => spoiler.asJson.mapObject(_.add("type", Json.fromString("spoiler")))
    case mention: MentionMessageEntity => mention.asJson.mapObject(_.add("type", Json.fromString("mention")))
  }

  implicit lazy val messageentityDecoder: Decoder[iozhik.OpenEnum[MessageEntity]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "underline"             => Decoder[UnderlineMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "expandable_blockquote" => Decoder[ExpandableBlockquoteMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "hashtag"               => Decoder[HashtagMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "blockquote"            => Decoder[BlockquoteMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "text_link"             => Decoder[TextLinkMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "strikethrough"         => Decoder[StrikethroughMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "pre"                   => Decoder[PreMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "text_mention"          => Decoder[TextMentionMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "bot_command"           => Decoder[BotCommandMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "phone_number"          => Decoder[PhoneNumberMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "email"                 => Decoder[EmailMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "custom_emoji"          => Decoder[CustomEmojiMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "url"                   => Decoder[UrlMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "italic"                => Decoder[ItalicMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "bold"                  => Decoder[BoldMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "cashtag"               => Decoder[CashtagMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "code"                  => Decoder[CodeMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "spoiler"               => Decoder[SpoilerMessageEntity].map(iozhik.OpenEnum.Known(_))
      case "mention"               => Decoder[MentionMessageEntity].map(iozhik.OpenEnum.Known(_))
      case unknown                 => Decoder.const(iozhik.OpenEnum.Unknown[MessageEntity](unknown))
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

  implicit lazy val customemojimessageentityEncoder: Encoder[CustomEmojiMessageEntity] =
    (x: CustomEmojiMessageEntity) => {
      Json.fromFields(
        List(
          "offset"          -> x.offset.asJson,
          "length"          -> x.length.asJson,
          "custom_emoji_id" -> x.customEmojiId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val customemojimessageentityDecoder: Decoder[CustomEmojiMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset        <- h.get[Int]("offset")
        _length        <- h.get[Int]("length")
        _customEmojiId <- h.get[String]("custom_emoji_id")
      } yield {
        CustomEmojiMessageEntity(offset = _offset, length = _length, customEmojiId = _customEmojiId)
      }
    }

  implicit lazy val expandableblockquotemessageentityEncoder: Encoder[ExpandableBlockquoteMessageEntity] =
    (x: ExpandableBlockquoteMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val expandableblockquotemessageentityDecoder: Decoder[ExpandableBlockquoteMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        ExpandableBlockquoteMessageEntity(offset = _offset, length = _length)
      }
    }

  implicit lazy val spoilermessageentityEncoder: Encoder[SpoilerMessageEntity] =
    (x: SpoilerMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val spoilermessageentityDecoder: Decoder[SpoilerMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        SpoilerMessageEntity(offset = _offset, length = _length)
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

  implicit lazy val blockquotemessageentityEncoder: Encoder[BlockquoteMessageEntity] =
    (x: BlockquoteMessageEntity) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "length" -> x.length.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val blockquotemessageentityDecoder: Decoder[BlockquoteMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
      } yield {
        BlockquoteMessageEntity(offset = _offset, length = _length)
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
          "offset"   -> x.offset.asJson,
          "length"   -> x.length.asJson,
          "language" -> x.language.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val premessageentityDecoder: Decoder[PreMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset   <- h.get[Int]("offset")
        _length   <- h.get[Int]("length")
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
          "user"   -> x.user.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val textmentionmessageentityDecoder: Decoder[TextMentionMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
        _user   <- h.get[User]("user")
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
          "url"    -> x.url.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val textlinkmessageentityDecoder: Decoder[TextLinkMessageEntity] =
    Decoder.instance { h =>
      for {
        _offset <- h.get[Int]("offset")
        _length <- h.get[Int]("length")
        _url    <- h.get[String]("url")
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

  implicit lazy val messageoriginEncoder: Encoder[MessageOrigin] = {
    case channel: MessageOriginChannel => channel.asJson.mapObject(_.add("type", Json.fromString("channel")))
    case hidden_user: MessageOriginHiddenUser =>
      hidden_user.asJson.mapObject(_.add("type", Json.fromString("hidden_user")))
    case chat: MessageOriginChat => chat.asJson.mapObject(_.add("type", Json.fromString("chat")))
    case user: MessageOriginUser => user.asJson.mapObject(_.add("type", Json.fromString("user")))
  }

  implicit lazy val messageoriginDecoder: Decoder[iozhik.OpenEnum[MessageOrigin]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "channel"     => Decoder[MessageOriginChannel].map(iozhik.OpenEnum.Known(_))
      case "hidden_user" => Decoder[MessageOriginHiddenUser].map(iozhik.OpenEnum.Known(_))
      case "chat"        => Decoder[MessageOriginChat].map(iozhik.OpenEnum.Known(_))
      case "user"        => Decoder[MessageOriginUser].map(iozhik.OpenEnum.Known(_))
      case unknown       => Decoder.const(iozhik.OpenEnum.Unknown[MessageOrigin](unknown))
    }
  } yield value

  implicit lazy val messageoriginuserEncoder: Encoder[MessageOriginUser] =
    (x: MessageOriginUser) => {
      Json.fromFields(
        List(
          "date"        -> x.date.asJson,
          "sender_user" -> x.senderUser.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageoriginuserDecoder: Decoder[MessageOriginUser] =
    Decoder.instance { h =>
      for {
        _date       <- h.get[Int]("date")
        _senderUser <- h.get[User]("sender_user")
      } yield {
        MessageOriginUser(date = _date, senderUser = _senderUser)
      }
    }

  implicit lazy val messageoriginchannelEncoder: Encoder[MessageOriginChannel] =
    (x: MessageOriginChannel) => {
      Json.fromFields(
        List(
          "date"             -> x.date.asJson,
          "chat"             -> x.chat.asJson,
          "message_id"       -> x.messageId.asJson,
          "author_signature" -> x.authorSignature.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageoriginchannelDecoder: Decoder[MessageOriginChannel] =
    Decoder.instance { h =>
      for {
        _date            <- h.get[Int]("date")
        _chat            <- h.get[Chat]("chat")
        _messageId       <- h.get[Int]("message_id")
        _authorSignature <- h.get[Option[String]]("author_signature")
      } yield {
        MessageOriginChannel(date = _date, chat = _chat, messageId = _messageId, authorSignature = _authorSignature)
      }
    }

  implicit lazy val messageoriginhiddenuserEncoder: Encoder[MessageOriginHiddenUser] =
    (x: MessageOriginHiddenUser) => {
      Json.fromFields(
        List(
          "date"             -> x.date.asJson,
          "sender_user_name" -> x.senderUserName.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageoriginhiddenuserDecoder: Decoder[MessageOriginHiddenUser] =
    Decoder.instance { h =>
      for {
        _date           <- h.get[Int]("date")
        _senderUserName <- h.get[String]("sender_user_name")
      } yield {
        MessageOriginHiddenUser(date = _date, senderUserName = _senderUserName)
      }
    }

  implicit lazy val messageoriginchatEncoder: Encoder[MessageOriginChat] =
    (x: MessageOriginChat) => {
      Json.fromFields(
        List(
          "date"             -> x.date.asJson,
          "sender_chat"      -> x.senderChat.asJson,
          "author_signature" -> x.authorSignature.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageoriginchatDecoder: Decoder[MessageOriginChat] =
    Decoder.instance { h =>
      for {
        _date            <- h.get[Int]("date")
        _senderChat      <- h.get[Chat]("sender_chat")
        _authorSignature <- h.get[Option[String]]("author_signature")
      } yield {
        MessageOriginChat(date = _date, senderChat = _senderChat, authorSignature = _authorSignature)
      }
    }

  implicit lazy val paidmediaEncoder: Encoder[PaidMedia] = {
    case photo: PaidMediaPhoto     => photo.asJson.mapObject(_.add("type", Json.fromString("photo")))
    case preview: PaidMediaPreview => preview.asJson.mapObject(_.add("type", Json.fromString("preview")))
    case video: PaidMediaVideo     => video.asJson.mapObject(_.add("type", Json.fromString("video")))
  }

  implicit lazy val paidmediaDecoder: Decoder[iozhik.OpenEnum[PaidMedia]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "photo"   => Decoder[PaidMediaPhoto].map(iozhik.OpenEnum.Known(_))
      case "preview" => Decoder[PaidMediaPreview].map(iozhik.OpenEnum.Known(_))
      case "video"   => Decoder[PaidMediaVideo].map(iozhik.OpenEnum.Known(_))
      case unknown   => Decoder.const(iozhik.OpenEnum.Unknown[PaidMedia](unknown))
    }
  } yield value

  implicit lazy val paidmediavideoEncoder: Encoder[PaidMediaVideo] =
    (x: PaidMediaVideo) => {
      Json.fromFields(
        List(
          "video" -> x.video.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val paidmediavideoDecoder: Decoder[PaidMediaVideo] =
    Decoder.instance { h =>
      for {
        _video <- h.get[Video]("video")
      } yield {
        PaidMediaVideo(video = _video)
      }
    }

  implicit lazy val paidmediaphotoEncoder: Encoder[PaidMediaPhoto] =
    (x: PaidMediaPhoto) => {
      Json.fromFields(
        List(
          "photo" -> x.photo.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val paidmediaphotoDecoder: Decoder[PaidMediaPhoto] =
    Decoder.instance { h =>
      for {
        _photo <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
      } yield {
        PaidMediaPhoto(photo = _photo)
      }
    }

  implicit lazy val paidmediapreviewEncoder: Encoder[PaidMediaPreview] =
    (x: PaidMediaPreview) => {
      Json.fromFields(
        List(
          "width"    -> x.width.asJson,
          "height"   -> x.height.asJson,
          "duration" -> x.duration.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val paidmediapreviewDecoder: Decoder[PaidMediaPreview] =
    Decoder.instance { h =>
      for {
        _width    <- h.get[Option[Int]]("width")
        _height   <- h.get[Option[Int]]("height")
        _duration <- h.get[Option[Int]]("duration")
      } yield {
        PaidMediaPreview(width = _width, height = _height, duration = _duration)
      }
    }

  implicit lazy val passportelementerrorEncoder: Encoder[PassportElementError] = {
    case translation_file: PassportElementErrorTranslationFile =>
      translation_file.asJson.mapObject(_.add("source", Json.fromString("translation_file")))
    case translation_files: PassportElementErrorTranslationFiles =>
      translation_files.asJson.mapObject(_.add("source", Json.fromString("translation_files")))
    case front_side: PassportElementErrorFrontSide =>
      front_side.asJson.mapObject(_.add("source", Json.fromString("front_side")))
    case files: PassportElementErrorFiles => files.asJson.mapObject(_.add("source", Json.fromString("files")))
    case unspecified: PassportElementErrorUnspecified =>
      unspecified.asJson.mapObject(_.add("source", Json.fromString("unspecified")))
    case file: PassportElementErrorFile     => file.asJson.mapObject(_.add("source", Json.fromString("file")))
    case selfie: PassportElementErrorSelfie => selfie.asJson.mapObject(_.add("source", Json.fromString("selfie")))
    case reverse_side: PassportElementErrorReverseSide =>
      reverse_side.asJson.mapObject(_.add("source", Json.fromString("reverse_side")))
    case data: PassportElementErrorDataField => data.asJson.mapObject(_.add("source", Json.fromString("data")))
  }

  implicit lazy val passportelementerrorDecoder: Decoder[PassportElementError] = for {
    fType <- Decoder[String].prepare(_.downField("source"))
    value <- fType match {
      case "translation_file"  => Decoder[PassportElementErrorTranslationFile]
      case "translation_files" => Decoder[PassportElementErrorTranslationFiles]
      case "front_side"        => Decoder[PassportElementErrorFrontSide]
      case "files"             => Decoder[PassportElementErrorFiles]
      case "unspecified"       => Decoder[PassportElementErrorUnspecified]
      case "file"              => Decoder[PassportElementErrorFile]
      case "selfie"            => Decoder[PassportElementErrorSelfie]
      case "reverse_side"      => Decoder[PassportElementErrorReverseSide]
      case "data"              => Decoder[PassportElementErrorDataField]
      case unknown             => throw iozhik.DecodingError(s"Unknown type for PassportElementError: $unknown")
    }
  } yield value

  implicit lazy val passportelementerrorfilesEncoder: Encoder[PassportElementErrorFiles] =
    (x: PassportElementErrorFiles) => {
      Json.fromFields(
        List(
          "type"        -> x.`type`.asJson,
          "file_hashes" -> x.fileHashes.asJson,
          "message"     -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorfilesDecoder: Decoder[PassportElementErrorFiles] =
    Decoder.instance { h =>
      for {
        _type       <- h.get[String]("type")
        _fileHashes <- h.getOrElse[List[String]]("file_hashes")(List.empty)
        _message    <- h.get[String]("message")
      } yield {
        PassportElementErrorFiles(`type` = _type, fileHashes = _fileHashes, message = _message)
      }
    }

  implicit lazy val passportelementerrordatafieldEncoder: Encoder[PassportElementErrorDataField] =
    (x: PassportElementErrorDataField) => {
      Json.fromFields(
        List(
          "type"       -> x.`type`.asJson,
          "field_name" -> x.fieldName.asJson,
          "data_hash"  -> x.dataHash.asJson,
          "message"    -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrordatafieldDecoder: Decoder[PassportElementErrorDataField] =
    Decoder.instance { h =>
      for {
        _type      <- h.get[String]("type")
        _fieldName <- h.get[String]("field_name")
        _dataHash  <- h.get[String]("data_hash")
        _message   <- h.get[String]("message")
      } yield {
        PassportElementErrorDataField(`type` = _type, fieldName = _fieldName, dataHash = _dataHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorreversesideEncoder: Encoder[PassportElementErrorReverseSide] =
    (x: PassportElementErrorReverseSide) => {
      Json.fromFields(
        List(
          "type"      -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message"   -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorreversesideDecoder: Decoder[PassportElementErrorReverseSide] =
    Decoder.instance { h =>
      for {
        _type     <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message  <- h.get[String]("message")
      } yield {
        PassportElementErrorReverseSide(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorselfieEncoder: Encoder[PassportElementErrorSelfie] =
    (x: PassportElementErrorSelfie) => {
      Json.fromFields(
        List(
          "type"      -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message"   -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorselfieDecoder: Decoder[PassportElementErrorSelfie] =
    Decoder.instance { h =>
      for {
        _type     <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message  <- h.get[String]("message")
      } yield {
        PassportElementErrorSelfie(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorfrontsideEncoder: Encoder[PassportElementErrorFrontSide] =
    (x: PassportElementErrorFrontSide) => {
      Json.fromFields(
        List(
          "type"      -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message"   -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorfrontsideDecoder: Decoder[PassportElementErrorFrontSide] =
    Decoder.instance { h =>
      for {
        _type     <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message  <- h.get[String]("message")
      } yield {
        PassportElementErrorFrontSide(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorfileEncoder: Encoder[PassportElementErrorFile] =
    (x: PassportElementErrorFile) => {
      Json.fromFields(
        List(
          "type"      -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message"   -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorfileDecoder: Decoder[PassportElementErrorFile] =
    Decoder.instance { h =>
      for {
        _type     <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message  <- h.get[String]("message")
      } yield {
        PassportElementErrorFile(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrorunspecifiedEncoder: Encoder[PassportElementErrorUnspecified] =
    (x: PassportElementErrorUnspecified) => {
      Json.fromFields(
        List(
          "type"         -> x.`type`.asJson,
          "element_hash" -> x.elementHash.asJson,
          "message"      -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrorunspecifiedDecoder: Decoder[PassportElementErrorUnspecified] =
    Decoder.instance { h =>
      for {
        _type        <- h.get[String]("type")
        _elementHash <- h.get[String]("element_hash")
        _message     <- h.get[String]("message")
      } yield {
        PassportElementErrorUnspecified(`type` = _type, elementHash = _elementHash, message = _message)
      }
    }

  implicit lazy val passportelementerrortranslationfileEncoder: Encoder[PassportElementErrorTranslationFile] =
    (x: PassportElementErrorTranslationFile) => {
      Json.fromFields(
        List(
          "type"      -> x.`type`.asJson,
          "file_hash" -> x.fileHash.asJson,
          "message"   -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrortranslationfileDecoder: Decoder[PassportElementErrorTranslationFile] =
    Decoder.instance { h =>
      for {
        _type     <- h.get[String]("type")
        _fileHash <- h.get[String]("file_hash")
        _message  <- h.get[String]("message")
      } yield {
        PassportElementErrorTranslationFile(`type` = _type, fileHash = _fileHash, message = _message)
      }
    }

  implicit lazy val passportelementerrortranslationfilesEncoder: Encoder[PassportElementErrorTranslationFiles] =
    (x: PassportElementErrorTranslationFiles) => {
      Json.fromFields(
        List(
          "type"        -> x.`type`.asJson,
          "file_hashes" -> x.fileHashes.asJson,
          "message"     -> x.message.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportelementerrortranslationfilesDecoder: Decoder[PassportElementErrorTranslationFiles] =
    Decoder.instance { h =>
      for {
        _type       <- h.get[String]("type")
        _fileHashes <- h.getOrElse[List[String]]("file_hashes")(List.empty)
        _message    <- h.get[String]("message")
      } yield {
        PassportElementErrorTranslationFiles(`type` = _type, fileHashes = _fileHashes, message = _message)
      }
    }

  implicit lazy val reactiontypeEncoder: Encoder[ReactionType] = {
    case custom_emoji: ReactionTypeCustomEmoji =>
      custom_emoji.asJson.mapObject(_.add("type", Json.fromString("custom_emoji")))
    case paid: ReactionTypePaid.type => paid.asJson.mapObject(_.add("type", Json.fromString("paid")))
    case emoji: ReactionTypeEmoji    => emoji.asJson.mapObject(_.add("type", Json.fromString("emoji")))
  }

  implicit lazy val reactiontypeDecoder: Decoder[iozhik.OpenEnum[ReactionType]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "custom_emoji" => Decoder[ReactionTypeCustomEmoji].map(iozhik.OpenEnum.Known(_))
      case "paid"         => Decoder[ReactionTypePaid.type].map(iozhik.OpenEnum.Known(_))
      case "emoji"        => Decoder[ReactionTypeEmoji].map(iozhik.OpenEnum.Known(_))
      case unknown        => Decoder.const(iozhik.OpenEnum.Unknown[ReactionType](unknown))
    }
  } yield value

  implicit lazy val reactiontypepaidEncoder: Encoder[ReactionTypePaid.type] = (_: ReactionTypePaid.type) => ().asJson
  implicit lazy val reactiontypepaidDecoder: Decoder[ReactionTypePaid.type] = (_: HCursor) => Right(ReactionTypePaid)

  implicit lazy val reactiontypeemojiEncoder: Encoder[ReactionTypeEmoji] =
    (x: ReactionTypeEmoji) => {
      Json.fromFields(
        List(
          "emoji" -> x.emoji.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val reactiontypeemojiDecoder: Decoder[ReactionTypeEmoji] =
    Decoder.instance { h =>
      for {
        _emoji <- h.get[String]("emoji")
      } yield {
        ReactionTypeEmoji(emoji = _emoji)
      }
    }

  implicit lazy val reactiontypecustomemojiEncoder: Encoder[ReactionTypeCustomEmoji] =
    (x: ReactionTypeCustomEmoji) => {
      Json.fromFields(
        List(
          "custom_emoji_id" -> x.customEmojiId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val reactiontypecustomemojiDecoder: Decoder[ReactionTypeCustomEmoji] =
    Decoder.instance { h =>
      for {
        _customEmojiId <- h.get[String]("custom_emoji_id")
      } yield {
        ReactionTypeCustomEmoji(customEmojiId = _customEmojiId)
      }
    }

  implicit lazy val revenuewithdrawalstateEncoder: Encoder[RevenueWithdrawalState] = {
    case failed: RevenueWithdrawalStateFailed.type => failed.asJson.mapObject(_.add("type", Json.fromString("failed")))
    case succeeded: RevenueWithdrawalStateSucceeded =>
      succeeded.asJson.mapObject(_.add("type", Json.fromString("succeeded")))
    case pending: RevenueWithdrawalStatePending.type =>
      pending.asJson.mapObject(_.add("type", Json.fromString("pending")))
  }

  implicit lazy val revenuewithdrawalstateDecoder: Decoder[iozhik.OpenEnum[RevenueWithdrawalState]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "failed"    => Decoder[RevenueWithdrawalStateFailed.type].map(iozhik.OpenEnum.Known(_))
      case "succeeded" => Decoder[RevenueWithdrawalStateSucceeded].map(iozhik.OpenEnum.Known(_))
      case "pending"   => Decoder[RevenueWithdrawalStatePending.type].map(iozhik.OpenEnum.Known(_))
      case unknown     => Decoder.const(iozhik.OpenEnum.Unknown[RevenueWithdrawalState](unknown))
    }
  } yield value

  implicit lazy val revenuewithdrawalstatependingEncoder: Encoder[RevenueWithdrawalStatePending.type] =
    (_: RevenueWithdrawalStatePending.type) => ().asJson

  implicit lazy val revenuewithdrawalstatependingDecoder: Decoder[RevenueWithdrawalStatePending.type] = (_: HCursor) =>
    Right(RevenueWithdrawalStatePending)

  implicit lazy val revenuewithdrawalstatesucceededEncoder: Encoder[RevenueWithdrawalStateSucceeded] =
    (x: RevenueWithdrawalStateSucceeded) => {
      Json.fromFields(
        List(
          "date" -> x.date.asJson,
          "url"  -> x.url.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val revenuewithdrawalstatesucceededDecoder: Decoder[RevenueWithdrawalStateSucceeded] =
    Decoder.instance { h =>
      for {
        _date <- h.get[Int]("date")
        _url  <- h.get[String]("url")
      } yield {
        RevenueWithdrawalStateSucceeded(date = _date, url = _url)
      }
    }

  implicit lazy val revenuewithdrawalstatefailedEncoder: Encoder[RevenueWithdrawalStateFailed.type] =
    (_: RevenueWithdrawalStateFailed.type) => ().asJson

  implicit lazy val revenuewithdrawalstatefailedDecoder: Decoder[RevenueWithdrawalStateFailed.type] = (_: HCursor) =>
    Right(RevenueWithdrawalStateFailed)

  implicit lazy val transactionpartnerEncoder: Encoder[TransactionPartner] = {
    case fragment: TransactionPartnerFragment => fragment.asJson.mapObject(_.add("type", Json.fromString("fragment")))
    case telegram_ads: TransactionPartnerTelegramAds.type =>
      telegram_ads.asJson.mapObject(_.add("type", Json.fromString("telegram_ads")))
    case telegram_api: TransactionPartnerTelegramApi =>
      telegram_api.asJson.mapObject(_.add("type", Json.fromString("telegram_api")))
    case affiliate_program: TransactionPartnerAffiliateProgram =>
      affiliate_program.asJson.mapObject(_.add("type", Json.fromString("affiliate_program")))
    case user: TransactionPartnerUser        => user.asJson.mapObject(_.add("type", Json.fromString("user")))
    case other: TransactionPartnerOther.type => other.asJson.mapObject(_.add("type", Json.fromString("other")))
  }

  implicit lazy val transactionpartnerDecoder: Decoder[iozhik.OpenEnum[TransactionPartner]] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "fragment"          => Decoder[TransactionPartnerFragment].map(iozhik.OpenEnum.Known(_))
      case "telegram_ads"      => Decoder[TransactionPartnerTelegramAds.type].map(iozhik.OpenEnum.Known(_))
      case "telegram_api"      => Decoder[TransactionPartnerTelegramApi].map(iozhik.OpenEnum.Known(_))
      case "affiliate_program" => Decoder[TransactionPartnerAffiliateProgram].map(iozhik.OpenEnum.Known(_))
      case "user"              => Decoder[TransactionPartnerUser].map(iozhik.OpenEnum.Known(_))
      case "other"             => Decoder[TransactionPartnerOther.type].map(iozhik.OpenEnum.Known(_))
      case unknown             => Decoder.const(iozhik.OpenEnum.Unknown[TransactionPartner](unknown))
    }
  } yield value

  implicit lazy val transactionpartneraffiliateprogramEncoder: Encoder[TransactionPartnerAffiliateProgram] =
    (x: TransactionPartnerAffiliateProgram) => {
      Json.fromFields(
        List(
          "sponsor_user"         -> x.sponsorUser.asJson,
          "commission_per_mille" -> x.commissionPerMille.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val transactionpartneraffiliateprogramDecoder: Decoder[TransactionPartnerAffiliateProgram] =
    Decoder.instance { h =>
      for {
        _sponsorUser        <- h.get[Option[User]]("sponsor_user")
        _commissionPerMille <- h.get[Int]("commission_per_mille")
      } yield {
        TransactionPartnerAffiliateProgram(sponsorUser = _sponsorUser, commissionPerMille = _commissionPerMille)
      }
    }

  implicit lazy val transactionpartnerotherEncoder: Encoder[TransactionPartnerOther.type] =
    (_: TransactionPartnerOther.type) => ().asJson

  implicit lazy val transactionpartnerotherDecoder: Decoder[TransactionPartnerOther.type] = (_: HCursor) =>
    Right(TransactionPartnerOther)

  implicit lazy val transactionpartnertelegramadsEncoder: Encoder[TransactionPartnerTelegramAds.type] =
    (_: TransactionPartnerTelegramAds.type) => ().asJson

  implicit lazy val transactionpartnertelegramadsDecoder: Decoder[TransactionPartnerTelegramAds.type] = (_: HCursor) =>
    Right(TransactionPartnerTelegramAds)

  implicit lazy val transactionpartneruserEncoder: Encoder[TransactionPartnerUser] =
    (x: TransactionPartnerUser) => {
      Json.fromFields(
        List(
          "user"                -> x.user.asJson,
          "affiliate"           -> x.affiliate.asJson,
          "invoice_payload"     -> x.invoicePayload.asJson,
          "subscription_period" -> x.subscriptionPeriod.asJson,
          "paid_media"          -> x.paidMedia.asJson,
          "paid_media_payload"  -> x.paidMediaPayload.asJson,
          "gift"                -> x.gift.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val transactionpartneruserDecoder: Decoder[TransactionPartnerUser] =
    Decoder.instance { h =>
      for {
        _user               <- h.get[User]("user")
        _affiliate          <- h.get[Option[AffiliateInfo]]("affiliate")
        _invoicePayload     <- h.get[Option[String]]("invoice_payload")
        _subscriptionPeriod <- h.get[Option[Int]]("subscription_period")
        _paidMedia          <- h.getOrElse[List[iozhik.OpenEnum[PaidMedia]]]("paid_media")(List.empty)
        _paidMediaPayload   <- h.get[Option[String]]("paid_media_payload")
        _gift               <- h.get[Option[Gift]]("gift")
      } yield {
        TransactionPartnerUser(
          user = _user,
          affiliate = _affiliate,
          invoicePayload = _invoicePayload,
          subscriptionPeriod = _subscriptionPeriod,
          paidMedia = _paidMedia,
          paidMediaPayload = _paidMediaPayload,
          gift = _gift
        )
      }
    }

  implicit lazy val transactionpartnertelegramapiEncoder: Encoder[TransactionPartnerTelegramApi] =
    (x: TransactionPartnerTelegramApi) => {
      Json.fromFields(
        List(
          "request_count" -> x.requestCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val transactionpartnertelegramapiDecoder: Decoder[TransactionPartnerTelegramApi] =
    Decoder.instance { h =>
      for {
        _requestCount <- h.get[Int]("request_count")
      } yield {
        TransactionPartnerTelegramApi(requestCount = _requestCount)
      }
    }

  implicit lazy val transactionpartnerfragmentEncoder: Encoder[TransactionPartnerFragment] =
    (x: TransactionPartnerFragment) => {
      Json.fromFields(
        List(
          "withdrawal_state" -> x.withdrawalState.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val transactionpartnerfragmentDecoder: Decoder[TransactionPartnerFragment] =
    Decoder.instance { h =>
      for {
        _withdrawalState <- h.get[Option[iozhik.OpenEnum[RevenueWithdrawalState]]]("withdrawal_state")
      } yield {
        TransactionPartnerFragment(withdrawalState = _withdrawalState)
      }
    }

  implicit lazy val affiliateinfoEncoder: Encoder[AffiliateInfo] =
    (x: AffiliateInfo) => {
      Json.fromFields(
        List(
          "affiliate_user"       -> x.affiliateUser.asJson,
          "affiliate_chat"       -> x.affiliateChat.asJson,
          "commission_per_mille" -> x.commissionPerMille.asJson,
          "amount"               -> x.amount.asJson,
          "nanostar_amount"      -> x.nanostarAmount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val affiliateinfoDecoder: Decoder[AffiliateInfo] =
    Decoder.instance { h =>
      for {
        _affiliateUser      <- h.get[Option[User]]("affiliate_user")
        _affiliateChat      <- h.get[Option[Chat]]("affiliate_chat")
        _commissionPerMille <- h.get[Int]("commission_per_mille")
        _amount             <- h.get[Int]("amount")
        _nanostarAmount     <- h.get[Option[Int]]("nanostar_amount")
      } yield {
        AffiliateInfo(
          affiliateUser = _affiliateUser,
          affiliateChat = _affiliateChat,
          commissionPerMille = _commissionPerMille,
          amount = _amount,
          nanostarAmount = _nanostarAmount
        )
      }
    }

  implicit lazy val animationEncoder: Encoder[Animation] =
    (x: Animation) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width"          -> x.width.asJson,
          "height"         -> x.height.asJson,
          "duration"       -> x.duration.asJson,
          "thumbnail"      -> x.thumbnail.asJson,
          "file_name"      -> x.fileName.asJson,
          "mime_type"      -> x.mimeType.asJson,
          "file_size"      -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val animationDecoder: Decoder[Animation] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _width        <- h.get[Int]("width")
        _height       <- h.get[Int]("height")
        _duration     <- h.get[Int]("duration")
        _thumbnail    <- h.get[Option[PhotoSize]]("thumbnail")
        _fileName     <- h.get[Option[String]]("file_name")
        _mimeType     <- h.get[Option[String]]("mime_type")
        _fileSize     <- h.get[Option[Long]]("file_size")
      } yield {
        Animation(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          width = _width,
          height = _height,
          duration = _duration,
          thumbnail = _thumbnail,
          fileName = _fileName,
          mimeType = _mimeType,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val audioEncoder: Encoder[Audio] =
    (x: Audio) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "duration"       -> x.duration.asJson,
          "performer"      -> x.performer.asJson,
          "title"          -> x.title.asJson,
          "file_name"      -> x.fileName.asJson,
          "mime_type"      -> x.mimeType.asJson,
          "file_size"      -> x.fileSize.asJson,
          "thumbnail"      -> x.thumbnail.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val audioDecoder: Decoder[Audio] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _duration     <- h.get[Int]("duration")
        _performer    <- h.get[Option[String]]("performer")
        _title        <- h.get[Option[String]]("title")
        _fileName     <- h.get[Option[String]]("file_name")
        _mimeType     <- h.get[Option[String]]("mime_type")
        _fileSize     <- h.get[Option[Long]]("file_size")
        _thumbnail    <- h.get[Option[PhotoSize]]("thumbnail")
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
          thumbnail = _thumbnail
        )
      }
    }

  implicit lazy val birthdateEncoder: Encoder[Birthdate] =
    (x: Birthdate) => {
      Json.fromFields(
        List(
          "day"   -> x.day.asJson,
          "month" -> x.month.asJson,
          "year"  -> x.year.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val birthdateDecoder: Decoder[Birthdate] =
    Decoder.instance { h =>
      for {
        _day   <- h.get[Int]("day")
        _month <- h.get[Int]("month")
        _year  <- h.get[Option[Int]]("year")
      } yield {
        Birthdate(day = _day, month = _month, year = _year)
      }
    }

  implicit lazy val botcommandEncoder: Encoder[BotCommand] =
    (x: BotCommand) => {
      Json.fromFields(
        List(
          "command"     -> x.command.asJson,
          "description" -> x.description.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botcommandDecoder: Decoder[BotCommand] =
    Decoder.instance { h =>
      for {
        _command     <- h.get[String]("command")
        _description <- h.get[String]("description")
      } yield {
        BotCommand(command = _command, description = _description)
      }
    }

  implicit lazy val botdescriptionEncoder: Encoder[BotDescription] =
    (x: BotDescription) => {
      Json.fromFields(
        List(
          "description" -> x.description.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botdescriptionDecoder: Decoder[BotDescription] =
    Decoder.instance { h =>
      for {
        _description <- h.get[String]("description")
      } yield {
        BotDescription(description = _description)
      }
    }

  implicit lazy val botnameEncoder: Encoder[BotName] =
    (x: BotName) => {
      Json.fromFields(
        List(
          "name" -> x.name.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botnameDecoder: Decoder[BotName] =
    Decoder.instance { h =>
      for {
        _name <- h.get[String]("name")
      } yield {
        BotName(name = _name)
      }
    }

  implicit lazy val botshortdescriptionEncoder: Encoder[BotShortDescription] =
    (x: BotShortDescription) => {
      Json.fromFields(
        List(
          "short_description" -> x.shortDescription.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val botshortdescriptionDecoder: Decoder[BotShortDescription] =
    Decoder.instance { h =>
      for {
        _shortDescription <- h.get[String]("short_description")
      } yield {
        BotShortDescription(shortDescription = _shortDescription)
      }
    }

  implicit lazy val businessconnectionEncoder: Encoder[BusinessConnection] =
    (x: BusinessConnection) => {
      Json.fromFields(
        List(
          "id"           -> x.id.asJson,
          "user"         -> x.user.asJson,
          "user_chat_id" -> x.userChatId.asJson,
          "date"         -> x.date.asJson,
          "can_reply"    -> x.canReply.asJson,
          "is_enabled"   -> x.isEnabled.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val businessconnectionDecoder: Decoder[BusinessConnection] =
    Decoder.instance { h =>
      for {
        _id         <- h.get[String]("id")
        _user       <- h.get[User]("user")
        _userChatId <- h.get[Long]("user_chat_id")
        _date       <- h.get[Int]("date")
        _canReply   <- h.get[Boolean]("can_reply")
        _isEnabled  <- h.get[Boolean]("is_enabled")
      } yield {
        BusinessConnection(
          id = _id,
          user = _user,
          userChatId = _userChatId,
          date = _date,
          canReply = _canReply,
          isEnabled = _isEnabled
        )
      }
    }

  implicit lazy val businessintroEncoder: Encoder[BusinessIntro] =
    (x: BusinessIntro) => {
      Json.fromFields(
        List(
          "title"   -> x.title.asJson,
          "message" -> x.message.asJson,
          "sticker" -> x.sticker.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val businessintroDecoder: Decoder[BusinessIntro] =
    Decoder.instance { h =>
      for {
        _title   <- h.get[Option[String]]("title")
        _message <- h.get[Option[String]]("message")
        _sticker <- h.get[Option[Sticker]]("sticker")
      } yield {
        BusinessIntro(title = _title, message = _message, sticker = _sticker)
      }
    }

  implicit lazy val businesslocationEncoder: Encoder[BusinessLocation] =
    (x: BusinessLocation) => {
      Json.fromFields(
        List(
          "address"  -> x.address.asJson,
          "location" -> x.location.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val businesslocationDecoder: Decoder[BusinessLocation] =
    Decoder.instance { h =>
      for {
        _address  <- h.get[String]("address")
        _location <- h.get[Option[Location]]("location")
      } yield {
        BusinessLocation(address = _address, location = _location)
      }
    }

  implicit lazy val businessmessagesdeletedEncoder: Encoder[BusinessMessagesDeleted] =
    (x: BusinessMessagesDeleted) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat"                   -> x.chat.asJson,
          "message_ids"            -> x.messageIds.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val businessmessagesdeletedDecoder: Decoder[BusinessMessagesDeleted] =
    Decoder.instance { h =>
      for {
        _businessConnectionId <- h.get[String]("business_connection_id")
        _chat                 <- h.get[Chat]("chat")
        _messageIds           <- h.getOrElse[List[Int]]("message_ids")(List.empty)
      } yield {
        BusinessMessagesDeleted(businessConnectionId = _businessConnectionId, chat = _chat, messageIds = _messageIds)
      }
    }

  implicit lazy val businessopeninghoursEncoder: Encoder[BusinessOpeningHours] =
    (x: BusinessOpeningHours) => {
      Json.fromFields(
        List(
          "time_zone_name" -> x.timeZoneName.asJson,
          "opening_hours"  -> x.openingHours.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val businessopeninghoursDecoder: Decoder[BusinessOpeningHours] =
    Decoder.instance { h =>
      for {
        _timeZoneName <- h.get[String]("time_zone_name")
        _openingHours <- h.getOrElse[List[BusinessOpeningHoursInterval]]("opening_hours")(List.empty)
      } yield {
        BusinessOpeningHours(timeZoneName = _timeZoneName, openingHours = _openingHours)
      }
    }

  implicit lazy val businessopeninghoursintervalEncoder: Encoder[BusinessOpeningHoursInterval] =
    (x: BusinessOpeningHoursInterval) => {
      Json.fromFields(
        List(
          "opening_minute" -> x.openingMinute.asJson,
          "closing_minute" -> x.closingMinute.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val businessopeninghoursintervalDecoder: Decoder[BusinessOpeningHoursInterval] =
    Decoder.instance { h =>
      for {
        _openingMinute <- h.get[Int]("opening_minute")
        _closingMinute <- h.get[Int]("closing_minute")
      } yield {
        BusinessOpeningHoursInterval(openingMinute = _openingMinute, closingMinute = _closingMinute)
      }
    }

  implicit lazy val callbackgameEncoder: Encoder[CallbackGame.type] = (_: CallbackGame.type) => ().asJson
  implicit lazy val callbackgameDecoder: Decoder[CallbackGame.type] = (_: HCursor) => Right(CallbackGame)

  implicit lazy val callbackqueryEncoder: Encoder[CallbackQuery] =
    (x: CallbackQuery) => {
      Json.fromFields(
        List(
          "id"                -> x.id.asJson,
          "from"              -> x.from.asJson,
          "message"           -> x.message.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "chat_instance"     -> x.chatInstance.asJson,
          "data"              -> x.data.asJson,
          "game_short_name"   -> x.gameShortName.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val callbackqueryDecoder: Decoder[CallbackQuery] =
    Decoder.instance { h =>
      for {
        _id              <- h.get[String]("id")
        _from            <- h.get[User]("from")
        _message         <- h.get[Option[MaybeInaccessibleMessage]]("message")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
        _chatInstance    <- h.get[String]("chat_instance")
        _data            <- h.get[Option[String]]("data")
        _gameShortName   <- h.get[Option[String]]("game_short_name")
      } yield {
        CallbackQuery(
          id = _id,
          from = _from,
          message = _message,
          inlineMessageId = _inlineMessageId,
          chatInstance = _chatInstance,
          data = _data,
          gameShortName = _gameShortName
        )
      }
    }

  implicit lazy val chatEncoder: Encoder[Chat] =
    (x: Chat) => {
      Json.fromFields(
        List(
          "id"         -> x.id.asJson,
          "type"       -> x.`type`.asJson,
          "title"      -> x.title.asJson,
          "username"   -> x.username.asJson,
          "first_name" -> x.firstName.asJson,
          "last_name"  -> x.lastName.asJson,
          "is_forum"   -> x.isForum.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatDecoder: Decoder[Chat] =
    Decoder.instance { h =>
      for {
        _id        <- h.get[Long]("id")
        _type      <- h.get[String]("type")
        _title     <- h.get[Option[String]]("title")
        _username  <- h.get[Option[String]]("username")
        _firstName <- h.get[Option[String]]("first_name")
        _lastName  <- h.get[Option[String]]("last_name")
        _isForum   <- h.get[Option[Boolean]]("is_forum")
      } yield {
        Chat(
          id = _id,
          `type` = _type,
          title = _title,
          username = _username,
          firstName = _firstName,
          lastName = _lastName,
          isForum = _isForum
        )
      }
    }

  implicit lazy val chatadministratorrightsEncoder: Encoder[ChatAdministratorRights] =
    (x: ChatAdministratorRights) => {
      Json.fromFields(
        List(
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
          "can_manage_topics"      -> x.canManageTopics.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatadministratorrightsDecoder: Decoder[ChatAdministratorRights] =
    Decoder.instance { h =>
      for {
        _isAnonymous         <- h.get[Boolean]("is_anonymous")
        _canManageChat       <- h.get[Boolean]("can_manage_chat")
        _canDeleteMessages   <- h.get[Boolean]("can_delete_messages")
        _canManageVideoChats <- h.get[Boolean]("can_manage_video_chats")
        _canRestrictMembers  <- h.get[Boolean]("can_restrict_members")
        _canPromoteMembers   <- h.get[Boolean]("can_promote_members")
        _canChangeInfo       <- h.get[Boolean]("can_change_info")
        _canInviteUsers      <- h.get[Boolean]("can_invite_users")
        _canPostStories      <- h.get[Boolean]("can_post_stories")
        _canEditStories      <- h.get[Boolean]("can_edit_stories")
        _canDeleteStories    <- h.get[Boolean]("can_delete_stories")
        _canPostMessages     <- h.get[Option[Boolean]]("can_post_messages")
        _canEditMessages     <- h.get[Option[Boolean]]("can_edit_messages")
        _canPinMessages      <- h.get[Option[Boolean]]("can_pin_messages")
        _canManageTopics     <- h.get[Option[Boolean]]("can_manage_topics")
      } yield {
        ChatAdministratorRights(
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

  implicit lazy val chatbackgroundEncoder: Encoder[ChatBackground] =
    (x: ChatBackground) => {
      Json.fromFields(
        List(
          "type" -> x.`type`.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatbackgroundDecoder: Decoder[ChatBackground] =
    Decoder.instance { h =>
      for {
        _type <- h.get[iozhik.OpenEnum[BackgroundType]]("type")
      } yield {
        ChatBackground(`type` = _type)
      }
    }

  implicit lazy val chatboostEncoder: Encoder[ChatBoost] =
    (x: ChatBoost) => {
      Json.fromFields(
        List(
          "boost_id"        -> x.boostId.asJson,
          "add_date"        -> x.addDate.asJson,
          "expiration_date" -> x.expirationDate.asJson,
          "source"          -> x.source.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatboostDecoder: Decoder[ChatBoost] =
    Decoder.instance { h =>
      for {
        _boostId        <- h.get[String]("boost_id")
        _addDate        <- h.get[Int]("add_date")
        _expirationDate <- h.get[Int]("expiration_date")
        _source         <- h.get[iozhik.OpenEnum[ChatBoostSource]]("source")
      } yield {
        ChatBoost(boostId = _boostId, addDate = _addDate, expirationDate = _expirationDate, source = _source)
      }
    }

  implicit lazy val chatboostaddedEncoder: Encoder[ChatBoostAdded] =
    (x: ChatBoostAdded) => {
      Json.fromFields(
        List(
          "boost_count" -> x.boostCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatboostaddedDecoder: Decoder[ChatBoostAdded] =
    Decoder.instance { h =>
      for {
        _boostCount <- h.get[Int]("boost_count")
      } yield {
        ChatBoostAdded(boostCount = _boostCount)
      }
    }

  implicit lazy val chatboostremovedEncoder: Encoder[ChatBoostRemoved] =
    (x: ChatBoostRemoved) => {
      Json.fromFields(
        List(
          "chat"        -> x.chat.asJson,
          "boost_id"    -> x.boostId.asJson,
          "remove_date" -> x.removeDate.asJson,
          "source"      -> x.source.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatboostremovedDecoder: Decoder[ChatBoostRemoved] =
    Decoder.instance { h =>
      for {
        _chat       <- h.get[Chat]("chat")
        _boostId    <- h.get[String]("boost_id")
        _removeDate <- h.get[Int]("remove_date")
        _source     <- h.get[iozhik.OpenEnum[ChatBoostSource]]("source")
      } yield {
        ChatBoostRemoved(chat = _chat, boostId = _boostId, removeDate = _removeDate, source = _source)
      }
    }

  implicit lazy val chatboostupdatedEncoder: Encoder[ChatBoostUpdated] =
    (x: ChatBoostUpdated) => {
      Json.fromFields(
        List(
          "chat"  -> x.chat.asJson,
          "boost" -> x.boost.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatboostupdatedDecoder: Decoder[ChatBoostUpdated] =
    Decoder.instance { h =>
      for {
        _chat  <- h.get[Chat]("chat")
        _boost <- h.get[ChatBoost]("boost")
      } yield {
        ChatBoostUpdated(chat = _chat, boost = _boost)
      }
    }

  implicit lazy val chatfullinfoEncoder: Encoder[ChatFullInfo] =
    (x: ChatFullInfo) => {
      Json.fromFields(
        List(
          "id"                                      -> x.id.asJson,
          "type"                                    -> x.`type`.asJson,
          "title"                                   -> x.title.asJson,
          "username"                                -> x.username.asJson,
          "first_name"                              -> x.firstName.asJson,
          "last_name"                               -> x.lastName.asJson,
          "is_forum"                                -> x.isForum.asJson,
          "accent_color_id"                         -> x.accentColorId.asJson,
          "max_reaction_count"                      -> x.maxReactionCount.asJson,
          "photo"                                   -> x.photo.asJson,
          "active_usernames"                        -> x.activeUsernames.asJson,
          "birthdate"                               -> x.birthdate.asJson,
          "business_intro"                          -> x.businessIntro.asJson,
          "business_location"                       -> x.businessLocation.asJson,
          "business_opening_hours"                  -> x.businessOpeningHours.asJson,
          "personal_chat"                           -> x.personalChat.asJson,
          "available_reactions"                     -> x.availableReactions.asJson,
          "background_custom_emoji_id"              -> x.backgroundCustomEmojiId.asJson,
          "profile_accent_color_id"                 -> x.profileAccentColorId.asJson,
          "profile_background_custom_emoji_id"      -> x.profileBackgroundCustomEmojiId.asJson,
          "emoji_status_custom_emoji_id"            -> x.emojiStatusCustomEmojiId.asJson,
          "emoji_status_expiration_date"            -> x.emojiStatusExpirationDate.asJson,
          "bio"                                     -> x.bio.asJson,
          "has_private_forwards"                    -> x.hasPrivateForwards.asJson,
          "has_restricted_voice_and_video_messages" -> x.hasRestrictedVoiceAndVideoMessages.asJson,
          "join_to_send_messages"                   -> x.joinToSendMessages.asJson,
          "join_by_request"                         -> x.joinByRequest.asJson,
          "description"                             -> x.description.asJson,
          "invite_link"                             -> x.inviteLink.asJson,
          "pinned_message"                          -> x.pinnedMessage.asJson,
          "permissions"                             -> x.permissions.asJson,
          "can_send_paid_media"                     -> x.canSendPaidMedia.asJson,
          "slow_mode_delay"                         -> x.slowModeDelay.asJson,
          "unrestrict_boost_count"                  -> x.unrestrictBoostCount.asJson,
          "message_auto_delete_time"                -> x.messageAutoDeleteTime.asJson,
          "has_aggressive_anti_spam_enabled"        -> x.hasAggressiveAntiSpamEnabled.asJson,
          "has_hidden_members"                      -> x.hasHiddenMembers.asJson,
          "has_protected_content"                   -> x.hasProtectedContent.asJson,
          "has_visible_history"                     -> x.hasVisibleHistory.asJson,
          "sticker_set_name"                        -> x.stickerSetName.asJson,
          "can_set_sticker_set"                     -> x.canSetStickerSet.asJson,
          "custom_emoji_sticker_set_name"           -> x.customEmojiStickerSetName.asJson,
          "linked_chat_id"                          -> x.linkedChatId.asJson,
          "location"                                -> x.location.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatfullinfoDecoder: Decoder[ChatFullInfo] =
    Decoder.instance { h =>
      for {
        _id                      <- h.get[Long]("id")
        _type                    <- h.get[String]("type")
        _title                   <- h.get[Option[String]]("title")
        _username                <- h.get[Option[String]]("username")
        _firstName               <- h.get[Option[String]]("first_name")
        _lastName                <- h.get[Option[String]]("last_name")
        _isForum                 <- h.get[Option[Boolean]]("is_forum")
        _accentColorId           <- h.get[Int]("accent_color_id")
        _maxReactionCount        <- h.get[Int]("max_reaction_count")
        _photo                   <- h.get[Option[ChatPhoto]]("photo")
        _activeUsernames         <- h.getOrElse[List[String]]("active_usernames")(List.empty)
        _birthdate               <- h.get[Option[Birthdate]]("birthdate")
        _businessIntro           <- h.get[Option[BusinessIntro]]("business_intro")
        _businessLocation        <- h.get[Option[BusinessLocation]]("business_location")
        _businessOpeningHours    <- h.get[Option[BusinessOpeningHours]]("business_opening_hours")
        _personalChat            <- h.get[Option[Chat]]("personal_chat")
        _availableReactions      <- h.getOrElse[List[iozhik.OpenEnum[ReactionType]]]("available_reactions")(List.empty)
        _backgroundCustomEmojiId <- h.get[Option[String]]("background_custom_emoji_id")
        _profileAccentColorId    <- h.get[Option[Int]]("profile_accent_color_id")
        _profileBackgroundCustomEmojiId     <- h.get[Option[String]]("profile_background_custom_emoji_id")
        _emojiStatusCustomEmojiId           <- h.get[Option[String]]("emoji_status_custom_emoji_id")
        _emojiStatusExpirationDate          <- h.get[Option[Int]]("emoji_status_expiration_date")
        _bio                                <- h.get[Option[String]]("bio")
        _hasPrivateForwards                 <- h.get[Option[Boolean]]("has_private_forwards")
        _hasRestrictedVoiceAndVideoMessages <- h.get[Option[Boolean]]("has_restricted_voice_and_video_messages")
        _joinToSendMessages                 <- h.get[Option[Boolean]]("join_to_send_messages")
        _joinByRequest                      <- h.get[Option[Boolean]]("join_by_request")
        _description                        <- h.get[Option[String]]("description")
        _inviteLink                         <- h.get[Option[String]]("invite_link")
        _pinnedMessage                      <- h.get[Option[Message]]("pinned_message")
        _permissions                        <- h.get[Option[ChatPermissions]]("permissions")
        _canSendPaidMedia                   <- h.get[Option[Boolean]]("can_send_paid_media")
        _slowModeDelay                      <- h.get[Option[Int]]("slow_mode_delay")
        _unrestrictBoostCount               <- h.get[Option[Int]]("unrestrict_boost_count")
        _messageAutoDeleteTime              <- h.get[Option[Int]]("message_auto_delete_time")
        _hasAggressiveAntiSpamEnabled       <- h.get[Option[Boolean]]("has_aggressive_anti_spam_enabled")
        _hasHiddenMembers                   <- h.get[Option[Boolean]]("has_hidden_members")
        _hasProtectedContent                <- h.get[Option[Boolean]]("has_protected_content")
        _hasVisibleHistory                  <- h.get[Option[Boolean]]("has_visible_history")
        _stickerSetName                     <- h.get[Option[String]]("sticker_set_name")
        _canSetStickerSet                   <- h.get[Option[Boolean]]("can_set_sticker_set")
        _customEmojiStickerSetName          <- h.get[Option[String]]("custom_emoji_sticker_set_name")
        _linkedChatId                       <- h.get[Option[Long]]("linked_chat_id")
        _location                           <- h.get[Option[ChatLocation]]("location")
      } yield {
        ChatFullInfo(
          id = _id,
          `type` = _type,
          title = _title,
          username = _username,
          firstName = _firstName,
          lastName = _lastName,
          isForum = _isForum,
          accentColorId = _accentColorId,
          maxReactionCount = _maxReactionCount,
          photo = _photo,
          activeUsernames = _activeUsernames,
          birthdate = _birthdate,
          businessIntro = _businessIntro,
          businessLocation = _businessLocation,
          businessOpeningHours = _businessOpeningHours,
          personalChat = _personalChat,
          availableReactions = _availableReactions,
          backgroundCustomEmojiId = _backgroundCustomEmojiId,
          profileAccentColorId = _profileAccentColorId,
          profileBackgroundCustomEmojiId = _profileBackgroundCustomEmojiId,
          emojiStatusCustomEmojiId = _emojiStatusCustomEmojiId,
          emojiStatusExpirationDate = _emojiStatusExpirationDate,
          bio = _bio,
          hasPrivateForwards = _hasPrivateForwards,
          hasRestrictedVoiceAndVideoMessages = _hasRestrictedVoiceAndVideoMessages,
          joinToSendMessages = _joinToSendMessages,
          joinByRequest = _joinByRequest,
          description = _description,
          inviteLink = _inviteLink,
          pinnedMessage = _pinnedMessage,
          permissions = _permissions,
          canSendPaidMedia = _canSendPaidMedia,
          slowModeDelay = _slowModeDelay,
          unrestrictBoostCount = _unrestrictBoostCount,
          messageAutoDeleteTime = _messageAutoDeleteTime,
          hasAggressiveAntiSpamEnabled = _hasAggressiveAntiSpamEnabled,
          hasHiddenMembers = _hasHiddenMembers,
          hasProtectedContent = _hasProtectedContent,
          hasVisibleHistory = _hasVisibleHistory,
          stickerSetName = _stickerSetName,
          canSetStickerSet = _canSetStickerSet,
          customEmojiStickerSetName = _customEmojiStickerSetName,
          linkedChatId = _linkedChatId,
          location = _location
        )
      }
    }

  implicit lazy val chatinvitelinkEncoder: Encoder[ChatInviteLink] =
    (x: ChatInviteLink) => {
      Json.fromFields(
        List(
          "invite_link"                -> x.inviteLink.asJson,
          "creator"                    -> x.creator.asJson,
          "creates_join_request"       -> x.createsJoinRequest.asJson,
          "is_primary"                 -> x.isPrimary.asJson,
          "is_revoked"                 -> x.isRevoked.asJson,
          "name"                       -> x.name.asJson,
          "expire_date"                -> x.expireDate.asJson,
          "member_limit"               -> x.memberLimit.asJson,
          "pending_join_request_count" -> x.pendingJoinRequestCount.asJson,
          "subscription_period"        -> x.subscriptionPeriod.asJson,
          "subscription_price"         -> x.subscriptionPrice.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatinvitelinkDecoder: Decoder[ChatInviteLink] =
    Decoder.instance { h =>
      for {
        _inviteLink              <- h.get[String]("invite_link")
        _creator                 <- h.get[User]("creator")
        _createsJoinRequest      <- h.get[Boolean]("creates_join_request")
        _isPrimary               <- h.get[Boolean]("is_primary")
        _isRevoked               <- h.get[Boolean]("is_revoked")
        _name                    <- h.get[Option[String]]("name")
        _expireDate              <- h.get[Option[Int]]("expire_date")
        _memberLimit             <- h.get[Option[Int]]("member_limit")
        _pendingJoinRequestCount <- h.get[Option[Int]]("pending_join_request_count")
        _subscriptionPeriod      <- h.get[Option[Int]]("subscription_period")
        _subscriptionPrice       <- h.get[Option[Int]]("subscription_price")
      } yield {
        ChatInviteLink(
          inviteLink = _inviteLink,
          creator = _creator,
          createsJoinRequest = _createsJoinRequest,
          isPrimary = _isPrimary,
          isRevoked = _isRevoked,
          name = _name,
          expireDate = _expireDate,
          memberLimit = _memberLimit,
          pendingJoinRequestCount = _pendingJoinRequestCount,
          subscriptionPeriod = _subscriptionPeriod,
          subscriptionPrice = _subscriptionPrice
        )
      }
    }

  implicit lazy val chatjoinrequestEncoder: Encoder[ChatJoinRequest] =
    (x: ChatJoinRequest) => {
      Json.fromFields(
        List(
          "chat"         -> x.chat.asJson,
          "from"         -> x.from.asJson,
          "user_chat_id" -> x.userChatId.asJson,
          "date"         -> x.date.asJson,
          "bio"          -> x.bio.asJson,
          "invite_link"  -> x.inviteLink.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatjoinrequestDecoder: Decoder[ChatJoinRequest] =
    Decoder.instance { h =>
      for {
        _chat       <- h.get[Chat]("chat")
        _from       <- h.get[User]("from")
        _userChatId <- h.get[Long]("user_chat_id")
        _date       <- h.get[Int]("date")
        _bio        <- h.get[Option[String]]("bio")
        _inviteLink <- h.get[Option[ChatInviteLink]]("invite_link")
      } yield {
        ChatJoinRequest(
          chat = _chat,
          from = _from,
          userChatId = _userChatId,
          date = _date,
          bio = _bio,
          inviteLink = _inviteLink
        )
      }
    }

  implicit lazy val chatlocationEncoder: Encoder[ChatLocation] =
    (x: ChatLocation) => {
      Json.fromFields(
        List(
          "location" -> x.location.asJson,
          "address"  -> x.address.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatlocationDecoder: Decoder[ChatLocation] =
    Decoder.instance { h =>
      for {
        _location <- h.get[Location]("location")
        _address  <- h.get[String]("address")
      } yield {
        ChatLocation(location = _location, address = _address)
      }
    }

  implicit lazy val chatmemberupdatedEncoder: Encoder[ChatMemberUpdated] =
    (x: ChatMemberUpdated) => {
      Json.fromFields(
        List(
          "chat"                        -> x.chat.asJson,
          "from"                        -> x.from.asJson,
          "date"                        -> x.date.asJson,
          "old_chat_member"             -> x.oldChatMember.asJson,
          "new_chat_member"             -> x.newChatMember.asJson,
          "invite_link"                 -> x.inviteLink.asJson,
          "via_join_request"            -> x.viaJoinRequest.asJson,
          "via_chat_folder_invite_link" -> x.viaChatFolderInviteLink.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberupdatedDecoder: Decoder[ChatMemberUpdated] =
    Decoder.instance { h =>
      for {
        _chat                    <- h.get[Chat]("chat")
        _from                    <- h.get[User]("from")
        _date                    <- h.get[Int]("date")
        _oldChatMember           <- h.get[iozhik.OpenEnum[ChatMember]]("old_chat_member")
        _newChatMember           <- h.get[iozhik.OpenEnum[ChatMember]]("new_chat_member")
        _inviteLink              <- h.get[Option[ChatInviteLink]]("invite_link")
        _viaJoinRequest          <- h.get[Option[Boolean]]("via_join_request")
        _viaChatFolderInviteLink <- h.get[Option[Boolean]]("via_chat_folder_invite_link")
      } yield {
        ChatMemberUpdated(
          chat = _chat,
          from = _from,
          date = _date,
          oldChatMember = _oldChatMember,
          newChatMember = _newChatMember,
          inviteLink = _inviteLink,
          viaJoinRequest = _viaJoinRequest,
          viaChatFolderInviteLink = _viaChatFolderInviteLink
        )
      }
    }

  implicit lazy val chatpermissionsEncoder: Encoder[ChatPermissions] =
    (x: ChatPermissions) => {
      Json.fromFields(
        List(
          "can_send_messages"         -> x.canSendMessages.asJson,
          "can_send_audios"           -> x.canSendAudios.asJson,
          "can_send_documents"        -> x.canSendDocuments.asJson,
          "can_send_photos"           -> x.canSendPhotos.asJson,
          "can_send_videos"           -> x.canSendVideos.asJson,
          "can_send_video_notes"      -> x.canSendVideoNotes.asJson,
          "can_send_voice_notes"      -> x.canSendVoiceNotes.asJson,
          "can_send_polls"            -> x.canSendPolls.asJson,
          "can_send_other_messages"   -> x.canSendOtherMessages.asJson,
          "can_add_web_page_previews" -> x.canAddWebPagePreviews.asJson,
          "can_change_info"           -> x.canChangeInfo.asJson,
          "can_invite_users"          -> x.canInviteUsers.asJson,
          "can_pin_messages"          -> x.canPinMessages.asJson,
          "can_manage_topics"         -> x.canManageTopics.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatpermissionsDecoder: Decoder[ChatPermissions] =
    Decoder.instance { h =>
      for {
        _canSendMessages       <- h.get[Option[Boolean]]("can_send_messages")
        _canSendAudios         <- h.get[Option[Boolean]]("can_send_audios")
        _canSendDocuments      <- h.get[Option[Boolean]]("can_send_documents")
        _canSendPhotos         <- h.get[Option[Boolean]]("can_send_photos")
        _canSendVideos         <- h.get[Option[Boolean]]("can_send_videos")
        _canSendVideoNotes     <- h.get[Option[Boolean]]("can_send_video_notes")
        _canSendVoiceNotes     <- h.get[Option[Boolean]]("can_send_voice_notes")
        _canSendPolls          <- h.get[Option[Boolean]]("can_send_polls")
        _canSendOtherMessages  <- h.get[Option[Boolean]]("can_send_other_messages")
        _canAddWebPagePreviews <- h.get[Option[Boolean]]("can_add_web_page_previews")
        _canChangeInfo         <- h.get[Option[Boolean]]("can_change_info")
        _canInviteUsers        <- h.get[Option[Boolean]]("can_invite_users")
        _canPinMessages        <- h.get[Option[Boolean]]("can_pin_messages")
        _canManageTopics       <- h.get[Option[Boolean]]("can_manage_topics")
      } yield {
        ChatPermissions(
          canSendMessages = _canSendMessages,
          canSendAudios = _canSendAudios,
          canSendDocuments = _canSendDocuments,
          canSendPhotos = _canSendPhotos,
          canSendVideos = _canSendVideos,
          canSendVideoNotes = _canSendVideoNotes,
          canSendVoiceNotes = _canSendVoiceNotes,
          canSendPolls = _canSendPolls,
          canSendOtherMessages = _canSendOtherMessages,
          canAddWebPagePreviews = _canAddWebPagePreviews,
          canChangeInfo = _canChangeInfo,
          canInviteUsers = _canInviteUsers,
          canPinMessages = _canPinMessages,
          canManageTopics = _canManageTopics
        )
      }
    }

  implicit lazy val chatphotoEncoder: Encoder[ChatPhoto] =
    (x: ChatPhoto) => {
      Json.fromFields(
        List(
          "small_file_id"        -> x.smallFileId.asJson,
          "small_file_unique_id" -> x.smallFileUniqueId.asJson,
          "big_file_id"          -> x.bigFileId.asJson,
          "big_file_unique_id"   -> x.bigFileUniqueId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatphotoDecoder: Decoder[ChatPhoto] =
    Decoder.instance { h =>
      for {
        _smallFileId       <- h.get[String]("small_file_id")
        _smallFileUniqueId <- h.get[String]("small_file_unique_id")
        _bigFileId         <- h.get[String]("big_file_id")
        _bigFileUniqueId   <- h.get[String]("big_file_unique_id")
      } yield {
        ChatPhoto(
          smallFileId = _smallFileId,
          smallFileUniqueId = _smallFileUniqueId,
          bigFileId = _bigFileId,
          bigFileUniqueId = _bigFileUniqueId
        )
      }
    }

  implicit lazy val chatsharedEncoder: Encoder[ChatShared] =
    (x: ChatShared) => {
      Json.fromFields(
        List(
          "request_id" -> x.requestId.asJson,
          "chat_id"    -> x.chatId.asJson,
          "title"      -> x.title.asJson,
          "username"   -> x.username.asJson,
          "photo"      -> x.photo.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatsharedDecoder: Decoder[ChatShared] =
    Decoder.instance { h =>
      for {
        _requestId <- h.get[Int]("request_id")
        _chatId    <- h.get[Long]("chat_id")
        _title     <- h.get[Option[String]]("title")
        _username  <- h.get[Option[String]]("username")
        _photo     <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
      } yield {
        ChatShared(requestId = _requestId, chatId = _chatId, title = _title, username = _username, photo = _photo)
      }
    }

  implicit lazy val choseninlineresultEncoder: Encoder[ChosenInlineResult] =
    (x: ChosenInlineResult) => {
      Json.fromFields(
        List(
          "result_id"         -> x.resultId.asJson,
          "from"              -> x.from.asJson,
          "location"          -> x.location.asJson,
          "inline_message_id" -> x.inlineMessageId.asJson,
          "query"             -> x.query.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val choseninlineresultDecoder: Decoder[ChosenInlineResult] =
    Decoder.instance { h =>
      for {
        _resultId        <- h.get[String]("result_id")
        _from            <- h.get[User]("from")
        _location        <- h.get[Option[Location]]("location")
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
        _query           <- h.get[String]("query")
      } yield {
        ChosenInlineResult(
          resultId = _resultId,
          from = _from,
          location = _location,
          inlineMessageId = _inlineMessageId,
          query = _query
        )
      }
    }

  implicit lazy val contactEncoder: Encoder[Contact] =
    (x: Contact) => {
      Json.fromFields(
        List(
          "phone_number" -> x.phoneNumber.asJson,
          "first_name"   -> x.firstName.asJson,
          "last_name"    -> x.lastName.asJson,
          "user_id"      -> x.userId.asJson,
          "vcard"        -> x.vcard.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val contactDecoder: Decoder[Contact] =
    Decoder.instance { h =>
      for {
        _phoneNumber <- h.get[String]("phone_number")
        _firstName   <- h.get[String]("first_name")
        _lastName    <- h.get[Option[String]]("last_name")
        _userId      <- h.get[Option[Long]]("user_id")
        _vcard       <- h.get[Option[String]]("vcard")
      } yield {
        Contact(
          phoneNumber = _phoneNumber,
          firstName = _firstName,
          lastName = _lastName,
          userId = _userId,
          vcard = _vcard
        )
      }
    }

  implicit lazy val copytextbuttonEncoder: Encoder[CopyTextButton] =
    (x: CopyTextButton) => {
      Json.fromFields(
        List(
          "text" -> x.text.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val copytextbuttonDecoder: Decoder[CopyTextButton] =
    Decoder.instance { h =>
      for {
        _text <- h.get[String]("text")
      } yield {
        CopyTextButton(text = _text)
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
        _emoji <- h.get[String]("emoji")
        _value <- h.get[Int]("value")
      } yield {
        Dice(emoji = _emoji, value = _value)
      }
    }

  implicit lazy val documentEncoder: Encoder[Document] =
    (x: Document) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "thumbnail"      -> x.thumbnail.asJson,
          "file_name"      -> x.fileName.asJson,
          "mime_type"      -> x.mimeType.asJson,
          "file_size"      -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val documentDecoder: Decoder[Document] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _thumbnail    <- h.get[Option[PhotoSize]]("thumbnail")
        _fileName     <- h.get[Option[String]]("file_name")
        _mimeType     <- h.get[Option[String]]("mime_type")
        _fileSize     <- h.get[Option[Long]]("file_size")
      } yield {
        Document(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          thumbnail = _thumbnail,
          fileName = _fileName,
          mimeType = _mimeType,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val encryptedcredentialsEncoder: Encoder[EncryptedCredentials] =
    (x: EncryptedCredentials) => {
      Json.fromFields(
        List(
          "data"   -> x.data.asJson,
          "hash"   -> x.hash.asJson,
          "secret" -> x.secret.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val encryptedcredentialsDecoder: Decoder[EncryptedCredentials] =
    Decoder.instance { h =>
      for {
        _data   <- h.get[String]("data")
        _hash   <- h.get[String]("hash")
        _secret <- h.get[String]("secret")
      } yield {
        EncryptedCredentials(data = _data, hash = _hash, secret = _secret)
      }
    }

  implicit lazy val encryptedpassportelementEncoder: Encoder[EncryptedPassportElement] =
    (x: EncryptedPassportElement) => {
      Json.fromFields(
        List(
          "type"         -> x.`type`.asJson,
          "data"         -> x.data.asJson,
          "phone_number" -> x.phoneNumber.asJson,
          "email"        -> x.email.asJson,
          "files"        -> x.files.asJson,
          "front_side"   -> x.frontSide.asJson,
          "reverse_side" -> x.reverseSide.asJson,
          "selfie"       -> x.selfie.asJson,
          "translation"  -> x.translation.asJson,
          "hash"         -> x.hash.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val encryptedpassportelementDecoder: Decoder[EncryptedPassportElement] =
    Decoder.instance { h =>
      for {
        _type        <- h.get[String]("type")
        _data        <- h.get[Option[String]]("data")
        _phoneNumber <- h.get[Option[String]]("phone_number")
        _email       <- h.get[Option[String]]("email")
        _files       <- h.getOrElse[List[PassportFile]]("files")(List.empty)
        _frontSide   <- h.get[Option[PassportFile]]("front_side")
        _reverseSide <- h.get[Option[PassportFile]]("reverse_side")
        _selfie      <- h.get[Option[PassportFile]]("selfie")
        _translation <- h.getOrElse[List[PassportFile]]("translation")(List.empty)
        _hash        <- h.get[String]("hash")
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

  implicit lazy val externalreplyinfoEncoder: Encoder[ExternalReplyInfo] =
    (x: ExternalReplyInfo) => {
      Json.fromFields(
        List(
          "origin"               -> x.origin.asJson,
          "chat"                 -> x.chat.asJson,
          "message_id"           -> x.messageId.asJson,
          "link_preview_options" -> x.linkPreviewOptions.asJson,
          "animation"            -> x.animation.asJson,
          "audio"                -> x.audio.asJson,
          "document"             -> x.document.asJson,
          "paid_media"           -> x.paidMedia.asJson,
          "photo"                -> x.photo.asJson,
          "sticker"              -> x.sticker.asJson,
          "story"                -> x.story.asJson,
          "video"                -> x.video.asJson,
          "video_note"           -> x.videoNote.asJson,
          "voice"                -> x.voice.asJson,
          "has_media_spoiler"    -> x.hasMediaSpoiler.asJson,
          "contact"              -> x.contact.asJson,
          "dice"                 -> x.dice.asJson,
          "game"                 -> x.game.asJson,
          "giveaway"             -> x.giveaway.asJson,
          "giveaway_winners"     -> x.giveawayWinners.asJson,
          "invoice"              -> x.invoice.asJson,
          "location"             -> x.location.asJson,
          "poll"                 -> x.poll.asJson,
          "venue"                -> x.venue.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val externalreplyinfoDecoder: Decoder[ExternalReplyInfo] =
    Decoder.instance { h =>
      for {
        _origin             <- h.get[iozhik.OpenEnum[MessageOrigin]]("origin")
        _chat               <- h.get[Option[Chat]]("chat")
        _messageId          <- h.get[Option[Int]]("message_id")
        _linkPreviewOptions <- h.get[Option[LinkPreviewOptions]]("link_preview_options")
        _animation          <- h.get[Option[Animation]]("animation")
        _audio              <- h.get[Option[Audio]]("audio")
        _document           <- h.get[Option[Document]]("document")
        _paidMedia          <- h.get[Option[PaidMediaInfo]]("paid_media")
        _photo              <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
        _sticker            <- h.get[Option[Sticker]]("sticker")
        _story              <- h.get[Option[Story]]("story")
        _video              <- h.get[Option[Video]]("video")
        _videoNote          <- h.get[Option[VideoNote]]("video_note")
        _voice              <- h.get[Option[Voice]]("voice")
        _hasMediaSpoiler    <- h.get[Option[Boolean]]("has_media_spoiler")
        _contact            <- h.get[Option[Contact]]("contact")
        _dice               <- h.get[Option[Dice]]("dice")
        _game               <- h.get[Option[Game]]("game")
        _giveaway           <- h.get[Option[Giveaway]]("giveaway")
        _giveawayWinners    <- h.get[Option[GiveawayWinners]]("giveaway_winners")
        _invoice            <- h.get[Option[Invoice]]("invoice")
        _location           <- h.get[Option[Location]]("location")
        _poll               <- h.get[Option[Poll]]("poll")
        _venue              <- h.get[Option[Venue]]("venue")
      } yield {
        ExternalReplyInfo(
          origin = _origin,
          chat = _chat,
          messageId = _messageId,
          linkPreviewOptions = _linkPreviewOptions,
          animation = _animation,
          audio = _audio,
          document = _document,
          paidMedia = _paidMedia,
          photo = _photo,
          sticker = _sticker,
          story = _story,
          video = _video,
          videoNote = _videoNote,
          voice = _voice,
          hasMediaSpoiler = _hasMediaSpoiler,
          contact = _contact,
          dice = _dice,
          game = _game,
          giveaway = _giveaway,
          giveawayWinners = _giveawayWinners,
          invoice = _invoice,
          location = _location,
          poll = _poll,
          venue = _venue
        )
      }
    }

  implicit lazy val fileEncoder: Encoder[File] =
    (x: File) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "file_size"      -> x.fileSize.asJson,
          "file_path"      -> x.filePath.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val fileDecoder: Decoder[File] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _fileSize     <- h.get[Option[Long]]("file_size")
        _filePath     <- h.get[Option[String]]("file_path")
      } yield {
        File(fileId = _fileId, fileUniqueId = _fileUniqueId, fileSize = _fileSize, filePath = _filePath)
      }
    }

  implicit lazy val forumtopicEncoder: Encoder[ForumTopic] =
    (x: ForumTopic) => {
      Json.fromFields(
        List(
          "message_thread_id"    -> x.messageThreadId.asJson,
          "name"                 -> x.name.asJson,
          "icon_color"           -> x.iconColor.asJson,
          "icon_custom_emoji_id" -> x.iconCustomEmojiId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val forumtopicDecoder: Decoder[ForumTopic] =
    Decoder.instance { h =>
      for {
        _messageThreadId   <- h.get[Int]("message_thread_id")
        _name              <- h.get[String]("name")
        _iconColor         <- h.get[Int]("icon_color")
        _iconCustomEmojiId <- h.get[Option[String]]("icon_custom_emoji_id")
      } yield {
        ForumTopic(
          messageThreadId = _messageThreadId,
          name = _name,
          iconColor = _iconColor,
          iconCustomEmojiId = _iconCustomEmojiId
        )
      }
    }

  implicit lazy val forumtopicclosedEncoder: Encoder[ForumTopicClosed.type] = (_: ForumTopicClosed.type) => ().asJson
  implicit lazy val forumtopicclosedDecoder: Decoder[ForumTopicClosed.type] = (_: HCursor) => Right(ForumTopicClosed)

  implicit lazy val forumtopiccreatedEncoder: Encoder[ForumTopicCreated] =
    (x: ForumTopicCreated) => {
      Json.fromFields(
        List(
          "name"                 -> x.name.asJson,
          "icon_color"           -> x.iconColor.asJson,
          "icon_custom_emoji_id" -> x.iconCustomEmojiId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val forumtopiccreatedDecoder: Decoder[ForumTopicCreated] =
    Decoder.instance { h =>
      for {
        _name              <- h.get[String]("name")
        _iconColor         <- h.get[Int]("icon_color")
        _iconCustomEmojiId <- h.get[Option[String]]("icon_custom_emoji_id")
      } yield {
        ForumTopicCreated(name = _name, iconColor = _iconColor, iconCustomEmojiId = _iconCustomEmojiId)
      }
    }

  implicit lazy val forumtopiceditedEncoder: Encoder[ForumTopicEdited] =
    (x: ForumTopicEdited) => {
      Json.fromFields(
        List(
          "name"                 -> x.name.asJson,
          "icon_custom_emoji_id" -> x.iconCustomEmojiId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val forumtopiceditedDecoder: Decoder[ForumTopicEdited] =
    Decoder.instance { h =>
      for {
        _name              <- h.get[Option[String]]("name")
        _iconCustomEmojiId <- h.get[Option[String]]("icon_custom_emoji_id")
      } yield {
        ForumTopicEdited(name = _name, iconCustomEmojiId = _iconCustomEmojiId)
      }
    }

  implicit lazy val forumtopicreopenedEncoder: Encoder[ForumTopicReopened.type] = (_: ForumTopicReopened.type) =>
    ().asJson

  implicit lazy val forumtopicreopenedDecoder: Decoder[ForumTopicReopened.type] = (_: HCursor) =>
    Right(ForumTopicReopened)

  implicit lazy val gameEncoder: Encoder[Game] =
    (x: Game) => {
      Json.fromFields(
        List(
          "title"         -> x.title.asJson,
          "description"   -> x.description.asJson,
          "photo"         -> x.photo.asJson,
          "text"          -> x.text.asJson,
          "text_entities" -> x.textEntities.asJson,
          "animation"     -> x.animation.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val gameDecoder: Decoder[Game] =
    Decoder.instance { h =>
      for {
        _title        <- h.get[String]("title")
        _description  <- h.get[String]("description")
        _photo        <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
        _text         <- h.get[Option[String]]("text")
        _textEntities <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("text_entities")(List.empty)
        _animation    <- h.get[Option[Animation]]("animation")
      } yield {
        Game(
          title = _title,
          description = _description,
          photo = _photo,
          text = _text,
          textEntities = _textEntities,
          animation = _animation
        )
      }
    }

  implicit lazy val gamehighscoreEncoder: Encoder[GameHighScore] =
    (x: GameHighScore) => {
      Json.fromFields(
        List(
          "position" -> x.position.asJson,
          "user"     -> x.user.asJson,
          "score"    -> x.score.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val gamehighscoreDecoder: Decoder[GameHighScore] =
    Decoder.instance { h =>
      for {
        _position <- h.get[Int]("position")
        _user     <- h.get[User]("user")
        _score    <- h.get[Int]("score")
      } yield {
        GameHighScore(position = _position, user = _user, score = _score)
      }
    }

  implicit lazy val generalforumtopichiddenEncoder: Encoder[GeneralForumTopicHidden.type] =
    (_: GeneralForumTopicHidden.type) => ().asJson

  implicit lazy val generalforumtopichiddenDecoder: Decoder[GeneralForumTopicHidden.type] = (_: HCursor) =>
    Right(GeneralForumTopicHidden)

  implicit lazy val generalforumtopicunhiddenEncoder: Encoder[GeneralForumTopicUnhidden.type] =
    (_: GeneralForumTopicUnhidden.type) => ().asJson

  implicit lazy val generalforumtopicunhiddenDecoder: Decoder[GeneralForumTopicUnhidden.type] = (_: HCursor) =>
    Right(GeneralForumTopicUnhidden)

  implicit lazy val giftEncoder: Encoder[Gift] =
    (x: Gift) => {
      Json.fromFields(
        List(
          "id"              -> x.id.asJson,
          "sticker"         -> x.sticker.asJson,
          "star_count"      -> x.starCount.asJson,
          "total_count"     -> x.totalCount.asJson,
          "remaining_count" -> x.remainingCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val giftDecoder: Decoder[Gift] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[String]("id")
        _sticker        <- h.get[Sticker]("sticker")
        _starCount      <- h.get[Int]("star_count")
        _totalCount     <- h.get[Option[Int]]("total_count")
        _remainingCount <- h.get[Option[Int]]("remaining_count")
      } yield {
        Gift(
          id = _id,
          sticker = _sticker,
          starCount = _starCount,
          totalCount = _totalCount,
          remainingCount = _remainingCount
        )
      }
    }

  implicit lazy val giftsEncoder: Encoder[Gifts] =
    (x: Gifts) => {
      Json.fromFields(
        List(
          "gifts" -> x.gifts.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val giftsDecoder: Decoder[Gifts] =
    Decoder.instance { h =>
      for {
        _gifts <- h.getOrElse[List[Gift]]("gifts")(List.empty)
      } yield {
        Gifts(gifts = _gifts)
      }
    }

  implicit lazy val giveawayEncoder: Encoder[Giveaway] =
    (x: Giveaway) => {
      Json.fromFields(
        List(
          "chats"                            -> x.chats.asJson,
          "winners_selection_date"           -> x.winnersSelectionDate.asJson,
          "winner_count"                     -> x.winnerCount.asJson,
          "only_new_members"                 -> x.onlyNewMembers.asJson,
          "has_public_winners"               -> x.hasPublicWinners.asJson,
          "prize_description"                -> x.prizeDescription.asJson,
          "country_codes"                    -> x.countryCodes.asJson,
          "prize_star_count"                 -> x.prizeStarCount.asJson,
          "premium_subscription_month_count" -> x.premiumSubscriptionMonthCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val giveawayDecoder: Decoder[Giveaway] =
    Decoder.instance { h =>
      for {
        _chats                         <- h.getOrElse[List[Chat]]("chats")(List.empty)
        _winnersSelectionDate          <- h.get[Int]("winners_selection_date")
        _winnerCount                   <- h.get[Int]("winner_count")
        _onlyNewMembers                <- h.get[Option[Boolean]]("only_new_members")
        _hasPublicWinners              <- h.get[Option[Boolean]]("has_public_winners")
        _prizeDescription              <- h.get[Option[String]]("prize_description")
        _countryCodes                  <- h.getOrElse[List[String]]("country_codes")(List.empty)
        _prizeStarCount                <- h.get[Option[Int]]("prize_star_count")
        _premiumSubscriptionMonthCount <- h.get[Option[Int]]("premium_subscription_month_count")
      } yield {
        Giveaway(
          chats = _chats,
          winnersSelectionDate = _winnersSelectionDate,
          winnerCount = _winnerCount,
          onlyNewMembers = _onlyNewMembers,
          hasPublicWinners = _hasPublicWinners,
          prizeDescription = _prizeDescription,
          countryCodes = _countryCodes,
          prizeStarCount = _prizeStarCount,
          premiumSubscriptionMonthCount = _premiumSubscriptionMonthCount
        )
      }
    }

  implicit lazy val giveawaycompletedEncoder: Encoder[GiveawayCompleted] =
    (x: GiveawayCompleted) => {
      Json.fromFields(
        List(
          "winner_count"          -> x.winnerCount.asJson,
          "unclaimed_prize_count" -> x.unclaimedPrizeCount.asJson,
          "giveaway_message"      -> x.giveawayMessage.asJson,
          "is_star_giveaway"      -> x.isStarGiveaway.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val giveawaycompletedDecoder: Decoder[GiveawayCompleted] =
    Decoder.instance { h =>
      for {
        _winnerCount         <- h.get[Int]("winner_count")
        _unclaimedPrizeCount <- h.get[Option[Int]]("unclaimed_prize_count")
        _giveawayMessage     <- h.get[Option[Message]]("giveaway_message")
        _isStarGiveaway      <- h.get[Option[Boolean]]("is_star_giveaway")
      } yield {
        GiveawayCompleted(
          winnerCount = _winnerCount,
          unclaimedPrizeCount = _unclaimedPrizeCount,
          giveawayMessage = _giveawayMessage,
          isStarGiveaway = _isStarGiveaway
        )
      }
    }

  implicit lazy val giveawaycreatedEncoder: Encoder[GiveawayCreated] =
    (x: GiveawayCreated) => {
      Json.fromFields(
        List(
          "prize_star_count" -> x.prizeStarCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val giveawaycreatedDecoder: Decoder[GiveawayCreated] =
    Decoder.instance { h =>
      for {
        _prizeStarCount <- h.get[Option[Int]]("prize_star_count")
      } yield {
        GiveawayCreated(prizeStarCount = _prizeStarCount)
      }
    }

  implicit lazy val giveawaywinnersEncoder: Encoder[GiveawayWinners] =
    (x: GiveawayWinners) => {
      Json.fromFields(
        List(
          "chat"                             -> x.chat.asJson,
          "giveaway_message_id"              -> x.giveawayMessageId.asJson,
          "winners_selection_date"           -> x.winnersSelectionDate.asJson,
          "winner_count"                     -> x.winnerCount.asJson,
          "winners"                          -> x.winners.asJson,
          "additional_chat_count"            -> x.additionalChatCount.asJson,
          "prize_star_count"                 -> x.prizeStarCount.asJson,
          "premium_subscription_month_count" -> x.premiumSubscriptionMonthCount.asJson,
          "unclaimed_prize_count"            -> x.unclaimedPrizeCount.asJson,
          "only_new_members"                 -> x.onlyNewMembers.asJson,
          "was_refunded"                     -> x.wasRefunded.asJson,
          "prize_description"                -> x.prizeDescription.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val giveawaywinnersDecoder: Decoder[GiveawayWinners] =
    Decoder.instance { h =>
      for {
        _chat                          <- h.get[Chat]("chat")
        _giveawayMessageId             <- h.get[Int]("giveaway_message_id")
        _winnersSelectionDate          <- h.get[Int]("winners_selection_date")
        _winnerCount                   <- h.get[Int]("winner_count")
        _winners                       <- h.getOrElse[List[User]]("winners")(List.empty)
        _additionalChatCount           <- h.get[Option[Int]]("additional_chat_count")
        _prizeStarCount                <- h.get[Option[Int]]("prize_star_count")
        _premiumSubscriptionMonthCount <- h.get[Option[Int]]("premium_subscription_month_count")
        _unclaimedPrizeCount           <- h.get[Option[Int]]("unclaimed_prize_count")
        _onlyNewMembers                <- h.get[Option[Boolean]]("only_new_members")
        _wasRefunded                   <- h.get[Option[Boolean]]("was_refunded")
        _prizeDescription              <- h.get[Option[String]]("prize_description")
      } yield {
        GiveawayWinners(
          chat = _chat,
          giveawayMessageId = _giveawayMessageId,
          winnersSelectionDate = _winnersSelectionDate,
          winnerCount = _winnerCount,
          winners = _winners,
          additionalChatCount = _additionalChatCount,
          prizeStarCount = _prizeStarCount,
          premiumSubscriptionMonthCount = _premiumSubscriptionMonthCount,
          unclaimedPrizeCount = _unclaimedPrizeCount,
          onlyNewMembers = _onlyNewMembers,
          wasRefunded = _wasRefunded,
          prizeDescription = _prizeDescription
        )
      }
    }

  implicit lazy val inlinekeyboardbuttonEncoder: Encoder[InlineKeyboardButton] =
    (x: InlineKeyboardButton) => {
      Json.fromFields(
        List(
          "text"                             -> x.text.asJson,
          "url"                              -> x.url.asJson,
          "callback_data"                    -> x.callbackData.asJson,
          "web_app"                          -> x.webApp.asJson,
          "login_url"                        -> x.loginUrl.asJson,
          "switch_inline_query"              -> x.switchInlineQuery.asJson,
          "switch_inline_query_current_chat" -> x.switchInlineQueryCurrentChat.asJson,
          "switch_inline_query_chosen_chat"  -> x.switchInlineQueryChosenChat.asJson,
          "copy_text"                        -> x.copyText.asJson,
          "callback_game"                    -> x.callbackGame.asJson,
          "pay"                              -> x.pay.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinekeyboardbuttonDecoder: Decoder[InlineKeyboardButton] =
    Decoder.instance { h =>
      for {
        _text                         <- h.get[String]("text")
        _url                          <- h.get[Option[String]]("url")
        _callbackData                 <- h.get[Option[String]]("callback_data")
        _webApp                       <- h.get[Option[WebAppInfo]]("web_app")
        _loginUrl                     <- h.get[Option[LoginUrl]]("login_url")
        _switchInlineQuery            <- h.get[Option[String]]("switch_inline_query")
        _switchInlineQueryCurrentChat <- h.get[Option[String]]("switch_inline_query_current_chat")
        _switchInlineQueryChosenChat  <- h.get[Option[SwitchInlineQueryChosenChat]]("switch_inline_query_chosen_chat")
        _copyText                     <- h.get[Option[CopyTextButton]]("copy_text")
        _callbackGame                 <- h.get[Option[CallbackGame.type]]("callback_game")
        _pay                          <- h.get[Option[Boolean]]("pay")
      } yield {
        InlineKeyboardButton(
          text = _text,
          url = _url,
          callbackData = _callbackData,
          webApp = _webApp,
          loginUrl = _loginUrl,
          switchInlineQuery = _switchInlineQuery,
          switchInlineQueryCurrentChat = _switchInlineQueryCurrentChat,
          switchInlineQueryChosenChat = _switchInlineQueryChosenChat,
          copyText = _copyText,
          callbackGame = _callbackGame,
          pay = _pay
        )
      }
    }

  implicit lazy val inlinequeryEncoder: Encoder[InlineQuery] =
    (x: InlineQuery) => {
      Json.fromFields(
        List(
          "id"        -> x.id.asJson,
          "from"      -> x.from.asJson,
          "query"     -> x.query.asJson,
          "offset"    -> x.offset.asJson,
          "chat_type" -> x.chatType.asJson,
          "location"  -> x.location.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryDecoder: Decoder[InlineQuery] =
    Decoder.instance { h =>
      for {
        _id       <- h.get[String]("id")
        _from     <- h.get[User]("from")
        _query    <- h.get[String]("query")
        _offset   <- h.get[String]("offset")
        _chatType <- h.get[Option[String]]("chat_type")
        _location <- h.get[Option[Location]]("location")
      } yield {
        InlineQuery(
          id = _id,
          from = _from,
          query = _query,
          offset = _offset,
          chatType = _chatType,
          location = _location
        )
      }
    }

  implicit lazy val inlinequeryresultsbuttonEncoder: Encoder[InlineQueryResultsButton] =
    (x: InlineQueryResultsButton) => {
      Json.fromFields(
        List(
          "text"            -> x.text.asJson,
          "web_app"         -> x.webApp.asJson,
          "start_parameter" -> x.startParameter.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultsbuttonDecoder: Decoder[InlineQueryResultsButton] =
    Decoder.instance { h =>
      for {
        _text           <- h.get[String]("text")
        _webApp         <- h.get[Option[WebAppInfo]]("web_app")
        _startParameter <- h.get[Option[String]]("start_parameter")
      } yield {
        InlineQueryResultsButton(text = _text, webApp = _webApp, startParameter = _startParameter)
      }
    }

  implicit lazy val inputfileEncoder: Encoder[InputFile.type] = (_: InputFile.type) => ().asJson
  implicit lazy val inputfileDecoder: Decoder[InputFile.type] = (_: HCursor) => Right(InputFile)

  implicit lazy val inputpolloptionEncoder: Encoder[InputPollOption] =
    (x: InputPollOption) => {
      Json.fromFields(
        List(
          "text"            -> x.text.asJson,
          "text_parse_mode" -> x.textParseMode.asJson,
          "text_entities"   -> x.textEntities.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputpolloptionDecoder: Decoder[InputPollOption] =
    Decoder.instance { h =>
      for {
        _text          <- h.get[String]("text")
        _textParseMode <- h.get[Option[ParseMode]]("text_parse_mode")
        _textEntities  <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("text_entities")(List.empty)
      } yield {
        InputPollOption(text = _text, textParseMode = _textParseMode, textEntities = _textEntities)
      }
    }

  implicit lazy val inputstickerEncoder: Encoder[InputSticker] =
    (x: InputSticker) => {
      Json.fromFields(
        List(
          "sticker"       -> x.sticker.asJson,
          "format"        -> x.format.asJson,
          "emoji_list"    -> x.emojiList.asJson,
          "mask_position" -> x.maskPosition.asJson,
          "keywords"      -> x.keywords.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputstickerDecoder: Decoder[InputSticker] =
    Decoder.instance { h =>
      for {
        _sticker      <- h.get[IFile]("sticker")
        _format       <- h.get[String]("format")
        _emojiList    <- h.getOrElse[List[String]]("emoji_list")(List.empty)
        _maskPosition <- h.get[Option[MaskPosition]]("mask_position")
        _keywords     <- h.getOrElse[List[String]]("keywords")(List.empty)
      } yield {
        InputSticker(
          sticker = _sticker,
          format = _format,
          emojiList = _emojiList,
          maskPosition = _maskPosition,
          keywords = _keywords
        )
      }
    }

  implicit lazy val invoiceEncoder: Encoder[Invoice] =
    (x: Invoice) => {
      Json.fromFields(
        List(
          "title"           -> x.title.asJson,
          "description"     -> x.description.asJson,
          "start_parameter" -> x.startParameter.asJson,
          "currency"        -> x.currency.asJson,
          "total_amount"    -> x.totalAmount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val invoiceDecoder: Decoder[Invoice] =
    Decoder.instance { h =>
      for {
        _title          <- h.get[String]("title")
        _description    <- h.get[String]("description")
        _startParameter <- h.get[String]("start_parameter")
        _currency       <- h.get[String]("currency")
        _totalAmount    <- h.get[Int]("total_amount")
      } yield {
        Invoice(
          title = _title,
          description = _description,
          startParameter = _startParameter,
          currency = _currency,
          totalAmount = _totalAmount
        )
      }
    }

  implicit lazy val keyboardbuttonEncoder: Encoder[KeyboardButton] =
    (x: KeyboardButton) => {
      Json.fromFields(
        List(
          "text"             -> x.text.asJson,
          "request_users"    -> x.requestUsers.asJson,
          "request_chat"     -> x.requestChat.asJson,
          "request_contact"  -> x.requestContact.asJson,
          "request_location" -> x.requestLocation.asJson,
          "request_poll"     -> x.requestPoll.asJson,
          "web_app"          -> x.webApp.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val keyboardbuttonDecoder: Decoder[KeyboardButton] =
    Decoder.instance { h =>
      for {
        _text            <- h.get[String]("text")
        _requestUsers    <- h.get[Option[KeyboardButtonRequestUsers]]("request_users")
        _requestChat     <- h.get[Option[KeyboardButtonRequestChat]]("request_chat")
        _requestContact  <- h.get[Option[Boolean]]("request_contact")
        _requestLocation <- h.get[Option[Boolean]]("request_location")
        _requestPoll     <- h.get[Option[KeyboardButtonPollType]]("request_poll")
        _webApp          <- h.get[Option[WebAppInfo]]("web_app")
      } yield {
        KeyboardButton(
          text = _text,
          requestUsers = _requestUsers,
          requestChat = _requestChat,
          requestContact = _requestContact,
          requestLocation = _requestLocation,
          requestPoll = _requestPoll,
          webApp = _webApp
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

  implicit lazy val keyboardbuttonrequestchatEncoder: Encoder[KeyboardButtonRequestChat] =
    (x: KeyboardButtonRequestChat) => {
      Json.fromFields(
        List(
          "request_id"                -> x.requestId.asJson,
          "chat_is_channel"           -> x.chatIsChannel.asJson,
          "chat_is_forum"             -> x.chatIsForum.asJson,
          "chat_has_username"         -> x.chatHasUsername.asJson,
          "chat_is_created"           -> x.chatIsCreated.asJson,
          "user_administrator_rights" -> x.userAdministratorRights.asJson,
          "bot_administrator_rights"  -> x.botAdministratorRights.asJson,
          "bot_is_member"             -> x.botIsMember.asJson,
          "request_title"             -> x.requestTitle.asJson,
          "request_username"          -> x.requestUsername.asJson,
          "request_photo"             -> x.requestPhoto.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val keyboardbuttonrequestchatDecoder: Decoder[KeyboardButtonRequestChat] =
    Decoder.instance { h =>
      for {
        _requestId               <- h.get[Int]("request_id")
        _chatIsChannel           <- h.get[Boolean]("chat_is_channel")
        _chatIsForum             <- h.get[Option[Boolean]]("chat_is_forum")
        _chatHasUsername         <- h.get[Option[Boolean]]("chat_has_username")
        _chatIsCreated           <- h.get[Option[Boolean]]("chat_is_created")
        _userAdministratorRights <- h.get[Option[ChatAdministratorRights]]("user_administrator_rights")
        _botAdministratorRights  <- h.get[Option[ChatAdministratorRights]]("bot_administrator_rights")
        _botIsMember             <- h.get[Option[Boolean]]("bot_is_member")
        _requestTitle            <- h.get[Option[Boolean]]("request_title")
        _requestUsername         <- h.get[Option[Boolean]]("request_username")
        _requestPhoto            <- h.get[Option[Boolean]]("request_photo")
      } yield {
        KeyboardButtonRequestChat(
          requestId = _requestId,
          chatIsChannel = _chatIsChannel,
          chatIsForum = _chatIsForum,
          chatHasUsername = _chatHasUsername,
          chatIsCreated = _chatIsCreated,
          userAdministratorRights = _userAdministratorRights,
          botAdministratorRights = _botAdministratorRights,
          botIsMember = _botIsMember,
          requestTitle = _requestTitle,
          requestUsername = _requestUsername,
          requestPhoto = _requestPhoto
        )
      }
    }

  implicit lazy val keyboardbuttonrequestusersEncoder: Encoder[KeyboardButtonRequestUsers] =
    (x: KeyboardButtonRequestUsers) => {
      Json.fromFields(
        List(
          "request_id"       -> x.requestId.asJson,
          "user_is_bot"      -> x.userIsBot.asJson,
          "user_is_premium"  -> x.userIsPremium.asJson,
          "max_quantity"     -> x.maxQuantity.asJson,
          "request_name"     -> x.requestName.asJson,
          "request_username" -> x.requestUsername.asJson,
          "request_photo"    -> x.requestPhoto.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val keyboardbuttonrequestusersDecoder: Decoder[KeyboardButtonRequestUsers] =
    Decoder.instance { h =>
      for {
        _requestId       <- h.get[Int]("request_id")
        _userIsBot       <- h.get[Option[Boolean]]("user_is_bot")
        _userIsPremium   <- h.get[Option[Boolean]]("user_is_premium")
        _maxQuantity     <- h.get[Option[Int]]("max_quantity")
        _requestName     <- h.get[Option[Boolean]]("request_name")
        _requestUsername <- h.get[Option[Boolean]]("request_username")
        _requestPhoto    <- h.get[Option[Boolean]]("request_photo")
      } yield {
        KeyboardButtonRequestUsers(
          requestId = _requestId,
          userIsBot = _userIsBot,
          userIsPremium = _userIsPremium,
          maxQuantity = _maxQuantity,
          requestName = _requestName,
          requestUsername = _requestUsername,
          requestPhoto = _requestPhoto
        )
      }
    }

  implicit lazy val labeledpriceEncoder: Encoder[LabeledPrice] =
    (x: LabeledPrice) => {
      Json.fromFields(
        List(
          "label"  -> x.label.asJson,
          "amount" -> x.amount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val labeledpriceDecoder: Decoder[LabeledPrice] =
    Decoder.instance { h =>
      for {
        _label  <- h.get[String]("label")
        _amount <- h.get[Int]("amount")
      } yield {
        LabeledPrice(label = _label, amount = _amount)
      }
    }

  implicit lazy val linkpreviewoptionsEncoder: Encoder[LinkPreviewOptions] =
    (x: LinkPreviewOptions) => {
      Json.fromFields(
        List(
          "is_disabled"        -> x.isDisabled.asJson,
          "url"                -> x.url.asJson,
          "prefer_small_media" -> x.preferSmallMedia.asJson,
          "prefer_large_media" -> x.preferLargeMedia.asJson,
          "show_above_text"    -> x.showAboveText.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val linkpreviewoptionsDecoder: Decoder[LinkPreviewOptions] =
    Decoder.instance { h =>
      for {
        _isDisabled       <- h.get[Option[Boolean]]("is_disabled")
        _url              <- h.get[Option[String]]("url")
        _preferSmallMedia <- h.get[Option[Boolean]]("prefer_small_media")
        _preferLargeMedia <- h.get[Option[Boolean]]("prefer_large_media")
        _showAboveText    <- h.get[Option[Boolean]]("show_above_text")
      } yield {
        LinkPreviewOptions(
          isDisabled = _isDisabled,
          url = _url,
          preferSmallMedia = _preferSmallMedia,
          preferLargeMedia = _preferLargeMedia,
          showAboveText = _showAboveText
        )
      }
    }

  implicit lazy val locationEncoder: Encoder[Location] =
    (x: Location) => {
      Json.fromFields(
        List(
          "latitude"               -> x.latitude.asJson,
          "longitude"              -> x.longitude.asJson,
          "horizontal_accuracy"    -> x.horizontalAccuracy.asJson,
          "live_period"            -> x.livePeriod.asJson,
          "heading"                -> x.heading.asJson,
          "proximity_alert_radius" -> x.proximityAlertRadius.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val locationDecoder: Decoder[Location] =
    Decoder.instance { h =>
      for {
        _latitude             <- h.get[Float]("latitude")
        _longitude            <- h.get[Float]("longitude")
        _horizontalAccuracy   <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod           <- h.get[Option[Int]]("live_period")
        _heading              <- h.get[Option[Int]]("heading")
        _proximityAlertRadius <- h.get[Option[Int]]("proximity_alert_radius")
      } yield {
        Location(
          latitude = _latitude,
          longitude = _longitude,
          horizontalAccuracy = _horizontalAccuracy,
          livePeriod = _livePeriod,
          heading = _heading,
          proximityAlertRadius = _proximityAlertRadius
        )
      }
    }

  implicit lazy val loginurlEncoder: Encoder[LoginUrl] =
    (x: LoginUrl) => {
      Json.fromFields(
        List(
          "url"                  -> x.url.asJson,
          "forward_text"         -> x.forwardText.asJson,
          "bot_username"         -> x.botUsername.asJson,
          "request_write_access" -> x.requestWriteAccess.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val loginurlDecoder: Decoder[LoginUrl] =
    Decoder.instance { h =>
      for {
        _url                <- h.get[String]("url")
        _forwardText        <- h.get[Option[String]]("forward_text")
        _botUsername        <- h.get[Option[String]]("bot_username")
        _requestWriteAccess <- h.get[Option[Boolean]]("request_write_access")
      } yield {
        LoginUrl(
          url = _url,
          forwardText = _forwardText,
          botUsername = _botUsername,
          requestWriteAccess = _requestWriteAccess
        )
      }
    }

  implicit lazy val maskpositionEncoder: Encoder[MaskPosition] =
    (x: MaskPosition) => {
      Json.fromFields(
        List(
          "point"   -> x.point.asJson,
          "x_shift" -> x.xShift.asJson,
          "y_shift" -> x.yShift.asJson,
          "scale"   -> x.scale.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val maskpositionDecoder: Decoder[MaskPosition] =
    Decoder.instance { h =>
      for {
        _point  <- h.get[String]("point")
        _xShift <- h.get[Float]("x_shift")
        _yShift <- h.get[Float]("y_shift")
        _scale  <- h.get[Float]("scale")
      } yield {
        MaskPosition(point = _point, xShift = _xShift, yShift = _yShift, scale = _scale)
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

  implicit lazy val messagereactioncountupdatedEncoder: Encoder[MessageReactionCountUpdated] =
    (x: MessageReactionCountUpdated) => {
      Json.fromFields(
        List(
          "chat"       -> x.chat.asJson,
          "message_id" -> x.messageId.asJson,
          "date"       -> x.date.asJson,
          "reactions"  -> x.reactions.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messagereactioncountupdatedDecoder: Decoder[MessageReactionCountUpdated] =
    Decoder.instance { h =>
      for {
        _chat      <- h.get[Chat]("chat")
        _messageId <- h.get[Int]("message_id")
        _date      <- h.get[Int]("date")
        _reactions <- h.getOrElse[List[ReactionCount]]("reactions")(List.empty)
      } yield {
        MessageReactionCountUpdated(chat = _chat, messageId = _messageId, date = _date, reactions = _reactions)
      }
    }

  implicit lazy val messagereactionupdatedEncoder: Encoder[MessageReactionUpdated] =
    (x: MessageReactionUpdated) => {
      Json.fromFields(
        List(
          "chat"         -> x.chat.asJson,
          "message_id"   -> x.messageId.asJson,
          "user"         -> x.user.asJson,
          "actor_chat"   -> x.actorChat.asJson,
          "date"         -> x.date.asJson,
          "old_reaction" -> x.oldReaction.asJson,
          "new_reaction" -> x.newReaction.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messagereactionupdatedDecoder: Decoder[MessageReactionUpdated] =
    Decoder.instance { h =>
      for {
        _chat        <- h.get[Chat]("chat")
        _messageId   <- h.get[Int]("message_id")
        _user        <- h.get[Option[User]]("user")
        _actorChat   <- h.get[Option[Chat]]("actor_chat")
        _date        <- h.get[Int]("date")
        _oldReaction <- h.getOrElse[List[iozhik.OpenEnum[ReactionType]]]("old_reaction")(List.empty)
        _newReaction <- h.getOrElse[List[iozhik.OpenEnum[ReactionType]]]("new_reaction")(List.empty)
      } yield {
        MessageReactionUpdated(
          chat = _chat,
          messageId = _messageId,
          user = _user,
          actorChat = _actorChat,
          date = _date,
          oldReaction = _oldReaction,
          newReaction = _newReaction
        )
      }
    }

  implicit lazy val orderinfoEncoder: Encoder[OrderInfo] =
    (x: OrderInfo) => {
      Json.fromFields(
        List(
          "name"             -> x.name.asJson,
          "phone_number"     -> x.phoneNumber.asJson,
          "email"            -> x.email.asJson,
          "shipping_address" -> x.shippingAddress.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val orderinfoDecoder: Decoder[OrderInfo] =
    Decoder.instance { h =>
      for {
        _name            <- h.get[Option[String]]("name")
        _phoneNumber     <- h.get[Option[String]]("phone_number")
        _email           <- h.get[Option[String]]("email")
        _shippingAddress <- h.get[Option[ShippingAddress]]("shipping_address")
      } yield {
        OrderInfo(name = _name, phoneNumber = _phoneNumber, email = _email, shippingAddress = _shippingAddress)
      }
    }

  implicit lazy val paidmediainfoEncoder: Encoder[PaidMediaInfo] =
    (x: PaidMediaInfo) => {
      Json.fromFields(
        List(
          "star_count" -> x.starCount.asJson,
          "paid_media" -> x.paidMedia.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val paidmediainfoDecoder: Decoder[PaidMediaInfo] =
    Decoder.instance { h =>
      for {
        _starCount <- h.get[Int]("star_count")
        _paidMedia <- h.getOrElse[List[iozhik.OpenEnum[PaidMedia]]]("paid_media")(List.empty)
      } yield {
        PaidMediaInfo(starCount = _starCount, paidMedia = _paidMedia)
      }
    }

  implicit lazy val paidmediapurchasedEncoder: Encoder[PaidMediaPurchased] =
    (x: PaidMediaPurchased) => {
      Json.fromFields(
        List(
          "from"               -> x.from.asJson,
          "paid_media_payload" -> x.paidMediaPayload.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val paidmediapurchasedDecoder: Decoder[PaidMediaPurchased] =
    Decoder.instance { h =>
      for {
        _from             <- h.get[User]("from")
        _paidMediaPayload <- h.get[String]("paid_media_payload")
      } yield {
        PaidMediaPurchased(from = _from, paidMediaPayload = _paidMediaPayload)
      }
    }

  implicit lazy val passportdataEncoder: Encoder[PassportData] =
    (x: PassportData) => {
      Json.fromFields(
        List(
          "data"        -> x.data.asJson,
          "credentials" -> x.credentials.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportdataDecoder: Decoder[PassportData] =
    Decoder.instance { h =>
      for {
        _data        <- h.getOrElse[List[EncryptedPassportElement]]("data")(List.empty)
        _credentials <- h.get[EncryptedCredentials]("credentials")
      } yield {
        PassportData(data = _data, credentials = _credentials)
      }
    }

  implicit lazy val passportfileEncoder: Encoder[PassportFile] =
    (x: PassportFile) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "file_size"      -> x.fileSize.asJson,
          "file_date"      -> x.fileDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val passportfileDecoder: Decoder[PassportFile] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _fileSize     <- h.get[Long]("file_size")
        _fileDate     <- h.get[Int]("file_date")
      } yield {
        PassportFile(fileId = _fileId, fileUniqueId = _fileUniqueId, fileSize = _fileSize, fileDate = _fileDate)
      }
    }

  implicit lazy val photosizeEncoder: Encoder[PhotoSize] =
    (x: PhotoSize) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width"          -> x.width.asJson,
          "height"         -> x.height.asJson,
          "file_size"      -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val photosizeDecoder: Decoder[PhotoSize] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _width        <- h.get[Int]("width")
        _height       <- h.get[Int]("height")
        _fileSize     <- h.get[Option[Long]]("file_size")
      } yield {
        PhotoSize(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          width = _width,
          height = _height,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val pollEncoder: Encoder[Poll] =
    (x: Poll) => {
      Json.fromFields(
        List(
          "id"                      -> x.id.asJson,
          "question"                -> x.question.asJson,
          "question_entities"       -> x.questionEntities.asJson,
          "options"                 -> x.options.asJson,
          "total_voter_count"       -> x.totalVoterCount.asJson,
          "is_closed"               -> x.isClosed.asJson,
          "is_anonymous"            -> x.isAnonymous.asJson,
          "type"                    -> x.`type`.asJson,
          "allows_multiple_answers" -> x.allowsMultipleAnswers.asJson,
          "correct_option_id"       -> x.correctOptionId.asJson,
          "explanation"             -> x.explanation.asJson,
          "explanation_entities"    -> x.explanationEntities.asJson,
          "open_period"             -> x.openPeriod.asJson,
          "close_date"              -> x.closeDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pollDecoder: Decoder[Poll] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[String]("id")
        _question              <- h.get[String]("question")
        _questionEntities      <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("question_entities")(List.empty)
        _options               <- h.getOrElse[List[PollOption]]("options")(List.empty)
        _totalVoterCount       <- h.get[Int]("total_voter_count")
        _isClosed              <- h.get[Boolean]("is_closed")
        _isAnonymous           <- h.get[Boolean]("is_anonymous")
        _type                  <- h.get[String]("type")
        _allowsMultipleAnswers <- h.get[Boolean]("allows_multiple_answers")
        _correctOptionId       <- h.get[Option[Int]]("correct_option_id")
        _explanation           <- h.get[Option[String]]("explanation")
        _explanationEntities   <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("explanation_entities")(List.empty)
        _openPeriod            <- h.get[Option[Int]]("open_period")
        _closeDate             <- h.get[Option[Int]]("close_date")
      } yield {
        Poll(
          id = _id,
          question = _question,
          questionEntities = _questionEntities,
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

  implicit lazy val pollanswerEncoder: Encoder[PollAnswer] =
    (x: PollAnswer) => {
      Json.fromFields(
        List(
          "poll_id"    -> x.pollId.asJson,
          "voter_chat" -> x.voterChat.asJson,
          "user"       -> x.user.asJson,
          "option_ids" -> x.optionIds.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pollanswerDecoder: Decoder[PollAnswer] =
    Decoder.instance { h =>
      for {
        _pollId    <- h.get[String]("poll_id")
        _voterChat <- h.get[Option[Chat]]("voter_chat")
        _user      <- h.get[Option[User]]("user")
        _optionIds <- h.getOrElse[List[Int]]("option_ids")(List.empty)
      } yield {
        PollAnswer(pollId = _pollId, voterChat = _voterChat, user = _user, optionIds = _optionIds)
      }
    }

  implicit lazy val polloptionEncoder: Encoder[PollOption] =
    (x: PollOption) => {
      Json.fromFields(
        List(
          "text"          -> x.text.asJson,
          "text_entities" -> x.textEntities.asJson,
          "voter_count"   -> x.voterCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val polloptionDecoder: Decoder[PollOption] =
    Decoder.instance { h =>
      for {
        _text         <- h.get[String]("text")
        _textEntities <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("text_entities")(List.empty)
        _voterCount   <- h.get[Int]("voter_count")
      } yield {
        PollOption(text = _text, textEntities = _textEntities, voterCount = _voterCount)
      }
    }

  implicit lazy val precheckoutqueryEncoder: Encoder[PreCheckoutQuery] =
    (x: PreCheckoutQuery) => {
      Json.fromFields(
        List(
          "id"                 -> x.id.asJson,
          "from"               -> x.from.asJson,
          "currency"           -> x.currency.asJson,
          "total_amount"       -> x.totalAmount.asJson,
          "invoice_payload"    -> x.invoicePayload.asJson,
          "shipping_option_id" -> x.shippingOptionId.asJson,
          "order_info"         -> x.orderInfo.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val precheckoutqueryDecoder: Decoder[PreCheckoutQuery] =
    Decoder.instance { h =>
      for {
        _id               <- h.get[String]("id")
        _from             <- h.get[User]("from")
        _currency         <- h.get[String]("currency")
        _totalAmount      <- h.get[Int]("total_amount")
        _invoicePayload   <- h.get[String]("invoice_payload")
        _shippingOptionId <- h.get[Option[String]]("shipping_option_id")
        _orderInfo        <- h.get[Option[OrderInfo]]("order_info")
      } yield {
        PreCheckoutQuery(
          id = _id,
          from = _from,
          currency = _currency,
          totalAmount = _totalAmount,
          invoicePayload = _invoicePayload,
          shippingOptionId = _shippingOptionId,
          orderInfo = _orderInfo
        )
      }
    }

  implicit lazy val preparedinlinemessageEncoder: Encoder[PreparedInlineMessage] =
    (x: PreparedInlineMessage) => {
      Json.fromFields(
        List(
          "id"              -> x.id.asJson,
          "expiration_date" -> x.expirationDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val preparedinlinemessageDecoder: Decoder[PreparedInlineMessage] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[String]("id")
        _expirationDate <- h.get[Int]("expiration_date")
      } yield {
        PreparedInlineMessage(id = _id, expirationDate = _expirationDate)
      }
    }

  implicit lazy val proximityalerttriggeredEncoder: Encoder[ProximityAlertTriggered] =
    (x: ProximityAlertTriggered) => {
      Json.fromFields(
        List(
          "traveler" -> x.traveler.asJson,
          "watcher"  -> x.watcher.asJson,
          "distance" -> x.distance.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val proximityalerttriggeredDecoder: Decoder[ProximityAlertTriggered] =
    Decoder.instance { h =>
      for {
        _traveler <- h.get[User]("traveler")
        _watcher  <- h.get[User]("watcher")
        _distance <- h.get[Int]("distance")
      } yield {
        ProximityAlertTriggered(traveler = _traveler, watcher = _watcher, distance = _distance)
      }
    }

  implicit lazy val reactioncountEncoder: Encoder[ReactionCount] =
    (x: ReactionCount) => {
      Json.fromFields(
        List(
          "type"        -> x.`type`.asJson,
          "total_count" -> x.totalCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val reactioncountDecoder: Decoder[ReactionCount] =
    Decoder.instance { h =>
      for {
        _type       <- h.get[iozhik.OpenEnum[ReactionType]]("type")
        _totalCount <- h.get[Int]("total_count")
      } yield {
        ReactionCount(`type` = _type, totalCount = _totalCount)
      }
    }

  implicit lazy val refundedpaymentEncoder: Encoder[RefundedPayment] =
    (x: RefundedPayment) => {
      Json.fromFields(
        List(
          "currency"                   -> x.currency.asJson,
          "total_amount"               -> x.totalAmount.asJson,
          "invoice_payload"            -> x.invoicePayload.asJson,
          "telegram_payment_charge_id" -> x.telegramPaymentChargeId.asJson,
          "provider_payment_charge_id" -> x.providerPaymentChargeId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val refundedpaymentDecoder: Decoder[RefundedPayment] =
    Decoder.instance { h =>
      for {
        _currency                <- h.get[String]("currency")
        _totalAmount             <- h.get[Int]("total_amount")
        _invoicePayload          <- h.get[String]("invoice_payload")
        _telegramPaymentChargeId <- h.get[String]("telegram_payment_charge_id")
        _providerPaymentChargeId <- h.get[Option[String]]("provider_payment_charge_id")
      } yield {
        RefundedPayment(
          currency = _currency,
          totalAmount = _totalAmount,
          invoicePayload = _invoicePayload,
          telegramPaymentChargeId = _telegramPaymentChargeId,
          providerPaymentChargeId = _providerPaymentChargeId
        )
      }
    }

  implicit lazy val replyparametersEncoder: Encoder[ReplyParameters] =
    (x: ReplyParameters) => {
      Json.fromFields(
        List(
          "message_id"                  -> x.messageId.asJson,
          "chat_id"                     -> x.chatId.asJson,
          "allow_sending_without_reply" -> x.allowSendingWithoutReply.asJson,
          "quote"                       -> x.quote.asJson,
          "quote_parse_mode"            -> x.quoteParseMode.asJson,
          "quote_entities"              -> x.quoteEntities.asJson,
          "quote_position"              -> x.quotePosition.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val replyparametersDecoder: Decoder[ReplyParameters] =
    Decoder.instance { h =>
      for {
        _messageId                <- h.get[Int]("message_id")
        _chatId                   <- h.get[Option[ChatId]]("chat_id")
        _allowSendingWithoutReply <- h.get[Option[Boolean]]("allow_sending_without_reply")
        _quote                    <- h.get[Option[String]]("quote")
        _quoteParseMode           <- h.get[Option[ParseMode]]("quote_parse_mode")
        _quoteEntities            <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("quote_entities")(List.empty)
        _quotePosition            <- h.get[Option[Int]]("quote_position")
      } yield {
        ReplyParameters(
          messageId = _messageId,
          chatId = _chatId,
          allowSendingWithoutReply = _allowSendingWithoutReply,
          quote = _quote,
          quoteParseMode = _quoteParseMode,
          quoteEntities = _quoteEntities,
          quotePosition = _quotePosition
        )
      }
    }

  implicit lazy val responseparametersEncoder: Encoder[ResponseParameters] =
    (x: ResponseParameters) => {
      Json.fromFields(
        List(
          "migrate_to_chat_id" -> x.migrateToChatId.asJson,
          "retry_after"        -> x.retryAfter.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val responseparametersDecoder: Decoder[ResponseParameters] =
    Decoder.instance { h =>
      for {
        _migrateToChatId <- h.get[Option[Long]]("migrate_to_chat_id")
        _retryAfter      <- h.get[Option[Int]]("retry_after")
      } yield {
        ResponseParameters(migrateToChatId = _migrateToChatId, retryAfter = _retryAfter)
      }
    }

  implicit lazy val sentwebappmessageEncoder: Encoder[SentWebAppMessage] =
    (x: SentWebAppMessage) => {
      Json.fromFields(
        List(
          "inline_message_id" -> x.inlineMessageId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sentwebappmessageDecoder: Decoder[SentWebAppMessage] =
    Decoder.instance { h =>
      for {
        _inlineMessageId <- h.get[Option[String]]("inline_message_id")
      } yield {
        SentWebAppMessage(inlineMessageId = _inlineMessageId)
      }
    }

  implicit lazy val shareduserEncoder: Encoder[SharedUser] =
    (x: SharedUser) => {
      Json.fromFields(
        List(
          "user_id"    -> x.userId.asJson,
          "first_name" -> x.firstName.asJson,
          "last_name"  -> x.lastName.asJson,
          "username"   -> x.username.asJson,
          "photo"      -> x.photo.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val shareduserDecoder: Decoder[SharedUser] =
    Decoder.instance { h =>
      for {
        _userId    <- h.get[Long]("user_id")
        _firstName <- h.get[Option[String]]("first_name")
        _lastName  <- h.get[Option[String]]("last_name")
        _username  <- h.get[Option[String]]("username")
        _photo     <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
      } yield {
        SharedUser(userId = _userId, firstName = _firstName, lastName = _lastName, username = _username, photo = _photo)
      }
    }

  implicit lazy val shippingaddressEncoder: Encoder[ShippingAddress] =
    (x: ShippingAddress) => {
      Json.fromFields(
        List(
          "country_code" -> x.countryCode.asJson,
          "state"        -> x.state.asJson,
          "city"         -> x.city.asJson,
          "street_line1" -> x.streetLine1.asJson,
          "street_line2" -> x.streetLine2.asJson,
          "post_code"    -> x.postCode.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val shippingaddressDecoder: Decoder[ShippingAddress] =
    Decoder.instance { h =>
      for {
        _countryCode <- h.get[String]("country_code")
        _state       <- h.get[String]("state")
        _city        <- h.get[String]("city")
        _streetLine1 <- h.get[String]("street_line1")
        _streetLine2 <- h.get[String]("street_line2")
        _postCode    <- h.get[String]("post_code")
      } yield {
        ShippingAddress(
          countryCode = _countryCode,
          state = _state,
          city = _city,
          streetLine1 = _streetLine1,
          streetLine2 = _streetLine2,
          postCode = _postCode
        )
      }
    }

  implicit lazy val shippingoptionEncoder: Encoder[ShippingOption] =
    (x: ShippingOption) => {
      Json.fromFields(
        List(
          "id"     -> x.id.asJson,
          "title"  -> x.title.asJson,
          "prices" -> x.prices.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val shippingoptionDecoder: Decoder[ShippingOption] =
    Decoder.instance { h =>
      for {
        _id     <- h.get[String]("id")
        _title  <- h.get[String]("title")
        _prices <- h.getOrElse[List[LabeledPrice]]("prices")(List.empty)
      } yield {
        ShippingOption(id = _id, title = _title, prices = _prices)
      }
    }

  implicit lazy val shippingqueryEncoder: Encoder[ShippingQuery] =
    (x: ShippingQuery) => {
      Json.fromFields(
        List(
          "id"               -> x.id.asJson,
          "from"             -> x.from.asJson,
          "invoice_payload"  -> x.invoicePayload.asJson,
          "shipping_address" -> x.shippingAddress.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val shippingqueryDecoder: Decoder[ShippingQuery] =
    Decoder.instance { h =>
      for {
        _id              <- h.get[String]("id")
        _from            <- h.get[User]("from")
        _invoicePayload  <- h.get[String]("invoice_payload")
        _shippingAddress <- h.get[ShippingAddress]("shipping_address")
      } yield {
        ShippingQuery(id = _id, from = _from, invoicePayload = _invoicePayload, shippingAddress = _shippingAddress)
      }
    }

  implicit lazy val startransactionEncoder: Encoder[StarTransaction] =
    (x: StarTransaction) => {
      Json.fromFields(
        List(
          "id"              -> x.id.asJson,
          "amount"          -> x.amount.asJson,
          "nanostar_amount" -> x.nanostarAmount.asJson,
          "date"            -> x.date.asJson,
          "source"          -> x.source.asJson,
          "receiver"        -> x.receiver.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val startransactionDecoder: Decoder[StarTransaction] =
    Decoder.instance { h =>
      for {
        _id             <- h.get[String]("id")
        _amount         <- h.get[Int]("amount")
        _nanostarAmount <- h.get[Option[Int]]("nanostar_amount")
        _date           <- h.get[Int]("date")
        _source         <- h.get[Option[iozhik.OpenEnum[TransactionPartner]]]("source")
        _receiver       <- h.get[Option[iozhik.OpenEnum[TransactionPartner]]]("receiver")
      } yield {
        StarTransaction(
          id = _id,
          amount = _amount,
          nanostarAmount = _nanostarAmount,
          date = _date,
          source = _source,
          receiver = _receiver
        )
      }
    }

  implicit lazy val startransactionsEncoder: Encoder[StarTransactions] =
    (x: StarTransactions) => {
      Json.fromFields(
        List(
          "transactions" -> x.transactions.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val startransactionsDecoder: Decoder[StarTransactions] =
    Decoder.instance { h =>
      for {
        _transactions <- h.getOrElse[List[StarTransaction]]("transactions")(List.empty)
      } yield {
        StarTransactions(transactions = _transactions)
      }
    }

  implicit lazy val stickerEncoder: Encoder[Sticker] =
    (x: Sticker) => {
      Json.fromFields(
        List(
          "file_id"           -> x.fileId.asJson,
          "file_unique_id"    -> x.fileUniqueId.asJson,
          "type"              -> x.`type`.asJson,
          "width"             -> x.width.asJson,
          "height"            -> x.height.asJson,
          "is_animated"       -> x.isAnimated.asJson,
          "is_video"          -> x.isVideo.asJson,
          "thumbnail"         -> x.thumbnail.asJson,
          "emoji"             -> x.emoji.asJson,
          "set_name"          -> x.setName.asJson,
          "premium_animation" -> x.premiumAnimation.asJson,
          "mask_position"     -> x.maskPosition.asJson,
          "custom_emoji_id"   -> x.customEmojiId.asJson,
          "needs_repainting"  -> x.needsRepainting.asJson,
          "file_size"         -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stickerDecoder: Decoder[Sticker] =
    Decoder.instance { h =>
      for {
        _fileId           <- h.get[String]("file_id")
        _fileUniqueId     <- h.get[String]("file_unique_id")
        _type             <- h.get[String]("type")
        _width            <- h.get[Int]("width")
        _height           <- h.get[Int]("height")
        _isAnimated       <- h.get[Boolean]("is_animated")
        _isVideo          <- h.get[Boolean]("is_video")
        _thumbnail        <- h.get[Option[PhotoSize]]("thumbnail")
        _emoji            <- h.get[Option[String]]("emoji")
        _setName          <- h.get[Option[String]]("set_name")
        _premiumAnimation <- h.get[Option[File]]("premium_animation")
        _maskPosition     <- h.get[Option[MaskPosition]]("mask_position")
        _customEmojiId    <- h.get[Option[String]]("custom_emoji_id")
        _needsRepainting  <- h.get[Option[Boolean]]("needs_repainting")
        _fileSize         <- h.get[Option[Long]]("file_size")
      } yield {
        Sticker(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          `type` = _type,
          width = _width,
          height = _height,
          isAnimated = _isAnimated,
          isVideo = _isVideo,
          thumbnail = _thumbnail,
          emoji = _emoji,
          setName = _setName,
          premiumAnimation = _premiumAnimation,
          maskPosition = _maskPosition,
          customEmojiId = _customEmojiId,
          needsRepainting = _needsRepainting,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val stickersetEncoder: Encoder[StickerSet] =
    (x: StickerSet) => {
      Json.fromFields(
        List(
          "name"         -> x.name.asJson,
          "title"        -> x.title.asJson,
          "sticker_type" -> x.stickerType.asJson,
          "stickers"     -> x.stickers.asJson,
          "thumbnail"    -> x.thumbnail.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stickersetDecoder: Decoder[StickerSet] =
    Decoder.instance { h =>
      for {
        _name        <- h.get[String]("name")
        _title       <- h.get[String]("title")
        _stickerType <- h.get[String]("sticker_type")
        _stickers    <- h.getOrElse[List[Sticker]]("stickers")(List.empty)
        _thumbnail   <- h.get[Option[PhotoSize]]("thumbnail")
      } yield {
        StickerSet(
          name = _name,
          title = _title,
          stickerType = _stickerType,
          stickers = _stickers,
          thumbnail = _thumbnail
        )
      }
    }

  implicit lazy val storyEncoder: Encoder[Story] =
    (x: Story) => {
      Json.fromFields(
        List(
          "chat" -> x.chat.asJson,
          "id"   -> x.id.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val storyDecoder: Decoder[Story] =
    Decoder.instance { h =>
      for {
        _chat <- h.get[Chat]("chat")
        _id   <- h.get[Int]("id")
      } yield {
        Story(chat = _chat, id = _id)
      }
    }

  implicit lazy val successfulpaymentEncoder: Encoder[SuccessfulPayment] =
    (x: SuccessfulPayment) => {
      Json.fromFields(
        List(
          "currency"                     -> x.currency.asJson,
          "total_amount"                 -> x.totalAmount.asJson,
          "invoice_payload"              -> x.invoicePayload.asJson,
          "subscription_expiration_date" -> x.subscriptionExpirationDate.asJson,
          "is_recurring"                 -> x.isRecurring.asJson,
          "is_first_recurring"           -> x.isFirstRecurring.asJson,
          "shipping_option_id"           -> x.shippingOptionId.asJson,
          "order_info"                   -> x.orderInfo.asJson,
          "telegram_payment_charge_id"   -> x.telegramPaymentChargeId.asJson,
          "provider_payment_charge_id"   -> x.providerPaymentChargeId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val successfulpaymentDecoder: Decoder[SuccessfulPayment] =
    Decoder.instance { h =>
      for {
        _currency                   <- h.get[String]("currency")
        _totalAmount                <- h.get[Int]("total_amount")
        _invoicePayload             <- h.get[String]("invoice_payload")
        _subscriptionExpirationDate <- h.get[Option[Int]]("subscription_expiration_date")
        _isRecurring                <- h.get[Option[Boolean]]("is_recurring")
        _isFirstRecurring           <- h.get[Option[Boolean]]("is_first_recurring")
        _shippingOptionId           <- h.get[Option[String]]("shipping_option_id")
        _orderInfo                  <- h.get[Option[OrderInfo]]("order_info")
        _telegramPaymentChargeId    <- h.get[String]("telegram_payment_charge_id")
        _providerPaymentChargeId    <- h.get[String]("provider_payment_charge_id")
      } yield {
        SuccessfulPayment(
          currency = _currency,
          totalAmount = _totalAmount,
          invoicePayload = _invoicePayload,
          subscriptionExpirationDate = _subscriptionExpirationDate,
          isRecurring = _isRecurring,
          isFirstRecurring = _isFirstRecurring,
          shippingOptionId = _shippingOptionId,
          orderInfo = _orderInfo,
          telegramPaymentChargeId = _telegramPaymentChargeId,
          providerPaymentChargeId = _providerPaymentChargeId
        )
      }
    }

  implicit lazy val switchinlinequerychosenchatEncoder: Encoder[SwitchInlineQueryChosenChat] =
    (x: SwitchInlineQueryChosenChat) => {
      Json.fromFields(
        List(
          "query"               -> x.query.asJson,
          "allow_user_chats"    -> x.allowUserChats.asJson,
          "allow_bot_chats"     -> x.allowBotChats.asJson,
          "allow_group_chats"   -> x.allowGroupChats.asJson,
          "allow_channel_chats" -> x.allowChannelChats.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val switchinlinequerychosenchatDecoder: Decoder[SwitchInlineQueryChosenChat] =
    Decoder.instance { h =>
      for {
        _query             <- h.get[Option[String]]("query")
        _allowUserChats    <- h.get[Option[Boolean]]("allow_user_chats")
        _allowBotChats     <- h.get[Option[Boolean]]("allow_bot_chats")
        _allowGroupChats   <- h.get[Option[Boolean]]("allow_group_chats")
        _allowChannelChats <- h.get[Option[Boolean]]("allow_channel_chats")
      } yield {
        SwitchInlineQueryChosenChat(
          query = _query,
          allowUserChats = _allowUserChats,
          allowBotChats = _allowBotChats,
          allowGroupChats = _allowGroupChats,
          allowChannelChats = _allowChannelChats
        )
      }
    }

  implicit lazy val textquoteEncoder: Encoder[TextQuote] =
    (x: TextQuote) => {
      Json.fromFields(
        List(
          "text"      -> x.text.asJson,
          "entities"  -> x.entities.asJson,
          "position"  -> x.position.asJson,
          "is_manual" -> x.isManual.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val textquoteDecoder: Decoder[TextQuote] =
    Decoder.instance { h =>
      for {
        _text     <- h.get[String]("text")
        _entities <- h.getOrElse[List[iozhik.OpenEnum[MessageEntity]]]("entities")(List.empty)
        _position <- h.get[Int]("position")
        _isManual <- h.get[Option[Boolean]]("is_manual")
      } yield {
        TextQuote(text = _text, entities = _entities, position = _position, isManual = _isManual)
      }
    }

  implicit lazy val updateEncoder: Encoder[Update] =
    (x: Update) => {
      Json.fromFields(
        List(
          "update_id"                 -> x.updateId.asJson,
          "message"                   -> x.message.asJson,
          "edited_message"            -> x.editedMessage.asJson,
          "channel_post"              -> x.channelPost.asJson,
          "edited_channel_post"       -> x.editedChannelPost.asJson,
          "business_connection"       -> x.businessConnection.asJson,
          "business_message"          -> x.businessMessage.asJson,
          "edited_business_message"   -> x.editedBusinessMessage.asJson,
          "deleted_business_messages" -> x.deletedBusinessMessages.asJson,
          "message_reaction"          -> x.messageReaction.asJson,
          "message_reaction_count"    -> x.messageReactionCount.asJson,
          "inline_query"              -> x.inlineQuery.asJson,
          "chosen_inline_result"      -> x.chosenInlineResult.asJson,
          "callback_query"            -> x.callbackQuery.asJson,
          "shipping_query"            -> x.shippingQuery.asJson,
          "pre_checkout_query"        -> x.preCheckoutQuery.asJson,
          "purchased_paid_media"      -> x.purchasedPaidMedia.asJson,
          "poll"                      -> x.poll.asJson,
          "poll_answer"               -> x.pollAnswer.asJson,
          "my_chat_member"            -> x.myChatMember.asJson,
          "chat_member"               -> x.chatMember.asJson,
          "chat_join_request"         -> x.chatJoinRequest.asJson,
          "chat_boost"                -> x.chatBoost.asJson,
          "removed_chat_boost"        -> x.removedChatBoost.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val updateDecoder: Decoder[Update] =
    Decoder.instance { h =>
      for {
        _updateId                <- h.get[Int]("update_id")
        _message                 <- h.get[Option[Message]]("message")
        _editedMessage           <- h.get[Option[Message]]("edited_message")
        _channelPost             <- h.get[Option[Message]]("channel_post")
        _editedChannelPost       <- h.get[Option[Message]]("edited_channel_post")
        _businessConnection      <- h.get[Option[BusinessConnection]]("business_connection")
        _businessMessage         <- h.get[Option[Message]]("business_message")
        _editedBusinessMessage   <- h.get[Option[Message]]("edited_business_message")
        _deletedBusinessMessages <- h.get[Option[BusinessMessagesDeleted]]("deleted_business_messages")
        _messageReaction         <- h.get[Option[MessageReactionUpdated]]("message_reaction")
        _messageReactionCount    <- h.get[Option[MessageReactionCountUpdated]]("message_reaction_count")
        _inlineQuery             <- h.get[Option[InlineQuery]]("inline_query")
        _chosenInlineResult      <- h.get[Option[ChosenInlineResult]]("chosen_inline_result")
        _callbackQuery           <- h.get[Option[CallbackQuery]]("callback_query")
        _shippingQuery           <- h.get[Option[ShippingQuery]]("shipping_query")
        _preCheckoutQuery        <- h.get[Option[PreCheckoutQuery]]("pre_checkout_query")
        _purchasedPaidMedia      <- h.get[Option[PaidMediaPurchased]]("purchased_paid_media")
        _poll                    <- h.get[Option[Poll]]("poll")
        _pollAnswer              <- h.get[Option[PollAnswer]]("poll_answer")
        _myChatMember            <- h.get[Option[ChatMemberUpdated]]("my_chat_member")
        _chatMember              <- h.get[Option[ChatMemberUpdated]]("chat_member")
        _chatJoinRequest         <- h.get[Option[ChatJoinRequest]]("chat_join_request")
        _chatBoost               <- h.get[Option[ChatBoostUpdated]]("chat_boost")
        _removedChatBoost        <- h.get[Option[ChatBoostRemoved]]("removed_chat_boost")
      } yield {
        Update(
          updateId = _updateId,
          message = _message,
          editedMessage = _editedMessage,
          channelPost = _channelPost,
          editedChannelPost = _editedChannelPost,
          businessConnection = _businessConnection,
          businessMessage = _businessMessage,
          editedBusinessMessage = _editedBusinessMessage,
          deletedBusinessMessages = _deletedBusinessMessages,
          messageReaction = _messageReaction,
          messageReactionCount = _messageReactionCount,
          inlineQuery = _inlineQuery,
          chosenInlineResult = _chosenInlineResult,
          callbackQuery = _callbackQuery,
          shippingQuery = _shippingQuery,
          preCheckoutQuery = _preCheckoutQuery,
          purchasedPaidMedia = _purchasedPaidMedia,
          poll = _poll,
          pollAnswer = _pollAnswer,
          myChatMember = _myChatMember,
          chatMember = _chatMember,
          chatJoinRequest = _chatJoinRequest,
          chatBoost = _chatBoost,
          removedChatBoost = _removedChatBoost
        )
      }
    }

  implicit lazy val userEncoder: Encoder[User] =
    (x: User) => {
      Json.fromFields(
        List(
          "id"                          -> x.id.asJson,
          "is_bot"                      -> x.isBot.asJson,
          "first_name"                  -> x.firstName.asJson,
          "last_name"                   -> x.lastName.asJson,
          "username"                    -> x.username.asJson,
          "language_code"               -> x.languageCode.asJson,
          "is_premium"                  -> x.isPremium.asJson,
          "added_to_attachment_menu"    -> x.addedToAttachmentMenu.asJson,
          "can_join_groups"             -> x.canJoinGroups.asJson,
          "can_read_all_group_messages" -> x.canReadAllGroupMessages.asJson,
          "supports_inline_queries"     -> x.supportsInlineQueries.asJson,
          "can_connect_to_business"     -> x.canConnectToBusiness.asJson,
          "has_main_web_app"            -> x.hasMainWebApp.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val userDecoder: Decoder[User] =
    Decoder.instance { h =>
      for {
        _id                      <- h.get[Long]("id")
        _isBot                   <- h.get[Boolean]("is_bot")
        _firstName               <- h.get[String]("first_name")
        _lastName                <- h.get[Option[String]]("last_name")
        _username                <- h.get[Option[String]]("username")
        _languageCode            <- h.get[Option[String]]("language_code")
        _isPremium               <- h.get[Option[Boolean]]("is_premium")
        _addedToAttachmentMenu   <- h.get[Option[Boolean]]("added_to_attachment_menu")
        _canJoinGroups           <- h.get[Option[Boolean]]("can_join_groups")
        _canReadAllGroupMessages <- h.get[Option[Boolean]]("can_read_all_group_messages")
        _supportsInlineQueries   <- h.get[Option[Boolean]]("supports_inline_queries")
        _canConnectToBusiness    <- h.get[Option[Boolean]]("can_connect_to_business")
        _hasMainWebApp           <- h.get[Option[Boolean]]("has_main_web_app")
      } yield {
        User(
          id = _id,
          isBot = _isBot,
          firstName = _firstName,
          lastName = _lastName,
          username = _username,
          languageCode = _languageCode,
          isPremium = _isPremium,
          addedToAttachmentMenu = _addedToAttachmentMenu,
          canJoinGroups = _canJoinGroups,
          canReadAllGroupMessages = _canReadAllGroupMessages,
          supportsInlineQueries = _supportsInlineQueries,
          canConnectToBusiness = _canConnectToBusiness,
          hasMainWebApp = _hasMainWebApp
        )
      }
    }

  implicit lazy val userchatboostsEncoder: Encoder[UserChatBoosts] =
    (x: UserChatBoosts) => {
      Json.fromFields(
        List(
          "boosts" -> x.boosts.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val userchatboostsDecoder: Decoder[UserChatBoosts] =
    Decoder.instance { h =>
      for {
        _boosts <- h.getOrElse[List[ChatBoost]]("boosts")(List.empty)
      } yield {
        UserChatBoosts(boosts = _boosts)
      }
    }

  implicit lazy val userprofilephotosEncoder: Encoder[UserProfilePhotos] =
    (x: UserProfilePhotos) => {
      Json.fromFields(
        List(
          "total_count" -> x.totalCount.asJson,
          "photos"      -> x.photos.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val userprofilephotosDecoder: Decoder[UserProfilePhotos] =
    Decoder.instance { h =>
      for {
        _totalCount <- h.get[Int]("total_count")
        _photos     <- h.getOrElse[List[List[PhotoSize]]]("photos")(List.empty)
      } yield {
        UserProfilePhotos(totalCount = _totalCount, photos = _photos)
      }
    }

  implicit lazy val userssharedEncoder: Encoder[UsersShared] =
    (x: UsersShared) => {
      Json.fromFields(
        List(
          "request_id" -> x.requestId.asJson,
          "users"      -> x.users.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val userssharedDecoder: Decoder[UsersShared] =
    Decoder.instance { h =>
      for {
        _requestId <- h.get[Int]("request_id")
        _users     <- h.getOrElse[List[SharedUser]]("users")(List.empty)
      } yield {
        UsersShared(requestId = _requestId, users = _users)
      }
    }

  implicit lazy val venueEncoder: Encoder[Venue] =
    (x: Venue) => {
      Json.fromFields(
        List(
          "location"          -> x.location.asJson,
          "title"             -> x.title.asJson,
          "address"           -> x.address.asJson,
          "foursquare_id"     -> x.foursquareId.asJson,
          "foursquare_type"   -> x.foursquareType.asJson,
          "google_place_id"   -> x.googlePlaceId.asJson,
          "google_place_type" -> x.googlePlaceType.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val venueDecoder: Decoder[Venue] =
    Decoder.instance { h =>
      for {
        _location        <- h.get[Location]("location")
        _title           <- h.get[String]("title")
        _address         <- h.get[String]("address")
        _foursquareId    <- h.get[Option[String]]("foursquare_id")
        _foursquareType  <- h.get[Option[String]]("foursquare_type")
        _googlePlaceId   <- h.get[Option[String]]("google_place_id")
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

  implicit lazy val videoEncoder: Encoder[Video] =
    (x: Video) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width"          -> x.width.asJson,
          "height"         -> x.height.asJson,
          "duration"       -> x.duration.asJson,
          "thumbnail"      -> x.thumbnail.asJson,
          "file_name"      -> x.fileName.asJson,
          "mime_type"      -> x.mimeType.asJson,
          "file_size"      -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val videoDecoder: Decoder[Video] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _width        <- h.get[Int]("width")
        _height       <- h.get[Int]("height")
        _duration     <- h.get[Int]("duration")
        _thumbnail    <- h.get[Option[PhotoSize]]("thumbnail")
        _fileName     <- h.get[Option[String]]("file_name")
        _mimeType     <- h.get[Option[String]]("mime_type")
        _fileSize     <- h.get[Option[Long]]("file_size")
      } yield {
        Video(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          width = _width,
          height = _height,
          duration = _duration,
          thumbnail = _thumbnail,
          fileName = _fileName,
          mimeType = _mimeType,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val videochatendedEncoder: Encoder[VideoChatEnded] =
    (x: VideoChatEnded) => {
      Json.fromFields(
        List(
          "duration" -> x.duration.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val videochatendedDecoder: Decoder[VideoChatEnded] =
    Decoder.instance { h =>
      for {
        _duration <- h.get[Int]("duration")
      } yield {
        VideoChatEnded(duration = _duration)
      }
    }

  implicit lazy val videochatparticipantsinvitedEncoder: Encoder[VideoChatParticipantsInvited] =
    (x: VideoChatParticipantsInvited) => {
      Json.fromFields(
        List(
          "users" -> x.users.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val videochatparticipantsinvitedDecoder: Decoder[VideoChatParticipantsInvited] =
    Decoder.instance { h =>
      for {
        _users <- h.getOrElse[List[User]]("users")(List.empty)
      } yield {
        VideoChatParticipantsInvited(users = _users)
      }
    }

  implicit lazy val videochatscheduledEncoder: Encoder[VideoChatScheduled] =
    (x: VideoChatScheduled) => {
      Json.fromFields(
        List(
          "start_date" -> x.startDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val videochatscheduledDecoder: Decoder[VideoChatScheduled] =
    Decoder.instance { h =>
      for {
        _startDate <- h.get[Int]("start_date")
      } yield {
        VideoChatScheduled(startDate = _startDate)
      }
    }

  implicit lazy val videochatstartedEncoder: Encoder[VideoChatStarted.type] = (_: VideoChatStarted.type) => ().asJson
  implicit lazy val videochatstartedDecoder: Decoder[VideoChatStarted.type] = (_: HCursor) => Right(VideoChatStarted)

  implicit lazy val videonoteEncoder: Encoder[VideoNote] =
    (x: VideoNote) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "length"         -> x.length.asJson,
          "duration"       -> x.duration.asJson,
          "thumbnail"      -> x.thumbnail.asJson,
          "file_size"      -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val videonoteDecoder: Decoder[VideoNote] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _length       <- h.get[Int]("length")
        _duration     <- h.get[Int]("duration")
        _thumbnail    <- h.get[Option[PhotoSize]]("thumbnail")
        _fileSize     <- h.get[Option[Long]]("file_size")
      } yield {
        VideoNote(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          length = _length,
          duration = _duration,
          thumbnail = _thumbnail,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val voiceEncoder: Encoder[Voice] =
    (x: Voice) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "duration"       -> x.duration.asJson,
          "mime_type"      -> x.mimeType.asJson,
          "file_size"      -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val voiceDecoder: Decoder[Voice] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _duration     <- h.get[Int]("duration")
        _mimeType     <- h.get[Option[String]]("mime_type")
        _fileSize     <- h.get[Option[Long]]("file_size")
      } yield {
        Voice(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          duration = _duration,
          mimeType = _mimeType,
          fileSize = _fileSize
        )
      }
    }

  implicit lazy val webappdataEncoder: Encoder[WebAppData] =
    (x: WebAppData) => {
      Json.fromFields(
        List(
          "data"        -> x.data.asJson,
          "button_text" -> x.buttonText.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val webappdataDecoder: Decoder[WebAppData] =
    Decoder.instance { h =>
      for {
        _data       <- h.get[String]("data")
        _buttonText <- h.get[String]("button_text")
      } yield {
        WebAppData(data = _data, buttonText = _buttonText)
      }
    }

  implicit lazy val webappinfoEncoder: Encoder[WebAppInfo] =
    (x: WebAppInfo) => {
      Json.fromFields(
        List(
          "url" -> x.url.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val webappinfoDecoder: Decoder[WebAppInfo] =
    Decoder.instance { h =>
      for {
        _url <- h.get[String]("url")
      } yield {
        WebAppInfo(url = _url)
      }
    }

  implicit lazy val webhookinfoEncoder: Encoder[WebhookInfo] =
    (x: WebhookInfo) => {
      Json.fromFields(
        List(
          "url"                             -> x.url.asJson,
          "has_custom_certificate"          -> x.hasCustomCertificate.asJson,
          "pending_update_count"            -> x.pendingUpdateCount.asJson,
          "ip_address"                      -> x.ipAddress.asJson,
          "last_error_date"                 -> x.lastErrorDate.asJson,
          "last_error_message"              -> x.lastErrorMessage.asJson,
          "last_synchronization_error_date" -> x.lastSynchronizationErrorDate.asJson,
          "max_connections"                 -> x.maxConnections.asJson,
          "allowed_updates"                 -> x.allowedUpdates.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val webhookinfoDecoder: Decoder[WebhookInfo] =
    Decoder.instance { h =>
      for {
        _url                          <- h.get[String]("url")
        _hasCustomCertificate         <- h.get[Boolean]("has_custom_certificate")
        _pendingUpdateCount           <- h.get[Int]("pending_update_count")
        _ipAddress                    <- h.get[Option[String]]("ip_address")
        _lastErrorDate                <- h.get[Option[Int]]("last_error_date")
        _lastErrorMessage             <- h.get[Option[String]]("last_error_message")
        _lastSynchronizationErrorDate <- h.get[Option[Int]]("last_synchronization_error_date")
        _maxConnections               <- h.get[Option[Int]]("max_connections")
        _allowedUpdates               <- h.getOrElse[List[String]]("allowed_updates")(List.empty)
      } yield {
        WebhookInfo(
          url = _url,
          hasCustomCertificate = _hasCustomCertificate,
          pendingUpdateCount = _pendingUpdateCount,
          ipAddress = _ipAddress,
          lastErrorDate = _lastErrorDate,
          lastErrorMessage = _lastErrorMessage,
          lastSynchronizationErrorDate = _lastSynchronizationErrorDate,
          maxConnections = _maxConnections,
          allowedUpdates = _allowedUpdates
        )
      }
    }

  implicit lazy val writeaccessallowedEncoder: Encoder[WriteAccessAllowed] =
    (x: WriteAccessAllowed) => {
      Json.fromFields(
        List(
          "from_request"         -> x.fromRequest.asJson,
          "web_app_name"         -> x.webAppName.asJson,
          "from_attachment_menu" -> x.fromAttachmentMenu.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val writeaccessallowedDecoder: Decoder[WriteAccessAllowed] =
    Decoder.instance { h =>
      for {
        _fromRequest        <- h.get[Option[Boolean]]("from_request")
        _webAppName         <- h.get[Option[String]]("web_app_name")
        _fromAttachmentMenu <- h.get[Option[Boolean]]("from_attachment_menu")
      } yield {
        WriteAccessAllowed(
          fromRequest = _fromRequest,
          webAppName = _webAppName,
          fromAttachmentMenu = _fromAttachmentMenu
        )
      }
    }

}
