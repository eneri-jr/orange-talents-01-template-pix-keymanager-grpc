package br.com.zup.itau

data class DetalhesDaContaResponse(
    val tipo: String,
    val instituicao: InstituicaoResponse,
    val agencia: String,
    val numero: String,
    val titular: TitularResponse
) {
    fun toModel(): ContaChavePix {
        return ContaChavePix(
            instituicao = this.instituicao.nome,
            nomeDoTitular = this.titular.nome,
            cpfDoTitular = this.titular.cpf,
            agencia = this.agencia,
            numeroDaConta = this.numero

        )
    }
}

data class InstituicaoResponse(
    val nome : String,
    val ispb: String
)

data class TitularResponse(
    val id : String,
    val nome: String,
    val cpf : String
)