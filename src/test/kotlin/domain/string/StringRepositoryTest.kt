package domain.string

import org.example.domain.Operator
import org.example.domain.string.StringCommand
import org.example.domain.string.StringRepository
import org.example.domain.string.StringValue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class StringRepositoryTest {

    private val reactiveStringRepository = StringRepository(mutableMapOf())

    @Test
    fun multiSet() {
        val command = listOf(StringCommand("hello", StringValue("world")))

        StepVerifier.create(reactiveStringRepository.multiSet(command))
            .expectNext(StringValue("world"))
            .verifyComplete()
    }

    @Test
    fun multiGet() {
        val command = listOf(
            StringCommand("hello", StringValue("world")),
            StringCommand("hi", StringValue("hello"))
        )
        reactiveStringRepository.multiSet(command).subscribe()

        StepVerifier.create(reactiveStringRepository.multiGet(listOf("hello", "hi")))
            .expectNext(StringValue("world"), StringValue("hello"))
            .verifyComplete()
    }

    @Test
    fun multiGet2() {
        reactiveStringRepository.set(StringCommand("hello", StringValue("world"))).block()

        StepVerifier.create(reactiveStringRepository.multiGet(listOf("hello", "hi")))
            .expectNextCount(1)
            .verifyComplete()
    }

    @Test
    @DisplayName("옵션이 UPDATE_ONLY 인 경우 기존 값이 없을 때 Mono.empty 를 반환한다.")
    fun set() {
        val command = StringCommand("hello", StringValue("world"), Operator.UPDATE_ONLY)

        StepVerifier.create(reactiveStringRepository.set(command))
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    @DisplayName("옵션이 INSERT_ONLY 인 경우 기존 값이 있을 때 Mono.empty 를 반환한다.")
    fun set2() {
        reactiveStringRepository.set(StringCommand("hello", StringValue("world"))).block()
        val command = StringCommand("hello", StringValue("world2"), Operator.INSERT_ONLY)

        StepVerifier.create(reactiveStringRepository.set(command))
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    @DisplayName("key 에 대한 값이 없는 경우 Mono.empty 를 반환한다.")
    fun get() {
        StepVerifier.create(reactiveStringRepository.get("hello"))
            .expectNextCount(0)
            .verifyComplete()
    }
}
