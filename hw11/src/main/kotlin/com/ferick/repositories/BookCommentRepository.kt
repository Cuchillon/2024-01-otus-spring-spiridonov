package com.ferick.repositories

import com.ferick.model.entities.BookComment
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookCommentRepository : ReactiveMongoRepository<BookComment, String> {

    fun findByBookId(bookId: String): Flux<BookComment>

    fun deleteByBookId(bookId: String): Mono<Void>
}
