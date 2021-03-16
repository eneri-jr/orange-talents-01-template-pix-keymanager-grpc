package br.com.zup.chave.lista

import br.com.zup.ListaDeChavesPixResponse
import br.com.zup.chave.ChavePixRepository
import br.com.zup.validacoes.ValidaUUID
import com.google.protobuf.Timestamp
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.util.*
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
@Validated
class ListaChavesPixService (
    val repository: ChavePixRepository
        ) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun listarChaves(@ValidaUUID clienteId: String) : List<ListaDeChavesPixResponse.ChavePix> {

        logger.info("Pedido de listagem para as chaves do clienteId: $clienteId")

        val listaDeChaves = repository.findByClienteId(UUID.fromString(clienteId))

        return listaDeChaves.map {
            val criada = it.criadaEm.atZone(ZoneId.of("UTC")).toInstant()

            ListaDeChavesPixResponse.ChavePix.newBuilder()
                .setPixId(it.id.toString())
                .setClienteId(it.clienteId.toString())
                .setTipoChave(it.tipo.toString())
                .setValorChave(it.chave)
                .setTipoConta(it.conta.tipoConta.toString())
                .setCriadaEm(
                    Timestamp.newBuilder()
                    .setNanos(criada.nano)
                    .setSeconds(criada.epochSecond)
                    .build())
                .build()

        }
    }
}