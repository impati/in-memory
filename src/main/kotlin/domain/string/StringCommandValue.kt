package org.example.domain.string

data class StringCommandValue(
    val value: String
) {

    companion object {

        fun initializeIntegerValue(): StringCommandValue {
            return StringCommandValue("0")
        }
    }

    fun incr(): StringCommandValue {
        return StringCommandValue(value.toInt().inc().toString())
    }

    fun decr(): StringCommandValue {
        return StringCommandValue(value.toInt().dec().toString())
    }

    fun plus(by: Int): StringCommandValue {
        return StringCommandValue(value.toInt().plus(by).toString())
    }

    fun minus(by: Int): StringCommandValue {
        return StringCommandValue(value.toInt().minus(by).toString())
    }
}
