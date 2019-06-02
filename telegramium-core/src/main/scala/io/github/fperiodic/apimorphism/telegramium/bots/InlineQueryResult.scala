package io.github.fperiodic.apimorphism.telegramium.bots

sealed trait InlineQueryResult {}

/** Represents a link to an animated GIF file. By default, this animated GIF file
  * will be sent by the user with optional caption. Alternatively, you can use
  * input_message_content to send a message with the specified content instead of
  * the animation.*/
final case class InlineQueryResultGif(
                                      /** Unique identifier for this result, 1-64 bytes*/
                                      id: String,
                                      /** A valid URL for the GIF file. File size must not exceed 1MB*/
                                      gifUrl: String,
                                      /** Optional. Width of the GIF*/
                                      gifWidth: Option[Int] = Option.empty,
                                      /** Optional. Height of the GIF*/
                                      gifHeight: Option[Int] = Option.empty,
                                      /** Optional. Duration of the GIF*/
                                      gifDuration: Option[Int] = Option.empty,
                                      /** URL of the static thumbnail for the result (jpeg or gif)*/
                                      thumbUrl: String,
                                      /** Optional. Title for the result*/
                                      title: Option[String] = Option.empty,
                                      /** Optional. Caption of the GIF file to be sent, 0-1024
                                        * characters*/
                                      caption: Option[String] = Option.empty,
                                      /** Optional. Send Markdown or HTML, if you want Telegram apps
                                        * to show bold, italic, fixed-width text or inline URLs in the
                                        * media caption.*/
                                      parseMode: Option[String] = Option.empty,
                                      /** Optional. Inline keyboard attached to the message*/
                                      replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                      /** Optional. Content of the message to be sent instead of the
                                        * GIF animation*/
                                      inputMessageContent: Option[InputMessageContent] =
                                        Option.empty)
    extends InlineQueryResult

/** Represents a venue. By default, the venue will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the
  * specified content instead of the venue.*/
final case class InlineQueryResultVenue(
                                        /** Unique identifier for this result, 1-64 Bytes*/
                                        id: String,
                                        /** Latitude of the venue location in degrees*/
                                        latitude: Float,
                                        /** Longitude of the venue location in degrees*/
                                        longitude: Float,
                                        /** Title of the venue*/
                                        title: String,
                                        /** Address of the venue*/
                                        address: String,
                                        /** Optional. Foursquare identifier of the venue if known*/
                                        foursquareId: Option[String] = Option.empty,
                                        /** Optional. Foursquare type of the venue, if known. (For
                                          * example, “arts_entertainment/default”,
                                          * “arts_entertainment/aquarium” or “food/icecream”.)*/
                                        foursquareType: Option[String] = Option.empty,
                                        /** Optional. Inline keyboard attached to the message*/
                                        replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                        /** Optional. Content of the message to be sent instead of the
                                          * venue*/
                                        inputMessageContent: Option[InputMessageContent] =
                                          Option.empty,
                                        /** Optional. Url of the thumbnail for the result*/
                                        thumbUrl: Option[String] = Option.empty,
                                        /** Optional. Thumbnail width*/
                                        thumbWidth: Option[Int] = Option.empty,
                                        /** Optional. Thumbnail height*/
                                        thumbHeight: Option[Int] = Option.empty)
    extends InlineQueryResult

/** Represents a contact with a phone number. By default, this contact will be sent
  * by the user. Alternatively, you can use input_message_content to send a message
  * with the specified content instead of the contact.*/
final case class InlineQueryResultContact(
                                          /** Unique identifier for this result, 1-64 Bytes*/
                                          id: String,
                                          /** Contact's phone number*/
                                          phoneNumber: String,
                                          /** Contact's first name*/
                                          firstName: String,
                                          /** Optional. Contact's last name*/
                                          lastName: Option[String] = Option.empty,
                                          /** Optional. Additional data about the contact in the form of
                                            * a vCard, 0-2048 bytes*/
                                          vcard: Option[String] = Option.empty,
                                          /** Optional. Inline keyboard attached to the message*/
                                          replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                          /** Optional. Content of the message to be sent instead of the
                                            * contact*/
                                          inputMessageContent: Option[InputMessageContent] =
                                            Option.empty,
                                          /** Optional. Url of the thumbnail for the result*/
                                          thumbUrl: Option[String] = Option.empty,
                                          /** Optional. Thumbnail width*/
                                          thumbWidth: Option[Int] = Option.empty,
                                          /** Optional. Thumbnail height*/
                                          thumbHeight: Option[Int] = Option.empty)
    extends InlineQueryResult

