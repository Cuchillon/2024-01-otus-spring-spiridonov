package com.ferick.configuration

import com.ferick.model.entities.jpa.JpaAuthor
import com.ferick.model.entities.mongo.MongoAuthor
import com.ferick.service.processors.AuthorProcessor
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
class AuthorStepConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager
) {

    @Bean
    fun authorReader(entityManagerFactory: EntityManagerFactory): JpaCursorItemReader<JpaAuthor> {
        return JpaCursorItemReaderBuilder<JpaAuthor>()
            .name("authorItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select a from JpaAuthor a")
            .build()
    }

    @Bean
    fun authorProcessor(mongoOperations: MongoOperations): AuthorProcessor {
        return AuthorProcessor(mongoOperations)
    }

    @Bean
    fun authorWriter(mongoOperations: MongoOperations): MongoItemWriter<MongoAuthor> {
        return MongoItemWriterBuilder<MongoAuthor>()
            .template(mongoOperations)
            .collection("authors")
            .build()
    }

    @Bean
    fun processAuthorStep(
        reader: ItemReader<JpaAuthor>,
        writer: ItemWriter<MongoAuthor>,
        processor: AuthorProcessor
    ): Step {
        return StepBuilder("processAuthorStep", jobRepository)
            .chunk<JpaAuthor, MongoAuthor>(CHUNK_SIZE, platformTransactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }
}
