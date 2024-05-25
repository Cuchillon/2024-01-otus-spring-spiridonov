package com.ferick.service

import com.ferick.model.dto.BookAllRelations
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class RelationCache {
    val authorRelations = ConcurrentHashMap<Long, String>()
    val genreRelations = ConcurrentHashMap<Long, String>()
    val bookRelations = ConcurrentHashMap<Long, String>()
    val bookCommentRelations = ConcurrentHashMap<String, String>()
    val bookAllRelations = ConcurrentHashMap<String, BookAllRelations>()

    fun findAllAuthorIds(): List<String> {
        return bookAllRelations.values.map { it.authorId }
    }

    fun findAllGenreIds(): List<String> {
        return bookAllRelations.values.flatMap { it.genreIds }
    }

    fun clearRelations() {
        bookRelations.clear()
        genreRelations.clear()
        authorRelations.clear()
    }
}
