package com.rootsid.wal.agent.dto

import com.rootsid.wal.library.dlt.model.Did
import com.rootsid.wal.library.dlt.model.KeyPath
import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class DidDto(
    val alias: String,
    val didIdx: Int,
    val uriCanonical: String,
    val uriLongForm: String,
    var keyPaths: MutableList<KeyPath> = mutableListOf(),
    var operationHash: String? = null,
    var publishedStatus: Any? = null,
    var publishedOperationId: String? = null
)

fun Did.convert() = DidDto(
    alias = alias,
    didIdx = didIdx,
    uriCanonical = uriCanonical,
    uriLongForm = uriLongForm,
    keyPaths = keyPaths,
    operationHash = operationHash,
    publishedStatus = publishedStatus,
    publishedOperationId = publishedOperationId,
)

fun DidDto.convert() = Did(
    alias = alias,
    didIdx = didIdx,
    uriCanonical = uriCanonical,
    uriLongForm = uriLongForm,
    keyPaths = keyPaths,
)

