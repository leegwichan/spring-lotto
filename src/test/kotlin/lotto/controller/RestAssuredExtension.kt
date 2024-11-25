package lotto.controller

import io.kotest.core.extensions.SpecExtension
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.restassured.RestAssured
import io.restassured.filter.log.ErrorLoggingFilter
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter

class RestAssuredExtension(private val port: Int) : TestCaseExtension, SpecExtension {

    override suspend fun intercept(spec: Spec, execute: suspend (Spec) -> Unit) {
        RestAssured.filters(RequestLoggingFilter(), ResponseLoggingFilter(), ErrorLoggingFilter())
        return super.intercept(spec, execute)
    }

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        RestAssured.port = port
        return execute.invoke(testCase)
    }
}
