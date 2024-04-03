package com.ferick.repositories.impl

import com.ferick.model.entities.Book
import com.ferick.repositories.BookCommentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate

@DataMongoTest
class BookCommentRepositoryTest {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var bookCommentRepository: BookCommentRepository

    @Test
    fun `should return correct book comments by book id`() {
        val books = mongoTemplate.findAll(Book::class.java, "books")
        val bookId = books.first { it.title == BOOK_TITLE }.id!!
        val actualComments = bookCommentRepository.findByBookId(bookId)
        assertThat(actualComments).hasSize(2)
        assertThat(actualComments.map { it.text }).containsExactlyElementsOf(expectedComments)
    }

    @Test
    fun `should delete book comments by book id`() {
        val books = mongoTemplate.findAll(Book::class.java, "books")
        val bookId = books.first { it.title == BOOK_TITLE }.id!!
        val actualCommentsBeforeDelete = bookCommentRepository.findByBookId(bookId)
        assertThat(actualCommentsBeforeDelete).hasSize(2)
        bookCommentRepository.deleteByBookId(bookId)
        val actualCommentsAfterDelete = bookCommentRepository.findByBookId(bookId)
        assertThat(actualCommentsAfterDelete).isEmpty()
    }

    companion object {
        private const val BOOK_TITLE = "Sherlock Holmes in Space"

        private val expectedComments = listOf(
            "Awesome book, like it",
            "Holmes in Space is something new"
        )
    }
}
