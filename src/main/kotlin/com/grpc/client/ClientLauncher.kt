@file:JvmName("ClientLauncher")
package com.grpc.client

import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    Executors.newCachedThreadPool().asCoroutineDispatcher().use { dispatcher ->
        GRPCClient(
            ManagedChannelBuilder.forAddress(args[0], args[1].toInt()).usePlaintext(), dispatcher
        ).use { client ->
            println("Starting client on port ${args[1]}")
            client.start()
        }
    }
}