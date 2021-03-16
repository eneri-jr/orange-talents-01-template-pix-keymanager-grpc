package br.com.zup.chave.registra

import br.com.zup.bcb.SistemaBcbClient
import br.com.zup.exception.ChavePixExistenteException
import br.com.zup.itau.SistemaItauClient
import br.com.zup.chave.ChavePix
import br.com.zup.chave.ChavePixRepository
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(
    val repository: ChavePixRepository,
    val itauClient: SistemaItauClient,
    val bcbClient: SistemaBcbClient
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun registrar(@Valid novaChave: NovaChavePix): ChavePix {

        logger.info("Registro da chave: ${novaChave.valorChave}, referente a conta do cliente: ${novaChave.clienteId}")

        if(repository.existsByChave(novaChave.valorChave!!))
            throw ChavePixExistenteException("Chave Pix ${novaChave.valorChave} já existe no sistema.")

        val response = itauClient.buscaContaDaChave(novaChave.clienteId!!, novaChave.tipoConta!!.name)
        val conta = response.body()?.toModel() ?: throw IllegalStateException("Cliente não encontrado no sistema do Itaú")

        logger.info("Conta localizada no sistema do Itaú: $conta")

        val chave = novaChave.toModel(conta)
        repository.save(chave)

        val chaveBcb = chave.toModel()

        val responseBcb = bcbClient.registraChaveBcb(chaveBcb)

        if(responseBcb.status != HttpStatus.CREATED)
            throw IllegalStateException("Erro ao cadastrar chave no Banco Central")

        chave.chave = responseBcb.body().key

        logger.info("Chave Pix cadastrada com sucesso no sistema do Itaú e Bcb")

        return chave
    }
}