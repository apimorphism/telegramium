package io.github.fperiodic.apimorphism.telegramium.bots

trait WebhookService[F[_]] {
  def handleUpdate(x: HandleUpdateReq): F[HandleUpdateRes]
}
