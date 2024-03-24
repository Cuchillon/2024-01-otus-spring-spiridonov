package com.ferick.model.dto

import com.ferick.model.entities.Author

data class BookDto(
    val id: Long,
    val title: String,
    val author: Author,
    val genres: List<GenreDto>
)
