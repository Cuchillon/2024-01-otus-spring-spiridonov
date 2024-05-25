package com.ferick.service.listeners

import com.ferick.model.entities.mongo.MongoBook
import com.ferick.model.entities.mongo.MongoBookComment
import com.ferick.service.RelationCache
import org.springframework.batch.core.ItemWriteListener
import org.springframework.batch.item.Chunk
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class BookCommentWriteListener(
    private val mongoOperations: MongoOperations,
    private val relationCache: RelationCache
) : ItemWriteListener<MongoBookComment> {

    override fun beforeWrite(items: Chunk<out MongoBookComment>) {
        val books = getBooks(relationCache.bookCommentRelations.values.toList())
        items.items.forEach { bookComment ->
            relationCache.bookCommentRelations[bookComment.id]?.let { bookId ->
                bookComment.book = books[bookId]
            }
        }
    }

    override fun afterWrite(items: Chunk<out MongoBookComment>) {
        relationCache.bookCommentRelations.clear()
    }

    private fun getBooks(bookIds: List<String>): Map<String, MongoBook> {
        return mongoOperations
            .find(Query(Criteria.where("id").`in`(bookIds)), MongoBook::class.java)
            .associateBy { it.id!! }
    }
}
