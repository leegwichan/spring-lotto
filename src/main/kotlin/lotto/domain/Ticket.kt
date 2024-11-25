package lotto.domain

import jakarta.persistence.*

@Entity
class Ticket(

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    val lottos: List<Lotto> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
) {

    constructor(numbers: List<List<Int>>) : this(toLottos(numbers))

    companion object {

        private fun toLottos(numbers: List<List<Int>>): List<Lotto> {
            require(numbers.isNotEmpty()) { "티켓은 적어도 한 장 이상의 로또가 있어야 합니다" }
            return numbers.map { Lotto(it) }
        }
    }
}
