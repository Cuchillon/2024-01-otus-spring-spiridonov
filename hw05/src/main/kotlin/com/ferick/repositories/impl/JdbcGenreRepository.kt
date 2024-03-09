package com.ferick.repositories.impl

import com.ferick.model.Genre
import com.ferick.repositories.GenreRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcGenreRepository(
    private val jdbc: NamedParameterJdbcOperations
) : GenreRepository {

    override fun findAll(): List<Genre> {
        return jdbc.query("SELECT id, name FROM authors", GenreRowMapper())
    }

    override fun findAllByIds(ids: Set<Long>): List<Genre> {
        val params = MapSqlParameterSource()
            .addValue("ids", ids)
        return jdbc.query("SELECT id, full_name FROM authors WHERE id IN (:ids)", params, GenreRowMapper())
    }

    private class GenreRowMapper : RowMapper<Genre> {

        override fun mapRow(rs: ResultSet, rowNum: Int): Genre {
            val id = rs.getLong("id")
            val name = rs.getString("name")
            return Genre(id, name)
        }
    }
}
