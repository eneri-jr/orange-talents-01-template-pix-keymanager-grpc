package br.com.zup.chave.lista

import br.com.zup.ClientePixRequest
import br.com.zup.ListaChavesPixServiceGrpc
import br.com.zup.ListaDeChavesPixResponse
import br.com.zup.handler.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
@ErrorHandler
class ListaChavesPixEndpoint (val service: ListaChavesPixService) : ListaChavesPixServiceGrpc.ListaChavesPixServiceImplBase() {

    override fun listar(request: ClientePixRequest, responseObserver: StreamObserver<ListaDeChavesPixResponse>) {

        val listaDeChaves = service.listarChaves(request.identificador)

        responseObserver.onNext(ListaDeChavesPixResponse.newBuilder()
            .addAllChavePix(listaDeChaves)
            .build()
        )
        responseObserver.onCompleted()
    }
}