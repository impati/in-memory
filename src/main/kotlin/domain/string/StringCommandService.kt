package org.example.domain.string

import reactor.core.publisher.Mono

class StringCommandService(
    private val reactiveStringRepository: ReactiveStringRepository
) {

    fun incr(key: String): Mono<String> {
        return reactiveStringRepository.get(key)
            .switchIfEmpty(Mono.just(StringCommandValue.initializeIntegerValue()))
            .flatMap { reactiveStringRepository.set(StringCommand(key, it!!.incr())) }
            .map { it!!.value }
    }

    fun decr(key: String): Mono<String> {
        return reactiveStringRepository.get(key)
            .switchIfEmpty(Mono.just(StringCommandValue.initializeIntegerValue()))
            .flatMap { reactiveStringRepository.set(StringCommand(key, it!!.decr())) }
            .map { it!!.value }
    }

    fun incr(key: String, by: Int): Mono<String> {
        return reactiveStringRepository.get(key)
            .switchIfEmpty(Mono.just(StringCommandValue.initializeIntegerValue()))
            .flatMap { reactiveStringRepository.set(StringCommand(key, it!!.plus(by))) }
            .map { it!!.value }
    }

    fun decr(key: String, by: Int): Mono<String> {
        return reactiveStringRepository.get(key)
            .switchIfEmpty(Mono.just(StringCommandValue.initializeIntegerValue()))
            .flatMap { reactiveStringRepository.set(StringCommand(key, it!!.minus(by))) }
            .map { it!!.value }
    }
}
