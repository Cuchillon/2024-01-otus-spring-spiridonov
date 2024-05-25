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
    val author: MongoAuthor,
    @DBRef(lazy = true)
    val genres: List<MongoGenre> = emptyList()
)
