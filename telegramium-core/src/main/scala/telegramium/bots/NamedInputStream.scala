package telegramium.bots

final case class NamedInputStream(filename: String, openStream: () => java.io.InputStream)
