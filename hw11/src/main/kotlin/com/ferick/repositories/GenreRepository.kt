package com.ferick.repositories

import com.ferick.model.entities.Genre
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface GenreRepository : ReactiveMongoRepository<Genre, String>
