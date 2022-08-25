package com.rootsid.wal.agent.persistence.model


import com.rootsid.wal.library.dlt.model.Did
import com.rootsid.wal.library.wallet.model.ImportedCredential
import com.rootsid.wal.library.wallet.model.IssuedCredential
import com.rootsid.wal.library.wallet.model.Wallet
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("wallets")
data class WalletEntity(
    @Id
    override val _id: String,
    override val seed: String,
    override var dids: MutableList<Did> = mutableListOf(),
    // List of imported (Issued elsewhere)
    override var importedCredentials: MutableList<ImportedCredential> = mutableListOf(),
    // List of credentials issued by a DID from this wallet
    override var issuedCredentials: MutableList<IssuedCredential> = mutableListOf()
) : Wallet
