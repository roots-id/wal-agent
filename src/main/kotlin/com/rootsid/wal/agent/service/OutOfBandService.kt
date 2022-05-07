package com.rootsid.wal.agent.service

import com.rootsid.wal.agent.api.request.outofband.InvitationCreateRequest
import com.rootsid.wal.agent.api.request.outofband.InvitationMessageRequest
import com.rootsid.wal.agent.api.response.outofband.ConnRecordResponse
import com.rootsid.wal.agent.api.response.outofband.InvitationRecordResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class OutOfBandService {
    fun createInvitation(invitationCreateRequest: InvitationCreateRequest): InvitationRecordResponse {
        return InvitationRecordResponse("dummy", null, "url")
    }

    fun createInvitation(invitationMessageRequest: InvitationMessageRequest): ConnRecordResponse {
        return ConnRecordResponse(null, null, UUID.randomUUID())
    }
}
