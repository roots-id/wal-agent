package com.rootsid.wal.agent.persistence.model

import com.rootsid.wal.library.didcom.model.DidComSecret
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("didcom_secrets")
data class DidComSecretEntity(
    @Id
    override val _id: String,
    override val secret: Map<String, Any> = mutableMapOf()
) : DidComSecret
