package com.ferick.repositories

import com.ferick.model.entities.Book
import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository : MongoRepository<Book, String>
