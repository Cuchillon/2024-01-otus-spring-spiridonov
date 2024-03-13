package com.ferick.repositories.impl

import com.ferick.model.Author
import com.ferick.repositories.AuthorRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcAuthorRepository(
    private val jdbc: NamedParameterJdbcOperations
) : AuthorRepository {

    override fun findAll(): List<Author> {
        return jdbc.query("SELECT id, full_name FROM authors", AuthorRowMapper())
    }

    override fun findById(id: Long): Author? {
        val params = MapSqlParameterSource()
            .addValue("id", id)
        return jdbc.queryForObject("SELECT id, full_name FROM authors WHERE id = :id", params, AuthorRowMapper())
    }

    private class AuthorRowMapper : RowMapper<Author> {

        override fun mapRow(rs: ResultSet, rowNum: Int): Author {
            val id = rs.getLong("id")
            val fullName = rs.getString("full_name")
            return Author(id, fullName)
        }
    }
}
