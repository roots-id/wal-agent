package com.rootsid.wal.agent.restclient

import com.fasterxml.jackson.databind.ObjectMapper
import com.rootsid.wal.agent.api.model.didcomm.PlainTextMessage
import com.rootsid.wal.agent.api.model.didcomm.toJson
import com.rootsid.wal.agent.api.request.action.ReceiveMessageRequest
import com.rootsid.wal.agent.persistence.DidCommMessageDataProvider
import com.rootsid.wal.agent.persistence.model.RouteType
import com.rootsid.wal.library.didcomm.DIDPeer
import com.rootsid.wal.library.didcomm.common.getServiceEndpoint
import org.didcommx.didcomm.model.PackEncryptedResult
import org.didcommx.peerdid.PeerDID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DidCommClientService(
    private val mapper: ObjectMapper, private val didCommWebClient: DidCommWebClient,
    private val didPeer: DIDPeer, private val didCommMessageDataProvider: DidCommMessageDataProvider
) {
    private val log = LoggerFactory.getLogger(DidCommClientService::class.java)

    fun sendMessage(connectionId: String, to: PeerDID, message: PlainTextMessage, autoAck: Boolean = false): Boolean {
        val packedMsg = packPlainTextMessage(to, message)

        to.getServiceEndpoint()
            ?.also {
                didCommMessageDataProvider.insert(
                    connectionId, it, RouteType.OUT, message
                )
            }
            ?.let {
                log.info("Send message to [{}]", it)
                didCommWebClient.receiveMessageSynchronous(
                    it,
                    ReceiveMessageRequest(autoAck = autoAck, content = packedMsg.packedMessage)
                )?.body
                    .let { response ->
                        log.info("Result of message sent.  Body = [{}]", response)
                    }

                return true
            }

        return false
    }

    private fun packPlainTextMessage(to: PeerDID, message: PlainTextMessage): PackEncryptedResult {
        val packedMsg = didPeer.pack(data = message.toJson(mapper), from = message.from, to = to)
        log.info("Packed message = [{}]", packedMsg)

        return packedMsg
    }
}
