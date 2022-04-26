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
package com.rootsid.wal.agent.api.model.outofband

/**
 * 
 * @param header 
 * @param &#x60;protected&#x60; protected JWS header
 * @param signature signature
 */
data class AttachDecoratorData1JWS (
    val header: AttachDecoratorDataJWSHeader,
    /* signature */
    val signature: String,
    /* protected JWS header */
    val `protected`: String? = null
) {

}

