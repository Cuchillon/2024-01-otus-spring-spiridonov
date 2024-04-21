package com.ferick.service.impl

import com.ferick.converters.GenreConverter
import com.ferick.model.dto.GenreDto
import com.ferick.repositories.GenreRepository
import com.ferick.service.GenreService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository,
    private val genreConverter: GenreConverter
) : GenreService {

    @Transactional(readOnly = true)
    override fun findAll(): List<GenreDto> {
        return genreRepository.findAll().map { genreConverter.genreToDto(it) }
    }
}
