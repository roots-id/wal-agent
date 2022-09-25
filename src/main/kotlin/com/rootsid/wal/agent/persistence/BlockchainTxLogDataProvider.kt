package com.rootsid.wal.agent.persistence

import com.rootsid.wal.agent.persistence.model.BlockchainTxLogEntity
import com.rootsid.wal.library.wallet.model.BlockchainTxAction
import com.rootsid.wal.library.wallet.model.BlockchainTxLog
import com.rootsid.wal.library.wallet.storage.BlockchainTxLogStorage
import io.iohk.atala.prism.api.models.AtalaOperationStatus
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class BlockchainTxLogDataProvider(private val repository: BlockchainTxLogRepository) : BlockchainTxLogStorage {
    override fun insert(txLog: BlockchainTxLog): BlockchainTxLog = repository.save(txLog as BlockchainTxLogEntity)

    /**
     * Update tx log with new entry     *
     * @param txLog updated tx log data object
     * @return true if the operation was acknowledged
     */
    override fun update(txLog: BlockchainTxLog): Boolean = Optional.ofNullable(this.insert(txLog)).isPresent

    override fun list(): List<BlockchainTxLog> = repository.findAll().toMutableList()

    override fun listPending(): List<BlockchainTxLog> = repository.findByStatusIn(listOf(AtalaOperationStatus.AWAIT_CONFIRMATION, AtalaOperationStatus.PENDING_SUBMISSION)).toMutableList()

    override fun findById(txLogId: String): BlockchainTxLog = repository.findById(txLogId).orElseThrow { RuntimeException("Transaction log with id=[$txLogId] not found.") }

    override fun exists(txLogId: String): Boolean = repository.findById(txLogId).isPresent

    override fun createTxLogObject(txLogId: String, walletId: String, action: BlockchainTxAction, description: String?): BlockchainTxLog =
        BlockchainTxLogEntity(txLogId, walletId, action, description)
}

interface BlockchainTxLogRepository : CrudRepository<BlockchainTxLogEntity, String> {
    fun findByStatusIn(opStatus: List<Int>): Iterable<BlockchainTxLogEntity>
}
