package com.ferick.service

import com.ferick.model.dto.AuthorDto
import reactor.core.publisher.Flux

interface AuthorService {
    fun findAll(): Flux<AuthorDto>
}
