package org.example

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.example.domain.string.ReactiveStringRepository
import org.example.handler.*

fun main() {
    val repository = ReactiveStringRepository(mutableMapOf())
    val handlerMap = mutableMapOf<String, Pair<Handler, HttpMethod>>()
    handlerMap["/set"] = Pair(StringSetCommandHandler(repository), HttpMethod.Post)
    handlerMap["/multi-set"] = Pair(StringMultiSetCommandHandler(repository), HttpMethod.Post)
    handlerMap["/get"] = Pair(StringGetCommandHandler(repository), HttpMethod.Get)
    handlerMap["/multi-get"] = Pair(StringMultiGetCommandHandler(repository), HttpMethod.Get)

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }
        routing {
            handlerMap.forEach { (path, handlerAndMethod) ->
                val (handler, method) = handlerAndMethod
                when (method) {
                    HttpMethod.Post -> post(path) { handler.handle(call) }
                    HttpMethod.Get -> get(path) { handler.handle(call) }
                }
            }
        }
    }.start(wait = true)
}
