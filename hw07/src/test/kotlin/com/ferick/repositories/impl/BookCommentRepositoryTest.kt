package com.ferick.repositories.impl

import com.ferick.model.entities.BookComment
import com.ferick.repositories.BookCommentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class BookCommentRepositoryTest {

    @Autowired
    private lateinit var em: TestEntityManager

    @Autowired
    private lateinit var bookCommentRepository: BookCommentRepository

    @Test
    fun `should return correct book comments by book id`() {
        val bookId = 1L
        val expectedComments = commentIds
            .map { em.find(BookComment::class.java, it) }
            .filter { it.book.id == bookId }
        val actualComments = bookCommentRepository.findByBookId(bookId)
        actualComments.forEachIndexed { i, comment ->
            assertThat(comment).usingRecursiveComparison().isEqualTo(expectedComments[i])
        }
    }

    @Test
    fun `should return correct book comment by id`() {
        val bookCommentId = 1L
        val expectedComment = em.find(BookComment::class.java, bookCommentId)
        val actualComment = bookCommentRepository.findById(bookCommentId)
        assertThat(actualComment).isPresent()
        assertThat(actualComment.get())
            .usingRecursiveComparison().isEqualTo(expectedComment)
    }

    companion object {
        private val commentIds = listOf(1, 2, 3, 4, 5, 6)
    }
}
