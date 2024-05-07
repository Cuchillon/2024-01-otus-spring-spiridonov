package com.ferick.service

import com.ferick.model.dto.BookDto
import com.ferick.model.dto.UpsertBookRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookService {
    fun findById(id: String): Mono<BookDto>
    fun findAll(): Flux<BookDto>
    fun insert(request: UpsertBookRequest): Mono<BookDto>
    fun update(id: String, request: UpsertBookRequest): Mono<BookDto>
    fun deleteById(id: String): Mono<Void>
}
