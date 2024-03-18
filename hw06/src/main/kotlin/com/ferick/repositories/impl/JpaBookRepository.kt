package com.ferick.repositories.impl

import com.ferick.model.entities.Book
import com.ferick.repositories.BookRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType
import org.springframework.stereotype.Repository

@Repository
class JpaBookRepository(
    @PersistenceContext
    private val em: EntityManager
) : BookRepository {

    override fun findAll(): List<Book> {
        val entityGraph = em.getEntityGraph("book-author-comments-entity-graph")
        val query = em.createQuery("select b from Book b", Book::class.java)
        query.setHint(EntityGraphType.FETCH.key, entityGraph)
        return query.resultList
    }

    override fun findById(id: Long): Book? {
        val entityGraph = em.getEntityGraph("book-author-comments-entity-graph")
        val query = em.createQuery("select b from Book b where b.id = :id", Book::class.java)
        query.setParameter("id", id)
        query.setHint(EntityGraphType.FETCH.key, entityGraph)
        return query.resultList.firstOrNull()
    }

    override fun save(book: Book): Book {
        return if (book.id == null) {
            em.persist(book)
            book
        } else {
            em.merge(book)
        }
    }

    override fun deleteById(id: Long) {
        val book = em.find(Book::class.java, id)
        em.remove(book)
    }
}
