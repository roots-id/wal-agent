package com.rootsid.wal.agent.config.properties


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "application.didcomm.oob")
data class OutOfBandProperties(
    var invitationUrl: String = ""
)
