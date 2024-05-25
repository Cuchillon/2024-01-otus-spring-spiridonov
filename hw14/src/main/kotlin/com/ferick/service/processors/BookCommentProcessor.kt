package com.ferick.service.processors

import com.ferick.model.entities.jpa.JpaBookComment
import com.ferick.model.entities.mongo.BookIds
import com.ferick.model.entities.mongo.MongoBook
import com.ferick.model.entities.mongo.MongoBookComment
import org.bson.types.ObjectId
import org.springframework.batch.item.ItemProcessor
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query

class BookCommentProcessor(
    private val mongoOperations: MongoOperations
) : ItemProcessor<JpaBookComment, MongoBookComment> {

    override fun process(item: JpaBookComment): MongoBookComment {
        val book = getBook(item.book.id!!)
        val bookComment = MongoBookComment(
            id = ObjectId().toString(),
            text = item.text,
            book = book
        )
        return bookComment
    }

    private fun getBook(jpaId: Long): MongoBook {
        val bookId = mongoOperations.findOne(
            Query(where("jpaId").`is`(jpaId)), BookIds::class.java
        )!!.mongoId
        return mongoOperations.findOne(Query(where("id").`is`(bookId)), MongoBook::class.java)!!
    }
}
