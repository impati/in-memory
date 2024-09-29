package org.example.handler

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.example.domain.string.ReactiveStringRepository

class StringGetCommandHandler(
    private val stringRepository: ReactiveStringRepository
) : Handler {
    override suspend fun handle(call: ApplicationCall) {
        val key = call.parameters["key"] ?: throw IllegalArgumentException("key 값은 필수입니다.")
        val response = stringRepository.get(key).awaitSingleOrNull()
        call.respond(HttpStatusCode.OK, response!!)
    }
}
