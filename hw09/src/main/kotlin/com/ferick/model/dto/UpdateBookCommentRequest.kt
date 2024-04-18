package com.ferick.model.dto

class UpdateBookCommentRequest(val id: Long, val bookId: Long) {
    var text: String? = null
}
