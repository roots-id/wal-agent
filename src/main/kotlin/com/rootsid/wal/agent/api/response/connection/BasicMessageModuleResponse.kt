package com.rootsid.wal.agent.api.response.connection

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema
data class BasicMessageModuleResponse (
    @Schema(description = "Unique identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    var uniqueIdentifier: UUID? = null
) {}

