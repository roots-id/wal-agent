package com.rootsid.wal.agent.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rootsid.wal.agent.api.model.didcomm.PlainTextMessage
import com.rootsid.wal.agent.api.model.didcomm.toJson
import com.rootsid.wal.agent.api.request.action.ReceiveMessageRequest
import com.rootsid.wal.agent.api.request.action.SendMessageRequest
import com.rootsid.wal.agent.api.response.action.ReceiveMessageResponse
import com.rootsid.wal.agent.api.response.action.SendMessageResponse
import com.rootsid.wal.agent.dto.ConnectionDto
import com.rootsid.wal.agent.dto.convert
import com.rootsid.wal.agent.persistence.DidCommConnectionDataProvider
import com.rootsid.wal.library.didcomm.DIDPeer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.URI
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class ConnectionService(private val didCommConnectionDataProvider: DidCommConnectionDataProvider, private val didPeer: DIDPeer) {
    private val log = LoggerFactory.getLogger(ConnectionService::class.java)

    fun listConnections(): List<ConnectionDto> = didCommConnectionDataProvider.list().convert()

    fun getConnection(id: String): ConnectionDto = didCommConnectionDataProvider.findById(id).convert()

    fun deleteConnection(id: String) = didCommConnectionDataProvider.delete(id)

    fun sendMessage(payload: SendMessageRequest): SendMessageResponse {
        var conn = didCommConnectionDataProvider.findById(payload.connectionId)

        if(conn.myDid == null) {
            throw RuntimeException("Connection not established")
        }

        // Create didcomm plaintext message
        var ptMsg = PlainTextMessage(type = URI.create(""), from = conn.myDid, to = mutableListOf(conn.theirDid),
            body = payload.content, createdTime = Instant.now().toEpochMilli(),
            expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
        ).toJson()
        log.info("Plaintext message = [{}]", ptMsg)

        val message = didPeer.pack(
            data = ptMsg, from = conn.myDid,
            to = conn.theirDid
        )
        log.info("Packed message = [{}]", message)

        return SendMessageResponse(message.packedMessage)
    }

    fun receiveMessage(payload: ReceiveMessageRequest): ReceiveMessageResponse {
        val receivedMsg = didPeer.unpack(payload.content)
        log.info("Received unpacked message = [{}]", receivedMsg)

        var receivedPtMsg = jacksonObjectMapper().readValue(receivedMsg.message, PlainTextMessage::class.java)
        log.info("Received plaintext message = [{}]", receivedPtMsg)

        // Create didcomm plaintext message (ACK)
//        var ptMsg = PlainTextMessage(type = URI.create(""), from = conn.myDid, to = mutableListOf(conn.theirDid),
//            body = payload.content, createdTime = Instant.now().toEpochMilli(),
//            expiresTime = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
//        ).toJson()
//        log.info("Plaintext message = [{}]", ptMsg)

        // TODO: payload.content => 3. Plain Text Message Structure  didcom

//        {
//            "id": "1234567890",
//            "type": "<message-type-uri>",
//            "from": "did:example:alice",
//            "to": ["did:example:bob"],
//            "created_time": 1516269022,
//            "expires_time": 1516385931,
//            "body": "Hello"
//        }

        // body: "{"ack": ["id del message del sender", "def", "xyz"]}"

        val ackMessage = didPeer.pack(
            data = "ACK >> " + msg.message, from = msg.from,
            to = msg.to
        )
        log.info("Packed ack message = [{}]", ackMessage)

        return ReceiveMessageResponse(ackMessage.packedMessage)
    }
}
