package telegramium.bots

sealed trait MessageEntity {

  /** Offset in UTF-16 code units to the start of the entity*/
  def offset: Int

  /** Length of the entity in UTF-16 code units*/
  def length: Int
}

/** mention*/
final case class MentionMessageEntity(
                                      /** Offset in UTF-16 code units to the start of the entity*/
                                      offset: Int,
                                      /** Length of the entity in UTF-16 code units*/
                                      length: Int)
    extends MessageEntity

/** cashtag*/
final case class CashtagMessageEntity(
                                      /** Offset in UTF-16 code units to the start of the entity*/
                                      offset: Int,
                                      /** Length of the entity in UTF-16 code units*/
                                      length: Int)
    extends MessageEntity

/** code*/
final case class CodeMessageEntity(
                                   /** Offset in UTF-16 code units to the start of the entity*/
                                   offset: Int,
                                   /** Length of the entity in UTF-16 code units*/
                                   length: Int)
    extends MessageEntity

/** bot_command*/
final case class BotCommandMessageEntity(
                                         /** Offset in UTF-16 code units to the start of the entity*/
                                         offset: Int,
                                         /** Length of the entity in UTF-16 code units*/
                                         length: Int)
    extends MessageEntity

/** email*/
final case class EmailMessageEntity(
                                    /** Offset in UTF-16 code units to the start of the entity*/
                                    offset: Int,
                                    /** Length of the entity in UTF-16 code units*/
                                    length: Int)
    extends MessageEntity

/** bold*/
final case class BoldMessageEntity(
                                   /** Offset in UTF-16 code units to the start of the entity*/
                                   offset: Int,
                                   /** Length of the entity in UTF-16 code units*/
                                   length: Int)
    extends MessageEntity

/** pre*/
final case class PreMessageEntity(
                                  /** Offset in UTF-16 code units to the start of the entity*/
                                  offset: Int,
                                  /** Length of the entity in UTF-16 code units*/
                                  length: Int,
                                  /** the programming language of the entity text*/
                                  language: String)
    extends MessageEntity

/** italic*/
final case class ItalicMessageEntity(
                                     /** Offset in UTF-16 code units to the start of the entity*/
                                     offset: Int,
                                     /** Length of the entity in UTF-16 code units*/
                                     length: Int)
    extends MessageEntity

/** strikethrough*/
final case class StrikethroughMessageEntity(
                                            /** Offset in UTF-16 code units to the start of the entity*/
                                            offset: Int,
                                            /** Length of the entity in UTF-16 code units*/
                                            length: Int)
    extends MessageEntity

/** underline*/
final case class UnderlineMessageEntity(
                                        /** Offset in UTF-16 code units to the start of the entity*/
                                        offset: Int,
                                        /** Length of the entity in UTF-16 code units*/
                                        length: Int)
    extends MessageEntity

/** hashtag*/
final case class HashtagMessageEntity(
                                      /** Offset in UTF-16 code units to the start of the entity*/
                                      offset: Int,
                                      /** Length of the entity in UTF-16 code units*/
                                      length: Int)
    extends MessageEntity

/** text_mention*/
final case class TextMentionMessageEntity(
                                          /** Offset in UTF-16 code units to the start of the entity*/
                                          offset: Int,
                                          /** Length of the entity in UTF-16 code units*/
                                          length: Int,
                                          /** the mentioned user*/
                                          user: User)
    extends MessageEntity

/** text_link*/
final case class TextLinkMessageEntity(
                                       /** Offset in UTF-16 code units to the start of the entity*/
                                       offset: Int,
                                       /** Length of the entity in UTF-16 code units*/
                                       length: Int,
                                       /** url that will be opened after user taps on the text*/
                                       url: String)
    extends MessageEntity

/** url*/
final case class UrlMessageEntity(
                                  /** Offset in UTF-16 code units to the start of the entity*/
                                  offset: Int,
                                  /** Length of the entity in UTF-16 code units*/
                                  length: Int)
    extends MessageEntity

/** phone_number*/
final case class PhoneNumberMessageEntity(
                                          /** Offset in UTF-16 code units to the start of the entity*/
                                          offset: Int,
                                          /** Length of the entity in UTF-16 code units*/
                                          length: Int)
    extends MessageEntity
