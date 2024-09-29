package org.example.handler

import io.ktor.server.application.*

interface Handler {

    suspend fun handle(call: ApplicationCall)
}
