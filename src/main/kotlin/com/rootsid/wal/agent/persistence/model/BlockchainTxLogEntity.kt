package com.rootsid.wal.agent.persistence.model

import com.rootsid.wal.library.wallet.model.BlockchainTxAction
import com.rootsid.wal.library.wallet.model.BlockchainTxLog
import io.iohk.atala.prism.api.models.AtalaOperationStatus
import io.iohk.atala.prism.api.models.AtalaOperationStatusEnum
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("tx_logs")
data class BlockchainTxLogEntity(
    override val _id: String,
    override val walletId: String,
    override val action: BlockchainTxAction,
    override var description: String? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime = LocalDateTime.now(),
    override var status: AtalaOperationStatusEnum = AtalaOperationStatus.PENDING_SUBMISSION,
    override var txId: String? = null,
    override var url: String? = null
) : BlockchainTxLog
