package com.ferick.converters

import com.ferick.model.entities.BookComment
import org.springframework.stereotype.Component

@Component
class BookCommentConverter {

    fun commentToString(bookComment: BookComment): String = "Id: ${bookComment.id}, Text: ${bookComment.text}"
}
