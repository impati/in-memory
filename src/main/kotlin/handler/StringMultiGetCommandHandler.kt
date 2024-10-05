package org.example.handler

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.reactive.awaitSingle
import org.example.domain.string.ReactiveStringRepository

class StringMultiGetCommandHandler(
    private val stringRepository: ReactiveStringRepository
) : Handler {
    override suspend fun handle(call: ApplicationCall) {
        val key = call.parameters.getAll("key") ?: throw IllegalArgumentException("key 값은 필수입니다.")
        val response = stringRepository.multiGet(key).collectList().awaitSingle()
        call.respond(HttpStatusCode.OK, response!!)
    }
}
