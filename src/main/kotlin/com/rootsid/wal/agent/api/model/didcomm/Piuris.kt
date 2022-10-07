package com.rootsid.wal.agent.api.model.didcomm

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonValue
import java.net.URI

@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class Piuris(@JsonValue val value: URI) {
    INVITATION(URI.create("https://didcomm.org/out-of-band/2.0/invitation")),
    INVITATION_RESPONSE(URI.create("https://didcomm.org/out-of-band/2.0/invitation-response")),
    MESSAGE(URI.create("https://didcomm.org/message")),
    MESSAGE_ACK(URI.create("https://didcomm.org/message-ack")),
}
