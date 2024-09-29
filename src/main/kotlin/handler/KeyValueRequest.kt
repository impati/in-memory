package org.example.handler

import org.example.domain.Operator

data class KeyValueRequest(
    val key: String?,
    val value: String?,
    val option: Operator = Operator.UPSERT
)
