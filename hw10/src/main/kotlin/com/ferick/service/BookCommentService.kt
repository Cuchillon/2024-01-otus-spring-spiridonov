package com.ferick.service

import com.ferick.model.dto.UpsertBookCommentRequest
import com.ferick.model.entities.BookComment

interface BookCommentService {
    fun findById(id: Long): BookComment
    fun findByBookId(bookId: Long): List<BookComment>
    fun insert(request: UpsertBookCommentRequest): BookComment
    fun update(id: Long, request: UpsertBookCommentRequest): BookComment
    fun deleteById(id: Long)
}
