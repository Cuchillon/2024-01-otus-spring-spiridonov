package com.ferick.service.processors

import com.ferick.model.entities.jpa.JpaAuthor
import com.ferick.model.entities.mongo.MongoAuthor
import com.ferick.service.RelationCache
import org.bson.types.ObjectId
import org.springframework.batch.item.ItemProcessor

class AuthorProcessor(
    private val relationCache: RelationCache
) : ItemProcessor<JpaAuthor, MongoAuthor> {

    override fun process(item: JpaAuthor): MongoAuthor {
        val author = MongoAuthor(
            id = ObjectId().toString(),
            fullName = item.fullName
        )
        relationCache.authorRelations[item.id!!] = author.id!!
        return author
    }
}
