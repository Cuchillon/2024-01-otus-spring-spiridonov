package com.ferick.repositories.impl

import com.ferick.model.entities.Book
import com.ferick.repositories.BookRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private lateinit var em: TestEntityManager

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Test
    fun `should return correct book list`() {
        val expectedBooks = bookIds.map { em.find(Book::class.java, it) }
        val actualBooks = bookRepository.findAll()
        actualBooks.forEachIndexed { i, book ->
            assertThat(book).usingRecursiveComparison().isEqualTo(expectedBooks[i])
        }
    }

    @ParameterizedTest(name = "should return correct book by id {0}")
    @ValueSource(longs = [1L, 2L, 3L])
    fun `should return correct book by id`(expectedBookId: Long) {
        val expectedBook = em.find(Book::class.java, expectedBookId)
        val actualBook = bookRepository.findById(expectedBookId)
        assertThat(actualBook).isPresent()
        assertThat(actualBook.get())
            .usingRecursiveComparison().isEqualTo(expectedBook)
    }

    companion object {
        private val bookIds = listOf(1, 2, 3)
    }
}
