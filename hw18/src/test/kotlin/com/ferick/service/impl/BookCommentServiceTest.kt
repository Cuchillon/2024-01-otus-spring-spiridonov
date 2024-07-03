package com.ferick.service.impl

import com.ferick.helpers.REPOSITORY_ERROR_MESSAGE
import com.ferick.helpers.assertCircuitBreakerResponse
import com.ferick.model.dto.UpsertBookCommentRequest
import com.ferick.service.BookCommentService
import com.ferick.service.ServiceBaseTest
import io.mockk.every
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookCommentServiceTest : ServiceBaseTest() {

    @Autowired
    private lateinit var bookCommentService: BookCommentService

    @ParameterizedTest
    @MethodSource("methodProvider")
    fun testMethods(methodName: String, methodArgTypes: Array<Class<*>>, methodArgs: Array<Any>) {
        val method = bookCommentService.javaClass.getMethod(methodName, *methodArgTypes)
        val circuitBreakerName = BookCommentServiceImpl.CIRCUIT_BREAKER_NAME
        every { bookCommentRepository.findById(any()) } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        every { bookCommentRepository.findByBookId(any()) } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        every { bookRepository.findById(any()) } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        every { bookCommentRepository.deleteById(any()) } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        assertCircuitBreakerResponse(circuitBreakerName) {
            method.invoke(bookCommentService, *methodArgs)
        }
    }

    companion object {
        private val request = UpsertBookCommentRequest(
            text = RandomStringUtils.randomAlphabetic(8),
            bookId = 1L
        )

        @JvmStatic
        fun methodProvider() = listOf(
            Arguments.of(BookCommentService::findById.name, arrayOf(Long::class.java), arrayOf(1L)),
            Arguments.of(BookCommentService::findByBookId.name, arrayOf(Long::class.java), arrayOf(1L)),
            Arguments.of(
                BookCommentService::insert.name, arrayOf(UpsertBookCommentRequest::class.java), arrayOf(request)
            ),
            Arguments.of(
                BookCommentService::update.name,
                arrayOf(Long::class.java, UpsertBookCommentRequest::class.java), arrayOf(1L, request)
            ),
            Arguments.of(BookCommentService::deleteById.name, arrayOf(Long::class.java), arrayOf(1L))
        )
    }
}
