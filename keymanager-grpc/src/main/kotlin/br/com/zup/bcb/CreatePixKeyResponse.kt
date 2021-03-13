package br.com.zup.bcb

import java.time.LocalDateTime

data class CreatePixKeyResponse(
    val keyType: KeyType,
    val key: String,
    val bankAccount: Bank,
    val owner: Owner,
    val createdAt: LocalDateTime
)
