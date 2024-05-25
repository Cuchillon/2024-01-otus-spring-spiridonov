package com.ferick.configuration

import com.ferick.model.entities.jpa.JpaGenre
import com.ferick.model.entities.mongo.MongoGenre
import com.ferick.service.RelationCache
import com.ferick.service.processors.GenreProcessor
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.data.MongoItemWriter
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.transaction.PlatformTransactionManager

private const val CHUNK_SIZE = 3
@Configuration
class GenreStepConfiguration(
    private val jobRepository: JobRepository,
    private val mongoOperations: MongoOperations,
    private val platformTransactionManager: PlatformTransactionManager,
    private val relationCache: RelationCache
) {

    @Bean
    fun genreReader(entityManagerFactory: EntityManagerFactory): JpaCursorItemReader<JpaGenre> {
        return JpaCursorItemReaderBuilder<JpaGenre>()
            .name("genreItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select g from JpaGenre g")
            .build()
    }

    @Bean
    fun genreProcessor(): GenreProcessor {
        return GenreProcessor(relationCache)
    }

    @Bean
    fun genreWriter(): MongoItemWriter<MongoGenre> {
        return MongoItemWriterBuilder<MongoGenre>()
            .template(mongoOperations)
            .collection("genres")
            .build()
    }

    @Bean
    fun processGenreStep(
        reader: ItemReader<JpaGenre>,
        writer: ItemWriter<MongoGenre>,
        processor: GenreProcessor
    ): Step {
        return StepBuilder("processGenreStep", jobRepository)
            .chunk<JpaGenre, MongoGenre>(CHUNK_SIZE, platformTransactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }
}
