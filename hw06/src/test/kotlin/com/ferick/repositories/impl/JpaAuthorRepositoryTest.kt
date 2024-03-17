package com.ferick.repositories.impl

import com.ferick.model.entities.Author
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaAuthorRepository::class)
class JpaAuthorRepositoryTest {

    @Autowired
    private lateinit var authorRepository: JpaAuthorRepository

    @Test
    fun `should return correct author list`() {
        val actualAuthors = authorRepository.findAll()
        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthors)
    }

    @ParameterizedTest(name = "should return correct author by id")
    @MethodSource("authorProvider")
    fun `should return correct author by id`(expectedAuthor: Author) {
        val actualAuthor = authorRepository.findById(expectedAuthor.id!!)
        assertThat(actualAuthor)
            .isNotNull()
            .isEqualTo(expectedAuthor)
    }

    companion object {
        private val expectedAuthors = listOf(
            Author(id = 1L, fullName = "Conan Quasi-Doyle"),
            Author(id = 2L, fullName = "Emmanuelle Quasi-Arsan"),
            Author(id = 3L, fullName = "Bram Quasi-Stoker")
        )

        @JvmStatic
        fun authorProvider() = expectedAuthors
    }
}
