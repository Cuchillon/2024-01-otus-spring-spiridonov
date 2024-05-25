package com.ferick.configuration

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.job.flow.support.SimpleFlow
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor

@Configuration
class JobConfiguration(
    private val jobRepository: JobRepository
) {

    @Bean
    fun libraryJob(
        splitFlow: Flow,
        processBookStep: Step,
        processBookCommentStep: Step
    ): Job {
        return JobBuilder("libraryJob", jobRepository)
            .start(splitFlow)
            .next(processBookStep)
            .next(processBookCommentStep)
            .end()
            .build()
    }

    @Bean
    fun splitFlow(
        taskExecutor: TaskExecutor,
        processAuthorStep: Step,
        processGenreStep: Step
    ): Flow {
        return FlowBuilder<SimpleFlow>("splitFlow")
            .split(taskExecutor)
            .add(
                FlowBuilder<SimpleFlow>("authorFlow")
                    .start(processAuthorStep)
                    .build(),
                FlowBuilder<SimpleFlow>("genreFlow")
                    .start(processGenreStep)
                    .build()
            )
            .build()
    }

    @Bean
    fun taskExecutor(): TaskExecutor {
        return SimpleAsyncTaskExecutor("spring_batch")
    }
}
