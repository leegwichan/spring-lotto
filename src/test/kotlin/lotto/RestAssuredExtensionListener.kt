package lotto

import io.restassured.RestAssured
import io.restassured.filter.log.ErrorLoggingFilter
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener

class RestAssuredExtensionListener : AbstractTestExecutionListener() {
    override fun beforeTestClass(testContext: TestContext) {
        RestAssured.filters(RequestLoggingFilter(), ResponseLoggingFilter(), ErrorLoggingFilter())
        super.beforeTestClass(testContext)
    }

    override fun beforeTestMethod(testContext: TestContext) {
        val port =
            testContext.applicationContext
                .environment.getProperty("local.server.port")?.toIntOrNull()
                ?: throw IllegalStateException("can't find local server port")

        RestAssured.port = port
        super.beforeTestMethod(testContext)
    }
}
