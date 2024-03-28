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
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
    private val bookConverter: BookConverter
) : BookService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): BookDto? {
        val book = bookRepository.findById(id)
        return book?.let { bookConverter.bookToDto(it) }
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<BookDto> {
        val books = bookRepository.findAll()
        return books.map { bookConverter.bookToDto(it) }
    }

    @Transactional
    override fun insert(title: String, authorId: Long, genresIds: Set<Long>): BookDto {
        val book = save(title, authorId, genresIds)
        return bookConverter.bookToDto(book)
    }

    @Transactional
    override fun update(id: Long, title: String, authorId: Long, genresIds: Set<Long>): BookDto {
        val book = save(title, authorId, genresIds, id)
        return bookConverter.bookToDto(book)
    }

    @Transactional
    override fun deleteById(id: Long) {
        bookRepository.deleteById(id)
    }

    private fun save(title: String, authorId: Long, genresIds: Set<Long>, id: Long? = null): Book {
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }
        val author = authorRepository.findById(authorId)
            ?: throw EntityNotFoundException("Author with id $authorId not found")
        val genres = genreRepository.findAllByIds(genresIds)
        if (genres.isEmpty() || genresIds.size != genres.size) {
            throw EntityNotFoundException("One or all genres with ids $genresIds not found")
        }
        val book = Book(id, title, author, genres)
        return bookRepository.save(book)
    }
}
