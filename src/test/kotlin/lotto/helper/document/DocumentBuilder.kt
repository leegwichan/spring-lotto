package lotto.helper.document

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.test.web.servlet.ResultHandler

class DocumentBuilder(private val identifier: String) {
    private val builder: ResourceSnippetParametersBuilder = ResourceSnippetParameters.builder()

    fun description(description: String): DocumentBuilder {
        builder.description(description)
        return this
    }

    fun pathParameters(vararg fields: ParameterDescriptor): DocumentBuilder {
        builder.pathParameters(*fields)
        return this
    }

    fun queryParameters(vararg fields: ParameterDescriptor): DocumentBuilder {
        builder.queryParameters(*fields)
        return this
    }

    fun requestFields(vararg fields: FieldDescriptor): DocumentBuilder {
        builder.requestFields(*fields)
        return this
    }

    fun responseFields(vararg fields: FieldDescriptor): DocumentBuilder {
        builder.responseFields(*fields)
        return this
    }

    fun build(): ResultHandler {
        return document(identifier, builder)
    }
}
