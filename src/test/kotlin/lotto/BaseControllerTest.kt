package lotto

import lotto.helper.controller.DatabaseCleanerExecutionListener
import lotto.helper.controller.RestAssuredExtensionListener
import lotto.repository.TicketRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
    value = [DatabaseCleanerExecutionListener::class, RestAssuredExtensionListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
)
abstract class BaseControllerTest {
    @Autowired
    protected lateinit var ticketRepository: TicketRepository
}
