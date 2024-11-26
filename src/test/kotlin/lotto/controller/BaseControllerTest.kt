package lotto.controller

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import lotto.repository.TicketRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
    value = [DatabaseCleanerExecutionListener::class, RestAssuredExtensionListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
abstract class BaseControllerTest : AnnotationSpec() {

    @Autowired
    protected lateinit var ticketRepository: TicketRepository

    override fun extensions(): List<Extension> = listOf(SpringExtension)
}

