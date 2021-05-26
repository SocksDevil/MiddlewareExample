@file:JvmName("ServerLauncher")
package com.grpc.server

fun main(args: Array<String>) {
    val server = GRPCServer(args[0].toInt())

    server.start()
    server.blockAndAwaitTermination()
}