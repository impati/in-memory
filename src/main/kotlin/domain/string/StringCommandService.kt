package org.example.domain.string

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class StringCommandService(
    private val reactiveStringRepository: StringRepository
) {

    fun incr(key: String): Mono<String> {
        return reactiveStringRepository.get(key)
            .switchIfEmpty(Mono.just(StringValue.initializeIntegerValue()))
            .flatMap { reactiveStringRepository.set(StringCommand(key, it.incr())) }
            .map { it.value }
    }

    fun decr(key: String): Mono<String> {
        return reactiveStringRepository.get(key)
            .switchIfEmpty(Mono.just(StringValue.initializeIntegerValue()))
            .flatMap { reactiveStringRepository.set(StringCommand(key, it.decr())) }
            .map { it.value }
    }

    fun incr(key: String, by: Int): Mono<String> {
        return reactiveStringRepository.get(key)
            .switchIfEmpty(Mono.just(StringValue.initializeIntegerValue()))
            .flatMap { reactiveStringRepository.set(StringCommand(key, it.plus(by))) }
            .map { it.value }
    }

    fun decr(key: String, by: Int): Mono<String> {
        return reactiveStringRepository.get(key)
            .switchIfEmpty(Mono.just(StringValue.initializeIntegerValue()))
            .flatMap { reactiveStringRepository.set(StringCommand(key, it.minus(by))) }
            .map { it.value }
    }

    fun set(command: StringCommand): Mono<String> {
        return reactiveStringRepository.set(command)
            .mapNotNull { it.value }
            .switchIfEmpty(Mono.just("err"))
    }

    fun multiSet(commands: List<StringCommand>): Flux<String> {
        return reactiveStringRepository.multiSet(commands)
            .mapNotNull { it.value }
            .switchIfEmpty(Flux.just("err"))
    }

    fun get(key: String): Mono<String> {
        return reactiveStringRepository.get(key)
            .mapNotNull { it.value }
            .switchIfEmpty(Mono.just("nil"))
    }

    fun multiGet(keys: List<String>): Flux<String> {
        return reactiveStringRepository.multiGet(keys)
            .switchIfEmpty(Flux.just(StringValue("nil")))
            .mapNotNull { it.value }
    }
}
