package org.example.handler

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.reactive.awaitSingle
import org.example.domain.string.StringCommand
import org.example.domain.string.StringCommandService
import org.example.domain.string.StringCommandValue

class StringMultiSetCommandHandler(
    private val stringCommandService: StringCommandService
) : Handler {
    override suspend fun handle(call: ApplicationCall) {
        val request = call.receive<List<KeyValueRequest>>()
        val commands = request.map {
            StringCommand(
                it.key ?: throw IllegalArgumentException("key 값은 필수입니다."),
                StringCommandValue(it.value ?: throw IllegalArgumentException("value 값은 필수입니다.")),
                it.option
            )
        }.toList()
        val response = stringCommandService.multiSet(commands).collectList().awaitSingle()
        call.respond(HttpStatusCode.OK, response!!)
    }
}
