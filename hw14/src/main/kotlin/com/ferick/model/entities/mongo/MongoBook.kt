package com.ferick.model.entities.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("books")
class MongoBook(
    @Id
    var id: String? = null,
    val title: String,
    @DBRef(lazy = true)
    var author: MongoAuthor? = null,
    @DBRef(lazy = true)
    var genres: List<MongoGenre> = emptyList()
)
