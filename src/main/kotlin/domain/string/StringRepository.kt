package org.example.domain.string

import org.example.domain.Operator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

class StringRepository(
    private val repository: MutableMap<String, StringCommandValue>
) {


    fun set(command: StringCommand): Mono<StringCommandValue> {
        return Mono.defer {
            val previous = repository[command.key]
            when {
                command.operator == Operator.UPDATE_ONLY && Objects.isNull(previous) -> Mono.empty()
                command.operator == Operator.INSERT_ONLY && Objects.nonNull(previous) -> Mono.empty()
                else -> {
                    repository[command.key] = command.value
                    Mono.just(command.value)
                }
            }
        }
    }

    fun multiSet(commands: List<StringCommand>): Flux<StringCommandValue> {
        return Flux.fromIterable(commands).flatMap { set(it) }
    }

    fun get(key: String): Mono<StringCommandValue> {
        return Mono.justOrEmpty(repository[key])
    }

    fun multiGet(keys: List<String>): Flux<StringCommandValue> {
        return Flux.fromIterable(keys).flatMap { get(it) }
    }
}
