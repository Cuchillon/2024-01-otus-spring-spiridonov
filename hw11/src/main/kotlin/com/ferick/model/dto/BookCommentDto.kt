package com.ferick.model.dto

data class BookCommentDto(
    val id: String,
    val text: String,
    val book: BookDto
)
