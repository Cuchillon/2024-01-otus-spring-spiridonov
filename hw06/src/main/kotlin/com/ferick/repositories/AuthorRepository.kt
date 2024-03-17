package com.ferick.repositories

import com.ferick.model.entities.Author

interface AuthorRepository {
    fun findAll(): List<Author>
    fun findById(id: Long): Author?
}
