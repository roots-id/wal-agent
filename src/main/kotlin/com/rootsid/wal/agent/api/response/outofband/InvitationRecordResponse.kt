package com.rootsid.wal.agent.api.response.outofband

import com.rootsid.wal.agent.api.model.outofband.InvitationMessage
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema
data class InvitationRecordResponse(
    @Schema(description = "Connection identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    val connectionId: String,

    @Schema(description = "Out of band invitation message")
    val invitationMessage: InvitationMessage,

    @Schema(description = "Invitation message URL",
        example = "https://example.com/endpoint?c_i=eyJAdHlwZSI6ICIuLi4iLCAiLi4uIjogIi4uLiJ9XX0=")
    val invitationUrl: String? = null,

    @Schema(description = "Out of band message exchange state", example = "await_response")
    val state: String? = null,

    @Schema(description = "Record trace information, based on agent configuration")
    val trace: Boolean? = null,

    @Schema(description = "Time of record creation", example = "2021-12-31T23:59:59Z",
        pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$")
    val createdAt: LocalDateTime? = null,

    @Schema(description = "Time of last record update", example = "2021-12-31T23:59:59Z",
        pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$")
    val updatedAt: LocalDateTime? = null
)
