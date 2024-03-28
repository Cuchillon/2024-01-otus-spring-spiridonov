package com.ferick.repositories.impl

import com.ferick.model.entities.Author
import com.ferick.repositories.AuthorRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class JpaAuthorRepository(
    @PersistenceContext
    private val em: EntityManager
) : AuthorRepository {

    override fun findAll(): List<Author> {
        return em.createQuery("select a from Author a", Author::class.java).resultList
    }

    override fun findById(id: Long): Author? {
        return em.find(Author::class.java, id)
    }
}
