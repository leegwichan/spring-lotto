package lotto.repository

import lotto.domain.Ticket
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TicketRepository : JpaRepository<Ticket, Long> {
    @EntityGraph(attributePaths = ["lottos"])
    override fun findById(id: Long): Optional<Ticket>
}
