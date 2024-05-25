package com.ferick.configuration

import com.ferick.model.entities.jpa.JpaBookComment
import com.ferick.model.entities.mongo.MongoBookComment
import com.ferick.service.processors.BookCommentProcessor
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
class BookCommentStepConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager
) {

    @Bean
    fun bookCommentReader(entityManagerFactory: EntityManagerFactory): JpaCursorItemReader<JpaBookComment> {
        return JpaCursorItemReaderBuilder<JpaBookComment>()
            .name("bookCommentItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select bc from JpaBookComment bc")
            .build()
    }

    @Bean
    fun bookCommentProcessor(mongoOperations: MongoOperations): BookCommentProcessor {
        return BookCommentProcessor(mongoOperations)
    }

    @Bean
    fun bookCommentWriter(mongoOperations: MongoOperations): MongoItemWriter<MongoBookComment> {
        return MongoItemWriterBuilder<MongoBookComment>()
            .template(mongoOperations)
            .collection("book_comments")
            .build()
    }

    @Bean
    fun processBookCommentStep(
        reader: ItemReader<JpaBookComment>,
        writer: ItemWriter<MongoBookComment>,
        processor: BookCommentProcessor
    ): Step {
        return StepBuilder("processBookCommentStep", jobRepository)
            .chunk<JpaBookComment, MongoBookComment>(CHUNK_SIZE, platformTransactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }
}