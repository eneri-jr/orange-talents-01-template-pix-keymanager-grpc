package br.com.zup.shared.handler.exceptions


import br.com.zup.handler.ExceptionHandler
import com.google.protobuf.Any
import com.google.rpc.BadRequest
import com.google.rpc.Code
import com.google.rpc.Status
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class ConstraintViolationExceptionHandler : ExceptionHandler<ConstraintViolationException> {

    override fun handle(e: ConstraintViolationException): ExceptionHandler.StatusWrapper {
        val details = BadRequest.newBuilder()
            .addAllFieldViolations(e.constraintViolations.map {
                BadRequest.FieldViolation.newBuilder()
                    .setField(it.propertyPath.toString())
                    .setDescription(it.message)
                    .build()
            }).build()

        val status = Status.newBuilder()
            .setCode(Code.INVALID_ARGUMENT_VALUE)
            .setMessage("Erro de validação no argumento ${details.getFieldViolations(0).field}, ${details.getFieldViolations(0).description}")
            .addDetails(Any.pack(details))
            .build()

        return ExceptionHandler.StatusWrapper(status)
    }

    override fun supports(e: Exception): Boolean {
        return e is ConstraintViolationException
    }
}