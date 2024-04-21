package com.ferick.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class UpsertBookRequest(
    @NotBlank(message = "Title must not be blank")
    val title: String,
    @Positive(message = "Author must be selected")
    val authorId: Long,
    @NotEmpty(message = "At least one genre must be selected")
    val genreIds: Set<Long>
)
