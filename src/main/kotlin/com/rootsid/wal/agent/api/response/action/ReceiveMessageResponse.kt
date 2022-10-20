package com.rootsid.wal.agent.api.response.action

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class ReceiveMessageResponse(
    @Schema(description = "Message status")
    val status: String? = "received",

    @Schema(description = "Packed message content", example = "Hello")
    val content: String
)

