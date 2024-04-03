package com.ferick.service.impl

import com.ferick.converters.BookConverter
import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.BookDto
import com.ferick.model.entities.Book
import com.ferick.repositories.AuthorRepository
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import com.ferick.service.BookService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
    private val bookConverter: BookConverter
) : BookService {

    override fun findById(id: String): BookDto? {
        val book = bookRepository.findById(id).getOrNull()
        return book?.let { bookConverter.bookToDto(it) }
    }

    override fun findAll(): List<BookDto> {
        val books = bookRepository.findAll()
        return books.map { bookConverter.bookToDto(it) }
    }

    override fun insert(title: String, authorId: String, genresIds: Set<String>): BookDto {
        val book = save(title, authorId, genresIds)
        return bookConverter.bookToDto(book)
    }

    override fun update(id: String, title: String, authorId: String, genresIds: Set<String>): BookDto {
        val book = save(title, authorId, genresIds, id)
        return bookConverter.bookToDto(book)
    }

    override fun deleteById(id: String) {
        bookRepository.deleteById(id)
    }

    private fun save(title: String, authorId: String, genresIds: Set<String>, id: String? = null): Book {
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
}
