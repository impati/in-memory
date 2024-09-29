package org.example.domain

enum class Operator {

    UPSERT,
    UPDATE_ONLY,
    INSERT_ONLY
    ;

    companion object {
        fun from(name: String?): Operator {
            if (name == null) {
                return UPSERT
            }

            return entries.single { it.name == name }
        }
    }
}
