package org.example.handler

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.example.domain.string.StringCommand
import org.example.domain.string.StringCommandService
import org.example.domain.string.StringCommandValue

class StringSetCommandHandler(
    private val stringCommandService: StringCommandService
) : Handler {
    override suspend fun handle(call: ApplicationCall) {
        val request = call.receive<KeyValueRequest>()
        val key = request.key ?: throw IllegalArgumentException("key 값은 필수 입니다.")
        val value = request.value ?: throw IllegalArgumentException("value 값은 필수 입니다.")
        val option = request.option
        val command = StringCommand(key, StringCommandValue(value), option)
        val response = stringCommandService.set(command).awaitSingleOrNull()
        call.respond(HttpStatusCode.OK, response!!)
    }
}
