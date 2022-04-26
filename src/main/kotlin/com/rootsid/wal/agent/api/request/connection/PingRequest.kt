package com.rootsid.wal.agent.api.request.connection

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class PingRequest (
    @Schema(description = "Comment for the ping message", nullable = true)
    val comment: String? = null
) {

}

