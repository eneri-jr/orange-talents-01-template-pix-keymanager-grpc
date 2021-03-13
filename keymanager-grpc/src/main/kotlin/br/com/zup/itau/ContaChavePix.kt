package br.com.zup.itau

import javax.persistence.Embeddable

@Embeddable
data class ContaChavePix(
    val instituicao : String,
    val nomeDoTitular : String,
    val cpfDoTitular : String,
    val agencia : String,
    val numeroDaConta : String,
    val tipoConta : String
)