package org.example.domain.string

import org.example.domain.Operator

data class StringCommand(
    val key: String,
    val value: StringCommandValue,
    val operator: Operator = Operator.UPSERT
)
