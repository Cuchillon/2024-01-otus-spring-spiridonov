package com.ferick.model.entities.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("book_comments")
class MongoBookComment(
    @Id
    var id: String? = null,
    var text: String,
    @DBRef(lazy = true)
    var book: MongoBook? = null
)
