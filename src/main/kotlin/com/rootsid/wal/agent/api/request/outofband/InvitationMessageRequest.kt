package com.rootsid.wal.agent.api.request.outofband

import com.rootsid.wal.agent.api.model.outofband.InvitationMessage
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema
data class InvitationMessageRequest(
    @Schema(description = "Alias for connection", example = "Bod")
    val alias: String,

    @Schema(description = "Invitation message")
    val invitationMessage: InvitationMessage,

    @Schema(description = "Inviter connection endpoint", example = "https://rootsid.com/inviter/service-endpoint")
    val serviceEndpoint: String = "https://rootsid.com/inviter/service-endpoint",

    @Schema(
        description = "[Not Implemented] Automatically accept invites and requests when they come in",
        defaultValue = "false",
        example = "false"
    )
    val autoAccept: Boolean = false,

    @Schema(
        description = "[Not Implemented] Use an existing connection, if possible",
        defaultValue = "false",
        example = "false"
    )
    val useExistingConnection: Boolean = false,

    @Schema(
        description = "[Not Implemented] Identifier for active mediation record to be used",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val mediationId: UUID? = null
)
