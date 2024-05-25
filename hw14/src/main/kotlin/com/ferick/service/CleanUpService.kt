package com.ferick.service

import com.ferick.model.entities.mongo.AuthorIds
import com.ferick.model.entities.mongo.BookIds
import com.ferick.model.entities.mongo.GenreIds
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Service

@Service
class CleanUpService(
    private val mongoOperations: MongoOperations
) {

    fun cleanUp() {
        mongoOperations.dropCollection(BookIds::class.java)
        mongoOperations.dropCollection(GenreIds::class.java)
        mongoOperations.dropCollection(AuthorIds::class.java)
    }
}
