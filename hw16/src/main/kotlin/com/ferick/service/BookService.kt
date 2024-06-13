package com.ferick.service

import com.ferick.model.dto.BookDto
import com.ferick.model.dto.UpsertBookRequest

interface BookService {
    fun findById(id: Long): BookDto
    fun findAll(): List<BookDto>
    fun insert(request: UpsertBookRequest): BookDto
    fun update(id: Long, request: UpsertBookRequest): BookDto
    fun deleteById(id: Long)
}
