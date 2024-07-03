package com.ferick.service.impl

import com.ferick.converters.BookConverter
import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.BookDto
import com.ferick.model.dto.UpsertBookRequest
import com.ferick.model.entities.Book
import com.ferick.repositories.AuthorRepository
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import com.ferick.service.BookService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CircuitBreaker(name = BookServiceImpl.CIRCUIT_BREAKER_NAME)
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
    private val bookConverter: BookConverter
) : BookService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): BookDto {
        val book = bookRepository.findById(id).orElseThrow {
            EntityNotFoundException("Book with id $id not found")
        }
        return bookConverter.bookToDto(book)
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<BookDto> {
        val books = bookRepository.findAll()
        return books.map { bookConverter.bookToDto(it) }
    }

    @Transactional
    override fun insert(request: UpsertBookRequest): BookDto {
        val book = save(request.title!!, request.authorId!!, request.genreIds)
        return bookConverter.bookToDto(book)
    }

    @Transactional
    override fun update(id: Long, request: UpsertBookRequest): BookDto {
        val book = save(request.title!!, request.authorId!!, request.genreIds, id)
        return bookConverter.bookToDto(book)
    }

    @Transactional
    override fun deleteById(id: Long) {
        bookRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    override fun count(): Long {
        return bookRepository.count()
    }

    private fun save(title: String, authorId: Long, genresIds: Set<Long>, id: Long? = null): Book {
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }
        val author = authorRepository.findById(authorId).orElseThrow {
            EntityNotFoundException("Author with id $authorId not found")
        }
        val genres = genreRepository.findAllById(genresIds)
        if (genres.isEmpty() || genresIds.size != genres.size) {
            throw EntityNotFoundException("One or all genres with ids $genresIds not found")
        }
        val book = Book(id, title, author, genres)
        return bookRepository.save(book)
    }

    companion object {
        const val CIRCUIT_BREAKER_NAME = "books"
    }
}
