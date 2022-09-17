package com.rootsid.wal.agent.persistence

import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import com.rootsid.wal.agent.persistence.repository.DidCommConnectionRepository
import com.rootsid.wal.library.didcomm.model.DidCommConnection
import com.rootsid.wal.library.didcomm.storage.DidCommConnectionStorage
import org.springframework.stereotype.Service
import java.util.*

@Service
class DidCommConnectionDataProvider(private val didCommConnectionRepository: DidCommConnectionRepository) :
    DidCommConnectionStorage {
    override fun insert(conn: DidCommConnection): DidCommConnection =
        didCommConnectionRepository.save(conn as DidCommConnectionEntity)

    override fun findById(id: String): DidCommConnection =
        didCommConnectionRepository.findById(id)
            .orElseThrow { RuntimeException("DidCom Connection with id=[$id] not found.") }

    override fun list(): List<DidCommConnection> = didCommConnectionRepository.findAll().toMutableList()
}
