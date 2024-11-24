package controller

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.test.TestCase
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import repository.TicketRepository

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseControllerTest : AnnotationSpec() {

    @Autowired
    protected lateinit var ticketRepository: TicketRepository

    @LocalServerPort
    private var port: Int = 0

    override suspend fun beforeEach(testCase: TestCase) {
        RestAssured.port = port
    }
}
