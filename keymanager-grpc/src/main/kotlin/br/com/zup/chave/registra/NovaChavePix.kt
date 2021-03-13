package br.com.zup.chave.registra

import br.com.zup.itau.ContaChavePix
import br.com.zup.chave.ChavePix
import br.com.zup.chave.TipoChave
import br.com.zup.chave.TipoConta
import br.com.zup.validacoes.ValidPixKey
import br.com.zup.validacoes.ValidaUUID
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ValidPixKey
data class NovaChavePix(

    @field:ValidaUUID
    @field:NotBlank
    val clienteId: String?,
    @field:NotNull
    val tipoChave: TipoChave?,
    @field:Size(max = 77)
    val valorChave: String?,
    @field:NotNull
    val tipoConta: TipoConta?
) {
    fun toModel(conta: ContaChavePix): ChavePix {
        return ChavePix(
            clienteId = UUID.fromString(this.clienteId),
            tipo = tipoChave!!,
            chave = when (tipoChave) {
                TipoChave.ALEATORIA -> UUID.randomUUID().toString()
                else -> valorChave!!
            },
            conta = conta
        )
    }
}