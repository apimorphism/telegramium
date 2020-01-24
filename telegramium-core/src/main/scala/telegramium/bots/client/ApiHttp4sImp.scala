package telegramium.bots.client

import io.circe.syntax._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.client._
import org.http4s.circe._
import org.http4s.multipart.Part
import cats.effect._
import cats.MonadError
import cats.data.OptionT
import cats.syntax.functor._
import cats.syntax.flatMap._
import CirceImplicits._
import scala.concurrent.ExecutionContext
import org.http4s.multipart.Multipart
import cats.syntax.traverse._
import cats.instances.list._
import io.circe.Json
import telegramium.bots._
import telegramium.bots.CirceImplicits._

class ApiHttp4sImp[F[_]: ConcurrentEffect: ContextShift](http: Client[F], baseUrl: String)(
    implicit F: MonadError[F, Throwable],
    ec: ExecutionContext)
    extends Api[F] {

  def makePart(field: String, file: java.io.File)(
      implicit ec: ExecutionContext): F[List[Part[F]]] = {
    import org.http4s.headers._
    val ext    = "\\.[A-Za-z0-9]+$".r.findFirstIn(file.getName).getOrElse("").drop(1)
    val extOpt = MediaType.forExtension(ext).map(x => `Content-Type`(x))
    val error  = new IllegalArgumentException("Can't determine Content-Type for ext: " + ext)
    for {
      mediaType <- OptionT(F.pure(extOpt)).getOrElseF(F.raiseError(error))
    } yield {
      List(Part.fileData(field, file, ec, mediaType))
    }
  }

  /** Use this method to get current webhook status. Requires no parameters. On
          success, returns a WebhookInfo object. If the bot is using getUpdates, will
          return an object with the url field empty.  */
  def getWebhookInfo(): F[GetWebhookInfoRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getWebhookInfo"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)

      res <- http.expect(req)(jsonOf[F, GetWebhookInfoRes])
    } yield {
      res
    }

  }

  /** Use this method to set a new profile photo for the chat. Photos can't be
          changed for private chats. The bot must be an administrator in the chat for this
          to work and must have the appropriate admin rights. Returns True on success.  */
  def setChatPhoto(x: SetChatPhotoReq): F[SetChatPhotoRes] = {

    val photoPartF = x.photo match {
      case InputPartFile(f) => makePart("photo", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    List(photoPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri       <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setChatPhoto"))
          photoPart <- photoPartF
          body = Multipart[F](
            Vector(("chat_id", x.chatId.asJson),
                   ("photo", if (photoPart.isEmpty) { x.photo.asJson } else { Json.Null }))
              .filter(!_._2.isNull)
              .map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              photoPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SetChatPhotoRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setChatPhoto"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SetChatPhotoRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to get data for high score tables. Will return the score of the
          specified user and several of his neighbors in a game. On success, returns an
          Array of GameHighScore objects.  */
  def getGameHighScores(x: GetGameHighScoresReq): F[GetGameHighScoresRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getGameHighScores"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetGameHighScoresRes])
    } yield {
      res
    }

  }

  /** Use this method to send answers to callback queries sent from inline keyboards.
          The answer will be displayed to the user as a notification at the top of the
          chat screen or as an alert. On success, True is returned.  */
  def answerCallbackQuery(x: AnswerCallbackQueryReq): F[AnswerCallbackQueryRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/answerCallbackQuery"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, AnswerCallbackQueryRes])
    } yield {
      res
    }

  }

  /** Use this method to send text messages. On success, the sent Message is
          returned.  */
  def sendMessage(x: SendMessageReq): F[SendMessageRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendMessage"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SendMessageRes])
    } yield {
      res
    }

  }

  /** Use this method to get a list of profile pictures for a user. Returns a
          UserProfilePhotos object.  */
  def getUserProfilePhotos(x: GetUserProfilePhotosReq): F[GetUserProfilePhotosRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getUserProfilePhotos"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetUserProfilePhotosRes])
    } yield {
      res
    }

  }

  /** Use this method to send a native poll. On success, the sent Message is
          returned.  */
  def sendPoll(x: SendPollReq): F[SendPollRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendPoll"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SendPollRes])
    } yield {
      res
    }

  }

  /** Use this method to send phone contacts. On success, the sent Message is
          returned.  */
  def sendContact(x: SendContactReq): F[SendContactRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendContact"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SendContactRes])
    } yield {
      res
    }

  }

  /** Use this method to create new sticker set owned by a user. The bot will be able
          to edit the created sticker set. Returns True on success.  */
  def createNewStickerSet(x: CreateNewStickerSetReq): F[CreateNewStickerSetRes] = {

    val pngStickerPartF = x.pngSticker match {
      case InputPartFile(f) => makePart("png_sticker", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    List(pngStickerPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri            <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/createNewStickerSet"))
          pngStickerPart <- pngStickerPartF
          body = Multipart[F](
            Vector(
              ("user_id", x.userId.asJson),
              ("name", x.name.asJson),
              ("title", x.title.asJson),
              ("emojis", x.emojis.asJson),
              ("contains_masks", x.containsMasks.asJson),
              ("mask_position", x.maskPosition.asJson),
              ("pngSticker", if (pngStickerPart.isEmpty) { x.pngSticker.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              pngStickerPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, CreateNewStickerSetRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/createNewStickerSet"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, CreateNewStickerSetRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to upload a .png file with a sticker for later use in
          createNewStickerSet and addStickerToSet methods (can be used multiple times).
          Returns the uploaded File on success.  */
  def uploadStickerFile(x: UploadStickerFileReq): F[UploadStickerFileRes] = {

    val pngStickerPartF = x.pngSticker match {
      case InputPartFile(f) => makePart("png_sticker", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    List(pngStickerPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri            <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/uploadStickerFile"))
          pngStickerPart <- pngStickerPartF
          body = Multipart[F](
            Vector(("user_id", x.userId.asJson),
                   ("pngSticker", if (pngStickerPart.isEmpty) { x.pngSticker.asJson } else {
                     Json.Null
                   })).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              pngStickerPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, UploadStickerFileRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/uploadStickerFile"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, UploadStickerFileRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to set default chat permissions for all members. The bot must
          be an administrator in the group or a supergroup for this to work and must have
          the can_restrict_members admin rights. Returns True on success.  */
  def setChatPermissions(x: SetChatPermissionsReq): F[SetChatPermissionsRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setChatPermissions"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SetChatPermissionsRes])
    } yield {
      res
    }

  }

  /** Use this method to send point on the map. On success, the sent Message is
          returned.  */
  def sendLocation(x: SendLocationReq): F[SendLocationRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendLocation"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SendLocationRes])
    } yield {
      res
    }

  }

  /** Use this method to delete a group sticker set from a supergroup. The bot must
          be an administrator in the chat for this to work and must have the appropriate
          admin rights. Use the field can_set_sticker_set optionally returned in getChat
          requests to check if the bot can use this method. Returns True on success.  */
  def deleteChatStickerSet(x: DeleteChatStickerSetReq): F[DeleteChatStickerSetRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/deleteChatStickerSet"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, DeleteChatStickerSetRes])
    } yield {
      res
    }

  }

  /** Use this method to stop updating a live location message before live_period
          expires. On success, if the message was sent by the bot, the sent Message is
          returned, otherwise True is returned.  */
  def stopMessageLiveLocation(x: StopMessageLiveLocationReq): F[StopMessageLiveLocationRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/stopMessageLiveLocation"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, StopMessageLiveLocationRes])
    } yield {
      res
    }

  }

  /** Use this method to generate a new invite link for a chat; any previously
          generated link is revoked. The bot must be an administrator in the chat for this
          to work and must have the appropriate admin rights. Returns the new invite link
          as String on success.  */
  def exportChatInviteLink(x: ExportChatInviteLinkReq): F[ExportChatInviteLinkRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/exportChatInviteLink"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, ExportChatInviteLinkRes])
    } yield {
      res
    }

  }

  /** Use this method when you need to tell the user that something is happening on
          the bot's side. The status is set for 5 seconds or less (when a message arrives
          from your bot, Telegram clients clear its typing status). Returns True on
          success.  */
  def sendChatAction(): F[SendChatActionRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendChatAction"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)

      res <- http.expect(req)(jsonOf[F, SendChatActionRes])
    } yield {
      res
    }

  }

  /** Use this method to add a new sticker to a set created by the bot. Returns True
          on success.  */
  def addStickerToSet(x: AddStickerToSetReq): F[AddStickerToSetRes] = {

    val pngStickerPartF = x.pngSticker match {
      case InputPartFile(f) => makePart("png_sticker", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    List(pngStickerPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri            <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/addStickerToSet"))
          pngStickerPart <- pngStickerPartF
          body = Multipart[F](
            Vector(
              ("user_id", x.userId.asJson),
              ("name", x.name.asJson),
              ("emojis", x.emojis.asJson),
              ("mask_position", x.maskPosition.asJson),
              ("pngSticker", if (pngStickerPart.isEmpty) { x.pngSticker.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              pngStickerPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, AddStickerToSetRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/addStickerToSet"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, AddStickerToSetRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to delete a sticker from a set created by the bot. Returns True
          on success.  */
  def deleteStickerFromSet(x: DeleteStickerFromSetReq): F[DeleteStickerFromSetRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/deleteStickerFromSet"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, DeleteStickerFromSetRes])
    } yield {
      res
    }

  }

  /** Use this method to stop a poll which was sent by the bot. On success, the
          stopped Poll with the final results is returned.  */
  def stopPoll(x: StopPollReq): F[StopPollRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/stopPoll"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, StopPollRes])
    } yield {
      res
    }

  }

  /** Use this method to unpin a message in a group, a supergroup, or a channel. The
          bot must be an administrator in the chat for this to work and must have the
          ‘can_pin_messages’ admin right in the supergroup or ‘can_edit_messages’ admin
          right in the channel. Returns True on success.  */
  def unpinChatMessage(x: UnpinChatMessageReq): F[UnpinChatMessageRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/unpinChatMessage"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, UnpinChatMessageRes])
    } yield {
      res
    }

  }

  /** Use this method to send a group of photos or videos as an album. On success, an
          array of the sent Messages is returned.  */
  def sendMediaGroup(x: SendMediaGroupReq): F[SendMediaGroupRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendMediaGroup"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SendMediaGroupRes])
    } yield {
      res
    }

  }

  /** Use this method to send a game. On success, the sent Message is returned.  */
  def sendGame(x: SendGameReq): F[SendGameRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendGame"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SendGameRes])
    } yield {
      res
    }

  }

  /** Use this method to send information about a venue. On success, the sent Message
          is returned.  */
  def sendVenue(x: SendVenueReq): F[SendVenueRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendVenue"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SendVenueRes])
    } yield {
      res
    }

  }

  /** Use this method to unban a previously kicked user in a supergroup or channel.
          The user will not return to the group or channel automatically, but will be able
          to join via link, etc. The bot must be an administrator for this to work.
          Returns True on success.  */
  def unbanChatMember(x: UnbanChatMemberReq): F[UnbanChatMemberRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/unbanChatMember"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, UnbanChatMemberRes])
    } yield {
      res
    }

  }

  /** Use this method to change the description of a group, a supergroup or a
          channel. The bot must be an administrator in the chat for this to work and must
          have the appropriate admin rights. Returns True on success.  */
  def setChatDescription(x: SetChatDescriptionReq): F[SetChatDescriptionRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setChatDescription"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SetChatDescriptionRes])
    } yield {
      res
    }

  }

  /** Use this method to edit text and game messages. On success, if edited message
          is sent by the bot, the edited Message is returned, otherwise True is returned.  */
  def editMessageText(x: EditMessageTextReq): F[EditMessageTextRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/editMessageText"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, EditMessageTextRes])
    } yield {
      res
    }

  }

  /** Use this method to edit live location messages. A location can be edited until
          its live_period expires or editing is explicitly disabled by a call to
          stopMessageLiveLocation. On success, if the edited message was sent by the bot,
          the edited Message is returned, otherwise True is returned.  */
  def editMessageLiveLocation(x: EditMessageLiveLocationReq): F[EditMessageLiveLocationRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/editMessageLiveLocation"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, EditMessageLiveLocationRes])
    } yield {
      res
    }

  }

  /** Use this method to get basic info about a file and prepare it for downloading.
          For the moment, bots can download files of up to 20MB in size. On success, a
          File object is returned. The file can then be downloaded via the link
          https://api.telegram.org/file/bot<token>/<file_path>, where <file_path> is taken
          from the response. It is guaranteed that the link will be valid for at least 1
          hour. When the link expires, a new one can be requested by calling getFile
          again.  */
  def getFile(x: GetFileReq): F[GetFileRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getFile"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetFileRes])
    } yield {
      res
    }

  }

  /** Use this method to set the score of the specified user in a game. On success,
          if the message was sent by the bot, returns the edited Message, otherwise
          returns True. Returns an error, if the new score is not greater than the user's
          current score in the chat and force is False.  */
  def setGameScore(x: SetGameScoreReq): F[SetGameScoreRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setGameScore"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SetGameScoreRes])
    } yield {
      res
    }

  }

  /** Use this method for your bot to leave a group, supergroup or channel. Returns
          True on success.  */
  def leaveChat(x: LeaveChatReq): F[LeaveChatRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/leaveChat"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, LeaveChatRes])
    } yield {
      res
    }

  }

  /** Use this method to change the title of a chat. Titles can't be changed for
          private chats. The bot must be an administrator in the chat for this to work and
          must have the appropriate admin rights. Returns True on success.  */
  def setChatTitle(x: SetChatTitleReq): F[SetChatTitleRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setChatTitle"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SetChatTitleRes])
    } yield {
      res
    }

  }

  /** As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1
          minute long. Use this method to send video messages. On success, the sent
          Message is returned.  */
  def sendVideoNote(x: SendVideoNoteReq): F[SendVideoNoteRes] = {

    val videoNotePartF = x.videoNote match {
      case InputPartFile(f) => makePart("video_note", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    val thumbPartF = x.thumb match {
      case Some(InputPartFile(f)) => makePart("thumb", f)
      case _                      => F.pure(List.empty[Part[F]])
    }

    List(videoNotePartF, thumbPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri           <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendVideoNote"))
          videoNotePart <- videoNotePartF
          thumbPart     <- thumbPartF
          body = Multipart[F](
            Vector(
              ("chat_id", x.chatId.asJson),
              ("duration", x.duration.asJson),
              ("length", x.length.asJson),
              ("disable_notification", x.disableNotification.asJson),
              ("reply_to_message_id", x.replyToMessageId.asJson),
              ("reply_markup", x.replyMarkup.asJson),
              ("videoNote", if (videoNotePart.isEmpty) { x.videoNote.asJson } else { Json.Null }),
              ("thumb", if (thumbPart.isEmpty) { x.thumb.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              videoNotePart ++ thumbPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SendVideoNoteRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendVideoNote"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SendVideoNoteRes])
        } yield {
          res
        }

      }
    }
  }

  /** Informs a user that some of the Telegram Passport elements they provided
          contains errors. The user will not be able to re-submit their Passport to you
          until the errors are fixed (the contents of the field for which you returned the
          error must change). Returns True on success. Use this if the data submitted by
          the user doesn't satisfy the standards your service requires for any reason. For
          example, if a birthday date seems invalid, a submitted document is blurry, a
          scan shows evidence of tampering, etc. Supply some details in the error message
          to make sure the user knows how to correct the issues.  */
  def setPassportDataErrors(x: SetPassportDataErrorsReq): F[SetPassportDataErrorsRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setPassportDataErrors"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SetPassportDataErrorsRes])
    } yield {
      res
    }

  }

  /** Use this method to delete a chat photo. Photos can't be changed for private
          chats. The bot must be an administrator in the chat for this to work and must
          have the appropriate admin rights. Returns True on success.  */
  def deleteChatPhoto(x: DeleteChatPhotoReq): F[DeleteChatPhotoRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/deleteChatPhoto"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, DeleteChatPhotoRes])
    } yield {
      res
    }

  }

  /** Use this method to send invoices. On success, the sent Message is returned.  */
  def sendInvoice(x: SendInvoiceReq): F[SendInvoiceRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendInvoice"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SendInvoiceRes])
    } yield {
      res
    }

  }

  /** Use this method to send general files. On success, the sent Message is
          returned. Bots can currently send files of any type of up to 50 MB in size, this
          limit may be changed in the future.  */
  def sendDocument(x: SendDocumentReq): F[SendDocumentRes] = {

    val documentPartF = x.document match {
      case InputPartFile(f) => makePart("document", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    val thumbPartF = x.thumb match {
      case Some(InputPartFile(f)) => makePart("thumb", f)
      case _                      => F.pure(List.empty[Part[F]])
    }

    List(documentPartF, thumbPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri          <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendDocument"))
          documentPart <- documentPartF
          thumbPart    <- thumbPartF
          body = Multipart[F](
            Vector(
              ("chat_id", x.chatId.asJson),
              ("caption", x.caption.asJson),
              ("parse_mode", x.parseMode.asJson),
              ("disable_notification", x.disableNotification.asJson),
              ("reply_to_message_id", x.replyToMessageId.asJson),
              ("reply_markup", x.replyMarkup.asJson),
              ("document", if (documentPart.isEmpty) { x.document.asJson } else { Json.Null }),
              ("thumb", if (thumbPart.isEmpty) { x.thumb.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              documentPart ++ thumbPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SendDocumentRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendDocument"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SendDocumentRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to delete a message, including service messages, with the
          following limitations: - A message can only be deleted if it was sent less than
          48 hours ago. - Bots can delete outgoing messages in private chats, groups, and
          supergroups. - Bots can delete incoming messages in private chats. - Bots
          granted can_post_messages permissions can delete outgoing messages in channels.
          - If the bot is an administrator of a group, it can delete any message there. -
          If the bot has can_delete_messages permission in a supergroup or a channel, it
          can delete any message there. Returns True on success.  */
  def deleteMessage(x: DeleteMessageReq): F[DeleteMessageRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/deleteMessage"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, DeleteMessageRes])
    } yield {
      res
    }

  }

  /** Use this method to send answers to an inline query. On success, True is
          returned. No more than 50 results per query are allowed.  */
  def answerInlineQuery(x: AnswerInlineQueryReq): F[AnswerInlineQueryRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/answerInlineQuery"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, AnswerInlineQueryRes])
    } yield {
      res
    }

  }

  /** Use this method to kick a user from a group, a supergroup or a channel. In the
          case of supergroups and channels, the user will not be able to return to the
          group on their own using invite links, etc., unless unbanned first. The bot must
          be an administrator in the chat for this to work and must have the appropriate
          admin rights. Returns True on success.  */
  def kickChatMember(x: KickChatMemberReq): F[KickChatMemberRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/kickChatMember"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, KickChatMemberRes])
    } yield {
      res
    }

  }

  /** Use this method to send audio files, if you want Telegram clients to display
          them in the music player. Your audio must be in the .MP3 or .M4A format. On
          success, the sent Message is returned. Bots can currently send audio files of up
          to 50 MB in size, this limit may be changed in the future. For sending voice
          messages, use the sendVoice method instead.  */
  def sendAudio(x: SendAudioReq): F[SendAudioRes] = {

    val audioPartF = x.audio match {
      case InputPartFile(f) => makePart("audio", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    val thumbPartF = x.thumb match {
      case Some(InputPartFile(f)) => makePart("thumb", f)
      case _                      => F.pure(List.empty[Part[F]])
    }

    List(audioPartF, thumbPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri       <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendAudio"))
          audioPart <- audioPartF
          thumbPart <- thumbPartF
          body = Multipart[F](
            Vector(
              ("chat_id", x.chatId.asJson),
              ("caption", x.caption.asJson),
              ("parse_mode", x.parseMode.asJson),
              ("duration", x.duration.asJson),
              ("performer", x.performer.asJson),
              ("title", x.title.asJson),
              ("disable_notification", x.disableNotification.asJson),
              ("reply_to_message_id", x.replyToMessageId.asJson),
              ("reply_markup", x.replyMarkup.asJson),
              ("audio", if (audioPart.isEmpty) { x.audio.asJson } else { Json.Null }),
              ("thumb", if (thumbPart.isEmpty) { x.thumb.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              audioPart ++ thumbPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SendAudioRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendAudio"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SendAudioRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to restrict a user in a supergroup. The bot must be an
          administrator in the supergroup for this to work and must have the appropriate
          admin rights. Pass True for all permissions to lift restrictions from a user.
          Returns True on success.  */
  def restrictChatMember(x: RestrictChatMemberReq): F[RestrictChatMemberRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/restrictChatMember"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, RestrictChatMemberRes])
    } yield {
      res
    }

  }

  /** A simple method for testing your bot's auth token. Requires no parameters.
          Returns basic information about the bot in form of a User object.  */
  def getMe(): F[GetMeRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getMe"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)

      res <- http.expect(req)(jsonOf[F, GetMeRes])
    } yield {
      res
    }

  }

  /** Use this method to forward messages of any kind. On success, the sent Message
          is returned.  */
  def forwardMessage(x: ForwardMessageReq): F[ForwardMessageRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/forwardMessage"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, ForwardMessageRes])
    } yield {
      res
    }

  }

  /** Use this method to get information about a member of a chat. Returns a
          ChatMember object on success.  */
  def getChatMember(x: GetChatMemberReq): F[GetChatMemberRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getChatMember"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetChatMemberRes])
    } yield {
      res
    }

  }

  /** Use this method to get a list of administrators in a chat. On success, returns
          an Array of ChatMember objects that contains information about all chat
          administrators except other bots. If the chat is a group or a supergroup and no
          administrators were appointed, only the creator will be returned.  */
  def getChatAdministrators(x: GetChatAdministratorsReq): F[GetChatAdministratorsRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getChatAdministrators"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetChatAdministratorsRes])
    } yield {
      res
    }

  }

  /** Use this method to send audio files, if you want Telegram clients to display
          the file as a playable voice message. For this to work, your audio must be in an
          .ogg file encoded with OPUS (other formats may be sent as Audio or Document). On
          success, the sent Message is returned. Bots can currently send voice messages of
          up to 50 MB in size, this limit may be changed in the future.  */
  def sendVoice(x: SendVoiceReq): F[SendVoiceRes] = {

    val voicePartF = x.voice match {
      case InputPartFile(f) => makePart("voice", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    List(voicePartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri       <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendVoice"))
          voicePart <- voicePartF
          body = Multipart[F](
            Vector(
              ("chat_id", x.chatId.asJson),
              ("caption", x.caption.asJson),
              ("parse_mode", x.parseMode.asJson),
              ("duration", x.duration.asJson),
              ("disable_notification", x.disableNotification.asJson),
              ("reply_to_message_id", x.replyToMessageId.asJson),
              ("reply_markup", x.replyMarkup.asJson),
              ("voice", if (voicePart.isEmpty) { x.voice.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              voicePart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SendVoiceRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendVoice"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SendVoiceRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to promote or demote a user in a supergroup or a channel. The
          bot must be an administrator in the chat for this to work and must have the
          appropriate admin rights. Pass False for all boolean parameters to demote a
          user. Returns True on success.  */
  def promoteChatMember(x: PromoteChatMemberReq): F[PromoteChatMemberRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/promoteChatMember"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, PromoteChatMemberRes])
    } yield {
      res
    }

  }

  /** Use this method to edit captions of messages. On success, if edited message is
          sent by the bot, the edited Message is returned, otherwise True is returned.  */
  def editMessageCaption(x: EditMessageCaptionReq): F[EditMessageCaptionRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/editMessageCaption"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, EditMessageCaptionRes])
    } yield {
      res
    }

  }

  /** Use this method to edit animation, audio, document, photo, or video messages.
          If a message is a part of a message album, then it can be edited only to a photo
          or a video. Otherwise, message type can be changed arbitrarily. When inline
          message is edited, new file can't be uploaded. Use previously uploaded file via
          its file_id or specify a URL. On success, if the edited message was sent by the
          bot, the edited Message is returned, otherwise True is returned.  */
  def editMessageMedia(x: EditMessageMediaReq): F[EditMessageMediaRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/editMessageMedia"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, EditMessageMediaRes])
    } yield {
      res
    }

  }

  /** Use this method to pin a message in a group, a supergroup, or a channel. The
          bot must be an administrator in the chat for this to work and must have the
          ‘can_pin_messages’ admin right in the supergroup or ‘can_edit_messages’ admin
          right in the channel. Returns True on success.  */
  def pinChatMessage(x: PinChatMessageReq): F[PinChatMessageRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/pinChatMessage"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, PinChatMessageRes])
    } yield {
      res
    }

  }

  /** Use this method to edit only the reply markup of messages. On success, if
          edited message is sent by the bot, the edited Message is returned, otherwise
          True is returned.  */
  def editMessageReplyMarkup(x: EditMessageReplyMarkupReq): F[EditMessageReplyMarkupRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/editMessageReplyMarkup"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, EditMessageReplyMarkupRes])
    } yield {
      res
    }

  }

  /** Use this method to send video files, Telegram clients support mp4 videos (other
          formats may be sent as Document). On success, the sent Message is returned. Bots
          can currently send video files of up to 50 MB in size, this limit may be changed
          in the future.  */
  def sendVideo(x: SendVideoReq): F[SendVideoRes] = {

    val videoPartF = x.video match {
      case InputPartFile(f) => makePart("video", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    val thumbPartF = x.thumb match {
      case Some(InputPartFile(f)) => makePart("thumb", f)
      case _                      => F.pure(List.empty[Part[F]])
    }

    List(videoPartF, thumbPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri       <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendVideo"))
          videoPart <- videoPartF
          thumbPart <- thumbPartF
          body = Multipart[F](
            Vector(
              ("chat_id", x.chatId.asJson),
              ("duration", x.duration.asJson),
              ("width", x.width.asJson),
              ("height", x.height.asJson),
              ("caption", x.caption.asJson),
              ("parse_mode", x.parseMode.asJson),
              ("supports_streaming", x.supportsStreaming.asJson),
              ("disable_notification", x.disableNotification.asJson),
              ("reply_to_message_id", x.replyToMessageId.asJson),
              ("reply_markup", x.replyMarkup.asJson),
              ("video", if (videoPart.isEmpty) { x.video.asJson } else { Json.Null }),
              ("thumb", if (thumbPart.isEmpty) { x.thumb.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              videoPart ++ thumbPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SendVideoRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendVideo"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SendVideoRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to set a new group sticker set for a supergroup. The bot must
          be an administrator in the chat for this to work and must have the appropriate
          admin rights. Use the field can_set_sticker_set optionally returned in getChat
          requests to check if the bot can use this method. Returns True on success.  */
  def setChatStickerSet(x: SetChatStickerSetReq): F[SetChatStickerSetRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setChatStickerSet"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SetChatStickerSetRes])
    } yield {
      res
    }

  }

  /** Use this method to get up to date information about the chat (current name of
          the user for one-on-one conversations, current username of a user, group or
          channel, etc.). Returns a Chat object on success.  */
  def getChat(x: GetChatReq): F[GetChatRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getChat"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetChatRes])
    } yield {
      res
    }

  }

  /** Use this method to remove webhook integration if you decide to switch back to
          getUpdates. Returns True on success. Requires no parameters.  */
  def deleteWebhook(): F[DeleteWebhookRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/deleteWebhook"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)

      res <- http.expect(req)(jsonOf[F, DeleteWebhookRes])
    } yield {
      res
    }

  }

  /** Use this method to move a sticker in a set created by the bot to a specific
          position . Returns True on success.  */
  def setStickerPositionInSet(x: SetStickerPositionInSetReq): F[SetStickerPositionInSetRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setStickerPositionInSet"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SetStickerPositionInSetRes])
    } yield {
      res
    }

  }

  /** Use this method to set a custom title for an administrator in a supergroup
          promoted by the bot. Returns True on success.  */
  def setChatAdministratorCustomTitle(
      x: SetChatAdministratorCustomTitleReq): F[SetChatAdministratorCustomTitleRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setChatAdministratorCustomTitle"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, SetChatAdministratorCustomTitleRes])
    } yield {
      res
    }

  }

  /** Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without
          sound). On success, the sent Message is returned. Bots can currently send
          animation files of up to 50 MB in size, this limit may be changed in the future.  */
  def sendAnimation(x: SendAnimationReq): F[SendAnimationRes] = {

    val animationPartF = x.animation match {
      case InputPartFile(f) => makePart("animation", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    val thumbPartF = x.thumb match {
      case Some(InputPartFile(f)) => makePart("thumb", f)
      case _                      => F.pure(List.empty[Part[F]])
    }

    List(animationPartF, thumbPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri           <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendAnimation"))
          animationPart <- animationPartF
          thumbPart     <- thumbPartF
          body = Multipart[F](
            Vector(
              ("chat_id", x.chatId.asJson),
              ("duration", x.duration.asJson),
              ("width", x.width.asJson),
              ("height", x.height.asJson),
              ("caption", x.caption.asJson),
              ("parse_mode", x.parseMode.asJson),
              ("disable_notification", x.disableNotification.asJson),
              ("reply_to_message_id", x.replyToMessageId.asJson),
              ("reply_markup", x.replyMarkup.asJson),
              ("animation", if (animationPart.isEmpty) { x.animation.asJson } else { Json.Null }),
              ("thumb", if (thumbPart.isEmpty) { x.thumb.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              animationPart ++ thumbPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SendAnimationRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendAnimation"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SendAnimationRes])
        } yield {
          res
        }

      }
    }
  }

  /** If you sent an invoice requesting a shipping address and the parameter
          is_flexible was specified, the Bot API will send an Update with a shipping_query
          field to the bot. Use this method to reply to shipping queries. On success, True
          is returned.  */
  def answerShippingQuery(x: AnswerShippingQueryReq): F[AnswerShippingQueryRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/answerShippingQuery"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, AnswerShippingQueryRes])
    } yield {
      res
    }

  }

  /** Once the user has confirmed their payment and shipping details, the Bot API
          sends the final confirmation in the form of an Update with the field
          pre_checkout_query. Use this method to respond to such pre-checkout queries. On
          success, True is returned. Note: The Bot API must receive an answer within 10
          seconds after the pre-checkout query was sent.  */
  def answerPreCheckoutQuery(x: AnswerPreCheckoutQueryReq): F[AnswerPreCheckoutQueryRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/answerPreCheckoutQuery"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, AnswerPreCheckoutQueryRes])
    } yield {
      res
    }

  }

  /** Use this method to send static .WEBP or animated .TGS stickers. On success, the
          sent Message is returned.  */
  def sendSticker(x: SendStickerReq): F[SendStickerRes] = {

    val stickerPartF = x.sticker match {
      case InputPartFile(f) => makePart("sticker", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    List(stickerPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri         <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendSticker"))
          stickerPart <- stickerPartF
          body = Multipart[F](
            Vector(
              ("chat_id", x.chatId.asJson),
              ("disable_notification", x.disableNotification.asJson),
              ("reply_to_message_id", x.replyToMessageId.asJson),
              ("reply_markup", x.replyMarkup.asJson),
              ("sticker", if (stickerPart.isEmpty) { x.sticker.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              stickerPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SendStickerRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendSticker"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SendStickerRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to get the number of members in a chat. Returns Int on success.  */
  def getChatMembersCount(x: GetChatMembersCountReq): F[GetChatMembersCountRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getChatMembersCount"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetChatMembersCountRes])
    } yield {
      res
    }

  }

  /** Use this method to send photos. On success, the sent Message is returned.  */
  def sendPhoto(x: SendPhotoReq): F[SendPhotoRes] = {

    val photoPartF = x.photo match {
      case InputPartFile(f) => makePart("photo", f)
      case _                => F.pure(List.empty[Part[F]])
    }

    List(photoPartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri       <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendPhoto"))
          photoPart <- photoPartF
          body = Multipart[F](
            Vector(
              ("chat_id", x.chatId.asJson),
              ("caption", x.caption.asJson),
              ("parse_mode", x.parseMode.asJson),
              ("disable_notification", x.disableNotification.asJson),
              ("reply_to_message_id", x.replyToMessageId.asJson),
              ("reply_markup", x.replyMarkup.asJson),
              ("photo", if (photoPart.isEmpty) { x.photo.asJson } else { Json.Null })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              photoPart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SendPhotoRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/sendPhoto"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SendPhotoRes])
        } yield {
          res
        }

      }
    }
  }

  /** Use this method to receive incoming updates using long polling (wiki). An Array
          of Update objects is returned.  */
  def getUpdates(x: GetUpdatesReq): F[GetUpdatesRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getUpdates"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetUpdatesRes])
    } yield {
      res
    }

  }

  /** Use this method to get a sticker set. On success, a StickerSet object is
          returned.  */
  def getStickerSet(x: GetStickerSetReq): F[GetStickerSetRes] = {
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/getStickerSet"))
      req = Request[F]()
        .withMethod(GET)
        .withUri(uri)
        .withEntity(x.asJson)
      res <- http.expect(req)(jsonOf[F, GetStickerSetRes])
    } yield {
      res
    }

  }

  /** Use this method to specify a url and receive incoming updates via an outgoing
          webhook. Whenever there is an update for the bot, we will send an HTTPS POST
          request to the specified url, containing a JSON-serialized Update. In case of an
          unsuccessful request, we will give up after a reasonable amount of attempts.
          Returns True on success. If you'd like to make sure that the Webhook request
          comes from Telegram, we recommend using a secret path in the URL, e.g.
          https://www.example.com/<token>. Since nobody else knows your bot‘s token, you
          can be pretty sure it’s us.  */
  def setWebhook(x: SetWebhookReq): F[SetWebhookRes] = {

    val certificatePartF = x.certificate match {
      case Some(InputPartFile(f)) => makePart("certificate", f)
      case _                      => F.pure(List.empty[Part[F]])
    }

    List(certificatePartF).sequence.map(_.flatten).flatMap { l =>
      if (l.nonEmpty) {
        for {
          uri             <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setWebhook"))
          certificatePart <- certificatePartF
          body = Multipart[F](
            Vector(
              ("url", x.url.asJson),
              ("max_connections", x.maxConnections.asJson),
              ("allowed_updates", x.allowedUpdates.asJson),
              ("certificate", if (certificatePart.isEmpty) { x.certificate.asJson } else {
                Json.Null
              })
            ).filter(!_._2.isNull).map { case (n, v) => Part.formData(n, v.noSpaces) } ++
              certificatePart
          )
          req = Request[F]()
            .withMethod(POST)
            .withUri(uri)
            .withEntity(body)
            .withHeaders(body.headers)
          res <- http.expect(req)(jsonOf[F, SetWebhookRes])
        } yield {
          res
        }
      } else {
        for {
          uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/setWebhook"))
          req = Request[F]()
            .withMethod(GET)
            .withUri(uri)
            .withEntity(x.asJson)
          res <- http.expect(req)(jsonOf[F, SetWebhookRes])
        } yield {
          res
        }

      }
    }
  }

}
