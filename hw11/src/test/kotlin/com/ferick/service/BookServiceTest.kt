package com.ferick.service

import com.ferick.converters.AuthorConverter
import com.ferick.converters.BookConverter
import com.ferick.converters.GenreConverter
import com.ferick.model.dto.UpsertBookRequest
import com.ferick.model.entities.Author
import com.ferick.model.entities.Book
import com.ferick.model.entities.Genre
import com.ferick.repositories.AuthorRepository
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import com.ferick.service.impl.BookServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatException
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class BookServiceTest {

    private val authorRepository: AuthorRepository = mockk()
    private val genreRepository: GenreRepository = mockk()
    private val bookRepository: BookRepository = mockk()
    private val bookCommentRepository: BookCommentRepository = mockk()

    private val bookService: BookService = BookServiceImpl(
        authorRepository, genreRepository, bookRepository, bookCommentRepository, bookConverter
    )

    @Test
    fun insertSuccessfully() {
        val request = UpsertBookRequest().apply {
            title = randomText()
            authorId = author.id
            genreIds = setOf(genre.id!!)
        }
        every { authorRepository.findById(any<String>()) } returns Mono.just(author)
        every { genreRepository.findAllById(any<Set<String>>()) } returns Flux.just(genre)
        every { bookRepository.save(any()) } returns Mono.just(book)
        val bookDto = bookService.insert(request).block()!!
        assertThat(bookDto.id).isEqualTo(book.id)
        assertThat(bookDto.title).isEqualTo(book.title)
        assertThat(bookDto.author.id).isEqualTo(author.id)
        assertThat(bookDto.author.fullName).isEqualTo(author.fullName)
        assertThat(bookDto.genres).hasSize(1)
        assertThat(bookDto.genres[0].id).isEqualTo(genre.id)
        assertThat(bookDto.genres[0].name).isEqualTo(genre.name)
    }

    @Test
    fun insertWithNonExistingAuthor() {
        val request = UpsertBookRequest().apply {
            title = randomText()
            authorId = randomId()
            genreIds = setOf(genre.id!!)
        }
        every { authorRepository.findById(any<String>()) } returns Mono.empty()
        assertThatException().isThrownBy {
            bookService.insert(request).block()
        }.withMessage("Author with id ${request.authorId} not found")
    }

    @Test
    fun insertWithEmptyGenres() {
        val request = UpsertBookRequest().apply {
            title = randomText()
            authorId = randomId()
        }
        assertThatException().isThrownBy {
            bookService.insert(request).block()
        }.withMessage("Genres ids must not be empty")
    }

    @Test
    fun insertWithNonExistingGenres() {
        val request = UpsertBookRequest().apply {
            title = randomText()
            authorId = randomId()
            genreIds = setOf(randomId())
        }
        every { authorRepository.findById(any<String>()) } returns Mono.just(author)
        every { genreRepository.findAllById(any<Set<String>>()) } returns Flux.empty()
        assertThatException().isThrownBy {
            bookService.insert(request).block()
        }.withMessage("One or all genres with ids ${request.genreIds} not found")
    }

    @Test
    fun insertWithWrongNumberOfGenres() {
        val request = UpsertBookRequest().apply {
            title = randomText()
            authorId = randomId()
            genreIds = setOf(genre.id!!, randomId())
        }
        every { authorRepository.findById(any<String>()) } returns Mono.just(author)
        every { genreRepository.findAllById(any<Set<String>>()) } returns Flux.just(genre)
        assertThatException().isThrownBy {
            bookService.insert(request).block()
        }.withMessage("One or all genres with ids ${request.genreIds} not found")
    }

    @Test
    fun deleteById() {
        every { bookRepository.deleteById(any<String>()) } returns Mono.empty()
        every { bookCommentRepository.deleteByBookId(any()) } returns Mono.empty()
        bookService.deleteById(randomId()).block()
        verify(exactly = 1) {
            bookCommentRepository.deleteByBookId(any())
            bookRepository.deleteById(any<String>())
        }
    }

    companion object {
        private val bookConverter = BookConverter(AuthorConverter(), GenreConverter())

        private val author = Author(
            id = randomId(),
            fullName = randomText()
        )

        private val genre = Genre(
            id = randomId(),
            name = randomText()
        )

        private val book = Book(
            id = randomId(),
            title = randomText(),
            author = author,
            genres = listOf(genre)
        )

        private fun randomId() = RandomStringUtils.randomAlphanumeric(8)
        private fun randomText() = RandomStringUtils.randomAlphabetic(8)
    }
}
