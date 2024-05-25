package com.ferick.configuration

import com.ferick.service.CleanUpService
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.job.flow.support.SimpleFlow
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class JobConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
    private val cleanUpService: CleanUpService
) {

    @Bean
    fun libraryJob(
        splitFlow: Flow,
        processBookStep: Step,
        processBookCommentStep: Step,
        cleanUpStep: Step
    ): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(splitFlow)
            .next(processBookStep)
            .next(processBookCommentStep)
            .next(cleanUpStep)
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

    @Bean
    fun cleanUpStep(): Step {
        return StepBuilder("cleanUpStep", jobRepository)
            .tasklet(cleanUpTasklet(), platformTransactionManager)
            .build()
    }

    @Bean
    fun cleanUpTasklet(): MethodInvokingTaskletAdapter {
        val adapter = MethodInvokingTaskletAdapter()
        adapter.setTargetObject(cleanUpService)
        adapter.setTargetMethod("cleanUp")
        return adapter
    }

    companion object {
        private const val JOB_NAME = "libraryJob"
    }
}
