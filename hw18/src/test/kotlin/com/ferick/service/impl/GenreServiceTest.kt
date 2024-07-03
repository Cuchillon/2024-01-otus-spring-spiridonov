package com.ferick.service.impl

import com.ferick.helpers.REPOSITORY_ERROR_MESSAGE
import com.ferick.helpers.assertCircuitBreakerResponse
import com.ferick.service.GenreService
import com.ferick.service.ServiceBaseTest
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GenreServiceTest : ServiceBaseTest() {

    @Autowired
    private lateinit var genreService: GenreService

    @Test
    fun findAll() {
        val circuitBreakerName = GenreServiceImpl.CIRCUIT_BREAKER_NAME
        every { genreRepository.findAll() } throws RuntimeException(REPOSITORY_ERROR_MESSAGE)
        assertCircuitBreakerResponse(circuitBreakerName) {
            genreService.findAll()
        }
    }
}
