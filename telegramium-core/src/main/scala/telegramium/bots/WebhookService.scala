package telegramium.bots

trait WebhookService[F[_]] {
  def handleUpdate(x: HandleUpdateReq): F[HandleUpdateRes]
}
