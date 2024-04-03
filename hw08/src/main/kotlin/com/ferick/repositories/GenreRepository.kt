package com.ferick.repositories

import com.ferick.model.entities.Genre
import org.springframework.data.mongodb.repository.MongoRepository

interface GenreRepository : MongoRepository<Genre, String>
