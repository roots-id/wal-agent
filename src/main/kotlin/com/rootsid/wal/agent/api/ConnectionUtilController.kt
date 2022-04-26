package com.rootsid.wal.agent.api

import com.rootsid.wal.agent.api.request.connection.PingRequest
import com.rootsid.wal.agent.api.request.connection.SendMessageRequest
import com.rootsid.wal.agent.api.response.ErrorResponse
import com.rootsid.wal.agent.api.response.connection.BasicMessageModuleResponse
import com.rootsid.wal.agent.api.response.connection.PingResponse
import com.rootsid.wal.agent.service.ConnectionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ConnectionUtilController(private val connectionService: ConnectionService) {
    @Operation(summary = "Send a basic message to a connection", tags = ["basicmessage"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PostMapping("/connections/{connId}/send-message")
    fun sendMessage(@RequestBody payload: SendMessageRequest): BasicMessageModuleResponse = connectionService.sendPing(payload)

    @Operation(summary = "Send a trust ping to a connection", tags = ["pingtrust"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PostMapping("/connections/{connId}/send-ping")
    fun sendPing(@PathVariable connId: String, @RequestBody payload: PingRequest): PingResponse = connectionService.sendPing(payload)
}
