package com.ferick.service

import com.ferick.model.entities.Genre

interface GenreService {
    fun findAll(): List<Genre>
}
