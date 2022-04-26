package com.rootsid.wal.agent.api

import com.ninjasquad.springmockk.MockkBean
import com.rootsid.wal.agent.api.request.connection.CreateInvitationRequest
import com.rootsid.wal.agent.service.ConnectionService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class ConnectionControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var connectionService: ConnectionService

    @Test
    fun givenNewConnectionInvite_whenPostRequest_thenReturnsUnhandledErrorWithStatus500() {
        every { connectionService.createConnection(any()).hint(CreateInvitationRequest::class) }
            .throws (Exception("dummy error"))

        mockMvc.perform(post("/connections/create-invitation", "{}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isInternalServerError)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message")
                .value("No handler found for POST /connections/create-invitation"))
            .andExpect(jsonPath("$.statusCode").value("500"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.timestampMillis").exists())
    }

    @Test
    fun givenBadUrl_whenGetRequest_thenReturnsNoHandlerFoundErrorWithStatus404() {
        every { connectionService.createConnection(any()).hint(CreateInvitationRequest::class) }
            .throws (Exception("dummy error"))

//        {
//            "statusCode": 404,
//            "message": "No handler found for POST /create-invitation1",
//            "timestamp": "2022-04-25T03:39:45.868274Z",
//            "timestampMillis": 1650857985868
//        }
        mockMvc.perform(get("/connections/create-invitation1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message")
                .value("No handler found for GET /connections/create-invitation1"))
            .andExpect(jsonPath("$.statusCode").value("404"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.timestampMillis").exists())
    }
}
