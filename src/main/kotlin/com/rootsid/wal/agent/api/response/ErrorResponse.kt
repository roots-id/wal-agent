package com.rootsid.wal.agent.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema
data class ErrorResponse (
    @Schema(description = "Http status error code", example = "404")
    val statusCode: Int,

    @Schema(description = "Error Message", example = "No handler found for POST /create-invitation1")
    val message: String?,

    @Schema(description = "Error description")
    val description: String? = null,

    @Schema(description = "Error stacktrace")
    var stackTrace: String? = null,

    @Schema(description = "TBD", example = "TBD")
    var errors: MutableList<ValidationError>? = null,

    @Schema(description = "Timestamp", example = "2022-04-25T03:39:45.868274Z")
    val timestamp: Instant = Instant.now(),

    @Schema(description = "Timestamp millis", example = "1650857985868")
    val timestampMillis: Long = System.currentTimeMillis()
) {
    fun addValidationError(field: String?, message: String?) {
        if (Objects.isNull(errors)) {
            errors = mutableListOf<ValidationError>()
        }

        errors?.add(ValidationError(field, message))
    }

    class ValidationError (val field: String? = null, val message: String? = null)
}
