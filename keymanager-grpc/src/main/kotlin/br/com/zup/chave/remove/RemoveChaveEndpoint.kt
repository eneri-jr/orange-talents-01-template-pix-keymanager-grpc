package br.com.zup.chave.remove

import br.com.zup.ChavePixRequest
import br.com.zup.MensagemRemocaoResponse
import br.com.zup.RemoveChavePixServiceGrpc
import br.com.zup.handler.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
@ErrorHandler
class RemoveChaveEndpoint(val service: RemoveChaveService) : RemoveChavePixServiceGrpc.RemoveChavePixServiceImplBase() {

    override fun remover(request: ChavePixRequest, responseObserver: StreamObserver<MensagemRemocaoResponse>) {
        val chave = request.toModel()
        service.remover(chave)

        responseObserver.onNext(
            MensagemRemocaoResponse.newBuilder()
                .setMensagem("Chave removida com sucesso.")
                .build()
        )

        responseObserver.onCompleted()
    }

}