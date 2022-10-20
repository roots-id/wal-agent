package com.rootsid.wal.agent.persistence.model

import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import com.rootsid.wal.library.didcomm.model.DidCommConnection
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document("didcomm_connections")
data class DidCommConnectionEntity(
    override val alias: String,
    override val myDid: String,
    override val theirRole: DidCommDataTypes.TheirRole,
    override var theirDid: String? = null,
    override val accept: DidCommDataTypes.Accept = DidCommDataTypes.Accept.MANUAL,
    @Id
    override val _id: String = UUID.randomUUID().toString(),
    override val invitationMsgId: String = UUID.randomUUID().toString(),
    override val invitationUrl: String? = null,
    override val state: DidCommDataTypes.ConnectionState = DidCommDataTypes.ConnectionState.START,
    override val invitationMode: DidCommDataTypes.InvitationMode = DidCommDataTypes.InvitationMode.SIMPLE,
    override val connectionProtocol: DidCommDataTypes.ConnectionProtocol = DidCommDataTypes.ConnectionProtocol.DIDCOMM_2_0,
    override val routingState: DidCommDataTypes.RoutingState = DidCommDataTypes.RoutingState.NONE,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now()
) : DidCommConnection


