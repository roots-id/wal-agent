package com.rootsid.wal.agent.service

import com.rootsid.wal.agent.api.request.connection.CreateInvitationRequest
import com.rootsid.wal.agent.api.request.connection.PingRequest
import com.rootsid.wal.agent.api.request.connection.SendMessageRequest
import com.rootsid.wal.agent.api.response.connection.BasicMessageModuleResponse
import com.rootsid.wal.agent.api.response.connection.ConnRecordResponse
import com.rootsid.wal.agent.api.response.connection.InvitationResponse
import com.rootsid.wal.agent.api.response.connection.PingResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class ConnectionService {
    fun createConnection(createInvitationRequest: CreateInvitationRequest): InvitationResponse {
        return InvitationResponse("dummy", null, "url")
    }

    fun getConnection(connId: String): ConnRecordResponse {
        return ConnRecordResponse(null, "alias", UUID.randomUUID())
    }

    fun deleteConnection(connId: String) {

    }

    fun sendPing(payload: SendMessageRequest): BasicMessageModuleResponse {
        return BasicMessageModuleResponse()
    }

    fun sendPing(payload: PingRequest): PingResponse {
        return PingResponse()
    }
}
