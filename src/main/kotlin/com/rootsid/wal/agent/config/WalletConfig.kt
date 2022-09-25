package com.rootsid.wal.agent.config


import com.rootsid.wal.agent.persistence.BlockchainTxLogDataProvider
import com.rootsid.wal.agent.persistence.WalletDataProvider
import com.rootsid.wal.library.dlt.Dlt
import com.rootsid.wal.library.wallet.WalletService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WalletConfig {
    @Bean
    fun walletService(
        walletDataProvider: WalletDataProvider,
        dltService: Dlt,
        blockchainTxLogDataProvider: BlockchainTxLogDataProvider
    ): WalletService =
        WalletService(walletStorage = walletDataProvider, dlt = dltService, txLogStorage = blockchainTxLogDataProvider)

    @Bean
    fun dltService(): Dlt = Dlt()
}
