package com.ferick.service

import com.ferick.model.Author

interface AuthorService {
    fun findAll(): List<Author>
}
