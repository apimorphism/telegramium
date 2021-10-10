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

  implicit lazy val emojidiceEncoder: Encoder[EmojiDice.type]               = (_: EmojiDice.type) => "🎲".asJson
  implicit lazy val emojidiceDecoder: Decoder[EmojiDice.type]               = Decoder[String].map(_ => EmojiDice)
  implicit lazy val emojidartsEncoder: Encoder[EmojiDarts.type]             = (_: EmojiDarts.type) => "🎯".asJson
  implicit lazy val emojidartsDecoder: Decoder[EmojiDarts.type]             = Decoder[String].map(_ => EmojiDarts)
  implicit lazy val emojibasketballEncoder: Encoder[EmojiBasketball.type]   = (_: EmojiBasketball.type) => "🏀".asJson
  implicit lazy val emojibasketballDecoder: Decoder[EmojiBasketball.type]   = Decoder[String].map(_ => EmojiBasketball)
  implicit lazy val emojifootballEncoder: Encoder[EmojiFootball.type]       = (_: EmojiFootball.type) => "⚽".asJson
  implicit lazy val emojifootballDecoder: Decoder[EmojiFootball.type]       = Decoder[String].map(_ => EmojiFootball)
  implicit lazy val emojislotmachineEncoder: Encoder[EmojiSlotMachine.type] = (_: EmojiSlotMachine.type) => "🎰".asJson
  implicit lazy val emojislotmachineDecoder: Decoder[EmojiSlotMachine.type] = Decoder[String].map(_ => EmojiSlotMachine)
  implicit lazy val emojibowlingEncoder: Encoder[EmojiBowling.type]         = (_: EmojiBowling.type) => "🎳".asJson
  implicit lazy val emojibowlingDecoder: Decoder[EmojiBowling.type]         = Decoder[String].map(_ => EmojiBowling)

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

  implicit lazy val markdownEncoder: Encoder[Markdown.type]   = (_: Markdown.type) => "Markdown".asJson
  implicit lazy val markdownDecoder: Decoder[Markdown.type]   = Decoder[String].map(_ => Markdown)
  implicit lazy val markdown2Encoder: Encoder[Markdown2.type] = (_: Markdown2.type) => "MarkdownV2".asJson
  implicit lazy val markdown2Decoder: Decoder[Markdown2.type] = Decoder[String].map(_ => Markdown2)
  implicit lazy val htmlEncoder: Encoder[Html.type]           = (_: Html.type) => "HTML".asJson
  implicit lazy val htmlDecoder: Decoder[Html.type]           = Decoder[String].map(_ => Html)

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
        _resizeKeyboard        <- h.get[Option[Boolean]]("resize_keyboard")
        _oneTimeKeyboard       <- h.get[Option[Boolean]]("one_time_keyboard")
        _inputFieldPlaceholder <- h.get[Option[String]]("input_field_placeholder")
        _selective             <- h.get[Option[Boolean]]("selective")
      } yield {
        ReplyKeyboardMarkup(
          keyboard = _keyboard,
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

  implicit lazy val chatmemberEncoder: Encoder[ChatMember] = {
    case x: ChatMemberOwner         => x.asJson
    case x: ChatMemberAdministrator => x.asJson
    case x: ChatMemberLeft          => x.asJson
    case x: ChatMemberMember        => x.asJson
    case x: ChatMemberBanned        => x.asJson
    case x: ChatMemberRestricted    => x.asJson
  }

  implicit lazy val chatmemberDecoder: Decoder[ChatMember] = {
    List[Decoder[ChatMember]](
      chatmemberownerDecoder.widen,
      chatmemberadministratorDecoder.widen,
      chatmemberleftDecoder.widen,
      chatmembermemberDecoder.widen,
      chatmemberbannedDecoder.widen,
      chatmemberrestrictedDecoder.widen
    ).reduceLeft(_ or _)
  }

  implicit lazy val chatmemberownerEncoder: Encoder[ChatMemberOwner] =
    (x: ChatMemberOwner) => {
      Json.fromFields(
        List(
          "status"       -> x.status.asJson,
          "user"         -> x.user.asJson,
          "is_anonymous" -> x.isAnonymous.asJson,
          "custom_title" -> x.customTitle.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberownerDecoder: Decoder[ChatMemberOwner] =
    Decoder.instance { h =>
      for {
        _status      <- h.get[String]("status")
        _user        <- h.get[User]("user")
        _isAnonymous <- h.get[Boolean]("is_anonymous")
        _customTitle <- h.get[Option[String]]("custom_title")
      } yield {
        ChatMemberOwner(status = _status, user = _user, isAnonymous = _isAnonymous, customTitle = _customTitle)
      }
    }

  implicit lazy val chatmemberadministratorEncoder: Encoder[ChatMemberAdministrator] =
    (x: ChatMemberAdministrator) => {
      Json.fromFields(
        List(
          "status"                 -> x.status.asJson,
          "user"                   -> x.user.asJson,
          "can_be_edited"          -> x.canBeEdited.asJson,
          "is_anonymous"           -> x.isAnonymous.asJson,
          "can_manage_chat"        -> x.canManageChat.asJson,
          "can_delete_messages"    -> x.canDeleteMessages.asJson,
          "can_manage_voice_chats" -> x.canManageVoiceChats.asJson,
          "can_restrict_members"   -> x.canRestrictMembers.asJson,
          "can_promote_members"    -> x.canPromoteMembers.asJson,
          "can_change_info"        -> x.canChangeInfo.asJson,
          "can_invite_users"       -> x.canInviteUsers.asJson,
          "can_post_messages"      -> x.canPostMessages.asJson,
          "can_edit_messages"      -> x.canEditMessages.asJson,
          "can_pin_messages"       -> x.canPinMessages.asJson,
          "custom_title"           -> x.customTitle.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberadministratorDecoder: Decoder[ChatMemberAdministrator] =
    Decoder.instance { h =>
      for {
        _status              <- h.get[String]("status")
        _user                <- h.get[User]("user")
        _canBeEdited         <- h.get[Boolean]("can_be_edited")
        _isAnonymous         <- h.get[Boolean]("is_anonymous")
        _canManageChat       <- h.get[Boolean]("can_manage_chat")
        _canDeleteMessages   <- h.get[Boolean]("can_delete_messages")
        _canManageVoiceChats <- h.get[Boolean]("can_manage_voice_chats")
        _canRestrictMembers  <- h.get[Boolean]("can_restrict_members")
        _canPromoteMembers   <- h.get[Boolean]("can_promote_members")
        _canChangeInfo       <- h.get[Boolean]("can_change_info")
        _canInviteUsers      <- h.get[Boolean]("can_invite_users")
        _canPostMessages     <- h.get[Option[Boolean]]("can_post_messages")
        _canEditMessages     <- h.get[Option[Boolean]]("can_edit_messages")
        _canPinMessages      <- h.get[Option[Boolean]]("can_pin_messages")
        _customTitle         <- h.get[Option[String]]("custom_title")
      } yield {
        ChatMemberAdministrator(
          status = _status,
          user = _user,
          canBeEdited = _canBeEdited,
          isAnonymous = _isAnonymous,
          canManageChat = _canManageChat,
          canDeleteMessages = _canDeleteMessages,
          canManageVoiceChats = _canManageVoiceChats,
          canRestrictMembers = _canRestrictMembers,
          canPromoteMembers = _canPromoteMembers,
          canChangeInfo = _canChangeInfo,
          canInviteUsers = _canInviteUsers,
          canPostMessages = _canPostMessages,
          canEditMessages = _canEditMessages,
          canPinMessages = _canPinMessages,
          customTitle = _customTitle
        )
      }
    }

  implicit lazy val chatmemberleftEncoder: Encoder[ChatMemberLeft] =
    (x: ChatMemberLeft) => {
      Json.fromFields(
        List(
          "status" -> x.status.asJson,
          "user"   -> x.user.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberleftDecoder: Decoder[ChatMemberLeft] =
    Decoder.instance { h =>
      for {
        _status <- h.get[String]("status")
        _user   <- h.get[User]("user")
      } yield {
        ChatMemberLeft(status = _status, user = _user)
      }
    }

  implicit lazy val chatmembermemberEncoder: Encoder[ChatMemberMember] =
    (x: ChatMemberMember) => {
      Json.fromFields(
        List(
          "status" -> x.status.asJson,
          "user"   -> x.user.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmembermemberDecoder: Decoder[ChatMemberMember] =
    Decoder.instance { h =>
      for {
        _status <- h.get[String]("status")
        _user   <- h.get[User]("user")
      } yield {
        ChatMemberMember(status = _status, user = _user)
      }
    }

  implicit lazy val chatmemberbannedEncoder: Encoder[ChatMemberBanned] =
    (x: ChatMemberBanned) => {
      Json.fromFields(
        List(
          "status"     -> x.status.asJson,
          "user"       -> x.user.asJson,
          "until_date" -> x.untilDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberbannedDecoder: Decoder[ChatMemberBanned] =
    Decoder.instance { h =>
      for {
        _status    <- h.get[String]("status")
        _user      <- h.get[User]("user")
        _untilDate <- h.get[Int]("until_date")
      } yield {
        ChatMemberBanned(status = _status, user = _user, untilDate = _untilDate)
      }
    }

  implicit lazy val chatmemberrestrictedEncoder: Encoder[ChatMemberRestricted] =
    (x: ChatMemberRestricted) => {
      Json.fromFields(
        List(
          "status"                    -> x.status.asJson,
          "user"                      -> x.user.asJson,
          "is_member"                 -> x.isMember.asJson,
          "can_change_info"           -> x.canChangeInfo.asJson,
          "can_invite_users"          -> x.canInviteUsers.asJson,
          "can_pin_messages"          -> x.canPinMessages.asJson,
          "can_send_messages"         -> x.canSendMessages.asJson,
          "can_send_media_messages"   -> x.canSendMediaMessages.asJson,
          "can_send_polls"            -> x.canSendPolls.asJson,
          "can_send_other_messages"   -> x.canSendOtherMessages.asJson,
          "can_add_web_page_previews" -> x.canAddWebPagePreviews.asJson,
          "until_date"                -> x.untilDate.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberrestrictedDecoder: Decoder[ChatMemberRestricted] =
    Decoder.instance { h =>
      for {
        _status                <- h.get[String]("status")
        _user                  <- h.get[User]("user")
        _isMember              <- h.get[Boolean]("is_member")
        _canChangeInfo         <- h.get[Boolean]("can_change_info")
        _canInviteUsers        <- h.get[Boolean]("can_invite_users")
        _canPinMessages        <- h.get[Boolean]("can_pin_messages")
        _canSendMessages       <- h.get[Boolean]("can_send_messages")
        _canSendMediaMessages  <- h.get[Boolean]("can_send_media_messages")
        _canSendPolls          <- h.get[Boolean]("can_send_polls")
        _canSendOtherMessages  <- h.get[Boolean]("can_send_other_messages")
        _canAddWebPagePreviews <- h.get[Boolean]("can_add_web_page_previews")
        _untilDate             <- h.get[Int]("until_date")
      } yield {
        ChatMemberRestricted(
          status = _status,
          user = _user,
          isMember = _isMember,
          canChangeInfo = _canChangeInfo,
          canInviteUsers = _canInviteUsers,
          canPinMessages = _canPinMessages,
          canSendMessages = _canSendMessages,
          canSendMediaMessages = _canSendMediaMessages,
          canSendPolls = _canSendPolls,
          canSendOtherMessages = _canSendOtherMessages,
          canAddWebPagePreviews = _canAddWebPagePreviews,
          untilDate = _untilDate
        )
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
    }
  } yield value

  implicit lazy val inputmediaanimationEncoder: Encoder[InputMediaAnimation] =
    (x: InputMediaAnimation) => {
      Json.fromFields(
        List(
          "media"            -> x.media.asJson,
          "thumb"            -> x.thumb.asJson,
          "caption"          -> x.caption.asJson,
          "parse_mode"       -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson,
          "width"            -> x.width.asJson,
          "height"           -> x.height.asJson,
          "duration"         -> x.duration.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediaanimationDecoder: Decoder[InputMediaAnimation] =
    Decoder.instance { h =>
      for {
        _media           <- h.get[String]("media")
        _thumb           <- h.get[Option[IFile]]("thumb")
        _caption         <- h.get[Option[String]]("caption")
        _parseMode       <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _width           <- h.get[Option[Int]]("width")
        _height          <- h.get[Option[Int]]("height")
        _duration        <- h.get[Option[Int]]("duration")
      } yield {
        InputMediaAnimation(
          media = _media,
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
          "media"            -> x.media.asJson,
          "caption"          -> x.caption.asJson,
          "parse_mode"       -> x.parseMode.asJson,
          "caption_entities" -> x.captionEntities.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediaphotoDecoder: Decoder[InputMediaPhoto] =
    Decoder.instance { h =>
      for {
        _media           <- h.get[String]("media")
        _caption         <- h.get[Option[String]]("caption")
        _parseMode       <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
      } yield {
        InputMediaPhoto(media = _media, caption = _caption, parseMode = _parseMode, captionEntities = _captionEntities)
      }
    }

  implicit lazy val inputmediavideoEncoder: Encoder[InputMediaVideo] =
    (x: InputMediaVideo) => {
      Json.fromFields(
        List(
          "media"              -> x.media.asJson,
          "thumb"              -> x.thumb.asJson,
          "caption"            -> x.caption.asJson,
          "parse_mode"         -> x.parseMode.asJson,
          "caption_entities"   -> x.captionEntities.asJson,
          "width"              -> x.width.asJson,
          "height"             -> x.height.asJson,
          "duration"           -> x.duration.asJson,
          "supports_streaming" -> x.supportsStreaming.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputmediavideoDecoder: Decoder[InputMediaVideo] =
    Decoder.instance { h =>
      for {
        _media             <- h.get[String]("media")
        _thumb             <- h.get[Option[IFile]]("thumb")
        _caption           <- h.get[Option[String]]("caption")
        _parseMode         <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities   <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _width             <- h.get[Option[Int]]("width")
        _height            <- h.get[Option[Int]]("height")
        _duration          <- h.get[Option[Int]]("duration")
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
          "media"                          -> x.media.asJson,
          "thumb"                          -> x.thumb.asJson,
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
        _media                       <- h.get[String]("media")
        _thumb                       <- h.get[Option[IFile]]("thumb")
        _caption                     <- h.get[Option[String]]("caption")
        _parseMode                   <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities             <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
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
          "media"            -> x.media.asJson,
          "thumb"            -> x.thumb.asJson,
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
        _thumb           <- h.get[Option[IFile]]("thumb")
        _caption         <- h.get[Option[String]]("caption")
        _parseMode       <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _duration        <- h.get[Option[Int]]("duration")
        _performer       <- h.get[Option[String]]("performer")
        _title           <- h.get[Option[String]]("title")
      } yield {
        InputMediaAudio(
          media = _media,
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
    }
  } yield value

  implicit lazy val inlinequeryresultgifEncoder: Encoder[InlineQueryResultGif] =
    (x: InlineQueryResultGif) => {
      Json.fromFields(
        List(
          "id"                    -> x.id.asJson,
          "gif_url"               -> x.gifUrl.asJson,
          "gif_width"             -> x.gifWidth.asJson,
          "gif_height"            -> x.gifHeight.asJson,
          "gif_duration"          -> x.gifDuration.asJson,
          "thumb_url"             -> x.thumbUrl.asJson,
          "thumb_mime_type"       -> x.thumbMimeType.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultgifDecoder: Decoder[InlineQueryResultGif] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _gifUrl              <- h.get[String]("gif_url")
        _gifWidth            <- h.get[Option[Int]]("gif_width")
        _gifHeight           <- h.get[Option[Int]]("gif_height")
        _gifDuration         <- h.get[Option[Int]]("gif_duration")
        _thumbUrl            <- h.get[String]("thumb_url")
        _thumbMimeType       <- h.get[Option[String]]("thumb_mime_type")
        _title               <- h.get[Option[String]]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "thumb_url"             -> x.thumbUrl.asJson,
          "thumb_width"           -> x.thumbWidth.asJson,
          "thumb_height"          -> x.thumbHeight.asJson
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
        _thumbUrl            <- h.get[Option[String]]("thumb_url")
        _thumbWidth          <- h.get[Option[Int]]("thumb_width")
        _thumbHeight         <- h.get[Option[Int]]("thumb_height")
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
          "id"                    -> x.id.asJson,
          "phone_number"          -> x.phoneNumber.asJson,
          "first_name"            -> x.firstName.asJson,
          "last_name"             -> x.lastName.asJson,
          "vcard"                 -> x.vcard.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson,
          "thumb_url"             -> x.thumbUrl.asJson,
          "thumb_width"           -> x.thumbWidth.asJson,
          "thumb_height"          -> x.thumbHeight.asJson
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
        _thumbUrl            <- h.get[Option[String]]("thumb_url")
        _thumbWidth          <- h.get[Option[Int]]("thumb_width")
        _thumbHeight         <- h.get[Option[Int]]("thumb_height")
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
          "id"                    -> x.id.asJson,
          "photo_url"             -> x.photoUrl.asJson,
          "thumb_url"             -> x.thumbUrl.asJson,
          "photo_width"           -> x.photoWidth.asJson,
          "photo_height"          -> x.photoHeight.asJson,
          "title"                 -> x.title.asJson,
          "description"           -> x.description.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultphotoDecoder: Decoder[InlineQueryResultPhoto] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _photoUrl            <- h.get[String]("photo_url")
        _thumbUrl            <- h.get[String]("thumb_url")
        _photoWidth          <- h.get[Option[Int]]("photo_width")
        _photoHeight         <- h.get[Option[Int]]("photo_height")
        _title               <- h.get[Option[String]]("title")
        _description         <- h.get[Option[String]]("description")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "thumb_url"             -> x.thumbUrl.asJson,
          "thumb_width"           -> x.thumbWidth.asJson,
          "thumb_height"          -> x.thumbHeight.asJson
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
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _documentUrl         <- h.get[String]("document_url")
        _mimeType            <- h.get[String]("mime_type")
        _description         <- h.get[Option[String]]("description")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
        _inputMessageContent <- h.get[Option[InputMessageContent]]("input_message_content")
        _thumbUrl            <- h.get[Option[String]]("thumb_url")
        _thumbWidth          <- h.get[Option[Int]]("thumb_width")
        _thumbHeight         <- h.get[Option[Int]]("thumb_height")
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
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
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
          "thumb_url"             -> x.thumbUrl.asJson,
          "thumb_width"           -> x.thumbWidth.asJson,
          "thumb_height"          -> x.thumbHeight.asJson
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
        _thumbUrl            <- h.get[Option[String]]("thumb_url")
        _thumbWidth          <- h.get[Option[Int]]("thumb_width")
        _thumbHeight         <- h.get[Option[Int]]("thumb_height")
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
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
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
          "id"                    -> x.id.asJson,
          "mpeg4_url"             -> x.mpeg4Url.asJson,
          "mpeg4_width"           -> x.mpeg4Width.asJson,
          "mpeg4_height"          -> x.mpeg4Height.asJson,
          "mpeg4_duration"        -> x.mpeg4Duration.asJson,
          "thumb_url"             -> x.thumbUrl.asJson,
          "thumb_mime_type"       -> x.thumbMimeType.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultmpeg4gifDecoder: Decoder[InlineQueryResultMpeg4Gif] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _mpeg4Url            <- h.get[String]("mpeg4_url")
        _mpeg4Width          <- h.get[Option[Int]]("mpeg4_width")
        _mpeg4Height         <- h.get[Option[Int]]("mpeg4_height")
        _mpeg4Duration       <- h.get[Option[Int]]("mpeg4_duration")
        _thumbUrl            <- h.get[String]("thumb_url")
        _thumbMimeType       <- h.get[Option[String]]("thumb_mime_type")
        _title               <- h.get[Option[String]]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "id"                    -> x.id.asJson,
          "mpeg4_file_id"         -> x.mpeg4FileId.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedmpeg4gifDecoder: Decoder[InlineQueryResultCachedMpeg4Gif] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _mpeg4FileId         <- h.get[String]("mpeg4_file_id")
        _title               <- h.get[Option[String]]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
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
          "id"                    -> x.id.asJson,
          "video_file_id"         -> x.videoFileId.asJson,
          "title"                 -> x.title.asJson,
          "description"           -> x.description.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedvideoDecoder: Decoder[InlineQueryResultCachedVideo] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _videoFileId         <- h.get[String]("video_file_id")
        _title               <- h.get[String]("title")
        _description         <- h.get[Option[String]]("description")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "id"                    -> x.id.asJson,
          "photo_file_id"         -> x.photoFileId.asJson,
          "title"                 -> x.title.asJson,
          "description"           -> x.description.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedphotoDecoder: Decoder[InlineQueryResultCachedPhoto] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _photoFileId         <- h.get[String]("photo_file_id")
        _title               <- h.get[Option[String]]("title")
        _description         <- h.get[Option[String]]("description")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
          "id"                    -> x.id.asJson,
          "video_url"             -> x.videoUrl.asJson,
          "mime_type"             -> x.mimeType.asJson,
          "thumb_url"             -> x.thumbUrl.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "video_width"           -> x.videoWidth.asJson,
          "video_height"          -> x.videoHeight.asJson,
          "video_duration"        -> x.videoDuration.asJson,
          "description"           -> x.description.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultvideoDecoder: Decoder[InlineQueryResultVideo] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _videoUrl            <- h.get[String]("video_url")
        _mimeType            <- h.get[String]("mime_type")
        _thumbUrl            <- h.get[String]("thumb_url")
        _title               <- h.get[String]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _videoWidth          <- h.get[Option[Int]]("video_width")
        _videoHeight         <- h.get[Option[Int]]("video_height")
        _videoDuration       <- h.get[Option[Int]]("video_duration")
        _description         <- h.get[Option[String]]("description")
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
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
          "thumb_url"              -> x.thumbUrl.asJson,
          "thumb_width"            -> x.thumbWidth.asJson,
          "thumb_height"           -> x.thumbHeight.asJson
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
        _thumbUrl             <- h.get[Option[String]]("thumb_url")
        _thumbWidth           <- h.get[Option[Int]]("thumb_width")
        _thumbHeight          <- h.get[Option[Int]]("thumb_height")
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
          "id"                    -> x.id.asJson,
          "gif_file_id"           -> x.gifFileId.asJson,
          "title"                 -> x.title.asJson,
          "caption"               -> x.caption.asJson,
          "parse_mode"            -> x.parseMode.asJson,
          "caption_entities"      -> x.captionEntities.asJson,
          "reply_markup"          -> x.replyMarkup.asJson,
          "input_message_content" -> x.inputMessageContent.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inlinequeryresultcachedgifDecoder: Decoder[InlineQueryResultCachedGif] =
    Decoder.instance { h =>
      for {
        _id                  <- h.get[String]("id")
        _gifFileId           <- h.get[String]("gif_file_id")
        _title               <- h.get[Option[String]]("title")
        _caption             <- h.get[Option[String]]("caption")
        _parseMode           <- h.get[Option[ParseMode]]("parse_mode")
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
        _replyMarkup         <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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
        _captionEntities     <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
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
        _providerToken             <- h.get[String]("provider_token")
        _currency                  <- h.get[String]("currency")
        _prices                    <- h.getOrElse[List[LabeledPrice]]("prices")(List.empty)
        _maxTipAmount              <- h.get[Option[Int]]("max_tip_amount")
        _suggestedTipAmounts       <- h.getOrElse[List[Int]]("suggested_tip_amounts")(List.empty)
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
          "message_text"             -> x.messageText.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "entities"                 -> x.entities.asJson,
          "disable_web_page_preview" -> x.disableWebPagePreview.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val inputtextmessagecontentDecoder: Decoder[InputTextMessageContent] =
    Decoder.instance { h =>
      for {
        _messageText           <- h.get[String]("message_text")
        _parseMode             <- h.get[Option[ParseMode]]("parse_mode")
        _entities              <- h.getOrElse[List[MessageEntity]]("entities")(List.empty)
        _disableWebPagePreview <- h.get[Option[Boolean]]("disable_web_page_preview")
      } yield {
        InputTextMessageContent(
          messageText = _messageText,
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
    case data: PassportElementErrorDataField => data.asJson.mapObject(_.add("type", Json.fromString("data")))
    case front_side: PassportElementErrorFrontSide =>
      front_side.asJson.mapObject(_.add("type", Json.fromString("front_side")))
    case files: PassportElementErrorFiles => files.asJson.mapObject(_.add("type", Json.fromString("files")))
    case unspecified: PassportElementErrorUnspecified =>
      unspecified.asJson.mapObject(_.add("type", Json.fromString("unspecified")))
    case file: PassportElementErrorFile     => file.asJson.mapObject(_.add("type", Json.fromString("file")))
    case selfie: PassportElementErrorSelfie => selfie.asJson.mapObject(_.add("type", Json.fromString("selfie")))
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

  implicit lazy val messageentityEncoder: Encoder[MessageEntity] = {
    case pre: PreMessageEntity => pre.asJson.mapObject(_.add("type", Json.fromString("pre")))
    case text_mention: TextMentionMessageEntity =>
      text_mention.asJson.mapObject(_.add("type", Json.fromString("text_mention")))
    case bot_command: BotCommandMessageEntity =>
      bot_command.asJson.mapObject(_.add("type", Json.fromString("bot_command")))
    case phone_number: PhoneNumberMessageEntity =>
      phone_number.asJson.mapObject(_.add("type", Json.fromString("phone_number")))
    case email: EmailMessageEntity         => email.asJson.mapObject(_.add("type", Json.fromString("email")))
    case url: UrlMessageEntity             => url.asJson.mapObject(_.add("type", Json.fromString("url")))
    case underline: UnderlineMessageEntity => underline.asJson.mapObject(_.add("type", Json.fromString("underline")))
    case italic: ItalicMessageEntity       => italic.asJson.mapObject(_.add("type", Json.fromString("italic")))
    case bold: BoldMessageEntity           => bold.asJson.mapObject(_.add("type", Json.fromString("bold")))
    case cashtag: CashtagMessageEntity     => cashtag.asJson.mapObject(_.add("type", Json.fromString("cashtag")))
    case code: CodeMessageEntity           => code.asJson.mapObject(_.add("type", Json.fromString("code")))
    case mention: MentionMessageEntity     => mention.asJson.mapObject(_.add("type", Json.fromString("mention")))
    case hashtag: HashtagMessageEntity     => hashtag.asJson.mapObject(_.add("type", Json.fromString("hashtag")))
    case text_link: TextLinkMessageEntity  => text_link.asJson.mapObject(_.add("type", Json.fromString("text_link")))
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

  implicit lazy val animationEncoder: Encoder[Animation] =
    (x: Animation) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width"          -> x.width.asJson,
          "height"         -> x.height.asJson,
          "duration"       -> x.duration.asJson,
          "thumb"          -> x.thumb.asJson,
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
        _thumb        <- h.get[Option[PhotoSize]]("thumb")
        _fileName     <- h.get[Option[String]]("file_name")
        _mimeType     <- h.get[Option[String]]("mime_type")
        _fileSize     <- h.get[Option[Int]]("file_size")
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
          "id"                       -> x.id.asJson,
          "type"                     -> x.`type`.asJson,
          "title"                    -> x.title.asJson,
          "username"                 -> x.username.asJson,
          "first_name"               -> x.firstName.asJson,
          "last_name"                -> x.lastName.asJson,
          "photo"                    -> x.photo.asJson,
          "bio"                      -> x.bio.asJson,
          "description"              -> x.description.asJson,
          "invite_link"              -> x.inviteLink.asJson,
          "pinned_message"           -> x.pinnedMessage.asJson,
          "permissions"              -> x.permissions.asJson,
          "slow_mode_delay"          -> x.slowModeDelay.asJson,
          "message_auto_delete_time" -> x.messageAutoDeleteTime.asJson,
          "sticker_set_name"         -> x.stickerSetName.asJson,
          "can_set_sticker_set"      -> x.canSetStickerSet.asJson,
          "linked_chat_id"           -> x.linkedChatId.asJson,
          "location"                 -> x.location.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatDecoder: Decoder[Chat] =
    Decoder.instance { h =>
      for {
        _id                    <- h.get[Long]("id")
        _type                  <- h.get[String]("type")
        _title                 <- h.get[Option[String]]("title")
        _username              <- h.get[Option[String]]("username")
        _firstName             <- h.get[Option[String]]("first_name")
        _lastName              <- h.get[Option[String]]("last_name")
        _photo                 <- h.get[Option[ChatPhoto]]("photo")
        _bio                   <- h.get[Option[String]]("bio")
        _description           <- h.get[Option[String]]("description")
        _inviteLink            <- h.get[Option[String]]("invite_link")
        _pinnedMessage         <- h.get[Option[Message]]("pinned_message")
        _permissions           <- h.get[Option[ChatPermissions]]("permissions")
        _slowModeDelay         <- h.get[Option[Int]]("slow_mode_delay")
        _messageAutoDeleteTime <- h.get[Option[Int]]("message_auto_delete_time")
        _stickerSetName        <- h.get[Option[String]]("sticker_set_name")
        _canSetStickerSet      <- h.get[Option[Boolean]]("can_set_sticker_set")
        _linkedChatId          <- h.get[Option[Long]]("linked_chat_id")
        _location              <- h.get[Option[ChatLocation]]("location")
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
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "length"         -> x.length.asJson,
          "duration"       -> x.duration.asJson,
          "thumb"          -> x.thumb.asJson,
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
        _thumb        <- h.get[Option[PhotoSize]]("thumb")
        _fileSize     <- h.get[Option[Int]]("file_size")
      } yield {
        VideoNote(
          fileId = _fileId,
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
          "longitude"              -> x.longitude.asJson,
          "latitude"               -> x.latitude.asJson,
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
        _longitude            <- h.get[Float]("longitude")
        _latitude             <- h.get[Float]("latitude")
        _horizontalAccuracy   <- h.get[Option[Float]]("horizontal_accuracy")
        _livePeriod           <- h.get[Option[Int]]("live_period")
        _heading              <- h.get[Option[Int]]("heading")
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
          "can_send_messages"         -> x.canSendMessages.asJson,
          "can_send_media_messages"   -> x.canSendMediaMessages.asJson,
          "can_send_polls"            -> x.canSendPolls.asJson,
          "can_send_other_messages"   -> x.canSendOtherMessages.asJson,
          "can_add_web_page_previews" -> x.canAddWebPagePreviews.asJson,
          "can_change_info"           -> x.canChangeInfo.asJson,
          "can_invite_users"          -> x.canInviteUsers.asJson,
          "can_pin_messages"          -> x.canPinMessages.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatpermissionsDecoder: Decoder[ChatPermissions] =
    Decoder.instance { h =>
      for {
        _canSendMessages       <- h.get[Option[Boolean]]("can_send_messages")
        _canSendMediaMessages  <- h.get[Option[Boolean]]("can_send_media_messages")
        _canSendPolls          <- h.get[Option[Boolean]]("can_send_polls")
        _canSendOtherMessages  <- h.get[Option[Boolean]]("can_send_other_messages")
        _canAddWebPagePreviews <- h.get[Option[Boolean]]("can_add_web_page_previews")
        _canChangeInfo         <- h.get[Option[Boolean]]("can_change_info")
        _canInviteUsers        <- h.get[Option[Boolean]]("can_invite_users")
        _canPinMessages        <- h.get[Option[Boolean]]("can_pin_messages")
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
          "text"        -> x.text.asJson,
          "voter_count" -> x.voterCount.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val polloptionDecoder: Decoder[PollOption] =
    Decoder.instance { h =>
      for {
        _text       <- h.get[String]("text")
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

  implicit lazy val inputfileEncoder: Encoder[InputFile.type] = (_: InputFile.type) => ().asJson
  implicit lazy val inputfileDecoder: Decoder[InputFile.type] = (_: HCursor) => Right(InputFile)

  implicit lazy val updateEncoder: Encoder[Update] =
    (x: Update) => {
      Json.fromFields(
        List(
          "update_id"            -> x.updateId.asJson,
          "message"              -> x.message.asJson,
          "edited_message"       -> x.editedMessage.asJson,
          "channel_post"         -> x.channelPost.asJson,
          "edited_channel_post"  -> x.editedChannelPost.asJson,
          "inline_query"         -> x.inlineQuery.asJson,
          "chosen_inline_result" -> x.chosenInlineResult.asJson,
          "callback_query"       -> x.callbackQuery.asJson,
          "shipping_query"       -> x.shippingQuery.asJson,
          "pre_checkout_query"   -> x.preCheckoutQuery.asJson,
          "poll"                 -> x.poll.asJson,
          "poll_answer"          -> x.pollAnswer.asJson,
          "my_chat_member"       -> x.myChatMember.asJson,
          "chat_member"          -> x.chatMember.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val updateDecoder: Decoder[Update] =
    Decoder.instance { h =>
      for {
        _updateId           <- h.get[Int]("update_id")
        _message            <- h.get[Option[Message]]("message")
        _editedMessage      <- h.get[Option[Message]]("edited_message")
        _channelPost        <- h.get[Option[Message]]("channel_post")
        _editedChannelPost  <- h.get[Option[Message]]("edited_channel_post")
        _inlineQuery        <- h.get[Option[InlineQuery]]("inline_query")
        _chosenInlineResult <- h.get[Option[ChosenInlineResult]]("chosen_inline_result")
        _callbackQuery      <- h.get[Option[CallbackQuery]]("callback_query")
        _shippingQuery      <- h.get[Option[ShippingQuery]]("shipping_query")
        _preCheckoutQuery   <- h.get[Option[PreCheckoutQuery]]("pre_checkout_query")
        _poll               <- h.get[Option[Poll]]("poll")
        _pollAnswer         <- h.get[Option[PollAnswer]]("poll_answer")
        _myChatMember       <- h.get[Option[ChatMemberUpdated]]("my_chat_member")
        _chatMember         <- h.get[Option[ChatMemberUpdated]]("chat_member")
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

  implicit lazy val callbackgameEncoder: Encoder[CallbackGame.type] = (_: CallbackGame.type) => ().asJson
  implicit lazy val callbackgameDecoder: Decoder[CallbackGame.type] = (_: HCursor) => Right(CallbackGame)

  implicit lazy val keyboardbuttonEncoder: Encoder[KeyboardButton] =
    (x: KeyboardButton) => {
      Json.fromFields(
        List(
          "text"             -> x.text.asJson,
          "request_contact"  -> x.requestContact.asJson,
          "request_location" -> x.requestLocation.asJson,
          "request_poll"     -> x.requestPoll.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val keyboardbuttonDecoder: Decoder[KeyboardButton] =
    Decoder.instance { h =>
      for {
        _text            <- h.get[String]("text")
        _requestContact  <- h.get[Option[Boolean]]("request_contact")
        _requestLocation <- h.get[Option[Boolean]]("request_location")
        _requestPoll     <- h.get[Option[KeyboardButtonPollType]]("request_poll")
      } yield {
        KeyboardButton(
          text = _text,
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
        _fileSize     <- h.get[Int]("file_size")
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
        _fileSize     <- h.get[Option[Int]]("file_size")
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
          "id"                      -> x.id.asJson,
          "question"                -> x.question.asJson,
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
        _options               <- h.getOrElse[List[PollOption]]("options")(List.empty)
        _totalVoterCount       <- h.get[Int]("total_voter_count")
        _isClosed              <- h.get[Boolean]("is_closed")
        _isAnonymous           <- h.get[Boolean]("is_anonymous")
        _type                  <- h.get[String]("type")
        _allowsMultipleAnswers <- h.get[Boolean]("allows_multiple_answers")
        _correctOptionId       <- h.get[Option[Int]]("correct_option_id")
        _explanation           <- h.get[Option[String]]("explanation")
        _explanationEntities   <- h.getOrElse[List[MessageEntity]]("explanation_entities")(List.empty)
        _openPeriod            <- h.get[Option[Int]]("open_period")
        _closeDate             <- h.get[Option[Int]]("close_date")
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
          "name"           -> x.name.asJson,
          "title"          -> x.title.asJson,
          "is_animated"    -> x.isAnimated.asJson,
          "contains_masks" -> x.containsMasks.asJson,
          "stickers"       -> x.stickers.asJson,
          "thumb"          -> x.thumb.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stickersetDecoder: Decoder[StickerSet] =
    Decoder.instance { h =>
      for {
        _name          <- h.get[String]("name")
        _title         <- h.get[String]("title")
        _isAnimated    <- h.get[Boolean]("is_animated")
        _containsMasks <- h.get[Boolean]("contains_masks")
        _stickers      <- h.getOrElse[List[Sticker]]("stickers")(List.empty)
        _thumb         <- h.get[Option[PhotoSize]]("thumb")
      } yield {
        StickerSet(
          name = _name,
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
          "poll_id"    -> x.pollId.asJson,
          "user"       -> x.user.asJson,
          "option_ids" -> x.optionIds.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pollanswerDecoder: Decoder[PollAnswer] =
    Decoder.instance { h =>
      for {
        _pollId    <- h.get[String]("poll_id")
        _user      <- h.get[User]("user")
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

  implicit lazy val successfulpaymentEncoder: Encoder[SuccessfulPayment] =
    (x: SuccessfulPayment) => {
      Json.fromFields(
        List(
          "currency"                   -> x.currency.asJson,
          "total_amount"               -> x.totalAmount.asJson,
          "invoice_payload"            -> x.invoicePayload.asJson,
          "shipping_option_id"         -> x.shippingOptionId.asJson,
          "order_info"                 -> x.orderInfo.asJson,
          "telegram_payment_charge_id" -> x.telegramPaymentChargeId.asJson,
          "provider_payment_charge_id" -> x.providerPaymentChargeId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val successfulpaymentDecoder: Decoder[SuccessfulPayment] =
    Decoder.instance { h =>
      for {
        _currency                <- h.get[String]("currency")
        _totalAmount             <- h.get[Int]("total_amount")
        _invoicePayload          <- h.get[String]("invoice_payload")
        _shippingOptionId        <- h.get[Option[String]]("shipping_option_id")
        _orderInfo               <- h.get[Option[OrderInfo]]("order_info")
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
          "invite_link"  -> x.inviteLink.asJson,
          "creator"      -> x.creator.asJson,
          "is_primary"   -> x.isPrimary.asJson,
          "is_revoked"   -> x.isRevoked.asJson,
          "expire_date"  -> x.expireDate.asJson,
          "member_limit" -> x.memberLimit.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatinvitelinkDecoder: Decoder[ChatInviteLink] =
    Decoder.instance { h =>
      for {
        _inviteLink  <- h.get[String]("invite_link")
        _creator     <- h.get[User]("creator")
        _isPrimary   <- h.get[Boolean]("is_primary")
        _isRevoked   <- h.get[Boolean]("is_revoked")
        _expireDate  <- h.get[Option[Int]]("expire_date")
        _memberLimit <- h.get[Option[Int]]("member_limit")
      } yield {
        ChatInviteLink(
          inviteLink = _inviteLink,
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
          "chat"            -> x.chat.asJson,
          "from"            -> x.from.asJson,
          "date"            -> x.date.asJson,
          "old_chat_member" -> x.oldChatMember.asJson,
          "new_chat_member" -> x.newChatMember.asJson,
          "invite_link"     -> x.inviteLink.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val chatmemberupdatedDecoder: Decoder[ChatMemberUpdated] =
    Decoder.instance { h =>
      for {
        _chat          <- h.get[Chat]("chat")
        _from          <- h.get[User]("from")
        _date          <- h.get[Int]("date")
        _oldChatMember <- h.get[ChatMember]("old_chat_member")
        _newChatMember <- h.get[ChatMember]("new_chat_member")
        _inviteLink    <- h.get[Option[ChatInviteLink]]("invite_link")
      } yield {
        ChatMemberUpdated(
          chat = _chat,
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
        _fileSize     <- h.get[Option[Int]]("file_size")
        _filePath     <- h.get[Option[String]]("file_path")
      } yield {
        File(fileId = _fileId, fileUniqueId = _fileUniqueId, fileSize = _fileSize, filePath = _filePath)
      }
    }

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
        _textEntities <- h.getOrElse[List[MessageEntity]]("text_entities")(List.empty)
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
          "thumb"          -> x.thumb.asJson
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
        _fileSize     <- h.get[Option[Int]]("file_size")
        _thumb        <- h.get[Option[PhotoSize]]("thumb")
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
          "url"                    -> x.url.asJson,
          "has_custom_certificate" -> x.hasCustomCertificate.asJson,
          "pending_update_count"   -> x.pendingUpdateCount.asJson,
          "ip_address"             -> x.ipAddress.asJson,
          "last_error_date"        -> x.lastErrorDate.asJson,
          "last_error_message"     -> x.lastErrorMessage.asJson,
          "max_connections"        -> x.maxConnections.asJson,
          "allowed_updates"        -> x.allowedUpdates.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val webhookinfoDecoder: Decoder[WebhookInfo] =
    Decoder.instance { h =>
      for {
        _url                  <- h.get[String]("url")
        _hasCustomCertificate <- h.get[Boolean]("has_custom_certificate")
        _pendingUpdateCount   <- h.get[Int]("pending_update_count")
        _ipAddress            <- h.get[Option[String]]("ip_address")
        _lastErrorDate        <- h.get[Option[Int]]("last_error_date")
        _lastErrorMessage     <- h.get[Option[String]]("last_error_message")
        _maxConnections       <- h.get[Option[Int]]("max_connections")
        _allowedUpdates       <- h.getOrElse[List[String]]("allowed_updates")(List.empty)
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
          "can_join_groups"             -> x.canJoinGroups.asJson,
          "can_read_all_group_messages" -> x.canReadAllGroupMessages.asJson,
          "supports_inline_queries"     -> x.supportsInlineQueries.asJson
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
        _canJoinGroups           <- h.get[Option[Boolean]]("can_join_groups")
        _canReadAllGroupMessages <- h.get[Option[Boolean]]("can_read_all_group_messages")
        _supportsInlineQueries   <- h.get[Option[Boolean]]("supports_inline_queries")
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

  implicit lazy val stickerEncoder: Encoder[Sticker] =
    (x: Sticker) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width"          -> x.width.asJson,
          "height"         -> x.height.asJson,
          "is_animated"    -> x.isAnimated.asJson,
          "thumb"          -> x.thumb.asJson,
          "emoji"          -> x.emoji.asJson,
          "set_name"       -> x.setName.asJson,
          "mask_position"  -> x.maskPosition.asJson,
          "file_size"      -> x.fileSize.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stickerDecoder: Decoder[Sticker] =
    Decoder.instance { h =>
      for {
        _fileId       <- h.get[String]("file_id")
        _fileUniqueId <- h.get[String]("file_unique_id")
        _width        <- h.get[Int]("width")
        _height       <- h.get[Int]("height")
        _isAnimated   <- h.get[Boolean]("is_animated")
        _thumb        <- h.get[Option[PhotoSize]]("thumb")
        _emoji        <- h.get[Option[Emoji]]("emoji")
        _setName      <- h.get[Option[String]]("set_name")
        _maskPosition <- h.get[Option[MaskPosition]]("mask_position")
        _fileSize     <- h.get[Option[Int]]("file_size")
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
          "message_id"                        -> x.messageId.asJson,
          "from"                              -> x.from.asJson,
          "sender_chat"                       -> x.senderChat.asJson,
          "date"                              -> x.date.asJson,
          "chat"                              -> x.chat.asJson,
          "forward_from"                      -> x.forwardFrom.asJson,
          "forward_from_chat"                 -> x.forwardFromChat.asJson,
          "forward_from_message_id"           -> x.forwardFromMessageId.asJson,
          "forward_signature"                 -> x.forwardSignature.asJson,
          "forward_sender_name"               -> x.forwardSenderName.asJson,
          "forward_date"                      -> x.forwardDate.asJson,
          "reply_to_message"                  -> x.replyToMessage.asJson,
          "via_bot"                           -> x.viaBot.asJson,
          "edit_date"                         -> x.editDate.asJson,
          "media_group_id"                    -> x.mediaGroupId.asJson,
          "author_signature"                  -> x.authorSignature.asJson,
          "text"                              -> x.text.asJson,
          "entities"                          -> x.entities.asJson,
          "animation"                         -> x.animation.asJson,
          "audio"                             -> x.audio.asJson,
          "document"                          -> x.document.asJson,
          "photo"                             -> x.photo.asJson,
          "sticker"                           -> x.sticker.asJson,
          "video"                             -> x.video.asJson,
          "video_note"                        -> x.videoNote.asJson,
          "voice"                             -> x.voice.asJson,
          "caption"                           -> x.caption.asJson,
          "caption_entities"                  -> x.captionEntities.asJson,
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
          "connected_website"                 -> x.connectedWebsite.asJson,
          "passport_data"                     -> x.passportData.asJson,
          "proximity_alert_triggered"         -> x.proximityAlertTriggered.asJson,
          "voice_chat_scheduled"              -> x.voiceChatScheduled.asJson,
          "voice_chat_started"                -> x.voiceChatStarted.asJson,
          "voice_chat_ended"                  -> x.voiceChatEnded.asJson,
          "voice_chat_participants_invited"   -> x.voiceChatParticipantsInvited.asJson,
          "reply_markup"                      -> x.replyMarkup.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val messageDecoder: Decoder[Message] =
    Decoder.instance { h =>
      for {
        _messageId             <- h.get[Int]("message_id")
        _from                  <- h.get[Option[User]]("from")
        _senderChat            <- h.get[Option[Chat]]("sender_chat")
        _date                  <- h.get[Int]("date")
        _chat                  <- h.get[Chat]("chat")
        _forwardFrom           <- h.get[Option[User]]("forward_from")
        _forwardFromChat       <- h.get[Option[Chat]]("forward_from_chat")
        _forwardFromMessageId  <- h.get[Option[Int]]("forward_from_message_id")
        _forwardSignature      <- h.get[Option[String]]("forward_signature")
        _forwardSenderName     <- h.get[Option[String]]("forward_sender_name")
        _forwardDate           <- h.get[Option[Int]]("forward_date")
        _replyToMessage        <- h.get[Option[Message]]("reply_to_message")
        _viaBot                <- h.get[Option[User]]("via_bot")
        _editDate              <- h.get[Option[Int]]("edit_date")
        _mediaGroupId          <- h.get[Option[String]]("media_group_id")
        _authorSignature       <- h.get[Option[String]]("author_signature")
        _text                  <- h.get[Option[String]]("text")
        _entities              <- h.getOrElse[List[MessageEntity]]("entities")(List.empty)
        _animation             <- h.get[Option[Animation]]("animation")
        _audio                 <- h.get[Option[Audio]]("audio")
        _document              <- h.get[Option[Document]]("document")
        _photo                 <- h.getOrElse[List[PhotoSize]]("photo")(List.empty)
        _sticker               <- h.get[Option[Sticker]]("sticker")
        _video                 <- h.get[Option[Video]]("video")
        _videoNote             <- h.get[Option[VideoNote]]("video_note")
        _voice                 <- h.get[Option[Voice]]("voice")
        _caption               <- h.get[Option[String]]("caption")
        _captionEntities       <- h.getOrElse[List[MessageEntity]]("caption_entities")(List.empty)
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
        _pinnedMessage                <- h.get[Option[Message]]("pinned_message")
        _invoice                      <- h.get[Option[Invoice]]("invoice")
        _successfulPayment            <- h.get[Option[SuccessfulPayment]]("successful_payment")
        _connectedWebsite             <- h.get[Option[String]]("connected_website")
        _passportData                 <- h.get[Option[PassportData]]("passport_data")
        _proximityAlertTriggered      <- h.get[Option[ProximityAlertTriggered]]("proximity_alert_triggered")
        _voiceChatScheduled           <- h.get[Option[VoiceChatScheduled]]("voice_chat_scheduled")
        _voiceChatStarted             <- h.get[Option[VoiceChatStarted.type]]("voice_chat_started")
        _voiceChatEnded               <- h.get[Option[VoiceChatEnded]]("voice_chat_ended")
        _voiceChatParticipantsInvited <- h.get[Option[VoiceChatParticipantsInvited]]("voice_chat_participants_invited")
        _replyMarkup                  <- h.get[Option[InlineKeyboardMarkup]]("reply_markup")
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

  implicit lazy val voicechatstartedEncoder: Encoder[VoiceChatStarted.type] = (_: VoiceChatStarted.type) => ().asJson
  implicit lazy val voicechatstartedDecoder: Decoder[VoiceChatStarted.type] = (_: HCursor) => Right(VoiceChatStarted)

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

  implicit lazy val inlinekeyboardbuttonEncoder: Encoder[InlineKeyboardButton] =
    (x: InlineKeyboardButton) => {
      Json.fromFields(
        List(
          "text"                             -> x.text.asJson,
          "url"                              -> x.url.asJson,
          "login_url"                        -> x.loginUrl.asJson,
          "callback_data"                    -> x.callbackData.asJson,
          "switch_inline_query"              -> x.switchInlineQuery.asJson,
          "switch_inline_query_current_chat" -> x.switchInlineQueryCurrentChat.asJson,
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
        _loginUrl                     <- h.get[Option[LoginUrl]]("login_url")
        _callbackData                 <- h.get[Option[String]]("callback_data")
        _switchInlineQuery            <- h.get[Option[String]]("switch_inline_query")
        _switchInlineQueryCurrentChat <- h.get[Option[String]]("switch_inline_query_current_chat")
        _callbackGame                 <- h.get[Option[CallbackGame.type]]("callback_game")
        _pay                          <- h.get[Option[Boolean]]("pay")
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
        _fileSize     <- h.get[Option[Int]]("file_size")
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
        _message         <- h.get[Option[Message]]("message")
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

  implicit lazy val videoEncoder: Encoder[Video] =
    (x: Video) => {
      Json.fromFields(
        List(
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "width"          -> x.width.asJson,
          "height"         -> x.height.asJson,
          "duration"       -> x.duration.asJson,
          "thumb"          -> x.thumb.asJson,
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
        _thumb        <- h.get[Option[PhotoSize]]("thumb")
        _fileName     <- h.get[Option[String]]("file_name")
        _mimeType     <- h.get[Option[String]]("mime_type")
        _fileSize     <- h.get[Option[Int]]("file_size")
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
          "file_id"        -> x.fileId.asJson,
          "file_unique_id" -> x.fileUniqueId.asJson,
          "thumb"          -> x.thumb.asJson,
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
        _thumb        <- h.get[Option[PhotoSize]]("thumb")
        _fileName     <- h.get[Option[String]]("file_name")
        _mimeType     <- h.get[Option[String]]("mime_type")
        _fileSize     <- h.get[Option[Int]]("file_size")
      } yield {
        Document(
          fileId = _fileId,
          fileUniqueId = _fileUniqueId,
          thumb = _thumb,
          fileName = _fileName,
          mimeType = _mimeType,
          fileSize = _fileSize
        )
      }
    }

}
