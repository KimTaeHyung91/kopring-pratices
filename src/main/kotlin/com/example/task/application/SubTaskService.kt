package com.example.task.application

import com.example.task.application.inbound.SubTaskUseCase
import com.example.task.application.outbound.CUDTaskPort
import com.example.task.application.outbound.ReadTaskPort
import com.example.task.common.advice.BusinessErrorMessage
import com.example.task.common.exception.BusinessException
import com.example.task.domain.task.SubTask
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 별도 람다함수를 호출하는 tryCatch(() -> R) 보다는 명시적으로 쓰는게 좋은거 같음
 * catch 부분에서 에러 핸들링이 좋을것같음
 * inline fun <R> tryCatch(block: () -> R): R = try {block()} catch(e: Throwable) {null}
 * 위 처럼 할 경우 null을 리턴하면 service 객체에서 다시 한번 null 체크를 통해서 예외처리를 해줘야됨.
 */

@Service
class SubTaskService(
    private val readTaskPort: ReadTaskPort,
    private val cudTaskPort: CUDTaskPort,
) : SubTaskUseCase {
    @Transactional(readOnly = true)
    override fun retrieveSubTasks(taskId: Long): List<SubTaskUseCase.SubTaskInfo> =
        readTaskPort.readSubTasks(taskId).map {
            SubTaskUseCase.SubTaskInfo(
                id = it.id!!,
                name = it.name,
                reporter = it.reporter,
                description = it.description,
                assigner = it.assigner,
                createdAt = it.createdAt!!,
            )
        }

    @Transactional
    override fun createSubTask(command: SubTaskUseCase.CreateSubTaskCommand): SubTaskUseCase.IdInfo =
        try {
            val subTask =
                this.cudTaskPort.createSubTask(
                    command.taskId,
                    SubTask(
                        name = command.name,
                        description = command.description,
                        reporter = command.reporter,
                        assigner = command.assigner,
                    ),
                )

            SubTaskUseCase.IdInfo(subTask.id!!)
        } catch (e: EntityNotFoundException) {
            throw BusinessException(
                BusinessErrorMessage.SUB_TASK_NOT_FOUND
                    .apply { addrInfo = command.taskId.toString() }
                    .message,
            )
        }

    @Transactional
    override fun modifySubTask(
        id: String,
        command: SubTaskUseCase.ModifySubTaskCommand,
    ) = try {
        cudTaskPort.update(id) { subTask -> subTask.modifyInfo(command.name, command.description, command.assigner) }
    } catch (e: EntityNotFoundException) {
        throw BusinessException(
            BusinessErrorMessage.SUB_TASK_NOT_FOUND
                .apply { addrInfo = id }
                .message,
        )
    }
}
