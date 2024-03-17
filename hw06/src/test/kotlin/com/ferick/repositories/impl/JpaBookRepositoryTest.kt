package com.ferick.repositories.impl

import com.ferick.model.entities.Author
import com.ferick.model.entities.Book
import com.ferick.model.entities.Genre
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaBookRepository::class, JpaGenreRepository::class)
class JpaBookRepositoryTest {

    @Autowired
    private lateinit var em: TestEntityManager

    @Autowired
    private lateinit var bookRepository: JpaBookRepository

    @Test
    fun `should return correct book list`() {
        val actualBooks = bookRepository.findAll()
        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks)
    }

    @ParameterizedTest(name = "should return correct book by id")
    @MethodSource("bookProvider")
    fun `should return correct book by id`(expectedBook: Book) {
        val actualBook = bookRepository.findById(expectedBook.id!!)
        assertThat(actualBook)
            .isNotNull()
            .isEqualTo(expectedBook)
    }

    @Test
    fun `should save new book`() {
        val expectedBook = Book(
            id = 0L,
            title = "Sherlock Holmes and Secret Love",
            author = Author(id = 1L, fullName = "Conan Quasi-Doyle"),
            genres = mutableListOf(
                Genre(id = 2L, name = "Detective fiction"),
                Genre(id = 5L, name = "Erotic")
            )
        )
        val returnedBook = bookRepository.save(expectedBook)
        assertThat(returnedBook).isNotNull()
            .matches { it.id!! > 0 }
            .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook)

        assertThat(bookRepository.findById(returnedBook.id!!))
            .isNotNull()
            .isEqualTo(returnedBook)
    }

    @Test
    fun `should save updated book`() {
        val bookId = 1L
        val bookWithChanges = Book(
            id = bookId,
            title = "Sherlock Holmes and Secret Love",
            author = Author(id = 1L, fullName = "Conan Quasi-Doyle"),
            genres = mutableListOf(
                Genre(id = 2L, name = "Detective fiction"),
                Genre(id = 5L, name = "Erotic")
            )
        )
        val returnedBookBeforeUpdate = bookRepository.findById(bookId)
        val returnedBookAfterUpdate = bookRepository.save(bookWithChanges)

        assertThat(returnedBookBeforeUpdate)
            .isNotNull()
            .isNotEqualTo(bookWithChanges);

        assertThat(returnedBookAfterUpdate).isNotNull()
            .matches { it.id!! > 0 }
            .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(bookWithChanges)

        assertThat(bookRepository.findById(bookId))
            .isNotNull()
            .isEqualTo(returnedBookAfterUpdate)
    }

    @Test
    fun `should delete book`() {
        val bookId = 1L
        val returnedBookBeforeDelete = bookRepository.findById(bookId)
        bookRepository.deleteById(bookId)
        val returnedBookAfterDelete = bookRepository.findById(bookId)
        assertThat(returnedBookBeforeDelete).isNotNull()
        assertThat(returnedBookAfterDelete).isNull()
    }

    companion object {
        private val expectedBooks = listOf(
            Book(
                id = 1L,
                title = "Sherlock Holmes in Space",
                author = Author(id = 1L, fullName = "Conan Quasi-Doyle"),
                genres = mutableListOf(
                    Genre(id = 1L, name = "Science fiction"),
                    Genre(id = 2L, name = "Detective fiction")
                )
            ),
            Book(
                id = 2L,
                title = "Emmanuelle and the Night Chase",
                author = Author(id = 2L, fullName = "Emmanuelle Quasi-Arsan"),
                genres = mutableListOf(
                    Genre(id = 3L, name = "Thriller"),
                    Genre(id = 5L, name = "Erotic")
                )
            ),
            Book(
                id = 3L,
                title = "Dracula and the Big Dildo",
                author = Author(id = 3L, fullName = "Bram Quasi-Stoker"),
                genres = mutableListOf(
                    Genre(id = 4L, name = "Horror"),
                    Genre(id = 6L, name = "Porn")
                )
            )
        )

        @JvmStatic
        fun bookProvider() = expectedBooks
    }
}
