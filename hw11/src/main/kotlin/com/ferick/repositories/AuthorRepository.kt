package com.ferick.repositories

import com.ferick.model.entities.Author
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AuthorRepository : ReactiveMongoRepository<Author, String>
