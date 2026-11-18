package fix

import scala.meta._

import scalafix.lint.LintSeverity
import scalafix.v1._

/** Migrates code written against telegramium 10.x to 11.1004.0. Run it while the code still compiles against 10.x, then
  * bump the version:
  *
  *   - the 14 send methods take `chatId` as an `Option[ChatId]`, after their required parameters: `chatId` becomes
  *     `chatId = Some(...)`, and every argument passed by position is passed by name;
  *   - `case OpenEnum.Unknown(tag)` becomes `case OpenEnum.Unknown(tag, _)`;
  *   - `StoryAreaPosition` becomes `AreaPosition`, its new name in the Bot API;
  *   - an `OpenEnum.Unknown` built by hand is only reported: it now takes the JSON it stands for.
  */
class Telegramium11_1004 extends SemanticRule("Telegramium11_1004") {

  // The parameters each method had in 10.x before its optional ones, the only ones a call is likely to pass by
  // position. A call passing more by position is renamed from the method's signature, if the classpath has it.
  private val leadingParams: Map[String, List[String]] = Map(
    "sendMessage"     -> List("chatId", "text"),
    "sendRichMessage" -> List("chatId", "richMessage"),
    "sendAnimation"   -> List("chatId", "animation"),
    "sendAudio"       -> List("chatId", "audio"),
    "sendContact"     -> List("chatId", "phoneNumber", "firstName"),
    "sendDocument"    -> List("chatId", "document"),
    "sendLivePhoto"   -> List("chatId", "livePhoto", "photo"),
    "sendLocation"    -> List("chatId", "latitude", "longitude"),
    "sendPhoto"       -> List("chatId", "photo"),
    "sendSticker"     -> List("chatId", "sticker"),
    "sendVenue"       -> List("chatId", "latitude", "longitude", "title", "address"),
    "sendVideo"       -> List("chatId", "video"),
    "sendVideoNote"   -> List("chatId", "videoNote"),
    "sendVoice"       -> List("chatId", "voice")
  )

  private val methodsOwner = "telegramium/bots/client/Methods#"
  private val unknown      = "iozhik/OpenEnum.Unknown"

  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case t: Term.Apply if sendMethod(t.fun).isDefined => migrateSend(t, sendMethod(t.fun).get)
      case p: Pat.Extract if isUnknown(p.fun.symbol) && p.argClause.values.size == 1 =>
        Patch.addRight(p.argClause.values.head, ", _")
      case t: Term.Apply
          if isUnknown(t.fun.symbol) || t.fun
            .collect { case a: Term.ApplyType => a }
            .exists(a => isUnknown(a.fun.symbol)) =>
        Patch.lint(HandBuiltUnknown(t))
      // by hand, not with Patch.replaceSymbols, which adds an import that `telegramium.bots._` already covers
      case n: Term.Name if n.value == oldAreaPosition && isOldAreaPosition(n.symbol) =>
        Patch.replaceTree(n, "AreaPosition")
      case n: Type.Name if n.value == oldAreaPosition && isOldAreaPosition(n.symbol) =>
        Patch.replaceTree(n, "AreaPosition")
      case i: Importer if i.ref.syntax == "telegramium.bots" =>
        i.importees.collect {
          case Importee.Name(n) if n.value == oldAreaPosition      => Patch.replaceTree(n, "AreaPosition")
          case Importee.Rename(n, _) if n.value == oldAreaPosition => Patch.replaceTree(n, "AreaPosition")
        }.asPatch
    }.asPatch

  private def isUnknown(s: Symbol): Boolean = s.value.startsWith(unknown)

  private val oldAreaPosition = "StoryAreaPosition"

  private def isOldAreaPosition(s: Symbol): Boolean = s.value.startsWith("telegramium/bots/StoryAreaPosition")

  private def sendMethod(fun: Term)(implicit doc: SemanticDocument): Option[String] = {
    val s    = fun.symbol
    val name = s.displayName
    if (leadingParams.contains(name) && s.owner.value == methodsOwner) Some(name) else None
  }

  private def migrateSend(t: Term.Apply, method: String)(implicit doc: SemanticDocument): Patch = {
    val args = t.argClause.values
    // a positional argument may follow named ones only in its own position, so its index is its parameter's
    val positional = args.zipWithIndex.filterNot(_._1.isInstanceOf[Term.Assign])
    val names      = paramNames(t.fun.symbol).getOrElse(leadingParams(method))
    if (positional.exists(_._2 >= names.size)) Patch.lint(TooManyPositional(t, method))
    else {
      val named = positional.map { case (arg, i) =>
        if (names(i) == "chatId") Patch.addLeft(arg, "chatId = Some(") + Patch.addRight(arg, ")")
        else Patch.addLeft(arg, s"${names(i)} = ")
      }
      val chatIdByName = args.collect { case Term.Assign(Term.Name("chatId"), rhs) =>
        Patch.addLeft(rhs, "Some(") + Patch.addRight(rhs, ")")
      }
      (named ++ chatIdByName).asPatch
    }
  }

  // From the 10.x signature on the classpath, when the build gives scalafix one; None otherwise
  private def paramNames(s: Symbol)(implicit doc: SemanticDocument): Option[List[String]] =
    s.info.map(_.signature).collect {
      case m: MethodSignature if m.parameterLists.nonEmpty =>
        m.parameterLists.head.map(_.displayName)
    }

  private case class HandBuiltUnknown(t: Tree) extends Diagnostic {
    override def position: Position = t.pos
    // a warning, so the run still succeeds and keeps the rewrites it made
    override def severity: LintSeverity = LintSeverity.Warning
    override def message: String        =
      "telegramium 11.1004.0: OpenEnum.Unknown takes the JSON it stands for, OpenEnum.Unknown(tag, json), and has no " +
        "type parameter. Write the JSON by hand, with its tag in it, e.g. {\"type\": \"future_entity\"}."
  }

  private case class TooManyPositional(t: Tree, method: String) extends Diagnostic {
    override def position: Position = t.pos
    // a warning, so the run still succeeds and keeps the rewrites it made
    override def severity: LintSeverity = LintSeverity.Warning
    override def message: String        =
      s"telegramium 11.1004.0: $method's parameters moved, and this call passes more of them by position than the " +
        "rule can name. Pass them by name, and chatId as Some(...)."
  }
}
