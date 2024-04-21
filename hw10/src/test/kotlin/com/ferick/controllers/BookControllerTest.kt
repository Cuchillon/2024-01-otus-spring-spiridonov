package com.ferick.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ferick.model.dto.BookDto
import com.ferick.model.dto.GenreDto
import com.ferick.model.dto.UpsertBookRequest
import com.ferick.model.entities.Author
import com.ferick.service.BookService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import io.mockk.slot
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@WebMvcTest(BookController::class)
class BookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var bookService: BookService

    @Test
    fun getAllBooks() {
        every { bookService.findAll() } returns listOf(book)
        val response = mockMvc.get("/book").andExpect {
            status {
                isOk()
            }
        }.andReturn().response
        val books = jacksonObjectMapper().readValue<List<BookDto>>(response.contentAsString)
        assertThat(books).hasSize(1)
        assertThat(books[0]).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun getBookById() {
        every { bookService.findById(book.id) } returns book
        val response = mockMvc.get("/book/${book.id}").andExpect {
            status {
                isOk()
            }
        }.andReturn().response
        val actualBook = jacksonObjectMapper().readValue<BookDto>(response.contentAsString)
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun createBook() {
        every { bookService.insert(request) } returns book
        val response = mockMvc.perform(
            post("/book")
                .content(request.objToString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().response
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.contentAsString.toObj<BookDto>()).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun updateBook() {
        every { bookService.update(book.id, request) } returns book
        val response = mockMvc.perform(
            put("/book/${book.id}")
                .content(request.objToString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().response
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.contentAsString.toObj<BookDto>()).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun deleteBook() {
        val slot = slot<Long>()
        justRun { bookService.deleteById(capture(slot)) }
        val status = mockMvc.perform(delete("/book/${book.id}")).andReturn().response.status
        assertThat(status).isEqualTo(HttpStatus.OK.value())
        assertThat(slot.captured).isEqualTo(book.id)
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
        private val request = UpsertBookRequest(
            title = book.title,
            authorId = author.id!!,
            genreIds = setOf(genre.id)
        )

        fun <T> T.objToString(): String = jacksonObjectMapper().writeValueAsString(this)
        inline fun <reified T> String.toObj(): T = jacksonObjectMapper().readValue<T>(this)
    }
}
