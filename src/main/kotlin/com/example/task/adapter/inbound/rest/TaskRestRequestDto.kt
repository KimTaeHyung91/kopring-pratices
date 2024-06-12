@file:Suppress("ktlint:standard:filename")

package com.example.task.adapter.inbound.rest

import jakarta.validation.constraints.NotNull

data class RequestModifyTaskDto(
    // jackson 라이브러리에서 생성자를 만들어줘야되는데 non-nullable 이어서 역직렬화를 못했던거, validation check 하게 nullable 하게 바꿈.
    // 실제 호출쪽에서는 !! 연산자로 고정
    @field:NotNull(message = "name은 필수입니다.")
    val name: String?,
    var description: String?,
)

data class RequestCreateTaskDto(
    @field:NotNull(message = "name은 필수입니다.")
    val name: String?,
    var description: String?,
)

data class RequestCreateSubTaskDto(
    @field:NotNull(message = "name은 필수입니다.")
    val name: String?,
    @field:NotNull(message = "reporter은 필수입니다.")
    val reporter: String?,
    @field:NotNull(message = "taskId는 필수입니다.")
    val taskId: Long?,
    val description: String?,
    val assigner: String?,
)

data class RequestQueryRetrieveSubTasksDto(val taskId: Long)

data class RequestModifySubTaskDto(
    val name: String?,
    val description: String?,
    val assigner: String?,
)
