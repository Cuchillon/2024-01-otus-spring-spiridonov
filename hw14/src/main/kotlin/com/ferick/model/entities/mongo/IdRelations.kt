package com.ferick.model.entities.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("authorIds")
class AuthorIds(
    @Id
    var id: String? = null,
    val jpaId: Long,
    val mongoId: String
)

@Document("genreIds")
class GenreIds(
    @Id
    var id: String? = null,
    val jpaId: Long,
    val mongoId: String
)

@Document("bookIds")
class BookIds(
    @Id
    var id: String? = null,
    val jpaId: Long,
    val mongoId: String
)
