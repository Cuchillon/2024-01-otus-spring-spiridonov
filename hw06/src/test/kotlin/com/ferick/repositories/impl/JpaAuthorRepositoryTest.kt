package com.ferick.repositories.impl

import com.ferick.model.entities.Author
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaAuthorRepository::class)
class JpaAuthorRepositoryTest {

    @Autowired
    private lateinit var em: TestEntityManager

    @Autowired
    private lateinit var authorRepository: JpaAuthorRepository

    @Test
    fun `should return correct author list`() {
        val expectedAuthors = em.entityManager.createQuery("select a from Author a", Author::class.java).resultList
        val actualAuthors = authorRepository.findAll()
        actualAuthors.forEachIndexed { i, author ->
            assertThat(author).usingRecursiveComparison().isEqualTo(expectedAuthors[i])
        }
    }

    @ParameterizedTest(name = "should return correct author by id {0}")
    @ValueSource(longs = [1L, 2L, 3L])
    fun `should return correct author by id`(expectedAuthorId: Long) {
        val expectedAuthor = em.find(Author::class.java, expectedAuthorId)
        val actualAuthor = authorRepository.findById(expectedAuthorId)
        assertThat(actualAuthor)
            .isNotNull()
            .usingRecursiveComparison().isEqualTo(expectedAuthor)
    }
}
