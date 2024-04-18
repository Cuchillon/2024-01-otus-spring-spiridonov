package com.ferick.service

import com.ferick.model.dto.CreateBookCommentRequest
import com.ferick.model.dto.UpdateBookCommentRequest
import com.ferick.model.entities.BookComment

interface BookCommentService {
    fun findById(id: Long): BookComment?
    fun findByBookId(bookId: Long): List<BookComment>
    fun insert(request: CreateBookCommentRequest): BookComment
    fun update(request: UpdateBookCommentRequest): BookComment
    fun deleteById(id: Long)
}
