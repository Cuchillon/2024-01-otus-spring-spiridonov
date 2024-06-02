package com.ferick.service.listeners

import com.ferick.model.entities.mongo.MongoAuthor
import com.ferick.model.entities.mongo.MongoBook
import com.ferick.model.entities.mongo.MongoGenre
import com.ferick.service.RelationCache
import org.springframework.batch.core.ItemWriteListener
import org.springframework.batch.item.Chunk
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class BookWriteListener(
    private val mongoOperations: MongoOperations,
    private val relationCache: RelationCache
) : ItemWriteListener<MongoBook> {

    override fun beforeWrite(items: Chunk<out MongoBook>) {
        val authors = getAuthors(relationCache.findAllAuthorIds())
        val genres = getGenres(relationCache.findAllGenreIds())
        items.items.forEach { book ->
            relationCache.bookAllRelations[book.id]?.let { relations ->
                book.author = authors[relations.authorId]
                book.genres = relations.genreIds.mapNotNull { genres[it] }
            }
        }
    }

    override fun afterWrite(items: Chunk<out MongoBook>) {
        relationCache.bookAllRelations.clear()
    }

    private fun getAuthors(authorIds: List<String>): Map<String, MongoAuthor> {
        return mongoOperations
            .find(Query(Criteria.where("id").`in`(authorIds)), MongoAuthor::class.java)
            .associateBy { it.id!! }
    }

    private fun getGenres(genreIds: List<String>): Map<String, MongoGenre> {
        return mongoOperations
            .find(Query(Criteria.where("id").`in`(genreIds)), MongoGenre::class.java)
            .associateBy { it.id!! }
    }
}
