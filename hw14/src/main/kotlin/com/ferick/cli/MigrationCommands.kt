package com.ferick.cli

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MigrationCommands(
    private val jobLauncher: JobLauncher,
    private val libraryJob: Job
) {

    @ShellMethod("Migrate from jpa to mongo db", key = ["migrate"])
    fun migrate(): String {
        val execution = jobLauncher.run(libraryJob, JobParameters())
        return execution.status.name
    }
}
