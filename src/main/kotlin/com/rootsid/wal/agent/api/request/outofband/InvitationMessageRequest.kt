package com.rootsid.wal.agent.api.request.outofband

import com.fasterxml.jackson.annotation.JsonProperty
import com.rootsid.wal.agent.api.model.outofband.AttachDecorator
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class InvitationMessageRequest (
        @Schema(description = "Message identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        @JsonProperty("@id")
        val id: String? = null,

        @Schema(description = "Message type", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        @JsonProperty("@type")
        val type: String? = null,

        @Schema(description = "Handshake protocol", example = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/didexchange/1.0")
        var handshakeProtocols: List<String>?,

        @Schema(description = "Optional label", example = "Bod")
        val label: String? = null,

        @ArraySchema(arraySchema = Schema(description = "Optional request attachment"))
        val requestsAttach: List<AttachDecorator>? = null,

        @ArraySchema(arraySchema = Schema(example = "List [ OrderedMap { \"did\": \"WgWxqztrNooG92RXvxSTWv\", \"id\": \"string\", " +
                "\"recipientKeys\": List [ \"did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH\" ], " +
                "\"routingKeys\": List [ \"did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH\" ], " +
                "\"serviceEndpoint\": \"http://192.168.56.101:8020\", \"type\": \"string\" }, " +
                "\"did:sov:WgWxqztrNooG92RXvxSTWv\" ]"),
                schema = Schema(description = "Either a DIDComm service object (as per RFC0067) or a DID string.",
                ))
        val services: List<Any>? = null
)
