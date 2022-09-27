package com.rootsid.wal.agent.service

import com.rootsid.wal.agent.api.model.outofband.BodyDecorator
import com.rootsid.wal.agent.api.model.outofband.InvitationMessage
import com.rootsid.wal.agent.api.model.outofband.toBase64
import com.rootsid.wal.agent.api.request.outofband.InvitationCreateRequest
import com.rootsid.wal.agent.api.request.outofband.InvitationMessageRequest
import com.rootsid.wal.agent.api.response.outofband.InvitationRecordResponse
import com.rootsid.wal.agent.api.response.outofband.ReceiveInvitationResponse
import com.rootsid.wal.agent.persistence.DidCommConnectionDataProvider
import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import com.rootsid.wal.library.didcomm.DIDPeer
import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class OutOfBandService(
    private val didPeer: DIDPeer,
    private val didCommConnectionDataProvider: DidCommConnectionDataProvider
) {
    private val log = LoggerFactory.getLogger(OutOfBandService::class.java)

    fun createInvitation(invitationCreateRequest: InvitationCreateRequest): InvitationRecordResponse {
        val inviterDidPeer = didPeer.create(invitationCreateRequest.serviceEndpoint)

        val oobMsg = InvitationMessage(
            id = UUID.randomUUID().toString(), from = inviterDidPeer,
            body = BodyDecorator("issue-vc", "To issue a Faber College Graduate credential"),
            label = RandomStringUtils.randomAlphanumeric(5)
        )

        // TODO: Add invitation url
        val invitationUrl = "https://example.com/path?oob=" + oobMsg.toBase64()
        log.info("Invitation Url = [{}]", invitationUrl)

        val connection =
            didCommConnectionDataProvider.insert(
                DidCommConnectionEntity(
                    alias = invitationCreateRequest.alias, state = DidCommDataTypes.ConnectionState.INVITATION_SENT,
                    theirDid = inviterDidPeer, theirRole = DidCommDataTypes.TheirRole.INVITEE, invitationUrl = invitationUrl
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
                invitationMsgId = invitationMessageRequest.invitationMessage.id,
                theirDid = invitationMessageRequest.invitationMessage.from,
                theirRole = DidCommDataTypes.TheirRole.INVITER,
                myDid = inviteeDidPeer
            )
        )

        val message = didPeer.pack(
            data = """{"msg": "Hi Alice"}""", from = inviteeDidPeer,
            to = invitationMessageRequest.invitationMessage.from, protectSender = false
        )
        log.info("Packed message = [{}]", message)

        log.info("Unpacked message = [{}]", didPeer.unpack(message.packedMessage))

        return ReceiveInvitationResponse(
            connectionId = connection._id,
            packedMessage = message.packedMessage,
            alias = invitationMessageRequest.alias
        )
    }
}
