package com.ferick.model.dto

import jakarta.validation.constraints.NotBlank

class UpdateBookCommentRequest(val id: Long, val bookId: Long) {
    @NotBlank(message = "Comment text must not be blank")
    var text: String? = null
}
