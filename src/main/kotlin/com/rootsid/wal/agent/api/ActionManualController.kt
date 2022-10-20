package com.rootsid.wal.agent.api

import com.rootsid.wal.agent.api.request.action.ReceiveMessageRequest
import com.rootsid.wal.agent.api.request.action.SendDidRotationMessageRequest
import com.rootsid.wal.agent.api.request.action.SendMessageRequest
import com.rootsid.wal.agent.api.response.ErrorResponse
import com.rootsid.wal.agent.api.response.action.ReceiveMessageResponse
import com.rootsid.wal.agent.api.response.action.SendDidRotationMessageResponse
import com.rootsid.wal.agent.api.response.action.SendMessageResponse
import com.rootsid.wal.agent.service.ActionManualService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="actions-manual")
@RequestMapping("/manual-actions")
@RestController
class ActionManualController(private val actionManualService: ActionManualService) {

    @Operation(summary = "Send a basic message to a connection")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(
                responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PostMapping("/send-message")
    fun sendMessage(@RequestBody payload: SendMessageRequest): SendMessageResponse =
        actionManualService.sendMessage(payload)

    @Operation(summary = "Send a did rotation message to a connection")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(
                responseCode = "404", description = "The resource not found", useReturnTypeSchema = true,
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PostMapping("/send-did-rotation-message")
    fun sendDidRotationMessage(@RequestBody payload: SendDidRotationMessageRequest): SendDidRotationMessageResponse =
        actionManualService.sendDidRotationMessage(payload)

    @Operation(summary = "Receive packed message")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found")]
    )
    @PostMapping("/receive-message")
    fun receiveInvitation(@RequestBody payload: ReceiveMessageRequest): ReceiveMessageResponse =
        actionManualService.receiveMessage(payload)
}
