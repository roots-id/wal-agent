package com.rootsid.wal.agent.config


import com.rootsid.wal.agent.persistence.DidCommSecretDataProvider
import com.rootsid.wal.library.didcomm.DIDPeer
import com.rootsid.wal.library.didcomm.storage.SecretResolverCustom
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OutOfBandConfig {
    @Bean
    fun didPeer(didCommSecretDataProvider: DidCommSecretDataProvider): DIDPeer =
        DIDPeer(secretResolverAgent(didCommSecretDataProvider))

    @Bean
    fun secretResolverAgent(didCommSecretDataProvider: DidCommSecretDataProvider): SecretResolverCustom =
        SecretResolverCustom(didCommSecretDataProvider)
}
