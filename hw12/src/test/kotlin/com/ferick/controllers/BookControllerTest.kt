package com.ferick.controllers

import com.ferick.model.dto.BookDto
import com.ferick.model.dto.GenreDto
import com.ferick.model.entities.Author
import com.ferick.service.AuthorService
import com.ferick.service.BookService
import com.ferick.service.GenreService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

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
    @WithMockUser(username = "user", password = "password")
    fun `get all books with correct credentials successfully`() {
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
    fun `get all books without credentials returns unauthorized`() {
        every { bookService.findAll() } returns listOf(book)
        mockMvc.get("/books").andExpect {
            status {
                isUnauthorized()
            }
        }
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
