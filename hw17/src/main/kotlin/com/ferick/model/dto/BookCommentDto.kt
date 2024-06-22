package com.ferick.model.dto

data class BookCommentDto(
    val id: Long,
    val text: String,
    val book: BookDto
)
