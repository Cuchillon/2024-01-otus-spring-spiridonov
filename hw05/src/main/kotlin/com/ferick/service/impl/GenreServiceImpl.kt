package com.ferick.service.impl

import com.ferick.model.Genre
import com.ferick.repositories.GenreRepository
import com.ferick.service.GenreService
import org.springframework.stereotype.Service

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository
) : GenreService {

    override fun findAll(): List<Genre> {
        return genreRepository.findAll()
    }
}
