package com.ferick.model.entities.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("book_comments")
class BookComment(
    @Id
    var id: String? = null,
    var text: String,
    @DBRef(lazy = true)
    val book: MongoBook
)
