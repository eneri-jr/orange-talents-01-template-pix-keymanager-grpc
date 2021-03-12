package br.com.zup.key.register

import br.com.zup.NovaChavePixRequest
import br.com.zup.TipoChave.KEY_UNDEFINED
import br.com.zup.TipoConta.ACCOUNT_UNDEFINED
import br.com.zup.key.TipoConta

fun NovaChavePixRequest.toModel(): NovaChavePix {
    return NovaChavePix(
        clienteId = identificador,
        tipoChave = when(tipoChave) {
            KEY_UNDEFINED -> null
            else -> br.com.zup.key.TipoChave.valueOf(tipoChave.name)
        },
        valorChave = valorChave,
        tipoConta = when(tipoConta) {
            ACCOUNT_UNDEFINED -> null
            else -> TipoConta.valueOf(tipoConta.name)
        }
    )
}