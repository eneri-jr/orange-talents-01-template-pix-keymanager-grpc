package br.com.zup.bcb

import java.time.LocalDateTime

data class CreatePixKeyRequest (
    val keyType: KeyType,
    val key: String,
    val bankAccount: Bank,
    val owner: Owner
)

enum class KeyType {
    CPF, CNPJ, PHONE, EMAIL, RANDOM
}

data class Bank(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccountType
)

enum class AccountType {
    CACC, SVGS
}

data class Owner (
    val type: Type,
    val name: String,
    val taxIdNumber: String
)

enum class Type {
    NATURAL_PERSON, LEGAL_PERSON
}