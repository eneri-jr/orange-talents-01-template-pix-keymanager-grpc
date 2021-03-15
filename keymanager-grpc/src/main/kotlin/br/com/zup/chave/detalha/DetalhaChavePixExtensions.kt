package br.com.zup.chave.detalha

import br.com.zup.DetalhaChavePixRequest
import br.com.zup.DetalhaChavePixRequest.DadosPixExternoCase.CHAVE
import br.com.zup.DetalhaChavePixRequest.DadosPixExternoCase.DADOSPIXEXTERNO_NOT_SET
import br.com.zup.DetalhaChavePixRequest.DadosPixExternoCase.DADOS
import br.com.zup.chave.ChavePix
import br.com.zup.chave.remove.ChaveRequest

fun DetalhaChavePixRequest.selecionaServico (service: DetalhaChaveService) : ChavePix {

    val dadosPesquisa = when (dadosPixExternoCase) {
        DADOS -> dados.let {
            service.detalhesInternos(ChaveRequest(it.pixId, it.clienteId))
        }
        CHAVE -> service.detalhesExternos(chave)
        DADOSPIXEXTERNO_NOT_SET -> service.RequisicaoInvalida()
    }

    return dadosPesquisa as ChavePix

}