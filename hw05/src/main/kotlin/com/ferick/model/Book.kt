package com.ferick.model

data class Book(
    val id: Long,
    val title: String,
    val author: Author,
    val genres: List<Genre>
)
