package domain.string

import org.example.domain.Operator
import org.example.domain.string.StringCommand
import org.example.domain.string.StringRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class StringRepositoryTest {

    private val repository: StringRepository = StringRepository(mutableMapOf())

    @Test
    @DisplayName("UPSERT 하면 값이 있어도 새로운 값으로 업데이트한다")
    fun upsert1() {
        val command = StringCommand(key = "hello", value = "world", Operator.UPSERT)
        repository.set(StringCommand(key = "hello", value = "arount"))

        val result = repository.set(command)

        assertEquals(command.value, result)
    }

    @Test
    @DisplayName("UPSERT 하면 값이 없어도 새로운 값으로 업데이트한다")
    fun upsert2() {
        val command = StringCommand(key = "hello", value = "world", Operator.UPSERT)

        val result = repository.set(command)

        assertEquals(command.value, result)
    }

    @Test
    @DisplayName("UPDATE_ONLY 인 경우 기존에 값이 없으면 null 을 반환한다.")
    fun update_only1() {
        val command = StringCommand(key = "hello", value = "world", Operator.UPDATE_ONLY)

        val result = repository.set(command)

        assertEquals(null, result)
    }

    @Test
    @DisplayName("UPDATE_ONLY 인 경우 기존에 값이 있으면 업데이트한다.")
    fun update_only2() {
        val command = StringCommand(key = "hello", value = "world", Operator.UPDATE_ONLY)
        repository.set(StringCommand(key = "hello", value = "arount"))


        val result = repository.set(command)

        assertEquals(command.value, result)
    }


    @Test
    @DisplayName("INSERT_ONLY 인 경우 기존에 값이 있으면 예외를 던진다.")
    fun insert_only1() {
        val command = StringCommand(key = "hello", value = "world", Operator.INSERT_ONLY)
        repository.set(StringCommand(key = "hello", value = "arount"))

        val result = repository.set(command)

        assertEquals(null, result)
    }

    @Test
    @DisplayName("INSERT_ONLY 인 경우 기존에 값이 없으면 삽입 한다.")
    fun insert_only2() {
        val command = StringCommand(key = "hello", value = "world", Operator.INSERT_ONLY)

        val result = repository.set(command)

        assertEquals(command.value, result)
    }

    @Test
    @DisplayName("key 에 해당하는 value 가 있으면 리턴한다.")
    fun get1() {
        val command = StringCommand(key = "hello", value = "world")
        repository.set(command)

        val result = repository.get(command.key)

        assertEquals(command.value, result)
    }

    @Test
    @DisplayName("key 에 해당하는 value 가 없으면 null 리턴한다.")
    fun get2() {
        val key = "hello"

        val result = repository.get(key)

        assertEquals(null, result)
    }

    @Test
    @DisplayName("여러 개를 set 할 수 있어야한다.")
    fun multiSet() {
        val commands = listOf(
            StringCommand("first", "one", Operator.UPSERT),
            StringCommand("second", "two", Operator.UPDATE_ONLY),
            StringCommand("third", "three", Operator.INSERT_ONLY)
        )

        val result = repository.multiSet(commands)

        result.forEachIndexed { index, expected -> assertEquals(expected, result[index]) }
    }

    @Test
    @DisplayName("여러 개를 get 할 수 있어야한다.")
    fun multiGet() {
        val commands = listOf(
            StringCommand("first", "one", Operator.UPSERT),
            StringCommand("second", "two", Operator.UPDATE_ONLY),
            StringCommand("third", "three", Operator.INSERT_ONLY)
        )
        repository.multiSet(commands)

        val result = repository.multiGet(listOf("first", "second", "third"))

        assertEquals("one", result[0])
        assertEquals(null, result[1])
        assertEquals("three", result[2])
    }
}
