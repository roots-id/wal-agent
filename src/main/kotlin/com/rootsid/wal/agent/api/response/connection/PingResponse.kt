package com.rootsid.wal.agent.api.response.connection

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class PingResponse (
    @Schema(description = "Thread ID of the ping message")
    val threadId: String? = null
) {

}

