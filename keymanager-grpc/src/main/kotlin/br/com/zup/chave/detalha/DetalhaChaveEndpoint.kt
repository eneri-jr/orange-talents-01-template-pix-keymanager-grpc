package br.com.zup.chave.detalha

import br.com.zup.*
import br.com.zup.chave.remove.toModel
import br.com.zup.handler.ErrorHandler
import com.google.protobuf.Timestamp
import io.grpc.stub.StreamObserver
import java.time.ZoneId
import javax.inject.Singleton
import io.micronaut.validation.validator.Validator

@Singleton
@ErrorHandler
class DetalhaChaveEndpoint (val service: DetalhaChaveService) : DetalhaChavePixServiceGrpc.DetalhaChavePixServiceImplBase(){

    override fun detalhar(request: DetalhaChavePixRequest, responseObserver: StreamObserver<DetalhamentoPixResponse>) {
        val chaveDetalhada= request.selecionaServico(service)

        val criada = chaveDetalhada.criadaEm.atZone(ZoneId.of("UTC")).toInstant()

        responseObserver!!.onNext(
            DetalhamentoPixResponse.newBuilder()
                .setPixId(chaveDetalhada.id.toString())
                .setClientId(chaveDetalhada.clienteId.toString())
                .setTipoChave(chaveDetalhada.tipo.toString())
                .setValorChave(chaveDetalhada.chave)
                .setConta(Conta.newBuilder()
                    .setNome(chaveDetalhada.conta.nomeDoTitular)
                    .setCpf(chaveDetalhada.conta.cpfDoTitular)
                    .setInstituicao(chaveDetalhada.conta.instituicao)
                    .setAgencia(chaveDetalhada.conta.agencia)
                    .setConta(chaveDetalhada.conta.numeroDaConta)
                    .setTipo(chaveDetalhada.conta.tipoConta)
                    .build())
                .setRegistradaEm(Timestamp.newBuilder()
                    .setNanos(criada.nano)
                    .setSeconds(criada.epochSecond)
                    .build())
                .build()
        )

        responseObserver.onCompleted()

    }
}