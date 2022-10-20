package com.rootsid.wal.agent.service

import com.fasterxml.jackson.databind.ObjectMapper
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
import com.rootsid.wal.agent.persistence.DidCommMessageDataProvider
import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import com.rootsid.wal.agent.restclient.DidCommClientService
import com.rootsid.wal.library.didcomm.DIDPeer
import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import org.apache.commons.lang3.RandomStringUtils
import org.didcommx.didcomm.model.PackEncryptedResult
import org.didcommx.peerdid.PeerDID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class OutOfBandService(
    private val mapper: ObjectMapper,
    private val outOfBandProperties: OutOfBandProperties,
    private val didPeer: DIDPeer,
    private val didCommConnectionDataProvider: DidCommConnectionDataProvider,
    private val didCommClientService: DidCommClientService,
    private val didCommMessageDataProvider: DidCommMessageDataProvider,
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
        val plainTextMsgAck = PlainTextMessage(
            type = Piuris.INVITATION_RESPONSE.value,
            from = inviteeDidPeer,
            to = mutableListOf(invitationMessageRequest.invitationMessage.from),
            pthid = invitationMessageRequest.invitationMessage.id,
            body = """{"msg": "Hi inviter, I'm the invitee"}""",
            createdTime = Instant.now().toEpochMilli(),
            expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
        )

        if (invitationMessageRequest.autoAccept) {
            val theirDid = invitationMessageRequest.invitationMessage.from
            didCommClientService.sendMessage(connection._id, theirDid, plainTextMsgAck)
                .takeIf { it }
                ?.apply {
                    return ReceiveInvitationResponse(
                        connectionId = connection._id,
                        packedMessage = "Auto accepted executed",
                        alias = invitationMessageRequest.alias
                    )
                }

            log.error("Unable to execute the auto-accept process.   Execute manual process.")
        }

        return ReceiveInvitationResponse(
            connectionId = connection._id,
            packedMessage = packPlainTextMessage(
                invitationMessageRequest.invitationMessage.from,
                plainTextMsgAck
            ).packedMessage,
            alias = invitationMessageRequest.alias
        )
    }

    private fun packPlainTextMessage(to: PeerDID, message: PlainTextMessage): PackEncryptedResult {
        val packedMsg = didPeer.pack(data = message.toJson(mapper), from = message.from, to = to)
        log.info("Packed message = [{}]", packedMsg)

        return packedMsg
    }
}
