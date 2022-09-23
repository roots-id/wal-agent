package com.rootsid.wal.agent.api.model.outofband

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class AttachDecorator(
    @Schema(description = "Attachment identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    var id: String,

    @Schema(description = "MIME type", example = "application/json")
    @JsonProperty("mime_type")
    val mimeType: String = "application/json",

    @Schema(description = "Attachment data", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    val `data`: AttachDecoratorData
)
