package com.rootsid.wal.agent.dto

import com.rootsid.wal.library.didcomm.model.DidCommConnection
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import kotlin.streams.toList

@Schema
data class ConnectionDto(
    @Schema(description = "Connection identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    val id: String,

    @Schema(description = "Optional alias to apply to connection for later use", example = "Bob, providing quotes")
    val alias: String? = null,

    /* Their label for connection */
    @Schema(description = "Their alias for connection", example = "Bob")
    val theirAlias: String? = null,

    @Schema(
        description = "Their DID for connection",
        example = "did:peer:2.Ez6LSi2DUKWessWhBACa8UBKjLSuPjyni3fjMgfrpCVDcErcf.Vz6MkgUkVUpzJ8KEqJTfyoKR8Xfe...",
        pattern = "^did:peer:([\\d+]).*"
    )
    val theirDid: String? = null,

    @Schema(
        description = "Time of record creation", example = "2021-12-31T23:59:59Z",
        pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$"
    )
    val createdAt: LocalDateTime? = null,

    @Schema(
        description = "Time of last record update", example = "2021-12-31T23:59:59Z",
        pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$"
    )
    val updatedAt: LocalDateTime? = null
)

fun DidCommConnection.convert() = ConnectionDto(
    id = _id,
    alias = alias,
    theirAlias = "",
    theirDid = invitationKey,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun List<DidCommConnection>.convert(): List<ConnectionDto> = this.stream().map { it.convert() }.toList()
