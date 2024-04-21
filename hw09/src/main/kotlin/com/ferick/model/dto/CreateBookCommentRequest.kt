package com.ferick.model.dto

import jakarta.validation.constraints.NotBlank

class CreateBookCommentRequest(val bookId: Long) {
    @NotBlank(message = "Comment text must not be blank")
    var text: String? = null
}
