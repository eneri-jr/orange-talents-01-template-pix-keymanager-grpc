package br.com.zup.itau

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${itau.url}")
interface SistemaItauClient {

    @Get("/api/v1/clientes/{clienteId}/contas")
    fun buscaContaDaChave(@PathVariable clienteId: String, @QueryValue tipo: String) : HttpResponse<DetalhesDaContaResponse>
}