package org.example.domain.string

import org.example.domain.Operator

data class StringCommand(
    val key: String,
    val value: String,
    val operator: Operator = Operator.UPSERT
)
