package com.rootsid.wal.agent.api.request.wallet

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class CreateWalletRequest (
        @Schema(description = "Wallet identifier", example = "Bod's Wallet")
        var id: String,
        @Schema(description = "Mnemonic")
        var mnemonic: String,
        @Schema(description = "Passphrase")
        var passphrase: String,
)
