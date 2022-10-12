package com.rootsid.wal.agent.restclient

import com.rootsid.wal.agent.api.request.action.ReceiveMessageRequest
import com.rootsid.wal.agent.api.request.action.SendMessageRequest
import com.rootsid.wal.agent.api.response.action.ReceiveMessageResponse
import com.rootsid.wal.agent.api.response.action.SendMessageResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class DidCommWebClient(private val webClient: WebClient) {
    fun sendMessageSynchronous(url: String, payload: SendMessageRequest): ResponseEntity<SendMessageResponse>? =
        buildMessageWebClient(url, "/actions/send-message", payload)
            .toEntity(SendMessageResponse::class.java)
            .block()

    fun sendMessageAsynchronous(url: String, payload: SendMessageRequest): Mono<SendMessageResponse> =
        buildMessageWebClient(url, "/actions/send-message", payload).bodyToMono(SendMessageResponse::class.java)

    fun receiveMessageSynchronous(
        url: String,
        payload: ReceiveMessageRequest
    ): ResponseEntity<ReceiveMessageResponse>? =
        buildMessageWebClient(url, "/actions/receive-message", payload)
            .toEntity(ReceiveMessageResponse::class.java)
            .block()

    fun receiveMessageAsynchronous(url: String, payload: ReceiveMessageRequest): Mono<ReceiveMessageResponse> =
        buildMessageWebClient(url, "/actions/receive-message", payload).bodyToMono(ReceiveMessageResponse::class.java)

    private fun <T: Any> buildMessageWebClient(url: String, path: String, payload: T): WebClient.ResponseSpec =
        webClient
            .post()
            .uri(
                UriComponentsBuilder
                    .fromHttpUrl(url)
                    .path(path)
                    .build()
                    .toUri()
            )
            .body(BodyInserters.fromValue(payload))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) { Mono.error(RuntimeException("4XX Error ${it.statusCode()}")) }
            .onStatus(HttpStatus::is5xxServerError) { Mono.error(RuntimeException("5XX Error ${it.statusCode()}")) }
}
