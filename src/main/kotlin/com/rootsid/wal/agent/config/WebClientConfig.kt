package com.rootsid.wal.agent.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient = WebClient.builder()
        .clientConnector(
            ReactorClientHttpConnector(
                HttpClient
                    .create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .doOnConnected { connection: Connection ->
                        connection.addHandlerLast(ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS))
                        connection.addHandlerLast(WriteTimeoutHandler(10000, TimeUnit.MILLISECONDS))
                    })
        )
        .build()
}
