package br.com.zup.shared.handler.exceptions

import br.com.zup.handler.ExceptionHandler
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class IllegalStateExceptionHandler : ExceptionHandler<IllegalStateException> {
    override fun handle(e: IllegalStateException): ExceptionHandler.StatusWrapper {
        return ExceptionHandler.StatusWrapper(
            Status.FAILED_PRECONDITION
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is IllegalStateException
    }


}