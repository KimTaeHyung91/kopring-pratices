package com.example.task.adapter.inbound.rest

import com.example.task.application.inbound.SubTaskUseCase
import com.example.task.common.response.Response
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * controller dto convert to command
 * dto.run 으로 mapping 작업하는것도 나쁘지않은거 같음.
 */

@RestController
@RequestMapping("/api/v1/subtask")
class SubTaskRestController(private val subTaskUseCase: SubTaskUseCase) {
    @PostMapping
    fun createSubTask(
        @Valid @RequestBody request: RequestCreateSubTaskDto,
    ): Response.Success<SubTaskUseCase.IdInfo> {
        val command =
            request.let {
                SubTaskUseCase.CreateSubTaskCommand(
                    name = it.name!!,
                    reporter = it.reporter!!,
                    taskId = it.taskId!!,
                    description = it.description,
                    assigner = it.assigner,
                )
            }

        return Response.success(data = subTaskUseCase.createSubTask(command))
    }

    @GetMapping
    fun retrieveSubTasks(request: RequestQueryRetrieveSubTasksDto): Response.Success<List<SubTaskUseCase.SubTaskInfo>> =
        Response.success(data = subTaskUseCase.retrieveSubTasks(request.taskId))

    @PutMapping("{id}")
    fun modifySubTask(
        @PathVariable("id") id: String,
        @Valid @RequestBody request: RequestModifySubTaskDto,
    ): Response.Empty<Nothing> {
        val command =
            request.let {
                SubTaskUseCase.ModifySubTaskCommand(
                    name = it.name,
                    description = it.description,
                    assigner = it.assigner,
                )
            }

        subTaskUseCase.modifySubTask(id, command)
        return Response.empty()
    }
}
