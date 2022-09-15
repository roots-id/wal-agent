package com.rootsid.wal.agent.persistence

import com.rootsid.wal.agent.persistence.model.DidComSecretEntity
import com.rootsid.wal.agent.persistence.repository.DidComSecretRepository
import com.rootsid.wal.library.didcom.model.DidComSecret
import com.rootsid.wal.library.didcom.storage.DidComSecretStorage
import org.springframework.stereotype.Service


@Service
class DidComSecretDataProvider(private val didComSecretRepository: DidComSecretRepository) : DidComSecretStorage {
    override fun insert(kid: String, secretJson: Map<String, Any>): DidComSecret =
        didComSecretRepository.save(DidComSecretEntity(kid, secretJson))

    override fun findById(kid: String): DidComSecret =
        didComSecretRepository.findById(kid).orElseThrow { RuntimeException("DidCom Secret with id=[$kid] not found.") }

    override fun findIdsIn(kids: List<String>): Set<String> = didComSecretRepository.findIdsIn(kids).map { it._id }.toSet()

    override fun listIds(): List<String> = didComSecretRepository.listIds().map { it._id }.toMutableList()

    override fun list(): List<DidComSecret> = didComSecretRepository.findAll().toMutableList()
}
