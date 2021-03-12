package br.com.zup.key.register

import br.com.zup.exception.ChavePixExistenteException
import br.com.zup.itau.SistemaItauClient
import br.com.zup.key.ChavePix
import br.com.zup.key.ChavePixRepository
import io.micronaut.validation.Validated
import java.lang.IllegalStateException
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(
    val repository: ChavePixRepository,
    val itauClient: SistemaItauClient
) {

    @Transactional
    fun registrar(@Valid novaChave: NovaChavePix): ChavePix {

        if(repository.existsByChave(novaChave.valorChave!!))
            throw ChavePixExistenteException("Chave Pix ${novaChave.valorChave} já existe no sistema.")

        val response = itauClient.buscaContaDaChave(novaChave.clienteId!!, novaChave.tipoConta!!.name)
        val conta = response.body()?.toModel() ?: throw IllegalStateException("Cliente não encontrado no sistema do Itaú")

        val chave = novaChave.toModel(conta)
        repository.save(chave)

        return chave
    }
}