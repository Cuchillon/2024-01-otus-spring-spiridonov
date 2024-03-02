package com.ferick.cli

import com.ferick.model.Student
import com.ferick.service.TestRunnerService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class TestRunCommands(
    private val testRunnerService: TestRunnerService
) {

    @ShellMethod("Run testing for given student", key = ["run"])
    fun register(
        @ShellOption("--first-name", "-f") firstName: String,
        @ShellOption("--last-name", "-l") lastName: String,
    ) {
        val student = Student(firstName, lastName)
        testRunnerService.run(student)
    }
}
