package com.ferick.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.AuthorDto
import com.ferick.model.dto.BookDto
import com.ferick.model.dto.GenreDto
import com.ferick.model.dto.RestApiError
import com.ferick.model.dto.UpsertBookRequest
import com.ferick.service.BookService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(BookController::class)
class BookControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean
    private lateinit var bookService: BookService

    @Test
    fun getAllBooks() {
        every { bookService.findAll() } returns Flux.just(book)
        val books = webClient.get().uri("/book")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(BookDto::class.java).returnResult().responseBody!!
        assertThat(books).hasSize(1)
        assertThat(books[0]).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun getBookById() {
        every { bookService.findById(book.id) } returns Mono.just(book)
        val actualBook = webClient.get().uri("/book/${book.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(BookDto::class.java).returnResult().responseBody!!
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun createBook() {
        every { bookService.insert(any()) } returns Mono.just(book)
        val createdBook = webClient.post().uri("/book")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(BookDto::class.java).returnResult().responseBody!!
        assertThat(createdBook).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun updateBook() {
        every { bookService.update(any(), any()) } returns Mono.just(book)
        val updatedBook = webClient.put().uri("/book/${book.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(BookDto::class.java).returnResult().responseBody!!
        assertThat(updatedBook).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun deleteBook() {
        val slot = slot<String>()
        every { bookService.deleteById(capture(slot)) } returns Mono.empty()
        webClient.delete().uri("/book/${book.id}")
            .exchange()
            .expectStatus().isOk
        assertThat(slot.captured).isEqualTo(book.id)
    }

    @ParameterizedTest(name = "request validator throws exception")
    @MethodSource("requestProvider")
    fun bookRequestValidation(request: UpsertBookRequest, errorMessage: Pair<String, String>) {
        val restApiError = webClient.post().uri("/book")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(RestApiError::class.java).returnResult().responseBody!!
        assertThat(restApiError.statusCode).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(restApiError.errors[errorMessage.first]).isEqualTo(errorMessage.second)
    }

    @Test
    fun getNonExistingBook() {
        val bookId = RandomStringUtils.randomAlphabetic(8)
        val errorMessage = "Book not found"
        every { bookService.findById(bookId) } throws EntityNotFoundException(errorMessage)
        val restApiError = webClient.get().uri("/book/$bookId")
            .exchange()
            .expectStatus().isNotFound
            .expectBody(RestApiError::class.java).returnResult().responseBody!!
        assertThat(restApiError.statusCode).isEqualTo(HttpStatus.NOT_FOUND.value())
        assertThat(restApiError.errors["message"]).isEqualTo(errorMessage)
    }

    companion object {
        private val author = AuthorDto(
            RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(8)
        )
        private val genre = GenreDto(
            RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(8)
        )
        private val book = BookDto(
            id = RandomStringUtils.randomAlphabetic(8),
            title = RandomStringUtils.randomAlphabetic(8),
            author = author,
            genres = listOf(genre)
        )
        private val request = UpsertBookRequest().apply {
            title = book.title
            authorId = author.id
            genreIds = setOf(genre.id)
        }

        fun <T> T.objToString(): String = jacksonObjectMapper().writeValueAsString(this)
        inline fun <reified T> String.toObj(): T = jacksonObjectMapper().readValue<T>(this)

        @JvmStatic
        fun requestProvider() = listOf(
            Arguments.of(
                UpsertBookRequest().apply {
                    title = ""
                    authorId = author.id
                    genreIds = setOf(genre.id)
                }, "title" to "Title must not be blank"
            ),
            Arguments.of(
                UpsertBookRequest().apply {
                    title = book.title
                    authorId = ""
                    genreIds = setOf(genre.id)
                }, "authorId" to "Author must be selected"
            ),
            Arguments.of(
                UpsertBookRequest().apply {
                    title = book.title
                    authorId = author.id
                    genreIds = setOf()
                }, "genreIds" to "At least one genre must be selected"
            )
        )
    }
}
