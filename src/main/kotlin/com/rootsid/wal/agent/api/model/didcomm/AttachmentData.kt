/**
 * faber.agent
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0.7.3
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package com.rootsid.wal.agent.api.model.didcomm

import io.swagger.v3.oas.annotations.media.Schema


/**
 * @see <a href="https://identity.foundation/didcomm-messaging/spec/#attachments">Didcomm messaging attachments</a>
 *
 * @param base64 Base64-encoded data
 * @param json JSON-serialized data
 * @param jws Detached Java Web Signature
 * @param links List of hypertext links to data
 * @param hash SHA256 hash (binhex encoded) of content
 */
@Schema
data class AttachmentData(
    @Schema(description = "Detached Java Web Signature")
    val jws: Any? = null,

    @Schema(
        description = "SHA256 hash (binhex encoded) of content",
        example = "617a48c7c8afe0521efdc03e5bb0ad9e655893e6b4b51f0e794d70fba132aacb",
        pattern = "^[a-fA-F0-9+/]{64}\$"
    )
    val hash: String? = null,

    @Schema(description = "List of hypertext links to data", example = "https://link.to/data")
    val links: List<String>? = null,

    @Schema(description = "Base64-encoded data", example = "ey4uLn0=", pattern = "^[a-zA-Z0-9+/]*={0,2}\$")
    val base64: String? = null,

    /* JSON-serialized data */
    @Schema(description = "JSON-serialized data", example = "{\"sample\": \"content\"}")
    val json: String? = null,
)

