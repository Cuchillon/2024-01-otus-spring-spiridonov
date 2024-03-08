package com.ferick.service

import com.ferick.model.Genre

interface GenreService {
    fun findAll(): List<Genre>
}
