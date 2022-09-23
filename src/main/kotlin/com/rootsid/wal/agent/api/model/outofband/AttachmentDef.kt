package com.rootsid.wal.agent.api.model.outofband

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class AttachmentDef(
    @Schema(description = "Attachment identifier", example = "attachment-0")
    var id: String,

    @Schema(description = "Attachment type", example = "present-proof")
    var type: AttachmentType,
)

enum class AttachmentType(val type: String) {
    CREDENTIAL_OFFER("credential-offer"),
    PRESENT_PROOF("present-proof")
}
