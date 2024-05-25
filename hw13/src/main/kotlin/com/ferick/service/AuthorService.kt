package com.ferick.service

import com.ferick.model.entities.Author

interface AuthorService {
    fun findAll(): List<Author>
}
