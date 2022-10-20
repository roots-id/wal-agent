package com.rootsid.wal.agent.persistence.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonValue
import com.rootsid.wal.agent.api.model.didcomm.PlainTextMessage
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Document("didcomm_messages")
data class DidCommMessageEntity(
    @Id
    val _id: String = UUID.randomUUID().toString(),
    val connectionId: String,
    val serviceEndpoint: String,
    val routeType: RouteType,
    val messageId: String,
    val messageBody: String,
    val message: PlainTextMessage,
    val createdAt: LocalDateTime = LocalDateTime.now()
) : Serializable

@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class RouteType(@JsonValue val value: String) {
    IN("in"),
    OUT("out")
}
