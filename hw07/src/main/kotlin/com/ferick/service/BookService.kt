package com.ferick.service

import com.ferick.model.dto.BookDto

interface BookService {
    fun findById(id: Long): BookDto?
    fun findAll(): List<BookDto>
    fun insert(title: String, authorId: Long, genresIds: Set<Long>): BookDto
    fun update(id: Long, title: String, authorId: Long, genresIds: Set<Long>): BookDto
    fun deleteById(id: Long)
}
