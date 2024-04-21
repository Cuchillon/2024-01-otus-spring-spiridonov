package com.ferick.controllers

import com.ferick.model.dto.BookDto
import com.ferick.model.dto.CreateBookRequest
import com.ferick.model.dto.GenreDto
import com.ferick.model.dto.UpdateBookRequest
import com.ferick.model.entities.Author
import com.ferick.service.AuthorService
import com.ferick.service.BookService
import com.ferick.service.GenreService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import io.mockk.slot
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(BookController::class)
class BookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var bookService: BookService

    @MockkBean
    private lateinit var authorService: AuthorService

    @MockkBean
    private lateinit var genreService: GenreService

    @Test
    fun getAllBooks() {
        every { bookService.findAll() } returns listOf(book)
        val modelAndView = mockMvc.get("/books").andExpect {
            status {
                isOk()
            }
        }.andReturn().modelAndView
        assertThat(modelAndView!!.model).containsKey("books")
        assertThat((modelAndView.model["books"] as List<*>).first()).isEqualTo(book)
        assertThat(modelAndView.viewName).isEqualTo("books")
    }

    @Test
    fun addBook() {
        every { authorService.findAll() } returns listOf(author)
        every { genreService.findAll() } returns listOf(genre)
        val modelAndView = mockMvc.get("/book").andExpect {
            status {
                isOk()
            }
        }.andReturn().modelAndView
        assertThat(modelAndView!!.model).containsKeys("genres", "authors", "book")
        assertThat((modelAndView.model["genres"] as List<*>).first()).isEqualTo(genre)
        assertThat((modelAndView.model["authors"] as List<*>).first()).isEqualTo(author)
        assertThat((modelAndView.model["book"])).usingRecursiveComparison().isEqualTo(CreateBookRequest())
        assertThat(modelAndView.viewName).isEqualTo("add_book")
    }

    @Test
    fun updateBook() {
        val bookId = 1L
        every { authorService.findAll() } returns listOf(author)
        every { genreService.findAll() } returns listOf(genre)
        val modelAndView = mockMvc.get("/book/$bookId").andExpect {
            status {
                isOk()
            }
        }.andReturn().modelAndView
        assertThat(modelAndView!!.model).containsKeys("genres", "authors", "book")
        assertThat((modelAndView.model["genres"] as List<*>).first()).isEqualTo(genre)
        assertThat((modelAndView.model["authors"] as List<*>).first()).isEqualTo(author)
        assertThat((modelAndView.model["book"])).usingRecursiveComparison().isEqualTo(UpdateBookRequest(bookId))
        assertThat(modelAndView.viewName).isEqualTo("update_book")
    }

    @Test
    fun deleteBook() {
        val bookId = 1L
        val slot = slot<Long>()
        justRun { bookService.deleteById(capture(slot)) }
        val status = mockMvc.perform(post("/book/$bookId/delete")).andReturn().response.status
        assertThat(status).isEqualTo(302)
        assertThat(slot.captured).isEqualTo(bookId)
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
    }
}
