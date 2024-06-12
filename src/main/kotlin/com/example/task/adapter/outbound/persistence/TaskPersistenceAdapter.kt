package com.example.task.adapter.outbound.persistence

import com.example.task.adapter.outbound.persistence.entity.task.SubTaskEntity
import com.example.task.adapter.outbound.persistence.entity.task.TaskEntity
import com.example.task.application.outbound.CUDTaskPort
import com.example.task.application.outbound.ReadTaskPort
import com.example.task.domain.task.SubTask
import com.example.task.domain.task.Task
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component

@Component
class TaskPersistenceAdapter(
    private val taskJpaRepository: TaskJpaRepository,
    private val subTaskJpaRepository: SubTaskJpaRepository,
) : ReadTaskPort, CUDTaskPort {
    override fun read(): List<Task> = taskJpaRepository.findAll().map { Task(id = it.id!!, name = it.name, description = it.description) }

    override fun readBy(id: Long): Task =
        taskJpaRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException() }
            .let {
                Task(id = it.id!!, name = it.name, description = it.description)
            }

    override fun readSubTasks(id: Long): List<SubTask> =
        subTaskJpaRepository.findAll {
            select(entity(SubTaskEntity::class))
                .from(entity(SubTaskEntity::class))
                .where(
                    path(SubTaskEntity::taskEntity)(TaskEntity::id).eq(id),
                )
        }.filterNotNull()
            .map {
                SubTask(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    assigner = it.assigner,
                    reporter = it.reporter,
                    createdAt = it.createdAt,
                )
            }

    override fun create(domain: Task): Long = taskJpaRepository.save(TaskEntity(name = domain.name, description = domain.description)).id!!

    override fun createSubTask(
        taskId: Long,
        subTask: SubTask,
    ): SubTask =
        taskJpaRepository.findById(taskId)
            .orElseThrow { throw EntityNotFoundException() }
            .let {
                subTaskJpaRepository.save(
                    SubTaskEntity(
                        name = subTask.name,
                        description = subTask.description,
                        assigner = subTask.assigner,
                        reporter = subTask.reporter,
                        taskEntity = it,
                    ),
                )
            }.let {
                SubTask(
                    id = it.id,
                    name = it.name,
                    reporter = it.reporter,
                    description = it.description,
                    assigner = it.assigner,
                    createdAt = it.createdAt,
                )
            }

    override fun update(
        id: Long,
        block: (task: Task) -> Unit,
    ) {
        val taskEntity =
            taskJpaRepository.findById(id)
                .orElseThrow { throw EntityNotFoundException() }
        val task = taskEntity.toDomain()

        block(task)
        taskEntity.update(task)
    }

    override fun update(
        id: String,
        block: (subTask: SubTask) -> Unit,
    ) {
        val subTaskEntity =
            subTaskJpaRepository.findById(id)
                .orElseThrow { throw EntityNotFoundException() }

        val subTask = subTaskEntity.toDomain()

        block(subTask)

        subTaskEntity.update(subTask)
    }
}
