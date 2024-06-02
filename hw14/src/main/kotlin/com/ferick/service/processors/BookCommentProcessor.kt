package com.ferick.service.processors

import com.ferick.model.entities.jpa.JpaBookComment
import com.ferick.model.entities.mongo.MongoBookComment
import com.ferick.service.RelationCache
import org.bson.types.ObjectId
import org.springframework.batch.item.ItemProcessor

class BookCommentProcessor(
    private val relationCache: RelationCache
) : ItemProcessor<JpaBookComment, MongoBookComment> {

    override fun process(item: JpaBookComment): MongoBookComment {
        val bookComment = MongoBookComment(
            id = ObjectId().toString(),
            text = item.text
        )
        val bookId = relationCache.bookRelations[item.book.id]!!
        relationCache.bookCommentRelations[bookComment.id!!] = bookId
        return bookComment
    }
}
