package com.ferick.model.dto

class UpdateBookRequest(val id: Long) {
    var title: String? = null
    var authorId: Long? = null
    var genreIds: Set<Long> = emptySet()
}
