@file:JvmName("ProtobufApplication")
package com.proto

import com.proto.routes.registerCMYKRoutes
import com.proto.routes.registerHSVRoutes
import com.proto.routes.registerHexRoutes
import com.proto.routes.registerRGBRoutes
import com.proto.serializers.ColorsSerializer
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit {
    start(args[0].toInt())
}

fun start(port: Int): NettyApplicationEngine {
    return embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            register(ContentType.Application.ProtoBuf, ColorsSerializer)
        }

        routing {
            get("/") {
                call.respondText("Hello, world!")
            }
        }

        registerRGBRoutes()
        registerCMYKRoutes()
        registerHSVRoutes()
        registerHexRoutes()
    }.start(wait = true)
}
