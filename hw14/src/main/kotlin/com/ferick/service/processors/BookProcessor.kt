package com.ferick.service.processors

import com.ferick.model.entities.jpa.JpaBook
import com.ferick.model.entities.mongo.BookAllRelations
import com.ferick.model.entities.mongo.MongoBook
import com.ferick.service.RelationCache
import org.bson.types.ObjectId
import org.springframework.batch.item.ItemProcessor

class BookProcessor(
    private val relationCache: RelationCache
) : ItemProcessor<JpaBook, MongoBook> {

    override fun process(item: JpaBook): MongoBook {
        val book = MongoBook(
            id = ObjectId().toString(),
            title = item.title
        )
        relationCache.bookRelations[item.id!!] = book.id!!
        val authorId = relationCache.authorRelations[item.author.id]!!
        val genreIds = item.genres.mapNotNull { relationCache.genreRelations[it.id] }
        relationCache.bookAllRelations[book.id!!] = BookAllRelations(authorId, genreIds)
        return book
    }
}
