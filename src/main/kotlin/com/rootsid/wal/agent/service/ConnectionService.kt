package com.rootsid.wal.agent.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rootsid.wal.agent.api.model.didcomm.*
import com.rootsid.wal.agent.api.request.action.ReceiveMessageRequest
import com.rootsid.wal.agent.api.request.action.SendDidRotationMessageRequest
import com.rootsid.wal.agent.api.request.action.SendMessageRequest
import com.rootsid.wal.agent.api.response.action.ReceiveMessageResponse
import com.rootsid.wal.agent.api.response.action.SendDidRotationMessageResponse
import com.rootsid.wal.agent.api.response.action.SendMessageResponse
import com.rootsid.wal.agent.dto.ConnectionDto
import com.rootsid.wal.agent.dto.convert
import com.rootsid.wal.agent.persistence.DidCommConnectionDataProvider
import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import com.rootsid.wal.agent.restclient.DidCommWebClient
import com.rootsid.wal.library.didcomm.DIDPeer
import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import com.rootsid.wal.library.didcomm.common.getServiceEndpoint
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class ConnectionService(
    private val didCommConnectionDataProvider: DidCommConnectionDataProvider,
    private val didPeer: DIDPeer,
    private val didCommWebClient: DidCommWebClient
) {
    private val log = LoggerFactory.getLogger(ConnectionService::class.java)

    fun listConnections(): List<ConnectionDto> = didCommConnectionDataProvider.list().convert()

    fun getConnection(id: String): ConnectionDto = didCommConnectionDataProvider.findById(id).convert()

    fun deleteConnection(id: String) = didCommConnectionDataProvider.delete(id)

    fun sendMessage(payload: SendMessageRequest): SendMessageResponse {
        val conn = didCommConnectionDataProvider.findById(payload.connectionId)

        conn.myDid.let { myDid ->
            conn.theirDid?.let { theirDid ->
                // Create didcomm plaintext message
                val ptMsg = PlainTextMessage(
                    type = Piuris.MESSAGE.value, from = myDid, to = mutableListOf(theirDid),
                    body = payload.content, createdTime = Instant.now().toEpochMilli(),
                    expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                ).toJson()
                log.info("Plaintext message = [{}]", ptMsg)

                val message = didPeer.pack(
                    data = ptMsg, from = myDid,
                    to = theirDid
                )
                log.info("Packed message = [{}]", message)

                return SendMessageResponse(message.packedMessage)
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
                val ptMsg = PlainTextMessage(
                    type = Piuris.MESSAGE.value, fromPrior = myDid, from = newMyDid, to = mutableListOf(theirDid),
                    body = payload.content, createdTime = Instant.now().toEpochMilli(),
                    expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                ).toJson()
                log.info("Plaintext message = [{}]", ptMsg)

                val packMessage = didPeer.pack(
                    data = ptMsg, from = newMyDid,
                    to = theirDid
                )
                log.info("Packed message = [{}]", packMessage)

                if (payload.autoSend) {
                    log.info("Start auto send process.")
                    theirDid.getServiceEndpoint()?.let { serviceEndpoint ->
                        log.info("Send did rotation message to [{}]", serviceEndpoint)
                        didCommWebClient.receiveMessageSynchronous(
                            serviceEndpoint,
                            ReceiveMessageRequest(packMessage.packedMessage)
                        )?.body.let { response ->
                            log.info("Result of did rotation message.  Body = [{}]", response)

                            return SendDidRotationMessageResponse()
                        }
                    }
                }

                return SendDidRotationMessageResponse(status = "manual", content = packMessage.packedMessage)
            }
        }

        throw RuntimeException("Connection not established")
    }

    fun receiveMessage(payload: ReceiveMessageRequest): ReceiveMessageResponse {
        val receivedMsg = didPeer.unpack(payload.content)
        log.info("Received unpacked message = [{}]", receivedMsg)

        val receivedPtMsg = jacksonObjectMapper().readValue(receivedMsg.message, PlainTextMessage::class.java)
        log.info("Received plaintext message = [{}]", receivedPtMsg)

        // Create didcomm plaintext message (ACK)
        val ackPtMsg = when {
            receivedPtMsg.isInvitationResponseMsg() -> handleInvitationResponseMessage(receivedPtMsg)
            receivedPtMsg.isDidRotationMsg() -> handleDidRotation(receivedPtMsg)
            else -> ackMessage(receivedPtMsg)
        }
        log.info("Ack plaintext message = [{}]", ackPtMsg)

        val ackMessage = didPeer.pack(data = ackPtMsg, from = receivedMsg.to, to = receivedPtMsg.from)
        log.info("Packed ack message = [{}]", ackMessage)

        return ReceiveMessageResponse(ackMessage.packedMessage)
    }

    private fun handleInvitationResponseMessage(receivedPtMsg: PlainTextMessage): String {
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
            ).toJson()
        }

        throw RuntimeException("Invalid invitation response with id=[${receivedPtMsg.id}].")
    }

    // TODO: Rotate using JWT header
    private fun handleDidRotation(receivedPtMsg: PlainTextMessage): String {
        receivedPtMsg.fromPrior?.let { fromPrior ->
            val conn = didCommConnectionDataProvider.findByTheirDid(fromPrior) as DidCommConnectionEntity

            didCommConnectionDataProvider.insert(conn.copy(theirDid = receivedPtMsg.from))
            log.info("Connection with id = [{}] updated.", conn._id)

            return PlainTextMessage(
                type = Piuris.MESSAGE_ACK.value,
                thid = receivedPtMsg.id,
                from = conn.myDid,
                body = "ack: ${receivedPtMsg.id}",
                createdTime = Instant.now().toEpochMilli(),
                expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
            ).toJson()
        }

        throw RuntimeException("Invalid invitation response with id=[${receivedPtMsg.id}].")
    }

    private fun ackMessage(receivedPtMsg: PlainTextMessage): String {
        receivedPtMsg.to?.let {
            return PlainTextMessage(
                type = Piuris.MESSAGE_ACK.value,
                thid = receivedPtMsg.id,
                from = it.first(),
                to = mutableListOf(receivedPtMsg.from),
                body = "ack: ${receivedPtMsg.id}",
                createdTime = Instant.now().toEpochMilli(),
                expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
            ).toJson()
        }

        throw RuntimeException("Invalid message with id=[${receivedPtMsg.id}].")
    }
}
