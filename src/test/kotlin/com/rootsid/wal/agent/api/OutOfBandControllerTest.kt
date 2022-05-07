package com.rootsid.wal.agent.api

import com.ninjasquad.springmockk.MockkBean
import com.rootsid.wal.agent.service.ConnectionService
import com.rootsid.wal.agent.service.OutOfBandService
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(OutOfBandController::class)
class OutOfBandControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var outOfBandService: OutOfBandService

}
