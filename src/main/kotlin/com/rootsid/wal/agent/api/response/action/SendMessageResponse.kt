package com.rootsid.wal.agent.api.response.action

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class SendMessageResponse (
    @Schema(description = "Message status")
    val status: String? = "sent",

    @Schema(description = "Message content", example = "Hello")
    val content: String? = null
)

