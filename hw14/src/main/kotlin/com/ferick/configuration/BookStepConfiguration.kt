package com.ferick.configuration

import com.ferick.model.entities.jpa.JpaBook
import com.ferick.model.entities.mongo.MongoBook
import com.ferick.service.processors.BookProcessor
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
class BookStepConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager
) {

    @Bean
    fun bookReader(entityManagerFactory: EntityManagerFactory): JpaCursorItemReader<JpaBook> {
        return JpaCursorItemReaderBuilder<JpaBook>()
            .name("bookItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select b from JpaBook b")
            .build()
    }

    @Bean
    fun bookProcessor(mongoOperations: MongoOperations): BookProcessor {
        return BookProcessor(mongoOperations)
    }

    @Bean
    fun bookWriter(mongoOperations: MongoOperations): MongoItemWriter<MongoBook> {
        return MongoItemWriterBuilder<MongoBook>()
            .template(mongoOperations)
            .collection("books")
            .build()
    }

    @Bean
    fun processBookStep(
        reader: ItemReader<JpaBook>,
        writer: ItemWriter<MongoBook>,
        processor: BookProcessor
    ): Step {
        return StepBuilder("processBookStep", jobRepository)
            .chunk<JpaBook, MongoBook>(CHUNK_SIZE, platformTransactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }
}
