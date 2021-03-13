package br.com.zup.chave.remove

import br.com.zup.ChavePixRequest

fun ChavePixRequest.toModel() : ChaveRequest {
    return ChaveRequest(
        chaveId = pixId,
        clienteId = clientId
    )
}