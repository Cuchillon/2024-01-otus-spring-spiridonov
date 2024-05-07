package com.ferick.service

import com.ferick.model.dto.GenreDto
import reactor.core.publisher.Flux

interface GenreService {
    fun findAll(): Flux<GenreDto>
}
