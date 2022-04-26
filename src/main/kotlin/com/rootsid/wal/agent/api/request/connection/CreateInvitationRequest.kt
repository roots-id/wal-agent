package com.rootsid.wal.agent.api.request.connection

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema
data class CreateInvitationRequest (
        @Schema(description = "Identifier for active mediation record to be used",
                pattern = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
                example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        var mediationId: UUID?,

        @Schema(description = "Optional metadata to attach to the connection created with the invitation")
        var metadata: String?,

        @Schema(description = "Optional label for connection invitation", example = "Bod")
        var myLabel: String?,

        @Schema(description = "Recipient public key", example = "H3C2AVvLMv6gmMNam3uVAjZpfkcJCwDwnZn6z3wXmqPV",
                pattern = "^[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{43,44}\$")
        var recipientKeys: List<String>?,

        @Schema(description = "Routing key", example = "H3C2AVvLMv6gmMNam3uVAjZpfkcJCwDwnZn6z3wXmqPV",
                pattern = "^[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{43,44}\$")
        var routingKeys: List<String>?,

        @Schema(description = "Connection endpoint", example = "http://192.168.56.102:8020")
        var serviceEndpoint: String
)
