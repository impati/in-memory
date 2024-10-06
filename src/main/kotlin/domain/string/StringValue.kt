package org.example.domain.string

data class StringValue(
    val value: String
) {

    companion object {

        fun initializeIntegerValue(): StringValue {
            return StringValue("0")
        }
    }

    fun incr(): StringValue {
        return StringValue(value.toInt().inc().toString())
    }

    fun decr(): StringValue {
        return StringValue(value.toInt().dec().toString())
    }

    fun plus(by: Int): StringValue {
        return StringValue(value.toInt().plus(by).toString())
    }

    fun minus(by: Int): StringValue {
        return StringValue(value.toInt().minus(by).toString())
    }
}
