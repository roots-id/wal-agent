package com.rootsid.wal.agent.api.model.didcomm

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rootsid.wal.library.didcomm.model.UnpackResult
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.net.URI
import java.util.*

/**
 * @see <a href="https://identity.foundation/didcomm-messaging/spec/#plaintext-message-structure">Didcomm plaintext message structure</a>
 */
@Schema
data class PlainTextMessage(
    @Schema(description = "Message type", pattern = "(.*?)([a-z0-9._-]+)/(\\d[^/]*)/([a-z0-9._-]+)\$",
        example = "https://didcomm.org/lets_do_lunch/1.0/proposal")
    val type: URI,

    @Schema(
        description = "Message as text",
        example = "{\"msg\": \"Hi Alice\"}"
    )
    val body: String,

    @Schema(
        description = "Sender's did",
        example = "did:peer:2.Ez6LSi2DUKWessWhBACa8UBKjLSuPjyni3fjMgfrpCVDcErcf.Vz6MkgUkVUpzJ8KEqJTfyoKR8Xfe...",
        pattern = "^did:peer:([\\d+]).*"
    )
    val from: String,

    @Schema(
        description = "Prior sender's did. Used in the did rotation scenario. [Not Implemented]",
        example = "did:peer:2.Ez6LSi2DUKWessWhBACa8UBKjLSuPjyni3fjMgfrpCVDcErcf.Vz6MkgUkVUpzJ8KEqJTfyoKR8Xfe...",
        pattern = "^did:peer:([\\d+]).*"
    )
    @JsonProperty("from_prior")
    val fromPrior: String? = null,

    @ArraySchema(arraySchema = Schema(
        description = "List of receiver's did",
        example = "did:peer:2.Ez6LSi2DUKWessWhBACa8UBKjLSuPjyni3fjMgfrpCVDcErcf.Vz6MkgUkVUpzJ8KEqJTfyoKR8Xfe...",
        pattern = "^did:peer:([\\d+]).*"
    ))
    val to: MutableList<String>? = null,

    @Schema(
        description = "Thread identifier. Uniquely identifies the thread that the message belongs to. If not included, " +
                "the id property of the message MUST be treated as the value of the thid. See Threads for details.",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val thid: String? = null,

    @Schema(
        description = "Parent thread identifier. If the message is a child of a thread the pthid will uniquely identify " +
                "which thread is the parent. See Parent Threads for details.",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val pthid: String? = null,

    @Schema(
        description = "Message identifier",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val id: String = UUID.randomUUID().toString(),

    @Schema(description = "Time of record creation", example = "1664410889")
    @JsonProperty("created_time")
    val createdTime: Long? = null,

    @Schema(description = "Time of record last update", example = "1664410889")
    @JsonProperty("expires_time")
    val expiresTime: Long? = null,

    @ArraySchema(arraySchema = Schema(description = "Message attachments"))
    val attachments: List<Attachment>? = null
)

fun PlainTextMessage.toJson(mapper: ObjectMapper = jacksonObjectMapper()): String = mapper.writeValueAsString(this)

fun PlainTextMessage.isDidRotationMsg(): Boolean = (this.fromPrior?.isNotBlank() == true && this.from.isNotBlank())

fun PlainTextMessage.isInvitationResponseMsg(): Boolean = Piuris.INVITATION_RESPONSE.value == this.type

fun UnpackResult.toPlainTextMessage(): PlainTextMessage =
    jacksonObjectMapper().readValue(this.message, PlainTextMessage::class.java)

