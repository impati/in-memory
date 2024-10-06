package org.example.handler

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.example.domain.string.StringCommandService

class StringIncrCommandHandler(
    private val stringCommandService: StringCommandService
) : Handler {
    override suspend fun handle(call: ApplicationCall) {
        val key = call.parameters["key"] ?: throw IllegalArgumentException("key 값은 필수입니다.")
        call.parameters["by"]?.let {
            val response = stringCommandService.incr(key, it.toInt()).awaitSingleOrNull()
            call.respond(HttpStatusCode.OK, response!!)
        }
        val response = stringCommandService.incr(key).awaitSingleOrNull()
        call.respond(HttpStatusCode.OK, response!!)
    }
}
