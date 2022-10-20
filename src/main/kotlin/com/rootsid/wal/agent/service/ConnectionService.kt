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
}
