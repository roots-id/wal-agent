package com.rootsid.wal.agent.api

import com.rootsid.wal.agent.api.request.action.ReceiveMessageRequest
import com.rootsid.wal.agent.api.request.action.SendDidRotationMessageRequest
import com.rootsid.wal.agent.api.request.action.SendMessageRequest
import com.rootsid.wal.agent.api.response.ErrorResponse
import com.rootsid.wal.agent.service.ActionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name="actions")
@RequestMapping("/actions")
@RestController
class ActionController(private val actionService: ActionService) {

    @Operation(summary = "Send a basic message to a connection")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "OK"),
            ApiResponse(
                responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping("/send-message")
    fun sendMessage(@RequestBody payload: SendMessageRequest) {
        actionService.sendMessage(payload)
    }

    @Operation(summary = "Send a did rotation message to a connection")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "OK"),
            ApiResponse(
                responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping("/send-did-rotation-message")
    fun sendDidRotationMessage(@RequestBody payload: SendDidRotationMessageRequest) {
        actionService.sendDidRotationMessage(payload)
    }

    @Operation(summary = "Receive packed message")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found")]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping("/receive-message")
    fun receiveInvitation(@RequestBody payload: ReceiveMessageRequest) {
        actionService.receiveMessage(payload)
    }
}
