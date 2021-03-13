package br.com.zup.shared.handler.exceptions

import br.com.zup.exception.ChavePixInexistenteException
import br.com.zup.handler.ExceptionHandler
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ChavePixInexistenteExceptionHandler : ExceptionHandler<ChavePixInexistenteException> {
    override fun handle(e: ChavePixInexistenteException): ExceptionHandler.StatusWrapper {
        return ExceptionHandler.StatusWrapper(
            Status.NOT_FOUND
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is ChavePixInexistenteException
    }
}