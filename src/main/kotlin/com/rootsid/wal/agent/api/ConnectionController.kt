package com.rootsid.wal.agent.api

import com.rootsid.wal.agent.api.response.ErrorResponse
import com.rootsid.wal.agent.dto.ConnectionDto
import com.rootsid.wal.agent.service.ConnectionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name="connections")
@RequestMapping("/connections")
@RestController
class ConnectionController(private val connectionService: ConnectionService) {
    @Operation(summary = "Remove an existing connection record")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(
                responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteConnection(
        @Parameter(
            required = true,
            description = "Connection identifier",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
        @PathVariable id: String
    ) = connectionService.deleteConnection(id)

    @Operation(summary = "Fetch a single connection record")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(
                responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @Parameter
    @GetMapping("/{id}")
    fun getConnection(
        @Parameter(
            required = true,
            description = "Connection identifier",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
        @PathVariable id: String
    ):
            ConnectionDto = connectionService.getConnection(id)

    @Operation(summary = "Fetch available connection records")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK")
        ]
    )
    @Parameter
    @GetMapping
    fun listConnections(): List<ConnectionDto> = connectionService.listConnections()
}
