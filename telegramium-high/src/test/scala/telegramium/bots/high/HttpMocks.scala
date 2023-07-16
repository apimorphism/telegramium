package telegramium.bots.high

import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody

object HttpMocks {

  val sendMessageResponse: HttpResponse =
    response()
      .withBody(
        new JsonBody(
          """
              {
                "ok": true,
                "result": {
                  "message_id": 0,
                  "date": 1593365356,
                  "chat": {
                    "id": 0,
                    "type": "private"
                  },
                  "text": "Sample text"
                }
              }
            """
        )
      )

  def sendMessageRequest(text: String): HttpRequest =
    request()
      .withPath("/sendMessage")
      .withMethod("POST")
      .withBody(
        new JsonBody(
          s"""
            {
              "chat_id": 0,
              "text": "$text",
              "method": "sendMessage"
            }
          """
        )
      )

}
