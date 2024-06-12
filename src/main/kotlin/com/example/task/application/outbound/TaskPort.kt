@file:Suppress("ktlint:standard:filename")

package com.example.task.application.outbound

import com.example.task.domain.task.SubTask
import com.example.task.domain.task.Task

interface ReadTaskPort {
    fun read(): List<Task>

    fun readBy(id: Long): Task

    fun readSubTasks(id: Long): List<SubTask>
}

interface CUDTaskPort {
    fun create(domain: Task): Long

    fun createSubTask(
        taskId: Long,
        subTask: SubTask,
    ): SubTask

    fun update(
        id: Long,
        block: (task: Task) -> Unit,
    )

    fun update(
        id: String,
        block: (subTask: SubTask) -> Unit,
    )
}
