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
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
class ControllerAuthorizationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @ParameterizedTest
    @MethodSource("userEndpointProvider")
    @WithMockUser(username = "user", password = "password", roles = ["USER"])
    fun `endpoints open to user answer successfully with user credentials`(
        requestBuilder: MockHttpServletRequestBuilder
    ) {
        val actualStatus = mockMvc.perform(requestBuilder).andReturn().response.status
        assertThat(actualStatus).isIn(HttpStatus.OK.value(), HttpStatus.FOUND.value())
    }

    @ParameterizedTest
    @MethodSource("adminEndpointProvider")
    @WithMockUser(username = "user", password = "password", roles = ["USER"])
    fun `endpoints closed to user return forbidden with user credentials`(
        requestBuilder: MockHttpServletRequestBuilder
    ) {
        val actualStatus = mockMvc.perform(requestBuilder).andReturn().response.status
        assertThat(actualStatus).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @ParameterizedTest
    @MethodSource("allEndpointProvider")
    @WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
    fun `all endpoints answer successfully with admin credentials`(
        requestBuilder: MockHttpServletRequestBuilder
    ) {
        val actualStatus = mockMvc.perform(requestBuilder).andReturn().response.status
        assertThat(actualStatus).isIn(HttpStatus.OK.value(), HttpStatus.FOUND.value())
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
        fun allEndpointProvider() = userEndpointProvider() + adminEndpointProvider()

        @JvmStatic
        fun userEndpointProvider() = listOf(
            Arguments.of(MockMvcRequestBuilders.get("/")),
            Arguments.of(MockMvcRequestBuilders.get("/authors")),
            Arguments.of(MockMvcRequestBuilders.get("/genres")),
            Arguments.of(MockMvcRequestBuilders.get("/books")),
            Arguments.of(MockMvcRequestBuilders.get("/book-details/${book.id}")),
            Arguments.of(MockMvcRequestBuilders.get("/comment/${book.id}")),
            Arguments.of(MockMvcRequestBuilders.get("/comment/${book.id}/${bookComment.id}")),
            Arguments.of(
                MockMvcRequestBuilders.post("/comment/${bookComment.id}/delete")
                    .with(csrf())
            )
        )

        @JvmStatic
        fun adminEndpointProvider() = listOf(
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
            )
        )
    }
}
