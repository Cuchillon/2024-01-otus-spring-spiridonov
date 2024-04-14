package com.ferick.service

import com.ferick.model.entities.BookComment

interface BookCommentService {
    fun findById(id: String): BookComment?
    fun findByBookId(bookId: String): List<BookComment>
    fun insert(text: String, bookId: String): BookComment
    fun update(id: String, text: String, bookId: String): BookComment
    fun deleteById(id: String)
}
