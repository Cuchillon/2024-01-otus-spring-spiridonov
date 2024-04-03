package com.ferick.model.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("books")
class Book(
    @Id
    var id: String? = null,
    val title: String,
    @DBRef(lazy = true)
    val author: Author,
    @DBRef(lazy = true)
    val genres: List<Genre> = emptyList()
)
