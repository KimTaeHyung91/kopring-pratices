package com.example.task.adapter.inbound.rest

import com.example.task.application.inbound.CreateTaskCommand
import com.example.task.application.inbound.CreateTaskUseCase
import com.example.task.application.inbound.ModifyTaskCommand
import com.example.task.application.inbound.ModifyTaskUseCase
import com.example.task.application.inbound.RetrieveTaskUseCase
import com.example.task.application.inbound.RetrieveTasksUseCase
import com.example.task.application.inbound.TaskInfo
import com.example.task.common.response.Response
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/tasks")
class TaskRestController(
    private val retrieveTasksUseCase: RetrieveTasksUseCase,
    private val retrieveTaskUseCase: RetrieveTaskUseCase,
    private val modifyTaskUseCase: ModifyTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveTasks(): Response<List<TaskInfo>> = Response.success(data = retrieveTasksUseCase.retrieveTasks())

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveTask(
        @PathVariable("id") id: Long,
    ): Response<TaskInfo> = Response.success(data = retrieveTaskUseCase.retrieveTask(id))

    @PostMapping
    fun createTask(
        @Valid @RequestBody request: RequestCreateTaskDto,
    ): Response.Success<Long> {
        val id = createTaskUseCase.createTask(CreateTaskCommand(request.name!!, request.description))
        return Response.success(data = id)
    }

    @PutMapping("{id}")
    fun modifyTask(
        @PathVariable("id") id: Long,
        @Valid @RequestBody request: RequestModifyTaskDto,
    ): Response.Empty<Nothing> {
        val command = ModifyTaskCommand(name = request.name!!, description = request.description)
        modifyTaskUseCase.modifyTask(id, command)

        return Response.empty()
    }
}
