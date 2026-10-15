package telegramium.bots

sealed trait IFile {}

final case class InputPartFile(file: java.io.File) extends IFile

final case class InputLinkFile(file: String) extends IFile

final case class InputStreamFile(file: telegramium.bots.NamedInputStream) extends IFile
