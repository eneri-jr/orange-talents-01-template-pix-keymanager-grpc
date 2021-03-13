package br.com.zup.bcb

import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${bcb.url}")
interface SistemaBcbClient {

    @Post("/api/v1/pix/keys")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    fun registraChaveBcb(@Body request: CreatePixKeyRequest) : HttpResponse<CreatePixKeyResponse>

    @Delete("/api/v1/pix/keys/{key}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    fun deletaChaveBcb(@Parameter key: String, @Body request: DeletePixKeyRequest) : HttpResponse<DeletePixKeyResponse>
}