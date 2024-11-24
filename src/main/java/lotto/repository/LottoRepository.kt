package lotto.repository

import lotto.domain.Lotto
import lotto.domain.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface LottoRepository : JpaRepository<Lotto, Int> {

    fun findAllByTicket(ticket: Ticket): List<Lotto>
}
