package com.rootsid.wal.agent.persistence.model

import com.rootsid.wal.library.didcomm.model.DidCommSecret
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("didcomm_secrets")
data class DidCommSecretEntity(
    @Id
    override val _id: String,
    override val secret: Map<String, Any> = mutableMapOf()
) : DidCommSecret
