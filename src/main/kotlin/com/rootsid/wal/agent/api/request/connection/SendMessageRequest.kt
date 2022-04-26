package com.rootsid.wal.agent.api.request.connection

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class SendMessageRequest (
    @Schema(description = "Message content", example = "Hello")
    val content: String? = null
) {

}

