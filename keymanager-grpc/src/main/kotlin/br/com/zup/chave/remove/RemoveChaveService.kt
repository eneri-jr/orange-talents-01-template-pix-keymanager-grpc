package br.com.zup.chave.remove

import br.com.zup.bcb.DeletePixKeyRequest
import br.com.zup.bcb.SistemaBcbClient
import br.com.zup.chave.ChavePixRepository
import br.com.zup.exception.ChavePixExistenteException
import br.com.zup.exception.ChavePixInexistenteException
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import java.lang.IllegalStateException
import java.util.*
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
@Validated
class RemoveChaveService (val repository : ChavePixRepository, val bcbClient: SistemaBcbClient) {

    @Transactional
    fun remover(@Valid chave : ChaveParaRemover) {

        val possivelChave = repository.findByIdAndClienteId(UUID.fromString(chave.chaveId), UUID.fromString(chave.clienteId))
        if(!possivelChave.isPresent)
            throw ChavePixInexistenteException("Dados inválidos para a requisição de remoção")

        val request = DeletePixKeyRequest(possivelChave.get().chave, "60701190")

        val responseBcb = bcbClient.deletaChaveBcb(request.key, request)
        if(responseBcb.status != HttpStatus.OK)
            throw IllegalStateException("Erro ao remover chave no Banco Central")

        repository.deleteById(UUID.fromString(chave.chaveId))
    }
}