package com.rootsid.wal.agent.api.model.outofband

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class AttachDecorator (
        @Schema(description = "Attachment identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        val `data`: AttachDecoratorData,

        @Schema(description = "Attachment identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        @JsonProperty("@id")
        var id: String,

        @Schema(description = "Byte count of data included by reference", example = "1234")
        val byteCount: Int? = null,

        @Schema(description = "Human-readable description of content",
                example = "view from doorway, facing east, with lights off")
        val description: String? = null,

        @Schema(description = "File name", example = "IMG1092348.png")
        val filename: String? = null,

        @Schema(description = "Hint regarding last modification datetime, in ISO-8601 format", example = "2021-12-31T23:59:59Z",
                pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$")
        val lastmodTime: String? = null,

        @Schema(description = "MIME type", example = "image/png")
        val mimeType: String? = null
)
