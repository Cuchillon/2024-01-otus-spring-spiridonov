package com.ferick.service.impl

import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.Book
import com.ferick.repositories.AuthorRepository
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import com.ferick.service.BookService
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository
) : BookService {

    override fun findById(id: Long): Book? {
        return bookRepository.findById(id)
    }

    override fun findAll(): List<Book> {
        return bookRepository.findAll()
    }

    override fun insert(title: String, authorId: Long, genresIds: Set<Long>): Book {
        return save(0, title, authorId, genresIds)
    }

    override fun update(id: Long, title: String, authorId: Long, genresIds: Set<Long>): Book {
        return save(id, title, authorId, genresIds)
    }

    override fun deleteById(id: Long) {
        bookRepository.deleteById(id)
    }

    private fun save(id: Long, title: String, authorId: Long, genresIds: Set<Long>): Book {
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }
        val author = authorRepository.findById(authorId)
            ?: throw EntityNotFoundException("Author with id $authorId not found")
        val genres = genreRepository.findAllByIds(genresIds)
        if (genres.isEmpty() || genresIds.size != genres.size) {
            throw EntityNotFoundException("One or all genres with ids $genresIds not found")
        }
        val book = Book(id, title, author, genres.toMutableList())
        return bookRepository.save(book)
    }
}
