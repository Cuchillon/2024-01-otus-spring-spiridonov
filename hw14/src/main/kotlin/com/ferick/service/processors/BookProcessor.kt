package com.ferick.service.processors

import com.ferick.model.entities.jpa.JpaBook
import com.ferick.model.entities.mongo.AuthorIds
import com.ferick.model.entities.mongo.BookIds
import com.ferick.model.entities.mongo.GenreIds
import com.ferick.model.entities.mongo.MongoAuthor
import com.ferick.model.entities.mongo.MongoBook
import com.ferick.model.entities.mongo.MongoGenre
import org.bson.types.ObjectId
import org.springframework.batch.item.ItemProcessor
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query

class BookProcessor(
    private val mongoOperations: MongoOperations
) : ItemProcessor<JpaBook, MongoBook> {

    override fun process(item: JpaBook): MongoBook {
        val author = getAuthor(item.author.id!!)
        val genres = getGenres(item.genres.map { it.id!! })
        val book = MongoBook(
            id = ObjectId().toString(),
            title = item.title,
            author = author,
            genres = genres
        )
        mongoOperations.insert(BookIds(jpaId = item.id!!, mongoId = book.id!!))
        return book
    }

    private fun getAuthor(jpaId: Long): MongoAuthor {
        val authorId = mongoOperations.findOne(
            Query(where("jpaId").`is`(jpaId)), AuthorIds::class.java
        )!!.mongoId
        return mongoOperations.findOne(Query(where("id").`is`(authorId)), MongoAuthor::class.java)!!
    }

    private fun getGenres(jpaIds: List<Long>): List<MongoGenre> {
        val genreIds = mongoOperations.find(
            Query(where("jpaId").`in`(jpaIds)), GenreIds::class.java
        ).map { it.mongoId }
        return mongoOperations.find(Query(where("id").`in`(genreIds)), MongoGenre::class.java)
    }
}
