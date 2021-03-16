package br.com.zup.chave.detalha

import br.com.zup.bcb.AccountType
import br.com.zup.bcb.CreatePixKeyResponse
import br.com.zup.bcb.KeyType
import br.com.zup.bcb.SistemaBcbClient
import br.com.zup.chave.ChavePix
import br.com.zup.chave.ChavePixRepository
import br.com.zup.chave.TipoChave
import br.com.zup.chave.remove.ChaveRequest
import br.com.zup.exception.ChavePixInexistenteException
import br.com.zup.itau.ContaChavePix
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Size

@Singleton
@Validated
class DetalhaChaveService (val repository: ChavePixRepository, val bcbClient: SistemaBcbClient) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun detalhesInternos(@Valid chave : ChaveRequest) : ChavePix {

        logger.info("Pedido de detalhamento para a chave: $chave, usando o sistema interno")

        val possivelChave = repository.findByIdAndClienteId(UUID.fromString(chave.chaveId), UUID.fromString(chave.clienteId))
        if(!possivelChave.isPresent)
            throw ChavePixInexistenteException("A chave que foi digitada é inválida ou não pertence ao cliente informado")

        return possivelChave.get()
    }


    fun detalhesExternos(@Size(max = 77) chave : String) : ChavePix{

        logger.info("Pedido de detalhamento para a chave: $chave, usando o sistema externo")

        val possivelChave = repository.findByChave(chave)
        if(!possivelChave.isPresent)
        {
            val chaveBcb = bcbClient.buscaChave(chave)
            if(chaveBcb.status != HttpStatus.OK)
                throw IllegalStateException("A chave digitada não existe em nossos dados e nem no BCB")

            return ChavePix(null,
                when (chaveBcb.body().keyType) {
                    KeyType.EMAIL -> TipoChave.EMAIL
                    KeyType.PHONE -> TipoChave.CELULAR
                    KeyType.CPF -> TipoChave.CPF
                    else -> TipoChave.ALEATORIA
                }, chave, ContaChavePix("", chaveBcb.body().owner.name, chaveBcb.body().owner.taxIdNumber,
                    chaveBcb.body().bankAccount.branch, chaveBcb.body().bankAccount.accountNumber,
                    when(chaveBcb.body().bankAccount.accountType) {
                        AccountType.SVGS -> "CONTA_POUPANCA"
                        else -> "CONTA_CORRENTE"
                    }))
        }

        return possivelChave.get()
    }

    fun RequisicaoInvalida() {
        println("Caiu aqui")
        throw IllegalStateException("Dados inválidos para a requisição.")
    }
}