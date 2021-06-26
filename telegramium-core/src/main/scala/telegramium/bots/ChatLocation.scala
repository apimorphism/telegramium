package telegramium.bots

/** Represents a location to which a chat is connected.
  *
  * @param location The location to which the supergroup is connected. Can't be
  * a live location.
  * @param address Location address; 1-64 characters, as defined by the chat
  * owner */
final case class ChatLocation(location: Location, address: String)