/** Represents a link to a photo. By default, this photo will be sent by the user
  * with optional caption. Alternatively, you can use input_message_content to send
  * a message with the specified content instead of the photo.*/
final case class InlineQueryResultPhoto(
                                        /** Unique identifier for this result, 1-64 bytes*/
                                        id: String,
                                        /** A valid URL of the photo. Photo must be in jpeg format.
                                          * Photo size must not exceed 5MB*/
                                        photoUrl: String,
                                        /** URL of the thumbnail for the photo*/
                                        thumbUrl: String,
                                        /** Optional. Width of the photo*/
                                        photoWidth: Option[Int] = Option.empty,
                                        /** Optional. Height of the photo*/
                                        photoHeight: Option[Int] = Option.empty,
                                        /** Optional. Title for the result*/
                                        title: Option[String] = Option.empty,
                                        /** Optional. Short description of the result*/
                                        description: Option[String] = Option.empty,
                                        /** Optional. Caption of the photo to be sent, 0-1024
                                          * characters*/
                                        caption: Option[String] = Option.empty,
                                        /** Optional. Send Markdown or HTML, if you want Telegram apps
                                          * to show bold, italic, fixed-width text or inline URLs in the
                                          * media caption.*/
                                        parseMode: Option[String] = Option.empty,
                                        /** Optional. Inline keyboard attached to the message*/
                                        replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                        /** Optional. Content of the message to be sent instead of the
                                          * photo*/
                                        inputMessageContent: Option[InputMessageContent] =
                                          Option.empty)
    extends InlineQueryResult

/** Represents a link to a file. By default, this file will be sent by the user
  * with an optional caption. Alternatively, you can use input_message_content to
  * send a message with the specified content instead of the file. Currently, only
  * .PDF and .ZIP files can be sent using this method.*/
final case class InlineQueryResultDocument(
                                           /** Unique identifier for this result, 1-64 bytes*/
                                           id: String,
                                           /** Title for the result*/
                                           title: String,
                                           /** Optional. Caption of the document to be sent, 0-1024
                                             * characters*/
                                           caption: Option[String] = Option.empty,
                                           /** Optional. Send Markdown or HTML, if you want Telegram apps
                                             * to show bold, italic, fixed-width text or inline URLs in the
                                             * media caption.*/
                                           parseMode: Option[String] = Option.empty,
                                           /** A valid URL for the file*/
                                           documentUrl: String,
                                           /** Mime type of the content of the file, either
                                             * “application/pdf” or “application/zip”*/
                                           mimeType: String,
                                           /** Optional. Short description of the result*/
                                           description: Option[String] = Option.empty,
                                           /** Optional. Inline keyboard attached to the message*/
                                           replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                           /** Optional. Content of the message to be sent instead of the
                                             * file*/
                                           inputMessageContent: Option[InputMessageContent] =
                                             Option.empty,
                                           /** Optional. URL of the thumbnail (jpeg only) for the file*/
                                           thumbUrl: Option[String] = Option.empty,
                                           /** Optional. Thumbnail width*/
                                           thumbWidth: Option[Int] = Option.empty,
                                           /** Optional. Thumbnail height*/
                                           thumbHeight: Option[Int] = Option.empty)
    extends InlineQueryResult

/** Represents a link to a voice message stored on the Telegram servers. By
  * default, this voice message will be sent by the user. Alternatively, you can use
  * input_message_content to send a message with the specified content instead of
  * the voice message.*/
