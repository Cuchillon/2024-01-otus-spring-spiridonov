package com.ferick.service

import com.ferick.model.dto.AuthorDto

interface AuthorService {
    fun findAll(): List<AuthorDto>
}
