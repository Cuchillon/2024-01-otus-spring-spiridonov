package com.ferick.service.impl

import com.ferick.helpers.REPOSITORY_ERROR_MESSAGE
import com.ferick.helpers.assertCircuitBreakerResponse
import com.ferick.model.dto.UpsertBookRequest
import com.ferick.service.BookService
import com.ferick.service.ServiceBaseTest
import io.mockk.every
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest : ServiceBaseTest() {

    @Autowired
    private lateinit var bookService: BookService

    @ParameterizedTest
    @MethodSource("methodProvider")
    fun testMethods(methodName: String, methodArgTypes: Array<Class<*>>, methodArgs: Array<Any>) {
        val method = bookService.javaClass.getMethod(methodName, *methodArgTypes)
        val circuitBreakerName = BookServiceImpl.CIRCUIT_BREAKER_NAME
        every { bookRepository.findAll() } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        every { bookRepository.findById(any()) } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        every { bookRepository.deleteById(any()) } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        every { bookRepository.count() } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        every { authorRepository.findById(any()) } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        assertCircuitBreakerResponse(circuitBreakerName) {
            method.invoke(bookService, *methodArgs)
        }
    }

    companion object {
        private val request = UpsertBookRequest().apply {
            title = RandomStringUtils.randomAlphabetic(8)
            authorId = 1L
            genreIds = setOf(1L)
        }

        @JvmStatic
        fun methodProvider() = listOf(
            Arguments.of(BookService::findById.name, arrayOf(Long::class.java), arrayOf(1L)),
            Arguments.of(BookService::findAll.name, emptyArray<Class<*>>(), emptyArray<Any>()),
            Arguments.of(BookService::insert.name, arrayOf(UpsertBookRequest::class.java), arrayOf(request)),
            Arguments.of(
                BookService::update.name, arrayOf(Long::class.java, UpsertBookRequest::class.java), arrayOf(1L, request)
            ),
            Arguments.of(BookService::deleteById.name, arrayOf(Long::class.java), arrayOf(1L)),
            Arguments.of(BookService::count.name, emptyArray<Class<*>>(), emptyArray<Any>())
        )
    }
}