final case class InlineQueryResultCachedVoice(
    /** Unique identifier for this result, 1-64 bytes*/
    id: String,
    /** A valid file identifier for the voice message*/
    voiceFileId: String,
    /** Voice message title*/
    title: String,
    /** Optional. Caption, 0-1024 characters*/
    caption: Option[String] = Option.empty,
    /** Optional. Send Markdown or HTML, if you want Telegram apps
      * to show bold, italic, fixed-width text or inline URLs in the
      * media caption.*/
    parseMode: Option[String] = Option.empty,
    /** Optional. Inline keyboard attached to the message*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
    /** Optional. Content of the message to be sent instead of the
      * voice message*/
    inputMessageContent: Option[InputMessageContent] = Option.empty)
    extends InlineQueryResult

/** Represents a link to an article or web page.*/
final case class InlineQueryResultArticle(
                                          /** Unique identifier for this result, 1-64 Bytes*/
                                          id: String,
                                          /** Title of the result*/
                                          title: String,
                                          /** Content of the message to be sent*/
                                          inputMessageContent: InputMessageContent,
                                          /** Optional. Inline keyboard attached to the message*/
                                          replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                          /** Optional. URL of the result*/
                                          url: Option[String] = Option.empty,
                                          /** Optional. Pass True, if you don't want the URL to be shown
                                            * in the message*/
                                          hideUrl: Option[Boolean] = Option.empty,
                                          /** Optional. Short description of the result*/
                                          description: Option[String] = Option.empty,
                                          /** Optional. Url of the thumbnail for the result*/
                                          thumbUrl: Option[String] = Option.empty,
                                          /** Optional. Thumbnail width*/
                                          thumbWidth: Option[Int] = Option.empty,
                                          /** Optional. Thumbnail height*/
                                          thumbHeight: Option[Int] = Option.empty)
    extends InlineQueryResult

/** Represents a link to an mp3 audio file. By default, this audio file will be
  * sent by the user. Alternatively, you can use input_message_content to send a
  * message with the specified content instead of the audio.*/
final case class InlineQueryResultAudio(
                                        /** Unique identifier for this result, 1-64 bytes*/
                                        id: String,
                                        /** A valid URL for the audio file*/
                                        audioUrl: String,
                                        /** Title*/
                                        title: String,
                                        /** Optional. Caption, 0-1024 characters*/
                                        caption: Option[String] = Option.empty,
                                        /** Optional. Send Markdown or HTML, if you want Telegram apps
                                          * to show bold, italic, fixed-width text or inline URLs in the
                                          * media caption.*/
                                        parseMode: Option[String] = Option.empty,
                                        /** Optional. Performer*/
                                        performer: Option[String] = Option.empty,
                                        /** Optional. Audio duration in seconds*/
                                        audioDuration: Option[Int] = Option.empty,
                                        /** Optional. Inline keyboard attached to the message*/
                                        replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                        /** Optional. Content of the message to be sent instead of the
                                          * audio*/
                                        inputMessageContent: Option[InputMessageContent] =
                                          Option.empty)
    extends InlineQueryResult

/** Represents a link to a video animation (H.264/MPEG-4 AVC video without sound).
  * By default, this animated MPEG-4 file will be sent by the user with optional
  * caption. Alternatively, you can use input_message_content to send a message with
  * the specified content instead of the animation.*/
final case class InlineQueryResultMpeg4Gif(
                                           /** Unique identifier for this result, 1-64 bytes*/
                                           id: String,
                                           /** A valid URL for the MP4 file. File size must not exceed 1MB*/
                                           mpeg4Url: String,
                                           /** Optional. Video width*/
                                           mpeg4Width: Option[Int] = Option.empty,
                                           /** Optional. Video height*/
                                           mpeg4Height: Option[Int] = Option.empty,
                                           /** Optional. Video duration*/
                                           mpeg4Duration: Option[Int] = Option.empty,
                                           /** URL of the static thumbnail (jpeg or gif) for the result*/
                                           thumbUrl: String,
                                           /** Optional. Title for the result*/
                                           title: Option[String] = Option.empty,
                                           /** Optional. Caption of the MPEG-4 file to be sent, 0-1024
                                             * characters*/
                                           caption: Option[String] = Option.empty,
                                           /** Optional. Send Markdown or HTML, if you want Telegram apps
                                             * to show bold, italic, fixed-width text or inline URLs in the
                                             * media caption.*/
                                           parseMode: Option[String] = Option.empty,
                                           /** Optional. Inline keyboard attached to the message*/
                                           replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                           /** Optional. Content of the message to be sent instead of the
                                             * video animation*/
                                           inputMessageContent: Option[InputMessageContent] =
                                             Option.empty)
    extends InlineQueryResult

