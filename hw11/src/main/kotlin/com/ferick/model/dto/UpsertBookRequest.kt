package com.ferick.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

class UpsertBookRequest {
    @NotBlank(message = "Title must not be blank")
    var title: String? = null

    @NotNull
    @Positive(message = "Author must be selected")
    var authorId: String? = null

    @NotEmpty(message = "At least one genre must be selected")
    var genreIds: Set<String> = emptySet()
}
