package br.com.zup.key

import br.com.zup.itau.ContaChavePix
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
    val clienteId: UUID,

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
    val id: UUID? = null

    @Column(nullable = false)
    val criadaEm: LocalDateTime = LocalDateTime.now()
}