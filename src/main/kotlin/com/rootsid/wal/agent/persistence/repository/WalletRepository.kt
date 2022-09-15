package com.rootsid.wal.agent.persistence.repository

import com.rootsid.wal.agent.persistence.model.WalletEntity
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

sealed interface WalletRepository : CrudRepository<WalletEntity, String> {

    @Query(value = "{ _id: :#{#walletId}, dids: { \$elemMatch: { alias: :#{#didAlias} } } }")
    fun findDidByAlias(@Param("walletId") walletId: String, @Param("didAlias") didAlias: String): Optional<WalletEntity>

}
