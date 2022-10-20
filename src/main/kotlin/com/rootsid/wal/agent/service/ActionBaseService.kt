package com.rootsid.wal.agent.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.rootsid.wal.agent.api.model.didcomm.Piuris
import com.rootsid.wal.agent.api.model.didcomm.PlainTextMessage
import com.rootsid.wal.agent.api.model.didcomm.toJson
import com.rootsid.wal.agent.persistence.DidCommConnectionDataProvider
import com.rootsid.wal.agent.persistence.DidCommMessageDataProvider
import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import com.rootsid.wal.agent.persistence.model.DidCommMessageEntity
import com.rootsid.wal.agent.persistence.model.RouteType
import com.rootsid.wal.library.didcomm.DIDPeer
import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import com.rootsid.wal.library.didcomm.common.getServiceEndpoint
import org.didcommx.didcomm.model.PackEncryptedResult
import org.didcommx.peerdid.PeerDID
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.temporal.ChronoUnit

abstract class ActionBaseService(
    private val mapper: ObjectMapper,
    private val didCommConnectionDataProvider: DidCommConnectionDataProvider,
    private val didPeer: DIDPeer,
    private val didCommMessageDataProvider: DidCommMessageDataProvider
) {
    private val log = LoggerFactory.getLogger(ActionBaseService::class.java)

    fun handleInvitationResponseMessage(receivedPtMsg: PlainTextMessage): PlainTextMessage {
        receivedPtMsg.pthid?.let { pthid ->
            val conn = didCommConnectionDataProvider.findByInvitationMsgId(pthid) as DidCommConnectionEntity
            didCommConnectionDataProvider.insert(
                conn.copy(
                    theirDid = receivedPtMsg.from,
                    state = DidCommDataTypes.ConnectionState.COMPLETED
                )
            )
            log.info("Connection with id = [{}] updated.", conn._id)

            return PlainTextMessage(
                type = Piuris.MESSAGE_ACK.value,
                pthid = pthid,
                thid = receivedPtMsg.id,
                from = conn.myDid,
                body = "ack: $pthid",
                createdTime = Instant.now().toEpochMilli(),
                expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
            )
        }

        throw RuntimeException("Invalid invitation response with id=[${receivedPtMsg.id}].")
    }

    // TODO: Rotate using JWT header
    fun handleDidRotation(receivedPtMsg: PlainTextMessage): PlainTextMessage {
        receivedPtMsg.fromPrior?.let { fromPrior ->
            val conn = didCommConnectionDataProvider.findByTheirDid(fromPrior) as DidCommConnectionEntity

            didCommConnectionDataProvider.insert(conn.copy(theirDid = receivedPtMsg.from))
            log.info("Connection with id = [{}] updated.", conn._id)

            return PlainTextMessage(
                type = Piuris.MESSAGE_ACK.value,
                thid = receivedPtMsg.id,
                from = conn.myDid,
                to = mutableListOf(receivedPtMsg.from),
                body = "ack: ${receivedPtMsg.id}",
                createdTime = Instant.now().toEpochMilli(),
                expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
            )
        }

        throw RuntimeException("Invalid invitation response with id=[${receivedPtMsg.id}].")
    }

    fun handleAckMessage(receivedPtMsg: PlainTextMessage): PlainTextMessage {
        receivedPtMsg.to?.let {
            return PlainTextMessage(
                type = Piuris.MESSAGE_ACK.value,
                thid = receivedPtMsg.id,
                from = it.first(),
                to = mutableListOf(receivedPtMsg.from),
                body = "ack: ${receivedPtMsg.id}",
                createdTime = Instant.now().toEpochMilli(),
                expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
            )
        }

        throw RuntimeException("Invalid message with id=[${receivedPtMsg.id}].")
    }

    fun packPlainTextMessage(to: PeerDID, message: PlainTextMessage): PackEncryptedResult {
        val packedMsg = didPeer.pack(data = message.toJson(mapper), from = message.from, to = to)
        log.info("Packed message = [{}]", packedMsg)

        return packedMsg
    }

    fun persistMessageReceived(
        receivedPtMsg: PlainTextMessage,
        to: PeerDID,
        from: PeerDID? = null,
        connectionId: String? = null,
    ): DidCommMessageEntity {
        if (from.isNullOrBlank() && connectionId.isNullOrBlank()) {
            throw RuntimeException("Unable to persist the received message because connection identifier was not found.")
        }

        log.info("Received plaintext message = [{}] persisted.", receivedPtMsg)
        return didCommMessageDataProvider.insert(
            connectionId ?: didCommConnectionDataProvider.findByMyDid(to)._id,
            to.getServiceEndpoint() ?: "N/A",
            RouteType.IN,
            receivedPtMsg
        )
    }
}
