package com.ferick.repositories.impl

import com.ferick.model.entities.Genre
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaGenreRepository::class)
class JpaGenreRepositoryTest {

    @Autowired
    private lateinit var genreRepository: JpaGenreRepository

    @Test
    fun `should return correct genre list`() {
        val actualGenres = genreRepository.findAll()
        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres)
    }

    @Test
    fun `should return correct genre list by ids`() {
        val expectedGenreIds = setOf<Long>(2, 4, 6)
        val expectedGenresByIds = expectedGenres.filter { it.id in expectedGenreIds }
        val actualGenres = genreRepository.findAllByIds(expectedGenreIds)
        assertThat(actualGenres).containsExactlyElementsOf(expectedGenresByIds)
    }

    companion object {
        private val expectedGenres = listOf(
            Genre(id = 1L, name = "Science fiction"),
            Genre(id = 2L, name = "Detective fiction"),
            Genre(id = 3L, name = "Thriller"),
            Genre(id = 4L, name = "Horror"),
            Genre(id = 5L, name = "Erotic"),
            Genre(id = 6L, name = "Porn")
        )
    }
}
