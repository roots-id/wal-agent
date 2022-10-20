package com.rootsid.wal.agent.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.rootsid.wal.agent.api.model.didcomm.*
import com.rootsid.wal.agent.api.request.action.ReceiveMessageRequest
import com.rootsid.wal.agent.api.request.action.SendDidRotationMessageRequest
import com.rootsid.wal.agent.api.request.action.SendMessageRequest
import com.rootsid.wal.agent.api.response.action.ReceiveMessageResponse
import com.rootsid.wal.agent.api.response.action.SendDidRotationMessageResponse
import com.rootsid.wal.agent.api.response.action.SendMessageResponse
import com.rootsid.wal.agent.persistence.DidCommConnectionDataProvider
import com.rootsid.wal.agent.persistence.DidCommMessageDataProvider
import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import com.rootsid.wal.library.didcomm.DIDPeer
import com.rootsid.wal.library.didcomm.common.getServiceEndpoint
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class ActionManualService(
    private val didCommConnectionDataProvider: DidCommConnectionDataProvider,
    private val didPeer: DIDPeer,
    mapper: ObjectMapper,
    didCommMessageDataProvider: DidCommMessageDataProvider
): ActionBaseService(mapper, didCommConnectionDataProvider, didPeer, didCommMessageDataProvider) {
    private val log = LoggerFactory.getLogger(ActionManualService::class.java)

    fun sendMessage(payload: SendMessageRequest): SendMessageResponse {
        val conn = didCommConnectionDataProvider.findById(payload.connectionId)

        conn.myDid.let { myDid ->
            conn.theirDid?.let { theirDid ->
                // Create didcomm plaintext message
                val message = PlainTextMessage(
                    type = Piuris.MESSAGE.value,
                    from = myDid,
                    to = mutableListOf(theirDid),
                    body = payload.content,
                    createdTime = Instant.now().toEpochMilli(),
                    expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                )

                return SendMessageResponse(packPlainTextMessage(myDid, message).packedMessage)
            }
        }

        throw RuntimeException("Connection not established")
    }

    fun sendDidRotationMessage(payload: SendDidRotationMessageRequest): SendDidRotationMessageResponse {
        val conn = didCommConnectionDataProvider.findById(payload.connectionId) as DidCommConnectionEntity

        // Create new did (MyDid)
        val newMyDid = payload.newPeerDid ?: didPeer.create(conn.myDid.getServiceEndpoint())

        conn.also {
            didCommConnectionDataProvider.insert(it.copy(myDid = newMyDid))
        }.myDid.let { myDid ->
            conn.theirDid?.let { theirDid ->
                // Create didcomm plaintext message
                val message = PlainTextMessage(
                    type = Piuris.MESSAGE.value,
                    from = newMyDid,
                    fromPrior = myDid,
                    to = mutableListOf(theirDid),
                    body = payload.content,
                    createdTime = Instant.now().toEpochMilli(),
                    expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                )

                return SendDidRotationMessageResponse(
                    status = "manual",
                    content = packPlainTextMessage(newMyDid, message).packedMessage
                )
            }
        }

        throw RuntimeException("Connection not established")
    }

    fun receiveMessage(payload: ReceiveMessageRequest): ReceiveMessageResponse {
        val receivedMsg = didPeer.unpack(payload.content)
        log.info("Received unpacked message = [{}]", receivedMsg)

        receivedMsg.from?.let { from ->
            // Parse and store message received
            val receivedPtMsg = receivedMsg.toPlainTextMessage()

            // Create didcomm plaintext message (ACK)
            val pTextMsgAck = when {
                receivedPtMsg.isInvitationResponseMsg() -> handleInvitationResponseMessage(receivedPtMsg)
                receivedPtMsg.isDidRotationMsg() -> handleDidRotation(receivedPtMsg)
                else -> handleAckMessage(receivedPtMsg)
            }
            log.info("Ack plaintext message = [{}]", pTextMsgAck)

            // Persist the received message after creating connection
            persistMessageReceived(receivedPtMsg = receivedPtMsg, from = from, to = receivedMsg.to)

            val ackMessage = didPeer.pack(data = pTextMsgAck.toJson(), from = receivedMsg.to, to = receivedPtMsg.from)
            log.info("Packed ack message = [{}]", ackMessage)

            return ReceiveMessageResponse("manual", ackMessage.packedMessage)
        }

        throw RuntimeException("Unable to process message")
    }
}
