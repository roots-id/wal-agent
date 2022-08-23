package com.rootsid.wal.agent.config


import com.rootsid.wal.agent.persistence.WalletDataProvider
import com.rootsid.wal.library.dlt.Dlt
import com.rootsid.wal.library.wallet.WalletService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WalletConfig {
    @Bean
    fun walletService(walletDataProvider: WalletDataProvider, dltService: Dlt): WalletService {
        return WalletService(walletDataProvider, dltService)
    }

    @Bean
    fun dltService(): Dlt {
        return Dlt()
    }
}
