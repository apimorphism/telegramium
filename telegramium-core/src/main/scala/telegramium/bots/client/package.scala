package telegramium.bots.client

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Encoder, Decoder, Json}
  import telegramium.bots.InputSticker
  import telegramium.bots.CirceImplicits._
  import telegramium.bots.InlineQueryResult
  import telegramium.bots.InlineQueryResultsButton
  import telegramium.bots.ShippingOption
  import telegramium.bots.ChatId
  import io.circe.HCursor
  import telegramium.bots.ParseMode
  import telegramium.bots.MessageEntity
  import telegramium.bots.ReplyParameters
  import telegramium.bots.KeyboardMarkup
  import telegramium.bots.LabeledPrice
  import telegramium.bots.BotCommandScope
  import telegramium.bots.InlineKeyboardMarkup
  import telegramium.bots.InputMedia
  import telegramium.bots.LinkPreviewOptions
  import telegramium.bots.ChatPermissions
  import telegramium.bots.IFile
  import telegramium.bots.InputPaidMedia
  import telegramium.bots.InputPollOption
  import telegramium.bots.MenuButton
  import telegramium.bots.ReactionType
  import telegramium.bots.BotCommand
  import telegramium.bots.ChatAdministratorRights
  import telegramium.bots.PassportElementError
  import telegramium.bots.MaskPosition

  implicit lazy val responseEncoder: Encoder[Response] =
    (x: Response) => {
      Json.fromFields(
        List(
          "ok"          -> x.ok.asJson,
          "description" -> x.description.asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val closereqEncoder: Encoder[CloseReq.type] = (_: CloseReq.type) => ().asJson

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

  implicit lazy val closegeneralforumtopicreqEncoder: Encoder[CloseGeneralForumTopicReq] =
    (x: CloseGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "closeGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val copymessagereqEncoder: Encoder[CopyMessageReq] =
    (x: CopyMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"                  -> x.chatId.asJson,
          "message_thread_id"        -> x.messageThreadId.asJson,
          "from_chat_id"             -> x.fromChatId.asJson,
          "message_id"               -> x.messageId.asJson,
          "video_start_timestamp"    -> x.videoStartTimestamp.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "disable_notification"     -> x.disableNotification.asJson,
          "protect_content"          -> x.protectContent.asJson,
          "allow_paid_broadcast"     -> x.allowPaidBroadcast.asJson,
          "reply_parameters"         -> x.replyParameters.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "method"                   -> "copyMessage".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val createchatsubscriptioninvitelinkreqEncoder: Encoder[CreateChatSubscriptionInviteLinkReq] =
    (x: CreateChatSubscriptionInviteLinkReq) => {
      Json.fromFields(
        List(
          "chat_id"             -> x.chatId.asJson,
          "name"                -> x.name.asJson,
          "subscription_period" -> x.subscriptionPeriod.asJson,
          "subscription_price"  -> x.subscriptionPrice.asJson,
          "method"              -> "createChatSubscriptionInviteLink".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val createinvoicelinkreqEncoder: Encoder[CreateInvoiceLinkReq] =
    (x: CreateInvoiceLinkReq) => {
      Json.fromFields(
        List(
          "business_connection_id"        -> x.businessConnectionId.asJson,
          "title"                         -> x.title.asJson,
          "description"                   -> x.description.asJson,
          "payload"                       -> x.payload.asJson,
          "provider_token"                -> x.providerToken.asJson,
          "currency"                      -> x.currency.asJson,
          "prices"                        -> x.prices.asJson,
          "subscription_period"           -> x.subscriptionPeriod.asJson,
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

  implicit lazy val deletechatphotoreqEncoder: Encoder[DeleteChatPhotoReq] =
    (x: DeleteChatPhotoReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "deleteChatPhoto".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val deletestickerfromsetreqEncoder: Encoder[DeleteStickerFromSetReq] =
    (x: DeleteStickerFromSetReq) => {
      Json.fromFields(
        List(
          "sticker" -> x.sticker.asJson,
          "method"  -> "deleteStickerFromSet".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val deletewebhookreqEncoder: Encoder[DeleteWebhookReq] =
    (x: DeleteWebhookReq) => {
      Json.fromFields(
        List(
          "drop_pending_updates" -> x.dropPendingUpdates.asJson,
          "method"               -> "deleteWebhook".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val editchatsubscriptioninvitelinkreqEncoder: Encoder[EditChatSubscriptionInviteLinkReq] =
    (x: EditChatSubscriptionInviteLinkReq) => {
      Json.fromFields(
        List(
          "chat_id"     -> x.chatId.asJson,
          "invite_link" -> x.inviteLink.asJson,
          "name"        -> x.name.asJson,
          "method"      -> "editChatSubscriptionInviteLink".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val editmessagecaptionreqEncoder: Encoder[EditMessageCaptionReq] =
    (x: EditMessageCaptionReq) => {
      Json.fromFields(
        List(
          "business_connection_id"   -> x.businessConnectionId.asJson,
          "chat_id"                  -> x.chatId.asJson,
          "message_id"               -> x.messageId.asJson,
          "inline_message_id"        -> x.inlineMessageId.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "method"                   -> "editMessageCaption".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagelivelocationreqEncoder: Encoder[EditMessageLiveLocationReq] =
    (x: EditMessageLiveLocationReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
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

  implicit lazy val editmessagemediareqEncoder: Encoder[EditMessageMediaReq] =
    (x: EditMessageMediaReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_id"             -> x.messageId.asJson,
          "inline_message_id"      -> x.inlineMessageId.asJson,
          "media"                  -> x.media.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "editMessageMedia".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagereplymarkupreqEncoder: Encoder[EditMessageReplyMarkupReq] =
    (x: EditMessageReplyMarkupReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_id"             -> x.messageId.asJson,
          "inline_message_id"      -> x.inlineMessageId.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "editMessageReplyMarkup".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val editmessagetextreqEncoder: Encoder[EditMessageTextReq] =
    (x: EditMessageTextReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_id"             -> x.messageId.asJson,
          "inline_message_id"      -> x.inlineMessageId.asJson,
          "text"                   -> x.text.asJson,
          "parse_mode"             -> x.parseMode.asJson,
          "entities"               -> x.entities.asJson,
          "link_preview_options"   -> x.linkPreviewOptions.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "editMessageText".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val edituserstarsubscriptionreqEncoder: Encoder[EditUserStarSubscriptionReq] =
    (x: EditUserStarSubscriptionReq) => {
      Json.fromFields(
        List(
          "user_id"                    -> x.userId.asJson,
          "telegram_payment_charge_id" -> x.telegramPaymentChargeId.asJson,
          "is_canceled"                -> x.isCanceled.asJson,
          "method"                     -> "editUserStarSubscription".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val forwardmessagereqEncoder: Encoder[ForwardMessageReq] =
    (x: ForwardMessageReq) => {
      Json.fromFields(
        List(
          "chat_id"               -> x.chatId.asJson,
          "message_thread_id"     -> x.messageThreadId.asJson,
          "from_chat_id"          -> x.fromChatId.asJson,
          "video_start_timestamp" -> x.videoStartTimestamp.asJson,
          "disable_notification"  -> x.disableNotification.asJson,
          "protect_content"       -> x.protectContent.asJson,
          "message_id"            -> x.messageId.asJson,
          "method"                -> "forwardMessage".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val getavailablegiftsreqEncoder: Encoder[GetAvailableGiftsReq.type] = (_: GetAvailableGiftsReq.type) =>
    ().asJson

  implicit lazy val getbusinessconnectionreqEncoder: Encoder[GetBusinessConnectionReq] =
    (x: GetBusinessConnectionReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "method"                 -> "getBusinessConnection".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val getchatadministratorsreqEncoder: Encoder[GetChatAdministratorsReq] =
    (x: GetChatAdministratorsReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "getChatAdministrators".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val getchatmembercountreqEncoder: Encoder[GetChatMemberCountReq] =
    (x: GetChatMemberCountReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "getChatMemberCount".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val getcustomemojistickersreqEncoder: Encoder[GetCustomEmojiStickersReq] =
    (x: GetCustomEmojiStickersReq) => {
      Json.fromFields(
        List(
          "custom_emoji_ids" -> x.customEmojiIds.asJson,
          "method"           -> "getCustomEmojiStickers".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val getforumtopiciconstickersreqEncoder: Encoder[GetForumTopicIconStickersReq.type] =
    (_: GetForumTopicIconStickersReq.type) => ().asJson

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

  implicit lazy val getmereqEncoder: Encoder[GetMeReq.type] = (_: GetMeReq.type) => ().asJson

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

  implicit lazy val getmydefaultadministratorrightsreqEncoder: Encoder[GetMyDefaultAdministratorRightsReq] =
    (x: GetMyDefaultAdministratorRightsReq) => {
      Json.fromFields(
        List(
          "for_channels" -> x.forChannels.asJson,
          "method"       -> "getMyDefaultAdministratorRights".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val getmynamereqEncoder: Encoder[GetMyNameReq] =
    (x: GetMyNameReq) => {
      Json.fromFields(
        List(
          "language_code" -> x.languageCode.asJson,
          "method"        -> "getMyName".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val getstartransactionsreqEncoder: Encoder[GetStarTransactionsReq] =
    (x: GetStarTransactionsReq) => {
      Json.fromFields(
        List(
          "offset" -> x.offset.asJson,
          "limit"  -> x.limit.asJson,
          "method" -> "getStarTransactions".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val getwebhookinforeqEncoder: Encoder[GetWebhookInfoReq.type] = (_: GetWebhookInfoReq.type) => ().asJson

  implicit lazy val hidegeneralforumtopicreqEncoder: Encoder[HideGeneralForumTopicReq] =
    (x: HideGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "hideGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val logoutreqEncoder: Encoder[LogOutReq.type] = (_: LogOutReq.type) => ().asJson

  implicit lazy val pinchatmessagereqEncoder: Encoder[PinChatMessageReq] =
    (x: PinChatMessageReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_id"             -> x.messageId.asJson,
          "disable_notification"   -> x.disableNotification.asJson,
          "method"                 -> "pinChatMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

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

  implicit lazy val refundstarpaymentreqEncoder: Encoder[RefundStarPaymentReq] =
    (x: RefundStarPaymentReq) => {
      Json.fromFields(
        List(
          "user_id"                    -> x.userId.asJson,
          "telegram_payment_charge_id" -> x.telegramPaymentChargeId.asJson,
          "method"                     -> "refundStarPayment".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val removechatverificationreqEncoder: Encoder[RemoveChatVerificationReq] =
    (x: RemoveChatVerificationReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "removeChatVerification".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val removeuserverificationreqEncoder: Encoder[RemoveUserVerificationReq] =
    (x: RemoveUserVerificationReq) => {
      Json.fromFields(
        List(
          "user_id" -> x.userId.asJson,
          "method"  -> "removeUserVerification".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val reopengeneralforumtopicreqEncoder: Encoder[ReopenGeneralForumTopicReq] =
    (x: ReopenGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "reopenGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val savepreparedinlinemessagereqEncoder: Encoder[SavePreparedInlineMessageReq] =
    (x: SavePreparedInlineMessageReq) => {
      Json.fromFields(
        List(
          "user_id"             -> x.userId.asJson,
          "result"              -> x.result.asJson,
          "allow_user_chats"    -> x.allowUserChats.asJson,
          "allow_bot_chats"     -> x.allowBotChats.asJson,
          "allow_group_chats"   -> x.allowGroupChats.asJson,
          "allow_channel_chats" -> x.allowChannelChats.asJson,
          "method"              -> "savePreparedInlineMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendanimationreqEncoder: Encoder[SendAnimationReq] =
    (x: SendAnimationReq) => {
      Json.fromFields(
        List(
          "business_connection_id"   -> x.businessConnectionId.asJson,
          "chat_id"                  -> x.chatId.asJson,
          "message_thread_id"        -> x.messageThreadId.asJson,
          "animation"                -> x.animation.asJson,
          "duration"                 -> x.duration.asJson,
          "width"                    -> x.width.asJson,
          "height"                   -> x.height.asJson,
          "thumbnail"                -> x.thumbnail.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "has_spoiler"              -> x.hasSpoiler.asJson,
          "disable_notification"     -> x.disableNotification.asJson,
          "protect_content"          -> x.protectContent.asJson,
          "allow_paid_broadcast"     -> x.allowPaidBroadcast.asJson,
          "message_effect_id"        -> x.messageEffectId.asJson,
          "reply_parameters"         -> x.replyParameters.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "method"                   -> "sendAnimation".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendAudio".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendContact".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendDice".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"           -> x.allowPaidBroadcast.asJson,
          "message_effect_id"              -> x.messageEffectId.asJson,
          "reply_parameters"               -> x.replyParameters.asJson,
          "reply_markup"                   -> x.replyMarkup.asJson,
          "method"                         -> "sendDocument".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendGame".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendgiftreqEncoder: Encoder[SendGiftReq] =
    (x: SendGiftReq) => {
      Json.fromFields(
        List(
          "user_id"         -> x.userId.asJson,
          "chat_id"         -> x.chatId.asJson,
          "gift_id"         -> x.giftId.asJson,
          "pay_for_upgrade" -> x.payForUpgrade.asJson,
          "text"            -> x.text.asJson,
          "text_parse_mode" -> x.textParseMode.asJson,
          "text_entities"   -> x.textEntities.asJson,
          "method"          -> "sendGift".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"          -> x.allowPaidBroadcast.asJson,
          "message_effect_id"             -> x.messageEffectId.asJson,
          "reply_parameters"              -> x.replyParameters.asJson,
          "reply_markup"                  -> x.replyMarkup.asJson,
          "method"                        -> "sendInvoice".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendLocation".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "method"                 -> "sendMediaGroup".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendMessage".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendpaidmediareqEncoder: Encoder[SendPaidMediaReq] =
    (x: SendPaidMediaReq) => {
      Json.fromFields(
        List(
          "business_connection_id"   -> x.businessConnectionId.asJson,
          "chat_id"                  -> x.chatId.asJson,
          "star_count"               -> x.starCount.asJson,
          "media"                    -> x.media.asJson,
          "payload"                  -> x.payload.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "disable_notification"     -> x.disableNotification.asJson,
          "protect_content"          -> x.protectContent.asJson,
          "allow_paid_broadcast"     -> x.allowPaidBroadcast.asJson,
          "reply_parameters"         -> x.replyParameters.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "method"                   -> "sendPaidMedia".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendphotoreqEncoder: Encoder[SendPhotoReq] =
    (x: SendPhotoReq) => {
      Json.fromFields(
        List(
          "business_connection_id"   -> x.businessConnectionId.asJson,
          "chat_id"                  -> x.chatId.asJson,
          "message_thread_id"        -> x.messageThreadId.asJson,
          "photo"                    -> x.photo.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "has_spoiler"              -> x.hasSpoiler.asJson,
          "disable_notification"     -> x.disableNotification.asJson,
          "protect_content"          -> x.protectContent.asJson,
          "allow_paid_broadcast"     -> x.allowPaidBroadcast.asJson,
          "message_effect_id"        -> x.messageEffectId.asJson,
          "reply_parameters"         -> x.replyParameters.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "method"                   -> "sendPhoto".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"    -> x.allowPaidBroadcast.asJson,
          "message_effect_id"       -> x.messageEffectId.asJson,
          "reply_parameters"        -> x.replyParameters.asJson,
          "reply_markup"            -> x.replyMarkup.asJson,
          "method"                  -> "sendPoll".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendSticker".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendVenue".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val sendvideoreqEncoder: Encoder[SendVideoReq] =
    (x: SendVideoReq) => {
      Json.fromFields(
        List(
          "business_connection_id"   -> x.businessConnectionId.asJson,
          "chat_id"                  -> x.chatId.asJson,
          "message_thread_id"        -> x.messageThreadId.asJson,
          "video"                    -> x.video.asJson,
          "duration"                 -> x.duration.asJson,
          "width"                    -> x.width.asJson,
          "height"                   -> x.height.asJson,
          "thumbnail"                -> x.thumbnail.asJson,
          "cover"                    -> x.cover.asJson,
          "start_timestamp"          -> x.startTimestamp.asJson,
          "caption"                  -> x.caption.asJson,
          "parse_mode"               -> x.parseMode.asJson,
          "caption_entities"         -> x.captionEntities.asJson,
          "show_caption_above_media" -> x.showCaptionAboveMedia.asJson,
          "has_spoiler"              -> x.hasSpoiler.asJson,
          "supports_streaming"       -> x.supportsStreaming.asJson,
          "disable_notification"     -> x.disableNotification.asJson,
          "protect_content"          -> x.protectContent.asJson,
          "allow_paid_broadcast"     -> x.allowPaidBroadcast.asJson,
          "message_effect_id"        -> x.messageEffectId.asJson,
          "reply_parameters"         -> x.replyParameters.asJson,
          "reply_markup"             -> x.replyMarkup.asJson,
          "method"                   -> "sendVideo".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendVideoNote".asJson
        ).filter(!_._2.isNull)
      )
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
          "allow_paid_broadcast"   -> x.allowPaidBroadcast.asJson,
          "message_effect_id"      -> x.messageEffectId.asJson,
          "reply_parameters"       -> x.replyParameters.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "sendVoice".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val setuseremojistatusreqEncoder: Encoder[SetUserEmojiStatusReq] =
    (x: SetUserEmojiStatusReq) => {
      Json.fromFields(
        List(
          "user_id"                      -> x.userId.asJson,
          "emoji_status_custom_emoji_id" -> x.emojiStatusCustomEmojiId.asJson,
          "emoji_status_expiration_date" -> x.emojiStatusExpirationDate.asJson,
          "method"                       -> "setUserEmojiStatus".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val stopmessagelivelocationreqEncoder: Encoder[StopMessageLiveLocationReq] =
    (x: StopMessageLiveLocationReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_id"             -> x.messageId.asJson,
          "inline_message_id"      -> x.inlineMessageId.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "stopMessageLiveLocation".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val stoppollreqEncoder: Encoder[StopPollReq] =
    (x: StopPollReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_id"             -> x.messageId.asJson,
          "reply_markup"           -> x.replyMarkup.asJson,
          "method"                 -> "stopPoll".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val unhidegeneralforumtopicreqEncoder: Encoder[UnhideGeneralForumTopicReq] =
    (x: UnhideGeneralForumTopicReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "unhideGeneralForumTopic".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val unpinallgeneralforumtopicmessagesreqEncoder: Encoder[UnpinAllGeneralForumTopicMessagesReq] =
    (x: UnpinAllGeneralForumTopicMessagesReq) => {
      Json.fromFields(
        List(
          "chat_id" -> x.chatId.asJson,
          "method"  -> "unpinAllGeneralForumTopicMessages".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val unpinchatmessagereqEncoder: Encoder[UnpinChatMessageReq] =
    (x: UnpinChatMessageReq) => {
      Json.fromFields(
        List(
          "business_connection_id" -> x.businessConnectionId.asJson,
          "chat_id"                -> x.chatId.asJson,
          "message_id"             -> x.messageId.asJson,
          "method"                 -> "unpinChatMessage".asJson
        ).filter(!_._2.isNull)
      )
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

  implicit lazy val verifychatreqEncoder: Encoder[VerifyChatReq] =
    (x: VerifyChatReq) => {
      Json.fromFields(
        List(
          "chat_id"            -> x.chatId.asJson,
          "custom_description" -> x.customDescription.asJson,
          "method"             -> "verifyChat".asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val verifyuserreqEncoder: Encoder[VerifyUserReq] =
    (x: VerifyUserReq) => {
      Json.fromFields(
        List(
          "user_id"            -> x.userId.asJson,
          "custom_description" -> x.customDescription.asJson,
          "method"             -> "verifyUser".asJson
        ).filter(!_._2.isNull)
      )
    }

}
