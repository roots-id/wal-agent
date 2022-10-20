package com.rootsid.wal.agent.service

import com.rootsid.wal.agent.dto.ConnectionDto
import com.rootsid.wal.agent.dto.convert
import com.rootsid.wal.agent.persistence.DidCommConnectionDataProvider
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ConnectionService(
    private val didCommConnectionDataProvider: DidCommConnectionDataProvider,
) {
    private val log = LoggerFactory.getLogger(ConnectionService::class.java)

    fun listConnections(): List<ConnectionDto> = didCommConnectionDataProvider.list().convert()

    fun getConnection(id: String): ConnectionDto = didCommConnectionDataProvider.findById(id).convert()

    fun deleteConnection(id: String) = didCommConnectionDataProvider.delete(id)

//    fun sendMessage(payload: SendMessageRequest): SendMessageResponse {
//        val conn = didCommConnectionDataProvider.findById(payload.connectionId)
//
//        conn.myDid.let { myDid ->
//            conn.theirDid?.let { theirDid ->
//                // Create didcomm plaintext message
//                val message = PlainTextMessage(
//                    type = Piuris.MESSAGE.value,
//                    from = myDid,
//                    to = mutableListOf(theirDid),
//                    body = payload.content,
//                    createdTime = Instant.now().toEpochMilli(),
//                    expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
//                )
//
//                if (payload.autoSend) {
//                    log.info("Start auto send process.")
//                    didCommClientService.sendMessage(theirDid, message).also {
//                        didCommMessageDataProvider.insert(
//                            conn._id, theirDid.getServiceEndpoint() ?: "N/A", RouteType.OUT, message
//                        )
//                    }
//                }
//
//                return SendMessageResponse(packPlainTextMessage(myDid, message).packedMessage)
//            }
//        }
//
//        throw RuntimeException("Connection not established")
//    }
//
//    fun sendDidRotationMessage(payload: SendDidRotationMessageRequest): SendDidRotationMessageResponse {
//        val conn = didCommConnectionDataProvider.findById(payload.connectionId) as DidCommConnectionEntity
//
//        // Create new did (MyDid)
//        val newMyDid = payload.newPeerDid ?: didPeer.create(conn.myDid.getServiceEndpoint())
//
//        conn.also {
//            didCommConnectionDataProvider.insert(it.copy(myDid = newMyDid))
//        }.myDid.let { myDid ->
//            conn.theirDid?.let { theirDid ->
//                // Create didcomm plaintext message
//                val message = PlainTextMessage(
//                    type = Piuris.MESSAGE.value,
//                    from = newMyDid,
//                    fromPrior = myDid,
//                    to = mutableListOf(theirDid),
//                    body = payload.content,
//                    createdTime = Instant.now().toEpochMilli(),
//                    expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
//                )
//
//                if (payload.autoSend) {
//                    log.info("Start auto send process.")
//                    didCommClientService.sendMessage(theirDid, message).also {
//                        didCommMessageDataProvider.insert(
//                            conn._id, theirDid.getServiceEndpoint() ?: "N/A", RouteType.OUT, message
//                        )
//                    }
//                }
//
//                return SendDidRotationMessageResponse(
//                    status = "manual",
//                    content = packPlainTextMessage(newMyDid, message).packedMessage
//                )
//            }
//        }
//
//        throw RuntimeException("Connection not established")
//    }
//
//    fun receiveMessage(payload: ReceiveMessageRequest): ReceiveMessageResponse {
//        val receivedMsg = didPeer.unpack(payload.content)
//        log.info("Received unpacked message = [{}]", receivedMsg)
//
//        receivedMsg.from?.let { from ->
//            // Parse and store message received
//            val receivedPtMsg = receivedMsg.toPlainTextMessage()
//
//            // Create didcomm plaintext message (ACK)
//            val pTextMsgAck = when {
//                receivedPtMsg.isInvitationResponseMsg() -> handleInvitationResponseMessage(receivedPtMsg)
//                receivedPtMsg.isDidRotationMsg() -> handleDidRotation(receivedPtMsg)
//                else -> ackMessage(receivedPtMsg)
//            }
//            log.info("Ack plaintext message = [{}]", pTextMsgAck)
//
//            // Persist the received message after creating connection
//            persistMessageReceived(receivedPtMsg = receivedPtMsg, myDid = receivedMsg.to, theirDid = from)
//
//            val ackMessage = didPeer.pack(data = pTextMsgAck.toJson(), from = receivedMsg.to, to = receivedPtMsg.from)
//            log.info("Packed ack message = [{}]", ackMessage)
//
//            return ReceiveMessageResponse(ackMessage.packedMessage)
//        }
//
//        throw RuntimeException("Unable to process message")
//    }
//
//    fun ReceiveMessageWithAutoAck(payload: ReceiveMessageRequest): ReceiveMessageResponse {
//        val receivedMsg = didPeer.unpack(payload.content)
//        log.info("Received unpacked message = [{}]", receivedMsg)
//
//        receivedMsg.from?.let { from ->
//            val conn = didCommConnectionDataProvider.findByTheirDid(from) as DidCommConnectionEntity
//
//            // Parse and store message received
//            val receivedPtMsg = receivedMsg.toPlainTextMessage().also {
//                persistMessageReceived(receivedPtMsg = it, connectionId = conn._id, myDid = receivedMsg.to)
//            }
//
//            // Create didcomm plaintext message (ACK)
//            val pTextMsgAck = when {
//                receivedPtMsg.isInvitationResponseMsg() -> handleInvitationResponseMessage(receivedPtMsg)
//                receivedPtMsg.isDidRotationMsg() -> handleDidRotation(receivedPtMsg)
//                else -> ackMessage(receivedPtMsg)
//            }
//            log.info("Ack plaintext message = [{}]", pTextMsgAck)
//
//            if (payload.autoAck) {
//                // Send message to emitter and response 204 (no content)
//                didCommClientService.sendMessage(receivedMsg.to, pTextMsgAck)
//                    .also {
//                        didCommMessageDataProvider.insert(
//                            conn._id, receivedMsg.to.getServiceEndpoint() ?: "N/A", RouteType.OUT, pTextMsgAck
//                        )
//                    }
//                    .takeIf { it }
//                    ?.apply {
//                        return ReceiveMessageResponse(
//                            content = "Auto-ack message executed"
//                        )
//                    }
//
//                log.error("Unable to execute the auto-ack process.   Execute manual process.")
//            }
//
//            val ackMessage = didPeer.pack(data = pTextMsgAck.toJson(), from = receivedMsg.to, to = receivedPtMsg.from)
//            log.info("Packed ack message = [{}]", ackMessage)
//
//            return ReceiveMessageResponse(ackMessage.packedMessage)
//        }
//
//        throw RuntimeException("Unable to process message")
//    }
//
//    private fun handleInvitationResponseMessage(receivedPtMsg: PlainTextMessage): PlainTextMessage {
//        receivedPtMsg.pthid?.let { pthid ->
//            val conn = didCommConnectionDataProvider.findByInvitationMsgId(pthid) as DidCommConnectionEntity
//            didCommConnectionDataProvider.insert(
//                conn.copy(
//                    theirDid = receivedPtMsg.from,
//                    state = DidCommDataTypes.ConnectionState.COMPLETED
//                )
//            )
//            log.info("Connection with id = [{}] updated.", conn._id)
//
//            return PlainTextMessage(
//                type = Piuris.MESSAGE_ACK.value,
//                pthid = pthid,
//                thid = receivedPtMsg.id,
//                from = conn.myDid,
//                body = "ack: $pthid",
//                createdTime = Instant.now().toEpochMilli(),
//                expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
//            )
//        }
//
//        throw RuntimeException("Invalid invitation response with id=[${receivedPtMsg.id}].")
//    }
//
//    // TODO: Rotate using JWT header
//    private fun handleDidRotation(receivedPtMsg: PlainTextMessage): PlainTextMessage {
//        receivedPtMsg.fromPrior?.let { fromPrior ->
//            val conn = didCommConnectionDataProvider.findByTheirDid(fromPrior) as DidCommConnectionEntity
//
//            didCommConnectionDataProvider.insert(conn.copy(theirDid = receivedPtMsg.from))
//            log.info("Connection with id = [{}] updated.", conn._id)
//
//            return PlainTextMessage(
//                type = Piuris.MESSAGE_ACK.value,
//                thid = receivedPtMsg.id,
//                from = conn.myDid,
//                body = "ack: ${receivedPtMsg.id}",
//                createdTime = Instant.now().toEpochMilli(),
//                expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
//            )
//        }
//
//        throw RuntimeException("Invalid invitation response with id=[${receivedPtMsg.id}].")
//    }
//
//    private fun ackMessage(receivedPtMsg: PlainTextMessage): PlainTextMessage {
//        receivedPtMsg.to?.let {
//            return PlainTextMessage(
//                type = Piuris.MESSAGE_ACK.value,
//                thid = receivedPtMsg.id,
//                from = it.first(),
//                to = mutableListOf(receivedPtMsg.from),
//                body = "ack: ${receivedPtMsg.id}",
//                createdTime = Instant.now().toEpochMilli(),
//                expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
//            )
//        }
//
//        throw RuntimeException("Invalid message with id=[${receivedPtMsg.id}].")
//    }
//
//    private fun packPlainTextMessage(to: PeerDID, message: PlainTextMessage): PackEncryptedResult {
//        val packedMsg = didPeer.pack(data = message.toJson(mapper), from = message.from, to = to)
//        log.info("Packed message = [{}]", packedMsg)
//
//        return packedMsg
//    }
//
//    private fun persistMessageReceived(
//        receivedPtMsg: PlainTextMessage,
//        myDid: PeerDID,
//        theirDid: PeerDID? = null,
//        connectionId: String? = null,
//    ) {
//        if (theirDid.isNullOrBlank() && connectionId.isNullOrBlank()) {
//            throw RuntimeException("Unable to persist the received message because connection identifier was not found.")
//        }
//
//        didCommMessageDataProvider.insert(
//            connectionId ?: didCommConnectionDataProvider.findByTheirDid(theirDid!!)._id,
//            myDid.getServiceEndpoint() ?: "N/A",
//            RouteType.IN,
//            receivedPtMsg
//        )
//        log.info("Received plaintext message = [{}] persisted.", receivedPtMsg)
//    }
}
