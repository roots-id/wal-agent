package com.rootsid.wal.agent.persistence

import com.rootsid.wal.agent.persistence.model.DidCommSecretEntity
import com.rootsid.wal.library.didcomm.model.DidCommSecret
import com.rootsid.wal.library.didcomm.storage.DidCommSecretStorage
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service


@Service
class DidCommSecretDataProvider(private val didComSecretRepository: DidCommSecretRepository) : DidCommSecretStorage {
    override fun insert(kid: String, secretJson: Map<String, Any>): DidCommSecret =
        didComSecretRepository.save(DidCommSecretEntity(kid, secretJson))

    override fun findById(kid: String): DidCommSecret =
        didComSecretRepository.findById(kid).orElseThrow { RuntimeException("DidComm Secret with id=[$kid] not found.") }

    override fun findIdsIn(kids: List<String>): Set<String> = didComSecretRepository.findIdsIn(kids).map { it._id }.toSet()

    override fun listIds(): List<String> = didComSecretRepository.listIds().map { it._id }.toMutableList()

    override fun list(): List<DidCommSecret> = didComSecretRepository.findAll().toMutableList()
}

interface DidCommSecretRepository : CrudRepository<DidCommSecretEntity, String> {
    @Query(value = "{ _id: { \$in: :#{#kids} } }", fields="{ _id : 1 }")
    fun findIdsIn(@Param("kids") kids: List<String>): Set<DidCommSecretEntity>

    @Query(value = "{}", fields="{ _id : 1 }")
    fun listIds(): Iterable<DidCommSecretEntity>
}
