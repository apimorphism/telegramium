package telegramium.bots

import io.circe.Json
import io.circe.syntax.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.CirceImplicits.*

class EphemeralMessageParametersSpec extends AnyFlatSpec with Matchers {

  // User identifiers may exceed 32 bits (User.id is Long), so receiver_user_id must accept them.
  "EphemeralMessageParameters" should "encode a receiver_user_id that doesn't fit into Int" in {
    val userId = 5000000000L
    EphemeralMessageParameters(receiverUserId = userId).asJson shouldBe Json.obj(
      "receiver_user_id" -> Json.fromLong(userId)
    )
  }

}
