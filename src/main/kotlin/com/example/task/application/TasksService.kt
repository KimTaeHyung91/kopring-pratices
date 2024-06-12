package com.example.task.application

import com.example.task.application.inbound.CreateTaskCommand
import com.example.task.application.inbound.CreateTaskUseCase
import com.example.task.application.inbound.ModifyTaskCommand
import com.example.task.application.inbound.ModifyTaskUseCase
import com.example.task.application.inbound.RetrieveTaskUseCase
import com.example.task.application.inbound.RetrieveTasksUseCase
import com.example.task.application.inbound.TaskInfo
import com.example.task.application.outbound.CUDTaskPort
import com.example.task.application.outbound.ReadTaskPort
import com.example.task.common.exception.BusinessException
import com.example.task.domain.task.Task
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TasksService(
    private val readTaskPort: ReadTaskPort,
    private val cudTaskPort: CUDTaskPort,
) : RetrieveTasksUseCase, RetrieveTaskUseCase, ModifyTaskUseCase, CreateTaskUseCase {
    @Transactional(readOnly = true)
    override fun retrieveTasks(): List<TaskInfo> =
        readTaskPort.read().map {
            TaskInfo(id = it.id, name = it.name, description = it.description)
        }

    @Transactional(readOnly = true)
    override fun retrieveTask(id: Long): TaskInfo =
        try {
            readTaskPort.readBy(id).let { TaskInfo(id = it.id, name = it.name, description = it.description) }
        } catch (e: EntityNotFoundException) {
            throw BusinessException("태스크 정보를 찾을 수 없습니다. (${id}번)")
        }

    @Transactional
    override fun createTask(command: CreateTaskCommand): Long =
        try {
            cudTaskPort.create(Task(name = command.name, description = command.description))
        } catch (e: RuntimeException) {
            throw BusinessException("저장 오류: 태스크 저장을 실패했습니다.")
        }

    @Transactional
    override fun modifyTask(
        id: Long,
        command: ModifyTaskCommand,
    ) = try {
        cudTaskPort.update(id) { task: Task -> task.modify(command.name, command.description) }
    } catch (e: EntityNotFoundException) {
        throw BusinessException("수정 오류: 태스크 정보를 찾을 수 없습니다. (${id}번)")
    }
}
