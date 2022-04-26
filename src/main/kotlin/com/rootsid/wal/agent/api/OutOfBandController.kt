package com.rootsid.wal.agent.api

import com.rootsid.wal.agent.api.request.outofband.InvitationCreateRequest
import com.rootsid.wal.agent.api.request.outofband.InvitationMessageRequest
import com.rootsid.wal.agent.api.response.outofband.ConnRecordResponse
import com.rootsid.wal.agent.api.response.outofband.InvitationRecordResponse
import com.rootsid.wal.agent.service.OutOfBandService
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="out-of-band", description = "Out-of-band connections",
    externalDocs = ExternalDocumentation(description = "Specification",
        url = "https://github.com/hyperledger/aries-rfcs/tree/2da7fc4ee043effa3a9960150e7ba8c9a4628b68/features/0434-outofband")
)
@RequestMapping("/out-of-band")
@RestController
class OutOfBandController(private val outOfBandService: OutOfBandService) {
    @Operation(summary = "Create a new connection invitation")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found")]
    )
    @PostMapping("/create-invitation")
    fun createInvitation(@RequestBody payload: InvitationCreateRequest): InvitationRecordResponse =
        outOfBandService.createInvitation(payload)

    @Operation(summary = "Receive a new connection invitation")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "The resource not found")]
    )
    @PostMapping("/receive-invitation")
    fun receiveInvitation(@RequestBody payload: InvitationMessageRequest): ConnRecordResponse =
        outOfBandService.createInvitation(payload)
}
