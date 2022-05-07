package com.rootsid.wal.agent.api

import com.ninjasquad.springmockk.MockkBean
import com.rootsid.wal.agent.api.request.connection.CreateInvitationRequest
import com.rootsid.wal.agent.api.response.connection.InvitationResponse
import com.rootsid.wal.agent.service.ConnectionService
import io.mockk.every
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(ConnectionController::class)
class ConnectionControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var connectionService: ConnectionService

    @Test
    fun `given an empty request body when create connection then returns error with status 400`() {
        every { connectionService.createConnection(any()).hint(CreateInvitationRequest::class) }
            .throws (Exception("dummy error"))

        mockMvc.perform(post("/connections/create-invitation", "{}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message")
                .value(containsString("Required request body is missing")))
            .andExpect(jsonPath("$.statusCode").value("400"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.timestampMillis").exists())
    }

    @Test
    fun `given bad url when get request then returns a NoHandlerFoundError with status 404`() {
        every { connectionService.createConnection(any()) }
            .returns(InvitationResponse(UUID.randomUUID().toString(), null,
                "http://192.168.56.101:8020/invite?c_i=eyJAdHlwZSI6Li4ufQ=="))

//        {
//            "statusCode": 404,
//            "message": "No handler found for GET /connections1/create-invitation",
//            "timestamp": "2022-04-25T03:39:45.868274Z",
//            "timestampMillis": 1650857985868
//        }
        mockMvc.perform(get("/connections1/create-invitation"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message")
                .value("No handler found for GET /connections1/create-invitation"))
            .andExpect(jsonPath("$.statusCode").value("404"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.timestampMillis").exists())
    }
}
