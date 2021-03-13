package br.com.zup.chave.detalha

import br.com.zup.chave.ChavePix
import br.com.zup.chave.ChavePixRepository
import br.com.zup.chave.remove.ChaveRequest
import br.com.zup.exception.ChavePixInexistenteException
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
@Validated
class DetalhaChaveService (val repository: ChavePixRepository) {

    @Transactional
    fun detalhar(@Valid chave : ChaveRequest) : ChavePix {

        val possivelChave = repository.findByIdAndClienteId(UUID.fromString(chave.chaveId), UUID.fromString(chave.clienteId))
        if(!possivelChave.isPresent)
            throw ChavePixInexistenteException("A chave que foi digitada e invalida ou nao pertence ao cliente informado")

        return possivelChave.get()
    }
}