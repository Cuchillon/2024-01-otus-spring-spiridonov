package com.ferick.model.entities.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("genres")
class MongoGenre(
    @Id
    var id: String? = null,
    val name: String
)
