package com.rootsid.wal.agent.service

import com.rootsid.wal.agent.api.request.outofband.InvitationCreateRequest
import com.rootsid.wal.agent.api.request.outofband.InvitationMessageRequest
import com.rootsid.wal.agent.api.response.outofband.ConnRecordResponse
import com.rootsid.wal.agent.api.response.outofband.InvitationRecordResponse
import com.rootsid.wal.agent.persistence.DidCommConnectionDataProvider
import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import com.rootsid.wal.library.didcomm.DIDPeer
import org.springframework.stereotype.Service
import java.util.*

@Service
class OutOfBandService(private val didPeer: DIDPeer, private val didCommConnectionDataProvider: DidCommConnectionDataProvider) {
    fun createInvitation(invitationCreateRequest: InvitationCreateRequest): InvitationRecordResponse {
        val newDidPeer = didPeer.create(invitationCreateRequest.serviceEndpoint)

        val inserted = didCommConnectionDataProvider.insert(DidCommConnectionEntity(invitationCreateRequest.alias, newDidPeer))

        println(didCommConnectionDataProvider.findById(inserted._id))

        return InvitationRecordResponse("dummy", null, "url")
    }

    fun createInvitation(invitationMessageRequest: InvitationMessageRequest): ConnRecordResponse {
        return ConnRecordResponse(null, null, UUID.randomUUID())
    }
}
