package com.ferick.repositories

import com.ferick.model.entities.Book
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository : JpaRepository<Book, Long> {

    @EntityGraph("book-author-entity-graph")
    override fun findAll(): List<Book>

    @EntityGraph("book-author-entity-graph")
    override fun findById(id: Long): Optional<Book>
}
