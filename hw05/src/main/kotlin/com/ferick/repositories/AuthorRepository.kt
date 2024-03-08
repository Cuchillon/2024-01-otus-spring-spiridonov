package com.ferick.repositories

import com.ferick.model.Author

interface AuthorRepository {
    fun findAll(): List<Author>
    fun findById(id: Long): Author?
}
