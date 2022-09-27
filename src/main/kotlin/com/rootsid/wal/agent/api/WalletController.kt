package com.rootsid.wal.agent.api

import com.rootsid.wal.agent.api.request.wallet.CreateWalletRequest
import com.rootsid.wal.agent.dto.DidDto
import com.rootsid.wal.agent.dto.WalletDto
import com.rootsid.wal.agent.dto.convert
import com.rootsid.wal.library.wallet.WalletService
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name="wallets", description = "Interaction with wallet",
    externalDocs = ExternalDocumentation(description = "Specification",
        url = "")
)
@RequestMapping("/wallets")
@RestController
class WalletController(private val walletService: WalletService) {
    @Operation(summary = "Create a new wallet")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Created"),
            ApiResponse(responseCode = "404", description = "The resource not found")]
    )
    @PostMapping
    fun create(@RequestBody payload: CreateWalletRequest): WalletDto =
        walletService.createWallet(payload.id, payload.mnemonic, payload.passphrase).convert()

    @Operation(summary = "Receive a new connection invitation")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found")]
    )
    @GetMapping("/{id}")
    fun get(@PathVariable id: String): WalletDto =
        walletService.findWalletById(id).convert()

    @Operation(summary = "Receive a new connection invitation")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found")]
    )
    @GetMapping
    fun list(): List<WalletDto> = walletService.listWallets().convert()

    @Operation(summary = "Retrieve wallet did")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found")]
    )
    @GetMapping("/{id}/dids/{alias}")
    fun findWalletDid(@PathVariable id: String, @PathVariable alias: String): DidDto =
        walletService.findDid(id, alias).convert()

}
