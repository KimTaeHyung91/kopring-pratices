@file:Suppress("ktlint:standard:filename")

package com.example.task.application.inbound

data class TaskInfo(val id: Long, val name: String, val description: String?)

data class ModifyTaskCommand(val name: String, var description: String?)

data class CreateTaskCommand(val name: String, var description: String?)

interface RetrieveTasksUseCase {
    fun retrieveTasks(): List<TaskInfo>
}

interface RetrieveTaskUseCase {
    fun retrieveTask(id: Long): TaskInfo
}

interface CreateTaskUseCase {
    fun createTask(command: CreateTaskCommand): Long
}

interface ModifyTaskUseCase {
    fun modifyTask(
        id: Long,
        command: ModifyTaskCommand,
    )
}
