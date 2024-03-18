package com.ferick.repositories.impl

import com.ferick.model.entities.BookComment
import com.ferick.repositories.BookCommentRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class JpaBookCommentRepository(
    @PersistenceContext
    private val em: EntityManager
) : BookCommentRepository {

    override fun findById(id: Long): BookComment? {
        return em.find(BookComment::class.java, id)
    }

    override fun findByBookId(bookId: Long): List<BookComment> {
        val query = em.createQuery("select bc from BookComment bc where bc.bookId = :bookId", BookComment::class.java)
        query.setParameter("bookId", bookId)
        return query.resultList
    }

    override fun save(bookComment: BookComment): BookComment {
        return if (bookComment.id == null) {
            em.persist(bookComment)
            bookComment
        } else {
            em.merge(bookComment)
        }
    }

    override fun deleteById(id: Long) {
        val bookComment = em.find(BookComment::class.java, id)
        em.remove(bookComment)
    }
}
