package com.rootsid.wal.agent.persistence

import com.rootsid.wal.agent.persistence.model.DidCommSecretEntity
import com.rootsid.wal.agent.persistence.repository.DidCommSecretRepository
import com.rootsid.wal.library.didcomm.model.DidCommSecret
import com.rootsid.wal.library.didcomm.storage.DidCommSecretStorage
import org.springframework.stereotype.Service


@Service
class DidCommSecretDataProvider(private val didComSecretRepository: DidCommSecretRepository) : DidCommSecretStorage {
    override fun insert(kid: String, secretJson: Map<String, Any>): DidCommSecret =
        didComSecretRepository.save(DidCommSecretEntity(kid, secretJson))

    override fun findById(kid: String): DidCommSecret =
        didComSecretRepository.findById(kid).orElseThrow { RuntimeException("DidCom Secret with id=[$kid] not found.") }

    override fun findIdsIn(kids: List<String>): Set<String> = didComSecretRepository.findIdsIn(kids).map { it._id }.toSet()

    override fun listIds(): List<String> = didComSecretRepository.listIds().map { it._id }.toMutableList()

    override fun list(): List<DidCommSecret> = didComSecretRepository.findAll().toMutableList()
}
