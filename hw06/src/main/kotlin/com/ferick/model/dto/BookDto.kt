package com.ferick.model.dto

import com.ferick.model.entities.Author
import com.ferick.model.entities.BookComment
import com.ferick.model.entities.Genre

data class BookDto(
    val id: Long,
    val title: String,
    val author: Author,
    val bookComments: List<BookComment>,
    val genres: List<Genre>
)
