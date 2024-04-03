package com.ferick.repositories

import com.ferick.model.entities.Author
import org.springframework.data.mongodb.repository.MongoRepository

interface AuthorRepository : MongoRepository<Author, String>
