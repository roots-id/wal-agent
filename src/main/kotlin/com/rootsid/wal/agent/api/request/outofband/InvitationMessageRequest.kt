package com.rootsid.wal.agent.api.request.outofband

import com.rootsid.wal.agent.api.model.outofband.InvitationMessage
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema
data class InvitationMessageRequest (
        @Schema(description = "Alias for connection", example = "Bod")
        val alias: String? = null,

        @Schema(description = "Alias for connection", defaultValue = "false", example = "false")
        val autoAccept: Boolean = false,

        @Schema(description = "Identifier for active mediation record to be used", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        val mediationId: UUID? = null,

        @Schema(description = "Use an existing connection, if possible", defaultValue = "false", example = "false")
        val useExistingConnection: Boolean = false,

        @Schema(description = "Invitation message")
        val invitationMessage: InvitationMessage,
)
