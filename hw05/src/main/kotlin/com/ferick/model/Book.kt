package com.ferick.model

data class Book(
    var id: Long,
    val title: String,
    val author: Author,
    val genres: MutableList<Genre> = mutableListOf()
)
