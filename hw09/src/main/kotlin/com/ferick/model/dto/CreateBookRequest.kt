package com.ferick.model.dto

class CreateBookRequest {
    var title: String? = null
    var authorId: Long? = null
    var genreIds: String? = null

    fun genreIdSet() = genreIds?.split(", ")?.map { it.toLong() }?.toSet() ?: emptySet()
}
