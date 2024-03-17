package com.ferick.service.impl

import com.ferick.model.entities.Genre
import com.ferick.repositories.GenreRepository
import com.ferick.service.GenreService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository
) : GenreService {

    @Transactional(readOnly = true)
    override fun findAll(): List<Genre> {
        return genreRepository.findAll()
    }
}
