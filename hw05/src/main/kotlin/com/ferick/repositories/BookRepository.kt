package com.ferick.repositories

import com.ferick.model.Book

interface BookRepository {
    fun findAll(): List<Book>
    fun findById(id: Long): Book?
    fun save(book: Book): Book
    fun deleteById(id: Long)
}
