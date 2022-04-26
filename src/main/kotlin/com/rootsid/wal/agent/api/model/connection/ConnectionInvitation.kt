package com.rootsid.wal.agent.api.model.connection

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema
data class ConnectionInvitation (
    @Schema(description = "Message identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    @JsonProperty("@id")
    var id: UUID,

    @Schema(description = "Message type", example = "https://didcomm.org/my-family/1.0/my-message-type",
    readOnly = true)
    @JsonProperty("@type")
    var type: String?,

    @Schema(description = "DID for connection invitation", example = "WgWxqztrNooG92RXvxSTWv",
    pattern = "^(did:sov:)?[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{21,22}\$")
    var didString: String,

    @Schema(description = "Optional image URL for connection invitation", example = "http://192.168.56.101/img/logo.jpg")
    var imageUrl: String,

    @Schema(description = "Optional label for connection invitation", example = "Bob")
    var label: String,

    @ArraySchema(arraySchema = Schema(description = "List of recipient keys"),
        schema = Schema(description = "Recipient public key",
        example = "H3C2AVvLMv6gmMNam3uVAjZpfkcJCwDwnZn6z3wXmqPV",
        pattern = "^[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{43,44}\$"))
    var recipientKeys: List<String>,

    @ArraySchema(arraySchema = Schema(description = "List of routing keys"),
        schema = Schema(description = "Routing key",
            example = "H3C2AVvLMv6gmMNam3uVAjZpfkcJCwDwnZn6z3wXmqPV",
            pattern = "^[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{43,44}\$"))
    var routingKeys: List<String>,

    @Schema(description = "Service endpoint at which to reach this agent", example = "http://192.168.56.101:8020")
    var serviceEndpoint: String
)
