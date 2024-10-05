package domain.string

import org.example.domain.string.ReactiveStringRepository
import org.example.domain.string.StringCommand
import org.example.domain.string.StringCommandValue
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class ReactiveStringRepositoryTest {

    private val reactiveStringRepository = ReactiveStringRepository(mutableMapOf())

    @Test
    fun multiSet() {
        val command = listOf(StringCommand("hello", StringCommandValue("world")))

        StepVerifier.create(reactiveStringRepository.multiSet(command))
            .expectNext(StringCommandValue("world"))
            .verifyComplete()
    }

    @Test
    fun multiGet() {
        val command = listOf(
            StringCommand("hello", StringCommandValue("world")),
            StringCommand("hi", StringCommandValue("hello"))
        )
        reactiveStringRepository.multiSet(command).subscribe()

        StepVerifier.create(reactiveStringRepository.multiGet(listOf("hello", "hi")))
            .expectNext(StringCommandValue("world"), StringCommandValue("hello"))
            .verifyComplete()
    }
}
