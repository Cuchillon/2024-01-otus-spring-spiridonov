package com.ferick.service.processors

import com.ferick.model.entities.jpa.JpaAuthor
import com.ferick.model.entities.mongo.AuthorIds
import com.ferick.model.entities.mongo.MongoAuthor
import org.bson.types.ObjectId
import org.springframework.batch.item.ItemProcessor
import org.springframework.data.mongodb.core.MongoOperations

class AuthorProcessor(
    private val mongoOperations: MongoOperations
) : ItemProcessor<JpaAuthor, MongoAuthor> {

    override fun process(item: JpaAuthor): MongoAuthor {
        val author = MongoAuthor(
            id = ObjectId().toString(),
            fullName = item.fullName
        )
        mongoOperations.insert(AuthorIds(jpaId = item.id!!, mongoId = author.id!!))
        return author
    }
}
