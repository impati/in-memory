package domain.string

import org.example.domain.string.ReactiveStringRepository
import org.example.domain.string.StringCommand
import org.example.domain.string.StringCommandService
import org.example.domain.string.StringCommandValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class StringCommandServiceTest {

    private lateinit var reactiveStringRepository: ReactiveStringRepository
    private lateinit var stringCommandService: StringCommandService

    @BeforeEach
    fun setUp() {
        reactiveStringRepository = ReactiveStringRepository(mutableMapOf())
        stringCommandService = StringCommandService(reactiveStringRepository)
    }

    @Test
    @DisplayName("key 에 대한 값이 없는 경우 0으로 초기화하고 incr 한다.")
    fun test() {
        val key = "hello"

        StepVerifier.create(stringCommandService.incr(key))
            .expectNext("1")
            .verifyComplete()
    }

    @Test
    @DisplayName("key 에 대한 값이 10 이었을 경우 incr 하면 11 로 응답한다.")
    fun test2() {
        val key = "hello"
        reactiveStringRepository.set(StringCommand(key, StringCommandValue("10"))).block()

        StepVerifier.create(stringCommandService.incr(key))
            .expectNext("11")
            .verifyComplete()
    }

    @Test
    @DisplayName("key 에 대한 값이 String 이었을 경우 에러를 응답한다.")
    fun test3() {
        val key = "hello"
        reactiveStringRepository.set(StringCommand(key, StringCommandValue("10ㅎ"))).block()

        StepVerifier.create(stringCommandService.incr(key))
            .verifyError()
    }

    @Test
    @DisplayName("key 에 대한 값이 10 이었을 경우 decr 하면 9 로 응답한다.")
    fun test4() {
        val key = "hello"
        reactiveStringRepository.set(StringCommand(key, StringCommandValue("10"))).block()

        StepVerifier.create(stringCommandService.decr(key))
            .expectNext("9")
            .verifyComplete()
    }

    @Test
    @DisplayName("key 에 대한 값이 10 이었을 경우 by 값으로 5를 넣으면 15가 된다.")
    fun test5() {
        val key = "hello"
        reactiveStringRepository.set(StringCommand(key, StringCommandValue("10"))).block()

        StepVerifier.create(stringCommandService.incr(key, 5))
            .expectNext("15")
            .verifyComplete()
    }

    @Test
    @DisplayName("key 에 대한 값이 10 이었을 경우 by 값으로 5를 넣으면 5가 된다.")
    fun test6() {
        val key = "hello"
        reactiveStringRepository.set(StringCommand(key, StringCommandValue("10"))).block()

        StepVerifier.create(stringCommandService.decr(key, 5))
            .expectNext("5")
            .verifyComplete()
    }
}
