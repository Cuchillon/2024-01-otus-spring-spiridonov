package com.ferick.service.impl

import com.ferick.converters.AuthorConverter
import com.ferick.model.dto.AuthorDto
import com.ferick.repositories.AuthorRepository
import com.ferick.service.AuthorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
    private val authorConverter: AuthorConverter
) : AuthorService {

    @Transactional(readOnly = true)
    override fun findAll(): List<AuthorDto> {
        return authorRepository.findAll().map { authorConverter.authorToDto(it) }
    }
}
