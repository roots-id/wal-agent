package com.rootsid.wal.agent.config


import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.tags.Tag
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("wal-agent-public")
            .packagesToScan("com.rootsid.wal.agent.api")
            .build()
    }

    @Bean
    fun agentInfoOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info()
                    .title("Wal-Agent Api")
                    .description("Spring shop sample application")
                    .version("1.0.0")
                    .license(License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
            )
            .tags(getProjectTagList())
            .externalDocs(
                ExternalDocumentation()
                    .description("SpringShop Wiki Documentation")
                    .url("https://springshop.wiki.github.org/docs")
            )
    }

    fun getProjectTagList(): List<Tag> {
        // Basic Message
        val basicmessageTag = Tag().name("basicmessage").description("Simple messaging")
            .externalDocs(ExternalDocumentation().description("Specification")
                .url("https://github.com/hyperledger/aries-rfcs/tree/527849ec3aa2a8fd47a7bb6c57f918ff8bcb5e8c/features/0095-basic-message"))

        // Ping Trust
        val pingtrustTag = Tag().name("pingtrust").description("Trust-ping over connection")
            .externalDocs(ExternalDocumentation().description("Specification")
                .url("https://github.com/hyperledger/aries-rfcs/tree/527849ec3aa2a8fd47a7bb6c57f918ff8bcb5e8c/features/0048-trust-ping"))
        
        // Connections
        val connectionsTag = Tag().name("connections").description("Connection management")
            .externalDocs(ExternalDocumentation().description("Specification")
                .url("https://github.com/hyperledger/aries-rfcs/tree/9b0aaa39df7e8bd434126c4b33c097aae78d65bf/features/0160-connection-protocol"))

        return listOf(connectionsTag, basicmessageTag, pingtrustTag)
    }
}
