package com.example.task.application.inbound

import java.time.LocalDateTime

/**
 * CRUD Interface
 */
interface SubTaskUseCase {
    fun retrieveSubTasks(taskId: Long): List<SubTaskInfo>

    fun createSubTask(command: CreateSubTaskCommand): IdInfo

    fun modifySubTask(
        id: String,
        command: ModifySubTaskCommand,
    )

    data class IdInfo(val id: String)

    data class SubTaskInfo(
        val id: String,
        val name: String,
        val reporter: String,
        var description: String?,
        var assigner: String?,
        val createdAt: LocalDateTime,
    )

    data class CreateSubTaskCommand(
        val name: String,
        val reporter: String,
        val taskId: Long,
        var description: String?,
        var assigner: String?,
    )

    data class ModifySubTaskCommand(
        val name: String?,
        var description: String?,
        var assigner: String?,
    )
}
