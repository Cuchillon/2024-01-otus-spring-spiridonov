package com.ferick.service

import com.ferick.model.dto.BookDto
import com.ferick.model.dto.CreateBookRequest
import com.ferick.model.dto.UpdateBookRequest

interface BookService {
    fun findById(id: Long): BookDto?
    fun findAll(): List<BookDto>
    fun insert(request: CreateBookRequest): BookDto
    fun update(request: UpdateBookRequest): BookDto
    fun deleteById(id: Long)
}
