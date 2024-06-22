package com.ferick.repositories

import com.ferick.model.entities.Genre
import org.springframework.data.jpa.repository.JpaRepository

interface GenreRepository : JpaRepository<Genre, Long>
