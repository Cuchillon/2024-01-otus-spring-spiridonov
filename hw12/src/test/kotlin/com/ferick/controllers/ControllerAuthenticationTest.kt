package com.ferick.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ferick.model.dto.BookDto
import com.ferick.model.dto.CreateBookRequest
import com.ferick.model.dto.GenreDto
import com.ferick.model.dto.UpdateBookRequest
import com.ferick.model.entities.Author
import com.ferick.model.entities.Book
import com.ferick.model.entities.BookComment
import com.ferick.model.entities.Genre
import com.ferick.service.AuthorService
import com.ferick.service.BookCommentService
import com.ferick.service.BookService
import com.ferick.service.GenreService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@WebMvcTest(
    MainPageController::class,
    AuthorController::class,
    GenreController::class,
    BookController::class,
    BookDetailsController::class,
    BookCommentController::class
)
class ControllerAuthenticationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var authorService: AuthorService

    @MockkBean
    private lateinit var genreService: GenreService

    @MockkBean
    private lateinit var bookService: BookService

    @MockkBean
    private lateinit var bookCommentService: BookCommentService

    @ParameterizedTest
    @MethodSource("requestBuilderProvider")
    @WithMockUser(username = "user", password = "password")
    fun `requests with correct credentials run successfully`(requestBuilder: MockHttpServletRequestBuilder) {
        every { authorService.findAll() } returns listOf(author)
        every { genreService.findAll() } returns listOf(genre)
        every { bookService.findAll() } returns listOf(book)
        every { bookService.findById(any()) } returns book
        every { bookService.insert(any()) } returns book
        every { bookService.update(any()) } returns book
        justRun { bookService.deleteById(any()) }
        every { bookCommentService.findByBookId(any()) } returns listOf(bookComment)
        every { bookCommentService.findById(any()) } returns bookComment
        every { bookCommentService.insert(any()) } returns bookComment
        every { bookCommentService.update(any()) } returns bookComment
        justRun { bookCommentService.deleteById(any()) }

        val actualStatus = mockMvc.perform(requestBuilder).andReturn().response.status
        assertThat(actualStatus).isIn(HttpStatus.OK.value(), HttpStatus.FOUND.value())
    }

    @ParameterizedTest
    @MethodSource("requestBuilderProvider")
    fun `requests without credentials return unauthorized`(requestBuilder: MockHttpServletRequestBuilder) {
        val actualStatus = mockMvc.perform(requestBuilder).andReturn().response.status
        assertThat(actualStatus).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    companion object {
        private val author = Author(1L, RandomStringUtils.randomAlphabetic(8))
        private val genre = GenreDto(1L, RandomStringUtils.randomAlphabetic(8))
        private val book = BookDto(
            id = 1L,
            title = RandomStringUtils.randomAlphabetic(8),
            author = author,
            genres = listOf(genre)
        )
        private val bookComment = BookComment(
            id = 1L,
            text = RandomStringUtils.randomAlphabetic(8),
            book = Book(book.id, book.title, author, listOf(Genre(genre.id, genre.name)))
        )

        private val createBookRequest = CreateBookRequest().apply {
            title = RandomStringUtils.randomAlphabetic(8)
            authorId = author.id
            genreIds = setOf(genre.id)
        }

        private val updateBookRequest = UpdateBookRequest(book.id).apply {
            title = RandomStringUtils.randomAlphabetic(8)
            authorId = author.id
            genreIds = setOf(genre.id)
        }

        private fun <T> T.objToString(): String = jacksonObjectMapper().writeValueAsString(this)

        @JvmStatic
        fun requestBuilderProvider() = listOf(
            Arguments.of(MockMvcRequestBuilders.get("/")),
            Arguments.of(MockMvcRequestBuilders.get("/authors")),
            Arguments.of(MockMvcRequestBuilders.get("/genres")),
            Arguments.of(MockMvcRequestBuilders.get("/books")),
            Arguments.of(MockMvcRequestBuilders.get("/book")),
            Arguments.of(MockMvcRequestBuilders.get("/book/${book.id}")),
            Arguments.of(
                MockMvcRequestBuilders.post("/book")
                    .with(csrf())
                    .content(createBookRequest.objToString())
            ),
            Arguments.of(
                MockMvcRequestBuilders.post("/book/${book.id}")
                    .with(csrf())
                    .content(updateBookRequest.objToString())
            ),
            Arguments.of(
                MockMvcRequestBuilders.post("/book/${book.id}/delete")
                    .with(csrf())
            ),
            Arguments.of(MockMvcRequestBuilders.get("/book-details/${book.id}")),
            Arguments.of(MockMvcRequestBuilders.get("/comment-details/${bookComment.id}")),
            Arguments.of(MockMvcRequestBuilders.get("/comment/${book.id}")),
            Arguments.of(MockMvcRequestBuilders.get("/comment/${book.id}/${bookComment.id}")),
            Arguments.of(
                MockMvcRequestBuilders.post("/comment/${bookComment.id}/delete")
                    .with(csrf())
            )
        )
    }
}
