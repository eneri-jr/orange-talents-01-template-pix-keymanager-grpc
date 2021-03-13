package br.com.zup.chave.remove

import br.com.zup.ChavePixRequest

fun ChavePixRequest.toModel() : ChaveParaRemover {
    return ChaveParaRemover(
        chaveId = pixId,
        clienteId = clientId
    )
}