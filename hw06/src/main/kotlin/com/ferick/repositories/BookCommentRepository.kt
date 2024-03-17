package com.ferick.repositories

import com.ferick.model.entities.BookComment

interface BookCommentRepository {
    fun findById(id: Long): BookComment?
    fun findByBookId(bookId: Long): List<BookComment>
    fun save(bookComment: BookComment): BookComment
    fun deleteById(id: Long)
}
