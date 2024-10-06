package org.example.handler

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.reactive.awaitSingle
import org.example.domain.string.StringCommandService

class StringMultiGetCommandHandler(
    private val stringCommandService: StringCommandService
) : Handler {
    override suspend fun handle(call: ApplicationCall) {
        val key = call.parameters.getAll("key") ?: throw IllegalArgumentException("key 값은 필수입니다.")
        val response = stringCommandService.multiGet(key).collectList().awaitSingle()
        call.respond(HttpStatusCode.OK, response!!)
    }
}
