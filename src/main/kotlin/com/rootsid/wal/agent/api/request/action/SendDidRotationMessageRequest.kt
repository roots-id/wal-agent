package com.rootsid.wal.agent.api.request.action

import io.swagger.v3.oas.annotations.media.Schema
import org.didcommx.peerdid.PeerDID

@Schema
data class SendDidRotationMessageRequest (
    @Schema(description = "Connection identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    val connectionId: String,

    @Schema(description = "Message content", example = "Hello")
    val content: String = "Hello world!!!",

    @Schema(description = "Specify the new PeerDid to use after the rotation.")
    val newPeerDid: PeerDID?,

    @Schema(description = "Send Did rotation message automatically.")
    val autoSend: Boolean = true,

    @Schema(description = "Tell the receiver to auto-ack message to sender")
    val autoAck: Boolean = false
)

