package telegramium.bots

/** Represents a location to which a chat is connected.*/
final case class ChatLocation(
                              /** The location to which the supergroup is connected. Can't be
                                * a live location.*/
                              location: Location,
                              /** Location address; 1-64 characters, as defined by the chat
                                * owner*/
                              address: String)
