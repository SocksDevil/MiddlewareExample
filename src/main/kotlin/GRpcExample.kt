@file:JvmName("GRpcExample")

package org.feup.lapd.middleware

import com.google.protobuf.MessageOrBuilder
import com.grpc.models.ColorsServiceGrpcKt
import org.feup.lapd.middleware.builder.server.MiddlewareServer
import org.feup.lapd.middleware.builder.server.requests.GRpcRequestBuilder
import org.feup.lapd.middleware.builder.strategies.GRpcStrategy


fun main() {
    MiddlewareServer.Builder<GRpcStrategy.ParamsData, MessageOrBuilder>()
        .portToServe(8889)
        .serverUrl("localhost:8808")
        .setStrategy(GRpcStrategy("localhost", 8808))
        .addRequestBuilder(GRpcRequestBuilder(ColorsServiceGrpcKt.serviceDescriptor))
        .build()
        .start()
}