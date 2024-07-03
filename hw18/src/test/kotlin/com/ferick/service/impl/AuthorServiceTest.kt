package com.ferick.service.impl

import com.ferick.helpers.REPOSITORY_ERROR_MESSAGE
import com.ferick.helpers.assertCircuitBreakerResponse
import com.ferick.service.AuthorService
import com.ferick.service.ServiceBaseTest
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthorServiceTest : ServiceBaseTest() {

    @Autowired
    private lateinit var authorService: AuthorService

    @Test
    fun findAll() {
        val circuitBreakerName = AuthorServiceImpl.CIRCUIT_BREAKER_NAME
        every { authorRepository.findAll() } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        assertCircuitBreakerResponse(circuitBreakerName) {
            authorService.findAll()
        }
    }
}
