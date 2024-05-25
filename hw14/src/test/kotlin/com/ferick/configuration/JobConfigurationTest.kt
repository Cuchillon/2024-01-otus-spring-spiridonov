package com.ferick.configuration

import com.ferick.model.entities.mongo.MongoAuthor
import com.ferick.model.entities.mongo.MongoBook
import com.ferick.model.entities.mongo.MongoBookComment
import com.ferick.model.entities.mongo.MongoGenre
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.JobRepositoryTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoOperations

@SpringBootTest
@SpringBatchTest
class JobConfigurationTest {

    @Autowired
    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    private lateinit var jobRepositoryTestUtils: JobRepositoryTestUtils

    @Autowired
    private lateinit var mongoOperations: MongoOperations

    @BeforeEach
    fun clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions()
    }

    @Test
    fun testJob() {
        val jobExecution = jobLauncherTestUtils.launchJob()
        val mongoAuthors = mongoOperations.findAll(MongoAuthor::class.java)
        val mongoGenres = mongoOperations.findAll(MongoGenre::class.java)
        val mongoBooks = mongoOperations.findAll(MongoBook::class.java)
        val mongoBookComments = mongoOperations.findAll(MongoBookComment::class.java)
        assertThat(jobExecution.exitStatus.exitCode).isEqualTo("COMPLETED")
        assertThat(mongoAuthors).hasSize(3)
        assertThat(mongoGenres).hasSize(6)
        assertThat(mongoBooks).hasSize(3)
        mongoBooks.forEach { book ->
            assertThat(book.genres).hasSize(2)
        }
        assertThat(mongoBookComments).hasSize(6)
    }
}
