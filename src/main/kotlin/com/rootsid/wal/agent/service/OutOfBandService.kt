package com.rootsid.wal.agent.service

import com.rootsid.wal.agent.api.request.outofband.InvitationCreateRequest
import com.rootsid.wal.agent.api.request.outofband.InvitationMessageRequest
import com.rootsid.wal.agent.api.response.outofband.ConnRecordResponse
import com.rootsid.wal.agent.api.response.outofband.InvitationRecordResponse
import com.rootsid.wal.library.didcom.DIDPeer
import org.springframework.stereotype.Service
import java.util.*

@Service
class OutOfBandService(private val didPeer: DIDPeer) {
    fun createInvitation(invitationCreateRequest: InvitationCreateRequest): InvitationRecordResponse {
        // TODO: more to come
        didPeer.create(1, 1, invitationCreateRequest.serviceEndpoint,
            mutableListOf("123123", "324234324"))

        return InvitationRecordResponse("dummy", null, "url")
    }

    fun createInvitation(invitationMessageRequest: InvitationMessageRequest): ConnRecordResponse {
        return ConnRecordResponse(null, null, UUID.randomUUID())
    }
}
