package com.ferick.converters

import com.ferick.model.dto.BookCommentDto
import com.ferick.model.entities.BookComment
import org.springframework.stereotype.Component

@Component
class BookCommentConverter(
    private val bookConverter: BookConverter
) {

    fun bookCommentToDto(bookComment: BookComment): BookCommentDto = BookCommentDto(
        id = bookComment.id!!,
        text = bookComment.text,
        book = bookConverter.bookToDto(bookComment.book)
    )
}
