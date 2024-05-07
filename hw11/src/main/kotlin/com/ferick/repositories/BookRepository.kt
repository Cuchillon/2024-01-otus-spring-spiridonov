package com.ferick.repositories

import com.ferick.model.entities.Book
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface BookRepository : ReactiveMongoRepository<Book, String>
