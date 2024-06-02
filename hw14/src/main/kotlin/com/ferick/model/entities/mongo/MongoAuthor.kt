package com.ferick.model.entities.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("authors")
class MongoAuthor(
    @Id
    var id: String? = null,
    val fullName: String
)
