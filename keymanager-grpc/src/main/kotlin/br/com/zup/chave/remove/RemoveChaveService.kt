package br.com.zup.chave.remove

import br.com.zup.bcb.DeletePixKeyRequest
import br.com.zup.bcb.SistemaBcbClient
import br.com.zup.chave.ChavePixRepository
import br.com.zup.exception.ChavePixExistenteException
import br.com.zup.exception.ChavePixInexistenteException
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import java.util.*
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
@Validated
class RemoveChaveService (val repository : ChavePixRepository, val bcbClient: SistemaBcbClient) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun remover(@Valid chave : ChaveRequest) {

        logger.info("Pedido de remoção para a chave: $chave")

        val possivelChave = repository.findByIdAndClienteId(UUID.fromString(chave.chaveId), UUID.fromString(chave.clienteId))
        if(!possivelChave.isPresent)
            throw ChavePixInexistenteException("Dados inválidos para a requisição de remoção")

        val request = DeletePixKeyRequest(possivelChave.get().chave, chave.ispb)

        val responseBcb = bcbClient.deletaChaveBcb(request.key, request)
        if(responseBcb.status != HttpStatus.OK)
            throw IllegalStateException("Erro ao remover chave no Banco Central")

        logger.info("Chave removida com sucesso no sistema BCB")

        repository.deleteById(UUID.fromString(chave.chaveId))

        logger.info("Chave removida com sucesso no sistema Itaú")
    }
}