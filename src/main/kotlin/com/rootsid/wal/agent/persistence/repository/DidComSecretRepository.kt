package com.rootsid.wal.agent.persistence.repository

import com.rootsid.wal.agent.persistence.model.DidComSecretEntity
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

sealed interface DidComSecretRepository : CrudRepository<DidComSecretEntity, String> {
    @Query(value = "{ _id: { \$in: :#{#kids} } }", fields="{ _id : 1 }")
    fun findIdsIn(@Param("kids") kids: List<String>): Set<DidComSecretEntity>

    @Query(value = "{}", fields="{ _id : 1 }")
    fun listIds(): Iterable<DidComSecretEntity>
}
