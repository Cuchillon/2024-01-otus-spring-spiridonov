package com.ferick.service.processors

import com.ferick.model.entities.jpa.JpaGenre
import com.ferick.model.entities.mongo.MongoGenre
import com.ferick.service.RelationCache
import org.bson.types.ObjectId
import org.springframework.batch.item.ItemProcessor

class GenreProcessor(
    private val relationCache: RelationCache
) : ItemProcessor<JpaGenre, MongoGenre> {

    override fun process(item: JpaGenre): MongoGenre {
        val genre = MongoGenre(
            id = ObjectId().toString(),
            name = item.name
        )
        relationCache.genreRelations[item.id!!] = genre.id!!
        return genre
    }
}
