package com.ferick.model.dto

import com.ferick.model.entities.Author
import com.ferick.model.entities.BookComment

data class BookDto(
    val id: Long,
    val title: String,
    val author: Author,
    val bookComments: List<BookComment>,
    val genres: List<GenreDto>
)
