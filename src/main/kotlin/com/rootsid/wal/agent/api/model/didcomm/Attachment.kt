package com.rootsid.wal.agent.api.model.didcomm

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

/**
 * @see <a href="https://identity.foundation/didcomm-messaging/spec/#attachments">Didcomm messaging attachments</a>
 */
@Schema
data class Attachment(
    @Schema(description = "Attachment identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    var id: String = UUID.randomUUID().toString(),

    @Schema(description = "A JSON object that gives access to the actual content of the attachment.")
    val `data`: AttachmentData,

    @Schema(description = "Attachment description", example = "Bod image")
    var description: String? = null,

    @Schema(description = "Describes the media type of the attached content.", example = "application/json")
    @JsonProperty("media_type")
    val mediaType: String? = null,

    @Schema(description = "Further describes the format of the attachment if the media_type is not sufficient.")
    val format: String? = null,

    @Schema(description = "Time of record last update", example = "1664410889")
    @JsonProperty("lastmod_time")
    val lastmodTime: Long? = null,

    @Schema(description = "Mostly relevant when content is included by reference instead of by value")
    @JsonProperty("byte_count")
    val byteCount: Long? = null
)
