package com.ferick.service.impl

import com.ferick.converters.AuthorConverter
import com.ferick.model.dto.AuthorDto
import com.ferick.repositories.AuthorRepository
import com.ferick.service.AuthorService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
    private val authorConverter: AuthorConverter
) : AuthorService {

    override fun findAll(): Flux<AuthorDto> {
        return authorRepository.findAll().map { authorConverter.authorToDto(it) }
    }
}
