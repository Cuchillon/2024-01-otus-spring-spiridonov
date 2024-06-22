package com.ferick.service

import com.ferick.model.dto.BookCommentDto
import com.ferick.model.dto.UpsertBookCommentRequest

interface BookCommentService {
    fun findById(id: Long): BookCommentDto
    fun findByBookId(bookId: Long): List<BookCommentDto>
    fun insert(request: UpsertBookCommentRequest): BookCommentDto
    fun update(id: Long, request: UpsertBookCommentRequest): BookCommentDto
    fun deleteById(id: Long)
}
