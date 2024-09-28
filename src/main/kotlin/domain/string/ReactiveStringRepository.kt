package org.example.domain.string

import org.example.domain.Operator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

class ReactiveStringRepository(
    private val repository: MutableMap<String, String>
) {


    fun set(command: StringCommand): Mono<String?> {
        return Mono.fromCallable {
            val previous = repository[command.key]
            when {
                command.operator == Operator.UPDATE_ONLY && Objects.isNull(previous) -> null
                command.operator == Operator.INSERT_ONLY && Objects.nonNull(previous) -> null
                else -> {
                    repository[command.key] = command.value
                    command.value
                }
            }
        }
    }

    fun multiSet(commands: List<StringCommand>): Flux<String?> {
        return Flux.fromIterable(commands).flatMap { set(it) }
    }

    fun get(key: String): Mono<String?> {
        return Mono.fromCallable { repository[key] }
    }

    fun multiGet(keys: List<String>): Flux<String?> {
        return Flux.fromIterable(keys).flatMap { get(it) }
    }
}
