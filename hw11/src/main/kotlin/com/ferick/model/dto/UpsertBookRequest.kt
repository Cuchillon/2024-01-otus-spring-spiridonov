package com.ferick.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

class UpsertBookRequest {
    @NotBlank(message = "Title must not be blank")
    var title: String? = null

    @NotBlank(message = "Author must be selected")
    var authorId: String? = null

    @NotEmpty(message = "At least one genre must be selected")
    var genreIds: Set<String> = emptySet()
}
