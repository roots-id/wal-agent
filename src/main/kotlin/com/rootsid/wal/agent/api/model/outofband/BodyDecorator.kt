package com.rootsid.wal.agent.api.model.outofband

import com.fasterxml.jackson.annotation.JsonProperty
import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class BodyDecorator(
    @Schema(
        description = "A self-attested code the receiver may want to display to the user or use in automatically " +
                "deciding what to do with the out-of-band message",
        example = "issue-vc"
    )
    @JsonProperty("goal_code")
    val goalCode: String?,

    @Schema(
        description = "A self-attested string that the receiver may want to display to the user about the " +
                "context-specific goal of the out-of-band message.",
        example = "To issue a Faber College Graduate credential"
    )
    val goal: String?,

    @Schema(
        description = "Message identifier [id used for context as pthid]",
        example = """["didcomm/v2"]"""
    )
    @ArraySchema(arraySchema = Schema(description = "An array of media types in the order of preference for sending a " +
            "message to the endpoint", example = """["didcomm/v2"]"""))
    val accept: List<String> = listOf(DidCommDataTypes.ConnectionProtocol.DIDCOMM_2_0.value)
)

