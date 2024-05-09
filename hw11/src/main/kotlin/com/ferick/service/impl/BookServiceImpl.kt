package com.ferick.service.impl

import com.ferick.converters.BookConverter
import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.BookDto
import com.ferick.model.dto.UpsertBookRequest
import com.ferick.model.entities.Book
import com.ferick.repositories.AuthorRepository
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import com.ferick.service.BookService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
    private val bookCommentRepository: BookCommentRepository,
    private val bookConverter: BookConverter
) : BookService {

    override fun findById(id: String): Mono<BookDto> {
        return bookRepository.findById(id)
            .map { bookConverter.bookToDto(it) }
            .switchIfEmpty { Mono.error(EntityNotFoundException("Book with id $id not found")) }
    }

    override fun findAll(): Flux<BookDto> {
        return bookRepository.findAll().map { bookConverter.bookToDto(it) }
    }

    override fun insert(request: UpsertBookRequest): Mono<BookDto> {
        return save(request.title!!, request.authorId!!, request.genreIds).map {bookConverter.bookToDto(it) }
    }

    override fun update(id: String, request: UpsertBookRequest): Mono<BookDto> {
        return save(request.title!!, request.authorId!!, request.genreIds, id).map {bookConverter.bookToDto(it) }
    }

    override fun deleteById(id: String): Mono<Void> {
        return bookCommentRepository.deleteByBookId(id).then(bookRepository.deleteById(id))
    }

    private fun save(title: String, authorId: String, genresIds: Set<String>, id: String? = null): Mono<Book> {
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }

        return authorRepository.findById(authorId)
            .switchIfEmpty {
                Mono.error(EntityNotFoundException("Author with id $authorId not found"))
            }.flatMap { author ->
                genreRepository.findAllById(genresIds).collectList().flatMap { genres ->
                    if (genres.isEmpty() || genresIds.size != genres.size) {
                        throw EntityNotFoundException("One or all genres with ids $genresIds not found")
                    }
                    val book = Book(id, title, author, genres)
                    bookRepository.save(book)
                }
            }
    }
}
