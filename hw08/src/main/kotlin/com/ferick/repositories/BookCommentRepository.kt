package com.ferick.repositories

import com.ferick.model.entities.BookComment
import org.springframework.data.mongodb.repository.MongoRepository

interface BookCommentRepository : MongoRepository<BookComment, String> {

    fun findByBookId(bookId: String): List<BookComment>

    fun deleteByBookId(bookId: String)
}
