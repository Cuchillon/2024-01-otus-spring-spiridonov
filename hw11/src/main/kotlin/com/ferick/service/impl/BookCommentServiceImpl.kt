package com.ferick.service.impl

import com.ferick.converters.BookCommentConverter
import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.BookCommentDto
import com.ferick.model.dto.UpsertBookCommentRequest
import com.ferick.model.entities.BookComment
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.service.BookCommentService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class BookCommentServiceImpl(
    private val bookCommentRepository: BookCommentRepository,
    private val bookRepository: BookRepository,
    private val bookCommentConverter: BookCommentConverter
) : BookCommentService {

    override fun findById(id: String): Mono<BookCommentDto> {
        return bookCommentRepository.findById(id)
            .map { bookCommentConverter.bookCommentToDto(it) }
            .switchIfEmpty { Mono.error(EntityNotFoundException("Book comment with id $id not found")) }
    }

    override fun findByBookId(bookId: String): Flux<BookCommentDto> {
        if (bookRepository.findById(bookId).blockOptional().isEmpty) {
            throw EntityNotFoundException("Book with id $bookId not found")
        }
        return bookCommentRepository.findByBookId(bookId).map { bookCommentConverter.bookCommentToDto(it) }
    }

    override fun insert(request: UpsertBookCommentRequest): Mono<BookCommentDto> {
        return save(request.text, request.bookId).map { bookCommentConverter.bookCommentToDto(it) }
    }

    override fun update(id: String, request: UpsertBookCommentRequest): Mono<BookCommentDto> {
        return save(request.text, request.bookId, id).map { bookCommentConverter.bookCommentToDto(it) }
    }

    override fun deleteById(id: String): Mono<Void> {
        return bookCommentRepository.deleteById(id)
    }

    private fun save(text: String, bookId: String, id: String? = null): Mono<BookComment> {
        val book = bookRepository.findById(bookId).blockOptional().orElseThrow {
            EntityNotFoundException("Book with id $bookId not found")
        }
        val bookComment = BookComment(id, text, book)
        return bookCommentRepository.save(bookComment)
    }
}
