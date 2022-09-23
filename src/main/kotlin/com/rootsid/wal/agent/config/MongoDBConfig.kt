package com.rootsid.wal.agent.config

import com.rootsid.wal.library.didcomm.common.DidCommDataTypes
import com.rootsid.wal.library.didcomm.common.StorageRepresentable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions


@Configuration
class MongoDBConfig {
    @Bean
    fun mongoCustomConversions(): MongoCustomConversions {
        return MongoCustomConversions(
            listOf(
                ConnectionStatusReadConverter(),
                TheirRoleReadConverter(),
                InvitationModeReadConverter(),
                ConnectionProtocolReadConverter(),
                RoutingStateReadConverter(),
                AcceptReadConverter(),
                StorageRepresentableWriteConverter()
            )
        )
    }
}

@WritingConverter
class StorageRepresentableWriteConverter : Converter<StorageRepresentable<String>?, String?> {
    override fun convert(source: StorageRepresentable<String>): String? {
        return source.value
    }
}

@ReadingConverter
class ConnectionStatusReadConverter : Converter<String?, DidCommDataTypes.ConnectionState?> {
    override fun convert(source: String): DidCommDataTypes.ConnectionState? =
        DidCommDataTypes.findByStorageRepresentation(source)
}

@ReadingConverter
class TheirRoleReadConverter : Converter<String?, DidCommDataTypes.TheirRole?> {
    override fun convert(source: String): DidCommDataTypes.TheirRole? =
        DidCommDataTypes.findByStorageRepresentation(source)
}

@ReadingConverter
class InvitationModeReadConverter : Converter<String?, DidCommDataTypes.InvitationMode?> {
    override fun convert(source: String): DidCommDataTypes.InvitationMode? =
        DidCommDataTypes.findByStorageRepresentation(source)
}

@ReadingConverter
class ConnectionProtocolReadConverter : Converter<String?, DidCommDataTypes.ConnectionProtocol?> {
    override fun convert(source: String): DidCommDataTypes.ConnectionProtocol? =
        DidCommDataTypes.findByStorageRepresentation(source)
}

@ReadingConverter
class RoutingStateReadConverter : Converter<String?, DidCommDataTypes.RoutingState?> {
    override fun convert(source: String): DidCommDataTypes.RoutingState? =
        DidCommDataTypes.findByStorageRepresentation(source)
}

@ReadingConverter
class AcceptReadConverter : Converter<String?, DidCommDataTypes.Accept?> {
    override fun convert(source: String): DidCommDataTypes.Accept? =
        DidCommDataTypes.findByStorageRepresentation(source)
}
