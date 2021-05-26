package com.proto.serializers

import com.proto.models.Colors
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.jvm.jvmErasure

object ColorsSerializer : ContentConverter {
    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        val channel = request.value as? ByteReadChannel ?: return null
        val type = request.typeInfo
        val javaType = type.jvmErasure

        return withContext(Dispatchers.IO) {
            when (javaType) {
                Colors.Color::class -> Colors.Color.parseFrom(channel.toInputStream())
                Colors.ColorConversionRequest::class -> Colors.ColorConversionRequest.parseFrom(channel.toInputStream())
                Colors.ColorConversionResponse::class -> Colors.ColorConversionResponse.parseFrom(channel.toInputStream())
                Colors.ColorPaletteRequest::class -> Colors.ColorPaletteRequest.parseFrom(channel.toInputStream())
                Colors.ColorPaletteResponse::class -> Colors.ColorPaletteResponse.parseFrom(channel.toInputStream())
                else -> null
            }
        }
    }

    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any? {
        if (!contentType.match(ContentType.Application.ProtoBuf))
            return null

        return when (value) {
            is Colors.Color -> (value as? Colors.Color)?.toByteArray()
            is Colors.ColorConversionRequest -> (value as? Colors.ColorConversionRequest)?.toByteArray()
            is Colors.ColorConversionResponse -> (value as? Colors.ColorConversionResponse)?.toByteArray()
            is Colors.ColorPaletteRequest -> (value as? Colors.ColorPaletteRequest)?.toByteArray()
            is Colors.ColorPaletteResponse -> (value as? Colors.ColorPaletteResponse)?.toByteArray()
            else -> null
        }
    }
}