/** Represents a link to a video animation (H.264/MPEG-4 AVC video without sound)
  * stored on the Telegram servers. By default, this animated MPEG-4 file will be
  * sent by the user with an optional caption. Alternatively, you can use
  * input_message_content to send a message with the specified content instead of
  * the animation.*/
final case class InlineQueryResultCachedMpeg4Gif(
    /** Unique identifier for this result, 1-64 bytes*/
    id: String,
    /** A valid file identifier for the MP4 file*/
    mpeg4FileId: String,
    /** Optional. Title for the result*/
    title: Option[String] = Option.empty,
    /** Optional. Caption of the MPEG-4 file to be sent, 0-1024
      * characters*/
    caption: Option[String] = Option.empty,
    /** Optional. Send Markdown or HTML, if you want Telegram apps
      * to show bold, italic, fixed-width text or inline URLs in the
      * media caption.*/
    parseMode: Option[String] = Option.empty,
    /** Optional. Inline keyboard attached to the message*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
    /** Optional. Content of the message to be sent instead of the
      * video animation*/
    inputMessageContent: Option[InputMessageContent] = Option.empty)
    extends InlineQueryResult

/** Represents a link to a file stored on the Telegram servers. By default, this
  * file will be sent by the user with an optional caption. Alternatively, you can
  * use input_message_content to send a message with the specified content instead
  * of the file.*/
final case class InlineQueryResultCachedDocument(
    /** Unique identifier for this result, 1-64 bytes*/
    id: String,
    /** Title for the result*/
    title: String,
    /** A valid file identifier for the file*/
    documentFileId: String,
    /** Optional. Short description of the result*/
    description: Option[String] = Option.empty,
    /** Optional. Caption of the document to be sent, 0-1024
      * characters*/
    caption: Option[String] = Option.empty,
    /** Optional. Send Markdown or HTML, if you want Telegram apps
      * to show bold, italic, fixed-width text or inline URLs in the
      * media caption.*/
    parseMode: Option[String] = Option.empty,
    /** Optional. Inline keyboard attached to the message*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
    /** Optional. Content of the message to be sent instead of the
      * file*/
    inputMessageContent: Option[InputMessageContent] = Option.empty)
    extends InlineQueryResult

/** Represents a link to a video file stored on the Telegram servers. By default,
  * this video file will be sent by the user with an optional caption.
  * Alternatively, you can use input_message_content to send a message with the
  * specified content instead of the video.*/
final case class InlineQueryResultCachedVideo(
    /** Unique identifier for this result, 1-64 bytes*/
    id: String,
    /** A valid file identifier for the video file*/
    videoFileId: String,
    /** Title for the result*/
    title: String,
    /** Optional. Short description of the result*/
    description: Option[String] = Option.empty,
    /** Optional. Caption of the video to be sent, 0-1024
      * characters*/
    caption: Option[String] = Option.empty,
    /** Optional. Send Markdown or HTML, if you want Telegram apps
      * to show bold, italic, fixed-width text or inline URLs in the
      * media caption.*/
    parseMode: Option[String] = Option.empty,
    /** Optional. Inline keyboard attached to the message*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
    /** Optional. Content of the message to be sent instead of the
      * video*/
    inputMessageContent: Option[InputMessageContent] = Option.empty)
    extends InlineQueryResult

