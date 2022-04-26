package com.rootsid.wal.agent.api.response.connection

import com.rootsid.wal.agent.api.model.connection.ConnectionInvitation
import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class InvitationResponse (
    @Schema(description = "Connection identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    var connectionId: String,

    var invitation: ConnectionInvitation?,

    @Schema(description = "Invitation URL", example = "http://192.168.56.101:8020/invite?c_i=eyJAdHlwZSI6Li4ufQ==")
    var invitationUrl: String
)
