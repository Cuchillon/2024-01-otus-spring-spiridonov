package com.ferick.service

import com.ferick.model.dto.BookCommentDto
import com.ferick.model.dto.UpsertBookCommentRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookCommentService {
    fun findById(id: String): Mono<BookCommentDto>
    fun findByBookId(bookId: String): Flux<BookCommentDto>
    fun insert(request: UpsertBookCommentRequest): Mono<BookCommentDto>
    fun update(id: String, request: UpsertBookCommentRequest): Mono<BookCommentDto>
    fun deleteById(id: String): Mono<Void>
}
