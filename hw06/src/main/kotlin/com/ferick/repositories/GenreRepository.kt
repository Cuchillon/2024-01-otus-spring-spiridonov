package com.ferick.repositories

import com.ferick.model.entities.Genre

interface GenreRepository {
    fun findAll(): List<Genre>
    fun findAllByIds(ids: Set<Long>): List<Genre>
}
