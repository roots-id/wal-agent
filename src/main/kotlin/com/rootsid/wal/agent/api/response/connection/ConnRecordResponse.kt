package com.rootsid.wal.agent.api.response.connection

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema
data class ConnRecordResponse (
    @Schema(description = "Connection acceptance: manual or auto", example = "auto")
    val accept: Accept? = null,

    @Schema(description = "Optional alias to apply to connection for later use", example = "Bob, providing quotes")
    val alias: String? = null,

    @Schema(description = "Connection identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    val connectionId: UUID? = null,

    @Schema(description = "Connection protocol used", example = "connections/1.0")
    val connectionProtocol: ConnectionProtocol? = null,

    @Schema(description = "Time of record creation", example = "2021-12-31T23:59:59Z",
        pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$")
    val createdAt: String? = null,

    @Schema(description = "Error message", example = "No DIDDoc provided; cannot connect to public DID")
    val errorMsg: String? = null,

    @Schema(description = "Inbound routing connection id to use", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    val inboundConnectionId: String? = null,

    @Schema(description = "Public key for connection", example = "H3C2AVvLMv6gmMNam3uVAjZpfkcJCwDwnZn6z3wXmqPV",
        pattern = "^[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{43,44}\$")
    val invitationKey: String? = null,

    @Schema(description = "Invitation mode", example = "once")
    val invitationMode: InvitationMode? = null,

    @Schema(description = "ID of out-of-band invitation message", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6\n")
    val invitationMsgId: String? = null,

    @Schema(description = "Our DID for connection", example = "WgWxqztrNooG92RXvxSTWv",
        pattern = "^(did:sov:)?[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{21,22}\$")
    val myDid: String? = null,

    @Schema(description = "Connection request identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    val requestId: String? = null,

    @Schema(description = "State per RFC 23", example = "invitation-sent", readOnly = true)
    val rfc23State: String? = null,

    @Schema(description = "Routing state of connection", example = "active")
    val routingState: RoutingState? = null,

    @Schema(description = "Current record state", example = "active")
    val state: String? = null,

    @Schema(description = "Their DID for connection", example = "WgWxqztrNooG92RXvxSTWv",
        pattern = "^(did:sov:)?[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{21,22}\$")
    val theirDid: String? = null,

    /* Their label for connection */
    @Schema(description = "Their label for connection", example = "Bob")
    val theirLabel: String? = null,

    @Schema(description = "Other agent's public DID for connection", example = "2cpBmR3FqGKWi5EyUbpRY8")
    val theirPublicDid: String? = null,

    @Schema(description = "Their role in the connection protocol", example = "requester")
    val theirRole: TheirRole? = null,

    @Schema(description = "Time of last record update", example = "2021-12-31T23:59:59Z",
        pattern = "^\\d{4}-\\d\\d-\\d\\d[T ]\\d\\d:\\d\\d(?:\\:(?:\\d\\d(?:\\.\\d{1,6})?))?(?:[+-]\\d\\d:?\\d\\d|Z|)\$")
    val updatedAt: String? = null
) {

    /**
    * Connection acceptance: manual or auto
    * Values: manual,auto
    */
    enum class Accept(val value: String){
        manual("manual"),
        auto("auto");
    }

    /**
    * Connection protocol used
    * Values: connectionsSlash1Period0,didexchangeSlash1Period0
    */
    enum class ConnectionProtocol(val value: String){
        connectionsSlash1Period0("connections/1.0"),
        didexchangeSlash1Period0("didexchange/1.0");
    }

    /**
    * Invitation mode
    * Values: once,multi,static
    */
    enum class InvitationMode(val value: String){
        once("once"),
        multi("multi"),
        static("static");
    }

    /**
    * Routing state of connection
    * Values: none,request,active,error
    */
    enum class RoutingState(val value: String){
        none("none"),
        request("request"),
        active("active"),
        error("error");
    }

    /**
    * Their role in the connection protocol
    * Values: invitee,requester,inviter,responder
    */
    enum class TheirRole(val value: String){
        invitee("invitee"),
        requester("requester"),
        inviter("inviter"),
        responder("responder");
    }

}
