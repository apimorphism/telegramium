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
What Telegram actually receives for a Method: a JSON body or a multipart form, sent as an HTTP request or as a webhook reply.
_Avoid_: HTTP payload, raw request

**Webhook reply**:
A Method a webhook bot returns in its response to an update, which Telegram executes instead of the bot calling the API.
_Avoid_: reply (alone), webhook answer, webhook response

**Omitted vs empty**:
An omitted field is absent from the wire request; an empty one is sent as `[]`. Telegram can give them different meanings.
_Avoid_: missing, blank, null (for either)

### Updates

**Unknown update**:
An update carrying fields or kinds this version of the library does not know.
_Avoid_: unsupported update, invalid update

**Open enum**:
A set of strings Telegram may add to, such as the type of a chat.
_Avoid_: string enum, extensible enum

**Open union**:
A family of objects told apart by a type tag, which Telegram may add to, such as message entities.
_Avoid_: tagged enum, open enum (for objects), polymorphic type

**Unknown value**:
A value of an open enum or an open union that this version of the library does not know.
_Avoid_: unsupported value, raw value

**Forward compatibility**:
A bot built on an older version of the library keeps working against a newer Bot API: values it doesn't know are accepted as Unknown values and Unknown updates instead of failing.
_Avoid_: future-proofing, backward compatibility (for this direction)
