package com.ferick.repositories.impl

import com.ferick.model.entities.Genre
import com.ferick.repositories.GenreRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class JpaGenreRepository(
    @PersistenceContext
    private val em: EntityManager
) : GenreRepository {

    override fun findAll(): List<Genre> {
        return em.createQuery("select g from Genre g", Genre::class.java).resultList
    }

    override fun findAllByIds(ids: Set<Long>): List<Genre> {
        val query = em.createQuery("select g from Genre g where g.id in :ids", Genre::class.java)
        query.setParameter("ids", ids)
        return query.resultList
    }
}