/** Represents a Game.*/
final case class InlineQueryResultGame(
                                       /** Unique identifier for this result, 1-64 bytes*/
                                       id: String,
                                       /** Short name of the game*/
                                       gameShortName: String,
                                       /** Optional. Inline keyboard attached to the message*/
                                       replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
    extends InlineQueryResult

/** Represents a link to a photo stored on the Telegram servers. By default, this
  * photo will be sent by the user with an optional caption. Alternatively, you can
  * use input_message_content to send a message with the specified content instead
  * of the photo.*/
final case class InlineQueryResultCachedPhoto(
    /** Unique identifier for this result, 1-64 bytes*/
    id: String,
    /** A valid file identifier of the photo*/
    photoFileId: String,
    /** Optional. Title for the result*/
    title: Option[String] = Option.empty,
    /** Optional. Short description of the result*/
    description: Option[String] = Option.empty,
    /** Optional. Caption of the photo to be sent, 0-1024
      * characters*/
    caption: Option[String] = Option.empty,
    /** Optional. Send Markdown or HTML, if you want Telegram apps
      * to show bold, italic, fixed-width text or inline URLs in the
      * media caption.*/
    parseMode: Option[String] = Option.empty,
    /** Optional. Inline keyboard attached to the message*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
    /** Optional. Content of the message to be sent instead of the
      * photo*/
    inputMessageContent: Option[InputMessageContent] = Option.empty)
    extends InlineQueryResult

/** Represents a link to a sticker stored on the Telegram servers. By default, this
  * sticker will be sent by the user. Alternatively, you can use
  * input_message_content to send a message with the specified content instead of
  * the sticker.*/
final case class InlineQueryResultCachedSticker(
    /** Unique identifier for this result, 1-64 bytes*/
    id: String,
    /** A valid file identifier of the sticker*/
    stickerFileId: String,
    /** Optional. Inline keyboard attached to the message*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
    /** Optional. Content of the message to be sent instead of the
      * sticker*/
    inputMessageContent: Option[InputMessageContent] = Option.empty)
    extends InlineQueryResult

/** Represents a link to a page containing an embedded video player or a video
  * file. By default, this video file will be sent by the user with an optional
  * caption. Alternatively, you can use input_message_content to send a message with
  * the specified content instead of the video.*/
final case class InlineQueryResultVideo(
                                        /** Unique identifier for this result, 1-64 bytes*/
                                        id: String,
                                        /** A valid URL for the embedded video player or video file*/
                                        videoUrl: String,
                                        /** Mime type of the content of video url, “text/html” or
                                          * “video/mp4”*/
                                        mimeType: String,
                                        /** URL of the thumbnail (jpeg only) for the video*/
                                        thumbUrl: String,
                                        /** Title for the result*/
                                        title: String,
                                        /** Optional. Caption of the video to be sent, 0-1024
                                          * characters*/
                                        caption: Option[String] = Option.empty,
                                        /** Optional. Send Markdown or HTML, if you want Telegram apps
                                          * to show bold, italic, fixed-width text or inline URLs in the
                                          * media caption.*/
                                        parseMode: Option[String] = Option.empty,
                                        /** Optional. Video width*/
                                        videoWidth: Option[Int] = Option.empty,
                                        /** Optional. Video height*/
                                        videoHeight: Option[Int] = Option.empty,
                                        /** Optional. Video duration in seconds*/
                                        videoDuration: Option[Int] = Option.empty,
                                        /** Optional. Short description of the result*/
                                        description: Option[String] = Option.empty,
                                        /** Optional. Inline keyboard attached to the message*/
                                        replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                        /** Optional. Content of the message to be sent instead of the
                                          * video. This field is required if InlineQueryResultVideo is
                                          * used to send an HTML-page as a result (e.g., a YouTube
                                          * video).*/
                                        inputMessageContent: Option[InputMessageContent] =
                                          Option.empty)
    extends InlineQueryResult

/** Represents a link to an mp3 audio file stored on the Telegram servers. By
  * default, this audio file will be sent by the user. Alternatively, you can use
  * input_message_content to send a message with the specified content instead of
  * the audio.*/
final case class InlineQueryResultCachedAudio(
    /** Unique identifier for this result, 1-64 bytes*/
    id: String,
    /** A valid file identifier for the audio file*/
    audioFileId: String,
    /** Optional. Caption, 0-1024 characters*/
    caption: Option[String] = Option.empty,
    /** Optional. Send Markdown or HTML, if you want Telegram apps
      * to show bold, italic, fixed-width text or inline URLs in the
      * media caption.*/
    parseMode: Option[String] = Option.empty,
    /** Optional. Inline keyboard attached to the message*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
    /** Optional. Content of the message to be sent instead of the
      * audio*/
    inputMessageContent: Option[InputMessageContent] = Option.empty)
    extends InlineQueryResult

/** Represents a location on a map. By default, the location will be sent by the
  * user. Alternatively, you can use input_message_content to send a message with
  * the specified content instead of the location.*/
final case class InlineQueryResultLocation(
                                           /** Unique identifier for this result, 1-64 Bytes*/
                                           id: String,
                                           /** Location latitude in degrees*/
                                           latitude: Float,
                                           /** Location longitude in degrees*/
                                           longitude: Float,
                                           /** Location title*/
                                           title: String,
                                           /** Optional. Period in seconds for which the location can be
                                             * updated, should be between 60 and 86400.*/
                                           livePeriod: Option[Int] = Option.empty,
                                           /** Optional. Inline keyboard attached to the message*/
                                           replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                           /** Optional. Content of the message to be sent instead of the
                                             * location*/
                                           inputMessageContent: Option[InputMessageContent] =
                                             Option.empty,
                                           /** Optional. Url of the thumbnail for the result*/
                                           thumbUrl: Option[String] = Option.empty,
                                           /** Optional. Thumbnail width*/
                                           thumbWidth: Option[Int] = Option.empty,
                                           /** Optional. Thumbnail height*/
                                           thumbHeight: Option[Int] = Option.empty)
    extends InlineQueryResult

/** Represents a link to an animated GIF file stored on the Telegram servers. By
  * default, this animated GIF file will be sent by the user with an optional
  * caption. Alternatively, you can use input_message_content to send a message with
  * specified content instead of the animation.*/
final case class InlineQueryResultCachedGif(
    /** Unique identifier for this result, 1-64 bytes*/
    id: String,
    /** A valid file identifier for the GIF file*/
    gifFileId: String,
    /** Optional. Title for the result*/
    title: Option[String] = Option.empty,
    /** Optional. Caption of the GIF file to be sent, 0-1024
      * characters*/
    caption: Option[String] = Option.empty,
    /** Optional. Send Markdown or HTML, if you want Telegram apps
      * to show bold, italic, fixed-width text or inline URLs in the
      * media caption.*/
    parseMode: Option[String] = Option.empty,
    /** Optional. Inline keyboard attached to the message*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
    /** Optional. Content of the message to be sent instead of the
      * GIF animation*/
    inputMessageContent: Option[InputMessageContent] = Option.empty)
    extends InlineQueryResult

/** Represents a link to a voice recording in an .ogg container encoded with OPUS.
  * By default, this voice recording will be sent by the user. Alternatively, you
  * can use input_message_content to send a message with the specified content
  * instead of the the voice message.*/
final case class InlineQueryResultVoice(
                                        /** Unique identifier for this result, 1-64 bytes*/
                                        id: String,
                                        /** A valid URL for the voice recording*/
                                        voiceUrl: String,
                                        /** Recording title*/
                                        title: String,
                                        /** Optional. Caption, 0-1024 characters*/
                                        caption: Option[String] = Option.empty,
                                        /** Optional. Send Markdown or HTML, if you want Telegram apps
                                          * to show bold, italic, fixed-width text or inline URLs in the
                                          * media caption.*/
                                        parseMode: Option[String] = Option.empty,
                                        /** Optional. Recording duration in seconds*/
                                        voiceDuration: Option[Int] = Option.empty,
                                        /** Optional. Inline keyboard attached to the message*/
                                        replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
                                        /** Optional. Content of the message to be sent instead of the
                                          * voice recording*/
                                        inputMessageContent: Option[InputMessageContent] =
                                          Option.empty)
    extends InlineQueryResult
