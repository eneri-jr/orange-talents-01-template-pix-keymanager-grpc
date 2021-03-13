package br.com.zup.chave.remove

import br.com.zup.chave.ChavePixRepository
import br.com.zup.exception.ChavePixExistenteException
import br.com.zup.exception.ChavePixInexistenteException
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
@Validated
class RemoveChaveService (val repository : ChavePixRepository) {

    @Transactional
    fun remover(@Valid chave : ChaveParaRemover) {

        if(!repository.existsByIdAndClienteId(UUID.fromString(chave.chaveId), UUID.fromString(chave.clienteId)))
            throw ChavePixInexistenteException("Dados inválidos para a requisição de remoção")

        repository.deleteById(UUID.fromString(chave.chaveId))
    }
}