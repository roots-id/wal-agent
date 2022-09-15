package com.rootsid.wal.agent.config


import com.rootsid.wal.agent.persistence.DidComSecretDataProvider
import com.rootsid.wal.library.didcom.DIDPeer
import com.rootsid.wal.library.didcom.storage.SecretResolverCustom
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OutOfBandConfig {
    @Bean
    fun didPeer(didComSecretDataProvider: DidComSecretDataProvider): DIDPeer =
        DIDPeer(secretResolverAgent(didComSecretDataProvider))

    @Bean
    fun secretResolverAgent(didComSecretDataProvider: DidComSecretDataProvider): SecretResolverCustom =
        SecretResolverCustom(didComSecretDataProvider)
}
