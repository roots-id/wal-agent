package com.rootsid.wal.agent.api.request.action

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class ReceiveMessageRequest(
    @Schema(description = "Packed message content")
    val content: String
)

