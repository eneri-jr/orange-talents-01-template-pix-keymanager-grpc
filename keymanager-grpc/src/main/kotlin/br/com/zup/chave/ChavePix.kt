package br.com.zup.chave

import br.com.zup.bcb.*
import br.com.zup.itau.ContaChavePix
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(name = "chave_pix_uk", columnNames = ["chave"])
    ]
)
class ChavePix(
    @field:NotNull
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    val clienteId: UUID?,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipo: TipoChave,

    @field:NotBlank
    @Column(unique = true, nullable = false)
    var chave: String,

    @field:Valid
    @Embedded
    val conta: ContaChavePix
) {
    @Id
    @GeneratedValue
    @Type(type = "org.hibernate.type.UUIDCharType")
    val id: UUID? = null

    @Column(nullable = false)
    val criadaEm: LocalDateTime = LocalDateTime.now()

    fun toModel() : CreatePixKeyRequest {
        return CreatePixKeyRequest(
            keyType = when(tipo) {
                TipoChave.ALEATORIA -> KeyType.RANDOM
                TipoChave.CPF -> KeyType.CPF
                TipoChave.CELULAR -> KeyType.PHONE
                TipoChave.EMAIL -> KeyType.EMAIL
            },
            key = chave,
            Bank(
                participant = "60701190",
                branch = conta.agencia,
                accountNumber = conta.numeroDaConta,
                accountType = when(conta.tipoConta) {
                    "CONTA_CORRENTE" -> AccountType.CACC
                    else -> AccountType.SVGS
                }
            ),
            Owner(
                type = br.com.zup.bcb.Type.NATURAL_PERSON,
                name = conta.nomeDoTitular,
                taxIdNumber = conta.cpfDoTitular
            )
        )
    }
}