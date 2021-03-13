package br.com.zup.chave.registra

import br.com.zup.*
import br.com.zup.handler.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
@ErrorHandler
class RegistraChaveEndpoint (val service: NovaChavePixService) : RegistrarNovaChavePixServiceGrpc.RegistrarNovaChavePixServiceImplBase(){

    override fun registrar(request: NovaChavePixRequest?, responseObserver: StreamObserver<NovaChavePixResponse>?) {

        val novaChave = request!!.toModel()
        val chaveCriada = service.registrar(novaChave)

        responseObserver!!.onNext(NovaChavePixResponse.newBuilder()
            .setClientId(chaveCriada.clienteId.toString())
            .setPixId(chaveCriada.id.toString())
            .build())

        responseObserver.onCompleted()
    }
}

