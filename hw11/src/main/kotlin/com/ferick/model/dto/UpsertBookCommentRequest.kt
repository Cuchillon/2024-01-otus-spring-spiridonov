package com.ferick.model.dto

import jakarta.validation.constraints.NotBlank

data class UpsertBookCommentRequest(
    @NotBlank(message = "Comment text must not be blank")
    val text: String,
    val bookId: String
)
