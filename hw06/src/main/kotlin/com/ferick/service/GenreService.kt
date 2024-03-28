package com.ferick.service

import com.ferick.model.dto.GenreDto

interface GenreService {
    fun findAll(): List<GenreDto>
}
