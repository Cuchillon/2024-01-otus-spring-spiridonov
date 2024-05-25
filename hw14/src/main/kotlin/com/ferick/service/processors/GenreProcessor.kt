package com.ferick.service.processors

import com.ferick.model.entities.jpa.JpaGenre
import com.ferick.model.entities.mongo.GenreIds
import com.ferick.model.entities.mongo.MongoGenre
import org.bson.types.ObjectId
import org.springframework.batch.item.ItemProcessor
import org.springframework.data.mongodb.core.MongoOperations

class GenreProcessor(
    private val mongoOperations: MongoOperations
) : ItemProcessor<JpaGenre, MongoGenre> {

    override fun process(item: JpaGenre): MongoGenre {
        val genre = MongoGenre(
            id = ObjectId().toString(),
            name = item.name
        )
        mongoOperations.insert(GenreIds(jpaId = item.id!!, mongoId = genre.id!!))
        return genre
    }
}
