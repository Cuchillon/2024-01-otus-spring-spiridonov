package com.ferick.model.dto

data class BookAllRelations(
    val authorId: String,
    val genreIds: List<String>
)
