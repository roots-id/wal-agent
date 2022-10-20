package com.rootsid.wal.agent.persistence

import com.rootsid.wal.agent.api.model.didcomm.PlainTextMessage
import com.rootsid.wal.agent.persistence.model.DidCommMessageEntity
import com.rootsid.wal.agent.persistence.model.RouteType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service


@Service
class DidCommMessageDataProvider(private val repository: DidCommMessageRepository) {

    fun insert(connectionId: String, serviceEndpoint: String, routeType: RouteType, message: PlainTextMessage): DidCommMessageEntity =
        repository.save(
            DidCommMessageEntity(
                connectionId = connectionId,
                serviceEndpoint = serviceEndpoint,
                routeType = routeType,
                messageId = message.id,
                messageBody = message.body,
                message = message
            )
        )


    fun findById(id: String): DidCommMessageEntity =
        repository.findById(id).orElseThrow { RuntimeException("DidComm message with id=[$id] not found.") }

    fun list(): List<DidCommMessageEntity> = repository.findAll().toMutableList()
}

interface DidCommMessageRepository : CrudRepository<DidCommMessageEntity, String> {}
