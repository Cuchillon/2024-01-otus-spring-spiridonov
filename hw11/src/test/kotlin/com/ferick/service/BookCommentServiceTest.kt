package com.ferick.service

import com.ferick.converters.AuthorConverter
import com.ferick.converters.BookCommentConverter
import com.ferick.converters.BookConverter
import com.ferick.converters.GenreConverter
import com.ferick.model.dto.UpsertBookCommentRequest
import com.ferick.model.entities.Author
import com.ferick.model.entities.Book
import com.ferick.model.entities.BookComment
import com.ferick.model.entities.Genre
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.service.impl.BookCommentServiceImpl
import io.mockk.every
import io.mockk.mockk
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatException
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class BookCommentServiceTest {

    private val bookCommentRepository: BookCommentRepository = mockk()
    private val bookRepository: BookRepository = mockk()

    private val bookCommentService: BookCommentService = BookCommentServiceImpl(
        bookCommentRepository, bookRepository, bookCommentConverter
    )

    @Test
    fun insertSuccessfully() {
        val request = UpsertBookCommentRequest(
            text = randomText(),
            bookId = book.id!!
        )
        every { bookRepository.findById(any<String>()) } returns Mono.just(book)
        every { bookCommentRepository.save(any()) } returns Mono.just(bookComment)
        val bookCommentDto = bookCommentService.insert(request).block()!!
        assertThat(bookCommentDto.id).isEqualTo(bookComment.id)
        assertThat(bookCommentDto.text).isEqualTo(bookComment.text)
        assertThat(bookCommentDto.book).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun insertWithNonExistingBook() {
        val request = UpsertBookCommentRequest(
            text = randomText(),
            bookId = randomId()
        )
        every { bookRepository.findById(any<String>()) } returns Mono.empty()
        assertThatException().isThrownBy {
            bookCommentService.insert(request).block()
        }.withMessage("Book with id ${request.bookId} not found")
    }

    companion object {
        private val bookCommentConverter = BookCommentConverter(
            BookConverter(
                AuthorConverter(), GenreConverter()
            )
        )

        private val book = Book(
            id = randomId(),
            title = randomText(),
            author = Author(
                id = randomId(),
                fullName = randomText()
            ),
            genres = listOf(
                Genre(
                    id = randomId(),
                    name = randomText()
                )
            )
        )

        private val bookComment = BookComment(
            id = randomId(),
            text = randomText(),
            book = book
        )

        private fun randomId() = RandomStringUtils.randomAlphanumeric(8)
        private fun randomText() = RandomStringUtils.randomAlphabetic(8)
    }
}
