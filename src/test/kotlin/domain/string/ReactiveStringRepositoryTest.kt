package domain.string

import org.example.domain.string.ReactiveStringRepository
import org.example.domain.string.StringCommand
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class ReactiveStringRepositoryTest {

    private val reactiveStringRepository = ReactiveStringRepository(mutableMapOf())

    @Test
    fun multiSet() {
        val command = listOf(StringCommand("hello", "world"))

        StepVerifier.create(reactiveStringRepository.multiSet(command))
            .expectNext("world")
            .verifyComplete()
    }

    @Test
    fun multiGet() {
        val command = listOf(StringCommand("hello", "world"), StringCommand("hi", "hello"))
        reactiveStringRepository.multiSet(command).subscribe()

        StepVerifier.create(reactiveStringRepository.multiGet(listOf("hello", "hi")))
            .expectNext("world", "hello")
            .verifyComplete()
    }
}
