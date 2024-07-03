package com.ferick.service.impl

import com.ferick.converters.BookCommentConverter
import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.BookCommentDto
import com.ferick.model.dto.UpsertBookCommentRequest
import com.ferick.model.entities.BookComment
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.service.BookCommentService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CircuitBreaker(name = BookCommentServiceImpl.CIRCUIT_BREAKER_NAME)
class BookCommentServiceImpl(
    private val bookCommentRepository: BookCommentRepository,
    private val bookRepository: BookRepository,
    private val bookCommentConverter: BookCommentConverter
) : BookCommentService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): BookCommentDto {
        val bookComment = bookCommentRepository.findById(id).orElseThrow {
            EntityNotFoundException("Book comment with id $id not found")
        }
        return bookCommentConverter.bookCommentToDto(bookComment)
    }

    @Transactional(readOnly = true)
    override fun findByBookId(bookId: Long): List<BookCommentDto> {
        if (bookRepository.findById(bookId).isEmpty) {
            throw EntityNotFoundException("Book with id $bookId not found")
        }
        return bookCommentRepository.findByBookId(bookId).map { bookCommentConverter.bookCommentToDto(it) }
    }

    @Transactional
    override fun insert(request: UpsertBookCommentRequest): BookCommentDto {
        val savedComment = save(request.text, request.bookId)
        return bookCommentConverter.bookCommentToDto(savedComment)
    }

    @Transactional
    override fun update(id: Long, request: UpsertBookCommentRequest): BookCommentDto {
        val savedComment = save(request.text, request.bookId, id)
        return bookCommentConverter.bookCommentToDto(savedComment)
    }

    @Transactional
    override fun deleteById(id: Long) {
        bookCommentRepository.deleteById(id)
    }

    private fun save(text: String, bookId: Long, id: Long? = null): BookComment {
        val book = bookRepository.findById(bookId).orElseThrow {
            EntityNotFoundException("Book with id $bookId not found")
        }
        val bookComment = BookComment(id, text, book)
        return bookCommentRepository.save(bookComment)
    }

    companion object {
        const val CIRCUIT_BREAKER_NAME = "comments"
    }
}
