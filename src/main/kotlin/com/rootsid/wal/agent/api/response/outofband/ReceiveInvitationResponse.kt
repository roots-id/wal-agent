package com.rootsid.wal.agent.api.response.outofband

import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema
data class ReceiveInvitationResponse (
    @Schema(description = "Connection identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    val connectionId: String,

    @Schema(description = "Pack encrypted message")
    val packedMessage: String,

    @Schema(description = "Connection protocol used", example = "didcomm/2.0")
    val connectionProtocol: DidCommDataTypes.ConnectionProtocol = DidCommDataTypes.ConnectionProtocol.DIDCOMM_2_0,

    @Schema(description = "Optional alias to apply to connection for later use", example = "Bob, providing quotes")
    val alias: String? = null,

    @Schema(description = "Time of record creation", example = "2021-12-31T23:59:59Z",
        pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$")
    val createdAt: LocalDateTime? = null,

    @Schema(description = "Time of last record update", example = "2021-12-31T23:59:59Z",
        pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$")
    val updatedAt: LocalDateTime? = null
)
