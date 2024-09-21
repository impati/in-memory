package org.example.domain.string

import org.example.domain.Operator
import java.util.*

class StringRepository(
    private val repository: MutableMap<String, String>
) {


    fun set(command: StringCommand): String? {
        val previous = repository[command.key]
        if (command.operator == Operator.UPDATE_ONLY && Objects.isNull(previous)) {
            return null
        }
        if (command.operator == Operator.INSERT_ONLY && Objects.nonNull(previous)) {
            return null
        }

        repository[command.key] = command.value
        return command.value
    }

    fun multiSet(commands: List<StringCommand>): List<String?> {
        return commands.map { set(it) }.toList()
    }

    fun get(key: String): String? {
        return repository[key]
    }

    fun multiGet(keys: List<String>): List<String?> {
        return keys.map { get(it) }.toList()
    }
}
