package lotto.repository

import lotto.domain.Ticket
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.Optional

fun TicketRepository.getById(id: Long): Ticket = findByIdOrNull(id) ?: throw throw IllegalArgumentException("Ticket with id $id not found")

interface TicketRepository : CrudRepository<Ticket, Long> {
    @EntityGraph(attributePaths = ["lottos"])
    override fun findById(id: Long): Optional<Ticket>
}
