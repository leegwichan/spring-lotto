package lotto

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(RestDocumentationExtension::class)
@AutoConfigureRestDocs
abstract class BaseDocumentTest {
    protected lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setup(
        applicationContext: WebApplicationContext,
        restDocumentationContext: RestDocumentationContextProvider,
    ) {
        mockMvc =
            MockMvcBuilders.webAppContextSetup(applicationContext)
                .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
                .apply<DefaultMockMvcBuilder>(
                    documentationConfiguration(restDocumentationContext)
                        .operationPreprocessors()
                        .withRequestDefaults(
                            Preprocessors.prettyPrint(),
                            Preprocessors.modifyHeaders().remove(HttpHeaders.CONTENT_LENGTH),
                        )
                        .withResponseDefaults(
                            Preprocessors.prettyPrint(),
                            Preprocessors.modifyHeaders().remove(HttpHeaders.CONTENT_LENGTH),
                        ),
                )
                .build()
    }
}
