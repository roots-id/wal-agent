package com.rootsid.wal.agent.dto

import com.rootsid.wal.library.dlt.model.Did
import com.rootsid.wal.library.wallet.model.ImportedCredential
import com.rootsid.wal.library.wallet.model.IssuedCredential
import com.rootsid.wal.library.wallet.model.Wallet
import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class WalletDto (
    var id: String,
    var dids: MutableList<Did>? = mutableListOf(),
    // List of imported (Issued elsewhere)
    var importedCredentials: MutableList<ImportedCredential>? = mutableListOf(),
    // List of credentials issued by a DID from this wallet
    var issuedCredentials: MutableList<IssuedCredential>? = mutableListOf()
)

fun Wallet.convert() = WalletDto(
    id = _id,
    dids = dids,
    importedCredentials = importedCredentials,
    issuedCredentials = issuedCredentials,
)

