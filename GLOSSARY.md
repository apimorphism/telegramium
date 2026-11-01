# Telegramium

A Scala client for the Telegram Bot API: generated request and model types (telegramium-core) plus bots that send them over HTTP (telegramium-high).

## Language

### Requests

**Method**:
A Bot API call such as `sendMediaGroup`, together with its arguments.
_Avoid_: endpoint, request (when the call itself is meant)

**Payload**:
What a Method carries: its JSON fields plus its attachments.
_Avoid_: body, params

**Attachment**:
A file uploaded as its own multipart part and referenced from the JSON as `attach://<name>`.
_Avoid_: upload, part

**Embedded file**:
A file nested inside a compound argument (`InputMedia`, `InputSticker`, ...), as opposed to a top-level file argument.
_Avoid_: nested attachment, inner file

**Wire request**:
The HTTP request actually sent to Telegram for a Method: a JSON body or a multipart form.
_Avoid_: HTTP payload, raw request

**Omitted vs empty**:
An omitted field is absent from the wire request; an empty one is sent as `[]`. Telegram can give them different meanings.
_Avoid_: missing, blank, null (for either)

### Updates

**Unknown update**:
An update carrying fields or kinds this version of the library does not know.
_Avoid_: unsupported update, invalid update

**Unknown value**:
An enum value from Telegram that this version of the library does not know. It is only ever received, never sent back.
_Avoid_: unsupported value, raw value
