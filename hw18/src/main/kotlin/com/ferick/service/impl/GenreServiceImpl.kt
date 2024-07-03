package com.ferick.service.impl

import com.ferick.converters.GenreConverter
import com.ferick.model.dto.GenreDto
import com.ferick.repositories.GenreRepository
import com.ferick.service.GenreService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository,
    private val genreConverter: GenreConverter
) : GenreService {

    @Transactional(readOnly = true)
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME)
    override fun findAll(): List<GenreDto> {
        return genreRepository.findAll().map { genreConverter.genreToDto(it) }
    }

    companion object {
        const val CIRCUIT_BREAKER_NAME = "genres"
    }
}
