package com.rootsid.wal.agent.api

import com.rootsid.wal.agent.api.request.connection.CreateInvitationRequest
import com.rootsid.wal.agent.api.response.ErrorResponse
import com.rootsid.wal.agent.api.response.connection.ConnRecordResponse
import com.rootsid.wal.agent.api.response.connection.InvitationResponse
import com.rootsid.wal.agent.service.ConnectionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RequestMapping("/connections")
@RestController
class ConnectionController(private val connectionService: ConnectionService) {

    @Deprecated("Replaced by out-of-band")
    @Operation(summary = "Create a new connection invitation", tags = ["connections"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PostMapping("/create-invitation")
    fun createConnection(@RequestBody payload: CreateInvitationRequest): InvitationResponse = connectionService.createConnection(payload)

    @Operation(summary = "Fetch a single connection record", tags = ["connections"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @Parameter
    @GetMapping("/{connId}")
    fun getConnection(@Parameter(required = true, description = "Connection identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                      @PathVariable connId: String):
            ConnRecordResponse = connectionService.getConnection(connId)

    @Operation(summary = "Remove an existing connection record", tags = ["connections"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @DeleteMapping("/{connId}")
    fun deleteConnection(@Parameter(required = true, description = "Connection identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                         @PathVariable connId: String) = connectionService.deleteConnection(connId)
}
