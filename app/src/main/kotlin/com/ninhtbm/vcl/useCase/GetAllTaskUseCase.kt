package com.ninhtbm.vcl.useCase

import com.ninhtbm.vcl.useCase.model.Task

object GetAllTaskUseCase {

    operator fun invoke(): Result<List<Task>> {
        val data = listOf<Task>( //fixme: sample data!
            Task(
                name = "Complete project proposal",
                icon = null, // No icon
                durationSeconds = 120
            ),
            Task(
                name = "Morning workout",
                icon = null, // No icon
                durationSeconds = 45
            ),
            Task(
                name = "Team meeting",
                icon = null, // No icon
                durationSeconds = 30
            ),
            Task(
                name = "Review code changes",
                icon = null, // No icon
                durationSeconds = 60
            ),
            Task(
                name = "Read new articles",
                icon = null, // No icon
                durationSeconds = 20
            ),
            Task(
                name = "Buy groceries",
                icon = null, // No icon
                durationSeconds = 90
            ),
            Task(
                name = "Learn new Kotlin features",
                icon = null, // No icon
                durationSeconds = 50
            ),
            Task(
                name = "Walk the dog",
                icon = null, // No icon
                durationSeconds = 25
            ),
            Task(
                name = "Meditation session",
                icon = null, // No icon
                durationSeconds = 15
            ),
            Task(
                name = "Prepare dinner",
                icon = null, // No icon
                durationSeconds = 60
            )
        )
        return Result.success(data)
    }
}