package com.rootsid.wal.agent.api.request.outofband

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema
data class InvitationCreateRequest (
        @Schema(description = "Alias for connection", example = "Barry")
        var alias: String,

        @Schema(description = "Optional invitation attachments", example = "Barry")
        var attachments: List<String>?,

        @Schema(description = "Handshake protocol to specify in invitation",
                example = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/didexchange/1.0")
        var handshakeProtocols: List<String>?,

        @Schema(description = "Connection endpoint", example = "http://192.168.56.102:8020")
        var serviceEndpoint: String,

        @Schema(description = "Identifier for active mediation record to be used",
                pattern = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
                example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        var mediationId: UUID?,

        @Schema(description = "Optional metadata to attach to the connection created with the invitation")
        var metadata: String?,

        @Schema(description = "Label for connection invitation", example = "Invitation to Barry")
        var myLabel: String?,

        @Schema(description = "Whether to use public DID in invitation", example = "false")
        var usePublicDid: Boolean,

        @Schema(description = "Auto-accept connection", defaultValue = "false", example = "false")
        var autoAccept: Boolean = false,

        @Schema(description = "Create invitation for multiple use", defaultValue = "false", example = "false")
        var multiUse: Boolean = false
)
