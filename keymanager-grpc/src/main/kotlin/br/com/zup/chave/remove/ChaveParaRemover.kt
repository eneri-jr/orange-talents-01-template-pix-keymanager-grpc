package br.com.zup.chave.remove

import br.com.zup.validacoes.ValidaUUID
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank

@Introspected
data class ChaveParaRemover(

    @field:ValidaUUID
    val chaveId : String,

    @field:ValidaUUID
    val clienteId: String
)