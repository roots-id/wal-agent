package com.rootsid.wal.agent.service

import com.rootsid.wal.agent.api.model.didcomm.Piuris
import com.rootsid.wal.agent.api.model.didcomm.PlainTextMessage
import com.rootsid.wal.agent.api.model.didcomm.toJson
import com.rootsid.wal.agent.api.model.outofband.BodyDecorator
import com.rootsid.wal.agent.api.model.outofband.InvitationMessage
import com.rootsid.wal.agent.api.model.outofband.toBase64
import com.rootsid.wal.agent.api.request.outofband.InvitationCreateRequest
import com.rootsid.wal.agent.api.request.outofband.InvitationMessageRequest
import com.rootsid.wal.agent.api.response.outofband.InvitationRecordResponse
import com.rootsid.wal.agent.api.response.outofband.ReceiveInvitationResponse
import com.rootsid.wal.agent.config.properties.OutOfBandProperties
import com.rootsid.wal.agent.persistence.DidCommConnectionDataProvider
import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import com.rootsid.wal.library.didcomm.DIDPeer
import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class OutOfBandService(
    private val outOfBandProperties: OutOfBandProperties,
    private val didPeer: DIDPeer,
    private val didCommConnectionDataProvider: DidCommConnectionDataProvider
) {
    private val log = LoggerFactory.getLogger(OutOfBandService::class.java)

    fun createInvitation(invitationCreateRequest: InvitationCreateRequest): InvitationRecordResponse {
        val inviterDidPeer = didPeer.create(invitationCreateRequest.serviceEndpoint)

        val oobMsg = InvitationMessage(
            id = UUID.randomUUID().toString(),
            from = inviterDidPeer,
            type = Piuris.INVITATION.value.toString(),
            body = BodyDecorator("issue-vc", "To issue a Faber College Graduate credential"),
            label = RandomStringUtils.randomAlphanumeric(5)
        )

        val invitationUrl = outOfBandProperties.invitationUrl + "?oob=${oobMsg.toBase64()}"
        log.info("Invitation Url = [{}]", invitationUrl)

        val connection =
            didCommConnectionDataProvider.insert(
                DidCommConnectionEntity(
                    alias = invitationCreateRequest.alias,
                    state = DidCommDataTypes.ConnectionState.INVITATION_SENT,
                    myDid = inviterDidPeer,
                    theirRole = DidCommDataTypes.TheirRole.INVITEE,
                    invitationMsgId = oobMsg.id,
                    invitationUrl = invitationUrl
                )
            )

        return InvitationRecordResponse(
            connectionId = connection._id,
            invitationMessage = oobMsg,
            invitationUrl = invitationUrl,
            state = connection.state.value,
            createdAt = connection.createdAt,
            updatedAt = connection.updatedAt
        )
    }

    fun receiveInvitation(invitationMessageRequest: InvitationMessageRequest): ReceiveInvitationResponse {
        val inviteeDidPeer = didPeer.create(invitationMessageRequest.serviceEndpoint)

        val connection = didCommConnectionDataProvider.insert(
            DidCommConnectionEntity(
                alias = invitationMessageRequest.alias,
                state = DidCommDataTypes.ConnectionState.INVITATION_RECEIVED,
                invitationMsgId = invitationMessageRequest.invitationMessage.id,
                theirDid = invitationMessageRequest.invitationMessage.from,
                theirRole = DidCommDataTypes.TheirRole.INVITER,
                myDid = inviteeDidPeer
            )
        )

        // Create didcomm plaintext message
        val ptMsg = PlainTextMessage(
            type = Piuris.INVITATION_RESPONSE.value,
            from = inviteeDidPeer,
            to = mutableListOf(invitationMessageRequest.invitationMessage.from),
            pthid = invitationMessageRequest.invitationMessage.id,
            body = """{"msg": "Hi Alice"}""",
            createdTime = Instant.now().toEpochMilli(),
            expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
        ).toJson()
        log.info("Plaintext message = [{}]", ptMsg)

        val packMessage = didPeer.pack(
            data = ptMsg, from = inviteeDidPeer,
            to = invitationMessageRequest.invitationMessage.from
        )
        log.info("Packed message = [{}]", packMessage)

        log.info("Unpacked message = [{}]", didPeer.unpack(packMessage.packedMessage))

        return ReceiveInvitationResponse(
            connectionId = connection._id,
            packedMessage = packMessage.packedMessage,
            alias = invitationMessageRequest.alias
        )
    }
}
