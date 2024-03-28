package com.ferick.repositories

import com.ferick.model.entities.BookComment
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookCommentRepository : JpaRepository<BookComment, Long> {

    @EntityGraph("book-comment-entity-graph")
    override fun findById(id: Long): Optional<BookComment>

    fun findByBookId(bookId: Long): List<BookComment>
}
