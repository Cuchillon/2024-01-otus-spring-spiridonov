package com.ferick.model.dto

class CreateBookRequest {
    var title: String? = null
    var authorId: Long? = null
    var genreIds: Set<Long> = emptySet()
}
