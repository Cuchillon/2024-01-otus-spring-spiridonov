package com.ferick.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class UpdateBookRequest(val id: Long) {
    @NotBlank(message = "Title must not be blank")
    var title: String? = null
    @Positive(message = "Author must be selected")
    var authorId: Long? = null
    @NotEmpty(message = "At least one genre must be selected")
    var genreIds: Set<Long> = emptySet()
}
